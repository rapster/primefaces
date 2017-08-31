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
package org.primefaces.component.dock;

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
	@ResourceDependency(library="primefaces", name="dock/dock.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="dock/dock.js")
})
@PFComponent(tagName = "dock",
             description = "Dock component mimics the well known dock interface of Mac OS X.",
             widget = true,
             rtl = true)
public abstract class DockCore extends AbstractMenu implements IDock {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "MenuModel instance to create menus programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Position of the dock, bottom or top. Default is bottom", defaultValue = "bottom")
		position,
		@PFProperty(description = "Initial width of items. Default is 40", defaultValue = "40", type = Integer.class)
		itemWidth,
		@PFProperty(description = "Maximum width of items. Default is 50", defaultValue = "50", type = Integer.class)
		maxWidth,
		@PFProperty(description = "Distance to enlarge. Default is 90", defaultValue = "90", type = Integer.class)
		proximity,
		@PFProperty(description = "Horizontal alignment. Default is center", defaultValue = "center")
		halign,;
	}

}