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
package org.primefaces.component.inputnumber;

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
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="inputnumber/inputnumber.js"),
	@ResourceDependency(library="primefaces", name="inputnumber/inputnumber.css")
})
@PFComponent(tagName = "inputNumber",
             description = "InputNumber is an extension to the inputText with optimized handling for numbers.",
             widget = true,
             rtl = true)
public abstract class InputNumberCore extends HtmlInputText implements IInputNumber, org.primefaces.component.api.InputHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Input field type. Valid values are \"text\"(default), \"tel\" and \"hidden\"", defaultValue = "text")
		type,
		@PFProperty(description = "Desired symbol or unit. Default is none")
		symbol,
		@PFProperty(description = "Symbol suffix. Default is prefix")
		symbolPosition,
		@PFProperty(description = "Minimum values. Default is 0.00")
		minValue,
		@PFProperty(description = "Maximum values. Default is 999999999.99")
		maxValue,
		@PFProperty(description = "Controls the rounding method. Default is Round-Half-Up Symmetric")
		roundMethod,
		@PFProperty(description = "Number of decimal places. Default are taken from minValue and MaxValue")
		decimalPlaces,
		@PFProperty(description = "Controls empty input display behavior, options are empty, zero, sign. Default is empty", defaultValue = "empty")
		emptyValue,
		@PFProperty(description = "Inline style of the input element")
		inputStyle,
		@PFProperty(description = "Style class of the input element")
		inputStyleClass,
		@PFProperty(description = "Controls padding of the decimal places. If true, always pads the decimal with zeros", defaultValue = "true", type = Boolean.class)
		padControl,
		@PFProperty(description = "Controls leading zero behavior. Valid values are \"allow\"(default), \"deny\" and \"keep\"", defaultValue = "allow")
		leadingZero,;
	}


    public final static String STYLE_CLASS = "ui-inputnumber ui-widget";

    @Override
	public String getInputClientId() {
		return getClientId() + "_input";
	}

    @Override
    public String getValidatableInputClientId() {
        return getClientId() + "_hinput";
    }

    @Override
    public void setLabelledBy(String labelledBy) {
        getStateHelper().put("labelledby", labelledBy);
    }

    @Override
    public String getLabelledBy() {
        return (String) getStateHelper().get("labelledby");
    } 

    public String getDecimalSeparator() {
        return (String) getStateHelper().eval("decimalSeparator", getCalculatedDecimalSepartor());
    }

    public void setDecimalSeparator(final String decimalSeparator) {
        getStateHelper().put("decimalSeparator", decimalSeparator);
    }

    public String getThousandSeparator() {
        return (String) getStateHelper().eval("thousandSeparator", getCalculatedThousandSeparator());
    }

    public void setThousandSeparator(final String thousandSeparator) {
        getStateHelper().put("thousandSeparator", thousandSeparator);
    }

    private String getCalculatedDecimalSepartor(){
        String decimalSeparator = (String) getStateHelper().eval("decimalSeparator", null);
        if (decimalSeparator==null){
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
            decimalSeparator = Character.toString(decimalFormatSymbols.getDecimalSeparator());
        }
        return decimalSeparator;
    }

    private String getCalculatedThousandSeparator(){
        String thousandSeparator = (String) getStateHelper().eval("thousandSeparator", null);
        if (thousandSeparator==null){
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
            thousandSeparator =  Character.toString(decimalFormatSymbols.getGroupingSeparator());
        }
        return thousandSeparator;
    }

}