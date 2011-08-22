package org.apache.tapestry.finder.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

/**
 * DIV component that uses JavaScript to ensure the element stays within the
 * viewable part of the browser window.  As the user scrolls down, if the
 * top of this element would go off the top of the browser window AND the bottom
 * of this element is above the bottom of the browser window, then this
 * element is moved down. As the user scrolls up, the opposite occurs, to
 * ensure that the element stays at least partly in view and that the user can
 * always scroll up or down to see more of this element if the element is
 * taller than the current browser window.
 * 
 * This component renders its body.
 * 
 */
@Import(library = {"context:js/keepinview.js"})
public class KeepInView
{

}
