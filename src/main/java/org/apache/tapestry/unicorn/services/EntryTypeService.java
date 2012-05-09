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
package org.apache.tapestry.unicorn.services;

import java.util.List;

import org.apache.tapestry.unicorn.entities.EntryType;

/**
 * Service for all {@link EntryType} related functionality.
 * See also {@link GenericDataService}
 * 
 */
public interface EntryTypeService extends GenericDataService<EntryType, Integer> {

	public EntryType create();
	
	/**
	 * Returns an {@link EntryType} with the specified ID, or returns null.
	 *
	 * @param id
	 * @return
	 */
	public EntryType findById(Integer id);
	
	/**
	 * Get a list of all entry types, sorted by name ascending.
	 *
	 * @return List of all {@link EntryType}s in the system.
	 */
	public List<EntryType> findAll();
}