package com.equinix.appops.dart.portal.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.constant.MSCacheConstant;
import com.equinix.appops.dart.portal.dao.AppOpsDartElasticDao;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.model.errorsection.ErrorData;
import com.equinix.appops.dart.portal.model.errorsection.ErrorElasticData;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.model.search.product.ProductResp;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.util.DartUtil;
import com.google.gson.Gson;


@Repository
public class AppOpsDartElasticDaoImpl implements AppOpsDartElasticDao {

	

	public static final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
	public static final Gson gson = new Gson();
	
	@Autowired
	@Qualifier("elasticClient")
	Client client;
	
	@Override
	public ProductSearchResponse getProductTiles(ProductFilter productFilter) throws InterruptedException, ExecutionException{
		long start = System.currentTimeMillis();
		Long cleanCount = 0L ;
		Long errorCount = 0L;
		Set<String > sblRidList = null;
		for (PFilter filter : productFilter.getFilters()) {

			if (filter.getLable().toLowerCase().contains("error")) {
				filter.setValue(filter.getValue());
				if(CollectionUtils.isNotEmpty(filter.getIs()) || CollectionUtils.isNotEmpty(filter.getIsNot())){
					sblRidList = getSblListWithError(filter);
				}
			}
		}
		boolean isIgnoreMinimumshouldMatch = false;
		if (productFilter.getKeyword() == null || productFilter.getKeyword().isEmpty() || productFilter.getKeyword().equals("") ) {
			boolean header16 = false;
			boolean header2 = false;
			boolean header3 = false;
			boolean header26 = false;
			for(PFilter filter : productFilter.getFilters()){
				if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
					if (productFilter.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header16")) {
						header16 = true;
					}else if (productFilter.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header2") && filter.getValue().contains("*")) {
						header2 = true;
					}else if (productFilter.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header3")  && filter.getValue().contains("*")) {
						header3 = true;
					}else if (productFilter.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header24")  && filter.getValue().contains("*")) {
						header26 = true;
					}
				}
			}
			if(header16 || header2 || header3 || header26 ){
				isIgnoreMinimumshouldMatch = false;
			}else{
				isIgnoreMinimumshouldMatch = true;
			}
			
		}
		
		BoolQueryBuilder queryBuilder = applyProductFilter(productFilter);
		/**
		 * Apply error filter
		 */
		if(CollectionUtils.isNotEmpty(sblRidList)){
			applyErrorFilter(productFilter,queryBuilder,new ArrayList<String>(sblRidList));
		}
		
		
		if (!isIgnoreMinimumshouldMatch) {
			queryBuilder.minimumShouldMatch(1);
		}
//		queryBuilder.minimumShouldMatch(1);
	//	applyStatusCDActiveClause(queryBuilder);
		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);
		
		searchReqBuilder.addAggregation(
				 AggregationBuilders
				.terms(CacheConstant.AGG_PRODUCT_TILES)
				.field(CacheConstant.KEYWORD_HEADER20_PRODUCT_NAME)
					.subAggregation(
						 AggregationBuilders
						.terms(CacheConstant.AGG_DQM_ERROR)
						.field(CacheConstant.KEYWORD_HEADER38_DQM_ERROR_FLAG)
						.size(CacheConstant.AGG_SIZE)
					)
				)
				.setSize(0);
		System.out.println("search builder : "+ searchReqBuilder);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		Aggregations aggsProductTile =   sr.getAggregations();
		StringTerms termProducts = aggsProductTile.get(CacheConstant.AGG_PRODUCT_TILES);
		ProductSearchResponse productSearchResponse = new ProductSearchResponse();
		long totalSblRecords = 0L;
		for(Terms.Bucket productBucket : termProducts.getBuckets()){
			ProductResp product = new ProductResp();
			product.setName(productBucket.getKeyAsString());
			totalSblRecords = totalSblRecords + productBucket.getDocCount();
			cleanCount = 0L;
			errorCount = 0L;
//			System.out.println(productBucket.getKey());
			StringTerms termDqmError = productBucket.getAggregations().get(CacheConstant.AGG_DQM_ERROR);
			for(Terms.Bucket dqmBucket : termDqmError.getBuckets()){
				if(dqmBucket.getKeyAsString().equalsIgnoreCase("N")){
					cleanCount = dqmBucket.getDocCount();
				}else if(dqmBucket.getKeyAsString().equalsIgnoreCase("Y")){
					errorCount = dqmBucket.getDocCount();
				}
				product.setClean(cleanCount);
				product.setError(errorCount);
				product.setTotal(errorCount + cleanCount); 
				//System.out.println(dqmBucket.getKey() + " , " + dqmBucket.getDocCount());
			}
			productSearchResponse.getProducts().add(product);
		}
		productSearchResponse.setTotalRecords(totalSblRecords+"");
		
//		long end = System.currentTimeMillis();
	//	System.out.println("getProductTileQuery time " + (end - start));
		return productSearchResponse;
	}

	private Set<String> getSblListWithError(PFilter filter) throws InterruptedException, ExecutionException {
		BoolQueryBuilder errorQuery = QueryBuilders.boolQuery();
	    errorQuery.must(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("errorenddate")));
	    errorQuery.must(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("sblrid")));
	    errorQuery.must(QueryBuilders.boolQuery()
	                .filter(QueryBuilders.termsQuery("active.keyword", CacheConstant.Y)));
	    if(CollectionUtils.isNotEmpty(filter.getIs())){
	    	errorQuery.must(QueryBuilders.boolQuery().filter(
					QueryBuilders.termsQuery("errorcode.keyword", filter.getIs())));
		}
		if(CollectionUtils.isNotEmpty(filter.getIsNot())){
			errorQuery.mustNot(QueryBuilders.boolQuery().filter(
					QueryBuilders.termsQuery("errorcode.keyword", filter.getIsNot())));
		}
	    
	    SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_ERROR_IDX);
		searchRequest.types(CacheConstant.DART_ERROR_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(errorQuery).fetchSource(new String[]{"sblrid"}, null);;
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		Set<String > sblRidList = new HashSet<>();
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	ErrorElasticData elasticError = gson.fromJson(hitItem.getSourceAsString(), ErrorElasticData.class);
				sblRidList.add(elasticError.getSblrid());
			}
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
	   
		long end = System.currentTimeMillis();
		//System.out.println("sbl size: " + sblList.size() + " ,time " + (end-start) );
		return  sblRidList;
	}

	private void applyErrorFilter(ProductFilter productFilter, BoolQueryBuilder queryBuilder,List<String> sblRids){
		
		for(PFilter filter : productFilter.getFilters()){
			if(filter.getLable().toLowerCase().contains("error")){
				filter.setValue(filter.getValue());
				if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
					queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRids)));
				}
			}
		}
	}

	private ProductSearchResponse getProductTileseWithErrorsSblRids(List<String> sblRidList, ProductSearchResponse productSearchResponse) {
		long start = System.currentTimeMillis();
		Long cleanCount = 0L;
		Long errorCount = 0L;
		
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRidList)));
		queryBuilder.must(QueryBuilders.boolQuery().filter(
				QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER18_STATUS_CD, CacheConstant.STATUS_CD_ACTIVE)));
		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);

		searchReqBuilder
				.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_PRODUCT_TILES)
						.field(CacheConstant.KEYWORD_HEADER20_PRODUCT_NAME)
						.subAggregation(AggregationBuilders.terms(CacheConstant.AGG_DQM_ERROR)
								.field(CacheConstant.KEYWORD_HEADER38_DQM_ERROR_FLAG).size(CacheConstant.AGG_SIZE)))
				.setSize(0);
//		System.out.println("search build : "+ searchReqBuilder);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		Aggregations aggsProductTile = sr.getAggregations();
		StringTerms termProducts = aggsProductTile.get(CacheConstant.AGG_PRODUCT_TILES);
//		ProductSearchResponse productSearchResponse = new ProductSearchResponse();
		long totalSblRecords = 0L;
		for (Terms.Bucket productBucket : termProducts.getBuckets()) {
			ProductResp product = new ProductResp();
			product.setName(productBucket.getKeyAsString());
			totalSblRecords = totalSblRecords + productBucket.getDocCount();
			cleanCount = 0L;
			errorCount = 0L;
			System.out.println(productBucket.getKey());
			StringTerms termDqmError = productBucket.getAggregations().get(CacheConstant.AGG_DQM_ERROR);
			for (Terms.Bucket dqmBucket : termDqmError.getBuckets()) {
				if (dqmBucket.getKeyAsString().equalsIgnoreCase("N")) {
					cleanCount = dqmBucket.getDocCount();
				} else if (dqmBucket.getKeyAsString().equalsIgnoreCase("Y")) {
					errorCount = dqmBucket.getDocCount();
				}
				product.setClean(cleanCount);
				product.setError(errorCount);
				product.setTotal(errorCount + cleanCount);
				System.out.println(dqmBucket.getKey() + " , " + dqmBucket.getDocCount());
			}
			productSearchResponse.getProducts().add(product);
		}
		String totalRecords = productSearchResponse.getTotalRecords();
		long total = 0;
		if(StringUtils.isNoneEmpty(totalRecords) && StringUtils.isNotBlank(totalRecords)){
			total = Long.parseLong(totalRecords);
			total += totalSblRecords;
		}
		productSearchResponse.setTotalRecords(total + "");

		Gson g = new Gson();
//		System.out.println(g.toJson(productSearchResponse));

		long end = System.currentTimeMillis();
//		System.out.println("getProductTileQuery time " + (end - start));
		return productSearchResponse;
	}

	@Override
	public HashMap<String,List<String>> getFilterList(ProductFilter productFilter){
		long start = System.currentTimeMillis();
		HashMap<String,List<String>> resultMap = new HashMap<>();
	//	productFilter.setFilters(null);
		BoolQueryBuilder queryBuilder = applyProductFilter(productFilter);
		queryBuilder.minimumShouldMatch(1);
		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);
		searchReqBuilder.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_IBX).field(CacheConstant.KEYWORD_HEADER8_IBX).size(CacheConstant.AGG_SIZE))
						.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_ACCOUNT_NUM).field(CacheConstant.KEYWORD_HEADER6_ACC_NUM).size(CacheConstant.AGG_SIZE))
						.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_SYSTEM_NAME).field(CacheConstant.KEYWORD_HEADER16_SYSTEM_NAME).size(CacheConstant.AGG_SIZE))
						.setSize(0).setFetchSource(false);
						
		SearchResponse sr = searchReqBuilder.setFetchSource(false).execute().actionGet();
		Aggregations aggs =   sr.getAggregations();
		
		
		System.out.println("Check Point " + aggs);
		StringTerms term = aggs.get(CacheConstant.AGG_IBX);
		List<String> ibxList = new ArrayList<>();
		for(Terms.Bucket bucket :term.getBuckets()){
			ibxList.add(bucket.getKeyAsString());
		}
		
		term = aggs.get(CacheConstant.AGG_ACCOUNT_NUM);
		List<String> accountNameList = new ArrayList<>();
		for(Terms.Bucket bucket :term.getBuckets()){
			accountNameList.add(bucket.getKeyAsString());
		}
		
		term = aggs.get(CacheConstant.AGG_SYSTEM_NAME);
		List<String> sysNameList = new ArrayList<>();
		for(Terms.Bucket bucket :term.getBuckets()){
			sysNameList.add(bucket.getKeyAsString());
		}
		
		resultMap.put("ibx", ibxList);
		resultMap.put("accNum", accountNameList);
		resultMap.put("sysName", sysNameList);
		long end = System.currentTimeMillis();
		System.out.println("getFilterList time " + (end - start));
		return resultMap;
	}

	
	private List<PFilter>  getProductPfilter(String product, List<PFilter> pfilterList){
		PFilter pf = new PFilter();
		pf.setKey("header20");
		pf.setValue(product);
		pf.setLable("Product");
		List<PFilter> pfilterListNew = new ArrayList<>();
		for(PFilter pFilter : pfilterList ){
			if(!pFilter.getKey().equalsIgnoreCase("header20")){
				pfilterListNew.add(pFilter);
			}
		}
		pfilterListNew.add(pf);
		return pfilterListNew;
	}
	
	private List<ProductFilter> getProductWiseProductFilter(ProductFilter productFilter) throws IllegalAccessException, InvocationTargetException{
		List<ProductFilter> listOfProductFilters = new ArrayList<>();
		for(PFilter pf : productFilter.getFilters()){
			if(pf.getKey().equalsIgnoreCase("header20") && StringUtils.isNotBlank(pf.getValue())){
				pf.setValue(pf.getValue());
				if(CollectionUtils.isNotEmpty(pf.getListOfValues()) && pf.getListOfValues().size()>1){
				 	for(String product :pf.getListOfValues() ){
				 		ProductFilter  productFilterNew = new ProductFilter();
				 		BeanUtils.copyProperties(productFilterNew, productFilter);
				 		productFilterNew.setFilters(null);
				 		List<PFilter> pfilterListNew  = getProductPfilter(product,productFilter.getFilters());
				 		productFilterNew.setFilters(pfilterListNew);
				 		listOfProductFilters.add(productFilterNew);
				 	}
				}else {
					listOfProductFilters.add(productFilter);
				}
			}
		}
		return listOfProductFilters;
	}
	@Override
	public ProductFilterResult getDataForDfrElastic(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException{

		ProductFilter productFilterMain = productFilterResult.getProductFilter();
		List<SiebelAssetDa> sblList = new ArrayList<>();
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
		
		BoolQueryBuilder query = applyProductFilterWithoutProduct(productFilterMain);
		query.minimumShouldMatch(1);
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);
		searchSourceBuilder.size(1000);
		searchRequest.source(searchSourceBuilder);
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get();
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
//		System.out.println(" Hits " + searchHits.length );
		long start = System.currentTimeMillis();
		if(searchHits.length>1000){
			while (searchHits != null && searchHits.length > 0) { 
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
				scrollRequest.scroll(scroll);
				ActionFuture<SearchResponse> resp2 = client.searchScroll(scrollRequest);
				SearchResponse searchResponse2 = resp2.get();
				scrollId = searchResponse2.getScrollId();
				searchHits = searchResponse2.getHits().getHits();
				for(SearchHit hitItem :searchHits ){
					SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
					sblList.add(sbl);
//					System.out.println(sbl.getHeader1() + "," + sbl.getHeader8() + ", " + sbl.getHeader16() + ", " + sbl.getHeader18() + "," + sbl.getHeader20());
					assetSet.add(sbl.getHeader2());
					sblRowIds.add(sbl.getHeader1());
					if("Cage".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader10()){
						cageUniquesSpaceId.add(sbl.getHeader10());
					}else if("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
						cabUniqueSpaceId.add(sbl.getHeader12());
					}else if("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
						cabDpUniqueSpaceId.add(sbl.getHeader12());
					}
				}
			}
		}else {
//			System.out.println("search hits less than 1000");
			for(SearchHit hitItem :searchHits ){
				SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
				System.out.println(sbl.getHeader1() + "," + sbl.getHeader8() + ", " + sbl.getHeader16() + ", " + sbl.getHeader18() + "," + sbl.getHeader20());
				assetSet.add(sbl.getHeader2());
				sblRowIds.add(sbl.getHeader1());
				if("Cage".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader10()){
					cageUniquesSpaceId.add(sbl.getHeader10());
				}else if("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
					cabUniqueSpaceId.add(sbl.getHeader12());
				}else if("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
					cabDpUniqueSpaceId.add(sbl.getHeader12());
				}
			}
		}
		
		clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId,cabDpUniqueSpaceId,cageRowIds,cabinetRowIds,cabinetDpRowIds);
		svList = getSvList(assetSet);

		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		productFilterResult.setClxCabninetRowIds(cabinetRowIds);
		productFilterResult.setClxCageRowIds(cageRowIds);
		productFilterResult.setSblRowIds(sblRowIds);
		productFilterResult.setClxCabninetDpRowIds(cabinetDpRowIds);
		long end = System.currentTimeMillis();
		System.out.println("sbl size: " + sblList.size() + " ,time " + (end-start) );
		return productFilterResult;
	
	}
	
	@Override
	public  ProductFilterResult getProductAttributeView(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException{
		ProductFilter productFilterMain = productFilterResult.getProductFilter();

		long start = System.currentTimeMillis();
		List<SiebelAssetDa> sblList = new ArrayList<>();
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


		try {
			List<ProductFilter> productWiseProductFilter = getProductWiseProductFilter(productFilterMain);
			for(ProductFilter productFilter : productWiseProductFilter ){
				BoolQueryBuilder queryBuilder = applyProductFilter(productFilter);
			//	applyStatusCDActiveClause(queryBuilder);
				boolean isIgnoreMinimumshouldMatch = false;
				if (productFilterMain.getKeyword() == null || productFilterMain.getKeyword().isEmpty() || productFilterMain.getKeyword().equals("") ) {
					boolean header16 = false;
					for(PFilter filter : productFilter.getFilters()){
						if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
							if (productFilter.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header16")) {
								header16 = true;
							}
						}
					}
					if(header16){
						isIgnoreMinimumshouldMatch = false;
					}else{
						isIgnoreMinimumshouldMatch = true;
					}
				}
				if (!isIgnoreMinimumshouldMatch) {
					queryBuilder.minimumShouldMatch(1);
				}
				SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);

				SearchResponse sr = null;
				sr = searchReqBuilder.execute().actionGet();
				
				if(sr.getHits()!=null && sr.getHits().getTotalHits()!=null){
					productFilterResult.setTotalRows(sr.getHits().getTotalHits().value);
				}
				SearchHit[] searchHits = sr.getHits().getHits();
				for(SearchHit hitItem :searchHits ){
					SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
					sblList.add(sbl);
//					System.out.println(sbl.getHeader1() + "," + sbl.getHeader8() + ", " + sbl.getHeader16() + ", " + sbl.getHeader18() + "," + sbl.getHeader20());
					assetSet.add(sbl.getHeader2());
					sblRowIds.add(sbl.getHeader1());
					if("Cage".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader10()){
						cageUniquesSpaceId.add(sbl.getHeader10());
					}else if("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
						cabUniqueSpaceId.add(sbl.getHeader12());
					}else if("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
						cabDpUniqueSpaceId.add(sbl.getHeader12());
					}
				}

				clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId,cabDpUniqueSpaceId,cageRowIds,cabinetRowIds,cabinetDpRowIds);
				svList = getSvList(assetSet);

				productFilterResult.setSblList(sblList);
				productFilterResult.setClxList(clxList);
				productFilterResult.setSvList(svList);
				productFilterResult.setClxCabninetRowIds(cabinetRowIds);
				productFilterResult.setClxCageRowIds(cageRowIds);
				productFilterResult.setSblRowIds(sblRowIds);
				productFilterResult.setClxCabninetDpRowIds(cabinetDpRowIds);

			}
			long end = System.currentTimeMillis();
			System.out.println("getProductTileQuery time " + (end - start));
			System.out.println("done");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return productFilterResult;
	}
	@Override
	public List<SvAssetDa> getSvList(Set<String> assetList) throws InterruptedException, ExecutionException {
		List<SvAssetDa> svList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(assetList)) {
			BoolQueryBuilder query = QueryBuilders.boolQuery();
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER2_ASSET_NUM, assetList)));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.scroll(scroll);
			searchRequest.indices(CacheConstant.DART_SV_IDX);
			searchRequest.types(CacheConstant.DART_SV_DOCUMENT_TYPE);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1000);
			searchSourceBuilder.query(query);
//			System.out.println("query builder : " + clxQuery);
			searchRequest.source(searchSourceBuilder);

			ActionFuture<SearchResponse> resp = client.search(searchRequest);
			SearchResponse searchResponse = resp.get();
			String scrollId = searchResponse.getScrollId();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			long start = System.currentTimeMillis();
			while (searchHits != null && searchHits.length > 0) {

				for (SearchHit hitItem : searchHits) {
					SvAssetDa sv = gson.fromJson(hitItem.getSourceAsString(), SvAssetDa.class);
					svList.add(sv);
				}
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
				scrollRequest.scroll(scroll);
				resp = client.searchScroll(scrollRequest);
				searchResponse = resp.get();
				scrollId = searchResponse.getScrollId();
				searchHits = searchResponse.getHits().getHits();
			}
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			clearScrollRequest.addScrollId(scrollId);
			ActionFuture<ClearScrollResponse> clrearResp = client.clearScroll(clearScrollRequest);
			ClearScrollResponse clearScrollResponse = clrearResp.get();
			boolean succeeded = clearScrollResponse.isSucceeded();
			System.out.println("succeeded" + succeeded);
			long end = System.currentTimeMillis();
//			System.out.println("sv size:  " + svList.size() + " ,time " + (end - start));

		}
		
		
		/*
		
		List<SvAssetDa> svList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(assetList)){
			BoolQueryBuilder query = QueryBuilders.boolQuery();
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER2_ASSET_NUM, assetList)));
			SearchRequestBuilder searchReqBuilder =  getSvSearchRequestBuilder(query);
			SearchResponse sr = searchReqBuilder.execute().actionGet();
			SearchHit[] searchHits = sr.getHits().getHits();
			for(SearchHit hitItem :searchHits ){
				SvAssetDa sv = gson.fromJson(hitItem.getSourceAsString(), SvAssetDa.class);
				svList.add(sv);
			}
			
		}
		return svList;*/
		return svList;
	}

	@Override
	public List<ClxAssetDa> getClxList(Set<String> cageList ,Set<String> cabList,Set<String> cabDpList, Set<String> cageRowIds, Set<String> cabinetRowIds,Set<String> cabinetDpRowIds ) throws InterruptedException, ExecutionException {
		List<ClxAssetDa> clxList= new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(cageList)){
			BoolQueryBuilder query = QueryBuilders.boolQuery();
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header10.keyword", cageList)));
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header20.keyword", CacheConstant.CAGE_CHECK)));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.scroll(scroll);
			searchRequest.indices(CacheConstant.DART_CLX_IDX);
			searchRequest.types(CacheConstant.DART_CLX_DOCUMENT_TYPE);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1000);
			searchSourceBuilder.query(query);
//			System.out.println("query builder : "+ query);
			searchRequest.source(searchSourceBuilder);
			
			ActionFuture<SearchResponse> resp = client.search(searchRequest);
			SearchResponse searchResponse = resp.get(); 
			String scrollId = searchResponse.getScrollId();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			
			while (searchHits != null && searchHits.length > 0) {

			    for(SearchHit hitItem :searchHits ){
			    	ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
					clxList.add(clx);
			    }
			    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
			    scrollRequest.scroll(scroll);
			    resp = client.searchScroll(scrollRequest);
			    searchResponse = resp.get(); 
			    scrollId = searchResponse.getScrollId();
			    searchHits = searchResponse.getHits().getHits();
			}
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
			clearScrollRequest.addScrollId(scrollId);
			ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
			ClearScrollResponse clearScrollResponse = clrearResp.get();
			boolean succeeded = clearScrollResponse.isSucceeded();
		    System.out.println("succeeded" + succeeded);
		    System.out.println("clx size:  " + clxList.size());
			
		}
		if(CollectionUtils.isNotEmpty(cabList)){

			BoolQueryBuilder query = QueryBuilders.boolQuery();
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header12.keyword", cabList)));
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header20.keyword", CacheConstant.CABINET_CHECK)));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.scroll(scroll);
			searchRequest.indices(CacheConstant.DART_CLX_IDX);
			searchRequest.types(CacheConstant.DART_CLX_DOCUMENT_TYPE);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1000);
			searchSourceBuilder.query(query);
//			System.out.println("query builder : "+ query);
			searchRequest.source(searchSourceBuilder);
			
			ActionFuture<SearchResponse> resp = client.search(searchRequest);
			SearchResponse searchResponse = resp.get(); 
			String scrollId = searchResponse.getScrollId();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			
			while (searchHits != null && searchHits.length > 0) {

			    for(SearchHit hitItem :searchHits ){
			    	ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
					clxList.add(clx);
			    }
			    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
			    scrollRequest.scroll(scroll);
			    resp = client.searchScroll(scrollRequest);
			    searchResponse = resp.get(); 
			    scrollId = searchResponse.getScrollId();
			    searchHits = searchResponse.getHits().getHits();
			}
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
			clearScrollRequest.addScrollId(scrollId);
			ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
			ClearScrollResponse clearScrollResponse = clrearResp.get();
			boolean succeeded = clearScrollResponse.isSucceeded();
		    System.out.println("succeeded" + succeeded);
		    System.out.println("clx size:  " + clxList.size());
			
			/**
			 * Old code
			 */
			/*SearchRequestBuilder searchReqBuilder =  getClxSearchRequestBuilder(query);
			SearchResponse sr = searchReqBuilder.execute().actionGet();
			SearchHit[] searchHits = sr.getHits().getHits();
			for(SearchHit hitItem :searchHits ){
				ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
				clxList.add(clx);
			}*/
		
		}
		
		if(CollectionUtils.isNotEmpty(cabDpList)){
			BoolQueryBuilder query = QueryBuilders.boolQuery();
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header12.keyword", cabDpList)));
			query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header20.keyword", CacheConstant.DEMARCATION_POINT_CHECK)));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.scroll(scroll);
			searchRequest.indices(CacheConstant.DART_CLX_IDX);
			searchRequest.types(CacheConstant.DART_CLX_DOCUMENT_TYPE);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1000);
			searchSourceBuilder.query(query);
//			System.out.println("query builder : "+ query);
			searchRequest.source(searchSourceBuilder);
			
			ActionFuture<SearchResponse> resp = client.search(searchRequest);
			SearchResponse searchResponse = resp.get(); 
			String scrollId = searchResponse.getScrollId();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			
			while (searchHits != null && searchHits.length > 0) {

			    for(SearchHit hitItem :searchHits ){
			    	ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
					clxList.add(clx);
			    }
			    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
			    scrollRequest.scroll(scroll);
			    resp = client.searchScroll(scrollRequest);
			    searchResponse = resp.get(); 
			    scrollId = searchResponse.getScrollId();
			    searchHits = searchResponse.getHits().getHits();
			}
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
			clearScrollRequest.addScrollId(scrollId);
			ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
			ClearScrollResponse clearScrollResponse = clrearResp.get();
			boolean succeeded = clearScrollResponse.isSucceeded();
		    System.out.println("succeeded" + succeeded);
		    System.out.println("clx size:  " + clxList.size());
			
			/**
			 * Old code
			 */
			/*SearchRequestBuilder searchReqBuilder =  getClxSearchRequestBuilder(query);
			SearchResponse sr = searchReqBuilder.execute().actionGet();
			SearchHit[] searchHits = sr.getHits().getHits();
			for(SearchHit hitItem :searchHits ){
				ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
				clxList.add(clx);
			}*/
		}
		
		return clxList;
	}

	public  BoolQueryBuilder applyProductFilter(ProductFilter productFilter){
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		if(StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters()==null){
			applyGolbalSearchColumnsOnQuery(productFilter, query);
			applyStatusCDActiveClause(query);
			return query;
		}else if(StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters()!=null) {
			applyGolbalSearchColumnsOnQuery(productFilter, query);
			applyFiltersOnSearchQuery(productFilter, query);
			applyStatusCDActiveClause(query);
			return query;
		} else if(StringUtils.isEmpty(productFilter.getKeyword())){
			applyFiltersOnSearchQuery(productFilter, query);
			applyStatusCDActiveClause(query);
			return query;
		}
		return null;
	}
	


	private void applyFiltersOnSearchQuery(ProductFilter productFilter, BoolQueryBuilder query) {
		for (PFilter filter : productFilter.getFilters()) {
			if (!filter.getLable().toLowerCase().contains("error") && !filter.getKey().equalsIgnoreCase("header20")) {
				// filter.setValue(filter.getValue());
				if (CollectionUtils.isNotEmpty(productFilter.getSearchDropBox().getSearchDrop())
						&& filter.getKey().equalsIgnoreCase("header16")) {
					List<String> systemNameList = DartUtil.deserializedSystemName(filter.getListOfValues());
					query.should(QueryBuilders.boolQuery()
							.filter(QueryBuilders.termsQuery(filter.getKey() + ".keyword", systemNameList)));
					query.should(QueryBuilders.boolQuery()
							.filter(QueryBuilders.termsQuery("attr253.keyword", systemNameList)));
				} else {
					if (filter.getKey().equalsIgnoreCase("header2") || filter.getKey().equalsIgnoreCase("header3")
							|| filter.getKey().equalsIgnoreCase("header24")) {
						if (filter.getValue().contains("*")) {
							if (CollectionUtils.isNotEmpty(filter.getListOfValues())) {
								for (String val : filter.getListOfValues()) {
									query.should(QueryBuilders.wildcardQuery(filter.getKey() + ".keyword", val));
								}
							}

						} else {
							query.must(QueryBuilders.boolQuery().filter(
									QueryBuilders.termsQuery(filter.getKey() + ".keyword", filter.getListOfValues())));
						}
					} else {
						if (CollectionUtils.isNotEmpty(filter.getIs())) {
							query.must(QueryBuilders.boolQuery()
									.filter(QueryBuilders.termsQuery(filter.getKey() + ".keyword", filter.getIs())));
						}
						if (CollectionUtils.isNotEmpty(filter.getIsNot())) {
							query.mustNot(QueryBuilders.boolQuery()
									.filter(QueryBuilders.termsQuery(filter.getKey() + ".keyword", filter.getIsNot())));
						}
					}

				}
			}
		}

	}

	

	private void applyStatusCDActiveClause(BoolQueryBuilder query) {
		query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER18_STATUS_CD, CacheConstant.STATUS_CD_ACTIVE)));
	}

	private  void applyGolbalSearchColumnsOnQuery(ProductFilter productFilter, BoolQueryBuilder query) {
		for(String searchColumn : CacheConstant.globalFilters2){
			query.should(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(searchColumn, productFilter.getKeyword())));
		}
		
	} 
	
	
	private SearchRequestBuilder getSblSearchRequestBuilder(BoolQueryBuilder queryBuilder) {
		return this.client.prepareSearch(MSCacheConstant.DART_MS_SBL_IDX).setTypes(MSCacheConstant.DART_MS_SBL_DOCUMENT_TYPE).setQuery(queryBuilder);
	}
	
	private SearchRequestBuilder getClxSearchRequestBuilder(BoolQueryBuilder queryBuilder) {
		return this.client.prepareSearch(CacheConstant.DART_CLX_IDX).setTypes(CacheConstant.DART_CLX_DOCUMENT_TYPE).setQuery(queryBuilder);
	}
	
	private SearchRequestBuilder getSvSearchRequestBuilder(BoolQueryBuilder queryBuilder) {
		return this.client.prepareSearch(CacheConstant.DART_SV_IDX).setTypes(CacheConstant.DART_SV_DOCUMENT_TYPE).setQuery(queryBuilder);
	}
	
	private SearchRequestBuilder getErrorSearchRequestBuilder(BoolQueryBuilder queryBuilder) {
		return this.client.prepareSearch(CacheConstant.DART_ERROR_IDX).setTypes(CacheConstant.DART_ERROR_DOCUMENT_TYPE).setQuery(queryBuilder);
	}
	
	
	private BoolQueryBuilder getErrorDataQueryByDA(String da, Set<String> rowIds,boolean keywordHasVal , List<String> errCodeSet ){
		BoolQueryBuilder errorQuery = QueryBuilders.boolQuery();
		
	    errorQuery.must(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("errorenddate")));
		errorQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("active.keyword", CacheConstant.Y)));
		if(keywordHasVal){
			errorQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("merrorcode.keyword", errCodeSet)));
			/*errorQuery.should(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("sblrid.keyword", rowIds)));
			errorQuery.should(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("clxrid.keyword", rowIds)));
			errorQuery.should(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("svrid.keyword", rowIds)));*/
		}
		if(da.equalsIgnoreCase("sbl")){
			errorQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("sblrid.keyword", rowIds)));
		}else if(da.equalsIgnoreCase("clx")){
			errorQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("clxrid.keyword", rowIds)));
		}else if(da.equalsIgnoreCase("sv")){
			errorQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("svrid.keyword", rowIds)));
		}
		return errorQuery;
	}
	
	
	@Override
	public  List<ErrorData> getErrorSection(ProductFilter productFilter, boolean keywordHasVal) throws InterruptedException, ExecutionException{
		List<ErrorData> errorList = new ArrayList<>();
		List<SiebelAssetDa> sblList = new ArrayList<>();
		List<ClxAssetDa> clxList = new ArrayList<>();
		List<SvAssetDa> svList = new ArrayList<>();
		HashMap<String, SiebelAssetDa> sblMap = new HashMap<>();
		HashMap<String, ClxAssetDa> clxMap = new HashMap<>();
		HashMap<String, SvAssetDa> svMap = new HashMap<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
	
		Set<String> sblRidSet = new HashSet<>();
		Set<String> clxRidSet = new HashSet<>();
		Set<String> svRidSet = new HashSet<>();
		List<String> errCodeSet = new ArrayList<>();
		
		Set<String> sblRidSetForErrCode = new HashSet<>();
		HashMap<String, ErrorData> errorMap = new HashMap<>();
		
		BoolQueryBuilder query = applyProductFilter(productFilter);
		query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header38.keyword", CacheConstant.Y)));
		query.must(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("header2")));
		//query.minimumShouldMatch(1);
		boolean isIgnoreMinimumshouldMatch = false;
		
		if (productFilter.getKeyword() == null || productFilter.getKeyword().isEmpty() || productFilter.getKeyword().equals("") || keywordHasVal ) {
				isIgnoreMinimumshouldMatch = true;
		}
		if (!isIgnoreMinimumshouldMatch) {
			query.minimumShouldMatch(1);
		}
		if(keywordHasVal){
			List<PFilter> filters = productFilter.getFilters();
			PFilter filter = null ;
			for(int fcount= 0 ; fcount< filters.size() ; fcount++){
				filter = filters.get(fcount);
				if(filter.getLable().toLowerCase().contains("error")){
				  if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
					  errCodeSet = filter.getListOfValues();
				  }
			  	}
			}
		}
		
		if(keywordHasVal){
			getErrorData(getErrorDataQueryByDA("", sblRidSet,keywordHasVal,errCodeSet),sblMap, clxMap, svMap, errorList,"SBL",keywordHasVal,errorMap);
			
			
			if(null!=errorList){
				List<ErrorData> errorListWithErrCode = new ArrayList<>();
				errorListWithErrCode = errorList;
				errorList = new ArrayList<>();
				for(ErrorData errorData:errorListWithErrCode){
					sblRidSetForErrCode.add(errorData.getRowId());
				}
				query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRidSetForErrCode)));
				
				getSblList(query,sblRidSet,sblList,sblMap,assetSet,cageUniquesSpaceId,
						cabUniqueSpaceId,cabDpUniqueSpaceId);
				getClxList(clxRidSet,clxList,clxMap,cageUniquesSpaceId,cabUniqueSpaceId,cabDpUniqueSpaceId);
				getSvList(svRidSet,svList,svMap,assetSet);
				
				for(String sblRid: sblRidSet){
					ErrorData errorData = errorMap.get(sblRid);
					errorData.setAssetNum(sblMap.get(sblRid).getHeader2());
					errorList.add(errorData);
				}
				
				if(CollectionUtils.isNotEmpty(clxRidSet)){
					getErrorData(getErrorDataQueryByDA("clx", clxRidSet,keywordHasVal,errCodeSet),sblMap, clxMap, svMap, errorList,"CLX",keywordHasVal,errorMap);
				}
				if(CollectionUtils.isNotEmpty(svRidSet)){
					getErrorData(getErrorDataQueryByDA("sv", svRidSet, keywordHasVal,errCodeSet),sblMap, clxMap, svMap, errorList,"SV",keywordHasVal,errorMap);
				}
				
				/*for(String clxRid: clxRidSet){
					ErrorData errorData = errorMap.get(clxRid);
					if(null != errorData){
						errorData.setAssetNum(clxMap.get(clxRid).getHeader2());
						errorList.add(errorData);
					}
				}
				for(String svRid: svRidSet){
					ErrorData errorData = errorMap.get(svRid);
					if(null != errorData){
						errorData.setAssetNum(svMap.get(svRid).getHeader2());
						errorList.add(errorData);
					}
					
				}*/
			}
			
		}else{
	
			getSblList(query,sblRidSet,sblList,sblMap,assetSet,cageUniquesSpaceId,
					cabUniqueSpaceId,cabDpUniqueSpaceId);
			getClxList(clxRidSet,clxList,clxMap,cageUniquesSpaceId,cabUniqueSpaceId,cabDpUniqueSpaceId);
			getSvList(svRidSet,svList,svMap,assetSet);
			// sbl errors 
			if(CollectionUtils.isNotEmpty(sblRidSet)){
				getErrorData(getErrorDataQueryByDA("sbl", sblRidSet,keywordHasVal,errCodeSet),sblMap, clxMap, svMap, errorList,"SBL",keywordHasVal,errorMap);
			}
			if(CollectionUtils.isNotEmpty(clxRidSet)){
				getErrorData(getErrorDataQueryByDA("clx", clxRidSet,keywordHasVal,errCodeSet),sblMap, clxMap, svMap, errorList,"CLX",keywordHasVal,errorMap);
			}
			if(CollectionUtils.isNotEmpty(svRidSet)){
				getErrorData(getErrorDataQueryByDA("sv", svRidSet, keywordHasVal,errCodeSet),sblMap, clxMap, svMap, errorList,"SV",keywordHasVal,errorMap);
			}
		}	
			System.out.println("check1");
			System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			flushAllErrorCollection(sblList,clxList,svList,sblMap,clxMap,svMap,assetSet,cageUniquesSpaceId,
					cabUniqueSpaceId,cabDpUniqueSpaceId,sblRidSet,clxRidSet,svRidSet);
			
			System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		
		return errorList;
		
	}
	
	private void flushAllErrorCollection(List<SiebelAssetDa> sblList, List<ClxAssetDa> clxList, List<SvAssetDa> svList,
			HashMap<String, SiebelAssetDa> sblMap, HashMap<String, ClxAssetDa> clxMap, HashMap<String, SvAssetDa> svMap,
			Set<String> assetSet, Set<String> cageUniquesSpaceId, Set<String> cabUniqueSpaceId,
			Set<String> cabDpUniqueSpaceId, Set<String> sblRidSet, Set<String> clxRidSet, Set<String> svRidSet) {
		// TODO Auto-generated method stub
		sblList = null; clxList = null; svList =null ;	sblMap =null ; 	clxMap = null; clxMap =null;assetSet=null;
		cageUniquesSpaceId = null;cageUniquesSpaceId=null;cabUniqueSpaceId=null; cabDpUniqueSpaceId =null;sblRidSet=null;
		sblRidSet=null;clxRidSet=null;svRidSet=null;
	}

	private void getErrorData(BoolQueryBuilder errorDataQueryByDA, HashMap<String, SiebelAssetDa> sblMap, HashMap<String, ClxAssetDa> clxMap, HashMap<String, SvAssetDa> svMap, List<ErrorData> errorList, String TBL,boolean keywordHasVal,HashMap<String, ErrorData> errorMap) throws InterruptedException, ExecutionException {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_ERROR_IDX);
		searchRequest.types(CacheConstant.DART_ERROR_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(errorDataQueryByDA).fetchSource(CacheConstant.ERROR_COLUMNS_TO_INCLUDE, null);
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(errorDataQueryByDA);
//		System.out.println("query builder : "+ errorDataQueryByDA);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		long start = System.currentTimeMillis();
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	if(keywordHasVal){
		    		populateErrorDataWithErrorCode(sblMap, clxMap, svMap, errorList,errorMap, TBL, hitItem);
		    	}else{
		    		populateErrorData(sblMap, clxMap, svMap, errorList, TBL, hitItem);
		    	}
		    	
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
	}

	private void populateErrorData(HashMap<String, SiebelAssetDa> sblMap, HashMap<String, ClxAssetDa> clxMap,
			HashMap<String, SvAssetDa> svMap, List<ErrorData> errorList, String TBL, SearchHit hitItem) {
		ErrorElasticData elasticError = gson.fromJson(hitItem.getSourceAsString(), ErrorElasticData.class);
		if(TBL.equalsIgnoreCase("sbl") && sblMap.size()>0 && elasticError.getSblrid()!=null){
			elasticError.setAssetNum(sblMap.get(elasticError.getSblrid()).getHeader2());
			errorList.add(elasticError.buildSblErrorData());
		}
		if(TBL.equalsIgnoreCase("clx") && clxMap.size()>0 && elasticError.getClxrid()!=null){
			elasticError.setAssetNum(clxMap.get(elasticError.getClxrid()).getHeader2());
			errorList.add(elasticError.buildClxErrorData());
		}
		if(TBL.equalsIgnoreCase("sv") && svMap.size()>0 && elasticError.getSvrid()!=null){
			elasticError.setAssetNum(svMap.get(elasticError.getSvrid()).getHeader2());
			errorList.add(elasticError.buildSvErrorData());
		}
	}
	
	private void populateErrorDataWithErrorCode(HashMap<String, SiebelAssetDa> sblMap, HashMap<String, ClxAssetDa> clxMap,
			HashMap<String, SvAssetDa> svMap, List<ErrorData> errorList, HashMap<String, ErrorData> errorMap, String TBL, SearchHit hitItem) {
		ErrorElasticData elasticError = gson.fromJson(hitItem.getSourceAsString(), ErrorElasticData.class);
		if(TBL.equalsIgnoreCase("sbl") && elasticError.getSblrid()!=null){
		//	elasticError.setAssetNum(elasticError.getAssetNum());
			errorList.add(elasticError.buildSblErrorData());
			errorMap.put(elasticError.getSblrid(), elasticError.buildSblErrorData());
		}
		if(TBL.equalsIgnoreCase("clx") && elasticError.getClxrid()!=null){
		//	elasticError.setAssetNum(elasticError.getAssetNum());
			errorList.add(elasticError.buildClxErrorData());
			errorMap.put(elasticError.getClxrid(), elasticError.buildClxErrorData());
		}
		if(TBL.equalsIgnoreCase("sv") && elasticError.getSvrid()!=null){
		//	elasticError.setAssetNum(elasticError.getAssetNum());
			errorList.add(elasticError.buildSvErrorData());
			errorMap.put(elasticError.getSvrid(), elasticError.buildSvErrorData());
		}
	}

	private void getSvList(Set<String> svRidSet, List<SvAssetDa> svList, HashMap<String, SvAssetDa> svMap,
			Set<String> assetSet) throws InterruptedException, ExecutionException {
		if (CollectionUtils.isNotEmpty(assetSet)) {
			BoolQueryBuilder svQuery = QueryBuilders.boolQuery();
			svQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header2.keyword", assetSet)));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.scroll(scroll);
			searchRequest.indices(CacheConstant.DART_SV_IDX);
			searchRequest.types(CacheConstant.DART_SV_DOCUMENT_TYPE);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1000);
			searchSourceBuilder.query(svQuery);
//			System.out.println("query builder : " + clxQuery);
			searchRequest.source(searchSourceBuilder);

			ActionFuture<SearchResponse> resp = client.search(searchRequest);
			SearchResponse searchResponse = resp.get();
			String scrollId = searchResponse.getScrollId();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			long start = System.currentTimeMillis();
			while (searchHits != null && searchHits.length > 0) {

				for (SearchHit hitItem : searchHits) {
					SvAssetDa sv = gson.fromJson(hitItem.getSourceAsString(), SvAssetDa.class);
					svList.add(sv);
					svRidSet.add(sv.getHeader1());
					svMap.put(sv.getHeader1(), sv);
				}
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
				scrollRequest.scroll(scroll);
				resp = client.searchScroll(scrollRequest);
				searchResponse = resp.get();
				scrollId = searchResponse.getScrollId();
				searchHits = searchResponse.getHits().getHits();
			}
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			clearScrollRequest.addScrollId(scrollId);
			ActionFuture<ClearScrollResponse> clrearResp = client.clearScroll(clearScrollRequest);
			ClearScrollResponse clearScrollResponse = clrearResp.get();
			boolean succeeded = clearScrollResponse.isSucceeded();
			System.out.println("succeeded" + succeeded);
			long end = System.currentTimeMillis();
			System.out.println("sv size:  " + svList.size() + " ,time " + (end - start));

		}
	}
	
	private void getClxList(Set<String> clxRidSet, List<ClxAssetDa> clxList,HashMap<String, ClxAssetDa> clxMap , Set<String> cageUniquesSpaceId, Set<String> cabUniqueSpaceId, Set<String> cabDpUniqueSpaceId) throws InterruptedException, ExecutionException {
		if(CollectionUtils.isNotEmpty(cageUniquesSpaceId)){
			BoolQueryBuilder clxQuery = QueryBuilders.boolQuery();
			clxQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header10.keyword", cageUniquesSpaceId)));
			clxQuery.must(QueryBuilders.termQuery("header20.keyword", "Cage"));
			getClxList(clxQuery, clxRidSet, clxList, clxMap);
		}
		if(CollectionUtils.isNotEmpty(cabUniqueSpaceId)){
			BoolQueryBuilder clxQuery = QueryBuilders.boolQuery();
			clxQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header12.keyword", cageUniquesSpaceId)));
			clxQuery.must(QueryBuilders.termQuery("header20.keyword", "Cabinet"));
			getClxList(clxQuery, clxRidSet, clxList,clxMap);
		}
		if(CollectionUtils.isNotEmpty(cabDpUniqueSpaceId)){
			BoolQueryBuilder clxQuery = QueryBuilders.boolQuery();
			clxQuery.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header12.keyword", cageUniquesSpaceId)));
			clxQuery.must(QueryBuilders.termQuery("header20.keyword", "Demarcation Point"));
			getClxList(clxQuery, clxRidSet, clxList,clxMap);
		}
	}

	private void getClxList(BoolQueryBuilder clxQuery,Set<String> clxRidSet,List<ClxAssetDa> clxList, HashMap<String, ClxAssetDa> clxMap) throws InterruptedException, ExecutionException{
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_CLX_IDX);
		searchRequest.types(CacheConstant.DART_CLX_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(clxQuery);
		System.out.println("query builder : "+ clxQuery);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
				clxList.add(clx);
				clxRidSet.add(clx.getHeader1());
				clxMap.put(clx.getHeader1(), clx);
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
	    System.out.println("clx size:  " + clxList.size());
	}
	
	private List<SiebelAssetDa> getSblList(BoolQueryBuilder query,Set<String> sblRid , List<SiebelAssetDa> sblList, HashMap<String, SiebelAssetDa> sblMap, Set<String> assetSet, Set<String> cageUniquesSpaceId, Set<String> cabUniqueSpaceId, Set<String> cabDpUniqueSpaceId) throws InterruptedException, ExecutionException{
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(query);
//		System.out.println("query builder : "+ query);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
				sblMap.put(sbl.getHeader1(), sbl);
				sblRid.add(sbl.getHeader1());
				assetSet.add(sbl.getHeader2());
				if("Cage".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader10()){
					cageUniquesSpaceId.add(sbl.getHeader10());
				}else if("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
					cabUniqueSpaceId.add(sbl.getHeader12());
				}else if("Demarcation Point".equalsIgnoreCase(sbl.getHeader10()) && null!=sbl.getHeader12()){
					cabDpUniqueSpaceId.add(sbl.getHeader12());
				}
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
		return sblList;
	}
	
	@Override
	public List<SiebelAssetDa> getSiebelAssetDaDataByProduct(ProductFilter productFilter, String product) throws InterruptedException, ExecutionException{
		List<SiebelAssetDa> sblList = new ArrayList<>();
		BoolQueryBuilder query = applyProductFilterWithoutProduct(productFilter);
		query.must(QueryBuilders.boolQuery().filter(QueryBuilders.matchQuery("header20.keyword", product)));
		query.minimumShouldMatch(1);
		
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);
		searchSourceBuilder.size(1000);
		searchRequest.source(searchSourceBuilder);
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get();
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		System.out.println(" Hits " + searchHits.length );
		long start = System.currentTimeMillis();
		if(searchHits.length>1000){
			while (searchHits != null && searchHits.length > 0) { 
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
				scrollRequest.scroll(scroll);
				ActionFuture<SearchResponse> resp2 = client.searchScroll(scrollRequest);
				SearchResponse searchResponse2 = resp2.get();
				scrollId = searchResponse2.getScrollId();
				searchHits = searchResponse2.getHits().getHits();
				for(SearchHit hitItem :searchHits ){
					SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
					sblList.add(sbl);
				}
			}
		}else {
			System.out.println("search hits less than 1000");
			for(SearchHit hitItem :searchHits ){
				SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("sbl size: " + sblList.size() + " ,time " + (end-start) );
		return sblList;
		
	}
	
	@Override
	public List<SiebelAssetDa> getHierarchyView(ProductFilter productFilter) throws InterruptedException, ExecutionException{
		List<SiebelAssetDa> sblList = new ArrayList<>();
		BoolQueryBuilder query = applyProductFilterWithoutProduct(productFilter);
		query.minimumShouldMatch(1);
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);
		searchSourceBuilder.size(1000);
		searchRequest.source(searchSourceBuilder);
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get();
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		System.out.println(" Hits " + searchHits.length );
		long start = System.currentTimeMillis();
		if(searchHits.length>1000){
			while (searchHits != null && searchHits.length > 0) { 
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
				scrollRequest.scroll(scroll);
				ActionFuture<SearchResponse> resp2 = client.searchScroll(scrollRequest);
				SearchResponse searchResponse2 = resp2.get();
				scrollId = searchResponse2.getScrollId();
				searchHits = searchResponse2.getHits().getHits();
				for(SearchHit hitItem :searchHits ){
					SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
					sblList.add(sbl);
				}
			}
		}else {
			System.out.println("search hits less than 1000");
			for(SearchHit hitItem :searchHits ){
				SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("sbl size: " + sblList.size() + " ,time " + (end-start) );
		return sblList;
	}
	
	public  BoolQueryBuilder applyProductFilterWithoutProduct(ProductFilter productFilter){
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		if(StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters()==null){
			applyGolbalSearchColumnsOnQuery(productFilter, query);
			applyStatusCDActiveClause(query);
			return query;
		}else if(StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters()!=null) {
			applyGolbalSearchColumnsOnQuery(productFilter, query);
			applyFiltersOnSearchQueryWithoutProductFilter(productFilter, query);
			applyStatusCDActiveClause(query);
			return query;
		} else if(StringUtils.isEmpty(productFilter.getKeyword())){
			applyFiltersOnSearchQuery(productFilter, query);
			applyStatusCDActiveClause(query);
			return query;
		}
		return null;
	}
	
	private void applyFiltersOnSearchQueryWithoutProductFilter(ProductFilter productFilter, BoolQueryBuilder query) {
		for (PFilter filter : productFilter.getFilters()) {
			if (!filter.getLable().toLowerCase().contains("error") && !filter.getKey().equalsIgnoreCase("header20")) {
				filter.setValue(filter.getValue());
				if (CollectionUtils.isNotEmpty(filter.getListOfValues())) {
					if (CollectionUtils.isNotEmpty(productFilter.getSearchDropBox().getSearchDrop()) && filter.getKey().equalsIgnoreCase("header16")) {
						List<String> systemNameList = DartUtil.deserializedSystemName(filter.getListOfValues());
						query.should(QueryBuilders.boolQuery()
								.filter(QueryBuilders.termsQuery(filter.getKey() + ".keyword", systemNameList)));
						query.should(QueryBuilders.boolQuery()
								.filter(QueryBuilders.termsQuery("attr253.keyword", systemNameList)));
					} else {
						if (filter.getKey().equalsIgnoreCase("header2") || filter.getKey().equalsIgnoreCase("header3")
								|| filter.getKey().equalsIgnoreCase("header24")) {
							if (filter.getValue().contains("*")) {
								if (CollectionUtils.isNotEmpty(filter.getListOfValues())) {
									for (String val : filter.getListOfValues()) {
										query.should(QueryBuilders.wildcardQuery(filter.getKey() + ".keyword", val));
									}
								}

							} else {
								query.must(QueryBuilders.boolQuery().filter(QueryBuilders
										.termsQuery(filter.getKey() + ".keyword", filter.getListOfValues())));
							}
						} else {
							if(CollectionUtils.isNotEmpty(filter.getIs())){
								query.must(QueryBuilders.boolQuery().filter(
										QueryBuilders.termsQuery(filter.getKey() + ".keyword", filter.getIs())));
							}
							if(CollectionUtils.isNotEmpty(filter.getIsNot())){
								query.mustNot(QueryBuilders.boolQuery().filter(
										QueryBuilders.termsQuery(filter.getKey() + ".keyword", filter.getIsNot())));
							}
						}

					}
				}
			}
		}
	}

	@Override
	public List<SiebelAssetDa> getSblList(List<String> sblRowIdList){
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		List<SiebelAssetDa> sblList = new ArrayList<>();
		query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRowIdList)));
		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(query);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		SearchHit[] searchHits = sr.getHits().getHits();
		for(SearchHit hitItem :searchHits ){
			SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
			sblList.add(sbl);
		}
		return sblList;
	}
	
	@Override
	public List<ClxAssetDa> getClxList(List<String> clxRowIdList){
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		List<ClxAssetDa> clxList = new ArrayList<>();
		query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", clxRowIdList)));
		SearchRequestBuilder searchReqBuilder = getClxSearchRequestBuilder(query);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		SearchHit[] searchHits = sr.getHits().getHits();
		for(SearchHit hitItem :searchHits ){
			ClxAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
			clxList.add(sbl);
		}
		return clxList;
	}
	@Override
	public List<SvAssetDa> getSvList(List<String> svRowIdList){
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		List<SvAssetDa> svList = new ArrayList<>();
		query.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", svRowIdList)));
		SearchRequestBuilder searchReqBuilder = getSvSearchRequestBuilder(query);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		SearchHit[] searchHits = sr.getHits().getHits();
		for(SearchHit hitItem :searchHits ){
			SvAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SvAssetDa.class);
			svList.add(sbl);
		}
		return svList;
	}

	@Override
	public List<SiebelAssetDa> getSblList(List<String> sblRidList, ProductFilter productFilter) {
		List<SiebelAssetDa> sblList = new ArrayList<>();
		
		BoolQueryBuilder queryBuilder= QueryBuilders.boolQuery();
		
		for(PFilter filter : productFilter.getFilters()){
			if(!filter.getLable().toLowerCase().contains("error")){	
				filter.setValue(filter.getValue());
				if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
					queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(filter.getKey()+".keyword", filter.getListOfValues())));
				}
			}
		}
	
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRidList)));
		
		//queryBuilder.minimumShouldMatch(1);
		
		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);
		
		searchReqBuilder.addSort("header1.keyword", SortOrder.ASC);

		/*if(productFilter.getPageNumber()-1 == 0){
			searchReqBuilder.setFrom(0);
		}else {
			searchReqBuilder.setFrom((int)((productFilter.getPageNumber()-1)* 10));
		}*/
		searchReqBuilder.setSize(CacheConstant.PAGE_SIZE);

		SearchResponse sr = searchReqBuilder.execute().actionGet();
		if(sr.getHits()!=null && sr.getHits().getTotalHits()!=null){
//			productFilter.setTotalRows(sr.getHits().getTotalHits().value);
		}
		SearchHit[] searchHits = sr.getHits().getHits();
		for(SearchHit hitItem :searchHits ){
			SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
			sblList.add(sbl);
		}
		
		return sblList;
	}

	@Override
	public ProductFilterResult getAllProductAttributeView(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException {
		ProductFilter productFilterMain = productFilterResult.getProductFilter();
		List<SiebelAssetDa> sblList = new ArrayList<>();
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
		
		Set<String > sblRidList = null;
		for (PFilter filter : productFilterMain.getFilters()) {

			if (filter.getLable().toLowerCase().contains("error")) {
				filter.setValue(filter.getValue());
				if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
					sblRidList = getSblListWithError(filter);
				}
			}
		}
		BoolQueryBuilder query = applyProductFilterWithoutProduct(productFilterMain);
		/**
		 * Apply error filter
		 */
		if(CollectionUtils.isNotEmpty(sblRidList)){
			applyErrorFilter(productFilterMain,query,new ArrayList<String>(sblRidList));
		}
		//query.minimumShouldMatch(1);
		boolean isIgnoreMinimumshouldMatch = false;
		if (productFilterMain.getKeyword() == null || productFilterMain.getKeyword().isEmpty() || productFilterMain.getKeyword().equals("") ) {
			boolean header16 = false;
			boolean header2 = false;
			boolean header3 = false;
			boolean header26 = false;
		
			for(PFilter filter : productFilterMain.getFilters()){
				if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
					if (productFilterMain.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header16")) {
						header16 = true;
					}else if (productFilterMain.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header2") && filter.getValue().contains("*")) {
						header2 = true;
					}else if (productFilterMain.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header3")  && filter.getValue().contains("*")) {
						header3 = true;
					}else if (productFilterMain.getSearchDropBox() != null && filter.getKey().equalsIgnoreCase("header24")  && filter.getValue().contains("*")) {
						header26 = true;
					}
				}
			}
			if(header16 || header2 || header3 || header26 ){
				isIgnoreMinimumshouldMatch = false;
			}else{
				isIgnoreMinimumshouldMatch = true;
			}
		}
		if (!isIgnoreMinimumshouldMatch) {
			query.minimumShouldMatch(1);
		}
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(query);
		searchRequest.source(searchSourceBuilder);
		System.out.println("Initiate dfr elastic query : "+searchSourceBuilder);
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		productFilterResult.setTotalRows(searchResponse.getHits().getTotalHits().value);
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
				SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
				System.out.println(sbl.getHeader1() + "," + sbl.getHeader8() + ", " + sbl.getHeader16() + ", " + sbl.getHeader18() + "," + sbl.getHeader20());
				assetSet.add(sbl.getHeader2());
				sblRowIds.add(sbl.getHeader1());
				if("Cage".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader10()){
					cageUniquesSpaceId.add(sbl.getHeader10());
				}else if("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
					cabUniqueSpaceId.add(sbl.getHeader12());
				}else if("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null!=sbl.getHeader12()){
					cabDpUniqueSpaceId.add(sbl.getHeader12());
				}
			}
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
		
		clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId,cabDpUniqueSpaceId,cageRowIds,cabinetRowIds,cabinetDpRowIds);
		svList = getSvList(assetSet);

		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		productFilterResult.setClxCabninetRowIds(cabinetRowIds);
		productFilterResult.setClxCageRowIds(cageRowIds);
		productFilterResult.setSblRowIds(sblRowIds);
		productFilterResult.setClxCabninetDpRowIds(cabinetDpRowIds);
		long end = System.currentTimeMillis();
	//	System.out.println("sbl size: " + sblList.size() + " ,time " + (end-start) );
		return productFilterResult;
		
	}

	@Override
	public List<SiebelAssetDa> getSblAllByError(List<String> sblRowIdList, ProductFilter productFilter)throws InterruptedException, ExecutionException {
		List<SiebelAssetDa> sblList = new ArrayList<>();
		productFilter.setError(sblRowIdList);
		BoolQueryBuilder queryBuilder = applyProductFilterWithError(productFilter);
//		queryBuilder.minimumShouldMatch(1);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(queryBuilder);
		System.out.println("query builder : "+ queryBuilder);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
		return sblList;
	}

	private BoolQueryBuilder applyProductFilterWithError(ProductFilter productFilter) {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		applyFiltersOnSearchQueryWithError(productFilter, query);
		applyStatusCDActiveClause(query);
		return query;
	}

	@Override
	public List<ClxAssetDa> getClxAllByError(List<String> clxRowIdList, ProductFilter productFilter) throws InterruptedException, ExecutionException {
		List<ClxAssetDa> clxList = new ArrayList<>();
		productFilter.setError(clxRowIdList);
		BoolQueryBuilder queryBuilder = applyProductFilterWithError(productFilter);
//		queryBuilder.minimumShouldMatch(1);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_CLX_IDX);
		searchRequest.types(CacheConstant.DART_CLX_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	ClxAssetDa clx = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
				clxList.add(clx);
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
		return clxList;
	}

	@Override
	public List<SvAssetDa> getSvAllByError(List<String> svRowIdList, ProductFilter productFilter) throws InterruptedException, ExecutionException {
		List<SvAssetDa> svList = new ArrayList<>();
		productFilter.setError(svRowIdList);
		BoolQueryBuilder queryBuilder = applyProductFilterWithError(productFilter);
//		queryBuilder.minimumShouldMatch(1);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SV_IDX);
		searchRequest.types(CacheConstant.DART_SV_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	SvAssetDa clx = gson.fromJson(hitItem.getSourceAsString(),SvAssetDa.class);
				svList.add(clx);
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
		return svList;
	}
	
	private void applyFiltersOnSearchQueryWithError(ProductFilter productFilter, BoolQueryBuilder query) {
		if (CollectionUtils.isNotEmpty(productFilter.getError())) {
			query.must(QueryBuilders.boolQuery()
					.filter(QueryBuilders.termsQuery("header1.keyword", productFilter.getError())));

		}
	}
	
	@Override
	public ProductFilterResult getSblListByError(List<String> sblRowIdList, ProductFilter productFilter) {
		List<SiebelAssetDa> sblList = new ArrayList<>();

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRowIdList)));
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER18_STATUS_CD, CacheConstant.STATUS_CD_ACTIVE)));
		// queryBuilder.minimumShouldMatch(1);

		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);

//		searchReqBuilder.addSort("header1.keyword", SortOrder.ASC);
		/*if (productFilter.getAttributeFlag().equalsIgnoreCase("true")) {
			if (productFilter.getPageNumber() > 0) {
				if (productFilter.getPageNumber() - 1 == 0) {
					searchReqBuilder.setFrom(0);
				} else {
					searchReqBuilder.setFrom((int) ((productFilter.getPageNumber() - 1) * 10));
				}
			}

		} else if (productFilter.getAttributeFlag().equalsIgnoreCase("false")) {
			if (productFilter.getSize() != null && productFilter.getSize().trim() != ""
					&& StringUtils.isNotEmpty(productFilter.getSize())) {
				int size = Integer.parseInt(productFilter.getSize());
				searchReqBuilder.setSize(size);
			}
		}*/
		SearchResponse sr = null;
		sr = searchReqBuilder.execute().actionGet();
		
		ProductFilterResult productFilterResult = new ProductFilterResult();
		if (sr.getHits() != null && sr.getHits().getTotalHits() != null) {
			productFilterResult.setTotalRows(sr.getHits().getTotalHits().value);
		}
		SearchHit[] searchHits = sr.getHits().getHits();
		for (SearchHit hitItem : searchHits) {
			SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
			sblList.add(sbl);
		}
		productFilterResult.setSblList(sblList);
		return productFilterResult;
	}

	@Override
	public List<ClxAssetDa> getClxListByError(List<String> clxRowIdList, ProductFilter productFilter) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		List<ClxAssetDa> clxList = new ArrayList<>();
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", clxRowIdList)));
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER18_STATUS_CD, CacheConstant.STATUS_CD_ACTIVE)));
		SearchRequestBuilder searchReqBuilder = getClxSearchRequestBuilder(queryBuilder);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		SearchHit[] searchHits = sr.getHits().getHits();
		for(SearchHit hitItem :searchHits ){
			ClxAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), ClxAssetDa.class);
			clxList.add(sbl);
		}
		return clxList;
	}

	@Override
	public List<SvAssetDa> getSvListByError(List<String> svRowIdList, ProductFilter productFilter) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		List<SvAssetDa> svList = new ArrayList<>();
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", svRowIdList)));
		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER18_STATUS_CD, CacheConstant.STATUS_CD_ACTIVE)));
		SearchRequestBuilder searchReqBuilder = getSvSearchRequestBuilder(queryBuilder);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		SearchHit[] searchHits = sr.getHits().getHits();
		for(SearchHit hitItem :searchHits ){
			SvAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SvAssetDa.class);
			svList.add(sbl);
		}
		return svList;
	}
	
	@Override
	public ProductSearchResponse getSblListWhenOnlyErrorFilterAppliedElastic(ProductFilter productFilter) throws InterruptedException, ExecutionException {
		String errorCodes[] = null ;
		for(PFilter pf : productFilter.getFilters()){
			if (pf.getKey().toLowerCase().contains("error") && !pf.getValue().trim().equalsIgnoreCase("")){
				errorCodes = pf.getValue().split(",");
				break;
			}
		}
		
		BoolQueryBuilder errorQuery = QueryBuilders.boolQuery();
	    errorQuery.must(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("errorenddate")));
	    errorQuery.must(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("sblrid")));
	    errorQuery.must(QueryBuilders.boolQuery()
	                .filter(QueryBuilders.termsQuery("active.keyword", CacheConstant.Y))
	                .filter(QueryBuilders.termsQuery("errorcode.keyword", errorCodes)));
	
	    
	    SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_ERROR_IDX);
		searchRequest.types(CacheConstant.DART_ERROR_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(errorQuery).fetchSource(new String[]{"sblrid"}, null);;
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		Set<String > sblRidList = new HashSet<>();
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	ErrorElasticData elasticError = gson.fromJson(hitItem.getSourceAsString(), ErrorElasticData.class);
				sblRidList.add(elasticError.getSblrid());
			}
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
//	    System.out.println("succeeded" + succeeded);
	   
		long end = System.currentTimeMillis();
		//System.out.println("sbl size: " + sblList.size() + " ,time " + (end-start) );
		return  getProductTileseWithSblRids(new ArrayList<>(sblRidList),productFilter);
	}
	
	private ProductSearchResponse getProductTileseWithSblRids(List<String> sblRidList, ProductFilter productFilter) {
		long start = System.currentTimeMillis();
		Long cleanCount = 0L;
		Long errorCount = 0L;
		
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

		for (PFilter filter : productFilter.getFilters()) {
			if (!filter.getLable().toLowerCase().contains("error")) {
				filter.setValue(filter.getValue());
				if (CollectionUtils.isNotEmpty(filter.getListOfValues())) {
					queryBuilder.must(QueryBuilders.boolQuery()
							.filter(QueryBuilders.termsQuery(filter.getKey() + ".keyword", filter.getListOfValues())));
				}
			}
		}

		queryBuilder.must(QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("header1.keyword", sblRidList)));
		queryBuilder.must(QueryBuilders.boolQuery().filter(
				QueryBuilders.termsQuery(CacheConstant.KEYWORD_HEADER18_STATUS_CD, CacheConstant.STATUS_CD_ACTIVE)));
		SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(queryBuilder);

		searchReqBuilder
				.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_PRODUCT_TILES)
						.field(CacheConstant.KEYWORD_HEADER20_PRODUCT_NAME)
						.subAggregation(AggregationBuilders.terms(CacheConstant.AGG_DQM_ERROR)
								.field(CacheConstant.KEYWORD_HEADER38_DQM_ERROR_FLAG).size(CacheConstant.AGG_SIZE)))
				.setSize(0);
		System.out.println("search build : "+ searchReqBuilder);
		SearchResponse sr = searchReqBuilder.execute().actionGet();
		Aggregations aggsProductTile = sr.getAggregations();
		StringTerms termProducts = aggsProductTile.get(CacheConstant.AGG_PRODUCT_TILES);
		ProductSearchResponse productSearchResponse = new ProductSearchResponse();
		long totalSblRecords = 0L;
		for (Terms.Bucket productBucket : termProducts.getBuckets()) {
			ProductResp product = new ProductResp();
			product.setName(productBucket.getKeyAsString());
			totalSblRecords = totalSblRecords + productBucket.getDocCount();
			cleanCount = 0L;
			errorCount = 0L;
			System.out.println(productBucket.getKey());
			StringTerms termDqmError = productBucket.getAggregations().get(CacheConstant.AGG_DQM_ERROR);
			for (Terms.Bucket dqmBucket : termDqmError.getBuckets()) {
				if (dqmBucket.getKeyAsString().equalsIgnoreCase("N")) {
					cleanCount = dqmBucket.getDocCount();
				} else if (dqmBucket.getKeyAsString().equalsIgnoreCase("Y")) {
					errorCount = dqmBucket.getDocCount();
				}
				product.setClean(cleanCount);
				product.setError(errorCount);
				product.setTotal(errorCount + cleanCount);
				System.out.println(dqmBucket.getKey() + " , " + dqmBucket.getDocCount());
			}
			productSearchResponse.getProducts().add(product);
		}
		productSearchResponse.setTotalRecords(totalSblRecords + "");

		Gson g = new Gson();
		System.out.println(g.toJson(productSearchResponse));

		long end = System.currentTimeMillis();
		System.out.println("getProductTileQuery time " + (end - start));
		return productSearchResponse;

	}
	
	@Override
	public List<SiebelAssetDa> getProductAttributeViewForErrorCodeGlobalElastic(ProductFilter productFilter) throws InterruptedException, ExecutionException {
		String errorCodes[] = null ;
		for(PFilter pf : productFilter.getFilters()){
			if (pf.getKey().toLowerCase().contains("error") && !pf.getValue().trim().equalsIgnoreCase("")){
				errorCodes = pf.getValue().split(",");
				break;
			}
		}
		
		BoolQueryBuilder errorQuery = QueryBuilders.boolQuery();
	    errorQuery.must(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("errorenddate")));
	    errorQuery.must(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("sblrid")));
	    errorQuery.must(QueryBuilders.boolQuery()
	                .filter(QueryBuilders.termsQuery("active.keyword", CacheConstant.Y))
	                .filter(QueryBuilders.termsQuery("errorcode.keyword", errorCodes)));
	
	    
	    SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_ERROR_IDX);
		searchRequest.types(CacheConstant.DART_ERROR_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(errorQuery).fetchSource(new String[]{"sblrid"}, null);;
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		Set<String > sblRidList = new HashSet<>();
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	ErrorElasticData elasticError = gson.fromJson(hitItem.getSourceAsString(), ErrorElasticData.class);
				sblRidList.add(elasticError.getSblrid());
			}
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
		return getSblListWithFilterAndError(new ArrayList<>(sblRidList),productFilter);
	}

	private List<SiebelAssetDa> getSblListWithFilterAndError(List<String> sblRowIdList, ProductFilter productFilter) throws InterruptedException, ExecutionException {
		List<SiebelAssetDa> sblList = new ArrayList<>();
		productFilter.setError(sblRowIdList);
		BoolQueryBuilder queryBuilder = applyProductFilterWithError(productFilter);
		applyFiltersOnSearchQuery(productFilter, queryBuilder);
//		queryBuilder.minimumShouldMatch(1);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(scroll);
		searchRequest.indices(CacheConstant.DART_SBL_IDX);
		searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(1000);
		searchSourceBuilder.query(queryBuilder);
//		System.out.println("query builder : "+ searchSourceBuilder);
		searchRequest.source(searchSourceBuilder);
		
		ActionFuture<SearchResponse> resp = client.search(searchRequest);
		SearchResponse searchResponse = resp.get(); 
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		
		while (searchHits != null && searchHits.length > 0) {

		    for(SearchHit hitItem :searchHits ){
		    	SiebelAssetDa sbl = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
				sblList.add(sbl);
		    }
		    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); 
		    scrollRequest.scroll(scroll);
		    resp = client.searchScroll(scrollRequest);
		    searchResponse = resp.get(); 
		    scrollId = searchResponse.getScrollId();
		    searchHits = searchResponse.getHits().getHits();
		}
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); 
		clearScrollRequest.addScrollId(scrollId);
		ActionFuture<ClearScrollResponse>  clrearResp = client.clearScroll(clearScrollRequest);
		ClearScrollResponse clearScrollResponse = clrearResp.get();
		boolean succeeded = clearScrollResponse.isSucceeded();
	    System.out.println("succeeded" + succeeded);
		return sblList;
	}
	
/*	@Override
	public List<String> getAllSystemName() {
		try {
			System.out.println("Stat time of getAllSystemName " + new Date());
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			applyStatusCDActiveClause(boolQuery);
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.scroll(scroll);
			searchRequest.indices(CacheConstant.DART_SBL_IDX);
			searchRequest.types(CacheConstant.DART_SBL_DOCUMENT_TYPE);
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(1000);
			searchSourceBuilder.query(boolQuery).fetchSource(new String[] { "header16" }, null);
			
			searchRequest.source(searchSourceBuilder);

			ActionFuture<SearchResponse> resp = client.search(searchRequest);
			SearchResponse searchResponse = null;
			try {
				searchResponse = resp.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String scrollId = searchResponse.getScrollId();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			Set<String> systemNames = new HashSet<>();
			while (searchHits != null && searchHits.length > 0) {

				for (SearchHit hitItem : searchHits) {
					SiebelAssetDa systemName = gson.fromJson(hitItem.getSourceAsString(), SiebelAssetDa.class);
					systemNames.add(systemName.getHeader16());
				}
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
				scrollRequest.scroll(scroll);
				resp = client.searchScroll(scrollRequest);
				searchResponse = resp.get();
				scrollId = searchResponse.getScrollId();
				searchHits = searchResponse.getHits().getHits();
			}

			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			clearScrollRequest.addScrollId(scrollId);
			ActionFuture<ClearScrollResponse> clrearResp = client.clearScroll(clearScrollRequest);
			ClearScrollResponse clearScrollResponse = clrearResp.get();
			boolean succeeded = clearScrollResponse.isSucceeded();
			System.out.println("succeeded" + succeeded);

			System.out.println("end time of getAllSystemName: " + systemNames.size() + " ,time " + new Date());
			return new ArrayList<>(systemNames);
		} catch (Exception e) {
			return null;
		}
		
	}*/
	
	@Override
	public List<String> getAllSystemName() {
		try {
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			applyStatusCDActiveClause(boolQuery);
//			queryBuilder.minimumShouldMatch(1);
			SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(boolQuery);
			searchReqBuilder.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_SYSTEM_NAME).field(CacheConstant.KEYWORD_HEADER16_SYSTEM_NAME).size(CacheConstant.AGG_SIZE))
							.setSize(0).setFetchSource(false);
							
			SearchResponse sr = searchReqBuilder.setFetchSource(false).execute().actionGet();
			Aggregations aggs =   sr.getAggregations();
			
			
//			System.out.println("Check Point " + aggs);
			StringTerms term = aggs.get(CacheConstant.AGG_SYSTEM_NAME);
			Set<String> systemNames = new HashSet<>();
			for(Terms.Bucket bucket :term.getBuckets()){
				systemNames.add(bucket.getKeyAsString());
			}
			
			
			return new ArrayList<>(systemNames);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@Override
	public List<AccountVo> getAllActiveAccount(SearchFormFilter formFilter) {
		try {
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			applyStatusCDActiveClause(boolQuery);
			applySubFiltersForAccount(boolQuery,formFilter);
			SearchRequestBuilder searchReqBuilder = getSblSearchRequestBuilder(boolQuery);
			searchReqBuilder.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_ACCOUNT_NUM).field(CacheConstant.KEYWORD_HEADER6_ACC_NUM).size(CacheConstant.AGG_SIZE)
					.subAggregation(AggregationBuilders.terms(CacheConstant.AGG_ACCOUNT_NAME).field(CacheConstant.KEYWORD_HEADER7_ACC_NAME).size(CacheConstant.AGG_SIZE)))
							.setSize(0).setFetchSource(false);
			SearchResponse sr = searchReqBuilder.setFetchSource(false).execute().actionGet();
			Aggregations aggs =   sr.getAggregations();
			
			StringTerms term = aggs.get(CacheConstant.AGG_ACCOUNT_NUM);
			List<AccountVo> accountVos = new ArrayList<>();
			for(Terms.Bucket bucket :term.getBuckets()){
				AccountVo accountVo = new AccountVo();
				accountVo.setAccountNum(bucket.getKeyAsString());
				Aggregations subAggs = bucket.getAggregations();
				StringTerms subTerm = subAggs.get(CacheConstant.AGG_ACCOUNT_NAME);
				for(Terms.Bucket subBucket :subTerm.getBuckets()){
					accountVo.setAccountName(subBucket.getKeyAsString());
				}
				accountVos.add(accountVo);
			}
			return accountVos;
		} catch (Exception e) {
			return null;
		}
		
	}

	private void applySubFiltersForAccount(BoolQueryBuilder boolQuery, SearchFormFilter formFilter) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(formFilter.getRegion())&& !StringUtils.isBlank(formFilter.getRegion())){
			boolQuery.must(QueryBuilders.boolQuery()
		                .filter(QueryBuilders.termsQuery(CacheConstant.HEADER51_REGION+CacheConstant.DOT_KEYWORD, formFilter.getRegion())));
		}
		if(!StringUtils.isEmpty(formFilter.getCountry())&& !StringUtils.isBlank(formFilter.getCountry())){
			boolQuery.must(QueryBuilders.boolQuery()
		                .filter(QueryBuilders.termsQuery(CacheConstant.HEADER56_COUNTRY+CacheConstant.DOT_KEYWORD, Arrays.asList(formFilter.getCountry().split(",")))));
		}
		if(!StringUtils.isEmpty(formFilter.getIbx())&& !StringUtils.isBlank(formFilter.getIbx())){
			boolQuery.must(QueryBuilders.boolQuery()
		                .filter(QueryBuilders.termsQuery(CacheConstant.HEADER8_IBX+CacheConstant.DOT_KEYWORD, Arrays.asList(formFilter.getIbx().split(",")))));
		}
	}
	
	@Override
	public List<ErrorCodeVO> getAllActiveErrorsElastic() {
		try {
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			boolQuery.must(QueryBuilders.boolQuery()
	                .filter(QueryBuilders.termsQuery(CacheConstant.KEYWORD_ACTIVE, CacheConstant.Y)));
			SearchRequestBuilder searchReqBuilder = getErrorSearchRequestBuilder(boolQuery);
			searchReqBuilder.addAggregation(AggregationBuilders.terms(CacheConstant.AGG_ERROR_CODE).field(CacheConstant.KEYWORD_ERROR_CODE).size(CacheConstant.AGG_SIZE)
					.subAggregation(AggregationBuilders.terms(CacheConstant.AGG_ERROR_NAME).field(CacheConstant.KEYWORD_ERROR_NAME).size(CacheConstant.AGG_SIZE)))
							.setSize(0).setFetchSource(false);
			SearchResponse sr = searchReqBuilder.setFetchSource(false).execute().actionGet();
			Aggregations aggs =   sr.getAggregations();
			
			StringTerms term = aggs.get(CacheConstant.AGG_ERROR_CODE);
			List<ErrorCodeVO> errorCodeVOs = new ArrayList<>();
			for(Terms.Bucket bucket :term.getBuckets()){
				ErrorCodeVO accountVo = new ErrorCodeVO();
				accountVo.setErrorCode(bucket.getKeyAsString());
				Aggregations subAggs = bucket.getAggregations();
				StringTerms subTerm = subAggs.get(CacheConstant.AGG_ERROR_NAME);
				for(Terms.Bucket subBucket :subTerm.getBuckets()){
					accountVo.setErrorName(subBucket.getKeyAsString());
				}
				errorCodeVOs.add(accountVo);
			}
			return errorCodeVOs;
		} catch (Exception e) {
			return null;
		}
		

	}
	
}


