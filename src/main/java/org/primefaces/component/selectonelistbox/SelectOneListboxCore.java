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
package org.primefaces.component.selectonelistbox;

import javax.faces.component.html.HtmlSelectOneListbox;
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
import javax.faces.component.UINamingContainer;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "selectOneListbox",
             description = "",
             widget = true,
             rtl = true)
public abstract class SelectOneListboxCore extends HtmlSelectOneListbox implements ISelectOneListbox, org.primefaces.component.api.InputHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Name of iterator to be used in custom content display")
		var,
		@PFProperty(description = "Displays an input filter for the list", defaultValue = "false", type = Boolean.class)
		filter,
		@PFProperty(description = "Match mode for filtering, valid values are startsWith (default), contains, endsWith and custom")
		filterMatchMode,
		@PFProperty(description = "Client side function to use in custom filterMatchMode")
		filterFunction,
		@PFProperty(description = "Defines if filtering would be case sensitive. Default is false", defaultValue = "false", type = Boolean.class)
		caseSensitive,
		@PFProperty(description = "Defines the height of the scrollable area", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		scrollHeight,;
	}


    public static final String CONTAINER_CLASS = "ui-selectonelistbox ui-inputfield ui-widget ui-widget-content ui-corner-all";
    public static final String LIST_CONTAINER_CLASS = "ui-selectlistbox-listcontainer";
    public static final String LIST_CLASS = "ui-selectlistbox-list";
    public static final String ITEM_CLASS = "ui-selectlistbox-item ui-corner-all";
    public final static String FILTER_CONTAINER_CLASS = "ui-selectlistbox-filter-container";
    public final static String FILTER_CLASS = "ui-selectlistbox-filter ui-inputfield ui-widget ui-state-default ui-corner-all";
    public final static String FILTER_ICON_CLASS = "ui-icon ui-icon-search";

    public String getInputClientId() {
        return this.getClientId(getFacesContext()) + "_input";
    }

    public String getValidatableInputClientId() {
        return this.getInputClientId();
    }

    public void setLabelledBy(String labelledBy) {
        getStateHelper().put("labelledby", labelledBy);
    }
    public String getLabelledBy() {
        return (String) getStateHelper().get("labelledby");
    }

    public static String[] DOM_EVENTS = {
		"onchange",
		"onclick",
        "ondblclick"
	};
}