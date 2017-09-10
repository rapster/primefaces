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
package org.primefaces.component.contextmenu;

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
@PFComponent(tagName = "contextMenu",
             description = "ContextMenu provides an overlay menu displayed on mouse right-click event.",
             widget = true,
             parent = AbstractUIMenu.class)
public class ContextMenu extends AbstractContextMenu {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Id of the component to attach to")
		forValue("for"),
		@PFProperty(description = "Style of the main container element")
		style,
		@PFProperty(description = "Style class of the main container element")
		styleClass,
		@PFProperty(description = "Menu model instance to create menu programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Type of tree nodes to get attached")
		nodeType,
		@PFProperty(description = "Event to bind the contextmenu")
		event,
		@PFProperty(description = "Client side callback to execute before context menu is shown")
		beforeShow,
		@PFProperty(description = "Defines the selection behavior. Valid values are \"single\" and \"multiple\" (default)", defaultValue = "multiple")
		selectionMode,
		@PFProperty(description = "Selector to filter the elements to attach the menu")
		targetFilter,;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return toString != null ? toString : super.toString();
		}
	}


    public static final String CONTAINER_CLASS = "ui-menu ui-menu-dynamic ui-contextmenu ui-widget ui-widget-content ui-corner-all ui-helper-clearfix ui-shadow";
}