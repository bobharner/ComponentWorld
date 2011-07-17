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
package org.apache.tapestry.finder.encoders;

import org.apache.tapestry.finder.entities.ComponentEntry;
import org.apache.tapestry.finder.services.EntryService;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderFactory;

/**
 * A ValueEncoder for the ComponentEntry entity. This makes it easy for Tapestry
 * to convert a ComponentEntry ID to a fully-populated object, and vice-versa.
 * See http://tapestry.apache.org/using-select-with-a-list.html
 * 
 * @author bharner
 *
 */
public class ComponentEntryEncoder implements ValueEncoder<ComponentEntry>,
		ValueEncoderFactory<ComponentEntry> {

    @Inject
    private EntryService entryService;
    
    @Override
    public String toClient(ComponentEntry value) {
        // return the given object's ID
    	String cval = String.valueOf(value.getId()); 
    	//logger.error("here i am with cval=" + cval);
        return cval;
    }

    @Log
    @Override
    public ComponentEntry toValue(String id) { 
        // find the componentEntry object of the given ID in the database
        try {
			return entryService.findById(Integer.parseInt(id));
		}
		catch (NumberFormatException e) {
			throw new RuntimeException("ID " + id + " is not a number", e);
		}
    }

    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<ComponentEntry> create(Class<ComponentEntry> type) {
        return this; 
    }
} 
