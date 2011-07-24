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
package org.apache.tapestry.finder.pages;

import java.util.Date;
import java.util.List;

import org.apache.tapestry.finder.entities.ComponentEntry;
import org.apache.tapestry.finder.entities.EntryType;
import org.apache.tapestry.finder.services.EntryService;
import org.apache.tapestry.finder.services.EntryTypeService;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.slf4j.Logger;

/**
 * A Tapestry Page for editing an entry or creating a new one.  For editing an
 * existing entry, first call setEntry() to set the entry to be edited.
 *  
 */
public class EditEntry
{
	private EntryType type;

	@SuppressWarnings("unused")
	@Property
	private String name;

	@SuppressWarnings("unused")
	@Property
	private String description;

	@Property
	private String documentationUrl;

	@Property
	private String demonstrationUrl;
	
	@Property
	private Date firstAvailable;
	
	@Property
	private ComponentEntry parent;
	
	@Property
	private Boolean enabled;
	
	@SuppressWarnings("unused")
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String statusMessage;

	@PageActivationContext // tell Tapestry to generate onActivate() & onPassivate()
	private ComponentEntry entry;

	@SuppressWarnings("unused")
	@Property
	private String pageHeading;

	@SuppressWarnings("unused")
	@Property
	private SelectModel parentSelectModel;

	@SuppressWarnings("unused")
	@Property
	private List<EntryType> entryTypes;

	@SuppressWarnings("unused")
	@Property
	private EntryType entryType; // used in a loop

	@Inject
	private SelectModelFactory selectModelFactory;

	@Inject
	private EntryService entryService;
	
	@Component
	private Form editForm;
	
	@Inject
	private Messages messages;
	
	@Inject
	private Logger logger;

	@Inject
	private EntryTypeService entryTypeService;

	@InjectPage
	private Index indexPage;

	public ComponentEntry getEntry()
	{
		return entry;
	}

	public EntryType getType()
	{
		return type;
	}

	/**
	 * Handle the form's PREPARE_FOR_RENDER event: Do setup actions
	 * prior to rendering the form.
	 */
	void onPrepareForRender()
	{
		if (entry != null)
		{
			// copy to temporary properties (so we don't pollute our context
			// with potentially invalid/incomplete entities)
			this.type = entry.getEntryType();
			this.name = entry.getName();
			this.description = entry.getDescription();
			this.documentationUrl = entry.getDocumentationUrl();
			this.demonstrationUrl = entry.getDemonstrationUrl();
			this.firstAvailable = entry.getFirstAvailable();
			this.parent = entry.getParent();
			this.enabled = entry.getEnabled();
		}
		
		// populate the list of entry types for the radio button group
		entryTypes = entryTypeService.findAll();

		// populate the list of components for the "parent" select menu
		List<ComponentEntry> parents = entryService.findParentCandidates(entry);

		// create a SelectModel from the list of available parents
		parentSelectModel = selectModelFactory.create(parents,
				ComponentEntry.NAME_PROPERTY);
	}

	/**
	 * Handle the form's "Validate" event (after per-field
	 * validation succeeded): Do the cross-field validation.
	 */
	void onValidateFromEditForm()
	{
		// We must have at least one URL
		if ((documentationUrl == null) && (demonstrationUrl == null))
		{
			// record an error, which also tells Tapestry to redisplay the form
			editForm.recordError(messages.get("some-url-required"));
		}
	}

	/**
	 * Handle the form's "Success" event (after form validation
	 * succeeded): Save changes to the database.
	 * 
	 * @return the saved object
	 */
	Object onSuccessFromEditForm()
	{
		if (entry == null)
		{
			// create a new, empty entry object
			entry = entryService.create();
			enabled = true; // TODO - set enabled based on whether user is privileged
		}

		// copy the submitted form values into the (new or existing) object.
		// Inserting the values *after* validation ensures that we don't
		// pollute our entities with invalid objects.
		entry.setEntryType(this.type);
		entry.setName(this.name);
		entry.setDescription(this.description);
		entry.setDocumentationUrl(this.documentationUrl);
		entry.setDemonstrationUrl(this.demonstrationUrl);
		entry.setFirstAvailable(this.firstAvailable);
		entry.setParent(this.parent);
		entry.setEnabled(enabled);
		entry.setEnabled(true);

		// save to the database
		entry = entryService.save(entry);
		
		// TODO: if user was unprivileged, send e-mail notifications about
		// entry needing to be approved/enabled

		logger.info("Saved " + entry.getName());

		statusMessage = "Success";
		indexPage.setSelectedEntry(entry);
		indexPage.setSuccessMessage(entry.getName() + " entry saved");
		return indexPage;
	}

	public void setEntry(ComponentEntry entry)
	{
		this.entry = entry;
	}

	public void setType(EntryType type)
	{
		this.type = type;
	}

	/**
	 * Perform initializations (not necessarily form-related) needed before the
	 * page renders
	 */
	@SetupRender
	void initPage()
	{
		if ((entry == null) || (entry.getId() == null))
		{
			pageHeading = "New Entry";
		}
		else
		{
			pageHeading = "Edit Entry";
		}
	}
}
