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
package org.primefaces.component.link;

import javax.faces.component.html.HtmlOutcomeTargetLink;
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
import java.util.Map;
import org.primefaces.util.ComponentUtils;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js")
})
@PFComponent(tagName = "link",
             description = "Link is an extension to the standard h:link component with skinning capabilities.",
             parent = HtmlOutcomeTargetLink.class)
public class Link extends AbstractLink implements org.primefaces.component.api.UIOutcomeTarget {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Identifier of the target page which should be scrolled to")
		fragment,
		@PFProperty(description = "Disable appending the \n on the rendering of this element", defaultValue = "false", type = Boolean.class)
		disableClientWindow,
		@PFProperty(description = "Resource to link to")
		href,
		@PFProperty(description = "Defines if label of the component is escaped or not", defaultValue = "true", type = Boolean.class)
		escape,;
	}


    public final static String STYLE_CLASS = "ui-link ui-widget";
    public final static String DISABLED_STYLE_CLASS = "ui-link ui-widget ui-state-disabled";

    public final static String MOBILE_STYLE_CLASS = "ui-link ui-widget";
    public final static String MOBILE_DISABLED_STYLE_CLASS = "ui-link ui-widget ui-state-disabled";

    public Map<String, List<String>> getParams() {
        return ComponentUtils.getUIParams(this);
    }
}