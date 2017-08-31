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
package org.primefaces.component.outputlabel;

import javax.faces.component.html.HtmlOutputLabel;
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

@PFComponent(tagName = "outputLabel",
             description = "OutputLabel is an extension to the standard outputLabel.")
public abstract class OutputLabelCore extends HtmlOutputLabel implements IOutputLabel {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Displays * symbol if the input is required, default value is true", defaultValue = "true", type = Boolean.class)
		indicateRequired,;
	}


    public final static String STYLE_CLASS = "ui-outputlabel ui-widget";
    public final static String REQUIRED_FIELD_INDICATOR_CLASS = "ui-outputlabel-rfi";
}