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
package org.primefaces.component.resizable;

import javax.faces.component.UIComponentBase;
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
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import org.primefaces.event.ResizeEvent;
import org.primefaces.util.Constants;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "resizable",
             description = "PrimeFaces features a resizable component that has the ability to make a JSF component resizable.\n      Resizable can be used on various components like resize an input fields, panels, menus, images and more.",
             widget = true,
             rtl = true,
             parent = UIComponentBase.class)
public class Resizable extends AbstractResizable implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Identifier of the target component to make resizable")
		forValue("for"),
		@PFProperty(description = "Defines if aspectRatio should be kept or not. Default is false", defaultValue = "false", type = Boolean.class)
		aspectRatio,
		@PFProperty(description = "Displays proxy element instead of actual element", defaultValue = "false", type = Boolean.class)
		proxy,
		@PFProperty(description = "Specifies the resize handles")
		handles,
		@PFProperty(description = "In ghost mode, resize helper is displayed as the original element with less opacity. Default is false", defaultValue = "false", type = Boolean.class)
		ghost,
		@PFProperty(description = "Enables animation. Default is false", defaultValue = "false", type = Boolean.class)
		animate,
		@PFProperty(description = "Effect to use in animation. Default is swing", defaultValue = "swing")
		effect,
		@PFProperty(description = "Effect duration of animation. Default is normal", defaultValue = "normal")
		effectDuration,
		@PFProperty(description = "Maximum width boundary in pixels. Default is max integer value", defaultValue = "Integer.MAX_VALUE", type = Integer.class)
		maxWidth,
		@PFProperty(description = "Maximum height boundary in pixels. Default is max integer value", defaultValue = "Integer.MAX_VALUE", type = Integer.class)
		maxHeight,
		@PFProperty(description = "Minimum width boundary in pixels. Default is min integer value", defaultValue = "Integer.MIN_VALUE", type = Integer.class)
		minWidth,
		@PFProperty(description = "Maximum height boundary in pixels. Default is min integer value", defaultValue = "Integer.MIN_VALUE", type = Integer.class)
		minHeight,
		@PFProperty(description = "Sets resizable boundaries as the parents size. Default is false", defaultValue = "false", type = Boolean.class)
		containment,
		@PFProperty(description = "Snaps resizing to grid structure. Default is 1", defaultValue = "1", type = Integer.class)
		grid,
		@PFProperty(description = "Client side callback to execute when resizing begins")
		onStart,
		@PFProperty(description = "Client side callback to execute during resizing")
		onResize,
		@PFProperty(description = "Client side callback to execute after resizing end")
		onStop,;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return toString != null ? toString : super.toString();
		}
	}


        private final static String DEFAULT_EVENT = "resize";

        private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
            put("resize", ResizeEvent.class);
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
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();

            if(ComponentUtils.isRequestSource(this, context)) {
                String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
                String clientId = getClientId(context);
                
                if(eventName.equals("resize")) {
                    AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
                    int width = Integer.parseInt(params.get(clientId + "_width"));
                    int height = Integer.parseInt(params.get(clientId + "_height"));

                    super.queueEvent(new ResizeEvent(this, behaviorEvent.getBehavior(), width, height));
                }
                
            } else {
                super.queueEvent(event);
            }
        }
}