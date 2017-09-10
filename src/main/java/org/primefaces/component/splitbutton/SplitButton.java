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
package org.primefaces.component.splitbutton;

import javax.faces.component.html.HtmlCommandButton;
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
import org.primefaces.util.HTML;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "splitButton",
             description = "SplitButton displays a default command and additional ones in an overlay.",
             widget = true,
             parent = HtmlCommandButton.class)
public class SplitButton extends AbstractSplitButton implements org.primefaces.component.api.AjaxSource {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UICommandPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Specifies the submit mode, when set to true (default), submit would be made with Ajax", defaultValue = "true", type = Boolean.class)
		ajax,
		@PFProperty(description = "When set to true, ajax requests are not queued", defaultValue = "false", type = Boolean.class)
		async,
		@PFProperty(description = "Component(s) to process partially instead of whole view")
		process,
		@PFProperty(description = "Component(s) to be updated with ajax")
		update,
		@PFProperty(description = "Client side callback to execute before ajax request is begins")
		onstart,
		@PFProperty(description = "Client side callback to execute when ajax request is completed")
		oncomplete,
		@PFProperty(description = "Client side callback to execute when ajax request fails")
		onerror,
		@PFProperty(description = "Client side callback to execute when ajax request succeeds")
		onsuccess,
		@PFProperty(description = "Defines whether to trigger ajaxStatus or not", defaultValue = "true", type = Boolean.class)
		global,
		@PFProperty(description = "If less than delay milliseconds elapses between calls to request() only the most recent one is sent and all other requests are discarded. The default value of this option is null. If the value of delay is the literal string 'none' without the quotes or the default, no delay is used")
		delay,
		@PFProperty(description = "Defines the timeout for the ajax request", defaultValue = "0", type = Integer.class)
		timeout,
		@PFProperty(description = "Icon of the button")
		icon,
		@PFProperty(description = "Position of the icon, default value is left", defaultValue = "left")
		iconPos,
		@PFProperty(description = "Displays button inline instead of fitting the content width, only used by mobile", defaultValue = "false", type = Boolean.class)
		inline,
		@PFProperty(description = "When enabled, only values related to partially processed components would be serialized for ajax \n instead of whole form", defaultValue = "false", type = Boolean.class)
		partialSubmit,
		@PFProperty(description = "If true, indicate that this particular Ajax transaction is a value reset transaction. This will cause resetValue() to be called on any EditableValueHolder instances encountered as a result of this ajax transaction. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		resetValues,
		@PFProperty(description = "If true, components which autoUpdate=\"true\" will not be updated for this request. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		ignoreAutoUpdate,
		@PFProperty(description = "Appends the overlay to the element defined by search expression. Defaults to document body")
		appendTo,
		@PFProperty(description = "Selector to use when partial submit is on,")
		partialSubmitFilter,
		@PFProperty(description = "Style class of the overlay menu element")
		menuStyleClass,
		@PFProperty(description = "Form to serialize for an ajax request")
		form,;
	}


    public static final String STYLE_CLASS = "ui-splitbutton ui-buttonset ui-widget";
    public static final String BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-left ui-button-text-icon-left";
    public static final String BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-left ui-button-text-icon-right";
    public static final String MENU_ICON_BUTTON_CLASS = "ui-splitbutton-menubutton  ui-button ui-widget ui-state-default ui-corner-right ui-button-icon-only";
    public final static String BUTTON_TEXT_ONLY_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-left ui-button-text-only";
    public final static String BUTTON_ICON_ONLY_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-left ui-button-icon-only";

    public String resolveStyleClass() {
        String icon = getIcon();
        Object value = getValue();
        String styleClass = ""; 
    
        if(value != null && icon == null) {
            styleClass = BUTTON_TEXT_ONLY_BUTTON_CLASS;
        }
        else if(value != null && icon != null) {
            styleClass = getIconPos().equals("left") ? BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS : BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS;
        }
        else if(value == null && icon != null) {
            styleClass = BUTTON_ICON_ONLY_BUTTON_CLASS;
        }
    
        if(isDisabled()) {
            styleClass = styleClass + " ui-state-disabled";
        } 
        
        return styleClass;
    }

    public boolean isPartialSubmitSet() {
        return (getStateHelper().get(PropertyKeys.partialSubmit) != null) || (this.getValueExpression(PropertyKeys.partialSubmit.toString()) != null);
    }
    
    public boolean isResetValuesSet() {
        return (getStateHelper().get(PropertyKeys.resetValues) != null) || (this.getValueExpression(PropertyKeys.resetValues.toString()) != null);
    }
    
    public boolean isAjaxified() {
        return isAjax();
    }
}