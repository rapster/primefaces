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
package org.primefaces.component.outputpanel;

import javax.faces.component.UIPanel;
import org.primefaces.component.api.AutoUpdatable;
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
import javax.faces.context.FacesContext;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "outputPanel",
             description = "OutputPanel is used to group content on a page.",
             widget = true,
             parent = UIPanel.class)
public class OutputPanel extends AbstractOutputPanel implements AutoUpdatable {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Style of the html container element")
		style,
		@PFProperty(description = "StyleClass of the html container element")
		styleClass,
		@PFProperty(description = "Updates outputPanel after each ajax request implicity", defaultValue = "false", type = Boolean.class)
		autoUpdate,
		@PFProperty(description = "Deferred mode loads the contents after page load to speed up page load", defaultValue = "false", type = Boolean.class)
		deferred,
		@PFProperty(description = "Defines deferred loading mode, valid values are \"load\" (after page load) and \"visible\" (once the panel is visible on scroll). Default value is \"load\"", defaultValue = "load")
		deferredMode,
		@PFProperty(description = "Global ajax requests are listened by ajaxStatus component, setting global to false will not trigger ajaxStatus on deferred loading", defaultValue = "false", type = Boolean.class)
		global,
		@PFProperty(description = "Shortcut for the css display property, valid values are block (default) and inline", defaultValue = "block")
		layout,
		@PFProperty(description = "Delay in milliseconds to wait before initiating a deferred loading, default value is \"none\"")
		delay,;
	}

    
    public static final String CONTAINER_CLASS = "ui-outputpanel ui-widget";
    public static final String LOADING_CLASS = "ui-outputpanel-loading ui-widget";

    public boolean isContentLoad(FacesContext context) {
        String clientId = this.getClientId(context);

        return context.getExternalContext().getRequestParameterMap().containsKey(clientId + "_load");
    }
}