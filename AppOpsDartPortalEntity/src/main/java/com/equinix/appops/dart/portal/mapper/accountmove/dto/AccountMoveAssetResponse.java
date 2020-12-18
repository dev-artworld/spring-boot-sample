package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.List;

public class AccountMoveAssetResponse {

	String message;

	String product;

	List<AssetVaildationData> list;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<AssetVaildationData> getList() {
		return list;
	}

	public void setList(List<AssetVaildationData> list) {
		this.list = list;
	}


}
