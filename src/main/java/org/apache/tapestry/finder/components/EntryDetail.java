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
package org.apache.tapestry.finder.components;

import java.util.List;

import org.apache.tapestry.finder.entities.ComponentEntry;
import org.apache.tapestry.finder.services.EntryService;
import org.apache.tapestry.finder.utils.UrlUtils;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * Tapestry component to display detailed information about a particular entry
 * 
 * @author bharner
 * 
 */
public class EntryDetail
{
	@Parameter
	@Property
	private ComponentEntry entry; // the selected entry

	@SuppressWarnings("unused")
	@Property
	private String shortDocUrl; // shortened version of entry's doc URL

	@SuppressWarnings("unused")
	@Property
	private String shortDemoUrl; // shortened version of entry's demo URL

	@Property
	private ComponentEntry child; // used in a loop

	@Property
	private List<ComponentEntry> children;

	@Inject
	private Request request;

	@Inject
	private EntryService entryService;

	/**
	 * Perform page initializations
	 */
	@SetupRender
	public void init()
	{
		if (entry != null)
		{
			shortDocUrl = UrlUtils.shorten(entry.getDocumentationUrl(), 50);
			shortDemoUrl = UrlUtils.shorten(entry.getDemonstrationUrl(), 50);
			children = entryService.findChildren(entry);
		}
	}
	
	/**
	 * Return an English article based on whether the current entry's entry type
	 * starts with a vowel.
	 * 
	 * @return "A" or "An", or an empty string
	 */
	public String getArticle()
	{
		if (entry.getEntryType() == null)
		{
			return "";
		}
		char c = entry.getEntryType().getName().toLowerCase().charAt(0);
        if (c=='a' || c=='e' || c=='i' || c=='o' || c=='u') // vowels
        {
        	return "an ";
        }
    	return "a ";
	}

	/**
	 * Determine whether the current entry has any "since" or "until" version
	 * restrictions.
	 * 
	 * @return true or false
	 */
	public Boolean getHasVersions()
	{
		if ((entry.getSince() != null) || (entry.getUntil() != null))
		{
			return true;
		}
		return false;
	}

	/**
	 * Set the CSS class for the list of children based on how many children
	 * there are. (Multiple columns only look good when you have a larger number
	 * of items.)
	 * 
	 * @return the CSS class name to use, or an empty string
	 */
	public String getMulticolumnClass()
	{
		if (children.size() > 5)
		{
			return "multicolumn3";
		}
		return "";
	}
	
	/**
	 * As an event handler, respond to a click on a link whose event is named
	 * "viewEntry".
	 * 
	 * @return this component (for display within the current zone)
	 */
	@OnEvent (value="viewEntry")
	public Object viewChildDetails(ComponentEntry entry)
	{
		this.entry = entry;
		//setSelectedEntry(entryService.findById(id));
		if (request.isXHR()) // an AJAX request?
		{
			return this; // return the entryDetail component
		}
		else
		{
			return null; // redraw the whole current page
		}
	}
}
