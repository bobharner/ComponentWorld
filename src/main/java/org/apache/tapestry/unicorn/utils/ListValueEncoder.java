
package org.apache.tapestry.unicorn.utils;

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
