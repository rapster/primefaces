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
package org.primefaces.component.barcode;

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

@PFComponent(tagName = "barcode",
             description = "Barcode component is used to display various barcode formats.",
             parent = HtmlGraphicImage.class)
public class Barcode extends AbstractBarcode {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Type of the barcode")
		type,
		@PFProperty(description = "Controls browser caching mode of the resource", defaultValue = "true", type = Boolean.class)
		cache,
		@PFProperty(description = "Format of the generated barcode, valid values are png (default) and svg", defaultValue = "svg")
		format,
		@PFProperty(description = "The barcode orientation in terms of angle (0, 90, 180, 270)", defaultValue = "0", type = Integer.class)
		orientation,;
	}

}