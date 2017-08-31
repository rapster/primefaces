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
package org.primefaces.component.overlaypanel;

import javax.faces.component.UIPanel;
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
import org.primefaces.util.Constants;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "overlayPanel",
             description = "OverlayPanel is a generic container component that is displayed as a popup.",
             widget = true,
             rtl = true)
public abstract class OverlayPanelCore extends UIPanel implements IOverlayPanel {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "Identifier of the target component to show the panel")
		forValue("for"),
		@PFProperty(description = "Event to show the overlay panel, default is \"click\"")
		showEvent,
		@PFProperty(description = "Event to hide the overlay panel, default is \"click\"")
		hideEvent,
		@PFProperty(description = "Effect to display when showing the panel")
		showEffect,
		@PFProperty(description = "Effect to display when hiding the panel")
		hideEffect,
		@PFProperty(description = "When set true, panel is appended as a child of document body", defaultValue = "false", type = Boolean.class)
		appendToBody,
		@PFProperty(description = "Client side callback to execute when panel is displayed")
		onShow,
		@PFProperty(description = "Client side callback to execute when panel is hidden")
		onHide,
		@PFProperty(description = "Position of the panel relative to the target")
		my,
		@PFProperty(description = "Position of the target relative to the panel")
		at,
		@PFProperty(description = "Dynamic mode allows overlay panel to fetch it's contents before it's shown rather than on page load\n which is useful to reduce initial page load times. Default is false", defaultValue = "false", type = Boolean.class)
		dynamic,
		@PFProperty(description = "When set true, clicking outside of the panel hides the overlay", defaultValue = "true", type = Boolean.class)
		dismissable,
		@PFProperty(description = "Displays a close icon to hide the overlay, default is false", defaultValue = "false", type = Boolean.class)
		showCloseIcon,
		@PFProperty(description = "Boolean value that specifies whether the document should be shielded with a partially transparent mask to require the user to close the Panel before being able to activate any elements in the document. Default is false", defaultValue = "false", type = Boolean.class)
		modal,
		@PFProperty(description = "Delay time to show overlay panel in milliseconds. Default is 0", defaultValue = "0", type = Integer.class)
		showDelay,;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return toString != null ? toString : super.toString();
		}
	}


    public static final String STYLE_CLASS = "ui-overlaypanel ui-widget ui-widget-content ui-overlay-hidden ui-corner-all ui-shadow";
    public static final String CONTENT_CLASS = "ui-overlaypanel-content";

    @Override
    public void processDecodes(FacesContext context) {
        if(ComponentUtils.isRequestSource(this, context)) {
            this.decode(context);
        }
        else {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if(!ComponentUtils.isRequestSource(this, context)) {
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(!ComponentUtils.isRequestSource(this, context)) {
            super.processUpdates(context);
        }
    }

    public boolean isContentLoadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_contentLoad");
    }
}