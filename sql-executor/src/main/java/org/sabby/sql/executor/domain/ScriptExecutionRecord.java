package org.sabby.sql.executor.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class ScriptExecutionRecord {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Long executedBy;

	@Column(nullable = false)
	private Date executedOn;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ScriptExecutionItemRecord> items;

	@Column(nullable = true)
	@Lob
	private String source;

	public ScriptExecutionRecord() {
		items = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(Long executedBy) {
		this.executedBy = executedBy;
	}

	public Date getExecutedOn() {
		return executedOn;
	}

	public void setExecutedOn(Date executedOn) {
		this.executedOn = executedOn;
	}

	public List<ScriptExecutionItemRecord> getItems() {
		return items;
	}

	public void setItems(List<ScriptExecutionItemRecord> items) {
		this.items = items;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
