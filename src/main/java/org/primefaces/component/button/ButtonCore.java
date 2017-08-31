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
package org.primefaces.component.button;

import javax.faces.component.html.HtmlOutcomeTargetButton;
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
import java.util.Map;
import org.primefaces.util.HTML;
import java.util.logging.Logger;
import org.primefaces.util.ComponentUtils;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "button",
             description = "Button is an extension to the standard h:button component with skinning capabilities.",
             widget = true,
             rtl = true)
public abstract class ButtonCore extends HtmlOutcomeTargetButton implements IButton, org.primefaces.component.api.UIOutcomeTarget {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Identifier of the target page which should be scrolled to")
		fragment,
		@PFProperty(description = "Disables button", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Icon of the button")
		icon,
		@PFProperty(description = "Position of the icon, default value is left", defaultValue = "left")
		iconPos,
		@PFProperty(description = "Resource to link to")
		href,
		@PFProperty(description = "The window target. Default _self", defaultValue = "_self")
		target,
		@PFProperty(description = "Defines if label of the component is escaped or not", defaultValue = "true", type = Boolean.class)
		escape,
		@PFProperty(description = "Displays button inline instead of fitting the content width, only used by mobile", defaultValue = "false", type = Boolean.class)
		inline,
		@PFProperty(description = "Disable appending the \n on the rendering of this element", defaultValue = "false", type = Boolean.class)
		disableClientWindow,;
	}

        
    private final static Logger logger = Logger.getLogger(Button.class.getName());
                
    public String resolveIcon() {
        String icon = getIcon();
    
        if(icon == null) {
            icon = getImage();
            
            if(icon != null)
                logger.info("image attribute is deprecated to define an icon, use icon attribute instead.");
        }
    
        return icon;
    }
    
    public String resolveStyleClass() {
        String icon = resolveIcon();
        Object value = getValue();
        String styleClass = ""; 
    
        if(value != null && ComponentUtils.isValueBlank(icon)) {
            styleClass = HTML.BUTTON_TEXT_ONLY_BUTTON_CLASS;
        }
        else if(value != null && !ComponentUtils.isValueBlank(icon)) {
            styleClass = getIconPos().equals("left") ? HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS : HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS;
        }
        else if(value == null && !ComponentUtils.isValueBlank(icon)) {
            styleClass = HTML.BUTTON_ICON_ONLY_BUTTON_CLASS;
        }
    
        if(isDisabled()) {
            styleClass = styleClass + " ui-state-disabled";
        } 
    
        String userStyleClass = getStyleClass();
        if(userStyleClass != null) {
            styleClass = styleClass + " " + userStyleClass;
        }
    
        return styleClass;
    }
    
    public Map<String, List<String>> getParams() {
        return ComponentUtils.getUIParams(this);
    }
}