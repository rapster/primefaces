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
package org.primefaces.component.ajaxstatus;

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

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js")
})
@PFComponent(tagName = "ajaxStatus",
             description = "AjaxStatus is a global notifier for ajax requests made by PrimeFaces components.",
             widget = true,
             parent = UIComponentBase.class)
public class AjaxStatus extends AbstractAjaxStatus {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Client side callback to execute after ajax requests start")
		onstart,
		@PFProperty(description = "Client side callback to execute after ajax requests complete")
		oncomplete,
		@PFProperty(description = "Client side callback to execute after ajax requests completed succesfully")
		onsuccess,
		@PFProperty(description = "Client side callback to execute when an ajax request fails")
		onerror,
		@PFProperty(description = "Inline style of the container element")
		style,
		@PFProperty(description = "Style class of the container element")
		styleClass,;
	}


    public final static String START = "start";
    public final static String SUCCESS= "success";
    public final static String COMPLETE = "complete";
    public final static String ERROR = "error";
    public final static String DEFAULT = "default";
    public final static String CALLBACK_SIGNATURE = "function()";

	public final static String[] EVENTS = {
        START,SUCCESS,COMPLETE,ERROR
    };
}