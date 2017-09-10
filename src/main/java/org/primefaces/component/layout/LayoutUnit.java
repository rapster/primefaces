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
package org.primefaces.component.layout;

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
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;

@PFComponent(tagName = "layoutUnit",
             description = "LayoutUnit represents a region in the border layout model of the Layout component.",
             parent = UIComponentBase.class)
public class LayoutUnit extends AbstractLayoutUnit {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Position of the unit. Required", required = true)
		position,
		@PFProperty(description = "", defaultValue = "auto")
		size,
		@PFProperty(description = "Makes the unit resizable", defaultValue = "false", type = Boolean.class)
		resizable,
		@PFProperty(description = "Makes the unit closable", defaultValue = "false", type = Boolean.class)
		closable,
		@PFProperty(description = "Makes the unit collapsible", defaultValue = "false", type = Boolean.class)
		collapsible,
		@PFProperty(description = "Text of header")
		header,
		@PFProperty(description = "Text of footer")
		footer,
		@PFProperty(description = "", defaultValue = "50", type = Integer.class)
		minSize,
		@PFProperty(description = "", defaultValue = "0", type = Integer.class)
		maxSize,
		@PFProperty(description = "Gutter size of layout unit", defaultValue = "6", type = Integer.class)
		gutter,
		@PFProperty(description = "Specifies default visibility", defaultValue = "true", type = Boolean.class)
		visible,
		@PFProperty(description = "Specifies toggle status of unit", defaultValue = "false", type = Boolean.class)
		collapsed,
		@PFProperty(description = "Size of the unit when collapsed", defaultValue = "25", type = Integer.class)
		collapseSize,
		@PFProperty(description = "Style to apply to container element")
		style,
		@PFProperty(description = "Style class to apply to container element")
		styleClass,
		@PFProperty(description = "Effect name of the layout transitions")
		effect,
		@PFProperty(description = "Effect speeds of the layout transitions")
		effectSpeed,;
	}


    public String getCollapseIcon() {
        return "ui-icon-triangle-1-" + this.getPosition().substring(0,1);
    }

    public boolean isNesting() {
        for(UIComponent child : getChildren()) {
            if(child instanceof Layout)
                return true;
        }

        return false;
    }
}