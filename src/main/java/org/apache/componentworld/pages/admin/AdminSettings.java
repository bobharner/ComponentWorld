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
package org.apache.componentworld.pages.admin;

import java.util.List;

import org.apache.componentworld.entities.Setting;
import org.apache.componentworld.services.SettingsService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

/**
 * Administration page for the "Other Settings" data
 *
 */
@Secure
public class AdminSettings
{
	@Inject
	private SettingsService settingsService;

	@Inject
	private Request request;
	
	@Inject
	private AlertManager alertManager;

	@InjectComponent
	private Zone editZone;

	@Inject
	Logger log;

	@Property
	private Setting setting; // used in a list
	
	@Property
	private Setting selected; // the selected setting item
	
	@Property
	private String name;
	
	@Property
	private Boolean showZone;

	@Property
	private Integer id;

	@Property
	private String value;

	@Property
	private String description;
	
	private List<Setting> settings;
	
	public List<Setting> getSettings()
	{
		return settings;
	}

	/**
	 * Perform initializations needed before page renders
	 */
	@SetupRender
	public void init()
	{
		showZone = false;
		settings = settingsService.findAll();
	}

	/**
	 * As an event listener, respond to a click on the item to be edited by
	 * displaying the edit form.
	 * 
	 * @param setting
	 * @return
	 */
	@OnEvent(value = EventConstants.ACTION, component = "itemLink")
	public Object editSelectedItem(Integer id)
	{
		showZone = true;
		if (request.isXHR()) // an AJAX request?
		{
			this.selected = settingsService.findById(id);
			return editZone.getBody(); // return the zone body to be redrawn
		}
		return this; // graceful degradation: redraw the whole current page
	}

	/**
	 * As an event listener, prepare for form submission by prepopulating the
	 * item before it receives the submitted form data.
	 * 
	 */
	@OnEvent(value=EventConstants.PREPARE_FOR_SUBMIT, component="editForm")
	void prepopulateItem()
	{
		this.selected = settingsService.findById(id);
	}

	/**
	 * As an event listener, respond to a successful form submission by
	 * saving the submitted form data.
	 * 
	 * @param setting
	 * @return
	 */
	@OnEvent(value=EventConstants.SUCCESS, component="editForm")
	public Object saveEditedItem()
	{
		// Copy the submitted form values into the (new or existing) object.
		// (Inserting the values *after* validation ensures that we don't
		// pollute our entity set with invalid or abandoned objects.)
		selected.setName(this.name);
		selected.setValue(this.value);
		selected.setDescription(this.description);

		// Save all changes to the database
		settingsService.save(selected);
		alertManager.info("Setting " + selected.getName() + " saved.");
		return this; // redraw this page
	}

	/**
	 * Do setup actions prior to the form being rendered.
	 */
//	@OnEvent(value = EventConstants.PREPARE_FOR_RENDER, component = "editForm")
//	void setupFormData()
//	{
//		if (selected != null)
//		{
//			// copy to temporary properties (so we don't pollute our entities
//			// with potentially invalid/incomplete data)
//			this.id = selected.getId();
//			this.name = selected.getName();
//			this.value = selected.getValue();
//			this.description = selected.getDescription();
//		}
//	}

}
