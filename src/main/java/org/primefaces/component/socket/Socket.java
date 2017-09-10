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
package org.primefaces.component.socket;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.primefaces.event.RateEvent;
import org.primefaces.util.Constants;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.PhaseId;
import javax.faces.event.BehaviorEvent;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="socket/socket.js")
})
@PFComponent(tagName = "socket",
             description = "Socket component is an agent that creates a channel between the server and the client.",
             widget = true,
             parent = UIComponentBase.class)
public class Socket extends AbstractSocket implements javax.faces.component.behavior.ClientBehaviorHolder, org.primefaces.component.api.PrimeClientBehaviorHolder {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Channel name of the connection", required = true)
		channel,
		@PFProperty(description = "Desired protocol to be used valid values are websocket (default), sse, streaming, long-polling, jsonp", defaultValue = "websocket")
		transport,
		@PFProperty(description = "Fallback protocol to be used when desired transport is not supported in environment, valid values are websocket, sse, streaming, long-polling (default), jsonp", defaultValue = "long-polling")
		fallbackTransport,
		@PFProperty(description = "Javascript event handler that is processed when the server publishes data")
		onMessage,
		@PFProperty(description = "Javascript event handler that is processed when there is an error")
		onError,
		@PFProperty(description = "Javascript event handler for atmospheres onClose callback")
		onClose,
		@PFProperty(description = "Javascript event handler for atmospheres onOpen callback")
		onOpen,
		@PFProperty(description = "Javascript event handler for atmospheres onReconnect callback")
		onReconnect,
		@PFProperty(description = "Javascript event handler for atmospheres onMessagePublished callback")
		onMessagePublished,
		@PFProperty(description = "Javascript event handler for atmospheres onTransportFailure callback")
		onTransportFailure,
		@PFProperty(description = "Javascript event handler for atmospheres onLocalMessage callback")
		onLocalMessage,
		@PFProperty(description = "Defines if client should connect to the channel on page load", defaultValue = "true", type = Boolean.class)
		autoConnect,;
	}


    private final static String DEFAULT_EVENT = "message";

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = Collections.unmodifiableMap(new HashMap<String, Class<? extends BehaviorEvent>>() {{
        put("message", null);
    }});

    private static final Collection<String> EVENT_NAMES = BEHAVIOR_EVENT_MAPPING.keySet();

    @Override
    public Map<String, Class<? extends BehaviorEvent>> getBehaviorEventMapping() {
         return BEHAVIOR_EVENT_MAPPING;
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public String getDefaultEventName() {
        return DEFAULT_EVENT;
    }
}