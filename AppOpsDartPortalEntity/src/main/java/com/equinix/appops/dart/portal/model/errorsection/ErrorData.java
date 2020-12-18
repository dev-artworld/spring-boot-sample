package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;

public class ErrorData implements Serializable {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tbl;
	private String rowId;
	private String assetNum;
	private String errorCode;
	private String errorName;
	private String validationClass;
	private String ownerOfFixing;
	
	
	
	public ErrorData(String tbl, String rowId, String assetNum, String errorCode, String errorName,
			String validationClass, String ownerOfFixing) {
		super();
		this.tbl = tbl;
		this.rowId = rowId;
		this.assetNum = assetNum;
		this.errorCode = errorCode;
		this.errorName = errorName;
		this.validationClass = validationClass;
		this.ownerOfFixing = ownerOfFixing;
	}
	public String getTbl() {
		return tbl;
	}
	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getAssetNum() {
		return assetNum;
	}
	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getValidationClass() {
		return validationClass;
	}
	public void setValidationClass(String validationClass) {
		this.validationClass = validationClass;
	}
	public String getOwnerOfFixing() {
		return ownerOfFixing;
	}
	public void setOwnerOfFixing(String ownerOfFixing) {
		this.ownerOfFixing = ownerOfFixing;
	}
	
	
	
}
