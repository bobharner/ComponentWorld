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
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

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

	@SuppressWarnings("unused")
	@Property
	private ComponentEntry child; // used in a loop

	@SuppressWarnings("unused")
	@Property
	private List<ComponentEntry> children;

	@Inject
	private EntryService entryService;

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

	public Boolean getHasVersions()
	{
		if ((entry.getSince() != null) || (entry.getUntil() != null))
		{
			return true;
		}
		return false;
	}

	public String getMulticolumnClass()
	{
		if (children.size() > 5)
		{
			return "multicolumn3";
		}
		return "";
	}
}
