
package com.equinix.appops.dart.portal.model.dfr;

import java.io.Serializable;
import java.util.List;

import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "products",
    "snapshotfilter"
})
public class DfrDaInput implements Serializable
{

    @JsonProperty("products")
    private List<Product> products = null;
    @JsonProperty("snapshotfilter")
    private ProductFilter snapshotfilter ;
    private final static long serialVersionUID = -4547107726639713620L;

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @JsonProperty("snapshotfilter")
    public ProductFilter getSnapshotfilter() {
        return snapshotfilter;
    }

    @JsonProperty("snapshotfilter")
    public void setSnapshotfilter(ProductFilter snapshotfilter) {
        this.snapshotfilter = snapshotfilter;
    }

}
