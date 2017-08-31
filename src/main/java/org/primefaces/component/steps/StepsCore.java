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
package org.primefaces.component.steps;

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
@PFComponent(tagName = "steps",
             description = "Steps is a menu component that displays steps of a workflow.",
             widget = true,
             rtl = true)
public abstract class StepsCore extends AbstractMenu implements ISteps {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "MenuModel instance to create menus programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Style of main container element")
		style,
		@PFProperty(description = "Style class of main container")
		styleClass,
		@PFProperty(description = "Index of the active tab. Default is 0", defaultValue = "0", type = Integer.class)
		activeIndex,
		@PFProperty(description = "Defines whether items would be clickable or not. Default is true", defaultValue = "true", type = Boolean.class)
		readonly,;
	}


    public static final String CONTAINER_CLASS = "ui-steps ui-widget ui-helper-clearfix";
    public static final String READONLY_CONTAINER_CLASS = "ui-steps ui-steps-readonly ui-widget ui-helper-clearfix";
    public static final String INACTIVE_ITEM_CLASS = "ui-steps-item ui-state-default ui-state-disabled ui-corner-all";
    public static final String ACTIVE_ITEM_CLASS = "ui-steps-item ui-state-highlight ui-corner-all";
    public static final String VISITED_ITEM_CLASS = "ui-steps-item ui-state-default ui-corner-all";
    public static final String STEP_NUMBER_CLASS = "ui-steps-number";
    public static final String STEP_TITLE_CLASS = "ui-steps-title";
}