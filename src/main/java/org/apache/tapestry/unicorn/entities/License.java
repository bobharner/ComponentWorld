package org.apache.tapestry.unicorn.entities;

import org.apache.tapestry.unicorn.entities.auto._License;

/**
 * A software license, such Apache 2.0, GPL, MPL.
 * 
 * @author bharner
 *
 */
public class License extends _License {

	private static final long serialVersionUID = 3724269616148911173L;

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Long id) {
	}
}
