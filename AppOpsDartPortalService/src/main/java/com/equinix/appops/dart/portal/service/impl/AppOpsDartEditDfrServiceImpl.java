package com.equinix.appops.dart.portal.service.impl;

import java.beans.Expression;
import java.beans.Statement;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.common.CGAlphaNumComparator;
import com.equinix.appops.dart.portal.common.NonResetAssetValVar;
import com.equinix.appops.dart.portal.common.ServiceUtil;
import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.dao.AppOppsDartHomeDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartDaDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartEditDfrDao;
import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.CxiErrorTbl;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.entity.SrcSLstOfVal;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrLineIdsVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.model.DFRFileModel;
import com.equinix.appops.dart.portal.model.Reset;
import com.equinix.appops.dart.portal.model.ResetAndDelete;
import com.equinix.appops.dart.portal.model.dfr.DfrUpdateInput;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;
import com.equinix.appops.dart.portal.model.errorsection.Error;
import com.equinix.appops.dart.portal.model.errorsection.ErrorCategory;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.errorsection.ErrorValue;
import com.equinix.appops.dart.portal.model.errorsection.SnapshotErrorData;
import com.equinix.appops.dart.portal.model.grid.Attribute;
import com.equinix.appops.dart.portal.model.grid.Product;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.grid.Values;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyProduct;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchySubproduct;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.Filter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductResp;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.search.product.SearchDrop;
import com.equinix.appops.dart.portal.model.search.product.SnapshotProductFilterResult;
import com.equinix.appops.dart.portal.model.widget.ProductWidget;
import com.equinix.appops.dart.portal.model.widget.ProductWidgetGroup;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;
import com.equinix.appops.dart.portal.service.AppOpsDartAttrConfigService;
import com.equinix.appops.dart.portal.service.AppOpsDartDaService;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.EmailSenderService;
import com.equinix.appops.dart.portal.service.SVSyncVService;
import com.equinix.appops.dart.portal.util.DartUtil;
import com.equinix.appops.dart.portal.vo.ChangeSummaryDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author Ankur Bhargava
 *
 */
@Service
@Transactional
public class AppOpsDartEditDfrServiceImpl implements AppOpsDartEditDfrService{

	private static final int MAX_ATTRIBUTES = 324;

	private static final int MAX_HEADERS = 92;
	
	public static final String BLANK = "";
	
	long count = 0;
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartDaService.class);
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	AppOpsDartAttrConfigService appOpsDartAttrConfigService;
	
	@Autowired
	AppOpsDartEditDfrDao  editDfrDao;
	
	@Autowired
	AppOpsInitiateDFRService initiateDFRService;
	
	@Autowired
	SVSyncVService svService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	AppOpsDartDaDao appOpsDartDaDao;
	
	@Autowired
	AppOpsInitiateDFRDao appOpsInitDartDaDao;
	
	@Autowired
	AppOppsDartHomeDao oppsDartHomeDao;
	
	@Autowired
    JdbcTemplate jdbc;	
	
	@Override
	public SearchFilters getFilterList(ProductFilter productFilter) {
		List<Filter> filters=new ArrayList<>();
		
		
			String dfrId = productFilter.getDfrId();
			AttributeConfig attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "IBX");
			Filter filter1=new Filter();
			filter1.setKey(attributeConfig.getHeaderPosition().toLowerCase());
			filter1.setLable("IBX");
			filter1.setValues(editDfrDao.getFilterListFromSnapshotDA(attributeConfig.getHeaderPosition(),dfrId));
			filters.add(filter1);

			attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "ACCOUNT NUMBER");
			Filter filter2=new Filter();
			filter2.setKey(attributeConfig.getHeaderPosition().toLowerCase());
			filter2.setLable("ACCOUNT NUMBER");
			filter2.setValues(editDfrDao.getFilterListFromSnapshotDA(attributeConfig.getHeaderPosition(),dfrId));
			filters.add(filter2);

			attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "SYSTEM NAME");
			Filter filter3=new Filter();
			filter3.setKey(attributeConfig.getHeaderPosition().toLowerCase());
			filter3.setLable("SYSTEM NAME");
			filter3.setValues(editDfrDao.getFilterListFromSnapshotDA(attributeConfig.getHeaderPosition(),dfrId));
			filters.add(filter3);

			List<String> errorCodeList= editDfrDao.getFilterListFromErrorSnapshotError(dfrId);
			Filter filter4=new Filter();
			filter4.setKey("Error Code");
			filter4.setLable("Error Code");
			filter4.setValues(errorCodeList);
			filters.add(filter4);
			
			List<String> regions = editDfrDao.getRegionFilter(dfrId);
			attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "Region");
			Filter regionFilter = new Filter();
			regionFilter.setKey(attributeConfig.getHeaderPosition().toLowerCase());
			regionFilter.setLable("REGION");
			regionFilter.setValues(regions);
			filters.add(regionFilter);

			SearchFilters searchFilters = new SearchFilters();
			for(Filter filter : filters){
				searchFilters.getFilters().add(filter);
			}

			return searchFilters;
		
	}

	@Override
	public ProductSearchResponse getProductSearchResponse(ProductFilter productFilter) {
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
				productFilter.getFilters().addAll(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			} else {
				productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			}
		}*/
		HashMap<String,Object> resultMap = editDfrDao.getProductFilters(productFilter);
		List<SnapshotSiebelAssetDa> sblList =(List<SnapshotSiebelAssetDa>) resultMap.get(DartConstants.SBL_LIST);
		ProductSearchResponse productSearchResponse = new ProductSearchResponse();
		Map<String, List<SnapshotSiebelAssetDa>> map =sblList.stream().collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader20,Collectors.toList()));
		Long cleanCount = 0L ;
		Long errorCount = 0L;
		for (Map.Entry<String, List<SnapshotSiebelAssetDa>> entry : map.entrySet()) {
			ProductResp product = new ProductResp();
			product.setName(entry.getKey());
			cleanCount = 0L;
			errorCount = 0L;
			for(SnapshotSiebelAssetDa sbl : entry.getValue()){
				if(sbl!=null){
					if(sbl.getHeader38() != null && sbl.getHeader38().equalsIgnoreCase("Y")){
						errorCount = errorCount + 1;
					} else if(sbl.getHeader38() == null || sbl.getHeader38().equalsIgnoreCase("N")){
						cleanCount = cleanCount+1;
					}
				}
			}   
			product.setClean(cleanCount);
			product.setError(errorCount);
			product.setTotal(cleanCount+errorCount);
			productSearchResponse.getProducts().add(product);
		}
		productSearchResponse.setDfrid(productFilter.getDfrId());
		return productSearchResponse;
	}

	@Override
	public SearchFilters globalSearch(String keyword, String dfrId, String key) {
		ProductFilter productFilter = new ProductFilter();
		if (StringUtils.isNotEmpty(key)) {
			List<String> serialNumList = null;
			List<PFilter> pFiltersList = new ArrayList<>();
			if ("header3".equalsIgnoreCase(key)) {
				serialNumList = new ArrayList<String>(
						Arrays.asList(keyword.split(",")).stream().filter(i -> i != null && !i.equalsIgnoreCase(""))
								.map(String::trim).collect(Collectors.toList()));
				PFilter serialNumFilter = new PFilter();
				serialNumFilter.setKey("header3");
				serialNumFilter.setValue(keyword);
				serialNumFilter.setLable("Serial Number");
				serialNumFilter.setListOfValues(serialNumList);
				pFiltersList.add(serialNumFilter);
			} else {
				List<String> assetNumList = new ArrayList<String>(
						Arrays.asList(keyword.split(",")).stream().filter(i -> i != null && !i.equalsIgnoreCase(""))
								.map(String::trim).collect(Collectors.toList()));
				PFilter assetNumFilter = new PFilter();
				assetNumFilter.setKey("header2");
				assetNumFilter.setLable("Asset Num");
				assetNumFilter.setValue(keyword);
				assetNumFilter.setListOfValues(assetNumList);
				pFiltersList.add(assetNumFilter);
			}
			productFilter.setFilters(pFiltersList);
			productFilter.setKeyword("");
		}else{
			productFilter.setKeyword(keyword);
		}
		productFilter.setDfrId(dfrId);
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
				productFilter.getFilters().addAll(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			} else {
				productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			}
			productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
		}*/
		List<SnapshotSiebelAssetDa> sdaList= editDfrDao.globalSearch(productFilter);
		Map<String,List<SnapshotSiebelAssetDa>> ibxMap = sdaList.stream().filter(p-> p.getHeader8()!=null).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader8));
		Map<String,List<SnapshotSiebelAssetDa>> accountNumMap = sdaList.stream().filter(p-> p.getHeader6()!=null).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader6));
		Map<String,List<SnapshotSiebelAssetDa>> systemNameMap = sdaList.stream().filter(p-> p.getHeader16()!=null).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
		List<String> errorCodeList= editDfrDao.getFilterListFromErrorSnapshotError(productFilter.getDfrId());
		List<String> ibxList = new ArrayList<>();
		List<String> accounNumList = new ArrayList<>();
		List<String> systemNameList = new ArrayList<>();
		ibxMap.entrySet().forEach(ibx->{
			ibxList.add(ibx.getKey());
		});
		accountNumMap.entrySet().forEach(accountNum->{
			accounNumList.add(accountNum.getKey());
		});
		systemNameMap.entrySet().forEach(systemName->{
			systemNameList.add(systemName.getKey());
		});
		SearchFilters searchFilters = new SearchFilters();
		List<Filter> filters = new ArrayList<>();
		AttributeConfig attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "IBX");
		Filter filter1=new Filter();
		filter1.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		filter1.setLable("IBX");
		filter1.setValues(ibxList);
		filters.add(filter1);
		
		attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "ACCOUNT NUMBER");
		Filter filter2=new Filter();
		filter2.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		filter2.setLable("ACCOUNT NUMBER");
		filter2.setValues(accounNumList);
		filters.add(filter2);
		
		attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "SYSTEM NAME");
		Filter filter3=new Filter();
		filter3.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		filter3.setLable("SYSTEM NAME");
		filter3.setValues(systemNameList);
		filters.add(filter3);
		
		Filter filter4=new Filter();
		filter4.setKey("Error Code");
		filter4.setLable("Error Code");
		filter4.setValues(errorCodeList);
		filters.add(filter4);
		
		if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "Region");
			Map<String,List<SnapshotSiebelAssetDa>> regionMap = sdaList.stream().filter(p-> p.getHeader51()!=null).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader51));
			Filter regionFilter = new Filter();
			regionFilter.setKey(attributeConfig.getHeaderPosition().toLowerCase());
			regionFilter.setLable("REGION");
			regionFilter.setValues(new ArrayList<>(regionMap.keySet()));
			filters.add(regionFilter);
		}
		
		searchFilters.setFilters(filters);
		searchFilters.setDfrid(productFilter.getDfrId());
		return searchFilters;
	}

	
	public ErrorSectionResponse getErrorSctionResponse(ProductFilter productFilter){
		ErrorSectionResponse errorResp= new ErrorSectionResponse(); 
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			/*if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
				productFilter.getFilters().addAll(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			} else {
				productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			}*/
			productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
		}
		List<SnapshotErrorData> errorList =editDfrDao.getErrorSectionJdbc(productFilter);
			
		Map<String, List<SnapshotErrorData>> mapByErrorCode = errorList.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getErrorCode));
		mapByErrorCode.forEach((errorCode,list)->{
			Error error = new Error(); 
			String valClass="";
			String bizGroup="";	
			String errDesc = "";
			String alertFlag="";
			Map<String, List<SnapshotErrorData>> mapByAsset = list.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
			Map<String, List<SnapshotErrorData>> mapByOpenAsset = list.stream()
					.filter(item->(item.getAssetNum()!=null 
					&& StringUtils.isNotEmpty(item.getStatusCd()) 
					&& null!=item.getStatusCd() && item.getStatusCd().toLowerCase().equalsIgnoreCase("open")))
					.collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
			
			Map<String, List<SnapshotErrorData>> mapByValidNotClosed = list.stream()
					.filter(item->(item.getAssetNum()!=null 
					&& ( StringUtils.isEmpty(item.getValidStat()) 
							|| null==item.getValidStat() || !item.getValidStat().toLowerCase().equalsIgnoreCase("closed")
						)
					))
					.collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
			
			StringBuilder rowId = new StringBuilder();
			for (Map.Entry<String, List<SnapshotErrorData>> entry : mapByAsset.entrySet()) {
				
				if(CollectionUtils.isNotEmpty(entry.getValue())){
					valClass = entry.getValue().get(0).getValidationClass();
					bizGroup = entry.getValue().get(0).getOwnerOfFixing();
					errDesc=  entry.getValue().get(0).getErrorName();
					alertFlag = entry.getValue().get(0).getAlertFlag();
				}
				for(SnapshotErrorData errorRecord : entry.getValue()){
					rowId.append(errorRecord.getTbl()+"##"+errorRecord.getRowId()+"##"+errorRecord.getValidStat()).append(",");
				}
			}
			
			int assetCount = 0;
			 for (Map.Entry<String, List<SnapshotErrorData>> entry : mapByOpenAsset.entrySet()) {
		            	assetCount = assetCount + entry.getValue().size();
		        }
			 int currentAssetCount = 0;
			 for (Map.Entry<String, List<SnapshotErrorData>> entry : mapByValidNotClosed.entrySet()) {
		            currentAssetCount  = currentAssetCount + entry.getValue().size();
		     }
			 
			 error.setAssetCount(assetCount+"");
			 error.setCurrentAssetCount(currentAssetCount+"");
			 if(assetCount < currentAssetCount ){
				 error.setTrend("up"); 
			 }else if(assetCount > currentAssetCount ){
				  error.setTrend("down"); 
			 } else if(assetCount ==currentAssetCount ){
				 error.setTrend("eq");
			 }
		//	error.setAssetCount(list.size()+"");
			error.setCode(errorCode);
			error.setAlertFlag(alertFlag);
			error.setBizGroup(bizGroup);
			if(StringUtils.isNoneEmpty(rowId) && StringUtils.isNotBlank(rowId))
				error.setPkid(rowId.substring(0,rowId.lastIndexOf(",")));
			error.setErrorDescription(errDesc);
			if(valClass.startsWith("1 -")){
				error.setCategory("P1");	
			}else if(valClass.startsWith("2 -")){
				error.setCategory("P2");	
			}else {
				error.setCategory("P3");
			}
			errorResp.getErrors().add(error);
		});
		
			Map<String, List<SnapshotErrorData>> mapByPriority = errorList.stream().filter(item->item.getValidationClass()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getValidationClass));
			mapByPriority.forEach((k,v)->{
			ErrorCategory errorCategory = new ErrorCategory();
			ErrorValue errorValue = new ErrorValue();
			if(k.startsWith("1 -")){
				errorCategory.setKey("P1");
				getErrorCategoryData(v, errorValue);
				errorCategory.setValue(errorValue);
				errorResp.getCategory().add(errorCategory);
			}else if(k.startsWith("2 -")){
				errorCategory.setKey("P2");
				getErrorCategoryData(v, errorValue);
				errorCategory.setValue(errorValue);
				errorResp.getCategory().add(errorCategory);
			}else if(k.startsWith("3 -")){
				errorCategory.setKey("P3");
				getErrorCategoryData(v, errorValue);
				errorCategory.setValue(errorValue);
				errorResp.getCategory().add(errorCategory);
			}else {
				for(ErrorCategory errCat : errorResp.getCategory()){
					if(errCat.getKey().equalsIgnoreCase("P3")){
						ErrorValue ev = errCat.getValue();
						errorValue.setTotalErrorCount(Integer.valueOf(ev.getTotalErrorCount())+v.size()+"");
						Map<String, List<SnapshotErrorData>> tdmap = v.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getErrorCode));
						errorValue.setTotalDistinctCount(Integer.valueOf(ev.getTotalDistinctCount())+tdmap.size()+"");
						Map<String, List<SnapshotErrorData>> tdamap = v.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
						errorValue.setTotalDistinctAsset(Integer.valueOf(ev.getTotalDistinctAsset())+tdamap.size()+"");
					}
				}
			}
			
			
		});
		boolean isP3Found =false;
		boolean isP1Found =false;
		boolean isP2Found =false;
		for(ErrorCategory category : errorResp.getCategory()){
			if(category.getKey().equalsIgnoreCase("P3")){
				isP3Found = true;
			}
			if(category.getKey().equalsIgnoreCase("P1")){
				isP1Found = true;
			}
			if(category.getKey().equalsIgnoreCase("P2")){
				isP2Found = true;
			}
		}
		
		if(!isP3Found){
			ErrorCategory category =new ErrorCategory();
			ErrorValue ev = new ErrorValue();
			category.setKey("P3");
			ev.setTotalDistinctAsset("0");
			ev.setTotalDistinctCount("0");
			ev.setTotalErrorCount("0");
			category.setValue(ev);
			errorResp.getCategory().add(category);
		}
		
		if(!isP1Found){
			ErrorCategory category =new ErrorCategory();
			ErrorValue ev = new ErrorValue();
			category.setKey("P1");
			ev.setTotalDistinctAsset("0");
			ev.setTotalDistinctCount("0");
			ev.setTotalErrorCount("0");
			category.setValue(ev);
			errorResp.getCategory().add(category);
		}
		if(!isP2Found){
			ErrorCategory category =new ErrorCategory();
			ErrorValue ev = new ErrorValue();
			category.setKey("P2");
			ev.setTotalDistinctAsset("0");
			ev.setTotalDistinctCount("0");
			ev.setTotalErrorCount("0");
			category.setValue(ev);
			errorResp.getCategory().add(category);
		}
		return errorResp;
	}

	
	/**
	 * @param v
	 * @param errorValue
	 */
	private void getErrorCategoryData(List<SnapshotErrorData> v, ErrorValue errorValue) {
		errorValue.setTotalErrorCount(v.size()+"");
		Map<String, List<SnapshotErrorData>> tdmap = v.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getErrorCode));
		errorValue.setTotalDistinctCount(tdmap.size()+"");
		Map<String, List<SnapshotErrorData>> tdamap = v.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
		errorValue.setTotalDistinctAsset(tdamap.size()+"");
	}
	
	@Override
	public ProductWidgets getProductWidgets(ProductFilter productFilter) {
		ProductWidgets productWidgets  = new ProductWidgets();
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
				productFilter.getFilters().addAll(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			} else {
				productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			}
			productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
		}*/
		for(PFilter filter : productFilter.getFilters()){
			if(filter.getKey().equalsIgnoreCase("header20")){
				for(String product : filter.getListOfValues()){
					List<SnapshotSiebelAssetDa> sbList = editDfrDao.getSiebelAssetDaDataByProduct(productFilter, product);
					System.out.println(sbList.size());
					if(product.equalsIgnoreCase("Cage")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cage");
						Map<String,List<SnapshotSiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
						ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
						widgetGroup.setName("");
						mapBySysName.entrySet().stream().forEach(entry->{
							widgetGroup.getValues().add(entry.getKey());
							
						});
						productWidget.getGroups().add(widgetGroup);
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Cabinet")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cabinet");
						Map<String,List<SnapshotSiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Cabinet"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader14());
								}
								//groupTitle = entry.getValue().get(0).getHeader16();
							}
							widgetGroup.setName(entry.getKey());
							productWidget.getGroups().add(widgetGroup);
							
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Demarcation Point")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Demarcation Point");
						Map<String,List<SnapshotSiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Demarcation Point"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader14());
								}
								//groupTitle = entry.getValue().get(0).getHeader16();
							}
							widgetGroup.setName(entry.getKey());
							productWidget.getGroups().add(widgetGroup);
							
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("AC Circuit")){

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("AC Circuit");
						Map<String,List<SnapshotSiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("AC Circuit"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
							}
							widgetGroup.setName(groupTitle);
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("DC Circuit")){

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("DC Circuit");
						Map<String,List<SnapshotSiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("DC Circuit"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
							}
							widgetGroup.setName(groupTitle);
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Patch Panel")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Patch Panel");
						Map<String,List<SnapshotSiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Patch Panel"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
								widgetGroup.setName(groupTitle);
								productWidget.getGroups().add(widgetGroup);
							}
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Network Cable Connection")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Network Cable Connection");
						Map<String,List<SnapshotSiebelAssetDa>> mapByASidePatchPanel = sbList.stream().filter(item->item.getAttr22()!=null).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getAttr22));
						mapByASidePatchPanel.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
								widgetGroup.setName(groupTitle);
								productWidget.getGroups().add(widgetGroup);
							}
						});
						productWidgets.getProducts().add(productWidget);
					}
				}
			}
		}
		return productWidgets;
	}
	
	@Override
	public ProductDataGrid getCommonAttributeView(ProductFilter productFilter) {
		
		try{
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
				if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
					productFilter.getFilters().addAll(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
				} else {
					productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
				}
				productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			}*/
			productFilterResult = editDfrDao.getProductFilterResult(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList  = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			
			/*List<SnapshotSiebelAssetDa> sblDaList  = editDfrDao.getSnapshotSiebleAssetDaData(productFilter);
			List<SnapshotClxAssetDa> clxDaList = editDfrDao.getSnapshotClxAssetDaData(productFilter);
			List<SnapshotSvAssetDa> svDaList = editDfrDao.getSnapshotSvAssetDaData(productFilter);*/
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
			//HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
			
			Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
			Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");
			Map<String,AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			// Main json object 
			ProductDataGrid dataGridJson = new ProductDataGrid();
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv =null;
			
				int maxCommonAttrCount = 0;
				String maxCommonProduct = "";
				for(Map.Entry<String, Integer> entry :maximumAttrCount.entrySet()){
					maxCommonAttrCount = entry.getValue().intValue();
					maxCommonProduct = entry.getKey();
					logger.info("Max product : " + maxCommonProduct + ":" + maxCommonAttrCount );
					break;
				}
				for(SnapshotSiebelAssetDa sbl : sblDaList){
					Product productJson = new Product();
					if(sbl!=null){
						productJson.setName(sbl.getHeader20());
						productJson.setDfrlineid(sbl.getDfrLineId());
						if(StringUtils.isNotEmpty(sbl.getHeader60())&& sbl.getHeader60().equalsIgnoreCase("Y")){
							productJson.setValidate("Y");
						}else{
							productJson.setValidate("N");
						}
						clx = ServiceUtil.getClx(sbl, clxDaList);
						sv = ServiceUtil.getSv(sbl,svDaList );
						/*for(int headerCounter =1 ; headerCounter <= 66 ; headerCounter++){*/
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, 2,assetNewValMap);
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, 6,assetNewValMap);
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, 7,assetNewValMap);
					//}
					//SortedMap<Integer,AttributeConfig> productAttrMap = configProductCommonAttrMap.get(sbl.getHeader20());
						for (Map.Entry<String, SortedMap<Integer, AttributeConfig>> entry : configProductCommonAttrMap.entrySet()) {
							if(isProductPresentInFilter(productFilter, entry.getKey())){
								if(isProductContainsAnyCommonAttributeViewDisplayYes(entry.getValue())){
									 for(int attrCounter =1 ; attrCounter <= maxCommonAttrCount ; attrCounter++){
										setAttributes(clx, sv, sbl, productJson, entry.getValue(), attrCounter,assetNewValMap);
									 }
								}
							}
						 }
					}
					dataGridJson.getProducts().add(productJson);
				}
			
			logger.info("Attribute View processing end ");
			return dataGridJson;
			
		}catch(Exception e){
			logger.error("Error in attribute view ", e);
			return null;
		}
		
	}
	
	@Override
	public ProductDataGrid getProductAttributeView(ProductFilter productFilter) {
		try {
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();

			productFilterResult.setProductFilter(productFilter);
			
			long start = System.currentTimeMillis();
			productFilterResult = editDfrDao.getProductFilterResult(productFilterResult);
			long end  = System.currentTimeMillis();
			long total = start-end;
			logger.info("Total time of getProductFilterResult "+total);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			/*
			 * List<SnapshotSiebelAssetDa> sblDaList =
			 * editDfrDao.getSnapshotSiebleAssetDaData(productFilter);
			 * List<SnapshotClxAssetDa> clxDaList =
			 * editDfrDao.getSnapshotClxAssetDaData(productFilter);
			 * List<SnapshotSvAssetDa> svDaList =
			 * editDfrDao.getSnapshotSvAssetDaData(productFilter);
			 */
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			dataGridJson.setTotalrows(productFilter.getTotalRows());
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
					}
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
						setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					productJson.setDqmErrorCodes(sbl.getDqmErrorDescription()!= null?sbl.getDqmErrorDescription():DartConstants.BLANK);
					productJson.setFixedValCodes(sbl.getFixedValCodes()!= null?sbl.getFixedValCodes():DartConstants.BLANK);
					productJson.setRedAttrNames(sbl.getRedAttrNames()!= null?sbl.getRedAttrNames():DartConstants.BLANK);
					productJson.setGreenAttrNames(sbl.getGreenAttrNames()!= null?sbl.getGreenAttrNames():DartConstants.BLANK);
					productJson.setRedRowIdentifier(sbl.getRedRowIdentifier()!= null?sbl.getRedRowIdentifier():DartConstants.BLANK);
					productJson.setGreenRowIdentifier(sbl.getGreenRowIdentifier()!= null?sbl.getGreenRowIdentifier():DartConstants.BLANK);
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	}
	
	@Override
	public List<String> getProductAttributeFilter(ProductFilter productFilter) {
		List<String> commonList = new ArrayList<String>();
		try {
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			/*
			 * if (productFilter.isErrorCodeGlobalFilterKeyword()) { if
			 * (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
			 * productFilter.getFilters().addAll(
			 * getProductFilterForErrorCodeGlobalFilter(productFilter).
			 * getFilters()); } else {
			 * productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter
			 * (productFilter).getFilters()); }
			 * productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter
			 * (productFilter).getFilters()); }
			 */
			productFilterResult = editDfrDao.getAllProductFilterResultByProduct(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			
			
			/**
			 * Json Logic
			 */
			
			
			if(productFilter.getApplications().equalsIgnoreCase("sbl")){
				
				for(SnapshotSiebelAssetDa sbl : sblDaList){
					
					String val ;
					if(sbl!=null){
						val  = ServiceUtil.getSblHeaderValue(productFilter.getHeader(),sbl);
						if(val.contains(productFilter.getSearchValue())){
							commonList.add(val);
						}
					}
				}
					
			}else if(productFilter.getApplications().equalsIgnoreCase("clx")){
				for(SnapshotClxAssetDa clx : clxDaList){
					
					String val ;
					if(clx!=null){
						val  = ServiceUtil.getClxHeaderValue(productFilter.getHeader(),clx);
						if(val.contains(productFilter.getSearchValue())){
							commonList.add(val);
						}
					}
					 
				}
				
			}else if(productFilter.getApplications().equalsIgnoreCase("sv")){
				for(SnapshotSvAssetDa sv : svDaList){
					
					String val ;
					if(sv!=null){
						val  = ServiceUtil.getSvHeaderValue(productFilter.getHeader(),sv);
						if(val.contains(productFilter.getSearchValue())){
							commonList.add(val);
						}
					}
					
				}
			}
			
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			
			logger.info("Attribute View processing end ");
			return commonList;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	}
	
	@Override
	public ProductDataGrid getProductPhysicalAuditAttributeView(ProductFilter productFilter) {
		try {
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			productFilterResult = editDfrDao.getProductFilterResult(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getPhysicalConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getPhysicalConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			dataGridJson.setTotalrows(productFilter.getTotalRows());
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					setPhysicalHeaders(configHeaderMap, clx, sv, sbl, productJson, assetNewValMap);
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					if(productAttrMap!= null){
					setPhysicalAttributes(clx, sv, sbl, productJson, productAttrMap, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
					if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorColl.forEach( errorObj -> {
							if (StringUtils.isNotEmpty(errorBuffer)) {
								errorBuffer.append(",");
							}
							errorBuffer.append(errorObj.getErrorCode());
						});
						if (StringUtils.isNotEmpty(errorBuffer)) {
							productJson.setDqmErrorCodes(errorBuffer.toString());
						}
					}
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;
		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	}
	
	
	
	/**
	 * @param configHeaderMap
	 * @param clx
	 * @param sv
	 * @param sbl
	 * @param productJson
	 * @param headerCounter
	 */
	private void setHeaders(final SortedMap<Integer, AttributeConfig> configHeaderMap, SnapshotClxAssetDa clx, SnapshotSvAssetDa sv,
			SnapshotSiebelAssetDa sbl, Product productJson, int headerCounter, Map<String,AssetNewVal> assetNewValMap) {
		AttributeConfig headerAttrConfig = configHeaderMap.get(headerCounter);
		
		if(headerAttrConfig!=null && isDisplay(headerAttrConfig)){
			Attribute attribute = new Attribute();
			attribute.setKey(headerAttrConfig.getHeaderPosition().toLowerCase().replace("_", ""));
			attribute.setName(headerAttrConfig.getAttrName());
			attribute.setDisplayName(headerAttrConfig.getDisplayName());
			attribute.setType("header");
			setAttributeDataType(headerAttrConfig, attribute);
			attribute.setSot(StringUtil.isEmpty(headerAttrConfig.getSot()) ? DartConstants.NA : headerAttrConfig.getSot() );
			AssetNewVal assetNewVal = null;
			if(StringUtils.isEmpty(headerAttrConfig.getEditable()))
				attribute.setEditable("N");
			else
				attribute.setEditable(headerAttrConfig.getEditable());
			attribute.setRunDependent(headerAttrConfig.getRunDependent());
			
			if(StringUtils.isNotBlank(headerAttrConfig.getPricingAttributeFlg()))
				attribute.setPricingFlg(headerAttrConfig.getEditable());
			else
				attribute.setPricingFlg("N");
			Values values = new Values();
			
			String val ="";
			
			if(sbl!=null){
				 val = ServiceUtil.getSblHeaderValue(headerCounter,sbl);
				 values.setSBL(val==null ? DartConstants.NA+"##"+sbl.getDfrLineId() : val+"##"+sbl.getDfrLineId());
				 attribute.setDfrlineid(sbl.getDfrLineId());
				 if(headerCounter ==60 && StringUtils.isNotEmpty(val) && val.equalsIgnoreCase("Y")){
					 attribute.setValidate("Y");
				 }else{
					 attribute.setValidate("N");
				 }
				 if(assetNewValMap.containsKey(sbl.getDfrLineId())){
					 assetNewVal = assetNewValMap.get(sbl.getDfrLineId());
				 }
				 
				 // assetNewVal = editDfrDao.getAssetNewValByDfrLineId(sbl.getDfrLineId());
			 }else{
				 values.setSBL(DartConstants.NA+"##");
				 attribute.setDfrlineid(DartConstants.NA);
			 }
			
			if(clx!=null){
				val = ServiceUtil.getClxHeaderValue(headerCounter,clx);
				values.setCLX(val==null ? DartConstants.NA+"##" +clx.getDfrLineId() : val +"##" +clx.getDfrLineId());
			}else{
				values.setCLX(DartConstants.NA+"##");
			}	

			if(sv!=null){
				val = ServiceUtil.getSvHeaderValue(headerCounter,sv);
				values.setSV(val==null ? DartConstants.NA+"##" + sv.getDfrLineId() : val + "##" + sv.getDfrLineId());
			}else{
				values.setSV(DartConstants.NA+"##");
			}
			attribute.setValues(values);
			
			if(assetNewVal!=null){
				  setAssetNewValPropertyValueInAttributeValue(attribute, assetNewVal);
			 }else{
				 values.setNewval("");
			 }
			
			productJson.getAttributes().add(attribute);
		}
	}
	
	
	
	/**
	 * @param clx
	 * @param sv
	 * @param sbl
	 * @param productJson
	 * @param productAttrMap
	 * @param attrCounter
	 */
	private void setAttributes(SnapshotClxAssetDa clx, SnapshotSvAssetDa sv, SnapshotSiebelAssetDa sbl, Product productJson,
			SortedMap<Integer, AttributeConfig> productAttrMap, int attrCounter, Map<String,AssetNewVal> assetNewValMap ) {
		AttributeConfig attrConfig = productAttrMap.get(attrCounter);
		 if(attrConfig!=null &&  isDisplay(attrConfig)){
			 Attribute attribute = new Attribute();
			 attribute.setKey(attrConfig.getHeaderPosition().toLowerCase().replace("_", ""));
			 attribute.setName(attrConfig.getAttrName());
			 attribute.setDisplayName(attrConfig.getDisplayName());
			 attribute.setType("attribute");
			 setAttributeDataType(attrConfig, attribute);
			 attribute.setSot(StringUtil.isEmpty(attrConfig.getSot()) ? DartConstants.NA : attrConfig.getSot() );
			 attribute.setRunDependent(attrConfig.getRunDependent());
			 if(StringUtils.isEmpty(attrConfig.getEditable()))
				attribute.setEditable("N");
			 else
				attribute.setEditable(attrConfig.getEditable());
			 
			 if(StringUtils.isNotBlank(attrConfig.getPricingAttributeFlg()))
					attribute.setPricingFlg(attrConfig.getEditable());
				else
					attribute.setPricingFlg("N");	
			 Values values = new Values();
			 String val;
			 AssetNewVal assetNewVal =null;
			 if(sbl!=null){
				 val = ServiceUtil.getSblAttrValue(attrCounter,sbl);
				 values.setSBL(val==null ? DartConstants.NA+"##"+sbl.getDfrLineId() : val+"##"+sbl.getDfrLineId());
				 attribute.setDfrlineid(sbl.getDfrLineId());
				 if(assetNewValMap.containsKey(sbl.getDfrLineId())){
					 assetNewVal= assetNewValMap.get(sbl.getDfrLineId());
				 }
				// assetNewVal = editDfrDao.getAssetNewValByDfrLineId(sbl.getDfrLineId());
					
					
			 }else{
				 values.setSBL(DartConstants.NA+"##");
				 attribute.setDfrlineid(DartConstants.NA);
			 }
			 if(clx!=null){
				 val = ServiceUtil.getClxAttrValue(attrCounter,clx);
				 values.setCLX(val==null ? DartConstants.NA+"##"+clx.getDfrLineId() : val+"##"+clx.getDfrLineId());
			 }else{
				 values.setCLX(DartConstants.NA+"##");
			 }
			 if(sv!=null){
				 val = ServiceUtil.getSvAttrValue(attrCounter,sv);
				 values.setSV(val==null ? DartConstants.NA+"##"+sv.getDfrLineId() : val+"##"+sv.getDfrLineId());
			 }else{
				 values.setSV(DartConstants.NA+"##");
			 }
			
			 attribute.setValues(values);
			if(assetNewVal!=null){
				  setAssetNewValPropertyValueInAttributeValue(attribute, assetNewVal);
			 }else{
				 values.setNewval("");
			 }
			 
			 productJson.getAttributes().add(attribute);
		 }
	}

	private void setAttributeDataType(AttributeConfig attrConfig, Attribute attribute) {
		Set<SrcSLstOfVal> lovEntities = attrConfig.getSrcSLstOfVals();
		 List<String> lov = null;
		 if(CollectionUtils.isNotEmpty(attrConfig.getSrcSLstOfVals())){
			 List<SrcSLstOfVal> lovList = new ArrayList<>(lovEntities);
			 boolean isOrderRequired = Boolean.FALSE;
			 for (SrcSLstOfVal sObj : lovList) {
				 if (sObj != null && sObj.getOrderBy() != null) {
					 isOrderRequired = Boolean.TRUE; 
				 }
			 }
			 if (isOrderRequired) {
				 lovList.sort(Comparator.comparing(SrcSLstOfVal::getOrderBy));
			 }
			 lov = new ArrayList<>();
			 lov.add("");
			 for(SrcSLstOfVal val : lovList){
				 if(StringUtils.isNotEmpty(val.getActiveFlg()) && val.getActiveFlg().equalsIgnoreCase("Y")) {
					 lov.add(val.getVal());
				 }
			 }
		 }
		 if(CollectionUtils.isNotEmpty(lov)){
			 attribute.setDataType("lov");
			 attribute.setLov(lov);
		 }else{
			 attribute.setDataType(attrConfig.getDataType());
			 attribute.setLov(null);
		 }
	}
	
	private boolean isDisplay(AttributeConfig attConfig){
		/*if(attConfig!=null){
			logger.info("Attribute name: " + attConfig.getAttrName());
			logger.info("Display flag: " + attConfig.getDisplayFlag());
		}*/
		if(attConfig!=null && attConfig.getDisplayFlag()!=null && attConfig.getDisplayFlag().equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public DfrMaster getDfrMasterById(String dfrId) {
		String fetchMode="join";
		return editDfrDao.getDfrMaster(dfrId,fetchMode);
	}
	
	private static final String PRODUCT_CAGE = "Cage";
	private static final String PRODUCT_CABINET = "Cabinet";
	private static final String PRODUCT_CABINET_DP = "Demarcation Point";

	@Override
	public HierarchyView getHierarchyView(ProductFilter productFilter) {
		List<HierarchyProduct> cageProduct = new ArrayList<HierarchyProduct>();
		List<HierarchyProduct> orphanProduct = new ArrayList<HierarchyProduct>();
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			/*if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
				productFilter.getFilters().addAll(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			} else {
				productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
			}*/
			productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
		}
		long start = System.currentTimeMillis();
		List<SnapshotSiebelAssetDa> sblList = editDfrDao.getHierarchyView(productFilter);
		long end = System.currentTimeMillis();
		 logger.info("hierarchy db call time  : "+ (end-start));
		 start = System.currentTimeMillis();
		Map<String,List<SnapshotSiebelAssetDa>> childAssetGroupedByRowId = sblList.stream()
				.filter(p -> (p.getHeader16() != null))
				.collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader1));
		Map<String, List<SnapshotSiebelAssetDa>> childAssets = sblList.stream()
				.filter(p -> (p.getHeader16() != null && p.getHeader27() != null))
				.collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader27));
		Map<String, Map<String, List<SnapshotSiebelAssetDa>>> parentAssets = sblList.stream()
				.filter(p -> (p.getHeader16() != null && p.getHeader27() == null)).collect(Collectors
						.groupingBy(SnapshotSiebelAssetDa::getHeader16, Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader20)));
		HierarchyView treeViewObj = new HierarchyView();
		childAssets.entrySet().stream().forEach(childAsset -> {
			if (!childAssetGroupedByRowId.containsKey(childAsset.getKey())) {
				childAsset.getValue().stream().forEach(assetDa -> {
					HierarchyProduct parentProduct = new HierarchyProduct();
					parentProduct.setKey(assetDa.getHeader20());
					parentProduct.setName(assetDa.getHeader16());
					parentProduct.setPRowId("");
					parentProduct.setIbx(assetDa.getHeader8());
					parentProduct.setAccNo(assetDa.getHeader6());
					parentProduct.setcRowId(assetDa.getHeader1());
					orphanProduct.add(parentProduct);
				});
			}
		});
		parentAssets.entrySet().stream().forEach(assetDataGroupedBySystemName -> {
			assetDataGroupedBySystemName.getValue().entrySet().stream().forEach(assetDataGroupedByName -> {
				HierarchyProduct parentProduct = new HierarchyProduct();
				parentProduct.setKey(assetDataGroupedByName.getKey());
				parentProduct.setName(assetDataGroupedBySystemName.getKey());
				parentProduct.setPRowId("");
				parentProduct.setIbx(assetDataGroupedByName.getValue().iterator().next().getHeader8());
				parentProduct.setAccNo(assetDataGroupedByName.getValue().iterator().next().getHeader6());
				parentProduct.setcRowId(assetDataGroupedByName.getValue().iterator().next().getHeader1());
				if (null != childAssets && !childAssets.isEmpty()) {
					assetDataGroupedByName.getValue().stream().forEach(level1Asset -> {
						if (childAssets.containsKey(level1Asset.getHeader1())) {
							List<SnapshotSiebelAssetDa> level2AssetList = childAssets.get(level1Asset.getHeader1());
							level2AssetList.stream().forEach(level2Asset -> {
								HierarchySubproduct level2Product = new HierarchySubproduct();
								level2Product.setKey(level2Asset.getHeader20());
								if (PRODUCT_CABINET.equalsIgnoreCase(level2Asset.getHeader20())) {
									level2Product.setName(level2Asset.getHeader14());
									level2Product.setIbx(level2Asset.getHeader8());
									level2Product.setAccNo(level2Asset.getHeader6());
									level2Product.setPRowId(level2Asset.getHeader27());
									level2Product.setcRowId(level2Asset.getHeader1());
								}else if (PRODUCT_CABINET_DP.equalsIgnoreCase(level2Asset.getHeader20())) {
									level2Product.setName(level2Asset.getHeader14());
									level2Product.setIbx(level2Asset.getHeader8());
									level2Product.setAccNo(level2Asset.getHeader6());
									level2Product.setPRowId(level2Asset.getHeader27());
									level2Product.setcRowId(level2Asset.getHeader1());
								} else {
									level2Product.setIbx(level2Asset.getHeader8());
									level2Product.setAccNo(level2Asset.getHeader6());
									level2Product.setName(level2Asset.getHeader3());
									level2Product.setPRowId(level2Asset.getHeader27());
									level2Product.setcRowId(level2Asset.getHeader1());

								}
								if (childAssets.containsKey(level2Asset.getHeader1())) {
									List<SnapshotSiebelAssetDa> level3AssetList = childAssets.get(level2Asset.getHeader1());
									level3AssetList.stream().forEach(level3Asset -> {
										HierarchySubproduct level3Product = new HierarchySubproduct();
										level3Product.setKey(level3Asset.getHeader20());
										level3Product.setName(level3Asset.getHeader3());
										level3Product.setIbx(level3Asset.getHeader8());
										level3Product.setAccNo(level3Asset.getHeader6());
										level3Product.setPRowId(level3Asset.getHeader27());
										level3Product.setcRowId(level3Asset.getHeader1());
										if (childAssets.containsKey(level3Asset.getHeader1())) {
											List<SnapshotSiebelAssetDa> level4AssetList = childAssets
													.get(level3Asset.getHeader1());
											level4AssetList.stream().forEach(level4Asset -> {
												HierarchySubproduct level4Product = new HierarchySubproduct();
												level4Product.setKey(level4Asset.getHeader20());
												level4Product.setName(level4Asset.getHeader3());
												level4Product.setIbx(level4Asset.getHeader8());
												level4Product.setAccNo(level4Asset.getHeader6());
												level4Product.setPRowId(level4Asset.getHeader27());
												level4Product.setcRowId(level4Asset.getHeader1());
												level3Product.getSubproducts().add(level4Product);
											});
										}
										level2Product.getSubproducts().add(level3Product);
									});
								}
								parentProduct.getSubproducts().add(level2Product);
							});
						}
					});
				}
				if (PRODUCT_CAGE.equalsIgnoreCase(parentProduct.getKey())) {
					cageProduct.add(parentProduct);
				} else {
					orphanProduct.add(parentProduct);
				}
			});
		});
		if (null != cageProduct && !cageProduct.isEmpty()) {
			treeViewObj.getProducts().addAll(cageProduct);
		}
		if (null != orphanProduct && !orphanProduct.isEmpty()) {
			treeViewObj.getOrphanProducts().addAll(orphanProduct);
		}
		end = System.currentTimeMillis();
		logger.info(" Hierarchy business logic time " + (end - start));
		return treeViewObj;
	}
	
	
	/*@Override
	public HierarchyView getHierarchyView(ProductFilter productFilter) {
		List<SnapshotSiebelAssetDa> sblList = editDfrDao.getHierarchyView(productFilter);
		Map<String, List<SnapshotSiebelAssetDa>> childAssets = sblList.stream()
				.filter(p -> (p.getHeader16() != null && p.getHeader27() != null))
				.collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader27));
		Map<String, Map<String, List<SnapshotSiebelAssetDa>>> parentAssets = sblList.stream()
				.filter(p -> (p.getHeader16() != null && p.getHeader27() == null)).collect(Collectors
						.groupingBy(SnapshotSiebelAssetDa::getHeader16, Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader20)));
		HierarchyView treeViewObj = new HierarchyView();
		List<HierarchyProduct> cageProduct = new ArrayList<HierarchyProduct>();
		List<HierarchyProduct> orphanProduct = new ArrayList<HierarchyProduct>();
		parentAssets.entrySet().stream().forEach(assetDataGroupedBySystemName -> {
			assetDataGroupedBySystemName.getValue().entrySet().stream().forEach(assetDataGroupedByName -> {
				HierarchyProduct parentProduct = new HierarchyProduct();
				parentProduct.setKey(assetDataGroupedByName.getKey());
				parentProduct.setName(assetDataGroupedBySystemName.getKey());
				parentProduct.setPRowId("");
				parentProduct.setIbx(assetDataGroupedByName.getValue().iterator().next().getHeader8());
				parentProduct.setAccNo(assetDataGroupedByName.getValue().iterator().next().getHeader6());
				parentProduct.setcRowId(assetDataGroupedByName.getValue().iterator().next().getHeader1());
				if (null != childAssets && !childAssets.isEmpty()) {
					assetDataGroupedByName.getValue().stream().forEach(level1Asset -> {
						if (childAssets.containsKey(level1Asset.getHeader1())) {
							List<SnapshotSiebelAssetDa> level2AssetList = childAssets.get(level1Asset.getHeader1());
							level2AssetList.stream().forEach(level2Asset -> {
								HierarchySubproduct level2Product = new HierarchySubproduct();
								level2Product.setKey(level2Asset.getHeader20());
								if (PRODUCT_CABINET.equalsIgnoreCase(level2Asset.getHeader20())) {
									level2Product.setName(level2Asset.getHeader14());
									level2Product.setIbx(level2Asset.getHeader8());
									level2Product.setAccNo(level2Asset.getHeader6());
									level2Product.setPRowId(level2Asset.getHeader27());
									level2Product.setcRowId(level2Asset.getHeader1());
								} else {
									level2Product.setIbx(level2Asset.getHeader8());
									level2Product.setAccNo(level2Asset.getHeader6());
									level2Product.setName(level2Asset.getHeader3());
									level2Product.setPRowId(level2Asset.getHeader27());
									level2Product.setcRowId(level2Asset.getHeader1());
								}
								if (childAssets.containsKey(level2Asset.getHeader1())) {
									List<SnapshotSiebelAssetDa> level3AssetList = childAssets.get(level2Asset.getHeader1());
									level3AssetList.stream().forEach(level3Asset -> {
										HierarchySubproduct level3Product = new HierarchySubproduct();
										level3Product.setKey(level3Asset.getHeader20());
										level3Product.setName(level3Asset.getHeader3());
										level3Product.setIbx(level3Asset.getHeader8());
										level3Product.setAccNo(level3Asset.getHeader6());
										level3Product.setPRowId(level3Asset.getHeader27());
										level3Product.setcRowId(level3Asset.getHeader1());
										if (childAssets.containsKey(level3Asset.getHeader1())) {
											List<SnapshotSiebelAssetDa> level4AssetList = childAssets
													.get(level3Asset.getHeader1());
											level4AssetList.stream().forEach(level4Asset -> {
												HierarchySubproduct level4Product = new HierarchySubproduct();
												level4Product.setKey(level4Asset.getHeader20());
												level4Product.setName(level4Asset.getHeader3());
												level4Product.setIbx(level4Asset.getHeader8());
												level4Product.setAccNo(level4Asset.getHeader6());
												level4Product.setPRowId(level4Asset.getHeader27());
												level4Product.setcRowId(level4Asset.getHeader1());
												level3Product.getSubproducts().add(level4Product);
											});
										}
										level2Product.getSubproducts().add(level3Product);
									});
								}
								parentProduct.getSubproducts().add(level2Product);
							});
						}
					});
				}
				if (PRODUCT_CAGE.equalsIgnoreCase(parentProduct.getKey())) {
					cageProduct.add(parentProduct);
				} else {


					orphanProduct.add(parentProduct);
				}
			});
		});
		if (null != cageProduct && !cageProduct.isEmpty()) {
			treeViewObj.getProducts().addAll(cageProduct);
		}
		if (null != orphanProduct && !orphanProduct.isEmpty()) {
			treeViewObj.getOrphanProducts().addAll(orphanProduct);
		}
		return treeViewObj;}*/

	
	@Override
	public String validateDependentAttributes(String product,String attrName,String dfrId,String cellType) throws Exception{
		try{
			AttributeConfig attributeConfig=new AttributeConfig();
			if("header".equalsIgnoreCase(cellType)){
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(cellType, attrName);
			}else{
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(product, attrName);
			}
			String dependentAttr=attributeConfig.getDependentAttribute();
			String editable=attributeConfig.getEditable();
			if(null!=dependentAttr&&null!=editable&&"Y".equalsIgnoreCase(dependentAttr)&&"Y".equalsIgnoreCase(editable)){
				List<DependentAttrUpdate> dependentAttrUpdates=appOpsDartAttrConfigService.getDependentAttrUpdate(attributeConfig.getAttributeFamily());
				for(int i = 0 ; i <dependentAttrUpdates.size() ; i++){
					if(dependentAttrUpdates.get(i).getExecutionOrder()==null){
						logger.info( "Dependent Attribute execution order null : "+ dependentAttrUpdates.get(i).toString());
						dependentAttrUpdates.remove(i);
					}
				}
				if(CollectionUtils.isNotEmpty(dependentAttrUpdates)){
					for(DependentAttrUpdate dependentAttrUpdate : dependentAttrUpdates){
						dependentAttrUpdate.setSql2(replacePlaceholders(dependentAttrUpdate.getSql2(),dfrId));
					}
					Collections.sort(dependentAttrUpdates, new CGAlphaNumComparator());
					for(DependentAttrUpdate dependentAttrUpdate : dependentAttrUpdates){
						appOpsDartAttrConfigService.runSqlQuery(dependentAttrUpdate.getSql2());
					}
				} else {
					logger.info("No dependentAttrUpdates found for : " + attributeConfig.getAttributeFamily());
				}
			}		

		}catch(Exception exception){
			logger.error("Error in validation Step1: "+exception.toString(),exception);
			throw exception; 
		}
		return "Success";
	}
	
	private String getAttributeFamily(String product,String attrName,String dfrId,String cellType) throws Exception{
		String attributeFamily="";
		try{
			AttributeConfig attributeConfig=new AttributeConfig();
			if("header".equalsIgnoreCase(cellType)){
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(cellType, attrName);
			}else{
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(product, attrName);
			}
			String dependentAttr=attributeConfig.getDependentAttribute();
			String editable=attributeConfig.getEditable();
			if(null!=dependentAttr&&null!=editable&&"Y".equalsIgnoreCase(dependentAttr)&&"Y".equalsIgnoreCase(editable)){
				attributeFamily=attributeConfig.getAttributeFamily();
			}		

		}catch(Exception exception){
			logger.error("Error in getAttributeFamily "+exception.toString(),exception);
			throw exception; 
		}
		return attributeFamily;
	}
	
	public HashMap<String,String> updateDfrDetail(DfrUpdateInput dfr) throws ParseException{

		DfrMaster dfrMaster = new DfrMaster();
		if(StringUtils.isNotEmpty(dfr.getDfrId())){
			dfrMaster.setDfrId(dfr.getDfrId());
		}
		if(StringUtils.isNotEmpty(dfr.getAssignedDt())){
			dfrMaster.setAssignedDt(DartConstants.DART_SDF.parse(dfr.getAssignedDt()));
		}
		return null;
	}
	
	@Override
	public String validate(ProductDataGrid dataGrid) throws Exception{
//		List<String> sqlList=new ArrayList<>();
		if(dataGrid.getDfrid()==null){
			throw new Exception("Dfr id received null in validate process");
		}else if(dataGrid.getProducts()==null){
			throw new Exception("Product list received null in validate process");
		}
		/*List<Product> rows= dataGrid.getProducts(); 
		String productName;
		String dfrId ;
		String sblRowId;
		String clxRowId;
		String newVal;
		String cellType;
		String loggerStr = "";
		if(CollectionUtils.isNotEmpty(rows)){
			dfrId = dataGrid.getDfrid();
			for(Product product:rows ){
				List<Attribute> attributeList = product.getAttributes();
				if(attributeList!=null){
					productName = product.getName();
					for(Attribute attr : attributeList){
						try{
							Values values = attr.getValues();
							sblRowId = values.getSBL().split("##").length>1 ? values.getSBL().split("##")[1]: "";
							clxRowId = values.getCLX().split("##").length>1 ? values.getCLX().split("##")[1]: "";
							newVal = StringUtils.isNotEmpty(values.getNewval())? values.getNewval() : "";
							cellType = attr.getType();
							loggerStr ="New Val=" + newVal + ",sblRowId = "+ sblRowId +",clxRowId =" + clxRowId +",cellType="+cellType+",dfrId="+dfrId ;
							if(null!=newVal&&""!=newVal.trim()&&!newVal.equalsIgnoreCase("?")){
								sqlList.addAll(getValidateSqls(productName, attr.getName(), dfrId, clxRowId, sblRowId, newVal, cellType));
							}
							
						}catch(Exception e){
							logger.info(loggerStr);
							logger.error("Error occured while validating attribute : ", e);
							throw e;
						}
					}
				}else{
					logger.info("attributeList found null for dfrid : " + dfrId);
					throw new Exception("attributeList found null for dfrid : " + dfrId);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(sqlList)){
			String[] sqlArray = new String[sqlList.size()];
			sqlArray=sqlList.toArray(sqlArray);
			appOpsDartAttrConfigService.runBatchUpdate(sqlArray);
		}*/
		DfrMaster dfrMaster = getDfrMasterById(dataGrid.getDfrid());
		ApprovalHistory approvalHistory = getApprovalHistory(dfrMaster, LogicConstants.DFR_VALIDATE,dfrMaster.getNotes());
		saveOrUpdateApprovalHistory(approvalHistory);
		
		return "Success";
	}
	
	public List<String> getValidateSqls(String product,String attrName,String dfrId,String clxRowId,String sblRowId,String newVal,String cellType) throws Exception{
		String sql="";
		String sbl ="";
		String clx = "";
		List<String> sqlList=new ArrayList<>();
		try{
			AttributeConfig attributeConfig=new AttributeConfig();
			if("header".equalsIgnoreCase(cellType)){
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(cellType, attrName);
			}else{
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(product, attrName);
			}
			 sbl=attributeConfig.getSiebel();
			 clx=attributeConfig.getClx();
			

			//if(null!=sbl && !newVal.equalsIgnoreCase(sblValue)){
				if(null!=sbl){
				String[] sblFix=sbl.split("\\.");
				if(2==sblFix.length){
					String table=sblFix[0];
					String column=sblFix[1];
					if(null!=sblRowId && !sblRowId.trim().equals("")){
						if(newVal.contains("'")){
							newVal = newVal.replace("\'", "\''");
						}
					sql="update eqx_dart."+table+" set "+column+"='"+newVal+"' where asset_id='"+sblRowId
							+"' and attr_name='"+attributeConfig.getAttrName()+"'";
					
					/*String assetXaSeq=editDfrDao.getAssetXaSeq();
					sql="MERGE INTO eqx_dart."+table+" USING dual ON ( asset_id='"+sblRowId+"' AND attr_name='"+
							attributeConfig.getAttrName()+"')"+
							" WHEN MATCHED THEN update set "+column+"='"+newVal+"' where asset_id='"+sblRowId+
							"' and attr_name='"+attributeConfig.getAttrName()+"'"+
							" WHEN NOT MATCHED THEN INSERT (ROW_ID,asset_id,attr_name,"+column+",LAST_UPD,LAST_UPD_BY,CREATED_BY,CREATED,"+
							"DFR_LINE_ID,REQUIRED_FLG,HIDDEN_FLG,CONFLICT_ID,MODIFICATION_NUM,READ_ONLY_FLG)"+
							" VALUES ( '"+assetXaSeq+"."+dfrId+"','"+sblRowId+"', '"+attributeConfig.getAttrName()+"','"+newVal+"',"+
							"sysdate,'DART','DART',sysdate,'"+sblRowId+"','N','N','0',0,'N')";*/
						sqlList.add(sql);
					
					}
				}
			}
			if(null!=clx && !"header".equalsIgnoreCase(product)){
				String pkId="";
				String[] clxFix=clx.split("\\.");
				switch (product.toLowerCase()) {
				case "cage":
					pkId = "cage_id";
					break;
				case "cabinet":
					pkId = "cabinet_id";
					break;
				}
				if(2==clxFix.length){
					String table=clxFix[0];
					String column=clxFix[1];
					if(newVal.contains("'")){
						newVal = newVal.replace("\'", "\''");
					}
					sql="update eqx_dart."+table+" set "+column+"='"+newVal+"' where "+pkId+"='"+clxRowId+"'";
					sqlList.add(sql);
				}
			}
		}catch(Exception exception){
			logger.info("SQL : " + sql);
			logger.info("attributeConfig.getSiebel() : " + sbl);
			logger.info("attributeConfig.getClx() : " + clx);
			logger.info("sblRowId : " + sblRowId);
			logger.info("clxRowId : " + clxRowId);
			logger.info("new val : " + newVal);
			logger.info("cellType : " + cellType);
			logger.info("attrName : " + attrName);
			logger.info("dfrid : " + dfrId);
			logger.error("Error in validation Step2 & 3: "+exception.toString());
			throw exception;
		}
		return sqlList;

	}
	
	public String validate(String product,String attrName,String dfrId,String clxRowId,String sblRowId,String newVal,String cellType) {
		String sql="";
		String sbl ="";
		String clx = "";
		
		try{
			AttributeConfig attributeConfig=new AttributeConfig();
			if("header".equalsIgnoreCase(cellType)){
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(cellType, attrName);
			}else{
				attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueByKey(product, attrName);
			}
			 sbl=attributeConfig.getSiebel();
			 clx=attributeConfig.getClx();
			

			if(null!=sbl){
				String[] sblFix=sbl.split("\\.");
				if(2==sblFix.length){
					String table=sblFix[0];
					String column=sblFix[1];
					//if(null!=sblRowId && !sblRowId.trim().equals("")){
					
					sql="update eqx_dart."+table+" set "+column+"='"+newVal+"' where asset_id='"+sblRowId
							+"' and attr_name='"+attributeConfig.getAttrName()+"'";
					
					/*String assetXaSeq=editDfrDao.getAssetXaSeq();
					sql="MERGE INTO eqx_dart."+table+" USING dual ON ( asset_id='"+sblRowId+"' AND attr_name='"+
							attributeConfig.getAttrName()+"')"+
							" WHEN MATCHED THEN update set "+column+"='"+newVal+"' where asset_id='"+sblRowId+
							"' and attr_name='"+attributeConfig.getAttrName()+"'"+
							" WHEN NOT MATCHED THEN INSERT (ROW_ID,asset_id,attr_name,"+column+",LAST_UPD,LAST_UPD_BY,CREATED_BY,CREATED,"+
							"DFR_LINE_ID,REQUIRED_FLG,HIDDEN_FLG,CONFLICT_ID,MODIFICATION_NUM,READ_ONLY_FLG)"+
							" VALUES ( '"+assetXaSeq+"."+dfrId+"','"+sblRowId+"', '"+attributeConfig.getAttrName()+"','"+newVal+"',"+
							"sysdate,'DART','DART',sysdate,'"+sblRowId+"','N','N','0',0,'N')";*/
					
					appOpsDartAttrConfigService.runSqlQuery(sql);
					//}
				}
			}
			if(null!=clx && !"header".equalsIgnoreCase(product)){
				String pkId="";
				String[] clxFix=clx.split("\\.");
				switch (product.toLowerCase()) {
				case "cage":
					pkId = "cage_id";
					break;
				case "cabinet":
					pkId = "cabinet_id";
					break;
				}
				if(2==clxFix.length){
					String table=clxFix[0];
					String column=clxFix[1];
					sql="update eqx_dart."+table+" set "+column+"='"+newVal+"' where "+pkId+"='"+clxRowId+"'";
					appOpsDartAttrConfigService.runSqlQuery(sql);
				}
			}
		}catch(Exception exception){
			logger.info("SQL : " + sql);
			logger.info("attributeConfig.getSiebel() : " + sbl);
			logger.info("attributeConfig.getClx() : " + clx);
			logger.info("sblRowId : " + sblRowId);
			logger.info("clxRowId : " + clxRowId);
			logger.info("new val : " + newVal);
			logger.info("cellType : " + cellType);
			logger.info("attrName : " + attrName);
			logger.info("dfrid : " + dfrId);
			logger.error("Error in validation Step2 & 3: "+exception.toString());
			throw exception;
		}
		return "Success";

	}
    
	
	@Override
	public String dqmValidation(String dfrId) throws Exception  {
		String shellResponse="";
		String fetchMode="select";
		try{
			String path = configService.getValueByKey("VALIDATATION_SCRIPT_PATH");
			if(!path.endsWith("/")){
				path = path+"/";
			}
			shellResponse=DartUtil.executeShellWithSUDO(configService.getValueByKey("VALIDATATION_SCRIPT_HOST"), 
					configService.getValueByKey("VALIDATATION_SCRIPT_USERNAME"),
					configService.getValueByKey("VALIDATATION_SCRIPT_PASSWORD"),
					new String[] { path+"scripts/exec_oracle_script_multiple.sh \"*.sql\" "+ path+"sqlmod "+configService.getValueByKey("VALIDATATION_DB")+" DART " + dfrId });
			logger.info("Shell response "+shellResponse);
			DfrMaster dfrMaster=editDfrDao.getDfrMaster(dfrId, fetchMode);
			dfrMaster.setValidStatus("Y");
			appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);
		}catch(Exception exception){
			logger.error("Error in validation Step2&3: "+exception.toString());
			throw exception;
		}
		return "Success";

	}

	
	private String replacePlaceholders(String sql,String dfr) {
		if(null!=sql && sql.contains("$#")){
			sql=sql.replaceAll("\r\n", " ").replaceAll("\n", " ");
			List<String> placeholders = Arrays.asList( sql.replaceAll("^.*?\\$\\#", "").split("\\#\\$.*?(\\$\\#|$)"));
			Map<String, String> placeholderMap=new HashMap<>();
			for(String placeholder : placeholders){
				if("DFR_ID".equalsIgnoreCase(placeholder)){
					placeholderMap.put(placeholder, dfr);
				}else if("DFR_LINE_ID".equalsIgnoreCase(placeholder)){
					placeholderMap.put(placeholder, dfr);
				}else{
					AttributeConfig attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueById(placeholder);
					if(null!=attributeConfig){
						placeholderMap.put(placeholder, attributeConfig.getHeaderPosition());
					}
				}
			}
			Set<String> keys = placeholderMap.keySet();
			if (null != keys) {
				for (Object object : keys) {

					if (sql.indexOf("$#" + (String) object
							+ "#$") != -1) {

						sql = sql.replace("$#"
								+ (String) object + "#$",
								(String) placeholderMap.get(object));
					}
				}
			}	
		}
		return sql;
	}
	
	
	@Override
	public void saveDependentAttributes(ProductDataGrid dataGrid) throws Exception{
		List<Product> rows= dataGrid.getProducts(); 
		if(CollectionUtils.isNotEmpty(rows)){
			Set<String> attrFamilies=new HashSet<>();
			for(Product product:rows ){
				List<Attribute> attributeList = product.getAttributes();
				for(Attribute attr : attributeList){
					try{
						if(attr.getValues()!=null && attr.getValues().getNewval()!=null && ""!=attr.getValues().getNewval().trim() 
								&& !attr.getValues().getNewval().equalsIgnoreCase("?") && product.getDfrlineid().contains("-")
								&& !attr.getValues().getNewval().equalsIgnoreCase(attr.getValues().getSBL().split("##")[0])){
							String attributeFamily=getAttributeFamily(product.getName(),attr.getName(),dataGrid.getDfrid(),attr.getType());
							if(null!=attributeFamily && !"".equalsIgnoreCase(attributeFamily)){
								attrFamilies.add(attributeFamily);
							}
						}else if(attr.getValues()!=null && attr.getValues().getNewval()!=null && ""!=attr.getValues().getNewval().trim() 
								&& !attr.getValues().getNewval().equalsIgnoreCase("?") && !product.getDfrlineid().contains("-")){
							String attributeFamily=getAttributeFamily(product.getName(),attr.getName(),dataGrid.getDfrid(),attr.getType());
							if(null!=attributeFamily && !"".equalsIgnoreCase(attributeFamily)){
								attrFamilies.add(attributeFamily);
							}
						}else{
							if(attr.getValues()==null)
								logger.info(attr.getName()+ " attr.getValues() is null so by passing validateDependentAttributes ");
							else if(attr.getValues().getNewval()==null)
								logger.info(attr.getName()+ "attr.getValues().getNewval() is null so by passing validateDependentAttributes");
							else 
								logger.info(attr.getName()+ " new val received '?' or empty so by passing validateDependentAttributes");
						}
						
						if(StringUtils.isNotEmpty(attr.getRunDependent()) 
								&& attr.getRunDependent().equalsIgnoreCase("y") 
								&& attr.getValues()!=null 
								&& attr.getValues().getNewval()!=null 
								&& ""!=attr.getValues().getNewval().trim() 
								&& !attr.getValues().getNewval().equalsIgnoreCase("?") 	
						 ){
							String attributeFamily=getAttributeFamily(product.getName(),attr.getName(),dataGrid.getDfrid(),attr.getType());
							logger.info("Attribute Family: "+ attributeFamily  + ", run dependent for attrName: " + attr.getName() + ", dfrLineId: " + product.getDfrlineid());
							if(StringUtils.isNotEmpty(attributeFamily)){
								attrFamilies.add(attributeFamily);
							}
						}
						logger.info("Attribute Famlies for :  " + dataGrid.getDfrid() + "," + attrFamilies );
					}catch(Exception e){
						logger.info("Error occured while validating dependent attribute : "  + product.getName() + "/" + attr.getName()+"/"+dataGrid.getDfrid()+"/"+attr.getDataType(), e);
						throw e;

					}
				}

			}
			runDependentAttributes(attrFamilies,dataGrid.getDfrid());
		}
	}
	
	@Override
	public void autoSaveDependentAttributes(SaveAssetForm dataGrid) throws Exception {
		Product product = dataGrid.getProducts();
		Set<String> attrFamilies = new HashSet<>();
		if (product != null) {
			
			List<Attribute> attributeList = product.getAttributes();
			for (Attribute attr : attributeList) {
				try {
					if (attr.getValues() != null && attr.getValues().getNewval() != null
							&& "" != attr.getValues().getNewval().trim()
							&& !attr.getValues().getNewval().equalsIgnoreCase("?")
							&& product.getDfrlineid().contains("-") && !attr.getValues().getNewval()
									.equalsIgnoreCase(attr.getValues().getSBL().split("##")[0])) {
						String attributeFamily = getAttributeFamily(product.getName(), attr.getName(),
								dataGrid.getDfrId(), attr.getType());
						if (null != attributeFamily && !"".equalsIgnoreCase(attributeFamily)) {
							attrFamilies.add(attributeFamily);
						}
					} else if (attr.getValues() != null && attr.getValues().getNewval() != null
							&& "" != attr.getValues().getNewval().trim()
							&& !attr.getValues().getNewval().equalsIgnoreCase("?")
							&& !product.getDfrlineid().contains("-")) {
						String attributeFamily = getAttributeFamily(product.getName(), attr.getName(),
								dataGrid.getDfrId(), attr.getType());
						if (null != attributeFamily && !"".equalsIgnoreCase(attributeFamily)) {
							attrFamilies.add(attributeFamily);
						}
					} else {
						if (attr.getValues() == null)
							logger.info(attr.getName()
									+ " attr.getValues() is null so by passing validateDependentAttributes ");
						else if (attr.getValues().getNewval() == null)
							logger.info(attr.getName()
									+ "attr.getValues().getNewval() is null so by passing validateDependentAttributes");
						else
							logger.info(attr.getName()
									+ " new val received '?' or empty so by passing validateDependentAttributes");
					}

					if (StringUtils.isNotEmpty(attr.getRunDependent()) && attr.getRunDependent().equalsIgnoreCase("y")
							&& attr.getValues() != null && attr.getValues().getNewval() != null
							&& "" != attr.getValues().getNewval().trim()
							&& !attr.getValues().getNewval().equalsIgnoreCase("?")) {
						String attributeFamily = getAttributeFamily(product.getName(), attr.getName(),
								dataGrid.getDfrId(), attr.getType());
						logger.info("Attribute Family: " + attributeFamily + ", run dependent for attrName: "
								+ attr.getName() + ", dfrLineId: " + product.getDfrlineid());
						if (StringUtils.isNotEmpty(attributeFamily)) {
							attrFamilies.add(attributeFamily);
						}
					}
					logger.info("Attribute Famlies for :  " + dataGrid.getDfrId() + "," + attrFamilies);
				} catch (Exception e) {
					logger.info("Error occured while validating dependent attribute : " + product.getName() + "/"
							+ attr.getName() + "/" + dataGrid.getDfrId() + "/" + attr.getDataType(), e);
					throw e;

				}
			}
		}
		
		runDependentAttributes(attrFamilies, dataGrid.getDfrId(),dataGrid.getDfrLineId());
	}
	
	
	
	private void runDependentAttributes(Set<String> attrFamilySet,String dfrId){
		try{
			List<String> attrFamilies=new ArrayList<>(attrFamilySet);
			Collections.sort(attrFamilies);
			attrFamilies.forEach((attrFamily) -> {
				logger.info("Family "+attrFamily);
				List<DependentAttrUpdate> dependentAttrUpdates=appOpsDartAttrConfigService.getDependentAttrUpdate(attrFamily);
				for(int i = 0 ; i <dependentAttrUpdates.size() ; i++){
					if(dependentAttrUpdates.get(i).getExecutionOrder()==null){
						logger.info( "Dependent Attribute execution order null : "+ dependentAttrUpdates.get(i).toString());
						dependentAttrUpdates.remove(i);
					}
				}
				if(CollectionUtils.isNotEmpty(dependentAttrUpdates)){
					for(DependentAttrUpdate dependentAttrUpdate : dependentAttrUpdates){
						dependentAttrUpdate.setSql1(replacePlaceholders(dependentAttrUpdate.getSql1(),dfrId));
					}
					Collections.sort(dependentAttrUpdates, new CGAlphaNumComparator());

					/*Map<String,List<DependentAttrUpdate>> dependentAttrUpdateBySeq = dependentAttrUpdates.stream()
							.collect(Collectors.groupingBy(DependentAttrUpdate::getExecutionOrder));
					dependentAttrUpdateBySeq.forEach((seq,dependentAttrUpdateList) -> {
						dependentAttrUpdateList.parallelStream().forEach((dependentAttrUpdate) -> {
							Instant start = Instant.now();
							appOpsDartAttrConfigService.runSqlQuery(dependentAttrUpdate.getSql());
							Instant finish = Instant.now();
							long timeElapsed = Duration.between(start, finish).toMillis();
							logger.info("Family "+attrFamily+" DFR:"+dfrId+" SQL:"+dependentAttrUpdate.getSql()+"Time taken "+timeElapsed+"ms");
						});
					});*/

					dependentAttrUpdates.forEach((dependentAttrUpdate) -> {
						Instant start = Instant.now();
						appOpsDartAttrConfigService.runSqlQuery(dependentAttrUpdate.getSql1());
					    Instant finish = Instant.now();
					    long timeElapsed = Duration.between(start, finish).toMillis();
						logger.info("Family "+attrFamily+" DFR:"+dfrId+" SQL:"+dependentAttrUpdate.getSql1()+"Time taken "+timeElapsed+"ms");						
					});
				} else {
					logger.info("No dependentAttrUpdates found for : " + attrFamily);
				} 
			});
		}catch(Exception exception){
			logger.error("Error in validation Step1: "+exception.toString(),exception);
			throw exception; 
		}
	}
	
	private void runDependentAttributes(Set<String> attrFamilySet,String dfrId,String dfrLineId){
		try{
			List<String> attrFamilies=new ArrayList<>(attrFamilySet);
			Collections.sort(attrFamilies);
			attrFamilies.forEach((attrFamily) -> {
				logger.info("Family "+attrFamily);
				List<DependentAttrUpdate> dependentAttrUpdates=appOpsDartAttrConfigService.getAutoDependentAttrUpdate(attrFamily);
//				logger.info("dependentAttrUpdates List : " + dependentAttrUpdates.toString() );
				/*for(int i = 0 ; i <dependentAttrUpdates.size() ; i++){
					if(dependentAttrUpdates.get(i).getExecutionOrder()==null){
						logger.info( "Dependent Attribute execution order null : "+ dependentAttrUpdates.get(i).toString());
						dependentAttrUpdates.remove(i);
					}
				}*/
				if(CollectionUtils.isNotEmpty(dependentAttrUpdates)){
					for(DependentAttrUpdate dependentAttrUpdate : dependentAttrUpdates){
						dependentAttrUpdate.setSql2(replacePlaceholders(dependentAttrUpdate.getSql2(),dfrId,dfrLineId));
					}
					logger.info("dependentAttrUpdates List before sort : " + dependentAttrUpdates.toString() );
					Collections.sort(dependentAttrUpdates, new CGAlphaNumComparator());
//					logger.info("dependentAttrUpdates List after sort: " + dependentAttrUpdates.toString() );
					/*Map<String,List<DependentAttrUpdate>> dependentAttrUpdateBySeq = dependentAttrUpdates.stream()
							.collect(Collectors.groupingBy(DependentAttrUpdate::getExecutionOrder));
					dependentAttrUpdateBySeq.forEach((seq,dependentAttrUpdateList) -> {
						dependentAttrUpdateList.parallelStream().forEach((dependentAttrUpdate) -> {
							Instant start = Instant.now();
							appOpsDartAttrConfigService.runSqlQuery(dependentAttrUpdate.getSql());
							Instant finish = Instant.now();
							long timeElapsed = Duration.between(start, finish).toMillis();
							logger.info("Family "+attrFamily+" DFR:"+dfrId+" SQL:"+dependentAttrUpdate.getSql()+"Time taken "+timeElapsed+"ms");
						});
					});*/

					dependentAttrUpdates.forEach((dependentAttrUpdate) -> {
						Instant start = Instant.now();
						appOpsDartAttrConfigService.runSqlQuery(dependentAttrUpdate.getSql2());
					    Instant finish = Instant.now();
					    long timeElapsed = Duration.between(start, finish).toMillis();
						logger.info("Family "+attrFamily+" DFR:"+dfrId+" SQL:"+dependentAttrUpdate.getSql2()+"Time taken "+timeElapsed+"ms");						
					});
				} else {
					logger.info("No dependentAttrUpdates found for : " + attrFamily);
				} 
			});
		}catch(Exception exception){
			logger.error("Error in validation Step1: "+exception.toString(),exception);
			throw exception; 
		}
	}
	
	private String replacePlaceholders(String sql2, String dfrId, String dfrLineId) {

		if (null != sql2 && sql2.contains("$#")) {
			sql2 = sql2.replaceAll("\r\n", " ").replaceAll("\n", " ");
			logger.info("sql query before replace : "+ sql2);
			Set<String> placeholders = new HashSet<>(Arrays.asList(sql2.replaceAll("^.*?\\$\\#", "").split("\\#\\$.*?(\\$\\#|$)")));
			
			Map<String, String> placeholderMap = new HashMap<>();
			for (String placeholder : placeholders) {
				if ("DFR_ID".equalsIgnoreCase(placeholder)) {
					placeholderMap.put(placeholder, dfrId);
				} else if ("DFR_LINE_ID".equalsIgnoreCase(placeholder)) {
					placeholderMap.put(placeholder, dfrLineId);
				} else {
					AttributeConfig attributeConfig = appOpsDartAttrConfigService.getAttrConfigVlaueById(placeholder);
					if (null != attributeConfig) {
						placeholderMap.put(placeholder, attributeConfig.getHeaderPosition());
					}
				}
			}
			Set<String> keys = placeholderMap.keySet();
			if (null != keys) {
				for (Object object : keys) {

					if (sql2.indexOf("$#" + (String) object + "#$") != -1) {

						sql2 = sql2.replace("$#" + (String) object + "#$", (String) placeholderMap.get(object));
					}
				}
			}
		}
		return sql2;
	}

	@Override
	public void saveNewAssetValues(ProductDataGrid dataGrid) throws Exception {
		long count = 0;
		List<Product> rows = dataGrid.getProducts();
		if (CollectionUtils.isNotEmpty(rows)) {
//			List<ChangeSummary> changeSummaryList = new ArrayList<>();
			for (Product product : rows) {
				List<Attribute> attributeList = product.getAttributes();
				AssetNewVal assetNewVal = editDfrDao.getAssetNewValByDfrLineId(product.getDfrlineid());
				//SnapshotSiebelAssetDa snapshotSiebelAssetDa=editDfrDao.getSnapshotSblAssetDaData(product.getDfrlineid());
				if (assetNewVal != null) {
					for (Attribute attr : attributeList) {
						count += setAssetNewValProperty(attr, assetNewVal);
					
					}
					appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
				} else {
					logger.info("AssetNewVal found null for dfrlineid : " + product.getDfrlineid());
					throw new Exception("AssetNewVal found null for dfrlineid : " + product.getDfrlineid());
				}
			}
			dataGrid.setTotalrows(count);
			
			DfrMaster dfrMaster = getDfrMasterById(dataGrid.getDfrid());
			ApprovalHistory approvalHistory = getApprovalHistory(dfrMaster, LogicConstants.DFR_SAVE,dfrMaster.getNotes());
			saveOrUpdateApprovalHistory(approvalHistory);														
		}
	}
	
	@Override
	public void autoSaveNewAssetValues(SaveAssetForm formData) throws Exception {
		long count = 0;
		Product product = formData.getProducts();
		if (product != null) {
				List<Attribute> attributeList = product.getAttributes();
				AssetNewVal assetNewVal = editDfrDao.getAssetNewValByDfrLineId(product.getDfrlineid());
				//SnapshotSiebelAssetDa snapshotSiebelAssetDa=editDfrDao.getSnapshotSblAssetDaData(product.getDfrlineid());
				if (assetNewVal != null) {
					for (Attribute attr : attributeList) {
						count += setAssetNewValProperty(attr, assetNewVal);
					}
					assetNewVal.setAttr350("Y");
					appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
				} else {
					logger.info("AssetNewVal found null for dfrlineid : " + product.getDfrlineid());
					throw new Exception("AssetNewVal found null for dfrlineid : " + product.getDfrlineid());
				}
			}
			formData.setTotalrows(count);
			
			DfrMaster dfrMaster = getDfrMasterById(formData.getDfrId());
			ApprovalHistory approvalHistory = getApprovalHistory(dfrMaster, LogicConstants.DFR_SAVE,dfrMaster.getNotes());
			saveOrUpdateApprovalHistory(approvalHistory);														
		}
		
	private long setAssetNewValProperty(Attribute attribute ,AssetNewVal assetNewVal) throws Exception{
		long count = 0;
		String propname = attribute.getKey();
		Values value = attribute.getValues();
		String newValue = value.getNewval();
		if(StringUtils.isNotEmpty(newValue)){
	   		count = 1;
			propname = ServiceUtil.getHeaderMappedAttrEntityProperty(propname);
			String setMethodName = "set"+StringUtils.capitalize(propname);
			Statement stmt = null;
			Object o = assetNewVal;
			if(DartConstants.DATE_TYPE_PROP_LIST.contains(propname)){
				// date type column already saved by creating empty entries in asset new val in initiate dfr process
				 stmt = new Statement(o, setMethodName, new Object[] { new Date() });
			}else{
				 stmt = new Statement(o, setMethodName, new Object[] { newValue });
				 stmt.execute();
			}
			assetNewVal.setAttr350("Y");
		}
	   	return count;
	}
	
	private String setAssetNewValPropertyValueInAttributeValue(Attribute attribute, AssetNewVal assetNewVal){
		String propname = attribute.getKey();
		
		propname = ServiceUtil.getHeaderMappedAttrEntityProperty(propname);
		
		String getMethodName = "get"+StringUtils.capitalize(propname);
		Expression expr = new Expression(assetNewVal, getMethodName, new Object[0]);
		if(DartConstants.DATE_TYPE_PROP_LIST.contains(propname)){
			attribute.getValues().setNewval("");
		}else {
			try{
				expr.execute();
				if(expr.getValue()!=null){
					attribute.getValues().setNewval((String)expr.getValue());
				}else{
					attribute.getValues().setNewval("");
				}

			}catch(Exception e){
				logger.error("Error in parsing asset new val value for dfrline id /property  " + assetNewVal.getDfrLineId() + "/" + propname );
			}
		}
		return null;
		
	}

	@Override
	public String editHierarchy(String dfrLineID, String parentDfrLineID, String product) {
		SnapshotSiebelAssetDa snapshotSiebelAssetDa=editDfrDao.getSnapshotSblAssetDaData(dfrLineID);
		AssetNewVal assetNewVal=editDfrDao.getAssetNewVal(dfrLineID);
		snapshotSiebelAssetDa.setHeader27(parentDfrLineID);
		snapshotSiebelAssetDa.setHeader57("Y");
		assetNewVal.setHeader27(parentDfrLineID);
		assetNewVal.setHeader57("Y");
		appOpsInitDartDaDao.saveOrUpdateSnapshotSiebelAssetDa(snapshotSiebelAssetDa);
		appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
		//Change Summary changes;start
		Attribute attrObj = new Attribute();
		attrObj.setName("X OPS PAR ASSET NUM");
		Values values = new Values();
		values.setNewval(parentDfrLineID);
		attrObj.setValues(values);
		List<ChangeSummaryDTO> changeSummaryDtoList = editDfrDao.getChangeSummaryDTO(dfrLineID);
		if (changeSummaryDtoList != null && CollectionUtils.isNotEmpty(changeSummaryDtoList)) {
			ChangeSummaryDTO changeSummaryDto = changeSummaryDtoList.get(0);
			if (changeSummaryDto != null) {
				changeSummaryDto.setDfrLineId(dfrLineID);
				ChangeSummary changeSummary = createChangeSummary(attrObj, changeSummaryDto);
				editDfrDao.saveOrUpdateChangeSummary(changeSummary);
			}
		}
		return "success";
	}

	@Override
	public String validateHierarchy(String dfrLineID, String product) {
		try{
			String attrFamily=product+"-OPS";
			List<DependentAttrUpdate> dependentAttrUpdates=appOpsDartAttrConfigService.getDependentAttrUpdate(attrFamily);
			for(int i = 0 ; i <dependentAttrUpdates.size() ; i++){
				if(dependentAttrUpdates.get(i).getExecutionOrder()==null){
					logger.info( "Dependent Attribute execution order null : "+ dependentAttrUpdates.get(i).toString());
					dependentAttrUpdates.remove(i);
				}
			}
			if(CollectionUtils.isNotEmpty(dependentAttrUpdates)){
				for(DependentAttrUpdate dependentAttrUpdate : dependentAttrUpdates){
					dependentAttrUpdate.setSql2(replacePlaceholders(dependentAttrUpdate.getSql2(),dfrLineID));
				}
				Collections.sort(dependentAttrUpdates, new CGAlphaNumComparator());
				for(DependentAttrUpdate dependentAttrUpdate : dependentAttrUpdates){
					appOpsDartAttrConfigService.runSqlQuery(dependentAttrUpdate.getSql2());
				}
			} else {
				logger.info("No dependentAttrUpdates found for : " + attrFamily);
			}	

		}catch(Exception exception){
			logger.error("Error in validation Step1: "+exception.toString(),exception);
			return "Error in SQL execution";
		}
		return "Success";
	}
	@Override
	public String saveDfrDetails(String dfrId,String fieldName, String fieldValue) {
		String fetchMode="select";
		DfrMaster dfrMaster=editDfrDao.getDfrMaster(dfrId,fetchMode);
		switch (fieldName) {
		case "priority":
			dfrMaster.setPriority(fieldValue);
			break;
		case "region":
			dfrMaster.setRegion(fieldValue);
			break;
		case "notes":
			dfrMaster.setNotes(fieldValue);
			break;
		case "incident":
			dfrMaster.setIncident(fieldValue);
			break;
		case "asyncVal":
			dfrMaster.setAsyncVal(fieldValue);
			break;
		case "assignedTeam":
			dfrMaster.setAssignedTeam(fieldValue);
			if(null!=dfrMaster.getPhysicalAudit()&&"y".equalsIgnoreCase(dfrMaster.getPhysicalAudit())){
				String editDfrLink=configService.getValueByKey("PA_LINK");
				String recepientList =getEmailIdByAssignGroup(dfrMaster.getAssignedTeam());
				String templateKey="DART_PA_NOTIFICATION";
				HashMap<String, String> dataMap = new HashMap<String, String>();					
				dataMap.put("USER_ID", dfrMaster.getAssignedTeam());
				dataMap.put("REQUESTED_ITEM", null==dfrMaster.getCreatedTeam()?"":dfrMaster.getCreatedTeam());
				dataMap.put("DFR", dfrMaster.getDfrId());
				dataMap.put("REQUESTED_BY", null==dfrMaster.getCreatedBy()?"":dfrMaster.getCreatedBy());
				dataMap.put("INCIDENT", null==dfrMaster.getIncident()?"":dfrMaster.getIncident());
				dataMap.put("REGION", dfrMaster.getRegion());
				dataMap.put("NOTES", null==dfrMaster.getNotes()?"":dfrMaster.getNotes());
				dataMap.put("LINK", null==editDfrLink?"":"<a href=\""+editDfrLink+dfrMaster.getDfrId()+"\">click here</a>");
				dataMap.put("STATUS", dfrMaster.getStatus());
				emailSenderService.sendAlert(recepientList,templateKey,dataMap);
			}
			break;
			
		}
		appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);
		return "Success";
	}

	@Override
	public String cancelDfr(String dfrId) {
		String fetchMode="select";
		DfrMaster dfrMaster=editDfrDao.getDfrMaster(dfrId,fetchMode);
		dfrMaster.setStatus("Cancelled");
		appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);
		ApprovalHistory approvalHistory = getApprovalHistory(dfrMaster, LogicConstants.DFR_CANCEL,dfrMaster.getNotes());
		saveOrUpdateApprovalHistory(approvalHistory);
		return "Success";
	}
	
	@Override
	public boolean isDFRFileExists(String dfrID) {
		if(dfrID!=null && appOpsInitDartDaDao.getDFRFileByDFRId(dfrID)!=null)
		{
			return true;
		}
		return false;
	}
	
	
	@Override
	public String uploadDfrFile(DFRFileModel dfrFile) {
		if(dfrFile!=null && appOpsInitDartDaDao.getDFRFileByDFRId(dfrFile.getDfrId())!=null)
		{
			return "File already Uploaded for DFR "+dfrFile.getDfrId();
		}
		DFRFile dfFile=new DFRFile();
		Random rand=new Random();
		long dfrFileID=rand.nextInt(1000);
		dfFile.setFileName(dfrFile.getFileName());
		dfFile.setFileType(dfrFile.getFileType());
		dfFile.setDfrId(dfrFile.getDfrId());
		dfFile.setDfrFile(dfrFile.getDfrFile());
		dfFile.setDfrFileId(dfrFileID);
		logger.info("Saving to DB "+dfFile);
		appOpsInitDartDaDao.saveOrUpdateDfrFile(dfFile);
		return "Success";
	}
	
	@Override
	public String deleteDfrFile(String dfrID) {
		DFRFile dfrFile=null;
		if(dfrID!=null)
		{
			dfrFile=appOpsInitDartDaDao.getDFRFileByDFRId(dfrID);
			logger.info("Got DFr File "+dfrFile);
			if(dfrFile!=null)
			{
				appOpsInitDartDaDao.deleteDfrFile(dfrFile);	
			}
			else
			{
				return "No File exists for this DFR";
			}
			
		}
		return "Success";
	}
	
	

	@Override
	public DFRFile downloadDfrFile(String dfrId) {
		DFRFile dfrFile=null;
		if(dfrId!=null)
		{
			dfrFile=appOpsInitDartDaDao.getDFRFileByDFRId(dfrId);
			logger.info("Got DFr File "+dfrFile);
		}
		return dfrFile;
	}

	@Override
	public String getWorkflowDetails(String dfrId) {
		String fetchMode="join";
		JsonObject workflowObject = new JsonObject();
		JsonArray stepArray = new JsonArray();
		DfrMaster dfrMaster= new DfrMaster();
		dfrMaster=editDfrDao.getDfrMaster(dfrId,fetchMode);
		if(!"New".equalsIgnoreCase(dfrMaster.getStatus())&&null!=dfrMaster.getApprovalHistories()&&!dfrMaster.getApprovalHistories().isEmpty()){
			Collections.sort(dfrMaster.getApprovalHistories(), new CGAlphaNumComparator());
			List<ApprovalHistory> approvalHistories = dfrMaster.getApprovalHistories().stream().filter(i -> 
			!(i.getStatus().equalsIgnoreCase(LogicConstants.DFR_SAVE) || i.getStatus().equalsIgnoreCase(LogicConstants.DFR_VALIDATE)
					|| i.getStatus().equalsIgnoreCase(LogicConstants.DFR_CANCEL) || i.getStatus().equalsIgnoreCase(LogicConstants.SYNCINPROGRESS) 
					|| i.getStatus().equalsIgnoreCase(LogicConstants.SYNCERROR) || i.getStatus().equalsIgnoreCase(LogicConstants.SYNCCOMPLETED)
					|| i.getStatus().equalsIgnoreCase(LogicConstants.PHYSICAL_AUDIT_INTIATE)))
					.collect(Collectors.toList());
			for(ApprovalHistory approvalHistory : approvalHistories){
				JsonObject stepObject = new JsonObject();
				if("New".equalsIgnoreCase(approvalHistory.getStatus())){
					stepObject.addProperty("label", "Initiate DFR");
					stepObject.addProperty("fillcolor", "solid");
					stepObject.addProperty("tooltip", "DFR Initiated by "+approvalHistory.getCreatedTeam());
				}else{
					stepObject.addProperty("label", approvalHistory.getAssignedTeam());
					stepObject.addProperty("tooltip", approvalHistory.getStatus());
				}
				stepObject.addProperty("fillcolor", "solid");
				stepArray.add(stepObject);
			}
		}
		if(null!=dfrMaster.getStatus()){
			JsonObject finalStepObject = new JsonObject();
			JsonObject stepObject = new JsonObject();
			switch (dfrMaster.getStatus().toLowerCase()) {
			case "new":
				stepObject.addProperty("label", "Initiate DFR");
				stepObject.addProperty("tooltip", "DFR Initiated by "+dfrMaster.getCreatedTeam());
				stepObject.addProperty("fillcolor", "solid");
				/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
				stepArray.add(stepObject);
				finalStepObject.addProperty("label", "Completed");
				finalStepObject.addProperty("tooltip", "");
				finalStepObject.addProperty("fillcolor", "empty");
				break;
			case "cancelled":
				if(dfrMaster.getApprovalHistories().isEmpty()){
					stepObject.addProperty("label", "Initiate DFR");
					stepObject.addProperty("tooltip", "DFR Initiated by "+dfrMaster.getCreatedTeam());
					stepObject.addProperty("fillcolor", "solid");
					/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
					stepArray.add(stepObject);
				}
				finalStepObject.addProperty("label", "Cancelled");
				finalStepObject.addProperty("tooltip", "DFR Cancelled");
				finalStepObject.addProperty("fillcolor", "solid");
				break;
			case "sync in progress":
				if(dfrMaster.getApprovalHistories().isEmpty()){
					stepObject.addProperty("label", "Initiate DFR");
					stepObject.addProperty("tooltip", "DFR Initiated by "+dfrMaster.getCreatedTeam());
					stepObject.addProperty("fillcolor", "solid");
					/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
					stepArray.add(stepObject);
				}
				finalStepObject.addProperty("label", "Sync In Progress");
				finalStepObject.addProperty("tooltip", "CLX Status:"+dfrMaster.getClxIntStatus()+
						",  SBL Status:"+dfrMaster.getSblIntStatus()+
						",  SV Status:"+dfrMaster.getSvIntStatus());
				finalStepObject.addProperty("fillcolor", "empty");
				break;
			case "sync error":
				if(dfrMaster.getApprovalHistories().isEmpty()){
					stepObject.addProperty("label", "Initiate DFR");
					stepObject.addProperty("tooltip", "DFR Initiated by "+dfrMaster.getCreatedTeam());
					stepObject.addProperty("fillcolor", "solid");
					/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
					stepArray.add(stepObject);
				}
				finalStepObject.addProperty("label", "Sync Error");
				finalStepObject.addProperty("tooltip", /*dfrMaster.getStatus()+" ## */"CLX Status:"+dfrMaster.getClxIntStatus()+
						",  SBL Status:"+dfrMaster.getSblIntStatus()+
						",  SV Status:"+dfrMaster.getSvIntStatus());
				finalStepObject.addProperty("fillcolor", "empty");
				break;
			case "completed":
				if(dfrMaster.getApprovalHistories().isEmpty()){
					stepObject.addProperty("label", "Initiate DFR");
					stepObject.addProperty("tooltip", "DFR Initiated by "+dfrMaster.getCreatedTeam());
					stepObject.addProperty("fillcolor", "solid");
					/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
					stepArray.add(stepObject);
				}
				finalStepObject.addProperty("label", "Completed");
				finalStepObject.addProperty("tooltip", "DFR Completed");
				finalStepObject.addProperty("fillcolor", "solid");
				break;
			case "partially completed":
				if(dfrMaster.getApprovalHistories().isEmpty()){
					stepObject.addProperty("label", "Initiate DFR");
					stepObject.addProperty("tooltip", "DFR Initiated by "+dfrMaster.getCreatedTeam());
					stepObject.addProperty("fillcolor", "solid");
					/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
					stepArray.add(stepObject);
				}
				finalStepObject.addProperty("label", "Partially Completed");
				finalStepObject.addProperty("tooltip", "DFR Completed");
				finalStepObject.addProperty("fillcolor", "solid");
				break;
			default:
				stepObject.addProperty("label", dfrMaster.getAssignedTeam());
				stepObject.addProperty("tooltip", dfrMaster.getStatus());
				stepObject.addProperty("fillcolor", "empty");
				/*stepObject.addProperty("team", dfrMaster.getCreatedTeam());*/
				stepArray.add(stepObject);
				finalStepObject.addProperty("label", dfrMaster.getStatus());
				finalStepObject.addProperty("tooltip", "");
				finalStepObject.addProperty("fillcolor", "empty");
				break;
			}
			stepArray.add(finalStepObject);
		}
		workflowObject.addProperty("dfrid", dfrId);
		workflowObject.add("Steps", stepArray);
		return workflowObject.toString();
	}
	
	@Override
	public String updateUserDfr(String userId, String dfrId) {

		int status = 0;
		String result = null;

		DfrMaster dfrMaster = oppsDartHomeDao.getDfrById(dfrId);
		if (dfrMaster == null) {

		} else {
			StringBuilder sb = new StringBuilder();
			String recentDfr = oppsDartHomeDao.getUserDfr(userId);

			if (recentDfr == null) {
				status = oppsDartHomeDao.updateUserDfr(userId, dfrId);

			} else {

				LinkedList<String> list = new LinkedList<String>();
				String ids[] = recentDfr.split(",");
				for (int i = 0; i < ids.length; i++) {
					list.add(ids[i]);
					if (list.contains(dfrId)) {
						list.remove(dfrId);
					}
				}

				if (list.size() == 5) {
					list.removeFirst();
					list.addLast(dfrId);
				} else {
					list.addLast(dfrId);
				}
				Iterator<String> iterator = list.iterator();
				while (iterator.hasNext()) {
					String dfr = iterator.next();
					sb.append(dfr + ",");
				}
				String finalDfr = removeComma(sb.toString());
				status = oppsDartHomeDao.updateUserDfr(userId, finalDfr);
			}
		}
		if (status > 0) {
			result = userId + " Updated Sucessfully";
		} else {
			result = userId + "  Updated Failed ";
		}
		return result;
	}

	private String removeComma(String dfr) {

		if (dfr != null && dfr.length() > 0 && dfr.charAt(dfr.length() - 1) == ',') {
			dfr = dfr.substring(0, dfr.length() - 1);
		}
		return dfr;
	}
	
	

	@Override
	public ProductDataGrid getRefreshedCommonAttributeGrid(ProductFilter filters){
		ProductDataGrid dataGrid = new ProductDataGrid();
		
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
		Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");
		
		List<String> errorList = filters.getError();
		String commaSperatedErrorList = errorList.stream().collect(Collectors.joining(","));
		HashSet<String> errorSet = new HashSet<String>();
		
		Map<String,List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String,AssetNewVal>  assetNewValMap = new HashMap<>();
		
		StringTokenizer st = new StringTokenizer(commaSperatedErrorList, ",");
		while(st.hasMoreTokens())
			errorSet.add(st.nextToken());
		
		List sblRowIdList = new ArrayList<>();
		List clxRowIdList = new ArrayList<>();
		List svRowIdList = new ArrayList<>();
		errorSet.stream().forEach(s2->{
			if(s2.split("##")[0].equalsIgnoreCase("SBL") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				sblRowIdList.add(s2.split("##")[1]);
			else if(s2.split("##")[0].equalsIgnoreCase("CLX") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				clxRowIdList.add(s2.split("##")[1]);			
			else if(s2.split("##")[0].equalsIgnoreCase("SV") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				svRowIdList.add(s2.split("##")[1]);
			
		});
		List<SnapshotSiebelAssetDa> sblList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
				sblList = editDfrDao.getSnapshotSblAssetDaByRowsIds(sblRowIdList);
				getSblAssetNewValMap(assetNewValMap, sblList);
				
			}
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(clxRowIdList)){
			 clxList = editDfrDao.getSnapshotClxAssetDaByRowsIds(clxRowIdList);
		}
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(svRowIdList)){
			svList = editDfrDao.getSnapshotSvAssetDaByRowsIds(svRowIdList);
		}
		SnapshotClxAssetDa clx = null;
		SnapshotSvAssetDa sv =null;
		
			int maxCommonAttrCount = 0;
			String maxCommonProduct = "";
			for(Map.Entry<String, Integer> entry :maximumAttrCount.entrySet()){
				maxCommonAttrCount = entry.getValue().intValue();
				maxCommonProduct = entry.getKey();
				logger.info("Max product : " + maxCommonProduct + ":" + maxCommonAttrCount );
				break;
			}
			for(SnapshotSiebelAssetDa sbl : sblList){
				Product productJson = new Product();
				if(sbl!=null){
					productJson.setName(sbl.getHeader20());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if(StringUtils.isNotEmpty(sbl.getHeader60())&& sbl.getHeader60().equalsIgnoreCase("Y")){
						productJson.setValidate("Y");
					}else{
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxList);
					sv = ServiceUtil.getSv(sbl,svList );
					/*for(int headerCounter =1 ; headerCounter <= 66 ; headerCounter++){*/
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, 2,assetNewValMap);
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, 6,assetNewValMap);
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, 7,assetNewValMap);
					//}
					//SortedMap<Integer,AttributeConfig> productAttrMap = configProductCommonAttrMap.get(sbl.getHeader20());
					for (Map.Entry<String, SortedMap<Integer, AttributeConfig>> entry : configProductCommonAttrMap.entrySet()) {
						if(ServiceUtil.productListWhichHaveCommonAttribute.contains(entry.getKey())){
							if(isProductContainsAnyCommonAttributeViewDisplayYes(entry.getValue())){
								 for(int attrCounter =1 ; attrCounter <= maxCommonAttrCount ; attrCounter++){
									setAttributes(clx, sv, sbl, productJson, entry.getValue(), attrCounter,assetNewValMap);
								 }
							}
						}
					 }
					}
				dataGrid.getProducts().add(productJson);
			}
		
		logger.info("Attribute View processing end ");
		
	//	dataGrid.setSnapfilter(removeProductFilter(filters));
		
		return dataGrid;
	}

	private void getSblAssetNewValMap(Map<String, AssetNewVal> assetNewValMap, List<SnapshotSiebelAssetDa> sblList) {
		Map<String, List<AssetNewVal>> assetNewValMapList;
		Set<String> sblDfrLineSet = new HashSet<>();
		for(SnapshotSiebelAssetDa sbl : sblList){
			sblDfrLineSet.add(sbl.getDfrLineId());
		}
		List<AssetNewVal> assetNewValList = editDfrDao.getAssetNewValBySblRowIds(sblDfrLineSet);
		if( CollectionUtils.isNotEmpty(assetNewValList)){
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k,v)->{
				System.out.println("sblrid: "+k +"," + v.size());
				if(v.size()==1){
					assetNewValMap.put(k,v.get(0));
				}
			});
		}
	}
	
	@Override
	public ProductDataGrid getRefreshedProductAttributeGrid(ProductFilter filters) {
		ProductDataGrid dataGrid = new ProductDataGrid();

		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
				.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap = (HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap
				.get("common");
		Map<String, Integer> maximumAttrCount = (Map<String, Integer>) productAttributeAndLengthMap.get("max");
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();

		/*
		 * List<String> errorList = filters.getError(); String
		 * commaSperatedErrorList =
		 * errorList.stream().collect(Collectors.joining(",")); HashSet<String>
		 * errorSet = new HashSet<String>(); StringTokenizer st = new
		 * StringTokenizer(commaSperatedErrorList, ",");
		 * while(st.hasMoreTokens()) errorSet.add(st.nextToken());
		 * 
		 * 
		 * errorSet.stream().forEach(s2->{
		 * if(s2.split("##")[0].equalsIgnoreCase("SBL") &&
		 * !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
		 * sblRowIdList.add(s2.split("##")[1]); else
		 * if(s2.split("##")[0].equalsIgnoreCase("CLX") &&
		 * !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
		 * clxRowIdList.add(s2.split("##")[1]); else
		 * if(s2.split("##")[0].equalsIgnoreCase("SV") &&
		 * !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
		 * svRowIdList.add(s2.split("##")[1]);
		 * 
		 * });
		 */
		List sblRowIdList = new ArrayList<>();
		List clxRowIdList = new ArrayList<>();
		List svRowIdList = new ArrayList<>();
		List<SnapshotSiebelAssetDa> sblList = new ArrayList<>();
		// if(CollectionUtils.isNotEmpty(sblRowIdList)){
		sblList = editDfrDao.getSnapshotSblAssetDaByPOEFilter(filters);

		/**
		 * Old way with sbl row ids
		 */
		// sblList = editDfrDao.getSnapshotSblAssetDaByRowsIds(sblRowIdList);

		// List<SnapshotSiebelAssetDa> siebelAssetDas =
		// sortSblProductWise(sblList,filters.getProducts());
		dataGrid.setTotalrows((long) sblList.size());
		/*
		 * if (StringUtils.isEmpty(filters.getShowAll()) &&
		 * StringUtils.isBlank(filters.getShowAll())) { sblList =
		 * siebelAssetDas; } else { if
		 * (filters.getShowAll().equalsIgnoreCase("true")) { sblList =
		 * siebelAssetDas; } else if
		 * (filters.getShowAll().equalsIgnoreCase("false")) { int page =
		 * filters.getPageNumber().intValue(); List<List<SnapshotSiebelAssetDa>>
		 * siebelAssets = getPages(siebelAssetDas, page); sblList =
		 * siebelAssets.get(0); } }
		 */

		getSblAssetNewValMap(assetNewValMap, sblList);
		// }
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(clxRowIdList)) {
			clxList = editDfrDao.getSnapshotClxAssetDaByRowsIds(clxRowIdList);
		}
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(svRowIdList)) {
			svList = editDfrDao.getSnapshotSvAssetDaByRowsIds(svRowIdList);
		}
		SnapshotClxAssetDa clx = null;
		SnapshotSvAssetDa sv = null;

		for (SnapshotSiebelAssetDa sbl : sblList) {
			Product productJson = new Product();
			if (sbl != null) {
				productJson.setName(sbl.getHeader20());
				productJson.setDfrlineid(sbl.getDfrLineId());
				if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
					productJson.setValidate("Y");
				} else {
					productJson.setValidate("N");
				}
				clx = ServiceUtil.getClx(sbl, clxList);
				sv = ServiceUtil.getSv(sbl, svList);
				for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
				}
				// attr loop
				SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
				for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
					setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
				}
				productJson.setDqmErrorFlag(sbl.getHeader38());
				List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
				if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
					StringBuffer errorBuffer = new StringBuffer();
					errorColl.forEach(errorObj -> {
						if (StringUtils.isNotEmpty(errorBuffer)) {
							errorBuffer.append(",");
						}
						errorBuffer.append(errorObj.getErrorCode());
					});
					if (StringUtils.isNotEmpty(errorBuffer)) {
						productJson.setDqmErrorCodes(errorBuffer.toString());
					}
				}
			}
			dataGrid.getProducts().add(productJson);
		}

		logger.info("Attribute View processing end ");
		// dataGrid.setSnapfilter(removeProductFilter(filters));
		return dataGrid;

	}
	
	@Override
	public ProductDataGrid getSelectedRefreshedProductAttributeView(ProductFilter filters) {


		ProductDataGrid dataGrid = new ProductDataGrid();
		
		
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String,AssetNewVal> assetNewValMap = new HashMap<>();
	
		List<String> errorList = filters.getError();
		String commaSperatedErrorList = errorList.stream().collect(Collectors.joining(","));
		HashSet<String> errorSet = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(commaSperatedErrorList, ",");
		while(st.hasMoreTokens())
			errorSet.add(st.nextToken());
		
		List sblRowIdList = new ArrayList<>();
		List clxRowIdList = new ArrayList<>();
		List svRowIdList = new ArrayList<>();
		errorSet.stream().forEach(s2->{
			if(s2.split("##")[0].equalsIgnoreCase("SBL") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				sblRowIdList.add(s2.split("##")[1]);
			else if(s2.split("##")[0].equalsIgnoreCase("CLX") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				clxRowIdList.add(s2.split("##")[1]);			
			else if(s2.split("##")[0].equalsIgnoreCase("SV") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				svRowIdList.add(s2.split("##")[1]);
			
		});
		List<SnapshotSiebelAssetDa> sblList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
				sblList = editDfrDao.getSnapshotSblAssetDaByRowsIds(sblRowIdList);
				getSblAssetNewValMap(assetNewValMap, sblList);
			}
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(clxRowIdList)){
			 clxList = editDfrDao.getSnapshotClxAssetDaByRowsIds(clxRowIdList);
		}
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(svRowIdList)){
			svList = editDfrDao.getSnapshotSvAssetDaByRowsIds(svRowIdList);
		}
		
		List<SnapshotSiebelAssetDa> sblDaList2 = new ArrayList<>();
		List<SnapshotClxAssetDa> clxDaList2 = new ArrayList<>();
		List<SnapshotSvAssetDa> svDaList2 = new ArrayList<>();
		
		if (filters.getApplications().equalsIgnoreCase("sbl")) {
			List<String> searchValues = filters.getSelectedAttribute();
			sblDaList2 = ServiceUtil.filterSnapshotSiebelAssetDa(sblList, searchValues,filters);
			if(filters.getShowAll().equalsIgnoreCase("true")){
				sblList = sblDaList2;
			}else if(filters.getShowAll().equalsIgnoreCase("false")){
				int page =  filters.getPageNumber().intValue();;
				List<List<SnapshotSiebelAssetDa>> siebelAssetDas = getPages(sblDaList2,page);
				sblList = siebelAssetDas.get(0);
			}
			
		} else if (filters.getApplications().equalsIgnoreCase("clx")) {
			List<String> searchValues = filters.getSelectedAttribute();
			clxDaList2 = ServiceUtil.filterSnapshotClxAssetDa(clxList, searchValues, filters);
			int page =  filters.getPageNumber().intValue();
			List<List<SnapshotClxAssetDa>> clxAssetDas = getPages(clxDaList2,page);
			clxList = clxAssetDas.get(0);
		} else if (filters.getApplications().equalsIgnoreCase("sv")) {
			List<String> searchValues = filters.getSelectedAttribute();
			svDaList2  = ServiceUtil.filterSnapshotSvAssetDa(svList, searchValues, filters);
			int page =  filters.getPageNumber().intValue();
			List<List<SnapshotSvAssetDa>> svAssetDas = getPages(svDaList2,page);
			svList = svAssetDas.get(0);
		}
		if(filters.getOrderBy().equalsIgnoreCase("DESC")){
			Collections.reverse(sblList);
			Collections.reverse(svList);
			Collections.reverse(clxList);
		}
		
		if(filters.getApplications().equalsIgnoreCase("sbl")){
			dataGrid.setTotalrows((long)sblList.size());
		}else if(filters.getApplications().equalsIgnoreCase("sv")){
			dataGrid.setTotalrows((long)svList.size());
		}else if(filters.getApplications().equalsIgnoreCase("clx")){
			dataGrid.setTotalrows((long)clxList.size());
		}
		
		SnapshotClxAssetDa clx = null;
		SnapshotSvAssetDa sv =null;

	
		for(SnapshotSiebelAssetDa sbl : sblList){
			Product productJson = new Product();
			if(sbl!=null){
				productJson.setName(sbl.getHeader20());
				productJson.setDfrlineid(sbl.getDfrLineId());
				if(StringUtils.isNotEmpty(sbl.getHeader60())&& sbl.getHeader60().equalsIgnoreCase("Y")){
					productJson.setValidate("Y");
				}else{
					productJson.setValidate("N");
				}
				clx = ServiceUtil.getClx(sbl, clxList);
				sv = ServiceUtil.getSv(sbl,svList );
				for(int headerCounter =1 ; headerCounter <= MAX_HEADERS ; headerCounter++){
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter,assetNewValMap);
					}
				// attr loop
				SortedMap<Integer,AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
				for(int attrCounter =1 ; attrCounter <= MAX_ATTRIBUTES ; attrCounter++){
					setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter,assetNewValMap);
				}
				
				productJson.setDqmErrorFlag(sbl.getHeader38());
				List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
				if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
					StringBuffer errorBuffer = new StringBuffer();
					errorColl.forEach( errorObj -> {
						if (StringUtils.isNotEmpty(errorBuffer)) {
							errorBuffer.append(",");
						}
						errorBuffer.append(errorObj.getErrorCode());
					});
					if (StringUtils.isNotEmpty(errorBuffer)) {
						productJson.setDqmErrorCodes(errorBuffer.toString());
					}
				}
			}
			dataGrid.getProducts().add(productJson);
		}

		logger.info("Attribute View processing end ");
		//dataGrid.setSnapfilter(removeProductFilter(filters));
		return dataGrid;
	}
	
	@Override
	public List<String> getRefreshedProductAttributeFilter(ProductFilter searchFilters) {

		ProductDataGrid dataGrid = new ProductDataGrid();
		String actualKeyword = searchFilters.getKeyword();
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
		Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");
		//Map<String,AssetNewVal> assetNewValMap = new HashMap<>();
	
		List<String> errorList = searchFilters.getError();
		String commaSperatedErrorList = errorList.stream().collect(Collectors.joining(","));
		HashSet<String> errorSet = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(commaSperatedErrorList, ",");
		while(st.hasMoreTokens())
			errorSet.add(st.nextToken());
		
		List sblRowIdList = new ArrayList<>();
		List clxRowIdList = new ArrayList<>();
		List svRowIdList = new ArrayList<>();
		errorSet.stream().forEach(s2->{
			if(s2.split("##")[0].equalsIgnoreCase("SBL") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				sblRowIdList.add(s2.split("##")[1]);
			else if(s2.split("##")[0].equalsIgnoreCase("CLX") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				clxRowIdList.add(s2.split("##")[1]);			
			else if(s2.split("##")[0].equalsIgnoreCase("SV") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				svRowIdList.add(s2.split("##")[1]);
			
		});
		List<String> commonList = new ArrayList<String>();
		if (searchFilters.getApplications().equalsIgnoreCase(DartConstants.SBL)) {
			List<SnapshotSiebelAssetDa> sblList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(sblRowIdList)){
					sblList = editDfrDao.getSnapshotSblAssetDaByRowsIds(sblRowIdList);
				}
			for (SnapshotSiebelAssetDa sbl : sblList) {

				String val;
				if (sbl != null) {
					val = ServiceUtil.getSblHeaderValue(searchFilters.getHeader(), sbl);
					if (val != null && StringUtils.isNotBlank(val) && StringUtils.isNotEmpty(val)) {
						if (val.contains(searchFilters.getSearchValue())) {
							commonList.add(val);
						}
					}
				}
			}
		}
		else if (searchFilters.getApplications().equalsIgnoreCase(DartConstants.CLX)) {
			List<SnapshotClxAssetDa> clxList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(clxRowIdList)){
				 clxList = editDfrDao.getSnapshotClxAssetDaByRowsIds(clxRowIdList);
			}
			for (SnapshotClxAssetDa clx : clxList) {

				String val;
				if (clx != null) {
					val = ServiceUtil.getClxHeaderValue(searchFilters.getHeader(), clx);
					if (val != null && StringUtils.isNotBlank(val) && StringUtils.isNotEmpty(val)) {
						if (val.contains(searchFilters.getSearchValue())) {
							commonList.add(val);
						}
					}
				}
			}
		}
		else if (searchFilters.getApplications().equalsIgnoreCase(DartConstants.SV)) {
			List<SnapshotSvAssetDa> svList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(svRowIdList)){
				svList = editDfrDao.getSnapshotSvAssetDaByRowsIds(svRowIdList);
			}
			for (SnapshotSvAssetDa sv : svList) {

				String val;
				if (sv != null) {
					val = ServiceUtil.getSvHeaderValue(searchFilters.getHeader(), sv);
					if (val != null && StringUtils.isNotBlank(val) && StringUtils.isNotEmpty(val)) {
						if (val.contains(searchFilters.getSearchValue())) {
							commonList.add(val);
						}
					}
				}

			}
		}
		return commonList;
	}
	private HashSet<String> getProductSetAfterRefresh(List<SnapshotSiebelAssetDa> sbList) {
		Map<String,List<SnapshotSiebelAssetDa>> map = sbList.stream().collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader20));
		HashSet<String> productSetAfterRefresh = new HashSet<>();
		for(Map.Entry<String, List<SnapshotSiebelAssetDa>> entry : map.entrySet()){
			if(CollectionUtils.isNotEmpty(entry.getValue())){
				productSetAfterRefresh.add(entry.getValue().get(0).getHeader20());
			}
		}
		
		return productSetAfterRefresh;
	}
	
	@Override
	public ProductWidgets getRefreshedProductWidgets(ProductFilter productFilter) {
		HashSet<String> productSetAfterRefresh = new HashSet<>();
		List<String> errorList = productFilter.getError();
		String commaSperatedErrorList = errorList.stream().collect(Collectors.joining(","));
		HashSet<String> errorSet = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(commaSperatedErrorList, ",");
		while(st.hasMoreTokens())
			errorSet.add(st.nextToken());
		
		List<String> sblRowIdList = new ArrayList<String>();		
		errorSet.stream().forEach(s2->{
			if(s2.split("##")[0].equalsIgnoreCase("SBL") && !s2.split("##")[2].equalsIgnoreCase("CLOSED"))
				sblRowIdList.add(s2.split("##")[1]);
		});
		
		List<SnapshotSiebelAssetDa> sbList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
			sbList = editDfrDao.getSnapshotSblAssetDaByRowsIds(sblRowIdList);
			productSetAfterRefresh =getProductSetAfterRefresh(sbList);
		}
		
		ProductWidgets productWidgets  = new ProductWidgets();
		for(PFilter filter : productFilter.getFilters()){
			if(filter.getKey().equalsIgnoreCase("header20")){
				for(String product : filter.getListOfValues()){
				//	List<SnapshotSiebelAssetDa> sbList = appOpsDartDaDao.getSnapshotSiebelAssetDaDataByProduct(productFilter, product);
					System.out.println(sbList.size());
					if(product.equalsIgnoreCase("Cage")&& productSetAfterRefresh.contains("Cage")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cage");
						Map<String,List<SnapshotSiebelAssetDa>> mapBySysName = sbList.stream().filter(item->((item.getHeader16()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Cage")))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
						ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
						widgetGroup.setName("");
						mapBySysName.entrySet().stream().forEach(entry->{
							widgetGroup.getValues().add(entry.getKey());
							
						});
						productWidget.getGroups().add(widgetGroup);
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Cabinet")&& productSetAfterRefresh.contains("Cabinet")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cabinet");
						Map<String,List<SnapshotSiebelAssetDa>> mapBySysName = sbList.stream().filter(item->((item.getHeader16()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Cabinet")))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader14());
								}
								//groupTitle = entry.getValue().get(0).getHeader16();
							}
							widgetGroup.setName(entry.getKey());
							productWidget.getGroups().add(widgetGroup);
							
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Demarcation Point")&& productSetAfterRefresh.contains("Demarcation Point")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Demarcation Point");
						Map<String,List<SnapshotSiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Demarcation Point"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader14());
								}
								//groupTitle = entry.getValue().get(0).getHeader16();
							}
							widgetGroup.setName(entry.getKey());
							productWidget.getGroups().add(widgetGroup);
							
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("AC Circuit")&& productSetAfterRefresh.contains("AC Circuit")){

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("AC Circuit");
						Map<String,List<SnapshotSiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->((item.getHeader14()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("AC Circuit")))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
							}
							widgetGroup.setName(groupTitle);
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("DC Circuit")&& productSetAfterRefresh.contains("DC Circuit")){

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("DC Circuit");
						Map<String,List<SnapshotSiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("DC Circuit"))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
							}
							widgetGroup.setName(groupTitle);
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Patch Panel")&& productSetAfterRefresh.contains("Patch Panel")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Patch Panel");
						Map<String,List<SnapshotSiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->((item.getHeader14()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Patch Panel")))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
								widgetGroup.setName(groupTitle);
								productWidget.getGroups().add(widgetGroup);
							}
						});
						productWidgets.getProducts().add(productWidget);
					}else if(product.equalsIgnoreCase("Network Cable Connection")&& productSetAfterRefresh.contains("Network Cable Connection")){

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Network Cable Connection");
						Map<String,List<SnapshotSiebelAssetDa>> mapByASidePatchPanel = sbList.stream().filter(item->((item.getHeader22()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Network Cable Connection")))).collect(Collectors.groupingBy(SnapshotSiebelAssetDa::getAttr22));
						mapByASidePatchPanel.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SnapshotSiebelAssetDa sb : entry.getValue()){
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() +" - " + entry.getKey() ;
								widgetGroup.setName(groupTitle);
								productWidget.getGroups().add(widgetGroup);
							}
						});
						productWidgets.getProducts().add(productWidget);
					}
				}
			}
		}
		return productWidgets;
	}
	
	@Override
	public DfrMaster getDfrMaster(String dfrId, String fetchMode){
		return editDfrDao.getDfrMaster(dfrId, fetchMode);
	}


	@Override
	public void saveOrUpdateApprovalHistory(ApprovalHistory approvalHistory) {
		appOpsInitDartDaDao.saveOrUpdateApprovalHistory(approvalHistory);	
	}

	@Override
	public void saveOrUpdateDfrMaster(DfrMaster dfrMaster) {
		appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);			
	}

	@Override
	public String getAssignedGroupByReqionAndSystem(String region, String sot) {
		return editDfrDao.getAssignedGroupByReqionAndSystem(region, sot);
	}

	@Override
	public ApprovalHistory getLatestAppHistory(String dfrId) {
		return appOpsInitDartDaDao.getLatestAppHistory(dfrId);
	}

	@Override
	public String getEmailIdByAssignGroup(String assignedTo) {
		List<String> emailIds= editDfrDao.getEmailIdByAssignGroup(assignedTo);
		return String.join(",", emailIds);
	}
	@Override
	public String initiatePhysicalAudit(String dfrId, String ibx) {
		String team = ibx + " Local OPS";
		boolean status = editDfrDao.checkAssignTeamExist(team);
		if (!status) {
			return "team not exist";
		} else {
			String fetchMode = "select";
			DfrMaster dfrMaster = editDfrDao.getDfrMaster(dfrId, fetchMode);
			if ("new".equalsIgnoreCase(dfrMaster.getStatus())) {
				ApprovalHistory approvalHistory = new ApprovalHistory();
				int appSeq = 0;
				ApprovalHistory approvalHistoryLatest = appOpsInitDartDaDao.getLatestAppHistory(dfrMaster.getDfrId());
				if (approvalHistoryLatest != null) {
					appSeq = approvalHistoryLatest.getAppSequence();
				}
				appSeq = appSeq + 1;
				approvalHistory.setAppSequence(appSeq);
				approvalHistory.setAssignedDt(dfrMaster.getAssignedDt());
				approvalHistory.setAssignedTeam(dfrMaster.getAssignedTeam());
				approvalHistory.setDfrId(dfrMaster.getDfrId());
				approvalHistory.setPhysicalAudit(dfrMaster.getPhysicalAudit());
				approvalHistory.setCreatedBy(dfrMaster.getCreatedBy());
				approvalHistory.setCreatedDate(dfrMaster.getCreatedDt());
				approvalHistory.setCreatedTeam(dfrMaster.getCreatedTeam());
				approvalHistory.setStatus(LogicConstants.PHYSICAL_AUDIT_INTIATE);
				approvalHistory.setRowId(null);
				approvalHistory.setDfrUpdateDate(new Date());
				approvalHistory.setNotes(dfrMaster.getNotes());
				User user = UserThreadLocal.userThreadLocalVar.get();
				if (user != null) {
					approvalHistory.setApprovedBy(user.getUserId());
				}
				appOpsInitDartDaDao.saveOrUpdateApprovalHistory(approvalHistory);
			}
			dfrMaster.setStatus("Physical Audit Initiated");
			dfrMaster.setPhysicalAudit("Y");
			dfrMaster.setAssignedDt(new Date());
			dfrMaster.setAssignedTeam(team);
			dfrMaster.setAssignedTo(DartConstants.BLANK);
			appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);
			return "Success";
		}
	}
	
	@Override
	public Map<String,Object> getPhysicalAuditDownloadData(String dfrId) {
		return editDfrDao.getPhysicalAuditDownloadData(dfrId);
	}
	
	private boolean isProductPresentInFilter(ProductFilter productFilter, String product){
		List<PFilter> filterList = productFilter.getFilters();
		for(PFilter filter : filterList){
			if(filter.getKey().equalsIgnoreCase("header20")){
				List<String> listOfValues = filter.getListOfValues();
				if(CollectionUtils.isNotEmpty(listOfValues)){
					return listOfValues.contains(product);
				}
			}
		}
		return false;
	}
	private boolean isProductContainsAnyCommonAttributeViewDisplayYes(SortedMap<Integer, AttributeConfig> map ){
		for (Map.Entry<Integer, AttributeConfig> entry : map.entrySet()) {
			AttributeConfig attributeConfig =  entry.getValue();
			if(attributeConfig.getDisplayFlag() !=null && attributeConfig.getDisplayFlag().equalsIgnoreCase("Y")){
				return true;
			}
		}
			
		return false;
	}

	@Override
	public List<DfrMaster> getDfrMasterCompleted(String emailFlag) {
		return editDfrDao.getDfrMasterCompleted(emailFlag);
	}
	
	
	/**
	 * @param configHeaderMap
	 * @param clx
	 * @param sv
	 * @param sbl
	 * @param productJson
	 * @param headerCounter
	 */
	private void setPhysicalHeaders(final SortedMap<Integer, AttributeConfig> configHeaderMap, SnapshotClxAssetDa clx, SnapshotSvAssetDa sv,
			SnapshotSiebelAssetDa sbl, Product productJson, Map<String,AssetNewVal> assetNewValMap) {
		
		for(Map.Entry<Integer, AttributeConfig> entry : configHeaderMap.entrySet()){
			AttributeConfig headerAttrConfig = entry.getValue();
			if(headerAttrConfig!=null && isDisplay(headerAttrConfig)){
				Attribute attribute = new Attribute();
				attribute.setKey(headerAttrConfig.getHeaderPosition().toLowerCase().replace("_", ""));
				attribute.setName(headerAttrConfig.getAttrName());
				attribute.setDisplayName(headerAttrConfig.getDisplayName());
				attribute.setType("header");
				setAttributeDataType(headerAttrConfig, attribute);
				attribute.setSot(StringUtil.isEmpty(headerAttrConfig.getSot()) ? DartConstants.NA : headerAttrConfig.getSot() );
				AssetNewVal assetNewVal = null;
				if(StringUtils.isEmpty(headerAttrConfig.getEditable()))
					attribute.setEditable("N");
				else
					attribute.setEditable(headerAttrConfig.getEditable());
				attribute.setRunDependent(headerAttrConfig.getRunDependent());
				if(StringUtils.isNotBlank(headerAttrConfig.getPricingAttributeFlg()))
					attribute.setPricingFlg(headerAttrConfig.getPricingAttributeFlg());
				else
					attribute.setPricingFlg("N");
				Values values = new Values();
				
				String val ="";
				
				if(sbl!=null){
					 val = ServiceUtil.getSblHeaderValue(entry.getKey(),sbl);
					 values.setSBL(val==null ? DartConstants.NA +"##"+sbl.getDfrLineId(): val+"##"+sbl.getDfrLineId());
					 attribute.setDfrlineid(sbl.getDfrLineId());
					 if(attribute.getKey().equalsIgnoreCase("header60")&& StringUtils.isNotEmpty(val) && val.equalsIgnoreCase("Y")){
						 attribute.setValidate("Y");
					 }else{
						 attribute.setValidate("N");
					 }
					 if(assetNewValMap.containsKey(sbl.getDfrLineId())){
						 assetNewVal = assetNewValMap.get(sbl.getDfrLineId());
					 }
					 
					 // assetNewVal = editDfrDao.getAssetNewValByDfrLineId(sbl.getDfrLineId());
				 }else{
					 values.setSBL(DartConstants.NA+"##");
					 attribute.setDfrlineid(DartConstants.NA);
				 }
				
				if(clx!=null){
					val = ServiceUtil.getClxHeaderValue(entry.getKey(),clx);
					values.setCLX(val==null ? DartConstants.NA +"##" +clx.getDfrLineId(): val +"##" +clx.getDfrLineId());
				}else{
					values.setCLX(DartConstants.NA+"##");
				}	

				if(sv!=null){
					val = ServiceUtil.getSvHeaderValue(entry.getKey(),sv);
					values.setSV(val==null ? DartConstants.NA + "##" + sv.getDfrLineId(): val + "##" + sv.getDfrLineId());
				}else{
					values.setSV(DartConstants.NA+"##");
				}
				attribute.setValues(values);
				if(assetNewVal!=null){
					  setAssetNewValPropertyValueInAttributeValue(attribute, assetNewVal);
				 }
				productJson.getAttributes().add(attribute);
			}
		}
	}
	
	private void setPhysicalAttributes(SnapshotClxAssetDa clx, SnapshotSvAssetDa sv, SnapshotSiebelAssetDa sbl, Product productJson,
			SortedMap<Integer, AttributeConfig> productAttrMap,  Map<String,AssetNewVal> assetNewValMap ) {
		
		for(Map.Entry<Integer, AttributeConfig> entry : productAttrMap.entrySet()){
			AttributeConfig attrConfig = entry.getValue();
			 if(attrConfig!=null &&  isDisplay(attrConfig)){
				 Attribute attribute = new Attribute();
				 attribute.setKey(attrConfig.getHeaderPosition().toLowerCase().replace("_", ""));
				 attribute.setName(attrConfig.getAttrName());
				 attribute.setDisplayName(attrConfig.getDisplayName());
				 attribute.setType("attribute");
				 setAttributeDataType(attrConfig, attribute);
				 attribute.setSot(StringUtil.isEmpty(attrConfig.getSot()) ? DartConstants.NA : attrConfig.getSot() );
				 if(StringUtils.isEmpty(attrConfig.getEditable()))
						attribute.setEditable("N");
				 else
						attribute.setEditable(attrConfig.getEditable());
				 attribute.setRunDependent(attrConfig.getRunDependent());
				 
				 if(StringUtils.isNotBlank(attrConfig.getPricingAttributeFlg()))
						attribute.setPricingFlg(attrConfig.getEditable());
					else
						attribute.setPricingFlg("N");	
				 Values values = new Values();
				 String val;
				 AssetNewVal assetNewVal =null;
				 if(sbl!=null){
					 val = ServiceUtil.getSblAttrValue(entry.getKey(),sbl);
					 values.setSBL(val==null ? DartConstants.NA +"##"+sbl.getDfrLineId(): val+"##"+sbl.getDfrLineId());
					 attribute.setDfrlineid(sbl.getDfrLineId());
					 if(assetNewValMap.containsKey(sbl.getDfrLineId())){
						 assetNewVal= assetNewValMap.get(sbl.getDfrLineId());
					 }
					// assetNewVal = editDfrDao.getAssetNewValByDfrLineId(sbl.getDfrLineId());
						
						
				 }else{
					 values.setSBL(DartConstants.NA+"##");
					 attribute.setDfrlineid(DartConstants.NA);
				 }
				 if(clx!=null){
					 val = ServiceUtil.getClxAttrValue(entry.getKey(),clx);
					 values.setCLX(val==null ? DartConstants.NA+"##"+clx.getDfrLineId() : val+"##"+clx.getDfrLineId());
				 }else{
					 values.setCLX(DartConstants.NA+"##");
				 }
				 if(sv!=null){
					 val = ServiceUtil.getSvAttrValue(entry.getKey(),sv);
					 values.setSV(val==null ? DartConstants.NA +"##"+sv.getDfrLineId(): val+"##"+sv.getDfrLineId());
				 }else{
					 values.setSV(DartConstants.NA+"##");
				 }
				
				 attribute.setValues(values);
				 if(assetNewVal!=null){
					  setAssetNewValPropertyValueInAttributeValue(attribute, assetNewVal);
				 }
				 
				 productJson.getAttributes().add(attribute);
			 }
		}
		
		
	}
	
	@Override
	public void saveOrUpdatePhysicalAuditData(List<SnapshotSiebelAssetDa> assetList,
			List<AssetNewVal> assetNewValListInsert, List<AssetNewVal> assetNewValListUpdate,List<SaveAssetForm> saveAssetForms) {
		editDfrDao.saveOrUpdatePhysicalAuditData(assetList, assetNewValListInsert, assetNewValListUpdate,saveAssetForms);
		if (CollectionUtils.isNotEmpty(assetNewValListUpdate) || CollectionUtils.isNotEmpty(assetNewValListInsert)) {
			String dfrId = null;
			if (CollectionUtils.isNotEmpty(assetNewValListUpdate)) {
				dfrId = assetNewValListUpdate.get(0).getDfrId() != null ? assetNewValListUpdate.get(0).getDfrId()
						: assetNewValListUpdate.get(0).getDfrId();
			} else if (CollectionUtils.isNotEmpty(assetNewValListInsert)) {
				dfrId = assetNewValListInsert.get(0).getDfrId() != null ? assetNewValListInsert.get(0).getDfrId()
						: assetNewValListInsert.get(0).getDfrId();
			}
			if (StringUtils.isNotEmpty(dfrId)) {
				DfrMaster dfrMaster = getDfrMasterById(dfrId);
				ApprovalHistory approvalHistory = getApprovalHistory(dfrMaster, LogicConstants.PHYSICAL_AUDIT_IMPORT,
						assetNewValListInsert.size() + " Assets Inserted, " + assetNewValListUpdate.size()
								+ " Assets Updated");
				saveOrUpdateApprovalHistory(approvalHistory);
			}
		}
	}
	
	@Override
	public HashMap<String,SrcCxiErrorMasterTbl> getErrorMasterList() {
		return appOpsInitDartDaDao.getErrorMasterList();
	}
	
	private void validateProductFilterForCommaSaparatedAssetNumsInKeyword(ProductFilter productFilter) {
		List<SearchDrop> searchDropList = productFilter.getSearchDropBox().getSearchDrop();
		if (CollectionUtils.isNotEmpty(searchDropList)) {
			for (SearchDrop searchDrop : searchDropList) {
				if (StringUtils.isNotEmpty(searchDrop.getKey())) {
					List<String> serialNumList = null;
					if ("header3".equalsIgnoreCase(searchDrop.getKey())) {
						serialNumList = new ArrayList<String>(Arrays.asList(productFilter.getKeyword().split(","))
								.stream().filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim)
								.collect(Collectors.toList()));
						if (CollectionUtils.isNotEmpty(serialNumList)) {
							PFilter serialNumFilter = new PFilter();
							serialNumFilter.setKey(searchDrop.getKey());
							serialNumFilter.setValue(productFilter.getKeyword());
							serialNumFilter.setLable(searchDrop.getLabel());
							serialNumFilter.setListOfValues(serialNumList);
							productFilter.getFilters().add(serialNumFilter);
							productFilter.setKeyword("");
						}
					} else {
						List<String> assetNumList = new ArrayList<String>(
								Arrays.asList(productFilter.getKeyword().split(",")).stream()
										.filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim)
										.collect(Collectors.toList()));
						if (CollectionUtils.isNotEmpty(assetNumList)) {
							PFilter assetNumFilter = new PFilter();
							assetNumFilter.setKey(searchDrop.getKey());
							assetNumFilter.setLable(searchDrop.getLabel());
							assetNumFilter.setValue(productFilter.getKeyword());
							assetNumFilter.setListOfValues(assetNumList);
							productFilter.getFilters().add(assetNumFilter);
							productFilter.setKeyword("");
						}
					}
				}
			}
		}
	}

	@Override
	public String getValidStatus(String dfrId) {
		return editDfrDao.getValidStatus(dfrId);
	}
	
	public ProductFilter getProductFilterForErrorCodeGlobalFilter (ProductFilter productFilter) {
		PFilter errorCodeFilter = new PFilter();
		errorCodeFilter.setKey("Error Code");
		errorCodeFilter.setLable("Error Code");
		errorCodeFilter.setValue(productFilter.getKeyword().toUpperCase());
		List<PFilter> pFiltersList = new ArrayList<>();
		pFiltersList.add(errorCodeFilter);
		if (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
			productFilter.getFilters().addAll(pFiltersList);
		} else {
			productFilter.setFilters(pFiltersList);
		}
		generateCacheKey(productFilter);
		return productFilter;
	}
	
	public void generateCacheKey(ProductFilter productFilter){
		productFilter.genrateCacheKey();
	}
	
	@Override
	public String saveOverrideFlagDFRMaster (String overrideFlag, String sblOverrideFlag, String clxOverrideFlag, String dfrId) {
		String fetchMode="select";
		DfrMaster dfrMaster=editDfrDao.getDfrMaster(dfrId,fetchMode);
		dfrMaster.setOverrideFlg(overrideFlag);
		dfrMaster.setSblOverride(sblOverrideFlag);
		dfrMaster.setClxOverride(clxOverrideFlag);
		appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);
		return "Success";
	}
	
	/**
	 * Change Summary changes
	 */
	private ChangeSummary createChangeSummary(Attribute attrObj, ChangeSummaryDTO changeSummaryDto) {
		if (attrObj != null) {
			ChangeSummary changeSummaryObj = null;
			changeSummaryObj = editDfrDao.getChangeSummary(changeSummaryDto.getDfrLineId(), attrObj.getName());
			if (changeSummaryObj == null || (changeSummaryObj != null
					&& !changeSummaryObj.getAttrValue().equals(attrObj.getValues().getNewval()))) {
				if (!attrObj.getValues().getNewval().equals(attrObj.getValues().getSBL())) {

					changeSummaryObj = new ChangeSummary();
					changeSummaryObj.setDfrId(changeSummaryDto.getDfrId());
					changeSummaryObj.setDfrLineId(changeSummaryDto.getDfrLineId());
					changeSummaryObj.setCreatedDate(new Date());
					changeSummaryObj.setAttrName(attrObj.getDisplayName());
					changeSummaryObj.setAttrValue(attrObj.getValues().getNewval());
					if (attrObj.getValues().getSBL().contains("##")) {
						changeSummaryObj.setOldValue(attrObj.getValues().getSBL().split("##")[0]);
					} else {
						changeSummaryObj.setOldValue(attrObj.getValues().getSBL());
					}
					if (StringUtils.isEmpty(changeSummaryDto.getAssetNum())) {
						if (attrObj.getName().equalsIgnoreCase("Asset #")
								&& StringUtils.isNotEmpty(attrObj.getValues().getNewval())) {
							changeSummaryObj.setAssetNum(attrObj.getValues().getNewval());
						}
					} else {
						changeSummaryObj.setAssetNum(changeSummaryDto.getAssetNum());
					}
					changeSummaryObj.setProductName(changeSummaryDto.getProductName());
					changeSummaryObj.setUserId(UserThreadLocal.userThreadLocalVar.get().getUserId());
					return changeSummaryObj;
				}
			}
		}
		return null;
	}
	
	//Change Summary changes
	@Override
	public List<ChangeSummary> getChangeSummary(String dfrId) {
		return editDfrDao.getChangeSummaryList(dfrId);
	}
	
	public ApprovalHistory getApprovalHistory(DfrMaster dfrMaster,String status, String notes){
		ApprovalHistory approvalHistory=new ApprovalHistory();
		int appSeq=0;
		ApprovalHistory approvalHistoryLatest = getLatestAppHistory(dfrMaster.getDfrId());
		if(approvalHistoryLatest != null){
			appSeq = approvalHistoryLatest.getAppSequence();
		}
	/*	for(ApprovalHistory approvalHist:dfrMaster.getApprovalHistories()){
			if(approvalHist.getAppSequence()>appSeq){
				appSeq=approvalHist.getAppSequence();
			}
		}*/
		appSeq = appSeq+1;
		approvalHistory.setAppSequence(appSeq);
		approvalHistory.setAssignedDt(dfrMaster.getAssignedDt());
		approvalHistory.setAssignedTeam(dfrMaster.getAssignedTeam());
		approvalHistory.setDfrId(dfrMaster.getDfrId());
		approvalHistory.setPhysicalAudit(dfrMaster.getPhysicalAudit());
		approvalHistory.setCreatedBy(dfrMaster.getCreatedBy());
		approvalHistory.setCreatedDate(dfrMaster.getCreatedDt());
		approvalHistory.setCreatedTeam(dfrMaster.getCreatedTeam());
		approvalHistory.setDfrUpdateDate(new Date());
		approvalHistory.setNotes(notes);
		User user = UserThreadLocal.userThreadLocalVar.get();
		if(user!=null){
			approvalHistory.setApprovedBy(user.getUserId());
		}
		approvalHistory.setStatus(status);
		approvalHistory.setRowId(null);
		return approvalHistory;
	}
	
	public void updateChangeSummaryValues(ProductDataGrid dataGrid) throws Exception {
		List<Product> rows = dataGrid.getProducts();
		if (CollectionUtils.isNotEmpty(rows)) {
			List<ChangeSummary> changeSummaryList = new ArrayList<>();
			for (Product product : rows) {
				List<Attribute> attributeList = product.getAttributes();
				List<ChangeSummaryDTO> changeSummaryDtoList = editDfrDao.getChangeSummaryDTO(product.getDfrlineid());
				if (changeSummaryDtoList != null && CollectionUtils.isNotEmpty(changeSummaryDtoList)) {
					ChangeSummaryDTO changeSummaryDto = changeSummaryDtoList.get(0);
					if (changeSummaryDto != null) {
						changeSummaryDto.setDfrLineId(product.getDfrlineid());
						for (Attribute attr : attributeList) {
							if(!attr.getName().equalsIgnoreCase("Product")&& StringUtils.isNotEmpty(attr.getValues().getNewval())){
								ChangeSummary changeSummaryObj = createChangeSummary(attr, changeSummaryDto);
								if (changeSummaryObj != null) {
									changeSummaryList.add(changeSummaryObj);
								}
							}
						}
					}
				}
			}
			editDfrDao.saveOrUpdateChangeSummaryList(changeSummaryList);
			logger.info("AppOpsDartEditDfrServiceImpl.updateChangeSummaryValues()");
		}	
	}
	
	@Override
	public void autoUpdateChangeSummaryValues(SaveAssetForm data) {
		Product product = data.getProducts();
		if (product != null) {
			List<ChangeSummary> changeSummaryList = new ArrayList<>();
			List<Attribute> attributeList = product.getAttributes();
			List<ChangeSummaryDTO> changeSummaryDtoList = editDfrDao.getChangeSummaryDTO(product.getDfrlineid());
			if (changeSummaryDtoList != null && CollectionUtils.isNotEmpty(changeSummaryDtoList)) {
				ChangeSummaryDTO changeSummaryDto = changeSummaryDtoList.get(0);
				if (changeSummaryDto != null) {
					changeSummaryDto.setDfrLineId(product.getDfrlineid());
					for (Attribute attr : attributeList) {
						ChangeSummary changeSummaryObj = createChangeSummary(attr, changeSummaryDto);
						if (changeSummaryObj != null) {
							changeSummaryList.add(changeSummaryObj);
						}
					}
				}
			}

			editDfrDao.saveOrUpdateChangeSummaryList(changeSummaryList);
			logger.info("AppOpsDartEditDfrServiceImpl.updateChangeSummaryValues()");
		}

	}
	
	@Override
	public void updatePhyChangeSummaryValues(ProductDataGrid dataGrid) {
		List<Product> rows = dataGrid.getProducts();
		if (CollectionUtils.isNotEmpty(rows)) {
			List<ChangeSummary> changeSummaryList = new ArrayList<>();
			for (Product product : rows) {
				List<Attribute> attributeList = product.getAttributes();
				List<ChangeSummaryDTO> changeSummaryDtoList = editDfrDao.getChangeSummaryDTO(product.getDfrlineid());
				if (changeSummaryDtoList != null && CollectionUtils.isNotEmpty(changeSummaryDtoList)) {
					ChangeSummaryDTO changeSummaryDto = changeSummaryDtoList.get(0);
					if (changeSummaryDto != null) {
						changeSummaryDto.setDfrLineId(product.getDfrlineid());
						for (Attribute attr : attributeList) {
							if (!attr.getName().equalsIgnoreCase("Product")
									&& StringUtils.isNotEmpty(attr.getValues().getNewval())) {
								ChangeSummary changeSummaryObj = createPhyChangeSummary(attr, changeSummaryDto);
								if (changeSummaryObj != null) {
									changeSummaryList.add(changeSummaryObj);
								}
							}
						}
					}
				}
			}
			editDfrDao.saveOrUpdateChangeSummaryList(changeSummaryList);
			logger.info("AppOpsDartEditDfrServiceImpl.updateChangeSummaryValues()");
		}
	}
	
	private ChangeSummary createPhyChangeSummary(Attribute attrObj, ChangeSummaryDTO changeSummaryDto) {

		if (attrObj != null) {
			ChangeSummary changeSummaryObj = null;
			changeSummaryObj = editDfrDao.getChangeSummary(changeSummaryDto.getDfrLineId(), attrObj.getName());
			if (changeSummaryObj == null || (changeSummaryObj != null
					&& !changeSummaryObj.getAttrValue().equals(attrObj.getValues().getNewval()))) {
				if (!attrObj.getValues().getNewval().equals(attrObj.getValues().getSBL().split("##")[0])) {

					changeSummaryObj = new ChangeSummary();
					changeSummaryObj.setDfrId(changeSummaryDto.getDfrId());
					changeSummaryObj.setDfrLineId(changeSummaryDto.getDfrLineId());
					changeSummaryObj.setCreatedDate(new Date());
					changeSummaryObj.setAttrName(attrObj.getDisplayName());
					changeSummaryObj.setAttrValue(attrObj.getValues().getNewval());
					if (attrObj.getValues().getSBL().contains("##")) {
						changeSummaryObj.setOldValue(attrObj.getValues().getSBL().split("##")[0]);
					} else {
						changeSummaryObj.setOldValue(attrObj.getValues().getSBL());
					}
					changeSummaryObj.setAssetNum(changeSummaryDto.getAssetNum());
					changeSummaryObj.setProductName(changeSummaryDto.getProductName());
					changeSummaryObj.setUserId(UserThreadLocal.userThreadLocalVar.get().getUserId());
					return changeSummaryObj;
				}
			}
		}
		return null;
	
	}

	@Override
	public String resetAndDelete(ResetAndDelete resetAndDelete) throws Exception {
		StringBuffer sb =new  StringBuffer();
		if(CollectionUtils.isNotEmpty(resetAndDelete.getResetData())){
			for(Reset resetData : resetAndDelete.getResetData()){
				String dfrLineId  = resetData.getDfrLineId();
				AssetNewVal assetNewVal =  editDfrDao.getAssetNewValByDfrLineId(dfrLineId);
				if(assetNewVal!=null){
					Statement stmt = null;
					for(String propname : resetData.getKeys()){
						propname = CacheConstant.getHeaderMappedAttrEntityProperty(propname);
						if(StringUtils.isNotEmpty(propname)&&( propname.contains("header")|| propname.contains("attr"))){
							String setMethodName = "set"+StringUtils.capitalize(propname);
							stmt = new Statement(assetNewVal, setMethodName, new Object[] { BLANK });
							stmt.execute();
						}
					}
					appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
					sb.append("Reset done for DFR Line ID: " + assetNewVal.getDfrLineId()).append(",");
				}else {
					sb.append("During Reset, Asset New Val Entity not found for DFR Line ID: " + dfrLineId).append(",");
				}
			}
		}
		/*if(CollectionUtils.isNotEmpty(resetAndDelete.getDeleteData())){
			for(String dfrLineId : resetAndDelete.getDeleteData()){
				if(!dfrLineId.contains("-")){
					AssetNewVal assetNewVal =  editDfrDao.getAssetNewVal(dfrLineId);
					SnapshotSiebelAssetDa snapshotSiebelAssetDa = editDfrDao.getSnapshotSblAssetDaData(dfrLineId);
					sb.append("[ DFR LINE ID :" +dfrLineId);
					if(assetNewVal !=null){
						editDfrDao.deleteAssetNewVal(assetNewVal.getDfrLineId());
						sb.append("[AssetNewVal Deleted ,");
					}
					if(snapshotSiebelAssetDa != null){
						editDfrDao.deleteSnapshotSiebelAsseDa(snapshotSiebelAssetDa.getDfrLineId());
						sb.append("SBL Deleted] ],");
					}
				}
			}
		}*/
		return sb.toString();
	}
	
	@Override
	 public String resetAndDelete(String dfrLineId) throws Exception{
		 StringBuffer sb =new  StringBuffer();
		 AssetNewVal assetNewVal =  editDfrDao.getAssetNewValByDfrLineId(dfrLineId);
		 Class curClass = AssetNewVal.class;
		 Method[] allMethods = curClass.getMethods();
		 List<String> setters = new ArrayList<String>();
		 for(Method method : allMethods) {
			if(method.getName().startsWith("set")) {
				if(null != method && !isValidEnum(method.getName())){
					setters.add(method.getName());
				}
			}
		 }
		if(assetNewVal!=null && null !=assetNewVal.getDfrLineId() ){
			if( assetNewVal.getDfrLineId().contains("-")){
					Statement stmt = null;
					 for(String method : setters){
						stmt = new Statement(assetNewVal, method, new Object[] { BLANK });
							stmt.execute();
					}
					 assetNewVal.setAttr350(BLANK);
					appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
					sb.append("Reset done for DFR Line ID: " + assetNewVal.getDfrLineId()).append(",");
				}else{
					editDfrDao.deleteAssetNewVal(assetNewVal.getDfrLineId());
					editDfrDao.deleteSnapshotSiebelAsseDa(assetNewVal.getDfrLineId());
				}
			}else {
				sb.append("During Reset, Asset New Val Entity not found for DFR Line ID: " + dfrLineId).append(",");
			}
		return sb.toString();
			
	 }

	@Override
	public ProductDataGrid getAccountMoveAttributeView(String dfrId) {

		try {
			
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			productFilterResult = editDfrDao.getAccountMoveAttributeView(dfrId, productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
					}
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					if(productAttrMap!=null) {
						for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
							setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
						}
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
					if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorColl.forEach( errorObj -> {
							if (StringUtils.isNotEmpty(errorBuffer)) {
								errorBuffer.append(",");
							}
							errorBuffer.append(errorObj.getErrorCode());
						});
						if (StringUtils.isNotEmpty(errorBuffer)) {
							productJson.setDqmErrorCodes(errorBuffer.toString());
						}
					}
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	
	}


	@Override
	public void saveDependentAttributesForAccountMove(Set<SnapshotSiebelAssetDa> sblSet, Set<AssetNewVal> newValSet) throws Exception {
		for(SnapshotSiebelAssetDa sbl : sblSet){
		  AssetNewVal newVal =	newValSet.stream()
			.filter(item-> item.getDfrLineId().equalsIgnoreCase(sbl.getDfrLineId()))
			.collect(Collectors.collectingAndThen(
		            Collectors.toList(),
		            list -> {
		                if (list.size() != 1) {
		                    throw new IllegalStateException();
		                }
		                return list.get(0);
		            }
		    ));
		  if(sbl!=null && newVal!=null){
	    		HashMap<String,SortedMap<Integer,AttributeConfig>> attrMapByProduct = appOpsDartDaDao.getPhysicalConfigProductAttrMap();
	        	SortedMap <Integer , AttributeConfig > headerMap = appOpsDartDaDao.getPhysicalConfigMobileHeaderMap();
	        	logger.info("Product: " + sbl.getHeader20()+", dfrLineId: "+ sbl.getDfrLineId() +", dfrId :"+ sbl.getDfrId());
	        	Set<String> attrFamilies=new HashSet<>();
	        	SortedMap <Integer , AttributeConfig > attrMap = attrMapByProduct.get(sbl.getHeader20());
	        	String dfrLineId = sbl.getDfrLineId();
	        	String dfrId = sbl.getDfrId();
	        	String product = sbl.getHeader20();
	        	for(Map.Entry<Integer, AttributeConfig> entry : headerMap.entrySet()){
	        		populateAttributeFamilyForDragDrop(sbl, newVal, attrFamilies, dfrLineId, dfrId, product, entry);
	        	}
	        	for(Map.Entry<Integer, AttributeConfig> entry : attrMap.entrySet()){
	        		populateAttributeFamilyForDragDrop(sbl, newVal, attrFamilies, dfrLineId, dfrId, product, entry);
	        	
	        	}
	        	if(CollectionUtils.isNotEmpty(attrFamilies)){
	    			runDependentAttributes(attrFamilies,dfrId);
	    		}else{
	    			logger.info("Attibute Family collction found empty");
	    		}
	    	}else{
	    		logger.info("Either sbl or newVal is null");
	    	}
		}
	}
	
	private void populateAttributeFamilyForDragDrop(SnapshotSiebelAssetDa sbl, AssetNewVal newVal, Set<String> attrFamilies,
			String dfrLineId, String dfrId, String product, Map.Entry<Integer, AttributeConfig> entry) throws Exception {
		AttributeConfig headerAttrConfig = entry.getValue();
		String sblValue = getSblVal(headerAttrConfig, sbl);
		String newValue = getNewVal(headerAttrConfig, newVal);
		String attrName = headerAttrConfig.getAttrName();
		String dataType = headerAttrConfig.getDataType();
		if(headerAttrConfig!=null /*&& isDisplay(headerAttrConfig)*/){
			try{
				if(StringUtils.isNotEmpty(newValue) 
						&& StringUtils.isNotEmpty(newValue.trim())
						&& !newValue.equalsIgnoreCase("?")
						&& dfrLineId.contains("-")
						&& StringUtils.isNotEmpty(sblValue)
						&& !sblValue.equalsIgnoreCase(newValue) ){
					String attributeFamily=getAttributeFamily(product,attrName,dfrId,dataType);
					if(StringUtils.isNotEmpty(attributeFamily)){
						logger.info("Attr Family : " + attributeFamily + ", attrName : " + attrName 
								+ ",newValue:"	+ newValue + ",sblValue " + sblValue  );
						attrFamilies.add(attributeFamily);
					}
				}else if(StringUtils.isNotEmpty(newValue) 
						&& StringUtils.isNotEmpty(newValue.trim())
						&& !newValue.equalsIgnoreCase("?") 
						&& !dfrLineId.contains("-")){
					String attributeFamily=getAttributeFamily(product,attrName,dfrId,dataType);
					if(null!=attributeFamily && !BLANK.equalsIgnoreCase(attributeFamily)){
						logger.info("Attr Family : " + attributeFamily + ", attrName : " + attrName 
								+ ",newValue:"	+ newValue + ",sblValue " + sblValue  );
						attrFamilies.add(attributeFamily);
					}
				}else{
					if(newValue==null)
						logger.info(attrName+ " attr.getValues() is null so by passing validateDependentAttributes ");
					else 
						logger.info(attrName+ " new val received '?' or empty so by passing validateDependentAttributes");
				}
				String attributeFamily ="";
	        	if(newVal!=null && StringUtils.isNotEmpty(newVal.getHeader12())){
					 attributeFamily = 	getAttributeFamily(product,"Cabinet Unique Space Val",dfrId,"header");
					if(StringUtils.isNotEmpty(attributeFamily)){
						attrFamilies.add(attributeFamily);
					}
	        	}
				if(newVal!=null && StringUtils.isNotEmpty(newVal.getHeader10())){
					attributeFamily = 	getAttributeFamily(product,"Cage Unique Space Val",dfrId,"header");
					if(StringUtils.isNotEmpty(attributeFamily)){
						attrFamilies.add(attributeFamily);
					}
				}
			}catch(Exception e){
				logger.info("Error occured while validating dependent attribute : "  + product + "/" +attrName+"/"+dfrId+"/"+dataType, e);
				throw e;

			}
		}
	}
	
	private String getSblVal(AttributeConfig attrConfig, SnapshotSiebelAssetDa sbl) throws Exception{
        String propname = attrConfig.getHeaderPosition().toLowerCase().replace("_", "");
		propname = CacheConstant.getHeaderMappedAttrEntityProperty(propname);
		String getMethodName = "get"+StringUtils.capitalize(propname);
	    java.beans.Expression expr = new  java.beans.Expression(sbl, getMethodName, new Object[0]);
			expr.execute();
		    if(StringUtils.isNotEmpty((String)expr.getValue())){
				return (String)expr.getValue();
			}else {
			    return null;
			}
	}
	
	 private String getNewVal(AttributeConfig attrConfig, AssetNewVal assetNewVal) throws Exception{
	        String propname = attrConfig.getHeaderPosition().toLowerCase().replace("_", "");
			propname = CacheConstant.getHeaderMappedAttrEntityProperty(propname);
			String getMethodName = "get"+StringUtils.capitalize(propname);
		    java.beans.Expression expr = new  java.beans.Expression(assetNewVal, getMethodName, new Object[0]);
				expr.execute();
			    if(StringUtils.isNotEmpty((String)expr.getValue())){
					return (String)expr.getValue();
				}else {
				    return null;
				}
		}
	 
	 @Override
	 public String resetAttributeView(String dfrId, String product) throws Exception{
		 StringBuffer sb =new  StringBuffer();
		 List<AssetNewVal> assetNewValList =  editDfrDao.getAssetNewValByDfrIdAndProd(dfrId, product);
		 Class curClass = AssetNewVal.class;
		 Method[] allMethods = curClass.getMethods();
		 List<String> setters = new ArrayList<String>();
		 for(Method method : allMethods) {
			if(method.getName().startsWith("set")) {
				if(null != method && !isValidEnum(method.getName())){
					setters.add(method.getName());
				}
			}
		 }
		 for(AssetNewVal assetNewVal: assetNewValList){
			if(assetNewVal!=null && null !=assetNewVal.getDfrLineId() ){
				if( assetNewVal.getDfrLineId().contains("-")){
					Statement stmt = null;
					 for(String method : setters){
						stmt = new Statement(assetNewVal, method, new Object[] { BLANK });
							stmt.execute();
					}
					appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
					sb.append("Reset done for DFR Line ID: " + assetNewVal.getDfrLineId()).append(",");
				}else{
					editDfrDao.deleteAssetNewVal(assetNewVal.getDfrLineId());
					editDfrDao.deleteSnapshotSiebelAsseDa(assetNewVal.getDfrLineId());
				}
			}else {
				sb.append("During Reset, Asset New Val Entity not found for DFR ID: " + dfrId).append(",");
			}
		}
		 return sb.toString();
			
	 }
	 
	 public static boolean isValidEnum(String test) {
		return EnumUtils.isValidEnum(NonResetAssetValVar.class, test);
	}
	
	private static final List<String> ignoreMethods = Arrays.asList(new String[]{
	"getDfrId",
	"getDfrLineId",
	"getHeader57",
	"getHeader1",
	"getHeader20",
	"getHeader34",
	"getHeader36",
	"getHeader55",
	"getHeader17",
	"getDisplayFlag",
	"getNotNull"
	});
	 
	 @Override
	 public boolean validateSaveAssetNewValuesByDfrId(ProductFilter filter) throws Exception {
		 List<AssetNewVal> assetNewVals = editDfrDao.getEditedAssets(filter.getDfrId());
		 List<SnapshotSiebelAssetDa> siebeldata = editDfrDao.getSnapshotSiebleAssetDaData(filter);
		 for(SnapshotSiebelAssetDa siebel : siebeldata){
			 AssetNewVal val = assetNewVals.stream().filter(asset-> asset.getDfrLineId().equals(siebel.getDfrLineId()))
					 .findAny().orElse(null);
			 if(val != null){
				Method[] methods = AssetNewVal.class.getDeclaredMethods();
				for(Method method : methods){
					if(method.getName().contains("get")&&!ignoreMethods.contains(method.getName())){
						logger.info(" validate method name : " + method.getName());
						String newValue = (String) executeMethod(AssetNewVal.class, method.getName(), val);
						String snapValue = (String) executeMethod(SnapshotSiebelAssetDa.class, method.getName(), siebel);
						if(StringUtils.isNotEmpty(newValue) && StringUtils.isNotEmpty(snapValue)){
						     if(!newValue.equalsIgnoreCase(snapValue)){
						    	 return true;
						     }
						}
						if(StringUtils.isNotEmpty(newValue) && StringUtils.isEmpty(snapValue)){
							return true;
						}
						
						/*if(!(newValue==null && snapValue==null)){
							if((newValue != null) && (!newValue.equals(snapValue))){
								return true;
							}
							if((snapValue != null) && (!snapValue.equals(newValue))){
								return true;
							}
						}*/
					}
				}
			 }
		 }
		 return false;
		}
	 
	 
	 private Object executeMethod(Class className, String methodName, Object target) {
		 Method method;
		 Object dd;
		try {
			method = className.getDeclaredMethod(methodName, null);
			dd = (Object)method.invoke(target,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 
		 return dd;
	 }
	 
	@Override
	public ProductDataGrid getSelectedProductAttributeView(ProductFilter productFilter) {
		try {
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			/*
			 * if (productFilter.isErrorCodeGlobalFilterKeyword()) { if
			 * (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
			 * productFilter.getFilters().addAll(
			 * getProductFilterForErrorCodeGlobalFilter(productFilter).
			 * getFilters()); } else {
			 * productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter
			 * (productFilter).getFilters()); }
			 * productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter
			 * (productFilter).getFilters()); }
			 */
			productFilterResult = editDfrDao.getProductFilterResult(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			List<SnapshotSiebelAssetDa> sblDaList2 = new ArrayList<>();
			List<SnapshotClxAssetDa> clxDaList2 = new ArrayList<>();
			List<SnapshotSvAssetDa> svDaList2 = new ArrayList<>();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			if (StringUtils.isNotEmpty(productFilter.getApplications())
					|| StringUtils.isNotBlank(productFilter.getApplications())) {
				if (productFilter.getApplications().equalsIgnoreCase("sbl")) {
					List<String> searchValues = productFilter.getSelectedAttribute();
					sblDaList2 = ServiceUtil.filterSnapshotSiebelAssetDa(sblDaList, searchValues, productFilter);
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotSiebelAssetDa>> siebelAssetDas = getPages(sblDaList2, page);
					sblDaList = siebelAssetDas.get(0);
				} else if (productFilter.getApplications().equalsIgnoreCase("clx")) {
					List<String> searchValues = productFilter.getSelectedAttribute();
					clxDaList2 = ServiceUtil.filterSnapshotClxAssetDa(clxDaList, searchValues, productFilter);
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotClxAssetDa>> clxAssetDas = getPages(clxDaList2, page);
					clxDaList = clxAssetDas.get(0);
				} else if (productFilter.getApplications().equalsIgnoreCase("sv")) {
					List<String> searchValues = productFilter.getSelectedAttribute();
					svDaList2 = ServiceUtil.filterSnapshotSvAssetDa(svDaList, searchValues, productFilter);
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotSvAssetDa>> svAssetDas = getPages(svDaList2, page);
					svDaList = svAssetDas.get(0);
				}
			} else {
				List<String> searchValues = productFilter.getSelectedAttribute();
				sblDaList2 = ServiceUtil.filterSnapshotSiebelAssetDa(sblDaList, searchValues, productFilter);
				if (productFilter.getShowAll().equalsIgnoreCase("true")
						&& StringUtils.isNotEmpty(productFilter.getShowAll())
						&& StringUtils.isNotBlank(productFilter.getShowAll())) {
					sblDaList = sblDaList2;

				} else if (productFilter.getShowAll().equalsIgnoreCase("false")
						&& StringUtils.isNotEmpty(productFilter.getShowAll())
						&& StringUtils.isNotBlank(productFilter.getShowAll())) {
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotSiebelAssetDa>> siebelAssetDas = getPages(sblDaList2, page);
					sblDaList = siebelAssetDas.get(0);
				}
				dataGridJson.setTotalrows((long) sblDaList2.size());
			}
			if (productFilter.getOrderBy().equalsIgnoreCase("DESC")) {
				Collections.reverse(sblDaList);
				Collections.reverse(svDaList);
				Collections.reverse(clxDaList);
			}

			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			/*
			 * List<SnapshotSiebelAssetDa> sblDaList =
			 * editDfrDao.getSnapshotSiebleAssetDaData(productFilter);
			 * List<SnapshotClxAssetDa> clxDaList =
			 * editDfrDao.getSnapshotClxAssetDaData(productFilter);
			 * List<SnapshotSvAssetDa> svDaList =
			 * editDfrDao.getSnapshotSvAssetDaData(productFilter);
			 */
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			
			/*if (productFilter.getApplications().equalsIgnoreCase("sbl")) {
				dataGridJson.setTotalrows((long) sblDaList2.size());
			} else if (productFilter.getApplications().equalsIgnoreCase("sv")) {
				dataGridJson.setTotalrows((long) svDaList2.size());
			} else if (productFilter.getApplications().equalsIgnoreCase("clx")) {
				dataGridJson.setTotalrows((long) clxDaList2.size());
			}*/
			
			dataGridJson.setTotalrows((long)sblDaList2.size());
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {

					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					if (productFilter.getApplications().equalsIgnoreCase(DartConstants.SBL)) {

						productJson.setName(sbl.getHeader20());
						productJson.setDfrlineid(sbl.getDfrLineId());
						for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
							setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
						}
						SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap
								.get(sbl.getHeader20());
						for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
							setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
						}

					} else if (productFilter.getApplications().equalsIgnoreCase(DartConstants.CLX)) {
						if (clx != null) {
							if (sv != null) {
								productJson.setName(sbl.getHeader20());
								productJson.setDfrlineid(sbl.getDfrLineId());
								for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
									setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter,
											assetNewValMap);
								}
								SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap
										.get(sbl.getHeader20());
								for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
									setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter,
											assetNewValMap);
								}
							}
						}

					} else if (productFilter.getApplications().equalsIgnoreCase(DartConstants.SV)) {
						if (sv != null) {
							productJson.setName(sbl.getHeader20());
							productJson.setDfrlineid(sbl.getDfrLineId());
							for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
								setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
							}
							SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap
									.get(sbl.getHeader20());
							for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
								setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
							}
						}

		}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
					if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorColl.forEach( errorObj -> {
							if (StringUtils.isNotEmpty(errorBuffer)) {
								errorBuffer.append(",");
							}
							errorBuffer.append(errorObj.getErrorCode());
						});
						if (StringUtils.isNotEmpty(errorBuffer)) {
							productJson.setDqmErrorCodes(errorBuffer.toString());
						}
					}

				}
				if (productJson.getName() != null && productJson.getAttributes().size() > 0) {
					dataGridJson.getProducts().add(productJson);
		}

	}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	}
	 
	private static <T> List<List<T>> getPages(Collection<T> collection, Integer pageSize) {
		List<T> list = new ArrayList<>(collection);
		List<List<T>> finalList = new ArrayList<List<T>>();

		finalList.add(list.subList((pageSize - 1) * CacheConstant.PAGE_SIZE,
				Math.min(CacheConstant.PAGE_SIZE * pageSize, list.size())));
		return finalList;
	}
	
	private List<SnapshotSiebelAssetDa> sortSblProductWise(List<SnapshotSiebelAssetDa> sblList, String products) {
		List<SnapshotSiebelAssetDa> siebelAssetDas = new ArrayList<>();
		List<String> prods = new ArrayList<>();
		if(products.contains(",")){
			String[] strs = products.split(",");
			for(String s : strs){
				prods.add(s);
			}
		}else{
			prods.add(products);
		}
		if (CollectionUtils.isNotEmpty(sblList)&& CollectionUtils.isNotEmpty(prods)) {
			for(String prod : prods){
				for (SnapshotSiebelAssetDa siebelAssetDa : sblList) {
					if (siebelAssetDa.getHeader20().equalsIgnoreCase(prod)) {
						siebelAssetDas.add(siebelAssetDa);
					}
				}
			}
		}
		return siebelAssetDas;
	}
	
	@Override
	public ProductDataGrid getSelectedProductPysicalAuditAttributeView(ProductFilter productFilter) {
		try {
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			productFilterResult = editDfrDao.getProductFilterResult(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			/**
			 * Sort selected assets from whole records
			 */
			List<SnapshotSiebelAssetDa> sblDaList2 = new ArrayList<>();
			List<SnapshotClxAssetDa> clxDaList2 = new ArrayList<>();
			List<SnapshotSvAssetDa> svDaList2 = new ArrayList<>();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			if (StringUtils.isNotEmpty(productFilter.getApplications())
					|| StringUtils.isNotBlank(productFilter.getApplications())) {
				if (productFilter.getApplications().equalsIgnoreCase("sbl")) {
					List<String> searchValues = productFilter.getSelectedAttribute();
					sblDaList2 = ServiceUtil.filterSnapshotSiebelAssetDa(sblDaList, searchValues, productFilter);
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotSiebelAssetDa>> siebelAssetDas = getPages(sblDaList2, page);
					sblDaList = siebelAssetDas.get(0);
				} else if (productFilter.getApplications().equalsIgnoreCase("clx")) {
					List<String> searchValues = productFilter.getSelectedAttribute();
					clxDaList2 = ServiceUtil.filterSnapshotClxAssetDa(clxDaList, searchValues, productFilter);
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotClxAssetDa>> clxAssetDas = getPages(clxDaList2, page);
					clxDaList = clxAssetDas.get(0);
				} else if (productFilter.getApplications().equalsIgnoreCase("sv")) {
					List<String> searchValues = productFilter.getSelectedAttribute();
					svDaList2 = ServiceUtil.filterSnapshotSvAssetDa(svDaList, searchValues, productFilter);
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotSvAssetDa>> svAssetDas = getPages(svDaList2, page);
					svDaList = svAssetDas.get(0);
				}
			} else {
				List<String> searchValues = productFilter.getSelectedAttribute();
				sblDaList2 = ServiceUtil.filterSnapshotSiebelAssetDa(sblDaList, searchValues, productFilter);
				if (productFilter.getShowAll().equalsIgnoreCase("true")
						&& StringUtils.isNotEmpty(productFilter.getShowAll())
						&& StringUtils.isNotBlank(productFilter.getShowAll())) {
					sblDaList = sblDaList2;

				} else if (productFilter.getShowAll().equalsIgnoreCase("false")
						&& StringUtils.isNotEmpty(productFilter.getShowAll())
						&& StringUtils.isNotBlank(productFilter.getShowAll())) {
					int page = productFilter.getPageNumber().intValue();
					List<List<SnapshotSiebelAssetDa>> siebelAssetDas = getPages(sblDaList2, page);
					sblDaList = siebelAssetDas.get(0);
				}
				dataGridJson.setTotalrows((long) sblDaList2.size());
			}
			if (productFilter.getOrderBy().equalsIgnoreCase("DESC")) {
				Collections.reverse(sblDaList);
				Collections.reverse(svDaList);
				Collections.reverse(clxDaList);
			}
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + 	svDaList.size());
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getPhysicalConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getPhysicalConfigProductAttrMap();
			dataGridJson.setTotalrows((long)sblDaList2.size());
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					setPhysicalHeaders(configHeaderMap, clx, sv, sbl, productJson, assetNewValMap);
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					if(productAttrMap!= null){
						setPhysicalAttributes(clx, sv, sbl, productJson, productAttrMap, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
					if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorColl.forEach( errorObj -> {
							if (StringUtils.isNotEmpty(errorBuffer)) {
								errorBuffer.append(",");
							}
							errorBuffer.append(errorObj.getErrorCode());
						});
						if (StringUtils.isNotEmpty(errorBuffer)) {
							productJson.setDqmErrorCodes(errorBuffer.toString());
						}
					}
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;
		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
			}
			}
			
	@Override
	public List<String> getProductPysicalAuditAttributeViewFilter(ProductFilter productFilter) {
		List<String> commonList = new ArrayList<String>();
		try {
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			productFilterResult = editDfrDao.getProductFilterResult(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();

			/**
			 * Json Logic
			 */

			if (productFilter.getApplications().equalsIgnoreCase("sbl")) {

				for (SnapshotSiebelAssetDa sbl : sblDaList) {

					String val;
					if (sbl != null) {
						val = ServiceUtil.getSblHeaderValue(productFilter.getHeader(), sbl);
						if (val.contains(productFilter.getSearchValue())) {
							commonList.add(val);
						}
		}
	}

			} else if (productFilter.getApplications().equalsIgnoreCase("clx")) {
				for (SnapshotClxAssetDa clx : clxDaList) {

					String val;
					if (clx != null) {
						val = ServiceUtil.getClxHeaderValue(productFilter.getHeader(), clx);
						if (val.contains(productFilter.getSearchValue())) {
							commonList.add(val);
						}
					}

				}

			} else if (productFilter.getApplications().equalsIgnoreCase("sv")) {
				for (SnapshotSvAssetDa sv : svDaList) {

					String val;
					if (sv != null) {
						val = ServiceUtil.getSvHeaderValue(productFilter.getHeader(), sv);
						if (val.contains(productFilter.getSearchValue())) {
							commonList.add(val);
						}
					}

				}
			}

			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());

			logger.info("Attribute View processing end ");
			return commonList;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}

		}
	
	@Override
	public String deleteAssetsByDfrLineIds(String dfrID,List<String> dfrLineIds){
		 StringBuffer sb =new  StringBuffer();
		 editDfrDao.deleteCxiErrTblByDfrLineIds(dfrLineIds);
		 editDfrDao.deleteSnapshotSiebelAsseDaByDfrLineIds(dfrLineIds);
		 editDfrDao.deleteAssetNewValByDfrLineIds(dfrLineIds);
		 List<AssetNewVal> assetNewVals = editDfrDao.getEditedAssets(dfrID);
		 if(null!=assetNewVals && assetNewVals.size()>0){
			 sb.append("Asset New Val and Snapshot table entries deleted for " + dfrLineIds).append(",");
		 }else{
			 
			String fetchMode="select";
			DfrMaster dfrMaster=editDfrDao.getDfrMaster(dfrID,fetchMode);
			dfrMaster.setStatus("Cancelled");
			dfrMaster.setNotes("All DFR Line Ids deleted");
			appOpsInitDartDaDao.saveOrUpdateDfrMaster(dfrMaster);
			ApprovalHistory approvalHistory = getApprovalHistory(dfrMaster, LogicConstants.DFR_CANCEL,dfrMaster.getNotes());
			saveOrUpdateApprovalHistory(approvalHistory);
			sb.append("Asset New Val and Snapshot table entries deleted for " + dfrLineIds).append(",");
			sb.append("DFR Master entry Cancelled for " + dfrID).append(",");
		 }
		 
		 return sb.toString();
	}
	
	@Override
	public String getNewPOEAssetNumber(){
		return  editDfrDao.getNewPOEAssetNumber();
	}
	
	@Override
	public List<String> getFilterListFromSnapshotDA(String header,String dfrId){
		return  editDfrDao.getFilterListFromSnapshotDA(header,dfrId);
	}

	@Override
	 public String resetAssetsByDfrLineIds(Set<String> dfrLineIds) throws Exception{
		 StringBuffer sb =new  StringBuffer();
		 
		 List<AssetNewVal> assetNewValList = editDfrDao.getAssetNewValBySblRowIds(dfrLineIds);
		 for(AssetNewVal assetNewVal : assetNewValList){
			 Class curClass = AssetNewVal.class;
			 Method[] allMethods = curClass.getMethods();
			 List<String> setters = new ArrayList<String>();
			 for(Method method : allMethods) {
				if(method.getName().startsWith("set")) {
					if(null != method && !isValidEnum(method.getName())){
						setters.add(method.getName());
					}
				}
			 }
			if(assetNewVal!=null && null !=assetNewVal.getDfrLineId() ){
				if( assetNewVal.getDfrLineId().contains("-")){
						Statement stmt = null;
						 for(String method : setters){
							stmt = new Statement(assetNewVal, method, new Object[] { BLANK });
								stmt.execute();
						}
						appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
						sb.append("Reset done for DFR Line ID: " + assetNewVal.getDfrLineId()).append(",");
					}else{
						editDfrDao.deleteAssetNewVal(assetNewVal.getDfrLineId());
						editDfrDao.deleteSnapshotSiebelAsseDa(assetNewVal.getDfrLineId());
					}
				}else {
					sb.append("During Reset, Asset New Val Entity not found for DFR Line ID: ");
				}
		 }
		 return sb.toString();
	}
	
	@Override
	public  List<ErrorCodeVO> validationsOnSubmit(String dfrId) throws Exception{
		return editDfrDao.validationsOnSubmit(dfrId);
	}

	@Override
	public ProductDataGrid autoValidate(ProductFilter productFilter) {
		try {
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			
			DfrMaster dfrMaster = editDfrDao.getDfrMaster(productFilter.getDfrId(), "join");
			String snapshot = dfrMaster.getSsFilter();
			Gson gson = new Gson();
			ProductFilter filter = gson.fromJson(snapshot, ProductFilter.class);
			productFilterResult.setProductFilter(filter);
			productFilterResult = editDfrDao.getAllProductFilterResult(productFilterResult);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
					}
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
						setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
					if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorColl.forEach( errorObj -> {
							if (StringUtils.isNotEmpty(errorBuffer)) {
								errorBuffer.append(",");
							}
							errorBuffer.append(errorObj.getErrorCode());
						});
						if (StringUtils.isNotEmpty(errorBuffer)) {
							productJson.setDqmErrorCodes(errorBuffer.toString());
						}
					}
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	}

	@Override
	public ProductDataGrid productAttributeViewByLineId(SaveAssetForm data) {
		try {
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
			long start = System.currentTimeMillis();
			productFilterResult = editDfrDao.productFilterResultByLineId(data);
			long end  = System.currentTimeMillis();
			long total = start-end;
			logger.info("Total time of getProductFilterResult "+total);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
					}
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
						setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					productJson.setDqmErrorCodes(sbl.getDqmErrorDescription()!= null?sbl.getDqmErrorDescription():DartConstants.BLANK);
					productJson.setFixedValCodes(sbl.getFixedValCodes()!= null?sbl.getFixedValCodes():DartConstants.BLANK);
					productJson.setRedAttrNames(sbl.getRedAttrNames()!= null?sbl.getRedAttrNames():DartConstants.BLANK);
					productJson.setGreenAttrNames(sbl.getGreenAttrNames()!= null?sbl.getGreenAttrNames():DartConstants.BLANK);
					productJson.setRedRowIdentifier(sbl.getRedRowIdentifier()!= null?sbl.getRedRowIdentifier():DartConstants.BLANK);
					productJson.setGreenRowIdentifier(sbl.getGreenRowIdentifier()!= null?sbl.getGreenRowIdentifier():DartConstants.BLANK);
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	}

	@Override
	public List<ErrorCardVO> getTotalErrorAssetById(String dfrId) {

		return editDfrDao.getTotalErrorAssetById(dfrId);
	}

	@Override
	public List<String> getChangesMadeById(String dfrId) {
		return editDfrDao.getChangesMadeById(dfrId);
	}
	
	@Override
	public List<String> getErrorListByKpi(ProductFilter productFilter){
		List<String> errorResp= new ArrayList<>(); 
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter(productFilter).getFilters());
		}
		List<SnapshotErrorData> errorList =editDfrDao.getErrorSectionJdbc(productFilter);
			
		Map<String, List<SnapshotErrorData>> mapByErrorCode = errorList.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getErrorCode));
		mapByErrorCode.forEach((errorCode,list)->{
//			Error error = new Error(); 
			
			Map<String, List<SnapshotErrorData>> mapByAsset = list.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
			/*Map<String, List<SnapshotErrorData>> mapByOpenAsset = list.stream()
					.filter(item->(item.getAssetNum()!=null 
					&& StringUtils.isNotEmpty(item.getStatusCd()) 
					&& null!=item.getStatusCd() && item.getStatusCd().toLowerCase().equalsIgnoreCase("open")))
					.collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));
			
			Map<String, List<SnapshotErrorData>> mapByValidNotClosed = list.stream()
					.filter(item->(item.getAssetNum()!=null 
					&& ( StringUtils.isEmpty(item.getValidStat()) 
							|| null==item.getValidStat() || !item.getValidStat().toLowerCase().equalsIgnoreCase("closed")
						)
					))
					.collect(Collectors.groupingBy(SnapshotErrorData::getAssetNum));*/
			
			StringBuilder rowId = new StringBuilder();
			for (Map.Entry<String, List<SnapshotErrorData>> entry : mapByAsset.entrySet()) {
				
				for(SnapshotErrorData errorRecord : entry.getValue()){
					rowId.append(errorRecord.getTbl()+"##"+errorRecord.getRowId()+"##"+errorRecord.getValidStat()).append(",");
				}
			}
//			error.setPkid(rowId.substring(0,rowId.lastIndexOf(",")));
			errorResp.add(rowId.substring(0,rowId.lastIndexOf(",")));
		});
		
					
		return errorResp;
	}

	@Override
	public List<POECountVO> getPOECountByFilters(ProductFilter productFilter) {
	
		return editDfrDao.getPOECountByFilters(productFilter);
	}

	@Override
	public ProductDataGrid getInitiateProductAttributeView(ProductFilter productFilter) {

		try {
//			validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
//			String key = productFilter.genrateCacheKey();
//			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			/*
			 * if (productFilter.isErrorCodeGlobalFilterKeyword()) { if
			 * (CollectionUtils.isNotEmpty(productFilter.getFilters())) {
			 * productFilter.getFilters().addAll(
			 * getProductFilterForErrorCodeGlobalFilter(productFilter).
			 * getFilters()); } else {
			 * productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter
			 * (productFilter).getFilters()); }
			 * productFilter.setFilters(getProductFilterForErrorCodeGlobalFilter
			 * (productFilter).getFilters()); }
			 */
			long start = System.currentTimeMillis();
			productFilterResult = editDfrDao.getAllProductFilterResult(productFilterResult);
			long end  = System.currentTimeMillis();
			long total = start-end;
			logger.info("Total time of getProductFilterResult "+total);
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			/*
			 * List<SnapshotSiebelAssetDa> sblDaList =
			 * editDfrDao.getSnapshotSiebleAssetDaData(productFilter);
			 * List<SnapshotClxAssetDa> clxDaList =
			 * editDfrDao.getSnapshotClxAssetDaData(productFilter);
			 * List<SnapshotSvAssetDa> svDaList =
			 * editDfrDao.getSnapshotSvAssetDaData(productFilter);
			 */
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			dataGridJson.setTotalrows(productFilter.getTotalRows());
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
					}
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
						setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					List<CxiErrorTbl> errorColl = sbl.getCxiErrorTbls();
					if (errorColl != null && CollectionUtils.isNotEmpty(errorColl)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorColl.forEach( errorObj -> {
							if (StringUtils.isNotEmpty(errorBuffer)) {
								errorBuffer.append(",");
							}
							errorBuffer.append(errorObj.getErrorCode());
						});
						if (StringUtils.isNotEmpty(errorBuffer)) {
							productJson.setDqmErrorCodes(errorBuffer.toString());
						}
					}
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	
	}

	@Override
	public List<String> getNewErrorCountById(String dfrId) {
		return editDfrDao.getNewErrorCountById(dfrId);
	}

	@Override
	public ProductDataGrid getAllProductAttributeView(ProductFilter productFilter) {

		try {
			SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();

			productFilterResult.setProductFilter(productFilter);
			
			productFilterResult = editDfrDao.getAllProductFilterResult(productFilterResult);
			
			List<SnapshotSiebelAssetDa> sblDaList = productFilterResult.getSblList();
			List<SnapshotClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SnapshotSvAssetDa> svDaList = productFilterResult.getSvList();
			Map<String, AssetNewVal> assetNewValMap = productFilterResult.getAssetNewValMap();
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			
			
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
					.getConfigProductAttrMap();
			ProductDataGrid dataGridJson = new ProductDataGrid();
			dataGridJson.setTotalrows(productFilter.getTotalRows());
			SnapshotClxAssetDa clx = null;
			SnapshotSvAssetDa sv = null;
			for (SnapshotSiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					productJson.setAssetNumber(sbl.getHeader2());
					productJson.setDfrlineid(sbl.getDfrLineId());
					if (StringUtils.isNotEmpty(sbl.getHeader60()) && sbl.getHeader60().equalsIgnoreCase("Y")) {
						productJson.setValidate("Y");
					} else {
						productJson.setValidate("N");
					}
					clx = ServiceUtil.getClx(sbl, clxDaList);
					sv = ServiceUtil.getSv(sbl, svDaList);
					for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter, assetNewValMap);
					}
					SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
					for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
						setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter, assetNewValMap);
					}
					productJson.setDqmErrorFlag(sbl.getHeader38());
					productJson.setDqmErrorCodes(sbl.getDqmErrorDescription() != null ? sbl.getDqmErrorDescription():DartConstants.BLANK);
					
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("Attribute View processing end ");
			return dataGridJson;

		} catch (Exception e) {
			logger.error("Error in attribute view ", e);
			return null;
		}
	
	}

	@Override
	public void saveDfrNotes(DfrNotes dfrNotes) {
		
		editDfrDao.saveDfrNotes(dfrNotes);
	}

	@Override
	public List<DfrNotes> getDfrNotes(String dfrId) {
		return editDfrDao.getDfrNotes(dfrId);
	}

	@Override
	public List<DfrLineIdsVo> dfrLineIdListByDfr(String dfrId) {
		return editDfrDao.dfrLineIdListByDfr(dfrId);
	}

	@Override
	public long getNextValueOfSerialNumber() {
		
		return editDfrDao.getNextValueOfSerialNumber();
	}

	@Override
	public HashMap<String, SortedMap<Integer, AttributeConfig>> getPhysicalConfigProductAttrMap() {
		return appOpsDartDaDao.getPhysicalConfigProductAttrMap();
	}

	@Override
	public SortedMap<Integer, AttributeConfig> getPhysicalConfigHeaderMap() {
		
		return appOpsDartDaDao.getPhysicalConfigHeaderMap();
	}

	@Override
	public SnapshotSiebelAssetDa getSnapshotSblAssetDaData(String parentDfrLineId) {
		return editDfrDao.getSnapshotSblAssetDaData(parentDfrLineId);
	}

	@Override
	public long getNextValueOfDFRLineIDSeq() {
		// TODO Auto-generated method stub
		return editDfrDao.getNextValueOfDFRLineIDSeq();
	}

	@Override
	public void saveOrUpdateAssetNewVal(AssetNewVal assetNewVal) {
		appOpsInitDartDaDao.saveOrUpdateAssetNewVal(assetNewVal);
	}

	@Override
	public void saveOrUpdateSnapshotSiebelAssetDa(SnapshotSiebelAssetDa newChild) {
		appOpsInitDartDaDao.saveOrUpdateSnapshotSiebelAssetDa(newChild);
	}

	@Override
	public List<String> checkSystemNameForPhysicalAudit(String dfrId) {
		return editDfrDao.checkSystemNameForPhysicalAudit(dfrId);
	}
	
}
