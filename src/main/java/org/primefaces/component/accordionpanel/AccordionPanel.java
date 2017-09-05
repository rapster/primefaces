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
package org.primefaces.component.accordionpanel;

import org.primefaces.component.api.UITabPanel;
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
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.event.TabEvent;
import javax.el.ValueExpression;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.Constants;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.event.AbortProcessingException;
import org.primefaces.context.RequestContext;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "accordionPanel",
             description = "AccordionPanel is a container component that displays content in stacked format.",
             widget = true,
             rtl = true,
             parent = UITabPanel.class)
public class AccordionPanel extends AbstractAccordionPanel implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIComponentPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Index of the active tab or a comma separated string of indexes when multiple mode is on. Default is zero", defaultValue = "0")
		activeIndex,
		@PFProperty(description = "Inline style of the container element")
		style,
		@PFProperty(description = "Style class of the container element")
		styleClass,
		@PFProperty(description = "Client side callback to execute when a tab is clicked")
		onTabChange,
		@PFProperty(description = "Client side callback to execute when a tab is shown")
		onTabShow,
		@PFProperty(description = "Client side callback to execute when a tab is closed")
		onTabClose,
		@PFProperty(description = "Defines if activating a dynamic tab should load the contents from server again", defaultValue = "true", type = Boolean.class)
		cache,
		@PFProperty(description = "Enables activating multiple tabs, default is false", defaultValue = "false", type = Boolean.class)
		multiple,
		@PFProperty(description = "Position of the headers in the tabbing order. Default is 0", defaultValue = "0")
		tabindex,
		@PFProperty(description = "Method providing suggestions", type = javax.el.MethodExpression.class)
		tabController,;
	}


    public final static String CONTAINER_CLASS = "ui-accordion ui-widget ui-helper-reset ui-hidden-container";
    public final static String ACTIVE_TAB_HEADER_CLASS = "ui-accordion-header ui-helper-reset ui-state-default ui-state-active ui-corner-top";
    public final static String TAB_HEADER_CLASS = "ui-accordion-header ui-helper-reset ui-state-default ui-corner-all";
    public final static String TAB_HEADER_ICON_CLASS = "ui-icon ui-icon-triangle-1-e";
    public final static String TAB_HEADER_ICON_RTL_CLASS = "ui-icon ui-icon-triangle-1-w";
    public final static String ACTIVE_TAB_HEADER_ICON_CLASS = "ui-icon ui-icon-triangle-1-s";
    public final static String ACTIVE_TAB_CONTENT_CLASS = "ui-accordion-content ui-helper-reset ui-widget-content";
    public final static String INACTIVE_TAB_CONTENT_CLASS = "ui-accordion-content ui-helper-reset ui-widget-content ui-helper-hidden";

    public final static String MOBILE_CONTAINER_CLASS = "ui-accordion ui-collapsible-set ui-corner-all ui-hidden-container";
    public final static String MOBILE_INACTIVE_TAB_CONTAINER_CLASS = "ui-collapsible ui-collapsible-inset ui-corner-all ui-collapsible-themed-content ui-collapsible-collapsed";
    public final static String MOBILE_ACTIVE_TAB_CONTAINER_CLASS = "ui-collapsible ui-collapsible-inset ui-corner-all ui-collapsible-themed-content";
    public final static String MOBILE_ACTIVE_TAB_HEADER_CLASS = "ui-collapsible-heading";
    public final static String MOBILE_INACTIVE_TAB_HEADER_CLASS = "ui-collapsible-heading ui-collapsible-heading-collapsed";
    public final static String MOBILE_ACTIVE_TAB_CONTENT_CLASS = "ui-collapsible-content ui-body-inherit";
    public final static String MOBILE_INACTIVE_TAB_CONTENT_CLASS = "ui-collapsible-content ui-body-inherit ui-collapsible-content-collapsed";
    public final static String MOBILE_ACTIVE_ICON_CLASS = "ui-collapsible-heading-toggle ui-btn ui-btn-icon-left ui-icon-minus";
    public final static String MOBILE_INACTIVE_ICON_CLASS = "ui-collapsible-heading-toggle ui-btn ui-btn-icon-left ui-icon-plus";   

    private final static String DEFAULT_EVENT = "tabChange";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("tabChange", TabChangeEvent.class);
        put("tabClose", TabCloseEvent.class);
    }});

    private static final Collection<String> EVENT_NAMES = BEHAVIOR_EVENT_MAPPING.keySet();

    @Override
    public Map<String, Class<? extends BehaviorEvent>> getBehaviorEventMapping() {
         return BEHAVIOR_EVENT_MAPPING ;
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public String getDefaultEventName() {
        return DEFAULT_EVENT;
    }

    public boolean isContentLoadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_contentLoad");
    }

	public Tab findTab(String tabClientId) {
        for(UIComponent component : getChildren()) {
            if(component.getClientId().equals(tabClientId))
                return (Tab) component;
        }

        return null;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(ComponentUtils.isRequestSource(this, context) && event instanceof AjaxBehaviorEvent) {
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            String clientId = this.getClientId(context);
            boolean repeating = this.isRepeating();
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

            if(eventName.equals("tabChange")) {
                String tabClientId = params.get(clientId + "_newTab");
                TabChangeEvent changeEvent = new TabChangeEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId));

                if(repeating) {
                    int index = Integer.parseInt(params.get(clientId + "_tabindex"));
                    setIndex(index);
                    changeEvent.setData(this.getIndexData());
                    changeEvent.setTab((Tab) getChildren().get(0));
                }

                changeEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(changeEvent);

                if(repeating) {
                    setIndex(-1);
                }
            }
            else if(eventName.equals("tabClose")) {
                String tabClientId = params.get(clientId + "_tabId");
                TabCloseEvent closeEvent = new TabCloseEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId));

                if(repeating) {
                    int index = Integer.parseInt(params.get(clientId + "_tabindex"));
                    setIndex(index);
                    closeEvent.setData(this.getIndexData());
                    closeEvent.setTab((Tab) getChildren().get(0));
                }

                closeEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(closeEvent);

                if(repeating) {
                    setIndex(-1);
                }
            }
        }
        else {
            super.queueEvent(event);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(!isRendered()) {
            return;
        }

        super.processUpdates(context);

        ValueExpression expr = this.getValueExpression(PropertyKeys.activeIndex.toString());
        if(expr != null) {
            expr.setValue(getFacesContext().getELContext(), getActiveIndex());
            resetActiveIndex();
        }
    }

    protected void resetActiveIndex() {
		getStateHelper().remove(PropertyKeys.activeIndex);
    }

    public boolean isRTL() {
        return this.getDir().equalsIgnoreCase("rtl");
    }

    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        super.broadcast(event);
        
        if(event instanceof TabEvent) {
            MethodExpression me = this.getTabController();
            if(me != null) {
                boolean retVal = (Boolean) me.invoke(getFacesContext().getELContext(), new Object[]{event});
                RequestContext.getCurrentInstance(getFacesContext()).addCallbackParam("access", retVal);
            }
        }
    }
}