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
package org.primefaces.component.timeline;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UniqueIdVendor;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import org.primefaces.event.timeline.TimelineAddEvent;
import org.primefaces.event.timeline.TimelineDragDropEvent;
import org.primefaces.event.timeline.TimelineLazyLoadEvent;
import org.primefaces.event.timeline.TimelineModificationEvent;
import org.primefaces.event.timeline.TimelineRangeEvent;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineGroup;
import org.primefaces.model.timeline.TimelineModel;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.Constants;
import org.primefaces.util.DateUtils;
import org.primefaces.visit.UIDataContextCallback;
import javax.faces.event.BehaviorEvent;
import org.primefaces.util.ComponentTraversalUtils;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="timeline/timeline.css"),
	@ResourceDependency(library="primefaces", name="timeline/timeline.js")
})
@PFComponent(tagName = "timeline",
             description = "Timeline is an interactive visualization chart to visualize events in time.",
             widget = true,
             rtl = true)
public abstract class TimelineCore extends UIComponentBase implements ITimeline, javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Inline style of the container element")
		style,
		@PFProperty(description = "Style class of the container element")
		styleClass,
		@PFProperty(description = "Name of the request-scoped variable for underlaying object in the TimelineEvent for each iteration")
		var,
		@PFProperty(description = "An instance of TimelineModel representing the backing model", type = org.primefaces.model.timeline.TimelineModel.class, required = true)
		value,
		@PFProperty(description = "Name of the request-scoped variable for underlaying object in the TimelineGroup for each iteration")
		varGroup,
		@PFProperty(description = "User locale for i18n messages. The attribute can be either a String or Locale object", type = Object.class)
		locale,
		@PFProperty(description = "Target time zone to convert start / end dates for displaying. This time zone is the time zone the user would like to see dates in UI.\n The attribute can be either a String or TimeZone object or null. If null, timeZone defaults to the server's time zone the application is running in", type = Object.class)
		timeZone,
		@PFProperty(description = "Time zone the user's browser / PC is running in. This time zone allows to correct the conversion of start / end dates to the target timeZone for displaying.\n The attribute can be either a String or TimeZone object or null. Note: browserTimeZone should be provided if the target timeZone is provided. If null, browserTimeZone defaults to the server's timeZone", type = Object.class)
		browserTimeZone,
		@PFProperty(description = "The height of the timeline in pixels, as a percentage, or \"auto\". When the height is set to \"auto\", the height of the timeline is automatically adjusted to fit the contents.\n If not, it is possible that events get stacked so high, that they are not visible in the timeline. When height is set to \"auto\", a minimum height can be specified with the option minHeight. Default is \"auto\"", defaultValue = "auto")
		height,
		@PFProperty(description = "Specifies a minimum height for the Timeline in pixels. Useful when height is set to \"auto\". Default is 0", defaultValue = "0", type = Integer.class)
		minHeight,
		@PFProperty(description = "The width of the timeline in pixels or as a percentage. Default is \"100%\"", defaultValue = "100%")
		width,
		@PFProperty(description = "Check if the timeline container is resized, and if so, resize the timeline.\n Useful when the webpage (browser window) or a layout pane / unit containing the timeline component is resized. Default is true", defaultValue = "true", type = Boolean.class)
		responsive,
		@PFProperty(description = "If false, the horizontal axis is drawn at the bottom. If true, the axis is drawn on top. Default is false", defaultValue = "false", type = Boolean.class)
		axisOnTop,
		@PFProperty(description = "The width of the drag areas in pixels. When an event with date range is selected, it has a drag area on the left and right side, with which the start or end dates of the event can be manipulated.\n Default is 10", defaultValue = "10", type = Integer.class)
		dragAreaWidth,
		@PFProperty(description = "If true, the events can be edited, changed, created and deleted. Events can only be editable when the option selectable is true (default). When editable is true, the timeline can fire AJAX events \"change\", \"edit\", \"add\", \"delete\", \"drop\".\n This global setting \"editable\" can be overwritten for individual events by setting a value in field \"editable\". Default is false", defaultValue = "false", type = Boolean.class)
		editable,
		@PFProperty(description = "If true, events on the timeline are selectable. Selectable events can fire AJAX \"select\" events. Default is true", defaultValue = "true", type = Boolean.class)
		selectable,
		@PFProperty(description = "If true, you can unselect an item by clicking in the empty space of the timeline. If false, you cannot unselect an item, there will be always one item selected. Default is true", defaultValue = "true", type = Boolean.class)
		unselectable,
		@PFProperty(description = "If true, the timeline is zoomable. When the timeline is zoomed, AJAX \"rangechange\" events are fired. Default is true", defaultValue = "true", type = Boolean.class)
		zoomable,
		@PFProperty(description = "If true, the timeline is movable. When the timeline is moved, AJAX \"rangechange\" events are fired. Default is true", defaultValue = "true", type = Boolean.class)
		moveable,
		@PFProperty(description = "The initial start date for the axis of the timeline. If not provided, the earliest date present in the events is taken as start date. Default is null", type = java.util.Date.class)
		start,
		@PFProperty(description = "The initial end date for the axis of the timeline. If not provided, the latest date present in the events is taken as end date. Default is null", type = java.util.Date.class)
		end,
		@PFProperty(description = "Set a minimum Date for the visible range. It will not be possible to move beyond this minimum. Default is null", type = java.util.Date.class)
		min,
		@PFProperty(description = "Set a maximum Date for the visible range. It will not be possible to move beyond this maximum. Default is null", type = java.util.Date.class)
		max,
		@PFProperty(description = "Set a minimum zoom interval for the visible range in milliseconds. It will not be possible to zoom in further than this minimum. Default is 10", defaultValue = "10L", type = Long.class)
		zoomMin,
		@PFProperty(description = "Set a maximum zoom interval for the visible range in milliseconds. It will not be possible to zoom out further than this maximum. Default value equals 315360000000000 ms (about 10000 years)", defaultValue = "315360000000000L", type = Long.class)
		zoomMax,
		@PFProperty(description = "Preload factor is a positive float value or 0 which can be used for lazy loading of events. When the lazy loading feature is active, the calculated time range for preloading will be multiplicated by the preload factor. The result of this multiplication specifies the additional time range which will be considered for the preloading during moving / zooming too. For example, if the calculated time range for preloading is 5 days and the preload factor is 0.2, the result is 5 * 0.2 = 1 day. That means, 1 day backwards and / or 1 day onwards will be added to the original calculated time range. The event's area to be preloaded is wider then. This helps to avoid frequently, time-consuming fetching of events. Default value is 0", defaultValue = "0.0f", type = Float.class)
		preloadFactor,
		@PFProperty(description = "The minimal margin in pixels between events. Default is 10", defaultValue = "10", type = Integer.class)
		eventMargin,
		@PFProperty(description = "The minimal margin in pixels between events and the horizontal axis. Default is 10", defaultValue = "10", type = Integer.class)
		eventMarginAxis,
		@PFProperty(description = "Specifies the style for the timeline events. Choose from \"dot\" or \"box\". Default is \"box\"", defaultValue = "box")
		eventStyle,
		@PFProperty(description = "If true, items can be moved from one group to another. Only applicable when groups are used. Default is true", defaultValue = "true", type = Boolean.class)
		groupsChangeable,
		@PFProperty(description = "If false, the groups legend is drawn at the left side of the timeline. If true, the groups legend is drawn on the right side. Default is false", defaultValue = "false", type = Boolean.class)
		groupsOnRight,
		@PFProperty(description = "Allows to customize the way groups are ordered. When true (default), groups will be ordered by content alphabetically (when the list of groups is missing) or by native ordering of TimelineGroup object in the list of groups (when the list of groups is available). When false, groups will not be ordered at all", defaultValue = "true", type = Boolean.class)
		groupsOrder,
		@PFProperty(description = "By default, the width of the groups legend is adjusted to the group names. A fixed width can be set for the groups legend by specifying the \"groupsWidth\" as a string, for example \"200px\". Default is null")
		groupsWidth,
		@PFProperty(description = "The minimum height of each individual group even if they have no items. The group height is set as the greatest value between items height and the groupMinHeight. Default is 0", defaultValue = "0", type = Integer.class)
		groupMinHeight,
		@PFProperty(description = "If true, the start and end of an event will be snapped nice integer values when moving or resizing the event. Default is true", defaultValue = "true", type = Boolean.class)
		snapEvents,
		@PFProperty(description = "If true, the events are stacked above each other to prevent overlapping events. Default is true", defaultValue = "true", type = Boolean.class)
		stackEvents,
		@PFProperty(description = "If true, the timeline shows a red, vertical line displaying the current time. Default is true", defaultValue = "true", type = Boolean.class)
		showCurrentTime,
		@PFProperty(description = "By default, the timeline shows both minor and major date labels on the horizontal axis.\n For example the minor labels show minutes and the major labels show hours. When \"showMajorLabels\" is false, no major labels are shown.\n Default is true", defaultValue = "true", type = Boolean.class)
		showMajorLabels,
		@PFProperty(description = "By default, the timeline shows both minor and major date labels on the horizontal axis.\n For example the minor labels show minutes and the major labels show hours. When \"showMinorLabels\" is false, no minor labels are shown.\n When both \"showMajorLabels\" and \"showMinorLabels\" are false, no horizontal axis will be visible. Default is true", defaultValue = "true", type = Boolean.class)
		showMinorLabels,
		@PFProperty(description = "Show the button \"Create new event\" in the a navigation menu. Default is false", defaultValue = "false", type = Boolean.class)
		showButtonNew,
		@PFProperty(description = "Show a navigation menu with buttons to move and zoom the timeline. Default is false", defaultValue = "false", type = Boolean.class)
		showNavigation,
		@PFProperty(description = "If false, items can not be moved or dragged horizontally (neither start time nor end time is changable).\n This is useful when items should be editable but can only be changed regarding group or content (typical use case: scheduling events).\n Default is true", defaultValue = "true", type = Boolean.class)
		timeChangeable,
		@PFProperty(description = "Style class to apply when an acceptable draggable is dragged over. Default is null")
		dropHoverStyleClass,
		@PFProperty(description = "Style class to apply when an acceptable draggable is being dragged over. Default is null")
		dropActiveStyleClass,
		@PFProperty(description = "Selector to define the accepted draggables. Default is null")
		dropAccept,
		@PFProperty(description = "Scope key to match draggables and droppables. Default is null")
		dropScope,
		@PFProperty(description = "When true, events are moved animated when resizing or moving them. This is very pleasing for the eye, but does require more computational power. Default is true", defaultValue = "true", type = Boolean.class)
		animate,
		@PFProperty(description = "When true, events are moved animated when zooming the Timeline. This looks cool, but does require more computational power. Default is true", defaultValue = "true", type = Boolean.class)
		animateZoom,;
	}


    private final static Logger logger = Logger.getLogger(Timeline.class.getName());
    
    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("add", TimelineAddEvent.class);
        put("change", TimelineModificationEvent.class);
        put("changed", TimelineModificationEvent.class);
        put("edit", TimelineModificationEvent.class);
        put("delete", TimelineModificationEvent.class);
        put("select", TimelineSelectEvent.class);
        put("rangechange", TimelineRangeEvent.class);
        put("rangechanged", TimelineRangeEvent.class);
        put("lazyload", TimelineLazyLoadEvent.class);
        put("drop", TimelineDragDropEvent.class);
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
    public void queueEvent(FacesEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (isSelfRequest(context)) {
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            String clientId = this.getClientId(context);

            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

            if ("add".equals(eventName)) {
                // preset start / end date and the group
                TimeZone targetTZ = ComponentUtils.resolveTimeZone(getTimeZone());
                TimeZone browserTZ = ComponentUtils.resolveTimeZone(getBrowserTimeZone());

                TimelineAddEvent te =
                        new TimelineAddEvent(this, behaviorEvent.getBehavior(),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_startDate")),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_endDate")),
                                getGroup(params.get(clientId + "_group")));
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            } else if ("change".equals(eventName) || "changed".equals(eventName)) {
                TimelineEvent clonedEvent = null;
                TimelineEvent timelineEvent = this.getValue().getEvent(params.get(clientId + "_eventIdx"));

                if (timelineEvent != null) {
                    clonedEvent = new TimelineEvent();
                    clonedEvent.setData(timelineEvent.getData());
                    clonedEvent.setEditable(timelineEvent.isEditable());
                    clonedEvent.setStyleClass(timelineEvent.getStyleClass());

                    // update start / end date and the group
                    TimeZone targetTZ = ComponentUtils.resolveTimeZone(getTimeZone());
                    TimeZone browserTZ = ComponentUtils.resolveTimeZone(getBrowserTimeZone());
                    clonedEvent.setStartDate(DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_startDate")));
                    clonedEvent.setEndDate(DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_endDate")));
                    clonedEvent.setGroup(getGroup(params.get(clientId + "_group")));
                }

                TimelineModificationEvent te = new TimelineModificationEvent(this, behaviorEvent.getBehavior(), clonedEvent);
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            } else if ("edit".equals(eventName) || "delete".equals(eventName)) {
                TimelineEvent clonedEvent = null;
                TimelineEvent timelineEvent = this.getValue().getEvent(params.get(clientId + "_eventIdx"));

                if (timelineEvent != null) {
                    clonedEvent = new TimelineEvent();
                    clonedEvent.setData(timelineEvent.getData());
                    clonedEvent.setStartDate((Date) timelineEvent.getStartDate().clone());
                    clonedEvent.setEndDate(timelineEvent.getEndDate() != null ? (Date) timelineEvent.getEndDate().clone() : null);
                    clonedEvent.setEditable(timelineEvent.isEditable());
                    clonedEvent.setGroup(timelineEvent.getGroup());
                    clonedEvent.setStyleClass(timelineEvent.getStyleClass());
                }

                TimelineModificationEvent te = new TimelineModificationEvent(this, behaviorEvent.getBehavior(), clonedEvent);
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            } else if ("select".equals(eventName)) {
                TimelineEvent timelineEvent = this.getValue().getEvent(params.get(clientId + "_eventIdx"));
                TimelineSelectEvent te = new TimelineSelectEvent(this, behaviorEvent.getBehavior(), timelineEvent);
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            } else if ("rangechange".equals(eventName) || "rangechanged".equals(eventName)) {
                TimeZone targetTZ = ComponentUtils.resolveTimeZone(getTimeZone());
                TimeZone browserTZ = ComponentUtils.resolveTimeZone(getBrowserTimeZone());

                TimelineRangeEvent te =
                        new TimelineRangeEvent(this, behaviorEvent.getBehavior(),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_startDate")),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_endDate")));
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            } else if ("lazyload".equals(eventName)) {
                TimeZone targetTZ = ComponentUtils.resolveTimeZone(getTimeZone());
                TimeZone browserTZ = ComponentUtils.resolveTimeZone(getBrowserTimeZone());

                TimelineLazyLoadEvent te =
                        new TimelineLazyLoadEvent(this, behaviorEvent.getBehavior(),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_startDateFirst")),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_endDateFirst")),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_startDateSecond")),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_endDateSecond")));
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            } else if ("drop".equals(eventName)) {
                Object data = null;
                final String dragId = params.get(clientId + "_dragId");
                final String uiDataId = params.get(clientId + "_uiDataId");

                if (dragId != null && uiDataId != null) {
                    // draggable is within a data iteration component
                    UIDataContextCallback contextCallback = new UIDataContextCallback(dragId);
                    context.getViewRoot().invokeOnComponent(context, uiDataId, contextCallback);
                    data = contextCallback.getData();
                }

                // preset start / end date, group, dragId and data object
                TimeZone targetTZ = ComponentUtils.resolveTimeZone(getTimeZone());
                TimeZone browserTZ = ComponentUtils.resolveTimeZone(getBrowserTimeZone());

                TimelineDragDropEvent te =
                        new TimelineDragDropEvent(this, behaviorEvent.getBehavior(),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_startDate")),
                                DateUtils.toUtcDate(browserTZ, targetTZ, params.get(clientId + "_endDate")),
                                getGroup(params.get(clientId + "_group")), dragId, data);
                te.setPhaseId(behaviorEvent.getPhaseId());
                super.queueEvent(te);

                return;
            }
        }

        super.queueEvent(event);
    }

    private String getGroup(String groupParam) {
        List<TimelineGroup> groups = this.getValue().getGroups();
        if (groups == null || groupParam == null) {
            return groupParam;
        }

        int idx = groupParam.indexOf("</span>");
        if (idx > -1) {
            groupParam = groupParam.substring(0, idx);
            int idxGroupOrder = groupParam.indexOf("#");
            if (idxGroupOrder > -1) {
                String groupOrder = groupParam.substring(idxGroupOrder + 1);
                return groups.get(Integer.valueOf(groupOrder)).getId();
            }
        }

        return groupParam;
    }

    private boolean isSelfRequest(FacesContext context) {
        return this.getClientId(context)
                .equals(context.getExternalContext().getRequestParameterMap().get(
                        Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }




}