package com.equinix.appops.dart.portal.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment.WorkbookNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.equinix.appops.dart.portal.buisness.AppOpsEditDfrBusiness;
import com.equinix.appops.dart.portal.buisness.AppOpsInitiateDfrBusiness;
import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.CxiErrorTbl;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.model.DfrsByStatus;
import com.equinix.appops.dart.portal.model.FileUp;
import com.equinix.appops.dart.portal.model.dfr.DfrNotesInput;
import com.equinix.appops.dart.portal.model.dfr.InitiateWorkflowInput;
import com.equinix.appops.dart.portal.model.errorsection.ErrorDetail;
import com.equinix.appops.dart.portal.model.errorsection.ErrorMessage;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.Attribute;
import com.equinix.appops.dart.portal.model.grid.AuditAttribute;
import com.equinix.appops.dart.portal.model.grid.AuditDataGrid;
import com.equinix.appops.dart.portal.model.grid.AuditProduct;
import com.equinix.appops.dart.portal.model.grid.Product;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.grid.Values;
import com.equinix.appops.dart.portal.model.hierarchy.EditHierarchyInput;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.BaseFilter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@RestController
@RequestMapping(value={"/editdfr"})
public class AppOpsDartPortalEditDfrController extends BaseContorller {
	Logger logger = LoggerFactory.getLogger(AppOpsDartPortalEditDfrController.class);
	
	private static final String PRODUCT_CAGE = "Cage";
	private static final String PRODUCT_CABINET = "Cabinet";
	private static final String PRODUCT_AC_CIRCUIT = "AC Circuit";
	private static final String PRODUCT_DC_CIRCUIT = "DC Circuit";
	private static final String PRODUCT_AC_DC_CIRCUIT = "AC/DC Circuit";
	private static final String PRODUCT_PP = "Patch Panel";
	private static final String PRODUCT_NCC = "Network Cable Connection";
	private static final String PRODUCT_DEMARCATION = "Demarcation Point";
	private static final String PRODUCT_CAGE_SHEET = "CAGE-AUDIT";
	private static final String PRODUCT_CABINET_SHEET = "CAB-AUDIT";
	private static final String PRODUCT_AC_CIRCUIT_SHEET = "POWER-AUDIT";
	private static final String PRODUCT_PP_SHEET = "PP-AUDIT";
	private static final String PRODUCT_NCC_SHEET = "NCC-AUDIT";
	private static final String PRODUCT_DEMARCATION_SHEET = "DEMARC-AUDIT";
	private static final String PRODUCT_PPPC = "Private Patch Panel (Customer)";
	private static final String PRODUCT_PPPE = "Private Patch Panel (Equinix)";
	
	private static final String PRODUCT_CAGE_HEADER = "Cage Header";
	private static final String PRODUCT_CABINET_HEADER = "Cabinet Header";
	private static final String PRODUCT_AC_CIRCUIT_HEADER = "AC Circuit Header";
	private static final String PRODUCT_DC_CIRCUIT_HEADER = "DC Circuit Header";

	private static final String PRODUCT_PP_HEADER = "Patch Panel Header";

	private static final String PRODUCT_NCC_HEADER = "Network Cable Connection Header";

	private static final String PRODUCT_DEMARCATION_HEADER = "Demarcation Point Header";
	private static final String PRODUCT_PPPC_HEADER = "PPP (Customer) Header";
	private static final String PRODUCT_PPPE_HEADER = "PPP (Equinix) Header";
	
	private static final String[] PRODUCTS_BY_SEQUENCE = new String[]{"Cage","Cabinet","AC Circuit","DC Circuit",
			"Patch Panel","Private Patch Panel (Customer)",
			"Private Patch Panel (Equinix)","Network Cable Connection","Demarcation Point"};
	
	@Autowired
	AppOpsEditDfrBusiness editDfrBusiness;
	
	@Autowired
	AppOpsInitiateDfrBusiness initDfrBusiness;
	
	HashMap<String,SrcCxiErrorMasterTbl> errorMasterMap = new HashMap<>();
	
	
	private DataFormatter dataFormatter = new DataFormatter();
	
	
	@RequestMapping(value = "/getDfrHistory", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO< List<ApprovalHistory>>> getApprovalHistory(HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		
		  String dfrId = req.getParameter("dfrId");
		  List<ApprovalHistory> history = null;
		  if(StringUtils.isNotEmpty(dfrId))
			  history  =   initDfrBusiness.getLatestAppHistoryList(dfrId);
		  return buildResponse(history, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/productfilter", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductFilterData(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		ProductSearchResponse searchResp ;
			if(productFilter!=null){
				searchResp =  editDfrBusiness.getProductSearchResponse(productFilter); 
			}else {
				searchResp = null;
			}		
			
		 return buildResponse(searchResp, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getFilters", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> globalSearch(@RequestBody BaseFilter baseFilter, HttpServletRequest req,
			HttpServletResponse resp, HttpResponse httpResp) {
		try {
			if (baseFilter != null && baseFilter.getKeyword() != null && baseFilter.getDfrId() != null) {
				SearchFilters searchFilters = editDfrBusiness.globalSearch(baseFilter.getKeyword().replaceAll("\r\n","").replaceAll("\n","")
						, baseFilter.getDfrId(),baseFilter.getKey().replaceAll("\r\n","").replaceAll("\n",""));
				searchFilters.setDfrid(baseFilter.getDfrId());
				return buildResponse(searchFilters, "Available", "Not Available");
			} else {
				throw new Exception("Invalid Search Filter...");
			}
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/getDefaultFiltersByDfrId/{dfrid}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getAllFilterColumns(@PathVariable String dfrid , HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
		ProductFilter productFilter = new ProductFilter();
		productFilter.setDfrId(dfrid);
		SearchFilters searchFilters =   editDfrBusiness.getFilterList(productFilter);
		
		searchFilters.setDfrid(dfrid);
		return buildResponse(searchFilters, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/dfrbyid/{dfrid}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getDfrById(@PathVariable String dfrid , HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
		DfrMaster dfr =   editDfrBusiness.getDfrMasterById(dfrid);
		
		return buildResponse(dfr, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	
	@RequestMapping(value = "/validatestatus/{dfrid}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap>> getValidStatus(@PathVariable String dfrid , HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try {
			String dfrStatus =   editDfrBusiness.getValidStatus(dfrid);
			HashMap<String,String> resultMap = new HashMap<>();
			if(dfrStatus != null && !dfrStatus.isEmpty()){
				resultMap.put("message", "dfr found");
				resultMap.put("validate_status", dfrStatus);
			}else {
				resultMap.put("message", "dfr not found");
			}
			return buildResponse(resultMap, "Available", "Not Available");
		}catch(Exception e){
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/snapshotFilter/{dfrid}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String getSnapshotFilter(@PathVariable String dfrid , HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
		DfrMaster dfr =   editDfrBusiness.getDfrMasterById(dfrid);
		String snapshotFilter = dfr.getSsFilter();
		return snapshotFilter;
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return "UUID : "+uuid+" Error : "+e.getMessage();
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/syncStatus/{dfrid}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap>> getSyncStatus(@PathVariable String dfrid , HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
		DfrMaster dfr =   editDfrBusiness.getDfrMasterById(dfrid);
		HashMap<String,String> resultMap = new HashMap<>();
		if(dfr!=null){
			resultMap.put("sync_status", dfr.getAsyncVal());
			resultMap.put("tooltip", "Please wait DART is warming up....");
		}else{
			resultMap.put("error", "dfr not found");
		}
		return buildResponse(resultMap, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/errorsection", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ErrorSectionResponse>> getErrorSection(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		ErrorSectionResponse errorSectionResponse ;
			if(productFilter!=null){
				errorSectionResponse =  editDfrBusiness.getErrorSctionResponse(productFilter); 
				errorSectionResponse.setDfrid(productFilter.getDfrId());
			}else {
				errorSectionResponse = null;
			}		
			
		 return buildResponse(errorSectionResponse, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	
	@RequestMapping(value = "/productwidgets", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductWidgets(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			ProductWidgets widgets = null ;
			try{
				
				if(productFilter!=null){
					widgets =  editDfrBusiness.getProductWidgets(productFilter); 
					widgets.setDfrid(productFilter.getDfrId());
					//appOpsDartDaService.test();
				}else {
					widgets = null;
				}
				
				return buildResponse(widgets, "Available", "Not Available");
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.info("UUID : "+uuid+" Error : "+e.getMessage());
				logger.error("UUID : "+uuid+" Error : "+e.getMessage(),e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
					
			
		
	 }
	
	@RequestMapping(value = "/commonattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getCommonAttributeView(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		ProductDataGrid dataGrid = null ;
			if(searchFilters!=null){
				dataGrid =  editDfrBusiness.getCommonAttributeView(searchFilters); 
				dataGrid.setDfrid(searchFilters.getDfrId());
			}else {
				dataGrid = null;
			}		
			
		 return buildResponse(dataGrid, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	
	@RequestMapping(value = "/productattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductAttributeView(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
			try {
				ProductDataGrid dataGrid = null;
				if (searchFilters != null) {
					if (searchFilters.getAttributeFlag().equalsIgnoreCase("true")) {
						dataGrid = editDfrBusiness.getProductAttributeView(searchFilters);
						dataGrid.setDfrid(searchFilters.getDfrId());
					} else if (searchFilters.getAttributeFlag().equalsIgnoreCase("false")) {
						dataGrid = editDfrBusiness.getSelectedProductAttributeView(searchFilters);
						dataGrid.setDfrid(searchFilters.getDfrId());
					}
				} else {
					dataGrid = null;
				}
				if (searchFilters != null && searchFilters.getSearchDropBox() != null
						&& searchFilters.getSearchDropBox().getSearchDrop() != null && searchFilters.getKeyword() != null
						&& CollectionUtils.isNotEmpty(searchFilters.getSearchDropBox().getSearchDrop())
						&& searchFilters.getSearchDropBox().getSearchDrop().get(0) != null
						&& "header2".equalsIgnoreCase(searchFilters.getSearchDropBox().getSearchDrop().get(0).getKey())
						&& dataGrid != null && CollectionUtils.isNotEmpty(dataGrid.getProducts())
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
						List<Product> nullAssets = dataGrid.getProducts().stream()
								.filter(item -> item.getAssetNumber() == null).collect(Collectors.toList());
						if (null != nullAssets && nullAssets.size() > 0) {
							return buildResponse(dataGrid, "Available", "Not Available");
						}
						List<Product> sortedProducts = new ArrayList<>();
						Map<String, List<Product>> productMap = dataGrid.getProducts().stream()
								.filter(item -> item.getAssetNumber() != null)
								.collect(Collectors.groupingBy(Product::getAssetNumber));
						// Map<String, List<Product>> productMap =
						// dataGrid.getProducts().stream().collect(Collectors.groupingBy(Product::getAssetNumber));
						for (String assetNum : searchForList) {
							if (productMap.containsKey(assetNum)) {
								sortedProducts.addAll(productMap.get(assetNum));
							}
						}
						dataGrid.getProducts().clear();
						dataGrid.getProducts().addAll(sortedProducts);
					}
				}
				return buildResponse(dataGrid, "Available", "Not Available");
			} catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid, e);
				return buildErrorResponse(e.getMessage(), uuid);
			} finally {
				HttpClientUtils.closeQuietly(httpResp);
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
	
	@RequestMapping(value = "/getProductAttributeFilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductAttributeFilter(
			@RequestBody ProductFilter searchFilters, HttpServletRequest req, HttpServletResponse resp,
			HttpResponse httpResp) {
		try {
			List<String> dataGrid = null;
			if (searchFilters != null) {

				dataGrid = editDfrBusiness.getProductAttributeFilter(searchFilters);

			} else {
				dataGrid = null;
			}

			return buildResponse(dataGrid, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/heirarchysection", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ErrorSectionResponse>> getHierarchySection(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
		HierarchyView view =null;
		
			if(productFilter!=null){
				view = editDfrBusiness.getHierarchyView(productFilter);
				view.setDfrid(productFilter.getDfrId());
				Gson gson = new  Gson();
			//	logger.info(" Tree"  + gson.toJson(view,HierarchyView.class));
			}			
		 return buildResponse(view, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	@RequestMapping(value = "/validateDependentAttributes", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String validateDependentAttributes(@RequestParam String dfrId,@RequestParam String product,@RequestParam String cellType,
			@RequestParam String attrName,HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) throws Exception {
		try{
			return editDfrBusiness.validateDependentAttributes(product,attrName,dfrId,cellType);
		}catch(Exception e){
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return uuid;
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		
		
	 }
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ErrorSectionResponse>> validate(@RequestBody ProductDataGrid data,
			HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			HashMap<String , String> resultMap = new HashMap<>();
			boolean isValidateDone = false;
			try{
				editDfrBusiness.validate(data);
				isValidateDone = true;
				resultMap.put("validate", "success");
			}catch(Exception e){
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
			if(isValidateDone){
				//Once above step is done for all attributes and assets execute below
				try {
					editDfrBusiness.dqmValidation(data.getDfrid());
				}catch (Exception e){
					String uuid = UUID.randomUUID().toString();
					logger.error(uuid,e);
					return buildErrorResponse(e.getMessage(), uuid);
				}finally {
					HttpClientUtils.closeQuietly(httpResp);
				}

			}
			return buildResponse(resultMap, "Available", "Not Available");
	 }
	
	@RequestMapping(value = "/initiateWorkflow", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String initiateWorkflow(@RequestBody InitiateWorkflowInput workflowInput,
			HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			//Gson gson = new Gson ();
		try{
	
			/**
			 * New logic for initiate work flow
			 */
			if(workflowInput != null && workflowInput.getProductFilter() != null){
				ProductFilter productFilter = workflowInput.getProductFilter();
				ProductDataGrid productDataGrid = editDfrBusiness.getInitiateProductAttributeView(productFilter);
				List<String> sots = editDfrBusiness.createInitiateWorkflowInput(productDataGrid);
				workflowInput.setSotArray(sots);
				Gson gson = new Gson();
				String workFlowJson = gson.toJson(workflowInput, InitiateWorkflowInput.class);
				System.out.println("Work flow request : "+ workFlowJson);
			}
			String status="";
			status = editDfrBusiness.initiateWorkflow(workflowInput);
			return  status;
		}catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return uuid;
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
			
	 }
	
	@RequestMapping(value = "/getWorkflowDetails", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String getWorkflowDetails(@RequestParam String dfrId,HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			//Gson gson = new Gson ();	
			
		  	try{
		  		
		  		String status="";
				status = editDfrBusiness.getWorkflowDetails(dfrId);
				return  status;
		  	}catch (Exception e) {
		  		String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return uuid;
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
	 }
	
	
	@RequestMapping(value = "/saveAssetNewValues", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveAssetNewValues(@RequestBody ProductDataGrid data,HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		Gson gson = new Gson ();	
		HashMap<String , String> resultMap = new HashMap<>();
		boolean isAssetValSuccess = false;
		try{	
			editDfrBusiness.saveNewAssetValues(data);
			resultMap.put("SaveAssetNew", "success");
			isAssetValSuccess = true;
		}catch(Exception e){
				logger.error("Error occured while saving asset new val",e);
				resultMap.put("SaveAssetNew", "failure");
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		
		try{
			if(isAssetValSuccess){
				editDfrBusiness.saveDependentAttributes(data);
				resultMap.put("saveDependentAttributes", "success");
				editDfrBusiness.updateChangeSummaryValues(data);
			}else{
				resultMap.put("saveDependentAttributes", "bypassed");
			}
		}catch(Exception e){
				logger.error("Error occured while saving asset new val",e);
			resultMap.put("saveDependentAttributes", "failure");
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		
		return gson.toJson(resultMap, HashMap.class);	
	 }
	
	@RequestMapping(value = "/autoSaveAssetNewValues", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> autoSaveAssetNewValues(@RequestBody SaveAssetForm data,HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		HashMap<String , String> resultMap = new HashMap<>();
		boolean isAssetValSuccess = false;
		ProductDataGrid dataGrid = null;
		try{	
			Instant start = Instant.now();
			editDfrBusiness.autoSaveNewAssetValues(data);
			Instant finish = Instant.now();
			resultMap.put("SaveAssetNew", "success");
			isAssetValSuccess = true;
			long timeElapsed = Duration.between(start, finish).toMillis();
			logger.info("saving asset new val Time taken "+timeElapsed+"ms");				
		}catch(Exception e){
				logger.error("Error occured while saving asset new val",e);
				resultMap.put("SaveAssetNew", "failure");
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		
		try{
			if(isAssetValSuccess){
				Instant start = Instant.now();
				editDfrBusiness.autoSaveDependentAttributes(data);
				Instant finish = Instant.now();
				long timeElapsed = Duration.between(start, finish).toMillis();
				logger.info(" saveDependentAttributes Time taken "+timeElapsed+"ms");
				resultMap.put("saveDependentAttributes", "success");
				
				dataGrid = editDfrBusiness.productAttributeViewByLineId(data);
				Instant start1 = Instant.now();
				SaveAssetForm inputData = editDfrBusiness.createChangeSummaryInput(dataGrid);
				editDfrBusiness.autoUpdateChangeSummaryValues(inputData);
				Instant finish1 = Instant.now();
				long timeElapsed1 = Duration.between(start1, finish1).toMillis();
				logger.info(" autoUpdateChangeSummaryValues  Time taken "+timeElapsed1+"ms");
				
			}else{
				resultMap.put("saveDependentAttributes", "bypassed");
			}
		}catch(Exception e){
				logger.error("Error occured while saving asset new val",e);
			resultMap.put("saveDependentAttributes", "failure");
			return buildResponse(resultMap, "Available", "Not Available");
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		
		return buildResponse(dataGrid, "Available", "Not Available");	
	 }
	
	@RequestMapping(value = ""
			+ "", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String editHierarchy(@RequestBody EditHierarchyInput hierarchyInput,
			HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			
			HashMap<String,String> resultMap = new HashMap<>();
			Gson gson = new Gson ();	
			String status="";
			try{
				status = editDfrBusiness.editHierarchy(hierarchyInput.getDfrlineid(),hierarchyInput.getParentdfrlineid(),hierarchyInput.getProduct());
				resultMap.put("editHierarchy", status);
				status = editDfrBusiness.validateHierarchy(hierarchyInput.getDfrlineid(),hierarchyInput.getProduct());
				resultMap.put("validateHierarchy", status);
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			return gson.toJson( resultMap,HashMap.class);
	 }
	@RequestMapping(value = "/saveDfrDetails", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveDfrDetails(@RequestParam String dfrId,@RequestParam String fieldName,@RequestParam String fieldValue,
			HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			Gson gson = new Gson ();	
			String status="";
			try{
				status = editDfrBusiness.saveDfrDetails(dfrId,fieldName,fieldValue);
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
			return gson.toJson( status,String.class);
	 }
	@RequestMapping(value = "/cancelDfr", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String cancelDfr(@RequestParam String dfrId,HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			Gson gson = new Gson ();	
			String status="";
			try{
				status = editDfrBusiness.cancelDfr(dfrId);
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
			return gson.toJson( status,String.class);
	 }
	
	
	
	@RequestMapping(value = "/auditAttributeView", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<AuditDataGrid>> getAuditAttributeView(@RequestParam("file") MultipartFile fileObj,HttpResponse httpResp) throws Exception {
		AuditDataGrid gridObj = new AuditDataGrid();
		FileInputStream input = null;
		Workbook workbook = null;
		List<String> attributeNamesList = null;
		String assetNum = null;
		Set<String> editedAssetSet = new HashSet<String>();
		try {
			input = (FileInputStream) fileObj.getInputStream();
			workbook = new XSSFWorkbook(input);
			FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbook);
			gridObj.setFormulaExtRef("N");
			for (int a = 0; a < workbook.getNumberOfSheets(); a++) {
				XSSFSheet sheetObj = (XSSFSheet) workbook.getSheetAt(a);
				if (PRODUCT_CAGE.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_CABINET.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_AC_CIRCUIT.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_PP.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_NCC.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_DC_CIRCUIT.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_DEMARCATION.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_PPPC.equalsIgnoreCase(sheetObj.getSheetName())
						|| PRODUCT_PPPE.equalsIgnoreCase(sheetObj.getSheetName())) {
					Row firstRow = sheetObj.getRow(0);
					attributeNamesList = new ArrayList<String>();
					for (int i = 0; i < firstRow.getPhysicalNumberOfCells(); i++) {
						attributeNamesList.add(firstRow.getCell(i).toString());
					}
					for (int i = 1; i <= sheetObj.getLastRowNum(); i++) {
						Row currentRow = sheetObj.getRow(i);
						if (validateFormulaForExternalSheet(currentRow)) {
							gridObj.setFormulaExtRef("Y");
							gridObj.setMessageText("File has External Sheet Reference in Cell Formula. Please remove them and re-upload.");
							gridObj.getAuditProductList().clear();
							return buildResponse(gridObj, "Available", "Not Available");
						}
						AuditProduct productObj = new AuditProduct();
						productObj.setProductName(sheetObj.getSheetName());
						for (int j = 0; j < attributeNamesList.size(); j++) {
							AuditAttribute attrObj = new AuditAttribute();
							attrObj.setHeader(attributeNamesList.get(j));
							attrObj.setValue(currentRow.getCell(j) != null
									? getDataFormatter().formatCellValue(currentRow.getCell(j), formulaEvaluatorObj)
									: null);
							if ("Asset # (SBL)".equalsIgnoreCase(attrObj.getHeader()) && StringUtils.isNotEmpty(attrObj.getValue())){
								assetNum = attrObj.getValue();
							}
							if (StringUtils.isNotEmpty(attrObj.getHeader()) && attrObj.getHeader().endsWith("(newvalue)") && 
									StringUtils.isNotEmpty(attrObj.getValue()) && !"Product (newvalue)".equalsIgnoreCase(attrObj.getHeader())) {
								editedAssetSet.add(assetNum);
							}
							productObj.getAuditAttributeList().add(attrObj);
						}
						gridObj.getAuditProductList().add(productObj);
					}
				}
			}
			gridObj.setEditedAssetCount(editedAssetSet.size());
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			logger.error("UUID : "+uuid+" Error : "+ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		} finally {
			workbook.close();
			input.close();
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildResponse(gridObj, "Available", "Not Available");
	}
	
	@RequestMapping(value = "/updateUserDfr/{userId}/{dfrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<DfrsByStatus>> updateUserDfr(@PathVariable("userId") String userId,
			@PathVariable("dfrId") String dfrId, HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try{
		String result = editDfrBusiness.updateUserDfr(userId, dfrId);
		return buildCollectionResponse(result,  "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	
	@RequestMapping(value = "/refreshedcommonattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedCommonAttributeView(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{
			ProductDataGrid dataGrid = null ;
			if(searchFilters!=null){
				dataGrid =  editDfrBusiness.getRefreshedCommonAttributeGrid(searchFilters); 
			}else {
				dataGrid = null;
			}		

			return buildResponse(dataGrid, "Available", "Not Available");
		}
			catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
	 }
	 
	 
	 @RequestMapping(value = "/refreshedproductattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedProductAttributeGrid(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		 ProductDataGrid dataGrid = null ;
			if(searchFilters!=null){
				if (searchFilters.getAttributeFlag().equalsIgnoreCase("true")) {
					dataGrid = editDfrBusiness.getRefreshedProductAttributeGrid(searchFilters);
					dataGrid.setDfrid(searchFilters.getDfrId());
				} else if (searchFilters.getAttributeFlag().equalsIgnoreCase("false")) {
					dataGrid = editDfrBusiness.getSelectedRefreshedProductAttributeView(searchFilters);
					dataGrid.setDfrid(searchFilters.getDfrId());
				}
			}else {
				dataGrid = null;
			}		
			
		 return buildResponse(dataGrid, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	 
	 @RequestMapping(value = "/getRefreshedProductAttributeFilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedProductAttributeFilter(
				@RequestBody ProductFilter searchFilters, HttpServletRequest req, HttpServletResponse resp,
				HttpResponse httpResp) {
			try {
				List<String> dataGrid = null;
				if (searchFilters != null) {

					dataGrid = editDfrBusiness.getRefreshedProductAttributeFilter(searchFilters);

				} else {
					dataGrid = null;
				}

				return buildResponse(dataGrid, "Available", "Not Available");
			} catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid, e);
				return buildErrorResponse(e.getMessage(), uuid);
			} finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}
	 
	 @RequestMapping(value = "/refreshedproductwidgets", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedProductWidgets(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		 ProductWidgets widgets = null ;
			if(productFilter!=null){
				widgets =  editDfrBusiness.getRefreshedProductWidgets(productFilter); 
				//appOpsDartDaService.test();
			}else {
				widgets = null;
			}		
			
		 return buildResponse(widgets, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	 
	 @RequestMapping(value = "/initiatePhysicalAudit", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<String>> initiatePhysicalAudit(@RequestParam String dfrId,@RequestParam String ibx, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
				String status="";
				try{
					status = editDfrBusiness.initiatePhysicalAudit(dfrId,ibx);
				}catch (Exception e) {
					String uuid = UUID.randomUUID().toString();
					logger.error(uuid,e);
					return buildErrorResponse(e.getMessage(), uuid);
				}finally {
					HttpClientUtils.closeQuietly(httpResp);
				}
				
				return buildResponse(status, "Available", "Not Available");
		 }
	 
	 @RequestMapping(value = "/physicalAuditData", method = RequestMethod.GET)
	 public ResponseEntity<InputStreamResource> getPhysicalAuditAttributeData(@RequestParam("dfrId") String dfrId, HttpResponse httpResp,
			 HttpServletResponse httpSerResp) throws Exception {
		 Map<String,Object> physicalAuditData = editDfrBusiness.getPhysicalAuditDownloadData(dfrId);
		 errorMasterMap = editDfrBusiness.getErrorMasterList();
		 List<SnapshotSiebelAssetDa> siebelAssetDataList = (ArrayList<SnapshotSiebelAssetDa>) physicalAuditData.get("SnapshotSiebelAssetDAList");
		 List<AssetNewVal> assetNewValDataList = (ArrayList<AssetNewVal>) physicalAuditData.get("AssetNewValList");
		 List<CxiErrorTbl> errorList = (ArrayList<CxiErrorTbl>) physicalAuditData.get("ErrorList");
		 Workbook workbook = null;
		 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		 ByteArrayInputStream inputStream = null;
		 ResponseEntity<InputStreamResource> response = null;
		 HashMap<String,List<SnapshotSiebelAssetDa>> resultMap = null;
		 HashMap<String,List<AssetNewVal>> assetNewValMap = null;
		 HashMap<String,List<CxiErrorTbl>> errorMap = new HashMap<>();
		 HashMap<String,List<CxiErrorTbl>> errorMapSbl = null;
		 /*HashMap<String,List<CxiErrorTbl>> errorMapClx = null;
		 HashMap<String,List<CxiErrorTbl>> errorMapSv = null;*/
		 List<SnapshotSiebelAssetDa> assetList = null;
		 ByteArrayOutputStream outByteStream = null;
		 OutputStream outStream = null;
		 try {
			resultMap = (HashMap<String, List<SnapshotSiebelAssetDa>>) siebelAssetDataList.stream().collect
					(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader20));
			assetNewValMap = (HashMap<String, List<AssetNewVal>>) assetNewValDataList.stream().collect
					(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			errorMapSbl = (HashMap<String,List<CxiErrorTbl>>) errorList.stream().filter(errorObj-> errorObj.getSnapshotSiebelAssetDa() != null).collect(
					Collectors.groupingBy( errorObj -> ((CxiErrorTbl)errorObj).getSnapshotSiebelAssetDa().getDfrLineId()));
			/*errorMapClx = (HashMap<String,List<CxiErrorTbl>>) errorList.stream().filter(errorObj-> errorObj.getSnapshotClxAssetDa() != null).collect(
					Collectors.groupingBy( errorObj -> ((CxiErrorTbl)errorObj).getSnapshotClxAssetDa().getDfrLineId()));
			errorMapSv = (HashMap<String,List<CxiErrorTbl>>) errorList.stream().filter(errorObj-> errorObj.getSnapshotSvAssetDa() != null).collect(
					Collectors.groupingBy( errorObj -> ((CxiErrorTbl)errorObj).getSnapshotSvAssetDa().getDfrLineId()));*/
			if (errorMapSbl != null && !errorMapSbl.isEmpty() && errorMapSbl.size() > 0) {
				errorMap.putAll(errorMapSbl);
			}
			/*if (errorMapClx != null && !errorMapClx.isEmpty() && errorMapClx.size() > 0) {
				errorMap.putAll(errorMapClx);
			}
			if (errorMapSv != null && !errorMapSv.isEmpty() && errorMapSv.size() > 0) {
				errorMap.putAll(errorMapSv);
			}*/
			ClassLoader classLoaderObj = getClass().getClassLoader();
			workbook = new XSSFWorkbook(classLoaderObj.getResourceAsStream("DART_Physical_Audit_Template_V1.0.xlsx"));
			CellStyle dateCellStyle = getDateCellType(workbook);
			if (resultMap.containsKey(PRODUCT_CAGE)) {
				assetList = resultMap.get(PRODUCT_CAGE);
				constructCageAuditData(workbook, assetList, dateCellStyle,assetNewValMap, errorMap);
			}
			if (resultMap.containsKey(PRODUCT_CABINET)) {
				assetList = resultMap.get(PRODUCT_CABINET);
				constructCabietAuditData(workbook, assetList, dateCellStyle,assetNewValMap, errorMap);
			}
			if (resultMap.containsKey(PRODUCT_AC_CIRCUIT) || resultMap.containsKey(PRODUCT_DC_CIRCUIT)) {
				assetList = resultMap.get(PRODUCT_AC_CIRCUIT);
				if (resultMap.containsKey(PRODUCT_DC_CIRCUIT)) {
					if (assetList != null && !assetList.isEmpty()) {
						assetList.addAll(resultMap.get(PRODUCT_DC_CIRCUIT));
					} else {
						assetList = resultMap.get(PRODUCT_DC_CIRCUIT);
					}
				}
				constructACCircuitAuditData(workbook, assetList, dateCellStyle,assetNewValMap, errorMap);
			}
			if (resultMap.containsKey(PRODUCT_PP)) {
				assetList = resultMap.get(PRODUCT_PP);
				constructPatchPanelAuditData(workbook, assetList, dateCellStyle,assetNewValMap, errorMap);
			}
			if (resultMap.containsKey(PRODUCT_NCC)) {
				assetList = resultMap.get(PRODUCT_NCC);
				constructNCCAuditData(workbook, assetList, dateCellStyle,assetNewValMap, errorMap);
			}
			if (resultMap.containsKey(PRODUCT_DEMARCATION)){
				assetList = resultMap.get(PRODUCT_DEMARCATION);
				constructDemacationPointAuditData(workbook, assetList, dateCellStyle, assetNewValMap, errorMap);
			}
			outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			inputStream = new ByteArrayInputStream(outArray);
			httpSerResp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			httpSerResp.setHeader("Content-Disposition", "attachment; filename=Physical_Audit_" + dfrId + ".xlsx");
			httpSerResp.setContentLength(outArray.length);
			httpSerResp.setHeader("Expires:", "0"); // Eliminates Browser Caching
			outStream = httpSerResp.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
		 } catch (Exception ex) {
			 String uuid = UUID.randomUUID().toString();
			 logger.error(uuid,ex);
			 throw new Exception("Exception thrown while exporting Physical Audit Data");
		 }
		 finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (workbook != null) {
				workbook.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (outByteStream != null) {
				outByteStream.close();
			}
			if (outStream != null) {
				outStream.close();
			}
			HttpClientUtils.closeQuietly(httpResp);
		 }
		 return response;
	 }
	 
	public CellStyle getDateCellType (Workbook workbookObj) {
		CellStyle cellStyle = workbookObj.createCellStyle();
		CreationHelper helper = workbookObj.getCreationHelper();
		cellStyle.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
		return cellStyle;
	}
	
	public void constructCageAuditData(Workbook workbook, List<SnapshotSiebelAssetDa> assetList,
			CellStyle dateCellStyle, HashMap<String, List<AssetNewVal>> assetNewValMap, HashMap<String,List<CxiErrorTbl>> errorMap) {
		XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet(PRODUCT_CAGE_SHEET);
		for (int i = 4; i < assetList.size() + 4; i++) {
			Row currentRow = sheetObj.createRow(i);
			SnapshotSiebelAssetDa assetDa = (SnapshotSiebelAssetDa) assetList.get(i - 4);
			currentRow.createCell(0).setCellValue(assetDa.getDfrLineId());
			currentRow.createCell(1).setCellValue(assetDa.getHeader38());
			if (errorMap != null && !errorMap.isEmpty() && errorMap.size() > 0) {
				assetDa.setCxiErrorTbls((List<CxiErrorTbl>) errorMap.get(assetDa.getDfrLineId()));
				if (assetDa.getCxiErrorTbls() != null && !assetDa.getCxiErrorTbls().isEmpty()) {
					StringBuilder errorString = new StringBuilder();
					assetDa.getCxiErrorTbls().stream().forEach(error -> {
						if (isErrorDisplayable(error)) {
							errorString.append(error.getErrorCode()).append(" - ").append(error.getErrorName())
									.append("\n");
						}
					});
					currentRow.createCell(2).setCellValue(errorString.toString());
				}
			}
			currentRow.createCell(3).setCellValue(assetDa.getHeader24());
			currentRow.createCell(4).setCellValue(assetDa.getHeader20());
			currentRow.createCell(5).setCellValue(assetDa.getHeader2());
			currentRow.createCell(6).setCellValue(assetDa.getHeader3());
			currentRow.createCell(7).setCellValue(assetDa.getHeader7());
			currentRow.createCell(8).setCellValue(assetDa.getHeader6());
			currentRow.createCell(9).setCellValue(assetDa.getHeader31());
			currentRow.createCell(10).setCellValue(assetDa.getHeader10());
			currentRow.createCell(11).setCellValue(assetDa.getHeader18());
			currentRow.createCell(12).setCellValue(assetDa.getHeader45());
			currentRow.createCell(13).setCellValue(assetDa.getHeader16());
			currentRow.createCell(15).setCellValue(assetDa.getAttr14());
			currentRow.createCell(19).setCellValue(assetDa.getAttr53());
			currentRow.createCell(21).setCellValue(assetDa.getAttr29());
			currentRow.createCell(23).setCellValue(assetDa.getAttr28());
			currentRow.createCell(25).setCellValue(assetDa.getAttr87());
			currentRow.createCell(27).setCellValue(assetDa.getAttr24());
			currentRow.createCell(29).setCellValue(assetDa.getAttr23());
			currentRow.createCell(31).setCellValue(assetDa.getAttr84());
			currentRow.createCell(33).setCellValue(assetDa.getAttr21());
			currentRow.createCell(35).setCellStyle(dateCellStyle);
			currentRow.getCell(35).setCellValue(assetDa.getHeader17());
			currentRow.createCell(41).setCellValue(assetDa.getHeader26());
			
			currentRow.createCell(37).setCellValue(assetDa.getAttr335());
			currentRow.createCell(39).setCellValue(assetDa.getHeader61());
			currentRow.createCell(40).setCellValue(assetDa.getHeader66());
			
			if (assetNewValMap != null && assetNewValMap.containsKey(assetDa.getDfrLineId())) {
				List<AssetNewVal> assetNewValList = (List<AssetNewVal>) assetNewValMap.get(assetDa.getDfrLineId());
				if (assetNewValList != null && assetNewValList.size() > 0) {
					AssetNewVal assetNewVal = assetNewValList.get(0);
					currentRow.createCell(14).setCellValue(assetNewVal.getHeader16());
					currentRow.createCell(16).setCellValue(assetNewVal.getAttr14());
					currentRow.createCell(20).setCellValue(assetNewVal.getAttr53());
					currentRow.createCell(22).setCellValue(assetNewVal.getAttr29());
					currentRow.createCell(24).setCellValue(assetNewVal.getAttr28());
					currentRow.createCell(26).setCellValue(assetNewVal.getAttr87());
					currentRow.createCell(28).setCellValue(assetNewVal.getAttr24());
					currentRow.createCell(30).setCellValue(assetNewVal.getAttr23());
					currentRow.createCell(32).setCellValue(assetNewVal.getAttr84());
					currentRow.createCell(34).setCellValue(assetNewVal.getAttr21());
					currentRow.createCell(38).setCellValue(assetNewVal.getHeader58());
					/**
					 * Enabled
					 * 
					 * Audit Results,IBX Audit Status,IBX Reconciliation Status
					 */
					if(StringUtils.isNotEmpty(assetNewVal.getHeader61()))
						currentRow.getCell(39).setCellValue(assetNewVal.getHeader61());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader66()))
						currentRow.getCell(40).setCellValue(assetNewVal.getHeader66());
					if(StringUtils.isNotEmpty(assetNewVal.getAttr335()))
						currentRow.getCell(37).setCellValue(assetDa.getAttr335());
				}
			}
		}
	}
	
	
	public void constructCabietAuditData(Workbook workbook,List<SnapshotSiebelAssetDa> assetList,CellStyle dateCellStyle,
			HashMap<String, List<AssetNewVal>> assetNewValMap, HashMap<String,List<CxiErrorTbl>> errorMap) {
		XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet(PRODUCT_CABINET_SHEET);
		for (int i = 4; i < assetList.size()+4; i++) {
			Row currentRow = sheetObj.createRow(i);
			SnapshotSiebelAssetDa assetDa = (SnapshotSiebelAssetDa) assetList.get(i-4);
			currentRow.createCell(0).setCellValue(assetDa.getDfrLineId());
			currentRow.createCell(1).setCellValue(assetDa.getHeader38());
			if (errorMap != null && !errorMap.isEmpty() && errorMap.size() > 0) {
				assetDa.setCxiErrorTbls((List<CxiErrorTbl>) errorMap.get(assetDa.getDfrLineId()));
				if (assetDa.getCxiErrorTbls() != null && !assetDa.getCxiErrorTbls().isEmpty()) {
					StringBuilder errorString = new StringBuilder();
					assetDa.getCxiErrorTbls().stream().forEach(error -> {
						if (isErrorDisplayable(error)) {
							errorString.append(error.getErrorCode()).append(" - ").append(error.getErrorName())
									.append("\n");
						}
					});
					currentRow.createCell(2).setCellValue(errorString.toString());
				}
			}
			currentRow.createCell(3).setCellValue(assetDa.getHeader24());
			currentRow.createCell(4).setCellValue(assetDa.getHeader20());
			currentRow.createCell(5).setCellValue(assetDa.getHeader2());
			currentRow.createCell(6).setCellValue(assetDa.getHeader3());
			currentRow.createCell(7).setCellValue(assetDa.getHeader7());
			currentRow.createCell(8).setCellValue(assetDa.getHeader6());
			currentRow.createCell(9).setCellValue(assetDa.getHeader31());
			currentRow.createCell(10).setCellValue(assetDa.getHeader10());
			currentRow.createCell(11).setCellValue(assetDa.getHeader18());
			currentRow.createCell(12).setCellValue(assetDa.getHeader45());
			currentRow.createCell(13).setCellValue(assetDa.getHeader16());
			currentRow.createCell(15).setCellValue(assetDa.getAttr18());
			currentRow.createCell(17).setCellValue(assetDa.getAttr43());
			currentRow.createCell(21).setCellValue(assetDa.getAttr11());
			currentRow.createCell(23).setCellValue(assetDa.getAttr41());
			currentRow.createCell(25).setCellValue(assetDa.getAttr66());
			currentRow.createCell(27).setCellValue(assetDa.getAttr75());
			currentRow.createCell(29).setCellValue(assetDa.getAttr55());
			currentRow.createCell(31).setCellValue(assetDa.getAttr62());
			currentRow.createCell(33).setCellValue(assetDa.getAttr70());
			currentRow.createCell(35).setCellValue(assetDa.getAttr73());
			currentRow.createCell(37).setCellValue(assetDa.getAttr74());
			currentRow.createCell(39).setCellValue(assetDa.getAttr63());
			currentRow.createCell(41).setCellValue(assetDa.getAttr88());
			currentRow.createCell(43).setCellValue(assetDa.getAttr53());
			currentRow.createCell(45).setCellStyle(dateCellStyle);
			currentRow.getCell(45).setCellValue(assetDa.getHeader17());
			currentRow.createCell(51).setCellValue(assetDa.getHeader26());
			
			currentRow.createCell(47).setCellValue(assetDa.getAttr335());
			currentRow.createCell(49).setCellValue(assetDa.getHeader61());
			currentRow.createCell(50).setCellValue(assetDa.getHeader66());
			
			if (assetNewValMap != null && assetNewValMap.containsKey(assetDa.getDfrLineId())) {
				List<AssetNewVal> assetNewValList = (List<AssetNewVal>) assetNewValMap.get(assetDa.getDfrLineId());
				if (assetNewValList != null && assetNewValList.size() > 0) {
					AssetNewVal assetNewVal = assetNewValList.get(0);
					currentRow.createCell(14).setCellValue(assetNewVal.getHeader16());
					currentRow.createCell(16).setCellValue(assetNewVal.getAttr18());
					currentRow.createCell(18).setCellValue(assetNewVal.getAttr43());
					currentRow.createCell(22).setCellValue(assetNewVal.getAttr11());
					currentRow.createCell(24).setCellValue(assetNewVal.getAttr41());
					currentRow.createCell(26).setCellValue(assetNewVal.getAttr66());
					currentRow.createCell(28).setCellValue(assetNewVal.getAttr75());
					currentRow.createCell(30).setCellValue(assetNewVal.getAttr55());
					currentRow.createCell(32).setCellValue(assetNewVal.getAttr62());
					currentRow.createCell(34).setCellValue(assetNewVal.getAttr70());
					currentRow.createCell(36).setCellValue(assetNewVal.getAttr73());
					currentRow.createCell(38).setCellValue(assetNewVal.getAttr74());
					currentRow.createCell(40).setCellValue(assetNewVal.getAttr63());
					currentRow.createCell(42).setCellValue(assetNewVal.getAttr88());
					currentRow.createCell(44).setCellValue(assetNewVal.getAttr53());
					currentRow.createCell(48).setCellValue(assetNewVal.getHeader58());
					/**
					 * Enabled
					 * 
					 * Audit Results,IBX Audit Status,IBX Reconciliation Status
					 */
					if(StringUtils.isNotEmpty(assetNewVal.getAttr335()))
						currentRow.getCell(47).setCellValue(assetNewVal.getAttr335());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader61()))
						currentRow.getCell(49).setCellValue(assetNewVal.getHeader61());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader66()))
						currentRow.getCell(50).setCellValue(assetNewVal.getHeader66());
				}
			}
		}
	}
	
	public void constructACCircuitAuditData(Workbook workbook,List<SnapshotSiebelAssetDa> assetList,CellStyle dateCellStyle,
			HashMap<String, List<AssetNewVal>> assetNewValMap, HashMap<String,List<CxiErrorTbl>> errorMap) {
		XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet(PRODUCT_AC_CIRCUIT_SHEET);
		for (int i = 4; i < assetList.size()+4; i++) {
			Row currentRow = sheetObj.createRow(i);
			SnapshotSiebelAssetDa assetDa = (SnapshotSiebelAssetDa) assetList.get(i-4);
			currentRow.createCell(0).setCellValue(assetDa.getDfrLineId());
			currentRow.createCell(1).setCellValue(assetDa.getHeader38());
			if (errorMap != null && !errorMap.isEmpty() && errorMap.size() > 0) {
				assetDa.setCxiErrorTbls((List<CxiErrorTbl>) errorMap.get(assetDa.getDfrLineId()));
				if (assetDa.getCxiErrorTbls() != null && !assetDa.getCxiErrorTbls().isEmpty()) {
					StringBuilder errorString = new StringBuilder();
					assetDa.getCxiErrorTbls().stream().forEach(error -> {
						if (isErrorDisplayable(error)) {
							errorString.append(error.getErrorCode()).append(" - ").append(error.getErrorName())
									.append("\n");
						}
					});
					currentRow.createCell(2).setCellValue(errorString.toString());
				}
			}
			currentRow.createCell(3).setCellValue(assetDa.getHeader24());
			currentRow.createCell(4).setCellValue(assetDa.getHeader20());
			currentRow.createCell(5).setCellValue(assetDa.getHeader2());
			currentRow.createCell(6).setCellValue(assetDa.getHeader3());
			currentRow.createCell(7).setCellValue(assetDa.getHeader7());
			currentRow.createCell(8).setCellValue(assetDa.getHeader6());
			currentRow.createCell(9).setCellValue(assetDa.getHeader31());
			currentRow.createCell(10).setCellValue(assetDa.getHeader10());
			currentRow.createCell(11).setCellValue(assetDa.getHeader18());
			currentRow.createCell(12).setCellValue(assetDa.getHeader45());
			currentRow.createCell(13).setCellValue(assetDa.getHeader16());
			currentRow.createCell(15).setCellValue(assetDa.getAttr18());
			if (PRODUCT_DC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
				currentRow.createCell(18).setCellValue(assetDa.getAttr70());
			} else {
				currentRow.createCell(18).setCellValue(assetDa.getAttr87());
			}
			currentRow.createCell(20).setCellValue(assetDa.getAttr23());
			currentRow.createCell(22).setCellValue(assetDa.getAttr25());
			currentRow.createCell(24).setCellValue(assetDa.getAttr26());
			currentRow.createCell(26).setCellValue(assetDa.getAttr27());
			if (PRODUCT_AC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
				currentRow.createCell(28).setCellValue(assetDa.getAttr70());
			}
			currentRow.createCell(30).setCellValue(assetDa.getAttr6());
			if (PRODUCT_AC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
				currentRow.createCell(32).setCellValue(assetDa.getAttr81());
			}
			currentRow.createCell(34).setCellValue(assetDa.getAttr16());
			currentRow.createCell(36).setCellValue(assetDa.getAttr47());
			currentRow.createCell(38).setCellValue(assetDa.getAttr49());
			if (PRODUCT_DC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
				currentRow.createCell(40).setCellValue(assetDa.getAttr63());
			} else {
				currentRow.createCell(40).setCellValue(assetDa.getAttr79());
			}
			currentRow.createCell(42).setCellStyle(dateCellStyle);
			currentRow.getCell(42).setCellValue(assetDa.getHeader17());
			currentRow.createCell(48).setCellValue(assetDa.getHeader26());
			
			currentRow.createCell(44).setCellValue(assetDa.getAttr335());
			currentRow.createCell(46).setCellValue(assetDa.getHeader61());
			currentRow.createCell(47).setCellValue(assetDa.getHeader66());
			
			if (assetNewValMap != null && assetNewValMap.containsKey(assetDa.getDfrLineId())) {
				List<AssetNewVal> assetNewValList = (List<AssetNewVal>) assetNewValMap.get(assetDa.getDfrLineId());
				if (assetNewValList != null && assetNewValList.size() > 0) {
					AssetNewVal assetNewVal = assetNewValList.get(0);
					currentRow.createCell(14).setCellValue(assetNewVal.getHeader16());
					currentRow.createCell(16).setCellValue(assetNewVal.getAttr18());
					if (PRODUCT_DC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
						currentRow.createCell(19).setCellValue(assetNewVal.getAttr70());
					} else {
						currentRow.createCell(19).setCellValue(assetNewVal.getAttr87());
					}
					currentRow.createCell(21).setCellValue(assetNewVal.getAttr23());
					currentRow.createCell(23).setCellValue(assetNewVal.getAttr25());
					currentRow.createCell(25).setCellValue(assetNewVal.getAttr26());
					currentRow.createCell(27).setCellValue(assetNewVal.getAttr27());
					if (PRODUCT_AC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
						currentRow.createCell(29).setCellValue(assetNewVal.getAttr70());
					}
					currentRow.createCell(31).setCellValue(assetNewVal.getAttr6());
					if (PRODUCT_AC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
						currentRow.createCell(33).setCellValue(assetNewVal.getAttr81());
					}
					currentRow.createCell(35).setCellValue(assetNewVal.getAttr16());
					currentRow.createCell(37).setCellValue(assetNewVal.getAttr47());
					currentRow.createCell(39).setCellValue(assetNewVal.getAttr49());
					if (PRODUCT_DC_CIRCUIT.equalsIgnoreCase(assetDa.getHeader20())) {
						currentRow.createCell(41).setCellValue(assetNewVal.getAttr63());
					} else {
						currentRow.createCell(41).setCellValue(assetNewVal.getAttr79());
					}
					currentRow.createCell(45).setCellValue(assetNewVal.getHeader58());
					/**
					 * Enabled
					 * 
					 * Audit Results,IBX Audit Status,IBX Reconciliation Status
					 */
					if(StringUtils.isNotEmpty(assetNewVal.getAttr335()))
						currentRow.getCell(44).setCellValue(assetNewVal.getAttr335());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader61()))
					currentRow.getCell(46).setCellValue(assetNewVal.getHeader61());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader66()))
					currentRow.getCell(47).setCellValue(assetNewVal.getHeader66());
				}
			}
		}
	}
	
	public void constructPatchPanelAuditData(Workbook workbook,List<SnapshotSiebelAssetDa> assetList,CellStyle dateCellStyle,
			HashMap<String, List<AssetNewVal>> assetNewValMap, HashMap<String,List<CxiErrorTbl>> errorMap) {
		XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet(PRODUCT_PP_SHEET);
		for (int i = 4; i < assetList.size()+4; i++) {
			Row currentRow = sheetObj.createRow(i);
			SnapshotSiebelAssetDa assetDa = (SnapshotSiebelAssetDa) assetList.get(i-4);
			currentRow.createCell(0).setCellValue(assetDa.getDfrLineId());
			currentRow.createCell(1).setCellValue(assetDa.getHeader38());
			if (errorMap != null && !errorMap.isEmpty() && errorMap.size() > 0) {
				assetDa.setCxiErrorTbls((List<CxiErrorTbl>) errorMap.get(assetDa.getDfrLineId()));
				if (assetDa.getCxiErrorTbls() != null && !assetDa.getCxiErrorTbls().isEmpty()) {
					StringBuilder errorString = new StringBuilder();
					assetDa.getCxiErrorTbls().stream().forEach(error -> {
						if (isErrorDisplayable(error)) {
							errorString.append(error.getErrorCode()).append(" - ").append(error.getErrorName())
									.append("\n");
						}
					});
					currentRow.createCell(2).setCellValue(errorString.toString());
				}
			}
			currentRow.createCell(3).setCellValue(assetDa.getHeader24());
			currentRow.createCell(4).setCellValue(assetDa.getHeader20());
			currentRow.createCell(5).setCellValue(assetDa.getHeader2());
			currentRow.createCell(6).setCellValue(assetDa.getHeader3());
			currentRow.createCell(7).setCellValue(assetDa.getHeader7());
			currentRow.createCell(8).setCellValue(assetDa.getHeader6());
			currentRow.createCell(9).setCellValue(assetDa.getHeader31());
			currentRow.createCell(10).setCellValue(assetDa.getHeader10());
			currentRow.createCell(11).setCellValue(assetDa.getHeader18());
			currentRow.createCell(12).setCellValue(assetDa.getHeader45());
			currentRow.createCell(13).setCellValue(assetDa.getHeader16());
			currentRow.createCell(15).setCellValue(assetDa.getAttr21());
			currentRow.createCell(17).setCellValue(assetDa.getAttr18());
			currentRow.createCell(21).setCellValue(assetDa.getAttr4());
			currentRow.createCell(23).setCellValue(assetDa.getAttr39());
			currentRow.createCell(25).setCellValue(assetDa.getAttr9());
			currentRow.createCell(29).setCellValue(assetDa.getAttr33());
			currentRow.createCell(31).setCellValue(assetDa.getAttr10());
			currentRow.createCell(33).setCellValue(assetDa.getAttr15());
			currentRow.createCell(35).setCellValue(assetDa.getAttr13());
			currentRow.createCell(37).setCellValue(assetDa.getAttr25());
			currentRow.createCell(39).setCellValue(assetDa.getAttr24());
			currentRow.createCell(41).setCellStyle(dateCellStyle);
			currentRow.getCell(41).setCellValue(assetDa.getHeader17());
			currentRow.createCell(47).setCellValue(assetDa.getHeader26());
			
			currentRow.createCell(43).setCellValue(assetDa.getAttr335());
			currentRow.createCell(45).setCellValue(assetDa.getHeader61());
			currentRow.createCell(46).setCellValue(assetDa.getHeader66());
			
			if (assetNewValMap != null && assetNewValMap.containsKey(assetDa.getDfrLineId())) {
				List<AssetNewVal> assetNewValList = (List<AssetNewVal>) assetNewValMap.get(assetDa.getDfrLineId());
				if (assetNewValList != null && assetNewValList.size() > 0) {
					AssetNewVal assetNewVal = assetNewValList.get(0);
					currentRow.createCell(14).setCellValue(assetNewVal.getHeader16());
					currentRow.createCell(16).setCellValue(assetNewVal.getAttr21());
					currentRow.createCell(18).setCellValue(assetNewVal.getAttr18());
					currentRow.createCell(22).setCellValue(assetNewVal.getAttr4());
					currentRow.createCell(24).setCellValue(assetNewVal.getAttr39());
					currentRow.createCell(26).setCellValue(assetNewVal.getAttr9());
					currentRow.createCell(30).setCellValue(assetNewVal.getAttr33());
					currentRow.createCell(32).setCellValue(assetNewVal.getAttr10());
					currentRow.createCell(34).setCellValue(assetNewVal.getAttr15());
					currentRow.createCell(36).setCellValue(assetNewVal.getAttr13());
					currentRow.createCell(38).setCellValue(assetNewVal.getAttr25());
					currentRow.createCell(40).setCellValue(assetNewVal.getAttr24());
					currentRow.createCell(44).setCellValue(assetNewVal.getHeader58());
					/**
					 * Enabled
					 * 
					 * Audit Results,IBX Audit Status,IBX Reconciliation Status
					 */
					if(StringUtils.isNotEmpty(assetNewVal.getAttr335()))
						currentRow.getCell(43).setCellValue(assetNewVal.getAttr335());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader61()))
					currentRow.getCell(45).setCellValue(assetNewVal.getHeader61());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader66()))
					currentRow.getCell(46).setCellValue(assetNewVal.getHeader66());
				}
			}
		}
	}
	
	public void constructNCCAuditData(Workbook workbook,List<SnapshotSiebelAssetDa> assetList,CellStyle dateCellStyle,
			HashMap<String, List<AssetNewVal>> assetNewValMap, HashMap<String,List<CxiErrorTbl>> errorMap) {
		XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet(PRODUCT_NCC_SHEET);
		for (int i = 4; i < assetList.size()+4; i++) {
			Row currentRow = sheetObj.createRow(i);
			SnapshotSiebelAssetDa assetDa = (SnapshotSiebelAssetDa) assetList.get(i-4);
			currentRow.createCell(0).setCellValue(assetDa.getDfrLineId());
			currentRow.createCell(1).setCellValue(assetDa.getHeader38());
			if (errorMap != null && !errorMap.isEmpty() && errorMap.size() > 0) {
				assetDa.setCxiErrorTbls((List<CxiErrorTbl>) errorMap.get(assetDa.getDfrLineId()));
				if (assetDa.getCxiErrorTbls() != null && !assetDa.getCxiErrorTbls().isEmpty()) {
					StringBuilder errorString = new StringBuilder();
					assetDa.getCxiErrorTbls().stream().forEach(error -> {
						if (isErrorDisplayable(error)) {
							errorString.append(error.getErrorCode()).append(" - ").append(error.getErrorName())
									.append("\n");
						}
					});
					currentRow.createCell(2).setCellValue(errorString.toString());
				}
			}
			currentRow.createCell(3).setCellValue(assetDa.getHeader24());
			currentRow.createCell(4).setCellValue(assetDa.getHeader20());
			currentRow.createCell(5).setCellValue(assetDa.getHeader2());
			currentRow.createCell(6).setCellValue(assetDa.getHeader3());
			currentRow.createCell(7).setCellValue(assetDa.getHeader7());
			currentRow.createCell(8).setCellValue(assetDa.getHeader6());
			currentRow.createCell(9).setCellValue(assetDa.getHeader31());
			currentRow.createCell(10).setCellValue(assetDa.getHeader10());
			currentRow.createCell(11).setCellValue(assetDa.getHeader18());
			currentRow.createCell(12).setCellValue(assetDa.getHeader45());
			currentRow.createCell(13).setCellValue(assetDa.getHeader16());
			currentRow.createCell(15).setCellValue(assetDa.getAttr181());
			currentRow.createCell(16).setCellValue(assetDa.getAttr180());
			currentRow.createCell(18).setCellValue(assetDa.getAttr11());
			currentRow.createCell(20).setCellValue(assetDa.getAttr22());
			currentRow.createCell(22).setCellValue(assetDa.getAttr24());
			currentRow.createCell(24).setCellValue(assetDa.getAttr25());
			currentRow.createCell(26).setCellValue(assetDa.getAttr12());
			currentRow.createCell(28).setCellValue(assetDa.getAttr253());
			currentRow.createCell(30).setCellValue(assetDa.getAttr231());
			currentRow.createCell(32).setCellValue(assetDa.getAttr248());
			currentRow.createCell(34).setCellValue(assetDa.getAttr249());
			currentRow.createCell(36).setCellValue(assetDa.getAttr250());
			currentRow.createCell(38).setCellValue(assetDa.getAttr234());
			currentRow.createCell(40).setCellValue(assetDa.getAttr240());
			currentRow.createCell(42).setCellValue(assetDa.getAttr228());
			currentRow.createCell(44).setCellValue(assetDa.getAttr47());
			currentRow.createCell(46).setCellValue(assetDa.getAttr49());
			currentRow.createCell(48).setCellValue(assetDa.getAttr50());
			currentRow.createCell(50).setCellValue(assetDa.getAttr56());
			currentRow.createCell(52).setCellValue(assetDa.getAttr58());
			currentRow.createCell(54).setCellValue(assetDa.getAttr59());
			currentRow.createCell(56).setCellValue(assetDa.getAttr75());
			currentRow.createCell(58).setCellValue(assetDa.getAttr77());
			currentRow.createCell(60).setCellValue(assetDa.getAttr78());
			currentRow.createCell(62).setCellValue(assetDa.getAttr84());
			currentRow.createCell(64).setCellValue(assetDa.getAttr86());
			currentRow.createCell(66).setCellValue(assetDa.getAttr87());
			currentRow.createCell(68).setCellValue(assetDa.getAttr105());
			currentRow.createCell(70).setCellValue(assetDa.getAttr107());
			currentRow.createCell(72).setCellValue(assetDa.getAttr108());
			currentRow.createCell(74).setCellValue(assetDa.getAttr133());
			currentRow.createCell(76).setCellValue(assetDa.getAttr135());
			currentRow.createCell(78).setCellValue(assetDa.getAttr136());
			currentRow.createCell(80).setCellValue(assetDa.getAttr137());
			currentRow.createCell(82).setCellValue(assetDa.getAttr139());
			currentRow.createCell(84).setCellValue(assetDa.getAttr140());
			currentRow.createCell(86).setCellValue(assetDa.getAttr141());
			currentRow.createCell(88).setCellValue(assetDa.getAttr143());
			currentRow.createCell(90).setCellValue(assetDa.getAttr144());
			currentRow.createCell(92).setCellValue(assetDa.getAttr146());
			currentRow.createCell(94).setCellValue(assetDa.getAttr148());
			currentRow.createCell(96).setCellValue(assetDa.getAttr149());
			currentRow.createCell(98).setCellValue(assetDa.getAttr150());
			currentRow.createCell(100).setCellValue(assetDa.getAttr152());
			currentRow.createCell(102).setCellValue(assetDa.getAttr153());
			currentRow.createCell(104).setCellValue(assetDa.getAttr154());
			currentRow.createCell(106).setCellValue(assetDa.getAttr156());
			currentRow.createCell(108).setCellValue(assetDa.getAttr157());
			currentRow.createCell(110).setCellValue(assetDa.getAttr173());
			currentRow.createCell(112).setCellValue(assetDa.getAttr175());
			currentRow.createCell(114).setCellValue(assetDa.getAttr176());
			currentRow.createCell(116).setCellValue(assetDa.getAttr215());
			currentRow.createCell(117).setCellValue(assetDa.getAttr223());
			currentRow.createCell(119).setCellStyle(dateCellStyle);
			currentRow.getCell(119).setCellValue(assetDa.getHeader17());
			currentRow.createCell(125).setCellValue(assetDa.getHeader26());
			
			currentRow.createCell(121).setCellValue(assetDa.getAttr335());
			currentRow.createCell(123).setCellValue(assetDa.getHeader61());
			currentRow.createCell(124).setCellValue(assetDa.getHeader66());
			
			if (assetNewValMap != null && assetNewValMap.containsKey(assetDa.getDfrLineId())) {
				List<AssetNewVal> assetNewValList = (List<AssetNewVal>) assetNewValMap.get(assetDa.getDfrLineId());
				if (assetNewValList != null && assetNewValList.size() > 0) {
					AssetNewVal assetNewVal = assetNewValList.get(0);
					currentRow.createCell(14).setCellValue(assetNewVal.getHeader16());
					currentRow.createCell(17).setCellValue(assetNewVal.getAttr180());
					currentRow.createCell(19).setCellValue(assetNewVal.getAttr11());
					currentRow.createCell(21).setCellValue(assetNewVal.getAttr22());
					currentRow.createCell(23).setCellValue(assetNewVal.getAttr24());
					currentRow.createCell(25).setCellValue(assetNewVal.getAttr25());
					currentRow.createCell(27).setCellValue(assetNewVal.getAttr12());
					currentRow.createCell(29).setCellValue(assetNewVal.getAttr253());
					currentRow.createCell(31).setCellValue(assetNewVal.getAttr231());
					currentRow.createCell(33).setCellValue(assetNewVal.getAttr248());
					currentRow.createCell(35).setCellValue(assetNewVal.getAttr249());
					currentRow.createCell(37).setCellValue(assetNewVal.getAttr250());
					currentRow.createCell(39).setCellValue(assetNewVal.getAttr234());
					currentRow.createCell(41).setCellValue(assetNewVal.getAttr240());
					currentRow.createCell(43).setCellValue(assetNewVal.getAttr228());
					currentRow.createCell(45).setCellValue(assetNewVal.getAttr47());
					currentRow.createCell(47).setCellValue(assetNewVal.getAttr49());
					currentRow.createCell(49).setCellValue(assetNewVal.getAttr50());
					currentRow.createCell(51).setCellValue(assetNewVal.getAttr56());
					currentRow.createCell(53).setCellValue(assetNewVal.getAttr58());
					currentRow.createCell(55).setCellValue(assetNewVal.getAttr59());
					currentRow.createCell(57).setCellValue(assetNewVal.getAttr75());
					currentRow.createCell(59).setCellValue(assetNewVal.getAttr77());
					currentRow.createCell(61).setCellValue(assetNewVal.getAttr78());
					currentRow.createCell(63).setCellValue(assetNewVal.getAttr84());
					currentRow.createCell(65).setCellValue(assetNewVal.getAttr86());
					currentRow.createCell(67).setCellValue(assetNewVal.getAttr87());
					currentRow.createCell(69).setCellValue(assetNewVal.getAttr105());
					currentRow.createCell(71).setCellValue(assetNewVal.getAttr107());
					currentRow.createCell(73).setCellValue(assetNewVal.getAttr108());
					currentRow.createCell(75).setCellValue(assetNewVal.getAttr133());
					currentRow.createCell(77).setCellValue(assetNewVal.getAttr135());
					currentRow.createCell(79).setCellValue(assetNewVal.getAttr136());
					currentRow.createCell(81).setCellValue(assetNewVal.getAttr137());
					currentRow.createCell(83).setCellValue(assetNewVal.getAttr139());
					currentRow.createCell(85).setCellValue(assetNewVal.getAttr140());
					currentRow.createCell(87).setCellValue(assetNewVal.getAttr141());
					currentRow.createCell(89).setCellValue(assetNewVal.getAttr143());
					currentRow.createCell(91).setCellValue(assetNewVal.getAttr144());
					currentRow.createCell(93).setCellValue(assetNewVal.getAttr146());
					currentRow.createCell(95).setCellValue(assetNewVal.getAttr148());
					currentRow.createCell(97).setCellValue(assetNewVal.getAttr149());
					currentRow.createCell(99).setCellValue(assetNewVal.getAttr150());
					currentRow.createCell(101).setCellValue(assetNewVal.getAttr152());
					currentRow.createCell(103).setCellValue(assetNewVal.getAttr153());
					currentRow.createCell(105).setCellValue(assetNewVal.getAttr154());
					currentRow.createCell(107).setCellValue(assetNewVal.getAttr156());
					currentRow.createCell(109).setCellValue(assetNewVal.getAttr157());
					currentRow.createCell(111).setCellValue(assetNewVal.getAttr173());
					currentRow.createCell(113).setCellValue(assetNewVal.getAttr175());
					currentRow.createCell(115).setCellValue(assetNewVal.getAttr176());
					currentRow.createCell(122).setCellValue(assetNewVal.getHeader58());
					/**
					 * Enabled
					 * 
					 * Audit Results,IBX Audit Status,IBX Reconciliation Status
					 */
					if(StringUtils.isNotEmpty(assetNewVal.getAttr335()))
						currentRow.getCell(121).setCellValue(assetNewVal.getAttr335());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader61()))
					currentRow.getCell(123).setCellValue(assetNewVal.getHeader61());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader66()))
					currentRow.getCell(124).setCellValue(assetNewVal.getHeader66());
				}
			}
		}
	}
	
	public void constructDemacationPointAuditData(Workbook workbook,List<SnapshotSiebelAssetDa> assetList,CellStyle dateCellStyle,
			HashMap<String, List<AssetNewVal>> assetNewValMap, HashMap<String,List<CxiErrorTbl>> errorMap) {
		XSSFSheet sheetObj = (XSSFSheet) workbook.getSheet(PRODUCT_DEMARCATION_SHEET);
		for (int i = 4; i < assetList.size()+4; i++) {
			Row currentRow = sheetObj.createRow(i);
			SnapshotSiebelAssetDa assetDa = (SnapshotSiebelAssetDa) assetList.get(i-4);
			currentRow.createCell(0).setCellValue(assetDa.getDfrLineId());
			currentRow.createCell(1).setCellValue(assetDa.getHeader38());
			if (errorMap != null && !errorMap.isEmpty() && errorMap.size() > 0) {
				assetDa.setCxiErrorTbls(((List<CxiErrorTbl>) errorMap.get(assetDa.getDfrLineId())));
				if (assetDa.getCxiErrorTbls() != null && !assetDa.getCxiErrorTbls().isEmpty()) {
					StringBuilder errorString = new StringBuilder();
					assetDa.getCxiErrorTbls().stream().forEach(error -> {
						if (isErrorDisplayable(error)) {
							errorString.append(error.getErrorCode()).append(" - ").append(error.getErrorName())
									.append("\n");
						}
					});
					currentRow.createCell(2).setCellValue(errorString.toString());
				}
			}
			currentRow.createCell(3).setCellValue(assetDa.getHeader24());
			currentRow.createCell(4).setCellValue(assetDa.getHeader20());
			currentRow.createCell(5).setCellValue(assetDa.getHeader2());
			currentRow.createCell(6).setCellValue(assetDa.getHeader3());
			currentRow.createCell(7).setCellValue(assetDa.getHeader7());
			currentRow.createCell(8).setCellValue(assetDa.getHeader6());
			currentRow.createCell(9).setCellValue(assetDa.getHeader31());
			currentRow.createCell(10).setCellValue(assetDa.getHeader10());
			currentRow.createCell(11).setCellValue(assetDa.getHeader18());
			currentRow.createCell(12).setCellValue(assetDa.getHeader45());
			currentRow.createCell(13).setCellValue(assetDa.getHeader16());
			currentRow.createCell(15).setCellValue(assetDa.getAttr18());
			currentRow.createCell(19).setCellValue(assetDa.getAttr91());
			currentRow.createCell(21).setCellStyle(dateCellStyle);
			currentRow.getCell(21).setCellValue(assetDa.getHeader17());
			currentRow.createCell(26).setCellValue(assetDa.getHeader26());
			
			currentRow.createCell(22).setCellValue(assetDa.getAttr335());
			currentRow.createCell(24).setCellValue(assetDa.getHeader61());
			currentRow.createCell(25).setCellValue(assetDa.getHeader66());
			
			if (assetNewValMap != null && assetNewValMap.containsKey(assetDa.getDfrLineId())) {
				List<AssetNewVal> assetNewValList = (List<AssetNewVal>) assetNewValMap.get(assetDa.getDfrLineId());
				if (assetNewValList != null && assetNewValList.size() > 0) {
					AssetNewVal assetNewVal = assetNewValList.get(0);
					currentRow.createCell(14).setCellValue(assetNewVal.getHeader16());
					currentRow.createCell(16).setCellValue(assetNewVal.getAttr18());
					currentRow.createCell(20).setCellValue(assetNewVal.getAttr91());
					currentRow.createCell(23).setCellValue(assetNewVal.getHeader58());
					/**
					 * Enabled
					 * 
					 * Audit Results,IBX Audit Status,IBX Reconciliation Status
					 */
					if(StringUtils.isNotEmpty(assetNewVal.getAttr335()))
						currentRow.getCell(22).setCellValue(assetNewVal.getAttr335());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader61()))
					currentRow.getCell(24).setCellValue(assetNewVal.getHeader61());
					if(StringUtils.isNotEmpty(assetNewVal.getHeader66()))
					currentRow.getCell(25).setCellValue(assetNewVal.getHeader66());
				}
			}
		}
	}
	
	@RequestMapping(value = "/savePhysicalAuditData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> savePhysicalAuditData(@RequestParam("file") MultipartFile fileObj,@RequestParam("dfrId") String dfrId,HttpResponse httpResp) throws Exception {
		FileInputStream input = null;
		Workbook workbook = null;
		HashMap<String,Object> resultMap = new HashMap<>();
		List<SnapshotSiebelAssetDa> assetList = new ArrayList<SnapshotSiebelAssetDa>();
		List<AssetNewVal> assetNewValListInsert = new ArrayList<AssetNewVal>();
		List<AssetNewVal> assetNewValListUpdate = new ArrayList<AssetNewVal>();
		List<ErrorDetail> errorDetailList = new ArrayList<>();
		HashSet<String> dfrLineIdSet = new HashSet<String>();
		List<SaveAssetForm> saveAssetForms = new ArrayList<>();
		int assetInsertSize = 0;
		int assetUpdateSize = 0;
		try {
			input = (FileInputStream) fileObj.getInputStream();
			workbook = new XSSFWorkbook(input);
			if (workbook != null && workbook.getNumberOfSheets() > 0) {
				parseCageAsset(workbook,assetList,assetNewValListInsert,assetNewValListUpdate,dfrId,dfrLineIdSet,errorDetailList);
				parseCabinetAsset(workbook, assetList, assetNewValListInsert,assetNewValListUpdate,dfrId,dfrLineIdSet,errorDetailList);
				parseACCircuitAsset(workbook, assetList, assetNewValListInsert,assetNewValListUpdate,dfrId,dfrLineIdSet,errorDetailList);
				parsePatchPanelAsset(workbook, assetList, assetNewValListInsert,assetNewValListUpdate,dfrId,dfrLineIdSet,errorDetailList);
				parseNCCAsset(workbook, assetList, assetNewValListInsert,assetNewValListUpdate,dfrId,dfrLineIdSet,errorDetailList);
				parseDemarcationAsset(workbook, assetList, assetNewValListInsert, assetNewValListUpdate, dfrId,dfrLineIdSet,errorDetailList);
				if (errorDetailList.size() == 0 && errorDetailList.isEmpty()) {
					editDfrBusiness.saveOrUpdatePhysicalAuditData(assetList, assetNewValListInsert,assetNewValListUpdate,saveAssetForms);
				}
				DfrMaster dfr = editDfrBusiness.getDfrMasterById(dfrId);
				String snapshotFilter = dfr.getSsFilter();
				ProductFilter searchFilters = new ObjectMapper().readValue(snapshotFilter, ProductFilter.class);
				ProductDataGrid dataGrid = null;
				if (searchFilters != null) {
						dataGrid = editDfrBusiness.getAllProductAttributeView(searchFilters);
						dataGrid.setDfrid(searchFilters.getDfrId());
				} else {
					dataGrid = null;
				}
					try {
						editDfrBusiness.saveDependentAttributes(dataGrid);
					} catch (Exception e) {
						logger.error("Error occured while updating dependent",e);
						resultMap.put("saveDependentAttributes", "failure");
					}
					
					try {
						editDfrBusiness.updatePhyChangeSummaryValues(dataGrid);
					} catch (Exception e) {
						logger.error("Error occured while change summary",e);
						resultMap.put("updateChangeSummary", "failure");
					}
				assetList.clear();
				assetInsertSize = assetNewValListInsert.size();
				assetUpdateSize = assetNewValListUpdate.size();
				assetNewValListInsert.clear();
				assetNewValListUpdate.clear();
				dfrLineIdSet.clear();
			}
		} catch (Exception ex) {
			if (ex instanceof RuntimeException && ex.getCause() instanceof WorkbookNotFoundException) {
				resultMap.put("message", "File has External Sheet Reference in Cell Formula. Please remove them and re-upload.");
				return buildResponse(resultMap, "Available", "Not Available");
			}
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			logger.error("UUID : "+uuid+" Error : "+ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		} finally {
			if (input != null) {
				input.close();
			}
			if (workbook != null) {
				workbook.close();
			}
			HttpClientUtils.closeQuietly(httpResp);
		}
		if (errorDetailList != null && !errorDetailList.isEmpty() && errorDetailList.size() > 0) {
			resultMap.put("message", "Physical Audit Data Import has failed.");
			resultMap.put("errorDetailList", errorDetailList);
		} else {
			resultMap.put("message", "Physical Audit Data has been imported successfully."+assetInsertSize+" records inserted and "+ assetUpdateSize+" records updated");			
		}
		return buildResponse(resultMap, "Available", "Not Available");
	}
	
	public void parseCageAsset(Workbook workbookObj, List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate, String dfrId,
			HashSet<String> dfrLineIdSet, List<ErrorDetail> errorDetailList) throws ParseException {
		XSSFSheet sheetObj = (XSSFSheet) workbookObj.getSheet(PRODUCT_CAGE_SHEET);
		FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbookObj);
		SnapshotSiebelAssetDa assetDa = null;
		AssetNewVal assetNewValObj = null;
		ErrorMessage messageObj = null;
		ErrorDetail detailObj = new ErrorDetail();
		detailObj.setProductName(PRODUCT_CAGE);
		if (sheetObj != null && sheetObj.getLastRowNum() > 3) {
			for (int i = 4; i <= sheetObj.getLastRowNum(); i++) {
				Row currentRow = sheetObj.getRow(i);

				if (!(isRowEmpty(currentRow)) && isMandatoryFieldsPresent(currentRow)) {
					String dfrLineId = currentRow.getCell(0) != null
							? getDataFormatter().formatCellValue(currentRow.getCell(0),formulaEvaluatorObj) : null;
					if (dfrLineId != null && !dfrLineId.trim().isEmpty()) {
						if (dfrLineIdSet.contains(dfrLineId)) {
							messageObj = new ErrorMessage();
							messageObj.setRowNum(i + 1);
							messageObj.setCellNum(0+1);
							messageObj.setMessageStr("Duplicate DFR Line ID # " + dfrLineId + ";");
							detailObj.getErrorList().add(messageObj);
						} else {
							dfrLineIdSet.add(dfrLineId);
						}
						assetNewValObj = new AssetNewVal();
						assetNewValObj.setDfrLineId(dfrLineId);
						assetNewValObj.setHeader1(dfrLineId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						parseDate(i, currentRow, detailObj, PRODUCT_CAGE, null, assetNewValObj, 35);
						assetNewValListUpdate.add(getCageAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					} else {
						assetDa = new SnapshotSiebelAssetDa();
						assetNewValObj = new AssetNewVal();
						assetDa.setHeader24(currentRow.getCell(3) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
						assetDa.setHeader20(currentRow.getCell(4) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
						assetDa.setHeader2(currentRow.getCell(5) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : null);
						assetDa.setHeader3(currentRow.getCell(6) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader7(currentRow.getCell(7) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
						assetDa.setHeader6(currentRow.getCell(8) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
						assetDa.setHeader31(currentRow.getCell(9) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
						assetDa.setHeader10(currentRow.getCell(10) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
						assetDa.setHeader18(currentRow.getCell(11) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
						assetDa.setHeader45(currentRow.getCell(12) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
						assetDa.setHeader16(currentRow.getCell(14) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
						parseDate(i, currentRow, detailObj, PRODUCT_CAGE, assetDa, null, 35);
						assetDa.setHeader52(assetDa.getHeader16());
						assetDa.setHeader26(currentRow.getCell(41) != null
								? getDataFormatter().formatCellValue(currentRow.getCell(41),formulaEvaluatorObj) : null);
						assetDa.setHeader8(getIBXFromSystemName(assetDa.getHeader16()));
						setCreateAndUpdateDate(assetDa);
						setDfrId(assetDa, null, dfrId);
						assetDa.setHeader57(getValueForHeaders());
						assetDa.setHeader60(getValueForHeaders());
						assetList.add(assetDa);
						setCreateDate(assetNewValObj);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader52(assetDa.getHeader52());
						assetNewValObj.setHeader26(assetDa.getHeader26());
						assetNewValObj.setHeader8(assetDa.getHeader8());
						assetNewValObj.setHeader17(assetDa.getHeader17() != null ? assetDa.getHeader17() : null);
						assetNewValListInsert.add(getCageAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					}
				}
			}
		}
		if (detailObj != null && detailObj.getErrorList() != null && !detailObj.getErrorList().isEmpty()) {
			errorDetailList.add(detailObj);
		}
	}
	
	public AssetNewVal getCageAssetNewVal(Row currentRow,AssetNewVal assetNewValObj, FormulaEvaluator formulaEvaluatorObj) {
		assetNewValObj.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : editDfrBusiness.getNewPOEAssetNumber());
		assetNewValObj.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr335(currentRow.getCell(37) != null ? getDataFormatter().formatCellValue(currentRow.getCell(37),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(38) != null ? getDataFormatter().formatCellValue(currentRow.getCell(38),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader61(currentRow.getCell(39) != null ? getDataFormatter().formatCellValue(currentRow.getCell(39),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader66(currentRow.getCell(40) != null ? getDataFormatter().formatCellValue(currentRow.getCell(40),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr14(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr53(currentRow.getCell(20) != null ? getDataFormatter().formatCellValue(currentRow.getCell(20),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr29(currentRow.getCell(22) != null ? getDataFormatter().formatCellValue(currentRow.getCell(22),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr28(currentRow.getCell(24) != null ? getDataFormatter().formatCellValue(currentRow.getCell(24),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr87(currentRow.getCell(26) != null ? getDataFormatter().formatCellValue(currentRow.getCell(26),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr24(currentRow.getCell(28) != null ? getDataFormatter().formatCellValue(currentRow.getCell(28),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr23(currentRow.getCell(30) != null ? getDataFormatter().formatCellValue(currentRow.getCell(30),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr84(currentRow.getCell(32) != null ? getDataFormatter().formatCellValue(currentRow.getCell(32),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr21(currentRow.getCell(34) != null ? getDataFormatter().formatCellValue(currentRow.getCell(34),formulaEvaluatorObj) : null);
		return assetNewValObj;
	}
	
	public void parseCabinetAsset(Workbook workbookObj, List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate, String dfrId,
			HashSet<String> dfrLineIdSet, List<ErrorDetail> errorDetailList) throws ParseException {
		XSSFSheet sheetObj = (XSSFSheet) workbookObj.getSheet(PRODUCT_CABINET_SHEET);
		FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbookObj);
		SnapshotSiebelAssetDa assetDa = null;
		AssetNewVal assetNewValObj = null;
		ErrorMessage messageObj = null;
		ErrorDetail detailObj = new ErrorDetail();
		detailObj.setProductName(PRODUCT_CABINET);
		if (sheetObj != null && sheetObj.getLastRowNum() > 3) {
			for (int i = 4; i <= sheetObj.getLastRowNum(); i++) {
				Row currentRow = sheetObj.getRow(i);
				if (!(isRowEmpty(currentRow)) && isMandatoryFieldsPresent(currentRow)) {
					String dfrLineId = currentRow.getCell(0) != null ? getDataFormatter().formatCellValue(currentRow.getCell(0),formulaEvaluatorObj)
							: null;
					if (dfrLineId != null && !dfrLineId.trim().isEmpty()) {
						if (dfrLineIdSet.contains(dfrLineId)) {
							messageObj = new ErrorMessage();
							messageObj.setRowNum(i+1);
							messageObj.setCellNum(0+1);
							messageObj.setMessageStr("Duplicate DFR Line ID # " + dfrLineId + ";");
							detailObj.getErrorList().add(messageObj);
						} else {
							dfrLineIdSet.add(dfrLineId);
						}
						assetNewValObj = new AssetNewVal();
						assetNewValObj.setDfrLineId(dfrLineId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader1(dfrLineId);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						parseDate(i, currentRow, detailObj, PRODUCT_CABINET, null, assetNewValObj, 45);
						assetNewValListUpdate.add(getCabinetAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					} else {
						assetDa = new SnapshotSiebelAssetDa();
						assetNewValObj = new AssetNewVal();
						assetDa.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
						assetDa.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
						assetDa.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : null);
						assetDa.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
						assetDa.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
						assetDa.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
						assetDa.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
						assetDa.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
						assetDa.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
						assetDa.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
						assetDa.setHeader14(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
						parseDate(i, currentRow, detailObj, PRODUCT_CABINET, assetDa, null, 45);
						assetDa.setHeader52(assetDa.getHeader16());
						if (StringUtils.isNotBlank(assetDa.getHeader14())) {
							assetDa.setHeader12(assetDa.getHeader10()+":"+assetDa.getHeader14());
						}
						assetDa.setHeader8(getIBXFromSystemName(assetDa.getHeader16()));
						assetDa.setHeader26(currentRow.getCell(51) != null ? getDataFormatter().formatCellValue(currentRow.getCell(51),formulaEvaluatorObj) : null);
						setCreateAndUpdateDate(assetDa);
						setDfrId(assetDa, null, dfrId);
						assetDa.setHeader57(getValueForHeaders());
						assetDa.setHeader60(getValueForHeaders());
						assetList.add(assetDa);
						setCreateDate(assetNewValObj);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader52(assetDa.getHeader52());
						assetNewValObj.setHeader26(assetDa.getHeader26());
						if (assetDa.getHeader12() != null) {
							assetNewValObj.setHeader12(assetDa.getHeader12());
						}
						assetNewValObj.setHeader8(assetDa.getHeader8());
						assetNewValObj.setHeader17(assetDa.getHeader17() != null ? assetDa.getHeader17() : null);
						assetNewValListInsert.add(getCabinetAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					}
				}
			}
		}
		if (detailObj != null && detailObj.getErrorList() != null && !detailObj.getErrorList().isEmpty()) {
			errorDetailList.add(detailObj);
		}
	}
	
	public AssetNewVal getCabinetAssetNewVal(Row currentRow,AssetNewVal assetNewValObj,FormulaEvaluator formulaEvaluatorObj) {
		assetNewValObj.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : editDfrBusiness.getNewPOEAssetNumber());
		assetNewValObj.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr18(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader14(assetNewValObj.getAttr18());
		
		assetNewValObj.setAttr335(currentRow.getCell(47) != null ? getDataFormatter().formatCellValue(currentRow.getCell(47),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(48) != null ? getDataFormatter().formatCellValue(currentRow.getCell(48),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader61(currentRow.getCell(49) != null ? getDataFormatter().formatCellValue(currentRow.getCell(49),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader66(currentRow.getCell(50) != null ? getDataFormatter().formatCellValue(currentRow.getCell(50),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr43(currentRow.getCell(18) != null ? getDataFormatter().formatCellValue(currentRow.getCell(18),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr11(currentRow.getCell(22) != null ? getDataFormatter().formatCellValue(currentRow.getCell(22),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr41(currentRow.getCell(24) != null ? getDataFormatter().formatCellValue(currentRow.getCell(24),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr66(currentRow.getCell(26) != null ? getDataFormatter().formatCellValue(currentRow.getCell(26),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr75(currentRow.getCell(28) != null ? getDataFormatter().formatCellValue(currentRow.getCell(28),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr55(currentRow.getCell(30) != null ? getDataFormatter().formatCellValue(currentRow.getCell(30),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr62(currentRow.getCell(32) != null ? getDataFormatter().formatCellValue(currentRow.getCell(32),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr70(currentRow.getCell(34) != null ? getDataFormatter().formatCellValue(currentRow.getCell(34),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr73(currentRow.getCell(36) != null ? getDataFormatter().formatCellValue(currentRow.getCell(36),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr74(currentRow.getCell(38) != null ? getDataFormatter().formatCellValue(currentRow.getCell(38),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr63(currentRow.getCell(40) != null ? getDataFormatter().formatCellValue(currentRow.getCell(40),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr88(currentRow.getCell(42) != null ? getDataFormatter().formatCellValue(currentRow.getCell(42),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr53(currentRow.getCell(44) != null ? getDataFormatter().formatCellValue(currentRow.getCell(44),formulaEvaluatorObj) : null);
		if (StringUtils.isNotBlank(assetNewValObj.getHeader14())) {
			assetNewValObj.setHeader12(assetNewValObj.getHeader10()+":"+assetNewValObj.getHeader14());
		}
		return assetNewValObj;
	}
	
	public void parseACCircuitAsset(Workbook workbookObj, List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate, String dfrId,
			HashSet<String> dfrLineIdSet, List<ErrorDetail> errorDetailList) throws ParseException {
		XSSFSheet sheetObj = (XSSFSheet) workbookObj.getSheet(PRODUCT_AC_CIRCUIT_SHEET);
		FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbookObj);
		SnapshotSiebelAssetDa assetDa = null;
		AssetNewVal assetNewValObj = null;
		ErrorMessage messageObj = null;
		ErrorDetail detailObj = new ErrorDetail();
		detailObj.setProductName(PRODUCT_AC_DC_CIRCUIT);
		if (sheetObj != null && sheetObj.getLastRowNum() > 3) {
			for (int i = 4; i <= sheetObj.getLastRowNum(); i++) {
				Row currentRow = sheetObj.getRow(i);
				if (!(isRowEmpty(currentRow)) && isMandatoryFieldsPresent(currentRow)) {
					String dfrLineId = currentRow.getCell(0) != null ? getDataFormatter().formatCellValue(currentRow.getCell(0),formulaEvaluatorObj)
							: null;
					if (dfrLineId != null && !dfrLineId.isEmpty()) {
						if (dfrLineIdSet.contains(dfrLineId)) {
							messageObj = new ErrorMessage();
							messageObj.setRowNum(i+1);
							messageObj.setCellNum(0+1);
							messageObj.setMessageStr("Duplicate DFR Line ID # " + dfrLineId + ";");
							detailObj.getErrorList().add(messageObj);
						} else {
							dfrLineIdSet.add(dfrLineId);
						}
						assetNewValObj = new AssetNewVal();
						assetNewValObj.setDfrLineId(dfrLineId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader1(dfrLineId);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						parseDate(i, currentRow, detailObj, PRODUCT_AC_DC_CIRCUIT, null, assetNewValObj, 42);
						assetNewValListUpdate.add(getACCircuitAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					} else {
						assetDa = new SnapshotSiebelAssetDa();
						assetNewValObj = new AssetNewVal();
						assetDa.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
						assetDa.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
						assetDa.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : null);
						assetDa.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
						assetDa.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
						assetDa.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
						assetDa.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
						assetDa.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
						assetDa.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
						assetDa.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
						parseDate(i, currentRow, detailObj, PRODUCT_AC_DC_CIRCUIT, assetDa, null, 42);
						assetDa.setHeader52(assetDa.getHeader16());
						String newCabNumber = currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null;
						if (StringUtils.isNotBlank(newCabNumber)) {
							assetDa.setHeader12(assetDa.getHeader10()+":"+getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj));
						}
						assetDa.setHeader8(getIBXFromSystemName(assetDa.getHeader16()));
						setCreateAndUpdateDate(assetDa);
						setDfrId(assetDa, null, dfrId);
						assetDa.setHeader26(currentRow.getCell(48) != null ? getDataFormatter().formatCellValue(currentRow.getCell(48),formulaEvaluatorObj) : null);
						assetDa.setHeader57(getValueForHeaders());
						assetDa.setHeader60(getValueForHeaders());
						assetList.add(assetDa);
						setCreateDate(assetNewValObj);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader52(assetDa.getHeader52());
						if (assetDa.getHeader12() != null) {
							assetNewValObj.setHeader12(assetDa.getHeader12());
						}
						assetNewValObj.setHeader26(assetDa.getHeader26());
						assetNewValObj.setHeader8(assetDa.getHeader8());
						assetNewValObj.setHeader17(assetDa.getHeader17() != null ? assetDa.getHeader17() : null);
						assetNewValListInsert.add(getACCircuitAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					}
				}
			}
		}
		if (detailObj != null && detailObj.getErrorList() != null && !detailObj.getErrorList().isEmpty()) {
			errorDetailList.add(detailObj);
		}
	}
	
	public AssetNewVal getACCircuitAssetNewVal(Row currentRow,AssetNewVal assetNewValObj,FormulaEvaluator formulaEvaluatorObj) {
		assetNewValObj.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : editDfrBusiness.getNewPOEAssetNumber());
		assetNewValObj.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(45) != null ? getDataFormatter().formatCellValue(currentRow.getCell(45),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr335(currentRow.getCell(44) != null ? getDataFormatter().formatCellValue(currentRow.getCell(44),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(45) != null ? getDataFormatter().formatCellValue(currentRow.getCell(45),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader61(currentRow.getCell(46) != null ? getDataFormatter().formatCellValue(currentRow.getCell(46),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader66(currentRow.getCell(47) != null ? getDataFormatter().formatCellValue(currentRow.getCell(47),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr18(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
		if (PRODUCT_DC_CIRCUIT.equalsIgnoreCase(assetNewValObj.getHeader20())) {
			assetNewValObj.setAttr70(currentRow.getCell(19) != null ? getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj) : null);
		} else {
			assetNewValObj.setAttr87(currentRow.getCell(19) != null ? getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj) : null);
		}
		assetNewValObj.setAttr23(currentRow.getCell(21) != null ? getDataFormatter().formatCellValue(currentRow.getCell(21),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr25(currentRow.getCell(23) != null ? getDataFormatter().formatCellValue(currentRow.getCell(23),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr26(currentRow.getCell(25) != null ? getDataFormatter().formatCellValue(currentRow.getCell(25),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr27(currentRow.getCell(27) != null ? getDataFormatter().formatCellValue(currentRow.getCell(27),formulaEvaluatorObj) : null);
		if (PRODUCT_AC_CIRCUIT.equalsIgnoreCase(assetNewValObj.getHeader20())) {
			assetNewValObj.setAttr70(currentRow.getCell(29) != null ? getDataFormatter().formatCellValue(currentRow.getCell(29),formulaEvaluatorObj) : null);
		}
		assetNewValObj.setAttr6(currentRow.getCell(31) != null ? getDataFormatter().formatCellValue(currentRow.getCell(31),formulaEvaluatorObj) : null);
		if (PRODUCT_AC_CIRCUIT.equalsIgnoreCase(assetNewValObj.getHeader20())) {
			assetNewValObj.setAttr81(currentRow.getCell(33) != null ? getDataFormatter().formatCellValue(currentRow.getCell(33),formulaEvaluatorObj) : null);
		}
		assetNewValObj.setAttr16(currentRow.getCell(35) != null ? getDataFormatter().formatCellValue(currentRow.getCell(35),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr47(currentRow.getCell(37) != null ? getDataFormatter().formatCellValue(currentRow.getCell(37),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr49(currentRow.getCell(39) != null ? getDataFormatter().formatCellValue(currentRow.getCell(39),formulaEvaluatorObj) : null);
		if (PRODUCT_DC_CIRCUIT.equalsIgnoreCase(assetNewValObj.getHeader20())) {
			assetNewValObj.setAttr63(currentRow.getCell(41) != null ? getDataFormatter().formatCellValue(currentRow.getCell(41),formulaEvaluatorObj) : null);
		} else {
			assetNewValObj.setAttr79(currentRow.getCell(41) != null ? getDataFormatter().formatCellValue(currentRow.getCell(41),formulaEvaluatorObj) : null);
		}
		if (StringUtils.isNotBlank(assetNewValObj.getAttr18())) {
			assetNewValObj.setHeader12(assetNewValObj.getHeader10()+":"+getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj));
		}
		return assetNewValObj;
	}
	
	public void parsePatchPanelAsset(Workbook workbookObj, List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate, String dfrId,
			HashSet<String> dfrLineIdSet, List<ErrorDetail> errorDetailList) throws ParseException {
		XSSFSheet sheetObj = (XSSFSheet) workbookObj.getSheet(PRODUCT_PP_SHEET);
		FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbookObj);
		SnapshotSiebelAssetDa assetDa = null;
		AssetNewVal assetNewValObj = null;
		ErrorMessage messageObj = null;
		ErrorDetail detailObj = new ErrorDetail();
		detailObj.setProductName(PRODUCT_PP);
		if (sheetObj != null && sheetObj.getLastRowNum() > 3) {
			for (int i = 4; i <= sheetObj.getLastRowNum(); i++) {
				Row currentRow = sheetObj.getRow(i);
				if (!(isRowEmpty(currentRow)) && isMandatoryFieldsPresent(currentRow)) {
					String dfrLineId = currentRow.getCell(0) != null ? getDataFormatter().formatCellValue(currentRow.getCell(0),formulaEvaluatorObj)
							: null;
					if (dfrLineId != null && !dfrLineId.isEmpty()) {
						if (dfrLineIdSet.contains(dfrLineId)) {
							messageObj = new ErrorMessage();
							messageObj.setRowNum(i+1);
							messageObj.setCellNum(0+1);
							messageObj.setMessageStr("Duplicate DFR Line ID # " + dfrLineId + ";");
							detailObj.getErrorList().add(messageObj);
						} else {
							dfrLineIdSet.add(dfrLineId);
						}
						assetNewValObj = new AssetNewVal();
						assetNewValObj.setDfrLineId(dfrLineId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader1(dfrLineId);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						parseDate(i, currentRow, detailObj, PRODUCT_PP, null, assetNewValObj, 41);
						assetNewValListUpdate.add(getPPAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					} else {
						assetDa = new SnapshotSiebelAssetDa();
						assetNewValObj = new AssetNewVal();
						assetDa.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
						assetDa.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
						assetDa.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : null);
						assetDa.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
						assetDa.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
						assetDa.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
						assetDa.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
						assetDa.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
						assetDa.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
						assetDa.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
						parseDate(i, currentRow, detailObj, PRODUCT_PP, assetDa, null, 41);
						assetDa.setHeader54(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader52(assetDa.getHeader16());
						String newCabNumber = currentRow.getCell(18) != null ? getDataFormatter().formatCellValue(currentRow.getCell(18),formulaEvaluatorObj) : null;
						if (StringUtils.isNotBlank(newCabNumber)) {
							assetDa.setHeader12(assetDa.getHeader10()+":"+getDataFormatter().formatCellValue(currentRow.getCell(18),formulaEvaluatorObj));
						}
						assetDa.setHeader26(currentRow.getCell(47) != null ? getDataFormatter().formatCellValue(currentRow.getCell(47),formulaEvaluatorObj) : null);
						assetDa.setHeader8(getIBXFromSystemName(assetDa.getHeader16()));
						setCreateAndUpdateDate(assetDa);
						setDfrId(assetDa, null, dfrId);
						assetDa.setHeader57(getValueForHeaders());
						assetDa.setHeader60(getValueForHeaders());
						assetList.add(assetDa);
						setCreateDate(assetNewValObj);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader52(assetDa.getHeader52());
						assetNewValObj.setHeader54(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						if (assetDa.getHeader12() != null) {
							assetNewValObj.setHeader12(assetDa.getHeader12());
						}
						assetNewValObj.setHeader26(assetDa.getHeader26());
						assetNewValObj.setHeader8(assetDa.getHeader8());
						assetNewValObj.setHeader17(assetDa.getHeader17() != null ? assetDa.getHeader17() : null);
						assetNewValListInsert.add(getPPAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					}
				}
			}
		}
		if (detailObj != null && detailObj.getErrorList() != null && !detailObj.getErrorList().isEmpty()) {
			errorDetailList.add(detailObj);
		}
	}
	
	public AssetNewVal getPPAssetNewVal(Row currentRow,AssetNewVal assetNewValObj,FormulaEvaluator formulaEvaluatorObj) {
		assetNewValObj.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : editDfrBusiness.getNewPOEAssetNumber());
		assetNewValObj.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr335(currentRow.getCell(43) != null ? getDataFormatter().formatCellValue(currentRow.getCell(43),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(44) != null ? getDataFormatter().formatCellValue(currentRow.getCell(44),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader61(currentRow.getCell(45) != null ? getDataFormatter().formatCellValue(currentRow.getCell(45),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader66(currentRow.getCell(46) != null ? getDataFormatter().formatCellValue(currentRow.getCell(46),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr21(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr18(currentRow.getCell(18) != null ? getDataFormatter().formatCellValue(currentRow.getCell(18),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr4(currentRow.getCell(22) != null ? getDataFormatter().formatCellValue(currentRow.getCell(22),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr39(currentRow.getCell(24) != null ? getDataFormatter().formatCellValue(currentRow.getCell(24),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr9(currentRow.getCell(26) != null ? getDataFormatter().formatCellValue(currentRow.getCell(26),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr33(currentRow.getCell(30) != null ? getDataFormatter().formatCellValue(currentRow.getCell(30),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr10(currentRow.getCell(32) != null ? getDataFormatter().formatCellValue(currentRow.getCell(32),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr15(currentRow.getCell(34) != null ? getDataFormatter().formatCellValue(currentRow.getCell(34),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr13(currentRow.getCell(36) != null ? getDataFormatter().formatCellValue(currentRow.getCell(36),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr25(currentRow.getCell(38) != null ? getDataFormatter().formatCellValue(currentRow.getCell(38),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr24(currentRow.getCell(40) != null ? getDataFormatter().formatCellValue(currentRow.getCell(40),formulaEvaluatorObj) : null);
		
		String newCabNumber = currentRow.getCell(18) != null ? getDataFormatter().formatCellValue(currentRow.getCell(18),formulaEvaluatorObj) : null;
		if (StringUtils.isNotBlank(newCabNumber)) {
			assetNewValObj.setHeader12(assetNewValObj.getHeader10()+":"+getDataFormatter().formatCellValue(currentRow.getCell(18),formulaEvaluatorObj));
		}
		return assetNewValObj;
	}
	
	public void parseNCCAsset(Workbook workbookObj, List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate, String dfrId,
			HashSet<String> dfrLineIdSet, List<ErrorDetail> errorDetailList) throws ParseException {
		XSSFSheet sheetObj = (XSSFSheet) workbookObj.getSheet(PRODUCT_NCC_SHEET);
		FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbookObj);
		SnapshotSiebelAssetDa assetDa = null;
		AssetNewVal assetNewValObj = null;
		ErrorMessage messageObj = null;
		ErrorDetail detailObj = new ErrorDetail();
		detailObj.setProductName(PRODUCT_NCC);
		if (sheetObj != null && sheetObj.getLastRowNum() > 3) {
			for (int i = 4; i <= sheetObj.getLastRowNum(); i++) {
				Row currentRow = sheetObj.getRow(i);
				if (!(isRowEmpty(currentRow)) && isMandatoryFieldsPresent(currentRow)) {
					String dfrLineId = currentRow.getCell(0) != null ? getDataFormatter().formatCellValue(currentRow.getCell(0),formulaEvaluatorObj)
							: null;
					if (dfrLineId != null && !dfrLineId.isEmpty()) {
						if (dfrLineIdSet.contains(dfrLineId)) {
							messageObj = new ErrorMessage();
							messageObj.setRowNum(i+1);
							messageObj.setCellNum(0+1);
							messageObj.setMessageStr("Duplicate DFR Line ID # " + dfrLineId + ";");
							detailObj.getErrorList().add(messageObj);
						} else {
							dfrLineIdSet.add(dfrLineId);
						}
						assetNewValObj = new AssetNewVal();
						assetNewValObj.setDfrLineId(dfrLineId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader1(dfrLineId);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						parseDate(i, currentRow, detailObj, PRODUCT_NCC, null, assetNewValObj, 119);
						assetNewValListUpdate.add(getNCCAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					} else {
						assetDa = new SnapshotSiebelAssetDa();
						assetNewValObj = new AssetNewVal();
						assetDa.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
						assetDa.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
						assetDa.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : null);
						assetDa.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
						assetDa.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
						assetDa.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
						assetDa.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
						assetDa.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
						assetDa.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
						assetDa.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
						parseDate(i, currentRow, detailObj, PRODUCT_NCC, assetDa, null, 119);
						assetDa.setHeader52(assetDa.getHeader16());
						assetDa.setHeader43(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						
						String newCabNumber = currentRow.getCell(19) != null ? getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj) : null;
						if (StringUtils.isNotBlank(newCabNumber)) {
							assetDa.setHeader12(assetDa.getHeader10()+":"+getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj));
						}
						assetDa.setAttr253(currentRow.getCell(29) != null ? getDataFormatter().formatCellValue(currentRow.getCell(29),formulaEvaluatorObj) : null);
						assetDa.setHeader26(currentRow.getCell(125) != null ? getDataFormatter().formatCellValue(currentRow.getCell(125),formulaEvaluatorObj) : null);
						assetDa.setHeader8(getIBXFromSystemName(assetDa.getHeader16()));
						setCreateAndUpdateDate(assetDa);
						setDfrId(assetDa, null, dfrId);
						assetDa.setHeader57(getValueForHeaders());
						assetDa.setHeader60(getValueForHeaders());
						assetList.add(assetDa);
						setCreateDate(assetNewValObj);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader52(assetDa.getHeader52());
						assetNewValObj.setHeader43(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						if (assetDa.getHeader12() != null) {
							assetNewValObj.setHeader12(assetDa.getHeader12());
						}
						assetNewValObj.setHeader26(assetDa.getHeader26());
						assetNewValObj.setHeader8(assetDa.getHeader8());
						assetNewValObj.setHeader17(assetDa.getHeader17() != null ? assetDa.getHeader17() : null);
						assetNewValListInsert.add(getNCCAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					}
				}
			}
		}
		if (detailObj != null && detailObj.getErrorList() != null && !detailObj.getErrorList().isEmpty()) {
			errorDetailList.add(detailObj);
		}
	}
	
	public AssetNewVal getNCCAssetNewVal(Row currentRow,AssetNewVal assetNewValObj,FormulaEvaluator formulaEvaluatorObj) {
		assetNewValObj.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : editDfrBusiness.getNewPOEAssetNumber());
		assetNewValObj.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr335(currentRow.getCell(121) != null ? getDataFormatter().formatCellValue(currentRow.getCell(121),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(122) != null ? getDataFormatter().formatCellValue(currentRow.getCell(122),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader61(currentRow.getCell(123) != null ? getDataFormatter().formatCellValue(currentRow.getCell(123),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader66(currentRow.getCell(124) != null ? getDataFormatter().formatCellValue(currentRow.getCell(124),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr181(currentRow.getCell(15) != null ? getDataFormatter().formatCellValue(currentRow.getCell(15),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr180(currentRow.getCell(17) != null ? getDataFormatter().formatCellValue(currentRow.getCell(17),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr11(currentRow.getCell(19) != null ? getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr22(currentRow.getCell(21) != null ? getDataFormatter().formatCellValue(currentRow.getCell(21),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr24(currentRow.getCell(23) != null ? getDataFormatter().formatCellValue(currentRow.getCell(23),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr25(currentRow.getCell(25) != null ? getDataFormatter().formatCellValue(currentRow.getCell(25),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr12(currentRow.getCell(27) != null ? getDataFormatter().formatCellValue(currentRow.getCell(27),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr253(currentRow.getCell(29) != null ? getDataFormatter().formatCellValue(currentRow.getCell(29),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr231(currentRow.getCell(31) != null ? getDataFormatter().formatCellValue(currentRow.getCell(31),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr248(currentRow.getCell(33) != null ? getDataFormatter().formatCellValue(currentRow.getCell(33),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr249(currentRow.getCell(35) != null ? getDataFormatter().formatCellValue(currentRow.getCell(35),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr250(currentRow.getCell(37) != null ? getDataFormatter().formatCellValue(currentRow.getCell(37),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr234(currentRow.getCell(39) != null ? getDataFormatter().formatCellValue(currentRow.getCell(39),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr240(currentRow.getCell(41) != null ? getDataFormatter().formatCellValue(currentRow.getCell(41),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr228(currentRow.getCell(43) != null ? getDataFormatter().formatCellValue(currentRow.getCell(43),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr47(currentRow.getCell(45) != null ? getDataFormatter().formatCellValue(currentRow.getCell(45),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr49(currentRow.getCell(47) != null ? getDataFormatter().formatCellValue(currentRow.getCell(47),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr50(currentRow.getCell(49) != null ? getDataFormatter().formatCellValue(currentRow.getCell(49),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr56(currentRow.getCell(51) != null ? getDataFormatter().formatCellValue(currentRow.getCell(51),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr58(currentRow.getCell(53) != null ? getDataFormatter().formatCellValue(currentRow.getCell(53),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr59(currentRow.getCell(55) != null ? getDataFormatter().formatCellValue(currentRow.getCell(55),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr75(currentRow.getCell(57) != null ? getDataFormatter().formatCellValue(currentRow.getCell(57),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr77(currentRow.getCell(59) != null ? getDataFormatter().formatCellValue(currentRow.getCell(59),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr78(currentRow.getCell(61) != null ? getDataFormatter().formatCellValue(currentRow.getCell(61),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr84(currentRow.getCell(63) != null ? getDataFormatter().formatCellValue(currentRow.getCell(63),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr86(currentRow.getCell(65) != null ? getDataFormatter().formatCellValue(currentRow.getCell(65),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr87(currentRow.getCell(67) != null ? getDataFormatter().formatCellValue(currentRow.getCell(67),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr105(currentRow.getCell(69) != null ? getDataFormatter().formatCellValue(currentRow.getCell(69),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr107(currentRow.getCell(71) != null ? getDataFormatter().formatCellValue(currentRow.getCell(71),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr108(currentRow.getCell(73) != null ? getDataFormatter().formatCellValue(currentRow.getCell(73),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr133(currentRow.getCell(75) != null ? getDataFormatter().formatCellValue(currentRow.getCell(75),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr135(currentRow.getCell(77) != null ? getDataFormatter().formatCellValue(currentRow.getCell(77),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr136(currentRow.getCell(79) != null ? getDataFormatter().formatCellValue(currentRow.getCell(79),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr137(currentRow.getCell(81) != null ? getDataFormatter().formatCellValue(currentRow.getCell(81),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr139(currentRow.getCell(83) != null ? getDataFormatter().formatCellValue(currentRow.getCell(83),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr140(currentRow.getCell(85) != null ? getDataFormatter().formatCellValue(currentRow.getCell(85),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr141(currentRow.getCell(87) != null ? getDataFormatter().formatCellValue(currentRow.getCell(87),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr143(currentRow.getCell(89) != null ? getDataFormatter().formatCellValue(currentRow.getCell(89),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr144(currentRow.getCell(91) != null ? getDataFormatter().formatCellValue(currentRow.getCell(91),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr146(currentRow.getCell(93) != null ? getDataFormatter().formatCellValue(currentRow.getCell(93),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr148(currentRow.getCell(95) != null ? getDataFormatter().formatCellValue(currentRow.getCell(95),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr149(currentRow.getCell(97) != null ? getDataFormatter().formatCellValue(currentRow.getCell(97),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr150(currentRow.getCell(99) != null ? getDataFormatter().formatCellValue(currentRow.getCell(99),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr152(currentRow.getCell(101) != null ? getDataFormatter().formatCellValue(currentRow.getCell(101),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr153(currentRow.getCell(103) != null ? getDataFormatter().formatCellValue(currentRow.getCell(103),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr154(currentRow.getCell(105) != null ? getDataFormatter().formatCellValue(currentRow.getCell(105),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr156(currentRow.getCell(107) != null ? getDataFormatter().formatCellValue(currentRow.getCell(107),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr157(currentRow.getCell(109) != null ? getDataFormatter().formatCellValue(currentRow.getCell(109),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr173(currentRow.getCell(111) != null ? getDataFormatter().formatCellValue(currentRow.getCell(111),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr175(currentRow.getCell(113) != null ? getDataFormatter().formatCellValue(currentRow.getCell(113),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr176(currentRow.getCell(115) != null ? getDataFormatter().formatCellValue(currentRow.getCell(115),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr215(currentRow.getCell(116) != null ? getDataFormatter().formatCellValue(currentRow.getCell(116),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr223(currentRow.getCell(117) != null ? getDataFormatter().formatCellValue(currentRow.getCell(117),formulaEvaluatorObj) : null);
		
		String newCabNumber = currentRow.getCell(19) != null ? getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj) : null;
		if (StringUtils.isNotBlank(newCabNumber)) {
			assetNewValObj.setHeader12(assetNewValObj.getHeader10()+":"+getDataFormatter().formatCellValue(currentRow.getCell(19),formulaEvaluatorObj));
		}
		return assetNewValObj;
	}
	
	public void parseDemarcationAsset(Workbook workbookObj, List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate, String dfrId,
			HashSet<String> dfrLineIdSet, List<ErrorDetail> errorDetailList) throws ParseException {
		XSSFSheet sheetObj = (XSSFSheet) workbookObj.getSheet(PRODUCT_DEMARCATION_SHEET);
		FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbookObj);
		SnapshotSiebelAssetDa assetDa = null;
		AssetNewVal assetNewValObj = null;
		ErrorMessage messageObj = null;
		ErrorDetail detailObj = new ErrorDetail();
		detailObj.setProductName(PRODUCT_DEMARCATION);
		if (sheetObj != null && sheetObj.getLastRowNum() > 3) {
			for (int i = 4; i <= sheetObj.getLastRowNum(); i++) {
				Row currentRow = sheetObj.getRow(i);
				if (!(isRowEmpty(currentRow)) && isMandatoryFieldsPresent(currentRow)) {
					String dfrLineId = currentRow.getCell(0) != null ? getDataFormatter().formatCellValue(currentRow.getCell(0),formulaEvaluatorObj)
							: null;
					if (dfrLineId != null && !dfrLineId.isEmpty()) {
						if (dfrLineIdSet.contains(dfrLineId)) {
							messageObj = new ErrorMessage();
							messageObj.setRowNum(i+1);
							messageObj.setCellNum(0+1);
							messageObj.setMessageStr("Duplicate DFR Line ID # " + dfrLineId + ";");
							detailObj.getErrorList().add(messageObj);
						} else {
							dfrLineIdSet.add(dfrLineId);
						}
						assetNewValObj = new AssetNewVal();
						assetNewValObj.setDfrLineId(dfrLineId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader1(dfrLineId);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						parseDate(i, currentRow, detailObj, PRODUCT_DEMARCATION, null, assetNewValObj, 21);
						assetNewValListUpdate.add(getDemarcationAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					} else {
						assetDa = new SnapshotSiebelAssetDa();
						assetNewValObj = new AssetNewVal();
						assetDa.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
						assetDa.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
						assetDa.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : null);
						assetDa.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
						assetDa.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
						assetDa.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
						assetDa.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
						assetDa.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
						assetDa.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
						assetDa.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
						assetDa.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
						assetDa.setHeader14(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
						parseDate(i, currentRow, detailObj, PRODUCT_DEMARCATION, assetDa, null, 21);
						assetDa.setHeader52(assetDa.getHeader16());
						if (StringUtils.isNotBlank(assetDa.getHeader14())){
							assetDa.setHeader12(assetDa.getHeader10()+":"+assetDa.getHeader14());
						}
						assetDa.setHeader8(getIBXFromSystemName(assetDa.getHeader16()));
						assetDa.setHeader26(currentRow.getCell(26) != null ? getDataFormatter().formatCellValue(currentRow.getCell(26),formulaEvaluatorObj) : null);
						setCreateAndUpdateDate(assetDa);
						setDfrId(assetDa, null, dfrId);
						assetDa.setHeader57(getValueForHeaders());
						assetDa.setHeader60(getValueForHeaders());
						assetList.add(assetDa);
						setCreateDate(assetNewValObj);
						setUpdateDate(assetNewValObj);
						setDfrId(null, assetNewValObj, dfrId);
						assetNewValObj.setHeader57(getValueForHeaders());
						assetNewValObj.setHeader60(getValueForHeaders());
						assetNewValObj.setHeader52(assetDa.getHeader52());
						assetNewValObj.setHeader26(assetDa.getHeader26());
						if (assetDa.getHeader12() != null) {
							assetNewValObj.setHeader12(assetDa.getHeader12());
						}
						assetNewValObj.setHeader8(assetDa.getHeader8());
						assetNewValObj.setHeader17(assetDa.getHeader17() != null ? assetDa.getHeader17() : null);
						assetNewValListInsert.add(getDemarcationAssetNewVal(currentRow, assetNewValObj,formulaEvaluatorObj));
					}
				}
			}
		}
		if (detailObj != null && detailObj.getErrorList() != null && !detailObj.getErrorList().isEmpty()) {
			errorDetailList.add(detailObj);
		}
	}
	
	public AssetNewVal getDemarcationAssetNewVal(Row currentRow,AssetNewVal assetNewValObj,FormulaEvaluator formulaEvaluatorObj) {
		assetNewValObj.setHeader24(currentRow.getCell(3) != null ? getDataFormatter().formatCellValue(currentRow.getCell(3),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader20(currentRow.getCell(4) != null ? getDataFormatter().formatCellValue(currentRow.getCell(4),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader2(currentRow.getCell(5) != null ? getDataFormatter().formatCellValue(currentRow.getCell(5),formulaEvaluatorObj) : editDfrBusiness.getNewPOEAssetNumber());
		assetNewValObj.setHeader3(currentRow.getCell(6) != null ? getDataFormatter().formatCellValue(currentRow.getCell(6),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader7(currentRow.getCell(7) != null ? getDataFormatter().formatCellValue(currentRow.getCell(7),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader6(currentRow.getCell(8) != null ? getDataFormatter().formatCellValue(currentRow.getCell(8),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader31(currentRow.getCell(9) != null ? getDataFormatter().formatCellValue(currentRow.getCell(9),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader10(currentRow.getCell(10) != null ? getDataFormatter().formatCellValue(currentRow.getCell(10),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader18(currentRow.getCell(11) != null ? getDataFormatter().formatCellValue(currentRow.getCell(11),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader45(currentRow.getCell(12) != null ? getDataFormatter().formatCellValue(currentRow.getCell(12),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader16(currentRow.getCell(14) != null ? getDataFormatter().formatCellValue(currentRow.getCell(14),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader14(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr335(currentRow.getCell(22) != null ? getDataFormatter().formatCellValue(currentRow.getCell(22),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader58(currentRow.getCell(23) != null ? getDataFormatter().formatCellValue(currentRow.getCell(23),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader61(currentRow.getCell(24) != null ? getDataFormatter().formatCellValue(currentRow.getCell(24),formulaEvaluatorObj) : null);
		assetNewValObj.setHeader66(currentRow.getCell(25) != null ? getDataFormatter().formatCellValue(currentRow.getCell(25),formulaEvaluatorObj) : null);
		
		assetNewValObj.setAttr18(currentRow.getCell(16) != null ? getDataFormatter().formatCellValue(currentRow.getCell(16),formulaEvaluatorObj) : null);
		assetNewValObj.setAttr91(currentRow.getCell(20) != null ? getDataFormatter().formatCellValue(currentRow.getCell(20),formulaEvaluatorObj) : null);
		if (StringUtils.isNotBlank(assetNewValObj.getHeader14())){
			assetNewValObj.setHeader12(assetNewValObj.getHeader10()+":"+assetNewValObj.getHeader14());
		}
		return assetNewValObj;
	}
	
	private void setCreateAndUpdateDate(SnapshotSiebelAssetDa assetDa) {
		if (assetDa != null) {
			assetDa.setHeader34(new Date());
			assetDa.setHeader36(new Date());
		}
	}
	
	private void setCreateDate(AssetNewVal assetNewVal) {
		if (assetNewVal != null) {
			assetNewVal.setHeader34(new Date());
		}
	}
	
	private void setUpdateDate(AssetNewVal assetNewVal) {
		if (assetNewVal != null) {
			assetNewVal.setHeader36(new Date());
		}
	}
	
	private void setDfrId(SnapshotSiebelAssetDa assetDa,AssetNewVal assetNewVal,String dfrId) {
		if (assetDa != null && (dfrId != null && !dfrId.isEmpty())) {
			assetDa.setDfrId(dfrId);
		}
		if (assetNewVal != null && (dfrId != null && !dfrId.isEmpty())) {
			assetNewVal.setDfrId(dfrId);
		}
	}
	
	private boolean isRowEmpty(Row row) {
		boolean isEmpty = Boolean.FALSE;
		if (row == null) {
			return isEmpty =  Boolean.TRUE;
		}
		if (row != null && row.getLastCellNum() <= 0) {
			return isEmpty =  Boolean.TRUE;
		}
		for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
			Cell cellObj = row.getCell(i);
			isEmpty = Boolean.TRUE;
			if (cellObj != null && cellObj.getCellTypeEnum() != CellType.BLANK && StringUtils.isNotBlank(cellObj.toString())){
				return Boolean.FALSE;
			}
		}
		return isEmpty;
	}
	
	private String getValueForHeaders() {
		return "Y";
	}
	
	private DataFormatter getDataFormatter(){
		if (dataFormatter == null) {
			dataFormatter = new DataFormatter();
		}
		return dataFormatter;
	}
	
	private boolean isMandatoryFieldsPresent(Row currentRowObj) {
		if (currentRowObj != null && getDataFormatter().formatCellValue(currentRowObj.getCell(4)) != null && 
				!getDataFormatter().formatCellValue(currentRowObj.getCell(4)).isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Boolean isErrorDisplayable(CxiErrorTbl error) {
		if (error != null && error.getErrorEndDate() == null && !errorMasterMap.isEmpty()) {
			List<SrcCxiErrorMasterTbl> errorMasterList = (List<SrcCxiErrorMasterTbl>) errorMasterMap.get(error.getErrorCode());
			if (errorMasterList != null && CollectionUtils.isNotEmpty(errorMasterList) &&
					StringUtils.isNotEmpty(errorMasterList.get(0).getOwnerOfFixing())
					&& errorMasterList.get(0).getOwnerOfFixing().startsWith("OPS")) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public void parseDate(int rowCount,Row currentRow,ErrorDetail errorDetailObj, String productName,
			SnapshotSiebelAssetDa assetDa, AssetNewVal assetNewVal, int cellNum) {
		ErrorMessage messageObj = null;
		try {
			if (currentRow.getCell(cellNum) != null) {
				if (CellType.NUMERIC.getCode() != currentRow.getCell(cellNum).getCellType()) {
					messageObj = new ErrorMessage();
					messageObj.setRowNum(rowCount+1);
					messageObj.setCellNum(cellNum+1);
					messageObj.setMessageStr("Invalid Asset Install Date # "+currentRow.getCell(cellNum).getStringCellValue()+";");
					errorDetailObj.getErrorList().add(messageObj);
				} else {
					if (assetDa != null) {
						assetDa.setHeader17(currentRow.getCell(cellNum).getDateCellValue());
					} else if (assetNewVal != null) {
						assetNewVal.setHeader17(currentRow.getCell(cellNum).getDateCellValue());
					}
				}
			}
		} catch (Exception ex) {
			messageObj = new ErrorMessage();
			messageObj.setRowNum(rowCount+1);
			messageObj.setCellNum(cellNum+1);
			messageObj.setMessageStr("Invalid Asset Install Date # "+currentRow.getCell(cellNum).getStringCellValue()+";");
			errorDetailObj.getErrorList().add(messageObj);
		}
	}
	
	public String getIBXFromSystemName (String systemName) {
		String ibx = null;
		if (systemName != null && !systemName.isEmpty() && systemName.length() > 0) {
			if (systemName.contains(":")) {
				ibx = systemName.substring(0, systemName.indexOf(":"));
			} else {
				if (systemName.length() == 1 || systemName.length() == 2 || systemName.length() == 3 || systemName.length() == 4) {
					ibx = systemName;
				} else{
					ibx = systemName.substring(0,4);
				}
			}
		}
		return ibx;
	}
	
	private FormulaEvaluator getFormulaEvaluator(Workbook workBookObj) {
		return workBookObj.getCreationHelper().createFormulaEvaluator();
	}
	
	@RequestMapping(value = "/saveOverrideFlag", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveOverrideFlag(@RequestParam String dfrId, @RequestParam String overrideFlag,
			@RequestParam String sblOverrideFlag, @RequestParam String clxOverrideFlag, 
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		Gson gson = new Gson();
		String status = "";
		try {
			status = editDfrBusiness.saveOverrideFlagDFRMaster(overrideFlag,sblOverrideFlag,clxOverrideFlag,dfrId);
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return gson.toJson(status, String.class);
	}
	
	//Change Summary changes
	@RequestMapping(value = "/getChangeSummary", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO< List<ChangeSummary>>> getChangeSummary(HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		
		  String dfrId = req.getParameter("dfrId");
		  List<ChangeSummary> changeSummary = null;
		  if(StringUtils.isNotEmpty(dfrId))
			  changeSummary = editDfrBusiness.getChangeSummary(dfrId);
		  return buildResponse(changeSummary, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	public boolean validateFormulaForExternalSheet (Row currentRow){
		if (currentRow != null) {
			for (Cell cell : currentRow) {
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA && cell.getCellFormula() != null
						&& cell.getCellFormula().contains("[") && cell.getCellFormula().contains("]")) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	/*@RequestMapping(value = "/resetOrDelete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String,Object>>> resetAndDelete(@RequestBody ResetAndDelete resetAndDelete,
			HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
		String result = null;
		try {
			if (resetAndDelete != null) {
				result = editDfrBusiness.resetAndDelete(resetAndDelete);
			} else {
				result = "Invalid Input. Please send a valid Input.";
			}
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(result, "Available", "Not Available");
	}*/
	
	@RequestMapping(value = "/resetOrDelete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String,Object>>> resetAndDelete(@RequestParam  String dfrLineId,
			HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
		String result = null;
		try {
			if (dfrLineId != null) {
				result = editDfrBusiness.resetAndDelete(dfrLineId);
			} else {
				result = "Invalid Input. Please send a valid Input.";
			}
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(result, "Available", "Not Available");
	}
	
	@RequestMapping(value = "/resetAttributes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String,Object>>> resetAttributeview(@RequestParam  String dfrId,@RequestParam(value = "product", required = false) String product,
	                                                        HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
	   String result = null;
	   try {
	      if (dfrId != null) {
	         result = editDfrBusiness.resetAttributeView(dfrId,product);
	      } else {
	         result = "Invalid Input. Please send a valid Input.";
	      }
	   } catch (Exception ex) {
	      String uuid = UUID.randomUUID().toString();
	      logger.error(uuid,ex);
	      return buildErrorResponse(ex.getMessage(), uuid);
	   }finally {
	      HttpClientUtils.closeQuietly(httpResp);
	   }
	   return buildCollectionResponse(result, "Available", "Not Available");
	}
	
	@RequestMapping(value = "/validateSaveAssetNewValues", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String,Object>>> validateSaveAssetNewValues(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		Gson gson = new Gson ();	
		HashMap<String , Object> resultMap = new HashMap<>();
		boolean isSubmitable = false;
		try{	
			isSubmitable = editDfrBusiness.validateSaveAssetNewValuesByDfrId(searchFilters);
			resultMap.put("isSubmitable", isSubmitable);
		}catch(Exception e){
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(resultMap, "Available", "Not Available");	
	 }
	//DFRile Changes
	@RequestMapping(value = "/uploadDFRFile", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String,Object>>> uploadDFRFile(@RequestParam("file") MultipartFile fileObj,@RequestParam("dfrId") String dfrId,HttpResponse httpResp) throws Exception {
		String result=null;
		HashMap<String,String> resultMap = new HashMap<>();
		FileUp fileUp=new FileUp();
		try
		{
			
			logger.info("Got File Object"+fileObj);
			logger.info("File Name "+fileObj.getOriginalFilename());
			logger.info("File Content Type "+fileObj.getContentType());
			logger.info("Got DFR ID"+dfrId);
			fileUp.setDfrId(dfrId);
			fileUp.setFileName(fileObj.getOriginalFilename());
			fileUp.setFileType(fileObj.getContentType());
			result=editDfrBusiness.uploadDFRFile(fileUp, fileObj);
			if(result!=null && result.equalsIgnoreCase("SUCCESS"))
			{
				resultMap.put("dfrid", fileUp.getDfrId());
				resultMap.put("filename", fileUp.getFileName());
			}
			else
			{
				resultMap.put("message", result);

			}
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		 return buildResponse(resultMap, "Available", "Not Available");

	}
	
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downLoadFile(HttpServletRequest req,
			HttpServletResponse httpSerResp, HttpResponse httpResp) throws Exception {
		DFRFile dfrFile=null;
		HashMap<String,Object> resultMap = new HashMap<>();
		 ByteArrayInputStream inputStream = null;
		 OutputStream outStream = null;
		 ResponseEntity<InputStreamResource> response = null;




		try
		{
		if(req.getParameter("dfrId")!=null)
		{
			logger.info("Getting DFR ID to download "+req.getParameter("dfrId"));
			dfrFile=editDfrBusiness.downloadFile(req.getParameter("dfrId"));
			if(dfrFile!=null && dfrFile.getDfrFile()!=null)
			{
				
				ByteArrayResource dfrData=new ByteArrayResource(dfrFile.getDfrFile());
				logger.info("Download file "+dfrFile.getFileName().replaceAll("\\W+", ""));
				byte[] outArray = dfrData.getByteArray();
				inputStream = new ByteArrayInputStream(outArray);
				httpSerResp.setContentType(dfrFile.getFileType());
				if(dfrFile.getFileType().contains("sheet"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".xlsx");
				}
				else if(dfrFile.getFileType().contains("ms-excel"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".xls");
				}
				else if(dfrFile.getFileType().contains("presentation"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".pptx");
				}
				else if(dfrFile.getFileType().contains("document"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".docx");
				}
				else if(dfrFile.getFileType().contains("msword"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".doc");
				}
				else if(dfrFile.getFileType().contains("text/plain"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".txt");
				}
				else if(dfrFile.getFileType().contains("pdf"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".pdf");
				}
				else if(dfrFile.getFileType().contains("image"))
				{
				httpSerResp.setHeader("Content-Disposition", "attachment; filename=DFR_File_" + dfrFile.getDfrId() + ".jpeg");
				}
				httpSerResp.setContentLength(outArray.length);
				httpSerResp.setHeader("Expires:", "0"); // Eliminates Browser Caching
				outStream = httpSerResp.getOutputStream();
				outStream.write(outArray);
				outStream.flush();
				
				/*return ResponseEntity.ok().
						header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+dfrFile.getFileName())
						.contentType(dfrFile.getFileType()).contentLength(dfrFile.getDfrFile().length)
						.body(dfrData);*/

			}
			else
			{
				String uuid = UUID.randomUUID().toString();				
				return buildErrorResponse("No File Found for this DFR id", uuid);

			}
	

		}
		else
		{
			String uuid = UUID.randomUUID().toString();
			return buildErrorResponse("Invalid DFR ID", uuid);


		}
		}
		catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			 logger.error(uuid,ex);
			 throw new Exception("Exception thrown while exporting DFR Data");
		}
		finally {
			
			if (inputStream != null) {
				inputStream.close();
			}
			
			if (outStream != null) {
				outStream.close();
			}
			HttpClientUtils.closeQuietly(httpResp);
		 }
		 return response;
		
	}
	
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String,Object>>> deleteFile(HttpServletRequest req,
			HttpServletResponse resp, HttpResponse httpResp) {
		String result=null;
		HashMap<String,Object> resultMap = new HashMap<>();

		try
		{
		if(req.getParameter("dfrId")!=null)
		{
			logger.info("Getting DFR ID to delete "+req.getParameter("dfrId"));
			result=editDfrBusiness.deleteDFRFile(req.getParameter("dfrId"));
			resultMap.put("message", result);

	

		}
		else
		{
			resultMap.put("message", "Invalid DFR ID");

		}
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		 return buildResponse(resultMap, "Available", "Not Available");
		
	}
	
	 private MediaType getMediaType(String type)
     {
  	   if(type.contains("text"))
  	   {
  		   return MediaType.TEXT_PLAIN;
  	   }
  	 return  MediaType.TEXT_PLAIN;
     }
	 
	 @RequestMapping(value = "/checkForDFRFile", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap<String,Object>>> checkForDFRFile(HttpServletRequest req,
				HttpServletResponse resp, HttpResponse httpResp) {
			Boolean result=null;
			HashMap<String,Object> resultMap = new HashMap<>();

			try
			{
			if(req.getParameter("dfrId")!=null)
			{
				logger.info("Check File exists for DFR "+req.getParameter("dfrId"));
				result=editDfrBusiness.checkFileExists(req.getParameter("dfrId"));
				if(result.equals(true))
						{
				resultMap.put("message", result);
						}
				else
				{
					resultMap.put("message", result);

				}

		

			}
			else
			{
				resultMap.put("message", "Invalid DFR ID");

			}
			}
			catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			 return buildResponse(resultMap, "Available", "Not Available");
			
		}
	
	 @RequestMapping(value = "/deleteAssets", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap<String,Object>>> deleteAssetsByDfrLineIds(@RequestParam String dfrId, @RequestParam  List<String> dfrLineIds,
		                                                        HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
		   String result = null;
		   try {
		      if (dfrLineIds != null  && dfrLineIds.size()>0) {
		         result = editDfrBusiness.deleteAssetsByDfrLineIds(dfrId,dfrLineIds);
		      } else {
		         result = "Invalid Input. Please send a valid Input.";
		      }
		   } catch (Exception ex) {
		      String uuid = UUID.randomUUID().toString();
		      logger.error(uuid,ex);
		      return buildErrorResponse(ex.getMessage(), uuid);
		   }finally {
		      HttpClientUtils.closeQuietly(httpResp);
		   }
		   return buildCollectionResponse(result, "Available", "Not Available");
		}

	@RequestMapping(value = "/resetAssetsByDfrLineIds", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String, Object>>> resetAssetsByDfrLineIds(
			@RequestParam Set<String> dfrLineIds, HttpServletRequest req, HttpServletResponse resp,
			HttpResponse httpResp) {
		String result = null;
		try {
			if (dfrLineIds != null && dfrLineIds.size() > 0) {
				for(String dfrLineId : dfrLineIds){
					result = editDfrBusiness.resetAndDelete(dfrLineId);
				}
				
			} else {
				result = "Invalid Input. Please send a valid Input.";
			}
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
		return buildCollectionResponse(result, "Available", "Not Available");
	}

	@RequestMapping(value = "/validationsOnSubmit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap<String, Object>>> validationsOnSubmit(@RequestParam String dfrId,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			List<ErrorCodeVO> result = editDfrBusiness.validationsOnSubmit(dfrId);
			return buildCollectionResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.info("UUID : " + uuid + " Error : " + e.getMessage());
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/autoValidate", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ErrorSectionResponse>> validate(@RequestParam String dfrId,
			HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			HashMap<String , String> resultMap = new HashMap<>();
			boolean isValidateDone = false;
			try{
				
				ProductDataGrid productDataGrid = new ProductDataGrid();
				productDataGrid.setDfrid(dfrId);
				editDfrBusiness.validate(productDataGrid);
				isValidateDone = true;
				resultMap.put("validate", "success");
			}catch(Exception e){
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
			if(isValidateDone){
				//Once above step is done for all attributes and assets execute below
				try {
					editDfrBusiness.dqmValidation(dfrId);
				}catch (Exception e){
					String uuid = UUID.randomUUID().toString();
					logger.error(uuid,e);
					return buildErrorResponse(e.getMessage(), uuid);
				}finally {
					HttpClientUtils.closeQuietly(httpResp);
				}

			}
			return buildResponse(resultMap, "Available", "Not Available");
	 }
	
	

	 @RequestMapping(value = "/exportProductAttributeView", method = RequestMethod.POST)
		public ResponseEntity<InputStreamResource> exportProductAttributeView(@RequestParam("keyword")String keyword,
		           @RequestParam("dfrid")String dfrid, @RequestParam("applications")String applications, @RequestParam("attributeFlag")String attributeFlag,
			@RequestParam("products") String products, @RequestParam("errorCode") String errorCode,@RequestParam("kpiFilterType") String kpiFilterType,
			@RequestParam("isErrorClassification") boolean isErrorClassification, HttpServletRequest req,
			HttpServletResponse resp, HttpResponse httpResp) {
		ProductFilter searchFilters = null;
		ProductDataGrid dataGrid = null;
		List<Product> productList = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayInputStream inputStream = null;
		ResponseEntity<InputStreamResource> response = null;
		ByteArrayOutputStream outByteStream = null;
		OutputStream outStream = null;
		Workbook workbook = null;
		byte[] outArray = null;
		Map<String, List<Product>> productListGrouped = null;

		String applicationList[] = null;

		try {
			DfrMaster dfr = editDfrBusiness.getDfrMasterById(dfrid);
			String snapshotFilter = dfr.getSsFilter();
			searchFilters = new ObjectMapper().readValue(snapshotFilter, ProductFilter.class);
			List<PFilter> pFilters = searchFilters.getFilters();
			for (PFilter pFilter : pFilters) {
				if (pFilter.getKey().equals("header20")) {
					pFilter.setValue(products);
				}
			}
			searchFilters.setApplications(applications);
			searchFilters.setAttributeFlag(attributeFlag);
			searchFilters.setDfrId(dfrid);
			searchFilters.setProducts(products);
			searchFilters.setKeyword(keyword);
			searchFilters.setKpiFilterType(kpiFilterType);
			searchFilters.setErrorClassification(isErrorClassification);
			if (StringUtils.isNotEmpty(errorCode) && StringUtils.isNotBlank(errorCode)) {
				PFilter filter = new PFilter();
				filter.setKey("Error Code");
				filter.setLable("Error Code");
				filter.setValue(errorCode);
				filter.setListOfValues(Arrays.asList(errorCode.split(",")));
				searchFilters.getFilters().add(filter);
			}
			// searchFilters.setFilters(filters);

			/**
			 * Get Data Grid
			 */

			if (searchFilters != null) {
				dataGrid = editDfrBusiness.getAllProductAttributeView(searchFilters);
				dataGrid.setDfrid(searchFilters.getDfrId());
			}
			
			if (searchFilters != null && searchFilters.getSearchDropBox() != null
					&& searchFilters.getSearchDropBox().getSearchDrop() != null && searchFilters.getKeyword() != null
					&& CollectionUtils.isNotEmpty(searchFilters.getSearchDropBox().getSearchDrop())
					&& searchFilters.getSearchDropBox().getSearchDrop().get(0) != null
					&& "header2".equalsIgnoreCase(searchFilters.getSearchDropBox().getSearchDrop().get(0).getKey())
					&& dataGrid != null && CollectionUtils.isNotEmpty(dataGrid.getProducts())
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
					List<Product> nullAssets = dataGrid.getProducts().stream()
							.filter(item -> item.getAssetNumber() == null).collect(Collectors.toList());
					if (null != nullAssets && nullAssets.size() > 0) {
						return buildResponse(dataGrid, "Available", "Not Available");
					}
					List<Product> sortedProducts = new ArrayList<>();
					Map<String, List<Product>> productMap = dataGrid.getProducts().stream()
							.filter(item -> item.getAssetNumber() != null)
							.collect(Collectors.groupingBy(Product::getAssetNumber));
					// Map<String, List<Product>> productMap =
					// dataGrid.getProducts().stream().collect(Collectors.groupingBy(Product::getAssetNumber));
					for (String assetNum : searchForList) {
						if (productMap.containsKey(assetNum)) {
							sortedProducts.addAll(productMap.get(assetNum));
						}
					}
					dataGrid.getProducts().clear();
					dataGrid.getProducts().addAll(sortedProducts);
				}
			}
			
			productList = dataGrid.getProducts();

			productListGrouped = productList.stream().collect(Collectors.groupingBy(w -> w.getName()));

			applicationList = searchFilters.getApplications().split(",");

			Map<String, List<String>> headerList = new HashMap<String, List<String>>();

			Set<String> commonProduct = new HashSet<String>();
			for (Product product : productList) {

				commonProduct.add(product.getName());
			}
			String[] comProduct = new String[commonProduct.size()];

			int index1 = 0;
			for (String str : commonProduct) {
				comProduct[index1++] = str;
			}

			for (String productName : commonProduct) {
				for (Product product : productList) {
					if (product.getName().equalsIgnoreCase(productName)) {
						List<String> headers = new ArrayList<String>();
						List<Attribute> attributes = product.getAttributes();
						for (Attribute attribute : attributes) {

							for (String app : applicationList) {
								if (app.equalsIgnoreCase("sbl")) {
									String sbl = attribute.getName() + "( sbl)";
									headers.add(sbl);
								} else if (app.equalsIgnoreCase("sv")) {
									String sv = attribute.getName() + "( sv)";
									headers.add(sv);
								} else if (app.equalsIgnoreCase("clx")) {
									String clx = attribute.getName() + "( clx)";
									headers.add(clx);
								} else if (app.equalsIgnoreCase("new_value")) {
									if (attribute.getEditable().equalsIgnoreCase("Y")) {
										String newValue = attribute.getName() + "( new_value)";
										headers.add(newValue);
									}
								}
							}
							if (attribute.getName().equals("Asset #")) {
								headers.add("DQM Error Flag");
								headers.add("DQM Error Description");
							}
						}
						headers.add("dfr_line_id");
						headerList.put(productName, headers);

					}
				}
			}
			System.out.println("Header List : " + headerList);

			try {
				workbook = new XSSFWorkbook();
				// Sheet sheet = workbook.createSheet(PRODUCT_CAGE);
				CellStyle dateCellStyle = getDateCellType(workbook);
				for (String productName : PRODUCTS_BY_SEQUENCE) {
					List<Product> productsDataList = productListGrouped.get(productName);
					if (CollectionUtils.isNotEmpty(productsDataList)) {
						for (Product product : productsDataList) {

							if (product.getName().equals(PRODUCT_CAGE)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_CAGE);
							} else if (product.getName().equals(PRODUCT_CABINET)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_CABINET);
							} else if (product.getName().equals(PRODUCT_AC_CIRCUIT)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_AC_CIRCUIT);

							} else if (product.getName().equals(PRODUCT_DC_CIRCUIT)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_DC_CIRCUIT);

							} else if (product.getName().equals(PRODUCT_PP)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_PP);

							} else if (product.getName().equals(PRODUCT_NCC)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_NCC);

							} else if (product.getName().equals(PRODUCT_DEMARCATION)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_DEMARCATION);

							} else if (product.getName().equals(PRODUCT_PPPC)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_PPPC);
							} else if (product.getName().equals(PRODUCT_PPPE)) {
								constructProductData(workbook, product, headerList, dateCellStyle, applications,
										PRODUCT_PPPE);
							}

						}
					}
				}
				for (String productName : PRODUCTS_BY_SEQUENCE) {
					List<Product> productsHeaderList = productListGrouped.get(productName);
					if (CollectionUtils.isNotEmpty(productsHeaderList)) {
						for (Product product : productsHeaderList) {

							if (product.getName().equals(PRODUCT_CAGE)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle, PRODUCT_CAGE,
										PRODUCT_CAGE_HEADER);
							} else if (product.getName().equals(PRODUCT_CABINET)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle,
										PRODUCT_CABINET, PRODUCT_CABINET_HEADER);
							} else if (product.getName().equals(PRODUCT_AC_CIRCUIT)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle,
										PRODUCT_AC_CIRCUIT, PRODUCT_AC_CIRCUIT_HEADER);
							} else if (product.getName().equals(PRODUCT_DC_CIRCUIT)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle,
										PRODUCT_DC_CIRCUIT, PRODUCT_DC_CIRCUIT_HEADER);
							} else if (product.getName().equals(PRODUCT_PP)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle, PRODUCT_PP,
										PRODUCT_PP_HEADER);
							} else if (product.getName().equals(PRODUCT_NCC)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle, PRODUCT_NCC,
										PRODUCT_NCC_HEADER);
							} else if (product.getName().equals(PRODUCT_DEMARCATION)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle,
										PRODUCT_DEMARCATION, PRODUCT_DEMARCATION_HEADER);
							} else if (product.getName().equals(PRODUCT_PPPC)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle, PRODUCT_PPPC,
										PRODUCT_PPPC_HEADER);
							} else if (product.getName().equals(PRODUCT_PPPE)) {
								constructProductHeaderSheet(workbook, product, headerList, dateCellStyle, PRODUCT_PPPE,
										PRODUCT_PPPE_HEADER);
							}
						}
					}
				}
				outByteStream = new ByteArrayOutputStream();
				workbook.write(outByteStream);
				outArray = outByteStream.toByteArray();
				inputStream = new ByteArrayInputStream(outArray);

				// return ResponseEntity.ok().headers(headers).body(new
				// InputStreamResource(in));
				resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				resp.setHeader("Content-Disposition", "attachment; filename=ProductAttributeView.xlsx");
				resp.setContentLength(outArray.length);
				resp.setHeader("Expires:", "0"); // Eliminates Browser
													// Caching
				outStream = resp.getOutputStream();
				outStream.write(outArray);
				outStream.flush();
			} catch (Exception ex) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid, ex);
				ex.printStackTrace();
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (workbook != null) {
					workbook.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outByteStream != null) {
					outByteStream.close();
				}
				if (outStream != null) {
					outStream.close();
				}

				HttpClientUtils.closeQuietly(httpResp);
			}

			return ResponseEntity.ok().body(new InputStreamResource(inputStream));

		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	 
	 private void constructProductData(Workbook workbook, Product product, Map<String, List<String>> headerList,
				CellStyle dateCellStyle,String applications,String productType) {
			XSSFSheet sheet = null;
			List<String> sheetHeaders = headerList.get(productType);
			sheet = (XSSFSheet) workbook.getSheet(productType);
			CellStyle headerCellStyle = null;
			if (sheet == null) {
				sheet = (XSSFSheet) workbook.createSheet(productType);
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setColor(IndexedColors.BLUE.getIndex());
				headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);
				Row headerRow = sheet.createRow(0);
				for (int col = 0; col < sheetHeaders.size(); col++) {
					Cell cell = headerRow.createCell(col);
					cell.setCellValue(sheetHeaders.get(col));
					cell.setCellStyle(headerCellStyle);
				}
			}
			int row = sheet.getPhysicalNumberOfRows();
			Row currentRow = sheet.createRow(row++);
			
			List<Attribute> attributeList = product.getAttributes();
			for (Attribute attribute : attributeList) {
				String attr1 = null;
				String attr2 = null;
				String attr3 = null;
				String attr4 = null;
				String attr5 = "dfr_line_id";
				String dqmErrorFlag = null;
				String dqmErrorDescription = null;
				if(attribute.getName().equalsIgnoreCase("Asset #")){
					
					attr1 = attribute.getName() + "( sbl)";
					attr2 = attribute.getName() + "( clx)";
					attr3 = attribute.getName() + "( sv)";
					dqmErrorFlag = "DQM Error Flag";
					dqmErrorDescription = "DQM Error Description";
					
				}else{
					attr1 = attribute.getName() + "( sbl)";
					attr2 = attribute.getName() + "( clx)";
					attr3 = attribute.getName() + "( sv)";
					attr4 = attribute.getName() + "( new_value)";
					attr5 = "dfr_line_id";
				}

				Values values = attribute.getValues();
				int sblIndex = 0;
				int clxIndex = 0;
				int svIndex = 0;
				int newValueIndex = 0;
				int dqmErrorFlagIndex = 0;
				int dqmErrorDescriptionIndex =0;
				int dfrLineIdIndex = 0;
			
				
				for (int j = 0; j < sheetHeaders.size(); j++) {

					if (sheetHeaders.get(j).equals(attr1)) {
						sblIndex = j;
					} else if (sheetHeaders.get(j).equals(attr2)) {
						clxIndex = j;
					} else if (sheetHeaders.get(j).equals(attr3)) {
						svIndex = j;
					} else if (sheetHeaders.get(j).equals(attr4)) {
						newValueIndex = j;
					}else if(sheetHeaders.get(j).equals(dqmErrorFlag)){
						dqmErrorFlagIndex = j;
					}else if(sheetHeaders.get(j).equals(dqmErrorDescription)){
						dqmErrorDescriptionIndex = j;
					}else if(sheetHeaders.get(j).equals(attr5)){
						dfrLineIdIndex = j;
					} 
				}
				
				if(attribute.getName().equals("Asset #")){
					
					currentRow.createCell(dqmErrorFlagIndex).setCellValue(product.getDqmErrorFlag());
					currentRow.createCell(dqmErrorDescriptionIndex).setCellValue(product.getDqmErrorCodes());
				}
				String[] apps = applications.split(",");
				for (String app : apps) {
					if (app.equalsIgnoreCase("sbl")) {
						String[] sbls = values.getSBL().split("##");
						currentRow.createCell(sblIndex).setCellValue(sbls[0]);
					}
					if (app.equalsIgnoreCase("clx")) {
						String[] clxs = values.getCLX().split("##");
						currentRow.createCell(clxIndex).setCellValue(clxs[0]);
					}
					if (app.equalsIgnoreCase("sv")) {
						String[] svs = values.getSV().split("##");
						currentRow.createCell(svIndex).setCellValue(svs[0]);

					}
				}
				if (attribute.getEditable().equalsIgnoreCase("Y")) {
					currentRow.createCell(newValueIndex).setCellValue(values.getNewval());
				}
				
				currentRow.createCell(dfrLineIdIndex).setCellValue(attribute.getDfrlineid());
			}
			
		}
	 
	 private void constructProductHeaderSheet(Workbook workbook, Product product, Map<String, List<String>> headerList,
				CellStyle dateCellStyle, String productType, String productHeaderType) {
			XSSFSheet sheet = null;
			List<String> sheetHeaders = headerList.get(productType);
			sheet = (XSSFSheet) workbook.getSheet(productHeaderType);
			CellStyle headerCellStyle = null;
			if (sheet == null) {
				sheet = (XSSFSheet) workbook.createSheet(productHeaderType);
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setColor(IndexedColors.BLUE.getIndex());
				headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);
				Row attributeRow = sheet.createRow(0);
				
				List<Attribute> attributeList = product.getAttributes();
				for (Attribute attribute2 : attributeList) {

					String attr01 = attribute2.getName() + "( sbl)";
					String attr02 = attribute2.getName() + "( clx)";
					String attr03 = attribute2.getName() + "( sv)";
					String attr04 = attribute2.getName() + "( new_value)";
					String dqmErrorFlag = "DQM Error Flag";
					String dqmErrorDescription = "DQM Error Description";
					String dfr_line_id = "dfr_line_id";
					
					for (int col = 0; col < sheetHeaders.size(); col++) {

						if (sheetHeaders.get(col).equals(attr01)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue(attribute2.getKey());
							cell.setCellStyle(headerCellStyle);
						} else if (sheetHeaders.get(col).equals(attr02)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue(attribute2.getKey());
							cell.setCellStyle(headerCellStyle);
						} else if (sheetHeaders.get(col).equals(attr03)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue(attribute2.getKey());
							cell.setCellStyle(headerCellStyle);
						} else if (sheetHeaders.get(col).equals(attr04)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue(attribute2.getKey());
							cell.setCellStyle(headerCellStyle);
						}else if (sheetHeaders.get(col).equals(dqmErrorFlag)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue("DQM Error Flag");
							cell.setCellStyle(headerCellStyle);
						}else if (sheetHeaders.get(col).equals(dqmErrorDescription)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue("DQM Error Description");
							cell.setCellStyle(headerCellStyle);
						}else if (sheetHeaders.get(col).equals(dfr_line_id)) {
							Cell cell = attributeRow.createCell(col);
							cell.setCellValue("dfr_line_id");
							cell.setCellStyle(headerCellStyle);
						}
					}

				}

			}
			int sheetIndex = workbook.getSheetIndex(productHeaderType);
			workbook.setSheetVisibility(sheetIndex, SheetVisibility.VERY_HIDDEN);
			
		}
	 
		@RequestMapping(value = "/importAttributeView", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap>> importAttributeView(@RequestParam("file") MultipartFile fileObj,@RequestParam String dfrId,
			HttpResponse httpResp) throws Exception {
		AuditDataGrid gridObj = new AuditDataGrid();
		FileInputStream input = null;
		Workbook workbook = null;
		List<String> attributeNamesList = null;
		Set<String> sortedAttributeNamesList = null;
		Map<String, String> headerMap = null;
		String dfrLineId = null;
		List<Attribute> attributes = null;
		List<Product> products = new ArrayList<>();
		Gson gson = new Gson();
		HashMap<String, String> resultMap = new HashMap<>();
		boolean isAssetValSuccess = false;
		ProductDataGrid dataGrid = new ProductDataGrid();
		ProductDataGrid productDataGrid = null;
		Map<String,Set<String>> dfrLineIds = new HashMap<>();
		try {
			input = (FileInputStream) fileObj.getInputStream();
			workbook = new XSSFWorkbook(input);
			FormulaEvaluator formulaEvaluatorObj = getFormulaEvaluator(workbook);
			HashMap<String, String> productAndHeader = new HashMap<String, String>();
			productAndHeader.put(PRODUCT_CAGE, PRODUCT_CAGE_HEADER);
			productAndHeader.put(PRODUCT_CABINET, PRODUCT_CABINET_HEADER);
			productAndHeader.put(PRODUCT_AC_CIRCUIT, PRODUCT_AC_CIRCUIT_HEADER);
			productAndHeader.put(PRODUCT_PP, PRODUCT_PP_HEADER);
			productAndHeader.put(PRODUCT_NCC, PRODUCT_NCC_HEADER);
			productAndHeader.put(PRODUCT_DC_CIRCUIT, PRODUCT_DC_CIRCUIT_HEADER);
			productAndHeader.put(PRODUCT_DEMARCATION, PRODUCT_DEMARCATION_HEADER);
			productAndHeader.put(PRODUCT_PPPC, PRODUCT_PPPC_HEADER);
			productAndHeader.put(PRODUCT_PPPE, PRODUCT_PPPE_HEADER);
			for (int a = 0; a < workbook.getNumberOfSheets(); a++) {
				XSSFSheet sheetObj = (XSSFSheet) workbook.getSheetAt(a);
				if (productAndHeader.containsKey(sheetObj.getSheetName())) {
					for (Map.Entry<String, String> entry : productAndHeader.entrySet()) {
						if (sheetObj.getSheetName().equalsIgnoreCase(entry.getKey())) {
							headerMap = new HashMap<>();
							XSSFSheet headerSheet = (XSSFSheet) workbook.getSheet(entry.getValue());
							Row firstRow = sheetObj.getRow(0);
							Row headerRow = headerSheet.getRow(0);
							/**
							 * Prepare Hashmap for Header/Attr and Position
							 * 
							 * Key = attr name Value = Position
							 */
							for (int i = 0; i < firstRow.getPhysicalNumberOfCells(); i++) {
								int index = getDataFormatter().formatCellValue(firstRow.getCell(i)).lastIndexOf('(');
								String attName = null;
								if (index >= 0) {
									attName = getDataFormatter().formatCellValue(firstRow.getCell(i)).substring(0,
											index);
								} else {
									attName = getDataFormatter().formatCellValue(firstRow.getCell(i));
								}
								String header = headerRow.getCell(i).toString();
								headerMap.put(attName.trim(), header);
							}

						}
					}

					/*
					 * if
					 * (PRODUCT_CAGE.equalsIgnoreCase(sheetObj.getSheetName())
					 * ||
					 * PRODUCT_CABINET.equalsIgnoreCase(sheetObj.getSheetName())
					 * ||
					 * PRODUCT_AC_CIRCUIT.equalsIgnoreCase(sheetObj.getSheetName
					 * ()) ||
					 * PRODUCT_PP.equalsIgnoreCase(sheetObj.getSheetName()) ||
					 * PRODUCT_NCC.equalsIgnoreCase(sheetObj.getSheetName()) ||
					 * PRODUCT_DC_CIRCUIT.equalsIgnoreCase(sheetObj.getSheetName
					 * ()) || PRODUCT_DEMARCATION.equalsIgnoreCase(sheetObj.
					 * getSheetName()) ||
					 * PRODUCT_PPPC.equalsIgnoreCase(sheetObj.getSheetName()) ||
					 * PRODUCT_PPPE.equalsIgnoreCase(sheetObj.getSheetName())) {
					 */
					Row firstRow = sheetObj.getRow(0);
					attributeNamesList = new ArrayList<String>();
					for (int i = 0; i < firstRow.getPhysicalNumberOfCells(); i++) {
						attributeNamesList.add(firstRow.getCell(i).toString());
					}
					sortedAttributeNamesList = new LinkedHashSet<String>();
					for (int i = 0; i < firstRow.getPhysicalNumberOfCells(); i++) {
						if (getDataFormatter().formatCellValue(firstRow.getCell(i)) != null) {
							int index = getDataFormatter().formatCellValue(firstRow.getCell(i)).lastIndexOf('(');
							String attName = null;
							if (index >= 0) {
								attName = getDataFormatter().formatCellValue(firstRow.getCell(i)).substring(0, index);
							} else {
								attName = getDataFormatter().formatCellValue(firstRow.getCell(i));
							}

							sortedAttributeNamesList.add(attName.trim());
						}

					}

					for (int i = 1; i <= sheetObj.getLastRowNum(); i++) {
						Row currentRow = sheetObj.getRow(i);
						if (validateFormulaForExternalSheet(currentRow)) {
							gridObj.setFormulaExtRef("Y");
							gridObj.setMessageText(
									"File has External Sheet Reference in Cell Formula. Please remove them and re-upload.");
							gridObj.getAuditProductList().clear();
							return buildResponse(gridObj, "Available", "Not Available");
						}
						if (currentRow != null) {
							Product productObj = new Product();
							attributes = new ArrayList<>();
							productObj.setName(sheetObj.getSheetName());
							for (String sortedAttr : sortedAttributeNamesList) {
								Attribute attrObj = new Attribute();
								Values values = new Values();
								String key = headerMap.get(sortedAttr);
								attrObj.setKey(key);
								if (key != null) {
									if (key.contains("header")) {
										attrObj.setType("header");
									} else if (key.contains("attr")) {
										attrObj.setType("attribute");
									}
								}

								for (int j = 0; j < attributeNamesList.size(); j++) {
									int index = attributeNamesList.get(j).lastIndexOf('(');
									String attributeName = null;
									if (index >= 0) {
										attributeName = attributeNamesList.get(j).substring(0, index);
									} else {
										attributeName = attributeNamesList.get(j);
									}

									// String[] attributeName =
									// attributeNamesList.get(j).split("[(]");
									// System.out.println("attr Name : "+
									// attributeName[0]+" "+sortedAttr );
									if (attributeName.equalsIgnoreCase(sortedAttr)
											&& attributeNamesList.get(j).contains(DartConstants.SBL.toLowerCase())) {
										values.setSBL(currentRow.getCell(j) != null ? getDataFormatter()
												.formatCellValue(currentRow.getCell(j), formulaEvaluatorObj) : null);
									} else if (attributeName.equalsIgnoreCase(sortedAttr)
											&& attributeNamesList.get(j).contains(DartConstants.CLX.toLowerCase())) {
										values.setCLX(currentRow.getCell(j) != null ? getDataFormatter()
												.formatCellValue(currentRow.getCell(j), formulaEvaluatorObj) : null);
									} else if (attributeName.equalsIgnoreCase(sortedAttr)
											&& attributeNamesList.get(j).contains(DartConstants.SV.toLowerCase())) {
										values.setSV(currentRow.getCell(j) != null ? getDataFormatter()
												.formatCellValue(currentRow.getCell(j), formulaEvaluatorObj) : null);
									} else if (attributeName.equalsIgnoreCase(sortedAttr) && attributeNamesList.get(j)
											.contains(DartConstants.NEW_VALUE.toLowerCase())) {
										values.setNewval(currentRow.getCell(j) != null ? getDataFormatter()
												.formatCellValue(currentRow.getCell(j), formulaEvaluatorObj) : null);
									}
									if ("dfr_line_id".equalsIgnoreCase(attributeNamesList.get(j))
											&& attributeName.equalsIgnoreCase(sortedAttr)) {
										dfrLineId = getDataFormatter().formatCellValue(currentRow.getCell(j));
									}
								}
								attrObj.setName(sortedAttr);
								attrObj.setValues(values);
								attributes.add(attrObj);

							}
							productObj.setDfrlineid(dfrLineId);
							productObj.setAttributes(attributes);
							products.add(productObj);
						}
					}

				}
			}

			dataGrid.setProducts(products);
			dataGrid.setDfrid(dfrId);
			
			try {
				productDataGrid = editDfrBusiness.sortChangedAssetNewVal(dataGrid,dfrLineIds);
				String grid = gson.toJson(productDataGrid);
				System.out.println("data grid after import : " + grid);
				editDfrBusiness.saveNewAssetValues(productDataGrid);
				resultMap.put("SaveAssetNew", "success");
				isAssetValSuccess = true;
			} catch (Exception e) {
				logger.error("Error occured while saving asset new val", e);
				resultMap.put("SaveAssetNew", "failure");
			} finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			try {
				if (isAssetValSuccess) {
					editDfrBusiness.saveDependentAttributes(productDataGrid);
					resultMap.put("saveDependentAttributes", "success");
					editDfrBusiness.populateChangeSummaryValues(dfrLineIds,productDataGrid.getDfrid());
				} else {
					resultMap.put("saveDependentAttributes", "bypassed");
				}
			} catch (Exception e) {
				logger.error("Error occured while saving asset new val", e);
				resultMap.put("saveDependentAttributes", "failure");
			} finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			resultMap.put("SaveAssetNew", "success");
			resultMap.put("editedAssetCount", productDataGrid.getTotalrows().toString());
			isAssetValSuccess = true;

		} catch (Exception ex) {
			logger.error("Error occured while saving asset new val", ex);
			resultMap.put("SaveAssetNew", "failure");
		} finally {
			workbook.close();
			input.close();
			HttpClientUtils.closeQuietly(httpResp);
		}

		return buildResponse(resultMap, "Available", "Not Available");
	}
		
		@RequestMapping(value = "/getFirstKpiInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap<String,Object>>> getFirstKpiInfo(HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
			try{
				String dfrId=  req.getParameter("dfrId");
//				HashMap<String,Object>	resultMap = null;
				List<ErrorCardVO> errorCardVOs = null;
				if(StringUtils.isNotEmpty(dfrId))
					errorCardVOs = editDfrBusiness.getTotalAndLeftAssetById(dfrId);
				
			
			return buildCollectionResponse(errorCardVOs, "Available", "Not Available");	
				
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}
		
		@RequestMapping(value = "/getSecondKpiInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap<String,Object>>> getSecondKpiInfo(HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
			try{
				String dfrId=  req.getParameter("dfrId");
				HashMap<String,Object>	resultMap = null;
				
				if(StringUtils.isNotEmpty(dfrId))
					resultMap = editDfrBusiness.getChangesMadeById(dfrId);
				
			
			return buildCollectionResponse(resultMap, "Available", "Not Available");	
				
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}
		
		@RequestMapping(value = "/getThirdKpiInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap<String,Object>>> getThirdKpiInfo(HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
			try{
				String dfrId=  req.getParameter("dfrId");
				HashMap<String,Object>	resultMap = null;
				
				if(StringUtils.isNotEmpty(dfrId))
					resultMap = editDfrBusiness.getNewErrorCountById(dfrId);
				
			
			return buildCollectionResponse(resultMap, "Available", "Not Available");	
				
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}
		
		@RequestMapping(value = "/getPOECountByFilters", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<ProductDataGrid>> getPOECountByFilters(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			try{	
			List<POECountVO> searchResp ;
				if(productFilter!=null){
					searchResp =  editDfrBusiness.getPOECountByFilters(productFilter); 
				}else {
					searchResp = null;
				}		
				
			 return buildResponse(searchResp, "Available", "Not Available");
			}
			catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		 }
		
		 @RequestMapping(value = "/exportChangeSummary", method = RequestMethod.GET)
			public ResponseEntity<InputStreamResource> exportChangeSummary(HttpServletRequest req,HttpServletResponse resp, HttpResponse httpResp) {
			ProductFilter searchFilters = null;
			ProductDataGrid dataGrid = null;
			Set<String> productList = null;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inputStream = null;
			ResponseEntity<InputStreamResource> response = null;
			ByteArrayOutputStream outByteStream = null;
			OutputStream outStream = null;
			Workbook workbook = null;
			byte[] outArray = null;

			String applicationList[] = null;

			try {
				 String dfrId = req.getParameter("dfrId");
				  List<ChangeSummary> changeSummaryList = null;
				  if(StringUtils.isNotEmpty(dfrId))
					  changeSummaryList = editDfrBusiness.getChangeSummary(dfrId);
				  
				 List<String> products  = changeSummaryList.stream()
						    .map(ChangeSummary::getProductName)
						    .collect(Collectors.toList());
				 productList = new HashSet<String>(products);
				  
				Map<String, List<ChangeSummary>> changeSummaryListGrouped =
						changeSummaryList.stream().collect(Collectors.groupingBy(w -> w.getProductName()));
				  
				  
				String[] headerList = new String[]{"Asset Number","Attribute Name","New Value","Old Value","Date Updated","Updated By"};
				Map<String,Integer> headerMap = new HashMap<>();
				headerMap.put("Asset Number", 0);
				headerMap.put("Attribute Name", 1);
				headerMap.put("New Value", 2);
				headerMap.put("Old Value", 3);
				headerMap.put("Date Updated", 4);
				headerMap.put("Updated By", 5);
				try {
					workbook = new XSSFWorkbook();
					// Sheet sheet = workbook.createSheet(PRODUCT_CAGE);
					CellStyle dateCellStyle = getDateCellType(workbook);
					for (String product : PRODUCTS_BY_SEQUENCE) {
						List<ChangeSummary> productsDataList = changeSummaryListGrouped.get(product);
						if (CollectionUtils.isNotEmpty(productsDataList)) {
					

						if (product.equals(PRODUCT_CAGE)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList, PRODUCT_CAGE);
						} else if (product.equals(PRODUCT_CABINET)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList,
									PRODUCT_CABINET);
						} else if (product.equals(PRODUCT_AC_CIRCUIT)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList,
									PRODUCT_AC_CIRCUIT);
						} else if (product.equals(PRODUCT_DC_CIRCUIT)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList,
									PRODUCT_DC_CIRCUIT);
						} else if (product.equals(PRODUCT_PP)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList, PRODUCT_PP);

						} else if (product.equals(PRODUCT_NCC)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList, PRODUCT_NCC);

						} else if (product.equals(PRODUCT_DEMARCATION)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList,
									PRODUCT_DEMARCATION);

						} else if (product.equals(PRODUCT_PPPC)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList, PRODUCT_PPPC);
						} else if (product.equals(PRODUCT_PPPE)) {
							constructChangeSummary(workbook, headerMap,headerList, dateCellStyle,productsDataList, PRODUCT_PPPE);
						}

					}}
					
					outByteStream = new ByteArrayOutputStream();
					workbook.write(outByteStream);
					outArray = outByteStream.toByteArray();
					inputStream = new ByteArrayInputStream(outArray);

					// return ResponseEntity.ok().headers(headers).body(new
					// InputStreamResource(in));
					resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					resp.setHeader("Content-Disposition", "attachment; filename=ChangeSummaryDetails.xlsx");
					resp.setContentLength(outArray.length);
					resp.setHeader("Expires:", "0"); // Eliminates Browser
														// Caching
					outStream = resp.getOutputStream();
					outStream.write(outArray);
					outStream.flush();
				} catch (Exception ex) {
					String uuid = UUID.randomUUID().toString();
					logger.error(uuid, ex);
					ex.printStackTrace();
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
					if (workbook != null) {
						workbook.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
					if (outByteStream != null) {
						outByteStream.close();
					}
					if (outStream != null) {
						outStream.close();
					}

					HttpClientUtils.closeQuietly(httpResp);
				}

				return ResponseEntity.ok().body(new InputStreamResource(inputStream));

			} catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid, e);
				return buildErrorResponse(e.getMessage(), uuid);
			} finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}

	private void constructChangeSummary(Workbook workbook, Map<String, Integer> headerMap, String[] headerList, CellStyle dateCellStyle,
			List<ChangeSummary> changeSummarys, String productType) {

		XSSFSheet sheet = null;
		sheet = (XSSFSheet) workbook.getSheet(productType);
		CellStyle headerCellStyle = null;
		if (sheet == null) {
			sheet = (XSSFSheet) workbook.createSheet(productType);
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
			headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			Row headerRow = sheet.createRow(0);
			
			for (int col = 0; col < headerList.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(headerList[col]);
				cell.setCellStyle(headerCellStyle);
			}
		}
		int row = 0;
		row = sheet.getPhysicalNumberOfRows();
		Row currentRow = null;
		currentRow = sheet.createRow(row++);

		for (ChangeSummary summary : changeSummarys) {
			currentRow.createCell(headerMap.get("Asset Number")).setCellValue(summary.getAssetNum());
			currentRow.createCell(headerMap.get("Attribute Name")).setCellValue(summary.getAttrName());
			currentRow.createCell(headerMap.get("New Value")).setCellValue(summary.getAttrValue());
			currentRow.createCell(headerMap.get("Old Value")).setCellValue(summary.getOldValue());
			currentRow.createCell(headerMap.get("Date Updated")).setCellStyle(dateCellStyle);
			currentRow.getCell(headerMap.get("Date Updated")).setCellValue(summary.getCreatedDate());
			currentRow.createCell(headerMap.get("Updated By")).setCellValue(summary.getUserId());
			row = sheet.getPhysicalNumberOfRows();
			currentRow = sheet.createRow(row++);
		}
	}
		
		@RequestMapping(value = "/saveDfrNotes", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> saveDfrNotes(@RequestBody DfrNotesInput dfrNotesInput,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			String status;
			if (dfrNotesInput != null) {
				editDfrBusiness.saveDfrNotes(dfrNotesInput);
				status = "Succes";
			} else {
				status = "Failed";
			}

			return buildResponse(status, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

		@RequestMapping(value = "/getDfrNotes", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<ProductDataGrid>> getDfrNotes(HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			try{	
			List<DfrNotes> dfrNotes ;
				String dfrId = req.getParameter("dfrId");
				if(dfrId!=null){
					dfrNotes = editDfrBusiness.getDfrNotes(dfrId);
					
				}else {
					dfrNotes = null;
				}		
				
			 return buildResponse(dfrNotes, "Available", "Not Available");
			}
			catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		 }
		public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		    Set<Object> seen = ConcurrentHashMap.newKeySet();
		    return t -> seen.add(keyExtractor.apply(t));
		}
		
		@RequestMapping(value = "/createChangeSummary", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<ChangeSummary>>> createChangeSummary(@RequestBody SaveAssetForm data,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			HashMap<String, String> resultMap = new HashMap<>();
			editDfrBusiness.autoUpdateChangeSummaryValues(data);
			resultMap.put("SavedChangedSummary", "success");
			return buildResponse(resultMap, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
		
		@RequestMapping(value = "/dfrLineIdListByDfr", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<List<ChangeSummary>>> dfrLineIdListByDfr(HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
			try {
				String dfrId = req.getParameter("dfrId");
				
				Map<String,List<String>> dfrLineIds = editDfrBusiness.dfrLineIdListByDfr(dfrId);
				
				return buildResponse(dfrLineIds, "Available", "Not Available");
			} catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid, e);
				return buildErrorResponse(e.getMessage(), uuid);
			} finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}
		
		@RequestMapping(value = "/assetByDfrLineID", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<ProductDataGrid>> assetByDfrLineID(@RequestBody SaveAssetForm data,HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
			HashMap<String , String> resultMap = new HashMap<>();
			ProductDataGrid dataGrid = null;
			
			try{
					if(data != null){
						dataGrid = editDfrBusiness.productAttributeViewByLineId(data);
					}else{
						dataGrid = null;
						resultMap.put("AssetByDfrLineId", "No record found");
					}
					
			}catch(Exception e){
					logger.error("Error occured while getting asset by dfrlineid",e);
				resultMap.put("AssetByDfrLineId", "failure");
				return buildResponse(resultMap, "Available", "Not Available");
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
			
			return buildResponse(dataGrid, "Available", "Not Available");	
		 }
		
		@RequestMapping(value = "/checkSystemNameForPhysicalAudit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseDTO<HashMap<String,Object>>> checkSystemNameForPhysicalAudit(HttpServletRequest req, HttpServletResponse resp , HttpResponse httpResp) {
			try{
				
				String status = "";
				if(StringUtils.isNotEmpty(req.getParameter("dfrId"))){
					
					String dfrId = req.getParameter("dfrId");
					status= editDfrBusiness.checkSystemNameForPhysicalAudit(dfrId);
					
				}else {
					status = "Error : product can not be empty";
				}
				if(StringUtils.isNotEmpty(status)){
					return buildCollectionResponse(status, "Available", "Not Available");
				}else{
					return buildCollectionResponse(status, "Available", "Not Available");
				}
				
				
			}catch (Exception e) {
				String uuid = UUID.randomUUID().toString();
				logger.error(uuid,e);
				return buildErrorResponse(e.getMessage(), uuid);
			}finally {
				HttpClientUtils.closeQuietly(httpResp);
			}
		}
}
