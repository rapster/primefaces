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
package org.primefaces.mobile.component.inputslider;

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

@PFComponent(tagName = "inputSlider",
             description = "",
             widget = true,
             rtl = true,
             parent = UIInput.class)
public class InputSlider extends AbstractInputSlider {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Minimum value of the slider. Default is 0", defaultValue = "0", type = Integer.class)
		minValue,
		@PFProperty(description = "Maximum value of the slider. Default is 100", defaultValue = "100", type = Integer.class)
		maxValue,
		@PFProperty(description = "Inline style of the container element")
		style,
		@PFProperty(description = "Style class of the container element")
		styleClass,
		@PFProperty(description = "Fixed pixel increments that the slider move in. Default is 1", defaultValue = "1", type = Integer.class)
		step,
		@PFProperty(description = "Disables or enables the slider", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "User presentable name")
		label,
		@PFProperty(description = "Highlights the value range when enabled", defaultValue = "false", type = Boolean.class)
		highlight,;
	}

}