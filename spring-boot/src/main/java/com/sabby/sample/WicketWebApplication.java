package com.sabby.sample;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.sabby.sample.components.Homepage;
import com.sabby.sample.components.MountedPage;

@Component
@EnableAutoConfiguration
@ComponentScan
public class WicketWebApplication extends WebApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Class<? extends Page> getHomePage() {
		return null;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
		mountPage("/wicket/home.html", Homepage.class);
		mountPage("/wicket/mounted.html", MountedPage.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WicketWebApplication.class, args);
	}
}
