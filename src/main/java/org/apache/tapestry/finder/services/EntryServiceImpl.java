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
import org.apache.tapestry.finder.entities.Entry;
import org.apache.tapestry.finder.entities.EntryType;
import org.apache.tapestry.finder.entities.SourceType;

/**
 * Service for all {@link Entry} related functionality.
 */

public class EntryServiceImpl extends
		GenericServiceImpl<Entry, Integer> implements
		EntryService
{

	private static final long serialVersionUID = -657199702704315580L;

	@Override
	public Entry create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);
	}

	/**
	 * Create a new {@link Entry} object, attached to the given
	 * Cayenne ObjectContext but not yet persisted to the database
	 * 
	 * @param context
	 * @return the new object
	 */
	private Entry create(ObjectContext context)
	{
		Entry entry = context.newObject(Entry.class);
		return entry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findAll()
	{
		SelectQuery query = new SelectQuery(Entry.class);
		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findChildren(Entry entry)
	{
		// where parent matches the id of this component
		Expression exp = ExpressionFactory.inExp(
				Entry.PARENT_PROPERTY, entry.getId());

		SelectQuery query = new SelectQuery(Entry.class, exp);

		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findByType(EntryType entryType,
			List<SourceType> sourceTypes)
	{
		Expression expression;
		if (entryType == null)
		{
			expression = null;
		}
		else
		{
			// an expression for matching the given entry type
			expression = ExpressionFactory.matchExp(
				Entry.ENTRY_TYPE_PROPERTY, entryType);
		}

		// add an expression for matching any of the selected sourceType values
		if ((sourceTypes != null) && (sourceTypes.size() != 0))
		{
			// FIXME: the following doesn't work			
			Expression exp = ExpressionFactory.inDbExp(Entry.SOURCE_TYPE_PROPERTY, sourceTypes);
			if (expression == null) {
				expression = exp;
			}
			else
			{
				expression.andExp(exp);
			}
		}

		SelectQuery query = new SelectQuery(Entry.class, expression);
		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findParentCandidates(Entry entry)
	{
		// TODO: need complex expression saying "all entries whose EntryType
		// has "isContainer" true

		Expression exp;
		if (entry == null)
		{
			exp = null;
		}
		else
		{
			// filter out entries of the same type as the given entry
			exp = ExpressionFactory.noMatchExp(
				Entry.ENTRY_TYPE_PROPERTY, entry.getEntryType());
		}

		SelectQuery query = new SelectQuery(Entry.class, exp);
		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@Override
	public Entry findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(Entry.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(Entry.class, exp);

		@SuppressWarnings("unchecked")
		List<Entry> components = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (components.size() == 0) {

			return null;

		}

		return components.get(0);

	}


}