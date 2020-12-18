package com.equinix.appops.dart.portal.constant;

public class ProNativeQueries {

	public static final String USR_BY_GROUP_QUERY = "select distinct(usr.user_id) as userId from eqx_dart.user_info usr"
			+ " inner join eqx_dart.user_group usrGrp on usrGrp.user_id=usr.pk_id "
			+ " inner join eqx_dart.assign_group grp on grp.row_id=usrGrp.group_id where grp.group_name=?";

	public static final String GROUP_BY_USR_QUERY = "select grp.group_name as groupName from eqx_dart.assign_group grp "
			+ " inner join  eqx_dart.user_group usrGrp on grp.row_id=usrGrp.group_id "
			+ " inner join   eqx_dart.user_info usr  on usrGrp.user_id=usr.pk_id  where usr.user_id=?";

	public static final String ASSET_COUNT_BY_DFR = "select (select count(*) from EQX_DART.snapshot_siebel_asset_da ss WHERE ss.HEADER_01 = 'Y' and ss.DFR_ID = '#dfrId#')No_OF_ASSETS,"
            +" (select count(*) from EQX_DART.ASSET_NEW_VAL a where a.DFR_ID = '#dfrId#' and a.DFR_LINE_ID NOT LIKE '%-%')NEW_ASSETS,"
            +" (SELECT COUNT(distinct c.DFR_LINE_ID) from EQX_DART.ASSET_NEW_VAL c WHERE c.DFR_ID = '#dfrId#' AND  c.DFR_LINE_ID like '%-%' AND C.ATTR_350 = 'Y' and C.ATTR_327 IS NULL)UPDATES,"
            +" ( select count(*) from EQX_DART.ASSET_NEW_VAL sss WHERE sss.DFR_ID= '#dfrId#' and sss.ATTR_327 IS NOT NULL)TERMINATORS,"
            +" (select s.SYSTEM_NAME from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA s WHERE s.DFR_ID= '#dfrId#' AND s.NAME = 'Cage' AND ROWNUM <= 1)SYSTEM_NAME,"
            +" (select count(*) from EQX_DART.CXI_ERROR_TBL c,eqx_dart.SNAPSHOT_SIEBEL_ASSET_DA b WHERE c.DFR_ID = '#dfrId#' AND nvl(c.VALID_STAT,'A') <> 'Closed'"
            +" AND C.ERR_UNIQUE_ID NOT LIKE 'ERR%' and c.sbl_dfr_line_id = b.dfr_line_id  and b.header_01 = 'Y')NEW_ERRORS,"
            +" (select count(*) from EQX_DART.CXI_ERROR_TBL c,eqx_dart.SNAPSHOT_SIEBEL_ASSET_DA b WHERE c.DFR_ID = '#dfrId#' AND c.VALID_STAT = 'Closed' and c.sbl_dfr_line_id = b.dfr_line_id  and b.header_01 = 'Y' )FIXED_ERRORS,"
            +" (select count(*) from EQX_DART.CXI_ERROR_TBL c,eqx_dart.SNAPSHOT_SIEBEL_ASSET_DA b WHERE c.DFR_ID = '#dfrId#' AND nvl(c.VALID_STAT,'A') <> 'Closed' and c.sbl_dfr_line_id = b.dfr_line_id "
            +" and b.header_01 = 'Y')REMAINING_ERRORS,"
            +" d.CREATED_BY, d.NOTES,d.ASSIGNED_DT,d.REGION,d.PRIORITY,d.CLX_UPD,d.OVERRIDE_FLG,d.CLX_OVR,d.SBL_OVR,d.VALID_STATUS from EQX_DART.DFR_MASTER d WHERE d.DFR_ID = '#dfrId#'";

	public static final String DFR_BY_TEAM_AND_STS = "select DFR_ID,ASSIGNED_TEAM,ASSIGNED_TO,ASSIGNED_DT,CREATED_BY, "
			+ " CREATED_DT ,CREATED_TEAM,PRIORITY,STATUS,IS_MOBILE_DFR, IS_NETWORK_DFR, IS_ACCOUNT_MOVE_DFR," + " (select case "
			+ " when  count(distinct(ibx))>1 " + " then   '-'" + " when  count(distinct(ibx))=1 " + " then   min(ibx) "
			+ " end as rec_exists "
			+ " FROM eqx_dart.snapshot_siebel_asset_da where dfr_id=dm.dfr_id and HEADER_01='Y') IBX"
			+ " from eqx_dart.DFR_MASTER dm where status in (:ids)";

	public static final String ASGND_TEAM_DFRMSTR = "select distinct(ASSIGNED_TEAM) from eqx_dart.DFR_MASTER dfrmaster";

	public static final String GLOBAL_SEARCH_Q = "WITH search_results AS ( SELECT  dfr_id FROM"
			+ "   eqx_dart.snapshot_siebel_asset_da sbl WHERE  upper(ibx) LIKE upper('#keyword#%') OR"
			+ "  upper(serial_num) LIKE upper('#keyword#%') OR"
			+ "    upper(cage_unique_space_id) LIKE upper('#keyword#%') OR"
			+ "      upper(asset_num) LIKE upper('#keyword#%') UNION SELECT  dfr_id FROM"
			+ "   eqx_dart.dfr_master WHERE  dfr_id = '#keyword#'  ORDER BY 1 DESC) SELECT DISTINCT"
			+ "    dfr_id FROM search_results";
	
	public static final String GET_DFR_BY_ID_FOR_HOME = "select DFR_ID,ASSIGNED_TEAM,ASSIGNED_TO,ASSIGNED_DT,CREATED_BY, "
			+ " CREATED_DT ,CREATED_TEAM,PRIORITY,STATUS,IS_MOBILE_DFR,IS_ACCOUNT_MOVE_DFR," + " (select case "
			+ " when  count(distinct(ibx))>1 " + " then   '-'" + " when  count(distinct(ibx))=1 " + " then   min(ibx) "
			+ " end as rec_exists "
			+ " FROM eqx_dart.snapshot_siebel_asset_da where dfr_id=dm.dfr_id and HEADER_01='Y') IBX "
			+ " from eqx_dart.DFR_MASTER dm where DFR_ID in (##GLOBALSEARCHQ##) ";

	public static final String GET_SUBMIT_VALIDATION_ERR_CDE = "select distinct error_code,error_name from eqx_dart.cxi_error_tbl where  "
			+ " error_code in (select error_code from eqx_dart.src_cxi_error_master_tbl where type='Validation') and valid_stat<>'Closed' and dfr_id='#dfrId#' ";

	public static final String PAGINATION_TEMPLATE = "SELECT outer.* FROM (SELECT ROWNUM rn, inner.* "
			+ " FROM (~sqlQuery~) inner) outer "
			+ "WHERE outer.rn > ? AND outer.rn <= ?";
	
	public static final String SBL_SNAPSHOT_Q = "SELECT * FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where HEADER_01 = 'Y' AND DFR_ID = ? and NAME = ?";
	
	public static final String SBL_SNAPSHOT_ALL_Q = "SELECT * FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where HEADER_01 = 'Y' AND DFR_ID = ?";

	public static final String SBL_SNAPSHOT_BY_DFRLINE_ID = "SELECT distinct LISTAGG(ERR.ERROR_CODE,',') WITHIN GROUP(ORDER BY ERR.ERROR_CODE) OVER(PARTITION BY ERR.ERROR_ITEM)  DQM_ERROR_DESCRIPTION"
						+" ,  SDA.*"
        +" FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA"
        +", EQX_DART.CXI_ERROR_TBL ERR"
        +" WHERE"
        +" sda.dfr_line_id = err.sbl_dfr_line_id(+)"
        +" and nvl(err.valid_stat,'a') <> 'Closed'"
        +" AND sda.DFR_LINE_ID = ?";

	public static final String CLX_SNAPSHOT_Q = "select * from EQX_DART.SNAPSHOT_CLX_ASSET_DA where DFR_ID = '#dfrId#' and NAME = '#name#' and #LHS# IN (:ids)";

	public static final String CLX_CAGE_QUERY = CLX_SNAPSHOT_Q.replace("#LHS#", "CAGE_UNIQUE_SPACE_ID");

	public static final String CLX_CAB_QUERY = CLX_SNAPSHOT_Q.replace("#LHS#", "CAB_UNIQUE_SPACE_ID");

	public static final String CLX_SNAPSHOT_BY_DFRLINE_ID = "select * from EQX_DART.SNAPSHOT_CLX_ASSET_DA where DFR_LINE_ID = '#dfrLineId#' and NAME = '#name#' and #LHS# IN (:ids)";

	public static final String CLX_CAGE_BY_DFRLINE_ID = CLX_SNAPSHOT_Q.replace("#LHS#", "CAGE_UNIQUE_SPACE_ID");

	public static final String CLX_CAB_BY_DFRLINE_ID = CLX_SNAPSHOT_Q.replace("#LHS#", "CAB_UNIQUE_SPACE_ID");

	public static final String ASSET_NEW_VAL_Q = "SELECT * FROM EQX_DART.ASSET_NEW_VAL where DFR_LINE_ID IN (:dfrLineIds)";

	public static final String SV_SNAPSHOT_Q = "select * from EQX_DART.SNAPSHOT_SV_ASSET_DA where DFR_ID = '#dfrId#' and ASSET_NUM IN (:assets)";

	public static final String SV_SNAPSHOT_BY_DFRLINE_ID = "select * from EQX_DART.SNAPSHOT_SV_ASSET_DA where DFR_ID = '#dfrLineId#' and ASSET_NUM IN (:assets)";

	public static final String SBL_COUNT_BY_DFR = "select count(*) from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where DFR_ID = #dfrId# and HEADER_01 = 'Y' ";

	public static final String SYSTEM_NAME_LIST = "select DISTINCT SYSTEM_NAME from EQX_DART.SIEBEL_ASSET_DA where STATUS_CD = 'Active' AND SYSTEM_NAME LIKE '%#keyword#%' ";

	public static final String REGION_LIST = "select DISTINCT REGION from EQX_DART.IBX_COUNTRY";

	public static final String COUNTRY_N_IBX_BY_REGION = "select DISTINCT COUNTRY,IBX from EQX_DART.IBX_COUNTRY where REGION = ?";

	public static final String IBX_BY_COUNTRY_N_REGION = "select DISTINCT IBX from EQX_DART.IBX_COUNTRY where REGION = '#region#' AND COUNTRY IN (:country)";

	public static final String ERRORS_BY_IBX = "SELECT DISTINCT ERROR_CODE, ERROR_NAME FROM EQX_DART.CXI_ERROR_TBL where IBX IN (:ibxs)";

	public static final String ACC_NUMBER_BY_IBX = "SELECT DISTINCT OU_NUM,ACCOUNT_NAME FROM EQX_DART.SIEBEL_ASSET_DA WHERE STATUS_CD = 'Active' AND REGION = (:region)";

	public static final String ACTIVE_ERROR_LIST = "SELECT M.ERROR_CODE, M.ERROR_NAME "
			+ "FROM EQX_DART.SRC_CXI_ERROR_MASTER_TBL M WHERE M.ACTIVE = 'Y' AND ERROR_CODE LIKE 'VAL%' ORDER BY 1";

	/*
	 * "SELECT DISTINCT ERR.ERROR_CODE," +" ERR.ERROR_NAME FROM " +
	 * " EQX_DART.SIEBEL_ASSET_DA SDA, EQX_DART.SRC_CXI_ERROR_TBL ERR," +
	 * " EQX_DART.SRC_CXI_ERROR_MASTER_TBL m WHERE SDA.ASSET_NUM=" +
	 * " ERR.ERROR_ITEM and m.error_code =" +" err.error_code and m.active='Y'"
	 * +" AND SDA.STATUS_CD='Active'" +" AND ERR.ERROR_END_DATE IS NULL";
	 */

	public static final String ATTR_FAMILIES_LIST = "Select DISTINCT attribute_fmly from eqx_dart.dependent_attr_upd where sql2 is not null Order by attribute_fmly";

	public static final String TOTAL_ASSET_WITH_ERROR_N_FIXED_CT = "SELECT COUNT(ASSET_NUM) TOTAL_ERR_CNT ,COUNT(FLG) TOTAL_FIXED_CNT"
			+ " FROM ( SELECT DFR_ID, ASSET_NUM, CASE WHEN MIN(CLOSED_ERROR_FLG) = 'N' THEN NULL ELSE MIN(CLOSED_ERROR_FLG) END FLG"
			+ " FROM ( SELECT SDA.DFR_ID, ASSET_NUM, VALID_STAT, ERROR_CODE, CASE WHEN VALID_STAT = 'Closed' THEN 'Y' ELSE 'N' END"
			+ " CLOSED_ERROR_FLG FROM EQX_DART.S_ASSET SDA, EQX_DART.CXI_ERROR_TBL ERR WHERE SDA.DFR_ID = ? "
			+ " AND SDA.HEADER_01 = 'Y' AND ERR.DFR_ID = SDA.DFR_ID AND ERR.ERROR_ITEM = SDA.ASSET_NUM"
			+ " AND ERR.ERR_UNIQUE_ID LIKE 'ERR%')"
			+ "GROUP BY DFR_ID ,ASSET_NUM)";

	public static final String TOTAL_CHANGES_MADE_CT = "SELECT COUNT(NVL(NV.ASSET_NUM,SDA.ASSET_NUM)) count"
			+ " FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA, EQX_DART.ASSET_NEW_VAL NV"
			+ " WHERE SDA.DFR_LINE_ID = NV.DFR_LINE_ID AND SDA.DFR_ID = ?"
			+ " AND SDA.HEADER_01 = 'Y' and NV.ATTR_350 = 'Y'";
	
	public static final String TOTAL_ASSET_WITH_NEW_ERROR_CT = "SELECT COUNT(ASSET_NUM) count "
			+ " FROM ( SELECT DFR_ID, ASSET_NUM, CASE WHEN MIN(CLOSED_ERROR_FLG) = 'N' THEN NULL ELSE MIN(CLOSED_ERROR_FLG) END FLG"
			+ " FROM ( SELECT SDA.DFR_ID, ASSET_NUM, VALID_STAT, ERROR_CODE, CASE WHEN VALID_STAT = 'Closed' THEN 'Y' ELSE 'N' END"
			+ " CLOSED_ERROR_FLG FROM EQX_DART.S_ASSET SDA, EQX_DART.CXI_ERROR_TBL ERR WHERE SDA.DFR_ID = ? "
			+ " AND SDA.HEADER_01 = 'Y' AND ERR.DFR_ID = SDA.DFR_ID AND ERR.ERROR_ITEM = SDA.ASSET_NUM"
			+ " AND ERR.ERR_UNIQUE_ID NOT LIKE 'ERR%' AND NVL(ERR.VALID_STAT,'X') <> 'Closed')"
			+ "GROUP BY DFR_ID ,ASSET_NUM)";

	public static String SBL_ERR_Q = "SELECT 'SBL' TBL ,  SD.ROW_ID , NVL(SD.ASSET_NUM,NV.ASSET_NUM) AS ASSET_NUM, SD.NAME,  SD.CAGE_UNIQUE_SPACE_ID, SD.CAB_UNIQUE_SPACE_ID,"
			+ " ERR.ERROR_CODE,  MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING ,"
			+ " ERR.STATUS_CD, ERR.VALID_STAT, MER.ALERT_FLAG   FROM EQX_DART.CXI_ERROR_TBL ERR"
			+ " INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER  ON ERR.ERROR_CODE = MER.ERROR_CODE"
			+ " INNER JOIN EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SD  ON SD.DFR_LINE_ID = ERR.SBL_DFR_LINE_ID"
			+ " INNER JOIN EQX_DART.ASSET_NEW_VAL NV  ON NV.DFR_LINE_ID=SD.DFR_LINE_ID"
			+ " WHERE NVL(SD.ASSET_NUM,NV.ASSET_NUM) IS NOT NULL AND SD.DFR_ID='#id#' AND SD.HEADER_01 = 'Y'";

	public static String APPLY_ERR_LIKE_CONDITION = " AND ERR.ERR_UNIQUE_ID LIKE 'ERR%'";
	
	public static String APPLY_ERR_NOT_LIKE_CONDITION = " AND ERR.ERR_UNIQUE_ID NOT LIKE 'ERR%'";

	/**
	 * Start of POE Count Queries
	 */

	/*
	 * public static String POE_COUNT_WITH_KPI_Q =
	 * "SELECT SDA1.NAME ,COUNT(DISTINCT Z.ERROR_ITEM) AS COUNT FROM " +
	 * "EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA1 LEFT OUTER JOIN " +
	 * "( SELECT SDA.DFR_LINE_ID,   ERR.ERROR_ITEM FROM EQX_DART.ASSET_NEW_VAL NV, "
	 * +
	 * "EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA, EQX_DART.CXI_ERROR_TBL ERR WHERE SDA.DFR_LINE_ID = NV.DFR_LINE_ID AND SDA.DFR_ID = '#id#'"
	 * + " AND SDA.HEADER_01 = 'Y' ";
	 */

	public static String POE_COUNT_WITH_KPI_Q = "SELECT SDA1.NAME, COUNT(DISTINCT Z.ERROR_ITEM) AS COUNT"
			+ " FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA1 LEFT OUTER JOIN ( SELECT SDA.DFR_LINE_ID,"
			+ " NVL(NV.ASSET_NUM,SDA.ASSET_NUM) ERROR_ITEM FROM EQX_DART.ASSET_NEW_VAL NV , "
			+ " EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA WHERE SDA.DFR_LINE_ID = NV.DFR_LINE_ID"
			+ " AND SDA.DFR_ID = ? AND SDA.HEADER_01 = 'Y'";

	public static String START_EXISTS_FOR_ASSETS_WITH_ERRORS = " AND EXISTS ( SELECT 1 FROM EQX_DART.CXI_ERROR_TBL ERR "
			+ " WHERE ERR.ERROR_ITEM = NVL(NV.ASSET_NUM,SDA.ASSET_NUM) AND nvl(ERR.VALID_STAT,'A') <> 'Closed' "
			+ " AND ERR.ERROR_END_DATE IS NULL AND ERR.DFR_ID = SDA.DFR_ID ";

	public static String END_EXISTS_FOR_ASSETS_WITH_ERRORS = ")";

	public static String END_POE_COUNT_Q = ") Z ON Z.DFR_LINE_ID = SDA1.DFR_LINE_ID"
			+ " WHERE SDA1.DFR_ID = ? AND SDA1.HEADER_01 = 'Y' GROUP BY SDA1.NAME";

	public static String POE_COUNT_WITH_DEFAULT_Q = "SELECT NAME, COUNT(*) AS COUNT FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA "
			+ "where DFR_ID = '#id#' and HEADER_01 = 'Y' GROUP BY NAME";

	public static String APPLY_ERROR_CODE_FILTER = " AND ERR.ERROR_CODE IN (#errors#)";

	public static String APPLY_CHANGES_MADE_FILTER = " AND NV.ATTR_350 = 'Y'";

	public static String APPLY_GROUPBY_NAME_FILTER = " AND ERR.ERROR_ITEM = NVL(NV.ASSET_NUM,SDA.ASSET_NUM) AND ERR.ERROR_END_DATE IS NULL "
			+ " AND ERR.DFR_ID = SDA.DFR_ID) Z ON Z.DFR_LINE_ID = SDA1.DFR_LINE_ID WHERE SDA1.DFR_ID = '#id#' "
			+ " AND SDA1.HEADER_01 = 'Y' GROUP BY SDA1.NAME";

	/**
	 * End of POE Count Queries
	 */

	/**
	 * Start OF Refresh attr Grid view Queries by Filters Like(KPI,EC,POE)
	 */

	public static String BASIC_Q_FOR_GRID_BY_KPI = "SELECT outer.* FROM (SELECT ROWNUM rn, inner.*"
			+ " FROM ( SELECT SDA.* FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA, EQX_DART.ASSET_NEW_VAL NV"
			+ " WHERE SDA.DFR_LINE_ID = NV.DFR_LINE_ID AND SDA.HEADER_01 = 'Y' AND SDA.DFR_ID = ? ";
	// add cm filter on demand

	public static String START_ERROR_CONDITION = " AND EXISTS ( SELECT 1 FROM EQX_DART.CXI_ERROR_TBL ERR"
			+ " WHERE ERR.ERROR_END_DATE IS NULL AND ERR.DFR_ID = SDA.DFR_ID"
			+ " AND ERR.ERROR_ITEM = NVL(NV.ASSET_NUM,SDA.ASSET_NUM) AND nvl(ERR.VALID_STAT,'A') <> 'Closed'";
	/**
	 * Apply Error classification filter on demand
	 */
	public static String CLOSE_ERROR_CONDITION = ")";

	public static String END_BLOCK_OF_GRID_BY_KPI_Q = " AND SDA.NAME = ? ) inner) outer WHERE outer.rn > ? AND outer.rn <= ?";
	/**
	 * End Of Refresh attr Grid view Queries by Filters Like(KPI,EC,POE)
	 */
	
	
	/**
	 * Start
	 *  Common Query for Data grid with error filter or without error filter
	 */
	
	public static String DATA_GRID_COMMON_Q = " WITH RED_VAL_ATTR_NAME AS"

		+" (SELECT DISTINCT ERR.DFR_ID, ERR.ERROR_ITEM"

        +"   ,   LISTAGG(ERR.ERROR_CODE,',') WITHIN GROUP(ORDER BY ERR.ERROR_CODE) OVER(PARTITION BY ERR.ERROR_ITEM) RED_VAL_CODES"

        +"   ,   LISTAGG(M.UI_ATTRIBUTE_NAME,',') WITHIN GROUP(ORDER BY M.UI_ATTRIBUTE_NAME) OVER(PARTITION BY ERR.ERROR_ITEM) RED_ATTR_NAMES"

        +" FROM EQX_DART.CXI_ERROR_TBL ERR"

        +" , EQX_DART.SRC_CXI_ERROR_MASTER_TBL M"

        +" WHERE M.ERROR_CODE = ERR.ERROR_CODE"

        +"  AND ERR.DFR_ID = ?"

        +"  AND NVL(ERR.VALID_STAT,'X') <> 'Closed'),"

        +" GREEN_VAL_ATTR_NAME AS"

        +" (SELECT DISTINCT ERR.DFR_ID, ERR.ERROR_ITEM"

        +" , LISTAGG(ERR.ERROR_CODE,',') WITHIN GROUP(ORDER BY ERR.ERROR_CODE) OVER(PARTITION BY ERR.ERROR_ITEM) GREEN_VAL_CODES"

        +" , LISTAGG(M.UI_ATTRIBUTE_NAME,',') WITHIN GROUP(ORDER BY M.UI_ATTRIBUTE_NAME) OVER(PARTITION BY ERR.ERROR_ITEM) GREEN_ATTR_NAMES"

        +" FROM EQX_DART.CXI_ERROR_TBL ERR"

        +" , EQX_DART.SRC_CXI_ERROR_MASTER_TBL M"

        +" WHERE M.ERROR_CODE = ERR.ERROR_CODE"

        +" AND ERR.DFR_ID = ?"

        +" AND NVL(ERR.VALID_STAT,'X') = 'Closed')"

        +" SELECT RED_VAL_ATTR_NAME.RED_VAL_CODES DQM_ERROR_DESCRIPTION"

        +" , GREEN_VAL_ATTR_NAME.GREEN_VAL_CODES FIXED_VAL_CODES"

        +" , RED_VAL_ATTR_NAME.RED_ATTR_NAMES"

        +" , GREEN_VAL_ATTR_NAME.GREEN_ATTR_NAMES"

        +" , CASE WHEN RED_VAL_ATTR_NAME.RED_VAL_CODES IS NOT NULL THEN 'Y' ELSE NULL END RED_ROW_IDENTIFIER"

        +" , CASE WHEN RED_VAL_ATTR_NAME.RED_VAL_CODES IS NULL AND  GREEN_VAL_ATTR_NAME.GREEN_VAL_CODES IS NOT NULL THEN 'Y' ELSE NULL END GREEN_ROW_IDENTIFIER"

        +" ,  SDA.*"

        +" FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SDA"

        +", EQX_DART.ASSET_NEW_VAL NV"

        +", RED_VAL_ATTR_NAME"

        +" , GREEN_VAL_ATTR_NAME"

        +" WHERE SDA.DFR_LINE_ID = NV.DFR_LINE_ID"

        +" AND SDA.HEADER_01 = 'Y'"

        +" AND SDA.DFR_ID = ?"

        +" AND SDA.DFR_ID = RED_VAL_ATTR_NAME.DFR_ID(+)"

        +" AND NVL(NV.ASSET_NUM, SDA.ASSET_NUM) = RED_VAL_ATTR_NAME.ERROR_ITEM(+)"

        +" AND SDA.DFR_ID = GREEN_VAL_ATTR_NAME.DFR_ID(+)"

        +" AND NVL(NV.ASSET_NUM, SDA.ASSET_NUM) = GREEN_VAL_ATTR_NAME.ERROR_ITEM(+)";
		
		public static String START_DATA_GRID_ERROR_CONDITION = " AND EXISTS ( SELECT 1"

                    +" FROM EQX_DART.CXI_ERROR_TBL ERR "

                    +" WHERE  ERR.ERROR_END_DATE IS NULL"
               
                    +" AND ERR.DFR_ID = SDA.DFR_ID"
                    
                    +" AND NVL(ERR.VALID_STAT,'X') <> 'Closed'"
					
					+" AND ERR.SBL_DFR_LINE_ID = SDA.DFR_LINE_ID";
                    
		/*
		 * Close error condition with )
		 */
		
		public static String APPLY_PRODUCT_NAME_FILTER = " AND SDA.NAME = ?";
		
		public static String APPLY_ORDER_BY_FILTER = " Order By SDA.Asset_NUM ASC NULLS First";
		
		public static String APPLY_DFR_LINE_ID_FILTER = " AND SDA.DFR_LINE_ID IN (#dfrLineIds#)";
		
		
	/**
	 * End
	 *  Common Query for Data grid with error filter or without error filter
	 */
		
		/**
		 * Get List of Notes y DFR_ID
		 */
		
		public static String DFR_NOTES_LIST_Q = "SELECT * FROM EQX_DART.DFR_NOTES where DFR_ID = ? ORDER BY CREATED_DT DESC";
		
		public static String CHANGE_SUMMARY_LIST = "SELECT ASSET_NUM ,ATTR_NAME, ATTR_VALUE,OLD_VALUE, CREATE_DATE, USER_ID,"
				+ " DFR_ID,DFR_LINE_ID,PRODUCT_NAME "
				+" FROM EQX_DART.CHANGE_SUMMARY A WHERE DFR_ID = ? AND CHANGE_SUMMARY_ID IN ( SELECT MAX(CHANGE_SUMMARY_ID) "
				+ " FROM EQX_DART.CHANGE_SUMMARY WHERE DFR_ID = ? GROUP BY ASSET_NUM,ATTR_NAME)"
				+" ORDER BY ASSET_NUM, CREATE_DATE DESC,ATTR_NAME";
		
		public static final String DFR_LINE_ID_LIST_BY_DFR_ID = "SELECT NAME,DFR_LINE_ID, ASSET_NUM FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA WHERE DFR_ID = ? AND HEADER_01 = 'Y' Order By Asset_NUM ASC";

		/*public static final String DFR_LINE_ID_LIST_BY_DFR_ID ="WITH REC AS (select SBL.NAME, SBL.DFR_LINE_ID from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SBL LEFT"
				+ " OUTER join EQX_DART.ASSET_NEW_VAL NVL on NVL.DFR_ID = SBL.DFR_ID where SBL.dfr_id = ? "
				+ " order by CASE WHEN SBL.ASSET_NUM IS NULL THEN NVL.ASSET_NUM ELSE SBL.ASSET_NUM END asc ) "
				+ " SELECT * FROM REC GROUP BY NAME, DFR_LINE_ID";*/

		
		public static final String IS_ASSIGN_TEAM_EXIST = "SELECT COUNT(*) FROM EQX_DART.ASSIGN_GROUP where GROUP_NAME = ?";

		public static final String IBX_LIST_FOR_ASSET_MOVE = "SELECT DISTINCT(IBX) FROM  EQX_DART.SRC_IBX_COUNTRY WHERE DTL__CAPXRESTART2 = 'Y'"; 

		public static final String CHECK_SYSTEM_NAME_COUNT="select case when count(distinct system_name) = 1 then 'True' else 'False' end as Status from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where dfr_id = ?";

		public static final String PRODUCT_LIST = "select PRODUCT_NAME from EQX_DART.PRODUCT_CONFIG where PRODUCT_CATEGORY =  'Network' order by sequence desc";
}
