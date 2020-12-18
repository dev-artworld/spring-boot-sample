package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.ArrayList;
import java.util.List;

public class PatchPanel extends Asset {
	
	public PatchPanel(Asset patchPanel) {
		this.rowId=patchPanel.getRowId(); //HEADER1 - ROW_ID
		this.assetNum=patchPanel.getAssetNum(); //HEADER2 - Asset
		this.serialNum=patchPanel.getSerialNum();	
		this.assetUCMID=patchPanel.getAssetUCMID();
		this.ucmID=patchPanel.getUcmID(); //HEADER5 - Account UCM Id
		this.account=patchPanel.getAccount(); //HEADER7 -  Account Name 
		this.accountName=patchPanel.getAccountName();
		this.ibxName=patchPanel.getIbxName(); //HEADER8 - IBX
		this.xUniqueSpaceId=patchPanel.getxUniqueSpaceId(); //HEADER9 - Cage Unique Space ID
		this.cageUSID=patchPanel.getCageUSID(); //HEADER10 - Cage_ Unique Space Id
		this.cabinetUSId=patchPanel.getCabinetUSId(); //HEADER11 - Cabinet Unique Space ID
		this.cabUSIdVal=patchPanel.getCabUSIdVal();
		this.cabinetNumber=patchPanel.getCabinetNumber();
		this.systemName=patchPanel.getSystemName(); //HEADER52 - Asset System Name
		this.poeName=patchPanel.getPoeName();
		this.parAssetId=patchPanel.getParAssetId(); // - PAR ASSET ID
		this.rootAssetId=patchPanel.getRootAssetId(); //HEADER22 - ROOT ASSET ID
		this.pofAssetNum=patchPanel.getPofAssetNum();
		this.pofName=patchPanel.getPofName();//Header26 - POF_NAME
		this.relatedAccountNumber=patchPanel.getRelatedAccountNumber();
		this.cableId=patchPanel.getCableId(); //HEADER43 - OPS Cable Id
		this.ownerAccoutId=patchPanel.getOwnerAccoutId(); // Header53 - owner Account Id
		this.ppId=patchPanel.getPpId(); //HEADER54 - Patch Panel Id
		this.parentAssetId=patchPanel.getParentAssetId(); //HEADER27 - X OPS PAR ASSET NUM
		this.parentAsset=patchPanel.getParentAsset();
		this.hieararcyLevel=patchPanel.getHieararcyLevel();
		this.product=patchPanel.getProduct(); //HEADER20 - Product
		this.isModified = patchPanel.isModified();
	}
	
	List<NetworkCabelConnection> networkCabelConnection = new ArrayList<NetworkCabelConnection>(0);

	public List<NetworkCabelConnection> getNetworkCabelConnection() {
		return networkCabelConnection;
	}

	public void setNetworkCabelConnection(List<NetworkCabelConnection> networkCabelConnection) {
		this.networkCabelConnection = networkCabelConnection;
	}
	
}
