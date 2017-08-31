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
package org.primefaces.component.media;

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

@PFComponent(tagName = "media",
             description = "Media component is used for embedding multimedia content such as videos and music to JSF views. Media renders object or embed html tags depending on the user client.")
public abstract class MediaCore extends UIComponentBase implements IMedia {

	@PFPropertyKeys(base = {})
	public enum PropertyKeys {

		@PFProperty(description = "Media source to play", type = Object.class)
		value,
		@PFProperty(description = "Type of the player, possible values are \"quicktime\",\"windows\",\"flash\",\"real\" and \"pdf\"")
		player,
		@PFProperty(description = "Width of the player")
		width,
		@PFProperty(description = "Height of the player")
		height,
		@PFProperty(description = "Style of the player")
		style,
		@PFProperty(description = "Style class of the player")
		styleClass,
		@PFProperty(description = "Controls browser caching mode of the resource. Default is true", defaultValue = "true", type = Boolean.class)
		cache,;
	}

}