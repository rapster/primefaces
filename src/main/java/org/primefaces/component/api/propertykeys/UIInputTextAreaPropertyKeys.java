package org.primefaces.component.api.propertykeys;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys(base = {UIComponentPropertyKeys.class})
public enum UIInputTextAreaPropertyKeys {


    @PFProperty(description ="Access key to transfer focus to the input element", ignore = true)
    accesskey,

    @PFProperty(description ="Specifies the visible width of input element", type = int.class, ignore = true)
    cols,

    @PFProperty(description ="Direction indication for text that does not inherit directionality", ignore = true)
    dir,

    @PFProperty(description ="Disables the input element, default is false", type = boolean.class, ignore = true)
    disabled,

    @PFProperty(description ="A localized user presentable name", ignore = true)
    label,

    @PFProperty(description ="A localized user presentable name", ignore = true)
    lang,

    @PFProperty(description ="Client side callback to execute when input element loses focus", ignore = true)
    onblur,

    @PFProperty(description ="Client side callback to execute when input element loses focus and its value has been modified since gaining focus", ignore = true)
    onchange,

    @PFProperty(description ="Client side callback to execute when input element is clicked", ignore = true)
    onclick,

    @PFProperty(description ="Client side callback to execute when input element is double clicked", ignore = true)
    ondblclick,

    @PFProperty(description ="Client side callback to execute when input element receives focus", ignore = true)
    onfocus,

    @PFProperty(description ="Client side callback to execute when a key is pressed down over input element", ignore = true)
    onkeydown,

    @PFProperty(description ="Client side callback to execute when a key is pressed and released over input element", ignore = true)
    onkeypress,

    @PFProperty(description ="Client side callback to execute when a key is released over input element", ignore = true)
    onkeyup,

    @PFProperty(description ="Client side callback to execute when a pointer input element is pressed down over input element", ignore = true)
    onmousedown,

    @PFProperty(description ="Client side callback to execute when a pointer input element is moved within input element", ignore = true)
    onmousemove,

    @PFProperty(description ="Client side callback to execute when a pointer input element is moved away from input element", ignore = true)
    onmouseout,

    @PFProperty(description ="Client side callback to execute when a pointer input element is moved onto input element", ignore = true)
    onmouseover,

    @PFProperty(description ="Client side callback to execute when a pointer input element is released over input element", ignore = true)
    onmouseup,

    @PFProperty(description ="Client side callback to execute when text within input element is selected by user", ignore = true)
    onselect,

    @PFProperty(description ="Flag indicating that this input element will prevent changes by the user", type = boolean.class, ignore = true)
    readonly,

    @PFProperty(description ="Specifies the visible height of input element", type = int.class, ignore = true)
    rows,

    @PFProperty(description ="Inline style of the input element", ignore = true)
    style,

    @PFProperty(description ="Style class of the input element", ignore = true)
    styleClass,

    @PFProperty(description ="Position of the element in the tabbing order", ignore = true)
    tabindex,

    @PFProperty(description ="Advisory tooltip information", ignore = true)
    title,

    @PFProperty(description ="The placeholder attribute specifies a short hint that describes the expected value of an input field", ignore = true)
    placeholder,


}
