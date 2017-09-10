/**
 * Copyright 2009-2017 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.component.carousel;

import org.primefaces.component.api.UIData;
import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.el.MethodExpression;
import javax.faces.render.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.List;
import java.util.ArrayList;
import org.primefaces.util.ComponentUtils;
import org.primefaces.cdk.annotations.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="touch/touchswipe.js")
})
@PFComponent(tagName = "carousel",
             description = "Carousel is a multi purpose component to display a set of data or general content with slide effects.",
             widget = true,
             parent = UIData.class)
public class Carousel extends AbstractCarousel {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIDataPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Index of the first visible item", defaultValue = "0", type = Integer.class)
		firstVisible,
		@PFProperty(description = "Number of visible items per page", defaultValue = "3", type = Integer.class)
		numVisible,
		@PFProperty(description = "Sets continuous scrolling", defaultValue = "false", type = Boolean.class)
		circular,
		@PFProperty(description = "Sets vertical scrolling", defaultValue = "false", type = Boolean.class)
		vertical,
		@PFProperty(description = "Sets the time in milliseconds to have Carousel start scrolling automatically after being initialized", defaultValue = "0", type = Integer.class)
		autoPlayInterval,
		@PFProperty(description = "Defines number of pageLinks of paginator", defaultValue = "3", type = Integer.class)
		pageLinks,
		@PFProperty(description = "Name of the animation effect slide or fade")
		effect,
		@PFProperty(description = "Name of the animation effect")
		easing,
		@PFProperty(description = "Sets the speed of the scrolling animation in milliseconds", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		effectDuration,
		@PFProperty(description = "Text format of the pager dropdown elements", defaultValue = "{page}")
		dropdownTemplate,
		@PFProperty(description = "Inline style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "Inline style of each item container")
		itemStyle,
		@PFProperty(description = "Style class of each item container")
		itemStyleClass,
		@PFProperty(description = "Shortcut for header facet")
		headerText,
		@PFProperty(description = "Shortcut for footer facet")
		footerText,
		@PFProperty(description = "In responsive mode, carousel adjusts its content based on screen size", defaultValue = "false", type = Boolean.class)
		responsive,
		@PFProperty(description = "Breakpoint value in pixels to switch between small and large viewport", defaultValue = "640", type = Integer.class)
		breakpoint,
		@PFProperty(description = "Makes panel toggleable", defaultValue = "false", type = Boolean.class)
		toggleable,
		@PFProperty(description = "Speed of toggling in milliseconds", defaultValue = "500", type = Integer.class)
		toggleSpeed,
		@PFProperty(description = "Renders a toggleable panel as collapsed", defaultValue = "false", type = Boolean.class)
		collapsed,
		@PFProperty(description = "When enabled, carousel state is saved in a cookie for the session", defaultValue = "false", type = Boolean.class)
		stateful,;
	}


    public final static String CONTAINER_CLASS = "ui-carousel ui-widget ui-widget-content ui-corner-all ui-hidden-container";
    public final static String ITEM_CLASS = "ui-carousel-item ui-widget-content ui-corner-all";
    public final static String HEADER_CLASS = "ui-carousel-header ui-widget-header ui-corner-all";
    public final static String HEADER_TITLE_CLASS = "ui-carousel-header-title";
    public final static String FOOTER_CLASS = "ui-carousel-footer ui-widget-header ui-corner-all";
    public final static String HORIZONTAL_NEXT_BUTTON = "ui-carousel-button ui-carousel-next-button ui-icon ui-icon-circle-triangle-e";
    public final static String HORIZONTAL_PREV_BUTTON = "ui-carousel-button ui-carousel-prev-button ui-icon ui-icon-circle-triangle-w";
    public final static String VERTICAL_NEXT_BUTTON = "ui-carousel-button ui-carousel-next-button ui-icon ui-icon-circle-triangle-s";
    public final static String VERTICAL_PREV_BUTTON = "ui-carousel-button ui-carousel-prev-button ui-icon ui-icon-circle-triangle-n";
    public final static String VIEWPORT_CLASS = "ui-carousel-viewport";
    public final static String ITEMS_CLASS = "ui-carousel-items";
    public final static String VERTICAL_VIEWPORT_CLASS = "ui-carousel-viewport ui-carousel-vertical-viewport";
    public final static String PAGE_LINKS_CONTAINER_CLASS = "ui-carousel-page-links";
    public final static String PAGE_LINK_CLASS = "ui-icon ui-carousel-page-link ui-icon-radio-off";
    public final static String DROPDOWN_CLASS = "ui-carousel-dropdown ui-widget ui-state-default ui-corner-left";
    public final static String MOBILE_DROPDOWN_CLASS = "ui-carousel-mobiledropdown ui-widget ui-state-default ui-corner-left";
    public final static String TOGGLER_LINK_CLASS = "ui-carousel-titlebar-icon ui-corner-all ui-state-default";

    private final static Logger logger = Logger.getLogger(Carousel.class.getName());
    
    public int getRenderedChildCount() {
        int i = 0;
    
        for(UIComponent child : getChildren()) {
            if(child.isRendered()) {
                i++;
            }
        }

        return i;
    }

    @Override
    public void setRows(int rows) {
        super.setRows(rows);
        this.setNumVisible(rows);
        
        logger.log(Level.WARNING, "rows is deprecated, use numVisible instead.");
    }
}