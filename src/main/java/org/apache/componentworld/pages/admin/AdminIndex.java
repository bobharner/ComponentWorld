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
package org.apache.componentworld.pages.admin;

import org.apache.componentworld.services.DatabaseAdminService;
import org.apache.componentworld.services.EntryService;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class AdminIndex
{

	@Inject
	AlertManager alertManager;
	
	@Inject
	Logger log;
	
	@Inject
	EntryService entryService;
	
	@Inject
	DatabaseAdminService databaseAdminService;

	/**
	 * Perform a database backup
	 * @return
	 */
	Object onActionFromBackupLink()
	{
		if (databaseAdminService.performBackup())
		{
		    alertManager.success("Backup completed");
		}
		else
        {
            alertManager.error("Backup was unsuccessful");
        }
		return null;
	}

}
