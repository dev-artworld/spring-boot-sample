package com.equinix.appops.dart.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.model.errorsection.ErrorData;
import com.equinix.appops.dart.portal.model.grid.ProductDataGrid;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;

public interface AppOpsDartElasticDao {
	
	ProductSearchResponse getProductTiles(ProductFilter productFilter) throws InterruptedException, ExecutionException;

	HashMap<String, List<String>> getFilterList(ProductFilter productFilter);

	ProductFilterResult getProductAttributeView(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException;

	List<ErrorData> getErrorSection(ProductFilter productFilter, boolean keywordHasVal) throws InterruptedException, ExecutionException;

	List<SiebelAssetDa> getSiebelAssetDaDataByProduct(ProductFilter productFilter, String product)
			throws InterruptedException, ExecutionException;

	List<SiebelAssetDa> getHierarchyView(ProductFilter productFilter) throws InterruptedException, ExecutionException;

	ProductFilterResult getDataForDfrElastic(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException;

	List<SiebelAssetDa> getSblList(List<String> sblRowIdList);

	List<ClxAssetDa> getClxList(List<String> sblRowIdList);

	List<SvAssetDa> getSvList(List<String> sblRowIdList);

	List<SvAssetDa> getSvList(Set<String> assetList) throws InterruptedException, ExecutionException;

	List<ClxAssetDa> getClxList(Set<String> cageList, Set<String> cabList, Set<String> cabDpList,
			Set<String> cageRowIds, Set<String> cabinetRowIds, Set<String> cabinetDpRowIds) throws InterruptedException, ExecutionException;

	List<SiebelAssetDa> getSblList(List<String> sblRidList, ProductFilter productFilter);

	ProductFilterResult getAllProductAttributeView(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException;

	List<SvAssetDa> getSvAllByError(List<String> svRowIdList, ProductFilter productFilter)
			throws InterruptedException, ExecutionException;

	List<ClxAssetDa> getClxAllByError(List<String> clxRowIdList, ProductFilter productFilter)
			throws InterruptedException, ExecutionException;

	List<SiebelAssetDa> getSblAllByError(List<String> sblRowIdList, ProductFilter productFilter)
			throws InterruptedException, ExecutionException;

	ProductFilterResult getSblListByError(List<String> sblRowIdList, ProductFilter productFilter);

	List<SvAssetDa> getSvListByError(List<String> svRowIdList, ProductFilter productFilter);

	List<ClxAssetDa> getClxListByError(List<String> clxRowIdList, ProductFilter productFilter);

	ProductSearchResponse getSblListWhenOnlyErrorFilterAppliedElastic(
			ProductFilter productFilterForErrorCodeGlobalFilter) throws InterruptedException, ExecutionException;

	List<SiebelAssetDa> getProductAttributeViewForErrorCodeGlobalElastic(ProductFilter searchFilters) throws InterruptedException, ExecutionException;

	List<String> getAllSystemName();

	List<AccountVo> getAllActiveAccount(SearchFormFilter formFilter);

	List<ErrorCodeVO> getAllActiveErrorsElastic();
}
