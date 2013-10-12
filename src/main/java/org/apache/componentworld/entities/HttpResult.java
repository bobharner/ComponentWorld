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
package org.apache.componentworld.entities;

import java.io.Serializable;

/**
 * A simple data object representing the result of an HTTP request
 */
public class HttpResult implements Serializable
{
    private static final long serialVersionUID = 3724269616148911173L;

    private int status;
    private String message;

    public HttpResult(int status, String message)
    {
        this.status = status;
        this.message = message; 
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

    /**
     * Get the response message (e.g. "OK" or "Not Found)
     * @return the response message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Set the response message (e.g. "OK" or "Not Found) 
     * @param message the response message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
