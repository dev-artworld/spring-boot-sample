
package com.equinix.appops.dart.portal.model.widget;

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
public class ProductWidgets implements Serializable
{

    @JsonProperty("products")
    private List<ProductWidget> products = new ArrayList<>();
    private final static long serialVersionUID = 7198701142022964024L;

    @JsonProperty("products")
    public List<ProductWidget> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<ProductWidget> products) {
        this.products = products;
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
