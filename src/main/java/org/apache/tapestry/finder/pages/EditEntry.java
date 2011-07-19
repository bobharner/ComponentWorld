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

import java.util.List;

import org.apache.tapestry.finder.entities.ComponentEntry;
import org.apache.tapestry.finder.entities.EntryType;
import org.apache.tapestry.finder.services.EntryService;
import org.apache.tapestry.finder.services.EntryTypeService;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

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

	@SuppressWarnings("unused")
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String statusMessage;

	@Persist
	// TODO: remove @Persist and use activation context instead
	private Integer entryId;

	@Persist
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
	 * As an event handler, respond to the form's PREPARE_FOR_RENDER event,
	 * doing setup actions prior to rendering the form.
	 */
	void onPrepareForRender()
	{
		// create an empty ComponentEntry if needed
		if (entryId == null || entry == null)
		{
			entry = entryService.create();
			// TODO: set disabled if user not logged in or insufficient authority
			entry.setEnabled(true);
		}
		else
		{
			entry = entryService.findById(entryId);
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
	 * As an event handler, respond to the "Validate" event from the form (after
	 * per-field validation succeeded) by doing any cross-field validation.
	 */
	void onValidateFromEditForm()
	{
		System.out.println("In onValidate, docurl=" + documentationUrl + ", demourl=" + demonstrationUrl + "\n");

		// We must have at least one URL
/*		if ((documentationUrl == null) && (demonstrationUrl == null))
		{
			// record an error, which also tells Tapestry to redisplay the form
			editForm.recordError(messages.get("some-url-required"));
		}*/
	}

	/**
	 * As an event handler, respond to the "Success" event from the form
	 * (after form validation succeeded) by saving changes to the database.
	 * 
	 * @return the saved object
	 */
	Object onSuccessFromEditForm()
	{
		entryService.save(entry);

		statusMessage = "Success";
		indexPage.setSelectedEntry(entry);
		indexPage.setSuccessMessage(entry.getName() + " entry saved");
		return indexPage;
	}

	public void setEntry(ComponentEntry entry)
	{
		this.entry = entry;
	}

	public void setEntryId(Integer entryId)
	{
		this.entryId = entryId;
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
	void setupRender()
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
