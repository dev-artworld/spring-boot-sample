package com.equinix.appops.dart.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.buisness.AppOpsEditDfrBusiness;
import com.equinix.appops.dart.portal.common.DartEntityConstants;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.dao.AppOppsDartHomeDao;
import com.equinix.appops.dart.portal.entity.DartResource;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.ProductConfig;
import com.equinix.appops.dart.portal.mapper.dto.pro.AssetCount;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrMasterHomeVO;
import com.equinix.appops.dart.portal.model.Dfr;
import com.equinix.appops.dart.portal.model.DfrResult;
import com.equinix.appops.dart.portal.model.DfrsByStatus;
import com.equinix.appops.dart.portal.model.Ibx;
import com.equinix.appops.dart.portal.model.ReportFilter;
import com.equinix.appops.dart.portal.model.TeamRegion;
import com.equinix.appops.dart.portal.service.AppOppsDartHomeService;
import com.equinix.appops.dart.portal.vo.DARTReportVO;

@Service
@Transactional
public class AppOppsDartHomeServiceImpl implements AppOppsDartHomeService {

	@Autowired
	AppOppsDartHomeDao oppsDartHomeDao;
	
	@Autowired
	AppOpsEditDfrBusiness appOpsEditDfrBusiness;

	@Override
	public List<Dfr> getAllDfrOrderedByCreatedDtDesc() {
		List<DfrMaster> dfrMaster = oppsDartHomeDao.getAllDfrOrderedByCreatedDtDesc();
		List<Dfr> dfrList = new ArrayList<>();
		for (DfrMaster dbDfr : dfrMaster) {
			Dfr dfr = new Dfr();
			dfr.setDfrId(dbDfr.getDfrId());
			dfr.setStatus(StringUtils.isNotEmpty(dbDfr.getStatus()) ? dbDfr.getStatus() : "NA");
			dfr.setAssignedTo(StringUtils.isNotEmpty(dbDfr.getAssignedTo()) ? dbDfr.getAssignedTo() : DartConstants.NA);
			dfr.setAssignedTeam(StringUtils.isNotEmpty(dbDfr.getAssignedTeam()) ? dbDfr.getAssignedTeam() : "NA");
			dfr.setNotes(StringUtils.isNotEmpty(dbDfr.getNotes()) ? dbDfr.getNotes() : "NA");
			dfr.setPendingSice(dbDfr.getPendingSince() != null
					? DartEntityConstants.DART_DATE_FORMAT.format(dbDfr.getPendingSince()) : "NA");
			dfr.setCreatedAt(dbDfr.getCreatedDt() != null
					? DartEntityConstants.DART_DATE_FORMAT.format(dbDfr.getCreatedDt()) : "NA");
			dfrList.add(dfr);
		}
		return dfrList;
	}

	@Override
	public TeamRegion getTeamRegions() {
		TeamRegion teamRegion = new TeamRegion();
		List<String> team = oppsDartHomeDao.getTeam();
		List<String> region = oppsDartHomeDao.getRegion();
		teamRegion.setTeam(team);
		teamRegion.setRegion(region);
		return teamRegion;
	}

	@Override
	public Ibx getIbxByRegion(String region) {
		Ibx ibx = new Ibx();
		List<String> ibxs = oppsDartHomeDao.getIBX();
		ibx.setRegion(region);
		ibx.setIbx(ibxs);
		return ibx;
	}

	@Override
	public DfrResult getDfrById(String dfrid) {
		DfrMaster dfrMaster = oppsDartHomeDao.getDfrById(dfrid);
		DfrResult dfrResult = new DfrResult();
		if (dfrMaster != null) {
			dfrResult.setFound(true);
		} else {
			dfrResult.setFound(false);
		}
		dfrResult.setDfrMaster(dfrMaster);
		return dfrResult;
	}

	@Override
	public Map<String, Map<String, String>> getDfrStatsByTeamOrRegion(String team, String region) {
		List<DfrMaster> dfrMaster = oppsDartHomeDao.getDfrStatsByTeamOrRegion(team, region);
		// Map<String,List<IndexingObject2>> usedCarCityListMap =
		// usedListFromDB.stream().collect(Collectors.groupingBy(IndexingObject2::getUsedCityUrl));
		Map<String, Map<String, String>> dfrStatusMap = new HashMap<>();

		if (CollectionUtils.isNotEmpty(dfrMaster)) {
			Map<String, List<DfrMaster>> mapByStatus = dfrMaster.stream()
					.collect(Collectors.groupingBy(DfrMaster::getStatus));
			for (Map.Entry<String, List<DfrMaster>> entry : mapByStatus.entrySet()) {
				Map<String, String> stats = new HashMap<>();
				stats.put("total", dfrMaster.size()+"");
				stats.put("status_count", entry.getValue().size()+"");
				stats.put("percentage", (entry.getValue().size() * 100 / dfrMaster.size())+"");
				stats.put("status", entry.getKey());
				String status = entry.getKey().replace("-", " ").replace(" ", "_").toLowerCase();
				dfrStatusMap.put(status, stats);
			}
		}
		return dfrStatusMap;
	}

	@Override
	public Map<String, Map<String, String>> getDfrStatsByTeamANDRegionAndIbx(String team, String region, String ibx) {
		List<DfrMaster> dfrMasterList = null;
		if (!ibx.equalsIgnoreCase("All")) {
			dfrMasterList = oppsDartHomeDao.getDfrStatsByTeamAndRegionAndIbx(team, region, ibx);

		} else {
			dfrMasterList = oppsDartHomeDao.getDfrStatsByTeamOrRegion(team, region);

		}
		Map<String, Map<String, String>> dfrStatusMap = new HashMap<>();

		if (CollectionUtils.isNotEmpty(dfrMasterList)) {
			Map<String, List<DfrMaster>> mapByStatus = dfrMasterList.stream()
					.collect(Collectors.groupingBy(DfrMaster::getStatus));
			for (Map.Entry<String, List<DfrMaster>> entry : mapByStatus.entrySet()) {
				Map<String, String> stats = new HashMap<>();
				stats.put("total", dfrMasterList.size() + "");
				stats.put("status_count", entry.getValue().size() + "");
				stats.put("percentage", (entry.getValue().size() * 100 / dfrMasterList.size()) + "");
				stats.put("status", entry.getKey());
				String status = entry.getKey().replace("-", " ").replace(" ", "_").toLowerCase();
				dfrStatusMap.put(status, stats);
			}
		}
		return dfrStatusMap;
	}
	
	@Override
	public DfrsByStatus getDfrsByStatus(String status, String team, String region) {
//		String dbStatus = DartEntityConstants.dbStatus(status);
		List<DfrMaster> dfrMaster = oppsDartHomeDao.getDfrsByStatus(status, team, region);
		DfrsByStatus dfrsByStatus = new DfrsByStatus();
		dfrsByStatus.setTeam(team);
		dfrsByStatus.setRegion(region);
		dfrsByStatus.setStatus(status);
		if (CollectionUtils.isNotEmpty(dfrMaster)) {
			populateDfrMasterResponse(dfrsByStatus, dfrMaster);
		}
		return dfrsByStatus;
	}

	@Override
	public DfrsByStatus getDfrsByIbx(String status, String team, String region, String ibx) {
		DfrsByStatus dfrsByStatus = new DfrsByStatus();
//		String dbStatus = DartEntityConstants.dbStatus(status);
		dfrsByStatus.setTeam(team);
		dfrsByStatus.setRegion(region);
		dfrsByStatus.setStatus(status);
		dfrsByStatus.setIbx(ibx);
		if (!ibx.equalsIgnoreCase("All")) {
			List<DfrMaster> dfrMasterList = oppsDartHomeDao.getSnapshotSiAssDaByDfrIdandIbx(status, team, region, ibx);
			populateDfrMasterResponse(dfrsByStatus, dfrMasterList);
		} else {
			List<DfrMaster> dfrMaster = oppsDartHomeDao.getDfrsByStatus(status, team, region);
			populateDfrMasterResponse(dfrsByStatus, dfrMaster);
		}
		return dfrsByStatus;
	}

	private void populateDfrMasterResponse(DfrsByStatus dfrsByStatus, List<DfrMaster> dfrFilterList) {
		if(CollectionUtils.isNotEmpty(dfrFilterList)){
			Set<String> dfrIdSet = new HashSet<String>();
			Set<String> statusSet = new HashSet<String>();
			Set<String> assignedTeamSet = new HashSet<String>();
			Set<String> createdBySet = new HashSet<String>();
			Set<String> createdAtSet = new HashSet<String>();
		
			for (DfrMaster master : dfrFilterList) {
				Dfr dfr = new Dfr();
				dfr.setDfrId(master.getDfrId());
				dfr.setAssignedTo(StringUtils.isNotEmpty(master.getAssignedTo()) ? master.getAssignedTo() : "NA");
				dfr.setAssignedTeam(StringUtils.isNotEmpty(master.getAssignedTeam()) ? master.getAssignedTeam() : "NA");
				dfr.setNotes(StringUtils.isNotEmpty(master.getNotes()) ? master.getNotes() : "NA");
				dfr.setPendingSice(master.getPendingSince() != null
						? DartEntityConstants.DART_DATE_FORMAT.format(master.getPendingSince()) : "NA");
				dfr.setCreatedAt(master.getCreatedDt() != null
						? DartEntityConstants.DART_DATE_FORMAT.format(master.getCreatedDt()) : "NA");
				dfr.setCreatedBy(StringUtils.isNotEmpty(master.getCreatedBy()) ? master.getCreatedBy() : "NA");
				if (!dfrIdSet.contains(dfr.getDfrId())) {
					dfrIdSet.add(dfr.getDfrId());
				}
				if (!statusSet.contains(dfr.getStatus())) {
					statusSet.add(dfr.getStatus());
				}
				if (!assignedTeamSet.contains(dfr.getAssignedTeam())) {
					assignedTeamSet.add(dfr.getAssignedTeam());
				}
				if (!createdBySet.contains(dfr.getCreatedBy())){
					createdBySet.add(dfr.getCreatedBy());
				}
				if (!createdAtSet.contains(dfr.getCreatedAt())) {
					createdAtSet.add(dfr.getCreatedAt());
				}
				dfrsByStatus.getDfrs().add(dfr);
			}
			if (CollectionUtils.isNotEmpty(dfrIdSet)) {
				dfrsByStatus.setDfrIdSet(dfrIdSet);
			}
			if (CollectionUtils.isNotEmpty(statusSet)) {
				dfrsByStatus.setStatusSet(statusSet);
			}
			if (CollectionUtils.isNotEmpty(assignedTeamSet)) {
				dfrsByStatus.setAssignedTeamSet(assignedTeamSet);
			}
			if (CollectionUtils.isNotEmpty(createdBySet)) {
				dfrsByStatus.setCreatedBySet(createdBySet);
			}
			if (CollectionUtils.isNotEmpty(createdAtSet)) {
				dfrsByStatus.setCreatedAtSet(createdAtSet);
			}
		}
	}
	
	@Override
	public DfrsByStatus getDfrByTeamAndStatus(String status, String team, String type) throws Exception{
		DfrsByStatus dfrsByStatus = new DfrsByStatus();
		dfrsByStatus.setTeam(team);
		dfrsByStatus.setStatus(status);
		List<DfrMaster> dfrMaster = oppsDartHomeDao.getDfrByTeamAndStatus(status, team, type);
	//	oppsDartHomeDao.getDfrByTeamAndStatusCount(dfrsByStatus,status, team, type);
		populateDfrMasterResp(dfrsByStatus, dfrMaster);
		return dfrsByStatus;
	}
	
	@Override
	public DfrsByStatus getDfrByTeamAndStatusVO(String status, String team, String type) throws Exception{
		DfrsByStatus dfrsByStatus = new DfrsByStatus();
		dfrsByStatus.setTeam(team);
		dfrsByStatus.setStatus(status);
		List<DfrMasterHomeVO> dfrMaster = oppsDartHomeDao.getDfrByTeamAndSts(status, team, type);
	//	oppsDartHomeDao.getDfrByTeamAndStatusCount(dfrsByStatus,status, team, type);
		populateDfrMasterRespVO(dfrsByStatus, dfrMaster);
		return dfrsByStatus;
	}
	
	@Override
	public DfrsByStatus getDfrByTeamAndStatusCount(String status, String team, String type) throws Exception{
		DfrsByStatus dfrsByStatus = new DfrsByStatus();
		dfrsByStatus.setTeam(team);
		dfrsByStatus.setStatus(status);
		oppsDartHomeDao.getDfrByTeamAndStatusCount(dfrsByStatus,status, team, type);
		return dfrsByStatus;
	}
	
	@Override
	public DfrsByStatus searchByDfrId(String dfrId) throws Exception{
		DfrsByStatus dfrsByStatus = new DfrsByStatus();
		List<DfrMasterHomeVO> dfrMaster = oppsDartHomeDao.getDfrByIdForVO(dfrId); 
		if(null!=dfrMaster){
//			List<DfrMasterHomeVO>  dfrMasterList = new ArrayList<DfrMasterHomeVO>();
//			dfrMasterList.add(dfrMaster);
			populateDfrMasterRespVO(dfrsByStatus,  dfrMaster);
		}
		return dfrsByStatus;
		
	}
	
	private void populateDfrMasterRespVO(DfrsByStatus dfrsByStatus, List<DfrMasterHomeVO> dfrFilterList) throws Exception{
		if(CollectionUtils.isNotEmpty(dfrFilterList)){
			Map<String,List<String>> teamToUsrMap = new HashMap<String,List<String>>();
			List<String> assignedTeams = oppsDartHomeDao.getAssignedTeams();
			for(String team:assignedTeams){
				if(team != null && !team.equalsIgnoreCase("null")){
					List<String> users = oppsDartHomeDao.getUserIdByGroupName(team);
					teamToUsrMap.put(team.toLowerCase(), users);
				}
				
			}
			
			for (DfrMasterHomeVO master : dfrFilterList) {
				Dfr dfr = new Dfr();
				dfr.setDfrId(master.getDfrId());
				dfr.setStatus(StringUtils.isNotEmpty(master.getStatus()) ? master.getStatus() : "NA");
				//set status "No Change" to "Cancelled"
				if(StringUtils.isNotEmpty(master.getStatus()) && master.getStatus().equalsIgnoreCase("No Change")){
					dfr.setStatus("Cancelled");
				}
				dfr.setAssignedTo(StringUtils.isNotEmpty(master.getAssignedTo()) ? master.getAssignedTo() : "NA");
				dfr.setAssignedDt(null!=master.getAssignedDt() ? DartEntityConstants.DART_DATE_FORMAT.format(master.getAssignedDt()) : "NA");
				dfr.setAssignedTeam(StringUtils.isNotEmpty(master.getAssignedTeam()) ? master.getAssignedTeam() : "NA");
				dfr.setPriority(StringUtils.isNotEmpty(master.getPriority()) ? master.getPriority() : "NA");
				dfr.setCreated_team(StringUtils.isNotEmpty(master.getCreatedTeam()) ? master.getCreatedTeam() : "NA");
				dfr.setCreatedAt(master.getCreatedDt() != null
						? DartEntityConstants.DART_DATE_FORMAT.format(master.getCreatedDt()) : "NA");
				dfr.setCreatedBy(StringUtils.isNotEmpty(master.getCreatedBy()) ? master.getCreatedBy() : "NA");
				dfr.setIbx(StringUtils.isNotEmpty(master.getIbx())? master.getIbx():"-");
				
				if(StringUtils.isNotEmpty(master.getAssignedTeam())){
					dfr.setAssigned_team_userlist(teamToUsrMap.get(master.getAssignedTeam().toLowerCase()));
				}
				dfrsByStatus.getDfrs().add(dfr);
			}
		}
	}
	
	private void populateDfrMasterResp(DfrsByStatus dfrsByStatus, List<DfrMaster> dfrFilterList) throws Exception{
		if(CollectionUtils.isNotEmpty(dfrFilterList)){
			for (DfrMaster master : dfrFilterList) {
				Dfr dfr = new Dfr();
				dfr.setDfrId(master.getDfrId());
				dfr.setStatus(StringUtils.isNotEmpty(master.getStatus()) ? master.getStatus() : "NA");
				//set status "No Change" to "Cancelled"
				if(StringUtils.isNotEmpty(master.getStatus()) && master.getStatus().equalsIgnoreCase("No Change")){
					dfr.setStatus("Cancelled");
				}
				dfr.setAssignedTo(StringUtils.isNotEmpty(master.getAssignedTo()) ? master.getAssignedTo() : "NA");
				dfr.setAssignedTeam(StringUtils.isNotEmpty(master.getAssignedTeam()) ? master.getAssignedTeam() : "NA");
				dfr.setPriority(StringUtils.isNotEmpty(master.getPriority()) ? master.getPriority() : "NA");
				dfr.setCreated_team(StringUtils.isNotEmpty(master.getCreatedTeam()) ? master.getCreatedTeam() : "NA");
				dfr.setCreatedAt(master.getCreatedDt() != null
						? DartEntityConstants.DART_DATE_FORMAT.format(master.getCreatedDt()) : "NA");
				dfr.setCreatedBy(StringUtils.isNotEmpty(master.getCreatedBy()) ? master.getCreatedBy() : "NA");
				if("Y".equalsIgnoreCase(master.getIsMobileDfr())){
					List<String> ibxList = appOpsEditDfrBusiness.getFilterListFromSnapshotDA("header8", master.getDfrId());
					if(CollectionUtils.isNotEmpty(ibxList))
						dfr.setIbx(ibxList.get(0));
					else
						dfr.setIbx("-");
				}else{
					dfr.setIbx("-");
				}
				if(StringUtils.isNotEmpty(master.getAssignedTeam())){
					dfr.setAssigned_team_userlist(oppsDartHomeDao.getUserIdByGroupName(master.getAssignedTeam()));
				}
				dfrsByStatus.getDfrs().add(dfr);
			}
		}
	}

	@Override
	public List<String> getDfrIdList() throws Exception {
		return  oppsDartHomeDao.getDfrIdList();
	}

	@Override
	public String getUserDfr(String userId) {

		return oppsDartHomeDao.getUserDfr(userId);
	}

	@Override
	public DfrsByStatus getRecentDfr(String userId) {
		String dfrIdList = oppsDartHomeDao.getUserDfr(userId);
		DfrsByStatus dfrByStatus = new DfrsByStatus();
		if (dfrIdList != null) {
			Set<String> dfrIdSet = new HashSet<String>();
			Set<String> statusSet = new HashSet<String>();
			Set<String> assignedTeamSet = new HashSet<String>();
			Set<String> createdBySet = new HashSet<String>();
			Set<String> createdAtSet = new HashSet<String>();
			String[] dfrArray = dfrIdList.split(",");
			for (int i = dfrArray.length - 1; i>= 0; i--) {
				Dfr dfr = new Dfr();
				DfrMaster dbDfr = oppsDartHomeDao.getDfrById(dfrArray[i]);
				if (dbDfr == null) {
					continue;
				} else {
					dfr.setDfrId(dbDfr.getDfrId());
					dfr.setStatus(StringUtils.isNotEmpty(dbDfr.getStatus()) ? dbDfr.getStatus() : "NA");
					dfr.setAssignedTo(
							StringUtils.isNotEmpty(dbDfr.getAssignedTo()) ? dbDfr.getAssignedTo() : DartConstants.NA);
					dfr.setAssignedTeam(
							StringUtils.isNotEmpty(dbDfr.getAssignedTeam()) ? dbDfr.getAssignedTeam() : "NA");
					dfr.setNotes(StringUtils.isNotEmpty(dbDfr.getNotes()) ? dbDfr.getNotes() : "NA");
					dfr.setPendingSice(dbDfr.getPendingSince() != null
							? DartEntityConstants.DART_DATE_FORMAT.format(dbDfr.getPendingSince()) : "NA");
					dfr.setCreatedAt(dbDfr.getCreatedDt() != null
							? DartEntityConstants.DART_DATE_FORMAT.format(dbDfr.getCreatedDt()) : "NA");
					dfr.setCreatedBy(StringUtils.isNotEmpty(dbDfr.getCreatedBy()) ? dbDfr.getCreatedBy() : "NA");
					dfrByStatus.getDfrs().add(dfr);
					if (!dfrIdSet.contains(dfr.getDfrId())) {
						dfrIdSet.add(dfr.getDfrId());
					}
					if (!statusSet.contains(dfr.getStatus())) {
						statusSet.add(dfr.getStatus());
					}
					if (!assignedTeamSet.contains(dfr.getAssignedTeam())) {
						assignedTeamSet.add(dfr.getAssignedTeam());
					}
					if (!createdBySet.contains(dfr.getCreatedBy())){
						createdBySet.add(dfr.getCreatedBy());
					}
					if (!createdAtSet.contains(dfr.getCreatedAt())) {
						createdAtSet.add(dfr.getCreatedAt());
					}
				}
			}
			if (CollectionUtils.isNotEmpty(dfrIdSet)) {
				dfrByStatus.setDfrIdSet(dfrIdSet);
			}
			if (CollectionUtils.isNotEmpty(statusSet)) {
				dfrByStatus.setStatusSet(statusSet);
			}
			if (CollectionUtils.isNotEmpty(assignedTeamSet)) {
				dfrByStatus.setAssignedTeamSet(assignedTeamSet);
			}
			if (CollectionUtils.isNotEmpty(createdBySet)) {
				dfrByStatus.setCreatedBySet(createdBySet);
			}
			if (CollectionUtils.isNotEmpty(createdAtSet)) {
				dfrByStatus.setCreatedAtSet(createdAtSet);
			}
			return dfrByStatus;
		} else {
			return dfrByStatus;
		}
	}
	
	@Override
	public List<DartResource> getAllDartResources(){
		return oppsDartHomeDao.getAllDartResources();
	}
	
	@Override
	public List<DARTReportVO> getDFRMasterByTeamRegionIBXDate(ReportFilter reportFilterObj) {
		Map<String, Object> responseMap = oppsDartHomeDao.getDFRMasterByTeamRegionIBXDate(reportFilterObj);
		List<DARTReportVO> dartReportVOList = (List<DARTReportVO>) responseMap.get("AssetMap");
		Map<String, List<DfrMaster>> dfrMasterMap = (Map<String, List<DfrMaster>>) responseMap.get("DFRMasterMap");
		Map<String, String> ibxCountryMap = oppsDartHomeDao.getIBXCountryForReport(reportFilterObj);
		dartReportVOList.forEach(dartReportVO -> {
			if (dfrMasterMap.containsKey(dartReportVO.getDfrId())) {
				DfrMaster dfrMasterObj = ((List<DfrMaster>) dfrMasterMap.get(dartReportVO.getDfrId())).get(0);
				if (dfrMasterObj != null) {
					dartReportVO.setCreatedBy(dfrMasterObj.getCreatedBy());
					dartReportVO.setCreatedDate(dfrMasterObj.getCreatedDt());
					dartReportVO.setCreatedTeam(dfrMasterObj.getCreatedTeam());
					dartReportVO.setIncident(dfrMasterObj.getIncident());
					dartReportVO.setAssignedTeam(dfrMasterObj.getAssignedTeam());
					dartReportVO.setNotes(dfrMasterObj.getNotes());
					dartReportVO.setStatus(dfrMasterObj.getStatus());
					dartReportVO.setAssignedDate(dfrMasterObj.getAssignedDt());
					dartReportVO.setRegion(dfrMasterObj.getRegion());
				}
			}
			if (ibxCountryMap.containsKey(dartReportVO.getIbx())) {
				dartReportVO.setCountry(ibxCountryMap.get(dartReportVO.getIbx()));
			}
		});
		return dartReportVOList;
	}
	
	@Override
	public List<ProductConfig> getAllProductConfig() {
		return oppsDartHomeDao.getAllProductConfig();
	}
	
	@Override
	public List<AssetCount> getCountByDfr(String dfrId) {
		return oppsDartHomeDao.getCountByDfr(dfrId);
	}
	
	@Override
	public List<String> ibxListForMoveAsset() {
		return oppsDartHomeDao.ibxListForMoveAsset();
	}
	
}
