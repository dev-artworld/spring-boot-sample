package com.equinix.appops.dart.portal.model.grid;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditDataGrid {
	
	@JsonProperty("auditProductList")
	private List<AuditProduct> auditProductList;
	
	@JsonProperty("editedAssetCount")
	private int editedAssetCount;
	
	@JsonProperty("formulaExtRef")
	private String formulaExtRef;
	
	@JsonProperty("messageText")
	private String messageText;
	
	public String getFormulaExtRef() {
		return formulaExtRef;
	}

	public void setFormulaExtRef(String formulaExtRef) {
		this.formulaExtRef = formulaExtRef;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	@JsonProperty("auditProductList")
	public List<AuditProduct> getAuditProductList() {
		if (auditProductList == null) {
			auditProductList = new ArrayList<AuditProduct>();
		}
		return auditProductList;
	}
	
	@JsonProperty("auditProductList")
	public void setAuditProductList(List<AuditProduct> auditProductList) {
		this.auditProductList = auditProductList;
	}

    @JsonProperty("editedAssetCount")
	public int getEditedAssetCount() {
		return editedAssetCount;
	}
	
	@JsonProperty("editedAssetCount")
	public void setEditedAssetCount(int editedAssetCount) {
		this.editedAssetCount = editedAssetCount;
	}
	

}
