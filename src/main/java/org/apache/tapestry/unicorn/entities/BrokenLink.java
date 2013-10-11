/*
 * Copyright 2013 The Apache Software Foundation
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tapestry.unicorn.entities;

import java.io.Serializable;

/**
 * A simple data object representing a broken link in an Entry.
 */
public class BrokenLink implements Serializable
{
    private static final long serialVersionUID = 3724269616148911173L;

    private Entry entry;
    private String fieldName;
    private int status;
    private String message;

    public BrokenLink(Entry entry, String fieldName, int status, String message)
    {
        this.entry = entry;
        this.fieldName = fieldName;
        this.status = status;
        this.setMessage(message);
    }

    /**
     * Get the entry that had the broken link
     * @return the entry
     */
    public Entry getEntry()
    {
        return entry;
    }

    /**
     * Set the entry that had the broken link
     * @param entry the entry
     */
    public void setEntry(Entry entry)
    {
        this.entry = entry;
    }

    /**
     * Get the name of the field in the entry that contained the broken link 
     * @return the field name
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * Set the name of the field in the entry that contained the broken link 
     * @param fieldName the field name
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * Get the status of the HTTP request to the broken link. 
     * @return a standard HTTP status code See {@link java.net.HttpURLConnection}
     * for constants.
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Set the status of the HTTP request to the broken link
     * @param status the HTTP status code
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
