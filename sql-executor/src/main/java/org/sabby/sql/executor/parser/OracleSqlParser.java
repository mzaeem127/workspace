package org.sabby.sql.executor.parser;

import java.util.List;

import gudusoft.gsqlparser.EDbVendor;

public class OracleSqlParser extends AbstractSqlParser {

	@Override
	public List<String> parse(String sqlCommands) throws Exception {
		return new GenericSqlParser(EDbVendor.dbvoracle).parse(sqlCommands);
	}
}
