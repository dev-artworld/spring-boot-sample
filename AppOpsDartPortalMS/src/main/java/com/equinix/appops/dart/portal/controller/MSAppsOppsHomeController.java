package com.equinix.appops.dart.portal.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.buisness.AppOpsAccountMovementBusiness;
import com.equinix.appops.dart.portal.buisness.AppOpsEditDfrBusiness;
import com.equinix.appops.dart.portal.buisness.AppOpsInitiateDfrBusiness;
import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.DartResource;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.Recon2Sync;
import com.equinix.appops.dart.portal.entity.UserInfo;
import com.equinix.appops.dart.portal.entity.XmlConfig;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetResponse;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;
import com.equinix.appops.dart.portal.mapper.dto.pro.AssetCount;
import com.equinix.appops.dart.portal.model.Dfr;
import com.equinix.appops.dart.portal.model.DfrsByStatus;
import com.equinix.appops.dart.portal.model.EmailAuditModel;
import com.equinix.appops.dart.portal.model.Ibx;
import com.equinix.appops.dart.portal.model.ReportFilter;
import com.equinix.appops.dart.portal.model.TeamRegion;
import com.equinix.appops.dart.portal.model.dfr.DfrSummaryInput;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.service.AppOppsDartHomeService;
import com.equinix.appops.dart.portal.service.AppOpsCommonService;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.EmailSenderService;
import com.equinix.appops.dart.portal.service.SVSyncVService;
import com.equinix.appops.dart.portal.service.UserService;
import com.equinix.appops.dart.portal.service.XmlConfigService;
import com.equinix.appops.dart.portal.util.DartUtil;
import com.equinix.appops.dart.portal.vo.DARTReportVO;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = { "/home" })
public class MSAppsOppsHomeController extends BaseContorller {

	Logger logger = LoggerFactory.getLogger(MSAppsOppsHomeController.class);

	Gson gson = new Gson();

	@Autowired
	AppOpsAccountMovementBusiness appOpsAccountMovementBusiness;
	
	@Autowired
	AppOpsInitiateDfrBusiness appOpsInitiateDFRBusiness;
	
	@Autowired
	AppOpsEditDfrBusiness appOpsEditBusiness;
	
	@Autowired
	AppOppsDartHomeService appOppsDartHomeService;

	@Autowired
	AppOpsCommonService appOpsCommonService;

	@Autowired
	EmailSenderService emailSenderService;

	@Autowired
	ConfigService configService;

	@Autowired
	UserService userService;

	@Autowired
	SVSyncVService svSyncVService;
	
	@Autowired
	XmlConfigService xmlConfigService;
	
	@Autowired
	AppOpsDartEditDfrService appOpsDartEditDfrService;
	
	@Autowired
	AppOpsDartEditDfrService editDfrService;

	@RequestMapping(value = "/alldfrorderedbycreateddatedesc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<DfrMaster>>> getAllDfr(HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
		try{
		List<Dfr> dfrList = appOppsDartHomeService.getAllDfrOrderedByCreatedDtDesc();
		return buildCollectionResponse(dfrList, "Available", "Not Available");
		}catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/allteamregions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<TeamRegion>> getTeamRegions(HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try{
		TeamRegion teamRegion = appOppsDartHomeService.getTeamRegions();
		return buildResponse(teamRegion, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/allorbyregionibx/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Ibx>> getIbxByRegion(@PathVariable("region") String region,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		Ibx ibx = appOppsDartHomeService.getIbxByRegion(region);
		return buildResponse(ibx, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/searchbydfrid/{dfrid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrMaster>> getDfrById(@PathVariable("dfrid") String dfrid,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		DfrMaster result = appOpsDartEditDfrService.getDfrMasterById(dfrid);
		
		return buildResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/dfrstatsbyteamorregion/{team}/{region}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Map<String, Map<String, Integer>>>> dfrStatsByTeamOrRegion(
			@PathVariable("team") String team, @PathVariable("region") String region, HttpServletRequest req,
			HttpServletResponse resp,HttpResponse httpResp) {
		try{
		Map<String, Map<String, String>> result = appOppsDartHomeService.getDfrStatsByTeamOrRegion(team, region);
		return buildCollectionResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/dfrStatsByTeamAndRegionAndIbx/{team}/{region}/{ibx}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Map<String, Map<String, Integer>>>> dfrStatsByTeamAndRegionAndIbx(
			@PathVariable("team") String team, @PathVariable("region") String region, @PathVariable("ibx") String ibx, HttpServletRequest req,
			HttpServletResponse resp,HttpResponse httpResp) {
		try{
		Map<String, Map<String, String>> result = appOppsDartHomeService.getDfrStatsByTeamANDRegionAndIbx(team, region, ibx);
		return buildCollectionResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/dfrbyteamorregionandstatus/{team}/{region}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> dfrbystatus(@PathVariable("team") String team,
			@PathVariable("region") String region, @PathVariable("status") String status, HttpServletRequest req,
			HttpServletResponse resp,HttpResponse httpResp) {
		try{
		DfrsByStatus result = appOppsDartHomeService.getDfrsByStatus(status, team, region);
		return buildCollectionResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/dfrbyteamorregionandstatusandibx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> dfrbyIbx(@RequestBody DfrSummaryInput dfrSummaryInput,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {

		try {
			DfrsByStatus result = appOppsDartHomeService.getDfrsByIbx(dfrSummaryInput.getStatus(),
					dfrSummaryInput.getTeam(), dfrSummaryInput.getRegion(), dfrSummaryInput.getIbx());
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	/*@RequestMapping(value = "/dfrbyteamorregionandstatusandibx/{team}/{region}/{ibx}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> dfrbyIbx(@PathVariable("team") String team,
			@PathVariable("region") String region, @PathVariable("ibx") String ibx,
			@PathVariable("status") String status, HttpServletRequest req, HttpServletResponse resp) {
		try{
		DfrsByStatus result = appOppsDartHomeService.getDfrsByIbx(status, team, region, ibx);
		return buildCollectionResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}
	}*/

	@RequestMapping(value = "/validateDFR", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> validateDFR(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		String response;
		try {
			String dfrNumber = req.getParameter("dfrNumber");
			if (StringUtils.isEmpty(dfrNumber)) {
				return buildCollectionResponse("dfrNumber cannot blank.", "Available", "Not Available");
			}
			//path+"/exec_oracle_script_multiple.sh \"*.sql\" "+ path+" CONQA DART"; 
			String path = configService.getValueByKey("VALIDATATION_SCRIPT_PATH");
			if(!path.endsWith("/")){
				path = path+"/";
			}
			response = DartUtil.executeShellWithSUDO(configService.getValueByKey("VALIDATATION_SCRIPT_HOST"), 
					configService.getValueByKey("VALIDATATION_SCRIPT_USERNAME"),
					configService.getValueByKey("VALIDATATION_SCRIPT_PASSWORD"),
					new String[] { path+"scripts/exec_oracle_script_multiple.sh \"*.sql\" "+ path+"sqlmod CONQA DART " + dfrNumber });
		} catch (Exception e) {
			response = e.getMessage();
			logger.error("unable to validate:", e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
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

	@RequestMapping(value = "/test/alert", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> testAlert(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		XmlConfig response = new XmlConfig();
		try {
			String type = req.getParameter("type");			
			String recepients = req.getParameter("recepients");
			if(type == null || "1".equalsIgnoreCase(type)){
				appOpsCommonService.sendSampleAlert(recepients);
			} else if("2".equalsIgnoreCase(type)){
				String recepientList ="";
				String templateKey="DART_REQ_NOTIFICATION";
				HashMap<String, String> dataMap = new HashMap<String, String>();					
				dataMap.put("UER_ID", "UER_ID");//ASSIGNED GROUP TEAM
				dataMap.put("REQUESTED_ITEM", "REQUESTED_ITEM");
				dataMap.put("DFR", "DFR");
				dataMap.put("SUBJECT", "DART TEST EMAIL #DFRID");
				dataMap.put("REQUESTED_BY", "REQUESTED_BY");
				dataMap.put("INCIDENT", "INCIDENT");
				dataMap.put("REGION", "REGION");
				dataMap.put("NOTES", "NOTES");
				dataMap.put("LINK", "TODO:");
				dataMap.put("NO_OF_ASSETS","NO_OF_ASSETS");
				emailSenderService.sendAlert(recepientList,templateKey,dataMap);
			}

		} catch (Exception e) {
			logger.error("unable to validate:", e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
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

	@RequestMapping(value = "/test/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> getValueByKey(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		String value = "";
		try {
			String key = req.getParameter("key");
			value = configService.getValueByKey(key);
		} catch (Exception e) {
			logger.error("unable to validate:", e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(value, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/test/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> getUser(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		UserInfo user = new UserInfo();
		try {
			String userId = req.getParameter("userId");
			user = userService.getUser(userId);
		} catch (Exception e) {
			logger.error("unable to validate:", e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(user, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> getCurrentUser(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		// Object userobj = req.getSession().getAttribute("user");
		try{
		User user = null;
		try {
			user = UserThreadLocal.userThreadLocalVar.get();
		} catch (Exception e) {
			logger.error("unable to validate:", e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(user, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> logoutUser(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		if (UserThreadLocal.userThreadLocalVar.get() != null) {
			UserThreadLocal.userThreadLocalVar.remove();
		}
		if (req.getSession().getAttribute("user") != null) {
			req.getSession().removeAttribute("user");
		}
		req.getSession().invalidate();
		return buildCollectionResponse("Logout Successfully", "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/getDfrIdList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<Dfr>>> getDfrIdList(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		List<String> result = appOppsDartHomeService.getDfrIdList();
		return buildCollectionResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/getSVSyncV", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> getSVSyncV(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		String dfrId = req.getParameter("dfrId");
		List<Recon2Sync> result = svSyncVService.getSvSyncV(dfrId);
		return buildCollectionResponse(result, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	

	@RequestMapping(value = "/getAllRecentDfr/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> getRecentDfr(@PathVariable("userId") String userId, HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		DfrsByStatus dfrByStatus = appOppsDartHomeService.getRecentDfr(userId);
		return buildCollectionResponse(dfrByStatus, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/getAllEmailAlerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<EmailAuditModel>>> getAllEmaillAlerts(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			List<EmailAuditModel> alerts = emailSenderService.allSentAlert();
			//throw new Exception("User Genereated Exception");
			return buildCollectionResponse(alerts, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/test/error/{error}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<String>>> testErrorHandling(
			@PathVariable("error") String error,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		if(error.equals("y")){
			throw new Exception("User Generate Exception - UserException");
		}
		else{
			return buildCollectionResponse("API Working Fine.", "Available", "Not Available");
		}
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		
	}
	
	
	@RequestMapping(value = "/loadcache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testErrorHandling(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			
			configService.loadConfigSingletonContainer();
		}catch(Exception e){
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
			
		return "done";
	}
	
	@RequestMapping(value = "/xml/loadcache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String xmlConfigReload(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			xmlConfigService.loadAllEmailTemplates();
	}catch(Exception e){
		String uuid = UUID.randomUUID().toString();
		logger.error(uuid,e);
	}finally {
		HttpClientUtils.closeQuietly(httpResp);
	}
			return "done";
	}

	@RequestMapping(value = "/getAllDartResources", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<DartResource>>> getAllDartResources(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		List<DartResource> dartResources = appOppsDartHomeService.getAllDartResources();
		return buildCollectionResponse(dartResources, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/getDFRReport", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getDFRMasterByTeamRegionIBXDate(@RequestParam("region") String region,@RequestParam("ibx") String ibx,
			@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate,
			HttpServletResponse resp) throws IOException {
		List<DARTReportVO> dartReportVOList = null;
		Workbook workbook = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayInputStream inputStream = null;
		OutputStream outStream = null;
		ResponseEntity<InputStreamResource> response = null;
		try {
			ReportFilter reportFilter = new ReportFilter();
			/*reportFilter.setTeam(team);*/
			reportFilter.setRegion(region);
			reportFilter.setIbx(ibx);
			if (StringUtils.isNotEmpty(fromDate)) {
				reportFilter.setFromDate(new SimpleDateFormat("dd-MM-yyyy").parse(fromDate));
			}
			if (StringUtils.isNotEmpty(toDate)) {
				reportFilter.setToDate(new SimpleDateFormat("dd-MM-yyyy").parse(toDate));
			}
			ClassLoader classLoaderObj = getClass().getClassLoader();
			workbook = new XSSFWorkbook(classLoaderObj.getResourceAsStream("DART_DFR_Report_V1.0.xlsx"));
			dartReportVOList = appOppsDartHomeService.getDFRMasterByTeamRegionIBXDate(reportFilter);
			CellStyle borderCellStyle = getBorderCellStyle(workbook);
			XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet("DFR_Report");
			for (int i = 1; i < dartReportVOList.size() + 1; i++) {
				Row currentRow = sheetObj.createRow(i);
				DARTReportVO dartReportVO = dartReportVOList.get(i - 1);
				currentRow.createCell(0).setCellValue(dartReportVO.getDfrId());
				currentRow.getCell(0).setCellStyle(borderCellStyle);
				currentRow.createCell(1).setCellValue(dartReportVO.getCreatedBy());
				currentRow.getCell(1).setCellStyle(borderCellStyle);
				currentRow.createCell(2).setCellStyle(getDateCellType(workbook));
				currentRow.getCell(2).setCellValue(dartReportVO.getCreatedDate());
				currentRow.createCell(3).setCellValue(dartReportVO.getCreatedTeam());
				currentRow.getCell(3).setCellStyle(borderCellStyle);
				currentRow.createCell(4).setCellValue(dartReportVO.getIncident());
				currentRow.getCell(4).setCellStyle(borderCellStyle);
				currentRow.createCell(5).setCellValue(dartReportVO.getAssignedTeam());
				currentRow.getCell(5).setCellStyle(borderCellStyle);
				currentRow.createCell(6).setCellValue(dartReportVO.getNotes());
				currentRow.getCell(6).setCellStyle(borderCellStyle);
				currentRow.createCell(7).setCellValue(dartReportVO.getStatus());
				currentRow.getCell(7).setCellStyle(borderCellStyle);
				currentRow.createCell(8).setCellStyle(getDateCellType(workbook));
				currentRow.getCell(8).setCellValue(dartReportVO.getAssignedDate());
				currentRow.createCell(9).setCellValue(dartReportVO.getRegion());
				currentRow.getCell(9).setCellStyle(borderCellStyle);
				currentRow.createCell(10).setCellValue(dartReportVO.getIbx());
				currentRow.getCell(10).setCellStyle(borderCellStyle);
				currentRow.createCell(11).setCellValue(dartReportVO.getCountry());
				currentRow.getCell(11).setCellStyle(borderCellStyle);
			}
			workbook.write(outputStream);
			byte[] outArray = outputStream.toByteArray();
			inputStream = new ByteArrayInputStream(outArray);
			resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			resp.setHeader("Content-Disposition", "attachment; filename=DART_DFR_Report.xlsx");
			resp.setContentLength(outArray.length);
			resp.setHeader("Expires:", "0"); // Eliminates Browser Caching
			outStream = resp.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			response.status(HttpStatus.OK);
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, ex);
			logger.error("UUID : " + uuid + " Error : " + ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (outStream != null) {
				outStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return response;
	}
	
	public CellStyle getDateCellType (Workbook workbookObj) {
		CellStyle cellStyle = workbookObj.createCellStyle();
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		CreationHelper helper = workbookObj.getCreationHelper();
		cellStyle.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
		return cellStyle;
	}
	
	public CellStyle getBorderCellStyle (Workbook workbookObj) {
		CellStyle cellStyle = workbookObj.createCellStyle();
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;
	}
		
	/*@Autowired
	Client elasticClient;
		
	
	@RequestMapping(value = "/test/elastic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity testElastic(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		String result =null;
		try{

			System.out.println(elasticClient);
			SearchResponse response = elasticClient.prepareSearch("darterrorbucket").setTypes("DartError")
					.setSearchType(SearchType.QUERY_AND_FETCH).get();
			List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
			System.err.println("=========== Get values from Elastic ===========");
			for (SearchHit searchHit : searchHits) {
				result+=","+searchHit.getSourceAsString();
				System.out.println(searchHit.getSourceAsMap());
			}
			System.err.println("=========== Done ===========");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buildCollectionResponse(result, "Available", "Not Available");
	}*/
	
	@RequestMapping(value = "/test/EMail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<HashMap<String, String>>>> testEMail (HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try {
			List<HashMap<String, String>> eMailDFRMap = new ArrayList<>();
			String emailFlag = configService.getValueByKey(LogicConstants.DFR_EMAIL_NOTIFICATION_FLAG);
			HashMap<String, String> dataMap = null;
			if ("ON".equalsIgnoreCase(emailFlag)) {
				List<DfrMaster> dfrs = editDfrService.getDfrMasterCompleted("N");
				logger.info("DFR Emails:" + (dfrs != null ? dfrs.size() : 0));
				for (DfrMaster dfrMaster : dfrs) {
					try {
						DfrMaster dfrMasterObj = editDfrService.getDfrMaster(dfrMaster.getDfrId(), "join");
						String editDfrLink = configService.getValueByKey("EDIT_DFR_LINK");
						String recepientList = editDfrService.getEmailIdByAssignGroup(dfrMaster.getAssignedTeam());
						String templateKey = "DART_REQ_NOTIFICATION";
						dataMap = populatePlaceHolders(dfrMaster, editDfrLink);
						StringBuilder approvedBy = null;
						if (dfrMasterObj != null && CollectionUtils.isNotEmpty(dfrMasterObj.getApprovalHistories())) {
							List<ApprovalHistory> apprHistoryList = dfrMasterObj.getApprovalHistories().stream()
									.filter(historyObj -> "Approved".equalsIgnoreCase(historyObj.getStatus()))
									.collect(Collectors.toList());
							if (apprHistoryList != null && CollectionUtils.isNotEmpty(apprHistoryList)) {
								logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()---> Approved History Size ### "+apprHistoryList.size());
								List<String> oldReceipientList = null;
								if (StringUtils.isNotEmpty(recepientList)) {
									String[] oldMailArray = recepientList.split(",");
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
									if (StringUtils.isNotEmpty(histObj.getCreatedBy())
											&& StringUtils.isNotEmpty(histObj.getAssignedTeam())) {
										UserInfo userObj = userService.getUser(histObj.getCreatedBy());
										logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()---> Assigned Team ###"+histObj.getAssignedTeam()+
												"; Approved By ###"+histObj.getCreatedBy());
										if (userObj != null) {
											if (approvedBy == null) {
												approvedBy = new StringBuilder();
											}
											if (approvedBy != null && approvedBy.length() > 0) {
												approvedBy.append(";");
											}
											approvedBy.append(histObj.getAssignedTeam() + " - " + userObj.getEmailId());
										}
									}
								}
								if (CollectionUtils.isNotEmpty(oldReceipientList)) {
									recepientList = String.join(",", oldReceipientList);
								}
							}
						}
						if (approvedBy != null && approvedBy.length() > 0) {
							dataMap.put("APPROVED_BY", approvedBy.toString());
						} else {
							dataMap.put("APPROVED_BY","");
						}
						logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()--->"+dataMap.get("APPROVED_BY")+";");
						if (dfrMaster.getStatus().equalsIgnoreCase(LogicConstants.CANCELLED)) {
							templateKey = "DART_DFR_CANCEL_NOTIFICATION";
							dataMap.put("USER_ID", dfrMaster.getAssignedTeam());
							dataMap.put("REQUESTED_ITEM",
									null == dfrMaster.getCreatedTeam() ? "" : dfrMaster.getCreatedTeam());
						}
						eMailDFRMap.add(dataMap);
						emailSenderService.sendAlert(recepientList, templateKey, dataMap);
						dfrMaster.setEmailFlag("Y");
						editDfrService.saveOrUpdateDfrMaster(dfrMaster);
					} catch (Exception ex) {
						logger.error("Unable to send alert for DFR # " + dfrMaster.getDfrId(), ex);
					}
				}
			} else {
				logger.info("Unable to send E-Mail due to E-Mail Flag:" + emailFlag);
			}
			return buildResponse(eMailDFRMap,"Available","Not Available");
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error("Unable to get DFR's while sending E-Mail Alert...", ex);
			logger.error(uuid, ex);
			logger.error("UUID : " + uuid + " Error : " + ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		}
	}
	
	private HashMap<String, String> populatePlaceHolders(DfrMaster dfrMaster, String editDfrLink) {
		HashMap<String, String> dataMap = new HashMap<String, String>();					
		dataMap.put("USER_ID", dfrMaster.getAssignedTeam());//ASSIGNED GROUP TEAM
		dataMap.put("REQUESTED_ITEM", null==dfrMaster.getAssignedTo()?"":dfrMaster.getAssignedTo());
		dataMap.put("DFR", dfrMaster.getDfrId());
		dataMap.put("SUBJECT", "DART Request "+dfrMaster.getStatus()+" for "+dfrMaster.getDfrId());
		dataMap.put("REQUESTED_BY", null==dfrMaster.getCreatedBy()?"":dfrMaster.getCreatedBy());
		dataMap.put("INCIDENT", null==dfrMaster.getIncident()?"":dfrMaster.getIncident());
		dataMap.put("REGION", dfrMaster.getRegion());
		dataMap.put("NOTES", null==dfrMaster.getNotes()?"":dfrMaster.getNotes());
		dataMap.put("LINK", null==editDfrLink?"":"<a href=\""+editDfrLink+dfrMaster.getDfrId()+"\">click here</a>");
		dataMap.put("STATUS", dfrMaster.getStatus());
		return dataMap;
	}
	
	@RequestMapping(value = "/getRootAssetId/{accountId}/{cageUSID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getRootAssestId(@PathVariable String accountId,
			@PathVariable String cageUSID,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			List<RootAsset> rootAsset = appOpsAccountMovementBusiness.getRootAssestId(accountId, cageUSID);
			return buildCollectionResponse(rootAsset, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/getOpsHierarchy/{rootAssesstId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getRootAssestId1(@PathVariable String rootAssesstId,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			OpsHierarchyAsset opsHierarchy = appOpsAccountMovementBusiness.getOpsHierarchyByRootAssesstId(rootAssesstId);
			return buildCollectionResponse(opsHierarchy.getCages(), "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/accountmove/attrview/{dfrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getAttrViewByDFR(@PathVariable String dfrId,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			ProductDataGrid dataGrid = appOpsDartEditDfrService.getAccountMoveAttributeView(dfrId);
			return buildCollectionResponse(dataGrid, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	
	@RequestMapping(value = "/accountmove/validation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> validateAccountMove(@RequestBody AccountMoveAssetRequest request,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			AccountMoveAssetResponse response = appOpsAccountMovementBusiness.validateAssetMove(request);
			if(response.getMessage().startsWith("OK")) {
				return buildCollectionResponse(response, "Available", "Not Available");
			} else {
				return buildCollectionResponse(null, "Available", response.getMessage());
			}
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/accountmove/validation/patchpanel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> postValidateForPatchPanel(@RequestBody AccountMoveAssetRequest request,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		List<String>  assetNums = new ArrayList<String>();
		try{
			assetNums = appOpsAccountMovementBusiness.postValidateForPatchPanel(request);
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return assetNums;
	}
	
	@RequestMapping(value = "/accountmove/getrequest/{dfrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AccountMoveRequest getRequestFilterAccountMove(@PathVariable String dfrId,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
			AccountMoveRequest acMoveRequest = appOpsAccountMovementBusiness.getAccountMoveRequest(dfrId);
			return acMoveRequest;
			
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return null;
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	
	@RequestMapping(value = "/accountmove/initDFR", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> initDFRAccountMove(@RequestBody AccountMoveAssetRequest request,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		long start =  System.currentTimeMillis(); 
		try{
			HashMap<String,Object> dfrResult= appOpsInitiateDFRBusiness.initiateDfrAccountMove(request);
			long end =  System.currentTimeMillis(); 			
			if(dfrResult.containsKey("error")){
				dfrResult.put("message", (String)dfrResult.get("error"));
			}else {
				String dfrId=(String)dfrResult.get("dfrid");
				logger.info("==>> dfr created with " + dfrId + " in " + (end-start) );				
				dfrResult.put("message", (String)dfrResult.get("message"));
				
				new Thread(() -> {
					try{						
						request.setDfrId(dfrId);
						request.setSearchCriteria(searchSearchFilterToString(request));
						logger.info(request.getDfrId() +"# "+request.getPortRowIds());
						appOpsAccountMovementBusiness.saveAccountMoveRequest(new AccountMoveRequest(request));							
						logger.info(dfrId+" Request filter save into Accout move request table." );
					
						
					}catch(Exception e){						
						logger.info("Error in save account move filter in db" , e);
					}finally {
						HttpClientUtils.closeQuietly(httpResp);
					}
				}).start();
			}
			return buildResponse(dfrResult, "Available", "Not Available");
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
	
	private String searchSearchFilterToString(AccountMoveAssetRequest request) {
		return gson.toJson(request);
	}
	
	@RequestMapping(value = "/dfrbyteamandstatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> getDfrByTeamStatus(@RequestBody DfrSummaryInput dfrSummaryInput,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {

		try {
			DfrsByStatus result = appOppsDartHomeService.getDfrByTeamAndStatusVO(dfrSummaryInput.getStatus(),
					dfrSummaryInput.getTeam(), dfrSummaryInput.getType());
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/dfrbyteamandstatuscount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> getDfrByTeamStatusCount(@RequestBody DfrSummaryInput dfrSummaryInput,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {

		try {
			DfrsByStatus result = appOppsDartHomeService.getDfrByTeamAndStatusCount(dfrSummaryInput.getStatus(),
					dfrSummaryInput.getTeam(), dfrSummaryInput.getType());
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/updateDfrMaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> updateDfrMaster(@RequestBody DfrMaster dfrMaster,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try {
			if(null == dfrMaster.getDfrId())
				return buildCollectionResponse("DFR ID cannot be null", "Available", "Not Available");
			DfrMaster dfrMasterdtl = editDfrService.getDfrMasterById(dfrMaster.getDfrId());
			String result="";
			if(null!=dfrMasterdtl){				
				dfrMasterdtl.setAssignedTo((null!=dfrMaster.getAssignedTo())?dfrMaster.getAssignedTo():dfrMasterdtl.getAssignedTo());
				dfrMasterdtl.setAssignedDt((null!=dfrMaster.getAssignedTo())?new Date():dfrMasterdtl.getAssignedDt());
				dfrMasterdtl.setAssignedTeam((null!=dfrMaster.getAssignedTeam())?dfrMaster.getAssignedTeam():dfrMasterdtl.getAssignedTeam());
				dfrMasterdtl.setNotes((null!=dfrMaster.getNotes())?dfrMaster.getNotes():dfrMasterdtl.getNotes());
				dfrMasterdtl.setOpenOrderFlg((null!=dfrMaster.getOpenOrderFlg())?dfrMaster.getOpenOrderFlg():dfrMasterdtl.getOpenOrderFlg());
				dfrMasterdtl.setOpenOrderNum((null!=dfrMaster.getOpenOrderNum())?dfrMaster.getOpenOrderNum():dfrMasterdtl.getOpenOrderNum());
				dfrMasterdtl.setOverrideFlg((null!=dfrMaster.getOverrideFlg())?dfrMaster.getOverrideFlg():dfrMasterdtl.getOverrideFlg());
				dfrMasterdtl.setPriority((null!=dfrMaster.getPriority())?dfrMaster.getPriority():dfrMasterdtl.getPriority());
				dfrMasterdtl.setStatus((null!=dfrMaster.getStatus())?dfrMaster.getStatus():dfrMasterdtl.getStatus());
				dfrMasterdtl.setValidStatus((null!=dfrMaster.getValidStatus())?dfrMaster.getValidStatus():dfrMasterdtl.getValidStatus());
				editDfrService.saveOrUpdateDfrMaster(dfrMasterdtl);
				result="success";
			}else{
				result = "DFR ID does not exist in Master :"+dfrMaster.getDfrId();
			}
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	
	
	}
	
	@RequestMapping(value = "/searchByDfrId/{dfrId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> searchBydfrId(@PathVariable String dfrId,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {

		try {
			DfrsByStatus result = appOppsDartHomeService.searchByDfrId(dfrId);
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/countByDfrId/{dfrId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<AssetCount>> getCountByDfr(@PathVariable String dfrId,
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp){
		try {
			List<AssetCount> result = appOppsDartHomeService.getCountByDfr(dfrId);
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/ibxListForMoveAsset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<AssetCount>> ibxListForMoveAsset(HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp){
		try {
			List<String> result = appOppsDartHomeService.ibxListForMoveAsset();
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	
}