/** 
 * JavaScript support for CheckAll component
 * 
 * Select or unselect all checkboxes in a list. The controlled checkboxes must
 * match the given CSS selector (e.g., if selector is ".something input"
 * then all the checkboxes must be in a div or other container having the CSS
 * class of "something"). If isChecked is true, we unselect all checkboxes; if
 * false, we select them all.
 */
Tapestry.Initializer.checkAll = function(spec)
{
	// this is generated unique id of the checkAll component
	var checkAllComponent = $(spec.id);
	
	// Get list of all selected items (we assume they're checkboxes).
	var listItems = $$(spec.selector);
	
	checkAllComponent.observe('change', function(){
		var setTo = checkAllComponent.checked;
		// loop over list items
		var count = listItems.length;
		for (i = 0; i < count; i++) {
			if (listItems[i].type && listItems[i].type.toLowerCase() === 'checkbox')
				listItems[i].checked = setTo;
		}
	});
	
	// loop over list items and assign each checkbox 
	// an event handler for the change event
	var count = listItems.length;
	for (i = 0; i < count; i++) {
		listItems[i].observe('change', function(){
			if(this.checked == false)
				checkAllComponent.checked = false;
		});
	}
}