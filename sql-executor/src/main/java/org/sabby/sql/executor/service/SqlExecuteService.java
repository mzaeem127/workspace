package org.sabby.sql.executor.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.sabby.sql.executor.RestJsonController;
import org.sabby.sql.executor.config.Database;
import org.sabby.sql.executor.config.Databases;
import org.sabby.sql.executor.domain.ScriptExecutionItemRecord;
import org.sabby.sql.executor.parser.MySqlParser;
import org.sabby.sql.executor.parser.OracleSqlParser;
import org.sabby.sql.executor.parser.SqlParser;
import org.sabby.sql.executor.parser.SqlServerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlExecuteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecuteService.class);

	private List<Database> databases;

	public SqlExecuteService() {
		try {
			InputStream inputStream = RestJsonController.class.getResourceAsStream("/databases.xml");
			databases = Databases.loadXml(inputStream).getDatabases();
		} catch (JAXBException e) {
			LOGGER.error("Error while loading databases.xml");
		}
	}

	public List<Database> getDatabases() {
		return databases;
	}

	public Database getDatabase(String id) {
		for (Database database : getDatabases()) {
			if (database.getId().equalsIgnoreCase(id)) {
				return database;
			}
		}
		throw new RuntimeException("Request database not found: " + id);
	}

	public List<ScriptExecutionItemRecord> executeSql(String sql, String databaseId, boolean failOnError,
			boolean autoCommit) {
		List<ScriptExecutionItemRecord> results = new ArrayList<>();
		Connection conn = null;
		try {
			Database db = getDatabase(databaseId);
			conn = getConnection(databaseId);
			List<String> commands = getSqlParser(db.getType()).parse(sql);
			for (String command : commands) {
				ScriptExecutionItemRecord result = new ScriptExecutionItemRecord();
				result.setSqlCommand(command);
				result.setDatabaseId(databaseId);
				try {
					executeSql(conn, command, autoCommit);
					result.setSuccess(true);
					results.add(result);
				} catch (Exception exp) {
					result.setErrorMessage(exp.getMessage());
					results.add(result);
					if (!failOnError) {
						break;
					}
				}
			}
			conn.close();
		} catch (Exception exp) {
			ScriptExecutionItemRecord result = new ScriptExecutionItemRecord();
			result.setSqlCommand("system error");
			result.setSuccess(false);
			result.setErrorMessage(exp.getMessage());
			result.setDatabaseId(databaseId);
			results.add(result);
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception exp2) {
				LOGGER.error("Error while closing the connection", exp2);
			}
		}
		return results;
	}

	private void executeSql(Connection conn, String sql, boolean autoCommit) throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute(sql);
		if (autoCommit && !conn.getAutoCommit()) {
			conn.commit();
		}
		try {
			statement.close();
		} catch (Exception e) {
			// Ignore error while closing the statement
		}

	}

	public Connection getConnection(String database) {
		try {
			Database db = getDatabase(database);
			Class.forName(db.getDriverClass());
			Connection connection = DriverManager.getConnection(db.getJdbcUrl(), db.getUsername(), db.getPassword());
			return connection;
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

	public SqlParser getSqlParser(String databaseType) {
		SqlParser parser = null;
		if (databaseType.equalsIgnoreCase("mysql")) {
			parser = new MySqlParser();
		} else if (databaseType.equalsIgnoreCase("oracle")) {
			parser = new OracleSqlParser();
		} else if (databaseType.equalsIgnoreCase("mssql")) {
			parser = new SqlServerParser();
		}
		return parser;
	}
}
