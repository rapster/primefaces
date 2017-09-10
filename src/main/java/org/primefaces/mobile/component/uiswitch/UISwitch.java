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
package org.primefaces.mobile.component.uiswitch;

import javax.faces.component.UIInput;
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

@PFComponent(tagName = "switch",
             description = "",
             widget = true,
             parent = UIInput.class)
public class UISwitch extends AbstractUISwitch {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Custom label for on state", defaultValue = "on")
		onLabel,
		@PFProperty(description = "Custom label for off state", defaultValue = "off")
		offLabel,
		@PFProperty(description = "User presentable name")
		label,
		@PFProperty(description = "Disables or enables the switch", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Client side callback to execute on value change event")
		onchange,
		@PFProperty(description = "Inline style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,;
	}


    public static String CONTAINER_CLASS = "ui-flipswitch ui-shadow-inset ui-bar-inherit ui-corner-all";
    public static String ON_CLASS = "ui-flipswitch-on ui-btn ui-shadow ui-btn-inherit";
    public static String OFF_CLASS = "ui-flipswitch-off";
    public static String INPUT_CLASS = "ui-flipswitch-input";
}