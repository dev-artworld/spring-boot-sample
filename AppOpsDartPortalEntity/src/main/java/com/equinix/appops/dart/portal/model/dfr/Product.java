
package com.equinix.appops.dart.portal.model.dfr;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "da"
})
public class Product implements Serializable
{

    @JsonProperty("Name")
    private String name;
    @JsonProperty("da")
    private Da da;
    private final static long serialVersionUID = -6991756490935049703L;

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("da")
    public Da getDa() {
        return da;
    }

    @JsonProperty("da")
    public void setDa(Da da) {
        this.da = da;
    }

}
