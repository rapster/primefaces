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
package org.primefaces.component.cache;

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
import javax.faces.component.visit.VisitContext;

@PFComponent(tagName = "cache",
             description = "Cache component is used to reduce page load time by caching the content after initial rendering.",
             parent = UIPanel.class)
public class UICache extends AbstractUICache {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Disables caching", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Unique id of the cache region, defaults to view id")
		region,
		@PFProperty(description = "Unique id of the cache entry in region, defaults to client id of component")
		key,
		@PFProperty(description = "When enabled, lifecycle events such as button actions are executed", defaultValue = "false", type = Boolean.class)
		processEvents,;
	}


    private boolean cacheSetInCurrentRequest = false;

    public boolean isCacheSetInCurrentRequest() {
        return this.cacheSetInCurrentRequest;
    }

    public void setCacheSetInCurrentRequest(boolean cacheSetInCurrentRequest) {
        this.cacheSetInCurrentRequest = cacheSetInCurrentRequest;
    }

    @Override
    protected boolean isVisitable(VisitContext visitContext) {
        return this.isDisabled() || this.isCacheSetInCurrentRequest();
    }

    protected boolean shouldProcess() {
        return this.isDisabled() || this.isCacheSetInCurrentRequest() || this.isProcessEvents();
    }

    @Override
    public void processDecodes(FacesContext context) {
        if(shouldProcess()) {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if(shouldProcess()) {
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(shouldProcess()) {
            super.processUpdates(context);
        }
    }
}