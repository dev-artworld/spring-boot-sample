package com.equinix.appops.dart.portal.mapper.dto.pro;

public class AssetCount {

	private String createdBy;
	private String systemName;
	private String noOfAssets;
	private String newAssets;
	private String updates;
	private String teminators;
	private String newErrors;
	private String fixedErrors;
	private String remainingErrors;
	private String comment;
	private String region;
	private String priority;
	private String clxUpdate;
	private String override_flag;
	private String siebelFlag;
	private String caplogixFlag;
	private String validState;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getNoOfAssets() {
		return noOfAssets;
	}

	public void setNoOfAssets(String noOfAssets) {
		this.noOfAssets = noOfAssets;
	}

	public String getNewAssets() {
		return newAssets;
	}

	public void setNewAssets(String newAssets) {
		this.newAssets = newAssets;
	}

	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	public String getTeminators() {
		return teminators;
	}

	public void setTeminators(String teminators) {
		this.teminators = teminators;
	}

	public String getNewErrors() {
		return newErrors;
	}

	public void setNewErrors(String newErrors) {
		this.newErrors = newErrors;
	}

	public String getFixedErrors() {
		return fixedErrors;
	}

	public void setFixedErrors(String fixedErrors) {
		this.fixedErrors = fixedErrors;
	}

	public String getRemainingErrors() {
		return remainingErrors;
	}

	public void setRemainingErrors(String remainingErrors) {
		this.remainingErrors = remainingErrors;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getClxUpdate() {
		return clxUpdate;
	}

	public void setClxUpdate(String clxUpdate) {
		this.clxUpdate = clxUpdate;
	}

	public String getOverride_flag() {
		return override_flag;
	}

	public void setOverride_flag(String override_flag) {
		this.override_flag = override_flag;
	}

	public String getSiebelFlag() {
		return siebelFlag;
	}

	public void setSiebelFlag(String siebelFlag) {
		this.siebelFlag = siebelFlag;
	}

	public String getCaplogixFlag() {
		return caplogixFlag;
	}

	public void setCaplogixFlag(String caplogixFlag) {
		this.caplogixFlag = caplogixFlag;
	}
	
	public String getValidState() {
		return validState;
	}

	public void setValidState(String validState) {
		this.validState = validState;
	}

	@Override
	public String toString() {
		return "AssetCount [createdBy=" + createdBy + ", systemName=" + systemName + ", noOfAssets=" + noOfAssets
				+ ", newAssets=" + newAssets + ", updates=" + updates + ", teminators=" + teminators + ", newErrors="
				+ newErrors + ", fixedErrors=" + fixedErrors + ", remainingErrors=" + remainingErrors + ", comment="
				+ comment + ", region=" + region + ", priority=" + priority + ", clxUpdate=" + clxUpdate
				+ ", override_flag=" + override_flag + ", siebelFlag=" + siebelFlag + ", caplogixFlag=" + caplogixFlag
				+ ", validState=" + validState + "]";
	}

}
