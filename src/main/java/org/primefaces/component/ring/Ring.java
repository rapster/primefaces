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
package org.primefaces.component.ring;

import org.primefaces.component.api.UIData;
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
	@ResourceDependency(library="primefaces", name="ring/ring.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="ring/ring.js")
})
@PFComponent(tagName = "ring",
             description = "",
             widget = true,
             parent = UIData.class)
public class Ring extends AbstractRing {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIDataPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "")
		style,
		@PFProperty(description = "")
		styleClass,
		@PFProperty(description = "")
		easing,
		@PFProperty(description = "When true, Ring will automatically advance the moving elements to the next child at a regular interval", defaultValue = "false", type = Boolean.class)
		autoplay,
		@PFProperty(description = "Time in milliseconds between animation triggers when a Ring's autoplay is playing", defaultValue = "1000", type = Integer.class)
		autoplayDuration,
		@PFProperty(description = "When true, Ring will pause autoPlay when the user mouseover the Ring container", defaultValue = "false", type = Boolean.class)
		autoplayPauseOnHover,
		@PFProperty(description = "Time in milliseconds to delay the start of Ring's configured autoplay option", defaultValue = "0", type = Integer.class)
		autoplayInitialDelay,;
	}


    public static final String STYLE_CLASS = "ui-ring ui-widget";
}