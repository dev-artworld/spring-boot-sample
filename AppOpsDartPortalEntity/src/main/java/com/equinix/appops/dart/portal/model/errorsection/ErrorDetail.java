package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"productName","errorList"
})
public class ErrorDetail implements Serializable {
	
	@JsonProperty("productName")
	private String productName;
	
	@JsonProperty("errorList")
	private List<ErrorMessage> errorList;
	
	@JsonProperty("productName")
	public String getProductName() {
		return productName;
	}
	
	@JsonProperty("productName")
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@JsonProperty("errorList")
	public List<ErrorMessage> getErrorList() {
		if (this.errorList == null) {
			this.errorList = new ArrayList<>();
		}
		return errorList;
	}
	
	@JsonProperty("errorList")
	public void setErrorList(List<ErrorMessage> errorList) {
		this.errorList = errorList;
	}
	
	
}
