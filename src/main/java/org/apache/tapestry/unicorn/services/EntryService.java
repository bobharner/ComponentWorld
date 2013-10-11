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
package org.apache.tapestry.unicorn.services;

import java.util.List;

import org.apache.tapestry.unicorn.entities.BrokenLink;
import org.apache.tapestry.unicorn.entities.Entry;
import org.apache.tapestry.unicorn.entities.EntryType;
import org.apache.tapestry.unicorn.entities.SourceType;

/**
 * Service for creating, managing and searching for {@link Entry}
 * entities. See also {@link GenericDataService}
 * 
 */
public interface EntryService extends GenericDataService<Entry, Integer> {

	/**
	 * Minimum and maximum length of the first sentence of an entry's
	 * description (or, at least, the boundaries that we use in sensibly
	 * abbreviating that description).
	 */
	public static final int MINIMUM_FIRST_SENTENCE_SIZE = 20;
	public static final int MAXIMUM_FIRST_SENTENCE_SIZE = 130;

	/**
	 * Create a new {@link Entry} object, not yet persisted to the
	 * database
	 * 
	 * @return the new object
	 */
	public Entry create();
	
	/**
	 * Find all {@link Entry}s, and return in alphabetical order by
	 * name
	 * 
	 * @return the list of components
	 */
	public List<Entry> findAll();

	/**
	 * Find all {@link Entry}s that match the given {@link EntryType}
	 * and at least one of the given {@link SourceType}s.
	 *  
	 * @param entryType the EntryType to match, or null to match all
	 * @param sourceTypes list of source types to match on, or null to match all
	 * @param firstLetter the first letter of all matching entries, or null to match on all
	 * @return a list of matching Entry objects, in alphabetical order by name
	 */
	public List<Entry> findByType(EntryType entryType,
			List<SourceType> sourceTypes, Character firstLetter);

	/**
	 * Find all {@link Entry}s that are eligible to be parents of the
	 * given entry, and return in alphabetical order by name. An entry can't be
	 * a parent of other entries of the same type.
	 * 	 
	 * @param entry
	 * @return the list of components
	 */
	public List<Entry> findParentCandidates(Entry entry);

	/**
	 * Find all {@link Entry}s that are children of the given entry.
	 * Typically these are the components & mixins included within a module,
	 * or the modules included in a framework.
	 * 
	 * @return
	 */
	public List<Entry> findChildren(Entry entry);
	

	/**
	 * Makes a sensible abbreviation of the given entry's description.
	 * 
	 * @return the abbreviated description
	 */
	public String abbreviateDescription(Entry entry);
	
	/**
	 * Test all URLs in all entries and return a list of those that are broken
	 * (that is, those that fail when an HTTP request is attempted)
	 * 
	 * @return the list of broken links
	 */
	public List<BrokenLink> findBrokenLinks();}