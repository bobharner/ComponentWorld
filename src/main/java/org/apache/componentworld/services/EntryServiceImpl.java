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
package org.apache.componentworld.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.componentworld.entities.BrokenLink;
import org.apache.componentworld.entities.Entry;
import org.apache.componentworld.entities.EntryType;
import org.apache.componentworld.entities.HttpResult;
import org.apache.componentworld.entities.SourceType;
import org.apache.tapestry5.func.Tuple;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

/**
 * Service for all {@link Entry} related functionality.
 */

public class EntryServiceImpl extends
		GenericDataServiceImpl<Entry, Integer> implements
		EntryService
{

	private static final long serialVersionUID = -657199702704315580L;
	
	@Inject
    private Logger log;

	@Override
	public Entry create()
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		return create(context);
	}

	/**
	 * Create a new {@link Entry} object, attached to the given
	 * Cayenne ObjectContext but not yet persisted to the database
	 * 
	 * @param context
	 * @return the new object
	 */
	private Entry create(ObjectContext context)
	{
		Entry entry = context.newObject(Entry.class);
		return entry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findAll()
	{
		SelectQuery query = new SelectQuery(Entry.class);
		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findChildren(Entry entry)
	{
		// where parent matches the id of this component
		Expression exp = ExpressionFactory.inExp(
				Entry.PARENT_PROPERTY, entry.getId());

		SelectQuery query = new SelectQuery(Entry.class, exp);

		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findByType(EntryType entryType,
			List<SourceType> sourceTypes, Character firstLetter)
	{
		Expression expression = null;
		if (entryType != null)
		{
			// an expression for matching the given EntryType
			expression = ExpressionFactory.matchExp(
				Entry.ENTRY_TYPE_PROPERTY, entryType);
		}

		// add an expression for matching any of the selected SourceType values
		if ((sourceTypes != null) && (sourceTypes.size() != 0))
		{
			Expression exp = null;
			ArrayList<Integer> idList = new ArrayList<Integer>();
			// build a list of IDs of desired sourceTypes 
			for (SourceType sourceType : sourceTypes)
			{
				idList.add(sourceType.getId());
			}

			// add an "in" expression for the desired SourceTypes 
			exp = ExpressionFactory.inExp(Entry.SOURCE_TYPE_PROPERTY, idList);
			expression = (expression == null) ? exp : expression.andExp(exp);
		}
		
		// add an expression for matching on first char of entry's name
		if (firstLetter != null)
		{
			Expression exp = null;
			// add "Entry.ENTRY_NAME_PROPERTY like 'A%'
			exp = ExpressionFactory.likeIgnoreCaseExp(Entry.NAME_PROPERTY, firstLetter.toString() + '%');
			expression = (expression == null) ? exp : expression.andExp(exp);
		}


		SelectQuery query = new SelectQuery(Entry.class, expression);
		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findParentCandidates(Entry entry)
	{
		// TODO: need complex expression saying "all entries whose EntryType
		// has "isContainer" true

		Expression exp;
		if (entry == null)
		{
			exp = null;
		}
		else
		{
			// filter out entries of the same type as the given entry
			exp = ExpressionFactory.noMatchExp(
				Entry.ENTRY_TYPE_PROPERTY, entry.getEntryType());
		}

		SelectQuery query = new SelectQuery(Entry.class, exp);
		Ordering order = new Ordering(Entry.NAME_PROPERTY,
					SortOrder.ASCENDING_INSENSITIVE);
		query.addOrdering(order);
		return DataContext.getThreadObjectContext().performQuery(query);
	}

	@Override
	public Entry findById(Integer id) {

		Expression exp = ExpressionFactory.inExp(Entry.ID_PROPERTY, id);

		SelectQuery query = new SelectQuery(Entry.class, exp);

		@SuppressWarnings("unchecked")
		List<Entry> components = DataContext.getThreadObjectContext()
				.performQuery(query);

		if (components.size() == 0) {

			return null;

		}

		return components.get(0);

	}

	/**
	 * Returns an abbreviation of the given entry's description. We take
	 * the first sentence if it's a reasonable length, otherwise we take either
	 * everything (if it's small enough) or else a truncated substring with
	 * "..." appended.
	 * 
	 * @return the abbreviated description
	 */
	@Override
	public String abbreviateDescription(Entry entry)
	{
		if (entry.getDescription() == null)
		{
			return "";
		}
		// look for period-space *after* the first few chars
		int endOfSentence = entry.getDescription().indexOf(". ", MINIMUM_FIRST_SENTENCE_SIZE);
		if (endOfSentence > MINIMUM_FIRST_SENTENCE_SIZE) // found?
		{
			return entry.getDescription().substring(0, endOfSentence);
		}
		if (entry.getDescription().length() < MAXIMUM_FIRST_SENTENCE_SIZE)
		{
			return entry.getDescription();
		}
		// find the last space in the first N chars
		String truncatedAbbreviation = entry.getDescription().substring(0, MAXIMUM_FIRST_SENTENCE_SIZE);
		int endOfWord = truncatedAbbreviation.lastIndexOf(" ");
		if (endOfWord > MINIMUM_FIRST_SENTENCE_SIZE)
		{
			return entry.getDescription().substring(0, endOfWord) + "...";
		}
		else
		{
			return truncatedAbbreviation + "...";
		}
	}

    @Override
    public List<BrokenLink> findBrokenLinks()
    {
        List<Entry> entries = findAll();
        final String[] urlProperties = { Entry.DOCUMENTATION_URL_PROPERTY, Entry.DEMONSTRATION_URL_PROPERTY };
        List<BrokenLink> brokenLinks = new ArrayList<BrokenLink>();
        
        log.info("Beginning link check on " + entries.size() + " entries");
        int counter = 0;
        for (Entry entry : entries)
        {
            counter ++;
            if (counter > 30) { break; } // harner debug only!

            String checkingNum = "entry " + counter + "/" + entries.size() + ": "; 
            for (String urlProperty : urlProperties)
            {
                String url = (String) entry.readProperty(urlProperty);
                if (url != null && ! url.trim().equals(""))
                {
                    log.debug("Checking " + checkingNum + urlProperty + " from " + entry.getName() + ": " + url);
                    HttpResult result = checkLink(url);
                    if (result.getStatus() < 200 || result.getStatus() >= 400)
                    {
                        log.info("Broken " + urlProperty + " in " + entry.getName() + ": "
                                + url + " [" + result.getStatus() + " " + result.getMessage() + "]");
                        brokenLinks.add(new BrokenLink(entry, urlProperty, result.getStatus(), result.getMessage()));
                    }
                }
            }
        }
        return brokenLinks;
    }

    /**
     * Validate that the given URL can be reached
     * 
     * @param url the URL to check
     * @return an HttpResult object containing the HTTP status code
     *         (HttpURLConnection.HTTP_OK (200) is normal) and the
     *         corresponding status/error message
     */
    private HttpResult checkLink(String targetUrl)
    {
        HttpURLConnection connection;
        try
        {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Apache Tapestry Component World Link Checker, bobharner@apache.org");

            // NOTE: we're using HEAD, not GET, for better performance
            connection.setRequestMethod("HEAD");
            connection.setInstanceFollowRedirects(true);

            // make the request and get the result 
            return new HttpResult(connection.getResponseCode(), connection.getResponseMessage());
        }
        catch (MalformedURLException e)
        {
            return new HttpResult(0, e.getMessage());
        }
        catch (IOException e)
        {
            return new HttpResult(0, e.getMessage());
        }
    }

}