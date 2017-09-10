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
package org.primefaces.component.signature;

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
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.primefaces.component.api.UITree;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="signature/signature.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="signature/signature.js")
})
@PFComponent(tagName = "signature",
             description = "Signature is an input component to provide a signature.",
             widget = true,
             parent = UIInput.class)
public class Signature extends AbstractSignature {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Background color,")
		backgroundColor,
		@PFProperty(description = "Foreground color,")
		color,
		@PFProperty(description = "Thickness of lines,", defaultValue = "2", type = Integer.class)
		thickness,
		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "When enabled, signature is used for display purposes only", defaultValue = "false", type = Boolean.class)
		readonly,
		@PFProperty(description = "Adds a guideline when enabled,", defaultValue = "false", type = Boolean.class)
		guideline,
		@PFProperty(description = "Color of the guideline,")
		guidelineColor,
		@PFProperty(description = "Offset of guideline from bottom", defaultValue = "25", type = Integer.class)
		guidelineOffset,
		@PFProperty(description = "Guide line indent from the edges", defaultValue = "10", type = Integer.class)
		guidelineIndent,
		@PFProperty(description = "Client side callback to execute when signature changes")
		onchange,
		@PFProperty(description = "Write-only value used to pass the value in base64 to backing bean")
		base64Value,;
	}


    public void processUpdates(FacesContext context) {
        super.processUpdates(context);
        String base64Value = this.getBase64Value();

        if(base64Value != null) {
            ValueExpression ve = this.getValueExpression(PropertyKeys.base64Value.toString());
            if(ve != null) {
                ve.setValue(context.getELContext(), base64Value);
                getStateHelper().put(PropertyKeys.base64Value, null);
            }
        }
    }
}