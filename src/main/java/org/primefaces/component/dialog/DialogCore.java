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
package org.primefaces.component.dialog;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.util.Constants;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.el.ELContext;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "dialog",
             description = "Dialog is a panel component overlaying other elements. Dialog avoids popup blockers, provides customization, resizing, modality, ajax interactions and more.",
             widget = true,
             rtl = true)
public abstract class DialogCore extends UIPanel implements IDialog, javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Text of the header")
		header,
		@PFProperty(description = "Specifies draggability. Default is true", defaultValue = "true", type = Boolean.class)
		draggable,
		@PFProperty(description = "Specifies resizability. Default is true", defaultValue = "true", type = Boolean.class)
		resizable,
		@PFProperty(description = "Boolean value that specifies whether the document should be shielded with a partially transparent mask to require the user to close the Panel before being able to activate any elements in the document. Default is false", defaultValue = "false", type = Boolean.class)
		modal,
		@PFProperty(description = "Sets dialogs visibility. Default is false", defaultValue = "false", type = Boolean.class)
		visible,
		@PFProperty(description = "Width of the dialog in pixels. Default is auto")
		width,
		@PFProperty(description = "Height of the dialog")
		height,
		@PFProperty(description = "Minimum width of a resizable dialog. Default is unl", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		minWidth,
		@PFProperty(description = "Minimum height of a resizable dialog. Default is height of titlebar", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		minHeight,
		@PFProperty(description = "Inline style of the dialog container")
		style,
		@PFProperty(description = "Style class of the dialog container")
		styleClass,
		@PFProperty(description = "Effect to use when showing the dialog")
		showEffect,
		@PFProperty(description = "Effect to use when hiding the dialog")
		hideEffect,
		@PFProperty(description = "Defines where the dialog should be displayed")
		position,
		@PFProperty(description = "Defines if close icon should be displayed or not", defaultValue = "true", type = Boolean.class)
		closable,
		@PFProperty(description = "Client side callback to execute when dialog is displayed")
		onShow,
		@PFProperty(description = "Client side callback to execute when dialog is hidden")
		onHide,
		@PFProperty(description = "Alternative to appendToBody. Appends the dialog to the given search expression")
		appendTo,
		@PFProperty(description = "Specifies visibility of header content", defaultValue = "true", type = Boolean.class)
		showHeader,
		@PFProperty(description = "Text of the footer")
		footer,
		@PFProperty(description = "Dynamic mode allows dialog to fetch it's contents before it's shown rather than on page load\n which is useful to reduce initial page load times. Default is false", defaultValue = "false", type = Boolean.class)
		dynamic,
		@PFProperty(description = "Specifies if dialog is minimizable or not. Default false", defaultValue = "false", type = Boolean.class)
		minimizable,
		@PFProperty(description = "Specifies if dialog is maximizable or not. Default false", defaultValue = "false", type = Boolean.class)
		maximizable,
		@PFProperty(description = "Defines if dialog should close when escape key is pressed. Default is false", defaultValue = "false", type = Boolean.class)
		closeOnEscape,
		@PFProperty(description = "Defines which component to focus")
		focus,
		@PFProperty(description = "Dialog size might exceeed viewport if content is bigger than viewport in terms of height. fitViewport option automatically\n adjusts height to fit dialog within the viewport", defaultValue = "false", type = Boolean.class)
		fitViewport,
		@PFProperty(description = "Defines whether dialog will be kept in viewport on scroll (fixed) or keep its position (absolute). Default is fixed", defaultValue = "fixed")
		positionType,
		@PFProperty(description = "In responsive mode, dialog adjusts itself based on screen width", defaultValue = "false", type = Boolean.class)
		responsive,;
	}


    public static final String CONTAINER_CLASS = "ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow ui-hidden-container";
    public static final String TITLE_BAR_CLASS = "ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top";
    public static final String TITLE_CLASS = "ui-dialog-title";
    public static final String TITLE_BAR_CLOSE_CLASS = "ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all";
    public static final String CLOSE_ICON_CLASS = "ui-icon ui-icon-closethick";
    public static final String TITLE_BAR_MINIMIZE_CLASS = "ui-dialog-titlebar-icon ui-dialog-titlebar-minimize ui-corner-all";
    public static final String MINIMIZE_ICON_CLASS = "ui-icon ui-icon-minus";
    public static final String TITLE_BAR_MAXIMIZE_CLASS = "ui-dialog-titlebar-icon ui-dialog-titlebar-maximize ui-corner-all";
    public static final String MAXIMIZE_ICON_CLASS = "ui-icon ui-icon-extlink";
    public static final String CONTENT_CLASS = "ui-dialog-content ui-widget-content";
    public static final String FOOTER_CLASS = "ui-dialog-footer ui-widget-content";

    public static final String MOBILE_CONTAINER_CLASS = "ui-popup-container ui-popup-hidden ui-popup-truncate";
    public static final String MOBILE_POPUP_CLASS = "ui-popup ui-body-inherit ui-overlay-shadow ui-corner-all";
    public static final String MOBILE_MASK_CLASS = "ui-popup-screen ui-overlay-b ui-screen-hidden";
    public static final String MOBILE_TITLE_BAR_CLASS = "ui-header ui-bar-inherit";
    public static final String MOBILE_TITLE_CLASS = "ui-title";
    public static final String MOBILE_CONTENT_CLASS = "ui-content";
    public static final String MOBILE_CLOSE_ICON_CLASS = "ui-btn ui-corner-all ui-icon-delete ui-btn-icon-notext ui-btn-left";

    public static final String ARIA_CLOSE = "primefaces.dialog.aria.CLOSE";

    private final static String DEFAULT_EVENT = "close";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("close", CloseEvent.class);
        put("minimize", null);
        put("maximize", null);
        put("move", MoveEvent.class);
        put("restoreMinimize", null);
        put("restoreMaximize", null);
        put("open", null);
        put("loadContent", null);
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

    @Override
    public String getDefaultEventName() {
        return DEFAULT_EVENT;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(ComponentUtils.isRequestSource(this, context) && event instanceof AjaxBehaviorEvent) {
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            AjaxBehaviorEvent ajaxBehaviorEvent = (AjaxBehaviorEvent) event;
            String clientId = getClientId(context);

            if(eventName.equals("close")) {
                setVisible(false);
                CloseEvent closeEvent = new CloseEvent(this, ((AjaxBehaviorEvent) event).getBehavior());
                closeEvent.setPhaseId(ajaxBehaviorEvent.getPhaseId());
                super.queueEvent(closeEvent);
            }
            else if(eventName.equals("move")) {
                int top = Double.valueOf(params.get(clientId + "_top")).intValue();
                int left = Double.valueOf(params.get(clientId + "_left")).intValue();
                MoveEvent moveEvent = new MoveEvent(this, ((AjaxBehaviorEvent) event).getBehavior(), top, left);
                moveEvent.setPhaseId(ajaxBehaviorEvent.getPhaseId());
                super.queueEvent(moveEvent);
            }
            else {
                //minimize and maximize
                super.queueEvent(event);
            }
        } else {
            super.queueEvent(event);
        }
    }

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
        else {
            ValueExpression visibleVE = this.getValueExpression(PropertyKeys.visible.toString());
            if(visibleVE != null) {
                FacesContext facesContext = getFacesContext();
                ELContext eLContext = facesContext.getELContext();

                if(!visibleVE.isReadOnly(eLContext)) {
                    visibleVE.setValue(eLContext, this.isVisible());
                    this.getStateHelper().put(PropertyKeys.visible, null);
                }
            }
        }
    }


    public boolean isContentLoadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_contentLoad");
    }

    public boolean isRTL() {
        return this.getDir().equalsIgnoreCase("rtl");
    }
}