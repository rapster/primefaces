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
package org.primefaces.component.keyboard;

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
	@ResourceDependency(library="primefaces", name="keyboard/keyboard.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="keyboard/keyboard.js")
})
@PFComponent(tagName = "keyboard",
             description = "Keyboard is an input component that uses a virtual keyboard to provide the input. Notable features are the customizable layouts and skinning capabilities.",
             widget = true,
             rtl = true)
public abstract class KeyboardCore extends HtmlInputText implements IKeyboard {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Makes the input a password field. Default is false", defaultValue = "false", type = Boolean.class)
		password,
		@PFProperty(description = "Specifies the showMode, 'focus', 'button', 'both'", defaultValue = "focus")
		showMode,
		@PFProperty(description = "Image for the button")
		buttonImage,
		@PFProperty(description = "When set to true only image of the button would be displayed. Default is false", defaultValue = "false", type = Boolean.class)
		buttonImageOnly,
		@PFProperty(description = "Effect of the display animation. Default is fadeIn", defaultValue = "fadeIn")
		effect,
		@PFProperty(description = "Length of the display animation")
		effectDuration,
		@PFProperty(description = "Built-in layout of the keyboard. Default is qwerty", defaultValue = "qwerty")
		layout,
		@PFProperty(description = "Template of the custom layout")
		layoutTemplate,
		@PFProperty(description = "Specifies displaying a keypad instead of a keyboard. Default is false", defaultValue = "false", type = Boolean.class)
		keypadOnly,
		@PFProperty(description = "Label of the prompt text")
		promptLabel,
		@PFProperty(description = "Label of the close key")
		closeLabel,
		@PFProperty(description = "Label of the clear key")
		clearLabel,
		@PFProperty(description = "Label of the backspace key")
		backspaceLabel,;
	}


    public final static String STYLE_CLASS = "ui-inputfield ui-keyboard-input ui-widget ui-state-default ui-corner-all";
}