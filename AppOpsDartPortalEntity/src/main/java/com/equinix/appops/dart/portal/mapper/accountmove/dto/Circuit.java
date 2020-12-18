package com.equinix.appops.dart.portal.mapper.accountmove.dto;

public class Circuit extends Asset{

	public Circuit(Asset circuit) {
		this.rowId=circuit.getRowId(); //HEADER1 - ROW_ID
		this.assetNum=circuit.getAssetNum(); //HEADER2 - Asset
		this.serialNum=circuit.getSerialNum();	
		this.assetUCMID=circuit.getAssetUCMID();
		this.ucmID=circuit.getUcmID(); //HEADER5 - Account UCM Id
		this.account=circuit.getAccount(); //HEADER7 -  Account Name 
		this.accountName=circuit.getAccountName();
		this.ibxName=circuit.getIbxName(); //HEADER8 - IBX
		this.xUniqueSpaceId=circuit.getxUniqueSpaceId(); //HEADER9 - Cage Unique Space ID
		this.cageUSID=circuit.getCageUSID(); //HEADER10 - Cage_ Unique Space Id
		this.cabinetUSId=circuit.getCabinetUSId(); //HEADER11 - Cabinet Unique Space ID
		this.cabUSIdVal=circuit.getCabUSIdVal();
		this.cabinetNumber=circuit.getCabinetNumber();
		this.systemName=circuit.getSystemName(); //HEADER52 - Asset System Name
		this.poeName=circuit.getPoeName();
		this.parAssetId=circuit.getParAssetId(); // - PAR ASSET ID
		this.rootAssetId=circuit.getRootAssetId(); //HEADER22 - ROOT ASSET ID
		this.pofAssetNum=circuit.getPofAssetNum();
		this.pofName=circuit.getPofName();//Header26 - POF_NAME
		this.relatedAccountNumber=circuit.getRelatedAccountNumber();
		this.cableId=circuit.getCableId(); //HEADER43 - OPS Cable Id
		this.ownerAccoutId=circuit.getOwnerAccoutId(); // Header53 - owner Account Id
		this.ppId=circuit.getPpId(); //HEADER54 - Patch Panel Id
		this.parentAssetId=circuit.getParentAssetId(); //HEADER27 - X OPS PAR ASSET NUM
		this.parentAsset=circuit.getParentAsset();
		this.hieararcyLevel=circuit.getHieararcyLevel();
		this.product=circuit.getProduct(); //HEADER20 - Product
		this.isModified = circuit.isModified();
	}
}
