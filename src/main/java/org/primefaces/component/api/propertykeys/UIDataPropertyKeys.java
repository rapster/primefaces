package org.primefaces.component.api.propertykeys;

import org.primefaces.cdk.annotations.PFProperty;
import org.primefaces.cdk.annotations.PFPropertyKeys;

@PFPropertyKeys(base = UIComponentPropertyKeys.class)
public enum UIDataPropertyKeys {

    @PFProperty(description = "Datasource of the component", ignore = true)
    value,

    @PFProperty(description = "Name of the iterator variable used to refer each data", ignore = true)
    var,

    @PFProperty(description = "Number of rows to display per page. Default value is 0 meaning to display all data available", type = Integer.class, ignore = true)
    rows,

    @PFProperty(description = "Index of the first data to display", type = Integer.class, ignore = true)
    first
}
