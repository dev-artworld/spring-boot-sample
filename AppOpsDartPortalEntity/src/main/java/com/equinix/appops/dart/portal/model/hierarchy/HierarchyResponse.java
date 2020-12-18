
package com.equinix.appops.dart.portal.model.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "products"
})
public class HierarchyResponse implements Serializable
{

    @JsonProperty("products")
    private List<HierarchyProduct> products = new ArrayList<>();
    private final static long serialVersionUID = 1412396109766619744L;

    @JsonProperty("products")
    public List<HierarchyProduct> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<HierarchyProduct> products) {
        this.products = products;
    }

}
