
package com.equinix.appops.dart.portal.model.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "products",
    "dfrid"
	
})
public class HierarchyView implements Serializable
{

    @JsonProperty("products")
    private List<HierarchyProduct> products = new ArrayList<>();
    private final static long serialVersionUID = -1215649317076751444L;

    @JsonProperty("products")
    public List<HierarchyProduct> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<HierarchyProduct> products) {
        this.products = products;
    }
    
    @JsonProperty("orphanProducts")
    private List<HierarchyProduct> orphanProducts = new ArrayList<>();
    
    @JsonProperty("orphanProducts")
    public List<HierarchyProduct> getOrphanProducts() {
		return orphanProducts;
	}

    @JsonProperty("orphanProducts")
	public void setOrphanProducts(List<HierarchyProduct> orphanProducts) {
		this.orphanProducts = orphanProducts;
	}
    
    @JsonProperty("dfrid")
    private String dfrid;

    @JsonProperty("dfrid")
	public String getDfrid() {
		return dfrid;
	}
    @JsonProperty("dfrid")
	public void setDfrid(String dfrid) {
		this.dfrid = dfrid;
	}
    
}
