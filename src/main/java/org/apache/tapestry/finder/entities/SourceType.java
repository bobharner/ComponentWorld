package org.apache.tapestry.finder.entities;

import org.apache.tapestry.finder.entities.auto._SourceType;

/**
 * A Source Type, indicating where a particular component/page/mixin/module came
 * from (e.g., from Tapestry itself, a 3rd party module, a blog post, etc)
 * 
 * @author bharner
 * 
 */
public class SourceType extends _SourceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5729618195432584843L;

	/**
	 * A no-op (Manually setting the ID is prohibited)
	 */
	@Override
	public void setId(Long id) {
	}
}
