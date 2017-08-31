package org.primefaces.component.api.propertykeys;

import javax.el.MethodExpression;
import javax.faces.event.ActionListener;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys(base = UIComponentPropertyKeys.class)
public enum UICommandPropertyKeys {

    @PFProperty(description = "Label of the component", type = Object.class, ignore = true)
    value,

    @PFProperty(description = "An actionlistener to process when command is executed", type = ActionListener.class, ignore = true)
    actionListener,

    @PFProperty(description = "A method expression or a string outcome to process when command is executed", type = MethodExpression.class, ignore = true)
    action,

    @PFProperty(description = "Boolean value that determines the phaseId of the action event, when true actions are processed at \"Apply Request Values\", when false at \"Invoke Application\" phase", ignore = true)
    immediate,
}
