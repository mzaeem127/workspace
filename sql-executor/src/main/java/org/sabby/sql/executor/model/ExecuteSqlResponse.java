package org.sabby.sql.executor.model;

public class ExecuteSqlResponse {

	private long executionId;

	public ExecuteSqlResponse(long executionId) {
		this.executionId = executionId;
	}

	public long getExecutionId() {
		return executionId;
	}

	public void setExecutionId(long executionId) {
		this.executionId = executionId;
	}
}
