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
package org.apache.componentworld.services;

import java.util.List;

import org.apache.componentworld.entities.User;

public interface UserService
{

	/**
	 * Find a user by the given user id.
	 *
	 * @param the user id of the desired user
	 * @return the first matching User, or null if not found
	 */
	public User findByUserId(String userId);

	/**
	 * Get a list of all users, sorted by name ascending.
	 *
	 * @return List of all {@link User}s in the system.
	 */
	public List<User> findAll();

}