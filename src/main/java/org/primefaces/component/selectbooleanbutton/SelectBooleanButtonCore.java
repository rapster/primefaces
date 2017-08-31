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
package org.primefaces.component.selectbooleanbutton;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;
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
import org.primefaces.util.HTML;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "selectBooleanButton",
             description = "",
             widget = true,
             rtl = true)
public abstract class SelectBooleanButtonCore extends HtmlSelectBooleanCheckbox implements ISelectBooleanButton {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "", required = true)
		onLabel,
		@PFProperty(description = "", required = true)
		offLabel,
		@PFProperty(description = "")
		onIcon,
		@PFProperty(description = "")
		offIcon,;
	}


    public final static String STYLE_CLASS = "ui-selectbooleanbutton ui-widget";

    public String resolveStyleClass(boolean checked, boolean disabled) {
        String icon = checked ? getOnIcon() : getOffIcon();
        String styleClass = icon != null ? HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS : HTML.BUTTON_TEXT_ONLY_BUTTON_CLASS;
    
        if(disabled) {
            styleClass = styleClass + " ui-state-disabled";
        } 

        if(checked) {
            styleClass = styleClass + " ui-state-active";
        }

        if(!isValid()) {
            styleClass = styleClass + " ui-state-error";
        }

        String userStyleClass = getStyleClass();
        if(userStyleClass != null) {
            styleClass = styleClass + " " + userStyleClass;
        }
    
        return styleClass;
    }
}