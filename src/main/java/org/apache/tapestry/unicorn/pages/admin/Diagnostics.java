/* Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tapestry.unicorn.pages.admin;

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
    private final static int MEGABYTES = 1024*1024;  

    @Property
	private String attributeName;
    
    @SuppressWarnings("unused")
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
	
	public String getJavaVendor() {
		return System.getProperty("java.vendor");
	}	

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}
	
	public String getOsArch() {
		return System.getProperty("os.arch");
	}
	
	public String getOsName() {
		return System.getProperty("os.name");
	}
	
	public String getOsVersion() {
		return System.getProperty("os.version");
	}

	public Session getSession() {
		return request.getSession(false);
	}

	public Object getAttributeValue() {
		return getSession().getAttribute(attributeName);
	}

	public Long getMemoryUsed() {
		Runtime runtime = Runtime.getRuntime();
		return (runtime.totalMemory() - runtime.freeMemory()) / MEGABYTES;
	}
	
	public Long getMemoryFree() {
		Runtime runtime = Runtime.getRuntime();
		return runtime.freeMemory() / MEGABYTES;
	}
	
	public Long getMemoryTotal() {
		Runtime runtime = Runtime.getRuntime();
		
		return runtime.totalMemory() / MEGABYTES;
	}
	public List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
		return ManagementFactory.getMemoryPoolMXBeans();
	}

	public Long getMemoryMax() {
		Runtime runtime = Runtime.getRuntime();
		return runtime.maxMemory() / MEGABYTES;
	}
}