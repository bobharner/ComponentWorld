/**
 * JavaScript support for ListFilter component
 * 
 * Filter a list. List items must match the given CSS selector (e.g. if
 * itemSelector is ".something" then items must have the CSS class of
 * "something". Any text which matches
 * "%filterText%" will be left, any others will be hidden.
 */
function filterList(itemsSelector, filterText) {

	filterListWithCounter(itemsSelector, '', filterText)

}

/**
 * Same as filterList() except also displays number of matching items
 * inside the container element whose ID matches itemsCounter
 */
function filterListWithCounter(itemsSelector, itemsCounter, filterText) {

	// Get list of all list items.
	var listItems = jQuery(itemsSelector);

	var regex = new RegExp(filterText, "i");

	// loop over list items
	var count = listItems.length;
	var counter = 0;
	while (count--) {

		// Get the list item
		var listItem = listItems[count];

		// Apply REGEX to text
		if (listItem.innerHTML.match(regex)) {

			// If REGEX matches, show it.
			listItem.style.display = 'block';
			counter++;
		} else {
			// If REGEX doesn't match, hide it.
			listItem.style.display = 'none';
		}
	}

	/* insert the count into the element with the given ID */
	if (itemsCounter != '') {
		var elem = jQuery(itemsCounter);
		if (elem != null) {
			elem.innerHTML = counter;
		}
	}

}