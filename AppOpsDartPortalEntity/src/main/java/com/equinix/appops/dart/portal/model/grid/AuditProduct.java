package com.equinix.appops.dart.portal.model.grid;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"productName",
	"auditAttributeList"
})
public class AuditProduct {
	
	@JsonProperty("productName")
	private String productName;
	
	@JsonProperty("auditAttributeList")
	private List<AuditAttribute> auditAttributeList;
	
	@JsonProperty("auditAttributeList")
	public List<AuditAttribute> getAuditAttributeList() {
		if (auditAttributeList == null) {
			auditAttributeList = new ArrayList<AuditAttribute>();
		}
		return auditAttributeList;
	}
	
	@JsonProperty("auditAttributeList")
	public void setAuditAttributeList(List<AuditAttribute> auditAttributeList) {
		this.auditAttributeList = auditAttributeList;
	}
	
	@JsonProperty("productName")
	public String getProductName() {
		return productName;
	}
	
	@JsonProperty("productName")
	public void setProductName(String productName) {
		this.productName = productName;
	}

}
