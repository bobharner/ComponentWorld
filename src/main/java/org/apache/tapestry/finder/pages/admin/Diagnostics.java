package org.apache.tapestry.finder.pages.admin;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

/**
 * This page is based on the portion of Tapestry's default ExceptionReport page that handles the session.
 */

public class Diagnostics
{
    private final static int megaBytes = 1024*1024;  

    @Property
	private String attributeName;
    
    @Property
    private MemoryPoolMXBean memoryPoolMXBean; // used in a loop
    
	@Inject
	private Request request;

	public boolean getHasSession() {
		return request.getSession(false) != null;
	}
	
	public int getMaxInactiveInterval() {
		if (getHasSession()) {
			return getSession().getMaxInactiveInterval() / 60;			
		}
		return 0;
	}

	public Session getSession() {
		return request.getSession(false);
	}

	public Object getAttributeValue() {
		return getSession().getAttribute(attributeName);
	}

	public Long getMemoryUsed() {
		Runtime runtime = Runtime.getRuntime();
		return (runtime.totalMemory() - runtime.freeMemory()) / megaBytes;
	}
	
	public Long getMemoryFree() {
		Runtime runtime = Runtime.getRuntime();
		return runtime.freeMemory() / megaBytes;
	}
	
	public Long getMemoryTotal() {
		Runtime runtime = Runtime.getRuntime();
		
		return runtime.totalMemory() / megaBytes;
	}
	public List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
		return ManagementFactory.getMemoryPoolMXBeans();
	}

	public Long getMemoryMax() {
		Runtime runtime = Runtime.getRuntime();
		return runtime.maxMemory() / megaBytes;
	}
}