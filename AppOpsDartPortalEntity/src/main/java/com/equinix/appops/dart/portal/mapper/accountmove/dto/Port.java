package com.equinix.appops.dart.portal.mapper.accountmove.dto;

public class Port extends Asset {

	public Port(Asset port) {
		
		this.rowId=port.getRowId(); //HEADER1 - ROW_ID
		this.assetNum=port.getAssetNum(); //HEADER2 - Asset
		this.serialNum=port.getSerialNum();	
		this.assetUCMID=port.getAssetUCMID();
		this.ucmID=port.getUcmID(); //HEADER5 - Account UCM Id
		this.account=port.getAccount(); //HEADER7 -  Account Name 
		this.accountName=port.getAccountName();
		this.ibxName=port.getIbxName(); //HEADER8 - IBX
		this.xUniqueSpaceId=port.getxUniqueSpaceId(); //HEADER9 - Cage Unique Space ID
		this.cageUSID=port.getCageUSID(); //HEADER10 - Cage_ Unique Space Id
		this.cabinetUSId=port.getCabinetUSId(); //HEADER11 - Cabinet Unique Space ID
		this.cabUSIdVal=port.getCabUSIdVal();
		this.cabinetNumber=port.getCabinetNumber();
		this.systemName=port.getSystemName(); //HEADER52 - Asset System Name
		this.poeName=port.getPoeName();
		this.parAssetId=port.getParAssetId(); // - PAR ASSET ID
		this.rootAssetId=port.getRootAssetId(); //HEADER22 - ROOT ASSET ID
		this.pofAssetNum=port.getPofAssetNum();
		this.pofName=port.getPofName();//Header26 - POF_NAME
		this.relatedAccountNumber=port.getRelatedAccountNumber();
		this.cableId=port.getCableId(); //HEADER43 - OPS Cable Id
		this.ownerAccoutId=port.getOwnerAccoutId(); // Header53 - owner Account Id
		this.ppId=port.getPpId(); //HEADER54 - Patch Panel Id
		this.parentAssetId=port.getParentAssetId(); //HEADER27 - X OPS PAR ASSET NUM
		this.parentAsset=port.getParentAsset();
		this.hieararcyLevel=port.getHieararcyLevel();
		this.product=port.getProduct(); //HEADER20 - Product
		this.isModified = port.isModified();
	}
}
