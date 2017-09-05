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
package org.primefaces.component.selectmanybutton;

import javax.faces.component.UISelectMany;
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
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "selectManyButton",
             description = "",
             widget = true,
             rtl = true,
             parent = UISelectMany.class)
public class SelectManyButton extends AbstractSelectManyButton implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "")
		label,
		@PFProperty(description = "")
		onchange,
		@PFProperty(description = "")
		style,
		@PFProperty(description = "")
		styleClass,
		@PFProperty(description = "Position of the element in the tabbing order")
		tabindex,;
	}


    public final static String STYLE_CLASS = "ui-selectmanybutton ui-buttonset ui-widget ui-corner-all";
    public final static String MOBILE_STYLE_CLASS = "ui-selectmanybutton ui-controlgroup ui-controlgroup-horizontal ui-corner-all";
    public final static String MOBILE_ITEMS_CLASS = "ui-controlgroup-controls";
    public final static String MOBILE_LABEL_CLASS = "ui-btn ui-corner-all ui-btn-inherit";

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