package com.sabby.sample.components;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.resource.CssResourceReference;

import com.sabby.sample.WicketWebApplication;

public class MountedPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public MountedPage() {
		add(new Label("title", "this is a mounted page"));
		add(new BookmarkablePageLink<String>("link", Homepage.class));
		/* showcase spring integration in wicket component */
		add(new Label("serviceText", "Sample service text"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		CssResourceReference cssResourceReference = new CssResourceReference(WicketWebApplication.class, "example.css");
		response.render(CssHeaderItem.forReference(cssResourceReference));
	}

}