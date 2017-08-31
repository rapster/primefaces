package org.primefaces.component.api.propertykeys;

import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys(base = {UIOutputPropertyKeys.class})
public enum UIInputPropertyKeys {

    @PFProperty(description = "When set true, process validations logic is executed at apply request values phase for this component", defaultValue = "false", type = Boolean.class, ignore = true)
    immediate,

    @PFProperty(description = "Marks component as required", type = Boolean.class, ignore = true)
    required,

    @PFProperty(description = "A method expression referring to a method validationg the input", type = Validator.class, ignore = true)
    validator,

    @PFProperty(description = "A method binding expression referring to a method for handling a value change event", type = ValueChangeListener.class, ignore = true)
    valueChangeListener,

    @PFProperty(description = "Message to display when required field validation fails", ignore = true)
    requiredMessage,

    @PFProperty(description = "Message to display when conversion fails", ignore = true)
    converterMessage,

    @PFProperty(description = "Message to display when validation fails", ignore = true)
    validatorMessage
}
