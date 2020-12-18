package com.equinix.appops.dart.portal.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.CountryAndIbxVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.Product;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.Filter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.search.product.SearchDropBox;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;
import com.equinix.appops.dart.portal.service.AppOpsDartDaService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;

@RestController
@RequestMapping(value={"/dataanalysis"})
public class AppOpsDartPortalDaContoller extends BaseContorller {
	Logger logger = LoggerFactory.getLogger(AppOpsDartPortalDaContoller.class);
	@Autowired
	AppOpsDartDaService appOpsDartDaService;
	
	@Autowired
	AppOpsInitiateDFRService appOpsInitiateDFRService;
	
	@RequestMapping(value = "/attributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getAttributeView(@RequestBody SearchFilters searchFilters, HttpServletRequest req , HttpServletResponse resp) {
			/*ProductDataGrid dataGrid = null ;
			if(searchFilters!=null){
				dataGrid =  appOpsDartDaService.getAttributeView(searchFilters); 
				//appOpsDartDaService.test();
			}else {
				dataGrid = null;
			}	*/	
			
		 return buildResponse(null, "Available", "Api discountinued");
	 }
	
	@RequestMapping(value = "/commonattributeview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getCommonAttributeView(@RequestBody ProductFilter searchFilters,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			ProductDataGrid dataGrid = null;
			if (searchFilters != null) {
				if (StringUtils.isNotEmpty(searchFilters.getKeyword()) && searchFilters.getKeyword().length() == 7
						&& searchFilters.getKeyword().toUpperCase().startsWith("VAL_")) {
					dataGrid = appOpsDartDaService.getCommonAttributeViewErrorCodeGlobal(searchFilters);
				} else {
					dataGrid = appOpsDartDaService.getCommonAttributeView(searchFilters);
				}
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
	
	@RequestMapping(value = "/refreshedcommonattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedCommonAttributeView(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{	
		ProductDataGrid dataGrid = null ;
			if(searchFilters!=null){
				dataGrid =  appOpsDartDaService.getRefreshedCommonAttributeGrid(searchFilters); 
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
	
	
	@RequestMapping(value = "/productattributeview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductAttributeView(
			@RequestBody ProductFilter searchFilters, HttpServletRequest req, HttpServletResponse resp,
			HttpResponse httpResp) {
		try {
			ProductDataGrid dataGrid = null;
			if (searchFilters != null) {
				if (StringUtils.isNotEmpty(searchFilters.getKeyword()) && searchFilters.getKeyword().length() == 7
						&& searchFilters.getKeyword().toUpperCase().startsWith("VAL_")) {
					if(DartConstants.IS_ELASTIC_CALL){
							dataGrid = appOpsDartDaService.getProductAttributeViewForErrorCodeGlobalElastic(searchFilters);
					}else{
						dataGrid = appOpsDartDaService.getProductAttributeViewForErrorCodeGlobal(searchFilters);
					}
				} else {
						dataGrid = appOpsDartDaService.getProductAttributeView(searchFilters, false);
				}
			} else {
				dataGrid = null;
			}
			if (searchFilters != null && searchFilters.getSearchDropBox() != null && searchFilters.getSearchDropBox().getSearchDrop() != null &&
					searchFilters.getKeyword() != null && CollectionUtils.isNotEmpty(searchFilters.getSearchDropBox().getSearchDrop()) &&
					searchFilters.getSearchDropBox().getSearchDrop().get(0) != null && 
							"header2".equalsIgnoreCase(searchFilters.getSearchDropBox().getSearchDrop().get(0).getKey()) && dataGrid != null && 
							CollectionUtils.isNotEmpty(dataGrid.getProducts())) {
				List<String> searchForList = new ArrayList<>();
				searchForList = Arrays.asList(searchFilters.getKeyword().split(",")).stream().filter(i -> i != null && !i.equalsIgnoreCase("")).
						map(obj -> obj.toString().trim()).collect(Collectors.toList());
				List<Product> sortedProducts = new ArrayList<>();
				Map<String,List<Product>>  productMap = dataGrid.getProducts().stream().collect(Collectors.groupingBy(Product::getAssetNumber));
				for (String assetNum : searchForList) {
					if (productMap.containsKey(assetNum)) {
						sortedProducts.addAll(productMap.get(assetNum));
					}
				}
				dataGrid.getProducts().clear();
				dataGrid.getProducts().addAll(sortedProducts);
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
	
	@RequestMapping(value = "/refreshedproductattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedProductAttributeGrid(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{	
		ProductDataGrid dataGrid = null ;
			if (searchFilters != null) {

				dataGrid = appOpsDartDaService.getRefreshedProductAttributeGrid(searchFilters);

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
	
	
	@RequestMapping(value = "/productwidgets", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductWidgets(@RequestBody ProductFilter productFilter,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			ProductWidgets widgets = null;
			if (productFilter != null) {
				if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getKeyword().length() == 7
						&& productFilter.getKeyword().toUpperCase().startsWith("VAL_")) {
					widgets = appOpsDartDaService.getProductWidgetsErrorCodeGlobal(productFilter);
				} else {
					widgets = appOpsDartDaService.getProductWidgets(productFilter);
				}
			} else {
				widgets = null;
			}
			return buildResponse(widgets, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/refreshedproductwidgets", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getRefreshedProductWidgets(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{	
		ProductWidgets widgets = null ;
			if(productFilter!=null){
				widgets =  appOpsDartDaService.getRefreshedProductWidgets(productFilter); 
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
	
	@RequestMapping(value = "/productfilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductFilterData(@RequestBody ProductFilter productFilter,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			ProductSearchResponse searchResp = null;
			if (productFilter != null) {
				if (StringUtils.isNotEmpty(productFilter.getKeyword()) && 
						/*productFilter.getKeyword().length() == 7 &&*/ productFilter.getKeyword().toUpperCase().startsWith("VAL_")) {
					if(DartConstants.IS_ELASTIC_CALL){
						searchResp = appOpsDartDaService.getProductSearchForErrorCodeGlobalFilterKeywordElastic(productFilter);
					}else{
						searchResp = appOpsDartDaService.getProductSearchForErrorCodeGlobalFilterKeyword(productFilter);
					}
				} else {
					searchResp = appOpsDartDaService.getProductSearchResponse(productFilter);
				}
			} 
			return buildResponse(searchResp, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/errorsection", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ErrorSectionResponse>> getErrorSection(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{	
		ErrorSectionResponse errorSectionResponse ;
			if(productFilter!=null){
				errorSectionResponse =  appOpsDartDaService.getErrorSctionResponse(productFilter); 
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
	
	
	@RequestMapping(value = "/heirarchysection", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ErrorSectionResponse>> getHierarchySection(@RequestBody ProductFilter productFilter, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
		HierarchyView view =null;
		
			if(productFilter!=null){
				view = appOpsDartDaService.getHierarchyView(productFilter);
				//Gson gson = new  Gson();
				logger.info(" Tree populated "   /*gson.toJson(view,HierarchyView.class)*/);
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
	
	@RequestMapping(value = "/test", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public String test(@RequestBody ProductFilter productFilter,HttpServletRequest req , HttpServletResponse resp) {
		long start = System.currentTimeMillis();
		String result =appOpsDartDaService.test(productFilter);
		long end= System.currentTimeMillis();
		logger.info("Time consumnerd " + (start - end));
		return result;
			
		
	 }
	
	@RequestMapping(value = "/fireSnapshotFilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> fireSnapshotFilter(@RequestBody ProductFilter productFilter,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			String result = null;
			long start = System.currentTimeMillis();
			if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getKeyword().length() == 7
					&& productFilter.getKeyword().toUpperCase().startsWith("VAL_")) {
				result = appOpsDartDaService.fireSnapshotFilterForErrorCodeGlobalFilter(productFilter);
			} else {
				result = appOpsDartDaService.fireSnapshotFilter(productFilter);
			}
			long end = System.currentTimeMillis();
			logger.info("Time Consumed>>> " + (start - end));
			return buildResponse(result, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	
	@RequestMapping(value = "/getSearchDropBox", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<SearchDropBox>> getSearchDropBox( HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			SearchDropBox data = appOpsDartDaService.getSearchDropBox();
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/errorCodeMaster", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<List<Filter>>> getErrorCodeMaster() {
		HashMap<String, SrcCxiErrorMasterTbl> errorCodeMaster = null;
		SearchFilters searchFilter = new SearchFilters();
		try{
			errorCodeMaster = appOpsInitiateDFRService.getErrorMasterMap();
			searchFilter.getFilters().addAll(appOpsDartDaService.getEmptyFilterList());
			Filter errorCodeFilter =  new Filter();
			errorCodeFilter.setKey("Error Code");
			errorCodeFilter.setLable("Error Code");
			errorCodeFilter.setValues(new ArrayList<String>(errorCodeMaster.keySet()));
			searchFilter.getFilters().add(errorCodeFilter);
			return buildResponse(searchFilter, "Available", "Not Available");
		}
		catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		}
	 }
	
	@RequestMapping(value = "/getAppTimeout", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getAppTimout( HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			String data = appOpsDartDaService.getApplicationTimeout();
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getAllSystemName", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getAllSystemName(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<String> data = appOpsDartDaService.getAllSystemName();
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getAllRegion", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getAllRegion(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<String> data = appOpsDartDaService.getAllRegion();
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/countriesIbxByRegion", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getCountriesIbxByRegion(@RequestParam("region")String region, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<CountryAndIbxVo> data = appOpsDartDaService.getCountriesIbxByRegion(region);
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/ibxBycountriesNRegion", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getIbxByCountriesNRegion(@RequestParam("region")String region,@RequestParam("country")String country, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<String> data = appOpsDartDaService.getIbxByCountriesNRegion(region,country);
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/errorsByIbx", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> errorsByIbx(@RequestParam("ibx")String ibx, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<ErrorCodeVO> data = appOpsDartDaService.getErrorsByIbx(ibx);
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getAllActiveAccountNum", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getAllActiveAccountNum(@RequestBody SearchFormFilter formFilter, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<AccountVo> data = appOpsDartDaService.getAllActiveAccountNum(formFilter);
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getAllActiveErrors", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getAllActiveErrors(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<ErrorCodeVO> data = appOpsDartDaService.getAllActiveErrors();
			return buildResponse(data, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getProductList(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
			List<String> data = appOpsDartDaService.getProductList();
			return buildResponse(data, "Available", "Not Available");
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
