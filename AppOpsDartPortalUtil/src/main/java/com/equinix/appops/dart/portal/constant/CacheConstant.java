package com.equinix.appops.dart.portal.constant;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CacheConstant {

	
	public static final String FILTER_LIST = "filterList";
	public static final String GLOBAL_SEARCH = "globalsearch";
	public static final String ERROR_SECTION = "getErrorSection";
	public static final String FILTER_LIST_FROM_ERROR_MASTER = "getFilterListFromErrorMaster";
	public static final String PRODUCT_FILTERS = "getProductFilters";
	public static final String HIERARCHY_VIEW = "getHierarchyView";
	public static final String SIEBEL_ASSET_DA_CACHE = "SiebelAssetDaCache";
	public static final String DART_DA_FILTER = "dartDafilter";
	public static final String daDartFilterProductWidget = "daDartFilterProductWidget";
	public static final String sblListWhenOnlyErrorFilterApplied = "sblListWhenOnlyErrorFilterApplied";
	
	public static final String CONFIG_HEADER_MAP = "getConfigHeaderMap";
	public static final String CONFIG_PRODUCT_ATTR_MAP = "getConfigProductAttrMap";
	public static final String CONFIG_PRODCUT_COMMON_ATTR_MAP = "getConfigProdcutCommonAttrMap";
	public static final String ATTRIBUTE_CONFIG_BY_PRODUCT_ATTR = "getAttributeConfigByProductAndAttr";
	public static final String DEPENDENT_ATTR_UPDATE_BY_ATTR_FAMILY = "getDependentAttrUpdateByAttrFamily";
	
	public static final String SBL_CACHE = "sblCache";
	public static final String CLX_CACHE = "clxCache";
	public static final String SV_CACHE = "svCache";
	
	public static final String BLANK = "";
	
	public static final String DART_SBL_IDX = "dart_sbl";
	public static final String DART_CLX_IDX = "dart_clx";
	public static final String DART_SV_IDX = "dart_sv";
//	public static final String DART_SBL_DOCUMENT_TYPE = "siebel_asset_da";
	public static final String DART_SBL_DOCUMENT_TYPE = "sbl_asset_da";
	public static final String DART_CLX_DOCUMENT_TYPE = "clx_asset_da";
	public static final String DART_SV_DOCUMENT_TYPE = "sv_asset_da";
	
	public static final String KEYWORD_HEADER8_IBX = "header8.keyword";
	public static final String KEYWORD_HEADER6_ACC_NUM = "header6.keyword";
	public static final String KEYWORD_HEADER7_ACC_NAME = "header7.keyword";
	public static final String KEYWORD_HEADER16_SYSTEM_NAME = "header16.keyword";
	public static final String KEYWORD_HEADER20_PRODUCT_NAME = "header20.keyword";
	public static final String KEYWORD_HEADER38_DQM_ERROR_FLAG = "header38.keyword";
	public static final String KEYWORD_HEADER18_STATUS_CD = "header18.keyword";
	public static final String KEYWORD_HEADER2_ASSET_NUM = "header2.keyword";
	public static final String KEYWORD_ERROR_CODE = "errorcode.keyword";
	public static final String KEYWORD_ERROR_NAME = "errorname.keyword";
	public static final String KEYWORD_ACTIVE = "active.keyword";
	
	public static final String HEADER51_REGION = "header51";
	public static final String HEADER56_COUNTRY = "header56";
	public static final String HEADER8_IBX = "header8";
	
	public static final List<String>globalFilters2= Arrays.asList( new String[]{"header8.keyword","header6.keyword","header16.keyword", "header2.keyword", "header7.keyword","attr253.keyword","header26.keyword"});
	
	public static final String AGG_IBX = "ibx";
	public static final String AGG_ACCOUNT_NUM = "acc";
	public static final String AGG_ACCOUNT_NAME = "accName";
	public static final String AGG_SYSTEM_NAME = "systemName";
	public static final String AGG_PRODUCT_TILES = "productTiles";
	public static final String AGG_DQM_ERROR = "dqmError";
	public static final String AGG_ERROR_CODE = "errorCode";
	public static final String AGG_ERROR_NAME = "errorName";

	public static final List<String> STATUS_CD_ACTIVE = Collections.unmodifiableList( Arrays.asList(new String[] {"Active"}));
	public static final List<String> CAGE_CHECK = Collections.unmodifiableList( Arrays.asList(new String[] {"Cage"}));
	public static final List<String> CABINET_CHECK = Collections.unmodifiableList( Arrays.asList(new String[] {"Cabinet"}));
	public static final List<String> DEMARCATION_POINT_CHECK = Collections.unmodifiableList( Arrays.asList(new String[] {"Demarcation Point"}));
	public static final List<String> Y = Collections.unmodifiableList( Arrays.asList(new String[] {"Y"}));
		
	public static final String [] ERROR_COLUMNS_TO_INCLUDE = new String[] {"owneroffixing","svrid","errorname","sblrid","validationclass","erruniqueid","errorcode"};
	
	public static final int AGG_SIZE = 200000;
	public static final int PAGE_SIZE = 10;
	public static final List<String>globalFilters= Arrays.asList( new String[]{"header8","header6","header16", "header2", "header7","attr253"});
	
	public static final String DART_ERROR_IDX = "dart_error";
	public static final String DART_ERROR_DOCUMENT_TYPE = "errors_data";
	public static final String DOT_KEYWORD = ".keyword";
	public static final String ACTIVE = "Active";
	
	public static final String ASSET_SET = "assetSet";

	public static final String SBL_LIST = "sblList";

	public static final String CAB_DP_UNIQUE_SPACE_ID = "cabDpUniqueSpaceId";

	public static final String CAB_UNIQUE_SPACE_ID = "cabUniqueSpaceId";

	public static final String CAGE_UNIQUES_SPACE_ID = "cageUniquesSpaceId";

	public static final String NOT_MATCHED_WITH_SBL_DEMARCATION_POINT_UNIQUE_SPACE_ID = "notMatchedWithSblDemarcationPointUniqueSpaceId";

	public static final String MATCHED_WITH_SBL_DEMARCATION_POINT_UNIQUE_SPACE_ID = "matchedWithSblDemarcationPointUniqueSpaceId";

	public static final String NOT_MATCHED_WITH_SBL_CABINET_UNIQUE_SPACE_ID = "notMatchedWithSblCabinetUniqueSpaceId";

	public static final String MATCHED_WITH_SBL_CABINET_UNIQUE_SPACE_ID = "matchedWithSblCabinetUniqueSpaceId";

	public static final String NOT_MATCHED_WITH_SBL_CAGE_UNIQUE_SPACE_ID = "notMatchedWithSblCageUniqueSpaceId";

	public static final String MATCHED_WITH_SBL_CAGE_UNIQUE_SPACE_ID = "matchedWithSblCageUniqueSpaceId";

	public static final String CAGE = "Cage";

	public static final String DEMARCATION_POINT = "Demarcation Point";
	
	public static final String PATCH_PANEL = "Patch Panel";
	
	public static final String NETWORK_CABLE_CONNECTION = "Network Cable Connection";
	
	public static final String DC_CIRCUIT = "DC Circuit";
	
	public static final String AC_CIRCUIT = "AC Circuit";

	public static final String CABINET = "Cabinet";
	
	public static final String PRIVATE_PATCH_PANEL_EQUINIX = "Private Patch Panel (Equinix)";
	
	public static final String PRIVATE_PATCH_PANEL_CUSTOMER = "Private Patch Panel (Customer)";

	public static final String HEADER10 = "header10";
	
	public static final String SV_LIST = "svList";
	
	public static final String CLX_DATA_MAP = "clxDataMap";
	
	public static final String SBL_DATA_MAP = "sblDataMap";
	
	public static final String CAGE_ROW_IDS = "cageRowIds";
	public static final String CAB_ROW_IDS = "cabRowIds";
	public static final String CAB_DP_ROW_IDS = "cabDpRowIds";
	public static final String SBL_ROW_IDS = "sblRowIds";
	public static final String DFR_DATA_MAP = "dfrDataMap";

	public static final String DFR_ID = "dfrId";
	
	public static final boolean IS_INTIATE_WITH_SELECTED_ASSET = true;
	
	public static final SimpleDateFormat LAST_UPD_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	
	public static  final String getHeaderMappedAttrEntityProperty(String headerKey){

		switch (headerKey){
		case "header67" : 
			return "attr325";
		case "header68" : 
			return "attr326";
		case "header69" : 
			return "attr327";
		case "header70" : 
			return "attr328";
		case "header71" : 
			return "attr329";
		case "header72" : 
			return "attr330";
		case "header73" : 
			return "attr331";
		case "header74" : 
			return "attr332";
		case "header75" : 
			return "attr333";
		case "header76" : 
			return "attr334";
		case "header77" : 
			return "attr335";
		case "header78" : 
			return "attr336";
		case "header79" : 
			return "attr337";
		case "header80" : 
			return "attr338";
		case "header81" : 
			return "attr339";
		case "header82" : 
			return "attr340";
		case "header83" : 
			return "attr341";
		case "header84" : 
			return "attr342";
		case "header85" : 
			return "attr343";
		case "header86" : 
			return "attr344";
		case "header87" : 
			return "attr345";
		case "header88" : 
			return "attr346";
		case "header89" : 
			return "attr347";
		case "header90" : 
			return "attr348";
		case "header91" : 
			return "attr349";
		case "header92" : 
			return "attr350";	 
		default : 
			return headerKey;
		}
	}
	
	public static final String parentCabOrDPsCommonHeadersForAcAndDC [] = 
		   {"Header11","Header9","Header5","Header8","Header24","Header26","Header40","Header41","Header4",
				   "Header43","Header14","Header54","Header6","Header7","Header10","Header12","Header13",
				   "Header15","Header16","Header18","Header42","Header46","Header49","Header50","Header51","Header53","Header56"};

	public static final String parentCageCommonHeadersForAcAndDC [] = 
		   {"Header6","Header11","Header4","Header5","Header7","Header8","Header9","Header10","Header13",
				   "Header14","Header15","Header16","Header18","Header24","Header26","Header40","Header41",
				   "Header42","Header46","Header49","Header50","Header51","Header53"};
}
