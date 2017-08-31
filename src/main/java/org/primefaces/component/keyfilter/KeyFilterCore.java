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
package org.primefaces.component.keyfilter;

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
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="keyfilter/keyfilter.js")
})
@PFComponent(tagName = "keyFilter",
             description = "KeyFilter filters keyboard input for a given input field.",
             widget = true,
             rtl = true)
public abstract class KeyFilterCore extends UIComponentBase implements IKeyFilter {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "The target input")
		forValue("for"),
		@PFProperty(description = "Defines the regular expression which should be used for filtering the input")
		regEx,
		@PFProperty(description = "Defines the regular expression which should be used to test the complete input text content")
		inputRegEx,
		@PFProperty(description = "Defines the predefined mask which should be used (pint, int, pnum, num, hex, email, alpha, alphanum)")
		mask,
		@PFProperty(description = "Defines a javascript code or function which should be used for filtering")
		testFunction,
		@PFProperty(description = "Boolean value to specify if the component also should prevent paste. Default value is true", defaultValue = "true", type = Boolean.class)
		preventPaste,;

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