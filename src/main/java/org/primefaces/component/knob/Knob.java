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
package org.primefaces.component.knob;

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
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="knob/knob.js")
})
@PFComponent(tagName = "knob",
             description = "",
             widget = true,
             rtl = true,
             parent = UIInput.class)
public class Knob extends AbstractKnob implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "min valid value of the component", defaultValue = "0", type = Integer.class)
		min,
		@PFProperty(description = "Max valid value of the component", defaultValue = "100", type = Integer.class)
		max,
		@PFProperty(description = "Increment/decrement step of the component", defaultValue = "1", type = Integer.class)
		step,
		@PFProperty(description = "Thickness of the bar", type = Float.class)
		thickness,
		@PFProperty(description = "Width of the component", type = Object.class)
		width,
		@PFProperty(description = "Height of the component", type = Object.class)
		height,
		@PFProperty(description = "Foreground color of the component, you can use an hex value, a css constant or a java.awt.Color object", type = Object.class)
		foregroundColor,
		@PFProperty(description = "Background color of the component, you can use an hex value, a css constant or a java.awt.Color object", type = Object.class)
		backgroundColor,
		@PFProperty(description = "Theme of the knob")
		colorTheme,
		@PFProperty(description = "Disables the input element, default is false", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Set false to hide the label, default is true", defaultValue = "true", type = Boolean.class)
		showLabel,
		@PFProperty(description = "Set true to show only a cursor instead of the full bar, default is false", defaultValue = "false", type = Boolean.class)
		cursor,
		@PFProperty(description = "Template of the progress value e.g. \"{value}%\"", defaultValue = "{value}")
		labelTemplate,
		@PFProperty(description = "")
		onchange,
		@PFProperty(description = "Gauge stroke endings. Valid values are \"butt\" (default) and \"round\"", defaultValue = "butt")
		lineCap,;
	}


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