package org.sabby.sql.executor.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.sabby.sql.executor.IndexController;
import org.sabby.sql.executor.config.Database;
import org.sabby.sql.executor.config.Databases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlExecuteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecuteService.class);

	private List<Database> databases;

	public SqlExecuteService() {
		try {
			InputStream inputStream = IndexController.class.getResourceAsStream("/databases.xml");
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

	public List<String> executeSql(String sql, String databaseId) throws SQLException {
		Connection conn = getConnection(databaseId);
		try {
			conn.close();
		} catch (SQLException exp) {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception exp2) {
				LOGGER.error("Error while closing the connection", exp2);
			}
			throw exp;
		}
		return null;
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

}
