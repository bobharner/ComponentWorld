package org.apache.tapestry.unicorn.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Tapestry component which renders as a checkbox that checks or unchecks all
 * checkboxes in a group identified by the given CSS selector. This operates
 * purely using JavaScript.
 */
@SupportsInformalParameters
@Import(library = { "context:js/checkall.js" })
public class CheckAll
{
	
    /**
     * The CSS selector expression for the list of checkboxes to control,
     * something like "ul.items input" or ".t-checklist-row input"
     */
	@Property
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String selector;

	@Inject
	private ComponentResources resources;

	boolean beginRender(MarkupWriter writer)
	{
		String onclickjs = "checkall('" + selector + "', this.checked);";
		writer.element("input", "type", "checkbox", "onclick", onclickjs);

		resources.renderInformalParameters(writer);

		writer.end();

		return false;
  }
}
