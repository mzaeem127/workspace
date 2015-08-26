package org.sabby.sql.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.sabby.sql.executor.config.Database;
import org.sabby.sql.executor.domain.ScriptExecutionItemRecord;
import org.sabby.sql.executor.domain.ScriptExecutionRecord;
import org.sabby.sql.executor.domain.ScriptExecutionRepository;
import org.sabby.sql.executor.model.DatabaseResponse;
import org.sabby.sql.executor.model.ExecuteSqlRequest;
import org.sabby.sql.executor.model.ExecuteSqlResponse;
import org.sabby.sql.executor.service.SqlExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestJsonController {

	@Autowired
	VelocityEngine engine;

	@Autowired
	SqlExecuteService sqlExecuteService;

	@Autowired
	ScriptExecutionRepository repository;

	@RequestMapping(value = "/rest/databases", method = RequestMethod.GET, consumes = {
			"application/json", }, produces = { "application/json" })
	public List<DatabaseResponse> getDatabases(@RequestParam(value = "type", required = true) String type) {
		List<DatabaseResponse> responses = new ArrayList<>();
		for (Database database : sqlExecuteService.getDatabases()) {
			if (database.getType().equalsIgnoreCase(type)) {
				DatabaseResponse response = new DatabaseResponse();
				response.setId(database.getId());
				response.setDescription(database.getDescription());
				responses.add(response);
			}
		}
		return responses;
	}

	@RequestMapping(value = "/rest/execute-sql", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public ExecuteSqlResponse executeSql(@RequestBody ExecuteSqlRequest sql) {
		ScriptExecutionRecord executionRecord = new ScriptExecutionRecord();
		for (String databaseId : sql.getDatabases()) {
			List<ScriptExecutionItemRecord> results = sqlExecuteService.executeSql(sql.getSql(), databaseId, true,
					true);
			executionRecord.setExecutedBy(1L);
			executionRecord.setExecutedOn(new Date());
			executionRecord.getItems().addAll(results);
		}
		repository.save(executionRecord);
		return new ExecuteSqlResponse(executionRecord.getId());
	}

	@RequestMapping(value = "/rest/execution/", method = RequestMethod.GET, produces = { "application/json" })
	public List<ScriptExecutionItemRecord> getExecutionItemById(@RequestParam(value = "id", required = true) Long id) {
		ScriptExecutionRecord record = repository.findOne(id);
		return record.getItems();
	}
}
