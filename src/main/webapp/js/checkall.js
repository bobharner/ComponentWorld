/**
 * JavaScript support for CheckAll component
 *
 * Select or unselect all checkboxes in a list. The controlled checkboxes must
 * match the given CSS selector (e.g., if selector is ".something input"
 * then all the checkboxes must be in a div or other container having the CSS
 * class of "something"). If isChecked is true, we unselect all checkboxes; if
 * false, we select them all.
 */
function checkall(selector, isChecked) {

	// Get list of all selected items (we just assume they're checkboxes).
	var listItems = $$(selector);
	alert ("found " + listItems.length);

	var setTo = ! isChecked;
	// loop over list items
	var count = listItems.length;
	for (i = 0; i < count; i++) {
		listItems[i].checked = setTo;
	}
}