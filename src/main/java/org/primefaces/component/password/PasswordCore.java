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
package org.primefaces.component.password;

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
import javax.faces.component.UIComponent;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.util.MessageFactory;
import org.primefaces.util.ComponentUtils;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "password",
             description = "Password component is an extended version of standard inputSecret component with theme integration and strength indicator.",
             widget = true,
             rtl = true)
public abstract class PasswordCore extends HtmlInputText implements IPassword {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Enables strength indicator. Default is false", defaultValue = "false", type = Boolean.class)
		feedback,
		@PFProperty(description = "Displays feedback inline rather than using a popup. Default is false", defaultValue = "false", type = Boolean.class)
		inline,
		@PFProperty(description = "Label of prompt. Default is \"Please enter a password\"", defaultValue = "Please enter a password")
		promptLabel,
		@PFProperty(description = "Label of weak password. Default is \"Weak\"", defaultValue = "Weak")
		weakLabel,
		@PFProperty(description = "Label of good password. Default is \"Good\"", defaultValue = "Good")
		goodLabel,
		@PFProperty(description = "Label of strong password. Default is \"Strong\"", defaultValue = "Strong")
		strongLabel,
		@PFProperty(description = "Boolean flag indicating whether or not a previously entered password should be rendered in form. Default is false", defaultValue = "false", type = Boolean.class)
		redisplay,
		@PFProperty(description = "Identifier of another password component to match value against")
		match,;
	}



    public final static String STYLE_CLASS = "ui-inputfield ui-password ui-widget ui-state-default ui-corner-all";
    public final static String MOBILE_STYLE_CLASS = "ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset ui-input-has-clear";
    public final static String MOBILE_CLEAR_ICON_CLASS = "ui-input-clear ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all ui-input-clear-hidden";
    public final static String INVALID_MATCH_KEY = "primefaces.password.INVALID_MATCH";

    @Override
	protected void validateValue(FacesContext context, Object value) {
		super.validateValue(context, value);
        String match = this.getMatch();
        Object submittedValue = this.getSubmittedValue();

        if(isValid() && !ComponentUtils.isValueBlank(match)) {
        	Password matchWith = (Password) SearchExpressionFacade.resolveComponent(context, this, match);

            if(submittedValue != null && !submittedValue.equals(matchWith.getSubmittedValue())) {
                this.setValid(false);
                matchWith.setValid(false);

                String validatorMessage = getValidatorMessage();
                FacesMessage msg = null;

                if(validatorMessage != null) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessage, validatorMessage);
                }
                else {
                    Object[] params = new Object[2];
                    params[0] = MessageFactory.getLabel(context, this);
                    params[1] = MessageFactory.getLabel(context, matchWith);

                    msg = MessageFactory.getMessage(Password.INVALID_MATCH_KEY, FacesMessage.SEVERITY_ERROR, params);
                }

                context.addMessage(getClientId(context), msg);
            }
        }
	}
}