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
package org.apache.tapestry.unicorn.pages;

import java.util.List;

import org.apache.tapestry.unicorn.encoders.SourceTypeEncoder;
import org.apache.tapestry.unicorn.entities.SourceType;
import org.apache.tapestry.unicorn.services.SourceTypeService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

/**
 * Start page of web application. Here we display and manage the high-level
 * selection menus, along with the initial list of entries (the latter appearing
 * within a zone)
 */
public class Demo
{	
	@SuppressWarnings("unused")
	@Property
	private List<SourceType> selectedSourceTypes;
	
	@Persist
	@Property
	private SourceType selectedSourceType;

	@Property
	private List<SourceType> sourceTypes; // not needed if using checklist!!!
	
	@SuppressWarnings("unused")
	@Property
	private SelectModel sourceTypeSelectModel;

	@Inject
	private SourceTypeService sourceTypeService;

	// FIXME: we have to inject SourceTypeService a second time, under a different name,
	// because within a ValueEncoder the first one is always null.
	// WHAT IF WE TRIED TWO SELECT COMPONENTS ON THE SAME PAGE???
	@Inject
	private SourceTypeService demoService;
		
	@Inject
	private SelectModelFactory selectModelFactory;
	
	@SuppressWarnings("unused")
	@Property
	@Persist
	private String msg;
	
	public ValueEncoder<SourceType> getSourceTypeEncoder()
    {
        return new SourceTypeEncoder()  {

            @Override
            public String toClient(SourceType value) {
            	if (value == null) {
            		return null;
            	}
                // return the given object's ID
            	String cval = String.valueOf(value.getId()); 
                return cval;
            }

            @Override
            public SourceType toValue(String id) { 
            	if (id == null)
            	{
            		return null;
            	}
                // find the Entry object of the given ID in the database
                try {
                	// FIXME: why is demoService sometimes null????
                	if (demoService == null) { System.out.println("AAAAAAAAAAAAAAAHHHHH it's still null"); }
        			return demoService.findById(Integer.parseInt(id));
        		}
        		catch (NumberFormatException e) {
        			throw new RuntimeException("ID " + id + " is not a number", e);
        		}
            }
        };
    }

	/**
	 * As an event handler, respond to the form's PREPARE_FOR_RENDER event,
	 * doing setup actions prior to rendering the form.
	 */
	@OnEvent(value = EventConstants.PREPARE_FOR_RENDER, component = "categorySelection")
	void prepare()
	{
		// populate the list of source types for the source type menu
		sourceTypes = sourceTypeService.findAll();

		// create a SelectModel from the list of source types
		sourceTypeSelectModel = selectModelFactory.create(sourceTypes,
				SourceType.NAME_PLURAL_PROPERTY);
		msg = "no message";
	}
	@OnEvent(value = EventConstants.ACTION, component = "categorySelection")
	Object handleSubmission() {
		System.out.println ("Okay, here in handleSubmission");
		msg = "here we are in handleSubmission";
		return "this";
	}

	/**
	 * Handle the "Success" event from the CategorySelection form
	 * 
	 * @return the current page (redraw self)
	 */
	@OnEvent(value = EventConstants.SUCCESS, component = "categorySelection")
	Object redrawPage()
	{
		msg = "inside redrawPage, selected=" + selectedSourceType.getName();
		System.out.println ("inside redrawPage...");
	    return this;
	}

}
