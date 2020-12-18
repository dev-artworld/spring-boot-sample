package com.equinix.appops.dart.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.equinix.appops.dart.portal.constant.MSNativeQueries;
import com.equinix.appops.dart.portal.constant.ProNativeQueries;
import com.equinix.appops.dart.portal.dao.AppOppsDartHomeDao;
import com.equinix.appops.dart.portal.entity.AssignGroup;
import com.equinix.appops.dart.portal.entity.DartResource;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.ProductConfig;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcIbxCountry;
import com.equinix.appops.dart.portal.entity.UserInfo;
import com.equinix.appops.dart.portal.mapper.dto.pro.AssetCount;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrMasterHomeVO;
import com.equinix.appops.dart.portal.mapper.pro.AssetCountMapper;
import com.equinix.appops.dart.portal.mapper.pro.DfrMasterHomeMapper;
import com.equinix.appops.dart.portal.model.Dfr;
import com.equinix.appops.dart.portal.model.DfrsByStatus;
import com.equinix.appops.dart.portal.model.Ibx;
import com.equinix.appops.dart.portal.model.ReportFilter;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;
import com.equinix.appops.dart.portal.vo.DARTReportVO;
import com.google.common.collect.Lists;

@Repository
public class AppOppsDartHomeDaoImpl implements AppOppsDartHomeDao {
	
	Logger logger = LoggerFactory.getLogger(AppOppsDartHomeDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
	NamedParameterJdbcTemplate namedJdbc; 
	
	@Override
	public List<DfrMaster> getAllDfrOrderedByCreatedDtDesc() {
		Session session = sessionFactory.getCurrentSession();
		// return
		// session.createCriteria(DfrMaster.class).add(Restrictions.isNotNull("region")).addOrder(Order.desc("createdDt")).list();
		return session.createCriteria(DfrMaster.class).addOrder(Order.desc("createdDt")).list();
	}

	@Override
	public Ibx getIbxByRegion(String region) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcIbxCountry.class);
		List<String> ibx;

		Ibx ibxObject = new Ibx();

		if (region != null && !region.equalsIgnoreCase("")) {
			if (region.equalsIgnoreCase("all")) {
				ibx = criteria.setProjection(Projections.property("ibx")).list();
			} else {
				ibx = criteria.add(Restrictions.eq("region", region)).setProjection(Projections.property("ibx")).list();
			}
			ibxObject.setRegion(region);
			for (Object ibxName : ibx) {
				ibxObject.getIbx().add((String) ibxName);
			}
		}
		return ibxObject;
	}

	@Override
	public DfrMaster getDfrById(String dfrid) {
		Session session = sessionFactory.getCurrentSession();
		DfrMaster dfrMaster = (DfrMaster) session.createCriteria(DfrMaster.class).add(Restrictions.eq("dfrId", dfrid))
				.uniqueResult();
		return dfrMaster;
	}

	@Override
	public List<DfrMaster> getDfrStatsByTeamOrRegion(String team, String region) {
		Session session = sessionFactory.getCurrentSession();
		List<DfrMaster> dfrList = new ArrayList<>();
		Criteria criteria  = session.createCriteria(DfrMaster.class);
		if (team != null && region != null) {
			if (team.equalsIgnoreCase("all") && region.equalsIgnoreCase("all")) {
				// dfrList =
				// session.createCriteria(DfrMaster.class).add(Restrictions.isNotNull("region")).list();
				criteria.add(Restrictions.isNotNull("createdDt"));
			} else if (team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all")) {
				// region provided
				criteria.add(Restrictions.isNotNull("region"))
				.add(Restrictions.eq("region", region));
			} else if (!team.equalsIgnoreCase("all") && region.equalsIgnoreCase("all")) {
				// team provided
				criteria/*.add(Restrictions.isNotNull("region"))*/
				.add(Restrictions.eq("assignedTeam", team));
			} else if (!team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all")) {
				// team provided
				criteria.add(Restrictions.isNotNull("region"))
				.add(Restrictions.eq("assignedTeam", team))
				.add(Restrictions.and(Restrictions.eq("region", region)));
			}
			ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);

			while (scrollableResults.next()) {
				DfrMaster dfrMaster = (DfrMaster) scrollableResults.get()[0];
				dfrList.add(dfrMaster);
			}
		}
		return dfrList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DfrMaster> getDfrStatsByTeamAndRegionAndIbx(String team, String region, String ibx) {
		Session session = sessionFactory.getCurrentSession();
		List<DfrMaster> dfrMasters = new ArrayList<>();
		if (!ibx.equalsIgnoreCase("all")) {
			Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
			criteria
					// .add(Restrictions.eq("dfrId", dfrId))
					.add(Restrictions.and(Restrictions.eq("header8", ibx)))
					.add(Restrictions.and(Restrictions.eq("header57", "Y"))).setProjection(Projections
							.distinct(Projections.projectionList().add(Projections.property("dfrId"), "dfrId")));
			List<String> list = criteria.list();

			if (!CollectionUtils.isEmpty(list)) {
				Criteria criteria2 = session.createCriteria(DfrMaster.class);

				if (!team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all")) {
					Conjunction conjunction = Restrictions.conjunction();
					conjunction.add(Restrictions.eq("assignedTeam", team));
					conjunction.add(Restrictions.eq("region", region));
					criteria2.add(conjunction);
				} else if (team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all")) {
					Conjunction conjunction = Restrictions.conjunction();
					conjunction.add(Restrictions.eq("region", region));
					criteria2.add(conjunction);
				} else if (!team.equalsIgnoreCase("all") && region.equalsIgnoreCase("all")) {
					Conjunction conjunction = Restrictions.conjunction();
					conjunction.add(Restrictions.eq("assignedTeam", team));
					criteria2.add(conjunction);
				}
				criteria2.add(Restrictions.in("dfrId", list.toArray()));
				ScrollableResults scrollableResults = criteria2.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					DfrMaster dfrMaster = (DfrMaster) scrollableResults.get()[0];
					dfrMasters.add(dfrMaster);
				}
			}
		} 
		return dfrMasters;
	}

	@Override
	public List<DfrMaster> getDfrsByStatus(String status, String team, String region) {
		Session session = sessionFactory.getCurrentSession();

		List<DfrMaster> dfrMasters = new ArrayList<>();
		if (StringUtils.isNotEmpty(status)) {

			if (team.equalsIgnoreCase("all") && region.equalsIgnoreCase("all")) {
				Criteria criteria = session.createCriteria(DfrMaster.class);
				criteria.add(Restrictions.eq("status", status));
				ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				
				while (scrollableResults.next()) {
					DfrMaster dfrMaster = (DfrMaster) scrollableResults.get()[0];
					dfrMasters.add(dfrMaster);
				}
				return dfrMasters;
				// return
				// session.createCriteria(DfrMaster.class).add(Restrictions.eq("status",
				// status)).list();
			} else if (team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all")) {
				// region provided
				return session.createCriteria(DfrMaster.class).add(Restrictions.eq("region", region))
						.add(Restrictions.and(Restrictions.eq("status", status))).list();
			} else if (!team.equalsIgnoreCase("all") && region.equalsIgnoreCase("all")) {
				// team provided
				return session.createCriteria(DfrMaster.class).add(Restrictions.eq("assignedTeam", team))
						.add(Restrictions.and(Restrictions.eq("status", status))).list();
			} else if (!team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all")) {
				// team provided
				return session.createCriteria(DfrMaster.class).add(Restrictions.eq("assignedTeam", team))
						.add(Restrictions.and(Restrictions.eq("region", region)))
						.add(Restrictions.and(Restrictions.eq("status", status))).list();
			}
		}
		return null;
	}

	@Override
	public List<String> getTeam() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AssignGroup.class).setProjection(
				Projections.distinct(Projections.projectionList().add(Projections.property("groupName"), "groupName")));
		return criteria.list();

	}

	@Override
	public List<String> getRegion() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcIbxCountry.class).setProjection(
				Projections.distinct(Projections.projectionList().add(Projections.property("region"), "region")));
		return criteria.list();

	}

	@Override
	public List<String> getIBX() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class).add(Restrictions.eq("header57", "Y"))
				.setProjection(Projections
						.distinct(Projections.projectionList().add(Projections.property("header8"), "header8")));
		return criteria.list();
	}

	@Override
	public List<DfrMaster> getSnapshotSiAssDaByDfrIdandIbx(String status,String team,String region,String ibx) {
		Session session = sessionFactory.getCurrentSession();
		List<DfrMaster> dfrMasters = new ArrayList<>();
		if (!ibx.equalsIgnoreCase("all")) {
			Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
			criteria
			//.add(Restrictions.eq("dfrId", dfrId))
			.add(Restrictions.and(Restrictions.eq("header8", ibx)))
			.add(Restrictions.and(Restrictions.eq("header57", "Y")))
			.setProjection(Projections
					.distinct(Projections.projectionList().add(Projections.property("dfrId"), "dfrId")));
			List<String> list = criteria.list();

			if(!CollectionUtils.isEmpty(list)){
				Criteria criteria2 = session.createCriteria(DfrMaster.class);
				
				if(!team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all") ){
					Conjunction conjunction = Restrictions.conjunction();
					conjunction.add(Restrictions.eq("status",status));
					conjunction.add(Restrictions.eq("assignedTeam",team));
					conjunction.add(Restrictions.eq("region",region));
					criteria2.add(conjunction);
				}else if (team.equalsIgnoreCase("all") && !region.equalsIgnoreCase("all") ){
					Conjunction conjunction = Restrictions.conjunction();
					conjunction.add(Restrictions.eq("status",status));
					conjunction.add(Restrictions.eq("region",region));
					criteria2.add(conjunction);
				}else if(!team.equalsIgnoreCase("all") && region.equalsIgnoreCase("all") ){
					Conjunction conjunction = Restrictions.conjunction();
					conjunction.add(Restrictions.eq("assignedTeam",team));
					conjunction.add(Restrictions.eq("status",status));
					criteria2.add(conjunction);
				}
				criteria2.add(Restrictions.in("dfrId",list.toArray()));
				ScrollableResults scrollableResults = criteria2.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
				while (scrollableResults.next()) {
					DfrMaster dfrMaster = (DfrMaster) scrollableResults.get()[0];
					dfrMasters.add(dfrMaster);
				}
			}
		}
		/*else{
			Criteria criteria = session.createCriteria(SnapshotSiebelAssetDa.class);
			ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
			while (scrollableResults.next()) {
				DfrMaster dfrMaster = (DfrMaster) scrollableResults.get()[0];
				dfrMasters.add(dfrMaster);
			}
		}*/
		return dfrMasters;

	}


	@Override
	public List<String> getDfrIdList() throws Exception{	   
	     return jdbc.queryForList("SELECT DFR_ID FROM EQX_DART.DFR_MASTER where IS_MS_DFR='Y'",String.class);
	}


	@Override
	public int updateUserDfr(String userId, String dfrId) {
	
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UserInfo u set u.recentDfr= :recentDfr where u.userId = :userId").setString("recentDfr", dfrId).setString("userId", userId);
		int status  = query.executeUpdate();
		return status;
		
	}
	
	@Override
	public String getUserDfr(String userId) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(UserInfo.class);
		criteria.add(Restrictions.eq("userId", userId))
		.setProjection(Projections
			.distinct(Projections.projectionList().add(Projections.property("recentDfr"), "recentDfr")));
		String recentDfr = (String)criteria.uniqueResult();
		return recentDfr;
	}
	
	@Override
	public List<DartResource> getAllDartResources(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DartResource.class);
		return (List<DartResource>)criteria.list();
	}
	
	@Override
	public Map<String,Object> getDFRMasterByTeamRegionIBXDate(ReportFilter reportFilterObj) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DfrMaster.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setFetchMode("approvalHistories", FetchMode.JOIN);
		if (StringUtils.isNotEmpty(reportFilterObj.getTeam()) && !reportFilterObj.getTeam().equalsIgnoreCase("All")) {
			criteria.add(Restrictions.eq("assignedTeam", reportFilterObj.getTeam()));
		}
		if (StringUtils.isNotEmpty(reportFilterObj.getRegion()) && !reportFilterObj.getRegion().equalsIgnoreCase("All")) {
			criteria.add(Restrictions.eq("region", reportFilterObj.getRegion()));
		}
		if (reportFilterObj.getFromDate() != null) {
			criteria.add(Restrictions.sqlRestriction("TO_DATE({alias}.CREATED_DT,'dd/mm/rrrr') >= " +
					"TO_DATE(?,'dd/mm/rrrr')",reportFilterObj.getFromDate(),new DateType()));
		}
		if (reportFilterObj.getToDate() != null) {
			criteria.add(Restrictions.sqlRestriction("TO_DATE({alias}.CREATED_DT,'dd/mm/rrrr') <= " +
					"TO_DATE(?,'dd/mm/rrrr')",reportFilterObj.getToDate(),new DateType()));
		}
		Map<String,List<DfrMaster>> dfrMasterMap = ((List<DfrMaster>) criteria.list()).stream().collect(Collectors.groupingBy(DfrMaster::getDfrId));
		Set<String> dfrIdSet = null;
		if (!CollectionUtils.isEmpty(dfrMasterMap)) {
			dfrIdSet = dfrMasterMap.keySet();
		}
		List<DARTReportVO> reportVOList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(dfrIdSet)) {
			if (dfrIdSet != null && dfrIdSet.size() > 999) {
				Criteria siebelAssetCriteria = session.createCriteria(SnapshotSiebelAssetDa.class);
				siebelAssetCriteria.add(Restrictions.eq("header57", "Y"));
				if (StringUtils.isNotEmpty(reportFilterObj.getIbx())
						&& !reportFilterObj.getIbx().equalsIgnoreCase("All")) {
					siebelAssetCriteria.add(Restrictions.eq("header8", reportFilterObj.getIbx()));
				}
				ProjectionList pList = Projections.projectionList();
				pList.add(Projections.property("dfrId"));
				pList.add(Projections.property("header8"));
				siebelAssetCriteria.setProjection(Projections.distinct(pList));
				Lists.partition(Arrays.asList(dfrIdSet.toArray()), 999).stream().forEach( dfrIdList -> {
					siebelAssetCriteria.add(Restrictions.in("dfrId", dfrIdList));
					ScrollableResults scrollableResults = siebelAssetCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
					DARTReportVO reportVO = null;
					while (scrollableResults.next()) {
						reportVO = new DARTReportVO();
						reportVO.setDfrId((String) scrollableResults.get()[0]);
						reportVO.setIbx((String) scrollableResults.get()[1]);
						reportVOList.add(reportVO);
					}
				});
			} else {
				Criteria siebelAssetCriteria = session.createCriteria(SnapshotSiebelAssetDa.class);
				siebelAssetCriteria.add(Restrictions.eq("header57", "Y"));
				siebelAssetCriteria.add(Restrictions.in("dfrId", dfrIdSet));
				if (StringUtils.isNotEmpty(reportFilterObj.getIbx())
						&& !reportFilterObj.getIbx().equalsIgnoreCase("All")) {
					siebelAssetCriteria.add(Restrictions.eq("header8", reportFilterObj.getIbx()));
				}
				ProjectionList pList = Projections.projectionList();
				pList.add(Projections.property("dfrId"));
				pList.add(Projections.property("header8"));
				siebelAssetCriteria.setProjection(Projections.distinct(pList));
				ScrollableResults scrollableResults = siebelAssetCriteria.setReadOnly(true)
						.scroll(ScrollMode.FORWARD_ONLY);
				DARTReportVO reportVO = null;
				while (scrollableResults.next()) {
					reportVO = new DARTReportVO();
					reportVO.setDfrId((String) scrollableResults.get()[0]);
					reportVO.setIbx((String) scrollableResults.get()[1]);
					reportVOList.add(reportVO);
				}
			}
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("DFRMasterMap", dfrMasterMap);
		resultMap.put("AssetMap", reportVOList);
		return resultMap;
	}	
	
	@Override
	public Map<String,String> getIBXCountryForReport(ReportFilter reportFilterObj) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcIbxCountry.class);
		if (StringUtils.isNotEmpty(reportFilterObj.getIbx()) && !reportFilterObj.getIbx().equalsIgnoreCase("All")) {
			criteria.add(Restrictions.eq("ibx", reportFilterObj.getIbx()));
		}
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("ibx"));
		pList.add(Projections.property("region"));
		pList.add(Projections.property("country"));
		criteria.setProjection(pList);
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		Map<String,String> ibxCountry = new HashMap<String,String>();
		while (scrollableResults.next()) {
			ibxCountry.put((String)scrollableResults.get()[0],(String)scrollableResults.get()[2]);
		}
		return ibxCountry;
	}
	
	@Override
	public List<ProductConfig> getAllProductConfig() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ProductConfig.class);
		return criteria.list();
	}
	
	@Override
//	@Cacheable(value="getDfrByTeamAndStatus",key="#status.concat('-').concat(#team).concat('-').concat(#type)")
	public List<DfrMaster> getDfrByTeamAndStatus(String status, String team, String type) throws Exception{
		Session session = sessionFactory.getCurrentSession();
		
		List<DfrMaster> dfrMasters = new ArrayList<>();
		if (StringUtils.isNotEmpty(status)) {
			String[] statusArr = status.split("\\s*,\\s*");
			
			Criteria criteria = session.createCriteria(DfrMaster.class);
			criteria.add(Restrictions.in("status", statusArr));
			if (!team.equalsIgnoreCase("all")) {
				criteria.add(Restrictions.eq("assignedTeam", team));
			}
			if(type.equalsIgnoreCase("my dfr")){
				User user = UserThreadLocal.userThreadLocalVar.get();
				Disjunction userIdOr = Restrictions.disjunction();
				userIdOr.add(Restrictions.eq("assignedTo",user.getUserId()));
			//	userIdOr.add(Restrictions.eq("createdBy",user.getUserId()));
				criteria.add(userIdOr);
			}else if(type.equalsIgnoreCase("unassigned")){
				criteria.add(Restrictions.isNull("assignedTo"));
			}
			ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
			
			while (scrollableResults.next()) {
				DfrMaster dfrMaster = (DfrMaster) scrollableResults.get()[0];
				dfrMasters.add(dfrMaster);
			}
			
			return dfrMasters;
		}
		return null;
	}
	
	@Override
	@CacheEvict(value="getDfrByTeamAndStatus",allEntries = true)
	public String resetGetDfrByTeamAndStatus() {
		return "getDfrByTeamAndStatus refreshed...";
	}
	
	public void getDfrByTeamAndStatusCount(DfrsByStatus dfrsByStatus,String status, String team, String type) throws Exception{
		Session session = sessionFactory.getCurrentSession();
		
		if (StringUtils.isNotEmpty(status)) {
			String[] statusArr = status.split("\\s*,\\s*");
			List<String> statusList = new ArrayList<String>(Arrays.asList(statusArr));
			HashMap<String,String> countMap = new HashMap<String,String>();
			//count based on status
			if(type.equals("inProgress")){
				for(String statusStr:statusList){
					User user = UserThreadLocal.userThreadLocalVar.get();
					String inProgressQuery = "SELECT count(*)  FROM DfrMaster"
							+" WHERE createdBy = '"+user.getUserId()+"'"
							+" and createdTeam <> assignedTeam "
							+ "and IS_MS_DFR = 'Y'";
					if(statusStr.equalsIgnoreCase("No Change"))
						continue;
				if(statusStr.equalsIgnoreCase("Cancelled") || statusStr.equalsIgnoreCase("Completed")){
					continue;
				}else{
					inProgressQuery+= " and  status = '"+  statusStr + "'";
				}
							
				Query query = session.createQuery(inProgressQuery);
				@SuppressWarnings("rawtypes")
				List counts = query.list();
				countMap.put(statusStr.replaceAll("\\s+","_"),counts.get(0).toString());
				}
			}else{
			for(String statusStr:statusList){
				Criteria criteria = session.createCriteria(DfrMaster.class);
				criteria.add(Restrictions.eq("isMsDfr","Y"));
				if(statusStr.equalsIgnoreCase("No Change"))
						continue;
				if(statusStr.equalsIgnoreCase("Cancelled")){
					Disjunction cancelledOrNochnge = Restrictions.disjunction();
					cancelledOrNochnge.add(Restrictions.eq("status",statusStr));
					cancelledOrNochnge.add(Restrictions.eq("status","No Change"));
					criteria.add(cancelledOrNochnge);
				}else{
					criteria.add(Restrictions.eq("status", statusStr));
				}
				
				if (!team.equalsIgnoreCase("all")) {
					criteria.add(Restrictions.eq("assignedTeam", team));
				}
				if(type.equalsIgnoreCase("my dfr")){
					User user = UserThreadLocal.userThreadLocalVar.get();
					criteria.add(Restrictions.eq("assignedTo",user.getUserId()));
				}else if(type.equalsIgnoreCase("unassigned")){
					criteria.add(Restrictions.isNull("assignedTo"));
				}else if(type.equalsIgnoreCase("my team")){
					criteria.add(Restrictions.isNotNull("assignedTo"));
				}
				criteria.setProjection(Projections.rowCount());
				Long count = (Long)criteria.uniqueResult();
				countMap.put(statusStr.replaceAll("\\s+","_"),count.toString());
			}
			}
			//my dfr count			
			Criteria criteria = session.createCriteria(DfrMaster.class);
			criteria.add(Restrictions.eq("isMsDfr","Y"));
			if(!statusList.contains("Completed")){
				statusList.add("Completed");
				statusArr = new String[statusList.size()];
				statusArr = statusList.toArray(statusArr);
			}
		
			criteria.add(Restrictions.in("status", statusArr));
			
			
			User user = UserThreadLocal.userThreadLocalVar.get();
			Disjunction userIdOr = Restrictions.disjunction();
			userIdOr.add(Restrictions.eq("assignedTo",user.getUserId()));
			criteria.add(userIdOr);
			criteria.setProjection(Projections.rowCount());
			Long count = (Long)criteria.uniqueResult();
			countMap.put("myDfr",count.toString());
			
			//For My Team and Unassigned, remove Completed DFRs
			if(statusList.contains("Completed")){
				statusList.remove("Completed");
				statusArr = new String[statusList.size()];
				statusArr = statusList.toArray(statusArr);
			}
			//unassigned count
			criteria = session.createCriteria(DfrMaster.class);
			criteria.add(Restrictions.eq("isMsDfr","Y"));
			criteria.add(Restrictions.in("status", statusArr));
			criteria.add(Restrictions.isNull("assignedTo"));
			if (team.equalsIgnoreCase("all")) {
				String grpName = getGroupNameByUserId(user.getUserId());
				criteria.add(Restrictions.eq("assignedTeam", grpName));
			}else{
				criteria.add(Restrictions.eq("assignedTeam", team));
			}
			criteria.setProjection(Projections.rowCount());
			count = (Long)criteria.uniqueResult();
			countMap.put("unassigned",count.toString());
			
			//My team Dfr count
			criteria = session.createCriteria(DfrMaster.class);
			criteria.add(Restrictions.eq("isMsDfr","Y"));
			criteria.add(Restrictions.in("status", statusArr));
			criteria.add(Restrictions.isNotNull("assignedTo"));
			if (team.equalsIgnoreCase("all")) {
				String grpName = getGroupNameByUserId(user.getUserId());
				criteria.add(Restrictions.eq("assignedTeam", grpName));
			}else{
				criteria.add(Restrictions.eq("assignedTeam", team));
			}
			criteria.setProjection(Projections.rowCount());
			count = (Long)criteria.uniqueResult();
			countMap.put("myTeam",count.toString());
			
			
			/**
			 * In-Progress
			 * 
			 * 
			 */
			
			String inProgressQuery = "SELECT count(*)  FROM DfrMaster"
			+" WHERE createdBy = '"+user.getUserId()+"'"
			+" and createdTeam <> assignedTeam"
			+" and status not in ('Completed','Cancelled')"
			+ " and IS_MS_DFR = 'Y'";
			
			Query query = session.createQuery(inProgressQuery);
			@SuppressWarnings("rawtypes")
			List counts = query.list();
			countMap.put("inProgress",counts.get(0).toString());
			dfrsByStatus.setCountMap(countMap);
		}

	}
	
	@Override
	public String getGroupNameByUserId(String userId) throws Exception{
		Object args[] = new Object []{userId};
		return jdbc.queryForObject(MSNativeQueries.GROUP_BY_USR_QUERY,  args , String.class);
		
	}
	
	@Override
	public List<String> getUserIdByGroupName(String groupName) throws Exception{
		
		Object args[] = new Object []{groupName};
		return jdbc.query(ProNativeQueries.USR_BY_GROUP_QUERY,args, new RowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) 
					throws SQLException {
				return rs.getString("userId");
			}
		});
		
	}
	
	@Override
	public List<AssetCount> getCountByDfr(String dfrId) {
		return (List<AssetCount>)jdbc.query(MSNativeQueries.ASSET_COUNT_BY_DFR.replace("#dfrId#", dfrId), new AssetCountMapper());
		
	}
	
	@Override
	public List<String> getAssignedTeams() {
		
		return jdbc.query(ProNativeQueries.ASGND_TEAM_DFRMSTR,new RowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) 
					throws SQLException {
				return rs.getString("ASSIGNED_TEAM");
			}
		});
		
	}
	
	@Override
//	@Cacheable(value="getDfrByTeamAndStatus",key="#status.concat('-').concat(#team).concat('-').concat(#type)")
	public List<DfrMasterHomeVO> getDfrByTeamAndSts(String status, String team, String type) throws Exception{
		List<String> ids = Arrays.asList(status.split(","));
		
		if (StringUtils.isNotEmpty(status)){
			String sql = MSNativeQueries.DFR_BY_TEAM_AND_STS;
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ids", ids);
			
			if (!team.equalsIgnoreCase("all") && StringUtils.isNotEmpty(team) && StringUtils.isNotBlank(team)) {
				sql = sql +" and ASSIGNED_TEAM = (:assignedTeam)";
				params.addValue("assignedTeam", team);
			}
			if(type.equalsIgnoreCase("my dfr")){
				User user = UserThreadLocal.userThreadLocalVar.get();
				if(null == user){
					return null;
				}
				sql = sql +" and ASSIGNED_TO = (:assignedTo)";
				params.addValue("assignedTo", user.getUserId());
			}else if(type.equalsIgnoreCase("unassigned")){
				sql = sql +" and ASSIGNED_TO is null";
			}else if(type.equalsIgnoreCase("my team")){
				sql = sql +" and ASSIGNED_TO is not null";
			}else if(type.equalsIgnoreCase("inProgress")){
				User user = UserThreadLocal.userThreadLocalVar.get();
				if(null == user){
					return null;
				}
				sql += "and CREATED_BY = '"+user.getUserId()+"' and created_team <> assigned_team";
			}
			
			return (List<DfrMasterHomeVO>)namedJdbc.query(sql,params,new DfrMasterHomeMapper());
		
		}
		return null;
	}
	
	@Override
	public List<DfrMasterHomeVO> getDfrByIdForVO(String dfrId){
		String query = ProNativeQueries.GET_DFR_BY_ID_FOR_HOME;
		String finalQuery = query.replace("##GLOBALSEARCHQ##", ProNativeQueries.GLOBAL_SEARCH_Q);
		List<DfrMasterHomeVO> dfrMasterHomeVOList =  (List<DfrMasterHomeVO>)jdbc.query(finalQuery
				.replace("#keyword#", dfrId), new DfrMasterHomeMapper());
		if(CollectionUtils.isEmpty(dfrMasterHomeVOList))
			return null;
		return dfrMasterHomeVOList;
	}
	
	@Override
	public List<String> ibxListForMoveAsset() {
	
		return jdbc.query(ProNativeQueries.IBX_LIST_FOR_ASSET_MOVE, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("IBX");
			}
		});
	}
}
