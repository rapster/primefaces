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
package org.primefaces.component.datagrid;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.model.DataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.util.Constants;
import java.util.HashMap;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "dataGrid",
             description = "DataGrid displays a collection of data in grid layout. Ajax Pagination is a built-in feature and paginator UI is fully customizable via various options like paginatorTemplate, rowPerPageOptions, pageLinks and more.",
             widget = true,
             parent = UIData.class)
public class DataGrid extends AbstractDataGrid implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder, org.primefaces.component.api.Pageable {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIDataPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Number of columns of grid", defaultValue = "3", type = Integer.class)
		columns,
		@PFProperty(description = "Inline style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "Text to display when there is no data to display", defaultValue = "No records found.")
		emptyMessage,
		@PFProperty(description = "Layout approach to use, valid values are \"tabular\" (default) and \"grid\" for responsive grid", defaultValue = "tabular")
		layout,;
	}


	public static final String DATAGRID_CLASS = "ui-datagrid ui-widget";
    public static final String HEADER_CLASS = "ui-datagrid-header ui-widget-header ui-corner-top";
    public static final String FOOTER_CLASS = "ui-datagrid-footer ui-widget-header ui-corner-bottom";
    public static final String TABLE_CONTENT_CLASS = "ui-datagrid-content ui-widget-content";
    public static final String EMPTY_CONTENT_CLASS = "ui-datagrid-content ui-datagrid-content-empty ui-widget-content";
	public static final String TABLE_CLASS = "ui-datagrid-data";
	public static final String TABLE_ROW_CLASS = "ui-datagrid-row";
    public static final String GRID_CONTENT_CLASS = "ui-datagrid-content ui-widget-content ui-grid ui-grid-responsive";
    public static final String GRID_ROW_CLASS = "ui-grid-row";
    public static final String COLUMN_CLASS = "ui-datagrid-column";

    public static final String MOBILE_DATAGRID_CLASS = "ui-datagrid";
    public static final String MOBILE_HEADER_CLASS = "ui-datagrid-header ui-bar ui-bar-b";
    public static final String MOBILE_FOOTER_CLASS = "ui-datagrid-footer ui-bar ui-bar-b";
    public static final String MOBILE_CONTENT_CLASS = "ui-datagrid-content ui-responsive";
    public static final String MOBILE_EMPTY_CONTENT_CLASS = "ui-datagrid-content ui-datagrid-content-empty";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("page", PageEvent.class);
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

    public void loadLazyData() {
        DataModel model = getDataModel();
        
        if(model != null && model instanceof LazyDataModel) {            
            LazyDataModel lazyModel = (LazyDataModel) model;

            List<?> data = lazyModel.load(getFirst(), getRows(), null, null, null);
            
            lazyModel.setPageSize(getRows());
            lazyModel.setWrappedData(data);

            //Update paginator for callback
            if(ComponentUtils.isRequestSource(this, getFacesContext()) && this.isPaginator()) {
                RequestContext requestContext = RequestContext.getCurrentInstance(getFacesContext());

                if(requestContext != null) {
                    requestContext.addCallbackParam("totalRecords", lazyModel.getRowCount());
                }
            }
        }
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(ComponentUtils.isRequestSource(this, context) && event instanceof AjaxBehaviorEvent) {
            setRowIndex(-1);
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);

            if(eventName.equals("page")) {
                AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
                String clientId = this.getClientId(context);
                int rows = this.getRowsToRender();
                int first = Integer.parseInt(params.get(clientId + "_first"));
                int page = rows > 0 ? (int) (first / rows) : 0;
        
                PageEvent pageEvent = new PageEvent(this, behaviorEvent.getBehavior(), page);
                pageEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(pageEvent);
            }
        }
        else {
            super.queueEvent(event);
        }
    }
}