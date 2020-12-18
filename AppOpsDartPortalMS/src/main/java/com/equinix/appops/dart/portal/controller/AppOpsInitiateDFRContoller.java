package com.equinix.appops.dart.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.buisness.AppOpsEditDfrBusiness;
import com.equinix.appops.dart.portal.buisness.AppOpsInitiateDfrBusiness;
import com.equinix.appops.dart.portal.common.CaplogixSyncResponse;
import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.DartSoapAuditModel;
import com.equinix.appops.dart.portal.model.dfr.DfrDaInput;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.service.AppOpsDartDaService;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
import com.equinix.appops.dart.portal.service.SVSyncVService;

@RestController
@RequestMapping(value={"/dfr"})
public class AppOpsInitiateDFRContoller extends BaseContorller {
	
	Logger logger = LoggerFactory.getLogger(AppOpsInitiateDFRContoller.class);
	
	@Autowired
	AppOpsInitiateDfrBusiness appOpsInitiateDFRBusiness;
	
	@Autowired
	SVSyncVService svService;
	
	@Autowired
	AppOpsEditDfrBusiness appOpsEditBusiness;
	
	@Autowired
	AppOpsDartEditDfrService editDfrService;
	
	@Autowired
	AppOpsInitiateDFRService appOpsInitiateDFRService;
	
	@Autowired
	AppOpsDartDaService appOpsDartDaService;
	
	@RequestMapping(value = "/initiate", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> initiateDfr(@RequestBody DfrDaInput dfrDaInput ,HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp ) {long start =  System.currentTimeMillis(); 
	try{
	HashMap<String,Object> dfrResult= appOpsInitiateDFRBusiness.initiateDfr(dfrDaInput);
	long end =  System.currentTimeMillis();  
	
	 HashMap<String,String> resultMap = new HashMap<>();
	 String dfrId = (String)dfrResult.get("dfrid");
	 if(dfrResult.containsKey("error")){
		 resultMap.put("message", (String)dfrResult.get("error"));
	 }else {
		 logger.info("==>> dfr created with " + (String)dfrResult.get("dfrid") + " in " + (end-start) ); 
		 resultMap.put("dfrid", (String)dfrResult.get("dfrid"));
		 resultMap.put("message", (String)dfrResult.get("message"));
		 ProductFilterResult searchResulstData =(ProductFilterResult) dfrResult.get(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS);	
		 
		new Thread(() -> {
			
			try{
				
				Set<String> sblRowId = searchResulstData.getSblRowIds();
				Set<String> cageRowIds = searchResulstData.getClxCageRowIds();
				Set<String> cabRowIds = searchResulstData.getClxCabninetRowIds();
				Set<String> cabDpRowIds = searchResulstData.getClxCabninetDpRowIds();
				long start1 = System.currentTimeMillis();
				long start2 = System.currentTimeMillis();
				logger.info("==>>> Sync Sbl Asset starts :" + sblRowId.size() );
				if(CollectionUtils.isNotEmpty(sblRowId)){
					appOpsInitiateDFRBusiness.syncAssets(sblRowId, dfrId);
				}
				long end1 = System.currentTimeMillis();
				
				logger.info("==>>> Sync Sbl Asset end" + sblRowId.size() +  " time :" + (end1-start1) );
				start1 = System.currentTimeMillis();
				logger.info("==>>> Sync Clx cabinets starts : " +cabRowIds.size() );
				if(CollectionUtils.isNotEmpty(cabRowIds)){
					appOpsInitiateDFRBusiness.syncCabinates(cabRowIds, dfrId);
				}
				end1 = System.currentTimeMillis();
				logger.info("==>>> Sync Clx cabinets end : " +cabRowIds.size() + " time :" + (end1-start1) );
				
				start1 = System.currentTimeMillis();
				logger.info("==>>> Sync Clx dp cabinets starts : " +cabRowIds.size() );
				if(CollectionUtils.isNotEmpty(cabDpRowIds)){
					
					
					appOpsInitiateDFRBusiness.syncCabinates(cabDpRowIds, dfrId);
				}
				end1 = System.currentTimeMillis();
				logger.info("==>>> Sync Clx dp cabinets end : " +cabRowIds.size() + " time :" + (end1-start1) );
				
				logger.info("==>>> Sync Clx cage starts : " +cageRowIds.size() );
				start1 = System.currentTimeMillis();
				if(CollectionUtils.isNotEmpty(cageRowIds)){
				appOpsInitiateDFRBusiness.syncCages(cageRowIds, dfrId);
				}
				end1 =  System.currentTimeMillis();
				logger.info("==>>> Sync Clx cage ends : " +cageRowIds.size() );
				
				appOpsEditBusiness.saveDfrDetails(dfrId, "asyncVal", "Y");
				end1 =  System.currentTimeMillis();
				logger.info("==>>> DFR : "+dfrId+ " updated Y time : " + (end1-start2)   );
		
			}catch(Exception e){
				appOpsEditBusiness.saveDfrDetails(dfrId, "asyncVal", "N");
				logger.info("Error in moving asset" , e);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
			
			
			}).start();
		
	 }
	 return buildResponse(resultMap, "Available", "Not Available");
	}
	catch (Exception e) {
		String uuid = UUID.randomUUID().toString();
		logger.error(uuid,e);
		logger.error("UUID : "+uuid+" Error : "+e.getMessage(), e);
		return buildErrorResponse(e.getMessage(), uuid);
	}finally {
		HttpClientUtils.closeQuietly(httpResp);
	}
	}
	
	/**
	 * Sandeep Singh
	 * Initiate dfr for all products new API
	 * 
	 * @param searchFilters
	 * @param req
	 * @param resp
	 * @param httpResp
	 * @return
	 */
	@RequestMapping(value = "/initiateall", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> initiateDfrAll(@RequestBody ProductFilter searchFilters, HttpServletRequest req,
			HttpServletResponse resp, HttpResponse httpResp) {
		long start = System.currentTimeMillis();

		DfrDaInput dfrDaInput = null;
		HashMap<String, String> resultMap = new HashMap<>();
		try {
			dfrDaInput = appOpsDartDaService.createInitiateDfrDaInputObject(searchFilters);

			/**
			 * Injecting dfrdainput object into initiate dfr method
			 */
			if (CacheConstant.IS_INTIATE_WITH_SELECTED_ASSET) {

				HashMap<String, Object> dfrResult = appOpsInitiateDFRBusiness.initiateDfr3(dfrDaInput);
				long end = System.currentTimeMillis();
				String dfrId = (String) dfrResult.get("dfrid");
				if (dfrResult.containsKey("error")) {
					resultMap.put("message", (String) dfrResult.get("error"));
				} else {
					logger.info("==>> dfr created with " + (String) dfrResult.get("dfrid") + " in " + (end - start));
					resultMap.put("dfrid", (String) dfrResult.get("dfrid"));
					resultMap.put("message", (String) dfrResult.get("message"));
					ProductFilterResult searchResulstData = (ProductFilterResult) dfrResult
							.get(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS);
					new Thread(() -> {

						try {

							// copy non selected assets in snapshots

							appOpsInitiateDFRBusiness.copyNonSelectedAssets(dfrResult);

/*							Set<String> sblRowId = searchResulstData.getSblRowIds();
							Set<String> cageRowIds = searchResulstData.getClxCageRowIds();
							Set<String> cabRowIds = searchResulstData.getClxCabninetRowIds();
							Set<String> cabDpRowIds = searchResulstData.getClxCabninetDpRowIds();
							long start1 = System.currentTimeMillis();
							long start2 = System.currentTimeMillis();
							logger.info("==>>> Sync Sbl Asset starts :" + sblRowId.size());
							if (CollectionUtils.isNotEmpty(sblRowId)) {
								appOpsInitiateDFRBusiness.syncAssets(sblRowId, dfrId);
							}
							long end1 = System.currentTimeMillis();

							logger.info("==>>> Sync Sbl Asset end" + sblRowId.size() + " time :" + (end1 - start1));
							start1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx cabinets starts : " + cabRowIds.size());
							if (CollectionUtils.isNotEmpty(cabRowIds)) {
								appOpsInitiateDFRBusiness.syncCabinates(cabRowIds, dfrId);
							}
							end1 = System.currentTimeMillis();
							logger.info(
									"==>>> Sync Clx cabinets end : " + cabRowIds.size() + " time :" + (end1 - start1));

							start1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx dp cabinets starts : " + cabRowIds.size());
							if (CollectionUtils.isNotEmpty(cabDpRowIds)) {
								appOpsInitiateDFRBusiness.syncCabinates(cabDpRowIds, dfrId);
							}
							end1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx dp cabinets end : " + cabRowIds.size() + " time :"
									+ (end1 - start1));

							logger.info("==>>> Sync Clx cage starts : " + cageRowIds.size());
							start1 = System.currentTimeMillis();
							if (CollectionUtils.isNotEmpty(cageRowIds)) {
								appOpsInitiateDFRBusiness.syncCages(cageRowIds, dfrId);
							}
							end1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx cage ends : " + cageRowIds.size());*/

							appOpsEditBusiness.saveDfrDetails(dfrId, "asyncVal", "Y");
							/*end1 = System.currentTimeMillis();
							logger.info("==>>> DFR : " + dfrId + " updated Y time : " + (end1 - start2));*/

						} catch (Exception e) {
							appOpsEditBusiness.saveDfrDetails(dfrId, "asyncVal", "N");
							logger.info("Error in moving asset", e);
						} finally {
							HttpClientUtils.closeQuietly(httpResp);
						}
					}).start();
				}

			} else {
				HashMap<String, Object> dfrResult = appOpsInitiateDFRBusiness.initiateDfr(dfrDaInput);
				long end = System.currentTimeMillis();

				String dfrId = (String) dfrResult.get("dfrid");
				if (dfrResult.containsKey("error")) {
					resultMap.put("message", (String) dfrResult.get("error"));
				} else {
					logger.info("==>> dfr created with " + (String) dfrResult.get("dfrid") + " in " + (end - start));
					resultMap.put("dfrid", (String) dfrResult.get("dfrid"));
					resultMap.put("message", (String) dfrResult.get("message"));
					ProductFilterResult searchResulstData = (ProductFilterResult) dfrResult
							.get(DartConstants.DFR_SNAPSHOT_FILTER_SEARCH_RESULTS);

					new Thread(() -> {

						try {

							Set<String> sblRowId = searchResulstData.getSblRowIds();
							Set<String> cageRowIds = searchResulstData.getClxCageRowIds();
							Set<String> cabRowIds = searchResulstData.getClxCabninetRowIds();
							Set<String> cabDpRowIds = searchResulstData.getClxCabninetDpRowIds();
							long start1 = System.currentTimeMillis();
							long start2 = System.currentTimeMillis();
							logger.info("==>>> Sync Sbl Asset starts :" + sblRowId.size());
							if (CollectionUtils.isNotEmpty(sblRowId)) {
								appOpsInitiateDFRBusiness.syncAssets(sblRowId, dfrId);
							}
							long end1 = System.currentTimeMillis();

							logger.info("==>>> Sync Sbl Asset end" + sblRowId.size() + " time :" + (end1 - start1));
							start1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx cabinets starts : " + cabRowIds.size());
							if (CollectionUtils.isNotEmpty(cabRowIds)) {
								appOpsInitiateDFRBusiness.syncCabinates(cabRowIds, dfrId);
							}
							end1 = System.currentTimeMillis();
							logger.info(
									"==>>> Sync Clx cabinets end : " + cabRowIds.size() + " time :" + (end1 - start1));

							start1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx dp cabinets starts : " + cabRowIds.size());
							if (CollectionUtils.isNotEmpty(cabDpRowIds)) {
								appOpsInitiateDFRBusiness.syncCabinates(cabDpRowIds, dfrId);
							}
							end1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx dp cabinets end : " + cabRowIds.size() + " time :"
									+ (end1 - start1));

							logger.info("==>>> Sync Clx cage starts : " + cageRowIds.size());
							start1 = System.currentTimeMillis();
							if (CollectionUtils.isNotEmpty(cageRowIds)) {
								appOpsInitiateDFRBusiness.syncCages(cageRowIds, dfrId);
							}
							end1 = System.currentTimeMillis();
							logger.info("==>>> Sync Clx cage ends : " + cageRowIds.size());

							appOpsEditBusiness.saveDfrDetails(dfrId, "asyncVal", "Y");
							end1 = System.currentTimeMillis();
							logger.info("==>>> DFR : " + dfrId + " updated Y time : " + (end1 - start2));

						} catch (Exception e) {
							appOpsEditBusiness.saveDfrDetails(dfrId, "asyncVal", "N");
							logger.info("Error in moving asset", e);
						} finally {
							HttpClientUtils.closeQuietly(httpResp);
						}

					}).start();
				}
			}
			return buildResponse(resultMap, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			logger.error("UUID : " + uuid + " Error : " + e.getMessage(), e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/byid/{dfrId}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> initiateDfr(@PathVariable String dfrId ,HttpServletRequest req , HttpServletResponse resp) {
		//DfrMaster dfrMaster = appOpsInitiateDFRService.getDfrById(dfrId);
		//DfrMaster dfrMaster =  appOpsEditBusiness.getDfrMasterByDfrId(dfrId);
		return buildResponse(null, "Available", "Not Available");
	 }
	
		
	@RequestMapping(value = "/sv/syncdfr", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> syncSVDfr(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		CaplogixSyncResponse response = new CaplogixSyncResponse();
		String dfrId= req.getParameter("dfrId");
		try{
			if(StringUtils.isNotBlank(dfrId)){
				response = appOpsInitiateDFRBusiness.syncSVDfr(dfrId);
			} else {
				response.setError(true);
				response.setMessage("dfr id cannot leave blank.");
			}
		}catch(Exception ex){
			response.setError(true);
			response.setMessage("Unable to push data to SV topic:"+dfrId);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(response, "Available", "Not Available");
	}
	
	@RequestMapping(value = "/test/sv/syncdfr", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> testSyncSVDfr(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		Object response = new CaplogixSyncResponse();
		try {
			String type=req.getParameter("type");
			String dfrId=req.getParameter("dfrId"); //9999
			if(StringUtils.isBlank(type) || type.equalsIgnoreCase("1")){
				
				response = appOpsInitiateDFRBusiness.syncSVDfr(dfrId);
			} else {
				response=appOpsInitiateDFRBusiness.syncSVDfrKafkaConsumer(new DFRKafkaMessageVO(dfrId));
			}

			return buildCollectionResponse(response, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/test/caplogix/syncdfr", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> syncCaplogixDfr(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
		Object response = new CaplogixSyncResponse();
		try {
			String type=req.getParameter("type");
			String dfrId=req.getParameter("dfrId"); //9999
			String dfrrequestId= req.getParameter("dfrRequstId"); //9999
			if(StringUtils.isBlank(type) || type.equalsIgnoreCase("1")){
				response = appOpsInitiateDFRBusiness.syncCaplogixDfr(dfrId);
			}else{
				DFRKafkaMessageVO requestVo = new DFRKafkaMessageVO(dfrId);
				if(StringUtils.isNotBlank(dfrrequestId)){
					requestVo.setDfrRequestId(dfrrequestId);
				}
				response=appOpsInitiateDFRBusiness.syncCLXDfrKafkaConsumer(requestVo);
			}
		} catch (Exception e) {			
			logger.error("unable to validate:",e);
		}
		 return buildCollectionResponse(response, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	
	@RequestMapping(value = "/test/siebel/syncdfr", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> syncSiebelDfr(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			Object response = null;
			try {
				String type=req.getParameter("type");
				String dfrId=req.getParameter("dfrId"); //9999
				String dfrrequestId=req.getParameter("dfrRequstId"); //9999
				if(StringUtils.isBlank(type) || type.equalsIgnoreCase("1")){
					response = appOpsInitiateDFRBusiness.syncSiebelDfr(dfrId);	
				}else {
					DFRKafkaMessageVO requestVo = new DFRKafkaMessageVO(dfrId);
					if(StringUtils.isNotBlank(dfrrequestId)){
						requestVo.setDfrRequestId(dfrrequestId);
					}
					response=appOpsInitiateDFRBusiness.syncSBLDfrKafkaConsumer(requestVo);
				}
			} catch (Exception e) {			
				logger.error("unable to validate:",e);
			}
			return buildCollectionResponse(response, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/refreshCache", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> refreshErrorCache(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			String status = appOpsInitiateDFRBusiness.refreshErrors();
		 return buildCollectionResponse(status, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getAllSoapReq", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<DartSoapAuditModel>>> getAllSoapReq(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
		List<DartSoapAuditModel> audits = appOpsInitiateDFRService.getAllSoapAuditReq();
		 return buildCollectionResponse(audits, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getSoapReqDfrOrPro", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<DartSoapAuditModel>>> getSoapReqDfrOrPro(
			@RequestParam("dfrId") String dfrId,
			@RequestParam("product") String product,
			HttpServletRequest req ,
			HttpServletResponse resp, HttpResponse httpResp) {
		try{
		List<DartSoapAuditModel> audits = 
				appOpsInitiateDFRService.getAuditsDfrorProduct(dfrId, product);
		 return buildCollectionResponse(audits, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/test/nocc/syncdfr", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> syncNoccDfr(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			Object response = null;
			try {
				String dfrId=req.getParameter("dfrId"); //9999
				String dfrrequestId= req.getParameter("dfrRequstId"); //9999
				DFRKafkaMessageVO requestVo = new DFRKafkaMessageVO(dfrId);
				if(StringUtils.isNotBlank(dfrrequestId)){
					requestVo.setDfrRequestId(dfrrequestId);
				}
				response=appOpsInitiateDFRBusiness.syncSBLDfrKafkaConsumer(requestVo);
			} catch (Exception e) {			
				logger.error("unable to validate:",e);
			}
			return buildCollectionResponse(response, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
}
