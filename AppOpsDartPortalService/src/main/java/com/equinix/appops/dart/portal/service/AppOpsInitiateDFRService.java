package com.equinix.appops.dart.portal.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.equinix.appops.dart.portal.common.CaplogixSyncResponse;
import com.equinix.appops.dart.portal.common.DFRSyncResponse;
import com.equinix.appops.dart.portal.common.SVSoapResponse;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorTbl;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.DartSoapAuditModel;
import com.equinix.appops.dart.portal.model.dfr.DfrDaInput;

public interface AppOpsInitiateDFRService {
	HashMap<String, Object> initiateDfrAccountMove(AccountMoveAssetRequest request) throws Exception;
	HashMap<String, Object> initiateDfr(DfrDaInput dfrDaInput) throws InterruptedException, ExecutionException;
	List<SVSoapResponse> syncSVAPI(String dfrId);
	void syncCages(String cageId, String dfrId);
	void syncCabinates(String cabinateId, String dfrId);
	void syncAssets(String assetId, String dfrId);
	CaplogixSyncResponse syncCaplogixDfr(String dfrId) throws Exception;
	CaplogixSyncResponse syncSiebelDfr(String dfrId);
	CaplogixSyncResponse syncSVDfr(String dfrId);
	HashMap<String, List<SrcCxiErrorTbl>> getSblErrorMap();
	HashMap<String, List<SrcCxiErrorTbl>> getClxErrorMap();
	HashMap<String, List<SrcCxiErrorTbl>> getSvErrorMap();
	HashMap<String, HashMap<String, List<SrcCxiErrorTbl>>> init();
	void moveSblAssets(List<SiebelAssetDa> sblAssetDaList, String dfrId);
	void moveClxCabinetAssets(List<ClxAssetDa> clxAssetDaList, String dfrId);
	void moveClxCageAssets(List<ClxAssetDa> clxAssetDaList, String dfrId);
	void syncAssets(Set<String> assetIds, String dfrId);
	void syncCages(Set<String> cageIds, String dfrId);
	void syncCabinates(Set<String> cabinateIds, String dfrId);
	DFRSyncResponse syncCLXDfrKafkaConsumer(String dfrId);
	DFRSyncResponse syncSBLDfrKafkaConsumer(DFRKafkaMessageVO dfrId);
	DFRSyncResponse syncSVDfrKafkaConsumer(String dfrId);
	boolean saveSoapAudit(DartSoapAudit dartSoapAudit);
	List<DartSoapAuditModel> getAllSoapAuditReq();
	List<DartSoapAuditModel> getAuditsDfrorProduct(String dfrId, String product);
	List<DartSoapAudit> getRecentAuditsDfrorProduct(Long responseTime, long retryLimit);
	DartSoapAudit getDFRAuditByReqId(String dfrRequestId);
	HashMap<String, SrcCxiErrorMasterTbl> getErrorMasterMap();
	HashMap<String, HashSet<String>> getErrorCodeSblRowIdMap();
	List<ApprovalHistory> getLatestAppHistoryList(String dfrId);
        DFRSyncResponse syncNOCCDFRSync(DFRKafkaMessageVO dfr);
	Long getNextNoccIntegrationReqSeq();
	HashMap<String, Object> initiateDfr3(DfrDaInput dfrDaInput) throws InterruptedException, ExecutionException;
	HashMap<String, Object> copyNonSelectedAssets(HashMap<String, Object> dfrDataMap);
}
