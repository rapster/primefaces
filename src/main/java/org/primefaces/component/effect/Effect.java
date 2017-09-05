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
package org.primefaces.component.effect;

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
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "effect",
             description = "Effect component is based on the jQuery UI effects library.",
             widget = true,
             rtl = true,
             parent = UIComponentBase.class)
public class Effect extends AbstractEffect {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Dom event to attach the event that executes the animation. Required", required = true)
		event,
		@PFProperty(description = "Specifies the name of the animation. Required", required = true)
		type,
		@PFProperty(description = "Component that is animated")
		forValue("for"),
		@PFProperty(description = "Speed of the animation in ms. Default is 1000", defaultValue = "1000", type = Integer.class)
		speed,
		@PFProperty(description = "Startup delay on firing animation", defaultValue = "0", type = Integer.class)
		delay,
		@PFProperty(description = "Specifies if effects should be queued. Default is true", defaultValue = "true", type = Boolean.class)
		queue,;

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