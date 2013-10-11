// A Require.js module that handles events related to filtering the entries on
// the home page

define(["t5/core/dom"], function(t5) {
	return function(selectClientId) {
		// event handler for "entry type" selection menu
		t5(selectClientId).on('change', function(e) {
			console.log("found entryType menu changed");
			var form = this.findParent("form");
			return form.trigger("submit");
		});
		// event handler for "source type" radio buttons
		t5("checkall").on('change', function(e) {
			console.log("found sourceType radio changed");
			var form = this.findParent("form");
			return form.trigger("submit");
		});
	};
});