package com.equinix.appops.dart.portal.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.CountryAndIbxVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.model.dfr.DfrDaInput;
import com.equinix.appops.dart.portal.model.errorsection.ErrorSectionResponse;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.hierarchy.HierarchyView;
import com.equinix.appops.dart.portal.model.search.filter.Filter;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;
import com.equinix.appops.dart.portal.model.search.product.SearchDropBox;
import com.equinix.appops.dart.portal.model.widget.ProductWidgets;

public interface AppOpsDartDaService {
     
     
	//ProductDataGrid getAttributeView(SearchFilters searchFilters);
	   SiebelAssetDa getSibelAssetDaByRowId(String rowId);
	   List<Filter> getFilterList();
	
	   public List<Object> getFilters(String filterName, String keyword, String []filterTables);
	   public ProductSearchResponse getProductSearchResponse(ProductFilter productFilter) throws InterruptedException, ExecutionException;
	   ErrorSectionResponse getErrorSctionResponse(ProductFilter productFilter) throws InterruptedException, ExecutionException;
	   HierarchyView getHierarchyView(ProductFilter productFilter);
	   void test();
	   ProductDataGrid getProductAttributeView(ProductFilter productFilter, boolean isCommonGrid);
	   ProductDataGrid getRefreshedProductAttributeGrid(ProductFilter productFilter) throws InterruptedException, ExecutionException;
	   ProductWidgets getProductWidgets(ProductFilter productFilter);
	   ProductWidgets getRefreshedProductWidgets(ProductFilter productFilter);
	   public SearchFilters globalSearch(String keyWord,String key);
	   public SearchFilters globalSearch(ProductFilter keyWord);
	   ProductDataGrid getCommonAttributeView(ProductFilter productFilter);
	   ProductDataGrid getRefreshedCommonAttributeGrid(ProductFilter productFilter) throws InterruptedException, ExecutionException;
	   String fireSnapshotFilter(ProductFilter filter);
	   String test(ProductFilter productFilter);
	   SearchDropBox getSearchDropBox();
	   List<Filter> getEmptyFilterList();
	   SearchFilters getErrorCodeGlobalKeywordFilters(String errorCode);
	   ProductSearchResponse getProductSearchForErrorCodeGlobalFilterKeyword(ProductFilter productFilter);
	   SearchFilters getFilterListForErrorCodeGlobalFilter(ProductFilter productFilter);
	   ProductDataGrid getProductAttributeViewForErrorCodeGlobal(ProductFilter productFilter);
	   ProductDataGrid getCommonAttributeViewErrorCodeGlobal(ProductFilter productFilter);
	   ProductWidgets getProductWidgetsErrorCodeGlobal(ProductFilter productFilter);
	   String fireSnapshotFilterForErrorCodeGlobalFilter(ProductFilter productFilter);
	   String getApplicationTimeout();
	DfrDaInput createInitiateDfrDaInputObject(ProductFilter searchFilters);
	DfrDaInput getProductAttributeViewForErrorCodeGlobalForInitiate(ProductFilter productFilter);
	ProductDataGrid getProductAttributeViewForErrorCodeGlobalElastic(ProductFilter searchFilters) throws InterruptedException,ExecutionException;
	ProductSearchResponse getProductSearchForErrorCodeGlobalFilterKeywordElastic(ProductFilter productFilter) throws InterruptedException, ExecutionException;
	List<String> getAllSystemName();
	List<String> getAllRegion();
	List<CountryAndIbxVo> getCountriesIbxByRegion(String region);
	List<String> getIbxByCountriesNRegion(String region, String country);
	List<ErrorCodeVO> getErrorsByIbx(String ibx);
	List<AccountVo> getAllActiveAccountNum(SearchFormFilter formFilter);
	List<ErrorCodeVO> getAllActiveErrors();
	List<String> getProductList();
	
}
