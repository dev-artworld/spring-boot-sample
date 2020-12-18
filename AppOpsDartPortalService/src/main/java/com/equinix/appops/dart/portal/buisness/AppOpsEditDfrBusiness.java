package com.equinix.appops.dart.portal.buisness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.ChangeSummary;
import com.equinix.appops.dart.portal.entity.DFRFile;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.DfrNotes;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SrcCxiErrorMasterTbl;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;
import com.equinix.appops.dart.portal.model.FileUp;
import com.equinix.appops.dart.portal.model.ResetAndDelete;
import com.equinix.appops.dart.portal.model.dfr.DfrNotesInput;
import com.equinix.appops.dart.portal.model.dfr.InitiateWorkflowInput;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.grid.SaveAssetForm;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;

public interface AppOpsEditDfrBusiness {

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

	String initiateWorkflow(InitiateWorkflowInput workflowInput);

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

	String initiatePhysicalAudit(String dfrId, String ibx);

	Map<String, Object> getPhysicalAuditDownloadData(String dfrId);

	ProductDataGrid getProductPysicalAuditAttributeView(ProductFilter productFilter);

	void saveOrUpdatePhysicalAuditData(List<SnapshotSiebelAssetDa> assetList, List<AssetNewVal> assetNewValListInsert,
			List<AssetNewVal> assetNewValListUpdate, List<SaveAssetForm> saveAssetForms);

	HashMap<String, SrcCxiErrorMasterTbl> getErrorMasterList();

	String getValidStatus(String dfrId);

	String saveOverrideFlagDFRMaster(String overrideFlag, String sblOverrideFlag, String clxOverrideFlag, String dfrId);

	List<ChangeSummary> getChangeSummary(String dfrId);

	void updateChangeSummaryValues(ProductDataGrid dataGrid) throws Exception;

	String resetAndDelete(ResetAndDelete resetAndDeleteObj) throws Exception;

	String resetAndDelete(String dfrLineId) throws Exception;

	public ProductDataGrid getAccountMoveAttributeView(String dfrId);

	public String resetAttributeView(String dfrId, String product) throws Exception;

	boolean validateSaveAssetNewValuesByDfrId(ProductFilter filter) throws Exception;

	String checkSystemNameForPhysicalAudit(String dfrId);

	DFRFile downloadFile(String dfrId) throws Exception;

	String deleteDFRFile(String dfrId) throws Exception;

	String uploadDFRFile(FileUp fileUp, MultipartFile file) throws Exception;

	Boolean checkFileExists(String dfrId) throws Exception;

	ProductDataGrid getSelectedProductAttributeView(ProductFilter searchFilters);

	List<String> getProductAttributeFilter(ProductFilter searchFilters);

	List<String> createInitiateWorkflowInput(ProductDataGrid productDataGrid);

	ProductDataGrid getSelectedRefreshedProductAttributeView(ProductFilter searchFilters);

	List<String> getRefreshedProductAttributeFilter(ProductFilter searchFilters);

	ProductDataGrid getSelectedProductPysicalAuditAttributeView(ProductFilter searchFilters);

	List<String> getProductPysicalAuditAttributeViewFilter(ProductFilter searchFilters);

	String deleteAssetsByDfrLineIds(String dfrID, List<String> dfrLineIds);

	String getNewPOEAssetNumber();

	List<String> getFilterListFromSnapshotDA(String header, String dfrId);

	String resetAssetsByDfrLineIds(Set<String> dfrLineIds) throws Exception;

	List<ErrorCodeVO> validationsOnSubmit(String dfrId) throws Exception;

	ProductDataGrid sortChangedAssetNewVal(ProductDataGrid dataGrid, Map<String, Set<String>> dfrLineIds);

	ProductDataGrid autoValidate(String dfr) throws Exception;

	void autoSaveNewAssetValues(SaveAssetForm data) throws Exception;

	void autoSaveDependentAttributes(SaveAssetForm data) throws Exception;

	void autoUpdateChangeSummaryValues(SaveAssetForm data) throws Exception;

	ProductDataGrid productAttributeViewByLineId(SaveAssetForm data);

	List<ErrorCardVO> getTotalAndLeftAssetById(String dfrId);

	HashMap<String, Object> getChangesMadeById(String dfrId);

	List<POECountVO> getPOECountByFilters(ProductFilter productFilter);

	ProductDataGrid getInitiateProductAttributeView(ProductFilter productFilter);

	HashMap<String, Object> getNewErrorCountById(String dfrId);

	ProductDataGrid getAllProductAttributeView(ProductFilter searchFilters);

	void saveDfrNotes(DfrNotesInput dfrNotesInput);

	List<DfrNotes> getDfrNotes(String dfrId);

	SaveAssetForm createChangeSummaryInput(ProductDataGrid dataGrid);

	Map<String, List<String>> dfrLineIdListByDfr(String dfrId);

	void populateChangeSummaryValues(Map<String, Set<String>> dfrLineIds, String dfrId) throws Exception;

	void updatePhyChangeSummaryValues(ProductDataGrid dataGrid);

}
