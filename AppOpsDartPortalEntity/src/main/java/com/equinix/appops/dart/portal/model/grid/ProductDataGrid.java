
package com.equinix.appops.dart.portal.model.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"totalrows",
    "products",
    "snapfilter",
    "dfrid"
})
public class ProductDataGrid implements Serializable
{

	@JsonProperty("totalrows")
    private Long totalrows;
	
    @JsonProperty("products")
    private List<Product> products = new ArrayList<>();
    private ProductFilter snapfilter = null;
    private final static long serialVersionUID = -4242693825377088929L;

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    @JsonProperty("snapfilter")
	public ProductFilter getSnapfilter() {
		return snapfilter;
	}
    @JsonProperty("snapfilter")
	public void setSnapfilter(ProductFilter snapfilter) {
		this.snapfilter = snapfilter;
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
    
    @JsonProperty("totalrows")
   	public Long getTotalrows() {
   		return totalrows;
   	}
       @JsonProperty("totalrows")
   	public void setTotalrows(Long totalrows) {
   		this.totalrows = totalrows;
   	}
    
    
}
