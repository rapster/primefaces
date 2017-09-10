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
package org.primefaces.component.editor;

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
	@ResourceDependency(library="primefaces", name="editor/editor.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="editor/editor.js")
})
@PFComponent(tagName = "editor",
             description = "Editor is an input component with rich text editing capabilities.",
             widget = true,
             parent = UIInput.class)
public class Editor extends AbstractEditor {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "List of controls to customize toolbar")
		controls,
		@PFProperty(description = "Height of the editor", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		height,
		@PFProperty(description = "Width of the editor", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		width,
		@PFProperty(description = "Disables editor", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Inline style of the editor container")
		style,
		@PFProperty(description = "Style class of the editor container")
		styleClass,
		@PFProperty(description = "Client side callback to execute when editor data changes")
		onchange,
		@PFProperty(description = "Maximum length for input", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		maxlength,;
	}

}