package com.equinix.appops.dart.portal.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
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
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.constant.DBNativeQueries;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.constant.ProNativeQueries;
import com.equinix.appops.dart.portal.dao.AppOpsDartDaDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartEditDfrDao;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.AssignGroup;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.CxiErrorTbl;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrLineIdsVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.mapper.pro.AssetNewValMapper;
import com.equinix.appops.dart.portal.mapper.pro.ChangeSummaryMapper;
import com.equinix.appops.dart.portal.mapper.pro.ClxSnapshotMapper;
import com.equinix.appops.dart.portal.mapper.pro.DfrLineIdsMapper;
import com.equinix.appops.dart.portal.mapper.pro.DfrNotesMapper;
import com.equinix.appops.dart.portal.mapper.pro.ErrorCardMapper;
import com.equinix.appops.dart.portal.mapper.pro.ErrorCodeMapper;
import com.equinix.appops.dart.portal.mapper.pro.POECountMapper;
import com.equinix.appops.dart.portal.mapper.pro.SblSnapshotMapper;
import com.equinix.appops.dart.portal.mapper.pro.SvSnapshotMapper;
import com.equinix.appops.dart.portal.model.errorsection.SnapshotErrorData;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.SnapshotProductFilterResult;
import com.equinix.appops.dart.portal.util.DartUtil;
import com.equinix.appops.dart.portal.vo.ChangeSummaryDTO;
import com.equinix.appops.dart.portal.vo.ChangeSummaryDTOMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

@Repository
@Transactional
public class AppOpsDartEditDfrDaoImpl implements AppOpsDartEditDfrDao {

	Logger logger = LoggerFactory.getLogger(AppOpsDartEditDfrDaoImpl.class); 
	List<String>globalFilters= Arrays.asList( new String[]{"header8","header6","header16", "header2", "header7","attr253"});

	public static final String DEL_SNAPSHOT_SIEBEL_BY_DFRLINEID_Q = "DELETE from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where DFR_LINE_ID in (:dfrLineIds)";
	public static final String DEL_ASSET_NEW_VAL_DFRLINEID_Q = "DELETE from  EQX_DART.ASSET_NEW_VAL where DFR_LINE_ID in (:dfrLineIds)";
	public static final String DEL_CXI_ERROR_TBL_BY_DFRLINEID_Q = "DELETE from  EQX_DART.CXI_ERROR_TBL where SBL_DFR_LINE_ID in (:dfrLineIds)";	    	    	    	    
	

	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	AppOpsDartDaDao daDao;
	
	@Autowired
    JdbcTemplate jdbc;
	
	@Autowired
	NamedParameterJdbcTemplate namedJdbc; 
	
	@Override
	public List<SnapshotSiebelAssetDa> getSnapshotSiebleAssetDaData(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SnapshotSiebelAssetDa> siebelAssetDaList=new ArrayList<>();
		Criteria criteria=session.createCriteria(SnapshotSiebelAssetDa.class);
		//getCriteriaForDa(productFilter, criteria,false);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq(DartConstants.DFRID, productFilter.getDfrId()));
		conjunction.add(Restrictions.eq(DartConstants.HEADER_57, "Y"));
		criteria.add(conjunction);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
		  while (scrollableResults.next()) {
			  SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
		   siebelAssetDaList.add(siebelAssetDa);
		   /*session.evict(siebelAssetDa);*/
		  }
		return siebelAssetDaList;
	}
	
	@Override
	public List<SnapshotClxAssetDa> getSnapshotClxAssetDaData(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SnapshotClxAssetDa> clxAssetDaList = new ArrayList<>();
		Criteria criteria = session.createCriteria(SnapshotClxAssetDa.class);
		getCriteriaForDa(productFilter, criteria,false);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa)scrollableResults.get()[0];
			clxAssetDaList.add(clxAssetDa);
			/*session.evict(clxAssetDa);*/
		}
		return clxAssetDaList;
	}

	

	@Override
	public List<SnapshotSvAssetDa> getSnapshotSvAssetDaData(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SnapshotSvAssetDa> svAssetDaList  = new ArrayList<>();
		Criteria criteria = session.createCriteria(SnapshotSvAssetDa.class);
		getCriteriaForDa(productFilter, criteria,false);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SnapshotSvAssetDa svAssetDa= (SnapshotSvAssetDa)scrollableResults.get()[0];
			svAssetDaList.add(svAssetDa);
			/*session.evict(svAssetDa);*/
		}
		return svAssetDaList;
	}
	
	
	@Override
	public List<SnapshotSiebelAssetDa> getSiebelAssetDaDataByProduct(ProductFilter productFilter, String product) {
		Session session =   sessionFactory.getCurrentSession();
		List<SnapshotSiebelAssetDa> sblAssetDaList  = new ArrayList<>();
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
		/*if(StringUtils.isNotEmpty(productFilter.getKeyword())){
			getCriteria(productFilter, criteria, globalFilters); 
		}*/
		getCriteria(productFilter, criteria,product);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SnapshotSiebelAssetDa svAssetDa= (SnapshotSiebelAssetDa)scrollableResults.get()[0];
			sblAssetDaList.add(svAssetDa);
			/*session.evict(svAssetDa);*/
		}
		return sblAssetDaList;
	}
	
	
	public List<SnapshotSiebelAssetDa> getSiebleAssetDaDataIgnoreProductFilter(ProductFilter productFilter) {
		Session session = sessionFactory.getCurrentSession();
		List<SnapshotSiebelAssetDa> siebelAssetDaList = new ArrayList<>();
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			getCriteriaForErrorCodeGlobalFilterKeyword(productFilter, criteria, Boolean.TRUE);
		} else {
			getCriteriaForHierarchyDa(productFilter, criteria, true);
		}*/
		getCriteriaForHierarchyDa(productFilter, criteria, true);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
			siebelAssetDaList.add(siebelAssetDa);
		}
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			siebelAssetDaList = filterAssetForErrorCodeGlobalKeyword(siebelAssetDaList, productFilter);
		}*/
		return siebelAssetDaList;
	}
	
	
	@Override
	public List<SnapshotSiebelAssetDa> getHierarchyView(ProductFilter productFilter){
		return getSiebleAssetDaDataIgnoreProductFilter(productFilter);
	}	
	
	
	private void getCriteria(ProductFilter productFilter, Criteria criteria, String product) {
		Conjunction conjunction = Restrictions.conjunction();
		List<PFilter> filters = productFilter.getFilters();
		/*PFilter filter = null;
		Criterion criterion;
		for (int fcount = 0; fcount < filters.size(); fcount++) {
			filter = filters.get(fcount);
			String headerOrAttr = filter.getKey();

			if (headerOrAttr.equalsIgnoreCase("header20")) {
				criterion = Restrictions.eq(headerOrAttr, product);
				conjunction.add(criterion);
			} else {
				if (StringUtils.isNotEmpty(headerOrAttr) && !headerOrAttr.toLowerCase().contains("error")) {
					if (CollectionUtils.isNotEmpty(filter.getListOfValues())) {
						criterion = Restrictions.in(headerOrAttr, filter.getListOfValues().toArray());
						conjunction.add(criterion);
					}
				}
			}
		}*/
		conjunction.add(Restrictions.eq(DartConstants.DFRID, productFilter.getDfrId()));
		conjunction.add(Restrictions.eq(DartConstants.HEADER_57, "Y"));
		criteria.add(conjunction);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFilterListFromSnapshotDA(String header,String dfrId) {
		Session session =   sessionFactory.getCurrentSession();
		ProjectionList proList = Projections.projectionList();
	    proList.add(Projections.property(header.toLowerCase()));
	    Conjunction  conjunction = Restrictions.conjunction();
	    conjunction.add(Restrictions.eq(DartConstants.DFRID, dfrId));
		conjunction.add(Restrictions.eq(DartConstants.HEADER_57, "Y"));    
		Criteria crit = session.createCriteria(SnapshotSiebelAssetDa.class).add(conjunction)
				.setProjection(Projections.distinct(proList)).setReadOnly(true);
		List<String> resultList = (List<String>)crit.list();
		if(header.equalsIgnoreCase("header7")){
			return DartUtil.senitizeOutputList(resultList);
		}
		return resultList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFilterListFromErrorSnapshotError(String dfrId) {
		Session session =   sessionFactory.getCurrentSession();
		ProjectionList proList = Projections.projectionList();
	    proList.add(Projections.property("errorCode"));
		Criteria crit = session.createCriteria(CxiErrorTbl.class).add(Restrictions.eq(DartConstants.DFRID, dfrId))
				.setProjection(Projections.distinct(proList)).setReadOnly(true); 
		return (List<String>)crit.list();
	}
	
	@Override
	public HashMap<String, Object> getProductFilters(ProductFilter productFilter) {
		Session session =   sessionFactory.getCurrentSession();
		List<SnapshotSiebelAssetDa> sblList = new ArrayList<>();
		Criteria sblCriteria = session.createCriteria(SnapshotSiebelAssetDa.class);
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			getCriteriaForErrorCodeGlobalFilterKeyword(productFilter, sblCriteria, Boolean.FALSE);
		} else {
			getCriteriaForDa(productFilter, sblCriteria, false);
		}*/
		getCriteriaForDa(productFilter, sblCriteria, false);
		ScrollableResults scrollableResults = sblCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		/*boolean isErrorCodeInFilter = false;
		List<PFilter> filters = productFilter.getFilters();
		List<String> errorCodes = new ArrayList<String>();
		PFilter filter = null ;
		for(int fcount= 0 ; fcount< filters.size() ; fcount++){
			filter = filters.get(fcount);
			String key = filter.getKey();
			if(StringUtils.isNotEmpty(key) && key.toLowerCase().contains("error"))
			{
				errorCodes = filter.getListOfValues();
				if(errorCodes.size()>0){
					isErrorCodeInFilter = true;	
					break;
				}
			}
		}
		while(scrollableResults.next()){
			SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
			if(isErrorCodeInFilter){
				for(CxiErrorTbl error :siebelAssetDa.getCxiErrorTbls() ){
					if(errorCodes.contains(error.getErrorCode())){
						sblList.add(siebelAssetDa);
					}
				}
			}else{
				sblList.add(siebelAssetDa);
			}
		}*/
		/*List<String> validSblRowIdList = new ArrayList<String>();
		if(isErrorCodeInFilter){
			List<String> sblRowIdList = new ArrayList<String>();
			List<SnapshotSiebelAssetDa> sblListTemp = new ArrayList<>();
			while(scrollableResults.next()){
				SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
				sblRowIdList.add(siebelAssetDa.getHeader1());
				sblListTemp.add(siebelAssetDa);
			}
			
			if(null!=sblListTemp && sblListTemp.size()>0){
				validSblRowIdList = getSBLErrorCodeList(sblRowIdList,errorCodes);
				for(int i=0;i<sblListTemp.size();i++){
					if(validSblRowIdList.contains(sblListTemp.get(i).getHeader1())){
						sblList.add(sblListTemp.get(i));
					}
				}
			}
		}else{
			while(scrollableResults.next()){
				SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
				//	SiebelAssetDa siebelAssetDa = new SiebelAssetDa( scrollableResults.get());
					sblList.add(siebelAssetDa);
			}
		}*/
		while (scrollableResults.next()) {
			SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
			sblList.add(siebelAssetDa);
		}
		HashMap<String,Object> resultMap = new HashMap<>();
		resultMap.put(DartConstants.SBL_LIST ,sblList);
		return resultMap;
	}
	
	@Override
	public List<SnapshotSiebelAssetDa> globalSearch(ProductFilter filter){
	//	filter.setFilters(null);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
		/*if (filter.isErrorCodeGlobalFilterKeyword()) {
			getCriteriaForErrorCodeGlobalFilterKeyword(filter, criteria, Boolean.FALSE);
		} else {
			getCriteriaForDa(filter, criteria, false);
		}*/
		getCriteriaForDa(filter, criteria, false);
		List<SnapshotSiebelAssetDa> siebelAssetDaList = new ArrayList<>();
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
		while (scrollableResults.next()) {
			SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
			siebelAssetDaList.add(siebelAssetDa);
		}
		/*if (filter.isErrorCodeGlobalFilterKeyword()) {
			siebelAssetDaList = filterAssetForErrorCodeGlobalKeyword(siebelAssetDaList, filter);
		}*/
		return siebelAssetDaList;
	}
	
	// hirarchy filter 
	private void getCriteriaForHierarchyDa(ProductFilter productFilter, Criteria criteria,
			boolean ignoreProductFilter) {
		/*if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters() == null) {
			logger.info("Global Search Columns " + globalFilters.toString());
			getCriteria(productFilter, criteria, globalFilters);
		} else if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters() != null) {
			logger.info("Filter + Global Search Columns " + globalFilters.toString());
			getCriteria(productFilter, criteria, globalFilters);
			getCriteria(productFilter, criteria, ignoreProductFilter);
		} else if (StringUtils.isEmpty(productFilter.getKeyword())) {
			logger.info("Filter Search Columns " + productFilter.toString());
			getCriteria(productFilter, criteria, ignoreProductFilter);
		}*/
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq(DartConstants.DFRID, productFilter.getDfrId()));
		criteria.add(conjunction);
	}
	
	
	
	private void getCriteriaForDa(ProductFilter productFilter, Criteria criteria, boolean ignoreProductFilter) {
		/*if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters() == null) {
			logger.info("Global Search Columns " + globalFilters.toString());
			getCriteria(productFilter, criteria, globalFilters);
		} else if (StringUtils.isNotEmpty(productFilter.getKeyword()) && productFilter.getFilters() != null) {
			logger.info("Filter + Global Search Columns " + globalFilters.toString());
			getCriteria(productFilter, criteria, globalFilters);
			getCriteria(productFilter, criteria, ignoreProductFilter);
		} else if (StringUtils.isEmpty(productFilter.getKeyword())) {
			logger.info("Filter Search Columns " + productFilter.toString());
			getCriteria(productFilter, criteria, ignoreProductFilter);
		}*/
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq(DartConstants.DFRID, productFilter.getDfrId()));
		conjunction.add(Restrictions.eq(DartConstants.HEADER_57, "Y"));
		criteria.add(conjunction);
	}
	
	private void getCriteria(ProductFilter productFilter, Criteria criteria, List<String> filters) {
		Disjunction disjunction = Restrictions.disjunction();
		for(String filterName : filters){
			  	 if(!filterName.toLowerCase().contains("error")){
				 Criterion criterion = Restrictions.ilike(filterName, productFilter.getKeyword(), MatchMode.ANYWHERE);
				 disjunction.add(criterion);
			  	 }
		}
		criteria.add(disjunction);
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
			if(StringUtils.isNotEmpty(headerOrAttr)&& !headerOrAttr.toLowerCase().contains("error")){
				if(isIgnoreProductFilter){
					if(!headerOrAttr.equalsIgnoreCase("header20") && CollectionUtils.isNotEmpty(filter.getListOfValues())) {
						Criterion criterion = Restrictions.in(headerOrAttr, filter.getListOfValues().toArray());
						conjunction.add(criterion);
					}
				}else{
					if( CollectionUtils.isNotEmpty(filter.getListOfValues())) {
						Criterion criterion = Restrictions.in(headerOrAttr, filter.getListOfValues().toArray());
						conjunction.add(criterion);
					}
				}
			}
		}
		criteria.add(conjunction);
	}
	
	
/*	@Override
	public List<SnapshotErrorData> getErrorSection(ProductFilter productFilter){
		List<SnapshotErrorData> errorList = new ArrayList<>();
		Gson gson = new Gson();
		logger.info("Error Filter  : " + gson.toJson(productFilter, ProductFilter.class));
		String sblQuery =getErrorQuery(productFilter, filterTemplateSbl, globalTemplateSbl, SBL_ERR_Q).replace("#id#", productFilter.getDfrId());
		String clxQuery = getErrorQuery(productFilter, filterTemplateClx, globalTemplateClx, CLX_ERR_Q).replace("#id#", productFilter.getDfrId());
		String svQuery = getErrorQuery(productFilter, filterTemplateSv, globalTemplateSv, SV_ERR_Q).replace("#id#", productFilter.getDfrId());
		
		logger.info("SBL Error Query : " + sblQuery);
		logger.info("CLX Error Query : " + clxQuery);
		logger.info("SV Error Query : " + svQuery);
		
		List<Object> list1 = daDao.executeSQL(sblQuery);
		List<Object> list2 = daDao.executeSQL(clxQuery);
		List<Object> list3 = daDao.executeSQL(svQuery);
		
		if(CollectionUtils.isNotEmpty(list1) || CollectionUtils.isNotEmpty(list2)){
			list1.addAll(list2);
		}
		if(CollectionUtils.isNotEmpty(list1) || CollectionUtils.isNotEmpty(list3)){
			list1.addAll(list3);
		}
		if(CollectionUtils.isNotEmpty(list1)){
			for(Object object : list1){
				Map<String,String> value = (Map<String,String>)object;
				errorList.add(new SnapshotErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
						value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING"),value.get("STATUS_CD"), value.get("VALID_STAT")));
			}
		}
		return errorList;
	}*/
	
	/*@Override
	public List<SnapshotErrorData> getErrorSection(ProductFilter productFilter){
		List<SnapshotErrorData> errorList = new ArrayList<>();
		Gson gson = new Gson();
		logger.info("Error Filter  : " + gson.toJson(productFilter, ProductFilter.class));
	//	String sblQuery =getErrorQuery(productFilter, filterTemplateSbl, globalTemplateSbl,errorTemplate, SBL_ERR_Q).replace("#id#", productFilter.getDfrId());
		
		
		String sblQuery =getErrorQuery(productFilter, filterTemplateSbl, globalTemplateSbl,errorTemplate ,SBL_ERR_Q).replace("#id#", productFilter.getDfrId());
		String clxQuery = getErrorQuery(productFilter, filterTemplateClx, globalTemplateClx,errorTemplate ,CLX_ERR_Q).replace("#id#", productFilter.getDfrId());
		String svQuery = getErrorQuery(productFilter, filterTemplateSv, globalTemplateSv,errorTemplate ,SV_ERR_Q).replace("#id#", productFilter.getDfrId());
		
		logger.info("SBL Error Query : " + sblQuery);
		logger.info("CLX Error Query : " + clxQuery);
		logger.info("SV Error Query : " + svQuery);
		
		List<Object> list1 = daDao.executeSQL(sblQuery);
		List<Object> list2 = daDao.executeSQL(clxQuery);
		List<Object> list3 = daDao.executeSQL(svQuery);
		
		if(CollectionUtils.isNotEmpty(list1) || CollectionUtils.isNotEmpty(list2)){
			list1.addAll(list2);
		}
		if(CollectionUtils.isNotEmpty(list1) || CollectionUtils.isNotEmpty(list3)){
			list1.addAll(list3);
		}
		if(CollectionUtils.isNotEmpty(list1)){
			for(Object object : list1){
				Map<String,String> value = (Map<String,String>)object;
				errorList.add(new SnapshotErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
						value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING"),value.get("STATUS_CD"), value.get("VALID_STAT")));
			}
		}
		return errorList;
	}*/
	
	public static String CLX_CAGE_ERR_Q =  " SELECT 'CLX' TBL ,  CD.ROW_ID, CD.ASSET_NUM,  ER.ERROR_CODE, MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING , ER.STATUS_CD, ER.VALID_STAT,MER.ALERT_FLAG"
			+" FROM EQX_DART.CXI_ERROR_TBL ER "
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER ON ER.ERROR_CODE = MER.ERROR_CODE "
			+" INNER JOIN EQX_DART.SNAPSHOT_CLX_ASSET_DA CD ON CD.DFR_LINE_ID = ER.CLX_DFR_LINE_ID WHERE  "
			+" (CD.ASSET_NUM IS NOT NULL AND CD.DFR_ID='#id#')"
			+ " AND lower(CD.NAME)= 'cage'"
			+ " AND CD.CAGE_UNIQUE_SPACE_ID in (:list1)";
	
	public static String CLX_CAB_ERR_Q =  " SELECT 'CLX' TBL ,  CD.ROW_ID, CD.ASSET_NUM,  ER.ERROR_CODE, MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING , ER.STATUS_CD, ER.VALID_STAT ,MER.ALERT_FLAG"
			+" FROM EQX_DART.CXI_ERROR_TBL ER "
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER ON ER.ERROR_CODE = MER.ERROR_CODE "
			+" INNER JOIN EQX_DART.SNAPSHOT_CLX_ASSET_DA CD ON CD.DFR_LINE_ID = ER.CLX_DFR_LINE_ID WHERE "
			+" (CD.ASSET_NUM IS NOT NULL AND CD.DFR_ID='#id#')"
			+ " AND lower(CD.NAME)= 'cabinet'"
			+ " AND CD.CAB_UNIQUE_SPACE_ID in (:list1)";
	
	public static String CLX_CAB_DP_ERR_Q =  " SELECT 'CLX' TBL ,  CD.ROW_ID, CD.ASSET_NUM,  ER.ERROR_CODE, MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING , ER.STATUS_CD, ER.VALID_STAT,MER.ALERT_FLAG"
			+" FROM EQX_DART.CXI_ERROR_TBL ER "
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER ON ER.ERROR_CODE = MER.ERROR_CODE "
			+" INNER JOIN EQX_DART.SNAPSHOT_CLX_ASSET_DA CD ON CD.DFR_LINE_ID = ER.CLX_DFR_LINE_ID WHERE "
			+" (CD.ASSET_NUM IS NOT NULL AND CD.DFR_ID='#id#')"
			+ " AND lower(CD.NAME)= 'demarcation point'"
			+ " AND CD.CAB_UNIQUE_SPACE_ID in (:list1)";
	
	@Override
	public List<SnapshotErrorData> getErrorSection(ProductFilter productFilter){
		List<SnapshotErrorData> errorList = new ArrayList<>();
		Gson gson = new Gson();
		logger.info("Error Filter  : " + gson.toJson(productFilter, ProductFilter.class));
		String sblQuery = null;
		if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			sblQuery = getErrorQueryForErrorCodeGlobalKeyword(productFilter, filterTemplateSbl, errorTemplate, SBL_ERR_Q).replace("#id#", productFilter.getDfrId());
		} else {
			//sblQuery = getErrorQuery(productFilter, filterTemplateSbl, globalTemplateSbl,errorTemplate, SBL_ERR_Q).replace("#id#", productFilter.getDfrId());
	                 sblQuery =SBL_ERR_Q.replace("#id#", productFilter.getDfrId());
		
                }
		logger.info("SBL Error Query : " + sblQuery);
		List<Object> sblList = daDao.executeSQL(sblQuery);
		List<Object> clxList = new ArrayList<>();
		List<Object> svList = new ArrayList<>();
		List<Object> clxSvList = new ArrayList<>();
	
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		for(Object sbl :sblList ){
			Map<String,String> value = (Map<String,String>)sbl;
			
			if(value.get("ASSET_NUM") == null){
				AssetNewVal assetnewVal = getAssetNewValByDfrLineId(value.get("ROW_ID"));
				value.put("ASSET_NUM", assetnewVal.getHeader2());
			}
			
			errorList.add(new SnapshotErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
					value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING"),value.get("STATUS_CD"), value.get("VALID_STAT"), value.get("ALERT_FLAG")));
			
			assetSet.add(value.get("ASSET_NUM"));
			if("Cage".equalsIgnoreCase(value.get("NAME")) && null!=value.get("CAGE_UNIQUE_SPACE_ID")){
				cageUniquesSpaceId.add(value.get("CAGE_UNIQUE_SPACE_ID"));
			}else if("Cabinet".equalsIgnoreCase(value.get("NAME")) && null!=value.get("CAB_UNIQUE_SPACE_ID")){
				cabUniqueSpaceId.add(value.get("CAB_UNIQUE_SPACE_ID"));
			}else if("Demarcation Point".equalsIgnoreCase(value.get("NAME")) && null!=value.get("CAB_UNIQUE_SPACE_ID")){
				cabDpUniqueSpaceId.add(value.get("CAB_UNIQUE_SPACE_ID"));
			}
		}
		 
		 if(CollectionUtils.isNotEmpty(cageUniquesSpaceId)){
			 clxList = daDao.executeParamListSQL(CLX_CAGE_ERR_Q.replace("#id#", productFilter.getDfrId()), cageUniquesSpaceId);
			 logger.info("CLX Cage Error Query : " + CLX_CAGE_ERR_Q);
		 }
		 
		 if(CollectionUtils.isNotEmpty(cabUniqueSpaceId)){
			 clxList = daDao.executeParamListSQL(CLX_CAB_ERR_Q.replace("#id#", productFilter.getDfrId()), cabUniqueSpaceId);
			 logger.info("CLX Cab Error Query : " + CLX_CAB_ERR_Q);
		 }
		 
		 if(CollectionUtils.isNotEmpty(cabDpUniqueSpaceId)){
			 clxList = daDao.executeParamListSQL(CLX_CAB_DP_ERR_Q.replace("#id#", productFilter.getDfrId()), cabDpUniqueSpaceId);
			 logger.info("CLX Cab dp Error Query : " + CLX_CAB_DP_ERR_Q);
		 }
		 if(CollectionUtils.isNotEmpty( assetSet)){
			 svList = daDao.executeParamListSQL(SV_ERR_Q.replace("#id#", productFilter.getDfrId()),assetSet);
			 logger.info("SV Error Query : " + SV_ERR_Q);
		 }
		 
		if(CollectionUtils.isNotEmpty(sblList) && CollectionUtils.isNotEmpty(clxList)){
			clxSvList.addAll(clxList);
		}
		if(CollectionUtils.isNotEmpty(sblList) && CollectionUtils.isNotEmpty(svList)){
			clxSvList.addAll(svList);
		}
		
		if(CollectionUtils.isNotEmpty(clxSvList)){
			for(Object objectList : clxSvList){
				List<Object> object = (List<Object>)objectList;
				for(Object objectMap :object){
					Map<String,String> value = (Map<String,String>)objectMap;
					errorList.add(new SnapshotErrorData(value.get("TBL"), value.get("ROW_ID"), value.get("ASSET_NUM"),
							value.get("ERROR_CODE"), value.get("ERROR_NAME"), value.get("VALIDATION_CLASS"), value.get("OWNER_OF_FIXING"),value.get("STATUS_CD"), value.get("VALID_STAT"),value.get("ALERT_FLAG")));
				}
				
			}
		}
		return errorList;
	}
	
	@Override
	public List<SnapshotErrorData> getErrorSectionJdbc(ProductFilter productFilter) {
		Gson gson = new Gson();
		logger.info("Error Filter  : " + gson.toJson(productFilter, ProductFilter.class));
		String sblQuery = null;
		/*if (productFilter.isErrorCodeGlobalFilterKeyword()) {
			sblQuery = getErrorQueryForErrorCodeGlobalKeyword(productFilter, filterTemplateSbl, errorTemplate,
					ProNativeQueries.SBL_ERR_Q).replace("#id#", productFilter.getDfrId());
		} else {*/
			/*sblQuery = getErrorQueryForErrorCodeGlobalKeyword(productFilter, filterTemplateSbl, errorTemplate,
					ProNativeQueries.SBL_ERR_Q).replace("#id#", productFilter.getDfrId());*/
			sblQuery = ProNativeQueries.SBL_ERR_Q.replace("#id#", productFilter.getDfrId());
			
		//}
		if(StringUtils.isNotBlank(productFilter.getKpiFilterType())&& StringUtils.isNotEmpty(productFilter.getKpiFilterType()) && productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.CM_KPI_FILTER)){
			sblQuery += ProNativeQueries.APPLY_CHANGES_MADE_FILTER;
		}
		if(StringUtils.isNotBlank(productFilter.getKpiFilterType())&& StringUtils.isNotEmpty(productFilter.getKpiFilterType())){
			if(StringUtils.isNotBlank(productFilter.getKpiFilterType())&& StringUtils.isNotEmpty(productFilter.getKpiFilterType()) && productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.NE_KPI_FILTER)){
				sblQuery += ProNativeQueries.APPLY_ERR_NOT_LIKE_CONDITION;
			}else{
				sblQuery += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
			}
		}
		logger.info("SBL Error Query : " + sblQuery);
		List<SnapshotErrorData> errorList = null;
		errorList = daDao.getErrorSectionJdbc(sblQuery);
		return errorList;
	}
	
	public static String SBL_ERR_Q = "  SELECT 'SBL' TBL ,  SD.ROW_ID , SD.ASSET_NUM, SD.NAME,  SD.CAGE_UNIQUE_SPACE_ID, SD.CAB_UNIQUE_SPACE_ID, ERR.ERROR_CODE,  MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING , ERR.STATUS_CD, ERR.VALID_STAT, MER.ALERT_FLAG "
			+ "  FROM EQX_DART.CXI_ERROR_TBL ERR "
			+ "  INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER  ON ERR.ERROR_CODE = MER.ERROR_CODE"
			+ "  INNER JOIN EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SD  ON SD.DFR_LINE_ID = ERR.SBL_DFR_LINE_ID"
		//	+ "  WHERE  (SD.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND SD.DFR_ID='#id#' AND SD.HEADER_01 = 'Y')";
		//	+ "  WHERE  (SD.ASSET_NUM IS NOT NULL AND ER.ERROR_END_DATE IS NULL AND SD.DFR_ID='#id#')";
			+ "  WHERE  ((SD.ASSET_NUM IS NOT NULL or ((select AN.ASSET_NUM from EQX_DART.ASSET_NEW_VAL AN where AN.DFR_LINE_ID=SD.DFR_LINE_ID) is NOT NULL ))"
			+ "  AND SD.DFR_ID='#id#' AND SD.HEADER_01 = 'Y')";
	
	public static final String errorTemplate = "ERR.#fname# IN (#fvalue#)";
	
	public static final String filterTemplateSbl = "SD.#fname# IN (#fvalue#)";
	
	public static final String globalTemplateSbl = "(lower(SD.IBX) LIKE '%#keyword#%'" 
					+" OR lower(SD.OU_NUM) LIKE '%#keyword#%'" 
					+" OR lower(SD.SYSTEM_NAME) LIKE '%#keyword#%'" 
					+" OR lower(SD.ASSET_NUM) LIKE '%#keyword#%'" 
					+" OR lower(SD.ACCOUNT_NAME) LIKE '%#keyword#%')";
	
	public static String CLX_ERR_Q =  " SELECT 'CLX' TBL ,  CD.ROW_ID, CD.ASSET_NUM,  ERR.ERROR_CODE, MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING , ERR.STATUS_CD, ERR.VALID_STAT "
			+" FROM EQX_DART.CXI_ERROR_TBL ERR "
			+" INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER ON ERR.ERROR_CODE = MER.ERROR_CODE "
			+" INNER JOIN EQX_DART.SNAPSHOT_CLX_ASSET_DA CD ON CD.DFR_LINE_ID = ERR.CLX_DFR_LINE_ID "
			+ "  WHERE  ((CD.ASSET_NUM IS NOT NULL or ((select AN.ASSET_NUM from EQX_DART.ASSET_NEW_VAL AN where AN.DFR_LINE_ID=CD.DFR_LINE_ID) is NOT NULL ))"
			+" AND CD.DFR_ID='#id#')";

	public static final String filterTemplateClx = "CD.#fname# IN (#fvalue#)";
	
	public static final String globalTemplateClx = "(lower(CD.IBX) LIKE '%#keyword#%'" 
			+" OR lower(CD.OU_NUM) LIKE '%#keyword#%'" 
			+" OR lower(CD.SYSTEM_NAME) LIKE '%#keyword#%'" 
			+" OR lower(CD.ASSET_NUM) LIKE '%#keyword#%'" 
			+" OR lower(CD.ACCOUNT_NAME) LIKE '%#keyword#%')";
	
	public static final String SV_ERR_Q = "SELECT 'SV' TBL ,"
			  +"  SV.ROW_ID,"
			  +"  SV.ASSET_NUM,"
			  +"  ERR.ERROR_CODE,"
			  +"  MER.ERROR_NAME,"
			  +"  MER.VALIDATION_CLASS,"
			  +"  MER.OWNER_OF_FIXING,"
			  +"  ERR.STATUS_CD, ERR.VALID_STAT "
			  +"  FROM EQX_DART.CXI_ERROR_TBL ERR"
			  +"  INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			  +"  ON ER.ERROR_CODE = MER.ERROR_CODE"
			  +"  INNER JOIN EQX_DART.SNAPSHOT_SV_ASSET_DA SV"
			  +"  ON SV.DFR_LINE_ID = ERR.SV_DFR_LINE_ID "
			  +"  WHERE  ((SV.ASSET_NUM IS NOT NULL or ((select AN.ASSET_NUM from EQX_DART.ASSET_NEW_VAL AN where AN.DFR_LINE_ID=SV.DFR_LINE_ID) is NOT NULL ))"
			  +"  AND SV.DFR_ID='#id#')"
			  + " AND SV.ASSET_NUM in (:list1) ";
	
	public static final String filterTemplateSv = "SV.#fname# IN (#fvalue#)";
	
	public static final String globalTemplateSv = "(lower(SV.IBX) LIKE '%#keyword#%'" 
					+" OR lower(SV.OU_NUM) LIKE '%#keyword#%'" 
					+" OR lower(SV.SYSTEM_NAME) LIKE '%#keyword#%'" 
					+" OR lower(SV.ASSET_NUM) LIKE '%#keyword#%'" 
					+" OR lower(SV.ACCOUNT_NAME) LIKE '%#keyword#%')";
	
	private String  getErrorQuery(ProductFilter filters, String filterTemplate, String globalTemplate, String errorTemplate, String SQL){
		String  conditionTemplate = " AND (#filters#) ";
		
		if(StringUtils.isNotEmpty(filters.getKeyword())){
			String condition1 = getGlobalFilterCondition(filters, filters.getKeyword().toLowerCase(), globalTemplate);
			String condition2 = getFilterCondition(filters, filterTemplate, errorTemplate);
			if(StringUtils.isNotBlank(condition2)){
				SQL = SQL + conditionTemplate.replace("#filters#", condition2);
			}
			if(StringUtils.isNotBlank(condition1)){
				SQL = SQL +conditionTemplate.replace("#filters#", condition1);
			}
		}else{
			String condition2 = getFilterCondition(filters, filterTemplate, errorTemplate);
			if(StringUtils.isNotBlank(condition2)){
				SQL = SQL + conditionTemplate.replace("#filters#", condition2);
			}
		}
		
		
		return SQL;
	}
	
	private  String getGlobalFilterCondition(ProductFilter filters, String keyword, String filterTemplate){
		if(StringUtils.isNotEmpty(filters.getKeyword())){
			return filterTemplate.replace("#keyword#", keyword);
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
			} else if(filter.getKey().equalsIgnoreCase("header6")&& StringUtils.isNotBlank(filter.getValue())){
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "OU_NUM");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			} else if(filter.getKey().equalsIgnoreCase("header20")&& StringUtils.isNotBlank(filter.getValue())){
				String fval ="";
				fval = getFilterValues(filter, filter.getValue());
				String f = fiterTemplate.replace("#fname#", "NAME");
				f= f.replace("#fvalue#", removeLastChar(fval));
				filterList.add(f);
			} else if(filter.getKey().equalsIgnoreCase("header16")&& StringUtils.isNotBlank(filter.getValue())){
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

	 // added in aug 9 release for trims values 
         private String getFilterValues(PFilter filter, String fval) {
		String value ="";
		if(StringUtils.isNotEmpty(fval)){
			for(String filterVal :filter.getValue().split(",")){
				if(StringUtils.isNotEmpty(filterVal)){
					filterVal = filterVal.trim();
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
	public List<AssetNewVal> getEditedAssets(String dfrId) {
		Session session =   sessionFactory.getCurrentSession();
		List<AssetNewVal> assetNewValList=new ArrayList<>();
		Criteria criteria=session.createCriteria(AssetNewVal.class)
				.add(Restrictions.eq("dfrId", dfrId))
				.add(Restrictions.eq("header57", "Y").ignoreCase());
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			AssetNewVal assetNewVal = (AssetNewVal) scrollableResults.get()[0];
			if(assetNewVal!=null){
				assetNewValList.add(assetNewVal);
				session.evict(assetNewVal);
			}
		}
		return assetNewValList;
	}

	@Override
	public List<AssetNewVal> getEditedAssets(String dfrId, String... assetNames) {
		Session session =   sessionFactory.getCurrentSession();
		List<AssetNewVal> siebelAssetDaList=new ArrayList<>();
		Criteria criteria=session.createCriteria(AssetNewVal.class)
				.add(Restrictions.eq("dfrId", dfrId))
				.add(Restrictions.in("header20", assetNames))
				.add(Restrictions.eq("header57", "Y").ignoreCase());
		
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			AssetNewVal assetNewVal = (AssetNewVal) scrollableResults.get()[0];
			if(assetNewVal!=null){
				siebelAssetDaList.add(assetNewVal);
				session.evict(assetNewVal);
			}
		}
		return siebelAssetDaList;
	}

	@Override
	public AssetNewVal getAssetNewValByDfrLineId(String dfrLineId) {
		Session session = sessionFactory.getCurrentSession();
		return (AssetNewVal)session.createCriteria(AssetNewVal.class).add(Restrictions.eq("dfrLineId", dfrLineId)).uniqueResult();
	}
	
	@Override
	public DfrMaster getDfrMaster(String dfrId,String fetchMode) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DfrMaster.class);
		if("join".equalsIgnoreCase(fetchMode)){
			criteria.setFetchMode("approvalHistories", FetchMode.JOIN);
		}else{
			criteria.setFetchMode("approvalHistories", FetchMode.SELECT);
		}
		criteria.add(Restrictions.eq("dfrId", dfrId));
		return (DfrMaster)criteria	.uniqueResult();
	}
	
	@Override
	public String getValidStatus(String dfrId) {
		Session session = sessionFactory.getCurrentSession();
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("validStatus"));
		Criteria crit = session.createCriteria(DfrMaster.class).add(Restrictions.eq("dfrId", dfrId))
				.setProjection(Projections.distinct(proList)).setReadOnly(true);
		String validStatus = (String) crit.uniqueResult();
		return validStatus;
	}


	@Override
	public String getAssignedGroupByReqionAndSystem(String region, String sot) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AssignGroup.class).setFetchMode("approvalHistories", FetchMode.SELECT);
		criteria.add(Restrictions.eq("region", region))
				.add(Restrictions.eq("system", sot))
				.setProjection(Projections.distinct(Projections.projectionList()
						.add(Projections.property("groupName"), "groupName")));
		return (String)criteria.uniqueResult();
	}

	@Override
	public SnapshotSiebelAssetDa getSnapshotSblAssetDaData(String dfrLineID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class).setFetchMode("cxiErrorTbls", FetchMode.SELECT);
		criteria.add(Restrictions.eq("dfrLineId", dfrLineID));
		return (SnapshotSiebelAssetDa)criteria.uniqueResult();
	
	}
	
	@Override
	public AssetNewVal getAssetNewVal(String dfrLineID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AssetNewVal.class);
		criteria.add(Restrictions.eq("dfrLineId", dfrLineID));
		return (AssetNewVal)criteria.uniqueResult();
	
	}
	
	
	
	private List<SnapshotSvAssetDa> getSvList(Set<String> assetList, String dfrId){
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(assetList)){
			if( assetList.size()>999){
				Lists.partition(Arrays.asList(assetList.toArray()), 999).stream().forEach(list->{
					Session session = sessionFactory.getCurrentSession();
					Criteria criteria = session.createCriteria(SnapshotSvAssetDa.class).setReadOnly(true);
					Criterion assetNumIn = Restrictions.in("header2", list.toArray());
					criteria.add(assetNumIn).add(Restrictions.and(Restrictions.eq("dfrId", dfrId)));
					ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
					while (scrollableResults.next()) {
						SnapshotSvAssetDa svAssetDa = (SnapshotSvAssetDa) scrollableResults.get()[0];
						svList.add(svAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				});
			}else{
				Session session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(SnapshotSvAssetDa.class).setReadOnly(true);
				Criterion assetNumIn = Restrictions.in("header2", assetList.toArray());
				criteria.add(assetNumIn).add(Restrictions.and(Restrictions.eq("dfrId", dfrId)));;
				ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
				while (scrollableResults.next()) {
					SnapshotSvAssetDa svAssetDa = (SnapshotSvAssetDa) scrollableResults.get()[0];
					svList.add(svAssetDa);
				   /*session.evict(siebelAssetDa);*/
				}
			}
			
			
			
		}
		return svList;
	}
	
	private List<SnapshotClxAssetDa> getClxList(Set<String> cageList ,Set<String> cabList,Set<String> cabDpList,  String dfrId ){
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		
		if(CollectionUtils.isNotEmpty(cageList)){
			if( cageList.size()>999){
				Lists.partition(Arrays.asList(cageList.toArray()), 999).stream().forEach(list->{
					Criteria cageCriteria = session.createCriteria(SnapshotClxAssetDa.class).setReadOnly(true);
					Criterion cageIn = Restrictions.in("header10", cageList.toArray());
					Criterion cageProduct = Restrictions.eq("header20", "Cage");
					Conjunction cageConjuncation = Restrictions.conjunction();
					cageConjuncation.add(cageIn);
					cageConjuncation.add(cageProduct);
					cageCriteria.add(cageConjuncation).add(Restrictions.and(Restrictions.eq("dfrId", dfrId)));;
					ScrollableResults scrollableResults = cageCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while (scrollableResults.next()) {
						  SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa) scrollableResults.get()[0];
						  clxList.add(clxAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				});
			}else{
				Criteria cageCriteria = session.createCriteria(SnapshotClxAssetDa.class).setReadOnly(true);
				Criterion cageIn = Restrictions.in("header10", cageList.toArray());
				Criterion cageProduct = Restrictions.eq("header20", "Cage");
				Conjunction cageConjuncation = Restrictions.conjunction();
				cageConjuncation.add(cageIn);
				cageConjuncation.add(cageProduct);
				cageCriteria.add(cageConjuncation).add(Restrictions.and(Restrictions.eq("dfrId", dfrId)));
				ScrollableResults scrollableResults = cageCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					  SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa) scrollableResults.get()[0];
					  clxList.add(clxAssetDa);
				   /*session.evict(siebelAssetDa);*/
				}
			
			}
		}
		if(CollectionUtils.isNotEmpty(cabList)){
			if( cabList.size()>999){
				Lists.partition(Arrays.asList(cabList.toArray()), 999).stream().forEach(list->{
					Conjunction cabinetConjuncation = Restrictions.conjunction();
					Criterion cabinetIn = Restrictions.in("header12", list.toArray());
					Criterion cabinetProduct = Restrictions.eq("header20", "Cabinet");
					cabinetConjuncation = Restrictions.conjunction();
					cabinetConjuncation.add(cabinetIn);
					cabinetConjuncation.add(cabinetProduct);
					Criteria cabinetCriteria = session.createCriteria(SnapshotClxAssetDa.class).setReadOnly(true);
					cabinetCriteria.add(cabinetConjuncation);
					ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
					while (scrollableResults.next()) {
						SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa) scrollableResults.get()[0];
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
				Criteria cabinetCriteria = session.createCriteria(SnapshotClxAssetDa.class).setReadOnly(true);
				cabinetCriteria.add(cabinetConjuncation);
				ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
				while (scrollableResults.next()) {
					SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa) scrollableResults.get()[0];
					clxList.add(clxAssetDa);
					/*session.evict(siebelAssetDa);*/
				}
			}
		}
		// dp
		if(CollectionUtils.isNotEmpty(cabDpList)){
			if( cabDpList.size()>999){
				Lists.partition(Arrays.asList(cabDpList.toArray()), 999).stream().forEach(list->{
					Conjunction cabinetConjuncation = Restrictions.conjunction();
					Criterion cabinetIn = Restrictions.in("header12", list.toArray());
					Criterion cabinetProduct = Restrictions.eq("header20", "Demarcation Point");
					cabinetConjuncation = Restrictions.conjunction();
					cabinetConjuncation.add(cabinetIn);
					cabinetConjuncation.add(cabinetProduct);
					Criteria cabinetCriteria = session.createCriteria(SnapshotClxAssetDa.class).setReadOnly(true);
					cabinetCriteria.add(cabinetConjuncation);
					ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
					while (scrollableResults.next()) {
						SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa) scrollableResults.get()[0];
						clxList.add(clxAssetDa);
						/*session.evict(siebelAssetDa);*/
					}
				});
			}else{
				Conjunction cabinetConjuncation = Restrictions.conjunction();
				Criterion cabinetIn = Restrictions.in("header12", cabDpList.toArray());
				Criterion cabinetProduct = Restrictions.eq("header20", "Demarcation Point");
				cabinetConjuncation = Restrictions.conjunction();
				cabinetConjuncation.add(cabinetIn);
				cabinetConjuncation.add(cabinetProduct);
				Criteria cabinetCriteria = session.createCriteria(SnapshotClxAssetDa.class).setReadOnly(true);
				cabinetCriteria.add(cabinetConjuncation);
				ScrollableResults scrollableResults = cabinetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
				while (scrollableResults.next()) {
					SnapshotClxAssetDa clxAssetDa = (SnapshotClxAssetDa) scrollableResults.get()[0];
					clxList.add(clxAssetDa);
					/*session.evict(siebelAssetDa);*/
				}
			}
		}
		
		//if(CollectionUtils.isNotEmpty(cabList)){}
		return clxList;
	}
	
	@Override
	public List<AssetNewVal> getAssetNewValBySblRowIds(Set<String> sblDfrLineSet){
		List<AssetNewVal> assetNewValList = new ArrayList<>();
		if(sblDfrLineSet.size()>999){
			Lists.partition(Arrays.asList(sblDfrLineSet.toArray()), 999).stream().forEach(list->{
				if(CollectionUtils.isNotEmpty(list)){
					Session session = sessionFactory.getCurrentSession();
					Criteria criteria = session.createCriteria(AssetNewVal.class).setReadOnly(true);
					Criterion assetNumIn = Restrictions.in("dfrLineId", list.toArray());
					criteria.add(assetNumIn);
					ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
					while (scrollableResults.next()) {
						AssetNewVal svAssetDa = (AssetNewVal) scrollableResults.get()[0];
						assetNewValList.add(svAssetDa);
					   /*session.evict(siebelAssetDa);*/
					}
				}
			});
		}else {
			if(CollectionUtils.isNotEmpty(sblDfrLineSet)){
				Session session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(AssetNewVal.class).setReadOnly(true);
				Criterion assetNumIn = Restrictions.in("dfrLineId", sblDfrLineSet.toArray());
				criteria.add(assetNumIn);
				ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);;
				while (scrollableResults.next()) {
					AssetNewVal assetNewVal = (AssetNewVal) scrollableResults.get()[0];
					assetNewValList.add(assetNewVal);
				   /*session.evict(siebelAssetDa);*/
				}
			}
		}
		return assetNewValList;
	}
	
	@Override
	// @Cacheable(value="dartDafilter",key
	// ="#productFilterResult.productFilter.cacheKey")
	public SnapshotProductFilterResult getProductFilterResult(SnapshotProductFilterResult productFilterResult) {
		String dfrId = productFilterResult.getProductFilter().getDfrId();
		String product =null;	
		ProductFilter productFilter = productFilterResult.getProductFilter();
		List<PFilter> pFilters = productFilter.getFilters();
		for(PFilter pFilter :pFilters ){
			if(pFilter.getKey().equalsIgnoreCase("header20")){
				product = pFilter.getValue();
			}
		}
//		List<SnapshotSiebelAssetDa> sblList = getSnapshotSiebleAssetDaData(productFilterResult.getProductFilter());
		/**
		 * Jdbc Template
		 */
		List<SnapshotSiebelAssetDa> sblList = null;
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
			sblList = getSnapshotSiebleAssetDaDataJdbc(productFilterResult.getProductFilter(),product);
			logger.info("end time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
		}else{
			sblList = getSnapshotPaginatedSiebleAssetDaData(productFilterResult.getProductFilter());
		}
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		Set<String> sblDfrLineSet = new HashSet<>();
		Map<String, List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();
		for (SnapshotSiebelAssetDa sbl : sblList) {
			assetSet.add(sbl.getHeader2());
			if (null != sbl.getHeader57() && sbl.getHeader57().equalsIgnoreCase("Y")) {
				sblDfrLineSet.add(sbl.getDfrLineId());
			}
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getClxListJdbc : "+ new Date());
			clxList = getClxListJdbc(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId,product);
			logger.info("end time of getClxListJdbc : "+ new Date());
		}else{
			clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId);
		}
//		
		/**
		 * Sv list by jdbc
		 */
		if (DartConstants.IS_JDBC_CALL) {
			logger.info("start time of getSvListJdbc : " + new Date());
			if (CollectionUtils.isNotEmpty(assetSet))
				svList = getSvListJdbc(assetSet, dfrId);
			logger.info("end time of getSvListJdbc : " + new Date());
		}else{
			svList = getSvList(assetSet, dfrId);
		}
		
		List<AssetNewVal> assetNewValList = null;
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
			assetNewValList = getAssetNewValBySblRowIdsJdbc(sblDfrLineSet);
			logger.info("end time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
		}else{
			assetNewValList = getAssetNewValBySblRowIds(sblDfrLineSet);
		}
		
		if (CollectionUtils.isNotEmpty(assetNewValList)) {
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k, v) -> {
				if (v.size() == 1) {
					assetNewValMap.put(k, v.get(0));
				}
			});
		}
		
		/*int count  = getSblCountByDfr(dfrId);
		productFilter.setTotalRows(count);*/
		productFilterResult.setAssetNewValMap(assetNewValMap);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		return productFilterResult;
	}
	
	private int getSblCountByDfr(String dfrId) {
		return jdbc.queryForObject(ProNativeQueries.SBL_COUNT_BY_DFR.replace("#dfrId#", dfrId), Integer.class);
	}

	@SuppressWarnings("unchecked")
	private List<SnapshotSvAssetDa> getSvListJdbc(Set<String> assetSet, String dfrId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("assets", assetSet);
		return (List<SnapshotSvAssetDa>) namedJdbc.query(
				ProNativeQueries.SV_SNAPSHOT_Q.replace("#dfrId#", dfrId), namedParameters,
				new SvSnapshotMapper());
	}

	@SuppressWarnings("unchecked")
	private List<AssetNewVal> getAssetNewValBySblRowIdsJdbc(Set<String> sblDfrLineSet) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("dfrLineIds", sblDfrLineSet);
		return (List<AssetNewVal>) namedJdbc.query(
				ProNativeQueries.ASSET_NEW_VAL_Q, namedParameters,
				new AssetNewValMapper());
	}

	private List<SnapshotSiebelAssetDa> getSnapshotSiebleAssetDaDataJdbc(ProductFilter productFilter, String product) {
	
		int pageNumber = productFilter.getPageNumber().intValue();
		int pageSize = productFilter.getRange();
		int start = 0;
		int end = 0;
		if (pageNumber - 1 == 0) {
			start = 0;
		} else {
			start = (pageNumber - 1) * pageSize;
		}
		end = start + pageSize;
		String sqlQuery = prepareSqlQueryForDataGrid(productFilter);
		sqlQuery += ProNativeQueries.APPLY_PRODUCT_NAME_FILTER;
		sqlQuery += ProNativeQueries.APPLY_ORDER_BY_FILTER;
		String sqlTemplate = ProNativeQueries.PAGINATION_TEMPLATE.replace("~sqlQuery~",sqlQuery);
		logger.info("SQL for default data grid : "+sqlTemplate);
		String dfrId = productFilter.getDfrId();
		Object args[] = new Object[] {dfrId,dfrId,dfrId,product,start,end};
		return (List<SnapshotSiebelAssetDa>) jdbc.query(
				sqlTemplate,args, new SblSnapshotMapper());
	}

	@Override
	// @Cacheable(value="dartDafilter",key
	// ="#productFilterResult.productFilter.cacheKey")
	public SnapshotProductFilterResult getProductPhysicalAuditFilterResult(SnapshotProductFilterResult productFilterResult) {
		String dfrId = productFilterResult.getProductFilter().getDfrId();
		List<SnapshotSiebelAssetDa> sblList = getSnapshotSiebleAssetDaData(productFilterResult.getProductFilter());
//		List<SnapshotSiebelAssetDa> sblList = getSnapshotPaginatedSiebleAssetDaData(productFilterResult.getProductFilter());
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		Set<String> sblDfrLineSet = new HashSet<>();
		Map<String, List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();
		for (SnapshotSiebelAssetDa sbl : sblList) {
			assetSet.add(sbl.getHeader2());
			if (null != sbl.getHeader57() && sbl.getHeader57().equalsIgnoreCase("Y")) {
				sblDfrLineSet.add(sbl.getDfrLineId());
			}
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId);
		svList = getSvList(assetSet, dfrId);
		List<AssetNewVal> assetNewValList = getAssetNewValBySblRowIds(sblDfrLineSet);
		if (CollectionUtils.isNotEmpty(assetNewValList)) {
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k, v) -> {
				if (v.size() == 1) {
					assetNewValMap.put(k, v.get(0));
				}
			});
		}
		productFilterResult.setAssetNewValMap(assetNewValMap);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		return productFilterResult;
	}
	
	
	@Override
	public List<SnapshotClxAssetDa> getSnapshotClxAssetDaByRowsIds(List<String> rowIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SnapshotClxAssetDa.class);
		Criterion   criterion = Restrictions.in("header1", rowIds.toArray());
		return (List<SnapshotClxAssetDa>)criteria.add(criterion).list();
	}

	@Override
	public List<SnapshotSvAssetDa> getSnapshotSvAssetDaByRowsIds(List<String> rowIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SnapshotSvAssetDa.class);
		Criterion   criterion = Restrictions.in("header1", rowIds.toArray());
		return (List<SnapshotSvAssetDa>)criteria.add(criterion).list();
	}
	
	@Override
	public List<SnapshotSiebelAssetDa> getSnapshotSblAssetDaByRowsIds(List<String> rowIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
		Criterion   criterion = Restrictions.in("header1", rowIds.toArray());
		return (List<SnapshotSiebelAssetDa>)criteria.add(criterion).list();
	}
	
	@Override
	public List<String> getEmailIdByAssignGroup(String assignmentGroup){
		String sql = "Select "
				+ "email_ID "
				+ "from EQX_DART.USER_INFO u"
				+ ",EQX_DART.USER_GROUP b"
				+ ",EQX_DART.ASSIGN_GROUP ag "
				+ "where "
				+ "u.pk_id=b.user_id and "
				+ "b.group_id=ag.row_id and "
				+ "ag.GROUP_NAME='"+assignmentGroup+"'";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<String> emailIds = new ArrayList<String>();
		List<Map<String,String>> emailMap = query.list();
		for (Map<String, String> map : emailMap) {
			for (Entry<String, String> entry : map.entrySet()) {
				emailIds.add(entry.getValue());
			}
		}
		return emailIds;		
	}
	
	public List<String> getSBLErrorCodeList(List<String> sblRowIdList,List<String> errorCodes){
		Session session =   sessionFactory.getCurrentSession();
		List<String> sblRidList = new ArrayList<String>();
		
		List<String> list1 = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(sblRowIdList)){
			if(sblRowIdList.size()>999){
				Lists.partition(Arrays.asList(sblRowIdList.toArray()), 999).stream().forEach(sublist->{
					Criteria criteria = session.createCriteria(CxiErrorTbl.class).setReadOnly(true);
					Conjunction conjunction = Restrictions.conjunction();
					Criterion   criterion1 = Restrictions.in("sblRid", sublist.toArray());
					Criterion   criterion2 = Restrictions.in("errorCode", errorCodes.toArray());
					Criterion   criterion3 = Restrictions.isNull("errorEndDate");
					conjunction.add(criterion1);
					conjunction.add(criterion2);
					conjunction.add(criterion3);
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
				Criteria criteria = session.createCriteria(CxiErrorTbl.class).setReadOnly(true);
				Conjunction conjunction = Restrictions.conjunction();
				Criterion   criterion1 = Restrictions.in("sblRid", sblRowIdList.toArray());
				Criterion   criterion2 = Restrictions.in("errorCode", errorCodes.toArray());
				Criterion   criterion3 = Restrictions.isNull("errorEndDate");
				conjunction.add(criterion1);
				conjunction.add(criterion2);
				conjunction.add(criterion3);
				ProjectionList pList = Projections.projectionList(); 
				pList.add(Projections.property("sblRid")); 
				criteria.setProjection(pList); 
				criteria.add(conjunction);
				sblRidList = (List<String>)criteria.list();
			}
		}
 		
		return sblRidList;
	}
	
	@Override
	public Map<String,Object> getPhysicalAuditDownloadData(String dfrId) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
		criteria.add(Restrictions.eq("dfrId", dfrId));
		List<SnapshotSiebelAssetDa> siebelAssetDaList = new ArrayList<SnapshotSiebelAssetDa>();
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
			siebelAssetDaList.add(siebelAssetDa);
		}
		
		Criteria errorCriteria = session.createCriteria(CxiErrorTbl.class).add(Restrictions.eq("dfrId", dfrId));
		List<CxiErrorTbl> errorList = new ArrayList<>();
		ScrollableResults scrollableResultsError = errorCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResultsError.next()) {
			CxiErrorTbl errorObj = (CxiErrorTbl) scrollableResultsError.get()[0];
			errorList.add(errorObj);
		}

		List<AssetNewVal> assetNewValList = new ArrayList<>();
		Criteria criteriaAssetNewVal = session.createCriteria(AssetNewVal.class).add(Restrictions.eq("dfrId", dfrId));
		ScrollableResults scrollableResultsAssetNewVal = criteriaAssetNewVal.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResultsAssetNewVal.next()) {
			AssetNewVal assetNewVal = (AssetNewVal) scrollableResultsAssetNewVal.get()[0];
			if(assetNewVal!=null){
				assetNewValList.add(assetNewVal);
			}
		}
		responseMap.put("SnapshotSiebelAssetDAList", siebelAssetDaList);
		responseMap.put("AssetNewValList", assetNewValList);
		responseMap.put("ErrorList", errorList);
		return responseMap;
	}

	@Override
	public List<DfrMaster> getDfrMasterCompleted(String emailFlag) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DfrMaster.class);
		criteria.add(Restrictions.or(
				Restrictions.eq("status", LogicConstants.SYNCCOMPLETED).ignoreCase(),
				Restrictions.eq("status", LogicConstants.SYNCPARTIALLYCOMPLETED).ignoreCase(),
				Restrictions.eq("status", LogicConstants.DFRCANCELLED).ignoreCase(),
				Restrictions.eq("status", LogicConstants.SYNCERROR).ignoreCase()));
		criteria.add(Restrictions.or(Restrictions.eq("emailFlag", emailFlag).ignoreCase(),
				Restrictions.isNull("emailFlag")));
		return criteria.list();
	}
	
	@Override
	public void saveOrUpdatePhysicalAuditData(List<SnapshotSiebelAssetDa> assetList,List<AssetNewVal> assetNewValListInsert,
			List<AssetNewVal> assetNewValListUpdate,List<SaveAssetForm> saveAssetForms) {
		Session session = sessionFactory.getCurrentSession();
		assetNewValListUpdate.stream().forEach((assetNewVal) -> {
			if (assetNewVal != null) {
				SaveAssetForm saveAssetForm = new SaveAssetForm();
				assetNewVal.setAttr350("Y");
				session.saveOrUpdate(assetNewVal);
				Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
				criteria.add(Restrictions.eq("dfrLineId", assetNewVal.getDfrLineId()));
				SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) criteria.uniqueResult();
				siebelAssetDa.setHeader57("Y");
				siebelAssetDa.setHeader60("Y");
				session.saveOrUpdate(siebelAssetDa);
				saveAssetForm.setDfrLineId(assetNewVal.getDfrLineId());
				saveAssetForm.setDfrId(siebelAssetDa.getDfrId());
				saveAssetForm.setName(siebelAssetDa.getHeader20());
				saveAssetForms.add(saveAssetForm);
			}
		});
		for (int i = 0; i < assetList.size(); i++) {
			SaveAssetForm saveAssetForm = new SaveAssetForm();
			String dfrSeqval = Long.toString(getNextValueOfDFRLineIDSeq(session));
			SnapshotSiebelAssetDa assetDa = assetList.get(i);
			dfrSeqval = dfrSeqval+"."+assetDa.getDfrId();
			assetDa.setHeader1(dfrSeqval);
			assetDa.setDfrLineId(dfrSeqval);
			AssetNewVal assetNewVal = assetNewValListInsert.get(i);
			assetNewVal.setHeader1(dfrSeqval);
			assetNewVal.setDfrLineId(dfrSeqval);
			assetNewVal.setAttr350("Y");
			session.saveOrUpdate(assetDa);
			session.saveOrUpdate(assetNewVal);
			saveAssetForm.setDfrLineId(assetNewVal.getDfrLineId());
			saveAssetForm.setDfrId(assetDa.getDfrId());
			saveAssetForm.setName(assetDa.getHeader20());
			saveAssetForms.add(saveAssetForm);
		}
	}
	@Override
	public long getNextValueOfDFRLineIDSeq(){
		Session session = sessionFactory.getCurrentSession();
		return getNextValueOfDFRLineIDSeq(session);
	}
	
	@Override
	public long getNextValueOfSerialNumber(){
		Session session = sessionFactory.getCurrentSession();
		return getNextValueOfSerialNumber(session);
	}
	
	public long getNextValueOfDFRLineIDSeq(Session sessionObj) {
		Query queryStr = sessionObj.createSQLQuery("SELECT EQX_DART.PHY_DFRLN_ID.NEXTVAL FROM DUAL");
		return ((BigDecimal)queryStr.uniqueResult()).longValue();	
	}

	public long getNextValueOfSerialNumber(Session sessionObj) {
		Query queryStr = sessionObj.createSQLQuery("SELECT EQX_DART.SERIAL.NEXTVAL FROM DUAL");
		return ((BigDecimal)queryStr.uniqueResult()).longValue();	
	}

	@Override
	public String getAssetXaSeq() {
		Session session =sessionFactory.getCurrentSession();
		Query query = 
				session.createSQLQuery("select EQX_DART.ASST_XA_SEQ.nextval as string from dual")
				.addScalar("string", StandardBasicTypes.STRING);

		return query.uniqueResult().toString();
	}
	
	public void getCriteriaForErrorCodeGlobalFilterKeyword (ProductFilter productFilter, Criteria criteria, boolean isIgnoreProductFilter) {
		getCriteria(productFilter, criteria, isIgnoreProductFilter);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq(DartConstants.DFRID, productFilter.getDfrId()));
		conjunction.add(Restrictions.eq(DartConstants.HEADER_57, "Y"));
		criteria.add(conjunction);
	}
	
	public List<SnapshotSiebelAssetDa> filterAssetForErrorCodeGlobalKeyword (List<SnapshotSiebelAssetDa> siebelAssetDaList, ProductFilter productFilter) {
		boolean isErrorCodeInFilter = false;
		List<PFilter> filters = productFilter.getFilters();
		List<String> errorCodes = new ArrayList<String>();
		PFilter pFilter = null ;
		List<SnapshotSiebelAssetDa> snapshotAssetList = new ArrayList<>();
		Set<SnapshotSiebelAssetDa> snapshotAssetSet = new HashSet<>();
		for(int fCount= 0 ; fCount< filters.size() ; fCount++){
			pFilter = filters.get(fCount);
			String key = pFilter.getKey();
			if(StringUtils.isNotEmpty(key) && key.toLowerCase().contains("error")) {
				errorCodes = pFilter.getListOfValues();
				if(errorCodes.size()>0){
					isErrorCodeInFilter = true;	
					break;
				}
			}
		}
		if (isErrorCodeInFilter) {
			SnapshotSiebelAssetDa assetObj = null;
			for (int i = 0; i < siebelAssetDaList.size(); i++) {
				assetObj = siebelAssetDaList.get(i);
				for(CxiErrorTbl error : assetObj.getCxiErrorTbls() ){
					if(errorCodes.contains(error.getErrorCode())){
						//snapshotAssetList.add(assetObj);
						snapshotAssetSet.add(assetObj);
					}
				}
			}
			snapshotAssetList = new ArrayList<>(snapshotAssetSet);
		} else {
			snapshotAssetList = siebelAssetDaList;
		}
		return snapshotAssetList;
	}
	
	@Override
	public List<String> getRegionFilter(String dfrId) {
		Session session =   sessionFactory.getCurrentSession();
		ProjectionList proList = Projections.projectionList();
	    proList.add(Projections.property("header51"));
		Criteria crit = session.createCriteria(SnapshotSiebelAssetDa.class).add(Restrictions.eq(DartConstants.DFRID, dfrId))
				.setProjection(Projections.distinct(proList)).setReadOnly(true); 
		return (List<String>)crit.list();
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
	/**
	 * Change Summary changes
	 */
	@Override
	public void saveOrUpdateChangeSummaryList (List<ChangeSummary> changeSummaryList) {
		Session session = sessionFactory.getCurrentSession();
		if (CollectionUtils.isNotEmpty(changeSummaryList)) {
			for (int i = 0; i < changeSummaryList.size(); i++) {
				session.saveOrUpdate(changeSummaryList.get(i));
				if (i % 50 == 0) {
					session.flush();
				}
			}
		}
	}
	
	/**
	 * Change Summary changes
	 */
	@Override
	public void saveOrUpdateChangeSummary (ChangeSummary changeSummary) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(changeSummary);
	}
	
	/**
	 * Change Summary changes
	 */
	@Override
	public ChangeSummary getChangeSummary(String dfrLineId,String attrName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ChangeSummary.class);
		criteria.add(Restrictions.eq("dfrLineId", dfrLineId))
				.add(Restrictions.eq("attrName", attrName))
				.addOrder(Order.desc("createdDate"))
				.setMaxResults(1);
		
		return (ChangeSummary)criteria.uniqueResult();
	}
	
	/**
	 * Change Summary changes
	 */
	@Override
	public List<ChangeSummary> getChangeSummaryList(String dfrId) {
		List<ChangeSummary> changeSummaryList = new ArrayList<>();
		if (DartConstants.IS_JDBC_CALL) {
			Object[] args = new Object[]{dfrId,dfrId};
			return jdbc.query(ProNativeQueries.CHANGE_SUMMARY_LIST, args,new ChangeSummaryMapper());
		} else {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ChangeSummary.class).add(Restrictions.eq("dfrId", dfrId));
			ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
			while (scrollableResults.next()) {
				ChangeSummary changeSummary = (ChangeSummary) scrollableResults.get()[0];
				if (changeSummary != null) {
					changeSummaryList.add(changeSummary);
					session.evict(changeSummary);
				}
			}
		}
		return changeSummaryList;
	}
	
	@Override
	public Map<String,String> getAssetNumberByDfrLineID (Set<String> dfrLineIdSet) {
		Map<String,String> responseMap = null;
		if (CollectionUtils.isNotEmpty(dfrLineIdSet)) {
			responseMap = new HashMap<>();
			Session sessionObj = sessionFactory.getCurrentSession();
			Criteria assetNumCriteria = sessionObj.createCriteria(SnapshotSiebelAssetDa.class);
			assetNumCriteria.add(Restrictions.in("dfrLineId", dfrLineIdSet));
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("dfrLineId"));
			pList.add(Projections.property("header2"));
			assetNumCriteria.setProjection(pList);
			ScrollableResults scrollableResults = assetNumCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
			while (scrollableResults.next()) {
				responseMap.put((String) scrollableResults.get()[0], (String) scrollableResults.get()[1]);
			}
		}
		return responseMap;
	}
	
	@Override
	public List<ChangeSummaryDTO> getChangeSummaryDTO(String dfrLineId) {
		Object[] args = new Object[]{dfrLineId};
		return jdbc.query(DBNativeQueries.ASSET_NEW_VAL_CHANGE_SUMMARY, args, new ChangeSummaryDTOMapper());
	}
	
	@Override
	public List<String> getAssetNumFromAssetNewVal(String dfrLineId) {
		Object[] args = new Object[]{dfrLineId};
		return jdbc.query(DBNativeQueries.ASSET_NEW_FROM_NEW_VAL, args, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getString("ASSET_NUM");
			}
			 
		});
	}
	
	@Override
	public void deleteAssetNewVal(String dfrLineId) {
		Session sessionObj = sessionFactory.getCurrentSession();
		AssetNewVal assetNewVal = sessionObj.get(AssetNewVal.class, dfrLineId);
        if (assetNewVal != null) {
        	  sessionObj.delete(assetNewVal);
        }
		/*CriteriaBuilder cBuilder = sessionObj.getCriteriaBuilder();
		CriteriaDelete<AssetNewVal> cQuery = cBuilder.createCriteriaDelete(AssetNewVal.class);
		Root<AssetNewVal> assetNewValRoot = cQuery.from(AssetNewVal.class);
		cQuery.where(cBuilder.equal(assetNewValRoot.get("dfrLineId"), dfrLineId));
		sessionObj.createQuery(cQuery).executeUpdate();*/
	}
	
	@Override
	public void deleteSnapshotSiebelAsseDa(String dfrLineId) {
		Session sessionObj = sessionFactory.getCurrentSession();
		SnapshotSiebelAssetDa snapshotSiebelAssetDa = sessionObj.get(SnapshotSiebelAssetDa.class, dfrLineId);
        if (snapshotSiebelAssetDa != null) {
        	  sessionObj.delete(snapshotSiebelAssetDa);
        }
		/* CriteriaBuilder cBuilder = sessionObj.getCriteriaBuilder();
		CriteriaDelete<SnapshotSiebelAssetDa> cQuery = cBuilder.createCriteriaDelete(SnapshotSiebelAssetDa.class);
		Root<SnapshotSiebelAssetDa> snapshotSiebelAssetRoot = cQuery.from(SnapshotSiebelAssetDa.class);
		cQuery.where(cBuilder.equal(snapshotSiebelAssetRoot.get("dfrLineId"), dfrLineId));
		sessionObj.createQuery(cQuery).executeUpdate();*/
	}
	
	/*@Override
	public void deleteClxError(String dfrLineId) {
		Session sessionObj = sessionFactory.getCurrentSession();
		
		Criteria errorCriteria = sessionObj.createCriteria(CxiErrorTbl.class).add(Restrictions.eq("dfrId", dfrId));
		List<CxiErrorTbl> errorList = new ArrayList<>();
		ScrollableResults scrollableResultsError = errorCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResultsError.next()) {
			CxiErrorTbl errorObj = (CxiErrorTbl) scrollableResultsError.get()[0];
			errorList.add(errorObj);
		}
		
		List<CxiErrorTbl> cxiErrorTbl = (List<CxiErrorTbl>)sessionObj.get(CxiErrorTbl.class, dfrLineId);
        if (cxiErrorTbl != null) {
        	  sessionObj.delete(cxiErrorTbl);
        }
	}*/

	@Override
	public SnapshotProductFilterResult getAccountMoveAttributeView(String dfrId,
			SnapshotProductFilterResult productFilterResult) {
		ProductFilter productFilter = new ProductFilter();
		productFilter.setDfrId(dfrId);
		List<SnapshotSiebelAssetDa> sblList = getSnapshotSiebleAssetDaData(productFilter);
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		Set<String> sblDfrLineSet = new HashSet<>();
		Map<String, List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();
		for (SnapshotSiebelAssetDa sbl : sblList) {
			assetSet.add(sbl.getHeader2());
			if (null != sbl.getHeader57() && sbl.getHeader57().equalsIgnoreCase("Y")) {
				sblDfrLineSet.add(sbl.getDfrLineId());
			}
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId);
		svList = getSvList(assetSet, dfrId);
		List<AssetNewVal> assetNewValList = getAssetNewValBySblRowIds(sblDfrLineSet);
		if (CollectionUtils.isNotEmpty(assetNewValList)) {
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k, v) -> {
				if (v.size() == 1) {
					assetNewValMap.put(k, v.get(0));
				}
			});
		}
		productFilterResult.setAssetNewValMap(assetNewValMap);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		return productFilterResult;
	
	}
	
	@Override
	public List<AssetNewVal> getAssetNewValByDfrIdAndProd(String dfrId, String product) {
		Session session = sessionFactory.getCurrentSession();
		List<AssetNewVal> assetNewValList = new ArrayList<AssetNewVal>();
		Criteria criteria = session.createCriteria(AssetNewVal.class);
		criteria.add(Restrictions.eq("dfrId", dfrId));
		criteria.add(Restrictions.eq("header57", "Y").ignoreCase());
		if(null!=product){
			criteria.add(Restrictions.eq("header20", product).ignoreCase());
		}
		
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			AssetNewVal assetNewVal = (AssetNewVal) scrollableResults.get()[0];
			if(assetNewVal!=null){
				assetNewValList.add(assetNewVal);
				session.evict(assetNewVal);
			}
		}
		return assetNewValList;
	}
	
	@Override
	public List<SnapshotSiebelAssetDa> getSnapshotPaginatedSiebleAssetDaData(ProductFilter productFilter) {
		Session session = sessionFactory.getCurrentSession();
		int total = 0;
		List<SnapshotSiebelAssetDa> siebelAssetDaList=new ArrayList<>();
		List<PFilter> filterList = productFilter.getFilters();
		List<String> products = new ArrayList<>();
		for(PFilter filter : filterList){
			if(filter.getKey().equalsIgnoreCase("header20")){
				products =filter.getListOfValues();
				break;
			}
		}
		for(String product : products){
			Criteria criteria=session.createCriteria(SnapshotSiebelAssetDa.class);
			Conjunction conjunction = Restrictions.conjunction();
			conjunction.add(Restrictions.eq("header20", product));
			conjunction.add(Restrictions.eq(DartConstants.DFRID, productFilter.getDfrId()));
			conjunction.add(Restrictions.eq(DartConstants.HEADER_57, "Y"));
			criteria.add(conjunction);
//			if(productFilter.getApplications().equalsIgnoreCase(DartConstants.SBL)){
			if (productFilter.getOrderBy() != null && productFilter.getOrderBy().trim() != ""
					&& StringUtils.isNotEmpty(productFilter.getOrderBy())&& StringUtils.isNotBlank(productFilter.getOrderBy())) {
				if (productFilter.getOrderBy().equalsIgnoreCase("ASC")) {
					criteria.addOrder(Order.asc(productFilter.getColumn()));
				} else if (productFilter.getOrderBy().equalsIgnoreCase("DESC")) {
					criteria.addOrder(Order.desc(productFilter.getColumn()));
				}
			}
			ScrollableResults scrollableResults = criteria.scroll(ScrollMode.SCROLL_INSENSITIVE);
			if(productFilter.getAttributeFlag().equalsIgnoreCase("true") && StringUtils.isNotEmpty(productFilter.getAttributeFlag())
					&& StringUtils.isNotBlank(productFilter.getAttributeFlag()) && productFilter.getShowAll().equalsIgnoreCase("false")
					&& StringUtils.isNotEmpty(productFilter.getShowAll())
					&& StringUtils.isNotBlank(productFilter.getShowAll())){
				if(scrollableResults.next()){
					int pageNumber = productFilter.getPageNumber().intValue();
					int pageSize = 10;
					int i = 0;
						if(pageNumber -1 == 0){
							scrollableResults.first();
						}else {
							scrollableResults.scroll((pageNumber -1 )* 10);
						}
					while(pageSize > i++ ){
							SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
							siebelAssetDaList.add(siebelAssetDa);
							if(!scrollableResults.next()){
								break;
							}
					}
					scrollableResults.last();
					total =  total + scrollableResults.getRowNumber() + 1;
				}
			}else if(productFilter.getAttributeFlag().equalsIgnoreCase("true") && StringUtils.isNotEmpty(productFilter.getAttributeFlag())
					&& StringUtils.isNotBlank(productFilter.getAttributeFlag()) && productFilter.getShowAll().equalsIgnoreCase("true")
					&& StringUtils.isNotEmpty(productFilter.getShowAll())
					&& StringUtils.isNotBlank(productFilter.getShowAll())){
				while(scrollableResults.next()){
					if(scrollableResults.get()!=null){
						SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
						siebelAssetDaList.add(siebelAssetDa);
					}
					total =  total + scrollableResults.getRowNumber() + 1;
				}
			}else if(productFilter.getAttributeFlag().equalsIgnoreCase("false") && StringUtils.isNotEmpty(productFilter.getAttributeFlag())
					&& StringUtils.isNotBlank(productFilter.getAttributeFlag())){
				while(scrollableResults.next()){
					if(scrollableResults.get()!=null){
						SnapshotSiebelAssetDa siebelAssetDa = (SnapshotSiebelAssetDa) scrollableResults.get()[0];
						siebelAssetDaList.add(siebelAssetDa);
					}
					total =  total + scrollableResults.getRowNumber() + 1;
				}
			}
			
		}
		productFilter.setTotalRows(Long.valueOf(total));
		return siebelAssetDaList;
	}
    
    @Override
    public int deleteSnapshotSiebelAsseDaByDfrLineIds(List<String> dfrLineIds){
    	SqlParameterSource namedParameters =  new MapSqlParameterSource("dfrLineIds", dfrLineIds);
    	return namedJdbc.update(DEL_SNAPSHOT_SIEBEL_BY_DFRLINEID_Q, namedParameters);
    	
    	/*Session sessionObj = sessionFactory.getCurrentSession();
        CriteriaBuilder cBuilder = sessionObj.getCriteriaBuilder();
		CriteriaDelete<SnapshotSiebelAssetDa> cQuery = cBuilder.createCriteriaDelete(SnapshotSiebelAssetDa.class);
		Root<SnapshotSiebelAssetDa> assetNewValRoot = cQuery.from(SnapshotSiebelAssetDa.class);
		cQuery.where(cBuilder.in(assetNewValRoot.get("dfrLineId")).value(dfrLineIds.toArray()));
		sessionObj.createQuery(cQuery).executeUpdate();*/
    }
    
    @Override
	public int deleteAssetNewValByDfrLineIds(List<String> dfrLineIds) {
    	SqlParameterSource namedParameters =  new MapSqlParameterSource("dfrLineIds", dfrLineIds);
    	return namedJdbc.update(DEL_ASSET_NEW_VAL_DFRLINEID_Q, namedParameters);
		
		/*Session sessionObj = sessionFactory.getCurrentSession();
        CriteriaBuilder cBuilder = sessionObj.getCriteriaBuilder();
		CriteriaDelete<AssetNewVal> cQuery = cBuilder.createCriteriaDelete(AssetNewVal.class);
		Root<AssetNewVal> assetNewValRoot = cQuery.from(AssetNewVal.class);
		cQuery.where(cBuilder.in(assetNewValRoot.get("dfrLineId")).value(dfrLineIds.toArray()));
		sessionObj.createQuery(cQuery).executeUpdate();*/
	}
    
    @Override
    public int  deleteCxiErrTblByDfrLineIds(List<String> dfrLineIds){
    	SqlParameterSource namedParameters =  new MapSqlParameterSource("dfrLineIds", dfrLineIds);
    	return namedJdbc.update(DEL_CXI_ERROR_TBL_BY_DFRLINEID_Q, namedParameters);
    	
    	/*Session sessionObj = sessionFactory.getCurrentSession();
        CriteriaBuilder cBuilder = sessionObj.getCriteriaBuilder();
		CriteriaDelete<SnapshotSiebelAssetDa> cQuery = cBuilder.createCriteriaDelete(SnapshotSiebelAssetDa.class);
		Root<SnapshotSiebelAssetDa> assetNewValRoot = cQuery.from(SnapshotSiebelAssetDa.class);
		cQuery.where(cBuilder.in(assetNewValRoot.get("dfrLineId")).value(dfrLineIds.toArray()));
		sessionObj.createQuery(cQuery).executeUpdate();*/
    }
    
    @Override
	public void deleteDfrmaster(String dfrId) {
		Session sessionObj = sessionFactory.getCurrentSession();
		DfrMaster dfrMasterVal = sessionObj.get(DfrMaster.class, dfrId);
        if (dfrMasterVal != null) {
        	  sessionObj.delete(dfrMasterVal);
        }
	}
    
    @Override
	public String getNewPOEAssetNumber() {
		Session session =sessionFactory.getCurrentSession();
		Query query = 
				session.createSQLQuery("select EQX_DART.POE_ASSET.nextval as string from dual")
				.addScalar("string", StandardBasicTypes.STRING);

		return "125_"+query.uniqueResult().toString();
	}
    
    
    @SuppressWarnings("unchecked")
	@Override
	public List<ErrorCodeVO> validationsOnSubmit(String dfrId) throws Exception{
    	return  (List<ErrorCodeVO>)jdbc.query(ProNativeQueries.GET_SUBMIT_VALIDATION_ERR_CDE.replace("#dfrId#", dfrId), new ErrorCodeMapper());
    }
   
    @SuppressWarnings("unchecked")
	private List<SnapshotClxAssetDa> getClxListJdbc(Set<String> cageUniquesSpaceId, Set<String> cabUniqueSpaceId,
			Set<String> cabDpUniqueSpaceId, String dfrId,String name) {
		
    	List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(cageUniquesSpaceId)) {

			SqlParameterSource namedParameters = new MapSqlParameterSource("ids", cageUniquesSpaceId);
			List<SnapshotClxAssetDa> clxList1 =  (List<SnapshotClxAssetDa>) namedJdbc.query(
					ProNativeQueries.CLX_CAGE_QUERY.replace("#dfrId#", dfrId).replace("#name#", "Cage"), namedParameters,
					new ClxSnapshotMapper());
			clxList.addAll(clxList1);

		}
		if (CollectionUtils.isNotEmpty(cabUniqueSpaceId)) {

			SqlParameterSource namedParameters = new MapSqlParameterSource("ids", cabUniqueSpaceId);
			List<SnapshotClxAssetDa> clxList2 = (List<SnapshotClxAssetDa>) namedJdbc.query(
					ProNativeQueries.CLX_CAB_QUERY.replace("#dfrId#", dfrId).replace("#name#", "Cabinet"), namedParameters,
					new ClxSnapshotMapper());
			clxList.addAll(clxList2);
		}
		// dp
		if (CollectionUtils.isNotEmpty(cabUniqueSpaceId)) {
			SqlParameterSource namedParameters = new MapSqlParameterSource("ids", cabUniqueSpaceId);
			List<SnapshotClxAssetDa> clxList3 = (List<SnapshotClxAssetDa>) namedJdbc.query(
					ProNativeQueries.CLX_CAB_QUERY.replace("#dfrId#", dfrId).replace("#name#", "Cabinet"), namedParameters,
					new ClxSnapshotMapper());
			clxList.addAll(clxList3);
		}
		return clxList;

	}
    
    @Override
    public SnapshotProductFilterResult productFilterResultByLineId(SaveAssetForm data) {
    	SnapshotProductFilterResult productFilterResult = new SnapshotProductFilterResult();
    	/**
		 * Jdbc Template
		 */
		List<SnapshotSiebelAssetDa> sblList = null;
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
			sblList = snapshotSiebleAssetDaDataByLineId(data.getDfrId(),data.getDfrLineId());
			logger.info("end time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
		}
		
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		Set<String> sblDfrLineSet = new HashSet<>();
		Map<String, List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();
		for (SnapshotSiebelAssetDa sbl : sblList) {
			assetSet.add(sbl.getHeader2());
			if (null != sbl.getHeader57() && sbl.getHeader57().equalsIgnoreCase("Y")) {
				sblDfrLineSet.add(sbl.getDfrLineId());
			}
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getClxListJdbc : "+ new Date());
			clxList = getClxListByLineId(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId,data.getDfrLineId(), data.getName());
			logger.info("end time of getClxListJdbc : "+ new Date());
		}
		/**
		 * Sv list by jdbc
		 */
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getSvListJdbc : "+ new Date());
			svList = getSvListByLineId(assetSet, data.getDfrLineId());
			logger.info("end time of getSvListJdbc : "+ new Date());
		}
		
		List<AssetNewVal> assetNewValList = null;
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
			assetNewValList = getAssetNewValBySblRowIdsJdbc(sblDfrLineSet);
			logger.info("end time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
		}
		
		if (CollectionUtils.isNotEmpty(assetNewValList)) {
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k, v) -> {
				if (v.size() == 1) {
					assetNewValMap.put(k, v.get(0));
				}
			});
		}
		
		productFilterResult.setAssetNewValMap(assetNewValMap);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		return productFilterResult;
    }

	private List<SnapshotClxAssetDa> getClxListByLineId(Set<String> cageUniquesSpaceId, Set<String> cabUniqueSpaceId,
			Set<String> cabDpUniqueSpaceId, String dfrLineId, String name) {
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(cageUniquesSpaceId)) {

			SqlParameterSource namedParameters = new MapSqlParameterSource("ids", cageUniquesSpaceId);
			clxList =  (List<SnapshotClxAssetDa>) namedJdbc.query(
					ProNativeQueries.CLX_CAGE_QUERY.replace("#dfrLineId#", dfrLineId).replace("#name#", name), namedParameters,
					new ClxSnapshotMapper());

		}
		if (CollectionUtils.isNotEmpty(cabUniqueSpaceId)) {

			SqlParameterSource namedParameters = new MapSqlParameterSource("ids", cabUniqueSpaceId);
			clxList = (List<SnapshotClxAssetDa>) namedJdbc.query(
					ProNativeQueries.CLX_CAB_QUERY.replace("#dfrLineId#", dfrLineId).replace("#name#", name), namedParameters,
					new ClxSnapshotMapper());
		}
		// dp
		if (CollectionUtils.isNotEmpty(cabUniqueSpaceId)) {
			SqlParameterSource namedParameters = new MapSqlParameterSource("ids", cabUniqueSpaceId);
			clxList = (List<SnapshotClxAssetDa>) namedJdbc.query(
					ProNativeQueries.CLX_CAB_QUERY.replace("#dfrLineId#", dfrLineId).replace("#name#", name), namedParameters,
					new ClxSnapshotMapper());
		}
		return clxList;

	}

	private List<SnapshotSiebelAssetDa> snapshotSiebleAssetDaDataByLineId(String dfrId, String dfrLineId) {
		String value = "";
		String sql = ProNativeQueries.DATA_GRID_COMMON_Q;
		if(StringUtils.isNotEmpty(dfrLineId) && StringUtils.isNotBlank(dfrLineId)){
			for(String filterVal :dfrLineId.split(",")){
				if(StringUtils.isNotEmpty(filterVal)){
					filterVal = filterVal.trim();
					value =value + "'"+filterVal+"'"+",";
				}
			}
		}
		
		sql += ProNativeQueries.APPLY_DFR_LINE_ID_FILTER.replace("#dfrLineIds#", removeLastChar(value));
		logger.info("SQL for default data grid : "+sql);
		Object args[] = new Object[] {dfrId,dfrId,dfrId};
		return (List<SnapshotSiebelAssetDa>) jdbc.query(
				sql,args, new SblSnapshotMapper());
	}
	
	private List<SnapshotSvAssetDa> getSvListByLineId(Set<String> assetSet, String dfrLineId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("assets", assetSet);
		return (List<SnapshotSvAssetDa>) namedJdbc.query(
				ProNativeQueries.SV_SNAPSHOT_BY_DFRLINE_ID.replace("#dfrLineId#", dfrLineId), namedParameters,
				new SvSnapshotMapper());
	}
	
	@Override
	public SnapshotProductFilterResult getAllProductFilterResult(
			SnapshotProductFilterResult productFilterResult) {
		String dfrId = productFilterResult.getProductFilter().getDfrId();
		String product =null;	
		ProductFilter productFilter = productFilterResult.getProductFilter();
		List<PFilter> pFilters = productFilter.getFilters();
		for(PFilter pFilter :pFilters ){
			if(pFilter.getKey().equalsIgnoreCase("header20")){
				product = pFilter.getValue();
			}
		}
		/**
		 * Jdbc Template
		 */
		List<SnapshotSiebelAssetDa> sblList = null;
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
			sblList = getAllSnapshotSiebleAssetDaData(productFilterResult.getProductFilter());
			logger.info("end time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
		}else{
			sblList = getSnapshotSiebleAssetDaData(productFilterResult.getProductFilter());
		}
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		Set<String> sblDfrLineSet = new HashSet<>();
		Map<String, List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();
		for (SnapshotSiebelAssetDa sbl : sblList) {
			assetSet.add(sbl.getHeader2());
			if (null != sbl.getHeader57() && sbl.getHeader57().equalsIgnoreCase("Y")) {
				sblDfrLineSet.add(sbl.getDfrLineId());
			}
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		if(DartConstants.IS_JDBC_CALL){
			//logger.info("start time of getClxListJdbc : "+ new Date());
			clxList = getClxListJdbc(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId,product);
			//logger.info("end time of getClxListJdbc : "+ new Date());
		}else{
			clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId);
		}
		
		/**
		 * Sv list by jdbc
		 */
		if (DartConstants.IS_JDBC_CALL) {
//			logger.info("start time of getSvListJdbc : " + new Date());
			if (CollectionUtils.isNotEmpty(assetSet))
				svList = getSvListJdbc(assetSet, dfrId);
	//		logger.info("end time of getSvListJdbc : " + new Date());
		}else{
			svList = getSvList(assetSet, dfrId);
		}
		
		List<AssetNewVal> assetNewValList = null;
		if(DartConstants.IS_JDBC_CALL){
		//	logger.info("start time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
			assetNewValList = getAssetNewValBySblRowIdsJdbc(sblDfrLineSet);
		//	logger.info("end time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
		}else{
			assetNewValList = getAssetNewValBySblRowIds(sblDfrLineSet);
		}
		
		if (CollectionUtils.isNotEmpty(assetNewValList)) {
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k, v) -> {
				if (v.size() == 1) {
					assetNewValMap.put(k, v.get(0));
				}
			});
		}
		
	/*	int count  = getSblCountByDfr(dfrId);
		productFilter.setTotalRows(count);*/
		productFilterResult.setAssetNewValMap(assetNewValMap);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		return productFilterResult;
	}
	
	private List<SnapshotSiebelAssetDa> getAllSnapshotSiebleAssetDaData(ProductFilter productFilter) {
		String dfrId = productFilter.getDfrId();
		Object args[] = new Object[] {dfrId,dfrId,dfrId };
		String sqlQuery = prepareSqlQueryForDataGrid(productFilter);
		sqlQuery += ProNativeQueries.APPLY_ORDER_BY_FILTER;
		logger.info("SQL Q for all result : "+sqlQuery);
		return (List<SnapshotSiebelAssetDa>) jdbc.query(
				sqlQuery,args, new SblSnapshotMapper());
	}
	
	private String prepareSqlQueryForDataGrid(ProductFilter productFilter) {

		String sql = ProNativeQueries.DATA_GRID_COMMON_Q;
		if (StringUtils.isNotEmpty(productFilter.getKpiFilterType()) && productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.CM_KPI_FILTER)) {
			sql += ProNativeQueries.APPLY_CHANGES_MADE_FILTER;
			if(productFilter.isErrorClassification()){
				sql += ProNativeQueries.START_DATA_GRID_ERROR_CONDITION; 
				sql += applyErrorClassificationFilter(productFilter);
				sql += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
				sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
			}
			
		}else if (StringUtils.isNotEmpty(productFilter.getKpiFilterType()) &&productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.AWE_KPI_FILTER)) {
			sql += ProNativeQueries.START_DATA_GRID_ERROR_CONDITION; 
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
			sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
		}else if (StringUtils.isNotEmpty(productFilter.getKpiFilterType()) && productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.NE_KPI_FILTER)) {
			sql += ProNativeQueries.START_DATA_GRID_ERROR_CONDITION; 
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.APPLY_ERR_NOT_LIKE_CONDITION;
			sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
		}else if(StringUtils.isEmpty(productFilter.getKpiFilterType())
				&& StringUtils.isBlank(productFilter.getKpiFilterType())
				&& productFilter.isErrorClassification()){
			sql += ProNativeQueries.START_DATA_GRID_ERROR_CONDITION; 
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
		}
		
		return sql;
	}

	@Override
	public List<ErrorCardVO> getTotalErrorAssetById(String dfrId) {
		Object args[] = new Object[] {dfrId}; 
		return jdbc.query(ProNativeQueries.TOTAL_ASSET_WITH_ERROR_N_FIXED_CT,args, new ErrorCardMapper());
	}

	@Override
	public List<String> getChangesMadeById(String dfrId) {
		Object args[] = new Object[] {dfrId}; 
		return jdbc.query(ProNativeQueries.TOTAL_CHANGES_MADE_CT,args, new RowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) 
					throws SQLException {
				return rs.getString("count");
			}
		});
	}
	
	@Override
	public List<POECountVO> getPOECountByFilters(ProductFilter productFilter) {
		if(StringUtils.isBlank(productFilter.getKpiFilterType()) && StringUtils.isEmpty(productFilter.getKpiFilterType()) && !productFilter.isErrorClassification()){
			return jdbc.query(ProNativeQueries.POE_COUNT_WITH_DEFAULT_Q.replace("#id#", productFilter.getDfrId()), new POECountMapper());
		}else{
			String  sql = applyKpiFilterOnCountQuery(ProNativeQueries.POE_COUNT_WITH_KPI_Q,productFilter);
			logger.info("SQL for POE Count : "+ sql);
			Object[] args = new Object[]{productFilter.getDfrId(),productFilter.getDfrId()};
			return jdbc.query(sql,args, new POECountMapper());
		}
	}

	private String applyKpiFilterOnCountQuery(String sqlQuery, ProductFilter productFilter) {
	
		String sql = sqlQuery;
		
		if(StringUtils.isNotEmpty(productFilter.getKpiFilterType())
				&& StringUtils.isNotBlank(productFilter.getKpiFilterType())
				&& productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.CM_KPI_FILTER)){
			sql += ProNativeQueries.APPLY_CHANGES_MADE_FILTER;
			if(productFilter.isErrorClassification()){
				sql += ProNativeQueries.START_EXISTS_FOR_ASSETS_WITH_ERRORS;
				sql += applyErrorClassificationFilter(productFilter);
				sql += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
				sql += ProNativeQueries.END_EXISTS_FOR_ASSETS_WITH_ERRORS;
			}
			sql +=  ProNativeQueries.END_POE_COUNT_Q;
		}else if(StringUtils.isNotEmpty(productFilter.getKpiFilterType())
				&& StringUtils.isNotBlank(productFilter.getKpiFilterType())
				&& productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.AWE_KPI_FILTER)) {

			sql += ProNativeQueries.START_EXISTS_FOR_ASSETS_WITH_ERRORS;
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
			sql += ProNativeQueries.END_EXISTS_FOR_ASSETS_WITH_ERRORS;
			sql +=  ProNativeQueries.END_POE_COUNT_Q;

		}else if(StringUtils.isNotEmpty(productFilter.getKpiFilterType())
				&& StringUtils.isNotBlank(productFilter.getKpiFilterType())
				&& productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.NE_KPI_FILTER)) {

			sql += ProNativeQueries.START_EXISTS_FOR_ASSETS_WITH_ERRORS;
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.APPLY_ERR_NOT_LIKE_CONDITION;
			sql += ProNativeQueries.END_EXISTS_FOR_ASSETS_WITH_ERRORS;
			sql +=  ProNativeQueries.END_POE_COUNT_Q;

		}else if(StringUtils.isEmpty(productFilter.getKpiFilterType())
				&& StringUtils.isBlank(productFilter.getKpiFilterType())
				&& productFilter.isErrorClassification()){
			
			sql += ProNativeQueries.START_EXISTS_FOR_ASSETS_WITH_ERRORS;
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.END_EXISTS_FOR_ASSETS_WITH_ERRORS;
			sql +=  ProNativeQueries.END_POE_COUNT_Q;
		}
		
		return sql;
	}
	
	private String applyErrorClassificationFilter(ProductFilter productFilter) {
		String sqlQuery = "";
		if(productFilter.isErrorClassification()){
			List<PFilter> filters = productFilter.getFilters();
			String value ="";
			for (PFilter filter : filters) {
				if (filter.getKey().equalsIgnoreCase("Error Code")) {
					for(String filterVal :filter.getValue().split(",")){
						if(StringUtils.isNotEmpty(filterVal)){
							filterVal = filterVal.trim();
							value =value + "'"+filterVal+"'"+",";
						}
					}
				}
			}
			sqlQuery += ProNativeQueries.APPLY_ERROR_CODE_FILTER.replace("#errors#",removeLastChar(value));
		}
		return sqlQuery;
	}

	@Override
	public List<SnapshotSiebelAssetDa> getSnapshotSblAssetDaByPOEFilter(ProductFilter productFilter) {
		List<PFilter> filters = productFilter.getFilters();
		String product = null;
		int pageNumber = productFilter.getPageNumber().intValue();
		int pageSize = productFilter.getRange();
		int start = 0;
		int end = 0;
		if (pageNumber - 1 == 0) {
			start = 0;
		} else {
			start = (pageNumber - 1) * pageSize;
		}
		end = start + pageSize;

		for (PFilter filter : filters) {
			if (filter.getKey().equalsIgnoreCase("header20")) {
				product = filter.getValue();
			}
		}
		
		Object args[] = new Object[] { productFilter.getDfrId(), product, start, end };

		String sql = prepareSqlQueryByPOEFilter(productFilter); 
		logger.info("SQL with POE Filter: "+ sql);
		return jdbc.query(sql, args, new SblSnapshotMapper());
	}

	private String prepareSqlQueryByPOEFilter(ProductFilter productFilter) {

		String sql = ProNativeQueries.BASIC_Q_FOR_GRID_BY_KPI;
		if (productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.CM_KPI_FILTER)) {
			sql += ProNativeQueries.APPLY_CHANGES_MADE_FILTER;
			if(productFilter.isErrorClassification()){
				sql += ProNativeQueries.START_ERROR_CONDITION; 
				sql += applyErrorClassificationFilter(productFilter);
				sql += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
				sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
			}
			
		}else if (productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.AWE_KPI_FILTER)) {
			sql += ProNativeQueries.START_ERROR_CONDITION; 
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.APPLY_ERR_LIKE_CONDITION;
			sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
		}else if (productFilter.getKpiFilterType().equalsIgnoreCase(DartConstants.NE_KPI_FILTER)) {
			sql += ProNativeQueries.START_ERROR_CONDITION; 
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.APPLY_ERR_NOT_LIKE_CONDITION;
			sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
		}else if(StringUtils.isEmpty(productFilter.getKpiFilterType())
				&& StringUtils.isBlank(productFilter.getKpiFilterType())
				&& productFilter.isErrorClassification()){
			sql += ProNativeQueries.START_ERROR_CONDITION; 
			sql += applyErrorClassificationFilter(productFilter);
			sql += ProNativeQueries.CLOSE_ERROR_CONDITION;
		}
		
		
		sql += ProNativeQueries.END_BLOCK_OF_GRID_BY_KPI_Q;
		
		return sql;
	}
	
	@Override
	public List<String> getNewErrorCountById(String dfrId) {
		Object args[] = new Object[] {dfrId}; 
		return jdbc.query(ProNativeQueries.TOTAL_ASSET_WITH_NEW_ERROR_CT,args, new RowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) 
					throws SQLException {
				return rs.getString("count");
			}
		});
	}
	
	@Override
	public void saveDfrNotes(DfrNotes dfrNotes) {
		Session session = sessionFactory.getCurrentSession();
		session.save(dfrNotes);
	}
	
	@Override
	public List<DfrNotes> getDfrNotes(String dfrId) {
		Object[] args = new Object[]{dfrId};
		return jdbc.query(ProNativeQueries.DFR_NOTES_LIST_Q, args, new DfrNotesMapper());
	}
	
	@Override
	public SnapshotProductFilterResult getAllProductFilterResultByProduct(
			SnapshotProductFilterResult productFilterResult) {
		String dfrId = productFilterResult.getProductFilter().getDfrId();
		String product =null;	
		ProductFilter productFilter = productFilterResult.getProductFilter();
		List<PFilter> pFilters = productFilter.getFilters();
		for(PFilter pFilter :pFilters ){
			if(pFilter.getKey().equalsIgnoreCase("header20")){
				product = pFilter.getValue();
			}
		}
		/**
		 * Jdbc Template
		 */
		List<SnapshotSiebelAssetDa> sblList = null;
		if(DartConstants.IS_JDBC_CALL){
			logger.info("start time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
			sblList = getAllSnapshotSiebleAssetDaDataByProduct(productFilterResult.getProductFilter());
			logger.info("end time of getSnapshotSiebleAssetDaDataJdbc : "+ new Date());
		}else{
			sblList = getSnapshotSiebleAssetDaData(productFilterResult.getProductFilter());
		}
		List<SnapshotClxAssetDa> clxList = new ArrayList<>();
		List<SnapshotSvAssetDa> svList = new ArrayList<>();
		Set<String> assetSet = new HashSet<>();
		Set<String> cageUniquesSpaceId = new HashSet<>();
		Set<String> cabUniqueSpaceId = new HashSet<>();
		Set<String> cabDpUniqueSpaceId = new HashSet<>();
		Set<String> sblDfrLineSet = new HashSet<>();
		Map<String, List<AssetNewVal>> assetNewValMapList = new HashMap<>();
		Map<String, AssetNewVal> assetNewValMap = new HashMap<>();
		for (SnapshotSiebelAssetDa sbl : sblList) {
			assetSet.add(sbl.getHeader2());
			if (null != sbl.getHeader57() && sbl.getHeader57().equalsIgnoreCase("Y")) {
				sblDfrLineSet.add(sbl.getDfrLineId());
			}
			if ("Cage".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader10()) {
				cageUniquesSpaceId.add(sbl.getHeader10());
			} else if ("Cabinet".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabUniqueSpaceId.add(sbl.getHeader12());
			} else if ("Demarcation Point".equalsIgnoreCase(sbl.getHeader20()) && null != sbl.getHeader12()) {
				cabDpUniqueSpaceId.add(sbl.getHeader12());
			}
		}
		if(DartConstants.IS_JDBC_CALL){
			//logger.info("start time of getClxListJdbc : "+ new Date());
			clxList = getClxListJdbc(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId,product);
			//logger.info("end time of getClxListJdbc : "+ new Date());
		}else{
			clxList = getClxList(cageUniquesSpaceId, cabUniqueSpaceId, cabDpUniqueSpaceId, dfrId);
		}
		
		/**
		 * Sv list by jdbc
		 */
		if (DartConstants.IS_JDBC_CALL) {
//			logger.info("start time of getSvListJdbc : " + new Date());
			if (CollectionUtils.isNotEmpty(assetSet))
				svList = getSvListJdbc(assetSet, dfrId);
	//		logger.info("end time of getSvListJdbc : " + new Date());
		}else{
			svList = getSvList(assetSet, dfrId);
		}
		
		List<AssetNewVal> assetNewValList = null;
		if(DartConstants.IS_JDBC_CALL){
		//	logger.info("start time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
			assetNewValList = getAssetNewValBySblRowIdsJdbc(sblDfrLineSet);
		//	logger.info("end time of getAssetNewValBySblRowIdsJdbc : "+ new Date());
		}else{
			assetNewValList = getAssetNewValBySblRowIds(sblDfrLineSet);
		}
		
		if (CollectionUtils.isNotEmpty(assetNewValList)) {
			assetNewValMapList = assetNewValList.stream().collect(Collectors.groupingBy(AssetNewVal::getDfrLineId));
			assetNewValMapList.forEach((k, v) -> {
				if (v.size() == 1) {
					assetNewValMap.put(k, v.get(0));
				}
			});
		}
		
	/*	int count  = getSblCountByDfr(dfrId);
		productFilter.setTotalRows(count);*/
		productFilterResult.setAssetNewValMap(assetNewValMap);
		productFilterResult.setSblList(sblList);
		productFilterResult.setClxList(clxList);
		productFilterResult.setSvList(svList);
		return productFilterResult;
	
		
	}

	private List<SnapshotSiebelAssetDa> getAllSnapshotSiebleAssetDaDataByProduct(ProductFilter productFilter) {

		String dfrId = productFilter.getDfrId();
		List<PFilter> filterList = productFilter.getFilters();
		String product = null;
		for (PFilter filter : filterList) {
			if (filter.getKey().equalsIgnoreCase("header20")) {
				product = filter.getValue();
			}
		}
		Object args[] = new Object[] {dfrId,dfrId,dfrId,product};
		String sqlQuery = prepareSqlQueryForDataGrid(productFilter);
		sqlQuery += ProNativeQueries.APPLY_PRODUCT_NAME_FILTER;
		logger.info("SQL Q for all result : "+sqlQuery);
		return (List<SnapshotSiebelAssetDa>) jdbc.query(
				sqlQuery,args, new SblSnapshotMapper());
	
	}
	
	@Override
	public List<DfrLineIdsVo> dfrLineIdListByDfr(String dfrId) {
		Object[] req = new Object[]{dfrId};
		return jdbc.query(ProNativeQueries.DFR_LINE_ID_LIST_BY_DFR_ID, req,new DfrLineIdsMapper());
	}
	
	@Override
	public boolean checkAssignTeamExist(String team) {
		int count = jdbc.queryForObject(ProNativeQueries.IS_ASSIGN_TEAM_EXIST, new Object[]{team},Integer.class);
		return count>0;
	}

	@Override
	public List<String> checkSystemNameForPhysicalAudit(String dfrId) {
		Object[] req = new Object[] { dfrId };
		List<String> statusList = jdbc.query(ProNativeQueries.CHECK_SYSTEM_NAME_COUNT, req, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("STATUS");
			}
		});
		return statusList;

	}
	
}
