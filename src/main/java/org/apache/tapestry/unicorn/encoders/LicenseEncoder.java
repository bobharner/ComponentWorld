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

import org.apache.tapestry.unicorn.entities.License;
import org.apache.tapestry.unicorn.services.LicenseService;
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
public class LicenseEncoder implements ValueEncoder<License>,
		ValueEncoderFactory<License> {

    @Inject
    private LicenseService LicenseService;
    
    @Override
    public String toClient(License value) {
    	if (value == null) {
    		return null;
    	}
        // return the given object's ID
    	String cval = String.valueOf(value.getId()); 
        return cval;
    }

    @Override
    public License toValue(String id) { 
    	if (id == null)
    	{
    		return null;
    	}
        // find the Entry object of the given ID in the database
        try {
			return LicenseService.findById(Integer.parseInt(id));
		}
		catch (NumberFormatException e) {
			throw new RuntimeException("ID " + id + " is not a number", e);
		}
    }

    // let this ValueEncoder also serve as a ValueEncoderFactory
    @Override
    public ValueEncoder<License> create(Class<License> type) {
        return this; 
    }
} 
