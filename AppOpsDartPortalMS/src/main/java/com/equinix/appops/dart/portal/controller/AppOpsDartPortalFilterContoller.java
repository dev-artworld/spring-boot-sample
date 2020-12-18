package com.equinix.appops.dart.portal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.model.search.filter.BaseFilter;
import com.equinix.appops.dart.portal.model.search.filter.Filter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.service.AppOpsDartAttrConfigService;
import com.equinix.appops.dart.portal.service.AppOpsDartDaService;

@RestController
@RequestMapping(value={"/filters"})
public class AppOpsDartPortalFilterContoller extends BaseContorller {

	Logger logger = LoggerFactory.getLogger(AppOpsDartPortalFilterContoller.class); 
	
	@Autowired
	AppOpsDartDaService appOppsDartDaService;
	
	@Autowired
	AppOpsDartAttrConfigService appOpsDartAttrConfigService;
	
	
	String filterTables[] = new String[]{"SIEBEL_ASSET_DA","CLX_ASSET_DA","SV_ASSET_DA"};
	String errTables[] = new String[]{"SRC_CXI_ERROR_MASTER_TBL"};
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> testFilter(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
		String keyword = req.getParameter("keyword");
		return buildResponse(keyword, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/defaultfilters", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> testapi(HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
		/*appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "IBX");
		appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "ACCOUNT NAME");
		appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "SYSTEM NAME");*/
		List<Filter> filters =   appOppsDartDaService.getFilterList();
		SearchFilters searchFilters = new SearchFilters();
		for(Filter filter : filters){
			searchFilters.getFilters().add(filter);
		}
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
	
	@RequestMapping(value = "/getFilters", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getAllFilterColumns(@RequestBody BaseFilter baseFilter, 
			HttpServletRequest req, HttpServletResponse resp,HttpResponse httpResp) {
		try {
			String keyword = null;
			String key = null;
			if (baseFilter != null && StringUtils.isNotEmpty(baseFilter.getKeyword())) {
				keyword = baseFilter.getKeyword().replaceAll("\r\n","").replaceAll("\n","");
				if (StringUtils.isNotEmpty(baseFilter.getKey())) {
					key = baseFilter.getKey().replaceAll("\r\n","").replaceAll("\n","");
				}
			} else {
				throw new Exception("Invalid Search Filter...");
			}
			SearchFilters searchFilters = new SearchFilters();
			if (StringUtils.isBlank(keyword)) {
				List<Filter> filters = appOppsDartDaService.getFilterList();
				for (Filter filter : filters) {
					searchFilters.getFilters().add(filter);
				}
			} else {
				try {
					if (keyword != null && keyword.length() == 7 && keyword.toUpperCase().startsWith("VAL_")) {
						searchFilters = appOppsDartDaService.getErrorCodeGlobalKeywordFilters(keyword);
					} else {
						searchFilters = appOppsDartDaService.globalSearch(keyword, key);
					}
				} catch (Exception e) {
					logger.error("Error in global search :", e);
					/*
					 * List<Filter> filters =
					 * appOppsDartDaService.getFilterList(); for(Filter filter :
					 * filters){ searchFilters.getFilters().add(filter); }
					 */
				} finally {
					HttpClientUtils.closeQuietly(httpResp);
				}
			}

			/*
			 * String []filterNames= new
			 * String[]{"IBX","ACCOUNT_NAME","SYSTEM_NAME","ERROR_CODE"}; Map
			 * resultMap = new HashMap(); for (String filterName : filterNames)
			 * { List<Object> columns=
			 * appOppsDartDaService.getFilters(filterName, keyword,
			 * getFiltertables(filterName)); List<String> list = new
			 * ArrayList<String>(); for (Object object : columns) { Map<String,
			 * String> map=(Map<String, String>)object;
			 * list.add(map.get(filterName)!=null?map.get(filterName).trim():"")
			 * ; } resultMap.put(getLabelValue(filterName),new
			 * FilterResponse(filterName,getLabelValue(filterName), list)); }
			 */
			return buildResponse(searchFilters, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}

	@RequestMapping(value = "/getFiltersList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getAllFilterColumns(@RequestBody ProductFilter productFilter,
			HttpServletRequest req, HttpServletResponse resp, HttpResponse httpResp) {
		try {
			SearchFilters searchFilters = new SearchFilters();
			if (null != productFilter && productFilter.getKeyword() != null) {
				logger.info(productFilter.getKeyword());
				/*
				 * if(StringUtils.isBlank(productFilter.getKeyword())){
				 * List<Filter> filters = appOppsDartDaService.getFilterList();
				 * for(Filter filter : filters){
				 * searchFilters.getFilters().add(filter); } }else{
				 */
				try {
					if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getKeyword().length() == 7
							&& productFilter.getKeyword().toUpperCase().startsWith("VAL_")) {
						searchFilters = appOppsDartDaService.getFilterListForErrorCodeGlobalFilter(productFilter);
					} else {
						searchFilters = appOppsDartDaService.globalSearch(productFilter);
					}
				} catch (Exception e) {
					logger.error("Error in global search :", e);
					List<Filter> filters = appOppsDartDaService.getFilterList();
					for (Filter filter : filters) {
						searchFilters.getFilters().add(filter);
					}
				} finally {
					HttpClientUtils.closeQuietly(httpResp);
				}
			}
			return buildResponse(searchFilters, "Available", "Not Available");
		} catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid, e);
			return buildErrorResponse(e.getMessage(), uuid);
		} finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	}
	
	@RequestMapping(value = "/getFilter/{filterName}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<Object>> getFiltes(@PathVariable(name="filterName") String filterName,HttpServletRequest req , HttpServletResponse resp, HttpResponse httpResp) {
		try{
		String keyword = req.getParameter("keyword");
		logger.info(keyword);
		if(StringUtils.isBlank(keyword)){
			keyword="";
		}
		List<Object> columns= appOppsDartDaService.getFilters(filterName, keyword, getFiltertables(filterName)); 
		List<FilterResponse> res = new ArrayList<FilterResponse>();
		for (Object object : columns) {
			
			Map<String, String> map=(Map<String, String>)object;			
		} 
		return buildResponse(res, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	private String[] getFiltertables(String filterName) {
		if("ERROR_CODE".equalsIgnoreCase(filterName)){
			return errTables;
		}
		return filterTables;
	}

	private String getLabelValue(String filterName) {
		switch (filterName) {
		case "IBX":
			return "header8";			
		case "OU_NUM":
			return "header6";
		case "SYSTEM_NAME":
			return "header16";
		default:
			return "UNKNOWN";
		}
	}

	class FilterResponse implements Serializable {
		String key;
		String label;
		List<String> value;
		public FilterResponse(String labelValue, String filterName, List<String> value) {
			this.label=labelValue;
			this.key=filterName;
			this.value=value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public List<String> getValue() {
			return value;
		}
		public void setValue(List<String> value) {
			this.value = value;
		}
		
		
	}
}
