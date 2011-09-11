/* Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
