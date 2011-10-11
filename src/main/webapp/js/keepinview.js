/**
 * JavaScript support for KeepInView component
 * 
 * Note: see http://stackoverflow.com/questions/3496776/keeping-a-page-element-in-view-while-scrolling-page
 * for a JQuery implementation of this
 */
  Event.observe(window, 'scroll', function(event){
  //alert('I have been scrolled!');
});
////////////////////////
  
  $(function() {
//	    var msie6 = Prototype.Browser.IE && $.browser.version < 7;
// 		// TODO: convert the following JQuery code to Prototype
//	    if (!msie6) {
	        var top = $('#keepinview').offset().top
	                - parseFloat($('#scroll_header').css('margin-top').replace(
	                        /auto/, 0));
	        $(window).scroll(function(event) {
	            var y = $(this).scrollTop();
	            if (y >= top) {
	                $('#scroll_header').addClass('fixed');
	            } else {
	                $('#scroll_header').removeClass('fixed');
	            }
	        });
	        var y = $(this).scrollTop();
	        if (y >= top) {
	            $('#scroll_header').addClass('fixed');
	        } else {
	            $('#scroll_header').removeClass('fixed');
	        }
//	    } else {
//	        setInterval("checkScroll()", 100);
//	    }
	});
//	function checkScroll() {
//	    ie6top = $('#scroll_header_wrapper').offset().top;
//	    if ($(document).scrollTop() > ie6top) {
//	        $('#scroll_header').css("top", $(document).scrollTop() - ie6top + "px");
//	        $('#scroll_header').css("visibility", "visible");
//	    } else {
//	        $('#scroll_header').css("visibility", "hidden");
//	}
