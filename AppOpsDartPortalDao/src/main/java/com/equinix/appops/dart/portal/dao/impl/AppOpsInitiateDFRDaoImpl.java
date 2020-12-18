package com.equinix.appops.dart.portal.dao.impl;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.type.StandardBasicTypes;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.CxiErrorTbl;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.EqxClCabinet;
import com.equinix.appops.dart.portal.entity.EqxClCage;
import com.equinix.appops.dart.portal.entity.SAssetXa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorTbl;
import com.equinix.appops.dart.portal.entity.SrcEqxClCabinet;
import com.equinix.appops.dart.portal.entity.SrcEqxClCage;
import com.equinix.appops.dart.portal.entity.SrcSAssetXa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.google.common.collect.Lists;
/**
 * 
 * @author Ankur Bhargava
 * 		   Saurabh Sharma	
 *
 */
@Repository
//@Transactional
public class AppOpsInitiateDFRDaoImpl implements AppOpsInitiateDFRDao {
	Logger logger = LoggerFactory.getLogger(AppOpsInitiateDFRDao.class); 
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbc;

	
	
	
	public HashMap<String, List<SrcCxiErrorTbl>> sblErrorMap = new HashMap<>();
	
	public HashMap<String, List<SrcCxiErrorTbl>> clxErrorMap = new HashMap<>();
	
	public HashMap<String, List<SrcCxiErrorTbl>> svErrorMap = new HashMap<>();	
	
	public HashMap<String,SrcCxiErrorMasterTbl> errorMasterMap = new HashMap<>();
	
	public HashMap<String, HashSet<String>> errorCodeSblRowIdMap = new HashMap<>();
	
	
	@Override
	public HashMap<String, HashSet<String>> getErrorCodeSblRowIdMap() {
		if (this.errorCodeSblRowIdMap == null || this.errorCodeSblRowIdMap.isEmpty() || this.errorCodeSblRowIdMap.size() == 0) {
			this.getErrorList();
		}
		return this.errorCodeSblRowIdMap;
	}
	public void setErrorCodeSblRowIdMap(HashMap<String, HashSet<String>> errorCodeSblRowIdMap) {
		this.errorCodeSblRowIdMap = errorCodeSblRowIdMap;
	}
	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getSblErrorMap() {
		return this.sblErrorMap;
	}
	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getClxErrorMap() {
		return this.clxErrorMap;
	}
	@Override
	public HashMap<String, List<SrcCxiErrorTbl>> getSvErrorMap() {
		return this.svErrorMap;
	}
	
	@Override
	public HashMap<String, SrcCxiErrorMasterTbl> getErrorMasterList() {
		if (this.errorMasterMap != null && !this.errorMasterMap.isEmpty()) {
			this.errorMasterMap.clear();
		}
		this.errorMasterMap = getErrorMasterListData();
		return this.errorMasterMap;
	}

	@Override
	public HashMap<String,HashMap<String, List<SrcCxiErrorTbl>>> getErrorList(){
		HashMap<String,HashMap<String, List<SrcCxiErrorTbl>>> dataMap= new HashMap<>();
		List <SrcCxiErrorTbl> errorList = new ArrayList<>();
		Session session =  sessionFactory.getCurrentSession();
		List<String> activeErrorCode = getActiveErrorCodes();
	    Criterion activeCriterion = Restrictions.in("errorCode", activeErrorCode.toArray());
	    Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.isNull("errorEndDate"))
		.add(activeCriterion);
		
		Criteria criteria = session.createCriteria(SrcCxiErrorTbl.class).add(conjunction);/*setFetchMode("siebelAssetDa", FetchMode.SELECT)
				.setFetchMode("clxAssetDa", FetchMode.SELECT)
				.setFetchMode("svAssetDa", FetchMode.SELECT)*/
				//.add(conjunction);
		logger.info("========Fetching error start");
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		int count=1;
		if (sblErrorMap != null && !sblErrorMap.isEmpty() && sblErrorMap.size() > 0) {
			sblErrorMap.clear();	
		}
		if (clxErrorMap != null && !clxErrorMap.isEmpty() && clxErrorMap.size() > 0) {
			clxErrorMap.clear();
		}
		if (svErrorMap != null && !svErrorMap.isEmpty() && svErrorMap.size() > 0) {
			svErrorMap.clear();
		}
		if (errorCodeSblRowIdMap != null && !errorCodeSblRowIdMap.isEmpty() && errorCodeSblRowIdMap.size() > 0) {
			errorCodeSblRowIdMap.clear();
		}
		long start = System.currentTimeMillis();
		sblErrorMap = new HashMap<>();
		clxErrorMap = new HashMap<>();
		svErrorMap = new HashMap<>();
		errorCodeSblRowIdMap = new HashMap<>();
		 while (scrollableResults.next()) {
			  SrcCxiErrorTbl error = (SrcCxiErrorTbl) scrollableResults.get()[0];
			  count= count+1 ;
			  errorList.add(error);
			  
			  
			  // populate map 
			  try{
					
					String  sblRid = error.getSblRid();
					String  clxRid = error.getClxRid();
					String svRid = error.getSvRid();
					if(sblRid!=null){
						try{
							if(!sblErrorMap.containsKey(sblRid)){
								List<SrcCxiErrorTbl>  sblErrorList  = new ArrayList<SrcCxiErrorTbl>();
								sblErrorList.add(error);
								sblErrorMap.put(sblRid,sblErrorList );
							}else {
								List<SrcCxiErrorTbl>  sblErrorList  =sblErrorMap.get(sblRid);
								sblErrorList.add(error);
								sblErrorMap.put(sblRid, sblErrorList);
							} 
							// creating val_code based sbl rowid hash set 
							if(!errorCodeSblRowIdMap.containsKey(error.getErrorCode()) && error.getErrorEndDate() == null){
								HashSet<String> sblRidSet = new HashSet<>();
								sblRidSet.add(sblRid);
								errorCodeSblRowIdMap.put(error.getErrorCode(),sblRidSet);
							}else{
								HashSet<String> sblRidSet = errorCodeSblRowIdMap.get(error.getErrorCode());
								sblRidSet.add(sblRid);
								errorCodeSblRowIdMap.put(error.getErrorCode(),sblRidSet);
							}
							
						}catch(Exception e){
							logger.info("sbl " + sblRid);
							logger.info("error " + sblRid);
							logger.info(" row_id " + sblRid);
							logger.info(e.getMessage() );
						}
						
						
					} else if (clxRid!=null){
						
						try{
							if ( !clxErrorMap.containsKey(clxRid)){
								List<SrcCxiErrorTbl>  clxErrorList  = new ArrayList<SrcCxiErrorTbl>();
								clxErrorList.add(error);
								clxErrorMap.put(clxRid,clxErrorList );
							}else {
								List<SrcCxiErrorTbl>  clxErrorList  =clxErrorMap.get(clxRid);
								clxErrorList.add(error);
								clxErrorMap.put(clxRid,clxErrorList );

							}
						}catch(Exception e){
							logger.info("cxl " + clxRid);
							logger.info("error " + clxRid);
							logger.info(" row_id " + clxRid);
							logger.info(e.getMessage() );
						}
						
					}else if (svRid!=null){
						try{
							
							if (svRid!=null && !svErrorMap.containsKey(svRid)){
								List<SrcCxiErrorTbl>  svErrorList  = new ArrayList<SrcCxiErrorTbl>();
								svErrorList.add(error);
								svErrorMap.put(svRid,svErrorList );
							}else {
								List<SrcCxiErrorTbl>  svErrorList  =svErrorMap.get(svRid);
								svErrorList.add(error);
								svErrorMap.put(svRid,svErrorList );
							}
						}catch(Exception e){
							logger.info("cxl " + svRid);
							logger.info("error " + svRid);
							logger.info(" row_id " + svRid);
							logger.info(e.getMessage() );
						}
						
					}
					
				}catch (Exception e) {
						logger.info(e.getMessage());
					}
				
				//logger.info(sblErrorMap.size() + ":" + clxErrorMap.size() + ":" +svErrorMap.size() );
			  
			  
				dataMap.put("sblError",sblErrorMap);
				dataMap.put("clxError",clxErrorMap);
				dataMap.put("svError", svErrorMap);
			  
		   /*session.evict(siebelAssetDa);*/
		  }
		 long end = System.currentTimeMillis();
		  logger.info("========Fetching error end :" +count + " : time consumed : " + (end - start) );  
		  return dataMap;
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
	
	private HashMap<String,SrcCxiErrorMasterTbl> getErrorMasterListData() {
		Session session =  sessionFactory.getCurrentSession();
		Criteria errorMasterCriteria = session.createCriteria(SrcCxiErrorMasterTbl.class).add(Restrictions.eq("active","Y"));
		return (HashMap<String,SrcCxiErrorMasterTbl>)errorMasterCriteria.list().stream().collect
				(Collectors.groupingBy(SrcCxiErrorMasterTbl::getErrorCode));
	}
	
	@Override
	public List<SrcEqxClCage> getSRCCages(String cageId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcEqxClCage.class);
		criteria.add(Restrictions.eq("cageId",cageId));
		return criteria.list();
	}
	
	@Override
	public List<SrcEqxClCage> getSRCCages(Set<String> clxCageIds) {
		Session session = sessionFactory.getCurrentSession();
		List<SrcEqxClCage> srcEqxClCageList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(clxCageIds)){
			if(clxCageIds.size() > 999 ){
				Lists.partition(Arrays.asList(clxCageIds.toArray()), 999).stream().forEach(list ->{
					Criteria criteria = session.createCriteria(SrcEqxClCage.class);
					criteria.add(Restrictions.in("cageId",list.toArray()));
					ScrollableResults srs = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while(srs.next()){
						SrcEqxClCage srcEqxClCage = (SrcEqxClCage)srs.get()[0];
						srcEqxClCageList.add(srcEqxClCage);
					}
				});
			}else{
				Criteria criteria = session.createCriteria(SrcEqxClCage.class);
				criteria.add(Restrictions.in("cageId",clxCageIds.toArray()));
				ScrollableResults srs = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while(srs.next()){
					SrcEqxClCage srcEqxClCage = (SrcEqxClCage)srs.get()[0];
					srcEqxClCageList.add(srcEqxClCage);
				}
			}
		}
		
		return srcEqxClCageList;
	}
	

	@Override
	public List<SrcEqxClCabinet> getSRCCabinates(String cabinateId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcEqxClCabinet.class);
		criteria.add(Restrictions.eq("cabinetId",cabinateId));
		return criteria.list();
	}
	
	@Override
	public List<SrcEqxClCabinet> getSRCCabinates(Set<String> cabinateIds) {
	Session session = sessionFactory.getCurrentSession();
	List<SrcEqxClCabinet> srcEqxClCabList = new ArrayList<>();
	if(CollectionUtils.isNotEmpty(cabinateIds)){
		if(cabinateIds.size() > 999 ){
			Lists.partition(Arrays.asList(cabinateIds.toArray()), 999).stream().forEach(list ->{
				Criteria criteria = session.createCriteria(SrcEqxClCabinet.class);
				criteria.add(Restrictions.in("cabinetId",list.toArray()));
				ScrollableResults srs = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while(srs.next()){
					SrcEqxClCabinet srcEqxClCage = (SrcEqxClCabinet)srs.get()[0];
					srcEqxClCabList.add(srcEqxClCage);
				}
			});
		}else{
			Criteria criteria = session.createCriteria(SrcEqxClCabinet.class);
			criteria.add(Restrictions.in("cabinetId",cabinateIds.toArray()));
			ScrollableResults srs = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
			while(srs.next()){
				SrcEqxClCabinet srcEqxClCage = (SrcEqxClCabinet)srs.get()[0];
				srcEqxClCabList.add(srcEqxClCage);
			}
		}
	}
	
	return srcEqxClCabList;
	}


	@Override
	public List<SrcSAssetXa> getSRCAssets(String assetId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcSAssetXa.class);
		criteria.add(Restrictions.eq("assetId",assetId));
		return criteria.list();
	}

	@Override
	public List<SrcSAssetXa> getSRCAssets(Set<String> assetIds) {
		Session session = sessionFactory.getCurrentSession();
		List<SrcSAssetXa> srcSAssetXaList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(assetIds)){
			if( assetIds.size() > 999 ){
				Lists.partition(Arrays.asList(assetIds.toArray()), 999).stream().forEach(list ->{
					Criteria criteria = session.createCriteria(SrcSAssetXa.class);
					criteria.add(Restrictions.in("assetId",list.toArray()));
					ScrollableResults srs = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					while(srs.next()){
						SrcSAssetXa srcSAssetXa = (SrcSAssetXa)srs.get()[0];
						srcSAssetXaList.add(srcSAssetXa);
					}
				});
			}else{
				Criteria criteria = session.createCriteria(SrcSAssetXa.class);
				criteria.add(Restrictions.in("assetId",assetIds.toArray()));
				ScrollableResults srs = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while(srs.next()){
					SrcSAssetXa srcSAssetXa = (SrcSAssetXa)srs.get()[0];
					srcSAssetXaList.add(srcSAssetXa);
				}
			}
		}
		return srcSAssetXaList;
	}
	@Override
	public void saveOrUpdateCages(List<EqxClCage> cages) {
		Session session = sessionFactory.getCurrentSession();
		for (EqxClCage cage : cages) {
			session.saveOrUpdate(cage);
		}
	}

	@Override
	public void saveOrUpdateCabinates(List<EqxClCabinet> cabinates) {
		Session session = sessionFactory.getCurrentSession();
		for (EqxClCabinet cabinet : cabinates) {
			session.saveOrUpdate(cabinet);
		}	
	}

	@Override
	public void saveOrUpdateAssets(List<SAssetXa> assets) {
		Session session = sessionFactory.getCurrentSession();
		for (SAssetXa asset : assets) {
			session.saveOrUpdate(asset);
		}
	}

	@Override
	public void saveOrUpdateCage(EqxClCage cage) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cage);
	}

	@Override
	public void saveOrUpdateCabinate(EqxClCabinet cabinate) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cabinate);
	}

	@Override
	public void saveOrUpdateAsset(SAssetXa asset) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(asset);
	}
	
	@Override
	public  Long getNextDartSeq() {
		Session session =sessionFactory.getCurrentSession();
		Query query = 
				session.createSQLQuery("select EQX_DART.DARTSEQ.nextval as num from dual")
				.addScalar("num", StandardBasicTypes.BIG_INTEGER);

		return ((BigInteger) query.uniqueResult()).longValue();
	}
	
	@Override
	public void saveOrUpdateDfrMaster(DfrMaster dfrMaster){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(dfrMaster);
	}
	
	@Override
	public void saveOrUpdateSnapshotSiebelAssetDa(SnapshotSiebelAssetDa snapshotSiebelAssetDa){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(snapshotSiebelAssetDa);
	}
	
	@Override
	public List<SnapshotClxAssetDa> getSnapshotClxAssetDaByDfrLineId(String dfrLineId){
		Session session = sessionFactory.getCurrentSession();
		List list = session.createCriteria(SnapshotClxAssetDa.class).add(Restrictions.eq("dfrLineId",dfrLineId )).list();		

		return list;
	}
	@Override
	public void saveOrUpdateSnapshotClxAssetDa(SnapshotClxAssetDa snapshotClxAssetDa){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(snapshotClxAssetDa);
	}
	
	
	
	@Override	
	public void saveOrUpdateSnapshotSvAssetDa(SnapshotSvAssetDa snapshotSvAssetDa){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(snapshotSvAssetDa);
	}
	
	@Override
	public void saveSblErrorSnapshots(SnapshotSiebelAssetDa sda,SiebelAssetDa da , String dfrId,	List<SrcCxiErrorTbl> errors ){
		Session session = sessionFactory.getCurrentSession();
	
		if(da!=null){
			if(CollectionUtils.isNotEmpty(errors)){
				for(SrcCxiErrorTbl error :errors ){
					//SrcCxiErrorMasterTbl  errorMasterCode= null;
					try{
						//errorMasterCode = error.getCxiErrorMasterTbl();
						CxiErrorTbl cxiError = getCxiError(error, dfrId);
						cxiError.setErrorCode(error.getErrorCode());
						cxiError.setSnapshotSiebelAssetDa(sda);
						cxiError.setStatusCD("Open");
						session.save(cxiError);
					}catch(Exception e){
						logger.info("Error code not found in master so snap shot not saving clx row Id :" + da.getHeader1());
					}
				}
			}
		}
	}
		
	@Override
	public void setSblErrorSnapshotList(SnapshotSiebelAssetDa sda,SiebelAssetDa da , String dfrId,	List<SrcCxiErrorTbl> errors, List<CxiErrorTbl> cxiErrorList ){
		
		if(da!=null){
			if(CollectionUtils.isNotEmpty(errors)){
				for(SrcCxiErrorTbl error :errors ){
					//SrcCxiErrorMasterTbl  errorMasterCode= null;
					try{
						//errorMasterCode = error.getCxiErrorMasterTbl();
						CxiErrorTbl cxiError = getCxiError(error, dfrId);
						cxiError.setErrorCode(error.getErrorCode());
						cxiError.setSnapshotSiebelAssetDa(sda);
						cxiError.setSnapshotSiebelAssetDaLineId(sda.getDfrLineId());
						cxiError.setStatusCD("Open");
						cxiErrorList.add(cxiError);
						//session.save(cxiError);
					}catch(Exception e){
						logger.info("Error code not found in master so snap shot not saving clx row Id :" + da.getHeader1());
					}
				}
	}
		}
	}
	
	@Override
	public void saveSvErrorSnapshots(SnapshotSvAssetDa sda, SvAssetDa da , String dfrId,List<SrcCxiErrorTbl> errors){
		Session session = sessionFactory.getCurrentSession();
		if(da!=null){
			if(CollectionUtils.isNotEmpty(errors)){
				for(SrcCxiErrorTbl error :errors ){
					try{
						//SrcCxiErrorMasterTbl errorMasterCode = error.getCxiErrorMasterTbl();
						CxiErrorTbl	cxiError = getCxiError(error, dfrId);
						cxiError.setErrorCode(error.getErrorCode());
						cxiError.setSnapshotSvAssetDa(sda);
						cxiError.setStatusCD("Open");
						session.save(cxiError);
					}catch(Exception e){
						logger.info("Error code not found in master so snap shot not saving clx row Id :" + da.getHeader1());
					}
				}
			}
		}
	}
	
	@Override
	public void setSvErrorSnapshotList(SnapshotSvAssetDa sda, SvAssetDa da , String dfrId,List<SrcCxiErrorTbl> errors, List<CxiErrorTbl> cxiErrorList){
		if(da!=null){
			if(CollectionUtils.isNotEmpty(errors)){
				for(SrcCxiErrorTbl error :errors ){
					try{
						//SrcCxiErrorMasterTbl errorMasterCode = error.getCxiErrorMasterTbl();
						CxiErrorTbl	cxiError = getCxiError(error, dfrId);
						cxiError.setErrorCode(error.getErrorCode());
						cxiError.setSnapshotSvAssetDa(sda);
						cxiError.setStatusCD("Open");
						cxiError.setSnapshotSvAssetDaLineId(sda.getDfrLineId());
						//session.save(cxiError);
						cxiErrorList.add(cxiError);
					}catch(Exception e){
						logger.info("Error code not found in master so snap shot not saving clx row Id :" + da.getHeader1());
					}
				}
			}
		}
	}
	
	@Override
	public void saveClxErrorSnapshots(SnapshotClxAssetDa sda, ClxAssetDa da  , String dfrId,List<SrcCxiErrorTbl> errors ){
		Session session = sessionFactory.getCurrentSession();
		
		if(da!=null){
			if(CollectionUtils.isNotEmpty(errors)){
				for(SrcCxiErrorTbl error :errors ){
						try{
						//	SrcCxiErrorMasterTbl errorMasterCode = error.getCxiErrorMasterTbl();
							CxiErrorTbl	cxiError = getCxiError(error, dfrId);
							cxiError.setErrorCode(error.getErrorCode());
							cxiError.setSnapshotClxAssetDa(sda);
							cxiError.setStatusCD("Open");
							session.save(cxiError);
						}catch(Exception e){
							logger.info("Error code not found in master so snap shot not saving clx row Id :" + da.getHeader1());
						}
				}
			}
		}
		
		
	}
	
	@Override
	public void setClxErrorSnapshotList(SnapshotClxAssetDa sda, ClxAssetDa da  , String dfrId,List<SrcCxiErrorTbl> errors , List<CxiErrorTbl> cxiErrorTblList){		
		if(da!=null){
			if(CollectionUtils.isNotEmpty(errors)){
				for(SrcCxiErrorTbl error :errors ){
						try{
						//	SrcCxiErrorMasterTbl errorMasterCode = error.getCxiErrorMasterTbl();
							CxiErrorTbl	cxiError = getCxiError(error, dfrId);
							cxiError.setErrorCode(error.getErrorCode());
							cxiError.setSnapshotClxAssetDa(sda);
							cxiError.setSnapshotClxAssetDaLineId(sda.getDfrLineId());
							cxiError.setStatusCD("Open");
							//session.save(cxiError);
							cxiErrorTblList.add(cxiError);
						}catch(Exception e){
							logger.info("Error code not found in master so snap shot not saving clx row Id :" + da.getHeader1());
						}
				}
			}
		}
		
		
	}
	
	private String getErrorDfrLineId(SrcCxiErrorTbl error,String seqDfrId ){
		if(error!=null ){
			return error.getErrUniqueId()+"."+seqDfrId ;
		}else{
			logger.info(" Error not found for " +seqDfrId );
			return null;
		}
		
	}
	
	
	private CxiErrorTbl getCxiError(SrcCxiErrorTbl error,String dfrId) {
		CxiErrorTbl serror = new CxiErrorTbl();	
		serror.setDfrId(dfrId);
		serror.setDfrLineId(error.getErrUniqueId()+"."+dfrId);
		serror.setErrUniqueId(error.getErrUniqueId()+"."+dfrId);
		
		serror.setAssetCreationDate(error.getAssetCreationDate());
		serror.setAssignedTo(error.getAssignedTo());
		
		serror.setAudit01(error.getAudit01());
		serror.setAudit01In(error.getAudit01In());
		serror.setAudit01Out(error.getAudit01Out());
		
		serror.setAudit02(error.getAudit02());
		serror.setAudit02In(error.getAudit02In());
		serror.setAudit02Out(error.getAudit02Out());
		
		serror.setAudit03(error.getAudit03());
		serror.setAudit03In(error.getAudit03In());
		serror.setAudit03Out(error.getAudit03Out());
		
		serror.setAudit04(error.getAudit04());
		serror.setAudit04In(error.getAudit04In());
		serror.setAudit04Out(error.getAudit04Out());
		
		serror.setAudit05(error.getAudit05());
		serror.setAudit05In(error.getAudit05In());
		serror.setAudit05Out(error.getAudit05Out());
		
		serror.setAudit06(error.getAudit06());
		serror.setAudit06In(error.getAudit06In());
		serror.setAudit06Out(error.getAudit06Out());
		
		serror.setAudit07(error.getAudit07());
		serror.setAudit07In(error.getAudit07In());
		serror.setAudit07Out(error.getAudit07Out());
		
		serror.setAudit08(error.getAudit08());
		serror.setAudit08In(error.getAudit08In());
		serror.setAudit08Out(error.getAudit08Out());
		
		serror.setAudit09(error.getAudit09());
		serror.setAudit09In(error.getAudit09In());
		serror.setAudit09Out(error.getAudit09Out());
		
		serror.setAudit09(error.getAudit09());
		serror.setAudit09In(error.getAudit09In());
		serror.setAudit09Out(error.getAudit09Out());
		
		serror.setAudit10(error.getAudit10());
		serror.setAudit10In(error.getAudit10In());
		serror.setAudit10Out(error.getAudit10Out());
		
		serror.setBatchNo(error.getBatchNo());
		serror.setComments(error.getComments());
		serror.setCreatedBy(error.getCreatedBy());
	
		serror.setErrorCreationDate(error.getErrorCreationDate());
		serror.setErrorEndDate(error.getErrorEndDate());
		serror.setErrorItem(error.getErrorItem());
		serror.setErrorName(error.getErrorName());
		serror.setIbx(error.getIbx());
		serror.setIncident(error.getIncident());
		serror.setLastUpdatedBy(error.getLastUpdatedBy());
		serror.setLastUpdatedDate(error.getLastUpdatedDate());
		
		return serror;
	}
	
	@Override
	public void saveOrUpdateAssetNewVal(AssetNewVal assetNewVal){
		assetNewVal.setHeader36(new Date());
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(assetNewVal);
	}

	@Override
	public List<SnapshotSiebelAssetDa> getSnapshotSibelAssetDaByRowId(String rowId) {
		Session session = sessionFactory.getCurrentSession();
		List list = session.createCriteria(SnapshotSiebelAssetDa.class).add(Restrictions.eq("header1",rowId )).list();		

		return list;
	}
	
	@Override
	public void saveOrUpdateApprovalHistory(ApprovalHistory approvalHistory){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(approvalHistory);
	}

	@Override
	public ApprovalHistory getLatestAppHistory(String dfrId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ApprovalHistory.class);
		criteria.add(Restrictions.eq("dfrId", dfrId)).addOrder(Order.desc("appSequence")).setMaxResults(1);
		return (ApprovalHistory)criteria.uniqueResult();
	}

	@Override
	public boolean saveSoapAudit(DartSoapAudit dartSoapAudit) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(dartSoapAudit);
		return true;
	}
	
	@Override
	public List<DartSoapAudit> getAllSoapAuditReq() {
		return sessionFactory.getCurrentSession().
				createQuery("from DartSoapAudit d").list();
	}
	@Override
	public List<DartSoapAudit> getAuditsDfrorProduct(String dfrId, String product) {

		Session session = sessionFactory.getCurrentSession();
		List list = null;
		if((dfrId !=null) && (product!=null))
		{ list = session.createCriteria(DartSoapAudit.class)
				.add(Restrictions.eq("dfrId",dfrId ).ignoreCase())
				.add(Restrictions.eq("product",product ).ignoreCase()).list();
		}
		else if(dfrId!=null){
			list = session.createCriteria(DartSoapAudit.class)
					.add(Restrictions.eq("dfrId",dfrId ).ignoreCase()).list();
		}
		else {
			list = session.createCriteria(DartSoapAudit.class)
					.add(Restrictions.eq("product",product ).ignoreCase()).list();
		}		

		return list;
	
	}
	
	@Override
	public List<DartSoapAudit> getRecentAuditsDfrorProduct(Long responseTime, long retryCount) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(DartSoapAudit.class)		
		.add(Restrictions.eq("fault",LogicConstants.SYNCERROR.toLowerCase()).ignoreCase())
		.add(Restrictions.lt("retryCount",retryCount))
		.add(Restrictions.ge("responseTime", new DateTime(new Date()).minusMinutes(responseTime.intValue()).toDate() ))
		.addOrder(Order.desc("responseTime"));
		return criteria.list();
	}
	
	@Override
	public DartSoapAudit getDFRAuditByReqId(String dfrRequestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(DartSoapAudit.class)
				.add(Restrictions.eq("requestId", dfrRequestId));
		return (DartSoapAudit)criteria.uniqueResult();
	}
	
	@Override
	public List<ApprovalHistory> getLatestAppHistoryList(String dfrId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ApprovalHistory.class);
		criteria.add(Restrictions.eq("dfrId", dfrId)).addOrder(Order.desc("appSequence"));
		return(List<ApprovalHistory>) criteria.list();
	}
	@Override
	public Long getNextNoccIntegrationReqSeq() {
		Session session =sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select EQX_DART.NOCC_REQUEST_SEQUENCE.nextval as num from dual")
				.addScalar("num", StandardBasicTypes.BIG_INTEGER);

		return ((BigInteger) query.uniqueResult()).longValue();
	}
	
	@Override
	public void deleteDfrFile(DFRFile dfrFile) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(dfrFile);
		
	}
	@Override
	public void saveOrUpdateDfrFile(DFRFile dfFile) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(dfFile);
		
	}
	@Override
	public DFRFile getDFRFileByDFRId(String dfrId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(DFRFile.class)
				.add(Restrictions.eq("dfrId", dfrId));
		return (DFRFile)criteria.uniqueResult();
	}
	
	// initiate dfr performance poc
	
		@Override
		public void saveOrUpdateBatchData(List<?> batchDataList) {
			if (CollectionUtils.isNotEmpty(batchDataList)) {
				Session session = sessionFactory.getCurrentSession();
				session.doWork(new Work() {
					@Override
					public void execute(Connection conn) throws SQLException {
						if (batchDataList.get(0) instanceof SnapshotSiebelAssetDa) {
							saveOrUpdateSnapshotSiebelAssetDaBatchData(conn, (List<SnapshotSiebelAssetDa>) batchDataList);
						}else if (batchDataList.get(0) instanceof AssetNewVal) {
							saveOrUpdateAssetNewValBatchData(conn, (List<AssetNewVal>) batchDataList);
						}else if (batchDataList.get(0) instanceof SnapshotClxAssetDa) {
							saveOrUpdateSnapshotClxAssetDaBatchData(conn, (List<SnapshotClxAssetDa>) batchDataList);
						}else if (batchDataList.get(0) instanceof SnapshotSvAssetDa) {
							saveOrUpdateSnapshotSvAssetDaBatchData(conn, (List<SnapshotSvAssetDa>) batchDataList);
						}else if (batchDataList.get(0) instanceof CxiErrorTbl) {
							saveOrUpdateSnapshotCXIErrorBatchData(conn, (List<CxiErrorTbl>) batchDataList);
						};
					}
				});	
			}
		}
	
	private void saveOrUpdateSnapshotSiebelAssetDaBatchData(Connection conn, List<SnapshotSiebelAssetDa> snapshotSiebelAssetDaList) throws SQLException {
		try {
			List<String> propertiesList = getClassPropertiesList(SnapshotSiebelAssetDa.class);
			String snapshotSiebelAssetDaInsert = getBatchInsertQuery("EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA", propertiesList);
			SqlParameterSource[] parameters = SqlParameterSourceUtils
					.createBatch(snapshotSiebelAssetDaList.toArray());
			namedJdbc.batchUpdate(snapshotSiebelAssetDaInsert, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveOrUpdateAssetNewValBatchData(Connection conn, List<AssetNewVal> assetNewValList) throws SQLException {
		try {
			List<String> propertiesList = getClassPropertiesList(AssetNewVal.class);
			String assetNewValInsert = getBatchInsertQuery("EQX_DART.ASSET_NEW_VAL", propertiesList);
			SqlParameterSource[] parameters = SqlParameterSourceUtils
					.createBatch(assetNewValList.toArray());
			namedJdbc.batchUpdate(assetNewValInsert, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveOrUpdateSnapshotClxAssetDaBatchData(Connection conn, List<SnapshotClxAssetDa> snapshotClxAssetDaList) throws SQLException {
		try {
			List<String> propertiesList = getClassPropertiesList(SnapshotClxAssetDa.class);
			String snapshotClxAssetDaInsert = getBatchInsertQuery("EQX_DART.SNAPSHOT_CLX_ASSET_DA", propertiesList);
			SqlParameterSource[] parameters = SqlParameterSourceUtils
					.createBatch(snapshotClxAssetDaList.toArray());
			namedJdbc.batchUpdate(snapshotClxAssetDaInsert, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveOrUpdateSnapshotSvAssetDaBatchData(Connection conn, List<SnapshotSvAssetDa> snapshotSvAssetDaList) throws SQLException {
		try {
			List<String> propertiesList = getClassPropertiesList(SnapshotSvAssetDa.class);
			String snapshotSvAssetDaInsert = getBatchInsertQuery("EQX_DART.SNAPSHOT_SV_ASSET_DA", propertiesList);
			SqlParameterSource[] parameters = SqlParameterSourceUtils
					.createBatch(snapshotSvAssetDaList.toArray());
			namedJdbc.batchUpdate(snapshotSvAssetDaInsert, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveOrUpdateSnapshotCXIErrorBatchData(Connection conn, List<CxiErrorTbl> cxiErrorList) throws SQLException {
		try {
			List<String> propertiesList = getClassPropertiesList(CxiErrorTbl.class);
			String cxiErrorTblInsert = getBatchInsertQuery("EQX_DART.CXI_ERROR_TBL", propertiesList);
			SqlParameterSource[] parameters = SqlParameterSourceUtils
					.createBatch(cxiErrorList.toArray());
			namedJdbc.batchUpdate(cxiErrorTblInsert, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getBatchInsertQuery(String tableName, List<String> propertiesList) {
		StringBuilder batchInsertQuery = new StringBuilder("INSERT INTO ").append(tableName).append("(");
		for (int i = 0; i < propertiesList.size(); i++) {
			batchInsertQuery.append(propertiesList.get(i).split("~")[1]).append(i != propertiesList.size() - 1 ? "," : "");
		}
		batchInsertQuery.append(") VALUES (");
		for (int i = 0; i < propertiesList.size(); i++) {
			batchInsertQuery.append(":").append(propertiesList.get(i).split("~")[0])
					.append(i != propertiesList.size() - 1 ? "," : "");
		}
		batchInsertQuery.append(")");
		return batchInsertQuery.toString();
	}
	
	/*private List<String> getClassPropertiesList(Class<?> className){
        List<String> propertiesList = new ArrayList<String>();
        try {
            PropertyDescriptor[] propertyDescArray = Introspector.getBeanInfo(className)
                    .getPropertyDescriptors();
            Map<String, String> propertyDescMap = new HashMap<String, String>();
            for (PropertyDescriptor propertyDescriptor : propertyDescArray) {
            	if (propertyDescriptor!=null && propertyDescriptor.getReadMethod()!=null && StringUtils.isNotBlank(propertyDescriptor.getName()) && StringUtils.isNotBlank(propertyDescriptor.getReadMethod().getName()) && propertyDescriptor.getPropertyType()!=null) {
                    propertyDescMap.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod().getName()+"~"+propertyDescriptor.getPropertyType());
				}
            }
            Field[] fields = className.getDeclaredFields();
            for(Field f : fields){
                Column col = f.getAnnotation(Column.class);
                JoinColumn jcol = f.getAnnotation(JoinColumn.class);
                if (col != null) {
                    propertiesList.add(f.getName()+"~"+propertyDescMap.get(f.getName())+"~"+col.name());
                }else if(jcol != null){
                	propertiesList.add(f.getName()+"LineId~"+propertyDescMap.get(f.getName())+"LineId~"+jcol.name());
                }
            }
         } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return propertiesList;
    }*/
	
	private List<String> getClassPropertiesList(Class<?> className) {
		List<String> propertiesList = new ArrayList<String>();
		Field[] fields = className.getDeclaredFields();
		for (Field f : fields) {
			Column col = f.getAnnotation(Column.class);
			JoinColumn jcol = f.getAnnotation(JoinColumn.class);
			if (col != null) {
				propertiesList.add(f.getName() + "~" + col.name());
			} else if (jcol != null) {
				propertiesList.add(f.getName() + "LineId~" + jcol.name());
			}
		}
		return propertiesList;
	}

	
}
