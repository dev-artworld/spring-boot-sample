package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.equinix.appops.dart.portal.model.grid.Attribute;

public class EditForm implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;
	private String dfrLineId;
	private String dfrId;
	private String audit_flag;
	private String selectedCabinetForAddDfrLineId;
	/*cabinet or cage dfrline ID */
	private String parentDfrLineId;
	private LinkedList<Attribute> items;
	private List<String> childDfrLineIds;
	private String parentPPDfrLineId ;
	private String modifiedDateStr;
	
	
	// if AC Circuit cabinet got changed then should received values in below attributes 
	private String dfrLineIdToMove; 
	private String destinationDfrLineId;
	private boolean isMobileDfr;
	
	private HashMap<String,Object> childDetails;
	
	private boolean isAssetToBeAudit;
	private String isFlaggedForAudit;
	private boolean isError;
	
	private String aSideOrZSide;
	
	private String zSideSystemName;
	private String nccIbx;
	
	/*
	 * below three properties will be used when any pp serial number got change 
	 * for e.g. If its panel type got changed 
	 *          If its cabinet number got changed 
	 *            Then need to update Z Side NCC of this pp in against the snapshot_siebel_asset  for the 
	 *            dfr id in asset_new_val  
	 */
	private boolean isPPMoved;
	private String  oldPPSerialNum;
	private String  modifiedPPSerialNum;
	private boolean isStagingDfr;
	
	boolean isAssetMoved ;
	
	public String getaSideOrZSide() {
		return aSideOrZSide;
	}
	public void setaSideOrZSide(String aSideOrZSide) {
		this.aSideOrZSide = aSideOrZSide;
	}
	public String getDfrLineIdToMove() {
		return dfrLineIdToMove;
	}
	public void setDfrLineIdToMove(String dfrLineIdToMove) {
		this.dfrLineIdToMove = dfrLineIdToMove;
	}
	public String getDestinationDfrLineId() {
		return destinationDfrLineId;
	}
	public void setDestinationDfrLineId(String destinationDfrLineId) {
		this.destinationDfrLineId = destinationDfrLineId;
	}
	public String getSelectedCabinetForAddDfrLineId() {
		return selectedCabinetForAddDfrLineId;
	}
	public void setSelectedCabinetForAddDfrLineId(String selectedCabinetForAddDfrLineId) {
		this.selectedCabinetForAddDfrLineId = selectedCabinetForAddDfrLineId;
	}
	public String getAudit_flag() {
		return audit_flag;
	}
	public void setAudit_flag(String audit_flag) {
		this.audit_flag = audit_flag;
	}
	public String getParentDfrLineId() {
		return parentDfrLineId;
	}
	public void setParentDfrLineId(String parentDfrLineId) {
		this.parentDfrLineId = parentDfrLineId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDfrLineId() {
		return dfrLineId;
	}
	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}
	public LinkedList<Attribute> getItems() {
		return items;
	}
	public void setItems(LinkedList<Attribute> items) {
		this.items = items;
	}
	public String getDfrId() {
		return dfrId;
	}
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
	public List<String> getChildDfrLineIds() {
		return childDfrLineIds;
	}
	public void setChildDfrLineIds(List<String> childDfrLineIds) {
		this.childDfrLineIds = childDfrLineIds;
	}
	public boolean isMobileDfr() {
		return isMobileDfr;
	}
	public void setMobileDfr(boolean isMobileDfr) {
		this.isMobileDfr = isMobileDfr;
	}
	public HashMap<String, Object> getChildDetails() {
		return childDetails;
	}
	public void setChildDetails(HashMap<String, Object> childDetails) {
		this.childDetails = childDetails;
	}
	public boolean isAssetToBeAudit() {
		return isAssetToBeAudit;
	}
	public void setAssetToBeAudit(boolean isAssetToBeAudit) {
		this.isAssetToBeAudit = isAssetToBeAudit;
	}
	public String getIsFlaggedForAudit() {
		return isFlaggedForAudit;
	}
	public void setIsFlaggedForAudit(String isFlaggedForAudit) {
		this.isFlaggedForAudit = isFlaggedForAudit;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public String getParentPPDfrLineId() {
		return parentPPDfrLineId;
	}
	public void setParentPPDfrLineId(String parentPPDfrLineId) {
		this.parentPPDfrLineId = parentPPDfrLineId;
	}
	public String getzSideSystemName() {
		return zSideSystemName;
	}
	public void setzSideSystemName(String zSideSystemName) {
		this.zSideSystemName = zSideSystemName;
	}
	public String getNccIbx() {
		return nccIbx;
	}
	public void setNccIbx(String nccIbx) {
		this.nccIbx = nccIbx;
	}
	public boolean isAssetMoved() {
		return isAssetMoved;
	}
	public void setAssetMoved(boolean isAssetMoved) {
		this.isAssetMoved = isAssetMoved;
	}
	public boolean isPPMoved() {
		return isPPMoved;
	}
	public void setPPMoved(boolean isPPMoved) {
		this.isPPMoved = isPPMoved;
	}
	public String getOldPPSerialNum() {
		return oldPPSerialNum;
	}
	public void setOldPPSerialNum(String oldPPSerialNum) {
		this.oldPPSerialNum = oldPPSerialNum;
	}
	public String getModifiedPPSerialNum() {
		return modifiedPPSerialNum;
	}
	public void setModifiedPPSerialNum(String modifiedPPSerialNum) {
		this.modifiedPPSerialNum = modifiedPPSerialNum;
	}
	public String getModifiedDateStr() {
		return modifiedDateStr;
	}
	public void setModifiedDateStr(String modifiedDateStr) {
		this.modifiedDateStr = modifiedDateStr;
	}
	public boolean isStagingDfr() {
		return isStagingDfr;
	}
	public void setStagingDfr(boolean isStagingDfr) {
		this.isStagingDfr = isStagingDfr;
	}
	
	
}
