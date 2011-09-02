package org.apache.tapestry.unicorn.entities;

import org.apache.tapestry.unicorn.entities.auto._Entry;

/**
 * An entity that stores all of the information about a Tapestry component,
 * mixin, page, module, etc., such as its name, description, documentation URL,
 * etc. Entries are hierarchical, which an optional parent entry and optional
 * children entries.
 * 
 * @author bharner
 */
public class Entry extends _Entry {

	private static final long serialVersionUID = -3455706854559238149L;
	
	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Integer id) {
	}

}
