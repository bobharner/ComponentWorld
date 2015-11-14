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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.componentworld.entities.TapestryVersion;
import org.apache.componentworld.services.TapestryVersionService;
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

	@InjectComponent
	private Zone editZone;
	
	@Property
	private TapestryVersion version; // used in a list
	
	@Property
	private TapestryVersion selected; // the selected version
	
    @Property
    private String name;

	@Property
	private String description;
	
	@Property
	private Date released;
	
	private List<TapestryVersion> versions;

	private DateFormat dateFormatter;

	@Inject
	private Logger logger;

	@Component
	private Form editForm;

	
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
	public Object onActionFromItemLink(TapestryVersion version)
	{
		this.selected = version;
		if (request.isXHR()) // an AJAX request?
		{
			return editZone.getBody(); // return the zone body to be redrawn
		}
		return this; // graceful degradation: redraw the whole current page
	}
	
	/**
     * As an event listener, respond to a click on the "Add Version" link by
     * displaying the edit form.
	 * @return
	 */
	public Object onActionFromAddLink()
	{
		return onActionFromItemLink(null);
	}

	/**
	 * As an event listener, respond to a successful form submission by
	 * saving the submitted form data.
	 * 
	 * @param version
	 * @return
	 */
	public Object onSuccessFromEditForm()
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
		version.setReleased(this.released);
		version.setDescription(this.description);

		// Save all changes to the database
		tapestryVersionService.save(version);
		alertManager.info("Version " + version.getName() + " saved.");
		logger.info("Saved version {} ({})", name, dateFormatter.format(selected.getReleased()));
		return this; // redraw this page
	}
	
	/**
	 * Do setup actions prior to the form being rendered.
	 */
	void onPrepareForRenderFromEditForm()
	{
		if (selected != null)
		{
			// copy to temporary properties (so we don't pollute our entities
			// with potentially invalid/incomplete data)
			this.name = selected.getName();
			this.released = selected.getReleased();
            this.description = selected.getDescription();
		}
	}
	
	/**
	 * Perform initializations needed before page renders
	 */
	public void setupRender()
	{
		versions = tapestryVersionService.findAll();
		dateFormatter = DateFormat.getDateInstance();
	}
	
	public String getReleasedDate()
	{
		if (version == null || version.getReleased() == null)
		{
			return "";
		}
		return dateFormatter.format(version.getReleased());
	}

}
