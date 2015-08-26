package org.sabby.sql.executor.parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSqlParser implements SqlParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSqlParser.class);

	private static final String DEFAULT_DELIMITER = ";";

	private String delimiter = DEFAULT_DELIMITER;

	private boolean fullLineDelimiter = false;

	@Override
	public List<String> parse(String sqlCommands) throws Exception {
		return parseScript(new StringReader(sqlCommands));
	}

	private List<String> parseScript(Reader reader) throws IOException, SQLException {
		List<String> commands = new ArrayList<String>();
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

					commands.add(command.toString());
					command = null;
				} else {
					command.append(line);
					command.append("\n");
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error while parsing SQL file", e);
			throw e;
		}
		return commands;
	}

	private void setDelimiter(String delimiter, boolean fullLineDelimiter) {
		this.delimiter = delimiter;
		this.fullLineDelimiter = fullLineDelimiter;
	}

	private String getDelimiter() {
		return delimiter;
	}
}
