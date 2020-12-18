package com.equinix.appops.dart.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrLineIdsVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.model.DFRFileModel;
import com.equinix.appops.dart.portal.model.ResetAndDelete;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;

public interface AppOpsDartEditDfrService {

	SearchFilters getFilterList(ProductFilter productFilter);

	ProductSearchResponse getProductSearchResponse(ProductFilter productFilter);

	ProductWidgets getProductWidgets(ProductFilter productFilter);

	ErrorSectionResponse getErrorSctionResponse(ProductFilter productFilter);

	SearchFilters globalSearch(String keyword, String dfrId, String key);

	DfrMaster getDfrMasterById(String dfrId);

	ProductDataGrid getCommonAttributeView(ProductFilter productFilter);

	ProductDataGrid getProductAttributeView(ProductFilter productFilter);

	HierarchyView getHierarchyView(ProductFilter productFilter);

	String validateDependentAttributes(String product, String attrName, String dfrId, String cellType) throws Exception;

	// String validate(String product,String attrName,String dfrId,String
	// clxRowId,String sblRowId,String newVal,String cellType);

	String dqmValidation(String dfrId) throws Exception;

	void saveNewAssetValues(ProductDataGrid dataGrid) throws Exception;

	String validate(ProductDataGrid dataGrid) throws Exception;

	String editHierarchy(String dfrLineID, String parentDfrLineID, String product);

	String validateHierarchy(String dfrLineID, String product);

	void saveDependentAttributes(ProductDataGrid dataGrid) throws Exception;

	String saveDfrDetails(String dfrId, String fieldName, String fieldValue);

	String cancelDfr(String dfrId);

	String getWorkflowDetails(String dfrId);

	String updateUserDfr(String userId, String dfrId);

	ProductDataGrid getRefreshedCommonAttributeGrid(ProductFilter filters);

	ProductDataGrid getRefreshedProductAttributeGrid(ProductFilter filters);

	ProductWidgets getRefreshedProductWidgets(ProductFilter productFilter);

	DfrMaster getDfrMaster(String dfrId, String fetchMode);

	void saveOrUpdateApprovalHistory(ApprovalHistory approvalHistory);

	void saveOrUpdateDfrMaster(DfrMaster dfrMaster);

	String getAssignedGroupByReqionAndSystem(String region, String sot);

	ApprovalHistory getLatestAppHistory(String dfrId);

	String getEmailIdByAssignGroup(String assignedTo);

	String initiatePhysicalAudit(String dfrId, String ibx);

	Map<String, Object> getPhysicalAuditDownloadData(String dfrId);

	List<DfrMaster> getDfrMasterCompleted(String emailFlag);

	ProductDataGrid getProductPhysicalAuditAttributeView(ProductFilter productFilter);

	void saveOrUpdatePhysicalAuditData(List<SnapshotSiebelAssetDa> assetList, List<AssetNewVal> assetNewValListInsert,
			List<AssetNewVal> assetNewValListUpdate, List<SaveAssetForm> saveAssetForms);

	HashMap<String, SrcCxiErrorMasterTbl> getErrorMasterList();

	String getValidStatus(String dfrId);

	String saveOverrideFlagDFRMaster(String overrideFlag, String sblOverrideFlag, String clxOverrideFlag, String dfrId);

	List<ChangeSummary> getChangeSummary(String dfrId);

	void updateChangeSummaryValues(ProductDataGrid dataGrid) throws Exception;

	ApprovalHistory getApprovalHistory(DfrMaster dfrMaster, String status, String notes);

	String resetAndDelete(ResetAndDelete resetAndDelete) throws Exception;

	String resetAndDelete(String dfrLineId) throws Exception;

	ProductDataGrid getAccountMoveAttributeView(String dfrId);

	public void saveDependentAttributesForAccountMove(Set<SnapshotSiebelAssetDa> sblSet, Set<AssetNewVal> newValSet)
			throws Exception;

	public String resetAttributeView(String dfrId, String product) throws Exception;

	boolean validateSaveAssetNewValuesByDfrId(ProductFilter filter) throws Exception;

	DFRFile downloadDfrFile(String dfrId);

	String uploadDfrFile(DFRFileModel dfrFile);

	String deleteDfrFile(String dfrId);

	List<String> checkSystemNameForPhysicalAudit(String dfrId);

	boolean isDFRFileExists(String dfrID);

	ProductDataGrid getSelectedProductAttributeView(ProductFilter productFilter);

	List<String> getProductAttributeFilter(ProductFilter productFilter);

	ProductDataGrid getSelectedRefreshedProductAttributeView(ProductFilter searchFilters);

	List<String> getRefreshedProductAttributeFilter(ProductFilter searchFilters);

	ProductDataGrid getSelectedProductPysicalAuditAttributeView(ProductFilter searchFilters);

	List<String> getProductPysicalAuditAttributeViewFilter(ProductFilter searchFilters);

	String deleteAssetsByDfrLineIds(String dfrID, List<String> dfrLineIds);

	String getNewPOEAssetNumber();

	List<String> getFilterListFromSnapshotDA(String header, String dfrId);

	String resetAssetsByDfrLineIds(Set<String> dfrLineIds) throws Exception;

	List<ErrorCodeVO> validationsOnSubmit(String dfrId) throws Exception;

	ProductDataGrid autoValidate(ProductFilter productFilter);

	void autoSaveNewAssetValues(SaveAssetForm formData) throws Exception;

	void autoSaveDependentAttributes(SaveAssetForm data) throws Exception;

	void autoUpdateChangeSummaryValues(SaveAssetForm data);

	ProductDataGrid productAttributeViewByLineId(SaveAssetForm data);

	List<ErrorCardVO> getTotalErrorAssetById(String dfrId);

	List<String> getChangesMadeById(String dfrId);

	List<String> getErrorListByKpi(ProductFilter productFilter);

	List<POECountVO> getPOECountByFilters(ProductFilter productFilter);

	ProductDataGrid getInitiateProductAttributeView(ProductFilter productFilter);

	List<String> getNewErrorCountById(String dfrId);

	ProductDataGrid getAllProductAttributeView(ProductFilter searchFilters);

	void saveDfrNotes(DfrNotes dfrNotes);

	List<DfrNotes> getDfrNotes(String dfrId);

	List<DfrLineIdsVo> dfrLineIdListByDfr(String dfrId);

	long getNextValueOfSerialNumber();

	HashMap<String, SortedMap<Integer, AttributeConfig>> getPhysicalConfigProductAttrMap();

	SortedMap<Integer, AttributeConfig> getPhysicalConfigHeaderMap();

	SnapshotSiebelAssetDa getSnapshotSblAssetDaData(String parentDfrLineId);

	long getNextValueOfDFRLineIDSeq();

	void saveOrUpdateAssetNewVal(AssetNewVal assetNewVal);

	void saveOrUpdateSnapshotSiebelAssetDa(SnapshotSiebelAssetDa newChild);

	void updatePhyChangeSummaryValues(ProductDataGrid dataGrid);

}
