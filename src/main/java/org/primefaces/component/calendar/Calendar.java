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
package org.primefaces.component.calendar;

import javax.faces.component.html.HtmlInputText;
import org.primefaces.component.api.InputHolder;
import org.primefaces.component.api.MixedClientBehaviorHolder;
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
import org.primefaces.event.DateViewChangeEvent;
import org.primefaces.util.HTML;
import org.primefaces.util.ArrayUtils;
import org.primefaces.util.Constants;
import org.primefaces.util.ComponentUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import org.primefaces.context.RequestContext;
import org.primefaces.convert.DateTimeConverter;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "calendar",
             description = "Calendar is an input component used to provide a date. Other than basic features calendar supports paging, localization, ajax selection and more.",
             widget = true,
             parent = HtmlInputText.class)
public class Calendar extends AbstractCalendar implements InputHolder, MixedClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputTextPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "The placeholder attribute specifies a short hint that describes the expected value of an input field")
		placeholder,
		@PFProperty(description = "Sets calendar's minimum visible date", type = Object.class)
		mindate,
		@PFProperty(description = "Sets calendar's maximum visible date", type = Object.class)
		maxdate,
		@PFProperty(description = "Enables multiple page rendering", defaultValue = "1", type = Integer.class)
		pages,
		@PFProperty(description = "Defines how the calendar will be displayed", defaultValue = "popup")
		mode,
		@PFProperty(description = "DateFormat pattern for localization")
		pattern,
		@PFProperty(description = "Locale to be used for labels and conversion", type = Object.class)
		locale,
		@PFProperty(description = "Enables month/year navigator", defaultValue = "false", type = Boolean.class)
		navigator,
		@PFProperty(description = "String or a java.util.TimeZone instance to specify the timezone used for date conversion, defaults to TimeZone.getDefault()", type = Object.class)
		timeZone,
		@PFProperty(description = "Makes input text of a popup calendar readonly", defaultValue = "false", type = Boolean.class)
		readonlyInput,
		@PFProperty(description = "Visibility of button panel containing today and done buttons", defaultValue = "false", type = Boolean.class)
		showButtonPanel,
		@PFProperty(description = "Effect to use when displaying and showing the popup calendar")
		effect,
		@PFProperty(description = "Duration of the effect", defaultValue = "normal")
		effectDuration,
		@PFProperty(description = "Client side event that displays the popup calendar", defaultValue = "focus")
		showOn,
		@PFProperty(description = "Displays the week number next to each week", defaultValue = "false", type = Boolean.class)
		showWeek,
		@PFProperty(description = "Disables weekend columns", defaultValue = "false", type = Boolean.class)
		disabledWeekends,
		@PFProperty(description = "Displays days belonging to other months", defaultValue = "false", type = Boolean.class)
		showOtherMonths,
		@PFProperty(description = "Enables selection of days belonging to other months", defaultValue = "false", type = Boolean.class)
		selectOtherMonths,
		@PFProperty(description = "Year range for the navigator")
		yearRange,
		@PFProperty(description = "Shows only time picker without date", defaultValue = "false", type = Boolean.class)
		timeOnly,
		@PFProperty(description = "Hour steps", defaultValue = "1", type = Integer.class)
		stepHour,
		@PFProperty(description = "Minute steps", defaultValue = "1", type = Integer.class)
		stepMinute,
		@PFProperty(description = "Second steps", defaultValue = "1", type = Integer.class)
		stepSecond,
		@PFProperty(description = "Minimum boundary for hour selection", defaultValue = "0", type = Integer.class)
		minHour,
		@PFProperty(description = "Maximum boundary for hour selection", defaultValue = "23", type = Integer.class)
		maxHour,
		@PFProperty(description = "Minimum boundary for minute selection", defaultValue = "0", type = Integer.class)
		minMinute,
		@PFProperty(description = "Maximum boundary for minute selection", defaultValue = "59", type = Integer.class)
		maxMinute,
		@PFProperty(description = "Minimum boundary for second selection", defaultValue = "0", type = Integer.class)
		minSecond,
		@PFProperty(description = "Maximum boundary for second selection", defaultValue = "59", type = Integer.class)
		maxSecond,
		@PFProperty(description = "Sets the initial date when value is not populated", type = Object.class)
		pagedate,
		@PFProperty(description = "Callback to execute before displaying a date, used to customize date display")
		beforeShowDay,
		@PFProperty(description = "Defines if a mask should be applied to the input field. Default value is \"false\" and valid values to enable are \"true\" that uses the pattern as the mask or a custom template. Refer to\n inputMask component for more information about custom templates", defaultValue = "false")
		mask,
		@PFProperty(description = "Defines the type of element to use for time picker, valid values are \"slider\" , \"select\" and \"custom\"(with \"timeControlObject\" attribute)", defaultValue = "slider")
		timeControlType,
		@PFProperty(description = "Callback to execute before displaying calendar, element and calendar instance are passed as parameters")
		beforeShow,
		@PFProperty(description = "PlaceHolder in mask template")
		maskSlotChar,
		@PFProperty(description = "Clears the field on blur when incomplete input is entered", defaultValue = "true", type = Boolean.class)
		maskAutoClear,
		@PFProperty(description = "Client side object to use in custom timeControlType")
		timeControlObject,
		@PFProperty(description = "Allows direct input in time field", defaultValue = "false", type = Boolean.class)
		timeInput,
		@PFProperty(description = "Whether to show the hour control, valid values are \"true\" and \"false\"")
		showHour,
		@PFProperty(description = "Whether to show the minute control, valid values are \"true\" and \"false\"")
		showMinute,
		@PFProperty(description = "Whether to show the second control, valid values are \"true\" and \"false\"")
		showSecond,
		@PFProperty(description = "Whether to show the millisecond control, valid values are \"true\" and \"false\"")
		showMillisec,
		@PFProperty(description = "Whether to show the \"Current Date\" button if showButtonPanel is rendered", defaultValue = "true", type = Boolean.class)
		showTodayButton,
		@PFProperty(description = "Position of the button in the tabbing order")
		buttonTabindex,
		@PFProperty(description = "Inline style of the input element. Used when mode is popup")
		inputStyle,
		@PFProperty(description = "Style class of the input element. Used when mode is popup")
		inputStyleClass,
		@PFProperty(description = "Input field type", defaultValue = "text")
		type,
		@PFProperty(description = "If enabled, the input is focused again after selecting a date", defaultValue = "false", type = Boolean.class)
		focusOnSelect,;
	}


    public final static String CONTAINER_CLASS = "ui-calendar";
    public final static String INPUT_STYLE_CLASS = "ui-inputfield ui-widget ui-state-default ui-corner-all";
    public final static String MOBILE_POPUP_CONTAINER_CLASS = "ui-calendar ui-calendar-popup";
    public final static String MOBILE_INLINE_CONTAINER_CLASS = "ui-calendar ui-calendar-inline";

    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur","change","valueChange","click","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select","dateSelect","viewChange"));
    private static final Collection<String> UNOBSTRUSIVE_EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("dateSelect","viewChange"));

    private Map<String,AjaxBehaviorEvent> customEvents = new HashMap<String,AjaxBehaviorEvent>();

	private java.util.Locale calculatedLocale;
	private java.util.TimeZone appropriateTimeZone;
	
	public java.util.Locale calculateLocale(FacesContext facesContext) {
		if(calculatedLocale == null) {
			Object userLocale = getLocale();
			if(userLocale != null) {
				if(userLocale instanceof String) {
					calculatedLocale = ComponentUtils.toLocale((String) userLocale);
				}
				else if(userLocale instanceof java.util.Locale)
					calculatedLocale = (java.util.Locale) userLocale;
				else
					throw new IllegalArgumentException("Type:" + userLocale.getClass() + " is not a valid locale type for calendar:" + this.getClientId(facesContext));
			} else {
				calculatedLocale = facesContext.getViewRoot().getLocale();
			}
		}
		
		return calculatedLocale;
	}
	
	public java.util.TimeZone calculateTimeZone() {
		if(appropriateTimeZone == null) {
			Object usertimeZone = getTimeZone();
			if(usertimeZone != null) {
				if(usertimeZone instanceof String)
					appropriateTimeZone =  java.util.TimeZone.getTimeZone((String) usertimeZone);
				else if(usertimeZone instanceof java.util.TimeZone)
					appropriateTimeZone = (java.util.TimeZone) usertimeZone;
				else
					throw new IllegalArgumentException("TimeZone could be either String or java.util.TimeZone");
			} else {
				appropriateTimeZone = java.util.TimeZone.getDefault();
			}
		}
		
		return appropriateTimeZone;
	}
	
	public boolean isPopup() {
		return getMode().equalsIgnoreCase("popup");
	}

    public boolean hasTime() {
        String pattern = getPattern();

        return (pattern != null && (pattern.contains("HH") || pattern.contains("mm") || pattern.contains("ss")));
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public Collection<String> getUnobstrusiveEventNames() {
        return UNOBSTRUSIVE_EVENT_NAMES;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(ComponentUtils.isRequestSource(this, context) && (event instanceof AjaxBehaviorEvent)) {
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            String clientId = this.getClientId(context);
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

            if(eventName != null) {
                if(eventName.equals("dateSelect")) {
                    customEvents.put("dateSelect", (AjaxBehaviorEvent) event);
                }
                else if(eventName.equals("viewChange")) {
                    int month = Integer.parseInt(params.get(clientId + "_month"));
                    int year = Integer.parseInt(params.get(clientId + "_year"));
                    DateViewChangeEvent dateViewChangeEvent = new DateViewChangeEvent(this, behaviorEvent.getBehavior(), month, year);
                    dateViewChangeEvent.setPhaseId(behaviorEvent.getPhaseId());
                    super.queueEvent(dateViewChangeEvent);
                }
                else {
                    super.queueEvent(event);        //regular events like change, click, blur
                }
            } 
        }
        else {
            super.queueEvent(event);            //valueChange
        }
    }

    @Override
    public void validate(FacesContext context) {
        super.validate(context);
       
        if(isValid() && ComponentUtils.isRequestSource(this, context)) {
            for(Iterator<String> customEventIter = customEvents.keySet().iterator(); customEventIter.hasNext();) {
                AjaxBehaviorEvent behaviorEvent = customEvents.get(customEventIter.next());
                SelectEvent selectEvent = new SelectEvent(this, behaviorEvent.getBehavior(), this.getValue());

                if(behaviorEvent.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES)) {
                    selectEvent.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
                }
                else {
                    selectEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                }

                super.queueEvent(selectEvent);
            }
        }
    }

    public String calculatePattern() {
        String pattern = this.getPattern();
        Locale locale = this.calculateLocale(getFacesContext());

        return pattern == null ? ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale)).toPattern() : pattern;
    }

    private String timeOnlyPattern = null;

    public String calculateTimeOnlyPattern() {
        if(timeOnlyPattern == null) {
            String localePattern = ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, calculateLocale(getFacesContext()))).toPattern();
            String userTimePattern = getPattern();

            timeOnlyPattern = localePattern + " " + userTimePattern;
        }

        return timeOnlyPattern;
    }

    private boolean conversionFailed = false;

    public void setConversionFailed(boolean value) {
        this.conversionFailed = value;
    }

    public boolean isConversionFailed() {
        return this.conversionFailed;
    }

    public String getInputClientId() {
        return this.getClientId(getFacesContext()) + "_input";
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

    @Override
    public Converter getConverter() {
        Converter converter = super.getConverter();
        
        if(converter == null && RequestContext.getCurrentInstance(getFacesContext()).getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            DateTimeConverter con = new DateTimeConverter();
            con.setPattern(this.calculatePattern());
            con.setTimeZone(this.calculateTimeZone());
            con.setLocale(this.calculateLocale(getFacesContext()));

            return con;
        }
        
        return converter;
    }

}