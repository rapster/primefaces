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
package org.primefaces.component.poll;

import javax.faces.component.UIComponentBase;
import org.primefaces.component.poll.PollHandler;
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
import javax.el.ValueExpression;
import org.primefaces.context.RequestContext;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js")
})
@PFComponent(tagName = "poll",
             description = "Poll is an ajax component that has the ability to send periodical ajax requests and execute listeners on JSF backing beans.",
             widget = true,
             parent = UIComponentBase.class,
             handlerClass = PollHandler.class)
public class Poll extends AbstractPoll implements org.primefaces.component.api.AjaxSource {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Interval in seconds to do periodic ajax requests", defaultValue = "2", type = Integer.class)
		interval,
		@PFProperty(description = "Component(s) to be updated with ajax")
		update,
		@PFProperty(description = "A method expression to invoke by polling", type = javax.el.MethodExpression.class)
		listener,
		@PFProperty(description = "Boolean value that determines the phaseId, when true actions are processed at apply_request_values, when false at invoke_application phase", defaultValue = "false", type = Boolean.class)
		immediate,
		@PFProperty(description = "Javascript handler to execute before ajax request is begins")
		onstart,
		@PFProperty(description = "Javascript handler to execute when ajax request is completed")
		oncomplete,
		@PFProperty(description = "Component id(s) to process partially instead of whole view")
		process,
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
		@PFProperty(description = "In autoStart mode, polling starts automatically on page load, to start polling on demand set to false", defaultValue = "true", type = Boolean.class)
		autoStart,
		@PFProperty(description = "Stops polling when true", defaultValue = "false", type = Boolean.class)
		stop,
		@PFProperty(description = "When enabled, only values related to partially processed components would be serialized for ajax \n instead of whole form", defaultValue = "false", type = Boolean.class)
		partialSubmit,
		@PFProperty(description = "If true, indicate that this particular Ajax transaction is a value reset transaction. This will cause resetValue() to be called on any EditableValueHolder instances encountered as a result of this ajax transaction. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		resetValues,
		@PFProperty(description = "If true, components which autoUpdate=\"true\" will not be updated for this request. If not specified, or the value is false, no such indication is made", defaultValue = "false", type = Boolean.class)
		ignoreAutoUpdate,
		@PFProperty(description = "Selector to use when partial submit is on")
		partialSubmitFilter,
		@PFProperty(description = "Form to serialize for an ajax request")
		form,;
	}


    public void broadcast(javax.faces.event.FacesEvent event) throws javax.faces.event.AbortProcessingException {
		super.broadcast(event); //backward compatibility

		FacesContext facesContext = getFacesContext();
		MethodExpression me = getListener();

		if (me != null) {
			me.invoke(facesContext.getELContext(), new Object[] {});
		}

        ValueExpression expr = getValueExpression(PropertyKeys.stop.toString());
        if(expr != null) {
        	Boolean stop = (Boolean) expr.getValue(facesContext.getELContext());
        	
        	if (Boolean.TRUE.equals(stop)) {
        		String widgetVar = resolveWidgetVar();
        		RequestContext requestContext = RequestContext.getCurrentInstance(getFacesContext());
        		requestContext.execute("PF('" + widgetVar + "').stop();");
        	}
        }
	}

    public boolean isPartialSubmitSet() {
        return (getStateHelper().get(PropertyKeys.partialSubmit) != null) || (this.getValueExpression(PropertyKeys.partialSubmit.toString()) != null);
    }
    
    public boolean isResetValuesSet() {
        return (getStateHelper().get(PropertyKeys.resetValues) != null) || (this.getValueExpression(PropertyKeys.resetValues.toString()) != null);
    }
    
    public boolean isAjaxified() {
        return true;
    }
}