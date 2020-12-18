package com.equinix.appops.dart.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.constant.ProNativeQueries;
import com.equinix.appops.dart.portal.dao.AppOpsDartDaDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartElasticDao;
import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorTbl;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.CountryAndIbxVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.pro.AccountMapper;
import com.equinix.appops.dart.portal.mapper.pro.CountryAndIbxMapper;
import com.equinix.appops.dart.portal.mapper.pro.ErrorCodeMapper;
import com.equinix.appops.dart.portal.mapper.pro.SnapshotErrorDataMapper;
import com.equinix.appops.dart.portal.model.errorsection.ErrorData;
import com.equinix.appops.dart.portal.model.errorsection.SnapshotErrorData;
import com.equinix.appops.dart.portal.model.search.filter.Filter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.util.DartUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

/**
 * 
 * @author Ankur
 *
 */
@Repository
@Transactional
public class AppOpsDartDaDaoImpl implements AppOpsDartDaDao {
	Logger logger = LoggerFactory.getLogger(AppOpsDartDaDaoImpl.class); 
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	AppOpsInitiateDFRDao initDfrDao; 
	
	/**
	 * Elastic dao autowired
	 */
  	@Autowired
	AppOpsDartElasticDao elasticDao;
  	
  	 @Autowired
     JdbcTemplate jdbc;
  	 
  	@Autowired
	NamedParameterJdbcTemplate namedJdbc;
	
	List<String>globalFilters= Arrays.asList( new String[]{"header8","header6","header16", "header2", "header7","attr253"});
	
	private void getCriteriaForDa(ProductFilter productFilter,Criteria criteria,boolean ignoreProductFilter){
		if(StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters()==null){
			logger.info("Global Search Columns " + globalFilters.toString());
			getCriteria(productFilter.getKeyword(), criteria, globalFilters); 
			getStatusCodeCriteria(criteria);
		}else if(StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters()!=null){
			logger.info("Filter + Global Search Columns " + globalFilters.toString());
			getCriteria(productFilter.getKeyword(), criteria, globalFilters);
			getStatusCodeCriteria(criteria);
			getCriteria(productFilter, criteria, ignoreProductFilter);
		}else if(StringUtils.isEmpty(productFilter.getKeyword())){
			logger.info("Filter Search Columns " + productFilter.toString());
			getCriteria(productFilter, criteria, ignoreProductFilter);
			getStatusCodeCriteria(criteria);
		}
	}
	
	@Override
	@Cacheable(value="daDartFilterProductWidget",key ="#productFilter.cacheKey.concat(#product)")
	public List<SiebelAssetDa> getSiebelAssetDaDataByProduct(ProductFilter productFilter, String product) {
		Session session =   sessionFactory.getCurrentSession();
		List<SiebelAssetDa> sblAssetDaList  = new ArrayList<>();
		Criteria criteria = session.createCriteria(SiebelAssetDa.class);
		if(StringUtils.isNotEmpty(productFilter.getKeyword())){
			getCriteria(productFilter.getKeyword(), criteria, globalFilters); 
		}
		getCriteria(productFilter, criteria,product);
		getStatusCodeCriteria(criteria);
		getSelectedHeadersList(criteria);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
		//	SiebelAssetDa siebelAssetDa= (SiebelAssetDa)scrollableResults.get()[0];
			sblAssetDaList.add(siebelAssetDa);
			/*session.evict(svAssetDa);*/
		}
		
		/* new sbl list when error codes are in filter */
		List<SiebelAssetDa> sblList  = new ArrayList<SiebelAssetDa>();		
		
		boolean isErrorCodeInFilter = false;
		List<PFilter> filters =productFilter.getFilters();
		List<String> errorCodes = new ArrayList<String>();
		PFilter filtr = null ;
		for(int fcount= 0 ; fcount< filters.size() ; fcount++){
			filtr = filters.get(fcount);
			if(filters.get(fcount).getLable().toLowerCase().contains("error")){
				errorCodes = filtr.getListOfValues();
				if(errorCodes.size()>0){
					isErrorCodeInFilter = true;	
					break;
				}
			}
		}
		
		if(isErrorCodeInFilter){
			sblList = getSiebelErrorListData(sblAssetDaList, errorCodes );
		}else{
			sblList = sblAssetDaList;
		}
		
		return sblList;
	}
	
	@Override
	@CacheEvict(value="daDartFilterProductWidget", allEntries = true)
	public String resetDartFilterProductWidget() {
	    return " DartFilterProductWidget refreshed ";
	 }
	
	private boolean isOnlyErrorFilterAppliedWithoutKeyWord(ProductFilter productFilter){
		boolean isNonErrorFilterEmpty = true;
		boolean isErrorFilterApplied = false; 
		for(PFilter pf : productFilter.getFilters()){
			if(!pf.getValue().trim().equalsIgnoreCase("")&& !pf.getKey().toLowerCase().contains("error")){
				isNonErrorFilterEmpty = false;
			}else if (pf.getKey().toLowerCase().contains("error") && !pf.getValue().trim().equalsIgnoreCase("")){
				isErrorFilterApplied = true;
			}
		}
		if(isNonErrorFilterEmpty && isErrorFilterApplied ){
			return true;
		}else {
			return false;
		}
			
	}
	private List<SiebelAssetDa> getSblList(List<String> sblRowIdList){
		List<SiebelAssetDa> sblList = new ArrayList<>();
		if(sblRowIdList.size()>999){
			Lists.partition(Arrays.asList(sblRowIdList.toArray()), 999).stream().forEach(list->{
				if(CollectionUtils.isNotEmpty(sblRowIdList)){
					Session session = sessionFactory.getCurrentSession();
					Criteria criteria = session.createCriteria(SiebelAssetDa.class).setReadOnly(true);
					getStatusCodeCriteria(criteria);
					Criterion assetNumIn = Restrictions.in("header1", list.toArray());
					criteria.add(assetNumIn);
					ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						SiebelAssetDa sblAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
						sblList.add(sblAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				}
			});
		}else {
			if(CollectionUtils.isNotEmpty(sblRowIdList)){
				Session session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(SiebelAssetDa.class).setReadOnly(true);
				getStatusCodeCriteria(criteria);
				Criterion assetNumIn = Restrictions.in("header1", sblRowIdList.toArray());
				criteria.add(assetNumIn);
				ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					SiebelAssetDa sblAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
					sblList.add(sblAssetDa);
				   /*session.evict(siebelAssetDa);*/
				}
			}
		}
		return sblList;
	}
		
	@Override
	@Cacheable(value="filterList",key="#productFilter.cacheKey")
	public SearchFilters globalSearch(ProductFilter productFilter){
		 Session session =   sessionFactory.getCurrentSession();
		 List<SiebelAssetDa> sblList = new ArrayList<>();
		/*if(isOnlyErrorFilterAppliedWithoutKeyWord(productFilter)){
			sblList = getSblListWhenOnlyErrorFilterApplied(productFilter, sblList);
		}else{*/
		Criteria sblCriteria = session.createCriteria(SiebelAssetDa.class);
		getCriteriaForDa(productFilter, sblCriteria,false);
		getSelectedHeadersList(sblCriteria);
		ScrollableResults scrollableResults = sblCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while(scrollableResults.next()){
		//	SiebelAssetDa siebelAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
			SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
			sblList.add(siebelAssetDa);
		}
		//}
		
		
		 logger.info("Sbl data fetched : " + sblList.size());
		 HashSet<String> ibxSet = new HashSet<>();
		 HashSet<String> accNumberSet = new HashSet<>();
		 HashSet<String> sysNameSet = new HashSet<>();
		 //
		sblList.stream().forEach(item ->{
			ibxSet.add(item.getHeader8());
		//	accNameSet.add(DartUtil.sanitizeOutput(item.getHeader7()));
			accNumberSet.add(item.getHeader6());
			sysNameSet.add(item.getHeader16());
		});
		SearchFilters filters = new SearchFilters();
		Filter filter1=new Filter();
		filter1.setKey("header8");
		filter1.setLable("IBX");
		filter1.setValues(new ArrayList<>(ibxSet));
		filters.getFilters().add(filter1);
		
		Filter filter2=new Filter();
		filter2.setKey("header6");
		filter2.setLable("ACCOUNT NUM");
		filter2.setValues(new ArrayList<>(accNumberSet));
		filters.getFilters().add(filter2);
		
		Filter filter3=new Filter();
		filter3.setKey("header16");
		filter3.setLable("SYSTEM NAME");
		filter3.setValues(new ArrayList<>(sysNameSet));
		filters.getFilters().add(filter3);
		
		List<String> errorCodeList= getFilterListFromErrorMaster();
		Filter filter4=new Filter();
		filter4.setKey("Error Code");
		filter4.setLable("Error Code");
		filter4.setValues(errorCodeList);
		filters.getFilters().add(filter4);
		 logger.info("Global search result Prepared : " + filters);
		return filters;
		
	}
	
	@Override
	@Cacheable(value="sblListWhenOnlyErrorFilterApplied",key="#productFilter.cacheKey")
	public List<SiebelAssetDa> getSblListWhenOnlyErrorFilterApplied(ProductFilter productFilter, List<SiebelAssetDa> sblList) {
		List<String> allSblRids = new ArrayList<>();
		String errorCodes[] = null ;
		for(PFilter pf : productFilter.getFilters()){
			if (pf.getKey().toLowerCase().contains("error") && !pf.getValue().trim().equalsIgnoreCase("")){
				errorCodes = pf.getValue().split(",");
				break;
			}
		}
		if(errorCodes!=null && errorCodes.length>0){
			for(String errorCode : errorCodes){
				HashSet<String > sblRowId = initDfrDao.getErrorCodeSblRowIdMap().get(errorCode);
				if(CollectionUtils.isNotEmpty(sblRowId))
					allSblRids.addAll(new ArrayList<>(sblRowId));
			}
			sblList = getSblList(allSblRids);
		}
		return sblList;
	}
	
	@Override
	@CacheEvict(value = "filterList", allEntries = true)
	public String resetFilterList() {
	    return " FilterList refreshed ";
	 }
	
	@Override
	@Cacheable(value="globalsearch",key ="#root.methodName.concat(#productFilter.cacheKey)")
	public List<SiebelAssetDa> getGlobalSearch(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SiebelAssetDa> sblListTemp = new ArrayList<>();
		long start = System.currentTimeMillis();
		logger.info("start time  "+start);
		Criteria sblCriteria = session.createCriteria(SiebelAssetDa.class);
		getCriteriaForDa(productFilter, sblCriteria,false);
		
		getSelectedHeadersList(sblCriteria);
		
		ScrollableResults scrollableResults = sblCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while(scrollableResults.next()){
			//	SiebelAssetDa siebelAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
				SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
				sblListTemp.add(siebelAssetDa);
		}
		
		
		
		/*List<PFilter> filters =productFilter.getFilters();
		List<String> errorCodes = new ArrayList<String>();
		PFilter filtr = null ;
		if(filters!=null){
			 new sbl list when error codes are in filter 
			List<SiebelAssetDa> sblList  = new ArrayList<SiebelAssetDa>();		

			boolean isErrorCodeInFilter = false;
			for(int fcount= 0 ; fcount< filters.size() ; fcount++){
				filtr = filters.get(fcount);
				if(filters.get(fcount).getLable().toLowerCase().contains("error")){
					errorCodes = filtr.getListOfValues();
					if(errorCodes.size()>0){
						isErrorCodeInFilter = true;	
						break;
					}
				}
			}

			if(isErrorCodeInFilter){
				sblList = getSiebelErrorListData(sblListTemp, errorCodes );
			}else{
				sblList = sblListTemp;
			}
		}*/
		return sblListTemp;
	}
	
	
	@Override
	@CacheEvict(value="globalsearch", allEntries = true)
	public String resetGlobalSearch() {
	    return " GlobalSearch refreshed ";
	 }
	
	
	@Override
	@Cacheable(value="getProductFilters",key ="#root.methodName.concat(#productFilter.cacheKey)")
	public List<SiebelAssetDa> getProductFilters(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		
		long start = System.currentTimeMillis();
		logger.info("start time  "+start);
		/* new sbl list when error codes are in filter */
		List<SiebelAssetDa> sblList  = new ArrayList<SiebelAssetDa>();	
		/*if(isOnlyErrorFilterAppliedWithoutKeyWord(productFilter)){
			sblList = getSblListWhenOnlyErrorFilterApplied(productFilter, sblList);
		}else{*/
			List<SiebelAssetDa> sblListTemp = new ArrayList<>();
		Criteria sblCriteria = session.createCriteria(SiebelAssetDa.class);
		getCriteriaForDa(productFilter, sblCriteria,false);
		
		getSelectedHeadersList(sblCriteria);
		
		ScrollableResults scrollableResults = sblCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while(scrollableResults.next()){
			//	SiebelAssetDa siebelAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
				SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
				sblListTemp.add(siebelAssetDa);
		}
		
		
		
		
		boolean isErrorCodeInFilter = false;
		List<PFilter> filters =productFilter.getFilters();
		List<String> errorCodes = new ArrayList<String>();
		PFilter filtr = null ;
		for(int fcount= 0 ; fcount< filters.size() ; fcount++){
			filtr = filters.get(fcount);
			if(filters.get(fcount).getLable().toLowerCase().contains("error")){
				errorCodes = filtr.getListOfValues();
				if(errorCodes.size()>0){
					isErrorCodeInFilter = true;	
					break;
				}
			}
		}
		
		if(isErrorCodeInFilter){
			sblList = getSiebelErrorListData(sblListTemp, errorCodes );
		}else{
			sblList = sblListTemp;
		}
		//}
		
		
		return sblList;
	}
	
	@Override
	@CacheEvict(value="getProductFilters", allEntries = true)
	public String resetProductFilters() {
	    return " ProductFilters refreshed ";
	 }
	
	
	private void getCriteria(String keyWord, Criteria criteria, List<String> filters) {
		
		Disjunction disjunction = Restrictions.disjunction();
		for(String filterName : filters){
			  	 if(!filterName.toLowerCase().contains("error")){
				 Criterion criterion = Restrictions.ilike(filterName, keyWord, MatchMode.ANYWHERE);
				 disjunction.add(criterion);
			  	 }
		}
		criteria.add(disjunction);
	}
	
	private void getStatusCodeCriteria(Criteria criteria) {
		Criterion criterion = Restrictions.ne("header18","Inactive").ignoreCase();
		criteria.add(criterion);
	}

	
	
	@Override
	public List<AttributeConfig> getHeaders() {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		List<AttributeConfig> headerList =criteria.add(Restrictions.like("headerPosition", "%HEADER%")).add(Restrictions.and(Restrictions.eq("product", "HEADER"))).addOrder(Order.asc("headerPosition")).list();
		return headerList;
	}

	@Override
	public List<AttributeConfig> getAttributesByProduct(String product) {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		List<AttributeConfig> attributeList =criteria.add(Restrictions.like("headerPosition", "%ATTR%")).add(Restrictions.and(Restrictions.eq("product", product))).addOrder(Order.asc("sequence")).list();
		return attributeList;
	}

	@Override
	public List<String> getProducts() {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class);
		List<String> productsList = criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("product")))).add(Restrictions.ne("product", "HEADER")).list();
		return productsList;
	}

	/*SortedMap<Integer, AttributeConfig> configHeaderMap; 
	List<String> productList;
	HashMap<String, SortedMap<Integer, AttributeConfig>> configProductAttrMap ;*/
	
	
/*	@PostConstruct*/
	@Override
	public void initilize(){/*
		List<AttributeConfig> attributeList = null;
		configHeaderMap = new TreeMap<>(); 
		configProductAttrMap = new HashMap<>();
		configProdcutCommonAttrMap = new HashMap<>();
		Map<String,Integer> productAttrLengthMap= new HashMap<>();
		attributeList =	getHeaders();
		if(CollectionUtils.isNotEmpty(attributeList)){
			attributeList.stream().forEach(attributeConfig -> {
				configHeaderMap.put(DartUtil.getIntegerValue(attributeConfig.getHeaderPosition()), attributeConfig);
			});
		}
		
		productList = getProducts();
		if(CollectionUtils.isNotEmpty(productList)){
			productList.stream().forEach(product->{
				List<AttributeConfig> attributeListByProduct = getAttributesByProduct(product);
				if(CollectionUtils.isNotEmpty(attributeListByProduct)){
					if(!configProductAttrMap.containsKey(product)){
						SortedMap<Integer, AttributeConfig> attributeMap= new TreeMap<>();
						IntStream.range(0, attributeListByProduct.size()).forEach(index -> {
							AttributeConfig config = attributeListByProduct.get(index);
							attributeMap.put(index+1 , config);	
						});
						configProductAttrMap.put(product, attributeMap);
					}
				}
			});
		}
		
		productList.stream().forEach(product->{
			List<AttributeConfig> attributeListByProduct = getCommonAttributesByProduct(product);
			if(CollectionUtils.isNotEmpty(attributeListByProduct)){
				if(!configProdcutCommonAttrMap.containsKey(product)){
					SortedMap<Integer, AttributeConfig> attributeMap= new TreeMap<>();
					IntStream.range(0, attributeListByProduct.size()).forEach(index -> {
						AttributeConfig config = attributeListByProduct.get(index);
						attributeMap.put(index+1 , config);	
					});
					configProdcutCommonAttrMap.put(product, attributeMap);
					productAttrLengthMap.put(product,attributeMap.size());
				}
			}
		});
		this.maxCommonAttrForProduct = productAttrLengthMap.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		System.out.println(this.maxCommonAttrForProduct);
	*/}


	private List<AttributeConfig> getCommonAttributesByProduct(String product) {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		List<AttributeConfig> attributeList =criteria.add(Restrictions.like("headerPosition", "%ATTR%"))
				.add(Restrictions.and(Restrictions.eq("product", product)))
				.add(Restrictions.and(Restrictions.isNotNull("groupSequence")))
				.addOrder(Order.asc("groupSequence")).list();
		return attributeList;
	}
	
	@Override
	@Cacheable(value="getConfigHeaderMap",key="#root.methodName")
	public SortedMap<Integer, AttributeConfig> getConfigHeaderMap() {
		List<AttributeConfig> attributeList =	getHeaders();
		SortedMap<Integer, AttributeConfig> configHeaderMap = new TreeMap<>();
		if(CollectionUtils.isNotEmpty(attributeList)){
			attributeList.stream().forEach(attributeConfig -> {
				configHeaderMap.put(DartUtil.getIntegerValue(attributeConfig.getHeaderPosition()), attributeConfig);
			});
		}
		return configHeaderMap;
	}
	
	@Override
	@CacheEvict(value = "getConfigHeaderMap", allEntries = true)
	public String resetConfigHeaderMap() {
	    return " getConfigHeaderMap refreshed ";
	 }

	@Override
	public List<String> getProductList() {
		return getProducts();
	}

	@Override
	@Cacheable(value="getConfigProductAttrMap",key="#root.methodName")
	public HashMap<String, SortedMap<Integer, AttributeConfig>> getConfigProductAttrMap() {
		HashMap<String, SortedMap<Integer, AttributeConfig>>	configProductAttrMap  = new HashMap<>();
		List<String> productList = getProductList();
		if(CollectionUtils.isNotEmpty(productList)){
			productList.stream().forEach(product->{
				List<AttributeConfig> attributeListByProduct = getAttributesByProduct(product);
				if(CollectionUtils.isNotEmpty(attributeListByProduct)){
					if(!configProductAttrMap.containsKey(product)){
						SortedMap<Integer, AttributeConfig> attributeMap= new TreeMap<>();
						IntStream.range(0, attributeListByProduct.size()).forEach(index -> {
							AttributeConfig config = attributeListByProduct.get(index);
							if(config!=null &&null!=config.getSequence())
								attributeMap.put(config.getSequence().intValue() , config);	
						});
						configProductAttrMap.put(product, attributeMap);
					}
				}
			});
		}
		
		return configProductAttrMap;
	}
	@Override
	@CacheEvict(value="getConfigProductAttrMap" , allEntries = true)
	public String resetConfigProductAttrMap(){
		return "getConfigProductAttrMap refreshed";
	}
	
	@Override
	public void refresh() {
		initilize();
	}

	@Override
	public SiebelAssetDa getSibelAssetDaByRowId(String rowId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SiebelAssetDa.class);
		SiebelAssetDa sblDa =(SiebelAssetDa)criteria.add(Restrictions.eq("header1", rowId)).uniqueResult();
		return sblDa;
	}

	@Override
	public List<Object> executeSQL(String sql) {
		Session session =   sessionFactory.getCurrentSession();		
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);		
		return query.list();
	}
	
	@Override
	public List<Object> executeParamListSQL(String sql, Set<String> list1) {
		Session session =   sessionFactory.getCurrentSession();		
		//SQLQuery query = session.createSQLQuery(sql);
		SQLQuery query = session.createSQLQuery(sql);
		List<Object> fullList = new ArrayList<Object>();
		if(CollectionUtils.isNotEmpty(list1)){
			if(list1.size()>999){
				Lists.partition(Arrays.asList(list1.toArray()), 999).stream().forEach(sublist->{
					query.setParameterList("list1", sublist.toArray());
					query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
					List<Object> reslutlist = query.list();
					if(null != reslutlist && !reslutlist.isEmpty())
						fullList.add(reslutlist);
				});
			}else{
				query.setParameterList("list1", list1.toArray());
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List<Object> reslutlist = query.list();
				if(null != reslutlist && !reslutlist.isEmpty())
					fullList.add(reslutlist);
			}
		}
		return fullList;
	}
	
	@Override
	public List<Object[]> executeSQLForArray(String sql) {
		Session session =   sessionFactory.getCurrentSession();		
		List<Object[]> resultList= session.createSQLQuery(sql).list();
		return resultList;
	}
	
	
	
	
	
	/**
	 * @param searchFilters
	 * @param criteria
	 */
	private void getCriteria(ProductFilter productFilter, Criteria criteria, boolean isIgnoreProductFilter) {
		List<PFilter> filters = productFilter.getFilters();
		PFilter filter = null ;
		Conjunction conjunction = Restrictions.conjunction();
		for(int fcount= 0 ; fcount< filters.size() ; fcount++){
			filter = filters.get(fcount);
			String headerOrAttr= filter.getKey();
			if(!filter.getLable().toLowerCase().contains("error")){
				if(isIgnoreProductFilter){
					if(!headerOrAttr.equalsIgnoreCase("header20") && CollectionUtils.isNotEmpty(filter.getListOfValues())) {
						Criterion criterion = Restrictions.in(headerOrAttr, filter.getListOfValues().toArray());
						conjunction.add(criterion);
					}
				}else{
					if( CollectionUtils.isNotEmpty(filter.getListOfValues())) {
						if(headerOrAttr.equalsIgnoreCase("header16")){
							List<String> result = DartUtil.senitizeOutputList(filter.getListOfValues());
							/*for(String str: filter.getListOfValues()){
								if(str.contains("~")){
									
								}
							}*/
							Criterion criterion = Restrictions.in(headerOrAttr, filter.getListOfValues());
							conjunction.add(criterion);
							
						}else{
							Criterion criterion = Restrictions.in(headerOrAttr, filter.getListOfValues().toArray());
							conjunction.add(criterion);
					   }
					}
				}
			}else{
				/*if(!isIgnoreProductFilter){
					if( CollectionUtils.isNotEmpty(filter.getListOfValues())) {
						Criteria errorCriteria = 
						criteria.createAlias("srcCxiErrorTbls", "srcCxiErrorTbls",JoinType.INNER_JOIN);
						criteria.createAlias("srcCxiErrorTbls.cxiErrorMasterTbl", "cxiErrorMasterTbl",JoinType.INNER_JOIN);
					//	Criterion criterion =  Restrictions.in("srcCxiErrorTbls.errorCode", filter.getListOfValues().toArray());
						conjunction.add(Restrictions.in("cxiErrorMasterTbl.errorCode", filter.getListOfValues().toArray()));
						conjunction.add(Restrictions.isNull("srcCxiErrorTbls.errorEndDate"));
						conjunction.add(Restrictions.eq("cxiErrorMasterTbl.active", 'Y').ignoreCase());
					}
				}*/
		}
		
		}
		
		
		criteria.add(conjunction);
	}

	@Override
	@Cacheable(value="getErrorSection",key ="#root.methodName.concat(#productFilter.cacheKey)")
	public List<ErrorData> getErrorSection(ProductFilter productFilter){
		List<ErrorData> errorList = new ArrayList<>();
		Gson gson = new Gson();
		logger.info("Error Filter  : " + gson.toJson(productFilter, ProductFilter.class));
		String sblQuery = null;
		if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			sblQuery = getErrorQueryForErrorCodeGlobalKeyword(productFilter, filterTemplateSbl, errorTemplate, SBL_ERR_Q);
		} else {
			sblQuery = getErrorQuery(productFilter, filterTemplateSbl, globalTemplateSbl, errorTemplate, SBL_ERR_Q);
		}
		logger.info("DAO getErrorSection :: SBL Error Query : " + sblQuery);
		long start = System.currentTimeMillis();
		List<Object> sblList = executeSQL(sblQuery);
		long end = System.currentTimeMillis() ;
		logger.info("DAO getErrorSection :: SBL Execute SQL DB Time --> " + (end-start) + "; Error Count #" + (sblList != null ? sblList.size() : 0));
		List<Object> clxList = new ArrayList<>();
		List<Object> svList = new ArrayList<>();
		List<Object> clxSvList = new ArrayList<>();
		
		start = System.currentTimeMillis();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		for(Object sbl :sblList ){
			Map<String,String> value = (Map<String,String>)sbl;
			
			errorList.add(new ErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
					value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING")));
			
			assetSet.add(value.get("ASSET_NUM"));
			if("Cage".equalsIgnoreCase(value.get("NAME")) && null!=value.get("CAGE_UNIQUE_SPACE_ID")){
				cageUniquesSpaceId.add(value.get("CAGE_UNIQUE_SPACE_ID"));
			}else if("Cabinet".equalsIgnoreCase(value.get("NAME")) && null!=value.get("CAB_UNIQUE_SPACE_ID")){
				cabUniqueSpaceId.add(value.get("CAB_UNIQUE_SPACE_ID"));
			}else if("Demarcation Point".equalsIgnoreCase(value.get("NAME")) && null!=value.get("CAB_UNIQUE_SPACE_ID")){
				cabDpUniqueSpaceId.add(value.get("CAB_UNIQUE_SPACE_ID"));
			}
		}
		end = System.currentTimeMillis() ;
		logger.info("DA DAO ErrorSection :: SBL Iterate Map Time --> " + (end-start));
		start = System.currentTimeMillis(); 
		 if(CollectionUtils.isNotEmpty(cageUniquesSpaceId)){
			 start = System.currentTimeMillis();
			 clxList =  (List<Object>)executeParamListSQL(CLX_CAGE_ERR_Q, cageUniquesSpaceId);
			 end = System.currentTimeMillis();
			 logger.info("Clx cage executeSQL db time " + (end-start));
			 logger.info("CLX Cage Error Query : " + CLX_CAGE_ERR_Q);
		 }
		 
		 if(CollectionUtils.isNotEmpty(cabUniqueSpaceId)){
			 start = System.currentTimeMillis();
			 clxList =  (List<Object>)executeParamListSQL(CLX_CAB_ERR_Q, cabUniqueSpaceId);
			 end = System.currentTimeMillis();
			 logger.info("Clx cabinet executeSQL db time " + (end-start));
			 logger.info("CLX Cab Error Query : " + CLX_CAB_ERR_Q);
		 }
		 
		 if(CollectionUtils.isNotEmpty(cabDpUniqueSpaceId)){
			 start = System.currentTimeMillis();
			 clxList =  (List<Object>)executeParamListSQL(CLX_CAB_DP_ERR_Q, cabDpUniqueSpaceId);
			 end = System.currentTimeMillis();
			 logger.info("Clx cabinet dp executeSQL db time " + (end-start));
			 logger.info("CLX Cab dp Error Query : " + CLX_CAB_DP_ERR_Q);
		 }
		 
		 if(CollectionUtils.isNotEmpty(assetSet)){
			 start = System.currentTimeMillis();
			 svList = (List<Object>)executeParamListSQL(SV_ERR_Q,assetSet);
			 end = System.currentTimeMillis();
			 logger.info("Sv  executeSQL db time " + (end-start));
			 logger.info("SV Error Query : " + SV_ERR_Q);
		 }
		if(CollectionUtils.isNotEmpty(sblList) && CollectionUtils.isNotEmpty(clxList)){
			clxSvList.addAll(clxList);
			logger.info("CLX Error Count -->"+(clxList != null ? clxList.size() : 0)); 
		}
		if(CollectionUtils.isNotEmpty(sblList) && CollectionUtils.isNotEmpty(svList)){
			clxSvList.addAll(svList);
			logger.info("CLX Error Count -->"+(svList != null ? svList.size() : 0));  
		}
		if(CollectionUtils.isNotEmpty(clxSvList)){
			for(Object objectList : clxSvList){
				List<Object> object = (List<Object>)objectList;
				for(Object objectMap :object){
					Map<String,String> value = (Map<String,String>)objectMap;
					errorList.add(new ErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
							value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING")));
				
				}
				
			}
		}
		
		end = System.currentTimeMillis() ;
		logger.info("DAO getErrorSection :: CLX and SV executeSQL db time " + (end-start));
		
		return errorList;
	}
	
	@Override
	@CacheEvict(value="getErrorSection", allEntries = true)
	public String resetErrorSection() {
	    return " ErrorSection refreshed ";
	 }
	
	private String castToString(Object obj){
		return (String)obj;
	}
	
	
	
	
	public List<SiebelAssetDa> getSiebleAssetDaDataIgnoreProductFilter(ProductFilter productFilter) {/*
		Session session =   sessionFactory.getCurrentSession();
		List<SiebelAssetDa> siebelAssetDaList=new ArrayList<>();
		Criteria criteria=session.createCriteria(SiebelAssetDa.class);
		getCriteriaForDa(productFilter, criteria,true);
		getSelectedHeadersList(criteria);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		  while (scrollableResults.next()) {
		//   SiebelAssetDa siebelAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
		   SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
		   siebelAssetDaList.add(siebelAssetDa);
		   session.evict(siebelAssetDa);
		  }*/
		return null;
	}
	
	
	@Override
	@Cacheable(value="getHierarchyView",key ="#root.methodName.concat(#productFilter.cacheKey)")
	public List<SiebelAssetDa> getHierarchyView(ProductFilter productFilter){
		Session session =   sessionFactory.getCurrentSession();
		List<SiebelAssetDa> siebelAssetDaList=new ArrayList<>();
		Criteria criteria=session.createCriteria(SiebelAssetDa.class);
		getCriteriaForDa(productFilter, criteria,true);
		getSelectedHeadersList(criteria);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		  while (scrollableResults.next()) {
		//   SiebelAssetDa siebelAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
		   SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
		   siebelAssetDaList.add(siebelAssetDa);
		   session.evict(siebelAssetDa);
		  }
		return siebelAssetDaList;
	}
	
	@Override
	@CacheEvict(value="getHierarchyView", allEntries = true)
	public String resetHierarchyView() {
	    return " HierarchyView refreshed ";
	 }
	
			 
     //	errorList.add(new ErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
	//value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING")));     
	         
	public static final String SBL_ERR_Q = "SELECT 'SBL' TBL ,"
			+"  SD.ROW_ID ,"
			+"  SD.ASSET_NUM,"
			+"  SD.NAME,"
			+"  SD.CAGE_UNIQUE_SPACE_ID,"
			+"  SD.CAB_UNIQUE_SPACE_ID,"
			+"  ER.ERROR_CODE,"
			+"  MER.ERROR_NAME,"
			+"  MER.VALIDATION_CLASS,"
			+"  MER.OWNER_OF_FIXING"
			+"  FROM EQX_DART.SRC_CXI_ERROR_TBL ER"
			+"  INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			+"  ON ER.ERROR_CODE = MER.ERROR_CODE"
			+"  INNER JOIN EQX_DART.SIEBEL_ASSET_DA SD"
			+"  ON SD.ROW_ID = ER.SBL_RID WHERE  SD.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND lower(SD.STATUS_CD)!='inactive'"
			+"  AND lower(MER.ACTIVE)='y' ";
	
	public static final String errorTemplate = "ER.#fname# IN (#fvalue#)";
	
	public static final String filterTemplateSbl = "SD.#fname# IN (#fvalue#)";	
	public static final String globalTemplateSbl = "(upper(SD.IBX) LIKE '%#keyword#%'" 
					+" OR upper(SD.OU_NUM) LIKE '%#keyword#%'" 
					+" OR upper(SD.SYSTEM_NAME) LIKE '%#keyword#%'" 
					+" OR upper(SD.ASSET_NUM) LIKE '%#keyword#%'" 
					+" OR upper(SD.ACCOUNT_NAME) LIKE '%#keyword#%')";
	
	
	public static final String CLX_CAGE_ERR_Q= "SELECT 'CLX' TBL ,"
			 +"  CD.ROW_ID,"
			 +"  CD.ASSET_NUM,"
			 +"  ER.ERROR_CODE,"
			 +"  MER.ERROR_NAME,"
			 +"  MER.VALIDATION_CLASS,"
			 +"  MER.OWNER_OF_FIXING"
			+" FROM EQX_DART.SRC_CXI_ERROR_TBL ER"
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			+" ON ER.ERROR_CODE = MER.ERROR_CODE"
			+" INNER JOIN EQX_DART.CLX_ASSET_DA CD"
			+" ON CD.ROW_ID = ER.CLX_RID WHERE  CD.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND lower(CD.STATUS_CD)!='inactive' "
			+ " AND lower(CD.NAME)= 'cage'"
			+"  AND lower(MER.ACTIVE)='y' "
			+ " AND CD.CAGE_UNIQUE_SPACE_ID in (:list1)";
	
	public static final String CLX_CAB_ERR_Q= "SELECT 'CLX' TBL ,"
			 +"  CD.ROW_ID,"
			 +"  CD.ASSET_NUM,"
			 +"  ER.ERROR_CODE,"
			 +"  MER.ERROR_NAME,"
			 +"  MER.VALIDATION_CLASS,"
			 +"  MER.OWNER_OF_FIXING"
			+" FROM EQX_DART.SRC_CXI_ERROR_TBL ER"
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			+" ON ER.ERROR_CODE = MER.ERROR_CODE"
			+" INNER JOIN EQX_DART.CLX_ASSET_DA CD"
			+" ON CD.ROW_ID = ER.CLX_RID WHERE  CD.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND lower(CD.STATUS_CD)!='inactive' "
			+ " AND lower(CD.NAME)= 'cabinet'"
			+"  AND lower(MER.ACTIVE)='y' "
			+ " AND CD.CAB_UNIQUE_SPACE_ID in (:list1)";
	
	public static final String CLX_CAB_DP_ERR_Q= "SELECT 'CLX' TBL ,"
			 +"  CD.ROW_ID,"
			 +"  CD.ASSET_NUM,"
			 +"  ER.ERROR_CODE,"
			 +"  MER.ERROR_NAME,"
			 +"  MER.VALIDATION_CLASS,"
			 +"  MER.OWNER_OF_FIXING"
			+" FROM EQX_DART.SRC_CXI_ERROR_TBL ER"
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			+" ON ER.ERROR_CODE = MER.ERROR_CODE"
			+" INNER JOIN EQX_DART.CLX_ASSET_DA CD"
			+" ON CD.ROW_ID = ER.CLX_RID WHERE  CD.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND lower(CD.STATUS_CD)!='inactive' "
			+ " AND lower(CD.NAME)= 'demarcation point'"
			+"  AND lower(MER.ACTIVE)='y' "
			+ " AND CD.CAB_UNIQUE_SPACE_ID in (:list1)";
			
	
	public static final String filterTemplateClx = "CD.#fname# IN (#fvalue#)";
	public static final String globalTemplateClx = "(CD.IBX LIKE '%#keyword#%'" 
					+" OR CD.OU_NUM LIKE '%#keyword#%'" 
					+" OR CD.SYSTEM_NAME LIKE '%#keyword#%'" 
					+" OR CD.ASSET_NUM LIKE '%#keyword#%'" 
					+" OR CD.ACCOUNT_NAME LIKE '%#keyword#%')";
	
	public static final String SV_ERR_Q = "SELECT 'SV' TBL ,"
			  +"  SV.ROW_ID,"
			  +"  SV.ASSET_NUM,"
			  +"  ER.ERROR_CODE,"
			  +"  MER.ERROR_NAME,"
			  +"  MER.VALIDATION_CLASS,"
			  +"  MER.OWNER_OF_FIXING"
			  +"  FROM EQX_DART.SRC_CXI_ERROR_TBL ER"
			  +"  INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			  +"  ON ER.ERROR_CODE = MER.ERROR_CODE"
			  +"  INNER JOIN EQX_DART.SV_ASSET_DA SV"
			  +"  ON SV.ROW_ID = ER.SV_RID WHERE  (SV.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND lower(SV.STATUS_CD)!='inactive')"
			  +"  AND lower(MER.ACTIVE)='y' "
			  +"  AND SV.ASSET_NUM in (:list1)";
	
	public static final String filterTemplateSv = "SV.#fname# IN (#fvalue#)";
	public static final String globalTemplateSv = "(SV.IBX LIKE '%#keyword#%'" 
					+" OR SV.OU_NUM LIKE '%#keyword#%'" 
					+" OR SV.SYSTEM_NAME LIKE '%#keyword#%'" 
					+" OR SV.ASSET_NUM LIKE '%#keyword#%'" 
					+" OR SV.ACCOUNT_NAME LIKE '%#keyword#%')";
	
	
	@Override
	public List<SvAssetDa> getSvList(Set<String> assetList){
		List<SvAssetDa> svList = new ArrayList<>();
		if(assetList.size()>999){
			Lists.partition(Arrays.asList(assetList.toArray()), 999).stream().forEach(list->{
				if(CollectionUtils.isNotEmpty(assetList)){
					Session session = sessionFactory.getCurrentSession();
					Criteria criteria = session.createCriteria(SvAssetDa.class).setReadOnly(true);
					Criterion assetNumIn = Restrictions.in("header2", list.toArray());
					criteria.add(assetNumIn);
					ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						SvAssetDa svAssetDa = (SvAssetDa) scrollableResults.get()[0];
						svList.add(svAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				}
			});
		}else {
			if(CollectionUtils.isNotEmpty(assetList)){
				Session session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(SvAssetDa.class).setReadOnly(true);
				Criterion assetNumIn = Restrictions.in("header2", assetList.toArray());
				criteria.add(assetNumIn);
				ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					SvAssetDa svAssetDa = (SvAssetDa) scrollableResults.get()[0];
					svList.add(svAssetDa);
				   /*session.evict(siebelAssetDa);*/
				}
			}
		}
		return svList;
	}
	
	@Override
	public List<ClxAssetDa> getClxList(Set<String> cageList ,Set<String> cabList,Set<String> cabDpList, Set<String> cageRowIds, Set<String> cabinetRowIds,Set<String> cabinetDpRowIds ){
		List<ClxAssetDa> clxList = new ArrayList<>();
		
		Session session = sessionFactory.getCurrentSession();
		
		
		if(CollectionUtils.isNotEmpty(cageList)){
			if(cageList.size()>999){
				Lists.partition(Arrays.asList(cageList.toArray()), 999).stream().forEach(list->{
					Criteria cageCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
					Criterion cageIn = Restrictions.in("header10", list.toArray());
					Criterion cageProduct = Restrictions.eq("header20", "Cage");
					Conjunction cageConjuncation = Restrictions.conjunction();
					cageConjuncation.add(cageIn);
					cageConjuncation.add(cageProduct);
					cageCriteria.add(cageConjuncation);
					ScrollableResults scrollableResults = cageCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						  ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
						  cageRowIds.add(clxAssetDa.getHeader1());
						  clxList.add(clxAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				
				
				});
			}else{
				
				Criteria cageCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
				Criterion cageIn = Restrictions.in("header10", cageList.toArray());
				Criterion cageProduct = Restrictions.eq("header20", "Cage");
				Conjunction cageConjuncation = Restrictions.conjunction();
				cageConjuncation.add(cageIn);
				cageConjuncation.add(cageProduct);
				cageCriteria.add(cageConjuncation);
				ScrollableResults scrollableResults = cageCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					  ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
					  cageRowIds.add(clxAssetDa.getHeader1());
					  clxList.add(clxAssetDa);
				   /*session.evict(siebelAssetDa);*/
				}
			}
		}
		
	
		if(CollectionUtils.isNotEmpty(cabList)){
			if(cabList.size()>999){
				Lists.partition(Arrays.asList(cabList.toArray()), 999).stream().forEach(list->{
					Conjunction cabinetConjuncation = Restrictions.conjunction();
					Criterion cabinetIn = Restrictions.in("header12", list.toArray());
					Criterion cabinetProduct = Restrictions.eq("header20", "Cabinet");
					cabinetConjuncation = Restrictions.conjunction();
					cabinetConjuncation.add(cabinetIn);
					cabinetConjuncation.add(cabinetProduct);
					Criteria cabinetCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
					cabinetCriteria.add(cabinetConjuncation);
					ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
						cabinetRowIds.add(clxAssetDa.getHeader1());
						clxList.add(clxAssetDa);
						/*session.evict(siebelAssetDa);*/
					}
				
				
				});
			}else{
				Conjunction cabinetConjuncation = Restrictions.conjunction();
				Criterion cabinetIn = Restrictions.in("header12", cabList.toArray());
				Criterion cabinetProduct = Restrictions.eq("header20", "Cabinet");
				cabinetConjuncation = Restrictions.conjunction();
				cabinetConjuncation.add(cabinetIn);
				cabinetConjuncation.add(cabinetProduct);
				Criteria cabinetCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
				cabinetCriteria.add(cabinetConjuncation);
				ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
					cabinetRowIds.add(clxAssetDa.getHeader1());
					clxList.add(clxAssetDa);
					/*session.evict(siebelAssetDa);*/
				}
			}
		}
			// DP
			if(CollectionUtils.isNotEmpty(cabDpList)){
				if(cabDpList.size()>999){
					Lists.partition(Arrays.asList(cabDpList.toArray()), 999).stream().forEach(list->{
						Conjunction cabinetConjuncation = Restrictions.conjunction();
						Criterion cabinetDpIn = Restrictions.in("header12", list.toArray());
						Criterion cabinetDpProduct = Restrictions.eq("header20", "Demarcation Point");
						cabinetConjuncation = Restrictions.conjunction();
						cabinetConjuncation.add(cabinetDpIn);
						cabinetConjuncation.add(cabinetDpProduct);
						Criteria cabinetCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
						cabinetCriteria.add(cabinetConjuncation);
						ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
						while (scrollableResults.next()) {
							ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
							cabinetDpRowIds.add(clxAssetDa.getHeader1());
							clxList.add(clxAssetDa);
							/*session.evict(siebelAssetDa);*/
						}
					
					
					});
				}else{
					Conjunction cabinetConjuncation = Restrictions.conjunction();
					Criterion cabinetDpIn = Restrictions.in("header12", cabDpList.toArray());
					Criterion cabinetDpProduct = Restrictions.eq("header20", "Demarcation Point");
					cabinetConjuncation = Restrictions.conjunction();
					cabinetConjuncation.add(cabinetDpIn);
					cabinetConjuncation.add(cabinetDpProduct);
					Criteria cabinetCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
					cabinetCriteria.add(cabinetConjuncation);
					ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
						cabinetDpRowIds.add(clxAssetDa.getHeader1());
						clxList.add(clxAssetDa);
						/*session.evict(siebelAssetDa);*/
					}
				}
			
		}
		return clxList;
	}
	
	private List<SiebelAssetDa> getSiebelErrorListData(List<SiebelAssetDa> sblListTemp, List<String> errorCodes ){
		List<SiebelAssetDa> sblList= new ArrayList<SiebelAssetDa>();
		if(null!=sblListTemp && sblListTemp.size()>0){
			HashMap<String, List<SrcCxiErrorTbl>> srcErrMap = initDfrDao.getSblErrorMap(); 
			for(int i=0;i<sblListTemp.size();i++){
				List<SrcCxiErrorTbl> sblErrRecordList = srcErrMap.get(sblListTemp.get(i).getHeader1());
				if(CollectionUtils.isNotEmpty(sblErrRecordList)){
					for(SrcCxiErrorTbl sblErrRecord:sblErrRecordList){
						if(errorCodes.contains(sblErrRecord.getErrorCode()))
							sblList.add(sblListTemp.get(i));
					}
				}
			}
		}
		return sblList;
	}
	
	@Override
	@Cacheable(value="dartDafilter",key ="#root.methodName.concat(#productFilterResult.productFilter.cacheKey)")
	public ProductFilterResult getProductFilterResult(ProductFilterResult productFilterResult) {
		long start = System.currentTimeMillis();
		List<SiebelAssetDa> sblListTemp = getSiebleAssetDaData(productFilterResult.getProductFilter());
		long end = System.currentTimeMillis();
		List<SiebelAssetDa> sblList = new ArrayList<SiebelAssetDa>();
		start = System.currentTimeMillis();
		boolean isErrorCodeInFilter = false;
		List<PFilter> filters = productFilterResult.getProductFilter().getFilters();
		List<String> errorCodes = new ArrayList<String>();
		PFilter filtr = null;
		for (int fcount = 0; fcount < filters.size(); fcount++) {
			filtr = filters.get(fcount);
			if (filters.get(fcount).getLable().toLowerCase().contains("error")) {
				errorCodes = filtr.getListOfValues();
				if (errorCodes.size() > 0) {
					isErrorCodeInFilter = true;
					break;
				}
			}
		}
		if (isErrorCodeInFilter) {
			sblList = getSiebelErrorListData(sblListTemp, errorCodes);
		} else {
			sblList = sblListTemp;
		}
		end = System.currentTimeMillis();
		start = System.currentTimeMillis();
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
		for (SiebelAssetDa sbl : sblList) {
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
		clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, cageRowIds, cabinetRowIds,
				cabinetDpRowIds);
		svList = getSvList(assetSet);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		productFilterResult.setClxCabninetRowIds(cabinetRowIds);
		productFilterResult.setClxCageRowIds(cageRowIds);
		productFilterResult.setSblRowIds(sblRowIds);
		productFilterResult.setClxCabninetDpRowIds(cabinetDpRowIds);
		end = System.currentTimeMillis();
		return productFilterResult;
	}
	
	@Override
	//@Cacheable(value="accMoveDartDafilter",key ="#root.methodName.concat(#productFilterResult.productFilter.cacheKey)")
	public ProductFilterResult getProductFilterResultForAccMoveDfr(List<String> sblRowIdList) {
		ProductFilterResult productFilterResult = new ProductFilterResult();
		long start = System.currentTimeMillis();
		List<SiebelAssetDa> sblList = getSblList(sblRowIdList);
		long end = System.currentTimeMillis();
		
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
		for (SiebelAssetDa sbl : sblList) {
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
		clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, cageRowIds, cabinetRowIds,
				cabinetDpRowIds);
		svList = getSvList(assetSet);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		productFilterResult.setClxCabninetRowIds(cabinetRowIds);
		productFilterResult.setClxCageRowIds(cageRowIds);
		productFilterResult.setSblRowIds(sblRowIds);
		productFilterResult.setClxCabninetDpRowIds(cabinetDpRowIds);
		end = System.currentTimeMillis();
		return productFilterResult;
	}
	
	@Override
	@CacheEvict(value="dartDafilter", allEntries = true)
	public String resetDartDaFilter() {
	    return " DartDaFilter refreshed ";
	 }
	
	@Override
	@Cacheable(value="SiebelAssetDaCache",key ="#root.methodName.concat(#productFilter.cacheKey)")
	public List<SiebelAssetDa> getSiebleAssetDaData(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SiebelAssetDa> siebelAssetDaList=new ArrayList<>();
		Criteria criteria=session.createCriteria(SiebelAssetDa.class);
		getCriteriaForDa(productFilter, criteria,false);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		  while (scrollableResults.next()) {
		   SiebelAssetDa siebelAssetDa = (SiebelAssetDa) scrollableResults.get()[0];
		   siebelAssetDaList.add(siebelAssetDa);
		   /*session.evict(siebelAssetDa);*/
		  }
		return siebelAssetDaList;
	}
	
	@Override
	@CacheEvict(value="SiebelAssetDaCache", allEntries = true)
	public String resetSiebelAssetDaCache() {
	    return " SiebelAssetDaCache refreshed ";
	 }
	
	@Override
	public List<ClxAssetDa> getClxAssetDaData(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<ClxAssetDa> clxAssetDaList = new ArrayList<>();
		Criteria criteria = session.createCriteria(ClxAssetDa.class);
		getCriteriaForDa(productFilter, criteria,false);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			ClxAssetDa clxAssetDa = (ClxAssetDa)scrollableResults.get()[0];
			clxAssetDaList.add(clxAssetDa);
			/*session.evict(clxAssetDa);*/
		}
		return clxAssetDaList;
	}

	

	@Override
	public List<SvAssetDa> getSvAssetDaData(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SvAssetDa> svAssetDaList  = new ArrayList<>();
		Criteria criteria = session.createCriteria(SvAssetDa.class);
		getCriteriaForDa(productFilter, criteria,false);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SvAssetDa svAssetDa= (SvAssetDa)scrollableResults.get()[0];
			svAssetDaList.add(svAssetDa);
			/*session.evict(svAssetDa);*/
		}
		return svAssetDaList;
	}
	 @Override
	 @Cacheable(value="getConfigProdcutCommonAttrMap",key="#root.methodName")
	 public Map<String,Object>  getConfigProdcutCommonAttrMap() {
		 	HashMap<String, SortedMap<Integer,AttributeConfig>> configProdcutCommonAttrMap = new HashMap<>();
		 	Map<String,Integer> productAttrLengthMap= new HashMap<>();
		 	Map<String,Integer> maxCommonAttrForProduct = new HashMap<>();
		 	Map<String,Object> resultMap = new HashMap<>();
		 	List<String> productList = getProducts();
		 	productList.stream().forEach(product->{
				List<AttributeConfig> attributeListByProduct = getCommonAttributesByProduct(product);
				if(CollectionUtils.isNotEmpty(attributeListByProduct)){
					if(!configProdcutCommonAttrMap.containsKey(product)){
						SortedMap<Integer, AttributeConfig> attributeMap= new TreeMap<>();
						IntStream.range(0, attributeListByProduct.size()).forEach(index -> {
							AttributeConfig config = attributeListByProduct.get(index);
							attributeMap.put(index+1 , config);	
						});
						configProdcutCommonAttrMap.put(product, attributeMap);
						productAttrLengthMap.put(product,attributeMap.size());
					}
				}
			});
			maxCommonAttrForProduct = productAttrLengthMap.entrySet()
	                .stream()
	                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			resultMap.put("common", configProdcutCommonAttrMap);
			resultMap.put("max", maxCommonAttrForProduct);
			return resultMap;
	 }
	 
	 @Override
	 @CacheEvict(value="getConfigProdcutCommonAttrMap", allEntries=true)
	 public String resetConfigProdcutCommonAttrMap(){
		 return "getConfigProdcutCommonAttrMap refreshed";
	 }
	 

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFilterListFromDA(String header) {
		Session session =   sessionFactory.getCurrentSession();
		ProjectionList proList = Projections.projectionList();
	    proList.add(Projections.property(header.toLowerCase()));
		Criteria crit = session.createCriteria(SiebelAssetDa.class)
				.setProjection(Projections.distinct(proList)).setReadOnly(true);
		List<String> resultList = (List<String>)crit.list();
		if(header.equalsIgnoreCase("header16")){
			return DartUtil.senitizeOutputList(resultList);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value="getFilterListFromErrorMaster")
	public List<String> getFilterListFromErrorMaster() {
		Session session =   sessionFactory.getCurrentSession();
		ProjectionList proList = Projections.projectionList();
	    proList.add(Projections.property("errorCode"));
		Criteria crit = session.createCriteria(SrcCxiErrorMasterTbl.class)
				.setProjection(Projections.distinct(proList)).setReadOnly(true); 
		crit.add(Restrictions.eq("active", "Y"));
		return (List<String>)crit.list();
	}
	
	@Override
	@CacheEvict(value="getFilterListFromErrorMaster", allEntries = true)
	public String resetFilterListFromErrorMaster() {
	    return " FilterListFromErrorMaster refreshed ";
	 }

	private void getCriteria(ProductFilter productFilter, Criteria criteria, String product) {
		List<PFilter> filters = productFilter.getFilters();
		PFilter filter = null ;
		Conjunction conjunction = Restrictions.conjunction();
		Criterion criterion;
		for(int fcount= 0 ; fcount< filters.size() ; fcount++){
			filter = filters.get(fcount);
			String headerOrAttr= filter.getKey();
			
			if(headerOrAttr.equalsIgnoreCase("header20")){
				criterion = Restrictions.eq(headerOrAttr, product);
				conjunction.add(criterion);
			}else{
				if(!filter.getLable().toLowerCase().contains("error")){
					if(CollectionUtils.isNotEmpty(filter.getListOfValues())){
						criterion = Restrictions.in(headerOrAttr, filter.getListOfValues().toArray());
						conjunction.add(criterion);
					}
				}
			}
			
		}
		criteria.add(conjunction);
		
	}
	private String  getErrorQuery(ProductFilter filters, String filterTemplate, String globalTemplate, String errorTemplate, String SQL){
		String  conditionTemplate = " AND (#filters#) ";
		
		if(StringUtils.isNotEmpty(filters.getKeyword())){
			String condition1 = getGlobalFilterCondition(filters, filters.getKeyword(), globalTemplate);
			String condition2 = getFilterCondition(filters, filterTemplate, errorTemplate);
			if(StringUtils.isNotBlank(condition2)){
				SQL = SQL + conditionTemplate.replace("#filters#", condition2);
			}
			if(StringUtils.isNotBlank(condition1)){
				SQL = SQL +conditionTemplate.replace("#filters#", condition1);
			}
		}else{
			String condition2 = getFilterCondition(filters, filterTemplate,errorTemplate);
			if(StringUtils.isNotBlank(condition2)){
				SQL = SQL + conditionTemplate.replace("#filters#", condition2);
			}
		}
		
		
		return SQL;
	}
	
	private  String getGlobalFilterCondition(ProductFilter filters, String keyword, String filterTemplate){
		if(StringUtils.isNotEmpty(filters.getKeyword())){
			return filterTemplate.replace("#keyword#", keyword.toUpperCase());
		}else{
			return "";
		}
	}
	private String getFilterCondition(ProductFilter filters, String fiterTemplate, String errorTemplate){
		List<String> filterList = new ArrayList<>();
		for(PFilter filter : filters.getFilters()){
			
			if(filter.getKey().equalsIgnoreCase("header2")&& StringUtils.isNotBlank(filter.getValue())){
				String fval="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "ASSET_NUM");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			} else if(filter.getKey().equalsIgnoreCase("header3")&& StringUtils.isNotBlank(filter.getValue())){
				String fval="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "SERIAL_NUM");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			} else if(filter.getKey().equalsIgnoreCase("header8")&& StringUtils.isNotBlank(filter.getValue())){
				String fval="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "IBX");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			}else if(filter.getKey().equalsIgnoreCase("header6")&& StringUtils.isNotBlank(filter.getValue())){
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "OU_NUM");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			}else if(filter.getKey().equalsIgnoreCase("header20")&& StringUtils.isNotBlank(filter.getValue())){
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "NAME");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			}else if(filter.getKey().equalsIgnoreCase("header16")&& StringUtils.isNotBlank(filter.getValue())){
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "SYSTEM_NAME");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			} else if (filter.getKey().equalsIgnoreCase("header51")&& StringUtils.isNotBlank(filter.getValue())) {
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "REGION");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			} else if(filter.getKey().equalsIgnoreCase("error code")&& StringUtils.isNotBlank(filter.getValue())){
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = errorTemplate.replace("#fname#", "ERROR_CODE");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			}
		}
		String filterCondition ="";
		for(int count = 0 ;count<filterList.size();count++ ){
			if(count+1 == filterList.size())
				filterCondition = filterCondition + filterList.get(count);
			else 
				filterCondition = filterCondition + filterList.get(count) + " AND ";
		}
		if(CollectionUtils.isNotEmpty(filterList)){
			return filterCondition;
		}else {
			return "";
		}
	
	}
	
	private  String removeLastChar(String s) {
	    return (s == null || s.length() == 0)
	      ? null
	      : (s.substring(0, s.length() - 1));
	}

	
	private String getFilterValues(PFilter filter, String fval) {
		String value ="";
		if(StringUtils.isNotEmpty(fval)){
			if(filter.getKey().equalsIgnoreCase("header2")){
				for(String filterVal : filter.getListOfValues()){
					value =value + "'"+filterVal+"'"+",";
				}
			}else{
			for(String filterVal :filter.getValue().split(",")){
				if(filter.getKey().equalsIgnoreCase("header16")&& fval.contains("~")){
					filterVal = DartUtil.sanitizeInput(filterVal);
				}
				value =value + "'"+filterVal+"'"+",";
			}
		}
		}
		return value;
	}
	
	@Override
	public ClxAssetDa getClxAssetDaByRowId(String rowId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ClxAssetDa.class);
		ClxAssetDa clxDa =(ClxAssetDa)criteria.add(Restrictions.eq("header1", rowId)).uniqueResult();
		return clxDa;
	}

	@Override
	public SvAssetDa getSvAssetDaByRowId(String rowId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SvAssetDa.class);
		SvAssetDa sblDa =(SvAssetDa)criteria.add(Restrictions.eq("header1", rowId)).uniqueResult();
		return sblDa;
	}
	
	
	@Override
	public List<ClxAssetDa> getClxAssetDaByRowsIds(List<String> rowIds) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ClxAssetDa.class);
		Criterion   criterion = Restrictions.in("header1", rowIds.toArray());
		Criterion   criterion2 = Restrictions.ne("header18", "Inactive").ignoreCase();
		criteria.add(criterion);
		criteria.add(criterion2);
		return (List<ClxAssetDa>)criteria.list();
	}

	@Override
	public List<SvAssetDa> getSvAssetDaByRowsIds(List<String> rowIds) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SvAssetDa.class);
		Criterion   criterion = Restrictions.in("header1", rowIds.toArray());
		Criterion   criterion2 = Restrictions.ne("header18", "Inactive").ignoreCase();
		criteria.add(criterion);
		criteria.add(criterion2);
		return (List<SvAssetDa>)criteria.list();
	}
	
	@Override
	public List<SiebelAssetDa> getSblAssetDaByRowsIds(List<String> rowIds) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SiebelAssetDa.class);
		Criterion   criterion = Restrictions.in("header1", rowIds.toArray());
		Criterion   criterion2 = Restrictions.ne("header18", "Inactive").ignoreCase();
		criteria.add(criterion);
		criteria.add(criterion2);
		return (List<SiebelAssetDa>)criteria.list();
	}
	
	
	public List<String> getSBLErrorCodeList(List<String> sblRowIdList,List<String> errorCodes){
		Session session =   sessionFactory.getCurrentSession();
		List<String> activeErrorCode = getActiveErrorCodes();
		List<String> sblRidList = new ArrayList<String>();
		
		List<String> list1 = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
			if(sblRowIdList.size()>999){
				Lists.partition(Arrays.asList(sblRowIdList.toArray()), 999).stream().forEach(sublist->{
					Criteria criteria = session.createCriteria(SrcCxiErrorTbl.class).setReadOnly(true);
					Conjunction conjunction = Restrictions.conjunction();
					Criterion   criterion1 = Restrictions.in("sblRid", sublist.toArray());
					Criterion   criterion2 = Restrictions.in("errorCode", errorCodes.toArray());
					Criterion   criterion3 = Restrictions.isNull("errorEndDate");
					Criterion activeCriterion = Restrictions.in("errorCode", activeErrorCode.toArray());
					conjunction.add(criterion1);
					conjunction.add(criterion2);
					conjunction.add(criterion3);
					conjunction.add(activeCriterion);
					ProjectionList pList = Projections.projectionList(); 
					pList.add(Projections.property("sblRid")); 
					criteria.setProjection(pList); 
					criteria.add(conjunction);
					
					ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						  String sblrid = (String) scrollableResults.get()[0];
						  list1.add(sblrid);
					   /*session.evict(siebelAssetDa);*/
					}
				
				
				});
			}else{
				Criteria criteria = session.createCriteria(SrcCxiErrorTbl.class).setReadOnly(true);
				Conjunction conjunction = Restrictions.conjunction();
				Criterion   criterion1 = Restrictions.in("sblRid", sblRowIdList.toArray());
				Criterion   criterion2 = Restrictions.in("errorCode", errorCodes.toArray());
				Criterion   criterion3 = Restrictions.isNull("errorEndDate");
				Criterion activeCriterion = Restrictions.in("errorCode", activeErrorCode.toArray());
				conjunction.add(criterion1);
				conjunction.add(criterion2);
				conjunction.add(criterion3);
				conjunction.add(activeCriterion);
				ProjectionList pList = Projections.projectionList(); 
				pList.add(Projections.property("sblRid")); 
				criteria.setProjection(pList); 
				criteria.add(conjunction);
				sblRidList = (List<String>)criteria.list();
			}
		}
 		
		return sblRidList;
	}
	
	/**
	 * @param criteria
	 */
	private void getSelectedHeadersList(Criteria criteria){
			ProjectionList pList = Projections.projectionList(); 
			pList.add(Projections.property("header1")); 
			pList.add(Projections.property("header2")); 
			pList.add(Projections.property("header3")); 
			pList.add(Projections.property("header4")); 
			pList.add(Projections.property("header5")); 
			pList.add(Projections.property("header6")); 
			pList.add(Projections.property("header7")); 
			pList.add(Projections.property("header8")); 
			pList.add(Projections.property("header9")); 
			pList.add(Projections.property("header10")); 
			pList.add(Projections.property("header11")); 
			pList.add(Projections.property("header12")); 
			pList.add(Projections.property("header13")); 
			pList.add(Projections.property("header14")); 
			pList.add(Projections.property("header16")); 
			pList.add(Projections.property("header20")); 
			pList.add(Projections.property("header22")); 
			pList.add(Projections.property("header27")); 
			pList.add(Projections.property("header38")); 
			pList.add(Projections.property("attr22")); 
			
			criteria.setProjection(pList); 
	}
	
	private List<String> getActiveErrorCodes() {
		Session session =  sessionFactory.getCurrentSession();
		Criteria activeErrorCritera = session.createCriteria(SrcCxiErrorMasterTbl.class).add(Restrictions.eq("active","Y"));
		ProjectionList proList = Projections.projectionList();
	    proList.add(Projections.property("errorCode"));
	    activeErrorCritera.setProjection(proList).setReadOnly(true);
	    List<String> activeErrorCode = (List<String>)activeErrorCritera.list();
	    logger.info("Active Errors : " +activeErrorCode.toString() );
		return activeErrorCode;
	}
	
	@Override
	public List<AttributeConfig> getPhysicalHeaders() {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		List<AttributeConfig> headerList =criteria
				.add(Restrictions.like("headerPosition", "%HEADER%"))
				.add(Restrictions.and(Restrictions.eq("product", "HEADER")))
				.add(Restrictions.and(Restrictions.isNotNull("auditHeader")))
				.addOrder(Order.asc("headerPosition")).list();
		return headerList;
	}

	@Override
	public List<AttributeConfig> getPhysicalAttributesByProduct(String product) {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		List<AttributeConfig> attributeList =criteria
				.add(Restrictions.like("headerPosition", "%ATTR%"))
				.add(Restrictions.and(Restrictions.eq("product", product)))
				.add(Restrictions.and(Restrictions.isNotNull("auditHeader")))
				.addOrder(Order.asc("sequence")).list();
		return attributeList;
	}
	
	
	
	@Override
	//@Cacheable(value="getConfigHeaderMap")
	public SortedMap<Integer, AttributeConfig> getPhysicalConfigHeaderMap() {
		List<AttributeConfig> attributeList =	getPhysicalHeaders();
		SortedMap<Integer, AttributeConfig> configHeaderMap = new TreeMap<>();
		if(CollectionUtils.isNotEmpty(attributeList)){
			attributeList.stream().forEach(attributeConfig -> {
				configHeaderMap.put(DartUtil.getIntegerValue(attributeConfig.getHeaderPosition()), attributeConfig);
			});
		}
		return configHeaderMap;
	}
	
	@Override
	//@Cacheable(value="getConfigProductAttrMap")
	public HashMap<String, SortedMap<Integer, AttributeConfig>> getPhysicalConfigProductAttrMap() {
		HashMap<String, SortedMap<Integer, AttributeConfig>>	configProductAttrMap  = new HashMap<>();
		List<String> productList = getProductList();
		if(CollectionUtils.isNotEmpty(productList)){
			productList.stream().forEach(product->{
				List<AttributeConfig> attributeListByProduct = getPhysicalAttributesByProduct(product);
				if(CollectionUtils.isNotEmpty(attributeListByProduct)){
					if(!configProductAttrMap.containsKey(product)){
						SortedMap<Integer, AttributeConfig> attributeMap= new TreeMap<>();
						for(AttributeConfig config : attributeListByProduct){
							attributeMap.put(DartUtil.getIntegerAttrValue(config.getHeaderPosition()), config);
						}
						configProductAttrMap.put(product, attributeMap);
					}
				}
			});
		}
		return configProductAttrMap;
	}
	
	private String getErrorQueryForErrorCodeGlobalKeyword(ProductFilter filters, String filterTemplate,
			String errorTemplate, String SQL) {
		String conditionTemplate = " AND (#filters#) ";
		String condition = getFilterCondition(filters, filterTemplate, errorTemplate);
		if (StringUtils.isNotBlank(condition)) {
			SQL = SQL + conditionTemplate.replace("#filters#", condition);
		}
		return SQL;
	}

	@Override
	@Cacheable(value="getPhysicalConfigMobileHeaderMap")
	public SortedMap<Integer, AttributeConfig> getPhysicalConfigMobileHeaderMap() {
		List<AttributeConfig> attributeList =	getPhysicalMobileHeaders();
		SortedMap<Integer, AttributeConfig> configHeaderMap = new TreeMap<>();
		if(CollectionUtils.isNotEmpty(attributeList)){
			attributeList.stream().forEach(attributeConfig -> {
				configHeaderMap.put(DartUtil.getIntegerValue(attributeConfig.getHeaderPosition()), attributeConfig);
			});
		}
		return configHeaderMap;
	}
	
	@Override
	public String refreshMobAuditHeaderAndAttributes() {
		refreshMobHeader();
		refreshMobAttributes();
		return "done";
	}
	
	@CacheEvict(value="getConfigMobileHeaderMap" , allEntries = true)
	private String refreshMobHeader(){
		return "refreshMobHeader refreshed";
	}
	
	@CacheEvict(value="getConfigMobileProductAttrMap",  allEntries = true)
	private String refreshMobAttributes(){
		return "refreshMobAttributes refreshed";
	}

	@Override
	public List<AttributeConfig> getPhysicalMobileHeaders() {
		Session session =   sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		List<AttributeConfig> headerList =criteria
				.add(Restrictions.like("headerPosition", "%HEADER%"))
				.add(Restrictions.and(Restrictions.eq("product", "HEADER")))
				.add(Restrictions.and(Restrictions.eq("auditFlag","Y")))
				.addOrder(Order.asc("headerPosition")).list();
		return headerList;
	}

	@Override
	public List<ClxAssetDa> getClxList(Set<String> cageList, Set<String> cabList) {
		List<ClxAssetDa> clxList = new ArrayList<>();		
		Session session = sessionFactory.getCurrentSession();
		
		if(CollectionUtils.isNotEmpty(cageList)){
			if(cageList.size()>999){
				Lists.partition(Arrays.asList(cageList.toArray()), 999).stream().forEach(list->{
					Criteria cageCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
					Criterion cageIn = Restrictions.in("header10", list.toArray());
					Criterion cageProduct = Restrictions.eq("header20", "Cage");
					Conjunction cageConjuncation = Restrictions.conjunction();
					cageConjuncation.add(cageIn);
					cageConjuncation.add(cageProduct);
					cageCriteria.add(cageConjuncation);
					ScrollableResults scrollableResults = cageCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						  ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];						  
						  clxList.add(clxAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				
				
				});
			}else{
				
				Criteria cageCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
				Criterion cageIn = Restrictions.in("header10", cageList.toArray());
				Criterion cageProduct = Restrictions.eq("header20", "Cage");
				Conjunction cageConjuncation = Restrictions.conjunction();
				cageConjuncation.add(cageIn);
				cageConjuncation.add(cageProduct);
				cageCriteria.add(cageConjuncation);
				ScrollableResults scrollableResults = cageCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					  ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];					  
					  clxList.add(clxAssetDa);
				   /*session.evict(siebelAssetDa);*/
				}
			}
		}
		
	
		if(CollectionUtils.isNotEmpty(cabList)){
			if(cabList.size()>999){
				Lists.partition(Arrays.asList(cabList.toArray()), 999).stream().forEach(list->{
					Conjunction cabinetConjuncation = Restrictions.conjunction();
					Criterion cabinetIn = Restrictions.in("header12", list.toArray());
					Criterion cabinetProduct = Restrictions.eq("header20", "Cabinet");
					cabinetConjuncation = Restrictions.conjunction();
					cabinetConjuncation.add(cabinetIn);
					cabinetConjuncation.add(cabinetProduct);
					Criteria cabinetCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
					cabinetCriteria.add(cabinetConjuncation);
					ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];						
						clxList.add(clxAssetDa);
						/*session.evict(siebelAssetDa);*/
					}
				
				
				});
			}else{
				Conjunction cabinetConjuncation = Restrictions.conjunction();
				Criterion cabinetIn = Restrictions.in("header12", cabList.toArray());
				Criterion cabinetProduct = Restrictions.eq("header20", "Cabinet");
				cabinetConjuncation = Restrictions.conjunction();
				cabinetConjuncation.add(cabinetIn);
				cabinetConjuncation.add(cabinetProduct);
				Criteria cabinetCriteria = session.createCriteria(ClxAssetDa.class).setReadOnly(true);
				cabinetCriteria.add(cabinetConjuncation);
				ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					ClxAssetDa clxAssetDa = (ClxAssetDa) scrollableResults.get()[0];
					clxList.add(clxAssetDa);
					/*session.evict(siebelAssetDa);*/
				}
			}
		}
		return clxList;
	}
	
	@Override
	public ProductFilterResult getAllProductFilterResultElastic(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException {
		productFilterResult = elasticDao.getAllProductAttributeView(productFilterResult);
		return productFilterResult;
	}
	
	@Override
	public SearchFilters getGlobalSearchElastic(ProductFilter productFilter) {

		SearchFilters filters = new SearchFilters();
		HashMap<String,List<String>> resultMap = elasticDao.getFilterList(productFilter);
		filters = new SearchFilters();
		Filter filter1=new Filter();
		filter1.setKey("header8");
		filter1.setLable("IBX");
		filter1.setValues(resultMap.get("ibx"));
		filters.getFilters().add(filter1);

		Filter filter2=new Filter();
		filter2.setKey("header6");
		filter2.setLable("ACCOUNT NUM");
		filter2.setValues(resultMap.get("accNum"));
		filters.getFilters().add(filter2);

		Filter filter3=new Filter();
		filter3.setKey("header16");
		filter3.setLable("SYSTEM NAME");
		filter3.setValues(resultMap.get("sysName"));
		filters.getFilters().add(filter3);
		return filters;
	
	}
	
	@Override
	public ProductSearchResponse getProductSearchResponseElastic(ProductFilter productFilter) throws InterruptedException, ExecutionException {
		ProductSearchResponse productSearchResponse = elasticDao.getProductTiles(productFilter);
		return productSearchResponse;
		
	}
	
	@Override
	public ProductFilterResult getProductFilterResultElastic(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException{
		productFilterResult = elasticDao.getProductAttributeView(productFilterResult);
		return productFilterResult;
	}
	
	@Override
	public List<SiebelAssetDa> getSblAssetDaAllByRowsIdsElastic(List<String> sblRowIdList,
			ProductFilter productFilter) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return elasticDao.getSblAllByError(sblRowIdList, productFilter);
	}

	@Override
	public List<ClxAssetDa> getClxAssetDaAllByRowsIdsElastic(List<String> clxRowIdList, ProductFilter productFilter) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return elasticDao.getClxAllByError(clxRowIdList, productFilter);
	}

	@Override
	public List<SvAssetDa> getSvAssetDaAllByRowsIdsElastic(List<String> svRowIdList, ProductFilter productFilter) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return elasticDao.getSvAllByError(svRowIdList, productFilter);
	}
	
	@Override
	public List<ErrorData> getErrorSectionElastic(ProductFilter productFilter, boolean keywordHasVal) throws InterruptedException, ExecutionException{
		List<ErrorData> errorList = new ArrayList<>();
		errorList =	elasticDao.getErrorSection(productFilter, keywordHasVal);
		return errorList;
	}
	
	@Override
	public List<String> getAllSystemName(String keyword) {
		return (List<String>)jdbc.query(ProNativeQueries.SYSTEM_NAME_LIST.replace("#keyword#", keyword), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("SYSTEM_NAME");
			}
		});
	}
	
	
	@Override
	public List<String> getAllRegion() {
		 
		return (List<String>)jdbc.query(ProNativeQueries.REGION_LIST, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("REGION");
			}
			
		});
	}
	
	@Override
	public List<CountryAndIbxVo> getCountriesIbxByRegion(String region) {
		
		return jdbc.query(ProNativeQueries.COUNTRY_N_IBX_BY_REGION, new Object[]{region}, new CountryAndIbxMapper());
	}
	
	@Override
	public List<String> getIbxByCountriesNRegion(String region, String country) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("country", Arrays.asList(country.split(",")));
		return (List<String>)namedJdbc.query(ProNativeQueries.IBX_BY_COUNTRY_N_REGION.replace("#region#", region),namedParameters, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("IBX");
			}
		});
	}
	
	@Override
	public List<ErrorCodeVO> getErrorsByIbx(String ibx) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("ibxs", Arrays.asList(ibx.split(",")));
		return namedJdbc.query(ProNativeQueries.ERRORS_BY_IBX, namedParameters, new ErrorCodeMapper());
	}
	
	@Override
	public List<AccountVo> getAllActiveAccountNum(SearchFormFilter searchFormFilter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = ProNativeQueries.ACC_NUMBER_BY_IBX;
		if (!StringUtils.isBlank(searchFormFilter.getRegion()) && !StringUtils.isEmpty(searchFormFilter.getRegion())) {
			sql += " AND REGION = (:region)";
			params.addValue("region", searchFormFilter.getRegion());
		}
		if (!StringUtils.isBlank(searchFormFilter.getCountry()) && !StringUtils.isEmpty(searchFormFilter.getCountry())) {
			sql += " AND COUNTRY= (:country)";
			params.addValue("country", searchFormFilter.getCountry());
		}
		if (!StringUtils.isBlank(searchFormFilter.getIbx()) && !StringUtils.isEmpty(searchFormFilter.getIbx())) {
			sql += "AND IBX =(:ibx)";	
			params.addValue("ibx", searchFormFilter.getIbx());
		
		}
		return namedJdbc.query(sql, params,new AccountMapper());
	}
	
	@Override
	public List<ErrorCodeVO> getAllActiveErrors() {
		/*MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = ProNativeQueries.ACTIVE_ERROR_LIST;
		if (!StringUtils.isBlank(searchFormFilter.getRegion()) && !StringUtils.isEmpty(searchFormFilter.getRegion())) {
			sql += " AND SDA.REGION = (:region)";
			params.addValue("region", searchFormFilter.getRegion());
		}
		if (!StringUtils.isBlank(searchFormFilter.getCountry()) && !StringUtils.isEmpty(searchFormFilter.getCountry())) {
			sql += " AND SDA.COUNTRY= (:country)";
			params.addValue("country", searchFormFilter.getCountry());
		}
		if (!StringUtils.isBlank(searchFormFilter.getIbx()) && !StringUtils.isEmpty(searchFormFilter.getIbx())) {
			sql += "AND SDA.IBX =(:ibx)";	
			params.addValue("ibx", searchFormFilter.getIbx());
		
		}
		return namedJdbc.query(sql,params, new ErrorCodeMapper());
		*/
		
		return jdbc.query(ProNativeQueries.ACTIVE_ERROR_LIST, new ErrorCodeMapper());
	}
	
	@Override
	public List<SnapshotErrorData> getErrorSectionJdbc(String sblQuery) {
	
		return jdbc.query(sblQuery, new SnapshotErrorDataMapper());
	}
	
	@Override
	public List<String> getAllProduct() {
		return (List<String>)jdbc.query(ProNativeQueries.PRODUCT_LIST, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("PRODUCT_NAME");
			}
		});
	}
	
}
