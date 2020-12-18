package com.equinix.appops.dart.portal.constant;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DartConstants {

	public static final String BLANK = "";
	public static final String SBL = "SBL";
	public static final String CLX ="CLX";
	public static final String SV = "SV";
	public static final String NA = "NA";
	public static final String HEADER ="HEADER";
	public static final String ATTR_UNDERSCORE ="ATTR_";
	public static final SimpleDateFormat DART_SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static final String SBL_LIST = "sbl";
	public static final String CLX_LIST = "clx";
	public static final String SV_LIST = "sv";
	public static final String IBX ="ibx";
	public static final String ACC ="acc";
	public static final String SYS ="sys";
	public static final String DFRID ="dfrId";
	public static final String HEADER_57= "header57";
	public static final String ATTR_325= "attr325";
	public static final List<String> DATE_TYPE_PROP_LIST = Arrays.asList(new String[]{"header17", "header34","header36","header55"});
	public static final String DFR_SNAPSHOT_FILTER_SEARCH_RESULTS = "dfrSearchResults";
	public static final Map<String,String> SEARCH_DROP_MAP;
	public static final Map<String,String> HEADER_TO_ATTR_MAP;
	public static final boolean IS_ELASTIC_CALL = true;
	public static final boolean IS_JDBC_CALL = true;
	public static final String NEW_VALUE ="New_Value";
	public static final String AWE_KPI_FILTER = "awe";
	public static final String CM_KPI_FILTER = "cm";
	public static final String NE_KPI_FILTER = "ne";
	
	static {
	        HashMap<String, String> aMap = new HashMap<>();
	        aMap.put("header2","Asset Number");
	        aMap.put("header3","Serial Number");
	        aMap.put("header16","System Name");
	        SEARCH_DROP_MAP = Collections.unmodifiableMap(aMap);
	        
	        HashMap<String, String> headerToAttrMap = new HashMap<>();
	        headerToAttrMap.put("ATTR334","HEADER76");
	        headerToAttrMap.put("ATTR335","HEADER77");
	        headerToAttrMap.put("ATTR336","HEADER78");
	        headerToAttrMap.put("ATTR337","HEADER79");
	        headerToAttrMap.put("ATTR338","HEADER80");
	        headerToAttrMap.put("ATTR339","HEADER81");
	        headerToAttrMap.put("ATTR340","HEADER82");
	        headerToAttrMap.put("ATTR341","HEADER83");
	        headerToAttrMap.put("ATTR342","HEADER84");
	        headerToAttrMap.put("ATTR343","HEADER85");
	        headerToAttrMap.put("ATTR344","HEADER86");
	        headerToAttrMap.put("ATTR345","HEADER87");
	        headerToAttrMap.put("ATTR346","HEADER88");
	        headerToAttrMap.put("ATTR347","HEADER89");
	        headerToAttrMap.put("ATTR348","HEADER90");
	        headerToAttrMap.put("ATTR349","HEADER91");
	        headerToAttrMap.put("ATTR350","HEADER92");
	        HEADER_TO_ATTR_MAP = Collections.unmodifiableMap(headerToAttrMap);
	}
	
	public static final List<String> SEARCH_DROP_COLUMNS = Arrays.asList(new String[]{"header2"});
	
	
	
}
