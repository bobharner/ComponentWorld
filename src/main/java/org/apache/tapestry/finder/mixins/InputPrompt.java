/**
 * This mixin uses JavaScript to observe a field, inserting prompt text
 * into it whenever it is empty and does not have focus.
 * 
 * This is from http://jumpstart.doublenegative.com.au/jumpstart/examples/javascript/mixin
 */

package org.apache.tapestry.finder.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "${context:/js/inputprompt.js")
public class InputPrompt
{
	// Required parameter: the text to insert as a prompt
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String promptText;

	// Optional parameter: color of the prompt
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String promptColor;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@InjectContainer
	private ClientElement clientElement;

	public void setupRender()
	{
		promptColor = "#808080";	// default text color for prompt
	}

	/**
	 * Tell the Tapestry.Initializer to set up the InputPrompt (after the
	 * DOM has been fully loaded). 
	 */
	@AfterRender
	public void addInitializer()
	{
		JSONObject spec = new JSONObject();
		spec.put("textboxId", clientElement.getClientId());
		spec.put("promptText", promptText);
		spec.put("promptColor", promptColor);
		javaScriptSupport.addInitializerCall("inputPrompt", spec);
	}

}