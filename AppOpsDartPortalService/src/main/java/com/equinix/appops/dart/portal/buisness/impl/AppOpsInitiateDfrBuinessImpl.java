package com.equinix.appops.dart.portal.buisness.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.buisness.AppOpsInitiateDfrBusiness;
import com.equinix.appops.dart.portal.common.CaplogixSyncResponse;
import com.equinix.appops.dart.portal.common.DFRSyncResponse;
import com.equinix.appops.dart.portal.common.SVSoapResponse;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorTbl;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.dfr.DfrDaInput;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
@Service
public class AppOpsInitiateDfrBuinessImpl implements AppOpsInitiateDfrBusiness {
	
	Logger logger = LoggerFactory.getLogger(AppOpsInitiateDfrBuinessImpl.class);
	
	@Autowired
	AppOpsInitiateDFRService initiateDfrService;
	
	@Autowired
	AppOpsDartEditDfrService editDfrService; 
	
	@PostConstruct
	public void  init(){
		logger.info("============Error loading start ====================");
		
	//	new Thread(() -> {
	
		initiateDfrService.init();
			
		//}).start();
		
		logger.info("============Error loading running in thread stops ====================");	
		
			
	}
	
	@Override
	public HashMap<String, Object> initiateDfr(DfrDaInput dfrDaInput) throws InterruptedException, ExecutionException{
		return initiateDfrService.initiateDfr(dfrDaInput);
	}

	@Override
	public List<SVSoapResponse> syncSVAPI(String dfrId) {
		return initiateDfrService.syncSVAPI(dfrId);
	}

	@Override
	public void syncCages(String cageId, String dfrId) {
		initiateDfrService.syncCages(cageId, dfrId);
	}

	@Override
	public void syncCabinates(String cabinateId, String dfrId) {
		initiateDfrService.syncCabinates(cabinateId, dfrId);
	}

	@Override
	public void syncAssets(String assetId, String dfrId) {
		initiateDfrService.syncAssets(assetId, dfrId);
		
	}

	@Override
	public CaplogixSyncResponse syncCaplogixDfr(String dfrId) throws Exception {
		return initiateDfrService.syncCaplogixDfr(dfrId);
	}

	@Override
	public CaplogixSyncResponse syncSiebelDfr(String dfrId) {
		return initiateDfrService.syncSiebelDfr(dfrId);
	}

	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getSblErrorMap() {
		return initiateDfrService.getSblErrorMap();
	}

	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getClxErrorMap() {
		 return initiateDfrService.getClxErrorMap();
	}

	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getSvErrorMap() {
		return initiateDfrService.getSvErrorMap();
	}

	@Override
	public String refreshErrors() {
		logger.info("refreshErrors starts  ");
		HashMap<String, HashMap<String, List<SrcCxiErrorTbl>>> data = initiateDfrService.init();
		logger.info("refreshErrors starts  ");
		if(data!=null){
		
		return "success";
		} else {
			return "failed";
		}
		
	}

	@Override
	public void moveSblAssets(List<SiebelAssetDa> sblAssetDaList, String dfrId) {
		initiateDfrService.moveSblAssets(sblAssetDaList, dfrId);
		
	}

	@Override
	public void moveClxCabinetAssets(List<ClxAssetDa> clxAssetDaList, String dfrId) {
		initiateDfrService.moveClxCabinetAssets(clxAssetDaList, dfrId);
		
	}

	@Override
	public void moveClxCageAssets(List<ClxAssetDa> clxAssetDaList, String dfrId) {
		initiateDfrService.moveClxCageAssets(clxAssetDaList, dfrId);
		
	}

	@Override
	public void syncAssets(Set<String> assetIds, String dfrId) {
		initiateDfrService.syncAssets(assetIds, dfrId);
		
	}

	@Override
	public void syncCages(Set<String> cageIds, String dfrId) {
		initiateDfrService.syncCages(cageIds, dfrId);
		
	}

	@Override
	public void syncCabinates(Set<String> cabinateIds, String dfrId) {
		initiateDfrService.syncCabinates(cabinateIds, dfrId);
	}

	@Override
	public DFRSyncResponse syncCLXDfrKafkaConsumer(DFRKafkaMessageVO dfr) {
		DFRSyncResponse dfrResponse = new DFRSyncResponse();
		String dfrId= dfr.getDfrId();
		DfrMaster dfrMaster= editDfrService.getDfrMasterById(dfrId);
		try{
			logger.info("DFRID#"+dfrId+" call clx in progress");
			dfrMaster.setClxIntStatus(LogicConstants.SYNCINPROGRESS);
			editDfrService.saveOrUpdateDfrMaster(dfrMaster);
			dfrResponse = initiateDfrService.syncCLXDfrKafkaConsumer(dfrId);		
			logger.info("DFRID#"+dfrId+" requestId:"+dfr.getDfrRequestId());			
			DartSoapAudit clxAudit = new DartSoapAudit(dfrResponse,LogicConstants.CLX_PRODUCT);
			if(StringUtils.isNotBlank(dfr.getDfrRequestId())){				
				DartSoapAudit  prevCLXAudit = initiateDfrService.getDFRAuditByReqId(dfr.getDfrRequestId());
				prevCLXAudit.setRequest(dfrResponse.getRequest().getBytes());				
				prevCLXAudit.setResponse(dfrResponse.getResponse().getBytes());
				prevCLXAudit.setRequestTime(new Timestamp(dfrResponse.getRequestDate().getTime()));
				prevCLXAudit.setResponseTime(new Timestamp(dfrResponse.getResponseDate().getTime()));				
				prevCLXAudit.setFault(dfrResponse.getStatus());				
				initiateDfrService.saveSoapAudit(prevCLXAudit);
			}else{
				initiateDfrService.saveSoapAudit(clxAudit);
			}
			
			logger.info("DFRID#"+dfrId+" requestId:"+dfr.getDfrRequestId()+" clx response:"+dfrResponse);
		}catch(Exception ex){
			logger.error("error while consuming CLX from kafka:",ex);
			dfrResponse.setError(true);
			dfrResponse.setStatus(LogicConstants.SYNCERROR);
			dfrMaster.setStatus(LogicConstants.SYNCERROR);
			dfrMaster.setStatusChangeDt(new Date());
		}
		dfrMaster.setClxIntStatus(dfrResponse.getStatus());
		if(dfrResponse.isError()){
			dfrMaster.setStatus(LogicConstants.SYNCERROR);
			dfrMaster.setStatusChangeDt(new Date());
		}
		editDfrService.saveOrUpdateDfrMaster(dfrMaster);
		//Call SBL Int
		if(dfrResponse.getStatus().equalsIgnoreCase(LogicConstants.SYNCCOMPLETED) ||
				dfrResponse.getStatus().equalsIgnoreCase(LogicConstants.SYNCNOTREQUIRED)){			
			try{
				logger.info("DFRID#"+dfrId+"pushing for sbl.");
				dfrMaster.setSblIntStatus(LogicConstants.SYNCINPROGRESS);			
				CaplogixSyncResponse syncResponse =initiateDfrService.syncSiebelDfr(dfrId);				
				dfrResponse.setError(syncResponse.getError());
				dfrResponse.setStatus(syncResponse.getMessage());
				logger.info("DFRID#"+dfrId+" sbl pushing response:"+dfrResponse);
			}catch(Exception ex){
				logger.error("error while pushing for sbl:",ex);
				dfrResponse.setError(true);
				dfrResponse.setStatus(LogicConstants.SYNCERROR);				
				dfrMaster.setStatus(LogicConstants.SYNCERROR);
				dfrMaster.setStatusChangeDt(new Date());				
			}
			
			if(dfrResponse.isError()){
				dfrMaster.setStatus(LogicConstants.SYNCERROR);
				dfrMaster.setStatusChangeDt(new Date());
			}
			dfrMaster.setSblIntStatus(dfrResponse.getStatus());
			editDfrService.saveOrUpdateDfrMaster(dfrMaster);		}
		return dfrResponse;
	}

	@Override
	public DFRSyncResponse syncSBLDfrKafkaConsumer(DFRKafkaMessageVO dfr) {
		DFRSyncResponse dfrResponse = new DFRSyncResponse();
		String dfrId = dfr.getDfrId();
		DfrMaster dfrMaster= editDfrService.getDfrMasterById(dfrId);
		try{
			logger.info("DFRID#"+dfrId+" call sbl in progress");
			dfrMaster.setSblIntStatus(LogicConstants.SYNCINPROGRESS);
			editDfrService.saveOrUpdateDfrMaster(dfrMaster);
			dfrResponse = initiateDfrService.syncSBLDfrKafkaConsumer(dfr);			
			logger.info("DFRID#"+dfrId+" sbl response:"+ dfrResponse);
		}catch(Exception ex){
			logger.error("error while sync SBL:",ex);
			dfrResponse.setError(true);
			dfrResponse.setStatus("Sync Error");
			dfrMaster.setStatus(LogicConstants.SYNCERROR);
			dfrMaster.setStatusChangeDt(new Date());
		}
		//ToDo SBL response will always be NR or Sync In Progress		
		if(dfrResponse.isError()){
			dfrMaster.setStatus(LogicConstants.SYNCERROR);
			dfrMaster.setStatusChangeDt(new Date());
		}
		dfrMaster.setSblIntStatus(dfrResponse.getStatus());
		editDfrService.saveOrUpdateDfrMaster(dfrMaster);
		if("Y".equalsIgnoreCase(dfrMaster.getIsAccountMoveDfr())){
			dfrResponse = initiateDfrService.syncNOCCDFRSync(dfr);	
		}
		return dfrResponse;
	}

	@Override
	public DFRSyncResponse syncSVDfrKafkaConsumer(DFRKafkaMessageVO dfr) {
		DFRSyncResponse dfrResponse = new DFRSyncResponse();
		String dfrId = dfr.getDfrId();
		DfrMaster dfrMaster= editDfrService.getDfrMasterById(dfrId);
		String status = LogicConstants.SYNCINPROGRESS;
		try{
			logger.info("DFRID#"+dfrId+" call SV in progress");
			dfrMaster.setSvIntStatus(LogicConstants.SYNCINPROGRESS);
			editDfrService.saveOrUpdateDfrMaster(dfrMaster);
			dfrResponse = initiateDfrService.syncSVDfrKafkaConsumer(dfrId);
			dfrResponse.setError(false);
			logger.info("DFRID#"+dfrId+" SV response:"+ dfrResponse);
		}catch(Exception ex){
			logger.error("error while sync SV:",ex);
			dfrResponse.setError(true);
			dfrResponse.setStatus("Sync Error");
			dfrMaster.setStatus(LogicConstants.SYNCERROR);
			dfrMaster.setStatusChangeDt(new Date());
		}
		dfrMaster.setSvIntStatus(dfrResponse.getStatus());
		//set overall status to completed if SBLStatus,CLXstatus,SVstatus=Completed else Sync Error
		if((dfrMaster.getClxIntStatus().equalsIgnoreCase(LogicConstants.SYNCCOMPLETED)
				||dfrMaster.getClxIntStatus().equalsIgnoreCase(LogicConstants.SYNCNOTREQUIRED))
				 && (dfrMaster.getSblIntStatus().equalsIgnoreCase(LogicConstants.SYNCCOMPLETED)
					||dfrMaster.getSblIntStatus().equalsIgnoreCase(LogicConstants.SYNCNOTREQUIRED))
				 && dfrResponse.isError()==false){
			if(dfrResponse.getStatus().equalsIgnoreCase(LogicConstants.SYNCNOTREQUIRED)){
				dfrMaster.setStatus(LogicConstants.SYNCCOMPLETED);
				status = LogicConstants.SYNCCOMPLETED;
			} else {
				dfrMaster.setStatus(LogicConstants.SYNCINPROGRESS);
				status = LogicConstants.SYNCINPROGRESS;
			}
		} else{
			dfrMaster.setStatus(LogicConstants.SYNCERROR);
			status = LogicConstants.SYNCERROR;
		}
		editDfrService.saveOrUpdateDfrMaster(dfrMaster);
		if(!status.equalsIgnoreCase(LogicConstants.SYNCINPROGRESS)){
			ApprovalHistory approvalHistory = editDfrService.getApprovalHistory(dfrMaster, status,dfrMaster.getNotes());
			editDfrService.saveOrUpdateApprovalHistory(approvalHistory);
		}
		return dfrResponse;
	}

	@Override
	public CaplogixSyncResponse syncSVDfr(String dfrId) {
		return initiateDfrService.syncSVDfr(dfrId);
	}

	@Override
	public List<DartSoapAudit> getRecentAuditsDfrorProduct(Long responseTime, long retryLimit) {
		return initiateDfrService.getRecentAuditsDfrorProduct(responseTime, retryLimit);
	}
	@Override
	public List<ApprovalHistory> getLatestAppHistoryList(String dfrId) {
		List<ApprovalHistory> listapprovalhistroy = initiateDfrService.getLatestAppHistoryList(dfrId);
		listapprovalhistroy.removeIf(approvalHistory -> approvalHistory.getStatus().equals("Saved"));
		return listapprovalhistroy;
	}
	@Override
	public HashMap<String, Object> initiateDfrAccountMove(AccountMoveAssetRequest request) throws Exception {
		return initiateDfrService.initiateDfrAccountMove(request);
	}
	
	@Override
	public Long getNextNoccIntegrationReqSeq() {
		return initiateDfrService.getNextNoccIntegrationReqSeq();
	}
	
	@Override
	public HashMap<String,Object> initiateDfr3(DfrDaInput dfrDaInput) throws InterruptedException, ExecutionException{
		return initiateDfrService.initiateDfr3(dfrDaInput);
	}
	
	@Override
	public HashMap<String,Object> copyNonSelectedAssets(HashMap<String,Object> dfrDataMap){
		return initiateDfrService.copyNonSelectedAssets(dfrDataMap);
	}
}
