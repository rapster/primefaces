package org.primefaces.component.api.propertykeys;

import javax.faces.component.UIComponent;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys
public enum UIComponentPropertyKeys {

	@PFProperty(description = "Unique identifier of the component in a namingContainer", ignore = true)
	id,

	@PFProperty(description = "Boolean value to specify the rendering of the component, when set to false component will not be rendered", type = boolean.class, ignore = true)
	rendered,

	@PFProperty(description = "An el expression referring to a server side UIComponent instance in a backing bean", type = UIComponent.class, ignore = true)
	binding;

}
