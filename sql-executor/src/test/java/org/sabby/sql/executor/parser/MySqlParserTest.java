package org.sabby.sql.executor.parser;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import gudusoft.gsqlparser.EDbVendor;

public class MySqlParserTest {

	@Test
	public void testParseScript() throws Exception {
		GenericSqlParser parser = new GenericSqlParser(EDbVendor.dbvmysql);
		InputStream is = MySqlParserTest.class.getResourceAsStream("/mysql.sql");
		String sql = IOUtils.toString(is);
		List<String> commands = parser.parse(sql);
		for (String command : commands) {
			System.out.println(command);
		}
	}

}
