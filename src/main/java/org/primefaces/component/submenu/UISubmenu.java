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
package org.primefaces.component.submenu;

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
import java.util.List;
import org.primefaces.model.menu.MenuElement;

@PFComponent(tagName = "submenu",
             description = "Submenu is nested in menu components and represents a sub menu items.",
             parent = UIComponentBase.class)
public class UISubmenu extends AbstractUISubmenu implements org.primefaces.model.menu.Submenu {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Label of the submenu header")
		label,
		@PFProperty(description = "Icon of a submenu, see menuitem to see how it is used")
		icon,
		@PFProperty(description = "Inline style of the menu")
		style,
		@PFProperty(description = "Style class of the submenu")
		styleClass,
		@PFProperty(description = "Defines the state of the submenu", defaultValue = "false", type = Boolean.class)
		expanded,;
	}

   
    public List getElements() {
        return getChildren();
    }
    
    public int getElementsCount() {
        return getChildCount();
    }
}