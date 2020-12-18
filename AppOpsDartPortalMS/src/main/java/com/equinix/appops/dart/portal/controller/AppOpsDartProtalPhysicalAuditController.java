package com.equinix.appops.dart.portal.controller;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.buisness.AppOpsEditDfrBusiness;
import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.Product;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;

@RestController
@RequestMapping(value={"/physicalaudit"})
public class AppOpsDartProtalPhysicalAuditController extends BaseContorller {
	Logger logger = LoggerFactory.getLogger(AppOpsDartProtalPhysicalAuditController.class);
	
	
	@Autowired
	AppOpsEditDfrBusiness editDfrBusiness;
	
	@RequestMapping(value = "/productattributeview", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductAttributeView(@RequestBody ProductFilter searchFilters, HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{	
		ProductDataGrid dataGrid = null ;
			if(searchFilters!=null){
				if (searchFilters.getAttributeFlag().equalsIgnoreCase("true")) {
				dataGrid =  editDfrBusiness.getProductPysicalAuditAttributeView(searchFilters); 
				dataGrid.setDfrid(searchFilters.getDfrId());
				}else if (searchFilters.getAttributeFlag().equalsIgnoreCase("false")) {
					dataGrid =  editDfrBusiness.getSelectedProductPysicalAuditAttributeView(searchFilters); 
				}
			}else {	
				dataGrid = null;
			}		
			if (searchFilters != null && searchFilters.getSearchDropBox() != null && searchFilters.getSearchDropBox().getSearchDrop() != null &&
					searchFilters.getKeyword() != null && CollectionUtils.isNotEmpty(searchFilters.getSearchDropBox().getSearchDrop()) &&
					searchFilters.getSearchDropBox().getSearchDrop().get(0) != null && 
							"header2".equalsIgnoreCase(searchFilters.getSearchDropBox().getSearchDrop().get(0).getKey()) && dataGrid != null && 
							CollectionUtils.isNotEmpty(dataGrid.getProducts()) && CollectionUtils.isNotEmpty(searchFilters.getFilters())) {
				List<String> searchForList = new ArrayList<>();
				String keywordSearched  = null;
				for (PFilter pFilter  : searchFilters.getFilters()) {
					if ("header2".equalsIgnoreCase(pFilter.getKey())) {
						keywordSearched = pFilter.getValue();
					}
				}
				if (StringUtils.isNotEmpty(keywordSearched)) {
					searchForList = Arrays.asList(keywordSearched.split(",")).stream().filter(i -> i != null && !i.equalsIgnoreCase(""))
							.map(obj -> obj.toString().trim()).collect(Collectors.toList());
					List<Product> nullAssets = dataGrid.getProducts().stream().filter(item->item.getAssetNumber()==null).collect(Collectors.toList());
					if(null!=nullAssets && nullAssets.size()>0){
						 return buildResponse(dataGrid, "Available", "Not Available");
					}
					List<Product> sortedProducts = new ArrayList<>();
					Map<String, List<Product>> productMap = dataGrid.getProducts().stream().filter(item->item.getAssetNumber()!=null).collect(Collectors.groupingBy(Product::getAssetNumber));
				//	Map<String, List<Product>> productMap = dataGrid.getProducts().stream().collect(Collectors.groupingBy(Product::getAssetNumber));
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
	
	@RequestMapping(value = "/getProductAttributeFilter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<ProductDataGrid>> getProductAttributeFilter(
			@RequestBody ProductFilter searchFilters, HttpServletRequest req, HttpServletResponse resp,
			HttpResponse httpResp) {
		try {
			List<String> dataGrid = null;
			if (searchFilters != null) {

				dataGrid = editDfrBusiness.getProductPysicalAuditAttributeViewFilter(searchFilters);

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
}