package org.apache.tapestry.finder.services;

import org.apache.tapestry.finder.entities.ModuleEntry;

/**
 * Service for all {@link ModuleEntry} related functionality.
 * See also {@link GenericService}
 * 
 */
public interface ModuleEntryService extends GenericService<ModuleEntry, Integer> {

	public ModuleEntry findById(Integer id);

}