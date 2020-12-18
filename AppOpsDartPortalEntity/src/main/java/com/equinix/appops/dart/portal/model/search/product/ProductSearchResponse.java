
package com.equinix.appops.dart.portal.model.search.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "products",
    "total_records",
    "dfrid"
})
public class ProductSearchResponse implements Serializable
{

    @JsonProperty("products")
    private List<ProductResp> products = new ArrayList<>();
   
    @JsonProperty("dfrid")
    private String dfrid;
    
    @JsonProperty("total_records")
    private String totalRecords;
    
    private final static long serialVersionUID = -8220441151873585817L;

    @JsonProperty("products")
    public List<ProductResp> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<ProductResp> products) {
        this.products = products;
    }
    @JsonProperty("dfrid")
	public String getDfrid() {
		return dfrid;
	}
    @JsonProperty("dfrid")
	public void setDfrid(String dfrid) {
		this.dfrid = dfrid;
	}
    @JsonProperty("total_records")
	public String getTotalRecords() {
		return totalRecords;
	}
    @JsonProperty("total_records")
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

    
    
}
