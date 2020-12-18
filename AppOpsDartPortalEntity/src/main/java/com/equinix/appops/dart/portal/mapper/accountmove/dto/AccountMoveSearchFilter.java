package com.equinix.appops.dart.portal.mapper.accountmove.dto;

public class AccountMoveSearchFilter {

	String account;
	String cageUSID;
	String cageAssetId;
	String cageRootAssetId;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCageUSID() {
		return cageUSID;
	}

	public void setCageUSID(String cageUSID) {
		this.cageUSID = cageUSID;
	}

	public String getCageAssetId() {
		return cageAssetId;
	}

	public void setCageAssetId(String cageAssetId) {
		this.cageAssetId = cageAssetId;
	}

	public String getCageRootAssetId() {
		return cageRootAssetId;
	}

	public void setCageRootAssetId(String cageRootAssetId) {
		this.cageRootAssetId = cageRootAssetId;
	}
}
