package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.ArrayList;
import java.util.List;

public class NetworkCabelConnection extends Asset {

	public NetworkCabelConnection(Asset ncc) {
		this.rowId=ncc.getRowId(); //HEADER1 - ROW_ID
		this.assetNum=ncc.getAssetNum(); //HEADER2 - Asset
		this.serialNum=ncc.getSerialNum();	
		this.assetUCMID=ncc.getAssetUCMID();
		this.ucmID=ncc.getUcmID(); //HEADER5 - Account UCM Id
		this.account=ncc.getAccount(); //HEADER7 -  Account Name 
		this.accountName=ncc.getAccountName();
		this.ibxName=ncc.getIbxName(); //HEADER8 - IBX
		this.xUniqueSpaceId=ncc.getxUniqueSpaceId(); //HEADER9 - Cage Unique Space ID
		this.cageUSID=ncc.getCageUSID(); //HEADER10 - Cage_ Unique Space Id
		this.cabinetUSId=ncc.getCabinetUSId(); //HEADER11 - Cabinet Unique Space ID
		this.cabUSIdVal=ncc.getCabUSIdVal();
		this.cabinetNumber=ncc.getCabinetNumber();
		this.systemName=ncc.getSystemName(); //HEADER52 - Asset System Name
		this.poeName=ncc.getPoeName();
		this.parAssetId=ncc.getParAssetId(); // - PAR ASSET ID
		this.rootAssetId=ncc.getRootAssetId(); //HEADER22 - ROOT ASSET ID
		this.pofAssetNum=ncc.getPofAssetNum();
		this.pofName=ncc.getPofName();//Header26 - POF_NAME
		this.relatedAccountNumber=ncc.getRelatedAccountNumber();
		this.cableId=ncc.getCableId(); //HEADER43 - OPS Cable Id
		this.ownerAccoutId=ncc.getOwnerAccoutId(); // Header53 - owner Account Id
		this.ppId=ncc.getPpId(); //HEADER54 - Patch Panel Id
		this.parentAssetId=ncc.getParentAssetId(); //HEADER27 - X OPS PAR ASSET NUM
		this.parentAsset=ncc.getParentAsset();
		this.hieararcyLevel=ncc.getHieararcyLevel();
		this.product=ncc.getProduct(); //HEADER20 - Product
		this.isModified = ncc.isModified();
	}
	List<Asset> equinxConnectPort = new ArrayList<Asset>(0);
	List<Asset> metroConnectPort = new ArrayList<Asset>(0);
	List<Asset> equinixConnectAddIPAllocation = new ArrayList<Asset>(0);
	List<Asset> equinixConnectMinBandWidthCommit = new ArrayList<Asset>(0);

	public List<Asset> getEquinxConnectPort() {
		return equinxConnectPort;
	}

	public void setEquinxConnectPort(List<Asset> equinxConnectPort) {
		this.equinxConnectPort = equinxConnectPort;
	}

	public List<Asset> getMetroConnectPort() {
		return metroConnectPort;
	}

	public void setMetroConnectPort(List<Asset> metroConnectPort) {
		this.metroConnectPort = metroConnectPort;
	}

	public List<Asset> getEquinixConnectAddIPAllocation() {
		return equinixConnectAddIPAllocation;
	}

	public void setEquinixConnectAddIPAllocation(List<Asset> equinixConnectAddIPAllocation) {
		this.equinixConnectAddIPAllocation = equinixConnectAddIPAllocation;
	}

	public List<Asset> getEquinixConnectMinBandWidthCommit() {
		return equinixConnectMinBandWidthCommit;
	}

	public void setEquinixConnectMinBandWidthCommit(List<Asset> equinixConnectMinBandWidthCommit) {
		this.equinixConnectMinBandWidthCommit = equinixConnectMinBandWidthCommit;
	}

}
