package org.sabby.sql.executor.parser;

import java.util.ArrayList;
import java.util.List;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;

public class GenericSqlParser implements SqlParser {

	private EDbVendor dbVendor;

	public GenericSqlParser(EDbVendor vendor) {
		dbVendor = vendor;
	}

	@Override
	public List<String> parse(String sqlCommands) throws Exception {
		List<String> commands = new ArrayList<>();
		TGSqlParser sqlparser = new TGSqlParser(dbVendor);

		sqlparser.sqltext = sqlCommands;

		int ret = sqlparser.getrawsqlstatements();
		if (ret == 0) {
			for (int i = 0; i < sqlparser.sqlstatements.size(); i++) {
				// System.out.println(sqlparser.sqlstatements.get(i).sqlstatementtype.toString());
				// System.out.print(sqlparser.sqlstatements.get(i).toString());
				commands.add(sqlparser.sqlstatements.get(i).toString());
			}
		} else {
			System.out.println(sqlparser.getErrormessage());
		}

		return commands;
	}
}
