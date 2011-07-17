package org.apache.tapestry.finder.entities;

import org.apache.tapestry.finder.entities.auto._ModuleEntry;

/**
 * An entity that stores all of the information about a Tapestry module, such as
 * its name, description, documentation URL, etc.
 * <p>
 * Note that this is called ModuleEntry, rather than Module, simply to avoid
 * confusion with Tapestry modules themselves. This object is a store of
 * information about a module, not the module itself.
 * 
 * @author bharner
 */
public class ModuleEntry extends _ModuleEntry {

	private static final long serialVersionUID = -4277745678424295852L;

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Long id) {
	}
}
