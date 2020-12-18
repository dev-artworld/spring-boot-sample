package com.equinix.appops.dart.portal.constant;

public class MSNativeQueries {

    public static final String PARENT_CONFIG_COL = "SELECT ATTR_NAME FROM eqx_dart.MS_ATTRIBUTE_CONFIG WHERE PRODUCT='API_CI_PARENT_COLS'";
    
    public static final String PAREN_CONTRACT_COL = "SELECT ATTR_NAME FROM eqx_dart.MS_ATTRIBUTE_CONFIG WHERE PRODUCT='AP_PARENT_CONTRACT_COLS";
    
    public static final String CHILD_CONTRACTT_COL = "SELECT ATTR_NAME FROM eqx_dart.MS_ATTRIBUTE_CONFIG WHERE PRODUCT='API_CONTRACT_CHILD_COLS'";
    
    public static final String MS_BY_TEAM_AND_STS = "select DFR_ID,ASSIGNED_TEAM,ASSIGNED_TO,ASSIGNED_DT,CREATED_BY, "
			+ " CREATED_DT ,CREATED_TEAM,PRIORITY,STATUS, IS_MS_DFR" + " (select case "
			+ " when  count(distinct(header_02))>1 " + " then   '-'" + " when  count(distinct(header_02))=1 " + " then   min(header_02) "
			+ " end as rec_exists "
			+ " FROM eqx_dart.snapshot_ms_siebel_asset_da where dfr_id=dm.dfr_id and HEADER_01='Y') IBX"
			+ " from eqx_dart.DFR_MASTER dm where status in (:ids) and IS_MS_DFR = 'Y'";
    
	public static final String GROUP_BY_USR_QUERY = "select grp.group_name as groupName from eqx_dart.assign_group grp "
			+ " inner join  eqx_dart.user_group usrGrp on grp.row_id=usrGrp.group_id "
			+ " inner join   eqx_dart.user_info usr  on usrGrp.user_id=usr.pk_id  where usr.user_id=?";
	
	public static final String ASSET_COUNT_BY_DFR = "select (select count(*) from EQX_DART.SNAPSHOT_MS_SIEBEL_ASSET_DA ss WHERE ss.HEADER_01 = 'Y' and ss.DFR_ID = '#dfrId#')No_OF_ASSETS,"
//            +" (select count(*) from EQX_DART.ASSET_NEW_VAL a where a.DFR_ID = '#dfrId#' and a.DFR_LINE_ID NOT LIKE '%-%')NEW_ASSETS,"
            +" (SELECT COUNT(distinct c.DFR_LINE_ID) from EQX_DART.MS_ASSET_NEW_VAL c WHERE c.DFR_ID = '#dfrId#' AND C.ATTR_350 = 'Y' and C.ATTR_327 IS NULL)UPDATES,"
            +" ( select count(*) from EQX_DART.MS_ASSET_NEW_VAL sss WHERE sss.DFR_ID= '#dfrId#' and sss.ATTR_327 IS NOT NULL)TERMINATORS,"
            +" (select s.SYSTEM_NAME from EQX_DART.SNAPSHOT_MS_SIEBEL_ASSET_DA s WHERE s.DFR_ID= '#dfrId#' AND ROWNUM <= 1)SYSTEM_NAME,"
            +" (select count(*) from EQX_DART.MS_CXI_ERROR_TBL c,eqx_dart.SNAPSHOT_MS_SIEBEL_ASSET_DA b WHERE c.DFR_ID = '#dfrId#' AND nvl(c.VALID_STAT,'A') <> 'Closed'"
            +" AND C.ERR_UNIQUE_ID NOT LIKE 'ERR%' and c.sbl_dfr_line_id = b.dfr_line_id  and b.header_01 = 'Y')NEW_ERRORS,"
            +" (select count(*) from EQX_DART.MS_CXI_ERROR_TBL c,eqx_dart.SNAPSHOT_MS_SIEBEL_ASSET_DA b WHERE c.DFR_ID = '#dfrId#' AND c.VALID_STAT = 'Closed' and c.sbl_dfr_line_id = b.dfr_line_id  and b.header_01 = 'Y' )FIXED_ERRORS,"
            +" (select count(*) from EQX_DART.MS_CXI_ERROR_TBL c,eqx_dart.SNAPSHOT_MS_SIEBEL_ASSET_DA b WHERE c.DFR_ID = '#dfrId#' AND nvl(c.VALID_STAT,'A') <> 'Closed' and c.sbl_dfr_line_id = b.dfr_line_id "
            +" and b.header_01 = 'Y')REMAINING_ERRORS,"
            +" d.CREATED_BY, d.NOTES,d.ASSIGNED_DT,d.REGION,d.PRIORITY,d.CLX_UPD,d.OVERRIDE_FLG,d.CLX_OVR,d.SBL_OVR,d.VALID_STATUS from EQX_DART.DFR_MASTER d WHERE d.DFR_ID = '#dfrId#' and IS_MS_DFR = 'Y'";
	
	public static final String DFR_BY_TEAM_AND_STS = "select DFR_ID,ASSIGNED_TEAM,ASSIGNED_TO,ASSIGNED_DT,CREATED_BY, "
			+ " CREATED_DT ,CREATED_TEAM,PRIORITY,STATUS,IS_MS_DFR," + " (select case "
			+ " when  count(distinct(header_02))>1 " + " then   '-'" + " when  count(distinct(header_02))=1 " + " then   min(header_02) "
			+ " end as rec_exists "
			+ " FROM eqx_dart.SNAPSHOT_MS_SIEBEL_ASSET_DA where dfr_id=dm.dfr_id and HEADER_01='Y') IBX"
			+ " from eqx_dart.DFR_MASTER dm where status in (:ids) and IS_MS_DFR = 'Y'";
	
	public static final String MS_PRODUCT_LIST = "select distinct(product) from eqx_dart.DART_MS_PROD_SCOPE where POF_FLAG='N'";

	public static final String MS_SIEBLE_ASSET_DA_ACCOUNTNUMBER_QUERY = "SELECT sbl.name,sbl.DQM_ERR_FLG FROM eqx_dart.ms_siebel_asset_da sbl,eqx_dart.snow_asset_da sn"
			+ " WHERE sbl.row_id = sn.row_id AND sbl.name in(select distinct(product) from eqx_dart.DART_MS_PROD_SCOPE where POF_FLAG='N')"
			+ " AND lower(sbl.ou_num) in (:keyList) AND sbl.STATUS_CD='Active'";

	public static final String MS_SIEBLE_ASSET_DA_HOSTNAME_QUERY = "SELECT sbl.name,sbl.DQM_ERR_FLG FROM eqx_dart.ms_siebel_asset_da sbl,eqx_dart.snow_asset_da sn"
			+ " WHERE sbl.row_id = sn.row_id AND sbl.name in(select distinct(product) from eqx_dart.DART_MS_PROD_SCOPE where POF_FLAG='N')"
			+ " AND lower(sbl.ATTR_11) in (:keyList) AND sbl.STATUS_CD='Active'";

    public static final String MS_SIEBLE_ASSET_DA_SIEBELASSETNUMBER_QUERY = "SELECT sbl.name,sbl.DQM_ERR_FLG FROM eqx_dart.ms_siebel_asset_da sbl,eqx_dart.snow_asset_da sn"
    		+ " WHERE sbl.row_id = sn.row_id AND sbl.name in(select distinct(product) from eqx_dart.DART_MS_PROD_SCOPE where POF_FLAG='N')"
    		+ " AND lower(sbl.ASSET_NUM) in (:keyList) AND sbl.STATUS_CD='Active'";
    
    public static final String MS_SIEBLE_ASSET_DA_SYSTEM_NAME_QUERY = "SELECT sbl.name,sbl.DQM_ERR_FLG FROM eqx_dart.ms_siebel_asset_da sbl,eqx_dart.snow_asset_da sn"
    		+ " WHERE sbl.row_id = sn.row_id AND sbl.name in(select distinct(product) from eqx_dart.DART_MS_PROD_SCOPE where POF_FLAG='N')"
    		+ " AND lower(SBL.SYSTEM_NAME) in (:keyList) AND sbl.STATUS_CD='Active'";

    public static final String MS_SNAPSHOT_SIEBEL_ASSET_DA_QUERY = "SELECT sbl.name,sbl.DQM_ERR_FLG FROM eqx_dart.SNAPSHOT_MS_SIEBEL_ASSET_DA sbl"
			+ " WHERE sbl.dfr_id = :dfrId AND sbl.HEADER_01='Y'";
    
    public static final String MS_SBL_SNAPSHOT_Q = "SELECT outer.* FROM (SELECT ROWNUM rn, inner.* "
			+ " FROM ( SELECT * FROM EQX_DART.SNAPSHOT_MS_SIEBEL_ASSET_DA where DFR_ID = ? and NAME = ?) inner) outer "
			+ "WHERE outer.rn > ? AND outer.rn <= ?";
    
    public static final String SNAPSHOT_SNOW_ASSET_Q = "select * from EQX_DART.SNAPSHOT_SNOW_ASSET_DA where DFR_ID = '#dfrId#' and ASSET_NUM IN (:assets)";
    
    public static final String SNAPSHOT_SNOW_CONFIG_DA_Q = "select * from EQX_DART.SNAPSHOT_SNOW_CONFIG_ITEM_DA where DFR_ID = '#dfrId#' and ASSET_NUM IN (:assets)";
    
    public static final String SNAPSHOT_SNOW_CONFIG_XA_Q = "select * from EQX_DART.SNAPSHOT_SNOW_CONFIG_ITEM_XA where DFR_ID = '#dfrId#' and ASSET_NUM IN (:assets)";

    public static final String MS_ASSET_NEW_VAL_Q = "SELECT * FROM EQX_DART.MS_ASSET_NEW_VAL where DFR_LINE_ID IN (:dfrLineIds)";
    
    public static final String MS_CI_DA_NEW_VAL_Q = "SELECT * FROM EQX_DART.MS_CI_DA_NEW_VAL where DFR_LINE_ID IN (:dfrLineIds)";
    
    public static final String MS_CI_XA_NEW_VAL_Q = "SELECT * FROM EQX_DART.MS_CI_XA_NEW_VAL where DFR_LINE_ID IN (:dfrLineIds)";

    
}
