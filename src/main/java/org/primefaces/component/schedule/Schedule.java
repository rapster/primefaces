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
package org.primefaces.component.schedule;

import javax.faces.component.UIComponentBase;
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
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TimeZone;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.util.Constants;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.ScheduleEvent;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="schedule/schedule.css"),
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="moment/moment.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="schedule/schedule.js")
})
@PFComponent(tagName = "schedule",
             description = "Schedule provides an Outlook Calendar, iCal like JSF component to manage events.\n      Schedule is highly customizable featuring various views (month, day, week), built-in I18N, drag-drop, resize, customizable event dialog and skinning.",
             widget = true,
             parent = UIComponentBase.class)
public class Schedule extends AbstractSchedule implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "An org.primefaces.model.ScheduleModel instance representing the backed model", type = org.primefaces.model.ScheduleModel.class)
		value,
		@PFProperty(description = "Locale for localization, can be String or a java.util.Locale instance", type = Object.class)
		locale,
		@PFProperty(description = "Ratio of calendar width to height, higher the value shorter the height is", defaultValue = "java.lang.Double.MIN_VALUE", type = Double.class)
		aspectRatio,
		@PFProperty(description = "The view type to use, possible values are 'month', 'agendaDay', 'agendaWeek', 'basicWeek', 'basicDay'", defaultValue = "month")
		view,
		@PFProperty(description = "The initial date that is used when schedule loads. If ommitted, the schedule starts on the current date", type = Object.class)
		initialDate,
		@PFProperty(description = "Specifies inclusion Saturday/Sunday columns in any of the views", defaultValue = "true", type = Boolean.class)
		showWeekends,
		@PFProperty(description = "Style of the main container element of schedule")
		style,
		@PFProperty(description = "Style class of the main container element of schedule")
		styleClass,
		@PFProperty(description = "When true, events are draggable", defaultValue = "true", type = Boolean.class)
		draggable,
		@PFProperty(description = "When true, events are resizable", defaultValue = "true", type = Boolean.class)
		resizable,
		@PFProperty(description = "Specifies visibility of header content", defaultValue = "true", type = Boolean.class)
		showHeader,
		@PFProperty(description = "Content of left side of header", defaultValue = "prev,next today")
		leftHeaderTemplate,
		@PFProperty(description = "Content of center of header", defaultValue = "title")
		centerHeaderTemplate,
		@PFProperty(description = "Content of right side of header", defaultValue = "month,agendaWeek,agendaDay")
		rightHeaderTemplate,
		@PFProperty(description = "Determines if all-day slot will be displayed in agendaWeek or agendaDay views", defaultValue = "true", type = Boolean.class)
		allDaySlot,
		@PFProperty(description = "The frequency for displaying time slots", defaultValue = "00:30:00")
		slotDuration,
		@PFProperty(description = "Interval in minutes in an hour to create a slot", defaultValue = "30", type = Integer.class)
		slotMinutes,
		@PFProperty(description = "Determines how far down the scroll pane is initially scrolled down", defaultValue = "06:00:00")
		scrollTime,
		@PFProperty(description = "First hour to display in day view", defaultValue = "6", type = Integer.class)
		firstHour,
		@PFProperty(description = "Minimum time to display in a day view")
		minTime,
		@PFProperty(description = "Maximum time to display in a day view")
		maxTime,
		@PFProperty(description = "Determines the time-text that will be displayed on the vertical axis of the agenda views")
		axisFormat,
		@PFProperty(description = "Determines the time-text that will be displayed on each event")
		timeFormat,
		@PFProperty(description = "Format for column headers")
		columnFormat,
		@PFProperty(description = "String or a java.util.TimeZone instance to specify the timezone used for date conversion to ISO_8601 format, defaults to \"UTC\"", type = Object.class)
		timeZone,
		@PFProperty(description = "Timezone to define how to interpret the dates at browser. Valid values are \"false\", \"local\", \"UTC\" and ids like \"America/Chicago\"")
		clientTimeZone,
		@PFProperty(description = "When parsing dates, whether UTC offsets should be ignored while processing event data", defaultValue = "true", type = Boolean.class)
		ignoreTimezone,
		@PFProperty(description = "Displays description of events on a tooltip, default value is false", defaultValue = "false", type = Boolean.class)
		tooltip,
		@PFProperty(description = "Display week numbers in schedule", defaultValue = "false", type = Boolean.class)
		showWeekNumbers,
		@PFProperty(description = "Name of javascript function to extend the options of the underlying fullcalendar plugin")
		extender,
		@PFProperty(description = "Whether or not to display an event's end time text when it is rendered on the calendar. Value can be a boolean to globally configure for\n all views or a comma separated list such as \"month:false,basicWeek:true\" to configure per view")
		displayEventEnd,
		@PFProperty(description = "The method for calculating week numbers that are displayed. Valid values are \"local\" (default), \"ISO\" and \"custom\"", defaultValue = "local")
		weekNumberCalculation,
		@PFProperty(description = "Client side function to use in custom weekNumberCalculation")
		weekNumberCalculator,
		@PFProperty(description = "When an event's end time spans into another day, the minimum time it must be in order for it to render as if it were on that day", defaultValue = "09:00:00")
		nextDayThreshold,
		@PFProperty(description = "If true contemporary events will be rendered one overlapping the other, else they will be rendered side by side", defaultValue = "true", type = Boolean.class)
		slotEventOverlap,
		@PFProperty(description = "Target for events with urls. Clicking on such events in the schedule will not trigger the selectEvent but open the url using this target instead", defaultValue = "_blank")
		urlTarget,;
	}


    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("dateSelect", SelectEvent.class);
        put("eventSelect", SelectEvent.class);
        put("eventMove", ScheduleEntryMoveEvent.class);
        put("eventResize", ScheduleEntryResizeEvent.class);
        put("viewChange", SelectEvent.class);
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


	private java.util.Locale appropriateLocale;
	
	java.util.Locale calculateLocale(FacesContext facesContext) {
		if(appropriateLocale == null) {
			Object userLocale = getLocale();
			if(userLocale != null) {
				if(userLocale instanceof String)
					appropriateLocale = new java.util.Locale((String) userLocale, "");
				else if(userLocale instanceof java.util.Locale)
					appropriateLocale = (java.util.Locale) userLocale;
				else
					throw new IllegalArgumentException("Type:" + userLocale.getClass() + " is not a valid locale type for calendar:" + this.getClientId(facesContext));
			} else {
				appropriateLocale = facesContext.getViewRoot().getLocale();
			}
		}
		
		return appropriateLocale;
	}

    private TimeZone appropriateTimeZone;

    java.util.TimeZone calculateTimeZone() {
		if(appropriateTimeZone == null) {
			Object usertimeZone = getTimeZone();
			if(usertimeZone != null) {
				if(usertimeZone instanceof String)
					appropriateTimeZone =  TimeZone.getTimeZone((String) usertimeZone);
				else if(usertimeZone instanceof java.util.TimeZone)
					appropriateTimeZone = (TimeZone) usertimeZone;
				else
					throw new IllegalArgumentException("TimeZone could be either String or java.util.TimeZone");
			} else {
				appropriateTimeZone = DEFAULT_TIME_ZONE;
			}
		}
		
		return appropriateTimeZone;
	}

	@Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
        String clientId = this.getClientId(context);
        TimeZone tz = calculateTimeZone();

        if(isSelfRequest(context)) {

            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
            FacesEvent wrapperEvent = null;

            if(eventName.equals("dateSelect")) {
                Long milliseconds = Long.valueOf(params.get(clientId + "_selectedDate"));
                Calendar calendar = Calendar.getInstance(tz);
                calendar.setTimeInMillis(milliseconds - tz.getOffset(milliseconds));
                Date selectedDate = calendar.getTime();
                SelectEvent selectEvent = new SelectEvent(this, behaviorEvent.getBehavior(), selectedDate);
                selectEvent.setPhaseId(behaviorEvent.getPhaseId());

                wrapperEvent = selectEvent;
            }
            else if(eventName.equals("eventSelect")) {
                String selectedEventId = params.get(clientId + "_selectedEventId");
				ScheduleEvent selectedEvent = this.getValue().getEvent(selectedEventId);

                wrapperEvent = new SelectEvent(this, behaviorEvent.getBehavior(), selectedEvent);
            }
            else if(eventName.equals("eventMove")) {
                String movedEventId = params.get(clientId + "_movedEventId");
				ScheduleEvent movedEvent = this.getValue().getEvent(movedEventId);
                int dayDelta = (int) Double.parseDouble(params.get(clientId + "_dayDelta"));
				int minuteDelta = (int) Double.parseDouble(params.get(clientId + "_minuteDelta"));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(movedEvent.getStartDate());
                calendar.setTimeZone(tz);
                calendar.add(Calendar.DATE, dayDelta);
                calendar.add(Calendar.MINUTE, minuteDelta);
                movedEvent.getStartDate().setTime(calendar.getTimeInMillis());

                calendar = Calendar.getInstance();
                calendar.setTime(movedEvent.getEndDate());
                calendar.setTimeZone(tz);
                calendar.add(Calendar.DATE, dayDelta);
                calendar.add(Calendar.MINUTE, minuteDelta);
				movedEvent.getEndDate().setTime(calendar.getTimeInMillis());

                wrapperEvent = new ScheduleEntryMoveEvent(this, behaviorEvent.getBehavior(), movedEvent, dayDelta, minuteDelta);
            }
            else if(eventName.equals("eventResize")) {
                String resizedEventId = params.get(clientId + "_resizedEventId");
				ScheduleEvent resizedEvent = this.getValue().getEvent(resizedEventId);
                int dayDelta = Integer.valueOf(params.get(clientId + "_dayDelta"));
				int minuteDelta = Integer.valueOf(params.get(clientId + "_minuteDelta"));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(resizedEvent.getEndDate());
                calendar.setTimeZone(tz);
				calendar.add(Calendar.DATE, dayDelta);
                calendar.add(Calendar.MINUTE, minuteDelta);
				resizedEvent.getEndDate().setTime(calendar.getTimeInMillis());

                wrapperEvent = new ScheduleEntryResizeEvent(this, behaviorEvent.getBehavior(), resizedEvent, dayDelta, minuteDelta);
            }
            else if(eventName.equals("viewChange")) {
				wrapperEvent = new SelectEvent(this, behaviorEvent.getBehavior(), this.getView());
            }

            wrapperEvent.setPhaseId(behaviorEvent.getPhaseId());

            super.queueEvent(wrapperEvent);
        }
        else {
            super.queueEvent(event);
        }
    }

    private boolean isSelfRequest(FacesContext context) {
        return this.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(!isRendered()) {
            return;
        }

        super.processUpdates(context);

        ValueExpression expr = this.getValueExpression(PropertyKeys.view.toString());
        if(expr != null) {
            expr.setValue(getFacesContext().getELContext(), this.getView());
            getStateHelper().remove(PropertyKeys.view);
        }
    }
}