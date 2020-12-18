package com.equinix.appops.dart.portal.service.impl;

import java.beans.Expression;
import java.beans.Statement;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.buisness.AppOpsAccountMovementBusiness;
import com.equinix.appops.dart.portal.common.CaplogixSyncResponse;
import com.equinix.appops.dart.portal.common.DFRSyncResponse;
import com.equinix.appops.dart.portal.common.SVSoapResponse;
import com.equinix.appops.dart.portal.common.ServiceUtil;
import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.dao.AppOppsDartAttrConfigDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartDaDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartEditDfrDao;
import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.dao.SrcAssetXADao;
import com.equinix.appops.dart.portal.dao.SrcServiceDaArrayESKDao;
import com.equinix.appops.dart.portal.dao.SrcServiceDaArrayRampDao;
import com.equinix.appops.dart.portal.dao.SvAPIDao;
import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.CxiErrorTbl;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.EqxClCabinet;
import com.equinix.appops.dart.portal.entity.EqxClCage;
import com.equinix.appops.dart.portal.entity.PortAsset;
import com.equinix.appops.dart.portal.entity.ProductConfig;
import com.equinix.appops.dart.portal.entity.Recon2Sync;
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
import com.equinix.appops.dart.portal.entity.SrcServiceDaArrayEsk;
import com.equinix.appops.dart.portal.entity.SrcServiceDaArrayRamp;
import com.equinix.appops.dart.portal.entity.SvApi;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.entity.SvSyncV;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.DartSoapAuditModel;
import com.equinix.appops.dart.portal.model.dfr.Da;
import com.equinix.appops.dart.portal.model.dfr.DfrDaInput;
import com.equinix.appops.dart.portal.model.dfr.Product;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.model.search.product.SearchDrop;
import com.equinix.appops.dart.portal.service.AppOppsDartHomeService;
import com.equinix.appops.dart.portal.service.AppOpsDartAttrConfigService;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.KafkaSenderService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intecbilling.tresoap._2_0.afs_inbound.ESKPricingTier;
import com.intecbilling.tresoap._2_0.afs_inbound.ListOfESKPricingTier;
import com.intecbilling.tresoap._2_0.afs_inbound.ListOfEqxOrderEntryIo;
import com.intecbilling.tresoap._2_0.afs_inbound.ListOfOrderEntryLineItems;
import com.intecbilling.tresoap._2_0.afs_inbound.ListOfOrderItemRamp;
import com.intecbilling.tresoap._2_0.afs_inbound.ListOfOrderItemXa;
import com.intecbilling.tresoap._2_0.afs_inbound.OrderEntryLineItems;
import com.intecbilling.tresoap._2_0.afs_inbound.OrderEntryOrders;
import com.intecbilling.tresoap._2_0.afs_inbound.OrderItemRamp;
import com.intecbilling.tresoap._2_0.afs_inbound.OrderItemXa;
import com.intecbilling.tresoap._2_0.afs_inbound.ProcessOrderRequest;
import com.intecbilling.tresoap._2_0.afs_inbound.ProcessOrderResponse;
import com.siebel.eqxprocessdartbatches.EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS;
import com.siebel.eqxprocessdartbatches.UpsertSpcDARTSpcBatchInput;
import com.siebel.eqxprocessdartbatches.UpsertSpcDARTSpcBatchOutput;
import com.siebel.xml.eqx_20dart_20batch_20io.EqxDartBatch;
import com.siebel.xml.eqx_20dart_20batch_20io.EqxDartBatchLineXa;
import com.siebel.xml.eqx_20dart_20batch_20io.EqxDartBatchLines;
import com.siebel.xml.eqx_20dart_20batch_20io.ListOfEqxDartBatchIo;
import com.siebel.xml.eqx_20dart_20batch_20io.ListOfEqxDartBatchLineXa;
import com.siebel.xml.eqx_20dart_20batch_20io.ListOfEqxDartBatchLines;
/**
 * 
 * @author Ankur Bhargava
 *
 */
@Service
@Transactional
public class AppOpsInitiateDFRServiceImpl implements AppOpsInitiateDFRService {

	private static final String SBL_EQX_DELETE_POE_OPERATION = "DeletePOE";
	
	private static final String SBL_EQX_INSERT_POE_OPERATION = "InsertPOE";

	private static final String SBL_EQX_INSERT_POF_OPERATION = "InsertPOF";

	public static final String SBL_EQX_UPDATE_OPERATION = "Update";

	public static final String BLANK_OUT = "blank out";

	private static final String GETTER = "get";
	
	private static final String SETTER = "set";
	
	Logger logger = LoggerFactory.getLogger(AppOpsInitiateDFRService.class);
		
	@Autowired
	AppOpsInitiateDFRDao appOpsInitiateDFRDao;
	
	@Autowired
    AppOpsDartDaDao daDao;

	@Autowired
	SvAPIDao svAPIDao;
	
	@Autowired
	SrcServiceDaArrayRampDao srcServiceDaArrayRampDao;
	
	@Autowired
	SrcServiceDaArrayESKDao srcServiceDaArrayEskDao;
	
	@Autowired
	SrcAssetXADao srcAssetXADao;
	
/*	@Autowired 
	AFSInboundPortType svSoapClient;*/
	
	@Autowired 
	KafkaSenderService kafkaSenderService;
	
	@Autowired
	AppOpsDartAttrConfigService appOpsDartAttrConfigService;
	
	@Autowired
	AppOpsDartEditDfrService appOpsDartEditDfrService;
		
	@Autowired
	AppOppsDartHomeService appOppsDartHomeService;
		
	@Autowired
	AppOpsAccountMovementBusiness  appOpsAccountMovementBusiness;
		
	public static final Gson gson = new Gson();
	
	
	public HashMap<String, List<SrcCxiErrorTbl>> sblErrorMap = new HashMap<>();
	
	public HashMap<String, List<SrcCxiErrorTbl>> clxErrorMap = new HashMap<>();
	
	public HashMap<String, List<SrcCxiErrorTbl>> svErrorMap = new HashMap<>();
	
	public HashMap<String,SrcCxiErrorMasterTbl> errorMasterMap = new HashMap<>();
	
	public HashMap<String, HashSet<String>> errorCodeSblRowIdMap = new HashMap<>();
	
	@Autowired
	private ConfigService configService;
	
	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getSblErrorMap() {
		return this.sblErrorMap;
	}

	
	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getClxErrorMap() {
		return this.clxErrorMap;
	}

	
	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getSvErrorMap() {
		return this.svErrorMap;
	}
	
	@Override
	public HashMap<String,SrcCxiErrorMasterTbl> getErrorMasterMap() {
		return this.errorMasterMap;
	}

	
	@Override
	public HashMap<String, HashSet<String>> getErrorCodeSblRowIdMap() {
		return errorCodeSblRowIdMap;
	}


	//@PostConstruct
	@Override
	public HashMap<String, HashMap<String, List<SrcCxiErrorTbl>>>  init(){
		logger.info("============Error loading start ====================");
		///new Thread(() -> {
			HashMap<String, HashMap<String, List<SrcCxiErrorTbl>>>  data = appOpsInitiateDFRDao.getErrorList();
			this.sblErrorMap = data.get("sblError");
			this.clxErrorMap = data.get("clxError");
			this.svErrorMap = data.get("svError");
			if(sblErrorMap!=null && clxErrorMap!=null && svErrorMap!=null ){
			logger.info("============Error loading End ====================");
			logger.info("====Sbl Error size========" + sblErrorMap.size());
			logger.info("====Clx Error size========" + clxErrorMap.size());
			logger.info("====Sv Error size========" + svErrorMap.size());	
			}
		//}).start();
			logger.info("============Error Master Loading Start ====================");
			this.errorMasterMap = appOpsInitiateDFRDao.getErrorMasterList();
			logger.info("====Error Master Size ========" + errorMasterMap.size());
			logger.info("============Error Master Loading End ====================");
			
			this.errorCodeSblRowIdMap = appOpsInitiateDFRDao.getErrorCodeSblRowIdMap();
			logger.info("====Error Code Sbl Map Size ========" + errorMasterMap.size());
			
			
		return data;
			
	}
	
	@Override
	public void syncCages(String cageId, String dfrId) {
		List<SrcEqxClCage> srcCages = appOpsInitiateDFRDao.getSRCCages(cageId);
		if(CollectionUtils.isNotEmpty(srcCages)){
			logger.info("Dfr Id  " + dfrId + " : Cages :" +srcCages.size());
			appOpsInitiateDFRDao.saveOrUpdateCages(getCagesList(srcCages,dfrId));
		}else {
			logger.info("Cages from for cageid / row_id (clx) found null or empty (select * from EQX_DART.SRC_EQX_CL_CAGE where cage_id = '" +cageId+"';)" ); 
		}
	}

	@Override
	public void syncCages(Set<String> cageIds, String dfrId) {
		List<SrcEqxClCage> srcCages = appOpsInitiateDFRDao.getSRCCages(cageIds);
		if(CollectionUtils.isNotEmpty(srcCages)){
			logger.info("Dfr Id  " + dfrId + " : Cages :" +srcCages.size());
			appOpsInitiateDFRDao.saveOrUpdateCages(getCagesList(srcCages,dfrId));
		}else {
			logger.info("Cages from for cageid / row_id (clx) found null or empty (select * from EQX_DART.SRC_EQX_CL_CAGE where cage_id = '" +cageIds.toArray()+"';)" ); 
		}
	}
	

	private List<EqxClCage> getCagesList(List<SrcEqxClCage> srcCages, String dfrId) {
		List<EqxClCage> cages  = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(srcCages)){
			srcCages.stream().forEach(src->{
				EqxClCage dest= new EqxClCage();
				dest.setDfrLineId(src.getCageId()+"."+dfrId);
				dest.setCageId(src.getCageId()+"."+dfrId);
				dest.setDfrId(dfrId);
				
				
				dest.setLocation(src.getLocation());
				dest.setActCabinet(src.getActCabinet());
				dest.setStatus(src.getStatus());
				dest.setSpaceType(src.getSpaceType());
				dest.setConfigurableCabinet(src.getConfigurableCabinet());
				dest.setDcCircuitsInstalled(src.getDcCircuitsInstalled());
				dest.setCabeThresholdLevel(src.getCabeThresholdLevel());
				dest.setChurnCageLocation(src.getChurnCageLocation());
				dest.setDirectAccessCrossConnect(src.getDirectAccessCrossConnect());
				dest.setCageLegacyNumber(src.getCageLegacyNumber());
				dest.setChurnDirectAccessCc(src.getChurnDirectAccessCc());
				dest.setCabeThresholdMin(src.getCabeThresholdMin());
				dest.setContractedCabe(src.getContractedCabe());
				dest.setCageDesignedKva(src.getCageDesignedKva());
				dest.setCageAtMaxDrawId(src.getCageAtMaxDrawId());
				dest.setCagesalesSiteRatingfrom(src.getCagesalesSiteRatingfrom());
				dest.setCagesalesSiteRatingto(src.getCagesalesSiteRatingto());
				dest.setChurnContractedKva(src.getChurnContractedKva());
				dest.setDrawcapPowValidations(src.getDrawcapPowValidations());
				dest.setDrawcapThresholdLevel(src.getDrawcapThresholdLevel());
				dest.setCabeThresholdMax(src.getCabeThresholdMax());
				dest.setChurnEquinixCageType(src.getChurnEquinixCageType());
				dest.setCircuitBrkPosInstalled(src.getCircuitBrkPosInstalled());
				dest.setAggregateKvaInstalled(src.getAggregateKvaInstalled());
				dest.setChurnContractedCabe(src.getChurnContractedCabe());
				dest.setChurnEquinixDeptType(src.getChurnEquinixDeptType());
				dest.setChurnPrevCapStatus(src.getChurnPrevCapStatus());
				dest.setAggregateCbpInstalled(src.getAggregateCbpInstalled());
				dest.setCabeThresholdValMax(src.getCabeThresholdValMax());
				dest.setCageDesignedPec(src.getCageDesignedPec());
				dest.setCapacityStatusId(src.getCapacityStatusId());
				dest.setCabeThresholdValMin(src.getCabeThresholdValMin());
				dest.setCircuitBrkPosCap(src.getCircuitBrkPosCap());
				dest.setDrawcapThresholdValMin(src.getDrawcapThresholdValMin());
				dest.setDrawcapkwThresholdValMin(src.getDrawcapkwThresholdValMin());
				dest.setDrawcapThresholdMin(src.getDrawcapThresholdMin());
				dest.setDrawcapThresholdValMax(src.getDrawcapThresholdValMax());
				dest.setDrawcapThresholdMax(src.getDrawcapThresholdMax());
				dest.setDtlCapxrestart1(src.getDtlCapxrestart1());
				dest.setDtlCapxrestart2(src.getDtlCapxrestart2());
				dest.setDtlCapxtimestamp(src.getDtlCapxtimestamp());
				dest.setDrawcapkwThresholdValMax(src.getDrawcapkwThresholdValMax());
				dest.setChurnPof(src.getChurnPof());
				dest.setAssetId(src.getAssetId());
				dest.setCdcCreationDt(src.getCdcCreationDt());
				dest.setChurnCustomer(src.getChurnCustomer());
				dest.setCageAreaSqmt(src.getCageAreaSqmt());
				dest.setAutocadTag(src.getAutocadTag());
				dest.setCageAreaSqft(src.getCageAreaSqft());
				dest.setChurnResvId(src.getChurnResvId());
				dest.setCageAudited(src.getCageAudited());
				dest.setCageLocation(src.getCageLocation());
				
				dest.setCircuitType(src.getCircuitType());
				dest.setCageKwh(src.getCageKwh());
				dest.setCageNum(src.getCageNum());
				dest.setChurnLocation(src.getChurnLocation());
				dest.setCreatedBy(src.getCreatedBy());
				
				dest.setDesgnCabinet(src.getDesgnCabinet());
				dest.setDrawCapKva(src.getDrawCapKva());
				dest.setContractedKva(src.getContractedKva());
				dest.setDtlCapxaction(src.getDtlCapxaction());
				dest.setEarmarkedTime(src.getEarmarkedTime());
				dest.setCreatedDate(src.getCreatedDate());
				dest.setDtlCapxuser(src.getDtlCapxuser());
				dest.setFloorId(src.getFloorId());
				dest.setIbxId(src.getIbxId());
				dest.setIsCabeValid(src.getIsCabeValid());
				dest.setInstallDate(src.getInstallDate());
				dest.setCustomerId(src.getCustomerId());
				dest.setPrimaryDrawRecDate(src.getPrimaryDrawRecDate());
				dest.setMaxNoUncondCircuits(src.getMaxNoUncondCircuits());
				dest.setLastUpdatedDate(src.getLastUpdatedDate());
				dest.setWorkflowStatusId(src.getWorkflowStatusId());
				dest.setWorkspaceTypeId(src.getWorkspaceTypeId());
				dest.setKvaPercabInstalled(src.getKvaPercabInstalled());
				dest.setPendAvailDateComments(src.getPendAvailDateComments());
				dest.setEquinixDeptType(src.getEquinixDeptType());
				dest.setPrevInstallKva(src.getPrevInstallKva());
				dest.setVerizonCustomerNumber(src.getVerizonCustomerNumber());
				dest.setMinimumCommitment(src.getMinimumCommitment());
				dest.setInstalledCabinet(src.getInstalledCabinet());
				dest.setIsDrawCapValid(src.getIsDrawCapValid());
				dest.setPendingAvailableDate(src.getPendingAvailableDate());
				dest.setPrevAvailStatus(src.getPrevAvailStatus());
				dest.setLeasedSpaceLinked(src.getLeasedSpaceLinked());
				dest.setPrevInstallCbp(src.getPrevInstallCbp());
				dest.setPreviousReservation(src.getPreviousReservation());
				dest.setEligibleFrMulReservation(src.getEligibleFrMulReservation());
				dest.setEquinixCageType(src.getEquinixCageType());
				dest.setMaxNoCondCircuits(src.getMaxNoCondCircuits());
				dest.setNoCondCircuitsInstalled(src.getNoCondCircuitsInstalled());
				dest.setKvaInstalledAtcage(src.getKvaInstalledAtcage());
				dest.setLegacySystemName(src.getLegacySystemName());
				dest.setStatusChangeDate(src.getStatusChangeDate());
				dest.setIsCbpEditedViaCfr(src.getIsCbpEditedViaCfr());
				dest.setLinkedPhysicalCage(src.getLinkedPhysicalCage());
				dest.setIsCustomerValid(src.getIsCustomerValid());
				dest.setNoUncondCircuitsInstalled(src.getNoUncondCircuitsInstalled());
				dest.setPec(src.getPec());
				dest.setLockedStatus(src.getLockedStatus());
				dest.setKvaPerCab(src.getKvaPerCab());
				dest.setMaxCabsPerRow(src.getMaxCabsPerRow());
				dest.setPhaseId(src.getPhaseId());
				dest.setNotes(src.getNotes());
				dest.setPof(src.getPof());
				dest.setIsEditable(src.getIsEditable());
				dest.setProductTypeId(src.getProductTypeId());
				dest.setMaxNumRows(src.getMaxNumRows());
				dest.setLastUpdatedBy(src.getLastUpdatedBy());
				dest.setLeasedSpaceId(src.getLeasedSpaceId());
				dest.setOpportunityId(src.getOpportunityId());
				dest.setPecCap(src.getPecCap());
				dest.setZoneId(src.getZoneId());
				dest.setReservationId(src.getReservationId());
				dest.setReasonForHold(src.getReasonForHold());
				dest.setUsage(src.getUsage());
				dest.setVersion(src.getVersion());
				dest.setWeight(src.getWeight());
				dest.setSubCustomer(src.getSubCustomer());
				dest.setPueCap(src.getPueCap());
				dest.setUniqueSpaceid(src.getUniqueSpaceid());
				dest.setStatus(src.getStatus());
				dest.setVDayOfWeek(src.getVDayOfWeek());
				dest.setSpaceType(src.getSpaceType());
				dest.setRoomId(src.getRoomId());
				dest.setVirtualCage(src.getVirtualCage());
				dest.setWwcsFlag(src.getWwcsFlag());
				cages.add(dest);
			});
		}
		return cages;
	}



	@Override
	public void syncCabinates(String cabinateId,String dfrId) {
		List<SrcEqxClCabinet> srcCabinates = appOpsInitiateDFRDao.getSRCCabinates(cabinateId);
		if(CollectionUtils.isNotEmpty(srcCabinates)){
			logger.info("Dfr Id  " + dfrId + " : Cabinets :" +srcCabinates.size());
			appOpsInitiateDFRDao.saveOrUpdateCabinates(getCabinets(srcCabinates,dfrId));
		}else {
			logger.info("Cabinet from for cabinetid / row_id (clx) found null or empty (select * from EQX_DART.SRC_EQX_CL_CABINET where cabinet_id = '" +cabinateId+"';)" ); 
		}
	}
	
	@Override
	public void syncCabinates(Set<String> cabinateIds,String dfrId) {
		List<SrcEqxClCabinet> srcCabinates = appOpsInitiateDFRDao.getSRCCabinates(cabinateIds);
		if(CollectionUtils.isNotEmpty(srcCabinates)){
			logger.info("Dfr Id  " + dfrId + " : Cabinets :" +srcCabinates.size());
			appOpsInitiateDFRDao.saveOrUpdateCabinates(getCabinets(srcCabinates,dfrId));
		}else {
			logger.info("Cabinet from for cabinetid / row_id (clx) found null or empty (select * from EQX_DART.SRC_EQX_CL_CABINET where cabinet_id = '" +cabinateIds+"';)" ); 
		}
	}
	
	private List<EqxClCabinet> getCabinets(List<SrcEqxClCabinet> srcCabinates, String dfrId){
		
		List<EqxClCabinet> Cabinates = new ArrayList<>();
		srcCabinates.stream().forEach(src->{
			EqxClCabinet dest = new EqxClCabinet();
			dest.setDfrId(dfrId);
			dest.setDfrLineId(src.getCabinetId()+"."+dfrId);
			dest.setCabinetId(src.getCabinetId()+"."+dfrId);
			dest.setActualCab(src.getActualCab());
			dest.setAssetId(src.getAssetId());
			dest.setCabinetKwh(src.getCabinetKwh());
			dest.setAutocadTag(src.getAutocadTag());
			dest.setCabinetNum(src.getCabinetNum());
			dest.setCabAtMaxDrawId(src.getCabAtMaxDrawId());
			dest.setCabinetAudited(src.getCabinetAudited());
			dest.setCabinetUsableKvaCap(src.getCabinetUsableKvaCap());
			dest.setChurnCabinetLocation(src.getChurnCabinetLocation());
			dest.setCapacityStatusId(src.getCapacityStatusId());
			dest.setChurnContractedCabe(src.getChurnContractedCabe());
			dest.setCabinetLocation(src.getCabinetLocation());
			dest.setChurnContractedKva(src.getChurnContractedKva());
			dest.setChurnEquinixCabinetType(src.getChurnEquinixCabinetType());
			dest.setChurnEquinixDeptType(src.getChurnEquinixDeptType());
			dest.setCircuitBrkPosInstalled(src.getCircuitBrkPosInstalled());
			dest.setChurnPrevCapStatus(src.getChurnPrevCapStatus());
			dest.setContractedCabe(src.getContractedCabe());
			dest.setDcCircuitsInstalled(src.getDcCircuitsInstalled());
			dest.setChurnPofIntgId(src.getChurnPofIntgId());
			dest.setChurnDirectAccessCc(src.getChurnDirectAccessCc());
			dest.setIsconfigured(src.getIsconfigured());
			
			dest.setDtlCapxaction(src.getDtlCapxaction());
			dest.setChurnResvId(src.getChurnResvId());
			dest.setCabinetRating(src.getCabinetRating());
			dest.setHeight(src.getHeight());
			dest.setCustomerId(src.getCustomerId());
			dest.setDrawCap(src.getDrawCap());
			dest.setContiguousNum(src.getContiguousNum());
			dest.setEarmarkedTime(src.getEarmarkedTime());
			dest.setKvaInstalled(src.getKvaInstalled());
			dest.setCageId(src.getCageId());
			dest.setLeasedSpaceId(src.getLeasedSpaceId());
			dest.setOpportunityId(src.getOpportunityId());
			dest.setCreatedDate(src.getCreatedDate());
			dest.setCabinetType(src.getCabinetType());
			dest.setDtlCapxuser(src.getDtlCapxuser());
			dest.setPec(src.getPec());
			dest.setInstallDate(src.getInstallDate());
			dest.setNotes(src.getNotes());
			dest.setPecCap(src.getPecCap());
			dest.setPof(src.getPof());
			dest.setCabinetStatus(src.getCabinetStatus());
			dest.setCreatedBy(src.getCreatedBy());
			dest.setCdcCreationDt(src.getCdcCreationDt());
			dest.setLastUpdatedBy(src.getLastUpdatedBy());
			dest.setDepth(src.getDepth());
			dest.setLockedStatus(src.getLockedStatus());
			dest.setContractedKva(src.getContractedKva());
			dest.setChurnPof(src.getChurnPof());
			dest.setCustCabRefNum(src.getCustCabRefNum());
			dest.setChurnCustomer(src.getChurnCustomer());
			dest.setWeight(src.getWeight());
			dest.setVDayOfWeek(src.getVDayOfWeek());
			dest.setUniqueSpaceid(src.getUniqueSpaceid());
			dest.setWidth(src.getWidth());
			dest.setWwcsFlag(src.getWwcsFlag());
			dest.setPofIntgId(src.getPofIntgId());
			dest.setZoneId(src.getZoneId());
			dest.setReasonForHold(src.getReasonForHold());
			dest.setVersion(src.getVersion());
			dest.setReservationId(src.getReservationId());
			dest.setEquinixCabinetType(src.getEquinixCabinetType());
			dest.setMaxCircuitBrkPosition(src.getMaxCircuitBrkPosition());
			dest.setDtlCapxrestart1(src.getDtlCapxrestart1());
			dest.setLegacyCabinetNum(src.getLegacyCabinetNum());
			dest.setMulitBillingTerms(src.getMulitBillingTerms());
			dest.setIsCustomerValid(src.getIsCustomerValid());
			dest.setInstalledUnitImpType(src.getInstalledUnitImpType());
			dest.setDtlCapxtimestamp(src.getDtlCapxtimestamp());
			dest.setEligibleFrMulReservation(src.getEligibleFrMulReservation());
			dest.setLegacySystemName(src.getLegacySystemName());
			dest.setPendAvailDateComments(src.getPendAvailDateComments());
			dest.setPendingAvailableDate(src.getPendingAvailableDate());
			dest.setDtlCapxrestart2(src.getDtlCapxrestart2());
			dest.setIsDrawCapValid(src.getIsDrawCapValid());
			dest.setLeasedSpaceLinked(src.getLeasedSpaceLinked());
			dest.setDirectAccessCrossConnect(src.getDirectAccessCrossConnect());
			dest.setPendingDeletion(src.getPendingDeletion());
			dest.setPrevAvailStatus(src.getPrevAvailStatus());
			dest.setPrevInstallCbp(src.getPrevInstallCbp());
			dest.setEquinixDeptType(src.getEquinixDeptType());
			dest.setIsPaViaTermination(src.getIsPaViaTermination());
			dest.setPrevInstallKva(src.getPrevInstallKva());
			dest.setInstalledUnitMtType(src.getInstalledUnitMtType());
			dest.setLastUpdatedDate(src.getLastUpdatedDate());
			dest.setParentCabinetId(src.getParentCabinetId());
			dest.setStatusChangeDate(src.getStatusChangeDate());
			dest.setVerizonCustomerNumber(src.getVerizonCustomerNumber());
			dest.setDrawcapPowValidations(src.getDrawcapPowValidations());
			dest.setPreviousReservation(src.getPreviousReservation());
			dest.setVirtualCabinet(src.getVirtualCabinet());
			dest.setDesignedCabinetTypeId(src.getDesignedCabinetTypeId());
			dest.setVirtualRackUnit(src.getVirtualRackUnit());
			dest.setWorkflowStatus(src.getWorkflowStatus());
			Cabinates.add(dest);
		});
		return Cabinates;
		
	}

	@Override
	public void syncAssets(String assetId,String dfrId) {
		List<SrcSAssetXa> srcAssets = appOpsInitiateDFRDao.getSRCAssets(assetId);
		if(CollectionUtils.isNotEmpty(srcAssets)){
			logger.info("Dfr Id  " + dfrId + " : Asset :" +srcAssets.size());
			List<SAssetXa> assets = getAssetes(srcAssets,dfrId);
			//logger.info("Asset list size : "+ assets.size()+ " for sbl row_id " + assetId);
			appOpsInitiateDFRDao.saveOrUpdateAssets(assets);
		}else{
			logger.info(" Src Asset not found by " + assetId);
		}
	}
	
	@Override
	public void syncAssets(Set<String> assetIds,String dfrId) {
		List<SrcSAssetXa> srcAssets = appOpsInitiateDFRDao.getSRCAssets(assetIds);
		if(CollectionUtils.isNotEmpty(srcAssets)){
			logger.info("Dfr Id  " + dfrId + " : Asset :" +srcAssets.size());
			List<SAssetXa> assets = getAssetes(srcAssets,dfrId);
			//logger.info("Asset list size : "+ assets.size()+ " for sbl row_id " + assetId);
			//appOpsInitiateDFRDao.saveOrUpdateAssets(assets);
			
			//TODO change logic to native query
			String sql="insert into eqx_dart.s_asset_xa select ROW_ID||'.'||"+dfrId+"     , CREATED              , CREATED_BY      , LAST_UPD           , LAST_UPD_BY   , MODIFICATION_NUM    , CONFLICT_ID     , ASSET_ID||'.'||"+dfrId+"             , ATTR_NAME      , HIDDEN_FLG      , READ_ONLY_FLG             , REQUIRED_FLG , DATE_VAL           , DB_LAST_UPD  , NUM_VAL          , SEQ_NUM          , ATTR_ID               , CFG_STATE_CD , CHAR_VAL          , DATA_TYPE_CD , DB_LAST_UPD_SRC        , DESC_TEXT         , DISPLAY_NAME , INTEGRATION_ID             , UOM_CD             , VALIDATION_SPEC          , VLDTN_LOV_TYPE_CD   , X_ATTR_TYPE_CD            , X_CMDB_VALUE              , X_INT_STATUS  , X_PREV_CHAR_VAL        , X_PREV_DATE_VAL         , X_PREV_NUM_VAL         , X_PREV_STATUS              , X_STATUS           , HDM_ID               , DTL__CAPXRESTART1    , DTL__CAPXRESTART2    , DTL__CAPXUSER              , DTL__CAPXTIMESTAMP , DTL__CAPXACTION         , CDC_CREATION_DT        , V_Day_Of_Week             , Asset_Num        , "+dfrId+", ROW_ID||'.'||"+dfrId+"  From eqx_dart.Src_S_Asset_Xa where asset_num in ( select distinct asset_num from eqx_dart.snapshot_siebel_asset_da where dfr_id = "+dfrId+")";
			appOpsDartAttrConfigService.runSqlQuery(sql);
			logger.info("asset xa SQL  " + sql+" executed");
			
		} else{
			logger.info(" Src Asset not found by " + assetIds);
		}
	}
	
	
	private List<SAssetXa> getAssetes(List<SrcSAssetXa> srcAssets , String dfrId){
		List<SAssetXa> assets = new ArrayList<>();
		srcAssets.stream().forEach(src->{
			SAssetXa dest = new SAssetXa();
			dest.setDfrId(dfrId);
			dest.setDfrLineId(src.getRowId()+"."+dfrId);
			dest.setRowId(src.getRowId()+"."+dfrId);
			dest.setAssetId(src.getAssetId()+"."+dfrId);
			
			
			dest.setDisplayName(src.getDisplayName());
			dest.setModificationNum(src.getModificationNum());
			dest.setValidationSpec(src.getValidationSpec());
			dest.setDtlCapxtimestamp(src.getDtlCapxtimestamp());
			dest.setDtlCapxrestart1(src.getDtlCapxrestart1());
			dest.setDtlCapxrestart2(src.getDtlCapxrestart2());
			dest.setRequiredFlg(src.getRequiredFlg());
			dest.setHdmId(src.getHdmId());
			dest.setConflictId(src.getConflictId());
			dest.setCfgStateCd(src.getCfgStateCd());
			
			dest.setDateVal(src.getDateVal());
			dest.setDbLastUpd(src.getDbLastUpd());
			dest.setDtlCapxaction(src.getDtlCapxaction());
			dest.setAttrName(src.getAttrName());
			dest.setIntegrationId(src.getIntegrationId());
			dest.setDbLastUpdSrc(src.getDbLastUpdSrc());
			dest.setCreated(src.getCreated());
		
			dest.setDataTypeCd(src.getDataTypeCd());
			dest.setDtlCapxuser(src.getDtlCapxuser());
			dest.setNumVal(src.getNumVal());
			dest.setAttrId(src.getAttrId());
			dest.setCharVal(src.getCharVal());
			dest.setLastUpd(src.getLastUpd());
			dest.setAssetNum(src.getAssetNum());
			dest.setHiddenFlg(src.getHiddenFlg());
			dest.setCreatedBy(src.getCreatedBy());
			dest.setDescText(src.getDescText());
			dest.setLastUpdBy(src.getLastUpdBy());
			dest.setReadOnlyFlg(src.getReadOnlyFlg());
			dest.setCdcCreationDt(src.getCdcCreationDt());
			dest.setSeqNum(src.getSeqNum());
			dest.setVDayOfWeek(src.getVDayOfWeek());
			dest.setUomCd(src.getUomCd());
			dest.setXStatus(src.getXStatus());
			dest.setXPrevDateVal(src.getXPrevDateVal());
			dest.setXPrevStatus(src.getXPrevStatus());
			dest.setXPrevCharVal(src.getXPrevCharVal());
			dest.setXAttrTypeCd(src.getXAttrTypeCd());
			dest.setXCmdbValue(src.getXCmdbValue());
			dest.setXIntStatus(src.getXIntStatus());
			dest.setXPrevNumVal(src.getXPrevNumVal());
			dest.setVldtnLovTypeCd(src.getVldtnLovTypeCd());
			
			assets.add(dest);
		});
		
		return assets;
	}

	private boolean markSblSelected(SiebelAssetDa da,  List<Product> rowIdsToFilter ){
		boolean result = false;
		for(Product product :  rowIdsToFilter){
			if(product.getName().equalsIgnoreCase(da.getHeader20())){
				 Da daa = product.getDa();
				 if(StringUtils.isNotBlank(daa.getSBL()) && daa.getSBL().equalsIgnoreCase(da.getHeader1())){
					 logger.info("Marked SBL ROW_ID " + da.getHeader1());
					 //da.setHeader57("Y");
					 return true;
				 }
			}
		}
		return result;
	}
	
	private boolean markClxSelected(ClxAssetDa da,  List<Product> rowIdsToFilter ){
		boolean result = false;
		for(Product product :  rowIdsToFilter){
			if(product.getName().equalsIgnoreCase(da.getHeader20())){
				 Da daa = product.getDa();
				 if(StringUtils.isNotBlank(daa.getCLX()) && daa.getCLX().equalsIgnoreCase(da.getHeader1())){
					 logger.info("Marked CLX ROW_ID " + da.getHeader1());
					 //da.setHeader57("Y");
					 return true;
				 }
			}
		}
		return result;
	}
	
	private boolean markSvSelected(SvAssetDa da,  List<Product> rowIdsToFilter ){
		boolean result = false;
		for(Product product :  rowIdsToFilter){
			if(product.getName().equalsIgnoreCase(da.getHeader20())){
				 Da daa = product.getDa();
				 if(StringUtils.isNotBlank(daa.getSV()) && daa.getSV().equalsIgnoreCase(da.getHeader1())){
					 logger.info("Marked SV ROW_ID " + da.getHeader1());
					// da.setHeader57("Y");
					 return true;
				 }
			}
		}
		return result;
	}
	
	
	// new method 
	@Override
	public void moveSblAssets(List<SiebelAssetDa> sblAssetDaList ,String dfrId){
		for(SiebelAssetDa sbl: sblAssetDaList){
			syncAssets(sbl.getHeader1(),dfrId);
		}
		
	}
	@Override
	public void moveClxCageAssets(List<ClxAssetDa> clxAssetDaList ,String dfrId){
		for(ClxAssetDa clx: clxAssetDaList){
			if(clx.getHeader20().equalsIgnoreCase("Cage") ){
				syncCages(clx.getHeader1(),dfrId);
			}
		}
	}
	
	@Override
	public void moveClxCabinetAssets(List<ClxAssetDa> clxAssetDaList ,String dfrId){
		for(ClxAssetDa clx: clxAssetDaList){
			 if(clx.getHeader20().equalsIgnoreCase("Cabinet")){
				syncCabinates(clx.getHeader1(),dfrId);
			}
		}
	}
	
	
	private ProductFilter deepCopyProductFilter(ProductFilter snapshotProductFilter){
		ProductFilter dbSnapShotfilter = new ProductFilter();
		dbSnapShotfilter.setKeyword(snapshotProductFilter.getKeyword());
		dbSnapShotfilter.setFilters(snapshotProductFilter.getFilters());
		dbSnapShotfilter.setSearchDropBox(snapshotProductFilter.getSearchDropBox());
		return dbSnapShotfilter ;
	}
	@Override
	public /*synchronized*/ HashMap<String,Object> initiateDfr(DfrDaInput dfrDaInput) throws InterruptedException, ExecutionException {
		HashMap<String,Object> dataMap = new HashMap<>();
		// db entry 
		ProductFilter dbSnapShotfilter = new ProductFilter();
		if(dfrDaInput!=null){
			// getting super set firing snapshot query
			logger.info("dfrDaInput :" + gson.toJson(dfrDaInput, DfrDaInput.class));
			ProductFilter snapshotFilter = dfrDaInput.getSnapshotfilter();
			dbSnapShotfilter =deepCopyProductFilter( dfrDaInput.getSnapshotfilter());
			String snapShotFilterJson = gson.toJson(snapshotFilter, ProductFilter.class);
			logger.info("Snapshotfilter : " + snapShotFilterJson);
			int step =1;
			 long start = System.currentTimeMillis();
			ProductFilterResult productFilterResult = new ProductFilterResult();
			String key = dfrDaInput.getSnapshotfilter().genrateCacheKey();
			logger.info("Cache Key " + key);
			logger.info("Cache Key decoded " + dfrDaInput.getSnapshotfilter().getDecodedKey());
			// Added this 9 aug 2019 release to fix if data got cleared from redis cache
            // in case user selected comma saprated asset numbers
			if (StringUtils.isNotEmpty(dfrDaInput.getSnapshotfilter().getKeyword()) && dfrDaInput.getSnapshotfilter().getKeyword().length() == 7
					&& dfrDaInput.getSnapshotfilter().getKeyword().toUpperCase().startsWith("VAL_")) {
				List<SiebelAssetDa> sblListCollTemp = new ArrayList<>();
				sblListCollTemp = daDao.getSblListWhenOnlyErrorFilterApplied(getProductFilterForErrorCodeGlobalFilter(dfrDaInput.getSnapshotfilter()),sblListCollTemp);
				sblListCollTemp = filterSiebelAssetDAForErrorCodeGlobalFilter(dfrDaInput.getSnapshotfilter().getFilters(),sblListCollTemp);
				productFilterResult = getProductFilterResultErrorCodeGlobal(sblListCollTemp);
			} else {
				
				validateProductFilterForCommaSaparatedAssetNumsInKeyword(dfrDaInput.getSnapshotfilter());
				productFilterResult.setProductFilter(dfrDaInput.getSnapshotfilter());
				if (DartConstants.IS_ELASTIC_CALL) {
					productFilterResult = daDao.getAllProductFilterResultElastic(productFilterResult);
				} else {
					productFilterResult = daDao.getProductFilterResult(productFilterResult);
				}
			}
			/**
			 * Sort selected products from  product filter result 
			 */
			
			List<String> products = null;
			if(dfrDaInput.getProducts()!=null){
				Set<String> selectedProducts = new HashSet<>();
				List<Product> dfrProducts = dfrDaInput.getProducts();
				dfrProducts.forEach(a -> selectedProducts.add(a.getName()));
				products = new ArrayList<>(selectedProducts);
				
			}else{
				products = ServiceUtil.getProductsFromRequest(snapshotFilter);
			}
			List<SiebelAssetDa> sblAssetDaList = ServiceUtil.filterSiebelAssetDa(productFilterResult.getSblList(),products);
//			List<SiebelAssetDa> sblAssetDaList  = productFilterResult.getSblList();
			List<ClxAssetDa> clxAssetDaList = productFilterResult.getClxList();
			List<SvAssetDa> svAssetDaList = productFilterResult.getSvList();
			 long end = System.currentTimeMillis();
			logger.info(" Snapshot filter time taken : " + (end -start));
			logger.info("step ==>"+step++ );
			logger.info("SBL : "+ sblAssetDaList.size());
			logger.info("CLX : "+ clxAssetDaList.size());
			logger.info("SV  : "+ svAssetDaList.size());
			List<Product> productListWithRowIds = null;
			if(dfrDaInput.getProducts()==null){
				productListWithRowIds = createDfrDaInputObject(sblAssetDaList,clxAssetDaList,svAssetDaList);
			}else{
				productListWithRowIds = dfrDaInput.getProducts();
			}
			String dfrId = String.valueOf(appOpsInitiateDFRDao.getNextDartSeq());
			
			dbSnapShotfilter.setDfrId(dfrId);
			/*dbSnapShotfilter.setFilters(snapshotFilter.getFilters());
			dbSnapShotfilter.setKeyword(snapshotFilter.getKeyword());
			dbSnapShotfilter.setSearchDropBox(snapshotFilter.getSearchDropBox());*/
			
			String dbSnapShotfilterJson = gson.toJson(dbSnapShotfilter, ProductFilter.class);
			// saving dfr in db 
			appOpsInitiateDFRDao.saveOrUpdateDfrMaster(getDfrMaster(dfrId,dbSnapShotfilterJson));

			DfrMaster dfrMaster = appOpsDartEditDfrService.getDfrMasterById(dfrId);
			ApprovalHistory approvalHistory = appOpsDartEditDfrService.getApprovalHistory(dfrMaster, LogicConstants.DFR_INITIATED,dfrMaster.getNotes());
			appOpsDartEditDfrService.saveOrUpdateApprovalHistory(approvalHistory);
			
			for(SiebelAssetDa sbl : sblAssetDaList){

				boolean isSelected = markSblSelected(sbl,productListWithRowIds);
				SnapshotSiebelAssetDa snapshotSiebelAssetDa = new SnapshotSiebelAssetDa();
				snapshotSiebelAssetDa.setDfrId(dfrId);
				snapshotSiebelAssetDa.setDfrLineId(sbl.getHeader1()+"."+dfrId);
				populateSblSnapshotAssetDa(snapshotSiebelAssetDa, sbl, dfrId,isSelected);
				appOpsInitiateDFRDao.saveOrUpdateSnapshotSiebelAssetDa(snapshotSiebelAssetDa);
					AssetNewVal assetNewVal = new AssetNewVal();
					assetNewVal.setDfrId(dfrId);
					assetNewVal.setDfrLineId(sbl.getHeader1()+"."+dfrId);
					assetNewVal.setHeader1(sbl.getHeader1()+"."+dfrId);
					assetNewVal.setHeader20(sbl.getHeader20()); // product name
					//assetNewVal.setHeader17(sbl.getHeader17()); // all date fields 
					//assetNewVal.setHeader34(sbl.getHeader34());
					//assetNewVal.setHeader36(sbl.getHeader36());
					//assetNewVal.setHeader55(sbl.getHeader55());
					if(isSelected){
					assetNewVal.setHeader57("Y"); // selected y
					}
					appOpsInitiateDFRDao.saveOrUpdateAssetNewVal(assetNewVal);
			
				List<SrcCxiErrorTbl> sblErrorList = this.sblErrorMap.get(sbl.getHeader1());
				if(CollectionUtils.isNotEmpty(sblErrorList)){
					appOpsInitiateDFRDao.saveSblErrorSnapshots(snapshotSiebelAssetDa,sbl, dfrId,sblErrorList);
				}
			}
			
			for(ClxAssetDa clx : clxAssetDaList){
					boolean isSelected = markClxSelected(clx, productListWithRowIds);
					SnapshotClxAssetDa snapshotClxAssetDa = new SnapshotClxAssetDa();
					snapshotClxAssetDa.setDfrId(dfrId);
					snapshotClxAssetDa.setDfrLineId(clx.getHeader1() + "."+dfrId);
					
					populateClxSnapshotAssetDa(snapshotClxAssetDa, clx, dfrId,isSelected);
					appOpsInitiateDFRDao.saveOrUpdateSnapshotClxAssetDa(snapshotClxAssetDa);
					
					List<SrcCxiErrorTbl> clxErrorList = this.clxErrorMap.get(clx.getHeader1());
					if(CollectionUtils.isNotEmpty(clxErrorList)){
						appOpsInitiateDFRDao.saveClxErrorSnapshots(snapshotClxAssetDa,clx, dfrId,clxErrorList);
					}
				}
		
			for(SvAssetDa sv : svAssetDaList){
				boolean isSelected = markSvSelected(sv,productListWithRowIds);
				SnapshotSvAssetDa snapshotSvAssetDa = new SnapshotSvAssetDa();
				snapshotSvAssetDa.setDfrId(dfrId);
				snapshotSvAssetDa.setDfrLineId(sv.getHeader1() + "."+dfrId);
				populateSvSnapshotAssetDa(snapshotSvAssetDa, sv, dfrId,isSelected);
				appOpsInitiateDFRDao.saveOrUpdateSnapshotSvAssetDa(snapshotSvAssetDa);
				List<SrcCxiErrorTbl> svErrorList = this.svErrorMap.get(sv.getHeader1());
				if(CollectionUtils.isNotEmpty(svErrorList)){
				appOpsInitiateDFRDao.saveSvErrorSnapshots(snapshotSvAssetDa,sv,  dfrId,svErrorList);
				}
						
			}
		
			dataMap.put(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS, productFilterResult);
			dataMap.put("dfrid", dfrId);
			dataMap.put("message", "dfr created successfully ");
			return dataMap ;	
			
	}else{
		dataMap.put("error", "no data received");
		return dataMap ;
	}
		
	}
	
	// new method end
	
	private DfrMaster getDfrMaster(String dfrId,String snapShotFilterJson){
		
		DfrMaster dfrMaster = new DfrMaster();
		dfrMaster.setDfrId(dfrId);
		
		dfrMaster.setCreatedDt(new Date());
		dfrMaster.setStatus("New");
		dfrMaster.setValidStatus("N");
		dfrMaster.setAsyncVal("N");
		dfrMaster.setSsFilter(snapShotFilterJson);
		User user = UserThreadLocal.userThreadLocalVar.get();
		dfrMaster.setCreatedBy(user.getUserId());
		dfrMaster.setCreatedTeam(user.getPrimaryAssignGroup().getGroupName());
		dfrMaster.setAssignedTeam(user.getPrimaryAssignGroup().getGroupName());
		dfrMaster.setRegion(user.getPrimaryAssignGroup().getRegion());
		dfrMaster.setAssignedTo(user.getUserId());
		dfrMaster.setAssignedDt(new Date());
		logger.info("DFR_MASTER: " + dfrMaster.toString());
		if(user.getPrimaryAssignGroup().getGroupName().contains("Network")){
			dfrMaster.setIsNetworkDfr("Y");
		}
		return dfrMaster;
		
	}
	
private DfrMaster getDfrMasterAccountMove(String dfrId){
		
		DfrMaster dfrMaster = new DfrMaster();
		dfrMaster.setDfrId(dfrId);		
		dfrMaster.setCreatedDt(new Date());
		dfrMaster.setStatus("New");
		dfrMaster.setValidStatus("Y");
		dfrMaster.setAsyncVal("Y");
		dfrMaster.setIsAccountMoveDfr("Y");
		User user = UserThreadLocal.userThreadLocalVar.get();
		dfrMaster.setCreatedBy(user.getUserId());
		dfrMaster.setCreatedTeam(user.getPrimaryAssignGroup().getGroupName());
		dfrMaster.setAssignedTeam(user.getPrimaryAssignGroup().getGroupName());
		dfrMaster.setRegion(user.getPrimaryAssignGroup().getRegion());
		logger.info("DFR_MASTER: " + dfrMaster.toString());
		return dfrMaster;
		
	}
	
private DfrMaster getDfrMasterMobile(String dfrId,String snapShotFilterJson){
		
		DfrMaster dfrMaster = new DfrMaster();
		dfrMaster.setDfrId(dfrId);
		
		dfrMaster.setCreatedDt(new Date());
		dfrMaster.setStatus(LogicConstants.PHYSICAL_AUDIT_INTIATE);
		dfrMaster.setValidStatus("Y");
		dfrMaster.setPhysicalAudit("Y");
		dfrMaster.setAsyncVal("N");
		dfrMaster.setSsFilter(snapShotFilterJson);
		User user = UserThreadLocal.userThreadLocalVar.get();
		dfrMaster.setCreatedBy(user.getUserId());
		dfrMaster.setAssignedTo(user.getUserId());
		dfrMaster.setCreatedTeam(user.getPrimaryAssignGroup().getGroupName());
		dfrMaster.setAssignedTeam(user.getPrimaryAssignGroup().getGroupName());
		dfrMaster.setRegion(user.getPrimaryAssignGroup().getRegion());
		dfrMaster.setPriority("P1");
		dfrMaster.setNotes("MOBILE-DFR");
		dfrMaster.setIsMobileDfr("Y");
		logger.info("DFR_MASTER: " + dfrMaster.toString());
		return dfrMaster;
		
	}
	
	private void populateSvSnapshotAssetDa(SnapshotSvAssetDa  sda,SvAssetDa da,String dfrId,boolean isSelected){
		
		sda.setHeader1(da.getHeader1()+"."+dfrId);
		sda.setHeader2(da.getHeader2());
		sda.setHeader3(da.getHeader3());
		sda.setHeader4(da.getHeader4());
		sda.setHeader5(da.getHeader5());
		sda.setHeader6(da.getHeader6());
		sda.setHeader7(da.getHeader7());
		sda.setHeader8(da.getHeader8());
		sda.setHeader9(da.getHeader9());
		sda.setHeader10(da.getHeader10());
		sda.setHeader11(da.getHeader11());
		sda.setHeader12(da.getHeader12());
		sda.setHeader13(da.getHeader13());
		sda.setHeader14(da.getHeader14());
		sda.setHeader15(da.getHeader15());
		sda.setHeader16(da.getHeader16());
		sda.setHeader17(da.getHeader17());
		sda.setHeader18(da.getHeader18());
		sda.setHeader19(da.getHeader19());
		sda.setHeader20(da.getHeader20());
		sda.setHeader21(da.getHeader21());
		sda.setHeader22(da.getHeader22());
		sda.setHeader23(da.getHeader23());
		sda.setHeader24(da.getHeader24());
		sda.setHeader25(da.getHeader25());
		sda.setHeader26(da.getHeader26());
		if(da.getHeader27()!=null){
			sda.setHeader27(da.getHeader27()+"."+dfrId);
		}else{
			sda.setHeader27(da.getHeader27());
		}
		sda.setHeader28(da.getHeader28());
		sda.setHeader29(da.getHeader29());
		sda.setHeader30(da.getHeader30());
		sda.setHeader31(da.getHeader31());
		sda.setHeader32(da.getHeader32());
		sda.setHeader33(da.getHeader33());
		sda.setHeader34(da.getHeader34());
		sda.setHeader35(da.getHeader35());
		sda.setHeader36(da.getHeader36());
		sda.setHeader37(da.getHeader37());
		sda.setHeader38(da.getHeader38());
		sda.setHeader39(da.getHeader39());
		sda.setHeader40(da.getHeader40());
		sda.setHeader41(da.getHeader41());
		sda.setHeader42(da.getHeader42());
		sda.setHeader43(da.getHeader43());
		sda.setHeader44(da.getHeader44());
		sda.setHeader45(da.getHeader45());
		sda.setHeader46(da.getHeader46());
		sda.setHeader47(da.getHeader47());
		sda.setHeader48(da.getHeader48());
		sda.setHeader49(da.getHeader49());
		sda.setHeader50(da.getHeader50());
		sda.setHeader51(da.getHeader51());
		sda.setHeader52(da.getHeader52());
		sda.setHeader53(da.getHeader53());
		sda.setHeader54(da.getHeader54());
		sda.setHeader55(da.getHeader55());
		sda.setHeader56(da.getHeader56());
		if(isSelected){
			sda.setHeader57("Y");
		}else{
			sda.setHeader57(da.getHeader57());
		}
		sda.setHeader58(da.getHeader58());
		sda.setHeader59(da.getHeader59());
		sda.setHeader60(da.getHeader60());
		sda.setHeader61(da.getHeader61());
		sda.setHeader62(da.getHeader62());
		sda.setHeader63(da.getHeader63());
		sda.setHeader64(da.getHeader64());
		sda.setHeader65(da.getHeader65());
		sda.setHeader66(da.getHeader66());

		
		sda.setAttr1(da.getAttr1());
		sda.setAttr2(da.getAttr2());
		sda.setAttr3(da.getAttr3());
		sda.setAttr4(da.getAttr4());
		sda.setAttr5(da.getAttr5());
		sda.setAttr6(da.getAttr6());
		sda.setAttr7(da.getAttr7());
		sda.setAttr8(da.getAttr8());
		sda.setAttr9(da.getAttr9());
		sda.setAttr10(da.getAttr10());
		sda.setAttr11(da.getAttr11());
		sda.setAttr12(da.getAttr12());
		sda.setAttr13(da.getAttr13());
		sda.setAttr14(da.getAttr14());
		sda.setAttr15(da.getAttr15());
		sda.setAttr16(da.getAttr16());
		sda.setAttr17(da.getAttr17());
		sda.setAttr18(da.getAttr18());
		sda.setAttr19(da.getAttr19());
		sda.setAttr20(da.getAttr20());
		sda.setAttr21(da.getAttr21());
		sda.setAttr22(da.getAttr22());
		sda.setAttr23(da.getAttr23());
		sda.setAttr24(da.getAttr24());
		sda.setAttr25(da.getAttr25());
		sda.setAttr26(da.getAttr26());
		sda.setAttr27(da.getAttr27());
		sda.setAttr28(da.getAttr28());
		sda.setAttr29(da.getAttr29());
		sda.setAttr30(da.getAttr30());
		sda.setAttr31(da.getAttr31());
		sda.setAttr32(da.getAttr32());
		sda.setAttr33(da.getAttr33());
		sda.setAttr34(da.getAttr34());
		sda.setAttr35(da.getAttr35());
		sda.setAttr36(da.getAttr36());
		sda.setAttr37(da.getAttr37());
		sda.setAttr38(da.getAttr38());
		sda.setAttr39(da.getAttr39());
		sda.setAttr40(da.getAttr40());
		sda.setAttr41(da.getAttr41());
		sda.setAttr42(da.getAttr42());
		sda.setAttr43(da.getAttr43());
		sda.setAttr44(da.getAttr44());
		sda.setAttr45(da.getAttr45());
		sda.setAttr46(da.getAttr46());
		sda.setAttr47(da.getAttr47());
		sda.setAttr48(da.getAttr48());
		sda.setAttr49(da.getAttr49());
		sda.setAttr50(da.getAttr50());
		sda.setAttr51(da.getAttr51());
		sda.setAttr52(da.getAttr52());
		sda.setAttr53(da.getAttr53());
		sda.setAttr54(da.getAttr54());
		sda.setAttr55(da.getAttr55());
		sda.setAttr56(da.getAttr56());
		sda.setAttr57(da.getAttr57());
		sda.setAttr58(da.getAttr58());
		sda.setAttr59(da.getAttr59());
		sda.setAttr60(da.getAttr60());
		sda.setAttr61(da.getAttr61());
		sda.setAttr62(da.getAttr62());
		sda.setAttr63(da.getAttr63());
		sda.setAttr64(da.getAttr64());
		sda.setAttr65(da.getAttr65());
		sda.setAttr66(da.getAttr66());
		sda.setAttr67(da.getAttr67());
		sda.setAttr68(da.getAttr68());
		sda.setAttr69(da.getAttr69());
		sda.setAttr70(da.getAttr70());
		sda.setAttr71(da.getAttr71());
		sda.setAttr72(da.getAttr72());
		sda.setAttr73(da.getAttr73());
		sda.setAttr74(da.getAttr74());
		sda.setAttr75(da.getAttr75());
		sda.setAttr76(da.getAttr76());
		sda.setAttr77(da.getAttr77());
		sda.setAttr78(da.getAttr78());
		sda.setAttr79(da.getAttr79());
		sda.setAttr80(da.getAttr80());
		sda.setAttr81(da.getAttr81());
		sda.setAttr82(da.getAttr82());
		sda.setAttr83(da.getAttr83());
		sda.setAttr84(da.getAttr84());
		sda.setAttr85(da.getAttr85());
		sda.setAttr86(da.getAttr86());
		sda.setAttr87(da.getAttr87());
		sda.setAttr88(da.getAttr88());
		sda.setAttr89(da.getAttr89());
		sda.setAttr90(da.getAttr90());
		sda.setAttr91(da.getAttr91());
		sda.setAttr92(da.getAttr92());
		sda.setAttr93(da.getAttr93());
		sda.setAttr94(da.getAttr94());
		sda.setAttr95(da.getAttr95());
		sda.setAttr96(da.getAttr96());
		sda.setAttr97(da.getAttr97());
		sda.setAttr98(da.getAttr98());
		sda.setAttr99(da.getAttr99());
		sda.setAttr100(da.getAttr100());
		sda.setAttr101(da.getAttr101());
		sda.setAttr102(da.getAttr102());
		sda.setAttr103(da.getAttr103());
		sda.setAttr104(da.getAttr104());
		sda.setAttr105(da.getAttr105());
		sda.setAttr106(da.getAttr106());
		sda.setAttr107(da.getAttr107());
		sda.setAttr108(da.getAttr108());
		sda.setAttr109(da.getAttr109());
		sda.setAttr110(da.getAttr110());
		sda.setAttr111(da.getAttr111());
		sda.setAttr112(da.getAttr112());
		sda.setAttr113(da.getAttr113());
		sda.setAttr114(da.getAttr114());
		sda.setAttr115(da.getAttr115());
		sda.setAttr116(da.getAttr116());
		sda.setAttr117(da.getAttr117());
		sda.setAttr118(da.getAttr118());
		sda.setAttr119(da.getAttr119());
		sda.setAttr120(da.getAttr120());
		sda.setAttr121(da.getAttr121());
		sda.setAttr122(da.getAttr122());
		sda.setAttr123(da.getAttr123());
		sda.setAttr124(da.getAttr124());
		sda.setAttr125(da.getAttr125());
		sda.setAttr126(da.getAttr126());
		sda.setAttr127(da.getAttr127());
		sda.setAttr128(da.getAttr128());
		sda.setAttr129(da.getAttr129());
		sda.setAttr130(da.getAttr130());
		sda.setAttr131(da.getAttr131());
		sda.setAttr132(da.getAttr132());
		sda.setAttr133(da.getAttr133());
		sda.setAttr134(da.getAttr134());
		sda.setAttr135(da.getAttr135());
		sda.setAttr136(da.getAttr136());
		sda.setAttr137(da.getAttr137());
		sda.setAttr138(da.getAttr138());
		sda.setAttr139(da.getAttr139());
		sda.setAttr140(da.getAttr140());
		sda.setAttr141(da.getAttr141());
		sda.setAttr142(da.getAttr142());
		sda.setAttr143(da.getAttr143());
		sda.setAttr144(da.getAttr144());
		sda.setAttr145(da.getAttr145());
		sda.setAttr146(da.getAttr146());
		sda.setAttr147(da.getAttr147());
		sda.setAttr148(da.getAttr148());
		sda.setAttr149(da.getAttr149());
		sda.setAttr150(da.getAttr150());
		sda.setAttr151(da.getAttr151());
		sda.setAttr152(da.getAttr152());
		sda.setAttr153(da.getAttr153());
		sda.setAttr154(da.getAttr154());
		sda.setAttr155(da.getAttr155());
		sda.setAttr156(da.getAttr156());
		sda.setAttr157(da.getAttr157());
		sda.setAttr158(da.getAttr158());
		sda.setAttr159(da.getAttr159());
		sda.setAttr160(da.getAttr160());
		sda.setAttr161(da.getAttr161());
		sda.setAttr162(da.getAttr162());
		sda.setAttr163(da.getAttr163());
		sda.setAttr164(da.getAttr164());
		sda.setAttr165(da.getAttr165());
		sda.setAttr166(da.getAttr166());
		sda.setAttr167(da.getAttr167());
		sda.setAttr168(da.getAttr168());
		sda.setAttr169(da.getAttr169());
		sda.setAttr170(da.getAttr170());
		sda.setAttr171(da.getAttr171());
		sda.setAttr172(da.getAttr172());
		sda.setAttr173(da.getAttr173());
		sda.setAttr174(da.getAttr174());
		sda.setAttr175(da.getAttr175());
		sda.setAttr176(da.getAttr176());
		sda.setAttr177(da.getAttr177());
		sda.setAttr178(da.getAttr178());
		sda.setAttr179(da.getAttr179());
		sda.setAttr180(da.getAttr180());
		sda.setAttr181(da.getAttr181());
		sda.setAttr182(da.getAttr182());
		sda.setAttr183(da.getAttr183());
		sda.setAttr184(da.getAttr184());
		sda.setAttr185(da.getAttr185());
		sda.setAttr186(da.getAttr186());
		sda.setAttr187(da.getAttr187());
		sda.setAttr188(da.getAttr188());
		sda.setAttr189(da.getAttr189());
		sda.setAttr190(da.getAttr190());
		sda.setAttr191(da.getAttr191());
		sda.setAttr192(da.getAttr192());
		sda.setAttr193(da.getAttr193());
		sda.setAttr194(da.getAttr194());
		sda.setAttr195(da.getAttr195());
		sda.setAttr196(da.getAttr196());
		sda.setAttr197(da.getAttr197());
		sda.setAttr198(da.getAttr198());
		sda.setAttr199(da.getAttr199());
		sda.setAttr200(da.getAttr200());
		sda.setAttr201(da.getAttr201());
		sda.setAttr202(da.getAttr202());
		sda.setAttr203(da.getAttr203());
		sda.setAttr204(da.getAttr204());
		sda.setAttr205(da.getAttr205());
		sda.setAttr206(da.getAttr206());
		sda.setAttr207(da.getAttr207());
		sda.setAttr208(da.getAttr208());
		sda.setAttr209(da.getAttr209());
		sda.setAttr210(da.getAttr210());
		sda.setAttr211(da.getAttr211());
		sda.setAttr212(da.getAttr212());
		sda.setAttr213(da.getAttr213());
		sda.setAttr214(da.getAttr214());
		sda.setAttr215(da.getAttr215());
		sda.setAttr216(da.getAttr216());
		sda.setAttr217(da.getAttr217());
		sda.setAttr218(da.getAttr218());
		sda.setAttr219(da.getAttr219());
		sda.setAttr220(da.getAttr220());
		sda.setAttr221(da.getAttr221());
		sda.setAttr222(da.getAttr222());
		sda.setAttr223(da.getAttr223());
		sda.setAttr224(da.getAttr224());
		sda.setAttr225(da.getAttr225());
		sda.setAttr226(da.getAttr226());
		sda.setAttr227(da.getAttr227());
		sda.setAttr228(da.getAttr228());
		sda.setAttr229(da.getAttr229());
		sda.setAttr230(da.getAttr230());
		sda.setAttr231(da.getAttr231());
		sda.setAttr232(da.getAttr232());
		sda.setAttr233(da.getAttr233());
		sda.setAttr234(da.getAttr234());
		sda.setAttr235(da.getAttr235());
		sda.setAttr236(da.getAttr236());
		sda.setAttr237(da.getAttr237());
		sda.setAttr238(da.getAttr238());
		sda.setAttr239(da.getAttr239());
		sda.setAttr240(da.getAttr240());
		sda.setAttr241(da.getAttr241());
		sda.setAttr242(da.getAttr242());
		sda.setAttr243(da.getAttr243());
		sda.setAttr244(da.getAttr244());
		sda.setAttr245(da.getAttr245());
		sda.setAttr246(da.getAttr246());
		sda.setAttr247(da.getAttr247());
		sda.setAttr248(da.getAttr248());
		sda.setAttr249(da.getAttr249());
		sda.setAttr250(da.getAttr250());
		sda.setAttr251(da.getAttr251());
		sda.setAttr252(da.getAttr252());
		sda.setAttr253(da.getAttr253());
		sda.setAttr254(da.getAttr254());
		sda.setAttr255(da.getAttr255());
		sda.setAttr256(da.getAttr256());
		sda.setAttr257(da.getAttr257());
		sda.setAttr258(da.getAttr258());
		sda.setAttr259(da.getAttr259());
		sda.setAttr260(da.getAttr260());
		sda.setAttr261(da.getAttr261());
		sda.setAttr262(da.getAttr262());
		sda.setAttr263(da.getAttr263());
		sda.setAttr264(da.getAttr264());
		sda.setAttr265(da.getAttr265());
		sda.setAttr266(da.getAttr266());
		sda.setAttr267(da.getAttr267());
		sda.setAttr268(da.getAttr268());
		sda.setAttr269(da.getAttr269());
		sda.setAttr270(da.getAttr270());
		sda.setAttr271(da.getAttr271());
		sda.setAttr272(da.getAttr272());
		sda.setAttr273(da.getAttr273());
		sda.setAttr274(da.getAttr274());
		sda.setAttr275(da.getAttr275());
		sda.setAttr276(da.getAttr276());
		sda.setAttr277(da.getAttr277());
		sda.setAttr278(da.getAttr278());
		sda.setAttr279(da.getAttr279());
		sda.setAttr280(da.getAttr280());
		sda.setAttr281(da.getAttr281());
		sda.setAttr282(da.getAttr282());
		sda.setAttr283(da.getAttr283());
		sda.setAttr284(da.getAttr284());
		sda.setAttr285(da.getAttr285());
		sda.setAttr286(da.getAttr286());
		sda.setAttr287(da.getAttr287());
		sda.setAttr288(da.getAttr288());
		sda.setAttr289(da.getAttr289());
		sda.setAttr290(da.getAttr290());
		sda.setAttr291(da.getAttr291());
		sda.setAttr292(da.getAttr292());
		sda.setAttr293(da.getAttr293());
		sda.setAttr294(da.getAttr294());
		sda.setAttr295(da.getAttr295());
		sda.setAttr296(da.getAttr296());
		sda.setAttr297(da.getAttr297());
		sda.setAttr298(da.getAttr298());
		sda.setAttr299(da.getAttr299());
		sda.setAttr300(da.getAttr300());
		sda.setAttr301(da.getAttr301());
		sda.setAttr302(da.getAttr302());
		sda.setAttr303(da.getAttr303());
		sda.setAttr304(da.getAttr304());
		sda.setAttr305(da.getAttr305());
		sda.setAttr306(da.getAttr306());
		sda.setAttr307(da.getAttr307());
		sda.setAttr308(da.getAttr308());
		sda.setAttr309(da.getAttr309());
		sda.setAttr310(da.getAttr310());
		sda.setAttr311(da.getAttr311());
		sda.setAttr312(da.getAttr312());
		sda.setAttr313(da.getAttr313());
		sda.setAttr314(da.getAttr314());
		sda.setAttr315(da.getAttr315());
		sda.setAttr316(da.getAttr316());
		sda.setAttr317(da.getAttr317());
		sda.setAttr318(da.getAttr318());
		sda.setAttr319(da.getAttr319());
		sda.setAttr320(da.getAttr320());
		sda.setAttr321(da.getAttr321());
		sda.setAttr322(da.getAttr322());
		sda.setAttr323(da.getAttr323());
		sda.setAttr324(da.getAttr324());
		sda.setAttr325(da.getAttr325());
		sda.setAttr326(da.getAttr326());
		sda.setAttr327(da.getAttr327());
		sda.setAttr328(da.getAttr328());
		sda.setAttr329(da.getAttr329());
		sda.setAttr330(da.getAttr330());
		sda.setAttr331(da.getAttr331());
		sda.setAttr332(da.getAttr332());
		sda.setAttr333(da.getAttr333());
		sda.setAttr334(da.getAttr334());
		sda.setAttr335(da.getAttr335());
		sda.setAttr336(da.getAttr336());
		sda.setAttr337(da.getAttr337());
		sda.setAttr338(da.getAttr338());
		sda.setAttr339(da.getAttr339());
		sda.setAttr340(da.getAttr340());
		sda.setAttr341(da.getAttr341());
		sda.setAttr342(da.getAttr342());
		sda.setAttr343(da.getAttr343());
		sda.setAttr344(da.getAttr344());
		sda.setAttr345(da.getAttr345());
		sda.setAttr346(da.getAttr346());
		sda.setAttr347(da.getAttr347());
		sda.setAttr348(da.getAttr348());
		sda.setAttr349(da.getAttr349());
		sda.setAttr350(da.getAttr350());
	}
	private void populateClxSnapshotAssetDa(SnapshotClxAssetDa  sda,ClxAssetDa da,String dfrId,boolean isSelected){
		
		sda.setHeader1(da.getHeader1()+"."+dfrId);
		sda.setHeader2(da.getHeader2());
		sda.setHeader3(da.getHeader3());
		sda.setHeader4(da.getHeader4());
		sda.setHeader5(da.getHeader5());
		sda.setHeader6(da.getHeader6());
		sda.setHeader7(da.getHeader7());
		sda.setHeader8(da.getHeader8());
		sda.setHeader9(da.getHeader9());
		sda.setHeader10(da.getHeader10());
		sda.setHeader11(da.getHeader11());
		sda.setHeader12(da.getHeader12());
		sda.setHeader13(da.getHeader13());
		sda.setHeader14(da.getHeader14());
		sda.setHeader15(da.getHeader15());
		sda.setHeader16(da.getHeader16());
		sda.setHeader17(da.getHeader17());
		sda.setHeader18(da.getHeader18());
		sda.setHeader19(da.getHeader19());
		sda.setHeader20(da.getHeader20());
		sda.setHeader21(da.getHeader21());
		sda.setHeader22(da.getHeader22());
		sda.setHeader23(da.getHeader23());
		sda.setHeader24(da.getHeader24());
		sda.setHeader25(da.getHeader25());
		sda.setHeader26(da.getHeader26());
		if(da.getHeader27()!=null){
			sda.setHeader27(da.getHeader27()+"."+dfrId);
		}else{
			sda.setHeader27(da.getHeader27());
		}
		sda.setHeader28(da.getHeader28());
		sda.setHeader29(da.getHeader29());
		sda.setHeader30(da.getHeader30());
		sda.setHeader31(da.getHeader31());
		sda.setHeader32(da.getHeader32());
		sda.setHeader33(da.getHeader33());
		sda.setHeader34(da.getHeader34());
		sda.setHeader35(da.getHeader35());
		sda.setHeader36(da.getHeader36());
		sda.setHeader37(da.getHeader37());
		sda.setHeader38(da.getHeader38());
		sda.setHeader39(da.getHeader39());
		sda.setHeader40(da.getHeader40());
		sda.setHeader41(da.getHeader41());
		sda.setHeader42(da.getHeader42());
		sda.setHeader43(da.getHeader43());
		sda.setHeader44(da.getHeader44());
		sda.setHeader45(da.getHeader45());
		sda.setHeader46(da.getHeader46());
		sda.setHeader47(da.getHeader47());
		sda.setHeader48(da.getHeader48());
		sda.setHeader49(da.getHeader49());
		sda.setHeader50(da.getHeader50());
		sda.setHeader51(da.getHeader51());
		sda.setHeader52(da.getHeader52());
		sda.setHeader53(da.getHeader53());
		sda.setHeader54(da.getHeader54());
		sda.setHeader55(da.getHeader55());
		sda.setHeader56(da.getHeader56());
		if(isSelected){
			sda.setHeader57("Y");
		}else{
			sda.setHeader57(da.getHeader57());
		}
		sda.setHeader58(da.getHeader58());
		sda.setHeader59(da.getHeader59());
		sda.setHeader60(da.getHeader60());
		sda.setHeader61(da.getHeader61());
		sda.setHeader62(da.getHeader62());
		sda.setHeader63(da.getHeader63());
		sda.setHeader64(da.getHeader64());
		sda.setHeader65(da.getHeader65());
		sda.setHeader66(da.getHeader66());

		
		sda.setAttr1(da.getAttr1());
		sda.setAttr2(da.getAttr2());
		sda.setAttr3(da.getAttr3());
		sda.setAttr4(da.getAttr4());
		sda.setAttr5(da.getAttr5());
		sda.setAttr6(da.getAttr6());
		sda.setAttr7(da.getAttr7());
		sda.setAttr8(da.getAttr8());
		sda.setAttr9(da.getAttr9());
		sda.setAttr10(da.getAttr10());
		sda.setAttr11(da.getAttr11());
		sda.setAttr12(da.getAttr12());
		sda.setAttr13(da.getAttr13());
		sda.setAttr14(da.getAttr14());
		sda.setAttr15(da.getAttr15());
		sda.setAttr16(da.getAttr16());
		sda.setAttr17(da.getAttr17());
		sda.setAttr18(da.getAttr18());
		sda.setAttr19(da.getAttr19());
		sda.setAttr20(da.getAttr20());
		sda.setAttr21(da.getAttr21());
		sda.setAttr22(da.getAttr22());
		sda.setAttr23(da.getAttr23());
		sda.setAttr24(da.getAttr24());
		sda.setAttr25(da.getAttr25());
		sda.setAttr26(da.getAttr26());
		sda.setAttr27(da.getAttr27());
		sda.setAttr28(da.getAttr28());
		sda.setAttr29(da.getAttr29());
		sda.setAttr30(da.getAttr30());
		sda.setAttr31(da.getAttr31());
		sda.setAttr32(da.getAttr32());
		sda.setAttr33(da.getAttr33());
		sda.setAttr34(da.getAttr34());
		sda.setAttr35(da.getAttr35());
		sda.setAttr36(da.getAttr36());
		sda.setAttr37(da.getAttr37());
		sda.setAttr38(da.getAttr38());
		sda.setAttr39(da.getAttr39());
		sda.setAttr40(da.getAttr40());
		sda.setAttr41(da.getAttr41());
		sda.setAttr42(da.getAttr42());
		sda.setAttr43(da.getAttr43());
		sda.setAttr44(da.getAttr44());
		sda.setAttr45(da.getAttr45());
		sda.setAttr46(da.getAttr46());
		sda.setAttr47(da.getAttr47());
		sda.setAttr48(da.getAttr48());
		sda.setAttr49(da.getAttr49());
		sda.setAttr50(da.getAttr50());
		sda.setAttr51(da.getAttr51());
		sda.setAttr52(da.getAttr52());
		sda.setAttr53(da.getAttr53());
		sda.setAttr54(da.getAttr54());
		sda.setAttr55(da.getAttr55());
		sda.setAttr56(da.getAttr56());
		sda.setAttr57(da.getAttr57());
		sda.setAttr58(da.getAttr58());
		sda.setAttr59(da.getAttr59());
		sda.setAttr60(da.getAttr60());
		sda.setAttr61(da.getAttr61());
		sda.setAttr62(da.getAttr62());
		sda.setAttr63(da.getAttr63());
		sda.setAttr64(da.getAttr64());
		sda.setAttr65(da.getAttr65());
		sda.setAttr66(da.getAttr66());
		sda.setAttr67(da.getAttr67());
		sda.setAttr68(da.getAttr68());
		sda.setAttr69(da.getAttr69());
		sda.setAttr70(da.getAttr70());
		sda.setAttr71(da.getAttr71());
		sda.setAttr72(da.getAttr72());
		sda.setAttr73(da.getAttr73());
		sda.setAttr74(da.getAttr74());
		sda.setAttr75(da.getAttr75());
		sda.setAttr76(da.getAttr76());
		sda.setAttr77(da.getAttr77());
		sda.setAttr78(da.getAttr78());
		sda.setAttr79(da.getAttr79());
		sda.setAttr80(da.getAttr80());
		sda.setAttr81(da.getAttr81());
		sda.setAttr82(da.getAttr82());
		sda.setAttr83(da.getAttr83());
		sda.setAttr84(da.getAttr84());
		sda.setAttr85(da.getAttr85());
		sda.setAttr86(da.getAttr86());
		sda.setAttr87(da.getAttr87());
		sda.setAttr88(da.getAttr88());
		sda.setAttr89(da.getAttr89());
		sda.setAttr90(da.getAttr90());
		sda.setAttr91(da.getAttr91());
		sda.setAttr92(da.getAttr92());
		sda.setAttr93(da.getAttr93());
		sda.setAttr94(da.getAttr94());
		sda.setAttr95(da.getAttr95());
		sda.setAttr96(da.getAttr96());
		sda.setAttr97(da.getAttr97());
		sda.setAttr98(da.getAttr98());
		sda.setAttr99(da.getAttr99());
		sda.setAttr100(da.getAttr100());
		sda.setAttr101(da.getAttr101());
		sda.setAttr102(da.getAttr102());
		sda.setAttr103(da.getAttr103());
		sda.setAttr104(da.getAttr104());
		sda.setAttr105(da.getAttr105());
		sda.setAttr106(da.getAttr106());
		sda.setAttr107(da.getAttr107());
		sda.setAttr108(da.getAttr108());
		sda.setAttr109(da.getAttr109());
		sda.setAttr110(da.getAttr110());
		sda.setAttr111(da.getAttr111());
		sda.setAttr112(da.getAttr112());
		sda.setAttr113(da.getAttr113());
		sda.setAttr114(da.getAttr114());
		sda.setAttr115(da.getAttr115());
		sda.setAttr116(da.getAttr116());
		sda.setAttr117(da.getAttr117());
		sda.setAttr118(da.getAttr118());
		sda.setAttr119(da.getAttr119());
		sda.setAttr120(da.getAttr120());
		sda.setAttr121(da.getAttr121());
		sda.setAttr122(da.getAttr122());
		sda.setAttr123(da.getAttr123());
		sda.setAttr124(da.getAttr124());
		sda.setAttr125(da.getAttr125());
		sda.setAttr126(da.getAttr126());
		sda.setAttr127(da.getAttr127());
		sda.setAttr128(da.getAttr128());
		sda.setAttr129(da.getAttr129());
		sda.setAttr130(da.getAttr130());
		sda.setAttr131(da.getAttr131());
		sda.setAttr132(da.getAttr132());
		sda.setAttr133(da.getAttr133());
		sda.setAttr134(da.getAttr134());
		sda.setAttr135(da.getAttr135());
		sda.setAttr136(da.getAttr136());
		sda.setAttr137(da.getAttr137());
		sda.setAttr138(da.getAttr138());
		sda.setAttr139(da.getAttr139());
		sda.setAttr140(da.getAttr140());
		sda.setAttr141(da.getAttr141());
		sda.setAttr142(da.getAttr142());
		sda.setAttr143(da.getAttr143());
		sda.setAttr144(da.getAttr144());
		sda.setAttr145(da.getAttr145());
		sda.setAttr146(da.getAttr146());
		sda.setAttr147(da.getAttr147());
		sda.setAttr148(da.getAttr148());
		sda.setAttr149(da.getAttr149());
		sda.setAttr150(da.getAttr150());
		sda.setAttr151(da.getAttr151());
		sda.setAttr152(da.getAttr152());
		sda.setAttr153(da.getAttr153());
		sda.setAttr154(da.getAttr154());
		sda.setAttr155(da.getAttr155());
		sda.setAttr156(da.getAttr156());
		sda.setAttr157(da.getAttr157());
		sda.setAttr158(da.getAttr158());
		sda.setAttr159(da.getAttr159());
		sda.setAttr160(da.getAttr160());
		sda.setAttr161(da.getAttr161());
		sda.setAttr162(da.getAttr162());
		sda.setAttr163(da.getAttr163());
		sda.setAttr164(da.getAttr164());
		sda.setAttr165(da.getAttr165());
		sda.setAttr166(da.getAttr166());
		sda.setAttr167(da.getAttr167());
		sda.setAttr168(da.getAttr168());
		sda.setAttr169(da.getAttr169());
		sda.setAttr170(da.getAttr170());
		sda.setAttr171(da.getAttr171());
		sda.setAttr172(da.getAttr172());
		sda.setAttr173(da.getAttr173());
		sda.setAttr174(da.getAttr174());
		sda.setAttr175(da.getAttr175());
		sda.setAttr176(da.getAttr176());
		sda.setAttr177(da.getAttr177());
		sda.setAttr178(da.getAttr178());
		sda.setAttr179(da.getAttr179());
		sda.setAttr180(da.getAttr180());
		sda.setAttr181(da.getAttr181());
		sda.setAttr182(da.getAttr182());
		sda.setAttr183(da.getAttr183());
		sda.setAttr184(da.getAttr184());
		sda.setAttr185(da.getAttr185());
		sda.setAttr186(da.getAttr186());
		sda.setAttr187(da.getAttr187());
		sda.setAttr188(da.getAttr188());
		sda.setAttr189(da.getAttr189());
		sda.setAttr190(da.getAttr190());
		sda.setAttr191(da.getAttr191());
		sda.setAttr192(da.getAttr192());
		sda.setAttr193(da.getAttr193());
		sda.setAttr194(da.getAttr194());
		sda.setAttr195(da.getAttr195());
		sda.setAttr196(da.getAttr196());
		sda.setAttr197(da.getAttr197());
		sda.setAttr198(da.getAttr198());
		sda.setAttr199(da.getAttr199());
		sda.setAttr200(da.getAttr200());
		sda.setAttr201(da.getAttr201());
		sda.setAttr202(da.getAttr202());
		sda.setAttr203(da.getAttr203());
		sda.setAttr204(da.getAttr204());
		sda.setAttr205(da.getAttr205());
		sda.setAttr206(da.getAttr206());
		sda.setAttr207(da.getAttr207());
		sda.setAttr208(da.getAttr208());
		sda.setAttr209(da.getAttr209());
		sda.setAttr210(da.getAttr210());
		sda.setAttr211(da.getAttr211());
		sda.setAttr212(da.getAttr212());
		sda.setAttr213(da.getAttr213());
		sda.setAttr214(da.getAttr214());
		sda.setAttr215(da.getAttr215());
		sda.setAttr216(da.getAttr216());
		sda.setAttr217(da.getAttr217());
		sda.setAttr218(da.getAttr218());
		sda.setAttr219(da.getAttr219());
		sda.setAttr220(da.getAttr220());
		sda.setAttr221(da.getAttr221());
		sda.setAttr222(da.getAttr222());
		sda.setAttr223(da.getAttr223());
		sda.setAttr224(da.getAttr224());
		sda.setAttr225(da.getAttr225());
		sda.setAttr226(da.getAttr226());
		sda.setAttr227(da.getAttr227());
		sda.setAttr228(da.getAttr228());
		sda.setAttr229(da.getAttr229());
		sda.setAttr230(da.getAttr230());
		sda.setAttr231(da.getAttr231());
		sda.setAttr232(da.getAttr232());
		sda.setAttr233(da.getAttr233());
		sda.setAttr234(da.getAttr234());
		sda.setAttr235(da.getAttr235());
		sda.setAttr236(da.getAttr236());
		sda.setAttr237(da.getAttr237());
		sda.setAttr238(da.getAttr238());
		sda.setAttr239(da.getAttr239());
		sda.setAttr240(da.getAttr240());
		sda.setAttr241(da.getAttr241());
		sda.setAttr242(da.getAttr242());
		sda.setAttr243(da.getAttr243());
		sda.setAttr244(da.getAttr244());
		sda.setAttr245(da.getAttr245());
		sda.setAttr246(da.getAttr246());
		sda.setAttr247(da.getAttr247());
		sda.setAttr248(da.getAttr248());
		sda.setAttr249(da.getAttr249());
		sda.setAttr250(da.getAttr250());
		sda.setAttr251(da.getAttr251());
		sda.setAttr252(da.getAttr252());
		sda.setAttr253(da.getAttr253());
		sda.setAttr254(da.getAttr254());
		sda.setAttr255(da.getAttr255());
		sda.setAttr256(da.getAttr256());
		sda.setAttr257(da.getAttr257());
		sda.setAttr258(da.getAttr258());
		sda.setAttr259(da.getAttr259());
		sda.setAttr260(da.getAttr260());
		sda.setAttr261(da.getAttr261());
		sda.setAttr262(da.getAttr262());
		sda.setAttr263(da.getAttr263());
		sda.setAttr264(da.getAttr264());
		sda.setAttr265(da.getAttr265());
		sda.setAttr266(da.getAttr266());
		sda.setAttr267(da.getAttr267());
		sda.setAttr268(da.getAttr268());
		sda.setAttr269(da.getAttr269());
		sda.setAttr270(da.getAttr270());
		sda.setAttr271(da.getAttr271());
		sda.setAttr272(da.getAttr272());
		sda.setAttr273(da.getAttr273());
		sda.setAttr274(da.getAttr274());
		sda.setAttr275(da.getAttr275());
		sda.setAttr276(da.getAttr276());
		sda.setAttr277(da.getAttr277());
		sda.setAttr278(da.getAttr278());
		sda.setAttr279(da.getAttr279());
		sda.setAttr280(da.getAttr280());
		sda.setAttr281(da.getAttr281());
		sda.setAttr282(da.getAttr282());
		sda.setAttr283(da.getAttr283());
		sda.setAttr284(da.getAttr284());
		sda.setAttr285(da.getAttr285());
		sda.setAttr286(da.getAttr286());
		sda.setAttr287(da.getAttr287());
		sda.setAttr288(da.getAttr288());
		sda.setAttr289(da.getAttr289());
		sda.setAttr290(da.getAttr290());
		sda.setAttr291(da.getAttr291());
		sda.setAttr292(da.getAttr292());
		sda.setAttr293(da.getAttr293());
		sda.setAttr294(da.getAttr294());
		sda.setAttr295(da.getAttr295());
		sda.setAttr296(da.getAttr296());
		sda.setAttr297(da.getAttr297());
		sda.setAttr298(da.getAttr298());
		sda.setAttr299(da.getAttr299());
		sda.setAttr300(da.getAttr300());
		sda.setAttr301(da.getAttr301());
		sda.setAttr302(da.getAttr302());
		sda.setAttr303(da.getAttr303());
		sda.setAttr304(da.getAttr304());
		sda.setAttr305(da.getAttr305());
		sda.setAttr306(da.getAttr306());
		sda.setAttr307(da.getAttr307());
		sda.setAttr308(da.getAttr308());
		sda.setAttr309(da.getAttr309());
		sda.setAttr310(da.getAttr310());
		sda.setAttr311(da.getAttr311());
		sda.setAttr312(da.getAttr312());
		sda.setAttr313(da.getAttr313());
		sda.setAttr314(da.getAttr314());
		sda.setAttr315(da.getAttr315());
		sda.setAttr316(da.getAttr316());
		sda.setAttr317(da.getAttr317());
		sda.setAttr318(da.getAttr318());
		sda.setAttr319(da.getAttr319());
		sda.setAttr320(da.getAttr320());
		sda.setAttr321(da.getAttr321());
		sda.setAttr322(da.getAttr322());
		sda.setAttr323(da.getAttr323());
		sda.setAttr324(da.getAttr324());
		sda.setAttr325(da.getAttr325());
		sda.setAttr326(da.getAttr326());
		sda.setAttr327(da.getAttr327());
		sda.setAttr328(da.getAttr328());
		sda.setAttr329(da.getAttr329());
		sda.setAttr330(da.getAttr330());
		sda.setAttr331(da.getAttr331());
		sda.setAttr332(da.getAttr332());
		sda.setAttr333(da.getAttr333());
		sda.setAttr334(da.getAttr334());
		sda.setAttr335(da.getAttr335());
		sda.setAttr336(da.getAttr336());
		sda.setAttr337(da.getAttr337());
		sda.setAttr338(da.getAttr338());
		sda.setAttr339(da.getAttr339());
		sda.setAttr340(da.getAttr340());
		sda.setAttr341(da.getAttr341());
		sda.setAttr342(da.getAttr342());
		sda.setAttr343(da.getAttr343());
		sda.setAttr344(da.getAttr344());
		sda.setAttr345(da.getAttr345());
		sda.setAttr346(da.getAttr346());
		sda.setAttr347(da.getAttr347());
		sda.setAttr348(da.getAttr348());
		sda.setAttr349(da.getAttr349());
		sda.setAttr350(da.getAttr350());
	}
	
	private void populateSblSnapshotAssetDa(SnapshotSiebelAssetDa  sda,SiebelAssetDa da,String dfrId, boolean isSelected){
	
		sda.setHeader1(da.getHeader1()+"."+dfrId);
		sda.setHeader2(da.getHeader2());
		sda.setHeader3(da.getHeader3());
		sda.setHeader4(da.getHeader4());
		sda.setHeader5(da.getHeader5());
		sda.setHeader6(da.getHeader6());
		sda.setHeader7(da.getHeader7());
		sda.setHeader8(da.getHeader8());
		sda.setHeader9(da.getHeader9());
		sda.setHeader10(da.getHeader10());
		sda.setHeader11(da.getHeader11());
		sda.setHeader12(da.getHeader12());
		sda.setHeader13(da.getHeader13());
		sda.setHeader14(da.getHeader14());
		sda.setHeader15(da.getHeader15());
		sda.setHeader16(da.getHeader16());
		sda.setHeader17(da.getHeader17());
		sda.setHeader18(da.getHeader18());
		sda.setHeader19(da.getHeader19());
		sda.setHeader20(da.getHeader20());
		sda.setHeader21(da.getHeader21());
		sda.setHeader22(da.getHeader22());
		sda.setHeader23(da.getHeader23());
		sda.setHeader24(da.getHeader24());
		sda.setHeader25(da.getHeader25());
		sda.setHeader26(da.getHeader26());
		if(da.getHeader27()!=null){
			sda.setHeader27(da.getHeader27()+"."+dfrId);
		}else{
			sda.setHeader27(da.getHeader27());
		}
		sda.setHeader28(da.getHeader28());
		sda.setHeader29(da.getHeader29());
		sda.setHeader30(da.getHeader30());
		sda.setHeader31(da.getHeader31());
		sda.setHeader32(da.getHeader32());
		sda.setHeader33(da.getHeader33());
		sda.setHeader34(da.getHeader34());
		sda.setHeader35(da.getHeader35());
		sda.setHeader36(da.getHeader36());
		sda.setHeader37(da.getHeader37());
		sda.setHeader38(da.getHeader38());
		sda.setHeader39(da.getHeader39());
		sda.setHeader40(da.getHeader40());
		sda.setHeader41(da.getHeader41());
		sda.setHeader42(da.getHeader42());
		sda.setHeader43(da.getHeader43());
		sda.setHeader44(da.getHeader44());
		sda.setHeader45(da.getHeader45());
		sda.setHeader46(da.getHeader46());
		sda.setHeader47(da.getHeader47());
		sda.setHeader48(da.getHeader48());
		sda.setHeader49(da.getHeader49());
		sda.setHeader50(da.getHeader50());
		sda.setHeader51(da.getHeader51());
		sda.setHeader52(da.getHeader52());
		sda.setHeader53(da.getHeader53());
		sda.setHeader54(da.getHeader54());
		sda.setHeader55(da.getHeader55());
		sda.setHeader56(da.getHeader56());
		if(isSelected){
			sda.setHeader57("Y");
		}else{
			sda.setHeader57(da.getHeader57());
		}
		sda.setHeader58(da.getHeader58());
		sda.setHeader59(da.getHeader59());
		sda.setHeader60(da.getHeader60());
		sda.setHeader61(da.getHeader61());
		sda.setHeader62(da.getHeader62());
		sda.setHeader63(da.getHeader63());
		sda.setHeader64(da.getHeader64());
		sda.setHeader65(da.getHeader65());
		sda.setHeader66(da.getHeader66());

		
		sda.setAttr1(da.getAttr1());
		sda.setAttr2(da.getAttr2());
		sda.setAttr3(da.getAttr3());
		sda.setAttr4(da.getAttr4());
		sda.setAttr5(da.getAttr5());
		sda.setAttr6(da.getAttr6());
		sda.setAttr7(da.getAttr7());
		sda.setAttr8(da.getAttr8());
		sda.setAttr9(da.getAttr9());
		sda.setAttr10(da.getAttr10());
		sda.setAttr11(da.getAttr11());
		sda.setAttr12(da.getAttr12());
		sda.setAttr13(da.getAttr13());
		sda.setAttr14(da.getAttr14());
		sda.setAttr15(da.getAttr15());
		sda.setAttr16(da.getAttr16());
		sda.setAttr17(da.getAttr17());
		sda.setAttr18(da.getAttr18());
		sda.setAttr19(da.getAttr19());
		sda.setAttr20(da.getAttr20());
		sda.setAttr21(da.getAttr21());
		sda.setAttr22(da.getAttr22());
		sda.setAttr23(da.getAttr23());
		sda.setAttr24(da.getAttr24());
		sda.setAttr25(da.getAttr25());
		sda.setAttr26(da.getAttr26());
		sda.setAttr27(da.getAttr27());
		sda.setAttr28(da.getAttr28());
		sda.setAttr29(da.getAttr29());
		sda.setAttr30(da.getAttr30());
		sda.setAttr31(da.getAttr31());
		sda.setAttr32(da.getAttr32());
		sda.setAttr33(da.getAttr33());
		sda.setAttr34(da.getAttr34());
		sda.setAttr35(da.getAttr35());
		sda.setAttr36(da.getAttr36());
		sda.setAttr37(da.getAttr37());
		sda.setAttr38(da.getAttr38());
		sda.setAttr39(da.getAttr39());
		sda.setAttr40(da.getAttr40());
		sda.setAttr41(da.getAttr41());
		sda.setAttr42(da.getAttr42());
		sda.setAttr43(da.getAttr43());
		sda.setAttr44(da.getAttr44());
		sda.setAttr45(da.getAttr45());
		sda.setAttr46(da.getAttr46());
		sda.setAttr47(da.getAttr47());
		sda.setAttr48(da.getAttr48());
		sda.setAttr49(da.getAttr49());
		sda.setAttr50(da.getAttr50());
		sda.setAttr51(da.getAttr51());
		sda.setAttr52(da.getAttr52());
		sda.setAttr53(da.getAttr53());
		sda.setAttr54(da.getAttr54());
		sda.setAttr55(da.getAttr55());
		sda.setAttr56(da.getAttr56());
		sda.setAttr57(da.getAttr57());
		sda.setAttr58(da.getAttr58());
		sda.setAttr59(da.getAttr59());
		sda.setAttr60(da.getAttr60());
		sda.setAttr61(da.getAttr61());
		sda.setAttr62(da.getAttr62());
		sda.setAttr63(da.getAttr63());
		sda.setAttr64(da.getAttr64());
		sda.setAttr65(da.getAttr65());
		sda.setAttr66(da.getAttr66());
		sda.setAttr67(da.getAttr67());
		sda.setAttr68(da.getAttr68());
		sda.setAttr69(da.getAttr69());
		sda.setAttr70(da.getAttr70());
		sda.setAttr71(da.getAttr71());
		sda.setAttr72(da.getAttr72());
		sda.setAttr73(da.getAttr73());
		sda.setAttr74(da.getAttr74());
		sda.setAttr75(da.getAttr75());
		sda.setAttr76(da.getAttr76());
		sda.setAttr77(da.getAttr77());
		sda.setAttr78(da.getAttr78());
		sda.setAttr79(da.getAttr79());
		sda.setAttr80(da.getAttr80());
		sda.setAttr81(da.getAttr81());
		sda.setAttr82(da.getAttr82());
		sda.setAttr83(da.getAttr83());
		sda.setAttr84(da.getAttr84());
		sda.setAttr85(da.getAttr85());
		sda.setAttr86(da.getAttr86());
		sda.setAttr87(da.getAttr87());
		sda.setAttr88(da.getAttr88());
		sda.setAttr89(da.getAttr89());
		sda.setAttr90(da.getAttr90());
		sda.setAttr91(da.getAttr91());
		sda.setAttr92(da.getAttr92());
		sda.setAttr93(da.getAttr93());
		sda.setAttr94(da.getAttr94());
		sda.setAttr95(da.getAttr95());
		sda.setAttr96(da.getAttr96());
		sda.setAttr97(da.getAttr97());
		sda.setAttr98(da.getAttr98());
		sda.setAttr99(da.getAttr99());
		sda.setAttr100(da.getAttr100());
		sda.setAttr101(da.getAttr101());
		sda.setAttr102(da.getAttr102());
		sda.setAttr103(da.getAttr103());
		sda.setAttr104(da.getAttr104());
		sda.setAttr105(da.getAttr105());
		sda.setAttr106(da.getAttr106());
		sda.setAttr107(da.getAttr107());
		sda.setAttr108(da.getAttr108());
		sda.setAttr109(da.getAttr109());
		sda.setAttr110(da.getAttr110());
		sda.setAttr111(da.getAttr111());
		sda.setAttr112(da.getAttr112());
		sda.setAttr113(da.getAttr113());
		sda.setAttr114(da.getAttr114());
		sda.setAttr115(da.getAttr115());
		sda.setAttr116(da.getAttr116());
		sda.setAttr117(da.getAttr117());
		sda.setAttr118(da.getAttr118());
		sda.setAttr119(da.getAttr119());
		sda.setAttr120(da.getAttr120());
		sda.setAttr121(da.getAttr121());
		sda.setAttr122(da.getAttr122());
		sda.setAttr123(da.getAttr123());
		sda.setAttr124(da.getAttr124());
		sda.setAttr125(da.getAttr125());
		sda.setAttr126(da.getAttr126());
		sda.setAttr127(da.getAttr127());
		sda.setAttr128(da.getAttr128());
		sda.setAttr129(da.getAttr129());
		sda.setAttr130(da.getAttr130());
		sda.setAttr131(da.getAttr131());
		sda.setAttr132(da.getAttr132());
		sda.setAttr133(da.getAttr133());
		sda.setAttr134(da.getAttr134());
		sda.setAttr135(da.getAttr135());
		sda.setAttr136(da.getAttr136());
		sda.setAttr137(da.getAttr137());
		sda.setAttr138(da.getAttr138());
		sda.setAttr139(da.getAttr139());
		sda.setAttr140(da.getAttr140());
		sda.setAttr141(da.getAttr141());
		sda.setAttr142(da.getAttr142());
		sda.setAttr143(da.getAttr143());
		sda.setAttr144(da.getAttr144());
		sda.setAttr145(da.getAttr145());
		sda.setAttr146(da.getAttr146());
		sda.setAttr147(da.getAttr147());
		sda.setAttr148(da.getAttr148());
		sda.setAttr149(da.getAttr149());
		sda.setAttr150(da.getAttr150());
		sda.setAttr151(da.getAttr151());
		sda.setAttr152(da.getAttr152());
		sda.setAttr153(da.getAttr153());
		sda.setAttr154(da.getAttr154());
		sda.setAttr155(da.getAttr155());
		sda.setAttr156(da.getAttr156());
		sda.setAttr157(da.getAttr157());
		sda.setAttr158(da.getAttr158());
		sda.setAttr159(da.getAttr159());
		sda.setAttr160(da.getAttr160());
		sda.setAttr161(da.getAttr161());
		sda.setAttr162(da.getAttr162());
		sda.setAttr163(da.getAttr163());
		sda.setAttr164(da.getAttr164());
		sda.setAttr165(da.getAttr165());
		sda.setAttr166(da.getAttr166());
		sda.setAttr167(da.getAttr167());
		sda.setAttr168(da.getAttr168());
		sda.setAttr169(da.getAttr169());
		sda.setAttr170(da.getAttr170());
		sda.setAttr171(da.getAttr171());
		sda.setAttr172(da.getAttr172());
		sda.setAttr173(da.getAttr173());
		sda.setAttr174(da.getAttr174());
		sda.setAttr175(da.getAttr175());
		sda.setAttr176(da.getAttr176());
		sda.setAttr177(da.getAttr177());
		sda.setAttr178(da.getAttr178());
		sda.setAttr179(da.getAttr179());
		sda.setAttr180(da.getAttr180());
		sda.setAttr181(da.getAttr181());
		sda.setAttr182(da.getAttr182());
		sda.setAttr183(da.getAttr183());
		sda.setAttr184(da.getAttr184());
		sda.setAttr185(da.getAttr185());
		sda.setAttr186(da.getAttr186());
		sda.setAttr187(da.getAttr187());
		sda.setAttr188(da.getAttr188());
		sda.setAttr189(da.getAttr189());
		sda.setAttr190(da.getAttr190());
		sda.setAttr191(da.getAttr191());
		sda.setAttr192(da.getAttr192());
		sda.setAttr193(da.getAttr193());
		sda.setAttr194(da.getAttr194());
		sda.setAttr195(da.getAttr195());
		sda.setAttr196(da.getAttr196());
		sda.setAttr197(da.getAttr197());
		sda.setAttr198(da.getAttr198());
		sda.setAttr199(da.getAttr199());
		sda.setAttr200(da.getAttr200());
		sda.setAttr201(da.getAttr201());
		sda.setAttr202(da.getAttr202());
		sda.setAttr203(da.getAttr203());
		sda.setAttr204(da.getAttr204());
		sda.setAttr205(da.getAttr205());
		sda.setAttr206(da.getAttr206());
		sda.setAttr207(da.getAttr207());
		sda.setAttr208(da.getAttr208());
		sda.setAttr209(da.getAttr209());
		sda.setAttr210(da.getAttr210());
		sda.setAttr211(da.getAttr211());
		sda.setAttr212(da.getAttr212());
		sda.setAttr213(da.getAttr213());
		sda.setAttr214(da.getAttr214());
		sda.setAttr215(da.getAttr215());
		sda.setAttr216(da.getAttr216());
		sda.setAttr217(da.getAttr217());
		sda.setAttr218(da.getAttr218());
		sda.setAttr219(da.getAttr219());
		sda.setAttr220(da.getAttr220());
		sda.setAttr221(da.getAttr221());
		sda.setAttr222(da.getAttr222());
		sda.setAttr223(da.getAttr223());
		sda.setAttr224(da.getAttr224());
		sda.setAttr225(da.getAttr225());
		sda.setAttr226(da.getAttr226());
		sda.setAttr227(da.getAttr227());
		sda.setAttr228(da.getAttr228());
		sda.setAttr229(da.getAttr229());
		sda.setAttr230(da.getAttr230());
		sda.setAttr231(da.getAttr231());
		sda.setAttr232(da.getAttr232());
		sda.setAttr233(da.getAttr233());
		sda.setAttr234(da.getAttr234());
		sda.setAttr235(da.getAttr235());
		sda.setAttr236(da.getAttr236());
		sda.setAttr237(da.getAttr237());
		sda.setAttr238(da.getAttr238());
		sda.setAttr239(da.getAttr239());
		sda.setAttr240(da.getAttr240());
		sda.setAttr241(da.getAttr241());
		sda.setAttr242(da.getAttr242());
		sda.setAttr243(da.getAttr243());
		sda.setAttr244(da.getAttr244());
		sda.setAttr245(da.getAttr245());
		sda.setAttr246(da.getAttr246());
		sda.setAttr247(da.getAttr247());
		sda.setAttr248(da.getAttr248());
		sda.setAttr249(da.getAttr249());
		sda.setAttr250(da.getAttr250());
		sda.setAttr251(da.getAttr251());
		sda.setAttr252(da.getAttr252());
		sda.setAttr253(da.getAttr253());
		sda.setAttr254(da.getAttr254());
		sda.setAttr255(da.getAttr255());
		sda.setAttr256(da.getAttr256());
		sda.setAttr257(da.getAttr257());
		sda.setAttr258(da.getAttr258());
		sda.setAttr259(da.getAttr259());
		sda.setAttr260(da.getAttr260());
		sda.setAttr261(da.getAttr261());
		sda.setAttr262(da.getAttr262());
		sda.setAttr263(da.getAttr263());
		sda.setAttr264(da.getAttr264());
		sda.setAttr265(da.getAttr265());
		sda.setAttr266(da.getAttr266());
		sda.setAttr267(da.getAttr267());
		sda.setAttr268(da.getAttr268());
		sda.setAttr269(da.getAttr269());
		sda.setAttr270(da.getAttr270());
		sda.setAttr271(da.getAttr271());
		sda.setAttr272(da.getAttr272());
		sda.setAttr273(da.getAttr273());
		sda.setAttr274(da.getAttr274());
		sda.setAttr275(da.getAttr275());
		sda.setAttr276(da.getAttr276());
		sda.setAttr277(da.getAttr277());
		sda.setAttr278(da.getAttr278());
		sda.setAttr279(da.getAttr279());
		sda.setAttr280(da.getAttr280());
		sda.setAttr281(da.getAttr281());
		sda.setAttr282(da.getAttr282());
		sda.setAttr283(da.getAttr283());
		sda.setAttr284(da.getAttr284());
		sda.setAttr285(da.getAttr285());
		sda.setAttr286(da.getAttr286());
		sda.setAttr287(da.getAttr287());
		sda.setAttr288(da.getAttr288());
		sda.setAttr289(da.getAttr289());
		sda.setAttr290(da.getAttr290());
		sda.setAttr291(da.getAttr291());
		sda.setAttr292(da.getAttr292());
		sda.setAttr293(da.getAttr293());
		sda.setAttr294(da.getAttr294());
		sda.setAttr295(da.getAttr295());
		sda.setAttr296(da.getAttr296());
		sda.setAttr297(da.getAttr297());
		sda.setAttr298(da.getAttr298());
		sda.setAttr299(da.getAttr299());
		sda.setAttr300(da.getAttr300());
		sda.setAttr301(da.getAttr301());
		sda.setAttr302(da.getAttr302());
		sda.setAttr303(da.getAttr303());
		sda.setAttr304(da.getAttr304());
		sda.setAttr305(da.getAttr305());
		sda.setAttr306(da.getAttr306());
		sda.setAttr307(da.getAttr307());
		sda.setAttr308(da.getAttr308());
		sda.setAttr309(da.getAttr309());
		sda.setAttr310(da.getAttr310());
		sda.setAttr311(da.getAttr311());
		sda.setAttr312(da.getAttr312());
		sda.setAttr313(da.getAttr313());
		sda.setAttr314(da.getAttr314());
		sda.setAttr315(da.getAttr315());
		sda.setAttr316(da.getAttr316());
		sda.setAttr317(da.getAttr317());
		sda.setAttr318(da.getAttr318());
		sda.setAttr319(da.getAttr319());
		sda.setAttr320(da.getAttr320());
		sda.setAttr321(da.getAttr321());
		sda.setAttr322(da.getAttr322());
		sda.setAttr323(da.getAttr323());
		sda.setAttr324(da.getAttr324());
		sda.setAttr325(da.getAttr325());
		sda.setAttr326(da.getAttr326());
		sda.setAttr327(da.getAttr327());
		sda.setAttr328(da.getAttr328());
		sda.setAttr329(da.getAttr329());
		sda.setAttr330(da.getAttr330());
		sda.setAttr331(da.getAttr331());
		sda.setAttr332(da.getAttr332());
		sda.setAttr333(da.getAttr333());
		sda.setAttr334(da.getAttr334());
		sda.setAttr335(da.getAttr335());
		sda.setAttr336(da.getAttr336());
		sda.setAttr337(da.getAttr337());
		sda.setAttr338(da.getAttr338());
		sda.setAttr339(da.getAttr339());
		sda.setAttr340(da.getAttr340());
		sda.setAttr341(da.getAttr341());
		sda.setAttr342(da.getAttr342());
		sda.setAttr343(da.getAttr343());
		sda.setAttr344(da.getAttr344());
		sda.setAttr345(da.getAttr345());
		sda.setAttr346(da.getAttr346());
		sda.setAttr347(da.getAttr347());
		sda.setAttr348(da.getAttr348());
		sda.setAttr349(da.getAttr349());
		sda.setAttr350(da.getAttr350());
	}



	@Override
	public List<SVSoapResponse> syncSVAPI(String dfrId) {
		List<SVSoapResponse> responses = new ArrayList<>();	
		logger.info("called syncSVAPI for :"+ dfrId);
		List<SvApi> poeItems = svAPIDao.getPoeItems(dfrId);
		int count=1;
		for (SvApi poeItem : poeItems) {
			SVSoapResponse svSoapResponse = new SVSoapResponse();
			try{
				svSoapResponse.setError(false);
				logger.info("start preparing request for :"+(count++)+" PoeItem Request : "+ dfrId);
				ProcessOrderRequest processOrderRequest = new ProcessOrderRequest();
				ListOfEqxOrderEntryIo listOfEqxOrderEntryIo = new ListOfEqxOrderEntryIo();
				OrderEntryOrders orderEntryOrders =preopareOderEntry(poeItem);			
				List<SvApi> pofItems = svAPIDao.getPofItems(poeItem.getRootid());
				ListOfOrderEntryLineItems listOfOrderEntryLineItems = new ListOfOrderEntryLineItems();
				for (SvApi pofItem : pofItems) {
					OrderEntryLineItems pofOderEntryItem = prepareStaticFieldOrderItem(pofItem, false);					
					
					List<SrcServiceDaArrayRamp> pofDynamicRampItems =srcServiceDaArrayRampDao.getSrcServiceDaArrayRamp(pofItem.getRowId());
					preparePOFRampItems(pofOderEntryItem,pofDynamicRampItems );
					List<SrcServiceDaArrayEsk> pofDynamicEskItems = srcServiceDaArrayEskDao.getSrcServiceDaArrayESK(pofItem.getRowId());
					preparePOFEskItems(pofOderEntryItem,pofDynamicEskItems );
					List<SrcSAssetXa> pofDynamicAssetsItems = srcAssetXADao.getSrcAssetXA(pofItem.getId());
					preparePOFSAssetXa(pofOderEntryItem,pofDynamicAssetsItems );					
					
					listOfOrderEntryLineItems.getOrderEntryLineItems().add(pofOderEntryItem);
				}
				
				OrderEntryLineItems poeOderEntryItem = prepareStaticFieldOrderItem(poeItem, true);
				
				List<SrcServiceDaArrayRamp> poeDynamicRampItems =srcServiceDaArrayRampDao.getSrcServiceDaArrayRamp(poeItem.getRowId());
				preparePOFRampItems(poeOderEntryItem,poeDynamicRampItems );

				List<SrcServiceDaArrayEsk> poeDynamicEskItems = srcServiceDaArrayEskDao.getSrcServiceDaArrayESK(poeItem.getRowId());
				preparePOFEskItems(poeOderEntryItem,poeDynamicEskItems );

				List<SrcSAssetXa> poeDynamicAssetsItems = srcAssetXADao.getSrcAssetXA(poeItem.getId());
				preparePOFSAssetXa(poeOderEntryItem,poeDynamicAssetsItems );

				listOfOrderEntryLineItems.getOrderEntryLineItems().add(poeOderEntryItem);
				orderEntryOrders.setListOfOrderEntryLineItems(listOfOrderEntryLineItems);
				listOfEqxOrderEntryIo.setOrderEntryOrders(orderEntryOrders);
				processOrderRequest.setListOfEqxOrderEntryIo(listOfEqxOrderEntryIo);
				logger.info("End preparing request for :"+(count++)+" PoeItem Request : "+ dfrId);
				//printRequest(processOrderRequest);
				logger.info("Pused Request into the SV SOAP  Kafka Topic.>>>> "+processOrderRequest);
				ProcessOrderResponse  response = null;// svSoapClient.processOrder(processOrderRequest);
				svSoapResponse.setProcessOrderResponse(response);
				svSoapResponse.setMessage("");
			}catch(Exception ex){
				svSoapResponse.setError(true);
				svSoapResponse.setProcessOrderResponse(null);
				svSoapResponse.setMessage(ex.getMessage());
				logger.error("unable to prepare and push the request:",ex);
			}
			responses.add(svSoapResponse);
		}
		return responses;
	}

	private final static QName _ProcessOrderRequest_QNAME = new QName("http://tresoap.intecbilling.com/2.0/AFS-Inbound", "ProcessOrderRequest");
	private void printRequest(ProcessOrderRequest processOrderRequest) {
		try{
			JAXBElement<ProcessOrderRequest> request = new JAXBElement<ProcessOrderRequest>(_ProcessOrderRequest_QNAME, ProcessOrderRequest.class, null, processOrderRequest); 
			JAXBContext jc = JAXBContext.newInstance(ProcessOrderRequest.class);
			Marshaller marshaller = jc.createMarshaller();
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(request, stringWriter );
			logger.info(stringWriter.toString());			
		
		}catch(Exception ex){
			logger.error("unable to print request: ",ex);
		}
	}

	private void preparePOFSAssetXa(OrderEntryLineItems pofOderEntryItem, List<SrcSAssetXa> pofDynamicAssetsItems) {
		ListOfOrderItemXa listOfOrderItemXa = new ListOfOrderItemXa();
		for(SrcSAssetXa srcServ : pofDynamicAssetsItems) {
			OrderItemXa e = new OrderItemXa();
			e.setName(srcServ.getAttrName());
			e.setValue(srcServ.getCharVal());
			listOfOrderItemXa.getOrderItemXa().add(e);
		}
		pofOderEntryItem.setListOfOrderItemXa(listOfOrderItemXa);
	}



	private void preparePOFEskItems(OrderEntryLineItems pofOderEntryItem,
			List<SrcServiceDaArrayEsk> pofDynamicEskItems) {
		ListOfESKPricingTier listOfESKPricingTier = new ListOfESKPricingTier();
		
		for(SrcServiceDaArrayEsk srcServ : pofDynamicEskItems) {
			ESKPricingTier e = new ESKPricingTier();
			e.setEQXLowerTier(srcServ.getIndex1Value()); 
			e.setEQXUpperTier(srcServ.getIndex2Value());
			e.setEQXTierPrice(srcServ.getResult1Value());
			listOfESKPricingTier.getESKPricingTier().add(e );
		}
		
		pofOderEntryItem.setListOfESKPricingTier(listOfESKPricingTier);
		
	}


	private void preparePOFRampItems(OrderEntryLineItems pofOderEntryItem,
			List<SrcServiceDaArrayRamp> pofDynamicRampItems) {
		ListOfOrderItemRamp listOfOrderItemRamp = new ListOfOrderItemRamp();
		for(SrcServiceDaArrayRamp srcServ : pofDynamicRampItems) {
			OrderItemRamp orderItemRamp = new OrderItemRamp();
			orderItemRamp.setEQXStartDate(srcServ.getIndex1Value());
			orderItemRamp.setEQXEndDate(srcServ.getIndex2Value());
			orderItemRamp.setEQXAmount(srcServ.getResult1Value());
			orderItemRamp.setEQXKVA(srcServ.getResult2Value());
			orderItemRamp.setEQXPercentage(srcServ.getResult5Value());
			orderItemRamp.setEQXDuration(srcServ.getResult6Value());
			
			listOfOrderItemRamp.getOrderItemRamp().add(orderItemRamp);
		}
		pofOderEntryItem.setListOfOrderItemRamp(listOfOrderItemRamp);
	}




	private OrderEntryLineItems prepareStaticFieldOrderItem(SvApi pofItem, boolean isPOE) {
		OrderEntryLineItems pofOderEntryItem = new OrderEntryLineItems();

		pofOderEntryItem.setActionCode(pofItem.getActioncode());
		pofOderEntryItem.setAssetId(pofItem.getAssetId());
		pofOderEntryItem.setBillableFlag(pofItem.getBillflag());
		pofOderEntryItem.setEQXContactUCID(pofItem.getContactucid());
		pofOderEntryItem.setCurrencyCode(pofItem.getCurrencycode());
		pofOderEntryItem.setEQXBillingCommencementDate(pofItem.getBillingdate());
		pofOderEntryItem.setEQXInstallDate(pofItem.getInstalldate());
		pofOderEntryItem.setEQXIBXName(pofItem.getIbxname());
		pofOderEntryItem.setId(pofItem.getId());
		pofOderEntryItem.setLineNumber(pofItem.getLineno());
		pofOderEntryItem.setLineStatus(pofItem.getLinestatus());
		pofOderEntryItem.setProductDescription(pofItem.getProductdescription());
		pofOderEntryItem.setPartNumber(pofItem.getPartnumber());
		pofOderEntryItem.setProductId(pofItem.getProductid());
		pofOderEntryItem.setRootOrderItemId(pofItem.getRootid());
		pofOderEntryItem.setEQXSerialNumber(pofItem.getSerialnum());
		pofOderEntryItem.setEQXProductFamily(pofItem.getProductfamily());
		pofOderEntryItem.setUnitOfMeasure(pofItem.getUnitofmeasure());
		pofOderEntryItem.setEQXSalesQuantity(pofItem.getSalesquantity());
		// pofOderEntryItem.setEQXBillReplayFlag(pofItem.getbi // TODO NA
		pofOderEntryItem.setEQXSequenceNumber(pofItem.getSequencenumber());
		// pofOderEntryItem.setEQXEligibleForBilling(pofItem.getExclinterconflag() // TODO NA
		if(isPOE){
			pofOderEntryItem.setParentOrderItemId(pofItem.getParentid());
			pofOderEntryItem.setEQXDeferredRevenueCode(pofItem.getDefrevcode());
			pofOderEntryItem.setEQXEarnedRevenueCode(pofItem.getEarnrevcode());
			pofOderEntryItem.setEQXProductCode(pofItem.getProductcode());
			pofOderEntryItem.setEQXRevRecRules(pofItem.getRevrecrules());
		}
		return pofOderEntryItem;
	}



	private OrderEntryOrders preopareOderEntry(SvApi poeItem) {
		
		OrderEntryOrders orderEntryOrders = new OrderEntryOrders();
		orderEntryOrders.setEQXAccountUCID(poeItem.getUcmId());
		//orderEntryOrders.setEQXSalesEngineer(poeItem.getEarnrevcode());
		//orderEntryOrders.setEQXBookedDate(poeItem.get);
		orderEntryOrders.setEQXOrderNumber(poeItem.getOrderno());
		orderEntryOrders.setEQXOrderSource(poeItem.getOrdersource());
		orderEntryOrders.setOrderStatus(poeItem.getOrderstatus());
		orderEntryOrders.setOrderDate(poeItem.getOrderdate());
		orderEntryOrders.setOrderType(poeItem.getOrdertype());
		orderEntryOrders.setEQXOrderSubType(poeItem.getOrdersubtype());
		//orderEntryOrders.setEQXMigrationSubType(poeItem.get);
		orderEntryOrders.setEQXSalesOrderInitialTerm(poeItem.getInitialterms());
		orderEntryOrders.setEQXSalesOrderRenewalTerm(poeItem.getRenewalterms());
		orderEntryOrders.setEQXCustomerNoticePeriod(poeItem.getNoticeperiod());
		orderEntryOrders.setEQXMaxPriceIncreasePercentage(poeItem.getMaxpipercent());
		orderEntryOrders.setEQXMaxPriceIncreaseValue(poeItem.getMaxpival());
		orderEntryOrders.setEQXPriceIncreaseNoticePeriod(poeItem.getPinoticeperiod());
		orderEntryOrders.setEQXBillingAgreementNumber(poeItem.getBillagrno());
	//	orderEntryOrders.setContactFirstName(poeItem)
	//	orderEntryOrders.setContactLastName
		orderEntryOrders.setRequestOrigin("Recon2");
		//orderEntryOrders.setEQXOrderComment
		//orderEntryOrders.setEQXOriginalOrderNumber
		orderEntryOrders.setEQXFirstPIAppAfter(poeItem.getFirstpiappafter());
		orderEntryOrders.setEQXExclInterconnectionFlag(poeItem.getExclinterconflag());
		orderEntryOrders.setEQXOneTimePIUpcharge(poeItem.getOnetimepiupcharge());
		orderEntryOrders.setEQXRegionalDefaultCalc(poeItem.getRegiondefaultcalc());
		orderEntryOrders.setEQXAllProducts(poeItem.getAllprod());
		orderEntryOrders.setEQXOtherProducts(poeItem.getOtherproducts());
		orderEntryOrders.setEQXPower(poeItem.getPower());
		orderEntryOrders.setEQXSmartHandsPlan(poeItem.getSmarthandsplan());
		orderEntryOrders.setEQXSpace(poeItem.getSpace());
		orderEntryOrders.setEQXInterconnection(poeItem.getInterconn());	  
		orderEntryOrders.setEQXAllProductsRenewal(poeItem.getAllprodren());
		orderEntryOrders.setEQXOtherProductsRenewal(poeItem.getOtherprodren());
		orderEntryOrders.setEQXPowerRenewal(poeItem.getPowerren());
		orderEntryOrders.setEQXSmartHandsPlanRenewal(poeItem.getSmarthandsplanren());
		orderEntryOrders.setEQXSpaceRenewal(poeItem.getSpaceren());
		orderEntryOrders.setEQXInterconnectionRenewal(poeItem.getInterconren());
		orderEntryOrders.setEQXResale(poeItem.getResale());
		orderEntryOrders.setEQXPartnerTier(poeItem.getPartnertier());
		orderEntryOrders.setEQXResellerDiscount(poeItem.getResellerdiscount());
		orderEntryOrders.setEQXDiscountType(poeItem.getDiscounttype());
		orderEntryOrders.setEQXPartnerType(poeItem.getPartnertype());
		orderEntryOrders.setEQXPartnerSubType(poeItem.getPartnersubtype());
		orderEntryOrders.setEQXPartnerStatus(poeItem.getPartnerstatus());
		orderEntryOrders.setEQXSweepInIndicator(poeItem.getSweepind()); 
		
		return orderEntryOrders;
	}
	
	private static final String DEMARK_POINT = "Demarcation Point";
	
	private static final String CABINET = "Cabinet";

	private static final String CAGE = "Cage";

	private static final String HEADER = "HEADER";


	@Autowired
	AppOpsDartEditDfrDao appOpsDartEditDfrDao;
	
	@Autowired
	AppOppsDartAttrConfigDao dartAttrConfigDao;
	

	@Override
	public CaplogixSyncResponse syncCaplogixDfr(String dfrId) {
		CaplogixSyncResponse response= new CaplogixSyncResponse();
		try{			
			kafkaSenderService.sendCLX(new DFRKafkaMessageVO(dfrId));
			response.setError(false);
			response.setMessage(LogicConstants.SYNCINPROGRESS);
		}catch(Exception ex){
			logger.error("unable to push data into clx kafka topic:",ex);
			response.setError(true);
			response.setMessage(LogicConstants.SYNCERROR);
		}
		return response;
	}

	@Autowired
	RestTemplate consumerTemplate;
	
	private JsonObject prepareCage(String product, AssetNewVal newAsset,
			Map<String, AttributeConfig> cageAttibuteConfigMap, Map<String, AttributeConfig> cageTagConfigMap,
			Map<String, AttributeConfig> cabinetAttibuteConfigMap, Map<String, AttributeConfig> cabinetTagConfigMap
			,SnapshotSiebelAssetDa siebelAsset) throws IllegalAccessException {
		JsonObject jCage = new JsonObject();
		logger.info("=================="+product+"======================");		
		List<Object> assets = newAsset.getNotNull();			
		int count=1;
		AttributeConfig dartAttrConfig = null;
		boolean isDirtyMark=false;
		System.out.println(
				"AssetsId:"+newAsset.getHeader2()+
				" UCMID:"+newAsset.getHeader5()+
				" AvailabilityStatus:"+newAsset.getHeader18()+
				" pof:"+newAsset.getHeader49()+
				" usid:"+newAsset.getAttr1()+
				" cageType:"+newAsset.getAttr14()+
				" actualCabinet:"+newAsset.getAttr56()+
				" drawCap:"+newAsset.getAttr64()
				);
		String excludedTag =configService.getValueByKey(LogicConstants.CLX_EXCLUDED_TAG_LIST);
		if(excludedTag == null){
			excludedTag="";
		}
		for (Object o : assets) {
			try{
				String key = "";
				String value="";
				Field f = (Field)o; 

				f.setAccessible(true);
				Column column = f.getAnnotation(Column.class);
				String annotation = (column != null)?column.name() :"";
				System.out.println((count++)+"\tannotations: "+annotation + "\t. Name:"+ f.getName() +"\t. type:"+ f.getType()
				+"\t value:"+ f.get(newAsset));	
				if(f.getName().startsWith("header")){
					dartAttrConfig= cageAttibuteConfigMap.get(HEADER+"_"+f.getName().toUpperCase());
					logger.info("### ("+dartAttrConfig +")========");				
					
				} else if(annotation.startsWith("ATTR")){
					dartAttrConfig= cageAttibuteConfigMap.get(CAGE+"_"+annotation);
					logger.info("### ("+dartAttrConfig +")========");					
				} else if(StringUtils.isBlank(annotation)){
					logger.info("no annotations");
					continue;
				}
				key=dartAttrConfig!=null?dartAttrConfig.getClxTag():key;
				if(excludedTag.contains(key)){
					logger.info(key+" contains inside excluded list: "+ excludedTag);
					continue;
				}
				value = f.get(newAsset)+""; 
				String snapshotSiebelDaVal="";
				try{
					snapshotSiebelDaVal = (String)getHeaderValue(siebelAsset, f.getName(), GETTER, new Object[]{});
				}catch(Exception ex){
					logger.error("Error with "+f.getName()+" :"+siebelAsset.getDfrLineId(),ex);
				}
				if(StringUtils.isNotBlank(key) && (cageTagConfigMap.containsKey(CAGE+"_"+key)|| cageTagConfigMap.containsKey(HEADER+"_"+key)) ){
					if(null !=value && BLANK_OUT.equalsIgnoreCase(value.trim())){
						value=null;
					}
					
					if(key.equalsIgnoreCase("usid")  || key.equalsIgnoreCase("assetId") ){
						jCage.addProperty(key,value);
					}
					
					if(!key.equalsIgnoreCase("usid") && !key.equalsIgnoreCase("assetId") 
							&& value.equalsIgnoreCase(snapshotSiebelDaVal)==false){
						jCage.addProperty(key,value);
						isDirtyMark=true;
					} else if(key.equalsIgnoreCase("usid") 
							&& newAsset.isUsidTempChange()==false
							&& value.equalsIgnoreCase(snapshotSiebelDaVal)==false){
						isDirtyMark=true;
					} else if(key.equalsIgnoreCase("assetId") 
							&& newAsset.isAssetIdTempChange()==false
							&& value.equalsIgnoreCase(snapshotSiebelDaVal)==false){
						isDirtyMark=true;
					}
				}				
				
			}catch(Exception ex){
				logger.info("unable prepare assets:",ex);
			}
		}
		jCage.addProperty("isCageDirty", isDirtyMark);
		//JsonObject cabinet = prepareCabinet(product, cabi,cabinetAttibuteConfigMap,cabinetTagConfigMap);
		//jCage.add("cabinets", JCabinetArray);
		return jCage;
	}



	private JsonObject prepareCabinetOrDemarkPoint(DfrMaster dfr, String productType, AssetNewVal product,
			Map<String, AttributeConfig> productAttibuteConfigMap, Map<String, AttributeConfig> productTagConfigMap
			,SnapshotSiebelAssetDa siebelAsset, List<JsonObject> circuits) {
		AttributeConfig dartAttrConfig = null;
		JsonObject jProduct = new JsonObject();
		try{
			System.out.println(
					" product:"+productType+
					" AssetId:"+product.getHeader2()+
					" ucmid:"+product.getHeader5()+
					" availablityStatus:"+product.getHeader18()+
					" pof:"+product.getHeader49()+
					" drawCap:"+product.getAttr35()+
					" usid:"+product.getAttr45()+
					" cabinetType:"+product.getAttr43()
					);
			
			List<Object> notNullAttrs = product.getNotNull();			
			int count=1;
			String productUSId ="";
			boolean isDirtyMark=false;
			String excludedTag =configService.getValueByKey(LogicConstants.CLX_EXCLUDED_TAG_LIST);
			if(excludedTag == null){
				excludedTag="";
			}
			
			for (Object o : notNullAttrs) {
				try{
					String key = "";
					String value="";
					Field f = (Field)o; 

					f.setAccessible(true);
					Column column = f.getAnnotation(Column.class);
					String annotation = (column != null)?column.name() :"";
					System.out.println((count++)+"\tannotations: "+annotation + "\t. Name:"+ f.getName() +"\t. type:"+ f.getType()
					+"\t value:"+ f.get(product));						
					if(f.getName().startsWith("header")){
						dartAttrConfig= productAttibuteConfigMap.get(HEADER+"_"+f.getName().toUpperCase());
						logger.info("### ("+dartAttrConfig +")========");				

					} else if(annotation.startsWith("ATTR")){
						dartAttrConfig= productAttibuteConfigMap.get(productType+"_"+annotation);
						logger.info("### ("+dartAttrConfig +")========");					
					} else if(StringUtils.isBlank(annotation)){
						logger.info("no annotations");
						continue;
					}
					key=dartAttrConfig!=null?dartAttrConfig.getClxTag():key;
					
					if(excludedTag.contains(key)){
						logger.info(key+" contains inside excluded list: "+ excludedTag);
						continue;
					}
					value = f.get(product)+"";
					String snapshotSiebelDaVal="";
					try{
						snapshotSiebelDaVal = (String)getHeaderValue(siebelAsset, f.getName(), GETTER, new Object[]{});
					}catch(Exception ex){
						logger.error("Error with "+f.getName()+" :"+siebelAsset.getDfrLineId(),ex);
					}
					
					if(StringUtils.isNotBlank(key) && (productTagConfigMap.containsKey(productType+"_"+ key)|| productTagConfigMap.containsKey(HEADER+"_"+ key))){
						if(null !=value && BLANK_OUT.equalsIgnoreCase(value.trim())){
							value=null;
						}
						
						if(key.equalsIgnoreCase("usid")  || key.equalsIgnoreCase("assetId") ){
							jProduct.addProperty(key,value);
						}
						
						if(!key.equalsIgnoreCase("usid") && !key.equalsIgnoreCase("assetId") 
								&& value.equalsIgnoreCase(snapshotSiebelDaVal)==false){
							jProduct.addProperty(key,value);
							isDirtyMark=true;
						} else if(key.equalsIgnoreCase("usid") 
								&& product.isUsidTempChange()==false
								&& value.equalsIgnoreCase(snapshotSiebelDaVal)==false){
							isDirtyMark=true;
						} else if(key.equalsIgnoreCase("assetId") 
								&& product.isAssetIdTempChange()==false
								&& value.equalsIgnoreCase(snapshotSiebelDaVal)==false){
							isDirtyMark=true;
						}
					}

				}catch(Exception ex){
					logger.info("unable prepare "+productType+" attributes:",ex);
				}			
			}
			
			if(circuits!=null && circuits.isEmpty()) {
				isDirtyMark=true;
				jProduct.add("circuits", circuitListToJson(circuits));
			}
			if(StringUtils.isBlank(productUSId)){
				if("Y".equalsIgnoreCase(dfr.getIsAccountMoveDfr())) {
					jProduct.addProperty("usid", product.getHeader12());
				} else {
				jProduct.addProperty("usid", product.getAttr45());
			}
			}
			if(isDirtyMark){
				jProduct.addProperty("isCabinetDirty", "true");
			}
		}catch(Exception e){
			logger.info("unable prepare "+productType+":",e);
		}
	
		return jProduct;
	}
	
	private JsonArray circuitListToJson(List<JsonObject> circuits) {
		JsonArray arr = new JsonArray();
		for (JsonObject circuit : circuits) {
			arr.add(circuit);
		}
		return arr;
	}


	private Object getHeaderValue(Object c, String header, String method, Object[] param) {
		Object value="";//
		try {
			header=StringUtils.capitalize(header.replace("_0", "").replaceAll("_", "").toLowerCase());
			Expression expr = new Expression(c,method+header, param);
			expr.execute();
			value = expr.getValue();
			if(value!=null && !(value instanceof String)) {
				value=value.toString();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return value;
	}
	
	private Object getFieldValue(Object c, String header, String method, Object[] param) {
		Object value="";//
		try {			
			Expression expr = new Expression(c,method+header, param);
			expr.execute();
			value = expr.getValue();
			if(value!=null && !(value instanceof String)) {
				value=value.toString();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return value;
	}
	
	private Object setHeaderValue(Object c, String header, String method, Object[] param) {
		Object value="";
		try {
			//header=StringUtils.capitalize(header.replace("_0", "").replaceAll("_", "").toLowerCase());
			Expression expr = new Expression(c,method+header, param);
			expr.execute();
			value = expr.getValue();
		} catch (Exception e) {
			logger.error("",e);
		}
		return value;
	}

	@Autowired
	EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS siebelBatchService;

	@Override
	public CaplogixSyncResponse syncSiebelDfr(String dfrId) {
		logger.info("syncSiebelDfr dfrId:"+dfrId);
		CaplogixSyncResponse response= new CaplogixSyncResponse();
		try{			
			kafkaSenderService.sendSBL(new DFRKafkaMessageVO(dfrId));
			response.setError(false);
			response.setMessage(LogicConstants.SYNCINPROGRESS);
		}catch(Exception ex){
			logger.error("unable to push data into sbl kafka topic:",ex);
			response.setError(true);
			response.setMessage(LogicConstants.SYNCERROR);
		}
		return response;
	
	}

	@Override
	public CaplogixSyncResponse syncSVDfr(String dfrId) {
		logger.info("syncSVDfr dfrId:"+dfrId);
		CaplogixSyncResponse response= new CaplogixSyncResponse();
		try{			
			kafkaSenderService.sendSV(new DFRKafkaMessageVO(dfrId));
			response.setError(false);
			response.setMessage(LogicConstants.SYNCINPROGRESS);
		}catch(Exception ex){
			logger.error("unable to push data into sv kafka topic:",ex);
			response.setError(true);
			response.setMessage(LogicConstants.SYNCERROR);
		}
		return response;
	
	}

/**
 * 
 * @param rowId
 * @param eqxDartBatchLines
 * @return
 * [
	POE Update 				If NEW VAL ROW_ID is like  %-%
    POF and POE Insert 		If POE ASSET NUM like 25_% and POF_ASSET_NUM like 26_% 
    POE Insert  			If POE like 25_%
	]
 */
	private String getOperation(AssetNewVal assetNewVal, EqxDartBatchLines eqxDartBatchLines) {
		String rowId = assetNewVal.getHeader1();
		String productName = assetNewVal.getHeader20();
		//getHeader66:-IBX Reconciliation status  attr325:- Effective Date is not null.
		if(StringUtils.isNotEmpty(assetNewVal.getHeader61()) && 
				"Audited - Physically not Exist".equalsIgnoreCase(assetNewVal.getHeader61()) &&
				StringUtils.isNotEmpty(assetNewVal.getAttr327())) {
			return SBL_EQX_DELETE_POE_OPERATION;
		} else if(StringUtils.containsIgnoreCase(rowId, "-")){
			return SBL_EQX_UPDATE_OPERATION;
		} else if(StringUtils.containsIgnoreCase(eqxDartBatchLines.getEQXPOEAssetNum(), "125_")
				&& StringUtils.containsIgnoreCase(eqxDartBatchLines.getEQXPOFAssetNum(), "126_") 
				&& ("Cage".equalsIgnoreCase(productName) || 
						("Network Cable Connection".equalsIgnoreCase(productName) && 
								(null!=eqxDartBatchLines.getEQXPOFName()&& eqxDartBatchLines.getEQXPOFName().equalsIgnoreCase("Cross Connect")||
										null!=eqxDartBatchLines.getEQXPOFName() && eqxDartBatchLines.getEQXPOFName().equalsIgnoreCase("Intra-Customer Cross Connect"))) ||
						("Patch Panel".equalsIgnoreCase(productName) && 
								(null!=eqxDartBatchLines.getEQXPOFName()&& eqxDartBatchLines.getEQXPOFName().equalsIgnoreCase("Configurable Accessories")))
						)){				
			return SBL_EQX_INSERT_POF_OPERATION;
		} else if(StringUtils.containsIgnoreCase(eqxDartBatchLines.getEQXPOEAssetNum(), "125_")){
			return SBL_EQX_INSERT_POE_OPERATION;
		} 		
		return "";
	}


	@Override
	public DFRSyncResponse syncCLXDfrKafkaConsumer(String dfrId) {
		logger.info("syncCLXDfrKafkaConsumer dfrId:"+dfrId);
		DFRSyncResponse res = new DFRSyncResponse();
		res.setDfrId(dfrId);
		res.setResponseId(UUID.randomUUID().toString());
		JsonObject request = new JsonObject();
		try{
			Map<String,AttributeConfig> cageAttibuteConfigMap = new HashMap<String,AttributeConfig>();
			Map<String,AttributeConfig> cageTagConfigMap = new HashMap<String,AttributeConfig>();

			Map<String,AttributeConfig> cabinetAttibuteConfigMap = new HashMap<String,AttributeConfig>();
			Map<String,AttributeConfig> cabinetTagConfigMap = new HashMap<String,AttributeConfig>();
			
			Map<String,AttributeConfig> demarkPointAttibuteConfigMap = new HashMap<String,AttributeConfig>();
			Map<String,AttributeConfig> demarkPointTagConfigMap = new HashMap<String,AttributeConfig>();

			Map<String,List<JsonObject>> circuitMap = new HashMap<String,List<JsonObject>>();

			List<AttributeConfig> headerAttrConfigs = dartAttrConfigDao.getAttrConfigVlaueByProduct(new String[]{HEADER});

			for (AttributeConfig attributeConfig : headerAttrConfigs) {
				if(StringUtils.isBlank(attributeConfig.getClx()))
					continue;
				System.out.println(attributeConfig.getHeaderPosition()+" - "+attributeConfig.getClxTag());

				cageAttibuteConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
				cageTagConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getClxTag(), attributeConfig);

				cabinetAttibuteConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
				cabinetTagConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getClxTag(), attributeConfig);

				demarkPointAttibuteConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
				demarkPointTagConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getClxTag(), attributeConfig);

			}

			List<AttributeConfig> cageAttrConfigs = dartAttrConfigDao.getAttrConfigVlaueByProduct(new String[]{CAGE});
			for (AttributeConfig attributeConfig : cageAttrConfigs) {
				System.out.println(attributeConfig.getHeaderPosition()+" - "+attributeConfig.getClxTag());
				cageAttibuteConfigMap.put(CAGE+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
				cageTagConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getClxTag(), attributeConfig);
			}

			List<AttributeConfig> cabinetAttrConfigs = dartAttrConfigDao.getAttrConfigVlaueByProduct(new String[]{CABINET});
			for (AttributeConfig attributeConfig : cabinetAttrConfigs) {
				System.out.println(attributeConfig.getHeaderPosition()+" - "+attributeConfig.getClxTag());
				cabinetAttibuteConfigMap.put(CABINET+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
				cabinetTagConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getClxTag(), attributeConfig);
			}
			
			List<AttributeConfig> demarkPointAttrConfigs = dartAttrConfigDao.getAttrConfigVlaueByProduct(new String[]{DEMARK_POINT});
			for (AttributeConfig attributeConfig : demarkPointAttrConfigs) {
				System.out.println(attributeConfig.getHeaderPosition()+" - "+attributeConfig.getClxTag());
				demarkPointAttibuteConfigMap.put(DEMARK_POINT+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
				demarkPointTagConfigMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getClxTag(), attributeConfig);
			}

			List<AssetNewVal> cages = appOpsDartEditDfrDao.getEditedAssets(dfrId, new String[]{CAGE});
			List<AssetNewVal> cabinets = appOpsDartEditDfrDao.getEditedAssets(dfrId, new String[]{CABINET});
			List<AssetNewVal> demarkPoints = appOpsDartEditDfrDao.getEditedAssets(dfrId, new String[]{DEMARK_POINT});
			List<AssetNewVal> circuits = appOpsDartEditDfrDao.getEditedAssets(dfrId, new String[]{CacheConstant.AC_CIRCUIT
					,CacheConstant.DC_CIRCUIT});
			
			circuitMap=prepareCircuits(circuits);
			
			if((cages!=null && cages.isEmpty()==false) || (cabinets!=null && cabinets.isEmpty()==false)
					|| (demarkPoints!=null && demarkPoints.isEmpty()==false)
					|| (circuitMap!=null && circuitMap.isEmpty()==false)){
				DfrMaster dfrMaster = appOpsDartEditDfrDao.getDfrMaster(dfrId, "join");
				Map<String, JsonObject> cageMap = new HashMap<String, JsonObject>();
				request = new JsonObject();
				request.addProperty("dartRequestId", dfrId);
				request.addProperty("requestBy", dfrMaster.getCreatedBy());
				request.addProperty("approvedBy", dfrMaster.getRegion()+" CLX");

				for (AssetNewVal cage : cages) {
					System.out.println(cage);
					System.out.println(
							"AssetsId:"+cage.getHeader2()+
							" UCMID:"+cage.getHeader5()+
							" AvailabilityStatus:"+cage.getHeader18()+
							" pof:"+cage.getHeader49()+
							" usid:"+cage.getAttr1()+
							" cageType:"+cage.getAttr14()+
							" actualCabinet:"+cage.getAttr56()+
							" drawCap:"+cage.getAttr64()
							);
					//TODO: CLX - are not null in asset val object.
					AttributeConfig usidAttr = cageTagConfigMap.get(CAGE+"_usid");				
					String cageUSID = (String)getHeaderValue(cage, usidAttr.getHeaderPosition(),GETTER, new Object[0]);
					String cageAssetId= cage.getHeader2();
					SnapshotSiebelAssetDa siebelAsset = null; 
					List<SnapshotSiebelAssetDa> sibelAssets = appOpsInitiateDFRDao.getSnapshotSibelAssetDaByRowId(cage.getHeader1());
					if(sibelAssets!=null && sibelAssets.isEmpty()==false){
						siebelAsset = sibelAssets.get(0);
					}
					
					if(siebelAsset!=null && StringUtils.isBlank(cageUSID)){
						cage.setUsidTempChange(true);
						cageUSID = (String)getHeaderValue(siebelAsset, usidAttr.getHeaderPosition(),GETTER, new Object[0]);
						getHeaderValue(cage,usidAttr.getHeaderPosition(),SETTER, new Object[]{cageUSID});
					}
					
					if(siebelAsset!=null && StringUtils.isBlank(cageAssetId)){
						cage.setAssetIdTempChange(true);
						cageAssetId=siebelAsset.getHeader2();
						cage.setHeader2(cageAssetId);
					}
					
					JsonObject cageObj = prepareCage(CABINET,cage,cageAttibuteConfigMap,cageTagConfigMap, cabinetAttibuteConfigMap,cabinetTagConfigMap,siebelAsset);
					if(cageObj.get("isCageDirty")!=null && "true".equalsIgnoreCase(cageObj.get("isCageDirty").getAsString())){						
						cageObj.remove("isCageDirty");
						cageMap.put(cageUSID, cageObj);// attr1 - usid
					}
				}
				for (AssetNewVal cabinet : cabinets) {
					System.out.println(
							" AssetId:"+cabinet.getHeader2()+
							" ucmid:"+cabinet.getHeader5()+
							" availablityStatus:"+cabinet.getHeader18()+
							" pof:"+cabinet.getHeader49()+
							" drawCap:"+cabinet.getAttr35()+
							" usid:"+cabinet.getAttr45()+
							" cabinetType:"+cabinet.getAttr43()
							);
					AttributeConfig usidAttr = cabinetTagConfigMap.get(CABINET+"_usid");				
					String cabinetUSID = (String)getHeaderValue(cabinet, usidAttr.getHeaderPosition(),GETTER, new Object[0]);
					if("Y".equalsIgnoreCase(dfrMaster.getIsAccountMoveDfr())) {
						cabinetUSID=cabinet.getHeader12();
					}
					String cageUSID = cabinet.getHeader10();//cageUSID
					String cabinetAssetId= cabinet.getHeader2();//cabinetAssetId
					List<SnapshotSiebelAssetDa> sibelAssets = appOpsInitiateDFRDao.getSnapshotSibelAssetDaByRowId(cabinet.getHeader1());					
					SnapshotSiebelAssetDa siebelAsset = null; 
					if(sibelAssets!=null && sibelAssets.isEmpty()==false){
						siebelAsset = sibelAssets.get(0);
					}
					if(StringUtils.isBlank(cageUSID) && siebelAsset!=null) {// Cage USID
						cageUSID=siebelAsset.getHeader10();
					}
					if(StringUtils.isBlank(cabinetAssetId) && siebelAsset!=null ) {// Cabinet assetId
						cabinetAssetId=siebelAsset.getHeader2();
						cabinet.setHeader2(cabinetAssetId);
						cabinet.setAssetIdTempChange(true);						
					}
					if(StringUtils.isBlank(cabinetUSID)){
						if(siebelAsset!=null ){
							cabinetUSID = (String)getHeaderValue(siebelAsset, usidAttr.getHeaderPosition(),GETTER, new Object[0]);
							cabinet.setUsidTempChange(true);
							getHeaderValue(cabinet,usidAttr.getHeaderPosition(),SETTER, new Object[]{cabinetUSID});
							
						}else {
							throw new Exception(cabinet.getHeader1()+ " cabinet not found in SnapshotSiebelAssetDa");
						}
					}
					
					JsonObject cabinetObj = prepareCabinetOrDemarkPoint(dfrMaster,CABINET,cabinet,cabinetAttibuteConfigMap,cabinetTagConfigMap,siebelAsset,circuitMap.get(cabinetUSID));
					if (cabinetObj.get("isCabinetDirty")!= null && "true".equalsIgnoreCase(cabinetObj.get("isCabinetDirty").getAsString())) {
						cabinetObj.remove("isCabinetDirty");						
						JsonObject cage = cageMap.get(cageUSID);
						if (cage != null) {
							JsonArray cabinetArr = cage.get("cabinets") == null ? new JsonArray()
									: (JsonArray) cage.get("cabinets");
							if (cage.get("cabinets") == null) {
								cabinetArr = new JsonArray();
							} else {
								cabinetArr = cage.get("cabinets").getAsJsonArray();
							}
							cabinetArr.add(cabinetObj);
							cage.add("cabinets", cabinetArr);
						} else {
							JsonObject newCage = new JsonObject();
							newCage.addProperty("usid", cageUSID);
							JsonArray cabinetArr = new JsonArray();
							cabinetArr.add(cabinetObj);
							newCage.add("cabinets", cabinetArr);
							cageMap.put(cageUSID, newCage);
						}
					}
				}
				//Demarkation points
				for (AssetNewVal demarkPoint : demarkPoints) {
					System.out.println(
							" AssetId:"+demarkPoint.getHeader2()+
							" ucmid:"+demarkPoint.getHeader5()+
							" availablityStatus:"+demarkPoint.getHeader18()+
							" pof:"+demarkPoint.getHeader49()+
							" drawCap:"+demarkPoint.getAttr35()+
							" usid:"+demarkPoint.getAttr45()+
							" cabinetType:"+demarkPoint.getAttr43()
							);
					AttributeConfig usidAttr = demarkPointTagConfigMap.get(DEMARK_POINT+"_usid");				
					String demarkPointUSID = (String)getHeaderValue(demarkPoint, usidAttr.getHeaderPosition(),GETTER, new Object[0]);
					if("Y".equalsIgnoreCase(dfrMaster.getIsAccountMoveDfr())) {
						demarkPointUSID=demarkPoint.getHeader12();
					}
					String cageUSID = demarkPoint.getHeader10();//cageUSID
					String demarkPointAssetId= demarkPoint.getHeader2();//cabinetAssetId
					List<SnapshotSiebelAssetDa> sibelAssets = appOpsInitiateDFRDao.getSnapshotSibelAssetDaByRowId(demarkPoint.getHeader1());					
					SnapshotSiebelAssetDa siebelAsset = null; 
					if(sibelAssets!=null && sibelAssets.isEmpty()==false){
						siebelAsset = sibelAssets.get(0);
					}
					if(StringUtils.isBlank(cageUSID) && siebelAsset!=null ) {// Cage USID
						cageUSID=siebelAsset.getHeader10();
					}
					if(StringUtils.isBlank(demarkPointAssetId) && siebelAsset!=null) {// demarkPoint assetId
						demarkPointAssetId=siebelAsset.getHeader2();
						demarkPoint.setHeader2(demarkPointAssetId);
						demarkPoint.setAssetIdTempChange(true);						
					}
					if(StringUtils.isBlank(demarkPointUSID)){
						if(siebelAsset!=null ){
							demarkPointUSID = (String)getHeaderValue(siebelAsset, usidAttr.getHeaderPosition(),GETTER, new Object[0]);
							demarkPoint.setUsidTempChange(true);
							getHeaderValue(demarkPoint,usidAttr.getHeaderPosition(),SETTER, new Object[]{demarkPointUSID});
							
						}else {
							throw new Exception(demarkPoint.getHeader1()+ " cabinet not found in SnapshotSiebelAssetDa");
						}
					}
				
					JsonObject demarkPointObj = prepareCabinetOrDemarkPoint(dfrMaster, DEMARK_POINT,demarkPoint,demarkPointAttibuteConfigMap,demarkPointTagConfigMap,siebelAsset,circuitMap.get(demarkPointUSID));
					if (demarkPointObj.get("isCabinetDirty")!= null && "true".equalsIgnoreCase(demarkPointObj.get("isCabinetDirty").getAsString())) {
						demarkPointObj.remove("isCabinetDirty");						
						JsonObject cage = cageMap.get(cageUSID);
						if (cage != null) {
							JsonArray demarkPointArr = cage.get("cabinets") == null ? new JsonArray()
									: (JsonArray) cage.get("cabinets");
							if (cage.get("cabinets") == null) {
								demarkPointArr = new JsonArray();
							} else {
								demarkPointArr = cage.get("cabinets").getAsJsonArray();
							}
							demarkPointArr.add(demarkPointObj);
							cage.add("cabinets", demarkPointArr);
						} else {
							JsonObject newCage = new JsonObject();
							newCage.addProperty("usid", cageUSID);
							JsonArray demarkPointArr = new JsonArray();
							demarkPointArr.add(demarkPointObj);
							newCage.add("cabinets", demarkPointArr);
							cageMap.put(cageUSID, newCage);
						}
					}
				}

				
				JsonArray jcageArr = new JsonArray();
				Boolean isAnyCageAvl = false;
				for (Entry<String, JsonObject> cageMapEntry : cageMap.entrySet()) {
					JsonObject cage = cageMapEntry.getValue();
					//JsonObject cageObject = prepareCage(CABINET,cage,cageAttibuteConfigMap,cageTagConfigMap, cabinetAttibuteConfigMap,cabinetTagConfigMap);
					jcageArr.add(cage);
					isAnyCageAvl=true;
				}

				if(isAnyCageAvl==false && circuitMap != null && circuitMap.isEmpty()==false) {
					isAnyCageAvl=true;
					for (Entry<String, List<JsonObject>> circuit : circuitMap.entrySet()) {
						String cageUSID=circuit.getKey().split("##")[0];
						String cabinetUSID=circuit.getKey().split("##")[1];
						JsonObject cage = new JsonObject();
						JsonArray cabinetJsonArr = new JsonArray();
						JsonObject cabinetJson = new JsonObject();
						cabinetJson.addProperty("usid", cabinetUSID);
						cabinetJson.add("circuits", circuitListToJson(circuit.getValue()));
						cabinetJsonArr.add(cabinetJson);
						
						if(isCageUSIDAlreadyExist(jcageArr, cageUSID,cabinetJson)) {
							System.out.println("Cage is alrdy there.");
							
						} else {
							cage.addProperty("usid", cageUSID);
							cage.add("cabinets", cabinetJsonArr);
							jcageArr.add(cage);
						}
					}
				}
				
				request.add("cages", jcageArr);
				logger.info("Request: "+ request);
				res.setRequest(request.toString());
				res.setRequestDate(new Date());
				ResponseEntity responseEntiry = null;
				try{
					if(isAnyCageAvl==false){
						res.setRequestDate(new Date());
						res.setResponseDate(new Date());
						res.setError(false);
						res.setResponse("No Cage available, no need send request.");
						res.setStatus(LogicConstants.SYNCNOTREQUIRED);
						return res;
					}
					HttpHeaders headers = new HttpHeaders();
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					headers.setContentType(MediaType.APPLICATION_JSON);
					HttpEntity entityDartRequest = new HttpEntity(request.toString(), headers);
					//static final String DART_REQ_URL = "http://qacaplogix.corp.equinix.com/CapLogixAPI/restservice/caplogix/dart/processDartRequest";
					//INSERT INTO "EQX_DART"."DART_APP_CONFIG" (CONFIG_KEY, CONFIG_VALUE) VALUES ('DART_CLX_SYNC_URL', 'http://qacaplogix.corp.equinix.com/CapLogixAPI/restservice/caplogix/dart/processDartRequest')

					String clxSyncURL = configService.getValueByKey("DART_CLX_SYNC_URL");
					responseEntiry = consumerTemplate.exchange(clxSyncURL, HttpMethod.POST, entityDartRequest, String.class);
					logger.debug("CLX Requst>>"+request +" Response:"+ responseEntiry);
					res.setResponse(responseEntiry.getStatusCode()+ ":"+ responseEntiry.getBody());
					res.setResponseDate(new Date());
					if(StringUtils.contains(responseEntiry.getBody().toString(), "success")){						
						res.setError(false);
						res.setStatus(LogicConstants.SYNCCOMPLETED);
					} else {						
						res.setStatus(LogicConstants.SYNCERROR);								
						res.setError(true);
					}					
				}catch(Exception ex){
					logger.error("unable to sync caplogix",ex);
					res.setResponseDate(new Date());
					res.setResponse(responseEntiry.getStatusCode()+ ":"+ ex.getMessage());
					res.setError(true);
				}

			} else {
				res.setResponseDate(new Date());
				res.setResponse("No cage or cabinate avilable.");
				res.setStatus(LogicConstants.SYNCNOTREQUIRED);
				res.setError(false);
			}
		}catch(Exception ex){
			logger.error("unable to sync caplogix",ex);
			res.setResponseDate(new Date());
			res.setError(false);
			res.setStatus(LogicConstants.SYNCERROR);
			
		}
		
		return res;

	}

	private boolean isCageUSIDAlreadyExist(JsonArray jcageArr, String cageUSID,JsonObject cabinetObject) {
		for (JsonElement jsonElement : jcageArr) {
			String jCageUSID=((JsonObject)jsonElement).get("usid").getAsString();
			if(jCageUSID.equalsIgnoreCase(cageUSID)) {
				JsonArray cabinetsArr = ((JsonObject)jsonElement).getAsJsonArray("cabinets");
				/*JsonObject cage = new JsonObject();
				cage.addProperty("usid", cageUSID);
				cage.add("cabinets", cabinetObject);*/
				cabinetsArr.add(cabinetObject);
				return true;
			}
		}
		return false;
	}


	private Map<String, List<JsonObject>> prepareCircuits(List<AssetNewVal> circuits) {
		Map<String,List<JsonObject>> circuitMap = new HashMap<String,List<JsonObject>>();
		for (AssetNewVal circuit : circuits) {
			System.out.println(
					" AssetId:"+circuit.getHeader2()+
					" ucmid:"+circuit.getHeader5()+							
					" CircuitUsableKva:"+circuit.getAttr29()+
					" cabinetusid:"+circuit.getHeader12()+
					" cabinetType:"+circuit.getAttr43()
					);
				
			String circuitAssetId= circuit.getHeader2();//cabinetAssetId
			String cabinetUSID= circuit.getHeader12();//cabinetUSID
			String cageUSID= circuit.getHeader10();//cageUSID
			List<SnapshotSiebelAssetDa> sibelAssets = appOpsInitiateDFRDao.getSnapshotSibelAssetDaByRowId(circuit.getHeader1());					
			SnapshotSiebelAssetDa siebelAsset = null; 
			if(sibelAssets!=null && sibelAssets.isEmpty()==false){
				siebelAsset = sibelAssets.get(0);
				circuitAssetId=siebelAsset.getHeader2();
				cageUSID=siebelAsset.getHeader10();
				cabinetUSID=siebelAsset.getHeader12();
			}
			
			String key = cageUSID+"##"+cabinetUSID;
			JsonArray circuitArr = new JsonArray();
			if (StringUtils.isNotEmpty(circuit.getAttr29()) &&
					StringUtils.compare(circuit.getAttr29(), siebelAsset.getAttr29())!=0) {
				JsonObject circuitObj = new JsonObject();
				circuitObj.addProperty("assetId", circuitAssetId);
				circuitObj.addProperty("oldCircuitUsableKva", siebelAsset.getAttr29());
				circuitObj.addProperty("newCircuitUsableKva", circuit.getAttr29());
				circuitArr.add(circuitObj);	
				if(circuitMap.get(key)==null){
					List<JsonObject> c = new ArrayList<JsonObject>();
					c.add(circuitObj);
					circuitMap.put(key,c);
				} else {
					List<JsonObject> c = circuitMap.get(key);
					c.add(circuitObj);
				}
			}
		}
		return circuitMap;
		
	}


	@Override
	public DFRSyncResponse syncSBLDfrKafkaConsumer(DFRKafkaMessageVO dfr) {
		
		DFRSyncResponse res = new DFRSyncResponse();
		try{
			String dfrId=dfr.getDfrId();
			logger.info("syncSBLDfrKafkaConsumer dfrId:"+dfrId);
			List<ProductConfig> productConfigList=appOppsDartHomeService.getAllProductConfig();
			List<AssetNewVal> newValAssets = appOpsDartEditDfrDao.getEditedAssets(dfrId);	
			List<AttributeConfig> siebelHeaderConf = dartAttrConfigDao.getAttrConfigVlaueByProduct(HEADER);
			
			Map<String,ProductConfig> productConfigMap = new HashMap<String,ProductConfig>();
			for (ProductConfig productConfig : productConfigList) {
				productConfigMap.put(productConfig.getName(),productConfig);
			}
			
			Map<String,AttributeConfig> siebelHeaderAttMap = new HashMap<String,AttributeConfig>();
			Map<String,AttributeConfig> siebelTagAttMap = new HashMap<String,AttributeConfig>();
			for (AttributeConfig attributeConfig : siebelHeaderConf) {
				siebelHeaderAttMap.put(attributeConfig.getHeaderPosition(), attributeConfig);
				siebelTagAttMap.put(attributeConfig.getSiebel(), attributeConfig);
			}

			List<AttributeConfig> siebelAttrConf = dartAttrConfigDao.getOnlyAttrConfigVlaueSiebel();
			Map<String,AttributeConfig> siebelAttMap = new HashMap<String,AttributeConfig>();
			for (AttributeConfig attributeConfig : siebelAttrConf) {
				siebelAttMap.put(attributeConfig.getProduct()+"_"+attributeConfig.getHeaderPosition(), attributeConfig);
			}
			String mandatoryHeaders=configService.getValueByKey("SIEBEL_SOAP_MANDATORY_HEADERS");//"POEAssetNum,CustUCMId,IBX,UnqSpaceIdVal,POEName,POFAssetNum,POFName,CageUniqueSpaceId";
			String mandatoryAttribute="Asset UCM Id";
			UpsertSpcDARTSpcBatchInput upsertSpcDARTSpcBatchInput = new UpsertSpcDARTSpcBatchInput();
			if(StringUtils.isNotBlank(dfr.getDfrRequestId())){
				upsertSpcDARTSpcBatchInput.setMessageId(dfr.getDfrRequestId());
			}
			
			DfrMaster dfrMaster= appOpsDartEditDfrService.getDfrMasterById(dfrId);
			ListOfEqxDartBatchIo listOfEqxDartBatchIo = new ListOfEqxDartBatchIo();					
			EqxDartBatch eqxDartBatch = new EqxDartBatch();
			//eqxDartBatch.setEQXBatchStatus("Not Started");
			eqxDartBatch.setEQXDFRID(dfrId);
			eqxDartBatch.setEQXLoginName(dfrMaster.getCreatedBy());//TODO: USERNAME
			ListOfEqxDartBatchLines listOfEqxDartBatchLines = new ListOfEqxDartBatchLines();
			AttributeConfig attrConfig = null; 
			
			boolean isDirty = false;
			for (AssetNewVal assetNewVal : newValAssets) {
				String operation=StringUtils.containsIgnoreCase( assetNewVal.getHeader1(),"-")?SBL_EQX_UPDATE_OPERATION:StringUtils.EMPTY;
				String productName=assetNewVal.getHeader20();
				Integer lineSequenceNumber = productConfigMap.get(productName).getSequence();
				EqxDartBatchLines eqxDartBatchLines = new EqxDartBatchLines();
				boolean isMandotryAttrubute=false;
				SnapshotSiebelAssetDa siebelAsset = null;
				List<SnapshotSiebelAssetDa> sibelAssets = appOpsInitiateDFRDao.getSnapshotSibelAssetDaByRowId(assetNewVal.getHeader1());
				if(sibelAssets!=null && sibelAssets.isEmpty()==false){
					siebelAsset = sibelAssets.get(0);
				} else{
					logger.error("Asset Not found in siebel for :"+assetNewVal.getHeader1());
				}
				List<Object> fields = null;
				boolean isMandatoryField=false;
				try {
					fields = assetNewVal.getNotNull();
				} catch (IllegalAccessException ex) {
					logger.error("unable to get not null values from new assetval",ex);
				}
				int count=1;
				eqxDartBatchLines.setEQXDFRItemId(assetNewVal.getDfrLineId());
				
				boolean isAnyAttrChanged=false;
				ListOfEqxDartBatchLineXa listOfEqxDartBatchLineXa = new ListOfEqxDartBatchLineXa();
				StringBuffer priceChangeAttr = new StringBuffer();
				for (Object field : fields) {
					try{

						Field f = (Field)field;
						f.setAccessible(true);
						Column column = f.getAnnotation(Column.class);
						String annotation = (column != null)?column.name() :"";							
						logger.info((count++)+"\tannotations: "+annotation + "\t. Name:"+ f.getName() +"\t. type:"+ f.getType()+"\t value:"+ f.get(assetNewVal));
						EqxDartBatchLineXa eqxDartBatchLineXa =null;
						if(f.getName().startsWith("header") || DartConstants.HEADER_TO_ATTR_MAP.containsKey(f.getName().toUpperCase())){
							if(DartConstants.HEADER_TO_ATTR_MAP.containsKey(f.getName().toUpperCase())){
								attrConfig = siebelHeaderAttMap.get(DartConstants.HEADER_TO_ATTR_MAP.get(f.getName().toUpperCase()));	
							}else{
								attrConfig = siebelHeaderAttMap.get(f.getName().toUpperCase());	
							}
																		
							if(attrConfig!=null && StringUtils.isNotBlank(attrConfig.getSiebel()) && 
									mandatoryHeaders.contains(attrConfig.getSiebel())){
								logger.info("### MANADATORY HEADER ###");										
								if(operation.equalsIgnoreCase(SBL_EQX_UPDATE_OPERATION)){									
									String newAssetVal =  getFieldValue(f.get(assetNewVal));								
									String snapshotSiebelDaVal = (String)getHeaderValue(siebelAsset, f.getName(), GETTER, new Object[]{});
									logger.info("\tannotations: "+annotation + "\t. Name:"+ f.getName() 
											+"newAssetVal:"+newAssetVal +"snapshotSiebelDaVal:"+snapshotSiebelDaVal);
									if(newAssetVal!=null && newAssetVal.equals(snapshotSiebelDaVal)){
										logger.info("SBL Ignore,because it has Same Value in both table:"
												+ "\tannotations: "+annotation + "\t. Name:"+ f.getName() 
												+"newAssetVal:"+newAssetVal
												+"snapshotSiebelDaVal:"+snapshotSiebelDaVal);
									}else{
										if(!StringUtils.contains("ROW_ID,NAME,DFR_ID,DFR_LINE_ID",annotation)){
											isMandatoryField=true;
										}
									}
									
								}else{
									if(!StringUtils.contains("ROW_ID,NAME,DFR_ID,DFR_LINE_ID",annotation)){
										isMandatoryField=true;
									}
								}
								setHeaderValue(eqxDartBatchLines,"EQX"+attrConfig.getSiebel(),SETTER, new Object[]{f.get(assetNewVal)});
								
								
							} else {
								logger.info("### HEADER >> ATTRBUTE ###");
								try{
									if(attrConfig!=null && StringUtils.isNotBlank(attrConfig.getSiebel())){
										if(attrConfig.getAttrName().equals("Reason Code")) { //TODO: add attr/header thod we want skip.
											continue;
										}
										if(operation.equalsIgnoreCase(StringUtils.EMPTY))
										isAnyAttrChanged=true;
										eqxDartBatchLineXa = new EqxDartBatchLineXa();
										eqxDartBatchLineXa.setEQXAttributeName(attrConfig.getAttrName());
										eqxDartBatchLineXa.setEQXAttributeType(attrConfig.getDataType());
										eqxDartBatchLineXa.setEQXSyncToAsset("Y");
										eqxDartBatchLineXa.setEQXXASequenceNumber(attrConfig.getSiebelSequence()+"");
										if(mandatoryAttribute.equalsIgnoreCase(attrConfig.getAttrName())){
											isMandotryAttrubute=true;
										}
										if("Date".equalsIgnoreCase(attrConfig.getDataType())){												
											try{
												eqxDartBatchLineXa.setEQXAttributeValue(((Date)f.get(assetNewVal)).toString());
											}catch(Exception ex){
												eqxDartBatchLineXa.setEQXAttributeValue(f.get(assetNewVal).toString());
											}
										}else {
											String fValue = getFieldValue(f.get(assetNewVal));
											eqxDartBatchLineXa.setEQXAttributeValue(fValue);
										}
									} else {
										logger.info("### HEADER >> ATTRBUTE ### not required. siebel tag is null");
									}
								}catch(Exception ex){
									logger.error("Unable to ### HEADER >> ATTRBUTE ###: ",ex);
								}

							}
						} else if(f.getName().startsWith("attr")){
							logger.info("### ATTRBUTE >> ATTRBUTE ###");
							try{
								if(operation.equalsIgnoreCase(StringUtils.EMPTY))
									isAnyAttrChanged=true;
								
								if(mandatoryAttribute.equalsIgnoreCase(attrConfig.getAttrName())){
									isMandotryAttrubute=true;
								}
								
								productName = (String)getHeaderValue(assetNewVal, "header20",GETTER, new Object[0]);
								attrConfig = siebelAttMap.get(productName+"_"+annotation);
								if(attrConfig !=null) {
									eqxDartBatchLineXa = new EqxDartBatchLineXa();										

									eqxDartBatchLineXa.setEQXAttributeName(attrConfig.getAttrName());
									eqxDartBatchLineXa.setEQXAttributeType(attrConfig.getDataType());
									eqxDartBatchLineXa.setEQXXASequenceNumber(attrConfig.getSiebelSequence()+"");
									if("Date".equalsIgnoreCase(attrConfig.getDataType())){												
										eqxDartBatchLineXa.setEQXAttributeValue(((Date)f.get(assetNewVal)).toString());
									}else {
										String fValue = getFieldValue(f.get(assetNewVal));
										eqxDartBatchLineXa.setEQXAttributeValue(fValue);
									}

									if("Y".equalsIgnoreCase(attrConfig.getPofAttr())){
										eqxDartBatchLineXa.setEQXSyncToPOF("Y");
									}
								}
							}catch(Exception ex){
								logger.error("### ATTRBUTE >> ATTRBUTE ###",ex);
							}

						}
						if(eqxDartBatchLineXa != null){
							if(operation.equalsIgnoreCase(SBL_EQX_UPDATE_OPERATION)){								
								String newAssetVal = eqxDartBatchLineXa.getEQXAttributeValue();								
								String snapshotSiebelDaVal = (String)getHeaderValue(siebelAsset, f.getName(), GETTER, new Object[]{});
								logger.info("\tannotations: "+annotation + "\t. Name:"+ f.getName() 
										+"newAssetVal:"+newAssetVal +"snapshotSiebelDaVal:"+snapshotSiebelDaVal);								
								
								if(newAssetVal!=null && newAssetVal.equals(snapshotSiebelDaVal)){
									logger.info("SBL Ignore,because it has Same Value in both table:"
											+ "\tannotations: "+annotation + "\t. Name:"+ f.getName() 
											+"newAssetVal:"+newAssetVal
											+"snapshotSiebelDaVal:"+snapshotSiebelDaVal);
								}else{
									listOfEqxDartBatchLineXa.getEqxDartBatchLineXa().add(eqxDartBatchLineXa );
									isAnyAttrChanged=true;
									if("Y".equalsIgnoreCase(attrConfig.getPricingAttributeFlg())){
										if(priceChangeAttr.length()==0){
											priceChangeAttr.append(attrConfig.getAttrName());
										} else {
											priceChangeAttr.append(",").append(attrConfig.getAttrName());
										}
									}
								}
								
							} else {
								listOfEqxDartBatchLineXa.getEqxDartBatchLineXa().add(eqxDartBatchLineXa );
							}
						}
					}catch (Exception e) {
						logger.error("",e);
					}
				}
				/*
				if(isAnyAttrChanged && isMandotryAttrubute == false && (!siebelAsset.getHeader4().equalsIgnoreCase(assetNewVal.getHeader4()))){
					EqxDartBatchLineXa eqxDartBatchLineXa = new EqxDartBatchLineXa();
					eqxDartBatchLineXa.setEQXAttributeName("Asset UCM Id");
					eqxDartBatchLineXa.setEQXAttributeType("Text");
					eqxDartBatchLineXa.setEQXSyncToAsset("Y");
					eqxDartBatchLineXa.setEQXAttributeValue(siebelAsset.getHeader4());
					eqxDartBatchLineXa.setEQXXASequenceNumber(attrConfig.getSiebelSequence()+"");
					listOfEqxDartBatchLineXa.getEqxDartBatchLineXa().add(eqxDartBatchLineXa );
				}*/
				
				for (String mandatoryHeader : mandatoryHeaders.split(",")) {
					try{

						String value = (String) getFieldValue(eqxDartBatchLines, "EQX"+mandatoryHeader, GETTER, new Object[0]);
						if(StringUtils.isBlank(value)){							
							AttributeConfig siebelAttr =  siebelTagAttMap.get(mandatoryHeader);
							value = (String) getHeaderValue(siebelAsset, siebelAttr.getHeaderPosition(), GETTER, new Object[0]);
							String fValue = getFieldValue(value);
							setHeaderValue(eqxDartBatchLines,"EQX"+mandatoryHeader,SETTER, new Object[]{fValue});
						}

					}catch(Exception ex){
						logger.error("Issue with mandatory:"+mandatoryHeader,ex);
					}
				}
				
				if(attrConfig!=null && attrConfig.getSiebelSequence()!=null) {
					eqxDartBatchLines.setEQXLineSequenceNumber(lineSequenceNumber.toString());
				}
				
				eqxDartBatchLines.setEQXGenAttrib7(priceChangeAttr.toString());
				if(StringUtils.isEmpty(assetNewVal.getHeader66())) {
					assetNewVal.setHeader66(siebelAsset.getHeader66());
				}
				
				if(StringUtils.isEmpty(assetNewVal.getAttr325())) {
					assetNewVal.setAttr325(siebelAsset.getAttr325());
				}
				
				if("Y".equalsIgnoreCase(dfrMaster.getIsAccountMoveDfr())){
					operation ="AssetMove";
				} else{
				operation =getOperation(assetNewVal,eqxDartBatchLines);
				}
				
				if(false == operation.equalsIgnoreCase(SBL_EQX_DELETE_POE_OPERATION)) {
					eqxDartBatchLines.setEQXGenAttrib2(null);//CascadeFlag 'Y'/ 'N'
					eqxDartBatchLines.setEQXGenAttrib6(null);//Effective Date MM/DD/YYYY
					eqxDartBatchLines.setEQXGenAttrib9(null);//Audit Note
				} else {
					//GenAttrib2 - Cascade Flag 	-  attr325
					//GenAttrib6 - Effective Date 	-  attr327
					//GenAttrib9 - Audit Note 		- header58
					if(assetNewVal.getAttr325()!=null) {
						eqxDartBatchLines.setEQXGenAttrib2(assetNewVal.getAttr325());//CascadeFlag 'Y'/ 'N'
					} else {
						eqxDartBatchLines.setEQXGenAttrib2(siebelAsset.getAttr325());//CascadeFlag 'Y'/ 'N'
					}
					
					if(assetNewVal.getAttr327()!=null) {
						eqxDartBatchLines.setEQXGenAttrib6(assetNewVal.getAttr327());//Effective Date MM/DD/YYYY
					} else {
						eqxDartBatchLines.setEQXGenAttrib6(siebelAsset.getAttr327());//Effective Date MM/DD/YYYY
					}
					
					if(assetNewVal.getHeader58()!=null) {
						eqxDartBatchLines.setEQXGenAttrib9(assetNewVal.getHeader58());//Audit Note
					} else {
						eqxDartBatchLines.setEQXGenAttrib9(siebelAsset.getHeader58());//Audit Note
					}
					
				}
				if(StringUtils.containsIgnoreCase(operation, "Insert")){
					if(StringUtils.isNotBlank(eqxDartBatchLines.getEQXPOFAssetNum()) 
							&& eqxDartBatchLines.getEQXPOFAssetNum().startsWith("126_"))
					eqxDartBatchLines.setEQXGroupNum(eqxDartBatchLines.getEQXPOFAssetNum());//POF assetnum, if operation contain insert.
				}
				
				
				eqxDartBatchLines.setEQXOperation(operation);
				/*
			 	Upsert_spcDART_spcBatch_Input
				 ListOfEqxDartBatchIo
					EqxDartBatch
						ListOfEqxDartBatchLines
							EqxDartBatchLines
								ListOfEqxDartBatchLineXa
									EqxDartBatchLineXa
				 */

				/*
					mandatory header ["POEAssetNum","CustUCMId","IBX","UnqSpaceIdVal","POEName","POFAssetNum","POFName"]
					
					---------------------------------------------
					Header_PoSITION	SIEBEL		ATTR_NAME
					---------------------------------------------
					HEADER2		POEAssetNum	ASSET NUMBER
					HEADER4		CustUCMId	ACCOUNT UCM ID
					HEADER8		IBX				IBX
					HEADER10	UnqSpaceIdVal	CAGE UNIQUE SPACE VAL
					HEADER20	POEName		PRODUCT
					HEADER24	POFAssetNum	ROOT ASSET NUMBER
					HEADER26	POFName		POF NAME
					EQXDFRItemId  Dfr line Item
					EQXOperation calculate it based on logic

				 *   Header section Optional only if New Value is present
				 *   Synch to Asset Y if it is part of header
				 *   Attribute section only when new value is present
				 *   Synch to POF Y if POF_ATTRIBUTE is set to Y in Attrib_CONfig table

				 */ 
			    eqxDartBatchLines.setListOfEqxDartBatchLineXa(listOfEqxDartBatchLineXa);
				
				if(isAnyAttrChanged || isMandatoryField){					
					listOfEqxDartBatchLines.getEqxDartBatchLines().add(eqxDartBatchLines);					
					isDirty=true;
				}
				eqxDartBatch.setListOfEqxDartBatchLines(listOfEqxDartBatchLines );

			}
			listOfEqxDartBatchIo.getEqxDartBatch().add(eqxDartBatch);
			upsertSpcDARTSpcBatchInput.setListOfEqxDartBatchIo(listOfEqxDartBatchIo);
			if(isDirty){
				UpsertSpcDARTSpcBatchOutput batchOutPut = siebelBatchService.upsertSpcDARTSpcBatch(upsertSpcDARTSpcBatchInput);
				res.setResponse(gson.toJson(batchOutPut));
				res.setError(false);
				res.setStatus(LogicConstants.SYNCINPROGRESS);
			}else{
				res.setResponse("Nothing change, no need to SBL sync.");
				res.setError(false);
				res.setStatus(LogicConstants.SYNCNOTREQUIRED);
			}
		}catch(Exception ex){
			res.setResponse(ex.getMessage());
			res.setError(true);
			res.setStatus(LogicConstants.SYNCERROR);
			logger.error("unable to sync SBL",ex);
		}
		return res;
	}

	private String getFieldValue(Object object) {
		if(object != null){
			if(BLANK_OUT.equalsIgnoreCase(object.toString().trim())){
				return null;
			}
			
		}
		return object!=null?object.toString():null;
	}


	@Override
	public DFRSyncResponse syncSVDfrKafkaConsumer(String dfrId) {
		logger.info("syncSVDfrKafkaConsumer dfrId:"+dfrId);
		DFRSyncResponse res = new DFRSyncResponse();
		try{		
			List<SvSyncV> svSyncVRecords = svAPIDao.getSvSyncVDart(dfrId);
			if(svSyncVRecords==null || svSyncVRecords.isEmpty()){
				res.setResponse("Total number of records sync from the sv view:0");
				res.setError(false);
				res.setStatus(LogicConstants.SYNCNOTREQUIRED);
			} else{
				List<Recon2Sync> recon2SyncRecords = new ArrayList<Recon2Sync>(); 
				for (SvSyncV svSyncV : svSyncVRecords) {
					Recon2Sync recon2Sync = new Recon2Sync(svSyncV);
					svAPIDao.saveRecon2Sync(recon2Sync);
					recon2SyncRecords.add(recon2Sync);
				}
				res.setResponse("Total number of records sync from the sv view: "+svSyncVRecords.size());
				res.setError(false);
				res.setStatus(LogicConstants.SYNCINPROGRESS);
			}
		}catch(Exception ex){
			res.setError(false);
			res.setStatus(LogicConstants.SYNCERROR);
			logger.error("unable to sync SV",ex);
		}
		return res;
	}
	
	
	@Override
	public boolean saveSoapAudit(DartSoapAudit dartSoapAudit) {
		appOpsInitiateDFRDao.saveSoapAudit(dartSoapAudit);
		return true;
	}
	
	@Override
	public List<DartSoapAuditModel> getAllSoapAuditReq() {
		List<DartSoapAuditModel> auditModels = new ArrayList<>();
		List<DartSoapAudit> auditEntities = appOpsInitiateDFRDao.getAllSoapAuditReq();
		for(DartSoapAudit en : auditEntities){
			DartSoapAuditModel model = new DartSoapAuditModel();
			model.setRequestId(en.getRequestId());
			model.setProduct(en.getProduct());
			model.setRequest(en.getRequest()==null?
					null:new String(en.getRequest()));
			model.setResponse(en.getResponse()==null?
					null:new String(en.getResponse()));
			model.setRequestTime(new Date(en.getRequestTime().getTime()));
			model.setResponseTime(new Date(en.getResponseTime().getTime()));
			model.setFault(en.getFault());
			model.setDfrId(en.getDfrId());
			auditModels.add(model);
		}
		return auditModels;
	}
	
	@Override
	public List<DartSoapAuditModel> getAuditsDfrorProduct(String dfrId, String product) {

		List<DartSoapAuditModel> auditModels = new ArrayList<>();
		List<DartSoapAudit> auditEntities = appOpsInitiateDFRDao.getAuditsDfrorProduct(dfrId, product);
		for(DartSoapAudit en : auditEntities){
			DartSoapAuditModel model = new DartSoapAuditModel();
			model.setRequestId(en.getRequestId());
			model.setProduct(en.getProduct());
			model.setRequest(en.getRequest()==null?
					null:new String(en.getRequest()));
			model.setResponse(en.getResponse()==null?
					null:new String(en.getResponse()));
			model.setRequestTime(new Date(en.getRequestTime().getTime()));
			model.setResponseTime(new Date(en.getResponseTime().getTime()));
			model.setFault(en.getFault());
			model.setDfrId(en.getDfrId());
			auditModels.add(model);
		}
		return auditModels;
	
	}


	@Override
	public List<DartSoapAudit> getRecentAuditsDfrorProduct(Long responseTime, long retryLimit) {
		return appOpsInitiateDFRDao.getRecentAuditsDfrorProduct(responseTime,retryLimit);
	}


	@Override
	public DartSoapAudit getDFRAuditByReqId(String dfrRequestId) {
		return appOpsInitiateDFRDao.getDFRAuditByReqId(dfrRequestId);
	}

	@Override
	public 	List<ApprovalHistory> getLatestAppHistoryList(String dfrId){
		return appOpsInitiateDFRDao.getLatestAppHistoryList(dfrId);
	}
	
	
	private void validateProductFilterForCommaSaparatedAssetNumsInKeyword(ProductFilter productFilter) {
		if (productFilter.getSearchDropBox() != null) {
			List<SearchDrop> searchDropList = productFilter.getSearchDropBox().getSearchDrop();
			if (CollectionUtils.isNotEmpty(searchDropList)) {
				for (SearchDrop searchDrop : searchDropList) {
					if (StringUtils.isNotEmpty(searchDrop.getKey())) {
						List<String> searchKewordsList = null;
						if ("header3".equalsIgnoreCase(searchDrop.getKey())||"header2".equalsIgnoreCase(searchDrop.getKey())||"header16".equalsIgnoreCase(searchDrop.getKey())) {
							searchKewordsList = new ArrayList<String>(
									Arrays.asList(productFilter.getKeyword().split(",")).stream()
									.filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim).collect(Collectors.toList()));
							if (CollectionUtils.isNotEmpty(searchKewordsList)) {
								PFilter pFilter = new PFilter();
								pFilter.setKey(searchDrop.getKey());
								pFilter.setValue(productFilter.getKeyword());
								pFilter.setLable(searchDrop.getLabel());
								pFilter.setListOfValues(searchKewordsList);
								productFilter.getFilters().add(pFilter);
								productFilter.setKeyword("");
							}
						} 
					}
				}
			} /*
				 * else if(StringUtils.isNotBlank(productFilter.getKeyword())){
				 * boolean isAllValCode = true; String errorCodeArray[] =
				 * productFilter.getKeyword().split(",");
				 * if(errorCodeArray.length>0){ for(String errorCode :
				 * errorCodeArray ){ if(!errorCode.startsWith("VAL_")){
				 * isAllValCode = false; break; } } if(isAllValCode)
				 * productFilter.setKeyword(""); } }
				 */
		}
	}
	
	public ProductFilter getProductFilterForErrorCodeGlobalFilter (ProductFilter productFilter) {
		PFilter errorCodeFilter = new PFilter();
		errorCodeFilter.setKey("Error Code");
		errorCodeFilter.setLable("Error Code");
		errorCodeFilter.setValue(productFilter.getKeyword().toUpperCase());
		List<PFilter> pFiltersList = new ArrayList<>();
		Map <String,List<PFilter>> filterMap = null;
		if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
			filterMap = productFilter.getFilters().stream().collect(Collectors.groupingBy(PFilter::getKey));
			if (filterMap.containsKey("header8")) {
				pFiltersList.addAll(filterMap.get("header8"));
			}
			if (filterMap.containsKey("header6")) {
				pFiltersList.addAll(filterMap.get("header6"));
			}
			if (filterMap.containsKey("header16")) {
				pFiltersList.addAll(filterMap.get("header16"));
			}
			pFiltersList.add(errorCodeFilter);
			if (filterMap.containsKey("header20")) {
				pFiltersList.addAll(filterMap.get("header20"));
			}
			if (filterMap.containsKey("header51")) {
				pFiltersList.addAll(filterMap.get("header51"));
			}
			if (filterMap.containsKey("header56")) {
				pFiltersList.addAll(filterMap.get("header56"));
			}
		} else {
			pFiltersList.add(errorCodeFilter);
		}
		productFilter.setFilters(pFiltersList);
		generateCacheKey(productFilter);
		return productFilter;
	}
	
	public void generateCacheKey(ProductFilter productFilter){
		productFilter.genrateCacheKey();
	}
	
	public List<SiebelAssetDa> filterSiebelAssetDAForErrorCodeGlobalFilter(List<PFilter> filterList,List<SiebelAssetDa> sblListColl) {
		Map<String,PFilter> filterMap = new HashMap<>();
		Map<String,List<SiebelAssetDa>> filteredAssetDaMap = new HashMap<>();
		for (PFilter filterObj : filterList) {
			if (CollectionUtils.isNotEmpty(filterObj.getListOfValues())) {
				filterMap.put(filterObj.getKey(), filterObj);
			}
		}
		if (filterMap.containsKey("header51") && CollectionUtils.isNotEmpty(sblListColl)){
			filteredAssetDaMap = sblListColl.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader51));
			PFilter filterObj = filterMap.get("header51");
			sblListColl.clear();
			for (String filterValue : filterObj.getListOfValues())  {
				if (filteredAssetDaMap.containsKey(filterValue)) {
					sblListColl.addAll(filteredAssetDaMap.get(filterValue));
				}
			}
		}
		if (filterMap.containsKey("header8") && CollectionUtils.isNotEmpty(sblListColl)){
			filteredAssetDaMap = sblListColl.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader8));
			PFilter filterObj = filterMap.get("header8");
			sblListColl.clear();
			for (String filterValue : filterObj.getListOfValues())  {
				if (filteredAssetDaMap.containsKey(filterValue)) {
					sblListColl.addAll(filteredAssetDaMap.get(filterValue));
				}
			}
		}
		if (filterMap.containsKey("header6") && CollectionUtils.isNotEmpty(sblListColl)){
			filteredAssetDaMap = sblListColl.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader6));
			PFilter filterObj = filterMap.get("header6");
			sblListColl.clear();
			for (String filterValue : filterObj.getListOfValues()) {
				if (filteredAssetDaMap.containsKey(filterValue)) {
					sblListColl.addAll(filteredAssetDaMap.get(filterValue));
				}
			}
		}
		if (filterMap.containsKey("header16") && CollectionUtils.isNotEmpty(sblListColl)){
			sblListColl = removeSiebelAssetDAWithNullSystemName(sblListColl);
			filteredAssetDaMap = sblListColl.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
			PFilter filterObj = filterMap.get("header16");
			sblListColl.clear();
			for (String filterValue : filterObj.getListOfValues())  {
				if (filteredAssetDaMap.containsKey(filterValue)) {
					sblListColl.addAll(filteredAssetDaMap.get(filterValue));
				}
			}
		}
		if (filterMap.containsKey("header56") && CollectionUtils.isNotEmpty(sblListColl)){
			sblListColl = removeSiebelAssetDAWithNullSystemName(sblListColl);
			filteredAssetDaMap = sblListColl.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader56));
			PFilter filterObj = filterMap.get("header56");
			sblListColl.clear();
			for (String filterValue : filterObj.getListOfValues())  {
				if (filteredAssetDaMap.containsKey(filterValue)) {
					sblListColl.addAll(filteredAssetDaMap.get(filterValue));
				}
			}
		}
		return sblListColl;
	}
	
	public List<SiebelAssetDa> removeSiebelAssetDAWithNullSystemName (List<SiebelAssetDa> sblListColl) {
		return sblListColl.stream().filter( assetDa -> assetDa.getHeader16() != null).collect(Collectors.toList());
	}
	
	public ProductFilterResult getProductFilterResultErrorCodeGlobal (List<SiebelAssetDa> sblListColl) {
		ProductFilterResult filterResult = new ProductFilterResult();
		List<ClxAssetDa> clxList = new ArrayList<>();
		List<SvAssetDa> svList = new ArrayList<>();
		Set<String> sblRowIds = new HashSet<>();
		Set<String> cageRowIds = new HashSet<>();
		Set<String> cabinetRowIds = new HashSet<>();
		Set<String> cabinetDpRowIds = new HashSet<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		for (SiebelAssetDa sbl : sblListColl) {
			assetSet.add(sbl.getHeader2());
			sblRowIds.add(sbl.getHeader1());
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		clxList = daDao.getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, cageRowIds, cabinetRowIds,cabinetDpRowIds);
		svList = daDao.getSvList(assetSet);
		/*clxList = appOpsDartDaDao.getClxListElastic(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, cageRowIds, cabinetRowIds,cabinetDpRowIds);
		svList = appOpsDartDaDao.getSvListElastic(assetSet);*/
		filterResult.setSblList(sblListColl);
		filterResult.setClxList(clxList);
		filterResult.setSvList(svList);
		filterResult.setClxCabninetRowIds(cabinetRowIds);
		filterResult.setClxCageRowIds(cageRowIds);
		filterResult.setSblRowIds(sblRowIds);
		filterResult.setClxCabninetDpRowIds(cabinetDpRowIds);
		return filterResult;
	}

@Override
	public HashMap<String, Object> initiateDfrAccountMove(AccountMoveAssetRequest request) throws Exception {	
		HashMap<String,Object> dataMap = new HashMap<>();
		HashMap<String,Asset> moveAssets = new HashMap<String,Asset>();
		HashMap<String,Asset> srcAssetMap = new HashMap<String,Asset>();
		List<Asset> targetAssets = new ArrayList<Asset>();
		List<Asset> srcAssets = new ArrayList<Asset>();
		getModifiedAssets(srcAssetMap, request.getSrcAssets().get(0), srcAssets,false);
		Set<String> clxAssetDacageUsid = new HashSet<String>();
		Set<String> clxAssetDacabUsid = new HashSet<String>();
		for (Asset asset : srcAssets) {
			if(asset.getProduct().equalsIgnoreCase(CacheConstant.CAGE)) {
				clxAssetDacageUsid.add(asset.getCageUSID());
			} else if(asset.getProduct().equalsIgnoreCase(CacheConstant.CABINET) || 
					asset.getProduct().equalsIgnoreCase(CacheConstant.DEMARCATION_POINT)) {
				clxAssetDacabUsid.add(asset.getCabinetUSId());
			}
			srcAssetMap.put(asset.getRowId(), asset);
		}
		
		getModifiedAssets(srcAssetMap, request.getTargetAsset().get(0), targetAssets,true);
		List<Asset> modifiedPortAssets = getModifiedPortAssests(targetAssets);
		
		
		dataMap.put("modifiedPort", modifiedPortAssets);
		List<String> rowIds = new ArrayList<String>(0);
		for (Asset asset : targetAssets) {
			rowIds.add(asset.getRowId());
			moveAssets.put(asset.getRowId(), asset);
		}
		
		
		List<ClxAssetDa> clxAssetDaList =  daDao.getClxList(clxAssetDacageUsid, clxAssetDacabUsid);
		
		logger.info("SRC Asset : "+ srcAssets.size());
		logger.info("target Asset : "+ targetAssets.size());
		logger.info("CLX : "+ clxAssetDaList.size());
		
		String dfrId = String.valueOf(appOpsInitiateDFRDao.getNextDartSeq());
		
		
		request.setDfrId(dfrId);
		
		appOpsInitiateDFRDao.saveOrUpdateDfrMaster(getDfrMasterAccountMove(dfrId));

		DfrMaster dfrMaster = appOpsDartEditDfrService.getDfrMasterById(dfrId);
		
		ApprovalHistory approvalHistory = appOpsDartEditDfrService.getApprovalHistory(dfrMaster, LogicConstants.DFR_INITIATED,dfrMaster.getNotes());
		appOpsDartEditDfrService.saveOrUpdateApprovalHistory(approvalHistory);
		Set<SnapshotSiebelAssetDa> snapshotSiebelAssetDaSet = new HashSet<SnapshotSiebelAssetDa>();
		Set<AssetNewVal> assetNewValSet = new HashSet<AssetNewVal>();
		for(Asset srcAsset : srcAssets){
			SnapshotSiebelAssetDa snapshotSiebelAssetDa = new SnapshotSiebelAssetDa();
			prepareSnapShotSiebleAssetDa(snapshotSiebelAssetDa, srcAsset);
			snapshotSiebelAssetDa.setHeader1(srcAsset.getRowId()+"."+dfrId);
			snapshotSiebelAssetDa.setDfrId(dfrId);
			snapshotSiebelAssetDa.setDfrLineId(srcAsset.getRowId()+"."+dfrId);
			
			appOpsInitiateDFRDao.saveOrUpdateSnapshotSiebelAssetDa(snapshotSiebelAssetDa);
			snapshotSiebelAssetDaSet.add(snapshotSiebelAssetDa);
			Asset moveAsset = moveAssets.get(srcAsset.getRowId());//Header1=rowId.
			moveAsset.populateAssetNewValDefaultValues(dfrId);
			appOpsInitiateDFRDao.saveOrUpdateAssetNewVal(moveAsset.getAssetNewVal());
			assetNewValSet.add(moveAsset.getAssetNewVal());
		
		}
		
		for(ClxAssetDa clx : clxAssetDaList){
				boolean isSelected = true;// markClxSelected(clx, productListWithRowIds);
				SnapshotClxAssetDa snapshotClxAssetDa = new SnapshotClxAssetDa();
				snapshotClxAssetDa.setDfrId(dfrId);
				snapshotClxAssetDa.setDfrLineId(clx.getHeader1() + "."+dfrId);
				
				populateClxSnapshotAssetDa(snapshotClxAssetDa, clx, dfrId,isSelected);
				appOpsInitiateDFRDao.saveOrUpdateSnapshotClxAssetDa(snapshotClxAssetDa);
			}
	
		
		//dataMap.put(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS, productFilterResult);
		dataMap.put("dfrid", dfrId);
		dataMap.put("snapshotSiebelAssetDaSet", snapshotSiebelAssetDaSet);
		dataMap.put("assetNewValSet", assetNewValSet);
		dataMap.put("message", "dfr created successfully ");
		
		return dataMap;
	}
	
	
	
	
//	public static void main(String[] args) {
//		String path = "C:\\Users\\mm\\Desktop\\Asstes_V1.json";
//		try (Stream<String> lines = Files.lines(Paths.get(path))) {	          
//			String content = lines.collect(Collectors.joining(System.lineSeparator()));
//			Asset a = new Gson().fromJson(content, Asset.class);
//			System.out.println(a);				
//			//displayAsset(a);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private List<Asset> getModifiedPortAssests(List<Asset> assets) throws Exception {
		List<Asset> assetList = new ArrayList<Asset>();
		for(Asset asset : assets) {
			if((asset.getProduct()!=null) &&
					(asset.getProduct().equals("Equinix Connect Port") || 
							asset.getProduct().equals("Metro Connect Port"))) {
				if(StringUtils.isNotEmpty(asset.getParAssetId())){
					   copyCommonHeaders(asset);
					 }
				
				
				assetList.add(asset);
			} 
		}
		return assetList;
	}

	@Autowired
	AppOpsDartEditDfrDao editDao;
	
	private void copyDestinationProductDetails(SiebelAssetDa destinationSbl, AssetNewVal toMoveSbl) throws Exception {
	   	 for(String propname : CacheConstant.parentCabOrDPsCommonHeadersForAcAndDC){
	 	    	propname = CacheConstant.getHeaderMappedAttrEntityProperty(propname);
	 	    	String setMethodName = "set"+propname;
	 	    	String getMethodName = "get"+propname;
	 	    	
	 	    	Expression expr = new Expression(destinationSbl, getMethodName, new Object[0]);
	 	    	Statement stmt  = new Statement(toMoveSbl, setMethodName, new String[] { (String)expr.getValue() });
	 	    	stmt.execute();
	 	    }
		}

	private void copyDestinationProductDetailsforAccountMove(SiebelAssetDa destinationSbl, AssetNewVal toMoveSbl, String []headers) throws Exception {
	   	 for(String propname : headers){
	 	    	propname = CacheConstant.getHeaderMappedAttrEntityProperty(propname);
	 	    	String setMethodName = "set"+propname;
	 	    	String getMethodName = "get"+propname;
	 	    	
	 	    	Expression expr = new Expression(destinationSbl, getMethodName, new Object[0]);
	 	    	Statement stmt  = new Statement(toMoveSbl, setMethodName, new String[] { (String)expr.getValue() });
	 	    	stmt.execute();
	 	    }
		}

	
	List<Asset> getModifiedAssets(HashMap<String,Asset> srcAssetMap, Asset a, List<Asset> assetList, boolean copyHeaderFlag) throws Exception {
		System.out.println(a.getRowId() +" - "+ a. getProduct());
		if(a.isModified()) {
			
			 if(StringUtils.isNotEmpty(a.getParentAssetId()) && copyHeaderFlag){
				 copyCommonHeaders(a);
			 }
			
			assetList.add(a);
		}
		if(a.getChildAssetList()!=null){
			for (Asset c : a.getChildAssetList()) {
				getModifiedAssets(srcAssetMap, c,assetList,copyHeaderFlag);
			}
		}
		return assetList;
	}


	private void copyCommonHeaders(Asset a) throws Exception {
		/*
		 * Header needs to copy
		 * HEADER1,HEADER2,HEADER3,HEADER4,HEADER5,HEADER6,HEADER7,HEADER8,HEADER9,HEADER10,HEADER11,HEADER12,
		 * HEADER14,HEADER16,HEADER20,HEADER21,HEADER22,HEADER24,HEADER26,HEADER40,HEADER43,HEADER53,HEADER54,
		 * 
		 */
		   AssetNewVal  assetNewVal = new AssetNewVal(); 
		   assetNewVal.setHeader1(a.getRowId());
		   assetNewVal.setHeader2(a.getAssetNum());
		   assetNewVal.setHeader3(a.getSerialNum());
		   assetNewVal.setHeader4(a.getAssetUCMID());
		   assetNewVal.setHeader5(a.getUcmID());
		   assetNewVal.setHeader6(a.getAccount());
		   assetNewVal.setHeader7(a.getAccountName());
		   assetNewVal.setHeader8(a.getIbxName());
		   assetNewVal.setHeader9(a.getxUniqueSpaceId());
		   assetNewVal.setHeader10(a.getCageUSID()); 
		   assetNewVal.setHeader16(a.getSystemName());
		   assetNewVal.setHeader40(a.getRelatedAccountNumber());
		   assetNewVal.setHeader53(a.getOwnerAccoutId());
		   assetNewVal.setHeader20(a.getPoeName());
		   
		   if(a.getProduct().equalsIgnoreCase(CacheConstant.CABINET)) {
			   assetNewVal.setHeader11(a.getCabinetUSId());
			   assetNewVal.setHeader12(a.getCabUSIdVal());
			   assetNewVal.setHeader14(a.getCabinetNumber());
			   
			   assetNewVal.setHeader21(a.getParAssetId());
			   assetNewVal.setHeader22(a.getRootAssetId());
			   assetNewVal.setHeader24(a.getPofAssetNum());
			   assetNewVal.setHeader26(a.getPofName());
		   } 
		   
		   if(a.getProduct().toLowerCase().contains("circuit")) {
			   	   
			   assetNewVal.setHeader11(a.getCabinetUSId());
			   assetNewVal.setHeader12(a.getCabUSIdVal());
			   assetNewVal.setHeader14(a.getCabinetNumber());
			   assetNewVal.setHeader21(a.getParAssetId());
			   assetNewVal.setHeader22(a.getRootAssetId());
			   assetNewVal.setHeader24(a.getPofAssetNum());
			   assetNewVal.setHeader26(a.getPofName());
		   } 
		   
		   if(a.getProduct().equalsIgnoreCase(CacheConstant.PATCH_PANEL)) {
			   assetNewVal.setHeader1(a.getRowId());
			   assetNewVal.setHeader2(a.getAssetNum());
			   assetNewVal.setHeader3(a.getSerialNum());			  
			   assetNewVal.setHeader11(a.getCabinetUSId());
			   assetNewVal.setHeader12(a.getCabUSIdVal());
			   assetNewVal.setHeader14(a.getCabinetNumber());
		   } 
		   
		   if(a.getProduct().equalsIgnoreCase(CacheConstant.NETWORK_CABLE_CONNECTION)) {
			   		  
			   assetNewVal.setHeader11(a.getCabinetUSId());
			   assetNewVal.setHeader12(a.getCabUSIdVal());
			   assetNewVal.setHeader14(a.getCabinetNumber());
			   assetNewVal.setHeader54(a.getPpId());
		   } 
		   
		   if(a.getProduct().toLowerCase().contains("port")) {
			   		  
			   assetNewVal.setHeader10(a.getCageUSID());
			   assetNewVal.setHeader11(a.getCabinetUSId());
			   assetNewVal.setHeader12(a.getCabUSIdVal());
			   assetNewVal.setHeader14(a.getCabinetNumber());			   
			   assetNewVal.setHeader43(a.getCableId());			  
		   }
		   assetNewVal.setHeader57("Y");
		   a.setAssetNewVal(assetNewVal);
	}
	
	private void prepareSnapShotSiebleAssetDa(SnapshotSiebelAssetDa snapshotSiebelAssetDa, Asset srcAsset) throws Exception {
		snapshotSiebelAssetDa.setHeader1(srcAsset.getRowId());
		snapshotSiebelAssetDa.setHeader2(srcAsset.getAssetNum());
		snapshotSiebelAssetDa.setHeader3(srcAsset.getSerialNum());
		snapshotSiebelAssetDa.setHeader4(srcAsset.getAssetUCMID());
		snapshotSiebelAssetDa.setHeader5(srcAsset.getUcmID());
		snapshotSiebelAssetDa.setHeader6(srcAsset.getAccount());
		snapshotSiebelAssetDa.setHeader7(srcAsset.getAccountName());
		snapshotSiebelAssetDa.setHeader8(srcAsset.getIbxName());
		snapshotSiebelAssetDa.setHeader9(srcAsset.getxUniqueSpaceId());
		snapshotSiebelAssetDa.setHeader10(srcAsset.getCageUSID());
		snapshotSiebelAssetDa.setHeader11(srcAsset.getCabinetUSId());
		snapshotSiebelAssetDa.setHeader12(srcAsset.getCabUSIdVal());
		snapshotSiebelAssetDa.setHeader14(srcAsset.getCabinetNumber());
		snapshotSiebelAssetDa.setHeader16(srcAsset.getSystemName());
		snapshotSiebelAssetDa.setHeader20(srcAsset.getPoeName());
		snapshotSiebelAssetDa.setHeader21(srcAsset.getParAssetId());
		snapshotSiebelAssetDa.setHeader22(srcAsset.getRootAssetId());
		snapshotSiebelAssetDa.setHeader24(srcAsset.getPofAssetNum());
		snapshotSiebelAssetDa.setHeader26(srcAsset.getPofName());
		snapshotSiebelAssetDa.setHeader40(srcAsset.getRelatedAccountNumber());
		snapshotSiebelAssetDa.setHeader43(srcAsset.getCableId());
		snapshotSiebelAssetDa.setHeader53(srcAsset.getOwnerAccoutId());
		snapshotSiebelAssetDa.setHeader54(srcAsset.getPpId());
		snapshotSiebelAssetDa.setHeader57("Y");
		
		

	}

	@Override
	public Long getNextNoccIntegrationReqSeq() {
		return appOpsInitiateDFRDao.getNextNoccIntegrationReqSeq();
	}


	@Override
	public DFRSyncResponse syncNOCCDFRSync(DFRKafkaMessageVO dfr) {
		AccountMoveRequest acMoveRequest = appOpsAccountMovementBusiness.getAccountMoveRequest(dfr.getDfrId());
		if(StringUtils.isBlank(acMoveRequest.getPortRowIds())) {
			DFRSyncResponse res = new DFRSyncResponse();
			res.setDfrId(dfr.getDfrId());
			res.setRequest("NA");
			res.setResponse("NA");
			res.setRequestDate(new Date());
			res.setResponseDate(new Date());
			res.setError(false);
			res.setStatus(LogicConstants.NA);
			saveNoccDFR(res);
		} else {
			List<PortAsset> portAssets = appOpsAccountMovementBusiness.getListPortAssets(getRowIds(acMoveRequest.getPortRowIds(),"ALL"),true);
			List<PortAsset> gMetroPortAssets = appOpsAccountMovementBusiness.getListPortAssets(getRowIds(acMoveRequest.getPortRowIds(),"METRO"),false);
			portAssets.addAll(gMetroPortAssets);
			ResponseEntity responseEntiry = null;
			Map<String, String> rowUcmMap = getRowUCMMap(acMoveRequest.getPortRowIds());
					DFRSyncResponse res = new DFRSyncResponse();
					res.setDfrId(dfr.getDfrId());
					res.setResponseId(UUID.randomUUID().toString());
			res.setRequestDate(new Date());
			
			JsonArray portsArray = new JsonArray();
			String clxSyncURL = configService.getValueByKey("DART_NOCC_SYNC_URL");
			Long seq = appOpsInitiateDFRDao.getNextNoccIntegrationReqSeq();
			
			for (PortAsset pa : portAssets) {
					try {
					JsonObject request = new JsonObject();
					
						request.addProperty("portId", pa.getPortId());
						request.addProperty("ucmId", rowUcmMap.get(pa.getAssetId()));
						request.addProperty("requestId", seq);
						portsArray.add(request);
						//saveNoccDFR(res);
				} catch (Exception ex) {
					logger.error("unable to sync nocc:", ex);
				}
			}
			
			//
			try {
						HttpHeaders headers = new HttpHeaders();
						headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
						headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity entityDartRequest = new HttpEntity(portsArray.toString(), headers);
				responseEntiry = consumerTemplate.exchange(clxSyncURL, HttpMethod.POST, entityDartRequest, String.class);
				logger.debug("NOCC Requst>>" + portsArray + " Response:" + responseEntiry);
				res.setRequest(portsArray.toString());
						res.setResponse(responseEntiry.getStatusCode() + ":" + responseEntiry.getBody());
						res.setResponseDate(new Date());
						if (StringUtils.contains(responseEntiry.getBody().toString().toLowerCase(), "success")) {
							res.setError(false);
							res.setStatus(LogicConstants.SYNCCOMPLETED);
						} else {
							res.setStatus(LogicConstants.SYNCERROR);
							res.setError(true);
						}
				saveNoccDFR(res);
					} catch (Exception ex) {
						res.setResponseDate(new Date());
						res.setResponse(responseEntiry.getStatusCode() + ":" + ex.getMessage());
						res.setError(true);
						logger.error("unable to sync noc", ex);
					}
			
		}
		return null;
	}
	
	private void saveNoccDFR(DFRSyncResponse dfrResponse) {
		DartSoapAudit clxAudit = new DartSoapAudit(dfrResponse,LogicConstants.NOCC_PRODUCT);
		clxAudit.setRequestId(UUID.randomUUID().toString());
		saveSoapAudit(clxAudit);
	}
	
	private String getRowIds(String data,String type) {
		 String rows = "";
		 String[]  row = data.split(",");
		 for(String s : row) {
			 if(type.equalsIgnoreCase("ALL") || (type.equalsIgnoreCase("METRO") && s.contains("Metro Connect port"))) {
				 String rowId = s.split("#")[0];
				 rows = rows+rowId+",";
			 }
		 }
		if(rows.equals("")) {
			return "";
		}
		return StringUtils.substring(rows, 0, rows.length() - 1);
	}
	
	private Map<String,String> getRowUCMMap(String data) {
		Map<String,String> rowUcmMap = new HashMap<String,String>();
		if(StringUtils.isBlank(data)) {
			return rowUcmMap; 
		}
		 String[]  row = data.split(",");
		 for(String s : row) {
			 String[] rowucm = s.split("#");
			 rowUcmMap.put(rowucm[0], rowucm[1]);
		 }
		return rowUcmMap;
	}
	
	public List<Product> createDfrDaInputObject(List<SiebelAssetDa> sblDas,List<ClxAssetDa> clxDas,List<SvAssetDa> svDas) {
		try{
			List<Product> products = new ArrayList<Product>();
			
			ClxAssetDa clx = null;
			SvAssetDa sv = null;
			Product product = null;
			Da da = null;
			
			for (SiebelAssetDa sbl : sblDas) {
				product = new Product();
				da = new Da();
				if (sbl != null) {

					clx = ServiceUtil.getClx(sbl, clxDas);
					sv = ServiceUtil.getSv(sbl, svDas);
					da.setSBL(sbl.getHeader1());
					da.setCLX(clx != null ? clx.getHeader1() : CacheConstant.BLANK);
					da.setSV(sv != null ? sv.getHeader1() : CacheConstant.BLANK);
					product.setDa(da);
					product.setName(sbl.getHeader20());
					products.add(product);
				}
			}
			return products;
		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}

	}
	
	@Override
	public /* synchronized */ HashMap<String, Object> initiateDfr3(DfrDaInput dfrDaInput)
			throws InterruptedException, ExecutionException {
		HashMap<String, Object> dataMap = new HashMap<>();

		ProductFilter dbSnapShotfilter = new ProductFilter();
		if (dfrDaInput != null) {
			// getting super set firing snapshot query
			logger.info("dfrDaInput :" + gson.toJson(dfrDaInput, DfrDaInput.class));
			ProductFilter snapshotFilter = dfrDaInput.getSnapshotfilter();
			dbSnapShotfilter = deepCopyProductFilter(dfrDaInput.getSnapshotfilter());
			String snapShotFilterJson = gson.toJson(snapshotFilter, ProductFilter.class);
			logger.info("Snapshotfilter : " + snapShotFilterJson);
			int step = 1;
			long start = System.currentTimeMillis();
			ProductFilterResult productFilterResult = new ProductFilterResult();
			String key = dfrDaInput.getSnapshotfilter().genrateCacheKey();
			logger.info("Cache Key " + key);
			logger.info("Cache Key decoded " + dfrDaInput.getSnapshotfilter().getDecodedKey());
			// Added this 9 aug 2019 release to fix if data got cleared from
			// redis cache
			// in case user selected comma saprated asset numbers
			if (StringUtils.isNotEmpty(dfrDaInput.getSnapshotfilter().getKeyword())
					/*&& dfrDaInput.getSnapshotfilter().getKeyword().length() == 7*/
					&& dfrDaInput.getSnapshotfilter().getKeyword().toUpperCase().startsWith("VAL_")) {
				List<SiebelAssetDa> sblListCollTemp = new ArrayList<>();
				sblListCollTemp = daDao.getSblListWhenOnlyErrorFilterApplied(
						getProductFilterForErrorCodeGlobalFilter(dfrDaInput.getSnapshotfilter()), sblListCollTemp);
				sblListCollTemp = filterSiebelAssetDAForErrorCodeGlobalFilter(
						dfrDaInput.getSnapshotfilter().getFilters(), sblListCollTemp);
				productFilterResult = getProductFilterResultErrorCodeGlobal(sblListCollTemp);
			} else {

				ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(dfrDaInput.getSnapshotfilter());
				productFilterResult.setProductFilter(dfrDaInput.getSnapshotfilter());
				if (DartConstants.IS_ELASTIC_CALL) {
					productFilterResult = daDao.getAllProductFilterResultElastic(productFilterResult);
				} else {
					productFilterResult = daDao.getProductFilterResult(productFilterResult);
				}
			}
			/*
			 * List<SiebelAssetDa> sblAssetDaList =
			 * productFilterResult.getSblList(); List<ClxAssetDa> clxAssetDaList
			 * = productFilterResult.getClxList(); List<SvAssetDa> svAssetDaList
			 * = productFilterResult.getSvList(); long end =
			 * System.currentTimeMillis(); logger.info(
			 * " Snapshot filter time taken : " + (end -start)); logger.info(
			 * "step ==>"+step++ ); logger.info("SBL : "+
			 * sblAssetDaList.size()); logger.info("CLX : "+
			 * clxAssetDaList.size()); logger.info("SV  : "+
			 * svAssetDaList.size());
			 */

			/**
			 * Sort selected products from product filter result
			 */

			List<String> products = null;
			if (dfrDaInput.getProducts() != null) {
				Set<String> selectedProducts = new HashSet<>();
				List<Product> dfrProducts = dfrDaInput.getProducts();
				dfrProducts.forEach(a -> selectedProducts.add(a.getName()));
				products = new ArrayList<>(selectedProducts);

			} else {
				products = ServiceUtil.getProductsFromRequest(snapshotFilter);
			}
			List<SiebelAssetDa> sblAssetDaList = ServiceUtil.filterSiebelAssetDa(productFilterResult.getSblList(),
					products);
			// List<SiebelAssetDa> sblAssetDaList =
			// productFilterResult.getSblList();
			List<ClxAssetDa> clxAssetDaList = productFilterResult.getClxList();
			List<SvAssetDa> svAssetDaList = productFilterResult.getSvList();
			List<Product> productListWithRowIds = null;
			if (dfrDaInput.getProducts() == null) {
				productListWithRowIds = createDfrDaInputObject(sblAssetDaList, clxAssetDaList, svAssetDaList);
			} else {
				productListWithRowIds = dfrDaInput.getProducts();
			}
			String dfrId = String.valueOf(appOpsInitiateDFRDao.getNextDartSeq());

			dataMap.put("productListWithRowIds", productListWithRowIds);

			dbSnapShotfilter.setDfrId(dfrId);
			/*
			 * dbSnapShotfilter.setFilters(snapshotFilter.getFilters());
			 * dbSnapShotfilter.setKeyword(snapshotFilter.getKeyword());
			 * dbSnapShotfilter.setSearchDropBox(snapshotFilter.getSearchDropBox
			 * ());
			 */

			String dbSnapShotfilterJson = gson.toJson(dbSnapShotfilter, ProductFilter.class);
			// saving dfr in db
			appOpsInitiateDFRDao.saveOrUpdateDfrMaster(getDfrMaster(dfrId, dbSnapShotfilterJson));

			DfrMaster dfrMaster = appOpsDartEditDfrService.getDfrMasterById(dfrId);
			ApprovalHistory approvalHistory = appOpsDartEditDfrService.getApprovalHistory(dfrMaster,
					LogicConstants.DFR_INITIATED, dfrMaster.getNotes());
			appOpsDartEditDfrService.saveOrUpdateApprovalHistory(approvalHistory);
			List<AssetNewVal> assetNewValList = new ArrayList<AssetNewVal>();
			List<SnapshotSiebelAssetDa> snapshotSiebelAssetDaList = new ArrayList<SnapshotSiebelAssetDa>();
			List<SnapshotClxAssetDa> snapshotClxAssetDaList = new ArrayList<SnapshotClxAssetDa>();
			List<SnapshotSvAssetDa> snapshotSvAssetDaList = new ArrayList<SnapshotSvAssetDa>();
			List<CxiErrorTbl> cxiErrorList = new ArrayList<CxiErrorTbl>();
			List<CxiErrorTbl> cxiErrorCLXList = new ArrayList<CxiErrorTbl>();
			List<CxiErrorTbl> cxiErrorSVList = new ArrayList<CxiErrorTbl>();
			for (SiebelAssetDa sbl : sblAssetDaList) {

				boolean isSelected = markSblSelected(sbl, productListWithRowIds);
				if (isSelected) {
					SnapshotSiebelAssetDa snapshotSiebelAssetDa = new SnapshotSiebelAssetDa();
					snapshotSiebelAssetDa.setDfrId(dfrId);
					snapshotSiebelAssetDa.setDfrLineId(sbl.getHeader1() + "." + dfrId);
					populateSblSnapshotAssetDa(snapshotSiebelAssetDa, sbl, dfrId, isSelected);
					// appOpsInitiateDFRDao.saveOrUpdateSnapshotSiebelAssetDa(snapshotSiebelAssetDa);
					snapshotSiebelAssetDaList.add(snapshotSiebelAssetDa);
					AssetNewVal assetNewVal = new AssetNewVal();
					assetNewVal.setDfrId(dfrId);
					assetNewVal.setDfrLineId(sbl.getHeader1() + "." + dfrId);
					assetNewVal.setHeader1(sbl.getHeader1() + "." + dfrId);
					assetNewVal.setHeader20(sbl.getHeader20()); // product name
					// assetNewVal.setHeader17(sbl.getHeader17()); // all date
					// fields
					// assetNewVal.setHeader34(sbl.getHeader34());
					// assetNewVal.setHeader36(sbl.getHeader36());
					// assetNewVal.setHeader55(sbl.getHeader55());
					if (isSelected) {
						assetNewVal.setHeader57("Y"); // selected y
					}
					// appOpsInitiateDFRDao.saveOrUpdateAssetNewVal(assetNewVal);
					assetNewValList.add(assetNewVal);

					List<SrcCxiErrorTbl> sblErrorList = this.sblErrorMap.get(sbl.getHeader1());
					if (CollectionUtils.isNotEmpty(sblErrorList)) {
						// appOpsInitiateDFRDao.saveSblErrorSnapshots(snapshotSiebelAssetDa,
						// sbl, dfrId, sblErrorList);
						appOpsInitiateDFRDao.setSblErrorSnapshotList(snapshotSiebelAssetDa, sbl, dfrId, sblErrorList,
								cxiErrorList);
					}
				}
			}
			// Saving SnapshotSiebelAssetDa Batch Data.
			appOpsInitiateDFRDao.saveOrUpdateBatchData(snapshotSiebelAssetDaList);

			// Saving AssetNewVal Batch Data.
			appOpsInitiateDFRDao.saveOrUpdateBatchData(assetNewValList);

			// Saving SblErrorSnapshot Batch Data.
			if (CollectionUtils.isNotEmpty(cxiErrorList)) {
				appOpsInitiateDFRDao.saveOrUpdateBatchData(cxiErrorList);
			}

			for (ClxAssetDa clx : clxAssetDaList) {
				boolean isSelected = markClxSelected(clx, productListWithRowIds);
				if (isSelected) {
					SnapshotClxAssetDa snapshotClxAssetDa = new SnapshotClxAssetDa();
					snapshotClxAssetDa.setDfrId(dfrId);
					snapshotClxAssetDa.setDfrLineId(clx.getHeader1() + "." + dfrId);

					populateClxSnapshotAssetDa(snapshotClxAssetDa, clx, dfrId, isSelected);
					// appOpsInitiateDFRDao.saveOrUpdateSnapshotClxAssetDa(snapshotClxAssetDa);
					snapshotClxAssetDaList.add(snapshotClxAssetDa);

					List<SrcCxiErrorTbl> clxErrorList = this.clxErrorMap.get(clx.getHeader1());
					if (CollectionUtils.isNotEmpty(clxErrorList)) {
						// appOpsInitiateDFRDao.saveClxErrorSnapshots(snapshotClxAssetDa,
						// clx, dfrId, clxErrorList);
						appOpsInitiateDFRDao.setClxErrorSnapshotList(snapshotClxAssetDa, clx, dfrId, clxErrorList,
								cxiErrorCLXList);
					}
				}

			}
			// Saving SnapshotClxAssetDa Batch Data.
			appOpsInitiateDFRDao.saveOrUpdateBatchData(snapshotClxAssetDaList);

			// Saving ClxErrorSnapshot Batch Data.
			if (CollectionUtils.isNotEmpty(cxiErrorCLXList)) {
				appOpsInitiateDFRDao.saveOrUpdateBatchData(cxiErrorCLXList);
			}

			for (SvAssetDa sv : svAssetDaList) {
				boolean isSelected = markSvSelected(sv, productListWithRowIds);
				if (isSelected) {
					SnapshotSvAssetDa snapshotSvAssetDa = new SnapshotSvAssetDa();
					snapshotSvAssetDa.setDfrId(dfrId);
					snapshotSvAssetDa.setDfrLineId(sv.getHeader1() + "." + dfrId);
					populateSvSnapshotAssetDa(snapshotSvAssetDa, sv, dfrId, isSelected);
					// appOpsInitiateDFRDao.saveOrUpdateSnapshotSvAssetDa(snapshotSvAssetDa);
					snapshotSvAssetDaList.add(snapshotSvAssetDa);

					List<SrcCxiErrorTbl> svErrorList = this.svErrorMap.get(sv.getHeader1());
					if (CollectionUtils.isNotEmpty(svErrorList)) {
						// appOpsInitiateDFRDao.saveSvErrorSnapshots(snapshotSvAssetDa,sv,
						// dfrId,svErrorList);
						appOpsInitiateDFRDao.setSvErrorSnapshotList(snapshotSvAssetDa, sv, dfrId, svErrorList,
								cxiErrorSVList);
					}
				}
			}
			// Saving SnapshotSvAssetDa Batch Data.
			appOpsInitiateDFRDao.saveOrUpdateBatchData(snapshotSvAssetDaList);

			// Saving SvErrorSnapshot Batch Data.
			if (CollectionUtils.isNotEmpty(cxiErrorSVList)) {
				appOpsInitiateDFRDao.saveOrUpdateBatchData(cxiErrorSVList);
			}
			logger.info("Initiate dfr 3 time taken " + (System.currentTimeMillis() - start));
			dataMap.put(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS, productFilterResult);
			dataMap.put("dfrid", dfrId);
			dataMap.put("message", "dfr created successfully ");
			return dataMap;

		} else {
			dataMap.put("error", "no data received");
			return dataMap;
		}

	}
	
	@Override
	public HashMap<String, Object> copyNonSelectedAssets(HashMap<String, Object> dataMap) {
		String dfrId =(String) dataMap.get("dfrid");
		List<Product> productListWithRowIds = (List<Product>)dataMap.get("productListWithRowIds");
		ProductFilterResult productFilterResult =(ProductFilterResult) dataMap.get(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS);
		List<SiebelAssetDa> sblAssetDaList  = productFilterResult.getSblList();
		List<ClxAssetDa> clxAssetDaList = productFilterResult.getClxList();
		List<SvAssetDa> svAssetDaList = productFilterResult.getSvList();
		List<AssetNewVal> assetNewValList = new ArrayList<AssetNewVal>();
		List<SnapshotSiebelAssetDa> snapshotSiebelAssetDaList = new ArrayList<SnapshotSiebelAssetDa>();
		List<SnapshotClxAssetDa> snapshotClxAssetDaList = new ArrayList<SnapshotClxAssetDa>();
		List<SnapshotSvAssetDa> snapshotSvAssetDaList =  new ArrayList<SnapshotSvAssetDa>();
		List<CxiErrorTbl> cxiErrorList =  new ArrayList<CxiErrorTbl>();
		List<CxiErrorTbl> cxiErrorCLXList =  new ArrayList<CxiErrorTbl>();
		List<CxiErrorTbl> cxiErrorSVList =  new ArrayList<CxiErrorTbl>();
		for(SiebelAssetDa sbl : sblAssetDaList){
			
			boolean isSelected = markSblSelected(sbl,productListWithRowIds);
			if(!isSelected){
				SnapshotSiebelAssetDa snapshotSiebelAssetDa = new SnapshotSiebelAssetDa();
				snapshotSiebelAssetDa.setDfrId(dfrId);
				snapshotSiebelAssetDa.setDfrLineId(sbl.getHeader1()+"."+dfrId);
				populateSblSnapshotAssetDa(snapshotSiebelAssetDa, sbl, dfrId,isSelected);
				// appOpsInitiateDFRDao.saveOrUpdateSnapshotSiebelAssetDa(snapshotSiebelAssetDa);
				snapshotSiebelAssetDaList.add(snapshotSiebelAssetDa);
					AssetNewVal assetNewVal = new AssetNewVal();
					assetNewVal.setDfrId(dfrId);
					assetNewVal.setDfrLineId(sbl.getHeader1()+"."+dfrId);
					assetNewVal.setHeader1(sbl.getHeader1()+"."+dfrId);
					assetNewVal.setHeader20(sbl.getHeader20()); 
					// appOpsInitiateDFRDao.saveOrUpdateAssetNewVal(assetNewVal);
					assetNewValList.add(assetNewVal);
			
				List<SrcCxiErrorTbl> sblErrorList = this.sblErrorMap.get(sbl.getHeader1());
				if(CollectionUtils.isNotEmpty(sblErrorList)){
					// appOpsInitiateDFRDao.saveSblErrorSnapshots(snapshotSiebelAssetDa, sbl, dfrId, sblErrorList);
					appOpsInitiateDFRDao.setSblErrorSnapshotList(snapshotSiebelAssetDa, sbl, dfrId, sblErrorList, cxiErrorList);
				}
			}
		}
		// Saving SnapshotSiebelAssetDa Batch Data.
		appOpsInitiateDFRDao.saveOrUpdateBatchData(snapshotSiebelAssetDaList);
		
		// Saving AssetNewVal Batch Data.
		appOpsInitiateDFRDao.saveOrUpdateBatchData(assetNewValList);
		
		// Saving SblErrorSnapshot Batch Data.
		if (CollectionUtils.isNotEmpty(cxiErrorList)) {
			appOpsInitiateDFRDao.saveOrUpdateBatchData(cxiErrorList);
		}
		
		for(ClxAssetDa clx : clxAssetDaList){
				boolean isSelected = markClxSelected(clx, productListWithRowIds);
				if(!isSelected){
					SnapshotClxAssetDa snapshotClxAssetDa = new SnapshotClxAssetDa();
					snapshotClxAssetDa.setDfrId(dfrId);
					snapshotClxAssetDa.setDfrLineId(clx.getHeader1() + "."+dfrId);
					
					populateClxSnapshotAssetDa(snapshotClxAssetDa, clx, dfrId,isSelected);
					// appOpsInitiateDFRDao.saveOrUpdateSnapshotClxAssetDa(snapshotClxAssetDa);
					snapshotClxAssetDaList.add(snapshotClxAssetDa);
					
					List<SrcCxiErrorTbl> clxErrorList = this.clxErrorMap.get(clx.getHeader1());
					if(CollectionUtils.isNotEmpty(clxErrorList)){
						// appOpsInitiateDFRDao.saveClxErrorSnapshots(snapshotClxAssetDa, clx, dfrId, clxErrorList);
						appOpsInitiateDFRDao.setClxErrorSnapshotList(snapshotClxAssetDa, clx, dfrId, clxErrorList, cxiErrorCLXList);
					}
				}
				
			}
		// Saving SnapshotClxAssetDa Batch Data.
		appOpsInitiateDFRDao.saveOrUpdateBatchData(snapshotClxAssetDaList);
		
		// Saving ClxErrorSnapshot Batch Data.
		if(CollectionUtils.isNotEmpty(cxiErrorCLXList)){
			appOpsInitiateDFRDao.saveOrUpdateBatchData(cxiErrorCLXList);
		}
			
		for(SvAssetDa sv : svAssetDaList){
			boolean isSelected = markSvSelected(sv,productListWithRowIds);
			if(!isSelected){
				SnapshotSvAssetDa snapshotSvAssetDa = new SnapshotSvAssetDa();
				snapshotSvAssetDa.setDfrId(dfrId);
				snapshotSvAssetDa.setDfrLineId(sv.getHeader1() + "."+dfrId);
				populateSvSnapshotAssetDa(snapshotSvAssetDa, sv, dfrId,isSelected);
				//appOpsInitiateDFRDao.saveOrUpdateSnapshotSvAssetDa(snapshotSvAssetDa);
				snapshotSvAssetDaList.add(snapshotSvAssetDa);
				List<SrcCxiErrorTbl> svErrorList = this.svErrorMap.get(sv.getHeader1());
				if(CollectionUtils.isNotEmpty(svErrorList)){
					//appOpsInitiateDFRDao.saveSvErrorSnapshots(snapshotSvAssetDa,sv,  dfrId,svErrorList);
					appOpsInitiateDFRDao.setSvErrorSnapshotList(snapshotSvAssetDa,sv,  dfrId,svErrorList, cxiErrorSVList);
				}
			}		
		}
		// Saving SnapshotSvAssetDa Batch Data.
		appOpsInitiateDFRDao.saveOrUpdateBatchData(snapshotSvAssetDaList);
		
		// Saving SvErrorSnapshot Batch Data.
		if (CollectionUtils.isNotEmpty(cxiErrorSVList)) {
			appOpsInitiateDFRDao.saveOrUpdateBatchData(cxiErrorSVList);
		}
		return null;
	}

}





