package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.PortAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetResponse;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;

public interface AppOpsDartAccountMovementDao {

	List<RootAsset> getRootAssestId(String accountId, String cageUSID);
	
	List<Asset> getOpsHierarchyByRootAssesstId(OpsHierarchyAsset asset, String rootAssetsId);

	AccountMoveAssetResponse validateInBillingTable(AccountMoveAssetRequest request);

	AccountMoveAssetResponse validateIsAnyOpenOrderExist(AccountMoveAssetRequest request);

	AccountMoveAssetResponse validateIsAnyOpenQuoteExist(AccountMoveAssetRequest request);

	AccountMoveAssetResponse updateAssetMoveAttr(AccountMoveAssetRequest request);

	void saveAccountMoveRequest(AccountMoveRequest request);

	AccountMoveRequest getAccountMoveRequest(String dfrId);

	List<PortAsset> getListPortAssets(String assetId,boolean flag);

	List<String> postValidateForPatchPanel(AccountMoveAssetRequest request);

	AccountMoveAssetResponse validateNCCMOVE(AccountMoveAssetRequest request);

	
}
