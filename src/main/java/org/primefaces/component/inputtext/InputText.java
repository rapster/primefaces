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
package org.primefaces.component.inputtext;

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
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "inputText",
             description = "InputText is an extension to standard inputText with skinning capabilities.",
             widget = true,
             rtl = true,
             parent = HtmlInputText.class)
public class InputText extends AbstractInputText {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Input field type. Default is text", defaultValue = "text")
		type,;
	}


    public final static String STYLE_CLASS = "ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all";
    public final static String MOBILE_STYLE_CLASS = "ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset ui-input-has-clear";
    public final static String MOBILE_SEARCH_STYLE_CLASS = "ui-input-search ui-body-inherit ui-corner-all ui-shadow-inset ui-input-has-clear";
    public final static String MOBILE_CLEAR_ICON_CLASS = "ui-input-clear ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all ui-input-clear-hidden";
}