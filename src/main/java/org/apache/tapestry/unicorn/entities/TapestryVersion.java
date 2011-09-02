package org.apache.tapestry.unicorn.entities;

import org.apache.tapestry.unicorn.entities.auto._TapestryVersion;

/**
 * A particular version of Tapestry. Generally these are release versions.
 */
public class TapestryVersion extends _TapestryVersion {

	private static final long serialVersionUID = -3226576049976116789L;

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Long id) {
	}
}
