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

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.componentworld.entities.License;

/**
 * Service for creating, managing and searching for {@link License}
 * entities. See also {@link GenericDataService}
 */

public class LicenseServiceImpl extends
		GenericDataServiceImpl<License, Integer> implements
		LicenseService
{

	private static final long serialVersionUID = -657199702704315580L;

	@Override
	public License create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);
	}

	private License create(ObjectContext context)
	{

		License entry = context.newObject(License.class);
		return entry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<License> findAll()
	{
		SelectQuery query = new SelectQuery(License.class);
		Ordering order = new Ordering(License.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@Override
	public License findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(License.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(License.class, exp);

		@SuppressWarnings("unchecked")
		List<License> components = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (components.size() == 0) {

			return null;

		}

		return components.get(0);

	}

}