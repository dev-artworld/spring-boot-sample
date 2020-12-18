package com.equinix.appops.dart.portal.dao;

import java.util.List;
import java.util.Map;

import com.equinix.appops.dart.portal.entity.DartResource;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.ProductConfig;
import com.equinix.appops.dart.portal.mapper.dto.pro.AssetCount;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrMasterHomeVO;
import com.equinix.appops.dart.portal.model.DfrsByStatus;
import com.equinix.appops.dart.portal.model.Ibx;
import com.equinix.appops.dart.portal.model.ReportFilter;

public interface AppOppsDartHomeDao {

	List<DfrMaster> getAllDfrOrderedByCreatedDtDesc();
	
	Ibx getIbxByRegion(String region);
	
	DfrMaster getDfrById(String dfrid);
	
	List<DfrMaster> getDfrStatsByTeamOrRegion(String team, String region);
	
	List<DfrMaster> getDfrsByStatus(String status,String team, String region);

	List<String> getTeam();

	List<String> getRegion();

	List<String> getIBX();
	
	//List<DfrMaster> getSnapshotSiAssDaByDfrIdandIbx(String dfrId,String ibx);
	
	List<String> getDfrIdList() throws Exception;
	
	int updateUserDfr(String userId, String dfrId);
	
	String getUserDfr(String userId);

	List<DfrMaster> getSnapshotSiAssDaByDfrIdandIbx(String status, String team, String region, String ibx);

	List<DfrMaster> getDfrStatsByTeamAndRegionAndIbx(String team, String region, String ibx);

	List<DartResource> getAllDartResources();

	Map<String, Object> getDFRMasterByTeamRegionIBXDate(ReportFilter reportFilterObj);

	Map<String, String> getIBXCountryForReport(ReportFilter reportFilterObj);

	List<ProductConfig> getAllProductConfig();
	
	List<String> getUserIdByGroupName(String groupName) throws Exception;
	
	List<DfrMaster> getDfrByTeamAndStatus(String status, String team, String type) throws Exception;
	
	void getDfrByTeamAndStatusCount(DfrsByStatus dfrsByStatus,String status, String team, String type) throws Exception;
	
	String getGroupNameByUserId(String userId) throws Exception;
	
	String resetGetDfrByTeamAndStatus();

	List<AssetCount> getCountByDfr(String dfrId);
	
	List<DfrMasterHomeVO> getDfrByTeamAndSts(String status, String team, String type) throws Exception;
	
	List<String> getAssignedTeams();
	
	List<DfrMasterHomeVO> getDfrByIdForVO(String dfrid);

	List<String> ibxListForMoveAsset();

}
