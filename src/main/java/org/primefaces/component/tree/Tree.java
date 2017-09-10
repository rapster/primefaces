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

import org.primefaces.component.api.UITree;
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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.TreeDragDropEvent;
import javax.faces.component.UIComponent;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.lang.StringBuilder;
import javax.faces.context.FacesContext;
import org.primefaces.model.TreeNode;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import org.primefaces.component.tree.UITreeNode;
import org.primefaces.util.Constants;
import org.primefaces.model.TreeNode;
import javax.faces.event.BehaviorEvent;
import org.primefaces.model.filter.ContainsFilterConstraint;
import org.primefaces.model.filter.EndsWithFilterConstraint;
import org.primefaces.model.filter.EqualsFilterConstraint;
import org.primefaces.model.filter.ExactFilterConstraint;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.model.filter.GlobalFilterConstraint;
import org.primefaces.model.filter.GreaterThanEqualsFilterConstraint;
import org.primefaces.model.filter.GreaterThanFilterConstraint;
import org.primefaces.model.filter.InFilterConstraint;
import org.primefaces.model.filter.LessThanEqualsFilterConstraint;
import org.primefaces.model.filter.LessThanFilterConstraint;
import org.primefaces.model.filter.StartsWithFilterConstraint;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "tree",
             description = "Tree is is used for displaying hierarchical data and creating site navigations.",
             widget = true,
             rtl = true,
             parent = UITree.class)
public class Tree extends AbstractTree implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Specifies the ajax/client toggleMode", defaultValue = "false", type = Boolean.class)
		dynamic,
		@PFProperty(description = "Specifies caching on dynamically loaded nodes. When set to true expanded nodes will be kept in memory", defaultValue = "true", type = Boolean.class)
		cache,
		@PFProperty(description = "Javascript event to process when a tree node is clicked")
		onNodeClick,
		@PFProperty(description = "Style of the main container element of tree")
		style,
		@PFProperty(description = "Style class of the main container element of tree")
		styleClass,
		@PFProperty(description = "Highlights nodes on hover when selection is enabled, set to false to disable highlighting", defaultValue = "true", type = Boolean.class)
		highlight,
		@PFProperty(description = "Unique key of the data presented by tree nodes", type = Object.class)
		datakey,
		@PFProperty(description = "When enabled, Displays slide effect during toggling of a node", defaultValue = "false", type = Boolean.class)
		animate,
		@PFProperty(description = "Defines the orientation of the tree, valid values are, \"vertical\" (default) and horizontal", defaultValue = "vertical")
		orientation,
		@PFProperty(description = "Defines upwards selection propagation for checkbox mode,", defaultValue = "true", type = Boolean.class)
		propagateSelectionUp,
		@PFProperty(description = "Defines upwards selection propagation for checkbox mode,", defaultValue = "true", type = Boolean.class)
		propagateSelectionDown,
		@PFProperty(description = "Controls dragging of tree nodes", defaultValue = "false", type = Boolean.class)
		draggable,
		@PFProperty(description = "Controls dropping of tree nodes", defaultValue = "false", type = Boolean.class)
		droppable,
		@PFProperty(description = "Scope key to group a set of tree components for transferring nodes using drag and drop")
		dragdropScope,
		@PFProperty(description = "Defines parent-child relationship when a node is dragged, valid values are self (default), parent and ancestor", defaultValue = "self")
		dragMode,
		@PFProperty(description = "Defines parent-child restrictions when a node is dropped valid values are none (default) and sibling", defaultValue = "none")
		dropRestrict,
		@PFProperty(description = "Position of the element in the tabbing order", defaultValue = "0", type = Integer.class)
		tabindex,
		@PFProperty(description = "Property to be used for filtering", type = Object.class)
		filterBy,
		@PFProperty(description = "Match mode for filtering", defaultValue = "startsWith")
		filterMatchMode,;
	}


	private Map<String,UITreeNode> nodes;

	public UITreeNode getUITreeNodeByType(String type) {
		UITreeNode node = getTreeNodes().get(type);
		
		if(node == null)
			throw new javax.faces.FacesException("Unsupported tree node type:" + type);
		else
			return node;
	}

		
    public boolean isNodeExpandRequest(FacesContext context) {
		return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_expandNode");
	}

    public boolean isSelectionRequest(FacesContext context) {
		return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_instantSelection");
	}

    public boolean isFilterRequest(FacesContext context) {
    	return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_filtering");
    }

    public static String CONTAINER_CLASS = "ui-tree ui-widget ui-widget-content ui-corner-all";
    public static String CONTAINER_RTL_CLASS = "ui-tree ui-tree-rtl ui-widget ui-widget-content ui-corner-all";
    public static String HORIZONTAL_CONTAINER_CLASS = "ui-tree ui-tree-horizontal ui-widget ui-widget-content ui-corner-all";
    public static String ROOT_NODES_CLASS = "ui-tree-container";
    public static String PARENT_NODE_CLASS = "ui-treenode ui-treenode-parent";
    public static String LEAF_NODE_CLASS = "ui-treenode ui-treenode-leaf";
    public static String CHILDREN_NODES_CLASS = "ui-treenode-children";
    public static String NODE_CONTENT_CLASS_V = "ui-treenode-content";
    public static String SELECTABLE_NODE_CONTENT_CLASS_V = "ui-treenode-content ui-tree-selectable";
    public static String NODE_CONTENT_CLASS_H = "ui-treenode-content ui-state-default ui-corner-all";
    public static String SELECTABLE_NODE_CONTENT_CLASS_H = "ui-treenode-content ui-tree-selectable ui-state-default ui-corner-all";
    public static String EXPANDED_ICON_CLASS_V = "ui-tree-toggler ui-icon ui-icon-triangle-1-s";
    public static String COLLAPSED_ICON_CLASS_V = "ui-tree-toggler ui-icon ui-icon-triangle-1-e";
    public static String COLLAPSED_ICON_RTL_CLASS_V = "ui-tree-toggler ui-icon ui-icon-triangle-1-w";
    public static String EXPANDED_ICON_CLASS_H = "ui-tree-toggler ui-icon ui-icon-minus";
    public static String COLLAPSED_ICON_CLASS_H = "ui-tree-toggler ui-icon ui-icon-plus";
    public static String LEAF_ICON_CLASS = "ui-treenode-leaf-icon";
    public static String NODE_ICON_CLASS = "ui-treenode-icon ui-icon";
    public static String NODE_LABEL_CLASS = "ui-treenode-label ui-corner-all";
    public static final String FILTER_CLASS = "ui-tree-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all";
    public static final String FILTER_CONTAINER = "ui-tree-filter-container";

    public Map<String,UITreeNode> getTreeNodes() {
        if(nodes == null) {
			nodes = new HashMap<String,UITreeNode>();
			for(UIComponent child : getChildren()) {
                UITreeNode node = (UITreeNode) child;
				nodes.put(node.getType(), node);
			}
		}

        return nodes;
    }

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("select", NodeSelectEvent.class);
        put("unselect", NodeUnselectEvent.class);
        put("expand", NodeExpandEvent.class);
        put("collapse", NodeCollapseEvent.class);
        put("dragdrop", TreeDragDropEvent.class);
        put("contextMenu", NodeSelectEvent.class);
    }});

    private static final Collection<String> EVENT_NAMES = BEHAVIOR_EVENT_MAPPING.keySet();

    private final static String STARTS_WITH_MATCH_MODE = "startsWith";
    private final static String ENDS_WITH_MATCH_MODE = "endsWith";
    private final static String CONTAINS_MATCH_MODE = "contains";
    private final static String EXACT_MATCH_MODE = "exact";
    private final static String LESS_THAN_MODE = "lt";
    private final static String LESS_THAN_EQUALS_MODE = "lte";
    private final static String GREATER_THAN_MODE = "gt";
    private final static String GREATER_THAN_EQUALS_MODE = "gte";
    private final static String EQUALS_MODE = "equals";
    private final static String IN_MODE = "in";
    private final static String GLOBAL_MODE = "global";
  
    final static Map<String,FilterConstraint> FILTER_CONSTRAINTS;
    
    static {
        FILTER_CONSTRAINTS = new HashMap<String,FilterConstraint>();
        FILTER_CONSTRAINTS.put(STARTS_WITH_MATCH_MODE, new StartsWithFilterConstraint());
        FILTER_CONSTRAINTS.put(ENDS_WITH_MATCH_MODE, new EndsWithFilterConstraint());
        FILTER_CONSTRAINTS.put(CONTAINS_MATCH_MODE, new ContainsFilterConstraint());
        FILTER_CONSTRAINTS.put(EXACT_MATCH_MODE, new ExactFilterConstraint());
        FILTER_CONSTRAINTS.put(LESS_THAN_MODE, new LessThanFilterConstraint());
        FILTER_CONSTRAINTS.put(LESS_THAN_EQUALS_MODE, new LessThanEqualsFilterConstraint());
        FILTER_CONSTRAINTS.put(GREATER_THAN_MODE, new GreaterThanFilterConstraint());
        FILTER_CONSTRAINTS.put(GREATER_THAN_EQUALS_MODE, new GreaterThanEqualsFilterConstraint());
        FILTER_CONSTRAINTS.put(EQUALS_MODE, new EqualsFilterConstraint());
        FILTER_CONSTRAINTS.put(IN_MODE, new InFilterConstraint());
        FILTER_CONSTRAINTS.put(GLOBAL_MODE, new GlobalFilterConstraint());
    }

    @Override
    public Map<String, Class<? extends BehaviorEvent>> getBehaviorEventMapping() {
         return BEHAVIOR_EVENT_MAPPING;
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(ComponentUtils.isRequestSource(this, context) && event instanceof AjaxBehaviorEvent) {
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            String clientId = this.getClientId(context);
            FacesEvent wrapperEvent = null;
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

            if(eventName.equals("expand")) {
                this.setRowKey(params.get(clientId + "_expandNode"));
                TreeNode expandedNode = this.getRowNode();
                expandedNode.setExpanded(true);

                wrapperEvent = new NodeExpandEvent(this, behaviorEvent.getBehavior(), expandedNode);
            }
            else if(eventName.equals("collapse")) {
                this.setRowKey(params.get(clientId + "_collapseNode"));
                TreeNode collapsedNode = this.getRowNode();
                collapsedNode.setExpanded(false);

                wrapperEvent = new NodeCollapseEvent(this, behaviorEvent.getBehavior(), collapsedNode);
            }
            else if(eventName.equals("select")) {
                setRowKey(params.get(clientId + "_instantSelection"));

                wrapperEvent = new NodeSelectEvent(this, behaviorEvent.getBehavior(), this.getRowNode());
            }
            else if(eventName.equals("unselect")) {
                setRowKey(params.get(clientId + "_instantUnselection"));

                wrapperEvent = new NodeUnselectEvent(this, behaviorEvent.getBehavior(), this.getRowNode());
            }
            else if(eventName.equals("dragdrop")) {
                int dndIndex = Integer.parseInt(params.get(clientId + "_dndIndex"));

                wrapperEvent = new TreeDragDropEvent(this, behaviorEvent.getBehavior(), dragNode, dropNode, dndIndex);
            }
            else if(eventName.equals("contextMenu")) {
                setRowKey(params.get(clientId + "_contextMenuNode"));

                wrapperEvent = new NodeSelectEvent(this, behaviorEvent.getBehavior(), this.getRowNode(), true);
            }

            wrapperEvent.setPhaseId(behaviorEvent.getPhaseId());
            
            super.queueEvent(wrapperEvent);
            
            this.setRowKey(null);
        }
        else {
            super.queueEvent(event);
        }
    }

    private boolean isToggleRequest(FacesContext context) {
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String clientId = getClientId(context);

        return params.get(clientId + "_expandNode") != null || params.get(clientId + "_collapseNode") != null;
    }

    public boolean isDragDropRequest(FacesContext context) {
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String clientId = getClientId(context);
        String source = context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM);

        return clientId.equals(source) && params.get(clientId + "_dragdrop") != null;
    }

    private boolean shouldSkipNodes(FacesContext context) {
        return this.isToggleRequest(context)||this.isDragDropRequest(context);
    }

    @Override
    public void processDecodes(FacesContext context) {
        if(shouldSkipNodes(context)) {
            this.decode(context);
        } 
        else {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if(!shouldSkipNodes(context)) {
            super.processValidators(context);
        } 
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(shouldSkipNodes(context)) {
            this.updateSelection(context);
        }
        else {
            super.processUpdates(context);
        }  
    }

    public boolean isCheckboxSelection() {
        String selectionMode = this.getSelectionMode();
        
        return selectionMode != null && selectionMode.equals("checkbox");
    }

    public boolean isRTL() {
        return this.getDir().equalsIgnoreCase("rtl");
    }

    private TreeNode dragNode;
    private TreeNode dropNode;

    TreeNode getDragNode() {
        return dragNode;
    }
    void setDragNode(TreeNode dragNode) {
        this.dragNode = dragNode;
    }

    TreeNode getDropNode() {
        return dropNode;
    }
    void setDropNode(TreeNode dropNode) {
        this.dropNode = dropNode;
    }

    @Override
    protected boolean shouldVisitNode(TreeNode node) {
        return this.isDynamic() ? (node.isExpanded() || node.getParent() == null) : true;
    }

    @Override
    protected void processColumnChildren(FacesContext context, PhaseId phaseId, String nodeKey) {
        setRowKey(nodeKey);
        TreeNode treeNode = this.getRowNode();

        if(treeNode == null)
            return;
        
        String treeNodeType = treeNode.getType();
        
        for(UIComponent child : getChildren()) {
            if(child instanceof UITreeNode && child.isRendered()) {
                UITreeNode uiTreeNode = (UITreeNode) child;

                if(!treeNodeType.equals(uiTreeNode.getType()))
                    continue;
                
                for(UIComponent grandkid : child.getChildren()) {
                    if(!grandkid.isRendered())
                        continue;
                    
                    if(phaseId == PhaseId.APPLY_REQUEST_VALUES)
                        grandkid.processDecodes(context);
                    else if(phaseId == PhaseId.PROCESS_VALIDATIONS)
                        grandkid.processValidators(context);
                    else if(phaseId == PhaseId.UPDATE_MODEL_VALUES)
                        grandkid.processUpdates(context);
                    else
                        throw new IllegalArgumentException();
                }
            }
        }
    }

    @Override
    protected void validateSelection(FacesContext context) {
        String selectionMode = this.getSelectionMode();

        if(selectionMode != null && this.isRequired()) {
            Object selection = this.getLocalSelectedNodes();
            boolean isValueBlank = (selectionMode.equalsIgnoreCase("single")) ? (selection == null) : (((TreeNode[]) selection).length == 0);
            
            if(isValueBlank) {
                super.updateSelection(context);
            }
        }
 
        super.validateSelection(context);
    }
    
    private List<String> filteredRowKeys = new ArrayList<String>();
    public List<String> getFilteredRowKeys() {
        return filteredRowKeys;
    }

    public void setFilteredRowKeys(List<String> filteredRowKeys) {
        this.filteredRowKeys = filteredRowKeys;
    }

}