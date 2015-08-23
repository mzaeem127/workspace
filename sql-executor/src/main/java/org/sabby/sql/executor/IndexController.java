package org.sabby.sql.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.sabby.sql.executor.config.Database;
import org.sabby.sql.executor.model.DatabaseResponse;
import org.sabby.sql.executor.model.ExecuteSqlRequest;
import org.sabby.sql.executor.model.ExecuteSqlResponse;
import org.sabby.sql.executor.service.SqlExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@Autowired
	VelocityEngine engine;

	@Autowired
	SqlExecuteService sqlExecuteService;

	@RequestMapping("/")
	public String index() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", "Welcome");
		model.put("time", new Date().toString());
		return VelocityEngineUtils.mergeTemplateIntoString(this.engine, "index.vm", "UTF-8", model);
	}

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
	public List<ExecuteSqlResponse> executeSql(@RequestBody ExecuteSqlRequest sql) {
		List<ExecuteSqlResponse> responses = new ArrayList<>();
		for (String databaseId : sql.getDatabases()) {
			ExecuteSqlResponse response = new ExecuteSqlResponse();
			response.setDatabaseId(databaseId);
			try {
				sqlExecuteService.executeSql(sql.getSql(), databaseId);
				System.out.println(sql.getSql());
				response.setSuccess(true);
			} catch (Exception exp) {
				response.setSuccess(false);
				response.setMessage(exp.getMessage());
			}
			responses.add(response);
		}
		return responses;
	}
}
