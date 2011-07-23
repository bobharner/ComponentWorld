package org.apache.tapestry.finder.components;

import org.apache.tapestry.finder.pages.EditEntry;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

/**
 * Layout component for most or all pages of application.
 * 
 * A "title" parameter is required.
 */
@Import(stylesheet = {"context:layout/layout.css"})
public class Layout
{
	/** The page title, for the <title> element and the <h1>element. */
	@SuppressWarnings("unused")
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Inject
	private ComponentResources resources;
	
	@InjectPage
	private EditEntry editEntry;

	private String pageName = resources.getPageName();

	@Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
	private String section;

	public String[] getPageNames()
	{
		return new String[]
		{ "Index", "About", "Contact" };
	}

	/**
	 * Output the HTML5 doctype, as a work-around to
	 * https://issues.apache.org/jira/browse/TAP5-1040
	 * 
	 * @param writer
	 */
	@SetupRender
	final void renderDocType(final MarkupWriter writer)
	{
		writer.getDocument().raw("<!DOCTYPE html>");
	}

	public void setPageName(String pageName)
	{
		this.pageName = pageName;
	}

	public String getPageName()
	{
		return pageName;
	}

	public void setSection(String section)
	{
		this.section = section;
	}

	public String getSection()
	{
		return section;
	}
	
	/**
	 * As an event listener, handle user's click on "Create a New Entry" link
	 * 
	 * @return the page to navigate to
	 */
	Object onActionFromNewEntry() {

		editEntry.setEntry(null); // ensure a blank form
		return editEntry;
	}
}
