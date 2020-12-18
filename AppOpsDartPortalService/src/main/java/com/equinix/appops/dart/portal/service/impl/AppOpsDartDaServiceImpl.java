package com.equinix.appops.dart.portal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.common.ServiceUtil;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.dao.AppOpsDartDaDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartElasticDao;
import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorTbl;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.CountryAndIbxVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.model.dfr.Da;
import com.equinix.appops.dart.portal.model.dfr.DfrDaInput;
import com.equinix.appops.dart.portal.model.errorsection.Error;
import com.equinix.appops.dart.portal.model.errorsection.ErrorCategory;
import com.equinix.appops.dart.portal.model.errorsection.ErrorData;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.errorsection.ErrorValue;
import com.equinix.appops.dart.portal.model.grid.Attribute;
import com.equinix.appops.dart.portal.model.grid.Product;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.grid.Values;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyProduct;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchySubproduct;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.Filter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.model.search.product.ProductResp;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.search.product.SearchDrop;
import com.equinix.appops.dart.portal.model.search.product.SearchDropBox;
import com.equinix.appops.dart.portal.model.widget.ProductWidget;
import com.equinix.appops.dart.portal.model.widget.ProductWidgetGroup;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;
import com.equinix.appops.dart.portal.service.AppOpsDartAttrConfigService;
import com.equinix.appops.dart.portal.service.AppOpsDartDaService;
import com.google.gson.Gson;
/**
 * 
 * @author Ankur Bhargava
 *
 */
@Service
@Transactional
public class AppOpsDartDaServiceImpl implements AppOpsDartDaService {

	public static final int MAX_ATTRIBUTES = 324;
	public static final int MAX_HEADERS = 92;
	Logger logger = LoggerFactory.getLogger(AppOpsDartDaService.class);
	private static final String PRODUCT_CAGE = "Cage";
	private static final String PRODUCT_CABINET = "Cabinet";
	private static final String PRODUCT_CABINET_DP = "Demarcation Point";
	
	@Value("${server.servlet.session.timeout}")
    private String applicationTimeout;
	
	@Autowired
	AppOpsDartDaDao appOpsDartDaDao;
	
	@Autowired
	AppOpsInitiateDFRDao initDfrDao; 
	
	@Autowired
	AppOpsDartAttrConfigService appOpsDartAttrConfigService;
	
	@Autowired
	AppOpsDartElasticDao elasticDao;
	
	@Override
	public void test(){
		System.out.println(new Date());
		List<Object> list = appOpsDartDaDao.executeSQL("Select * from EQX_DART.SIEBEL_ASSET_DA");
		System.out.println(list.size());
		System.out.println(new Date());
	}
	
	@Override
	public String getApplicationTimeout(){
		
		if(StringUtils.isNotEmpty(applicationTimeout)){
			return applicationTimeout.toLowerCase().replace("m", "").replace("s", "");
		}else {
			return "0";
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
	public List<Object> getFilters(String filterName, String keyword, String [] filterTable) {		
		List<Object> data= new ArrayList<>();
		String sql =prepareFilterQuery(filterName, keyword, filterTable);
		logger.info(sql);
	//	System.out.println(sql);
		data = appOpsDartDaDao.executeSQL(sql);
		return data;
	}


	private String prepareFilterQuery(String filterName, String keyword, String[] filterTables) {
		String finalSQL="";
		for (String tableName : filterTables) {
			String GETCOLUMNS = "SELECT TABLE_NAME,COLUMN_NAME"
					+ " from ALL_TAB_COLUMNS WHERE OWNER = 'EQX_DART'"
					+ " AND TABLE_NAME IN ('"+tableName+"') "
					+ " AND DATA_TYPE = 'VARCHAR2' ORDER BY ALL_TAB_COLUMNS.COLUMN_NAME DESC";
			List<Object> columns = appOpsDartDaDao.executeSQL(GETCOLUMNS);
			StringBuffer criteria = new StringBuffer();

			for (Object object : columns) {

				Map<String,String> value = (Map<String,String>)object;
				if(criteria.length() == 0){
					criteria.append("EQX_DART.")
					.append(value.get("TABLE_NAME"))
					.append(".")
					.append(value.get("COLUMN_NAME"))
					.append(" ")
					.append("LIKE")
					.append(" ")
					.append("'%")
					.append(keyword)
					.append("%'");
				} else {
					criteria
					.append(" OR ")
					.append("EQX_DART.")
					.append(value.get("TABLE_NAME"))
					.append(".")
					.append(value.get("COLUMN_NAME"))
					.append(" ")
					.append("LIKE")
					.append(" ")
					.append("'%")
					.append(keyword)
					.append("%'");			
				}

			}
			//criteria.append(" AND ROWNUM > 10");
			String sql = "SELECT  DISTINCT "+getColumnNameFromFilterName(tableName,filterName)+" FROM EQX_DART."+tableName+" WHERE "+criteria.toString();
			if(finalSQL.length()==0){
				finalSQL = sql;
			} else {
				finalSQL = finalSQL + " UNION "+sql;	
			}
		}
		
	return finalSQL;
	}	
	private String getColumnNameFromFilterName(String tableName, String filterName) {
		return "EQX_DART."+tableName+"."+filterName;
	}
	@Override
	public SiebelAssetDa getSibelAssetDaByRowId(String rowId) {
		// TODO Auto-generated method stub
		return appOpsDartDaDao.getSibelAssetDaByRowId(rowId) ;
	}
	@Override
	public String test(ProductFilter productFilter){
		String key = productFilter.genrateCacheKey();
		Gson gson = new Gson();
		HashMap<String,String> resultMap = new HashMap<>();
		logger.info(" Test " + key);
		logger.info(" Master query : " +gson.toJson(productFilter, ProductFilter.class) );
		resultMap.put("filter", gson.toJson(productFilter, ProductFilter.class));
		long start = System.currentTimeMillis();
		appOpsDartDaDao.getSiebleAssetDaData(productFilter);
		long end = System.currentTimeMillis();
		resultMap.put("Time consumed : ", (end - start)+"");
		return gson.toJson(resultMap, HashMap.class);
	}

	
	@Override
	public ProductSearchResponse getProductSearchResponse(ProductFilter productFilter) throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		generateCacheKey(productFilter);
		if (DartConstants.IS_ELASTIC_CALL) {
			return appOpsDartDaDao.getProductSearchResponseElastic(productFilter);
		} else {
			List<SiebelAssetDa> sblList = appOpsDartDaDao.getProductFilters(productFilter);
			ProductSearchResponse productSearchResponse = new ProductSearchResponse();
			Map<String, List<SiebelAssetDa>> map = sblList.stream()
					.collect(Collectors.groupingBy(SiebelAssetDa::getHeader20, Collectors.toList()));
			Long cleanCount = 0L;
			Long errorCount = 0L;
			for (Map.Entry<String, List<SiebelAssetDa>> entry : map.entrySet()) {
				ProductResp product = new ProductResp();
				product.setName(entry.getKey());
				cleanCount = 0L;
				errorCount = 0L;
				for (SiebelAssetDa sbl : entry.getValue()) {
					if (sbl != null) {
						if (sbl.getHeader38() == null || sbl.getHeader38().equalsIgnoreCase("Y")) {
							errorCount = errorCount + 1;
						} else if (sbl.getHeader38().equalsIgnoreCase("N")) {
							cleanCount = cleanCount + 1;
						}
					}
				}
				product.setClean(cleanCount);
				product.setError(errorCount);
				product.setTotal(cleanCount + errorCount);
				productSearchResponse.getProducts().add(product);
			}
			productSearchResponse.setTotalRecords(CollectionUtils.isNotEmpty(sblList) ? sblList.size() + "" : "0");
			long end = System.currentTimeMillis();
			logger.info("end time loop----------------------------- " + (end - start));
			return productSearchResponse;
		}
		
	}


	

	@Override
	public ErrorSectionResponse getErrorSctionResponse(ProductFilter productFilter) throws InterruptedException, ExecutionException{
		ErrorSectionResponse errorResp= new ErrorSectionResponse(); 
		long start = System.currentTimeMillis();
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		boolean keywordHasVal = false;
		if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getKeyword().length() == 7 && 
				productFilter.getKeyword().toUpperCase().startsWith("VAL_")) {
			productFilter = getProductFilterForErrorCodeGlobalFilter(productFilter);
			productFilter.setKeyword("");
			keywordHasVal = true;
		}
		List<PFilter> filters = productFilter.getFilters();
		PFilter filter = null;
		for (int fcount = 0; fcount < filters.size(); fcount++) {
			filter = filters.get(fcount);
			if (filter.getLable().toLowerCase().contains("error")) {
				if (CollectionUtils.isNotEmpty(filter.getListOfValues())) {
					keywordHasVal = true;
				}
			}
		}
		
		generateCacheKey(productFilter);
		List<ErrorData> errorList;
		if (DartConstants.IS_ELASTIC_CALL) {
			errorList = appOpsDartDaDao.getErrorSectionElastic(productFilter, keywordHasVal);
		} else {
			errorList = appOpsDartDaDao.getErrorSection(productFilter);
		}
		long end = System.currentTimeMillis();
		logger.info(" getErrorSctionResponse :: Error section db time " + (end - start));
		start = System.currentTimeMillis();	
		Map<String, List<ErrorData>> mapByErrorCode = errorList.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(ErrorData::getErrorCode));
		mapByErrorCode.forEach((errorCode,list)->{
			Error error = new Error(); 
			String valClass="";
			String bizGroup="";
			String errDesc = "";
			Map<String, List<ErrorData>> mapByAsset = list.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(ErrorData::getAssetNum));
			StringBuilder rowId = new StringBuilder();
			for (Map.Entry<String, List<ErrorData>> entry : mapByAsset.entrySet()) {
				
				if(CollectionUtils.isNotEmpty(entry.getValue())){
					valClass = entry.getValue().get(0).getValidationClass();
					bizGroup = entry.getValue().get(0).getOwnerOfFixing();
					errDesc=  entry.getValue().get(0).getErrorName();
				}
				for(ErrorData errorRecord : entry.getValue()){
					rowId.append(errorRecord.getTbl()+"##"+errorRecord.getRowId()).append(",");
				}
			}
			error.setAssetCount(list.size()+"");
			error.setCode(errorCode);
			error.setBizGroup(bizGroup);
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
		
			Map<String, List<ErrorData>> mapByPriority = errorList.stream().filter(item->item.getValidationClass()!=null).collect(Collectors.groupingBy(ErrorData::getValidationClass));
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
						Map<String, List<ErrorData>> tdmap = v.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(ErrorData::getErrorCode));
						errorValue.setTotalDistinctCount(Integer.valueOf(ev.getTotalDistinctCount())+tdmap.size()+"");
						Map<String, List<ErrorData>> tdamap = v.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(ErrorData::getAssetNum));
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
		end = System.currentTimeMillis();
		logger.info(" getErrorSctionResponse : Error section mapping time " + (end - start));
		return errorResp;
	}

	/**
	 * @param v
	 * @param errorValue
	 */
	private void getErrorCategoryData(List<ErrorData> v, ErrorValue errorValue) {
		errorValue.setTotalErrorCount(v.size()+"");
		Map<String, List<ErrorData>> tdmap = v.stream().filter(item->item.getErrorCode()!=null).collect(Collectors.groupingBy(ErrorData::getErrorCode));
		errorValue.setTotalDistinctCount(tdmap.size()+"");
		Map<String, List<ErrorData>> tdamap = v.stream().filter(item->item.getAssetNum()!=null).collect(Collectors.groupingBy(ErrorData::getAssetNum));
		errorValue.setTotalDistinctAsset(tdamap.size()+"");
	}

	@Override
	public HierarchyView getHierarchyView(ProductFilter productFilter) {
		List<HierarchyProduct> cageProduct = new ArrayList<HierarchyProduct>();
		List<HierarchyProduct> orphanProduct = new ArrayList<HierarchyProduct>();
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		generateCacheKey(productFilter);
		long start = System.currentTimeMillis();
		List<SiebelAssetDa> sblList = new ArrayList<>();
		if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getKeyword().length() == 7
				&& productFilter.getKeyword().toUpperCase().startsWith("VAL_")) {
			List<PFilter> filterList = productFilter.getFilters();
			sblList = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(
					getProductFilterForErrorCodeGlobalFilter(productFilter), sblList);
			sblList = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList, sblList);
		} else {
			sblList = appOpsDartDaDao.getHierarchyView(productFilter);
		}
		long end = System.currentTimeMillis();
		 logger.info("hierarchy db call time  : "+ (end-start));
		 start = System.currentTimeMillis();
		Map<String,List<SiebelAssetDa>> childAssetGroupedByRowId = sblList.stream()
				.filter(p -> (p.getHeader16() != null))
				.collect(Collectors.groupingBy(SiebelAssetDa::getHeader1));
		Map<String, List<SiebelAssetDa>> childAssets = sblList.stream()
				.filter(p -> (p.getHeader16() != null && p.getHeader27() != null))
				.collect(Collectors.groupingBy(SiebelAssetDa::getHeader27));
		Map<String, Map<String, List<SiebelAssetDa>>> parentAssets = sblList.stream()
				.filter(p -> (p.getHeader16() != null && p.getHeader27() == null)).collect(Collectors
						.groupingBy(SiebelAssetDa::getHeader16, Collectors.groupingBy(SiebelAssetDa::getHeader20)));
		HierarchyView treeViewObj = new HierarchyView();
		childAssets.entrySet().stream().forEach(childAsset -> {
			if (!childAssetGroupedByRowId.containsKey(childAsset.getKey())) {
				childAsset.getValue().stream().forEach(assetDa -> {
					HierarchyProduct parentProduct = new HierarchyProduct();
					parentProduct.setKey(assetDa.getHeader20());
					parentProduct.setName(assetDa.getHeader16());
					orphanProduct.add(parentProduct);
				});
			}
		});
		parentAssets.entrySet().stream().forEach(assetDataGroupedBySystemName -> {
			assetDataGroupedBySystemName.getValue().entrySet().stream().forEach(assetDataGroupedByName -> {
				HierarchyProduct parentProduct = new HierarchyProduct();
				parentProduct.setKey(assetDataGroupedByName.getKey());
				parentProduct.setName(assetDataGroupedBySystemName.getKey());
				if (null != childAssets && !childAssets.isEmpty()) {
					assetDataGroupedByName.getValue().stream().forEach(level1Asset -> {
						if (childAssets.containsKey(level1Asset.getHeader1())) {
							List<SiebelAssetDa> level2AssetList = childAssets.get(level1Asset.getHeader1());
							level2AssetList.stream().forEach(level2Asset -> {
								HierarchySubproduct level2Product = new HierarchySubproduct();
								level2Product.setKey(level2Asset.getHeader20());
								if (PRODUCT_CABINET.equalsIgnoreCase(level2Asset.getHeader20())) {
									level2Product.setName(level2Asset.getHeader14());
								} else if(PRODUCT_CABINET_DP.equalsIgnoreCase(level2Asset.getHeader20())) {
									level2Product.setName(level2Asset.getHeader14());
								}   else {
									level2Product.setName(level2Asset.getHeader3());
								}
								if (childAssets.containsKey(level2Asset.getHeader1())) {
									List<SiebelAssetDa> level3AssetList = childAssets.get(level2Asset.getHeader1());
									level3AssetList.stream().forEach(level3Asset -> {
										HierarchySubproduct level3Product = new HierarchySubproduct();
										level3Product.setKey(level3Asset.getHeader20());
										level3Product.setName(level3Asset.getHeader3());
										if (childAssets.containsKey(level3Asset.getHeader1())) {
											List<SiebelAssetDa> level4AssetList = childAssets
													.get(level3Asset.getHeader1());
											level4AssetList.stream().forEach(level4Asset -> {
												HierarchySubproduct level4Product = new HierarchySubproduct();
												level4Product.setKey(level4Asset.getHeader20());
												level4Product.setName(level4Asset.getHeader3());
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
		
	/**
	 * @param clx
	 * @param sv
	 * @param sbl
	 * @param productJson
	 * @param productAttrMap
	 * @param attrCounter
	 */
	private void setAttributes(ClxAssetDa clx, SvAssetDa sv, SiebelAssetDa sbl, Product productJson,
			SortedMap<Integer, AttributeConfig> productAttrMap, int attrCounter) {
		AttributeConfig attrConfig = productAttrMap.get(attrCounter);
		 if(attrConfig!=null &&  isDisplay(attrConfig)){
			 Attribute attribute = new Attribute();
			 attribute.setKey(attrConfig.getHeaderPosition().toLowerCase().replace("_", ""));
			 attribute.setName(attrConfig.getAttrName());
			 attribute.setDisplayName(attrConfig.getDisplayName());
			 attribute.setType("attribute");
			 attribute.setDataType(attrConfig.getDataType());
			 attribute.setEditable(attrConfig.getEditable());
			 attribute.setRunDependent(attrConfig.getRunDependent());
			 Values values = new Values();
			 String val;
			 if(sbl!=null){
				 val = ServiceUtil.getSblAttrValue(attrCounter,sbl);
				 values.setSBL(val==null ? DartConstants.NA+"##"+sbl.getHeader1() : val+"##"+sbl.getHeader1());
			 }else{
				 values.setSBL(DartConstants.NA+"##");
			 }
			 if(clx!=null){
				 val = ServiceUtil.getClxAttrValue(attrCounter,clx);
				 values.setCLX(val==null ? DartConstants.NA+"##"+clx.getHeader1() : val+"##"+clx.getHeader1());
			 }else{
				 values.setCLX(DartConstants.NA+"##");
			 }
			 if(sv!=null){
				 val = ServiceUtil.getSvAttrValue(attrCounter,sv);
				 values.setSV(val==null ? DartConstants.NA+"##"+sv.getHeader1() : val+"##"+sv.getHeader1());
			 }else{
				 values.setSV(DartConstants.NA+"##");
			 }
			 attribute.setValues(values);
			 productJson.getAttributes().add(attribute);
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
	private void setHeaders(final SortedMap<Integer, AttributeConfig> configHeaderMap, ClxAssetDa clx, SvAssetDa sv,
			SiebelAssetDa sbl, Product productJson, int headerCounter) {
		AttributeConfig headerAttrConfig = configHeaderMap.get(headerCounter);
		
		if(headerAttrConfig!=null && isDisplay(headerAttrConfig)){
			Attribute attribute = new Attribute();
			attribute.setKey(headerAttrConfig.getHeaderPosition().toLowerCase().replace("_", ""));
			attribute.setName(headerAttrConfig.getAttrName());
			attribute.setDisplayName(headerAttrConfig.getDisplayName());
			attribute.setType("header");
			attribute.setDataType(headerAttrConfig.getDataType());
			attribute.setRunDependent(headerAttrConfig.getRunDependent());
			if(StringUtils.isEmpty(headerAttrConfig.getEditable()))
				attribute.setEditable("N");
			else
				attribute.setEditable(headerAttrConfig.getEditable());
			Values values = new Values();
			
			String val ;
			if(sbl!=null){
				val  = ServiceUtil.getSblHeaderValue(headerCounter,sbl);
				values.setSBL(val==null ? DartConstants.NA+"##"+sbl.getHeader1() : val +"##"+sbl.getHeader1());
			}else{
				values.setSBL(DartConstants.NA+"##");
			}
			if(clx!=null){
				val = ServiceUtil.getClxHeaderValue(headerCounter,clx);
				values.setCLX(val==null ? DartConstants.NA+"##"+clx.getHeader1() : val + "##"+clx.getHeader1());
			}else{
				values.setCLX(DartConstants.NA+"##");
			}	

			if(sv!=null){
				val = ServiceUtil.getSvHeaderValue(headerCounter,sv);
				values.setSV(val==null ? DartConstants.NA+"##"+sv.getHeader1() : val +"##"+sv.getHeader1());
			}else{
				values.setSV(DartConstants.NA+"##");
			}
			attribute.setValues(values);
			productJson.getAttributes().add(attribute);
		}
	}
	
	@Override
	public ProductDataGrid getRefreshedCommonAttributeGrid(ProductFilter filters) throws InterruptedException, ExecutionException{
		ProductDataGrid dataGrid = new ProductDataGrid();
		String actualKeyword = filters.getKeyword();
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
		Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");
		
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
			if(s2.split("##")[0].equalsIgnoreCase("SBL"))
				sblRowIdList.add(s2.split("##")[1]);
			else if(s2.split("##")[0].equalsIgnoreCase("CLX"))
				clxRowIdList.add(s2.split("##")[1]);
			
			else if(s2.split("##")[0].equalsIgnoreCase("SV"))
				svRowIdList.add(s2.split("##")[1]);
			
		});
		List<SiebelAssetDa> sblList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(sblRowIdList)) {
			if (DartConstants.IS_ELASTIC_CALL) {
				sblList = appOpsDartDaDao.getSblAssetDaAllByRowsIdsElastic(sblRowIdList, filters);
				sblList = ServiceUtil.filterSiebelAssetDa(sblList, ServiceUtil.getProductsFromRequest(filters));
			} else {
				sblList = appOpsDartDaDao.getSblAssetDaByRowsIds(sblRowIdList);
			}

		}
		List<ClxAssetDa> clxList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(clxRowIdList)){
			if (DartConstants.IS_ELASTIC_CALL) {
				clxList = appOpsDartDaDao.getClxAssetDaAllByRowsIdsElastic(clxRowIdList, filters);
			} else {
				clxList = appOpsDartDaDao.getClxAssetDaByRowsIds(clxRowIdList);
			}
		}
		List<SvAssetDa> svList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(svRowIdList)){
			if (DartConstants.IS_ELASTIC_CALL) {
				svList = appOpsDartDaDao.getSvAssetDaAllByRowsIdsElastic(svRowIdList, filters);
			} else {
				svList = appOpsDartDaDao.getSvAssetDaByRowsIds(svRowIdList);
			}
		}
		ClxAssetDa clx = null;
		SvAssetDa sv =null;
		
			int maxCommonAttrCount = 0;
			String maxCommonProduct = "";
			for(Map.Entry<String, Integer> entry :maximumAttrCount.entrySet()){
				maxCommonAttrCount = entry.getValue().intValue();
				maxCommonProduct = entry.getKey();
				logger.info("Max product : " + maxCommonProduct + ":" + maxCommonAttrCount );
				break;
			}
			for(SiebelAssetDa sbl : sblList){
				Product productJson = new Product();
				if(sbl!=null){
					productJson.setName(sbl.getHeader20());
					clx = ServiceUtil.getClx(sbl, clxList);
					sv = ServiceUtil.getSv(sbl,svList );
					/*for(int headerCounter =1 ; headerCounter <= 66 ; headerCounter++){*/
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, 2);
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, 6);
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, 7);
				//}
				//SortedMap<Integer,AttributeConfig> productAttrMap = configProductCommonAttrMap.get(sbl.getHeader20());
					for (Map.Entry<String, SortedMap<Integer, AttributeConfig>> entry : configProductCommonAttrMap.entrySet()) {
						if(ServiceUtil.productListWhichHaveCommonAttribute.contains(entry.getKey())){
							if(isProductContainsAnyCommonAttributeViewDisplayYes(entry.getValue())){
								 for(int attrCounter =1 ; attrCounter <= maxCommonAttrCount ; attrCounter++){
									setAttributes(clx, sv, sbl, productJson, entry.getValue(), attrCounter);
								 }
							}
						}
					 }
				}
				dataGrid.getProducts().add(productJson);
			}
		
		logger.info("Attribute View processing end ");
		filters.setKeyword(actualKeyword);
		dataGrid.setSnapfilter(removeProductFilter(filters));
		
		return dataGrid;
	}
	
	private ProductFilter removeProductFilter(ProductFilter srcfilter) {
		ListIterator<PFilter> iter = srcfilter.getFilters().listIterator();
		while (iter.hasNext()) {
			PFilter pf = iter.next();
			if (pf.getKey().equalsIgnoreCase("header2") || pf.getKey().equalsIgnoreCase("header3")) {
				iter.remove();
			} else if (pf.getKey().equalsIgnoreCase("header20")) {
				pf.setValue(null);
			}
		}
		/*
		 * 
		 * for(PFilter filter : srcfilter.getFilters()){
		 * if(filter.getKey().equalsIgnoreCase("header20")){
		 * filter.setValue(null); return srcfilter; } }
		 */ return srcfilter;
	}
	
	
	@Override
	public String fireSnapshotFilter(ProductFilter filter){
			//Gson gson = new Gson();
			ProductFilterResult productFilterResult2 = new ProductFilterResult();
			String key2 = filter.genrateCacheKey();
			logger.info(" fireSnapshotFilter cache key : " +key2 );
			logger.info(" fireSnapshotFilter cache string : " +filter.getDecodedKey() );
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(filter);
			productFilterResult2.setProductFilter(filter);
			long start2 = System.currentTimeMillis();
			productFilterResult2 = appOpsDartDaDao.getProductFilterResult(productFilterResult2);
			long end2 = System.currentTimeMillis();
			logger.info("Snapfilter time " + (end2 - start2));
			
		return "Time consumed " + (end2 - start2) + " cache key :" + key2 ;
	}
	
	@Override
	public ProductDataGrid getCommonAttributeView(ProductFilter productFilter) {
		
		try{
			String actualKeyword = productFilter.getKeyword();
			/*List<SiebelAssetDa> sblDaList  = appOpsDartDaDao.getSiebleAssetDaData(productFilter);
			List<ClxAssetDa> clxDaList = appOpsDartDaDao.getClxAssetDaData(productFilter);
			List<SvAssetDa> svDaList = appOpsDartDaDao.getSvAssetDaData(productFilter);*/
			ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
			ProductFilterResult productFilterResult = new ProductFilterResult();
			String key = productFilter.genrateCacheKey();
			logger.info("Cache Key " + key);
			productFilterResult.setProductFilter(productFilter);
			long start = System.currentTimeMillis();
			if (DartConstants.IS_ELASTIC_CALL) {
				productFilterResult = appOpsDartDaDao.getProductFilterResultElastic(productFilterResult);
			} else {
				productFilterResult = appOpsDartDaDao.getProductFilterResult(productFilterResult);
			}
			List<SiebelAssetDa> sblDaList  = productFilterResult.getSblList();
			List<ClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SvAssetDa> svDaList = productFilterResult.getSvList();
			Gson gson = new Gson();
			logger.info("Product Filter " + gson.toJson(productFilter, ProductFilter.class));
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			logger.info("Attribute View clx data fetched :  " + clxDaList.size());
			logger.info("Attribute View Sv data fetched :  " + svDaList.size());
			long end = System.currentTimeMillis();
			logger.info("Time consumnerd " + (end - start));
			logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
			
			//appOpsDartDaDao.initilize();
			// commeted because as of now headers are hidden
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
			
			Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
			Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");
			
			// Main json object 
			ProductDataGrid dataGridJson = new ProductDataGrid();
			ClxAssetDa clx = null;
			SvAssetDa sv =null;
			
				int maxCommonAttrCount = 0;
				String maxCommonProduct = "";
				for(Map.Entry<String, Integer> entry :maximumAttrCount.entrySet()){
					maxCommonAttrCount = entry.getValue().intValue();
					maxCommonProduct = entry.getKey();
					logger.info("Max product : " + maxCommonProduct + ":" + maxCommonAttrCount );
					break;
				}
				for(SiebelAssetDa sbl : sblDaList){
					Product productJson = new Product();
					if(sbl!=null){
						productJson.setName(sbl.getHeader20());
						clx = ServiceUtil.getClx(sbl, clxDaList);
						sv = ServiceUtil.getSv(sbl,svDaList );
						/*for(int headerCounter =1 ; headerCounter <= 66 ; headerCounter++){*/
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, 2);
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, 6);
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, 7);
						//}
						for (Map.Entry<String, SortedMap<Integer, AttributeConfig>> entry : configProductCommonAttrMap.entrySet()) {
							if(isProductPresentInFilter(productFilter, entry.getKey())){
								if(isProductContainsAnyCommonAttributeViewDisplayYes(entry.getValue())){
									 for(int attrCounter =1 ; attrCounter <= maxCommonAttrCount ; attrCounter++){
										setAttributes(clx, sv, sbl, productJson, entry.getValue(), attrCounter);
									 }
								}
							}
						 }
					}
					dataGridJson.getProducts().add(productJson);
				}
			
			logger.info("Attribute View processing end ");
			productFilter.setKeyword(actualKeyword);
			dataGridJson.setSnapfilter(removeProductFilter(productFilter));
			return dataGridJson;
		}catch(Exception e){
			logger.error("Error in attribute view ", e);
			return null;
		}
		
	}
	
	@Override
	public ProductDataGrid getRefreshedProductAttributeGrid(ProductFilter filters) throws InterruptedException, ExecutionException{
		ProductDataGrid dataGrid = new ProductDataGrid();
		String actualKeyword = filters.getKeyword();
		
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
		Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");


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
			if(s2.split("##")[0].equalsIgnoreCase("SBL"))
				sblRowIdList.add(s2.split("##")[1]);
			else if(s2.split("##")[0].equalsIgnoreCase("CLX"))
				clxRowIdList.add(s2.split("##")[1]);
			
			else if(s2.split("##")[0].equalsIgnoreCase("SV"))
				svRowIdList.add(s2.split("##")[1]);
			
		});
		List<SiebelAssetDa> sblList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
			if (DartConstants.IS_ELASTIC_CALL) {
				sblList = appOpsDartDaDao.getSblAssetDaAllByRowsIdsElastic(sblRowIdList, filters);
				sblList = ServiceUtil.filterSiebelAssetDa(sblList, ServiceUtil.getProductsFromRequest(filters));
			} else {
				sblList = appOpsDartDaDao.getSblAssetDaByRowsIds(sblRowIdList);
			}
			}
		List<ClxAssetDa> clxList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(clxRowIdList)){
			if (DartConstants.IS_ELASTIC_CALL) {
				clxList = appOpsDartDaDao.getClxAssetDaAllByRowsIdsElastic(clxRowIdList, filters);
			} else {
				clxList = appOpsDartDaDao.getClxAssetDaByRowsIds(clxRowIdList);
			}
		}
		List<SvAssetDa> svList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(svRowIdList)){
			if (DartConstants.IS_ELASTIC_CALL) {
				svList = appOpsDartDaDao.getSvAssetDaAllByRowsIdsElastic(svRowIdList, filters);
			} else {
				svList = appOpsDartDaDao.getSvAssetDaByRowsIds(svRowIdList);
			}
		}
		ClxAssetDa clx = null;
		SvAssetDa sv =null;

	
		for(SiebelAssetDa sbl : sblList){
			Product productJson = new Product();
			if(sbl!=null){
				productJson.setName(sbl.getHeader20());
				clx = ServiceUtil.getClx(sbl, clxList);
				sv = ServiceUtil.getSv(sbl,svList );
				for(int headerCounter =1 ; headerCounter <= MAX_HEADERS ; headerCounter++){
						setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter);
					}
				// attr loop
				SortedMap<Integer,AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
				for(int attrCounter =1 ; attrCounter <= MAX_ATTRIBUTES ; attrCounter++){
					setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter);
				}
			}
			dataGrid.getProducts().add(productJson);
		}

		logger.info("Attribute View processing end ");
		filters.setKeyword(actualKeyword);
		dataGrid.setSnapfilter(removeProductFilter(filters));
		return dataGrid;
		
	}
	@Override
	public ProductDataGrid getProductAttributeView(ProductFilter productFilter, boolean isCommonGrid) {
		
		try{
				List<String> products = ServiceUtil.getProductsFromRequest(productFilter);
				ProductFilterResult productFilterResult = new ProductFilterResult();
				String actualKeyword = productFilter.getKeyword();
				ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
				String key = productFilter.genrateCacheKey();
				logger.info("Cache Key " + key);
				productFilterResult.setProductFilter(productFilter);
				long start = System.currentTimeMillis();
				List<SiebelAssetDa> sblDaList = null;
				if (DartConstants.IS_ELASTIC_CALL) {
					productFilterResult = appOpsDartDaDao.getAllProductFilterResultElastic(productFilterResult);
					sblDaList = ServiceUtil.filterSiebelAssetDa(productFilterResult.getSblList(),products);
				} else {
					productFilterResult = appOpsDartDaDao.getProductFilterResult(productFilterResult);
					sblDaList = productFilterResult.getSblList();
				}
				List<ClxAssetDa> clxDaList = productFilterResult.getClxList();
				List<SvAssetDa> svDaList = productFilterResult.getSvList();
				
				
				logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
				logger.info("Attribute View clx data fetched :  " + clxDaList.size());
				logger.info("Attribute View Sv data fetched :  " + svDaList.size());
				long end = System.currentTimeMillis();
				logger.info("getProductAttributeView Time consumnerd for getProductFilterResult" + (end - start));
				//appOpsDartDaDao.initilize();
				// commeted because as of now headers are hidden
				start = System.currentTimeMillis();
				SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
				HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
				Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
				end = System.currentTimeMillis();
				logger.info("getProductAttributeView Time consumnerd for Maps " + (end - start));
				HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
				// Main json object 
				Map<String,List<SrcCxiErrorTbl>> sblErrorMap = initDfrDao.getSblErrorMap();
				ProductDataGrid dataGridJson = new ProductDataGrid();
				ClxAssetDa clx = null;
				SvAssetDa sv =null;
				logger.info("Attribute View processing start isCommonGrid " + isCommonGrid);
				start = System.currentTimeMillis();
				for(SiebelAssetDa sbl : sblDaList){
					Product productJson = new Product();
					if(sbl!=null){
						productJson.setName(sbl.getHeader20());
						productJson.setAssetNumber(sbl.getHeader2());
						clx = ServiceUtil.getClx(sbl, clxDaList);
						sv = ServiceUtil.getSv(sbl,svDaList );
						for(int headerCounter =1 ; headerCounter <= MAX_HEADERS ; headerCounter++){
							setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter);
						}
						// attr loop
						SortedMap<Integer,AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
						for(int attrCounter =1 ; attrCounter <= MAX_ATTRIBUTES ; attrCounter++){
							setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter);
						}
						productJson.setDqmErrorFlag(sbl.getHeader38());
						if (sblErrorMap != null && sblErrorMap.size() > 0 && sblErrorMap.containsKey(sbl.getHeader1())) {
							List<SrcCxiErrorTbl> errorList = sblErrorMap.get(sbl.getHeader1());
							if (errorList != null && CollectionUtils.isNotEmpty(errorList)) {
								StringBuffer errorBuffer = new StringBuffer();
								errorList.forEach( errorObj -> {
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
					}
					dataGridJson.getProducts().add(productJson);
				}
				logger.info("Attribute View processing end ");
				end = System.currentTimeMillis();
				logger.info("getProductAttributeView Time consumnerd for datagrid" + (end - start));
				productFilter.setKeyword(actualKeyword);
				dataGridJson.setSnapfilter(removeProductFilter(productFilter));
				return dataGridJson;
		}catch(Exception e){
			logger.error("Error in attribute view ", e);
			return null;
		}
		
	}
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	
	private HashSet<String> getProductSetAfterRefresh(List<SiebelAssetDa> sbList) {
		Map<String,List<SiebelAssetDa>> map = sbList.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader20));
		HashSet<String> productSetAfterRefresh = new HashSet<>();
		for(Map.Entry<String, List<SiebelAssetDa>> entry : map.entrySet()){
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
			if(s2.split("##")[0].equalsIgnoreCase("SBL"))
				sblRowIdList.add(s2.split("##")[1]);
		});
		
		List<SiebelAssetDa> sbList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
			sbList = appOpsDartDaDao.getSblAssetDaByRowsIds(sblRowIdList);
			productSetAfterRefresh =getProductSetAfterRefresh(sbList);
		}
		
		ProductWidgets productWidgets  = new ProductWidgets();
		for(PFilter filter : productFilter.getFilters()){
			if(filter.getKey().equalsIgnoreCase("header20")){
				for(String product : filter.getListOfValues()){
				//	List<SiebelAssetDa> sbList = appOpsDartDaDao.getSiebelAssetDaDataByProduct(productFilter, product);
					logger.info("AppOpsDartDaServiceImpl :: getRefreshedProductWidgets :: "+sbList.size());
					if(product.equalsIgnoreCase("Cage") && productSetAfterRefresh.contains("Cage")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cage");
						Map<String,List<SiebelAssetDa>> mapBySysName = sbList.stream().filter(item->((item.getHeader16()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Cage")))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
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
						Map<String,List<SiebelAssetDa>> mapBySysName = sbList.stream().filter(item->((item.getHeader16()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Cabinet")))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Demarcation Point"))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->((item.getHeader14()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("AC Circuit")))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null&& (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("DC Circuit"))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->((item.getHeader14()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Patch Panel")))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByASidePatchPanel = sbList.stream().filter(item->((item.getAttr22()!=null) && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Network Cable Connection")))).collect(Collectors.groupingBy(SiebelAssetDa::getAttr22));
						mapByASidePatchPanel.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
	public ProductWidgets getProductWidgets(ProductFilter productFilter) {
		
		
		ProductWidgets productWidgets  = new ProductWidgets();
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		for(PFilter filter : productFilter.getFilters()){
			if(filter.getKey().equalsIgnoreCase("header20")){
				for(String product : filter.getListOfValues()){
					long start = System.currentTimeMillis();
					String key = productFilter.genrateCacheKey();
					List<SiebelAssetDa> sbList = appOpsDartDaDao.getSiebelAssetDaDataByProduct(productFilter, product);
					
					long end = System.currentTimeMillis();
					logger.info("Key  " + key);
					logger.info("Product widget db time " + product + ":" +  (end-start));
					
					if(product.equalsIgnoreCase("Cage")){
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cage");
						Map<String,List<SiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null).collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
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
						Map<String,List<SiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Cabinet"))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapBySysName = sbList.stream().filter(item->item.getHeader16()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("Demarcation Point"))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("AC Circuit"))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null && (item.getHeader20()!=null && item.getHeader20().equalsIgnoreCase("DC Circuit"))).collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByCabinetNumer = sbList.stream().filter(item->item.getHeader14()!=null).collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
						Map<String,List<SiebelAssetDa>> mapByASidePatchPanel = sbList.stream().filter(item->item.getAttr22()!=null).collect(Collectors.groupingBy(SiebelAssetDa::getAttr22));
						mapByASidePatchPanel.entrySet().stream().forEach(entry->{
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if(CollectionUtils.isNotEmpty(entry.getValue())){
								for(SiebelAssetDa sb : entry.getValue()){
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
	
	/*@PostConstruct
	public void init(){
		this.getFilterList();
	}*/
	
	@Override
	public	List<Filter> getFilterList(){
		List<Filter> filters=new ArrayList<>();
		
		AttributeConfig attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "IBX");
		Filter filter1=new Filter();
		filter1.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		filter1.setLable("IBX");
		filter1.setValues(appOpsDartDaDao.getFilterListFromDA(attributeConfig.getHeaderPosition()));
		filters.add(filter1);
		
		attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "ACCOUNT NUMBER");
		Filter filter2=new Filter();
		filter2.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		filter2.setLable("ACCOUNT NUMBER");
		filter2.setValues(appOpsDartDaDao.getFilterListFromDA(attributeConfig.getHeaderPosition()));
		filters.add(filter2);
		
		attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "SYSTEM NAME");
		Filter filter3=new Filter();
		filter3.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		filter3.setLable("SYSTEM NAME");
		filter3.setValues(appOpsDartDaDao.getFilterListFromDA(attributeConfig.getHeaderPosition()));
		filters.add(filter3);
		
		List<String> errorCodeList= appOpsDartDaDao.getFilterListFromErrorMaster();
		Filter filter4=new Filter();
		filter4.setKey("Error Code");
		filter4.setLable("Error Code");
		filter4.setValues(errorCodeList);
		filters.add(filter4);
		
		return filters;
	}
	
	public SearchFilters globalSearch(String keyword, String key) {
		ProductFilter productFilter = new ProductFilter();
		if (StringUtils.isNotEmpty(key)) {
			List<PFilter> pFiltersList = new ArrayList<>();
			List<String> serialNumberList = null;
			if ("header3".equalsIgnoreCase(key)) {
				serialNumberList = Arrays.asList(keyword.split(",")).stream()
						.filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim)
						.collect(Collectors.toList());
				PFilter serialNumberFilter = new PFilter();
				serialNumberFilter.setKey("header3");
				serialNumberFilter.setLable("Serial Number");
				serialNumberFilter.setValue(keyword);
				serialNumberFilter.setListOfValues(serialNumberList);
				pFiltersList.add(serialNumberFilter);
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
		} else {
			productFilter.setKeyword(keyword);
		}
		generateCacheKey(productFilter);
		SearchFilters filters = new SearchFilters();
		if (DartConstants.IS_ELASTIC_CALL) {
			filters = appOpsDartDaDao.getGlobalSearchElastic(productFilter);
		} else {
			List<SiebelAssetDa> sblList = appOpsDartDaDao.getGlobalSearch(productFilter);
			logger.info("Sbl data fetched : " + sblList.size());
			HashSet<String> ibxSet = new HashSet<>();
			HashSet<String> accNumberSet = new HashSet<>();
			HashSet<String> sysNameSet = new HashSet<>();

			sblList.stream().forEach(item -> {
				ibxSet.add(item.getHeader8());
				accNumberSet.add(item.getHeader6());
				sysNameSet.add(item.getHeader16());
			});
			Filter filter1 = new Filter();
			filter1.setKey("header8");
			filter1.setLable("IBX");
			filter1.setValues(new ArrayList<>(ibxSet));
			filters.getFilters().add(filter1);

			Filter filter2 = new Filter();
			filter2.setKey("header6");
			filter2.setLable("ACCOUNT NUM");
			filter2.setValues(new ArrayList<>(accNumberSet));
			filters.getFilters().add(filter2);

			Filter filter3 = new Filter();
			filter3.setKey("header16");
			filter3.setLable("SYSTEM NAME");
			filter3.setValues(new ArrayList<>(sysNameSet));
			filters.getFilters().add(filter3);

			List<String> errorCodeList = appOpsDartDaDao.getFilterListFromErrorMaster();
			Filter filter4 = new Filter();
			filter4.setKey("Error Code");
			filter4.setLable("Error Code");
			filter4.setValues(errorCodeList);
			filters.getFilters().add(filter4);
			logger.info("Global search result Prepared : " + filters);
		}
		return filters;
	}



	public SearchFilters globalSearch(ProductFilter productFilter){
		ServiceUtil.validateProductFilterForCommaSaparatedAssetNumsInKeyword(productFilter);
		productFilter.genrateCacheKey();
		SearchFilters filters = null;
		if (DartConstants.IS_ELASTIC_CALL) {
			filters = appOpsDartDaDao.getGlobalSearchElastic(productFilter);
			List<String> errorCodeList = appOpsDartDaDao.getFilterListFromErrorMaster();
			Filter filter4 = new Filter();
			filter4.setKey("Error Code");
			filter4.setLable("Error Code");
			filter4.setValues(errorCodeList);
			filters.getFilters().add(filter4);
		} else {
			filters = appOpsDartDaDao.globalSearch(productFilter);
		}
		return filters;
	}
	


	private void validateProductFilterForCommaSaparatedAssetNumsInKeyword(ProductFilter productFilter) {
		if (productFilter.getSearchDropBox() != null) {
			List<SearchDrop> searchDropList = productFilter.getSearchDropBox().getSearchDrop();
			if (CollectionUtils.isNotEmpty(searchDropList)) {
				for (SearchDrop searchDrop : searchDropList) {
					if (StringUtils.isNotEmpty(searchDrop.getKey())) {
						List<String> serialNumList = null;
						if ("header3".equalsIgnoreCase(searchDrop.getKey())) {
							serialNumList = new ArrayList<String>(
									Arrays.asList(productFilter.getKeyword().split(",")).stream()
									.filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim).collect(Collectors.toList()));
							if (CollectionUtils.isNotEmpty(serialNumList)) {
								PFilter serialNumFilter = new PFilter();
								serialNumFilter.setKey(searchDrop.getKey());
								serialNumFilter.setValue(productFilter.getKeyword());
								serialNumFilter.setLable(searchDrop.getLabel());
								serialNumFilter.setListOfValues(serialNumList);
								productFilter.getFilters().add(serialNumFilter);
								productFilter.setKeyword("");
							}
						} else if("header2".equalsIgnoreCase(searchDrop.getKey())) {
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
						}else if ("header16".equalsIgnoreCase(searchDrop.getKey())) {
							String[] systemNames = productFilter.getKeyword().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
							List<String> systemNameList = Arrays.asList(systemNames);
							/* List<String> systemNameList = new ArrayList<String>(
									Arrays.asList(productFilter.getKeyword().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")).stream()
									.filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim).collect(Collectors.toList()));*/
							if (CollectionUtils.isNotEmpty(systemNameList)) {
								PFilter systemNameFilter = new PFilter();
								systemNameFilter.setKey(searchDrop.getKey());
								systemNameFilter.setValue(productFilter.getKeyword());
								systemNameFilter.setLable(searchDrop.getLabel());
								systemNameFilter.setListOfValues(systemNameList);
								productFilter.getFilters().add(systemNameFilter);
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
	
	public void generateCacheKey(ProductFilter productFilter){
		productFilter.genrateCacheKey();
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
	public SearchDropBox getSearchDropBox() {
		List<SearchDrop> searchDropList = new ArrayList<>();
		for(Map.Entry<String,String> entry : DartConstants.SEARCH_DROP_MAP.entrySet()){
			SearchDrop searchDrop = new SearchDrop();
			searchDrop.setKey(entry.getKey());
			searchDrop.setLabel(entry.getValue());
			searchDropList.add(searchDrop);
		}
		SearchDropBox searchDropBox = new SearchDropBox();
		searchDropBox.setSearchDrop(searchDropList);
		return searchDropBox;
	}
	
	@Override
	public	List<Filter> getEmptyFilterList(){
		List<Filter> filters = new ArrayList<>();
		
		AttributeConfig attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "IBX");
		Filter ibxFilter =new Filter();
		ibxFilter.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		ibxFilter.setLable("IBX");
		filters.add(ibxFilter);
		
		attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "ACCOUNT NUMBER");
		Filter accountNumberFilter = new Filter();
		accountNumberFilter.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		accountNumberFilter.setLable("ACCOUNT NUMBER");
		filters.add(accountNumberFilter);
		
		attributeConfig=appOpsDartAttrConfigService.getAttrConfigVlaueByKey("HEADER", "SYSTEM NAME");
		Filter systemNameFilter = new Filter();
		systemNameFilter.setKey(attributeConfig.getHeaderPosition().toLowerCase());
		systemNameFilter.setLable("SYSTEM NAME");
		filters.add(systemNameFilter);
		
		return filters;
	}
	
	@Override
	public SearchFilters getErrorCodeGlobalKeywordFilters(String errorCode) {
		ProductFilter productFilter =  new ProductFilter();
		PFilter errorCodeFilter = new PFilter();
		errorCodeFilter.setKey("Error Code");
		errorCodeFilter.setLable("Error Code");
		errorCodeFilter.setValue(errorCode.toUpperCase());
		List<PFilter> pFiltersList = new ArrayList<>();
		pFiltersList.add(errorCodeFilter);
		productFilter.setFilters(pFiltersList);
		productFilter.setKeyword(errorCode.toUpperCase());
		generateCacheKey(productFilter);
		List<SiebelAssetDa> sblList = new ArrayList<>();
		sblList = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(productFilter,sblList);
		HashSet<String> ibxSet = new HashSet<>();
		HashSet<String> accNumberSet = new HashSet<>();
		HashSet<String> sysNameSet = new HashSet<>();
		HashSet<String> regionSet = new HashSet<>();
		sblList.stream().forEach(item ->{
			ibxSet.add(item.getHeader8());
			accNumberSet.add(item.getHeader6());
			sysNameSet.add(item.getHeader16());
			regionSet.add(item.getHeader51());
		});
		SearchFilters searchFiltersObj = new SearchFilters();
		Filter ibxFilter = new Filter();
		ibxFilter.setKey("header8");
		ibxFilter.setLable("IBX");
		ibxFilter.setValues(new ArrayList<>(ibxSet));
		searchFiltersObj.getFilters().add(ibxFilter);
		Filter accountNumFilter = new Filter();
		accountNumFilter.setKey("header6");
		accountNumFilter.setLable("ACCOUNT NUM");
		accountNumFilter.setValues(new ArrayList<>(accNumberSet));
		searchFiltersObj.getFilters().add(accountNumFilter);
		Filter sysNameFilter = new Filter();
		sysNameFilter.setKey("header16");
		sysNameFilter.setLable("SYSTEM NAME");
		sysNameFilter.setValues(new ArrayList<>(sysNameSet));
		searchFiltersObj.getFilters().add(sysNameFilter);
		Filter regionFilter = new Filter();
		regionFilter.setKey("header51");
		regionFilter.setLable("REGION");
		regionFilter.setValues(new ArrayList<>(regionSet));
		searchFiltersObj.getFilters().add(regionFilter);
		return searchFiltersObj;
	}
	
	@Override
	public ProductSearchResponse getProductSearchForErrorCodeGlobalFilterKeyword (ProductFilter productFilter) {
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		sblListColl = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(getProductFilterForErrorCodeGlobalFilter(productFilter),sblListColl);
		sblListColl = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList,sblListColl);
		ProductSearchResponse productSearchResponse = new ProductSearchResponse();
		Map<String, List<SiebelAssetDa>> map = sblListColl.stream().collect(Collectors.groupingBy(SiebelAssetDa::getHeader20,Collectors.toList()));
		Long cleanCount = 0L ;
		Long errorCount = 0L;
		for (Map.Entry<String, List<SiebelAssetDa>> entry : map.entrySet()) {
			ProductResp product = new ProductResp();
			product.setName(entry.getKey());
			cleanCount = 0L;
			errorCount = 0L;
			for(SiebelAssetDa sbl : entry.getValue()){
				if(sbl!=null){
					if(sbl.getHeader38()== null || sbl.getHeader38().equalsIgnoreCase("Y")){
						errorCount = errorCount + 1;
					}else if(sbl.getHeader38().equalsIgnoreCase("N")){						
						cleanCount = cleanCount+1;
					}
				}
			}  
			product.setClean(cleanCount);
			product.setError(errorCount);
			product.setTotal(cleanCount+errorCount);
			productSearchResponse.getProducts().add(product);
		}
		productSearchResponse.setTotalRecords(CollectionUtils.isNotEmpty(sblListColl)?sblListColl.size()+"":"0");
		return productSearchResponse;
	}
	
	@Override
	public ProductSearchResponse getProductSearchForErrorCodeGlobalFilterKeywordElastic(ProductFilter productFilter)
			throws InterruptedException, ExecutionException {
		return elasticDao
				.getSblListWhenOnlyErrorFilterAppliedElastic(getProductFilterForErrorCodeGlobalFilter(productFilter));
	}
	
	@Override
	public SearchFilters getFilterListForErrorCodeGlobalFilter(ProductFilter productFilter) {
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		sblListColl = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(getProductFilterForErrorCodeGlobalFilter(productFilter),sblListColl);
		sblListColl = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList,sblListColl);
		HashSet<String> ibxSet = new HashSet<>();
		HashSet<String> accNumberSet = new HashSet<>();
		HashSet<String> sysNameSet = new HashSet<>();
		HashSet<String> regionSet = new HashSet<>();
		sblListColl.stream().forEach(item ->{
			ibxSet.add(item.getHeader8());
			accNumberSet.add(item.getHeader6());
			sysNameSet.add(item.getHeader16());
			regionSet.add(item.getHeader51());
		});
		SearchFilters searchFiltersObj = new SearchFilters();
		Filter ibxFilter = new Filter();
		ibxFilter.setKey("header8");
		ibxFilter.setLable("IBX");
		ibxFilter.setValues(new ArrayList<>(ibxSet));
		searchFiltersObj.getFilters().add(ibxFilter);
		Filter accountNumFilter = new Filter();
		accountNumFilter.setKey("header6");
		accountNumFilter.setLable("ACCOUNT NUM");
		accountNumFilter.setValues(new ArrayList<>(accNumberSet));
		searchFiltersObj.getFilters().add(accountNumFilter);
		Filter sysNameFilter = new Filter();
		sysNameFilter.setKey("header16");
		sysNameFilter.setLable("SYSTEM NAME");
		sysNameFilter.setValues(new ArrayList<>(sysNameSet));
		searchFiltersObj.getFilters().add(sysNameFilter);
		Filter regionFilter = new Filter();
		regionFilter.setKey("header51");
		regionFilter.setLable("REGION");
		regionFilter.setValues(new ArrayList<>(regionSet));
		searchFiltersObj.getFilters().add(regionFilter);
		return searchFiltersObj;
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
	
	@Override
	public ProductDataGrid getProductAttributeViewForErrorCodeGlobal (ProductFilter productFilter) {
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<SiebelAssetDa> sblListCollTemp = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		ProductFilter filterObj = productFilter;
		sblListCollTemp = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(getProductFilterForErrorCodeGlobalFilter(productFilter),sblListCollTemp);
		sblListCollTemp = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList,sblListCollTemp);
		if (filterList != null && filterList.size() > 0) {
			for (int i = 0; i < filterList.size(); i++) {
				if (filterList.get(i).getKey().equalsIgnoreCase("header20")) {
					PFilter pFilterObj = filterList.get(i);
					sblListCollTemp.forEach(assetDa -> {
						if (pFilterObj.getListOfValues().contains(assetDa.getHeader20())) {
							sblListColl.add(assetDa);
						}
					});  
				}
			}
		}
		ProductFilterResult filterResult = getProductFilterResultErrorCodeGlobal(sblListColl);
		filterResult.setProductFilter(filterObj);
		List<SiebelAssetDa> sblDaList  = filterResult.getSblList();
		List<ClxAssetDa> clxDaList = filterResult.getClxList();
		List<SvAssetDa> svDaList = filterResult.getSvList();
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap = 
				(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
		ProductDataGrid dataGridJson = new ProductDataGrid();
		//dataGridJson.setTotalrows(productFilter.getTotalRows());
		Map<String,List<SrcCxiErrorTbl>> sblErrorMap = initDfrDao.getSblErrorMap();
		ClxAssetDa clx = null;
		SvAssetDa sv =null;
		logger.info("Error Code Global Keyword Attribute View Processing Start # isCommonGrid >>>" + Boolean.FALSE);
		for (SiebelAssetDa sbl : sblDaList) {
			Product productJson = new Product();
			if (sbl != null) {
				productJson.setName(sbl.getHeader20());
				clx = ServiceUtil.getClx(sbl, clxDaList);
				sv = ServiceUtil.getSv(sbl, svDaList);
				for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter);
				}
				SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
				for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
					setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter);
				}
				productJson.setDqmErrorFlag(sbl.getHeader38());
				if (sblErrorMap != null && sblErrorMap.size() > 0 && sblErrorMap.containsKey(sbl.getHeader1())) {
					List<SrcCxiErrorTbl> errorList = sblErrorMap.get(sbl.getHeader1());
					if (errorList != null && CollectionUtils.isNotEmpty(errorList)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorList.forEach( errorObj -> {
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
			}
			dataGridJson.getProducts().add(productJson);
		}
		logger.info("Attribute View processing end ");
		productFilter.setKeyword(productFilter.getKeyword());
		dataGridJson.setSnapfilter(removeProductFilter(productFilter));
		return dataGridJson;
	}
	
	@Override
	public ProductDataGrid getProductAttributeViewForErrorCodeGlobalElastic(ProductFilter productFilter)
			throws InterruptedException, ExecutionException {
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<SiebelAssetDa> sblListCollTemp = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		ProductFilter filterObj = productFilter;
		String product = null;
		/*
		 * Elastic call
		 */
		sblListCollTemp = elasticDao.getProductAttributeViewForErrorCodeGlobalElastic(
				getProductFilterForErrorCodeGlobalFilter(productFilter));

		sblListCollTemp = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList, sblListCollTemp);
		if (filterList != null && filterList.size() > 0) {
			for (int i = 0; i < filterList.size(); i++) {
				if (filterList.get(i).getKey().equalsIgnoreCase("header20")) {
					PFilter pFilterObj = filterList.get(i);
					product = pFilterObj.getValue();
					sblListCollTemp.forEach(assetDa -> {
						if (pFilterObj.getListOfValues().contains(assetDa.getHeader20())) {
							sblListColl.add(assetDa);
						}
					});
				}
			}
		}
		ProductDataGrid dataGridJson = new ProductDataGrid();
		ProductFilterResult filterResult = getProductFilterResultErrorCodeGlobal(sblListColl);
		filterResult.setProductFilter(filterObj);
		List<SiebelAssetDa> sblDaList = filterResult.getSblList();
		List<ClxAssetDa> clxDaList = filterResult.getClxList();
		List<SvAssetDa> svDaList = filterResult.getSvList();
		
		SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao
				.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap = (HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap
				.get("common");

		// dataGridJson.setTotalrows(productFilter.getTotalRows());
		Map<String, List<SrcCxiErrorTbl>> sblErrorMap = initDfrDao.getSblErrorMap();
		ClxAssetDa clx = null;
		SvAssetDa sv = null;
		logger.info("Error Code Global Keyword Attribute View Processing Start # isCommonGrid >>>" + Boolean.FALSE);
		for (SiebelAssetDa sbl : sblDaList) {
			Product productJson = new Product();
			if (sbl != null) {
				productJson.setName(sbl.getHeader20());
				clx = ServiceUtil.getClx(sbl, clxDaList);
				sv = ServiceUtil.getSv(sbl, svDaList);
				for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter);
				}
				SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
				for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
					setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter);
				}
				productJson.setDqmErrorFlag(sbl.getHeader38());
				if (sblErrorMap != null && sblErrorMap.size() > 0 && sblErrorMap.containsKey(sbl.getHeader1())) {
					List<SrcCxiErrorTbl> errorList = sblErrorMap.get(sbl.getHeader1());
					if (errorList != null && CollectionUtils.isNotEmpty(errorList)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorList.forEach(errorObj -> {
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
			}
			dataGridJson.getProducts().add(productJson);
		}
		logger.info("Attribute View processing end ");
		productFilter.setKeyword(productFilter.getKeyword());
		dataGridJson.setSnapfilter(removeProductFilter(productFilter));
		return dataGridJson;

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
		clxList = appOpsDartDaDao.getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, cageRowIds, cabinetRowIds,cabinetDpRowIds);
		svList = appOpsDartDaDao.getSvList(assetSet);
		
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
	public ProductDataGrid getCommonAttributeViewErrorCodeGlobal(ProductFilter productFilter) {
		try{
			String actualKeyword = productFilter.getKeyword();
			ProductFilter filterObj = productFilter;
			List<SiebelAssetDa> sblListColl = new ArrayList<>();
			List<SiebelAssetDa> sblListCollTemp = new ArrayList<>();
			List<PFilter> filterList = productFilter.getFilters();
			ProductFilterResult productFilterResult = new ProductFilterResult();
			sblListCollTemp = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(getProductFilterForErrorCodeGlobalFilter(productFilter),sblListCollTemp);
			sblListCollTemp = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList,sblListCollTemp);
			if (filterList != null && filterList.size() > 0) {
				for (int i = 0; i < filterList.size(); i++) {
					if (filterList.get(i).getKey().equalsIgnoreCase("header20")) {
						PFilter pFilterObj = filterList.get(i);
						sblListCollTemp.forEach(assetDa -> {
							if (pFilterObj.getListOfValues().contains(assetDa.getHeader20())) {
								sblListColl.add(assetDa);
							}
						});  
					}
				}
			}
			productFilterResult = getProductFilterResultErrorCodeGlobal(sblListColl);
			productFilterResult.setProductFilter(filterObj);
			List<SiebelAssetDa> sblDaList  = productFilterResult.getSblList();
			List<ClxAssetDa> clxDaList = productFilterResult.getClxList();
			List<SvAssetDa> svDaList = productFilterResult.getSvList();
			Gson gson = new Gson();
			logger.info("Product Filter " + gson.toJson(productFilter, ProductFilter.class));
			logger.info("Attribute View Siebel Data Fetched :  " + (sblDaList != null ? sblDaList.size() : 0));
			logger.info("Attribute View CLX Data Fetched :  " + (clxDaList != null ? clxDaList.size() : 0));
			logger.info("Attribute View SV Data Fetched :  " + (svDaList != null ? svDaList.size() : 0));
			SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
			Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
			HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap =(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
			Map<String,Integer> maximumAttrCount = (Map<String,Integer>) productAttributeAndLengthMap.get("max");
			ProductDataGrid dataGridJson = new ProductDataGrid();
			//dataGridJson.setTotalrows(productFilter.getTotalRows());
			ClxAssetDa clxAsset = null;
			SvAssetDa svAsset =null;
			int maxCommonAttrCount = 0;
			String maxCommonProduct = "";
			for (Map.Entry<String, Integer> entry : maximumAttrCount.entrySet()) {
				maxCommonAttrCount = entry.getValue().intValue();
				maxCommonProduct = entry.getKey();
				logger.info("Max Product : " + maxCommonProduct + ":" + maxCommonAttrCount);
				break;
			}
			for (SiebelAssetDa sbl : sblDaList) {
				Product productJson = new Product();
				if (sbl != null) {
					productJson.setName(sbl.getHeader20());
					clxAsset = ServiceUtil.getClx(sbl, clxDaList);
					svAsset = ServiceUtil.getSv(sbl, svDaList);
					setHeaders(configHeaderMap, clxAsset, svAsset, sbl, productJson, 2);
					setHeaders(configHeaderMap, clxAsset, svAsset, sbl, productJson, 6);
					setHeaders(configHeaderMap, clxAsset, svAsset, sbl, productJson, 7);
					for (Map.Entry<String, SortedMap<Integer, AttributeConfig>> entry : configProductCommonAttrMap
							.entrySet()) {
						if (isProductPresentInFilter(productFilter, entry.getKey())) {
							if (isProductContainsAnyCommonAttributeViewDisplayYes(entry.getValue())) {
								for (int attrCounter = 1; attrCounter <= maxCommonAttrCount; attrCounter++) {
									setAttributes(clxAsset, svAsset, sbl, productJson, entry.getValue(), attrCounter);
								}
							}
						}
					}
				}
				dataGridJson.getProducts().add(productJson);
			}
			logger.info("<<< Common Attribute View Processing End>>> ");
			productFilter.setKeyword(actualKeyword);
			dataGridJson.setSnapfilter(removeProductFilter(productFilter));
			return dataGridJson;
		}catch(Exception e){
			logger.error("Error in attribute view ", e);
			return null;
		}
	}
	
	@Override
	public ProductWidgets getProductWidgetsErrorCodeGlobal(ProductFilter productFilter) {
		ProductWidgets productWidgets = new ProductWidgets();
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		for (int i = 0; i < filterList.size(); i++) {
			PFilter filter = filterList.get(i);
			if (filter.getKey().equalsIgnoreCase("header20")) {
				for (String product : filter.getListOfValues()) {
					String key = productFilter.genrateCacheKey();
					sblListColl = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(
							getProductFilterForErrorCodeGlobalFilter(productFilter), sblListColl);
					sblListColl = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList, sblListColl);
					if (product.equalsIgnoreCase("Cage")) {
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cage");
						Map<String, List<SiebelAssetDa>> mapBySysName = sblListColl.stream()
								.filter(item -> item.getHeader16() != null)
								.collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
						widgetGroup.setName("");
						mapBySysName.entrySet().stream().forEach(entry -> {
							widgetGroup.getValues().add(entry.getKey());
						});
						productWidget.getGroups().add(widgetGroup);
						productWidgets.getProducts().add(productWidget);
					} else if (product.equalsIgnoreCase("Cabinet")) {
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Cabinet");
						Map<String, List<SiebelAssetDa>> mapBySysName = sblListColl.stream()
								.filter(item -> item.getHeader16() != null && (item.getHeader20() != null
										&& item.getHeader20().equalsIgnoreCase("Cabinet")))
								.collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry -> {
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if (CollectionUtils.isNotEmpty(entry.getValue())) {
								for (SiebelAssetDa sb : entry.getValue()) {
									widgetGroup.getValues().add(sb.getHeader14());
								}
							}
							widgetGroup.setName(entry.getKey());
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					} else if (product.equalsIgnoreCase("Demarcation Point")) {
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Demarcation Point");
						Map<String, List<SiebelAssetDa>> mapBySysName = sblListColl.stream()
								.filter(item -> item.getHeader16() != null && (item.getHeader20() != null
										&& item.getHeader20().equalsIgnoreCase("Demarcation Point")))
								.collect(Collectors.groupingBy(SiebelAssetDa::getHeader16));
						mapBySysName.entrySet().stream().forEach(entry -> {
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if (CollectionUtils.isNotEmpty(entry.getValue())) {
								for (SiebelAssetDa sb : entry.getValue()) {
									widgetGroup.getValues().add(sb.getHeader14());
								}
							}
							widgetGroup.setName(entry.getKey());
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					} else if (product.equalsIgnoreCase("AC Circuit")) {

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("AC Circuit");
						Map<String, List<SiebelAssetDa>> mapByCabinetNumer = sblListColl.stream()
								.filter(item -> item.getHeader14() != null && (item.getHeader20() != null
										&& item.getHeader20().equalsIgnoreCase("AC Circuit")))
								.collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry -> {
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if (CollectionUtils.isNotEmpty(entry.getValue())) {
								for (SiebelAssetDa sb : entry.getValue()) {
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() + " - " + entry.getKey();
							}
							widgetGroup.setName(groupTitle);
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					} else if (product.equalsIgnoreCase("DC Circuit")) {

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("DC Circuit");
						Map<String, List<SiebelAssetDa>> mapByCabinetNumer = sblListColl.stream()
								.filter(item -> item.getHeader14() != null && (item.getHeader20() != null
										&& item.getHeader20().equalsIgnoreCase("DC Circuit")))
								.collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry -> {
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if (CollectionUtils.isNotEmpty(entry.getValue())) {
								for (SiebelAssetDa sb : entry.getValue()) {
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() + " - " + entry.getKey();
							}
							widgetGroup.setName(groupTitle);
							productWidget.getGroups().add(widgetGroup);
						});
						productWidgets.getProducts().add(productWidget);
					} else if (product.equalsIgnoreCase("Patch Panel")) {
						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Patch Panel");
						Map<String, List<SiebelAssetDa>> mapByCabinetNumer = sblListColl.stream()
								.filter(item -> item.getHeader14() != null)
								.collect(Collectors.groupingBy(SiebelAssetDa::getHeader14));
						mapByCabinetNumer.entrySet().stream().forEach(entry -> {
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if (CollectionUtils.isNotEmpty(entry.getValue())) {
								for (SiebelAssetDa sb : entry.getValue()) {
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() + " - " + entry.getKey();
								widgetGroup.setName(groupTitle);
								productWidget.getGroups().add(widgetGroup);
							}
						});
						productWidgets.getProducts().add(productWidget);
					} else if (product.equalsIgnoreCase("Network Cable Connection")) {

						ProductWidget productWidget = new ProductWidget();
						productWidget.setName("Network Cable Connection");
						Map<String, List<SiebelAssetDa>> mapByASidePatchPanel = sblListColl.stream()
								.filter(item -> item.getAttr22() != null)
								.collect(Collectors.groupingBy(SiebelAssetDa::getAttr22));
						mapByASidePatchPanel.entrySet().stream().forEach(entry -> {
							ProductWidgetGroup widgetGroup = new ProductWidgetGroup();
							String groupTitle = "";
							if (CollectionUtils.isNotEmpty(entry.getValue())) {
								for (SiebelAssetDa sb : entry.getValue()) {
									widgetGroup.getValues().add(sb.getHeader3());
								}
								groupTitle = entry.getValue().get(0).getHeader16() + " - " + entry.getKey();
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
	public String fireSnapshotFilterForErrorCodeGlobalFilter(ProductFilter productFilter) {
		long start = System.currentTimeMillis();
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		sblListColl = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(
				getProductFilterForErrorCodeGlobalFilter(productFilter), sblListColl);
		sblListColl = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList, sblListColl);
		long end = System.currentTimeMillis();
		return "Time Consumed " + (end - start) + "; Cache Key :" + productFilter.getCacheKey();
	}
	
	@Override
	public DfrDaInput createInitiateDfrDaInputObject(ProductFilter productFilter) {
		DfrDaInput dfrDaInput = new DfrDaInput();
		dfrDaInput.setSnapshotfilter(productFilter);
		return dfrDaInput;
	}

	private Set<String> getProductList(ProductFilter productFilter) {
		Set<String> commonProducts = new HashSet<>();
		List<PFilter> pFilters = productFilter.getFilters();
		for(PFilter pFilter : pFilters){
			if(pFilter.getKey().equalsIgnoreCase("header20")){
				String[] selectedProducts = pFilter.getValue().split(",");
				for(String p : selectedProducts){
					commonProducts.add(p);
				}
			}
		}
		return commonProducts;
	}
	
	@Override
	public DfrDaInput getProductAttributeViewForErrorCodeGlobalForInitiate(ProductFilter productFilter) {
		String actualKeyword = productFilter.getKeyword();
		List<com.equinix.appops.dart.portal.model.dfr.Product> products = new ArrayList<com.equinix.appops.dart.portal.model.dfr.Product>();
		Set<String> commonProduct = getProductList(productFilter);
		List<SiebelAssetDa> sblListColl = new ArrayList<>();
		List<SiebelAssetDa> sblListCollTemp = new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		ProductFilter filterObj = productFilter;
		sblListCollTemp = appOpsDartDaDao.getSblListWhenOnlyErrorFilterApplied(getProductFilterForErrorCodeGlobalFilter(productFilter),sblListCollTemp);
		sblListCollTemp = filterSiebelAssetDAForErrorCodeGlobalFilter(filterList,sblListCollTemp);
		if (filterList != null && filterList.size() > 0) {
			for (int i = 0; i < filterList.size(); i++) {
				if (filterList.get(i).getKey().equalsIgnoreCase("header20")) {
					PFilter pFilterObj = filterList.get(i);
					sblListCollTemp.forEach(assetDa -> {
						if (pFilterObj.getListOfValues().contains(assetDa.getHeader20())) {
							sblListColl.add(assetDa);
						}
					});  
				}
			}
		}
		ProductFilterResult filterResult = getProductFilterResultErrorCodeGlobal(sblListColl);
		filterResult.setProductFilter(filterObj);
		List<SiebelAssetDa> sblDas  = filterResult.getSblList();
		List<ClxAssetDa> clxDas = filterResult.getClxList();
		List<SvAssetDa> svDas = filterResult.getSvList();
		
		/**
		 * Logic for create DFRDa input from Product Filter Result
		 */
		List<SiebelAssetDa> sblDaList = ServiceUtil.filterSiebelAssetDa(sblDas, new ArrayList<String>(commonProduct));
		List<ClxAssetDa> clxDaList = ServiceUtil.filterClxAssetDa(clxDas, commonProduct);
		List<SvAssetDa> svDaList = ServiceUtil.filterSvAssetDa(svDas, commonProduct);

		logger.info("Attribute View siebel data fetched :  " + sblDaList.size());
		logger.info("Attribute View clx data fetched :  " + clxDaList.size());
		logger.info("Attribute View Sv data fetched :  " + svDaList.size());
		
		ClxAssetDa clx = null;
		SvAssetDa sv = null;

//		start = System.currentTimeMillis();
		com.equinix.appops.dart.portal.model.dfr.Product product = null;
		Da da = null;

		for (SiebelAssetDa sbl : sblDaList) {
			product = new com.equinix.appops.dart.portal.model.dfr.Product();
			da = new Da();
			if (sbl != null) {

				clx = ServiceUtil.getClx(sbl, clxDaList);
				sv = ServiceUtil.getSv(sbl, svDaList);
				da.setSBL(sbl.getHeader1());
				da.setCLX(clx != null ? clx.getHeader1() : "");
				da.setSV(sv != null ? sv.getHeader1() : "");
				product.setDa(da);
				product.setName(sbl.getHeader20());
				products.add(product);
			}

		}
		DfrDaInput daInput = new DfrDaInput();
		daInput.setProducts(products);
		productFilter.setKeyword(actualKeyword);
		daInput.setSnapshotfilter(removeProductFilter(productFilter));
		// dataGridJson.setSnapfilter(removeProductFilter(productFilter));
		return daInput;
		
		/*SortedMap<Integer, AttributeConfig> configHeaderMap = appOpsDartDaDao.getConfigHeaderMap(); 
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap = appOpsDartDaDao.getConfigProductAttrMap();
		Map<String, Object> productAttributeAndLengthMap = appOpsDartDaDao.getConfigProdcutCommonAttrMap();
		HashMap<String, SortedMap<Integer, AttributeConfig>> configProductCommonAttrMap = 
				(HashMap<String, SortedMap<Integer, AttributeConfig>>) productAttributeAndLengthMap.get("common");
		ProductDataGrid dataGridJson = new ProductDataGrid();
		//dataGridJson.setTotalrows(productFilter.getTotalRows());
		Map<String,List<SrcCxiErrorTbl>> sblErrorMap = initDfrDao.getSblErrorMap();
		ClxAssetDa clx = null;
		SvAssetDa sv =null;
		logger.info("Error Code Global Keyword Attribute View Processing Start # isCommonGrid >>>" + Boolean.FALSE);
		for (SiebelAssetDa sbl : sblDaList) {
			Product productJson = new Product();
			if (sbl != null) {
				productJson.setName(sbl.getHeader20());
				clx = ServiceUtil.getClx(sbl, clxDaList);
				sv = ServiceUtil.getSv(sbl, svDaList);
				for (int headerCounter = 1; headerCounter <= MAX_HEADERS; headerCounter++) {
					setHeaders(configHeaderMap, clx, sv, sbl, productJson, headerCounter);
				}
				SortedMap<Integer, AttributeConfig> productAttrMap = configProductAttrMap.get(sbl.getHeader20());
				for (int attrCounter = 1; attrCounter <= MAX_ATTRIBUTES; attrCounter++) {
					setAttributes(clx, sv, sbl, productJson, productAttrMap, attrCounter);
				}
				productJson.setDqmErrorFlag(sbl.getHeader38());
				if (sblErrorMap != null && sblErrorMap.size() > 0 && sblErrorMap.containsKey(sbl.getHeader1())) {
					List<SrcCxiErrorTbl> errorList = sblErrorMap.get(sbl.getHeader1());
					if (errorList != null && CollectionUtils.isNotEmpty(errorList)) {
						StringBuffer errorBuffer = new StringBuffer();
						errorList.forEach( errorObj -> {
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
			}
			dataGridJson.getProducts().add(productJson);
		}
		logger.info("Attribute View processing end ");
		productFilter.setKeyword(productFilter.getKeyword());
		dataGridJson.setSnapfilter(removeProductFilter(productFilter));
		return dataGridJson;*/
		
	}
	
	@Override
	public List<String> getAllSystemName(){
		return elasticDao.getAllSystemName();
	}
	
	@Override
	public List<String> getAllRegion() {
		return appOpsDartDaDao.getAllRegion();
	}
	
	@Override
	public List<CountryAndIbxVo> getCountriesIbxByRegion(String region) {
		return appOpsDartDaDao.getCountriesIbxByRegion(region);
	}
	
	@Override
	public List<String> getIbxByCountriesNRegion(String region,String country) {
	
		return appOpsDartDaDao.getIbxByCountriesNRegion(region,country);
	}
	
	@Override
	public List<ErrorCodeVO> getErrorsByIbx(String ibx) {
		return appOpsDartDaDao.getErrorsByIbx(ibx);
	}
	
	@Override
	public List<AccountVo> getAllActiveAccountNum(SearchFormFilter formFilter) {
		if(DartConstants.IS_ELASTIC_CALL){
			return elasticDao.getAllActiveAccount(formFilter);
		}else{
			return appOpsDartDaDao.getAllActiveAccountNum(formFilter);
		}
		
	}
	
	@Override
	public List<ErrorCodeVO> getAllActiveErrors() {
		if(DartConstants.IS_ELASTIC_CALL){
			return elasticDao.getAllActiveErrorsElastic();
		}else{
			return appOpsDartDaDao.getAllActiveErrors();
		}
	}

	@Override
	public List<String> getProductList() {
		return appOpsDartDaDao.getAllProduct();
	}
}
