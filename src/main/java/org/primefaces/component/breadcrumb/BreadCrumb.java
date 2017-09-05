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
package org.primefaces.component.breadcrumb;

import org.primefaces.component.menu.AbstractUIMenu;
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
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "breadCrumb",
             description = "Breadcrumb is a navigation component that provides contextual information about page hierarchy in the workflow.",
             widget = true,
             rtl = true,
             parent = AbstractUIMenu.class)
public class BreadCrumb extends AbstractBreadCrumb {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "MenuModel instance to create menus programmatically", type = org.primefaces.model.menu.MenuModel.class)
		model,
		@PFProperty(description = "Style of main container element")
		style,
		@PFProperty(description = "Style class of main container")
		styleClass,
		@PFProperty(description = "Defines display mode of root link, valid values are \"icon\" default and \"text\"", defaultValue = "icon")
		homeDisplay,;
	}


    public final static String CONTAINER_CLASS = "ui-breadcrumb ui-module ui-widget ui-widget-header ui-helper-clearfix ui-corner-all";
    public final static String CHEVRON_CLASS = "ui-breadcrumb-chevron ui-icon ui-icon-triangle-1-e";
    public final static String OPTIONS_CLASS = "ui-breadcrumb-options";
}