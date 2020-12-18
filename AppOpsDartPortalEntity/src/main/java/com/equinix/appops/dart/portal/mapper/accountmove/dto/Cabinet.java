package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.ArrayList;
import java.util.List;

public class Cabinet extends Asset {
	
	public Cabinet(Asset cabinet) {
		this.rowId=cabinet.getRowId(); //HEADER1 - ROW_ID
		this.assetNum=cabinet.getAssetNum(); //HEADER2 - Asset
		this.serialNum=cabinet.getSerialNum();	
		this.assetUCMID=cabinet.getAssetUCMID();
		this.ucmID=cabinet.getUcmID(); //HEADER5 - Account UCM Id
		this.account=cabinet.getAccount(); //HEADER7 -  Account Name 
		this.accountName=cabinet.getAccountName();
		this.ibxName=cabinet.getIbxName(); //HEADER8 - IBX
		this.xUniqueSpaceId=cabinet.getxUniqueSpaceId(); //HEADER9 - Cage Unique Space ID
		this.cageUSID=cabinet.getCageUSID(); //HEADER10 - Cage_ Unique Space Id
		this.cabinetUSId=cabinet.getCabinetUSId(); //HEADER11 - Cabinet Unique Space ID
		this.cabUSIdVal=cabinet.getCabUSIdVal();
		this.cabinetNumber=cabinet.getCabinetNumber();
		this.systemName=cabinet.getSystemName(); //HEADER52 - Asset System Name
		this.poeName=cabinet.getPoeName();
		this.parAssetId=cabinet.getParAssetId(); // - PAR ASSET ID
		this.rootAssetId=cabinet.getRootAssetId(); //HEADER22 - ROOT ASSET ID
		this.pofAssetNum=cabinet.getPofAssetNum();
		this.pofName=cabinet.getPofName();//Header26 - POF_NAME
		this.relatedAccountNumber=cabinet.getRelatedAccountNumber();
		this.cableId=cabinet.getCableId(); //HEADER43 - OPS Cable Id
		this.ownerAccoutId=cabinet.getOwnerAccoutId(); // Header53 - owner Account Id
		this.ppId=cabinet.getPpId(); //HEADER54 - Patch Panel Id
		this.parentAssetId=cabinet.getParentAssetId(); //HEADER27 - X OPS PAR ASSET NUM
		this.parentAsset=cabinet.getParentAsset();
		this.hieararcyLevel=cabinet.getHieararcyLevel();
		this.product=cabinet.getProduct(); //HEADER20 - Product
		this.isModified = cabinet.isModified();
	}

	List<Circuit> circuits = new ArrayList<Circuit>(0);
	List<PatchPanel> patchPanels = new ArrayList<PatchPanel>(0);

	
	public List<Circuit> getCircuits() {
		return circuits;
	}

	public void setCircuits(List<Circuit> circuits) {
		this.circuits = circuits;
	}

	public List<PatchPanel> getPatchPanels() {
		return patchPanels;
	}

	public void setPatchPanels(List<PatchPanel> patchPanels) {
		this.patchPanels = patchPanels;
	}

}
