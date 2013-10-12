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
package org.apache.componentworld.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

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

	@Environmental
	private JavaScriptSupport jss;
	
	@BeginRender
	boolean renderCheckboxWithParameters(MarkupWriter writer)
	{
	    // assign a unique id to the rendered checkbox
	    final String uniqueId = jss.allocateClientId(resources);
		writer.element("input", "type", "checkbox", "id", uniqueId);
		
		resources.renderInformalParameters(writer);

		writer.end();

		jss.addInitializerCall("checkAll", new JSONObject("id", uniqueId, "selector", selector));		
		
		return false; // false skips rendering of body & end (not needed)
  }
}
