package com.equinix.appops.dart.portal.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.CxiErrorTbl;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.EqxClCabinet;
import com.equinix.appops.dart.portal.entity.EqxClCage;
import com.equinix.appops.dart.portal.entity.SAssetXa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorTbl;
import com.equinix.appops.dart.portal.entity.SrcEqxClCabinet;
import com.equinix.appops.dart.portal.entity.SrcEqxClCage;
import com.equinix.appops.dart.portal.entity.SrcSAssetXa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.model.DartSoapAuditModel;

public interface AppOpsInitiateDFRDao {
	List<SrcEqxClCage> getSRCCages(String cageId);
	List<SrcSAssetXa> getSRCAssets(String assetId);
	List<SrcEqxClCabinet> getSRCCabinates(String cabinateId);
	void saveOrUpdateCage(EqxClCage cages);
	void saveOrUpdateCages(List<EqxClCage> cages);
	
	void saveOrUpdateCabinate(EqxClCabinet cabinates);
	void saveOrUpdateCabinates(List<EqxClCabinet> cabinates);
	
	void saveOrUpdateAsset(SAssetXa assets);
	void saveOrUpdateAssets(List<SAssetXa> assets);
	
	Long getNextDartSeq();
	void saveOrUpdateDfrMaster(DfrMaster dfrMaster);
	void saveOrUpdateSnapshotSiebelAssetDa(SnapshotSiebelAssetDa snapshotSiebelAssetDa);
	void saveOrUpdateSnapshotClxAssetDa(SnapshotClxAssetDa snapshotClxAssetDa);
	void saveOrUpdateSnapshotSvAssetDa(SnapshotSvAssetDa snapshotSvAssetDa);
	/*void saveClxErrorSnapshots(SnapshotClxAssetDa sda, ClxAssetDa da, String dfrId);
	void saveSvErrorSnapshots(SnapshotSvAssetDa sda, SvAssetDa da, String dfrId);
	void saveSblErrorSnapshots(SnapshotSiebelAssetDa sda, SiebelAssetDa da, String dfrId);*/
	List<SnapshotClxAssetDa> getSnapshotClxAssetDaByDfrLineId(String dfrLineId);
	void saveOrUpdateAssetNewVal(AssetNewVal assetNewVal);
	List<SnapshotSiebelAssetDa> getSnapshotSibelAssetDaByRowId(String rowId);
	void saveOrUpdateApprovalHistory(ApprovalHistory approvalHistory);
	ApprovalHistory getLatestAppHistory(String dfrId);
	HashMap<String, HashMap<String, List<SrcCxiErrorTbl>>> getErrorList();
	void saveSblErrorSnapshots(SnapshotSiebelAssetDa sda, SiebelAssetDa da, String dfrId, List<SrcCxiErrorTbl> errors);
	void saveClxErrorSnapshots(SnapshotClxAssetDa sda, ClxAssetDa da, String dfrId, List<SrcCxiErrorTbl> errors);
	void saveSvErrorSnapshots(SnapshotSvAssetDa sda, SvAssetDa da, String dfrId, List<SrcCxiErrorTbl> errors);
	List<SrcEqxClCabinet> getSRCCabinates(Set<String> cabinateIds);
	List<SrcEqxClCage> getSRCCages(Set<String> clxCageIds);
	List<SrcSAssetXa> getSRCAssets(Set<String> assetIds);
	HashMap<String, List<SrcCxiErrorTbl>> getSblErrorMap();
	HashMap<String, List<SrcCxiErrorTbl>> getClxErrorMap();
	HashMap<String, List<SrcCxiErrorTbl>> getSvErrorMap();
	HashMap<String,SrcCxiErrorMasterTbl> getErrorMasterList();
	
	boolean saveSoapAudit(DartSoapAudit dartSoapAudit);
	List<DartSoapAudit> getAllSoapAuditReq();
	List<DartSoapAudit> getAuditsDfrorProduct(String dfrId, String product);
	List<DartSoapAudit> getRecentAuditsDfrorProduct(Long responseTime, long retryLimit);
	DartSoapAudit getDFRAuditByReqId(String dfrRequestId);
	HashMap<String, HashSet<String>> getErrorCodeSblRowIdMap();
	List<ApprovalHistory> getLatestAppHistoryList(String dfrId);
	Long getNextNoccIntegrationReqSeq();
	DFRFile getDFRFileByDFRId(String dfrId);
	void deleteDfrFile(DFRFile dfrFile);
	void saveOrUpdateDfrFile(DFRFile dfFile);
	void setSblErrorSnapshotList(SnapshotSiebelAssetDa snapshotSiebelAssetDa, SiebelAssetDa sbl, String dfrId,
			List<SrcCxiErrorTbl> sblErrorList, List<CxiErrorTbl> cxiErrorList);
	void saveOrUpdateBatchData(List<?> batchDataList);
	void setSvErrorSnapshotList(SnapshotSvAssetDa sda, SvAssetDa da, String dfrId, List<SrcCxiErrorTbl> errors,
			List<CxiErrorTbl> cxiErrorList);
	void setClxErrorSnapshotList(SnapshotClxAssetDa sda, ClxAssetDa da, String dfrId, List<SrcCxiErrorTbl> errors,
			List<CxiErrorTbl> cxiErrorTblList);
}
