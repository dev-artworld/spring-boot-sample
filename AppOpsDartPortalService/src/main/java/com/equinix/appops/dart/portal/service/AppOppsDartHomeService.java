package com.equinix.appops.dart.portal.service;

import java.util.List;
import java.util.Map;

import com.equinix.appops.dart.portal.entity.DartResource;
import com.equinix.appops.dart.portal.entity.ProductConfig;
import com.equinix.appops.dart.portal.mapper.dto.pro.AssetCount;
import com.equinix.appops.dart.portal.model.Dfr;
import com.equinix.appops.dart.portal.model.DfrResult;
import com.equinix.appops.dart.portal.model.DfrsByStatus;
import com.equinix.appops.dart.portal.model.Ibx;
import com.equinix.appops.dart.portal.model.ReportFilter;
import com.equinix.appops.dart.portal.model.TeamRegion;
import com.equinix.appops.dart.portal.vo.DARTReportVO;

public interface AppOppsDartHomeService {
     
	List<Dfr> getAllDfrOrderedByCreatedDtDesc();
	
	TeamRegion getTeamRegions();
	
	Ibx getIbxByRegion(String region);
	
	DfrResult getDfrById(String dfrid);
	
	Map<String, Map<String, String>> getDfrStatsByTeamOrRegion(String team, String region);
	
	DfrsByStatus getDfrsByStatus(String status, String team, String region);
	
	DfrsByStatus getDfrsByIbx(String status, String team, String region,String ibx);
	
	List<String> getDfrIdList() throws Exception;
	
	String getUserDfr(String userId);
	
	DfrsByStatus getRecentDfr(String userId);

	Map<String, Map<String, String>> getDfrStatsByTeamANDRegionAndIbx(String team, String region, String ibx);
	
	List<DartResource> getAllDartResources();

	List<DARTReportVO> getDFRMasterByTeamRegionIBXDate(ReportFilter reportFilterObj);
	
	List<ProductConfig> getAllProductConfig();
	
	DfrsByStatus getDfrByTeamAndStatus(String status, String team, String type) throws Exception;
	
	DfrsByStatus getDfrByTeamAndStatusCount(String status, String team, String type) throws Exception;
	
	DfrsByStatus searchByDfrId(String dfrId) throws Exception;

	List<AssetCount> getCountByDfr(String dfrId);
	
	DfrsByStatus getDfrByTeamAndStatusVO(String status, String team, String type) throws Exception;

	List<String> ibxListForMoveAsset();
}
