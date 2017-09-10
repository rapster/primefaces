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
package org.primefaces.component.clock;

import javax.faces.component.UIOutput;
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
import java.util.Map; 

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="clock/clock.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="raphael/raphael.js"),
	@ResourceDependency(library="primefaces", name="clock/clock.js")
})
@PFComponent(tagName = "clock",
             description = "Clock displays server or client datetime live.",
             widget = true,
             parent = UIOutput.class)
public class Clock extends AbstractClock {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Datetime format")
		pattern,
		@PFProperty(description = "Mode of the client, valid values are client and server", defaultValue = "client")
		mode,
		@PFProperty(description = "Syncs time periodically in server mode", defaultValue = "false", type = Boolean.class)
		autoSync,
		@PFProperty(description = "Defines the sync interval in autoSync mode", defaultValue = "60000", type = Integer.class)
		syncInterval,
		@PFProperty(description = "String or a java.util.TimeZone instance to specify the timezone used for date conversion, defaults to TimeZone.getDefault()", type = Object.class)
		timeZone,
		@PFProperty(description = "Display mode, valid values are digital(default) and analog", defaultValue = "digital")
		displayMode,;
	}


    public final static String STYLE_CLASS = "ui-clock ui-widget ui-widget-header ui-corner-all";
    public final static String ANALOG_STYLE_CLASS = "ui-analog-clock ui-widget";

    public boolean isSyncRequest() {
        FacesContext context = getFacesContext();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
    
        return params.containsKey(this.getClientId(context) + "_sync");
    }

    private java.util.TimeZone appropriateTimeZone;

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
    

}