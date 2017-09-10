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
package org.primefaces.component.dnd;

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

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "draggable",
             description = "",
             widget = true,
             parent = UIComponentBase.class)
public class Draggable extends AbstractDraggable {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Displays proxy element instead of actual element", defaultValue = "false", type = Boolean.class)
		proxy,
		@PFProperty(description = "", defaultValue = "false", type = Boolean.class)
		dragOnly,
		@PFProperty(description = "Id of the component to add draggable behavior")
		forValue("for"),
		@PFProperty(description = "Disables or enables dragging", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Specifies drag axis, valid values are 'x' and 'y'")
		axis,
		@PFProperty(description = "Constraints dragging within the boundaries of containment element")
		containment,
		@PFProperty(description = "Helper element to display when dragging")
		helper,
		@PFProperty(description = "Reverts draggable to it's original position when not dropped onto a valid droppable", defaultValue = "false", type = Boolean.class)
		revert,
		@PFProperty(description = "Draggable will snap to edge of near elements", defaultValue = "false", type = Boolean.class)
		snap,
		@PFProperty(description = "Specifies the snap mode. Valid values are 'both', 'inner' and 'outer'")
		snapMode,
		@PFProperty(description = "Distance from the snap element in pixels to trigger snap", defaultValue = "20", type = Integer.class)
		snapTolerance,
		@PFProperty(description = "ZIndex to apply during dragging", defaultValue = "-1", type = Integer.class)
		zindex,
		@PFProperty(description = "Specifies a handle for dragging")
		handle,
		@PFProperty(description = "Defines the opacity of the helper during dragging", defaultValue = "1.0", type = Double.class)
		opacity,
		@PFProperty(description = "In stack mode, draggable overlap is controlled automatically using the provided selector, dragged item always overlays other draggables")
		stack,
		@PFProperty(description = "Dragging happens in every x and y pixels")
		grid,
		@PFProperty(description = "Scope key to match draggables and droppables")
		scope,
		@PFProperty(description = "CSS cursor to display in dragging", defaultValue = "crosshair")
		cursor,
		@PFProperty(description = "Id of the dashboard to connect with")
		dashboard,
		@PFProperty(description = "A search expression to define which element to append the draggable helper to")
		appendTo,;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return toString != null ? toString : super.toString();
		}
	}

}