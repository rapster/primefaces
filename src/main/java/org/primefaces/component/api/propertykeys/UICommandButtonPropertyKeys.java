package org.primefaces.component.api.propertykeys;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys
public enum UICommandButtonPropertyKeys {

    @PFProperty(description ="Access key to transfer focus to the button.", ignore = true)
    accesskey,

    @PFProperty(description ="Alternate textual description of the button.", ignore = true)
    alt,

    @PFProperty(description ="Direction indication for text that does not inherit directionality.", ignore = true)
    dir,

    @PFProperty(description ="Disables the button, default is false.", type = boolean.class, ignore = true)
    disabled,

    @PFProperty(description ="Style class representing the button icon.", ignore = true)
    image,

    @PFProperty(description ="A localized user presentable name.", ignore = true)
    lang,

    @PFProperty(description ="Client side callback to execute when button loses focus.", ignore = true)
    onblur,

    @PFProperty(description ="Client side callback to execute when button loses focus and its value has been modified since gaining focus.", ignore = true)
    onchange,

    @PFProperty(description ="Client side callback to execute when button is clicked.", ignore = true)
    onclick,

    @PFProperty(description ="Client side callback to execute when button is double clicked.", ignore = true)
    ondblclick,

    @PFProperty(description ="Client side callback to execute when button receives focus.", ignore = true)
    onfocus,

    @PFProperty(description ="Client side callback to execute when a key is pressed down over button.", ignore = true)
    onkeydown,

    @PFProperty(description ="Client side callback to execute when a key is pressed and released over button.", ignore = true)
    onkeypress,

    @PFProperty(description ="Client side callback to execute when a key is released over button.", ignore = true)
    onkeyup,

    @PFProperty(description ="Client side callback to execute when a pointer button is pressed down over button.", ignore = true)
    onmousedown,

    @PFProperty(description ="Client side callback to execute when a pointer button is moved within button.", ignore = true)
    onmousemove,

    @PFProperty(description ="Client side callback to execute when a pointer button is moved away from button.", ignore = true)
    onmouseout,

    @PFProperty(description ="Client side callback to execute when a pointer button is moved onto button.", ignore = true)
    onmouseover,

    @PFProperty(description ="Client side callback to execute when a pointer button is released over button.", ignore = true)
    onmouseup,

    @PFProperty(description ="Client side callback to execute when text within button is selected by user.", ignore = true)
    onselect,

    @PFProperty(description ="Flag indicating that this button will prevent changes by the user.", type = boolean.class, ignore = true)
    readonly,

    @PFProperty(description ="Inline style of the button.", ignore = true)
    style,

    @PFProperty(description ="Style class of the button.", ignore = true)
    styleClass,

    @PFProperty(description ="Position of the button in the tabbing order.", ignore = true)
    tabindex,

    @PFProperty(description ="Advisory tooltip information.", ignore = true)
    title,

    @PFProperty(description ="Defines the behavior of the button, default is \"submit\".", defaultValue = "submit", ignore = true)
    type,

}
