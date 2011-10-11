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

import java.util.List;

import org.apache.tapestry.unicorn.entities.Entry;
import org.apache.tapestry.unicorn.entities.EntryType;
import org.apache.tapestry.unicorn.entities.SourceType;
import org.apache.tapestry.unicorn.services.EntryService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * A Tapestry component to display a list of components, mixins, pages, etc
 * along with details about the selected entry, if any.
 * 
 * @author bharner
 * 
 */
public class EntryList
{

	@SuppressWarnings("unused")
	@Property
	private String filterText;

	@Parameter
	private Entry selectedEntry;

	@Parameter
	private EntryType entryType;

	@Parameter
	private List<SourceType> sourceTypes;

	@Property
	private Entry entry; // used in a loop

	@SuppressWarnings("unused")
	@Property
	private List<Entry> entryList;

	@Inject
	private EntryService entryService;

	@Inject
	private Request request;

	@InjectComponent
	private Object entryDetail;
	
	public String getSelector() {
		return ".left-side-list ul.entry-list li";
	}
	/**
	 * @return the CSS class of current entry (depends on the entry type and
	 * whether it is enabled)
	 */
	public String getEntryCssClass()
	{
		if(entry.getEnabled())
		{
			return entry.getEntryType().getCssClass();
		}
		return entry.getEntryType().getCssClass() + " disabled";
	}

	/**
	 * @return a descriptive title for the current entry, suitable for a
	 *         tool tip on each entry in the list
	 */
	public String getEntryTitle()
	{
		if (entry.getParent() == null)
		{
			return entry.getDescription();
		}
		return entry.getDescription() + " (" + entry.getParent().getName() + ")";
	}

	public EntryType getEntryType()
	{
		return entryType;
	}

	public List<SourceType> getSourceTypes()
	{
		return sourceTypes;
	}

	/**
	 * Initializations needed each time this component is about to be rendered
	 */
	@SuppressWarnings("unused")
	@SetupRender
	private void init()
	{
		filterText = "";
		entryList = entryService.findByType(entryType, sourceTypes);
	}

	public void setEntryType(EntryType entryType)
	{
		this.entryType = entryType;
	}

	/**
	 * As an event listener, respond to a click on the "entrySellection"
	 * ActionLink. (Note that "entrySellection is intentionally misspelled to
	 * avoid matches with the JavaScript instant search).
	 * 
	 * @return the "entryDetail" component (to be put into a zone). If the event
	 *         is not part of an AJAX zone update (i.e. the browser has
	 *         JavaScript off) we return the whole page to be redrawn.
	 */
	@OnEvent(value = EventConstants.ACTION, component = "entrySellection")
	public Object viewEntryDetails(Integer id)
	{
		setSelectedEntry(entryService.findById(id));
		if (request.isXHR()) // an AJAX request?
		{
			return entryDetail; // return the entryDetail component
		}
		else
		{
			return null; // graceful degradation: redraw the whole current page
		}
	}

	public void setSelectedEntry(Entry selectedEntry)
	{
		this.selectedEntry = selectedEntry;
	}

	public Entry getSelectedEntry()
	{
		return selectedEntry;
	}

	public void setSourceTypes(List<SourceType> sourceTypes)
	{
		this.sourceTypes = sourceTypes;
	}
}
