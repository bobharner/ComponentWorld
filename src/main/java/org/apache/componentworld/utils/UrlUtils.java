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
package org.apache.componentworld.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Some simple URL manipulation utilities.
 */
public class UrlUtils
{
	/**
	 * The start of the URL of all core library components (without protocol)
	 */
	public static final String CORE_DOCUMENTATION_URL_PREFIX = "tapestry.apache.org/current/apidocs/org/apache/tapestry5/";

	/**
	 * Shorten the given Url string
	 * 
	 * @param url
	 * @return
	 */
	public static String shorten(String url, int length)
	{

		if (url == null)
		{
			return "";
		}

		// remove leading http:// or https://
		String newUrl = removeProtocol(url);

		// remove trailing slash, if any
		if (newUrl.endsWith("/"))
		{
			newUrl = newUrl.substring(0, newUrl.length() - 1);
		}

		if (newUrl.startsWith(CORE_DOCUMENTATION_URL_PREFIX))
		{
			return "..."
					+ newUrl.substring(CORE_DOCUMENTATION_URL_PREFIX.length());
		}
		return StringUtils.abbreviateMiddle(newUrl, "...", length);
	}

	/**
	 * Remove any "http://" or "https://" from the beginning of the given URL
	 * 
	 * @param url
	 * @return the shortened URL
	 */
	private static String removeProtocol(String url)
	{

		if (url.startsWith("http://"))
		{
			return url.substring("http://".length());
		}
		if (url.startsWith("https://"))
		{
			return url.substring("https://".length());
		}
		return url;
	}
}
