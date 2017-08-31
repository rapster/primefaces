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
package org.primefaces.component.menubutton;

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
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "menuButton",
             description = "MenuButton displays different commands in a popup menu.",
             widget = true,
             rtl = true)
public abstract class MenuButtonCore extends AbstractMenu implements IMenuButton {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "MenuModel instance to create menus programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Label of the button")
		value,
		@PFProperty(description = "Style of the main container element")
		style,
		@PFProperty(description = "Style class of the main container element")
		styleClass,
		@PFProperty(description = "Disables or enables the button", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Icon of the menu button")
		icon,
		@PFProperty(description = "Position of the icon, default value is left", defaultValue = "left")
		iconPos,
		@PFProperty(description = "Appends the overlay to the element defined by search expression. Defaults to document body")
		appendTo,
		@PFProperty(description = "Style class of the overlay menu element")
		menuStyleClass,;
	}

    
    public final static String CONTAINER_CLASS = "ui-menubutton";
    public final static String ICON_CLASS = "ui-icon-triangle-1-s";
}