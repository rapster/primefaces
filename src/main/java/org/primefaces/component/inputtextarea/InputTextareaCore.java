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
package org.primefaces.component.inputtextarea;

import javax.faces.component.html.HtmlInputTextarea;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.el.ValueExpression;
import javax.faces.convert.Converter;
import javax.faces.component.behavior.Behavior;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.util.Constants;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "inputTextarea",
             description = "InputTextarea is an extension to standard inputTextara with skinning capabilities and auto growing.",
             widget = true,
             rtl = true)
public abstract class InputTextareaCore extends HtmlInputTextarea implements IInputTextarea, org.primefaces.component.api.MixedClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Allows to expand height automatically when text input overflows", defaultValue = "true", type = Boolean.class)
		autoResize,
		@PFProperty(description = "Sets maximum character number of text area container. Default is 0, means no limit", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		maxlength,
		@PFProperty(description = "Id of the label component to display remaining characters")
		counter,
		@PFProperty(description = "Template text to display in counter, default value is \"{0}\"")
		counterTemplate,
		@PFProperty(description = "Method providing suggestions", type = javax.el.MethodExpression.class)
		completeMethod,
		@PFProperty(description = "Number of characters of a word to be typed before starting to query. Default is 3", defaultValue = "3", type = Integer.class)
		minQueryLength,
		@PFProperty(description = "Delay to wait in milliseconds before sending each query to the server. Default is 700", defaultValue = "700", type = Integer.class)
		queryDelay,
		@PFProperty(description = "Defines the height of the viewport for autocomplete suggestions", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		scrollHeight,
		@PFProperty(description = "Adds a new line at start as a workaround to SGML specification B.3.1", defaultValue = "false", type = Boolean.class)
		addLine,;
	}


    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur","change","valueChange","click","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select", "itemSelect"));
    private static final Collection<String> UNOBSTRUSIVE_EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("itemSelect"));

    public final static String STYLE_CLASS = "ui-inputfield ui-inputtextarea ui-widget ui-state-default ui-corner-all";
    public final static String MOBILE_STYLE_CLASS = "ui-input-text ui-shadow-inset ui-body-inherit ui-corner-all";

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public Collection<String> getUnobstrusiveEventNames() {
        return UNOBSTRUSIVE_EVENT_NAMES;
    }

    @Override
    public int getCols() {
        int cols = super.getCols();
    
        return cols > 0 ? cols : 20;
    }

    @Override
    public int getRows() {
        int rows = super.getRows();
    
        return rows > 0 ? rows : 3;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);

        if(eventName != null && event instanceof AjaxBehaviorEvent) {
            AjaxBehaviorEvent ajaxBehaviorEvent = (AjaxBehaviorEvent) event;

            if(eventName.equals("itemSelect")) {
                String selectedItemValue = params.get(this.getClientId(context) + "_itemSelect");
                SelectEvent selectEvent = new SelectEvent(this, (Behavior) ajaxBehaviorEvent.getBehavior(), selectedItemValue);
                selectEvent.setPhaseId(ajaxBehaviorEvent.getPhaseId());
                super.queueEvent(selectEvent);
            }
            else {
                //e.g. blur, focus, change
                super.queueEvent(event);
            }
        }
        else {
            //e.g. valueChange, autoCompleteEvent
            super.queueEvent(event);
        }
    }

    private List suggestions = null;

    public void broadcast(javax.faces.event.FacesEvent event) throws javax.faces.event.AbortProcessingException {
		super.broadcast(event);
		
		FacesContext facesContext = getFacesContext();
		MethodExpression me = getCompleteMethod();
		
		if(me != null && event instanceof org.primefaces.event.AutoCompleteEvent) {
			suggestions = (List) me.invoke(facesContext.getELContext(), new Object[] {((org.primefaces.event.AutoCompleteEvent) event).getQuery()});
            
            if(suggestions == null) {
                suggestions = new ArrayList();
            }

            facesContext.renderResponse();
		}
	}

    public List getSuggestions() {
        return this.suggestions;
    }
}