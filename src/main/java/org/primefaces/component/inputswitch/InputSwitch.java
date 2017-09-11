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
package org.primefaces.component.inputswitch;

import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;
import org.primefaces.component.api.PrimeClientBehaviorHolder;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="inputswitch/inputswitch.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="inputswitch/inputswitch.js")
})
@PFComponent(tagName = "inputSwitch",
             description = "",
             widget = true,
             parent = UIInput.class)
public class InputSwitch extends AbstractInputSwitch implements ClientBehaviorHolder, PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Custom label for on state", defaultValue = "on")
		onLabel,
		@PFProperty(description = "Custom label for off state", defaultValue = "off")
		offLabel,
		@PFProperty(description = "User presentable name")
		label,
		@PFProperty(description = "Disables or enables the switch", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Client side callback to execute on value change event")
		onchange,
		@PFProperty(description = "Inline style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "The tabindex attribute specifies the tab order of an element when the \"tab\" button is used for navigating")
		tabindex,
		@PFProperty(description = "Controls the visibility of the labels, defaults to true", defaultValue = "true", type = Boolean.class)
		showLabels,
		@PFProperty(description = "Client side callback to execute when component receives focus")
		onfocus,
		@PFProperty(description = "Client side callback to execute when component loses focus")
		onblur,;
	}


    public final static String CONTAINER_CLASS = "ui-inputswitch ui-widget ui-widget-content ui-corner-all";
    public final static String ON_LABEL_CLASS = "ui-inputswitch-on ui-state-active";
    public final static String OFF_LABEL_CLASS = "ui-inputswitch-off";
    public final static String HANDLE_CLASS = "ui-inputswitch-handle ui-state-default ";
   
    private final static String DEFAULT_EVENT = "change";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("change", null);
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
}