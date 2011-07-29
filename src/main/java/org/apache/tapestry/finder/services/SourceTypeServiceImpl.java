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
import org.apache.tapestry.finder.entities.SourceType;

/**
 * Service for all {@link SourceType} related functionality.
 */

public class SourceTypeServiceImpl extends
		GenericServiceImpl<SourceType, Integer> implements
		SourceTypeService
{

	private static final long serialVersionUID = -657199702704315580L;

	@Override
	public SourceType create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);

	}

	/**
	 * Create a new {@link SourceType} instance using the specified
	 * {@link ObjectContext}.
	 * 
	 * @param context
	 * @return Instance of SourceType attached to a valid
	 *         {@link ObjectContext}
	 */
	private SourceType create(ObjectContext context)
	{

		SourceType sourceType = context.newObject(SourceType.class);
		return sourceType;
	}

	@Override
	public SourceType findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(SourceType.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(SourceType.class, exp);

		@SuppressWarnings("unchecked")
		List<SourceType> sourceTypes = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (sourceTypes.size() == 0) {

			return null;

		}

		return sourceTypes.get(0);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SourceType> findAll() {
		
		SelectQuery query = new SelectQuery(SourceType.class);
		Ordering order = new Ordering(SourceType.SORT_BY_PROPERTY,
					SortOrder.ASCENDING);
		query.addOrdering(order);

		// TODO: change to use shared cache
		// per http://cayenne.apache.org/doc30/caching-lookup-tables.html
		return DataContext.getThreadObjectContext().performQuery(query);
	}

}