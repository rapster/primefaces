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
package org.primefaces.component.inplace;

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
import javax.el.ValueExpression;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.inplace.Inplace;
import org.primefaces.util.Constants;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "inplace",
             description = "Inplace provides easy inplace editing and inline content display.\n      Inplace consists of two members, display element is the inital clickable label and inline element is the hidden content\n      that'll be displayed when display element is toggled.",
             widget = true,
             rtl = true,
             parent = UIComponentBase.class)
public class Inplace extends AbstractInplace implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Label to be shown in display mode")
		label,
		@PFProperty(description = "Label to be shown in display mode when value isempty")
		emptyLabel,
		@PFProperty(description = "Effect to be used when toggling. Default fade", defaultValue = "fade")
		effect,
		@PFProperty(description = "Speed of the effect. Default normal", defaultValue = "normal")
		effectSpeed,
		@PFProperty(description = "Prevents hidden content to be shown", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Inline style of the main container element")
		style,
		@PFProperty(description = "Style class of the main container element")
		styleClass,
		@PFProperty(description = "Specifies the editor mode", defaultValue = "false", type = Boolean.class)
		editor,
		@PFProperty(description = "Tooltip text of save buttin in editor mode. Default is \"Save\"", defaultValue = "Save")
		saveLabel,
		@PFProperty(description = "Tooltip text of cancel buttin in editor mode. Default is \"Cancel\"", defaultValue = "Cancel")
		cancelLabel,
		@PFProperty(description = "Name of the client side event to display inline content. Default is click", defaultValue = "click")
		event,
		@PFProperty(description = "", defaultValue = "true", type = Boolean.class)
		toggleable,;
	}


    public static final String CONTAINER_CLASS = "ui-inplace ui-hidden-container";
    public static final String DISPLAY_CLASS = "ui-inplace-display";
    public static final String DISABLED_DISPLAY_CLASS = "ui-inplace-display-disabled";
    public static final String CONTENT_CLASS = "ui-inplace-content";
    public static final String EDITOR_CLASS = "ui-inplace-editor";
    public static final String SAVE_BUTTON_CLASS = "ui-inplace-save";
    public static final String CANCEL_BUTTON_CLASS = "ui-inplace-cancel";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("save", null);
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
    public void processDecodes(FacesContext context) {
        if(shouldSkipChildren(context)) {
            this.decode(context);
        }
        else {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if(!shouldSkipChildren(context)) {
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(!shouldSkipChildren(context)) {
            super.processUpdates(context);
        }
    }

    private boolean shouldSkipChildren(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_cancel")||this.isDisabled();
    }

    public boolean isValid() {
        boolean valid = true;
        
        for(Iterator<UIComponent> it = this.getFacetsAndChildren(); it.hasNext();) {
            UIComponent component = it.next();
            if(component instanceof EditableValueHolder && !((EditableValueHolder) component).isValid()) {
                valid = false;
                break;
            }
        }
        
        return valid;
    }
}