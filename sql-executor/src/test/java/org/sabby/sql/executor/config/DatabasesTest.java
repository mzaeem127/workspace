package org.sabby.sql.executor.config;

public class DatabasesTest {

	public static void main(String[] args) throws Exception {
		Databases databases = new Databases();
		Database db = new Database();
		db.setId("dev_oracle");
		db.setDescription("Oracle Development Instance");
		db.setJdbcUrl("");
		db.setUsername("scott");
		db.setPassword("trigger");
		db.setType("oracle");
		databases.getDatabases().add(db);
		System.out.println(databases.toXml());
	}

}
