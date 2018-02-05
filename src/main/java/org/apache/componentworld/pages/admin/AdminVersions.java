/* Copyright The Apache Software Foundation
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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.componentworld.entities.TapestryVersion;
import org.apache.componentworld.services.TapestryVersionService;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

/**
 * Administration page for the Versions database table
 *
 */
@Secure
public class AdminVersions
{
	@Inject
	private TapestryVersionService tapestryVersionService;

	@Inject
	private Request request;

	@Inject
	private AlertManager alertManager;
	
	@Inject
	private Logger log;

	@InjectComponent
	private Zone editZone;

	@Property
	private TapestryVersion version; // used in a list

	@Property
	private TapestryVersion selectedVersion; // the selected (clicked) version

	@Property
	private Boolean showZone;
	
	@Property
	private String name, description;
	
	@Property
	Date released;

	private List<TapestryVersion> versions;
	
	private Integer selectedId;

	private DateFormat dateFormatter;

	@Inject
	private Logger logger;

	@Component
	private Form editForm;

	/**
	 * 
	 */
	public void onActivate(TapestryVersion selectedVersion) {
		this.selectedVersion = selectedVersion;
	}

	/**
	 * Perform initializations needed before page renders
	 */
	public void setupRender()
	{
		showZone = false;
		versions = tapestryVersionService.findAll();
	}

	/**
	 * Respond to a click on an item to be edited by displaying the edit form
	 * populated by the given item.
	 * 
	 * @param selectedVersion
	 * @return the zone body (or whole page) to display
	 */
	public Object onActionFromItemLink(TapestryVersion selectedVersion)
	{
		showZone = true;
		name = selectedVersion.getName();
		description = selectedVersion.getDescription();
		released = selectedVersion.getReleased();

		if (request.isXHR()) // an AJAX request?
		{
			return editZone.getBody(); // return the zone body to be redrawn
		}
		return this; // graceful degradation: redraw the whole current page
	}

	/**
     * Respond to a click on the "Add Version" link by displaying an empty
     * edit form.
	 * @return
	 */
	public Object onActionFromAddLink()
	{
		return onActionFromItemLink(null);
	}

	/**
	 * Respond to the form's "prepare for submission" event
	 */
	void onPrepareForSubmitFromEditForm()
	{
		if (selectedId != null) {
    		// load selected version from database
    		selectedVersion = tapestryVersionService.findById(selectedId);
    		selectedVersion.setName(name);
    		selectedVersion.setDescription(description);
    		selectedVersion.setReleased(released);
        }
	}

	/**
	 * Respond to the form's "success" event by saving submitted form data.
	 * 
	 * @param version
	 * @return
	 */
	public Object onSuccessFromEditForm()
	{
		if (selectedVersion == null)
		{
			// create a new, empty entry object
			selectedVersion = tapestryVersionService.create();
		}

		// Save all changes to the database
		tapestryVersionService.save(selectedVersion);
		alertManager.info("Version " + selectedVersion.getName() + " saved.");
		logger.info("Saved version {} ({})", selectedVersion.getName(), getDateFormatter().format(selectedVersion.getReleased()));
		return this; // redraw this page
	}

	public List<TapestryVersion> getVersions()
	{
		return versions;
	}

	public String getReleasedDate()
	{
		if (version == null || version.getReleased() == null)
		{
			return "";
		}
		return getDateFormatter().format(version.getReleased());
	}
	
	public String getEditPageHeading()
	{
		if (selectedVersion == null)
		{
			return "Add a Version";
		}
		return "Edit Version " + selectedVersion.getName();
	}

	/**
	 * Get the current date formatter, creating one if needed
	 * @return the date formatter
	 */
	private DateFormat getDateFormatter() {
	    if (dateFormatter == null) {
	        dateFormatter = DateFormat.getDateInstance();
	    }
	    return dateFormatter;
	}
}
