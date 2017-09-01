package org.primefaces.component.api.propertykeys;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys
public enum UIComponentPropertyKeys {

	@PFProperty(description = "Unique identifier of the component in a namingContainer", ignore = true)
	id,

	@PFProperty(description = "Boolean value to specify the rendering of the component, when set to false component will not be rendered", type = Boolean.class, ignore = true)
	rendered,

	binding;

}
