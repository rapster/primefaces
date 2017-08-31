package org.primefaces.component.api.propertykeys;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys(base = {UIComponentPropertyKeys.class})
public enum UIOutputPropertyKeys {

	@PFProperty(description = "Value of the component", ignore = true)
	value,

	@PFProperty(description = "An el expression or a literal text that defines a converter for the component. When it's an EL expression, it's resolved to a converter instance. \r\n"
			+ "In case it's a static text, it must refer to a converter id", ignore = true)
	converter;
}
