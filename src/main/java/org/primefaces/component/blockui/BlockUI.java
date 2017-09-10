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
package org.primefaces.component.blockui;

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
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "blockUI",
             description = "BlockUI blocks JSF components during ajax processing.",
             widget = true,
             parent = UIPanel.class)
public class BlockUI extends AbstractBlockUI {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Identifier of component(s) to bind the block UI")
		trigger,
		@PFProperty(description = "Component whose UI to block")
		block,
		@PFProperty(description = "Blocks the ui by default when enabled", defaultValue = "false", type = Boolean.class)
		blocked,
		@PFProperty(description = "When disabled, Displays block without animation effect", defaultValue = "true", type = Boolean.class)
		animate,
		@PFProperty(description = "Style class of the component")
		styleClass,;
	}

}