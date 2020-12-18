package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;

public class ErrorElasticData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String owneroffixing;
	private String svrid;
	private String errorname;
	private String sblrid;
	private String clxrid;
	private String validationclass;
	private String erruniqueid;
	private String errorcode;
	private String assetNum;
	
	public String getOwneroffixing() {
		return owneroffixing;
	}
	public void setOwneroffixing(String owneroffixing) {
		this.owneroffixing = owneroffixing;
	}
	public String getSvrid() {
		return svrid;
	}
	public void setSvrid(String svrid) {
		this.svrid = svrid;
	}
	public String getErrorname() {
		return errorname;
	}
	public void setErrorname(String errorname) {
		this.errorname = errorname;
	}
	public String getSblrid() {
		return sblrid;
	}
	public void setSblrid(String sblrid) {
		this.sblrid = sblrid;
	}
	public String getClxrid() {
		return clxrid;
	}
	public void setClxrid(String clxrid) {
		this.clxrid = clxrid;
	}
	public String getValidationclass() {
		return validationclass;
	}
	public void setValidationclass(String validationclass) {
		this.validationclass = validationclass;
	}
	public String getErruniqueid() {
		return erruniqueid;
	}
	public void setErruniqueid(String erruniqueid) {
		this.erruniqueid = erruniqueid;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
	public String getAssetNum() {
		return assetNum;
	}
	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public ErrorData buildSblErrorData(){
		return new ErrorData("SBL", this.sblrid, this.assetNum, this.errorcode, this.errorname, this.validationclass, this.owneroffixing);
	}
	public ErrorData buildClxErrorData(){
		return new ErrorData("CLX", this.sblrid, this.assetNum, this.errorcode, this.errorname, this.validationclass, this.owneroffixing);
	}
	public ErrorData buildSvErrorData(){
		return new ErrorData("SV", this.sblrid, this.assetNum, this.errorcode, this.errorname, this.validationclass, this.owneroffixing);
	}
}
