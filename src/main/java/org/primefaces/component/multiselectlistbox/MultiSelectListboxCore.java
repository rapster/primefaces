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
package org.primefaces.component.multiselectlistbox;

import javax.faces.component.UISelectOne;
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
@PFComponent(tagName = "multiSelectListbox",
             description = "MultiSelectListbox is used to select an item from a collection of listboxes that are in parent-child relationship.",
             widget = true,
             rtl = true)
public abstract class MultiSelectListboxCore extends UISelectOne implements IMultiSelectListbox, javax.faces.component.behavior.ClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "Disables the component", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Effect to use when showing a group of items")
		effect,
		@PFProperty(description = "Displays label of a group at header section of the children items. Defaults to false", defaultValue = "false", type = Boolean.class)
		showHeaders,
		@PFProperty(description = "Label of the root group items")
		header,;
	}

    public static String CONTAINER_CLASS = "ui-multiselectlistbox ui-widget ui-helper-clearfix";
    public static String LIST_CONTAINER_CLASS = "ui-multiselectlistbox-listcontainer";
    public static String LIST_HEADER_CLASS = "ui-multiselectlistbox-header ui-widget-header ui-corner-top";
    public static String LIST_CLASS = "ui-multiselectlistbox-list ui-inputfield ui-widget-content";
    public static String ITEM_CLASS = "ui-multiselectlistbox-item";
}