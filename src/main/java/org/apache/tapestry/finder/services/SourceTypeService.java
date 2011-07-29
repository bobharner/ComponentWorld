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
import org.apache.tapestry.finder.entities.SourceType;

/**
 * Service for all {@link SourceType} related functionality.
 * See also {@link GenericService}
 * 
 */
public interface SourceTypeService extends GenericService<SourceType, Integer> {

	/**
	 * Create a new {@link SourceType} instance.
	 * 
	 * @return Instance of SourceType with only ID populated and attached to
	 *         a valid {@link ObjectContext}
	 */
	public SourceType create();
	
	/**
	 * Returns a {@link SourceType} with the specified ID or it returns null.
	 *
	 * @param id
	 * @return
	 */
	public SourceType findById(Integer id);
	/**
	 * Get a list of all entry types, sorted by name ascending.
	 *
	 * @return List of all {@link SourceType}s in the system.
	 */
	public List<SourceType> findAll();
}