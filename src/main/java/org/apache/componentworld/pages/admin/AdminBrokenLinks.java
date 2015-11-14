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

import org.apache.componentworld.entities.BrokenLink;
import org.apache.componentworld.entities.Entry;
import org.apache.componentworld.services.EntryService;
import org.apache.componentworld.utils.UrlUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * "Find Broken Links" administration page 
 *
 */
@Secure
public class AdminBrokenLinks
{
	@Inject
	private EntryService entryService;

	@Inject
	private Request request;

	@Inject
	private AlertManager alertManager;
	
	@Persist // FIXME - this is only here until we have database storage set up for broken links
	@Property
	private List<BrokenLink> brokenLinks;
	
	@Property
	private BrokenLink brokenLink; // used in a TML loop

	@InjectComponent
    private Zone listZone;

    /**
     * Find all broken links in the "documentation" and "demonstration" URLs
     * of all entries
     * @return
     */
    @OnEvent(value = EventConstants.ACTION, component = "start")
    Object findBrokenLinks()
    {
        brokenLinks = entryService.findBrokenLinks(); // TODO: make a background process

        // return either the zone (if using AJAX) or the whole page
        return request.isXHR() ? listZone.getBody() : this;
    }
    
    /**
     * Get an abbreviated form of the current broken URL
     * @return the abbreviated URL
     */
    public String getShortBrokenUrl()
    {
        return UrlUtils.shorten(getBrokenUrl(), 50);
    }
    
    /**
     * Get the current broken URL
     * @return the URL
     */
    public String getBrokenUrl()
    {
        if (Entry.DOCUMENTATION_URL_PROPERTY.equals(brokenLink.getFieldName()))
        {
            return brokenLink.getEntry().getDocumentationUrl();
        }
        else if (Entry.DEMONSTRATION_URL_PROPERTY.equals(brokenLink.getFieldName()))
        {
            return brokenLink.getEntry().getDemonstrationUrl();
        }
        return "[unknown field " + brokenLink.getFieldName() + "]";
    }
    
    /**
     * Get the current broken link's field name
     * @return the field name
     */
    public String getFieldName()
    {
        if (Entry.DOCUMENTATION_URL_PROPERTY.equals(brokenLink.getFieldName()))
        {
            return "Docu";
        }
        else if (Entry.DEMONSTRATION_URL_PROPERTY.equals(brokenLink.getFieldName()))
        {
            return "Demo";
        }
        return "";
    }

}
