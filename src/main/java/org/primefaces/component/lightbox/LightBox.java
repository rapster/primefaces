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
package org.primefaces.component.lightbox;

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
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "lightBox",
             description = "Lightbox features a powerful overlay that can display images, multimedia content, other JSF components and external urls.",
             widget = true,
             rtl = true,
             parent = UIComponentBase.class)
public class LightBox extends AbstractLightBox {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Style of the container element not the overlay element")
		style,
		@PFProperty(description = "Style class of the container element not the overlay element")
		styleClass,
		@PFProperty(description = "Width of the iframe")
		width,
		@PFProperty(description = "Height of the iframe")
		height,
		@PFProperty(description = "Specifies an iframe to display an external url in overlay. Default is false", defaultValue = "false", type = Boolean.class)
		iframe,
		@PFProperty(description = "Title of the iframe element")
		iframeTitle,
		@PFProperty(description = "Displays lightbox without requiring any user interaction by default. Default is false", defaultValue = "false", type = Boolean.class)
		visible,
		@PFProperty(description = "Client side callback to execute when lightbox is displayed")
		onShow,
		@PFProperty(description = "Client side callback to execute when lightbox is hidden")
		onHide,;
	}

}