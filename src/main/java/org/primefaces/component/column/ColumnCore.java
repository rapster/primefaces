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
package org.primefaces.component.column;

import javax.faces.component.UIColumn;
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
import org.primefaces.model.filter.*;
import org.primefaces.component.celleditor.CellEditor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.menu.MenuModel;

@PFComponent(tagName = "column",
             description = "Column is an extended version of the standard column used by various PrimeFaces components like datatable, treetable and more.")
public abstract class ColumnCore extends UIColumn implements IColumn, org.primefaces.component.api.UIColumn, org.primefaces.model.menu.MenuColumn {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Property to be used for sorting", type = Object.class)
		sortBy,
		@PFProperty(description = "Inline style of the column")
		style,
		@PFProperty(description = "Style class of the column")
		styleClass,
		@PFProperty(description = "Custom pluggable sortFunction", type = javax.el.MethodExpression.class)
		sortFunction,
		@PFProperty(description = "Property to be used for filtering", type = Object.class)
		filterBy,
		@PFProperty(description = "Inline style of the filter element")
		filterStyle,
		@PFProperty(description = "Style class of the filter element")
		filterStyleClass,
		@PFProperty(description = "A collection of selectitems for filter dropdown", type = Object.class)
		filterOptions,
		@PFProperty(description = "Match mode for filtering", defaultValue = "startsWith")
		filterMatchMode,
		@PFProperty(description = "Location of the column filter with respect to header content. Options are 'bottom'(default) and 'top'", defaultValue = "bottom")
		filterPosition,
		@PFProperty(description = "Defines the number of rows the column spans", defaultValue = "1", type = Integer.class)
		rowspan,
		@PFProperty(description = "Defines the number of columns the column spans", defaultValue = "1", type = Integer.class)
		colspan,
		@PFProperty(description = "Shortcut for header facet")
		headerText,
		@PFProperty(description = "Shortcut for footer facet")
		footerText,
		@PFProperty(description = "Enables selection mode, valid values are \"single\" and \"multiple\"")
		selectionMode,
		@PFProperty(description = "Maximum number of characters for an input filter", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		filterMaxLength,
		@PFProperty(description = "Specifies resizable feature at column level. Datatable's resizableColumns must be enabled to use this option", defaultValue = "true", type = Boolean.class)
		resizable,
		@PFProperty(description = "Defines if the column should be exported by dataexporter", defaultValue = "true", type = Boolean.class)
		exportable,
		@PFProperty(description = "Value of the filter field", type = Object.class)
		filterValue,
		@PFProperty(description = "Width of the column in pixels or percentage")
		width,
		@PFProperty(description = "Defines if panel is toggleable by columnToggler component. Default value is true and a false value marks the column as static", defaultValue = "true", type = Boolean.class)
		toggleable,
		@PFProperty(description = "Custom implementation to filter a value against a constraint", type = javax.el.MethodExpression.class)
		filterFunction,
		@PFProperty(description = "Name of the field to pass lazy load method for filtering and sorting. If not specified, filterBy-sortBy values are used to identify the field name")
		field,
		@PFProperty(description = "Priority of the column defined as an integer, lower values have more priority", defaultValue = "0", type = Integer.class)
		priority,
		@PFProperty(description = "Boolean value to mark column as sortable", defaultValue = "true", type = Boolean.class)
		sortable,
		@PFProperty(description = "Boolean value to mark column as filterable", defaultValue = "true", type = Boolean.class)
		filterable,
		@PFProperty(description = "Controls the visibilty of the column, default is true", defaultValue = "true", type = Boolean.class)
		visible,
		@PFProperty(description = "Whether clicking the column selects the row when datatable has row selection enabled, default is true", defaultValue = "true", type = Boolean.class)
		selectRow,
		@PFProperty(description = "Label to read by screen readers, when not specified headerText is used")
		ariaHeaderText,
		@PFProperty(description = "Custom pluggable exportFunction", type = javax.el.MethodExpression.class)
		exportFunction,
		@PFProperty(description = "Speficies whether to group rows based on the column data", defaultValue = "false", type = Boolean.class)
		groupRow,;
	}


    public CellEditor getCellEditor() {
    	
    	CellEditor cellEditor = null;
    	
	    for(UIComponent child : getChildren()) {
	        if(child instanceof CellEditor && ((CellEditor)child).isRendered()) {
	            cellEditor = (CellEditor) child;
	        	break;
	        }
	    }
	
	    return cellEditor;
    }

    public boolean isDynamic() {
        return false;
    }

    public String getColumnKey() {
        return this.getClientId();
    }

    public List getElements() {
        return getChildren();
    }

    public int getElementsCount() {
        return getChildCount();
    }
    
    public void renderChildren(FacesContext context) throws IOException {
        int childCount = this.getChildCount();
        if(childCount > 0) {
            for(int i = 0; i < childCount; i++) {
                UIComponent child = this.getChildren().get(i);
                child.encodeAll(context);
            }
        }
    }
}