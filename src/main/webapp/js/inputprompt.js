// Observe a text input field, inserting an "input prompt" when
// it is empty and does not have focus. Written in Prototype style because
// Tapestry includes the Prototype library (http://www.prototypejs.org/).
//
// Adapted from http://jumpstart.doublenegative.com.au/jumpstart/examples/javascript/mixin

InputPrompt = Class.create( {

	initialize : function(textboxId, promptText, promptColor) {
		this.textbox = $(textboxId);
		this.promptText = promptText;
		this.promptColor = promptColor;
		this.normalColor = this.textbox.getStyle('color');

		Event.observe(this.textbox, 'focus', this.doClearPrompt.bindAsEventListener(this));
		Event.observe(this.textbox, 'blur', this.doCheckPrompt.bindAsEventListener(this));
		Event.observe(this.textbox, 'change', this.doCheckPrompt.bindAsEventListener(this));
		Event.observe(this.textbox.form, 'submit', this.doClearPrompt.bindAsEventListener(this));
		
		this.doCheckPrompt();
	},
	
	doClearPrompt : function(e) {
		if (this.textbox.value == this.promptText) {
			this.textbox.value = "";
		}
		this.textbox.setStyle({color: this.normalColor});
	},

	doCheckPrompt : function(e) {

		// If field is empty, put the promptText in it and set its color
		if (this.textbox.value.length == 0) {
			this.textbox.value = this.promptText;
			this.textbox.setStyle({color: this.promptColor});
		}
		// Else if field contains promptText, set its color to promptColor
		else if (this.textbox.value == this.promptText) {
			this.textbox.setStyle({color: this.promptColor});
		}
		// Else, set the field's color to its normal color
		else {
			this.textbox.setStyle({color: this.normalColor});
		}
	}
} )

// Extend Tapestry.Initializer with static method that instantiates an InputPrompt

Tapestry.Initializer.InputPrompt = function(spec) {
	new InputPrompt(spec.textboxId, spec.promptText, spec.promptColor);
}