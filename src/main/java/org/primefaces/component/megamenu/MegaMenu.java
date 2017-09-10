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
package org.primefaces.component.megamenu;

import org.primefaces.component.menu.AbstractUIMenu;
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
@PFComponent(tagName = "megaMenu",
             description = "MegaMenu is a navigation component that displays submenus together.",
             widget = true,
             parent = AbstractUIMenu.class)
public class MegaMenu extends AbstractMegaMenu {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "MenuModel instance to create menus programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "Defines whether submenus will be displayed on mouseover or not. When\n set to false, click event is required to display", defaultValue = "true", type = Boolean.class)
		autoDisplay,
		@PFProperty(description = "Index of the active root menu to display as highlighted. By default no root is highlighted", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		activeIndex,
		@PFProperty(description = "Defines the orientation of the root menuitems, valid values are \"horizontal\" (default) and \"vertical\"", defaultValue = "horizontal")
		orientation,;
	}

    public static final String CONTAINER_CLASS = "ui-menu ui-menubar ui-megamenu ui-widget ui-widget-content ui-corner-all ui-helper-clearfix";
    public static final String VERTICAL_CLASS = "ui-megamenu-vertical";
}