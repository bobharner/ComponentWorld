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
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.componentworld.entities.Role;

/**
 * Service for all {@link Role} related functionality.
 * See also {@link GenericDataService}
 *
 */
public class RoleServiceImpl implements RoleService {

	/* (non-Javadoc)
	 * @see org.apache.componentworld.services.RoleService#getAllRoles()
	 */
	@SuppressWarnings("unchecked")
	public List<Role> findAllRoles() {

		SelectQuery query = new SelectQuery(Role.class);

		Ordering order = new Ordering(Role.NAME_PROPERTY,
				SortOrder.ASCENDING_INSENSITIVE);

		query.addOrdering(order);

		return DataContext.getThreadObjectContext().performQuery(query);

	}

	/**
	 * Returns a {@link Role} with the specified ID or it returns null.
	 *
	 * @param uid
	 * @return
	 */
/*	public Role getRoleByUid(String uid) {

		Expression exp = ExpressionFactory.inExp(Role.UID_PROPERTY, uid);

		SelectQuery query = new SelectQuery(Role.class, exp);

		@SuppressWarnings("unchecked")
		List<Role> roles = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (roles.size() == 0) {

			return null;

		}

		return roles.get(0);

	}*/

}
