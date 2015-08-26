package org.sabby.sql.executor.parser;

import java.util.List;

public interface SqlParser {

	List<String> parse(String sqlCommands) throws Exception;

}
