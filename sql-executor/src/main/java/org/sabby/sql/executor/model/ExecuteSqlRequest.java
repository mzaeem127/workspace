package org.sabby.sql.executor.model;

public class ExecuteSqlRequest {

	private String sql;

	private String databases[];

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String[] getDatabases() {
		return databases;
	}

	public void setDatabases(String[] databases) {
		this.databases = databases;
	}
}
