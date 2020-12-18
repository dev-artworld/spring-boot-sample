package com.equinix.appops.dart.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ExecutionException;

import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.CountryAndIbxVo;
import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;
import com.equinix.appops.dart.portal.model.errorsection.ErrorData;
import com.equinix.appops.dart.portal.model.errorsection.SnapshotErrorData;
import com.equinix.appops.dart.portal.model.search.filter.SearchFilters;
import com.equinix.appops.dart.portal.model.search.filter.SearchFormFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilterResult;
import com.equinix.appops.dart.portal.model.search.product.ProductSearchResponse;

public interface AppOpsDartDaDao {

	List<AttributeConfig> getHeaders();
	List<AttributeConfig> getAttributesByProduct(String product);
	List<String> getProducts();
	List<String> getProductList();
	SortedMap<Integer, AttributeConfig> getConfigHeaderMap();
	HashMap<String, SortedMap<Integer, AttributeConfig>> getConfigProductAttrMap();
	void refresh();
	void initilize();

	SiebelAssetDa getSibelAssetDaByRowId(String rowId);
	public List<Object> executeSQL(String sql);
	public List<Object> executeParamListSQL(String sql, Set<String> list1);
	public List<SiebelAssetDa> getProductFilters(ProductFilter productFilter);
	List<ErrorData> getErrorSection(ProductFilter productFilter);
	List<Object[]> executeSQLForArray(String sql);


	List<SiebelAssetDa> getSiebleAssetDaData(ProductFilter productFilter);
	List<ClxAssetDa> getClxAssetDaData(ProductFilter productFilter);
	List<SvAssetDa> getSvAssetDaData(ProductFilter productFilter);
	Map<String, Object> getConfigProdcutCommonAttrMap();
	List<String> getFilterListFromDA(String header);
	List<String> getFilterListFromErrorMaster();
	List<SiebelAssetDa> getSiebelAssetDaDataByProduct(ProductFilter productFilter, String product);
	SearchFilters globalSearch(ProductFilter keyWord);
	List<SiebelAssetDa> getHierarchyView(ProductFilter productFilter);
	ClxAssetDa getClxAssetDaByRowId(String rowId);
	SvAssetDa getSvAssetDaByRowId(String rowId);
	List<ClxAssetDa> getClxAssetDaByRowsIds(List<String> rowIds);
	List<SvAssetDa> getSvAssetDaByRowsIds(List<String> rowIds);
	List<SiebelAssetDa> getSblAssetDaByRowsIds(List<String> rowIds);
	ProductFilterResult getProductFilterResult(ProductFilterResult productFilterResult);
	List<SiebelAssetDa> getGlobalSearch(ProductFilter productFilter);
	List<AttributeConfig> getPhysicalHeaders();
	List<AttributeConfig> getPhysicalAttributesByProduct(String product);
	SortedMap<Integer, AttributeConfig> getPhysicalConfigHeaderMap();
	HashMap<String, SortedMap<Integer, AttributeConfig>> getPhysicalConfigProductAttrMap();
	String resetConfigHeaderMap();
	String resetConfigProductAttrMap();
	String resetConfigProdcutCommonAttrMap();
	String resetDartFilterProductWidget();
	String resetFilterList();
	String resetGlobalSearch();
	String resetProductFilters();
	String resetErrorSection();
	String resetHierarchyView();
	String resetDartDaFilter();
	String resetSiebelAssetDaCache();
	String resetFilterListFromErrorMaster();
	List<SiebelAssetDa> getSblListWhenOnlyErrorFilterApplied(ProductFilter productFilter, List<SiebelAssetDa> sblList);
	List<ClxAssetDa> getClxList(Set<String> cageUniquesSpaceId, Set<String> cabUniqueSpaceId,
			Set<String> cabDpUniqueSpaceId, Set<String> cageRowIds, Set<String> cabinetRowIds,
			Set<String> cabinetDpRowIds);
	List<SvAssetDa> getSvList(Set<String> assetSet);
	ProductFilterResult getProductFilterResultForAccMoveDfr(List<String> assets);
	SortedMap<Integer, AttributeConfig> getPhysicalConfigMobileHeaderMap();
	List<AttributeConfig> getPhysicalMobileHeaders();
	public String refreshMobAuditHeaderAndAttributes();
	List<ClxAssetDa> getClxList(Set<String> clxAssetDacageUsid, Set<String> clxAssetDacabUsid);
	ProductFilterResult getAllProductFilterResultElastic(ProductFilterResult productFilterResult) throws InterruptedException, ExecutionException;
	SearchFilters getGlobalSearchElastic(ProductFilter productFilter);
	ProductSearchResponse getProductSearchResponseElastic(ProductFilter productFilter) throws InterruptedException, ExecutionException;
	ProductFilterResult getProductFilterResultElastic(ProductFilterResult productFilterResult)
			throws InterruptedException, ExecutionException;
	List<SiebelAssetDa> getSblAssetDaAllByRowsIdsElastic(List<String> sblRowIdList, ProductFilter productFilter)
			throws InterruptedException, ExecutionException;
	List<ClxAssetDa> getClxAssetDaAllByRowsIdsElastic(List<String> clxRowIdList, ProductFilter productFilter)
			throws InterruptedException, ExecutionException;
	List<SvAssetDa> getSvAssetDaAllByRowsIdsElastic(List<String> svRowIdList, ProductFilter productFilter)
			throws InterruptedException, ExecutionException;
	List<ErrorData> getErrorSectionElastic(ProductFilter productFilter, boolean keywordHasVal)
			throws InterruptedException, ExecutionException;
	List<String> getAllSystemName(String keyword);
	List<String> getAllRegion();
	List<CountryAndIbxVo> getCountriesIbxByRegion(String region);
	List<String> getIbxByCountriesNRegion(String region, String country);
	List<ErrorCodeVO> getErrorsByIbx(String ibx);
	List<AccountVo> getAllActiveAccountNum(SearchFormFilter formFilter);
	List<ErrorCodeVO> getAllActiveErrors();
	List<SnapshotErrorData> getErrorSectionJdbc(String sblQuery);
	List<String> getAllProduct();
	
}
