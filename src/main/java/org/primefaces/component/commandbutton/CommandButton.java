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
package org.primefaces.component.commandbutton;

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
import java.util.logging.Logger;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.Collections;
import java.util.Collection;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import org.primefaces.component.button.Button;
import org.primefaces.event.data.PageEvent;
import org.primefaces.util.Constants;
import org.primefaces.event.SelectEvent;
import org.primefaces.util.ComponentUtils;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "commandButton",
             description = "CommandButton is an extended version of standard JSF commandButton with ajax and skinning features.",
             widget = true,
             parent = HtmlCommandButton.class)
public class CommandButton extends AbstractCommandButton implements org.primefaces.component.api.AjaxSource, org.primefaces.component.api.Confirmable, org.primefaces.component.api.PrimeClientBehaviorHolder {

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
		@PFProperty(description = "When enabled, only values related to partially processed components would be serialized for ajax\n instead of whole form", defaultValue = "false", type = Boolean.class)
		partialSubmit,
		@PFProperty(description = "If true, indicate that this particular Ajax transaction is a value reset transaction. This will cause resetValue() to be called on any EditableValueHolder instances encountered as a result of this ajax transaction. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		resetValues,
		@PFProperty(description = "If true, components which autoUpdate=\"true\" will not be updated for this request. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		ignoreAutoUpdate,
		@PFProperty(description = "Icon of the button")
		icon,
		@PFProperty(description = "Position of the icon, default value is left", defaultValue = "left")
		iconPos,
		@PFProperty(description = "Displays button inline instead of fitting the content width, only used by mobile", defaultValue = "false", type = Boolean.class)
		inline,
		@PFProperty(description = "Defines if label of the component is escaped or not", defaultValue = "true", type = Boolean.class)
		escape,
		@PFProperty(description = "When set to true client side validation is enabled, global setting is required to be enabled as a prerequisite", defaultValue = "false", type = Boolean.class)
		validateClient,
		@PFProperty(description = "Selector to use when partial submit is on")
		partialSubmitFilter,
		@PFProperty(description = "Form to serialize for an ajax request")
		form,
		@PFProperty(description = "When enabled, click event can be added to disabled button", defaultValue = "true", type = Boolean.class)
		renderDisabledClick,;
	}


    private final static Logger logger = Logger.getLogger(CommandButton.class.getName());

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("click", null);
        put("dialogReturn", SelectEvent.class);
    }});

    private static final Collection<String> EVENT_NAMES = BEHAVIOR_EVENT_MAPPING.keySet();

    @Override
    public Map<String, Class<? extends BehaviorEvent>> getBehaviorEventMapping() {
         return BEHAVIOR_EVENT_MAPPING;
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public String getDefaultEventName() {
        return "click";
    }
    
    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(event instanceof AjaxBehaviorEvent) {
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);

            if(eventName.equals("dialogReturn")) {
                AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
                Map<String,Object> session = context.getExternalContext().getSessionMap();
                String dcid = params.get(this.getClientId(context) + "_pfdlgcid");
                Object selectedValue = session.get(dcid);
                session.remove(dcid);
        
                event = new SelectEvent(this, behaviorEvent.getBehavior(), selectedValue);
                super.queueEvent(event);
            }
            else if(eventName.equals("click")) {
                super.queueEvent(event);
            }
        } 
        else {
            super.queueEvent(event);
        }
    }
            
    public String resolveIcon() {
        String icon = getIcon();
    
        if(icon == null) {
            icon = getImage();
            
            if(icon != null)
                logger.info("image attribute is deprecated to define an icon, use icon attribute instead.");
        }
    
        return icon;
    }

    public String resolveStyleClass() {
        String icon = resolveIcon();
        Object value = getValue();
        String styleClass = ""; 
    
        if(value != null && ComponentUtils.isValueBlank(icon)) {
            styleClass = HTML.BUTTON_TEXT_ONLY_BUTTON_CLASS;
        }
        else if(value != null && !ComponentUtils.isValueBlank(icon)) {
            styleClass = getIconPos().equals("left") ? HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS : HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS;
        }
        else if(value == null && !ComponentUtils.isValueBlank(icon)) {
            styleClass = HTML.BUTTON_ICON_ONLY_BUTTON_CLASS;
        }
    
        if(isDisabled()) {
            styleClass = styleClass + " ui-state-disabled";
        } 
    
        String userStyleClass = getStyleClass();
        if(userStyleClass != null) {
            styleClass = styleClass + " " + userStyleClass;
        }
    
        return styleClass;
    }
    
    public String resolveMobileStyleClass() {
        String icon = getIcon();
        String iconPos = getIconPos();
        Object value = getValue();
        String styleClass = "ui-btn ui-shadow ui-corner-all";
            
        if(value != null && icon != null) {
            styleClass = styleClass + " " + icon + " ui-btn-icon-" + iconPos;
        } else if(value == null && icon != null) {
            styleClass = styleClass + " " + icon + " ui-btn-icon-notext";
        }
    
        if(isDisabled()) {
            styleClass = styleClass + " ui-state-disabled";
        } 
    
        String userStyleClass = getStyleClass();
        if(userStyleClass != null) {
            styleClass = styleClass + " " + userStyleClass;
        }
    
        return styleClass;
    }
    
    public boolean isPartialSubmitSet() {
        return (getStateHelper().get(PropertyKeys.partialSubmit) != null) || (this.getValueExpression(PropertyKeys.partialSubmit.toString()) != null);
    }
    
    public boolean isResetValuesSet() {
        return (getStateHelper().get(PropertyKeys.resetValues) != null) || (this.getValueExpression(PropertyKeys.resetValues.toString()) != null);
    }
    
    private String confirmationScript;
    
    public String getConfirmationScript() {
        return this.confirmationScript;
    }
    public void setConfirmationScript(String confirmationScript) {
        this.confirmationScript = confirmationScript;
    }
    public boolean requiresConfirmation() {
        return this.confirmationScript != null;
    }
    
    public boolean isAjaxified() {
        return !getType().equals("reset") && !getType().equals("button") && isAjax();
    }
}