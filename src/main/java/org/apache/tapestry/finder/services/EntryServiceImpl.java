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
import org.apache.tapestry.finder.entities.ComponentEntry;

/**
 * Service for all {@link ComponentEntry} related functionality.
 */

public class EntryServiceImpl extends
		GenericServiceImpl<ComponentEntry, Integer> implements
		EntryService
{

	private static final long serialVersionUID = -657199702704315580L;

	@Override
	public ComponentEntry create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);
	}

	@Override
	public ComponentEntry create(ObjectContext context)
	{

		ComponentEntry componentEntry = context.newObject(ComponentEntry.class);
		return componentEntry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComponentEntry> findAll()
	{
		SelectQuery query = new SelectQuery(ComponentEntry.class);
		Ordering order = new Ordering(ComponentEntry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComponentEntry> findParentCandidates(ComponentEntry entry)
	{
		// TODO: need complex expression saying "all entries whose EntryType
		// relationship refers to an EntryType with "isContainer" true
		
		Expression exp;
		if (entry == null)
		{
			exp = null;
		}
		else
		{
			// filter out entries of the same type as the given entry
			exp = ExpressionFactory.noMatchExp(
				ComponentEntry.ENTRY_TYPE_PROPERTY, entry.getEntryType());
		}

		SelectQuery query = new SelectQuery(ComponentEntry.class, exp);
		Ordering order = new Ordering(ComponentEntry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@Override
	public ComponentEntry findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(ComponentEntry.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(ComponentEntry.class, exp);

		@SuppressWarnings("unchecked")
		List<ComponentEntry> components = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (components.size() == 0) {

			return null;

		}

		return components.get(0);

	}


}