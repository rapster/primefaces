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
package org.primefaces.component.imagecropper;

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
	@ResourceDependency(library="primefaces", name="imagecropper/imagecropper.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="imagecropper/imagecropper.js")
})
@PFComponent(tagName = "imageCropper",
             description = "ImageCropper allows cropping a certain region of an image. A new image is created containing the cropped area and assigned to a CroppedImage instanced on the server side.",
             widget = true,
             rtl = true)
public abstract class ImageCropperCore extends UIInput implements IImageCropper {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Context relative path to the image")
		image,
		@PFProperty(description = "Alternate text of the image")
		alt,
		@PFProperty(description = "Aspect ratio of the cropper area", defaultValue = "java.lang.Double.MIN_VALUE", type = Double.class)
		aspectRatio,
		@PFProperty(description = "Minimum size of the cropper area")
		minSize,
		@PFProperty(description = "Maximum size of the cropper area")
		maxSize,
		@PFProperty(description = "Background color of the container")
		backgroundColor,
		@PFProperty(description = "Background opacity of the container. Default is 0.6", defaultValue = "0.6", type = Double.class)
		backgroundOpacity,
		@PFProperty(description = "Initial coordinates of the cropper area")
		initialCoords,
		@PFProperty(description = "Maximum box width of the cropping area", defaultValue = "0", type = Integer.class)
		boxWidth,
		@PFProperty(description = "Maximum box height of the cropping area", defaultValue = "0", type = Integer.class)
		boxHeight,;
	}

}