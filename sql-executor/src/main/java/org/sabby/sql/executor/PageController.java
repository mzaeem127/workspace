package org.sabby.sql.executor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping(value = { "/", "/home.vml" }, produces = { "text/html" })
	public String home() {
		return "home";
	}

	@RequestMapping(value = { "/viewlogs.vml" }, produces = { "text/html" })
	public String viewLogs() {
		return "viewlog";
	}

	@RequestMapping(value = { "/viewlog.vml" }, produces = { "text/html" })
	public String viewLog() {
		return "viewlog";
	}
}
