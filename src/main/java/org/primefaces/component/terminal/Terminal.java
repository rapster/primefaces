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
package org.primefaces.component.terminal;

import javax.faces.component.UIPanel;
import org.primefaces.component.terminal.TerminalHandler;
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
	@ResourceDependency(library="primefaces", name="terminal/terminal.css"),
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="terminal/terminal.js")
})
@PFComponent(tagName = "terminal",
             description = "Terminal is an ajax powered component bringing desktop command-line tools to the web.",
             widget = true,
             parent = UIPanel.class,
             handlerClass = TerminalHandler.class)
public class Terminal extends AbstractTerminal {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Inline style of the component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "Welcome message to be displayed on initial load")
		welcomeMessage,
		@PFProperty(description = "Primary prompt text", defaultValue = "prime $")
		prompt,
		@PFProperty(description = "Method to execute by passing command and the arguments", type = javax.el.MethodExpression.class)
		commandHandler,
		@PFProperty(description = "Defines if the terminal is escaped or not", defaultValue = "true", type = Boolean.class)
		escape,;
	}


    public static final String CONTAINER_CLASS = "ui-terminal ui-widget ui-widget-content ui-corner-all";
    public static final String WELCOME_MESSAGE_CLASS = "ui-terminal-welcome";
    public static final String CONTENT_CLASS = "ui-terminal-content";
    public static final String PROMPT_CLASS = "ui-terminal-prompt";
    public static final String INPUT_CLASS = "ui-terminal-input";

    public boolean isCommandRequest() {
        FacesContext context = getFacesContext();
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_command");
    }
}