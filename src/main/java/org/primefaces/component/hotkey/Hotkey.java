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
package org.primefaces.component.hotkey;

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

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="hotkey/hotkey.js")
})
@PFComponent(tagName = "hotkey",
             description = "HotKey is a generic key binding component that can bind any formation of keys to javascript event handlers or ajax calls.",
             parent = UICommand.class)
public class Hotkey extends AbstractHotkey implements org.primefaces.component.api.AjaxSource {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UICommandPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The Key binding. Required", required = true)
		bind,
		@PFProperty(description = "Client side id of the component(s) to be updated after async partial submit request")
		update,
		@PFProperty(description = "Component id(s) to process partially instead of whole view")
		process,
		@PFProperty(description = "Javascript event handler to be executed when the key binding is pressed")
		handler,
		@PFProperty(description = "Javascript handler to execute before ajax request is begins")
		onstart,
		@PFProperty(description = "Javascript handler to execute when ajax request is completed")
		oncomplete,
		@PFProperty(description = "Javascript handler to execute when ajax request fails")
		onerror,
		@PFProperty(description = "Javascript handler to execute when ajax request succeeds")
		onsuccess,
		@PFProperty(description = "Global ajax requests are listened by ajaxStatus component, setting global to false will not trigger ajaxStatus. Default is true", defaultValue = "true", type = Boolean.class)
		global,
		@PFProperty(description = "If less than delay milliseconds elapses between calls to request() only the most recent one is sent and all other requests are discarded. The default value of this option is null. If the value of delay is the literal string 'none' without the quotes or the default, no delay is used")
		delay,
		@PFProperty(description = "Defines the timeout for the ajax request", defaultValue = "0", type = Integer.class)
		timeout,
		@PFProperty(description = "When set to true, ajax requests are not queued. Default is false", defaultValue = "false", type = Boolean.class)
		async,
		@PFProperty(description = "When enabled, only values related to partially processed components would be serialized for ajax \n instead of whole form", defaultValue = "false", type = Boolean.class)
		partialSubmit,
		@PFProperty(description = "If true, indicate that this particular Ajax transaction is a value reset transaction. This will cause resetValue() to be called on any EditableValueHolder instances encountered as a result of this ajax transaction. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		resetValues,
		@PFProperty(description = "If true, components which autoUpdate=\"true\" will not be updated for this request. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		ignoreAutoUpdate,
		@PFProperty(description = "Selector to use when partial submit is on, default is \":input\" to select all descendant inputs of a partially processed components")
		partialSubmitFilter,
		@PFProperty(description = "Form to serialize for an ajax request. Default is the enclosing form")
		form,;
	}

    
    public boolean isPartialSubmitSet() {
        return (getStateHelper().get(PropertyKeys.partialSubmit) != null) || (this.getValueExpression(PropertyKeys.partialSubmit.toString()) != null);
    }
    
    public boolean isResetValuesSet() {
        return (getStateHelper().get(PropertyKeys.resetValues) != null) || (this.getValueExpression(PropertyKeys.resetValues.toString()) != null);
    }
    
    public boolean isAjaxified() {
        return getHandler() == null;
    }
}