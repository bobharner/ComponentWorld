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
import org.apache.tapestry.finder.entities.EntryType;
import org.apache.tapestry.finder.entities.User;

/**
 * Service for all {@link ComponentEntry} related functionality.
 * See also {@link GenericService}
 * 
 */
public interface EntryTypeService extends GenericService<EntryType, Integer> {

	public EntryType create(ObjectContext context);
	public EntryType create();
	/**
	 * Get a list of all entry types, sorted by name ascending.
	 *
	 * @return List of all {@link User}s in the system.
	 */
	public List<EntryType> findAll();
}