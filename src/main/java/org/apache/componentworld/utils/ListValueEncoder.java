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

import java.util.List;

import org.apache.tapestry5.ValueEncoder;

/**
 * Generic Value Encoder for Lists. This lets you use any {@link java.util.List}
 * object as the source for a Tapestry select component.
 * 
 * The list items should be of a type that presents a nicely formatted
 * toString() method, since that will be what appears in the drop-down menu.
 * 
 * Adapted from examples by Thiago H. de Paula Figueiredo and Taha Hafeez, at
 * http://tapestry.1045711.n5.nabble.com/Enum-or-Drop-Down-td3394312.html#a3394448
 * 
 */
public class ListValueEncoder<T> implements ValueEncoder<T> { 
	final private List<T> values; 
	
	public ListValueEncoder(List<T> values){ 
		this.values = values;
	} 

	public String toClient(T value){ 
		return String.valueOf(values.indexOf(value)); 
	}

	public T toValue(String index){ 
		return values.get(Integer.parseInt(index)); 
	}
} 
