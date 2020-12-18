package com.equinix.appops.dart.portal.buisness.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.buisness.AppOpsEditDfrBusiness;
import com.equinix.appops.dart.portal.common.CaplogixSyncResponse;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.entity.UserInfo;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrLineIdsVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.model.DFRFileModel;
import com.equinix.appops.dart.portal.model.FileUp;
import com.equinix.appops.dart.portal.model.ResetAndDelete;
import com.equinix.appops.dart.portal.model.dfr.DfrNotesInput;
import com.equinix.appops.dart.portal.model.dfr.InitiateWorkflowInput;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.Attribute;
import com.equinix.appops.dart.portal.model.grid.Product;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.grid.Values;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;
import com.equinix.appops.dart.portal.service.AppOpsDartDaService;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.EmailSenderService;
import com.equinix.appops.dart.portal.service.SVSyncVService;
import com.equinix.appops.dart.portal.service.UserService;
import com.google.gson.Gson;

@Service
public class AppOpsEditDfrBusinessImpl implements AppOpsEditDfrBusiness {
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartDaService.class);
	
	private static final String Y = "Y";
	private static final String N = "N";

	@Autowired
	AppOpsDartEditDfrService editDfrService;
	
	@Autowired
	SVSyncVService svService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	AppOpsInitiateDFRService initiateDFRService;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	UserService userService;
	
	@Override
	public SearchFilters getFilterList(ProductFilter productFilter) {
		return editDfrService.getFilterList(productFilter);
	}

	@Override
	public ProductSearchResponse getProductSearchResponse(ProductFilter productFilter) {
		return editDfrService.getProductSearchResponse(productFilter);
	}

	@Override
	public ProductWidgets getProductWidgets(ProductFilter productFilter) {
		return editDfrService.getProductWidgets(productFilter);
	}

	@Override
	public ErrorSectionResponse getErrorSctionResponse(ProductFilter productFilter) {
		return editDfrService.getErrorSctionResponse(productFilter);
	}

	@Override
	public SearchFilters globalSearch(String keyword, String dfrId,String key) {
		return editDfrService.globalSearch(keyword, dfrId,key);
	}

	@Override
	public DfrMaster getDfrMasterById(String dfrId) {
		return editDfrService.getDfrMaster(dfrId, "join");
	}

	@Override
	public ProductDataGrid getCommonAttributeView(ProductFilter productFilter) {
		return editDfrService.getCommonAttributeView(productFilter);
	}

	@Override
	public ProductDataGrid getProductAttributeView(ProductFilter productFilter) {
		return editDfrService.getProductAttributeView(productFilter);
	}
	
	@Override
	public List<String> getProductAttributeFilter(ProductFilter productFilter) {
		return editDfrService.getProductAttributeFilter(productFilter);
	}
	
	@Override
	public ProductDataGrid getAccountMoveAttributeView(String dfrId) {
		return editDfrService.getAccountMoveAttributeView(dfrId);
	}
	
	@Override
	public ProductDataGrid getProductPysicalAuditAttributeView(ProductFilter productFilter) {
		return editDfrService.getProductPhysicalAuditAttributeView(productFilter);
	}

	@Override
	public HierarchyView getHierarchyView(ProductFilter productFilter) {
		return editDfrService.getHierarchyView(productFilter);
	}

	@Override
	public String validateDependentAttributes(String product, String attrName, String dfrId, String cellType)
			throws Exception {
		return editDfrService.validateDependentAttributes(product, attrName, dfrId, cellType);
	}

	@Override
	public String dqmValidation(String dfrId) throws Exception {
		return editDfrService.dqmValidation(dfrId);
	}

	@Override
	public String initiateWorkflow(InitiateWorkflowInput workflowInput) {
		String[] sotArray = workflowInput.getSotArray().stream().toArray(String[]::new);
		String status =initiateWorkflow(sotArray,workflowInput.getDfrid(),workflowInput.getClxUpdate()
				,workflowInput.getCaplogixFlag()
				,workflowInput.getNotes()
				,workflowInput.getOverrideFlag()
				,workflowInput.getPriority()
				,workflowInput.getRegion()
				,workflowInput.getSiebelFlag()
				,workflowInput.getSingleviewFlag());
	//	status=getWorkflowDetails(workflowInput.getDfrid());
		Gson gson = new Gson();
		return gson.toJson(status, String.class);
	}
	
	private String initiateWorkflow(String[] sotArray, String dfrId,String clxUpdate, String caplogixFlag, String notes, String overrideFlag, String priority, String region, String siebelFlag, String singleviewFlag) {
		String status="",fetchMode="join";  
		try {
			DfrMaster dfrMaster=editDfrService.getDfrMaster(dfrId,fetchMode);
			String currentStatus=dfrMaster.getStatus();
			boolean resetPhyAudit=false,sotFlag=false,intFlag=false,errorFlag=false;
			List<String> sotList = Arrays.asList(sotArray);
			String sot="NA";
			if(null!=overrideFlag&&"y".equalsIgnoreCase(overrideFlag)){
				if(!LogicConstants.SYNCINPROGRESS.equalsIgnoreCase(dfrMaster.getStatus())){
					//start integration (override)
					intFlag=true;
					dfrMaster.setStatus(LogicConstants.SYNCINPROGRESS);
					dfrMaster.setOverrideFlg(overrideFlag);
					dfrMaster.setClxOverride(caplogixFlag);
					dfrMaster.setSblOverride(siebelFlag);
					dfrMaster.setSvOverride(singleviewFlag);
					saveDfrMaster(dfrMaster,sot,resetPhyAudit,intFlag,currentStatus);
					CaplogixSyncResponse clxResponse = null;
					if(null!=caplogixFlag&&"y".equalsIgnoreCase(caplogixFlag)){
						//Call CLX Integration
						try{
							clxResponse = initiateDFRService.syncCaplogixDfr(dfrId);
							dfrMaster.setClxIntStatus(clxResponse.getError()?"Sync Error":"Completed");
							errorFlag = clxResponse.getError();
						}catch(Exception ex){
							dfrMaster.setClxIntStatus("Sync Error");
							dfrMaster.setStatus("Sync Error");
							errorFlag = true;
						}
						editDfrService.saveOrUpdateDfrMaster(dfrMaster);
					}
					CaplogixSyncResponse sblResponse = null;
					if(null != siebelFlag && "y".equalsIgnoreCase(siebelFlag)){
						//Call SBL Integration
						try{							
							sblResponse = initiateDFRService.syncSiebelDfr(dfrId);
							dfrMaster.setSblIntStatus(sblResponse.getError()?"Sync Error":"Completed");	
							errorFlag = sblResponse.getError();
						}catch(Exception ex){
							dfrMaster.setSblIntStatus("Sync Error");
							dfrMaster.setStatus("Sync Error");
							errorFlag = true;
						}
						editDfrService.saveOrUpdateDfrMaster(dfrMaster);
					}
					/*if(null!=singleviewFlag&&"y".equalsIgnoreCase(singleviewFlag) && (sblResponse !=null && sblResponse.getError()==false)){
						//Call SV Integration
						try{
							List<Recon2Sync> recds = svService.getSvSyncV(dfrId);
							logger.info("No of records sync:"+(recds!=null?recds.size():"Null"));
							dfrMaster.setSvIntStatus("Completed");							
						}catch(Exception ex){
							dfrMaster.setSvIntStatus("Sync Error");
							dfrMaster.setStatus("Sync Error");
						}
						editDfrService.saveOrUpdateDfrMaster(dfrMaster);
					}*/
					status="Integration started";
				}else{
					status="Integration InProgress";
				}
			}else{
				if(null!=dfrMaster.getPhysicalAudit()&&"Y".equalsIgnoreCase(dfrMaster.getPhysicalAudit())){
					sotFlag=true;
					sot="OPS";
					dfrMaster.setStatus("OPS Review");
					resetPhyAudit=true;
				}
				if(!sotFlag&&sotList.contains("CLX")){
					sotFlag=true;
					if(null!=clxUpdate&&clxUpdate.equalsIgnoreCase("Y")&&checkIfPhyAuditinHistory(dfrMaster)){
						sot="CLX";
						dfrMaster.setStatus("CLX Review");
					}else if(!checkIfSOTinHistory(dfrMaster, "CLX")){
						sot="CLX";
						dfrMaster.setStatus("CLX Review");
					}else{
						sotFlag=false;
					}
				}
				if(!sotFlag&&sotList.contains("OPS")&&!checkIfSOTinHistory(dfrMaster, "OPS")){
					sotFlag=true;
					sot="OPS";
					dfrMaster.setStatus("OPS Review");
				}
				if(!sotFlag&&sotList.stream().anyMatch("global"::equalsIgnoreCase)&&!checkIfSOTinHistory(dfrMaster, "Global")){
					sotFlag=true;
					sot="Global";
					dfrMaster.setStatus("Global Review");
				}
				logger.info("AppOpsEditDfrBusinessImpl.initiateWorkFlow() ---> DFR Status ---> " + dfrMaster.getStatus());
				if (null == sot || sot.equalsIgnoreCase("NA")) {
					if (!LogicConstants.SYNCINPROGRESS.equalsIgnoreCase(dfrMaster.getStatus())) {
						intFlag = true;
						dfrMaster.setStatus(LogicConstants.SYNCINPROGRESS);
						saveDfrMaster(dfrMaster, sot, resetPhyAudit, intFlag, currentStatus);
						try {
							CaplogixSyncResponse clxResponse = null;
							try {
								clxResponse = initiateDFRService.syncCaplogixDfr(dfrId);
								dfrMaster.setClxIntStatus(LogicConstants.SYNCINPROGRESS);
							} catch (Exception ex) {
								dfrMaster.setClxIntStatus("Sync Error");
								logger.error("Caplogix sync error:", ex);
								errorFlag = true;
							}
							editDfrService.saveOrUpdateDfrMaster(dfrMaster);
							sendEMailAlertForBlankSOT(dfrMaster);
						} catch (Exception ex) {
							dfrMaster.setStatus("Sync Error");
							errorFlag = true;
						}
						status = "Integration started";
					} else {
						status = "Integration InProgress";
					}
				} else {
					dfrMaster.setValidStatus("N");
					saveDfrMaster(dfrMaster,sot,resetPhyAudit,intFlag,currentStatus);
					status="Assigned to "+sot;
					String editDfrLink=configService.getValueByKey("EDIT_DFR_LINK");
					String recepientList =editDfrService.getEmailIdByAssignGroup(dfrMaster.getAssignedTeam());
					String approvedBy = null;
					if (dfrMaster != null && CollectionUtils.isNotEmpty(dfrMaster.getApprovalHistories())) {
						recepientList = setApprovedEMailReceipients(dfrMaster.getDfrId(), recepientList);
						approvedBy = getApprovedBy(dfrMaster.getDfrId());
						logger.info("Final Receipients --->"+recepientList);
					}
					String templateKey="DART_REQ_NOTIFICATION";
					HashMap<String, String> dataMap = new HashMap<String, String>();					
					dataMap.put("USER_ID", dfrMaster.getAssignedTeam());//ASSIGNED GROUP TEAM
					dataMap.put("REQUESTED_ITEM", null==dfrMaster.getCreatedTeam()?"":dfrMaster.getCreatedTeam());
					dataMap.put("DFR", dfrMaster.getDfrId());
					dataMap.put("SUBJECT", "DART Approval Request "+dfrMaster.getDfrId());
					dataMap.put("REQUESTED_BY", null==dfrMaster.getCreatedBy()?"":dfrMaster.getCreatedBy());
					dataMap.put("INCIDENT", null==dfrMaster.getIncident()?"":dfrMaster.getIncident());
					dataMap.put("REGION", dfrMaster.getRegion());
					dataMap.put("NOTES", null==dfrMaster.getNotes()?"":dfrMaster.getNotes());
					dataMap.put("LINK", null==editDfrLink?"":"<a href=\""+editDfrLink+dfrMaster.getDfrId()+"\">click here</a>");
					dataMap.put("STATUS", dfrMaster.getStatus());
					if (approvedBy != null && approvedBy.length() > 0) {
						dataMap.put("APPROVED_BY", approvedBy.toString());
					} else {
						dataMap.put("APPROVED_BY","");
					}
					logger.info("AppOpsEditDfrBusinessImpl.initiateWorkflow() # Sending Alert for DFR ---> " + dfrMaster.getDfrId());
					emailSenderService.sendAlert(recepientList,templateKey,dataMap);
				}
			}
			if(errorFlag){
				ApprovalHistory approvalHistory = editDfrService.getApprovalHistory(dfrMaster, LogicConstants.SYNCERROR,dfrMaster.getNotes());
				editDfrService.saveOrUpdateApprovalHistory(approvalHistory);
			}

		} catch (Exception e) {
			logger.error("Error in initiateWorkflow: "+e);
			status="Error "+e;
		}
		return status;
	}
	
	private void saveDfrMaster(DfrMaster dfrMaster,String sot,boolean resetPhyAudit,boolean intFlag,String currentStatus){
		logger.info("AppOpsEditDfrBusinessImpl.saveDfrMaster() ---> DFR Status ---> " + dfrMaster.getStatus() + "; Current Status ---> " + currentStatus);
		/*if(!"new".equalsIgnoreCase(currentStatus)){*/
		currentStatus = dfrMaster.getStatus();
		ApprovalHistory approvalHistory=new ApprovalHistory();
		approvalHistory.setAppSequence(getAppSequence(dfrMaster.getApprovalHistories()));
		approvalHistory.setAssignedDt(dfrMaster.getAssignedDt());
		approvalHistory.setAssignedTeam(dfrMaster.getAssignedTeam());
		approvalHistory.setDfrId(dfrMaster.getDfrId());
		approvalHistory.setPhysicalAudit(dfrMaster.getPhysicalAudit());
		approvalHistory.setCreatedBy(dfrMaster.getCreatedBy());
		approvalHistory.setCreatedDate(dfrMaster.getCreatedDt());
		approvalHistory.setCreatedTeam(dfrMaster.getCreatedTeam());
		approvalHistory.setDfrUpdateDate(new Date());
		approvalHistory.setNotes(dfrMaster.getNotes());
		User user = UserThreadLocal.userThreadLocalVar.get();
		if(user!=null){
			approvalHistory.setApprovedBy(user.getUserId());
		}
		
		if("new".equalsIgnoreCase(currentStatus)){
			approvalHistory.setStatus("New");
		} else if(null!=dfrMaster.getPhysicalAudit()&&dfrMaster.getPhysicalAudit().equalsIgnoreCase("Y")){
			approvalHistory.setStatus("Physical Audit Completed");
		} else {
			approvalHistory.setStatus("Approved");
		}
		approvalHistory.setRowId(null);
		dfrMaster.setAssignedDt(new Date());
		if(!intFlag){
			dfrMaster.setAssignedTeam(getAssignedTeam(dfrMaster.getRegion(), sot));
		}		
		ApprovalHistory approvalHistoryLatest = editDfrService.getLatestAppHistory(dfrMaster.getDfrId());
		if(approvalHistoryLatest!=null && !approvalHistory.getStatus().equalsIgnoreCase(approvalHistoryLatest.getStatus())){
			editDfrService.saveOrUpdateApprovalHistory(approvalHistory);
		}
		if(resetPhyAudit){
			dfrMaster.setPhysicalAudit("N");
		}
		editDfrService.saveOrUpdateDfrMaster(dfrMaster);
		/*}*/
	}
	
	private String getAssignedTeam(String region,String sot){
		return editDfrService.getAssignedGroupByReqionAndSystem(region,sot);
	}
	
	private boolean checkIfSOTinHistory(DfrMaster dfrMaster,String sot){
		boolean checkflag = false;

		if("new".equalsIgnoreCase(dfrMaster.getStatus())&&null!=dfrMaster.getCreatedTeam()&&dfrMaster.getCreatedTeam().contains(sot)){
			checkflag=true;
		}
		else if(null!=dfrMaster.getAssignedTeam()&&dfrMaster.getAssignedTeam().contains(sot)){
			checkflag=true;
		}else{
			for(ApprovalHistory approvalHistory:dfrMaster.getApprovalHistories()){
				if(null!=approvalHistory.getAssignedTeam()&&approvalHistory.getAssignedTeam().contains(sot)){
					checkflag=true;break;
				}
			}
		}
		return checkflag;
	}
	
	private boolean checkIfPhyAuditinHistory(DfrMaster dfrMaster){
		boolean checkflag = false;
		ApprovalHistory approvalHistory=editDfrService.getLatestAppHistory(dfrMaster.getDfrId());
		if(null!=approvalHistory&&null!=approvalHistory.getPhysicalAudit()&&approvalHistory.getPhysicalAudit().equalsIgnoreCase("Y")){
			checkflag=true;
		}
		return checkflag;
	}
	
	private int getAppSequence(List<ApprovalHistory> approvalHistories){
		int appSeq=0;
		for(ApprovalHistory approvalHistory:approvalHistories){
			if(approvalHistory.getAppSequence()>appSeq){
				appSeq=approvalHistory.getAppSequence();
			}
		}
		return appSeq+1;
	}

	@Override
	public void saveNewAssetValues(ProductDataGrid dataGrid) throws Exception {
		editDfrService.saveNewAssetValues(dataGrid);
	}
	
	@Override
	public void autoSaveNewAssetValues(SaveAssetForm formData) throws Exception {
		editDfrService.autoSaveNewAssetValues(formData);
	}
	
	@Override
	public Boolean checkFileExists(String dfrID) throws Exception {
		logger.info("Checking File Exists for  "+dfrID);
		if(editDfrService.isDFRFileExists(dfrID)==true)
		{
			return true;
		}
		return false;
	}
	
	

	@Override
	public String uploadDFRFile(FileUp fileUp,MultipartFile file) throws Exception {
		logger.info("Uploading DFR file "+file.getOriginalFilename());
		byte[] dfFile=file.getBytes();
		logger.info("Saving DFR File to DB "+dfFile);
		DFRFileModel dfrFile=new DFRFileModel(fileUp,dfFile);
		return editDfrService.uploadDfrFile(dfrFile);
	}
	
	@Override
	public DFRFile downloadFile(String dfrId) throws Exception {
		return editDfrService.downloadDfrFile(dfrId);
	}
	
	@Override
	public String deleteDFRFile(String dfrId) throws Exception {
		return editDfrService.deleteDfrFile(dfrId);
	}

	@Override
	public String validate(ProductDataGrid dataGrid) throws Exception {
		return editDfrService.validate(dataGrid);
	}

	@Override
	public String editHierarchy(String dfrLineID, String parentDfrLineID, String product) {
		return editDfrService.editHierarchy(dfrLineID, parentDfrLineID, product);
	}

	@Override
	public String validateHierarchy(String dfrLineID, String product) {
		return editDfrService.validateHierarchy(dfrLineID, product);
	}

	@Override
	public void saveDependentAttributes(ProductDataGrid dataGrid) throws Exception {
		editDfrService.saveDependentAttributes(dataGrid);
		
	}

	@Override
	public void autoSaveDependentAttributes(SaveAssetForm data) throws Exception {
		editDfrService.autoSaveDependentAttributes(data);
	}
	
	@Override
	public String saveDfrDetails(String dfrId, String fieldName, String fieldValue) {
		return editDfrService.saveDfrDetails(dfrId, fieldName, fieldValue);
	}

	@Override
	public String cancelDfr(String dfrId) {
		return editDfrService.cancelDfr(dfrId);
	}

	@Override
	public String getWorkflowDetails(String dfrId) {
		return editDfrService.getWorkflowDetails(dfrId);
	}

	@Override
	public String updateUserDfr(String userId, String dfrId) {
		return editDfrService.updateUserDfr(userId, dfrId);
	}

	@Override
	public ProductDataGrid getRefreshedCommonAttributeGrid(ProductFilter filters) {
		return editDfrService.getRefreshedCommonAttributeGrid(filters);
	}

	@Override
	public ProductDataGrid getRefreshedProductAttributeGrid(ProductFilter filters) {
		
		return editDfrService.getRefreshedProductAttributeGrid(filters);
	}

	@Override
	public ProductDataGrid getSelectedRefreshedProductAttributeView(ProductFilter searchFilters) {
		// TODO Auto-generated method stub
		return editDfrService.getSelectedRefreshedProductAttributeView(searchFilters);
	}
	
	@Override
	public List<String> getRefreshedProductAttributeFilter(ProductFilter searchFilters) {
		// TODO Auto-generated method stub
		return editDfrService.getRefreshedProductAttributeFilter(searchFilters);
	}
	
	@Override
	public ProductWidgets getRefreshedProductWidgets(ProductFilter productFilter) {
		return editDfrService.getRefreshedProductWidgets(productFilter);
	}
	
	@Override
	public String initiatePhysicalAudit(String dfrId,String ibx) {
		return editDfrService.initiatePhysicalAudit(dfrId,ibx);
	}
	
	@Override
	public Map<String,Object>  getPhysicalAuditDownloadData(String dfrId) {
		return editDfrService.getPhysicalAuditDownloadData(dfrId);
	}
	
	@Override
	public void saveOrUpdatePhysicalAuditData(List<SnapshotSiebelAssetDa> assetList,List<AssetNewVal> assetNewValListInsert,
			List<AssetNewVal> assetNewValListUpdate,List<SaveAssetForm> saveAssetForms) {
		editDfrService.saveOrUpdatePhysicalAuditData(assetList, assetNewValListInsert,assetNewValListUpdate,saveAssetForms);
	}
	
	@Override
	public HashMap<String,SrcCxiErrorMasterTbl> getErrorMasterList() {
		return editDfrService.getErrorMasterList();
	}

	@Override
	public String getValidStatus(String dfrId) {
		return editDfrService.getValidStatus(dfrId);
	}
	
	@Override
	public String saveOverrideFlagDFRMaster (String overrideFlag, String sblOverrideFlag, String clxOverrideFlag, String dfrId) {
		return editDfrService.saveOverrideFlagDFRMaster(overrideFlag, sblOverrideFlag, clxOverrideFlag, dfrId);
	}
	
	//Change Summary changes
	@Override
	public List<ChangeSummary> getChangeSummary(String dfrId) {
		return editDfrService.getChangeSummary(dfrId);
	}
	
	//Change Summary changes
	@Override
	public void updateChangeSummaryValues(ProductDataGrid data) throws Exception {
		editDfrService.updateChangeSummaryValues(data);
	}
	
	@Override
	public void autoUpdateChangeSummaryValues(SaveAssetForm data)throws Exception {
		editDfrService.autoUpdateChangeSummaryValues(data);
	}
	
	@Override
	public void updatePhyChangeSummaryValues(ProductDataGrid dataGrid) {
		editDfrService.updatePhyChangeSummaryValues(dataGrid);

	}
	
	private String setApprovedEMailReceipients(String dfrId, String receipientOld) {
		logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients() ---> Setting Approved E-Mail Receipients for DFR # "+dfrId+"with "
				+ "Old Receipients # " + receipientOld);
		DfrMaster dfrMasterObj = editDfrService.getDfrMaster(dfrId, "join");
		if (dfrMasterObj != null && CollectionUtils.isNotEmpty(dfrMasterObj.getApprovalHistories())) {
			logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients() # History Count ---> " + dfrMasterObj.getApprovalHistories().size());
			List<ApprovalHistory> apprHistoryList = dfrMasterObj.getApprovalHistories().stream()
					.filter(historyObj -> "Approved".equalsIgnoreCase(historyObj.getStatus()))
					.collect(Collectors.toList());
			logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients() # Approved History Count ---> " + 
					(apprHistoryList != null ? apprHistoryList.size() : 0));
			if (apprHistoryList != null && CollectionUtils.isNotEmpty(apprHistoryList)) {
				List<String> oldReceipientList = null;
				if (StringUtils.isNotEmpty(receipientOld)) {
					logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients()---> Old E-Mail Receipient # "+ receipientOld);
					String[] oldMailArray = receipientOld.split(",");
					if (oldMailArray != null && oldMailArray.length > 0) {
						oldReceipientList = new ArrayList<String>();
						for (String recipientAddr : oldMailArray) {
							oldReceipientList.add(recipientAddr);
						}
					}
				}
				String approvalEMailStr = null;
				for (ApprovalHistory histObj : apprHistoryList) {
					approvalEMailStr = editDfrService.getEmailIdByAssignGroup(histObj.getAssignedTeam());
					logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients()---> Assigned Team # "+ 
							histObj.getAssignedTeam()+"; New E-Mail String # "+ approvalEMailStr);
					if (StringUtils.isNotEmpty(approvalEMailStr)) {
						String[] approvalMailArray = approvalEMailStr.split(",");
						if (approvalMailArray != null && approvalMailArray.length > 0) {
							if (oldReceipientList == null) {
								oldReceipientList = new ArrayList<String>();
							}
							for (String recipientAddr : approvalMailArray) {
								if (!oldReceipientList.contains(recipientAddr)) {
									oldReceipientList.add(recipientAddr);
								}
							}
						}
					}
				}
				if (CollectionUtils.isNotEmpty(oldReceipientList)) {
					receipientOld = String.join(",", oldReceipientList);
				}
				logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients() # Receipients ---> " + (receipientOld != null ? receipientOld : ""));
			}
		}
		return receipientOld;
	}
	
	private String getApprovedBy(String dfrId) {
		StringBuilder approvedBy = null;
		DfrMaster dfrMaster = editDfrService.getDfrMaster(dfrId, "join");
		if (dfrMaster != null && CollectionUtils.isNotEmpty(dfrMaster.getApprovalHistories())) {
			logger.info("AppOpsEditDfrBusinessImpl.getApprovedBy() # History Size ---> "+ dfrMaster.getApprovalHistories().size());
			List<ApprovalHistory> apprHistoryList = dfrMaster.getApprovalHistories().stream()
					.filter(historyObj -> "Approved".equalsIgnoreCase(historyObj.getStatus())).collect(Collectors.toList());
			//logger.info("AppOpsEditDfrBusinessImpl.getApprovedBy() # Approved History Size ---> " + (apprHistoryList != null ? apprHistoryList.size() : 0));
			if (CollectionUtils.isNotEmpty(apprHistoryList)) {
				Collections.sort(apprHistoryList, new Comparator<ApprovalHistory>() {
					public int compare (ApprovalHistory histObj1, ApprovalHistory histObj2) {
						if (histObj1.getDfrUpdateDate() == null || histObj2.getDfrUpdateDate() == null) {
							return 0;
						}
						return histObj1.getDfrUpdateDate().compareTo(histObj2.getDfrUpdateDate());
					}
				});
				Collections.reverse(apprHistoryList);
				for (ApprovalHistory histObj : apprHistoryList) {
					if (StringUtils.isNotEmpty(histObj.getApprovedBy())
							&& StringUtils.isNotEmpty(histObj.getAssignedTeam())) {
						UserInfo userObj = userService.getUser(histObj.getApprovedBy());
						if (userObj != null) {
							if (approvedBy == null) {
								approvedBy = new StringBuilder();
							}
							if (approvedBy != null && approvedBy.length() > 0) {
								approvedBy.append("<br>");
							}
							approvedBy.append(histObj.getAssignedTeam() + " - " + userObj.getEmailId()).append("\n");
						}
					}
				}
			}
		}
		logger.info("AppOpsEditDfrBusinessImpl.getApprovedBy() # Approved By ---> " + (approvedBy != null ? approvedBy.toString() : ""));
		return (approvedBy != null ? approvedBy.toString() : null);
	}
	
	private void sendEMailAlertForBlankSOT (DfrMaster dfrMasterObj) {
		String editDfrLink=configService.getValueByKey("EDIT_DFR_LINK");
		String recepientList =editDfrService.getEmailIdByAssignGroup(dfrMasterObj.getAssignedTeam());
		String approvedBy = null;
		if (dfrMasterObj != null && CollectionUtils.isNotEmpty(dfrMasterObj.getApprovalHistories())) {
			recepientList = setApprovedEMailReceipients(dfrMasterObj.getDfrId(), recepientList);
			approvedBy = getApprovedBy(dfrMasterObj.getDfrId());
		}
		String templateKey="DART_REQ_NOTIFICATION";
		HashMap<String, String> dataMap = new HashMap<String, String>();					
		dataMap.put("USER_ID", dfrMasterObj.getAssignedTeam());//ASSIGNED GROUP TEAM
		dataMap.put("REQUESTED_ITEM", null == dfrMasterObj.getCreatedTeam() ? "" : dfrMasterObj.getCreatedTeam());
		dataMap.put("DFR", dfrMasterObj.getDfrId());
		dataMap.put("SUBJECT", "DART Approval Request "+dfrMasterObj.getDfrId());
		dataMap.put("REQUESTED_BY", null == dfrMasterObj.getCreatedBy() ? "" : dfrMasterObj.getCreatedBy());
		dataMap.put("INCIDENT", null == dfrMasterObj.getIncident() ? "" : dfrMasterObj.getIncident());
		dataMap.put("REGION", dfrMasterObj.getRegion());
		dataMap.put("NOTES", null == dfrMasterObj.getNotes() ? "" : dfrMasterObj.getNotes());
		dataMap.put("LINK", null==editDfrLink?"":"<a href=\""+editDfrLink+dfrMasterObj.getDfrId()+"\">click here</a>");
		dataMap.put("STATUS", dfrMasterObj.getStatus());
		if (approvedBy != null && approvedBy.length() > 0) {
			dataMap.put("APPROVED_BY", approvedBy.toString());
		} else {
			dataMap.put("APPROVED_BY","");
		}
		logger.info("AppOpsEditDfrBusinessImpl.sendEMailAlertForBlankSOT() # Sending Alert for DFR ---> " + dfrMasterObj.getDfrId());
		emailSenderService.sendAlert(recepientList,templateKey,dataMap);
	}
	
	@Override
	public String resetAndDelete(ResetAndDelete resetAndDeleteObj) throws Exception {
		return editDfrService.resetAndDelete(resetAndDeleteObj);
	}
	
	@Override
	public String resetAndDelete(String dfrLineId) throws Exception{
		return editDfrService.resetAndDelete(dfrLineId);
	}
	
	@Override
	public String resetAttributeView(String dfrId, String product) throws Exception{
	   return editDfrService.resetAttributeView(dfrId, product);
	}
	
	@Override
	public	boolean validateSaveAssetNewValuesByDfrId(ProductFilter filter) throws Exception{
		return editDfrService.validateSaveAssetNewValuesByDfrId(filter);
	}
	
	@Override
	public ProductDataGrid getSelectedProductAttributeView(ProductFilter productFilter) {
		return editDfrService.getSelectedProductAttributeView(productFilter);
	}

	@Override
	public List<String> createInitiateWorkflowInput(ProductDataGrid productDataGrid) {
		Set<String> sots = new HashSet<>();
		List<Product> products = productDataGrid.getProducts();
		for(Product product : products){
			List<Attribute> attributes = product.getAttributes();
			for(Attribute attribute : attributes){
				String sot = attribute.getSot();
				Values values = attribute.getValues();
				if(values.getNewval()!=null && values.getNewval()!= "" && StringUtils.isNotEmpty(values.getNewval())){
					if(sot.equalsIgnoreCase("ops")){
						String sbl = values.getSBL().split("##")[0];
						String newVal = values.getNewval();
						if(StringUtils.isNotEmpty(sbl) && StringUtils.isNotEmpty(newVal)){
							if(!sbl.trim().equals(newVal.trim())){
							sots.add("OPS");
						}
						}
					}else if(sot.equalsIgnoreCase("clx")){
						String clx = values.getCLX().split("##")[0];
						String newVal = values.getNewval();
						if(StringUtils.isNotEmpty(clx) && StringUtils.isNotEmpty(clx)){
							if(!clx.trim().equals(newVal.trim())){
							sots.add("CLX");
						}
						}
						
					}else if(sot.equalsIgnoreCase("global")){
						if(values.getNewval()!= ""){
							sots.add("GLOBAL");
						}
					}
				}
			}
	}
		 return sots.stream().collect(Collectors.toList());

	}
	
	@Override
	public ProductDataGrid getSelectedProductPysicalAuditAttributeView(ProductFilter searchFilters) {
	
		return editDfrService.getSelectedProductPysicalAuditAttributeView(searchFilters);
	}
	
	@Override
	public List<String> getProductPysicalAuditAttributeViewFilter(ProductFilter searchFilters) {
		
		return editDfrService.getProductPysicalAuditAttributeViewFilter(searchFilters);
		}
	
	@Override
	public String deleteAssetsByDfrLineIds(String dfrID,List<String> dfrLineIds){
		return editDfrService.deleteAssetsByDfrLineIds(dfrID, dfrLineIds);
	}
	
	@Override
	public String getNewPOEAssetNumber(){
		return editDfrService.getNewPOEAssetNumber();
	}
	
	@Override
	public List<String> getFilterListFromSnapshotDA(String header,String dfrId){
		return editDfrService.getFilterListFromSnapshotDA(header, dfrId);
	}
	
	@Override
	public String resetAssetsByDfrLineIds(Set<String> dfrLineIds) throws Exception{
		return editDfrService.resetAssetsByDfrLineIds(dfrLineIds);
	}
	
	@Override
	public  List<ErrorCodeVO> validationsOnSubmit(String dfrId) throws Exception{
		return editDfrService.validationsOnSubmit(dfrId);
	}

	@Override
	public ProductDataGrid sortChangedAssetNewVal(ProductDataGrid dataGrid,Map<String,Set<String>> dfrLineIds) {
		List<Product> rows = dataGrid.getProducts();
		ProductDataGrid grid = new ProductDataGrid(); 
		List<Product> updatedProductList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(rows)) {
			for (Product product : rows) {
				List<Attribute> updatedAttributeList = new ArrayList<>();
				List<Attribute> attributeList = product.getAttributes();
				for (Attribute attr : attributeList) {
					Values value = attr.getValues();
					String newValue = value.getNewval();
					if (StringUtils.isNotEmpty(newValue)) {
						String sbl = value.getSBL()+"##"+product.getDfrlineid();
						String clx = value.getCLX()+"##"+product.getDfrlineid();
						String sv = value.getSV()+"##"+product.getDfrlineid();
						value.setSBL(sbl);
						value.setCLX(clx);
						value.setSV(sv);
						updatedAttributeList.add(attr);
					}
				}
				if(CollectionUtils.isNotEmpty(updatedAttributeList)){
					Product product2 = new Product();
					product2.setAssetNumber(product.getAssetNumber()!=null?product.getAssetNumber():null);
					product2.setDfrlineid(product.getDfrlineid()!=null?product.getDfrlineid():null);
					product2.setName(product.getName()!=null?product.getName():null);
					product2.setAttributes(updatedAttributeList);
					updatedProductList.add(product2);
					Set<String> dfrLines = dfrLineIds.get(product2.getName());
					if(CollectionUtils.isNotEmpty(dfrLines)){
						dfrLines.add(product2.getDfrlineid());
						dfrLineIds.put(product2.getName(), dfrLines);
					}else{
						Set<String> idsSet = new HashSet<>();
						idsSet.add(product2.getDfrlineid());
						dfrLineIds.put(product2.getName(), idsSet);
					}
				}
			}
		}
		grid.setProducts(updatedProductList);
		grid.setDfrid(dataGrid.getDfrid());
		return grid;
	}
	
	@Override
	public ProductDataGrid autoValidate(String dfrId) throws Exception {
		ProductFilter productFilter = new ProductFilter();
		productFilter.setDfrId(dfrId);
		return editDfrService.autoValidate(productFilter);
	}
	
	@Override
	public ProductDataGrid productAttributeViewByLineId(SaveAssetForm data) {
		return editDfrService.productAttributeViewByLineId(data);
	}
	
	@Override
	public List<ErrorCardVO> getTotalAndLeftAssetById(String dfrId) {

		List<ErrorCardVO> totalErrorCount = editDfrService.getTotalErrorAssetById(dfrId);
		return totalErrorCount;
	}
	
	@Override
	public HashMap<String, Object> getChangesMadeById(String dfrId) {
		HashMap<String, Object> countmap = new HashMap<>();
		int totalCount = 0;
		List<String> totalChangesCount = editDfrService.getChangesMadeById(dfrId);
		if (CollectionUtils.isNotEmpty(totalChangesCount)) {
			String changes = totalChangesCount.get(0);
			if (changes != null && StringUtils.isNotBlank(changes)) {
				totalCount = Integer.parseInt(changes);
				countmap.put("total", totalCount);
			} else {
				countmap.put("total", 0);
			}
			
		} else {
			countmap.put("total", 0);
		}
		return countmap;

	}
	
	@Override
	public List<POECountVO> getPOECountByFilters(ProductFilter productFilter) {
		
			return editDfrService.getPOECountByFilters(productFilter);
	}
	
	@Override
	public ProductDataGrid getInitiateProductAttributeView(ProductFilter productFilter) {
		return editDfrService.getInitiateProductAttributeView(productFilter);
	}
	
	@Override
	public HashMap<String, Object> getNewErrorCountById(String dfrId) {
		HashMap<String, Object> countmap = new HashMap<>();
		int totalCount = 0;
		List<String> totalChangesCount = editDfrService.getNewErrorCountById(dfrId);
		if (CollectionUtils.isNotEmpty(totalChangesCount)) {
			String changes = totalChangesCount.get(0);
			if (changes != null && StringUtils.isNotBlank(changes)) {
				totalCount = Integer.parseInt(changes);
				countmap.put("total", totalCount);
			} else {
				countmap.put("total", 0);
			}
			
		} else {
			countmap.put("total", 0);
		}
		return countmap;
	}
	
	@Override
	public ProductDataGrid getAllProductAttributeView(ProductFilter searchFilters) {
		return editDfrService.getAllProductAttributeView(searchFilters);
	}
	
	@Override
	public void saveDfrNotes(DfrNotesInput dfrNotesInput) {
		DfrNotes dfrNotes = new DfrNotes(dfrNotesInput);
		dfrNotes.setCreatedDt(new Date());
		User user = UserThreadLocal.userThreadLocalVar.get();
		dfrNotes.setUserName(user.getUserId());
		dfrNotes.setFirstName(user.getFirstName());
		dfrNotes.setLastName(user.getLastName());
		editDfrService.saveDfrNotes(dfrNotes);
	}
	
	@Override
	public List<DfrNotes> getDfrNotes(String dfrId) {
		
		return editDfrService.getDfrNotes(dfrId);
	}
	
	@Override
	public SaveAssetForm createChangeSummaryInput(ProductDataGrid dataGrid) {
		SaveAssetForm saveAssetForm = new SaveAssetForm();
		List<Product> products = dataGrid.getProducts();
		Product sortedProduct = new Product();
		if (CollectionUtils.isNotEmpty(products)) {
			for (Product product : products) {
				List<Attribute> sortedAttributeList = new ArrayList<>();
				List<Attribute> attributes = product.getAttributes();
				for (Attribute attribute : attributes) {

					if (StringUtils.isNotEmpty(attribute.getValues().getNewval())
							&& !attribute.getName().equalsIgnoreCase("Product")) {
						sortedAttributeList.add(attribute);
					}
				}
				if (CollectionUtils.isNotEmpty(sortedAttributeList)) {
					sortedProduct.setAttributes(sortedAttributeList);
					sortedProduct.setDfrlineid(product.getDfrlineid());
				}
			}
			saveAssetForm.setProducts(sortedProduct);
		}
		return saveAssetForm;
	}
	
	@Override
	public Map<String,List<String>> dfrLineIdListByDfr(String dfrId) {
		List<DfrLineIdsVo> dfrLineIdsVos = editDfrService.dfrLineIdListByDfr(dfrId);
		Map<String, List<String>> dfrLineIdsGroupMap =
				dfrLineIdsVos.stream().collect(Collectors.groupingBy(DfrLineIdsVo::getName,
                        Collectors.mapping(DfrLineIdsVo::getDfrLineId, Collectors.toList())));
		DfrMaster  dfrMaster = editDfrService.getDfrMasterById(dfrId);
		String ssFilter = dfrMaster.getSsFilter();
		ProductFilter searchFilters = null;
		if(ssFilter != null && StringUtils.isNotEmpty(ssFilter)){
			Gson gson = new Gson();
			searchFilters = gson.fromJson(dfrMaster.getSsFilter(), ProductFilter.class);
		}
		if (searchFilters != null && searchFilters.getSearchDropBox() != null
				&& searchFilters.getSearchDropBox().getSearchDrop() != null && searchFilters.getKeyword() != null
				&& CollectionUtils.isNotEmpty(searchFilters.getSearchDropBox().getSearchDrop())
				&& searchFilters.getSearchDropBox().getSearchDrop().get(0) != null
				&& "header2".equalsIgnoreCase(searchFilters.getSearchDropBox().getSearchDrop().get(0).getKey())
				&& dfrLineIdsGroupMap != null 
				&& CollectionUtils.isNotEmpty(searchFilters.getFilters())) {
			List<String> searchForList = new ArrayList<>();
			String keywordSearched = null;
			for (PFilter pFilter : searchFilters.getFilters()) {
				if ("header2".equalsIgnoreCase(pFilter.getKey())) {
					keywordSearched = pFilter.getValue();
				}
			}
			if (StringUtils.isNotEmpty(keywordSearched)) {
				searchForList = Arrays.asList(keywordSearched.split(",")).stream()
						.filter(i -> i != null && !i.equalsIgnoreCase("")).map(obj -> obj.toString().trim())
						.collect(Collectors.toList());
				
				Map<String, Map<String, String>> dfrLineAssetGroup = new HashMap<>();
				dfrLineIdsGroupMap.forEach((k, v) -> {
					dfrLineAssetGroup.put(k, v.stream().map(e -> e.split("##")).collect(Collectors.toMap(s -> s[0], s -> s[1])));
				});
				
				long nullCount = dfrLineAssetGroup.values().stream().filter((m) -> m.containsKey("null")).count(); 
				if (nullCount > 0) {
					Map<String, List<String>> dfrLineResult = new HashMap<>();
					dfrLineIdsGroupMap.forEach((k, v) -> {
						dfrLineResult.put(k, v.stream().map(e -> e.split("##")[1]).collect(Collectors.toList()));
					});
					return dfrLineResult;
				}
				
				Map<String, List<String>> dfrLineIdsSortedMap = new HashMap<>();
				
				for (Map.Entry<String, Map<String, String>> entry : dfrLineAssetGroup.entrySet()) {
				    String key = entry.getKey();
				    Map<String, String> value = entry.getValue();
				    List<String> sortedList = new ArrayList<>();
				    for (String assetNum : searchForList) {
				    	if(value.containsKey(assetNum)){
				    		sortedList.add(value.get(assetNum));
				    	}
				    }
				    dfrLineIdsSortedMap.put(key, sortedList);
				}

				return dfrLineIdsSortedMap;
			}
		}
		
		Map<String, List<String>> dfrLineAssetGroup = new HashMap<>();
		dfrLineIdsGroupMap.forEach((k, v) -> {
			dfrLineAssetGroup.put(k, v.stream().map(e -> e.split("##")[1]).collect(Collectors.toList()));
		});
		
		return dfrLineAssetGroup;
	}
	
	@Override
	public void populateChangeSummaryValues(Map<String, Set<String>> dfrLineIdsMap,String dfrId) throws Exception {
		Set<Entry<String, Set<String>>> dfrLineIds = dfrLineIdsMap.entrySet();
		Iterator<Entry<String, Set<String>>> iterator = dfrLineIds.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Set<String>> idsMap = iterator.next();
			String productName = idsMap.getKey();
			String lineIds = String.join(",",idsMap.getValue());
			SaveAssetForm saveAssetForm = new SaveAssetForm();
			saveAssetForm.setDfrId(dfrId);
			saveAssetForm.setName(productName);
			saveAssetForm.setDfrLineId(lineIds);
			ProductDataGrid dataGrid = editDfrService.productAttributeViewByLineId(saveAssetForm);
			editDfrService.updateChangeSummaryValues(dataGrid);
		}
	}

	@Override
	public String checkSystemNameForPhysicalAudit(String dfrId) {
		
		List<String> statusList = editDfrService.checkSystemNameForPhysicalAudit(dfrId);
		return statusList.get(0);
	}
}
