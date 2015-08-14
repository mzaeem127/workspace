package com.sabby.sample;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@Autowired
	VelocityEngine engine;

	@RequestMapping("/rest")
	public String index() {
		Map<String, Object> model = new HashMap<>();
		model.put("message", "Welcome");
		model.put("time", new Date().toString());
		return VelocityEngineUtils.mergeTemplateIntoString(this.engine, "index.vm", "UTF-8", model);
		// return "Greetings from Spring Boot!";
	}

}
