/**
 *
 */
package org.apache.tapestry.finder.services;

import java.util.List;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry.finder.entities.ModuleEntry;

/**
 * Service for all {@link ModuleEntry} related functionality.
 */

public class ModuleEntryServiceImpl extends GenericServiceImpl<ModuleEntry, Integer>
		implements ModuleEntryService {

	private static final long serialVersionUID = -657199702704315580L;

	@Override
	public ModuleEntry findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(ModuleEntry.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(ModuleEntry.class, exp);

		@SuppressWarnings("unchecked")
		List<ModuleEntry> modules = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (modules.size() == 0) {

			return null;

		}
		return modules.get(0);
	}

}