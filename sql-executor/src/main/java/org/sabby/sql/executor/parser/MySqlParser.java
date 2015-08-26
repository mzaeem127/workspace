package org.sabby.sql.executor.parser;

import java.util.List;

import gudusoft.gsqlparser.EDbVendor;

public class MySqlParser extends AbstractSqlParser {

	@Override
	public List<String> parse(String sqlCommands) throws Exception {
		return new GenericSqlParser(EDbVendor.dbvmysql).parse(sqlCommands);
	}
}
