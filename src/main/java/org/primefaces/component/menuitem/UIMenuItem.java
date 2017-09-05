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
package org.primefaces.component.menuitem;

import javax.faces.component.UICommand;
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
import java.util.List;
import javax.faces.component.UIComponent;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.ActionEvent;
import javax.el.MethodExpression;
import javax.faces.component.UIParameter;
import org.primefaces.util.ComponentUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@PFComponent(tagName = "menuitem",
             description = "Menuitem is used by various menu components of PrimeFaces.",
             parent = UICommand.class)
public class UIMenuItem extends AbstractUIMenuItem implements org.primefaces.component.api.AjaxSource, org.primefaces.component.api.UIOutcomeTarget, org.primefaces.model.menu.MenuItem, org.primefaces.component.api.Confirmable, javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UICommandPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Url to be navigated when menuitem is clicked")
		url,
		@PFProperty(description = "Target type of url navigation")
		target,
		@PFProperty(description = "Style of the menuitem label")
		style,
		@PFProperty(description = "StyleClass of the menuitem label")
		styleClass,
		@PFProperty(description = "Javascript event handler for click event")
		onclick,
		@PFProperty(description = "Client side id of the component(s) to be updated after async partial submit request")
		update,
		@PFProperty(description = "Component id(s) to process partially instead of whole view")
		process,
		@PFProperty(description = "Javascript handler to execute before ajax request is begins")
		onstart,
		@PFProperty(description = "Disables or enables the menu item", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Javascript handler to execute when ajax request is completed")
		oncomplete,
		@PFProperty(description = "Javascript handler to execute when ajax request fails")
		onerror,
		@PFProperty(description = "Javascript handler to execute when ajax request succeeds")
		onsuccess,
		@PFProperty(description = "Global ajax requests are listened by ajaxStatus component, setting global to false will not trigger ajaxStatus", defaultValue = "true", type = Boolean.class)
		global,
		@PFProperty(description = "If less than delay milliseconds elapses between calls to request() only the most recent one is sent and all other requests are discarded. The default value of this option is null. If the value of delay is the literal string 'none' without the quotes or the default, no delay is used")
		delay,
		@PFProperty(description = "Defines the timeout for the ajax request", defaultValue = "0", type = Integer.class)
		timeout,
		@PFProperty(description = "When set to true, ajax requests are not queued", defaultValue = "false", type = Boolean.class)
		async,
		@PFProperty(description = "Specifies submit mode", defaultValue = "true", type = Boolean.class)
		ajax,
		@PFProperty(description = "Path of the menuitem image")
		icon,
		@PFProperty(description = "Position of the icon, default value is right", defaultValue = "right")
		iconPos,
		@PFProperty(description = "When enabled, only values related to partially processed components would be serialized for ajax\n instead of whole form", defaultValue = "false", type = Boolean.class)
		partialSubmit,
		@PFProperty(description = "If true, indicate that this particular Ajax transaction is a value reset transaction. This will cause resetValue() to be called on any EditableValueHolder instances encountered as a result of this ajax transaction. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		resetValues,
		@PFProperty(description = "If true, components which autoUpdate=\"true\" will not be updated for this request. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		ignoreAutoUpdate,
		@PFProperty(description = "Title text of the menuitem")
		title,
		@PFProperty(description = "Used to resolve a navigation case")
		outcome,
		@PFProperty(description = "Whether to include page parameters in target URI. Default is false", defaultValue = "false", type = Boolean.class)
		includeViewParams,
		@PFProperty(description = "Identifier of the target page which should be scrolled to")
		fragment,
		@PFProperty(description = "Disable appending the \n on the rendering of this element", defaultValue = "false", type = Boolean.class)
		disableClientWindow,
		@PFProperty(description = "Style of the menuitem container")
		containerStyle,
		@PFProperty(description = "StyleClass of the menuitem container")
		containerStyleClass,
		@PFProperty(description = "Selector to use when partial submit is on, default is \":input\" to select all descendant inputs of a partially processed components")
		partialSubmitFilter,
		@PFProperty(description = "Form to serialize for an ajax request. Default is the enclosing form")
		form,
		@PFProperty(description = "Defines whether value would be escaped or not, defaults to true", defaultValue = "true", type = Boolean.class)
		escape,
		@PFProperty(description = "Defines the relationship between the current document and the linked document")
		rel,;
	}


    private final static String DEFAULT_EVENT = "click";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("click", null);
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
        return DEFAULT_EVENT;
    }

	public void decode(FacesContext facesContext) {
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		String clientId = getClientId(facesContext);
		
		if(params.containsKey(clientId)) {
			this.queueEvent(new ActionEvent(this));
		}
	}
	
	public boolean shouldRenderChildren() {
		if(getChildCount() == 0)
			return false;
		else {
			for(UIComponent child : getChildren()) {
				if(! (child instanceof UIParameter) ) {
					return true;
				}
			}
		}
		
		return false;
	}

    public boolean isPartialSubmitSet() {
        return (getStateHelper().get(PropertyKeys.partialSubmit) != null) || (this.getValueExpression(PropertyKeys.partialSubmit.toString()) != null); 
    }
    
    public boolean isResetValuesSet() {
        return (getStateHelper().get(PropertyKeys.resetValues) != null) || (this.getValueExpression(PropertyKeys.resetValues.toString()) != null);
    }

    public String getHref() {
        return this.getUrl();
    }

    public boolean isDynamic() {
        return false;
    }

    public Map<String, List<String>> getParams() {
        return ComponentUtils.getUIParams(this);
    }

    public String getCommand() {
        MethodExpression expr = super.getActionExpression();
        return expr != null ? expr.getExpressionString() : null;
    }
    
    public boolean isAjaxified() {
    	return getUrl() == null && getOutcome() == null && isAjax();
    }

    public void setParam(String key, Object value) {
        throw new UnsupportedOperationException("Use UIParameter component instead to add parameters.");
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
}