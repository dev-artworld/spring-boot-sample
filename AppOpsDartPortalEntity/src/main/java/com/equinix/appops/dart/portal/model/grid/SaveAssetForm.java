package com.equinix.appops.dart.portal.model.grid;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"dfrId",
    "dfrLineId",
    "products",
    "totalrows",
    "name"
})
public class SaveAssetForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("dfrId")
    private String dfrId;
	
	@JsonProperty("dfrLineId")
    private String dfrLineId;
	
	@JsonProperty("products")
    private Product products;
	
	@JsonProperty("totalrows")
    private Long totalrows;
	
	@JsonProperty("name")
    private String name;

    @JsonProperty("dfrId")
	public String getDfrId() {
		return dfrId;
	}
    @JsonProperty("dfrId")
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
    
    @JsonProperty("dfrLineId")
	public String getDfrLineId() {
		return dfrLineId;
	}
    
    @JsonProperty("dfrLineId")
	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}
	
	@JsonProperty("products")
	public Product getProducts() {
		return products;
	}
	
	@JsonProperty("products")
	public void setProducts(Product products) {
		this.products = products;
	}
	
	@JsonProperty("totalrows")
   	public Long getTotalrows() {
   		return totalrows;
   	}
    @JsonProperty("totalrows")
   	public void setTotalrows(Long totalrows) {
   		this.totalrows = totalrows;
   	}
      
    @JsonProperty("name")
    public String getName() {
		return name;
	}
    
    @JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SaveAssetForm [dfrId=" + dfrId + ", dfrLineId=" + dfrLineId + ", products=" + products + "]";
	}
}
