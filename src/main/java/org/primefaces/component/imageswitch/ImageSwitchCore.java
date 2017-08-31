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
package org.primefaces.component.imageswitch;

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
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="imageswitch/imageswitch.js")
})
@PFComponent(tagName = "imageSwitch",
             description = "Imageswitch component is used to enable switching between a set of images with nice effects. ImageSwitch also provides a simple client side api for flexibility.",
             widget = true,
             rtl = true)
public abstract class ImageSwitchCore extends UIComponentBase implements IImageSwitch {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Style of the main container")
		style,
		@PFProperty(description = "Style class of the main container")
		styleClass,
		@PFProperty(description = "Name of the effect for transition. Required", required = true)
		effect,
		@PFProperty(description = "Speed of the effect in milliseconds. Default is 500", defaultValue = "500", type = Integer.class)
		speed,
		@PFProperty(description = "Slideshow speed in milliseconds. Default is 3000", defaultValue = "3000", type = Integer.class)
		slideshowSpeed,
		@PFProperty(description = "Starts slideshow automatically on page load. Default is true", defaultValue = "true", type = Boolean.class)
		slideshowAuto,
		@PFProperty(description = "Index of the first image, default is 0", defaultValue = "0", type = Integer.class)
		activeIndex,;
	}

}