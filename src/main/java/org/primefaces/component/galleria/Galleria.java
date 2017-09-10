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
package org.primefaces.component.galleria;

import javax.faces.component.UIOutput;
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
	@ResourceDependency(library="primefaces", name="galleria/galleria.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="galleria/galleria.js")
})
@PFComponent(tagName = "galleria",
             description = "Galleria is used to display a set of images.",
             widget = true,
             parent = UIOutput.class)
public class Galleria extends AbstractGalleria {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Collection of images", type = Object.class, required = true)
		value,
		@PFProperty(description = "Name of the iterator to display a collection of images", required = true)
		var,
		@PFProperty(description = "Inline style of the container element")
		style,
		@PFProperty(description = "Style class of the container element")
		styleClass,
		@PFProperty(description = "Name of animation to use on transitions", defaultValue = "fade")
		effect,
		@PFProperty(description = "Duration of animation in milliseconds", defaultValue = "500", type = Integer.class)
		effectSpeed,
		@PFProperty(description = "Width of the filmstrip frames", defaultValue = "60", type = Integer.class)
		frameWidth,
		@PFProperty(description = "Height of the filmstrip frames", defaultValue = "40", type = Integer.class)
		frameHeight,
		@PFProperty(description = "Defines visibility of filmstrip", defaultValue = "true", type = Boolean.class)
		showFilmstrip,
		@PFProperty(description = "Images are displayed with a slideshow in autoPlay mode", defaultValue = "true", type = Boolean.class)
		autoPlay,
		@PFProperty(description = "Time in milliseconds between each slide in autoPlay mode", defaultValue = "4000", type = Integer.class)
		transitionInterval,
		@PFProperty(description = "Width of the content panel", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		panelWidth,
		@PFProperty(description = "Height of the content panel", defaultValue = "java.lang.Integer.MIN_VALUE", type = Integer.class)
		panelHeight,
		@PFProperty(description = "Displays information retrieved from title and alt attributes of images in content caption", defaultValue = "false", type = Boolean.class)
		showCaption,;
	}


    public final static String CONTAINER_CLASS = "ui-galleria ui-widget ui-widget-content ui-corner-all";
    public final static String PANEL_WRAPPER_CLASS = "ui-galleria-panel-wrapper";
    public final static String PANEL_CLASS = "ui-galleria-panel ui-helper-hidden";
    public final static String PANEL_CONTENT_CLASS = "ui-galleria-panel-content";
}