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
import org.apache.componentworld.entities.Setting;

/**
 * Service for creating, managing and searching for {@link Setting}
 * entities (configuration settings). See also {@link GenericDataService}
 */

public class SettingsServiceImpl extends
		GenericDataServiceImpl<Setting, Integer> implements
		SettingsService
{
	private static final long serialVersionUID = -527834790428050586L;

	@Override
	public Setting create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);
	}

	private Setting create(ObjectContext context)
	{

		Setting entry = context.newObject(Setting.class);
		return entry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Setting> findAll()
	{
		SelectQuery query = new SelectQuery(Setting.class);
		Ordering order = new Ordering(Setting.NAME_PROPERTY,
					SortOrder.ASCENDING);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@Override
	public Setting findById(Integer id) {

	    if (id == null) {
	        throw new IllegalArgumentException("Settings ID cannot be null");
	    }
		Expression exp = ExpressionFactory.inExp(Setting.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(Setting.class, exp);

		@SuppressWarnings("unchecked")
		List<Setting> components = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (components.size() == 0) {

			return null;

		}

		return components.get(0);

	}

}