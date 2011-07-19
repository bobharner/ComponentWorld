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
import org.apache.tapestry.finder.entities.EntryType;

/**
 * Service for all {@link EntryType} related functionality.
 */

public class EntryTypeServiceImpl extends
		GenericServiceImpl<EntryType, Integer> implements
		EntryTypeService
{

	private static final long serialVersionUID = -657199702704315580L;

	/**
	 * Create a new {@link EntryType} instance.
	 * 
	 * @return Instance of EntryType with only ID populated and attached to
	 *         a valid {@link ObjectContext}
	 */
	public EntryType create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);

	}

	/**
	 * Create a new {@link EntryType} instance using the specified
	 * {@link ObjectContext}.
	 * 
	 * @param context
	 * @return Instance of EntryType attached to a valid
	 *         {@link ObjectContext}
	 */
	public EntryType create(ObjectContext context)
	{

		EntryType entryType = context.newObject(EntryType.class);
		return entryType;
	}
	
	/**
	 * Returns a {@link EntryType} with the specified ID or it returns null.
	 *
	 * @param id
	 * @return
	 */
	@Override
	public EntryType findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(EntryType.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(EntryType.class, exp);

		@SuppressWarnings("unchecked")
		List<EntryType> entryTypes = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (entryTypes.size() == 0) {

			return null;

		}

		return entryTypes.get(0);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EntryType> findAll() {
		
		SelectQuery query = new SelectQuery(EntryType.class);
		Ordering order = new Ordering(EntryType.SORT_BY_PROPERTY,
					SortOrder.ASCENDING);
		query.addOrdering(order);
		
		// TODO: change to use shared cache
		// per http://cayenne.apache.org/doc30/caching-lookup-tables.html
		return DataContext.getThreadObjectContext().performQuery(query);
	}

}