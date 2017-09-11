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
package org.primefaces.component.growl;

import javax.faces.component.UIMessages;
import org.primefaces.component.api.AutoUpdatable;
import org.primefaces.component.api.UINotification;
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
@PFComponent(tagName = "growl",
             description = "Growl is based on the Mac's growl notification widget and used to display FacesMessages similar to h:messages.",
             widget = true,
             parent = UIMessages.class)
public class Growl extends AbstractGrowl implements AutoUpdatable, UINotification {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Specifies if the message should stay instead of hidden automatically", defaultValue = "false", type = Boolean.class)
		sticky,
		@PFProperty(description = "Duration in milliseconds to display non-sticky messages", defaultValue = "6000", type = Integer.class)
		life,
		@PFProperty(description = "When enabled, growl is updated for each ajax request implicitly", defaultValue = "false", type = Boolean.class)
		autoUpdate,
		@PFProperty(description = "Defines whether html would be escaped or not, defaults to true", defaultValue = "true", type = Boolean.class)
		escape,
		@PFProperty(description = "Comma seperated list of severities to display only")
		severity,;
	}

}