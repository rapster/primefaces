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
package org.primefaces.component.texteditor;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.faces.event.AjaxBehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="texteditor/texteditor.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="texteditor/texteditor.js")
})
@PFComponent(tagName = "textEditor",
             description = "Editor is an input component with rich text editing capabilities.",
             widget = true,
             rtl = true,
             parent = UIInput.class)
public class TextEditor extends AbstractTextEditor implements javax.faces.component.behavior.ClientBehaviorHolder {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Height of the editor. Default is min integer value", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		height,
		@PFProperty(description = "Whether to instantiate the editor to read-only mode", defaultValue = "false", type = Boolean.class)
		readonly,
		@PFProperty(description = "Inline style of the editor container")
		style,
		@PFProperty(description = "Style class of the editor container")
		styleClass,
		@PFProperty(description = "Placeholder text to show when editor is empty.")
		placeholder,
		@PFProperty(description = "show / hide the editor toolbar visibility", defaultValue = "true", type = Boolean.class)
		toolbarVisible,;
	}


    private static final Collection<String> EVENT_NAMES=Collections.unmodifiableCollection(Arrays.asList("blur","change","click","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select"));

    @Override
    public Collection<String> getEventNames(){
        return EVENT_NAMES;
    }

    public String getDefaultEventName() {
        return "change";
    }
}