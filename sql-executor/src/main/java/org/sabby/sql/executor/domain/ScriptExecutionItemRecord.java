package org.sabby.sql.executor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ScriptExecutionItemRecord {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String databaseId;

	@Column(nullable = true)
	@Lob
	private String sqlCommand;

	@Column(nullable = true)
	@Lob
	private String errorMessage;

	@Column
	private boolean success;

	@ManyToOne
	private ScriptExecutionRecord parent;

	public ScriptExecutionItemRecord() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSqlCommand() {
		return sqlCommand;
	}

	public void setSqlCommand(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	public ScriptExecutionRecord getParent() {
		return parent;
	}

	public void setParent(ScriptExecutionRecord parent) {
		this.parent = parent;
	}
}
