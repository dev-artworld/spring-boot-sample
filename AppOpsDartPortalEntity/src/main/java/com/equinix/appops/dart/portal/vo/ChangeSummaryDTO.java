package com.equinix.appops.dart.portal.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"productName",
	"dfrId",
	"dfrLineId",
	"assetNum"
})
public class ChangeSummaryDTO implements Serializable {

	private static final long serialVersionUID = 6770642533199437573L;
	
	@JsonProperty("productName")
	private String productName;
	
	@JsonProperty("dfrId")
	private String dfrId;
	
	@JsonProperty("dfrLineId")
	private String dfrLineId;
	
	@JsonProperty("assetNum")
	private String assetNum;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getDfrLineId() {
		return dfrLineId;
	}

	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}

	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}
}
