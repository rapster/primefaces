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
package org.primefaces.component.rating;

import javax.faces.component.UIInput;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.primefaces.event.RateEvent;
import org.primefaces.util.Constants;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.PhaseId;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "rating",
             description = "Rating component features a star based rating system. Rating can be used as a plain input component or with ajax RateListeners.",
             widget = true,
             rtl = true,
             parent = UIInput.class)
public class Rating extends AbstractRating implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Number of stars to display. Default is 5", defaultValue = "5", type = Integer.class)
		stars,
		@PFProperty(description = "Disables user interaction", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Disables user interaction without adding disabled visuals", defaultValue = "false", type = Boolean.class)
		readonly,
		@PFProperty(description = "Client side callback to execute when rate happens")
		onRate,
		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "When enabled, displays a cancel icon to reset rating value, defaults to true", defaultValue = "true", type = Boolean.class)
		cancel,;
	}


    public static final String CONTAINER_CLASS = "ui-rating";
    public static final String CANCEL_CLASS = "ui-rating-cancel";
    public static final String STAR_CLASS = "ui-rating-star";
    public static final String STAR_ON_CLASS = "ui-rating-star ui-rating-star-on";

    private final static String DEFAULT_EVENT = "rate";

    private Map<String,AjaxBehaviorEvent> customEvents = new HashMap<String,AjaxBehaviorEvent>();

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("rate", RateEvent.class);
        put("cancel", null);
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

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(event instanceof AjaxBehaviorEvent) {
            String eventName = context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);

            if(eventName.equals("rate")) {
                customEvents.put(eventName, (AjaxBehaviorEvent) event);
            }
            else if(eventName.equals("cancel")) {
                super.queueEvent(event);
            }
        } 
        else {
            super.queueEvent(event);
        }
    }

    @Override
    public void validate(FacesContext context) {
        super.validate(context);

        if(isValid()) {
            for(Iterator<String> customEventIter = customEvents.keySet().iterator(); customEventIter.hasNext();) {
                AjaxBehaviorEvent behaviorEvent = customEvents.get(customEventIter.next());
                RateEvent rateEvent = new RateEvent(this, behaviorEvent.getBehavior(), getValue());

                rateEvent.setPhaseId(behaviorEvent.getPhaseId());
                
                super.queueEvent(rateEvent);
            }
        }
    }
}