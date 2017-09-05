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
package org.primefaces.component.datascroller;

import org.primefaces.component.api.UIData;
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

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "dataScroller",
             description = "DataScroller displays a collection of data featuring on demand loading using scroll.",
             widget = true,
             rtl = true,
             parent = UIData.class)
public class DataScroller extends AbstractDataScroller {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIDataPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Inline style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "Number of items to fetch", defaultValue = "0", type = Integer.class)
		chunkSize,
		@PFProperty(description = "Defines the target to listen for scroll event, valid values are \"document\" (default) and \"inline\"", defaultValue = "document")
		mode,
		@PFProperty(description = "Defines pixel height of the viewport in inline mode")
		scrollHeight,
		@PFProperty(description = "Percentage height of the buffer between the bottom of the page and the scroll position to initiate\n the load for the new chunk. Value is defined in integer and default is 10 meaning load would happen after 90% of the viewport is scrolled down", defaultValue = "10", type = Integer.class)
		buffer,;
	}

    public final static String CONTAINER_CLASS = "ui-datascroller ui-widget";
    public final static String INLINE_CONTAINER_CLASS = "ui-datascroller ui-datascroller-inline ui-widget";
    public final static String HEADER_CLASS = "ui-datascroller-header ui-widget-header ui-corner-top";
    public final static String CONTENT_CLASS = "ui-datascroller-content ui-widget-content";
    public final static String LIST_CLASS = "ui-datascroller-list";
    public final static String ITEM_CLASS = "ui-datascroller-item";
    public final static String LOADER_CLASS = "ui-datascroller-loader";
    public final static String LOADING_CLASS = "ui-datascroller-loading";

    public boolean isLoadRequest() {
        FacesContext context = getFacesContext();
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_load");
    }
}