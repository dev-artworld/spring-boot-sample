package com.equinix.appops.dart.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrLineIdsVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.model.errorsection.SnapshotErrorData;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.SnapshotProductFilterResult;
import com.equinix.appops.dart.portal.vo.ChangeSummaryDTO;

public interface AppOpsDartEditDfrDao {

	List<String> getFilterListFromSnapshotDA(String header, String dfrId);

	List<String> getFilterListFromErrorSnapshotError(String dfrId);

	HashMap<String, Object> getProductFilters(ProductFilter productFilter);

	List<SnapshotSiebelAssetDa> globalSearch(ProductFilter filter);

	List<SnapshotErrorData> getErrorSection(ProductFilter productFilter);

	List<SnapshotSiebelAssetDa> getSiebelAssetDaDataByProduct(ProductFilter productFilter, String product);

	List<SnapshotSvAssetDa> getSnapshotSvAssetDaData(ProductFilter productFilter);

	List<SnapshotClxAssetDa> getSnapshotClxAssetDaData(ProductFilter productFilter);

	List<SnapshotSiebelAssetDa> getSnapshotSiebleAssetDaData(ProductFilter productFilter);

	List<SnapshotSiebelAssetDa> getHierarchyView(ProductFilter productFilter);

	List<AssetNewVal> getEditedAssets(String dfrId);

	List<AssetNewVal> getEditedAssets(String dfrId, String... assetName);

	AssetNewVal getAssetNewValByDfrLineId(String dfrLineId);

	DfrMaster getDfrMaster(String dfrId, String fetchMode);

	String getAssignedGroupByReqionAndSystem(String region, String sot);

	SnapshotSiebelAssetDa getSnapshotSblAssetDaData(String dfrLineID);

	AssetNewVal getAssetNewVal(String dfrLineID);

	SnapshotProductFilterResult getProductFilterResult(SnapshotProductFilterResult productFilterResult);

	List<SnapshotSiebelAssetDa> getSnapshotSblAssetDaByRowsIds(List<String> rowIds);

	List<SnapshotSvAssetDa> getSnapshotSvAssetDaByRowsIds(List<String> rowIds);

	List<SnapshotClxAssetDa> getSnapshotClxAssetDaByRowsIds(List<String> rowIds);

	List<AssetNewVal> getAssetNewValBySblRowIds(Set<String> sblDfrLineSet);

	List<String> getEmailIdByAssignGroup(String assignmentGroup);

	Map<String, Object> getPhysicalAuditDownloadData(String dfrId);

	List<DfrMaster> getDfrMasterCompleted(String emailFlag);

	void saveOrUpdatePhysicalAuditData(List<SnapshotSiebelAssetDa> assetList, List<AssetNewVal> assetNewValListInsert,
			List<AssetNewVal> assetNewValListUpdate, List<SaveAssetForm> saveAssetForms);

	String getAssetXaSeq();

	String getValidStatus(String dfrId);

	List<String> getRegionFilter(String dfrId);

	void saveOrUpdateChangeSummaryList(List<ChangeSummary> changeSummaryList);

	void saveOrUpdateChangeSummary(ChangeSummary changeSummary);

	ChangeSummary getChangeSummary(String dfrLineId, String attrName);

	List<ChangeSummary> getChangeSummaryList(String dfrId);

	long getNextValueOfDFRLineIDSeq();

	Map<String, String> getAssetNumberByDfrLineID(Set<String> dfrLineIdSet);

	List<ChangeSummaryDTO> getChangeSummaryDTO(String dfrLineId);

	void deleteAssetNewVal(String dfrLineId);

	void deleteSnapshotSiebelAsseDa(String dfrLineId);

	long getNextValueOfSerialNumber();

	SnapshotProductFilterResult getAccountMoveAttributeView(String dfrId,
			SnapshotProductFilterResult productFilterResult);

	public List<AssetNewVal> getAssetNewValByDfrIdAndProd(String dfrId, String product);

	List<SnapshotSiebelAssetDa> getSnapshotPaginatedSiebleAssetDaData(ProductFilter productFilter);

	public int deleteSnapshotSiebelAsseDaByDfrLineIds(List<String> dfrLineIds);

	public int deleteAssetNewValByDfrLineIds(List<String> dfrLineIds);

	public int deleteCxiErrTblByDfrLineIds(List<String> dfrLineIds);

	void deleteDfrmaster(String dfrId);

	String getNewPOEAssetNumber();

	SnapshotProductFilterResult getProductPhysicalAuditFilterResult(SnapshotProductFilterResult productFilterResult);

	List<ErrorCodeVO> validationsOnSubmit(String dfrId) throws Exception;

	SnapshotProductFilterResult productFilterResultByLineId(SaveAssetForm data);

	SnapshotProductFilterResult getAllProductFilterResult(SnapshotProductFilterResult productFilterResult);

	List<ErrorCardVO> getTotalErrorAssetById(String dfrId);

	List<String> getChangesMadeById(String dfrId);

	List<SnapshotErrorData> getErrorSectionJdbc(ProductFilter productFilter);

	List<POECountVO> getPOECountByFilters(ProductFilter productFilter);

	List<SnapshotSiebelAssetDa> getSnapshotSblAssetDaByPOEFilter(ProductFilter filters);

	List<String> getNewErrorCountById(String dfrId);

	void saveDfrNotes(DfrNotes dfrNotes);

	List<DfrNotes> getDfrNotes(String dfrId);

	SnapshotProductFilterResult getAllProductFilterResultByProduct(SnapshotProductFilterResult productFilterResult);

	List<DfrLineIdsVo> dfrLineIdListByDfr(String dfrId);

	boolean checkAssignTeamExist(String team);

	List<String> checkSystemNameForPhysicalAudit(String dfrId);

	List<String> getAssetNumFromAssetNewVal(String dfrlineid);

}
