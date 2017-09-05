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
package org.primefaces.mobile.component.page;

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
import javax.faces.context.FacesContext;

@PFComponent(tagName = "page",
             description = "",
             parent = UIPanel.class)
public class Page extends AbstractPage implements javax.faces.component.NamingContainer {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Title of the view")
		title,
		@PFProperty(description = "Swatch of the view")
		swatch,
		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "Lazy loading views are not rendered on initial page load to improve performance and instead lazily loaded on demand when there are first navigated to", defaultValue = "false", type = Boolean.class)
		lazy,;
	}


    @Override
    public void processDecodes(FacesContext context) {
        if(!isLazyloadRequest(context)) {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if(!isLazyloadRequest(context)) {
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(!isLazyloadRequest(context)) {
            super.processUpdates(context);
        }
    }

    public boolean isLazyloadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_lazyload");
    }
}