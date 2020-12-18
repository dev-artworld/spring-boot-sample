package com.equinix.appops.dart.portal.buisness.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.buisness.AppOpsAccountMovementBusiness;
import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.PortAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetResponse;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AssetVaildationData;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;
import com.equinix.appops.dart.portal.service.AppOpsAccountMovementService;

@Service
public class AppOpsAccountMovementBusinessImpl implements AppOpsAccountMovementBusiness {
	Logger logger = LoggerFactory.getLogger(AppOpsAccountMovementBusiness.class);

	@Autowired
	AppOpsAccountMovementService appOpsAccountMovementService;

	@Override
	public List<RootAsset> getRootAssestId(String accountId, String cageUSID) {
		return appOpsAccountMovementService.getRootAssestId(accountId,cageUSID);
	}

	@Override
	public OpsHierarchyAsset getOpsHierarchyByRootAssesstId(String rootAssesstId) {
		OpsHierarchyAsset assets= new OpsHierarchyAsset();
		List<Asset> assetList =  appOpsAccountMovementService.getOpsHierarchyByRootAssesstId(assets, rootAssesstId);
		Asset cage=null;
		cage=prepareOpsHierarchy(assetList, cage);
		OpsHierarchyAsset asset = new OpsHierarchyAsset(); // TODO: We can send directly itself cage but due to front end change keep it OpsHierarchy.
		asset.getCages().put(cage.getAssetNum(), cage);
		return asset;
	}

	private Asset prepareOpsHierarchy(List<Asset> assets, Asset cage) {
		if(cage == null) {
			for (Asset asset : assets) {
				System.out.print(","+asset.getProduct());
				if(asset.getProduct().equalsIgnoreCase("Cage")) {
					cage=asset;
					prepareOpsHierarchy(assets, cage);
				}
			}
		} else {
			Set<Asset> childAsset=new HashSet<>(0);
			findChildren(assets,cage,childAsset);
			if(childAsset.size()==0) {
				return null;
			} else {
				cage.getChildAssetList().addAll(childAsset);
				for (Asset c : childAsset) {
					prepareOpsHierarchy(assets, c);
				}
			}
		}
		return cage;
	}

	private Set<Asset> findChildren(List<Asset> assets, Asset parentAsset, Set<Asset> children) {
		for (Asset asset : assets) {
			try{
				if(asset.getParentAsset().equalsIgnoreCase(parentAsset.getAssetNum())) {
					children.add(asset);
				}
			}catch(Exception e){
				
			}
		}
		return children;
	}

	@Override
	public AccountMoveAssetResponse validateAssetMove(AccountMoveAssetRequest request) {
		StringBuffer msg = new StringBuffer();

		AccountMoveAssetResponse response = new AccountMoveAssetResponse();
		response.setMessage("OK");
		boolean isVaildMove = true;
		if (request.getxATTR22().equalsIgnoreCase("N")) {
			response = appOpsAccountMovementService.validateInBillingTable(request);
			
			if (response.getList() != null) {
			msg.append("Account is Incorrect at Billing End ");
			for (AssetVaildationData assetData : response.getList()) {
					if (assetData.getAccountNum().equalsIgnoreCase(request.getTargetCriteria().getAccount()) == false) {
					isVaildMove = false;
					msg.append(assetData.getAssetNum()).append(" - ").append(assetData.getAccountNum()).append(" ");

				}
			}
		}
		}
		response = appOpsAccountMovementService.validateIsAnyOpenOrderExist(request);
		if(response.getList() != null && response.getList().size() > 1) {
			msg.append(" ").append("Open Order Exists in Siebel :");
			for (AssetVaildationData assetData : response.getList()) {
				if(assetData.getAccountNum().equalsIgnoreCase(request.getTargetCriteria().getAccount()) == false) {
					isVaildMove = false;
					msg.append(assetData.getAssetNum()).append(" - ").append(assetData.getOrderNum()).append(" ");

				}
			}

		}



		response = appOpsAccountMovementService.validateIsAnyOpenQuoteExist(request);
		if(response.getList() != null && response.getList().size() > 1) {
			msg.append(" ").append("Open Quote Exists in Siebel :");
			for (AssetVaildationData assetData : response.getList()) {
				if(assetData.getAccountNum().equalsIgnoreCase(request.getTargetCriteria().getAccount()) == false) {
					isVaildMove = false;
					msg.append(assetData.getAssetNum()).append(" - ").append(assetData.getQuoteNum()).append(" ");

				}
			}

		}

		if(request.getIsNCCChildValidation()==true){
			response = appOpsAccountMovementService.validateNCCMOVE(request);
			if(response.getList() != null && response.getList().size() > 1) {
				msg.append(" ").append("Account is Incorrect at Billing End :");
				for (AssetVaildationData assetData : response.getList()) {
					if(assetData.getAccountNum().equalsIgnoreCase(request.getTargetCriteria().getAccount()) == false) {
						isVaildMove = false;
						msg.append(assetData.getAssetNum()).append(", ");

					}
				}
				if(isVaildMove ==false){
					msg.append(" - ").append(request.getSourceCriteria().getAccount()).append(" ");
				}

			}
		}

		if(isVaildMove) {
			//Execute the update ATTR Queries.
			//String updateResponse = updateAssetAttr(request);
			msg=new StringBuffer("OK");
			//msg.append("update Attr (").append(updateResponse).append(")");
			response.setMessage(msg.toString());
		}else {
			response.setMessage(msg.toString());
		}

		return response;
	}

	@Override
	public void saveAccountMoveRequest(AccountMoveRequest request) {
		appOpsAccountMovementService.saveAccountMoveRequest(request);
	}

	@Override
	public AccountMoveRequest getAccountMoveRequest(String dfrId) {
		return appOpsAccountMovementService.getAccountMoveRequest(dfrId);
	}
	
	@Override
	public List<PortAsset> getListPortAssets(String assetId,boolean flag) {
		return appOpsAccountMovementService.getListPortAssets( assetId,flag);
	}

	@Override
	public List<String> postValidateForPatchPanel(AccountMoveAssetRequest request) {
		List<String> assetNums = appOpsAccountMovementService.postValidateForPatchPanel(request);
		return assetNums;
	}

}
