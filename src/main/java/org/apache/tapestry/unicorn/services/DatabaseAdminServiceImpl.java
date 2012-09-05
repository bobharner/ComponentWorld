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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SQLTemplate;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.tapestry.unicorn.entities.Entry;
import org.apache.tapestry.unicorn.entities.EntryType;
import org.apache.tapestry.unicorn.entities.SourceType;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class DatabaseAdminServiceImpl implements DatabaseAdminService
{
	
	@Inject
	Logger logger;

	@Override
	public Boolean performBackup()
	{
		DataContext dataContext = DataContext.createDataContext();
		
		File backupDir = new File(System.getProperty("java.io.tmpdir"), "dbbackups");

		String query = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE('%DIR%')".replace(
				"%DIR%", backupDir.toString());

		SQLTemplate template = new SQLTemplate(Entry.class, query);
		dataContext.performNonSelectingQuery(template);	
		logger.info ("Database backed up to " + backupDir);
		
		return true;
	}
}