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

import java.text.DateFormat;

import org.apache.componentworld.entities.auto._TapestryVersion;

/**
 * A particular version of Tapestry. Generally these are release versions.
 */
public class TapestryVersion extends _TapestryVersion {

	private static final long serialVersionUID = -3226576049976116789L;
    public static final String MENU_LABEL_PROPERTY = "menuLabel";

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Integer id) {
	}
	
	/**
	 * For select menus we display as "name - releasedDate"
	 * @return
	 */
	public String getMenuLabel() {
		if (getReleased() == null) {
			return getName();
		}
		return getName() + " - " + DateFormat.getDateInstance().format(getReleased());
	}
}
