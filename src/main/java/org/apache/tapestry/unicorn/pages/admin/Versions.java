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
package org.apache.tapestry.unicorn.pages.admin;

import java.util.List;

import org.apache.tapestry.unicorn.entities.TapestryVersion;
import org.apache.tapestry.unicorn.services.TapestryVersionService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * Administration page for the Versions database table
 *
 */
public class Versions
{
	@Inject
	private TapestryVersionService tapestryVersionService;

	@Inject
	private Request request;

	@InjectComponent
	private Zone editZone;
	
	@SuppressWarnings("unused")
	@Property
	private TapestryVersion version; // used in a list
	
	@Property
	private TapestryVersion selected; // the selected version
	
	@Property
	private String name;
	
	@SuppressWarnings("unused")
	@Property
	private String message;

	@Property
	private String description;
	
	private List<TapestryVersion> versions;
	
	public List<TapestryVersion> getVersions()
	{
		return versions;
	}
	
	/**
	 * As an event listener, respond to a click on an item to be edited by
	 * displaying the edit form.
	 * 
	 * @param version
	 * @return
	 */
	@OnEvent(value = EventConstants.ACTION, component = "itemLink")
	public Object editSelectedItem(TapestryVersion version)
	{
		if (request.isXHR()) // an AJAX request?
		{
			this.selected = version;
			return editZone.getBody(); // return the zone body to be redrawn
		}
		return this; // graceful degradation: redraw the whole current page
	}
	
	/**
	 * As an event listener, respond to a successful form submission by
	 * saving the submitted form data.
	 * 
	 * @param version
	 * @return
	 */
	@OnEvent(value = EventConstants.SUCCESS, component = "editForm")
	public Object saveEditedItem(TapestryVersion version)
	{
		if (version == null)
		{
			// create a new, empty entry object
			version = tapestryVersionService.create();
		}
		// Copy the submitted form values into the (new or existing) object.
		// (Inserting the values *after* validation ensures that we don't
		// pollute our entity set with invalid or abandoned objects.)
		version.setName(this.name);
		version.setDescription(this.description);

		// Save all changes to the database
		tapestryVersionService.save(version);
		message = "Version " + version.getName() + " saved.";
		return this; // redraw this page
	}
	
	/**
	 * Do setup actions prior to the form being rendered.
	 */
	@OnEvent(value = EventConstants.PREPARE_FOR_RENDER, component = "editForm")
	void setupFormData()
	{
		if (selected != null)
		{
			// copy to temporary properties (so we don't pollute our entities
			// with potentially invalid/incomplete data)
			this.name = selected.getName();
			this.description = selected.getDescription();
		}
	}
	
	/**
	 * Perform initializations needed before page renders
	 */
	@SetupRender
	public void init()
	{
		versions = tapestryVersionService.findAll();
	}
}
