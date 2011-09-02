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
 * Tapestry component which displays a filter key-in box by which users can
 * dynamically filter lists of things as they type, purely using JavaScript.
 */
@SupportsInformalParameters
@Import(library = { "context:js/listfilter.js" })
public class ListFilter
{
	
    /**
     * The CSS selector expression for the list items to examine, something
     * like "li.items" or "div#entry-list a"
     */
	@Property
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String selector;
	
	/**
	 * Client ID of container element to receive an integer count of the
	 * number of items matching the search string. (This number updates as the
	 * user types.) 
	 */
	@Property
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String counterId;

	@Inject
	private ComponentResources resources;

	boolean beginRender(MarkupWriter writer)
	{
		String onkeyupjs = "filterListWithCounter('" + selector + "','"
				+ counterId + "', this.value);";
		writer.element("input", "type", "text", "onkeyup", onkeyupjs);

		resources.renderInformalParameters(writer);

		writer.end();

		return false;
  }
}
