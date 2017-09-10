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
package org.primefaces.component.datalist;

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
import javax.faces.FacesException;
import javax.faces.event.PhaseId;
import javax.faces.component.UIColumn;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.model.DataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.mobile.event.SwipeEvent;
import org.primefaces.event.SelectEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.faces.component.UIComponent;
import org.primefaces.util.Constants;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import org.primefaces.event.data.PageEvent;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "dataList",
             description = "DataList presents a collection of data in list layout with several display types.\n      Ajax Pagination is a built-in feature and paginator UI is fully customizable via various options like paginatorTemplate, rowsPerPageOptions, pageLinks and more.",
             widget = true,
             parent = UIData.class)
public class DataList extends AbstractDataList implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder, org.primefaces.component.api.Pageable {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIDataPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Type of the list, valid values are \"unordered\", \"ordered\" and \"definition\"", defaultValue = "unordered")
		type,
		@PFProperty(description = "Specifies the list item type")
		itemType,
		@PFProperty(description = "Inline style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "Name of the exported request scoped variable for the status of the iteration")
		varStatus,
		@PFProperty(description = "Text to display when there is no data to display", defaultValue = "No records found.")
		emptyMessage,
		@PFProperty(description = "Style class of an item in list. This option is useful to assign specific styles to certain items using an EL expression")
		itemStyleClass,;
	}


	public static final String DATALIST_CLASS = "ui-datalist ui-widget";
    public static final String CONTENT_CLASS = "ui-datalist-content ui-widget-content";
	public static final String LIST_CLASS = "ui-datalist-data";
    public static final String NO_BULLETS_CLASS = "ui-datalist-nobullets";
	public static final String LIST_ITEM_CLASS = "ui-datalist-item";
    public static final String HEADER_CLASS = "ui-datalist-header ui-widget-header ui-corner-top";
    public static final String FOOTER_CLASS = "ui-datalist-footer ui-widget-header ui-corner-bottom";
	public static final String DATALIST_EMPTYMESSAGE_CLASS = "ui-datalist-empty-message";

    public static final String MOBILE_CONTENT_CLASS = "ui-datalist-content";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("page", PageEvent.class);
        put("swipeleft", SwipeEvent.class);
        put("swiperight", SwipeEvent.class);
        put("tap", SelectEvent.class);
        put("taphold", SelectEvent.class);
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

	public String getListTag() {
		String type = getType();
		
		if(type.equalsIgnoreCase("unordered"))
			return "ul";
		else if(type.equalsIgnoreCase("ordered"))
			return "ol";
		else if(type.equalsIgnoreCase("definition"))
			return "dl";
        else if(type.equalsIgnoreCase("none"))
            return null;
        else
			throw new FacesException("DataList '" + this.getClientId() + "' has invalid list type:'" + type + "'");
	}
	
	public boolean isDefinition() {
		return getType().equalsIgnoreCase("definition");
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
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);

            if(eventName.equals("page")) {
                String clientId = this.getClientId(context);
                int rows = this.getRowsToRender();
                int first = Integer.parseInt(params.get(clientId + "_first"));
                int page = rows > 0 ? (int) (first / rows) : 0;
        
                PageEvent pageEvent = new PageEvent(this, behaviorEvent.getBehavior(), page);
                pageEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(pageEvent);
            }
            else if(eventName.equals("swipeleft")||eventName.equals("swiperight")) {
                String clientId = this.getClientId(context);
                int index = Integer.parseInt(params.get(clientId + "_item"));
                this.setRowIndex(index);
        
                SwipeEvent swipeEvent = new SwipeEvent(this, behaviorEvent.getBehavior(), this.getRowData());
                swipeEvent.setPhaseId(behaviorEvent.getPhaseId());

                this.setRowIndex(-1);
                super.queueEvent(swipeEvent);
            }
            else if(eventName.equals("tap")||eventName.equals("taphold")) {
                String clientId = this.getClientId(context);
                int index = Integer.parseInt(params.get(clientId + "_item"));
                this.setRowIndex(index);
        
                SelectEvent selectEvent = new SelectEvent(this, behaviorEvent.getBehavior(), this.getRowData());
                selectEvent.setPhaseId(behaviorEvent.getPhaseId());

                this.setRowIndex(-1);
                super.queueEvent(selectEvent);
            }
        }
        else {
            super.queueEvent(event);
        }
    }

    @Override
    protected void processFacets(FacesContext context, PhaseId phaseId) {
        if(this.getFacetCount() > 0) {
            UIComponent descriptionFacet = getFacet("description");
            for(UIComponent facet : getFacets().values()) {
                if(facet.equals(descriptionFacet)) {
                   continue;
                }
                process(context, facet, phaseId);
            }
        }
    }
    
    @Override
    protected void processChildren(FacesContext context, PhaseId phaseId) {
        int first = getFirst();
        int rows = getRows();
        int last = rows == 0 ? getRowCount() : (first + rows);
        
        for(int rowIndex = first; rowIndex < last; rowIndex++) {
            setRowIndex(rowIndex);

            if(!isRowAvailable()) {
                break;
            }
            
            for(UIComponent child : this.getIterableChildren()) {
                if(child.isRendered()) {
                    process(context, child, phaseId);
                }
            }

            UIComponent descriptionFacet = getFacet("description");
            if(descriptionFacet != null && isDefinition()) {
                process(context, descriptionFacet, phaseId);
            }
        }
    }

}