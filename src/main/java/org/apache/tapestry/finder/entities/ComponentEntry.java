package org.apache.tapestry.finder.entities;

import org.apache.tapestry.finder.entities.auto._ComponentEntry;

/**
 * An entity that stores all of the information about a Tapestry component, such
 * as its name, description, documentation URL, etc.
 * <p>
 * Note that this is called ComponentEntry, rather than Component, simply to
 * avoid confusion with Tapestry components themselves, as well as the Tapestry
 * annotation of the same name. This object is a store of information about a
 * component, not the component itself.
 * 
 * @author bharner
 */
public class ComponentEntry extends _ComponentEntry {

	private static final long serialVersionUID = -3455706854559238149L;
	
	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Integer id) {
	}

}
