package org.sabby.sql.executor.service;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sabby.sql.executor.domain.ScriptExecutionItemRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tool to run database scripts
 */
public class ScriptRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptRunner.class);

	private static final String DEFAULT_DELIMITER = ";";

	private Connection connection;

	private boolean stopOnError;

	private boolean autoCommit;

	private String delimiter = DEFAULT_DELIMITER;

	private boolean fullLineDelimiter = false;

	private List<ScriptExecutionItemRecord> results;

	/**
	 * Default constructor
	 */
	public ScriptRunner(Connection connection, boolean autoCommit, boolean stopOnError) {
		this.connection = connection;
		this.autoCommit = autoCommit;
		this.stopOnError = stopOnError;
		results = new ArrayList<>();
	}

	public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
		this.delimiter = delimiter;
		this.fullLineDelimiter = fullLineDelimiter;
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter)
	 *
	 * @param reader
	 *            - the source of the script
	 */
	public List<ScriptExecutionItemRecord> runScript(Reader reader) throws IOException, SQLException {
		try {
			boolean originalAutoCommit = connection.getAutoCommit();
			try {
				if (originalAutoCommit != this.autoCommit) {
					connection.setAutoCommit(this.autoCommit);
				}
				runScript(connection, reader);
			} finally {
				connection.setAutoCommit(originalAutoCommit);
			}
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Error running script.  Cause: " + e, e);
		}
		return results;
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter) using the
	 * connection passed in
	 *
	 * @param conn
	 *            - the connection to use for the script
	 * @param reader
	 *            - the source of the script
	 * @throws SQLException
	 *             if any SQL errors occur
	 * @throws IOException
	 *             if there is an error reading from the Reader
	 */
	private void runScript(Connection conn, Reader reader) throws IOException, SQLException {
		StringBuffer command = null;
		try {
			LineNumberReader lineReader = new LineNumberReader(reader);
			String line = null;
			while ((line = lineReader.readLine()) != null) {
				if (command == null) {
					command = new StringBuffer();
				}
				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("--")) {
					LOGGER.debug(trimmedLine);
				} else if (trimmedLine.length() < 1 || trimmedLine.startsWith("//")) {
					// Do nothing
				} else if (trimmedLine.length() < 1 || trimmedLine.startsWith("--")) {
					// Do nothing
				} else if (trimmedLine.toLowerCase().startsWith("delimiter ")) {
					int delimiterPos = line.toLowerCase().indexOf("delimiter ") + "delimiter ".length();
					setDelimiter(line.substring(delimiterPos).trim(), false);
				} else if (!fullLineDelimiter && trimmedLine.endsWith(getDelimiter())
						|| fullLineDelimiter && trimmedLine.equals(getDelimiter())) {
					command.append(line.substring(0, line.lastIndexOf(getDelimiter())));
					command.append(" ");
					Statement statement = conn.createStatement();

					ScriptExecutionItemRecord result = new ScriptExecutionItemRecord();
					result.setSqlCommand(command.toString());

					if (stopOnError) {
						statement.execute(command.toString());
					} else {
						try {
							statement.execute(command.toString());
							result.setSuccess(true);
						} catch (SQLException e) {
							result.setErrorMessage(e.getMessage());
						}
					}
					results.add(result);
					if (autoCommit && !conn.getAutoCommit()) {
						conn.commit();
					}

					command = null;
					try {
						statement.close();
					} catch (Exception e) {
						// Ignore to workaround a bug in Jakarta DBCP
					}
					Thread.yield();
				} else {
					command.append(line);
					command.append("\n");
				}
			}
			if (!autoCommit) {
				conn.commit();
			}
		} catch (SQLException | IOException e) {
			ScriptExecutionItemRecord result = new ScriptExecutionItemRecord();
			result.setSqlCommand(command.toString());
			result.setErrorMessage(e.getMessage());
			LOGGER.error("Error executibe command", e);
			throw e;
		} finally {
			conn.rollback();
		}
	}

	private String getDelimiter() {
		return delimiter;
	}
}