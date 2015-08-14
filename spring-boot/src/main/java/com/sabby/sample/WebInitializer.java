package com.sabby.sample;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebInitializer implements ServletContextInitializer {

	private static final String PARAM_APP_BEAN = "applicationBean";

	@Override
	public void onStartup(ServletContext sc) throws ServletException {
		FilterRegistration filter = sc.addFilter("wicket-filter", WicketFilter.class);
		filter.setInitParameter(WicketFilter.APP_FACT_PARAM, SpringWebApplicationFactory.class.getName());
		filter.setInitParameter(PARAM_APP_BEAN, "wicketWebApplication");
		// This line is the only surprise when comparing to the equivalent
		// web.xml. Without some initialization seems to be missing.
		filter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
		EnumSet<DispatcherType> set = EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR);
		filter.addMappingForUrlPatterns(set, false, "/*");
	}

}