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
package org.apache.componentworld.pages.entry;

import java.util.Date;
import java.util.List;

import org.apache.componentworld.entities.Entry;
import org.apache.componentworld.entities.EntryType;
import org.apache.componentworld.entities.License;
import org.apache.componentworld.entities.SourceType;
import org.apache.componentworld.entities.TapestryVersion;
import org.apache.componentworld.pages.Index;
import org.apache.componentworld.services.EntryService;
import org.apache.componentworld.services.EntryTypeService;
import org.apache.componentworld.services.LicenseService;
import org.apache.componentworld.services.SourceTypeService;
import org.apache.componentworld.services.TapestryVersionService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.slf4j.Logger;

/**
 * A Tapestry Page for editing an entry or creating a new one. For editing an
 * existing entry, first call setEntry() to set the entry to be edited.
 * 
 */
@Secure
public class EditEntry
{
	private EntryType type; // selected entry type

	@Property
	private SourceType source; // selected source type

	@Property
	private String name;

	@Property
	private String description;

	@Property
	private String documentationUrl;

	@Property
	private String demonstrationUrl;

	@Property
	private Entry parent;

	@Property
	private License license;

	@Property
	private TapestryVersion since;

	@Property
	private TapestryVersion until;

	@Property
	private Date firstAvailable;

	@Property
	private Boolean enabled;

	@PageActivationContext
	// tell Tapestry to generate onActivate() & onPassivate()
	private Entry entry;

	@Property
	private String pageHeading;

	@Property
	private SelectModel parentSelectModel;

	@Property
	private SelectModel versionSelectModel;

	@Property
	private SelectModel licenseSelectModel;

	@Property
	private List<EntryType> entryTypes;

	@Property
	private List<SourceType> sourceTypes;

	@Property
	private EntryType entryType; // used in a loop

	@Property
	private SourceType sourceType; // used in a loop

	@Inject
	private SelectModelFactory selectModelFactory;

	@Inject
	private EntryService entryService;

	@Inject
	private EntryTypeService entryTypeService;

	@Inject
	private SourceTypeService sourceTypeService;

	@Inject
	private TapestryVersionService tapestryVersionService;

	@Inject
	private LicenseService licenseService;
	
	@Inject
	private AlertManager alertManager;

	@Component
	private Form editForm;

	@Inject
	private Messages messages;

	@Inject
	private Logger logger;

	@InjectPage
	private Index indexPage;

	public Entry getEntry()
	{
		return entry;
	}

	public String getDescriptionInstructions()
	{
		return messages.format("descriptionInstructions",
				EntryService.MAXIMUM_FIRST_SENTENCE_SIZE);
	}

	public EntryType getType()
	{
		return type;
	}

	/**
	 * Do setup actions prior to the form being rendered.
	 */
//    @OnEvent(value = EventConstants.PREPARE_FOR_RENDER, component = "editForm")
    @OnEvent(value = EventConstants.PREPARE, component = "editForm")
	void setupFormData()
	{
		if (entry == null)
		{
			this.enabled = true; // TODO: set to false for under-privileged users
		}
		else
		{
			// copy to temporary properties (so we don't pollute our entities
			// with potentially invalid/incomplete data)
			this.type = entry.getEntryType();
			this.source = entry.getSourceType();
			this.name = entry.getName();
			this.description = entry.getDescription();
			this.documentationUrl = entry.getDocumentationUrl();
			this.demonstrationUrl = entry.getDemonstrationUrl();
			this.parent = entry.getParent();
			this.since = entry.getSince();
			this.until = entry.getUntil();
			this.firstAvailable = entry.getFirstAvailable();
			this.license = entry.getLicense();
			this.enabled = entry.getEnabled();
		}

		// populate lists of entry types & source types for radio button groups
		entryTypes = entryTypeService.findAll();
		sourceTypes = sourceTypeService.findAll();

		// populate the lists for select menus:
		List<Entry> parents = entryService.findParentCandidates(entry);
		List<TapestryVersion> versions = tapestryVersionService.findAll();
		List<License> licenses = licenseService.findAll();

		// create SelectModels for the select menus:
		parentSelectModel = selectModelFactory.create(parents,
				Entry.NAME_PROPERTY);
		versionSelectModel = selectModelFactory.create(versions,
				TapestryVersion.MENU_LABEL_PROPERTY);
		licenseSelectModel = selectModelFactory.create(licenses,
				License.NAME_PROPERTY);
	}

	/**
	 * Do the cross-field validation on the submitted form data. (This only runs
	 * after individual fields have been validated.)
	 */
	@OnEvent(value = EventConstants.VALIDATE, component = "editForm")
	void validateAcrossMultipleFields()
	{
		// We must have at least one URL
		if ((documentationUrl == null) && (demonstrationUrl == null))
		{
			// record an error, which also tells Tapestry to redisplay the form
			editForm.recordError(messages.get("some-url-required"));
		}
		
		// if both since & until dates are given and point to versions that
		// have dates, those dates must be in the correct order (since < until)
		if ((since != null) && (until != null) &&
				(since.getReleased() != null && until.getReleased() != null) &&
				(! since.getReleased().before(until.getReleased())))
		{
			// record an error, which also tells Tapestry to redisplay the form
			editForm.recordError(messages.get("since-after-until"));			
		}
	}

	/**
	 * Save the submitted changes to the database.
	 * 
	 * @return the saved object
	 */
	@OnEvent(value = EventConstants.SUCCESS, component = "editForm")
	Object saveSubmittedEntry()
	{
		if (entry == null)
		{
			// create a new, empty entry object
			entry = entryService.create();
		}

		// Copy the submitted form values into the (new or existing) object.
		// (Inserting the values *after* validation ensures that we don't
		// pollute our entity set with invalid or abandoned objects.)
		entry.setEntryType(this.type);
		entry.setSourceType(this.source);
		entry.setName(this.name);
		entry.setDescription(this.description);
		entry.setDocumentationUrl(this.documentationUrl);
		entry.setDemonstrationUrl(this.demonstrationUrl);
		entry.setParent(this.parent);
		entry.setSince(this.since);
		entry.setUntil(this.until);
		entry.setFirstAvailable(this.firstAvailable);
		entry.setLicense(this.license);
		// TODO - set enabled to false if user is under-privileged
		entry.setEnabled(enabled);

		// save to the database
		entry = entryService.save(entry);

		// TODO: if user is under-privileged, send e-mail notifications about
		// entry needing to be approved/enabled

		logger.info("Saved " + entry.getName()
				+ (entry.getEnabled() ? " (enabled)" : " (disabled)"));

		alertManager.alert(Duration.TRANSIENT, Severity.INFO, entry.getName() + " entry saved");
		return indexPage;
	}

	public void setEntry(Entry entry)
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
	void initPage(final MarkupWriter writer)
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
