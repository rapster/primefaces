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
package org.primefaces.component.spinner;

import javax.faces.component.html.HtmlInputText;
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
@PFComponent(tagName = "spinner",
             description = "Spinner is an input component to provide a numerical input via increment and decrement buttons.",
             widget = true,
             rtl = true,
             parent = HtmlInputText.class)
public class Spinner extends AbstractSpinner implements org.primefaces.component.api.InputHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Stepping factor for each increment and decrement. Default is 1.0", defaultValue = "1.0", type = Double.class)
		stepFactor,
		@PFProperty(description = "Minimum boundary value. Default is min double value", defaultValue = "java.lang.Double.MIN_VALUE", type = Double.class)
		min,
		@PFProperty(description = "Maximum boundary value. Default is max double value", defaultValue = "java.lang.Double.MAX_VALUE", type = Double.class)
		max,
		@PFProperty(description = "Prefix of the input")
		prefix,
		@PFProperty(description = "Suffix of the input")
		suffix,;
	}


    public final static String CONTAINER_CLASS = "ui-spinner ui-widget ui-corner-all";
    public final static String INPUT_CLASS = "ui-spinner-input ui-inputfield ui-state-default ui-corner-all";
    public final static String UP_BUTTON_CLASS = "ui-spinner-button ui-spinner-up ui-corner-tr ui-button ui-widget ui-state-default ui-button-text-only";
    public final static String DOWN_BUTTON_CLASS = "ui-spinner-button ui-spinner-down ui-corner-br ui-button ui-widget ui-state-default ui-button-text-only";
    public final static String UP_ICON_CLASS = "ui-icon ui-icon-triangle-1-n ui-c";
    public final static String DOWN_ICON_CLASS = "ui-icon ui-icon-triangle-1-s ui-c";

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
}