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
package org.primefaces.component.notificationbar;

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
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "notificationBar",
             description = "NotificationBar displayes a multipurpose fixed positioned panel for notification. Any group of JSF content can be placed inside notificationbar.",
             widget = true,
             rtl = true,
             parent = UIComponentBase.class)
public class NotificationBar extends AbstractNotificationBar {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Style of the container element")
		style,
		@PFProperty(description = "StyleClass of the container element")
		styleClass,
		@PFProperty(description = "Position of the bar, \"top\" or \"bottom\". Default is top", defaultValue = "top")
		position,
		@PFProperty(description = "Name of the effect, \"fade\", \"slide\" or \"none\". Default is fade", defaultValue = "fade")
		effect,
		@PFProperty(description = "Speed of the effect, \"slow\", \"normal\" or \"fast\". Default is normal", defaultValue = "normal")
		effectSpeed,
		@PFProperty(description = "", defaultValue = "false", type = Boolean.class)
		autoDisplay,;
	}

  
  public final static String STYLE_CLASS = "ui-notificationbar ui-widget ui-widget-content";
}