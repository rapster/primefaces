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
package org.primefaces.component.selectonemenu;

import javax.faces.component.html.HtmlSelectOneMenu;
import org.primefaces.component.api.InputHolder;
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
import org.primefaces.component.column.Column;
import org.primefaces.config.PrimeConfiguration;
import org.primefaces.context.RequestContext;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.MessageFactory;
import org.primefaces.util.Constants;
import org.primefaces.event.SelectEvent;
import java.util.Map;
import javax.faces.render.Renderer;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "selectOneMenu",
             description = "",
             widget = true,
             parent = HtmlSelectOneMenu.class)
public class SelectOneMenu extends AbstractSelectOneMenu implements InputHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "")
		effect,
		@PFProperty(description = "")
		effectSpeed,
		@PFProperty(description = "")
		panelStyle,
		@PFProperty(description = "")
		panelStyleClass,
		@PFProperty(description = "")
		var,
		@PFProperty(description = "", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		height,
		@PFProperty(description = "", defaultValue = "false", type = Boolean.class)
		editable,
		@PFProperty(description = "Displays an input filter for the list", defaultValue = "false", type = Boolean.class)
		filter,
		@PFProperty(description = "Match mode for filtering, valid values are startsWith (default), contains, endsWith and custom")
		filterMatchMode,
		@PFProperty(description = "Client side function to use in custom filterMatchMode")
		filterFunction,
		@PFProperty(description = "Defines if filtering would be case sensitive", defaultValue = "false", type = Boolean.class)
		caseSensitive,
		@PFProperty(description = "Maximum number of characters that may be entered in this field", defaultValue = "Integer.MAX_VALUE", type = Integer.class)
		maxlength,
		@PFProperty(description = "Appends the overlay to the element defined by search expression. Defaults to document body")
		appendTo,
		@PFProperty(description = "Advisory tooltip information")
		title,
		@PFProperty(description = "Updates the title of the component with the description of the selected item", defaultValue = "false", type = Boolean.class)
		syncTooltip,
		@PFProperty(description = "Displays label of the element in a custom template. Valid placeholder is {0}")
		labelTemplate,
		@PFProperty(description = "Watermark displayed in the input field before the user enters a value in an HTML5 browser")
		placeholder,
		@PFProperty(description = "Calculates a fixed width based on the width of the maximum option label. Set to false for custom width", defaultValue = "true", type = Boolean.class)
		autoWidth,
		@PFProperty(description = "Defines if lazy loading is enabled for the element. If the value is \"true\", the overlay is not rendered on window load to improve performance", defaultValue = "false", type = Boolean.class)
		lazy,;
	}


    public final static String STYLE_CLASS = "ui-selectonemenu ui-widget ui-state-default ui-corner-all";
    public final static String LABEL_CLASS = "ui-selectonemenu-label ui-inputfield ui-corner-all";
    public final static String TRIGGER_CLASS = "ui-selectonemenu-trigger ui-state-default ui-corner-right";
    public final static String PANEL_CLASS = "ui-selectonemenu-panel ui-widget ui-widget-content ui-corner-all ui-helper-hidden ui-shadow";
    public final static String ITEMS_WRAPPER_CLASS = "ui-selectonemenu-items-wrapper";
    public final static String LIST_CLASS = "ui-selectonemenu-items ui-selectonemenu-list ui-widget-content ui-widget ui-corner-all ui-helper-reset";
    public final static String TABLE_CLASS = "ui-selectonemenu-items ui-selectonemenu-table ui-widget-content ui-widget ui-corner-all ui-helper-reset";
    public final static String ITEM_GROUP_CLASS = "ui-selectonemenu-item-group ui-corner-all";
    public final static String ITEM_CLASS = "ui-selectonemenu-item ui-selectonemenu-list-item ui-corner-all";
    public final static String ROW_CLASS = "ui-selectonemenu-item ui-selectonemenu-row ui-widget-content";
    public final static String FILTER_CONTAINER_CLASS = "ui-selectonemenu-filter-container";
    public final static String FILTER_CLASS = "ui-selectonemenu-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all";
    public final static String FILTER_ICON_CLASS = "ui-icon ui-icon-search";

    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("itemSelect","blur","change","valueChange","click","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select"));

    public Collection<String> getEventNames() {
        return EVENT_NAMES;    
    }

    public boolean isLazyloadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_lazyload");
    }

    public String getDefaultEventName() {
        return "valueChange";    
    }

    public List<Column> getColums() {
        List<Column> columns = new ArrayList<Column>();
        
        for(UIComponent kid : this.getChildren()) {
            if(kid instanceof Column)
                columns.add((Column) kid);
        }

        return columns;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        if(event instanceof AjaxBehaviorEvent) {
            FacesContext context = getFacesContext();
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            
            if("itemSelect".equals(eventName)) {
                Renderer renderer = ComponentUtils.getUnwrappedRenderer(
                    context,
                    "javax.faces.SelectOne",
                    "javax.faces.Menu",
                    Renderer.class);
                
                Object item = renderer.getConvertedValue(context, this, this.getSubmittedValue());
                SelectEvent selectEvent = new SelectEvent(this, behaviorEvent.getBehavior(), item);
                selectEvent.setPhaseId(event.getPhaseId());
                super.queueEvent(selectEvent);
            }
            else {
                super.queueEvent(event);
            }
        }
        else {
            super.queueEvent(event);
        }
    }

    @Override
    protected void validateValue(FacesContext context, Object value) {
        if(this.isEditable()) {
            
            //required field validation
            if(isValid() && isRequired() && isEmpty(value)) {
                String requiredMessageStr = getRequiredMessage();
                FacesMessage message;
                if(null != requiredMessageStr) {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                               requiredMessageStr,
                                               requiredMessageStr);
                } else {                    
                    message = MessageFactory.getMessage(REQUIRED_MESSAGE_ID, FacesMessage.SEVERITY_ERROR, new Object[]{MessageFactory.getLabel(context, this)});
                }
                context.addMessage(getClientId(context), message);
                setValid(false);
            }

            PrimeConfiguration config = RequestContext.getCurrentInstance(getFacesContext()).getApplicationContext().getConfig();
            
            //other validators
            if(isValid() && (!isEmpty(value) || config.isValidateEmptyFields())) {
                Validator[] validators = getValidators();
                    
                for(Validator validator : validators) {
                    try {
                        validator.validate(context, this, value);
                    }
                    catch(ValidatorException ve) {
                        setValid(false);
                        FacesMessage message;
                        String validatorMessageString = getValidatorMessage();

                        if(null != validatorMessageString) {
                            message =new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessageString, validatorMessageString);
                        } 
                        else {
                            Collection<FacesMessage> messages = ve.getFacesMessages();
                            
                            if(null != messages) {
                                message = null;
                                String cid = getClientId(context);
                                for(FacesMessage m : messages) {
                                    context.addMessage(cid, m);
                                }
                            } 
                            else {
                                message = ve.getFacesMessage();
                            }
                        }
                        
                        if(message != null) {
                            context.addMessage(getClientId(context), message);
                        }
                    }
                }
            }
        }
        else {
            super.validateValue(context, value);
        }
    }

    public String getInputClientId() {
        return this.getClientId(getFacesContext()) + "_focus";
    }

    public String getValidatableInputClientId() {
        return this.getClientId(getFacesContext()) + "_input";
    }

    public void setLabelledBy(String labelledBy) {
        getStateHelper().put("labelledby", labelledBy);
    }
    public String getLabelledBy() {
        return (String) getStateHelper().get("labelledby");
    }

    


    
}