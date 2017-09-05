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
package org.primefaces.component.autocomplete;

import javax.faces.component.html.HtmlInputText;
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
import org.primefaces.component.calendar.Calendar;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;
import org.primefaces.util.ArrayUtils;
import org.primefaces.util.Constants;
import org.primefaces.component.column.Column;
import javax.faces.component.UIComponent;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.el.ValueExpression;
import javax.faces.convert.Converter;
import javax.faces.component.behavior.Behavior;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "autoComplete",
             description = "AutoComplete provides live suggestions while an input is being typed.",
             widget = true,
             rtl = true,
             parent = HtmlInputText.class)
public class AutoComplete extends AbstractAutoComplete implements org.primefaces.component.api.InputHolder, org.primefaces.component.api.MixedClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Method providing suggestions", type = javax.el.MethodExpression.class)
		completeMethod,
		@PFProperty(description = "Name of the iterator used in pojo based suggestion")
		var,
		@PFProperty(description = "Label of the item")
		itemLabel,
		@PFProperty(description = "Value of the item", type = Object.class)
		itemValue,
		@PFProperty(description = "A string to be rendered onto the class tag of the selected items (tokens rendered inside the input container)")
		itemStyleClass,
		@PFProperty(description = "Maximum number of results to be displayed. Default is unlimited", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		maxResults,
		@PFProperty(description = "Number of characters to be typed before starting to query. Default is 1", defaultValue = "1", type = Integer.class)
		minQueryLength,
		@PFProperty(description = "Delay to wait in milliseconds before sending each query to the server. Default is 300", defaultValue = "300", type = Integer.class)
		queryDelay,
		@PFProperty(description = "When enabled, autoComplete only accepts input from the selection list. Default is false", defaultValue = "false", type = Boolean.class)
		forceSelection,
		@PFProperty(description = "Defines the height of the viewport for autocomplete suggestions", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		scrollHeight,
		@PFProperty(description = "")
		effect,
		@PFProperty(description = "", defaultValue = "400", type = Integer.class)
		effectDuration,
		@PFProperty(description = "", defaultValue = "false", type = Boolean.class)
		dropdown,
		@PFProperty(description = "")
		panelStyle,
		@PFProperty(description = "")
		panelStyleClass,
		@PFProperty(description = "", defaultValue = "false", type = Boolean.class)
		multiple,
		@PFProperty(description = "Position of itemtip with respect to item. Default is \"left top\"")
		itemtipMyPosition,
		@PFProperty(description = "Position of item with respect to item. Default is \"right bottom\"")
		itemtipAtPosition,
		@PFProperty(description = "When enabled autocomplete caches the searched result list", defaultValue = "false", type = Boolean.class)
		cache,
		@PFProperty(description = "Timeout value for cached results", defaultValue = "300000", type = Integer.class)
		cacheTimeout,
		@PFProperty(description = "Text to display when there is no data to display")
		emptyMessage,
		@PFProperty(description = "Appends the overlay to the element defined by search expression. Defaults to document body")
		appendTo,
		@PFProperty(description = "Hint text for screen readers to provide information about the search results. Default is \"{0} results found, use arrow keys to navigate\"")
		resultsMessage,
		@PFProperty(description = "Value to group items in categories", type = Object.class)
		groupBy,
		@PFProperty(description = "Event to initiate the query, valid values are \"keyup\" and \"enter\"")
		queryEvent,
		@PFProperty(description = "Specifies the behavior dropdown button. Default \"blank\" mode\n sends an empty string and \"current\" mode sends the input value")
		dropdownMode,
		@PFProperty(description = "Highlights the first suggested item automatically. Default is true", defaultValue = "true", type = Boolean.class)
		autoHighlight,
		@PFProperty(description = "Limits the selection. Default is unlimited", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		selectLimit,
		@PFProperty(description = "Inline style of the input element")
		inputStyle,
		@PFProperty(description = "Style class of the input element")
		inputStyleClass,
		@PFProperty(description = "Tooltip to display on group headers")
		groupByTooltip,
		@PFProperty(description = "Position of panel with respect to input Default is \"left top\"")
		my,
		@PFProperty(description = "Position of input with respect to panel Default is \"left bottom\"")
		at,
		@PFProperty(description = "Defines if autocomplete functionality is enabled. Default is true and a false value simply turns the component into a simple inputtext", defaultValue = "true", type = Boolean.class)
		active,
		@PFProperty(description = "Input field type. Default is text", defaultValue = "text")
		type,
		@PFProperty(description = "The text shown in panel when the suggested list is greater than maxResults", defaultValue = "...")
		moreText,
		@PFProperty(description = "Ensures uniqueness of selected items", defaultValue = "false", type = Boolean.class)
		unique,;
	}


    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur","change","valueChange","click","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select", "itemSelect", "itemUnselect", "query", "moreText"));
    private static final Collection<String> UNOBSTRUSIVE_EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("itemSelect", "itemUnselect", "query", "moreText"));
    
    public final static String STYLE_CLASS = "ui-autocomplete";
    public final static String MULTIPLE_STYLE_CLASS = "ui-autocomplete ui-autocomplete-multiple";
    public final static String INPUT_CLASS = "ui-autocomplete-input ui-inputfield ui-widget ui-state-default ui-corner-all";
    public final static String INPUT_WITH_DROPDOWN_CLASS = "ui-autocomplete-input ui-autocomplete-dd-input ui-inputfield ui-widget ui-state-default ui-corner-left";
    public final static String DROPDOWN_CLASS = "ui-autocomplete-dropdown ui-button ui-widget ui-state-default ui-corner-right ui-button-icon-only";
    public final static String PANEL_CLASS = "ui-autocomplete-panel ui-widget-content ui-corner-all ui-helper-hidden ui-shadow";
    public final static String LIST_CLASS = "ui-autocomplete-items ui-autocomplete-list ui-widget-content ui-widget ui-corner-all ui-helper-reset";
    public final static String TABLE_CLASS = "ui-autocomplete-items ui-autocomplete-table ui-widget-content ui-widget ui-corner-all ui-helper-reset";
    public final static String ITEM_CLASS = "ui-autocomplete-item ui-autocomplete-list-item ui-corner-all";
    public final static String ROW_CLASS = "ui-autocomplete-item ui-autocomplete-row ui-widget-content ui-corner-all";
    public final static String TOKEN_DISPLAY_CLASS = "ui-autocomplete-token ui-state-active ui-corner-all";
    public final static String TOKEN_LABEL_CLASS = "ui-autocomplete-token-label";
    public final static String TOKEN_LABEL_DISABLED_CLASS = "ui-autocomplete-token-label-disabled";
    public final static String TOKEN_ICON_CLASS = "ui-autocomplete-token-icon ui-icon ui-icon-close";
    public final static String TOKEN_INPUT_CLASS = "ui-autocomplete-input-token";
    public final static String MULTIPLE_CONTAINER_CLASS = "ui-autocomplete-multiple-container ui-widget ui-inputfield ui-state-default ui-corner-all";
    public final static String MULTIPLE_CONTAINER_WITH_DROPDOWN_CLASS = "ui-autocomplete-multiple-container ui-autocomplete-dd-multiple-container ui-widget ui-inputfield ui-state-default ui-corner-left";
    public final static String ITEMTIP_CONTENT_CLASS = "ui-autocomplete-itemtip-content";
    public final static String MORE_TEXT_LIST_CLASS = "ui-autocomplete-item ui-autocomplete-moretext ui-corner-all";
    public final static String MORE_TEXT_TABLE_CLASS = "ui-autocomplete-item ui-autocomplete-moretext ui-widget-content ui-corner-all";

    public final static String MOBILE_INPUT_CONTAINER_CLASS = "ui-input-search ui-body-inherit ui-corner-all ui-shadow-inset ui-input-has-clear";
    public final static String MOBILE_PANEL_CLASS = "ui-controlgroup ui-controlgroup-vertical ui-corner-all ui-screen-hidden";
    public final static String MOBILE_ITEM_CONTAINER_CLASS = "ui-controlgroup-controls";
    public final static String MOBILE_ITEM_CLASS = "ui-autocomplete-item ui-btn ui-corner-all ui-shadow";
    public final static String MOBILE_CLEAR_ICON_CLASS = "ui-input-clear ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all ui-input-clear-hidden";

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public Collection<String> getUnobstrusiveEventNames() {
        return UNOBSTRUSIVE_EVENT_NAMES;
    }

    public boolean isMoreTextRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_moreText");
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);

        if(eventName != null && event instanceof AjaxBehaviorEvent) {
            AjaxBehaviorEvent ajaxBehaviorEvent = (AjaxBehaviorEvent) event;

            if(eventName.equals("itemSelect")) {
                Object selectedItemValue = convertValue(context, params.get(this.getClientId(context) + "_itemSelect"));
                SelectEvent selectEvent = new SelectEvent(this, (Behavior) ajaxBehaviorEvent.getBehavior(), selectedItemValue);
                selectEvent.setPhaseId(ajaxBehaviorEvent.getPhaseId());
                super.queueEvent(selectEvent);
            }
            else if(eventName.equals("itemUnselect")) {
                Object unselectedItemValue = convertValue(context, params.get(this.getClientId(context) + "_itemUnselect"));
                UnselectEvent unselectEvent = new UnselectEvent(this, (Behavior) ajaxBehaviorEvent.getBehavior(), unselectedItemValue);
                unselectEvent.setPhaseId(ajaxBehaviorEvent.getPhaseId());
                super.queueEvent(unselectEvent);
            }
            else if(eventName.equals("moreText")) {
                ajaxBehaviorEvent.setPhaseId(event.getPhaseId());
                super.queueEvent(ajaxBehaviorEvent);
            }
            else {
                //e.g. blur, focus, change
                super.queueEvent(event);
            }
        }
        else {
            //e.g. valueChange, autoCompleteEvent
            super.queueEvent(event);
        }
    }

    private List suggestions = null;

    public void broadcast(javax.faces.event.FacesEvent event) throws javax.faces.event.AbortProcessingException {
		super.broadcast(event);
		
		FacesContext facesContext = getFacesContext();
		MethodExpression me = getCompleteMethod();
		
		if(me != null && event instanceof org.primefaces.event.AutoCompleteEvent) {
			suggestions = (List) me.invoke(facesContext.getELContext(), new Object[] {((org.primefaces.event.AutoCompleteEvent) event).getQuery()});
            
            if(suggestions == null) {
                suggestions = new ArrayList();
            }

            facesContext.renderResponse();
		}
	}

    public List<Column> getColums() {
        List<Column> columns = new ArrayList<Column>();
        
        for(UIComponent kid : this.getChildren()) {
            if(kid instanceof Column)
                columns.add((Column) kid);
        }

        return columns;
    }

    public List getSuggestions() {
        return this.suggestions;
    }

    private Object convertValue(FacesContext context, String submittedItemValue) {
        Converter converter = ComponentUtils.getConverter(context, this);

        if(converter == null)
            return submittedItemValue;
        else 
            return converter.getAsObject(context, this, submittedItemValue);
    }

    public String getInputClientId() {
        return this.getClientId(getFacesContext()) + "_input";
    }

    public String getValidatableInputClientId() {
        return this.getInputClientId();
    }

    public void setLabelledBy(String labelledBy) {
        getStateHelper().put("labelledby", labelledBy);
    }
    public String getLabelledBy() {
        return (String) getStateHelper().get("labelledby");
    }
}