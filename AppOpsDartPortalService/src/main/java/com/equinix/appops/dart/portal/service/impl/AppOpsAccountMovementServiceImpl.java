package com.equinix.appops.dart.portal.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.AppOpsDartAccountMovementDao;
import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.PortAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetResponse;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;
import com.equinix.appops.dart.portal.service.AppOpsAccountMovementService;
import com.equinix.appops.dart.portal.service.ConfigService;

/**
 * 
 * @author Ankur Bhargava
 *
 */
@Service
@Transactional
public class AppOpsAccountMovementServiceImpl implements AppOpsAccountMovementService{
	
	Logger logger = LoggerFactory.getLogger(AppOpsAccountMovementService.class);
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	AppOpsDartAccountMovementDao  appOpsDartAccountMovementDao;

	@Override
	public List<RootAsset> getRootAssestId(String accountId, String cageUSID) {
		return appOpsDartAccountMovementDao.getRootAssestId(accountId,cageUSID);
	}
	
	@Override
	public List<Asset> getOpsHierarchyByRootAssesstId(OpsHierarchyAsset opsHierarchyAsset,String rootAssesstId) {
		return appOpsDartAccountMovementDao.getOpsHierarchyByRootAssesstId(opsHierarchyAsset, rootAssesstId);
	}

	@Override
	public AccountMoveAssetResponse validateInBillingTable(AccountMoveAssetRequest request) {
		return appOpsDartAccountMovementDao.validateInBillingTable(request);
	}

	@Override
	public AccountMoveAssetResponse validateIsAnyOpenOrderExist(AccountMoveAssetRequest request) {
		return appOpsDartAccountMovementDao.validateIsAnyOpenOrderExist(request);
	}

	@Override
	public AccountMoveAssetResponse validateIsAnyOpenQuoteExist(AccountMoveAssetRequest request) {
		return appOpsDartAccountMovementDao.validateIsAnyOpenQuoteExist(request);
	}

	@Override
	public AccountMoveAssetResponse updateAssetMoveAttr(AccountMoveAssetRequest request) {
		return appOpsDartAccountMovementDao.updateAssetMoveAttr(request);
	}

	@Override
	public void saveAccountMoveRequest(AccountMoveRequest request) {
		appOpsDartAccountMovementDao.saveAccountMoveRequest(request);
		
	}

	@Override
	public AccountMoveRequest getAccountMoveRequest(String dfrId) {
		return appOpsDartAccountMovementDao.getAccountMoveRequest(dfrId);
	}
	
	@Override
	public List<PortAsset> getListPortAssets(String assetId,boolean flag) {
		return appOpsDartAccountMovementDao.getListPortAssets( assetId,flag);
	}
	
	@Override
	public List<String> postValidateForPatchPanel(AccountMoveAssetRequest request) {
		return appOpsDartAccountMovementDao.postValidateForPatchPanel(request);
	}
	
	@Override
	public AccountMoveAssetResponse validateNCCMOVE(AccountMoveAssetRequest request) {
		return appOpsDartAccountMovementDao.validateNCCMOVE(request);
	}
	
}
