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
package org.primefaces.component.tabmenu;

import org.primefaces.component.menu.AbstractMenu;
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
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "tabMenu",
             description = "TabMenu is a menu component that displays menuitems as tabs.",
             widget = true,
             rtl = true)
public abstract class TabMenuCore extends AbstractMenu implements ITabMenu {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "MenuModel instance to create menus programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Style of main container element")
		style,
		@PFProperty(description = "Style class of main container")
		styleClass,
		@PFProperty(description = "Index of the active tab. Default is 0", defaultValue = "0", type = Integer.class)
		activeIndex,;
	}


    public static final String CONTAINER_CLASS = "ui-tabmenu ui-widget ui-widget-content ui-corner-all";
    public static final String NAVIGATOR_CLASS = "ui-tabmenu-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all";
    public static final String INACTIVE_TAB_HEADER_CLASS = "ui-tabmenuitem ui-state-default ui-corner-top";
    public static final String ACTIVE_TAB_HEADER_CLASS = "ui-tabmenuitem ui-state-default ui-state-active ui-corner-top";

    public static final String MOBILE_CONTAINER_CLASS = "ui-tabmenu ui-navbar";
    public static final String MOBILE_INACTIVE_TAB_HEADER_CLASS = "ui-link ui-btn";
    public static final String MOBILE_ACTIVE_TAB_HEADER_CLASS = "ui-link ui-btn ui-btn-active";
}