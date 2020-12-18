package com.equinix.appops.dart.portal.model.msdfr;

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
public class MsDfrDaInputModel {

    @JsonProperty("products")
    private List<MsProductDfr> products = null;
    
    @JsonProperty("snapshotfilter")
    private ProductFilter snapshotfilter ;
    

    @JsonProperty("products")
    public List<MsProductDfr> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<MsProductDfr> products) {
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