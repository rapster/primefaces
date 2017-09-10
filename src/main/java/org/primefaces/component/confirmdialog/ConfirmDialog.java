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
package org.primefaces.component.confirmdialog;

import javax.faces.component.UIPanel;
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
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "confirmDialog",
             description = "ConfirmDialog is a replacement to the legacy javascript confirmation box. \n      Skinning, customization and avoiding popup blockers are notabled advantages over classic javascript confirmation.",
             widget = true,
             rtl = true,
             parent = UIPanel.class)
public class ConfirmDialog extends AbstractConfirmDialog {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Text to be displayed in body. Required")
		message,
		@PFProperty(description = "Text for the header")
		header,
		@PFProperty(description = "Message severity for the dislayed icon", defaultValue = "alert")
		severity,
		@PFProperty(description = "Width of the dialog in pixels")
		width,
		@PFProperty(description = "Height of the dialog")
		height,
		@PFProperty(description = "Inner style of the dialog container")
		style,
		@PFProperty(description = "Style class of the dialog container")
		styleClass,
		@PFProperty(description = "Defines if close icon should be displayed or not", defaultValue = "true", type = Boolean.class)
		closable,
		@PFProperty(description = "Alternative to appendToBody. Appends the dialog to the given search expression")
		appendTo,
		@PFProperty(description = "Sets dialogs visibility", defaultValue = "false", type = Boolean.class)
		visible,
		@PFProperty(description = "Effect to use when showing the dialog")
		showEffect,
		@PFProperty(description = "Effect to use when hiding the dialog")
		hideEffect,
		@PFProperty(description = "Defines if dialog should close when escape key is pressed", defaultValue = "false", type = Boolean.class)
		closeOnEscape,
		@PFProperty(description = "When enabled, confirmDialog becomes a shared for other components that require confirmation", defaultValue = "false", type = Boolean.class)
		global,
		@PFProperty(description = "In responsive mode, dialog adjusts itself based on screen width", defaultValue = "false", type = Boolean.class)
		responsive,;
	}

    
    public static final String CONTAINER_CLASS = "ui-confirm-dialog ui-dialog ui-widget ui-widget-content ui-corner-all ui-shadow ui-hidden-container";
    public static final String BUTTONPANE_CLASS = "ui-dialog-buttonpane ui-dialog-footer ui-widget-content ui-helper-clearfix";
    public static final String SEVERITY_ICON_CLASS = "ui-confirm-dialog-severity";
    public static final String MESSAGE_CLASS = "ui-confirm-dialog-message";

    public boolean isRTL() {
        return this.getDir().equalsIgnoreCase("rtl");
    }
}