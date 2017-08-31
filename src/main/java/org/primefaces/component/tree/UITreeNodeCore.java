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
package org.primefaces.component.tree;

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

@PFComponent(tagName = "treeNode",
             description = "TreeNode is used with Tree component to represent a node in tree.")
public abstract class UITreeNodeCore extends UIColumn implements IUITreeNode {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Type of the tree node. Default is \"default\"", defaultValue = "default")
		type,
		@PFProperty(description = "Style class to apply a particular tree node type")
		styleClass,
		@PFProperty(description = "")
		icon,
		@PFProperty(description = "")
		expandedIcon,
		@PFProperty(description = "")
		collapsedIcon,
		@PFProperty(description = "")
		ariaLabel,;
	}


    public String getIconToRender(boolean expanded) {
        String icon = getIcon();
        if(icon != null) {
            return icon;
        } else {
            String expandedIcon = getExpandedIcon();
            String collapsedIcon = getCollapsedIcon();

            if(expandedIcon != null && collapsedIcon != null) {
                return expanded ? expandedIcon : collapsedIcon;
            }
        }

        return null;
    }
}