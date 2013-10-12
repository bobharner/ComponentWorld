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
package org.apache.componentworld.entities;

import org.apache.componentworld.entities.auto._Entry;

/**
 * An entity that stores all of the information about a Tapestry component,
 * mixin, page, module, etc., such as its name, description, documentation URL,
 * etc. Entries are hierarchical, with an optional parent entry and optional
 * children entries.
 * 
 * @author bharner
 */
public class Entry extends _Entry {

	private static final long serialVersionUID = -3455706854559238149L;
	
	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Integer id) {
	}

}
