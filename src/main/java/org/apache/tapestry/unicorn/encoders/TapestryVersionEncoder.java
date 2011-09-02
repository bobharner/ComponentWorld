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
package org.apache.tapestry.unicorn.encoders;

import org.apache.tapestry.unicorn.entities.TapestryVersion;
import org.apache.tapestry.unicorn.services.TapestryVersionService;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderFactory;

/**
 * A ValueEncoder for the EntryType entity. This makes it easy for Tapestry
 * to convert an EntryType ID to a fully-populated object, and vice-versa.
 * See http://tapestry.apache.org/using-select-with-a-list.html
 * 
 * @author bharner
 *
 */
public class TapestryVersionEncoder implements ValueEncoder<TapestryVersion>,
		ValueEncoderFactory<TapestryVersion> {

    @Inject
    private TapestryVersionService tapestryVersionService;
    
    @Override
    public String toClient(TapestryVersion value) {
    	if (value == null) {
    		return null;
    	}
        // return the given object's ID
    	String cval = String.valueOf(value.getId()); 
        return cval;
    }

    @Override
    public TapestryVersion toValue(String id) { 
    	if (id == null)
    	{
    		return null;
    	}
        // find the Entry object of the given ID in the database
        try {
			return tapestryVersionService.findById(Integer.parseInt(id));
		}
		catch (NumberFormatException e) {
			throw new RuntimeException("ID " + id + " is not a number", e);
		}
    }

    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<TapestryVersion> create(Class<TapestryVersion> type) {
        return this; 
    }
} 
