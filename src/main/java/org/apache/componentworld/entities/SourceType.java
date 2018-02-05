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

import org.apache.componentworld.entities.auto._SourceType;

/**
 * A Source Type, indicating where a particular component/page/mixin/module came
 * from (e.g., from Tapestry itself, a 3rd party module, a blog post, etc)
 * 
 * @author bharner
 * 
 */
public class SourceType extends _SourceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5729618195432584843L;

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Integer id) {
	}
}
