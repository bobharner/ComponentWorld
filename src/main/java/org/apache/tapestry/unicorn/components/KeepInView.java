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

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;

/**
 * DIV component that uses JavaScript to ensure the element stays within the
 * viewable part of the browser window.  As the user scrolls down, if the
 * top of this element would go off the top of the browser window AND the bottom
 * of this element is above the bottom of the browser window, then this
 * element is moved down. As the user scrolls up, the opposite occurs, to
 * ensure that the element stays at least partly in view and that the user can
 * always scroll up or down to see more of this element if the element is
 * taller than the current browser window.
 * 
 * This component renders its body.
 * 
 */
@SupportsInformalParameters
@Import(library = {"context:js/keepinview.js"})
public class KeepInView
{
	@Inject
	private ComponentResources resources;
	
	@BeginRender
	boolean renderDivWithParameters(MarkupWriter writer)
	{
		
		writer.element("div", "id", resources.getId());
		resources.renderInformalParameters(writer);
		return true;
	}

	@AfterRender
	boolean renderEndDiv(MarkupWriter writer)
	{
		writer.end();
		return true;
	}

}
