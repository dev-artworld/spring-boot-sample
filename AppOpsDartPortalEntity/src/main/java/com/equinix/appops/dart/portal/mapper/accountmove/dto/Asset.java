package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.HashSet;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.AssetNewVal;

public class Asset {
		
	protected String rowId; //HEADER1 - ROW_ID
	protected String assetNum; //HEADER2 - Asset
	protected String serialNum;	
	protected String assetUCMID;
	protected String ucmID; //HEADER5 - Account UCM Id
	protected String account; //HEADER7 -  Account Name 
	protected String accountName;
	protected String ibxName; //HEADER8 - IBX
	protected String xUniqueSpaceId; //HEADER9 - Cage Unique Space ID
	protected String cageUSID; //HEADER10 - Cage_ Unique Space Id
	protected String cabinetUSId; //HEADER11 - Cabinet Unique Space ID
	protected String cabUSIdVal;
	protected String cabinetNumber;
	protected String systemName; //HEADER52 - Asset System Name
	protected String poeName;
	protected String parAssetId; // - PAR ASSET ID
	protected String rootAssetId; //HEADER22 - ROOT ASSET ID
	protected String pofAssetNum;
	protected String pofName;//Header26 - POF_NAME
	protected String relatedAccountNumber;
	protected String cableId; //HEADER43 - OPS Cable Id
	protected String ownerAccoutId; // Header53 - owner Account Id
	protected String ppId; //HEADER54 - Patch Panel Id
	protected String parentAssetId; //HEADER27 - X OPS PAR ASSET NUM
	protected String parentAsset;
	protected Integer hieararcyLevel;
	protected String product; //HEADER20 - Product
	protected boolean isModified = false;
	protected String xATTR22; //X_ATTR_22 - 
    
    Set<Asset> childAssetList = new HashSet<>(0);
    
  	AssetNewVal assetNewVal;
  	
  
  	public String getxATTR22() {
		return xATTR22;
	}
	public void setxATTR22(String xATTR22) {
		this.xATTR22 = xATTR22;
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
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getAssetUCMID() {
		return assetUCMID;
	}
	public void setAssetUCMID(String assetUCMID) {
		this.assetUCMID = assetUCMID;
	}
	public String getUcmID() {
		return ucmID;
	}
	public void setUcmID(String ucmID) {
		this.ucmID = ucmID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getIbxName() {
		return ibxName;
	}
	public void setIbxName(String ibxName) {
		this.ibxName = ibxName;
	}
	public String getxUniqueSpaceId() {
		return xUniqueSpaceId;
	}
	public void setxUniqueSpaceId(String xUniqueSpaceId) {
		this.xUniqueSpaceId = xUniqueSpaceId;
	}
	public String getCageUSID() {
		return cageUSID;
	}
	public void setCageUSID(String cageUSID) {
		this.cageUSID = cageUSID;
	}
	public String getCabinetUSId() {
		return cabinetUSId;
	}
	public void setCabinetUSId(String cabinetUSId) {
		this.cabinetUSId = cabinetUSId;
	}	
	
	public String getCabUSIdVal() {
		return cabUSIdVal;
	}
	public void setCabUSIdVal(String cabUSIdVal) {
		this.cabUSIdVal = cabUSIdVal;
	}
	public String getCabinetNumber() {
		return cabinetNumber;
	}
	public void setCabinetNumber(String cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getPoeName() {
		return poeName;
	}
	public void setPoeName(String poeName) {
		this.poeName = poeName;
	}
	public String getParAssetId() {
		return parAssetId;
	}
	public void setParAssetId(String parAssetId) {
		this.parAssetId = parAssetId;
	}
	public String getRootAssetId() {
		return rootAssetId;
	}
	public void setRootAssetId(String rootAssetId) {
		this.rootAssetId = rootAssetId;
	}
	public String getPofAssetNum() {
		return pofAssetNum;
	}
	public void setPofAssetNum(String pofAssetNum) {
		this.pofAssetNum = pofAssetNum;
	}
	public String getPofName() {
		return pofName;
	}
	public void setPofName(String pofName) {
		this.pofName = pofName;
	}
	public String getRelatedAccountNumber() {
		return relatedAccountNumber;
	}
	public void setRelatedAccountNumber(String relatedAccountNumber) {
		this.relatedAccountNumber = relatedAccountNumber;
	}
	public String getCableId() {
		return cableId;
	}
	public void setCableId(String cableId) {
		this.cableId = cableId;
	}
	public String getOwnerAccoutId() {
		return ownerAccoutId;
	}
	public void setOwnerAccoutId(String ownerAccoutId) {
		this.ownerAccoutId = ownerAccoutId;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getParentAssetId() {
		return parentAssetId;
	}
	public void setParentAssetId(String parentAssetId) {
		this.parentAssetId = parentAssetId;
	}
	public String getParentAsset() {
		return parentAsset;
	}
	public void setParentAsset(String parentAsset) {
		this.parentAsset = parentAsset;
	}
	public Integer getHieararcyLevel() {
		return hieararcyLevel;
	}
	public void setHieararcyLevel(Integer hieararcyLevel) {
		this.hieararcyLevel = hieararcyLevel;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public boolean isModified() {
		return isModified;
	}
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
	public Set<Asset> getChildAssetList() {
		return childAssetList;
	}
	public void setChildAssetList(Set<Asset> childAssetList) {
		this.childAssetList = childAssetList;
	}
	public AssetNewVal getAssetNewVal() {
		return assetNewVal;
	}
	public void setAssetNewVal(AssetNewVal assetNewVal) {
		this.assetNewVal = assetNewVal;
	}
	private void setAssetNewValDfrId(String dfrId){
		if(this.assetNewVal!=null){
			this.assetNewVal.setDfrId(dfrId);
		}
	}
	private void setAssetNewValDfrIdLineId(String dfrId){
		if(this.assetNewVal!=null){
			this.assetNewVal.setDfrLineId(this.assetNewVal.getHeader1()+"."+dfrId);
			this.assetNewVal.setHeader1(this.assetNewVal.getHeader1()+"."+dfrId);
		}
	}
	
	private void setAssetNewValParentDfrLineId(String dfrId){
		if(this.assetNewVal!=null){
			this.assetNewVal.setHeader27(this.assetNewVal.getHeader27()+"."+dfrId);
		}
	}
	
	public void populateAssetNewValDefaultValues(String dfrId){
		this.setAssetNewValDfrId(dfrId);
		this.setAssetNewValDfrIdLineId(dfrId);
		this.setAssetNewValParentDfrLineId(dfrId);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asset [rowId=");
		builder.append(rowId);
		builder.append(", assetNum=");
		builder.append(assetNum);
		builder.append(", serialNum=");
		builder.append(serialNum);
		builder.append(", assetUCMID=");
		builder.append(assetUCMID);
		builder.append(", ucmID=");
		builder.append(ucmID);
		builder.append(", account=");
		builder.append(account);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", ibxName=");
		builder.append(ibxName);
		builder.append(", xUniqueSpaceId=");
		builder.append(xUniqueSpaceId);
		builder.append(", cageUSID=");
		builder.append(cageUSID);
		builder.append(", cabinetUSId=");
		builder.append(cabinetUSId);
		builder.append(", cabUSIdVal=");
		builder.append(cabUSIdVal);
		builder.append(", cabinetNumber=");
		builder.append(cabinetNumber);
		builder.append(", systemName=");
		builder.append(systemName);
		builder.append(", poeName=");
		builder.append(poeName);
		builder.append(", parAssetId=");
		builder.append(parAssetId);
		builder.append(", rootAssetId=");
		builder.append(rootAssetId);
		builder.append(", pofAssetNum=");
		builder.append(pofAssetNum);
		builder.append(", pofName=");
		builder.append(pofName);
		builder.append(", relatedAccountNumber=");
		builder.append(relatedAccountNumber);
		builder.append(", cableId=");
		builder.append(cableId);
		builder.append(", ownerAccoutId=");
		builder.append(ownerAccoutId);
		builder.append(", ppId=");
		builder.append(ppId);
		builder.append(", parentAssetId=");
		builder.append(parentAssetId);
		builder.append(", parentAsset=");
		builder.append(parentAsset);
		builder.append(", hieararcyLevel=");
		builder.append(hieararcyLevel);
		builder.append(", product=");
		builder.append(product);
		builder.append(", isModified=");
		builder.append(isModified);
		builder.append(", childAssetList=");
		builder.append(childAssetList);
		builder.append(", assetNewVal=");
		builder.append(assetNewVal);
		builder.append("]");
		return builder.toString();
	}

	
}
