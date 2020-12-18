package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.SrcSAssetXa;

public interface SrcAssetXADao {
	public List<SrcSAssetXa> getSrcAssetXA(String assetId);
}
