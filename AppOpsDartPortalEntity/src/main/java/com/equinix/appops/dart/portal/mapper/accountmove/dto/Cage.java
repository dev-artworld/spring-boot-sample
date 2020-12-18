package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.ArrayList;
import java.util.List;

public class Cage extends Asset {

	public Cage(Asset cage) {
		this.rowId=cage.getRowId(); //HEADER1 - ROW_ID
		this.assetNum=cage.getAssetNum(); //HEADER2 - Asset
		this.serialNum=cage.getSerialNum();	
		this.assetUCMID=cage.getAssetUCMID();
		this.ucmID=cage.getUcmID(); //HEADER5 - Account UCM Id
		this.account=cage.getAccount(); //HEADER7 -  Account Name 
		this.accountName=cage.getAccountName();
		this.ibxName=cage.getIbxName(); //HEADER8 - IBX
		this.xUniqueSpaceId=cage.getxUniqueSpaceId(); //HEADER9 - Cage Unique Space ID
		this.cageUSID=cage.getCageUSID(); //HEADER10 - Cage_ Unique Space Id
		this.cabinetUSId=cage.getCabinetUSId(); //HEADER11 - Cabinet Unique Space ID
		this.cabUSIdVal=cage.getCabUSIdVal();
		this.cabinetNumber=cage.getCabinetNumber();
		this.systemName=cage.getSystemName(); //HEADER52 - Asset System Name
		this.poeName=cage.getPoeName();
		this.parAssetId=cage.getParAssetId(); // - PAR ASSET ID
		this.rootAssetId=cage.getRootAssetId(); //HEADER22 - ROOT ASSET ID
		this.pofAssetNum=cage.getPofAssetNum();
		this.pofName=cage.getPofName();//Header26 - POF_NAME
		this.relatedAccountNumber=cage.getRelatedAccountNumber();
		this.cableId=cage.getCableId(); //HEADER43 - OPS Cable Id
		this.ownerAccoutId=cage.getOwnerAccoutId(); // Header53 - owner Account Id
		this.ppId=cage.getPpId(); //HEADER54 - Patch Panel Id
		this.parentAssetId=cage.getParentAssetId(); //HEADER27 - X OPS PAR ASSET NUM
		this.parentAsset=cage.getParentAsset();
		this.hieararcyLevel=cage.getHieararcyLevel();
		this.product=cage.getProduct(); //HEADER20 - Product
		this.isModified = cage.isModified();
	}
	
	List<Cabinet> cabinets=new ArrayList<Cabinet>(0);

	public List<Cabinet> getCabinets() {
		return cabinets;
	}

	public void setCabinets(List<Cabinet> cabinets) {
		this.cabinets = cabinets;
	}

}
