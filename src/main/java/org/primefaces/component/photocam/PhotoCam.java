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
package org.primefaces.component.photocam;

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

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="photocam/photocam.js")
})
@PFComponent(tagName = "photoCam",
             description = ".",
             widget = true,
             rtl = true,
             parent = UIInput.class)
public class PhotoCam extends AbstractPhotoCam {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Style of the camera container")
		style,
		@PFProperty(description = "Style class of the camera container")
		styleClass,
		@PFProperty(description = "Identifiers of components to process during photo capture")
		process,
		@PFProperty(description = "Identifiers of components to update after photo capture")
		update,
		@PFProperty(description = "Method expression to listen capture events", type = javax.el.MethodExpression.class)
		listener,
		@PFProperty(description = "Width of the camera viewport", defaultValue = "320", type = Integer.class)
		width,
		@PFProperty(description = "Height of the camera viewport", defaultValue = "240", type = Integer.class)
		height,
		@PFProperty(description = "Width of the captured photo, defaults to width", defaultValue = "320", type = Integer.class)
		photoWidth,
		@PFProperty(description = "Height of the captured photo, defaults to height", defaultValue = "240", type = Integer.class)
		photoHeight,
		@PFProperty(description = "Format of the image, valid values are \"jpeg\" default and png")
		format,
		@PFProperty(description = "Quality of the image between 0 and 100 when the format is jpeg, default value is 90", defaultValue = "90", type = Integer.class)
		jpegQuality,
		@PFProperty(description = "Enables always using flash fallback even in an HTML5 environment", defaultValue = "false", type = Boolean.class)
		forceFlash,
		@PFProperty(description = "Disable camera start after initialize", defaultValue = "true", type = Boolean.class)
		autoStart,;
	}


    public void broadcast(javax.faces.event.FacesEvent event) throws javax.faces.event.AbortProcessingException {
		super.broadcast(event);
		
		FacesContext facesContext = getFacesContext();
		MethodExpression me = getListener();
		
		if (me != null && event instanceof org.primefaces.event.CaptureEvent) {
			me.invoke(facesContext.getELContext(), new Object[] {event});
		}
	}
}