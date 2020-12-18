package com.equinix.appops.dart.portal.buisness;

import java.util.List;

import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.PortAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetResponse;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;

public interface AppOpsAccountMovementBusiness {

	List<RootAsset> getRootAssestId(String accountId, String cageUSID);
	
	OpsHierarchyAsset getOpsHierarchyByRootAssesstId(String rootAssesstId);

	AccountMoveAssetResponse validateAssetMove(AccountMoveAssetRequest request);

	void saveAccountMoveRequest(AccountMoveRequest accountMoveRequest);

	AccountMoveRequest getAccountMoveRequest(String dfrId);

	List<PortAsset> getListPortAssets(String assetId,boolean flag);

	List<String> postValidateForPatchPanel(AccountMoveAssetRequest request);
}
