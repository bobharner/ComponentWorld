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
package org.apache.tapestry.finder.services;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry.finder.entities.ComponentEntry;


/**
 * Service for all {@link ComponentEntry} related functionality.
 * See also {@link GenericService}
 * 
 */
public interface EntryService extends GenericService<ComponentEntry, Integer> {

	/**
	 * Create a new {@link ComponentEntry} object, attached to the given
	 * Cayenne ObjectContext but not yet persisted to the database\
	 * 
	 * @param context
	 * @return the new object
	 */
	public ComponentEntry create(ObjectContext context);
	
	/**
	 * Create a new {@link ComponentEntry} object, not yet persisted to the
	 * database
	 * 
	 * @return the new object
	 */
	public ComponentEntry create();
	
	/**
	 * Find all {@link ComponentEntry}s, and return in alphabetical order by
	 * name
	 * 
	 * @return the list of components
	 */
	public List<ComponentEntry> findAll();

	/**
	 * Find all {@link ComponentEntry}s that are eligible to be parents of the
	 * given entry, and return in alphabetical order by name. An entry can't be
	 * a parent of other entries of the same type.
	 * 	 
	 * @param entry
	 * @return the list of components
	 */
	public List<ComponentEntry> findParentCandidates(ComponentEntry entry);

}