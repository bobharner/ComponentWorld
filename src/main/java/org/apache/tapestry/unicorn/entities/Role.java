package org.apache.tapestry.unicorn.entities;

import org.apache.tapestry.unicorn.entities.auto._Role;

/**
 * A user role, such as administrator or guest
 * 
 * @author bharner
 *
 */
public class Role extends _Role {

	private static final long serialVersionUID = 3256207895974166981L;

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Long id) {
	}
}
