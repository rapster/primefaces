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
package org.primefaces.component.graphicimage;

import javax.faces.component.html.HtmlGraphicImage;
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

@PFComponent(tagName = "graphicImage",
             description = "PrimeFaces GraphicImage extends standard JSF graphic image component with the ability of displaying binary data like an inputstream. \n      Main use cases of GraphicImage is to make displaying images stored in database or on-the-fly images easier. \n      Legacy way to do this is to come up with a Servlet that does the streaming, GraphicImage does all the hard work without the need of a Servlet.",
             parent = HtmlGraphicImage.class)
public class GraphicImage extends AbstractGraphicImage {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Controls browser caching mode of the resource", defaultValue = "true", type = Boolean.class)
		cache,
		@PFProperty(description = "Name of the image")
		name,
		@PFProperty(description = "Library name of the image")
		library,
		@PFProperty(description = "Defines if the image is streamed or rendered directly as data uri / base64 with ViewScoped support", defaultValue = "true", type = Boolean.class)
		stream,;
	}

}