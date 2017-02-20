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
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Layout component for most or all pages of application.
 * 
 * A "title" parameter is required.
 */
@Import(stylesheet = {"context:layout/layout.css"})
public class Layout
{
	@Inject
	private ComponentResources resources;

	/** The page title, for the <title> element and the <h1>element. */
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	/** Optional CSS rules to place into the page head */
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Block style;

	@Property
	private String pageName;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String section;

    /** A block that displays in the upper-right corner of the page */
    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.PROP)
    private Block tools;

    /** A block of additional links to display in the breadcrumbs */
    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.PROP)
    private Block pageLinks;

	void SetupRender()
	{
		pageName = resources.getPageName();
	}

	public String[] getPageNames()
	{
		return new String[] { "Index", "Index", "Contact" };
	}

}
