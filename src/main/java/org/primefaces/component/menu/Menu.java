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
package org.primefaces.component.menu;

import org.primefaces.component.menu.AbstractUIMenu;
import org.primefaces.component.menu.OverlayMenu;
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

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "menu",
             description = "Menu is a navigation component with various customized modes like multi tiers, ipod style sliding and overlays.",
             widget = true,
             parent = AbstractUIMenu.class)
public class Menu extends AbstractMenu implements OverlayMenu {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "A menu model instance to create menu programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Id of component whose click event will show the dynamic positioned menu")
		trigger,
		@PFProperty(description = "Corner of menu to align with trigger element")
		my,
		@PFProperty(description = "Corner of trigger to align with menu element")
		at,
		@PFProperty(description = "Defines positioning, when enabled menu is displayed with absolute positioning relative to the trigger. \n", defaultValue = "false", type = Boolean.class)
		overlay,
		@PFProperty(description = "Inline style of the main container element")
		style,
		@PFProperty(description = "Style class of the main container element")
		styleClass,
		@PFProperty(description = "Event name of component that will show the dynamic positioned menu", defaultValue = "click")
		triggerEvent,
		@PFProperty(description = "Defines whether clicking the header of a submenu toggles the visibility of children menuitems", defaultValue = "false", type = Boolean.class)
		toggleable,;
	}


    public static final String STATIC_CONTAINER_CLASS = "ui-menu ui-widget ui-widget-content ui-corner-all ui-helper-clearfix";
    public static final String DYNAMIC_CONTAINER_CLASS = "ui-menu ui-menu-dynamic ui-widget ui-widget-content ui-corner-all ui-helper-clearfix ui-shadow";
    public static final String SUBMENU_TITLE_CLASS = "ui-widget-header ui-corner-all";
    public static final String EXPANDED_SUBMENU_HEADER_ICON_CLASS = "ui-icon ui-icon-triangle-1-s";
    public static final String COLLAPSED_SUBMENU_HEADER_ICON_CLASS = "ui-icon ui-icon-triangle-1-e";
    public static final String TOGGLEABLE_MENU_CLASS = "ui-menu-toggleable";
    public static final String SUBMENU_CHILD_CLASS = "ui-submenu-child";

    public static final String MOBILE_CONTAINER_CLASS = "ui-menu ui-listview";
    public static final String MOBILE_DIVIDER_CLASS = "ui-li-divider ui-bar-inherit";
}