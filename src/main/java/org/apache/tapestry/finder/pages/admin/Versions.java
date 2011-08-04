package org.apache.tapestry.finder.pages.admin;

import java.util.List;

import org.apache.tapestry.finder.entities.TapestryVersion;
import org.apache.tapestry.finder.services.TapestryVersionService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Administration page for the Versions database table
 *
 */
public class Versions
{
	@Inject
	private TapestryVersionService tapestryVersionService;
	
	@SuppressWarnings("unused")
	@Property
	private TapestryVersion version; // used in a list

	private List<TapestryVersion> versions;
	
	public List<TapestryVersion> getVersions()
	{
		return versions;
	}
	
	/**
	 * Perform initializations needed before page renders
	 */
	@SetupRender
	public void init()
	{
		versions = tapestryVersionService.findAll();
	}
}
