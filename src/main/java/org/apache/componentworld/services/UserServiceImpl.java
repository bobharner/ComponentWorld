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


import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.componentworld.entities.User;

/**
 * Service for all {@link User} related functionality.
 * See also {@link GenericDataService}
 */
public class UserServiceImpl extends GenericDataServiceImpl<User, Integer> implements UserService {

	private static final long serialVersionUID = -6305278621616029751L;
	private static final String INVITE_STRING = "Invited";

	/* (non-Javadoc)
	 * @see org.apache.componentworld.services.UserService#findByUserId(java.lang.String)
	 */

	
	@Override
	public User findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(User.USER_ID_PROPERTY, id);
		SelectQuery query = new SelectQuery(User.class, exp);

		@SuppressWarnings("unchecked")
		List<User> users = DataContext.getThreadObjectContext().performQuery(
				query);

		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	/**
	 * Find and return the {@link User} instance with the given userId
	 * 
	 * returns the User, if found, or else null
	 */
	public User findByUserId(String userId) {

		Expression exp = ExpressionFactory.inExp(User.USER_ID_PROPERTY, userId);
		SelectQuery query = new SelectQuery(User.class, exp);

		@SuppressWarnings("unchecked")
		List<User> users = DataContext.getThreadObjectContext().performQuery(
				query);

		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.componentworld.services.UserService#getAllUsers()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() {

		Expression exp = ExpressionFactory.noMatchExp(User.USER_ID_PROPERTY,
				INVITE_STRING);

		SelectQuery query = new SelectQuery(User.class, exp);

		Ordering order = new Ordering(User.LAST_NAME_PROPERTY,
				SortOrder.ASCENDING_INSENSITIVE);

		query.addOrdering(order);

		return DataContext.getThreadObjectContext().performQuery(query);

	}


}
