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
package org.primefaces.component.checkbox;

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
@PFComponent(tagName = "checkbox",
             description = "",
             widget = true,
             rtl = true,
             parent = UIComponentBase.class)
public class Checkbox extends AbstractCheckbox {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Disables the component", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Index of the selectItem of the SelectManyCheckbox", defaultValue = "0", type = Integer.class)
		itemIndex,
		@PFProperty(description = "Client side callback to execute on state change")
		onchange,
		@PFProperty(description = "Id of the SelectManyCheckbox component to attach to")
		forValue("for"),
		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "The tabindex attribute specifies the tab order of an element when the \"tab\" button is used for navigating")
		tabindex,;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return toString != null ? toString : super.toString();
		}
	}

}