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
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.tapestry.finder.entities.TapestryVersion;

/**
 * Service for creating, managing and searching for {@link TapestryVersion}
 * entities. See also {@link GenericService}
 */

public class TapestryVersionServiceImpl extends
		GenericServiceImpl<TapestryVersion, Integer> implements
		TapestryVersionService
{

	private static final long serialVersionUID = -657199702704315580L;

	@Override
	public TapestryVersion create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);
	}

	private TapestryVersion create(ObjectContext context)
	{

		TapestryVersion componentEntry = context.newObject(TapestryVersion.class);
		return componentEntry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TapestryVersion> findAll()
	{
		SelectQuery query = new SelectQuery(TapestryVersion.class);
		Ordering order = new Ordering(TapestryVersion.SORT_BY_PROPERTY,
					SortOrder.ASCENDING);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@Override
	public TapestryVersion findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(TapestryVersion.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(TapestryVersion.class, exp);

		@SuppressWarnings("unchecked")
		List<TapestryVersion> components = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (components.size() == 0) {

			return null;

		}

		return components.get(0);

	}

}