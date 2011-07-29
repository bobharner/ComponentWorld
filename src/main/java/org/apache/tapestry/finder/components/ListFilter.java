package org.apache.tapestry.finder.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Tapestry component for including a filter key-in box by which users can
 * dynamically filter lists of things as they type
 */
@Import(library = { "context:js/listfilter.js" })
public class ListFilter
{
	
    /**
     * The CSS selector expression for the list items to examine, something
     * like "li.items" or "div#entry-list a"
     */
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String listSelector;
	
	/**
	 * Client ID of container element to receive an integer count of the
	 * number of items matching the search string. (This number updates as the
	 * user types.) 
	 */
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String counterId;

}
