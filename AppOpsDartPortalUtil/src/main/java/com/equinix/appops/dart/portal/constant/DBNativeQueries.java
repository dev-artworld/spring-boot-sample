package com.equinix.appops.dart.portal.constant;

public class DBNativeQueries {
	 public static final String SBL_MOBILE_QUERY = " SELECT 'SBL' TBL ,  SD.ROW_ID , SD.ASSET_NUM, SD.NAME, "
		 		+ " SD.CAGE_UNIQUE_SPACE_ID, SD.CAB_UNIQUE_SPACE_ID, ER.ERROR_CODE, "
					+ " MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING ,  " 
					+ " ER.STATUS_CD, ER.VALID_STAT, MER.ALERT_FLAG  "  
					+ " FROM EQX_DART.CXI_ERROR_TBL ER   " 
					+ " INNER JOIN EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA SD  ON ER.SBL_DFR_LINE_ID = SD.DFR_LINE_ID " 
					+ " INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER  ON MER.ERROR_CODE = ER.ERROR_CODE  " 
					+ " WHERE MER.ACTIVE = 'Y'   " 
					+ " AND SD.ASSET_NUM IS NOT NULL   " 
					+ " AND ER.ERROR_END_DATE IS NULL  " 
					+ " and sd.dfr_line_id = ?"   ;
	 
	 
	 public static final String CLX_MOBILE_QUERY = " SELECT 'SBL' TBL ,  SD.ROW_ID , SD.ASSET_NUM, SD.NAME, "
		 		+ " SD.CAGE_UNIQUE_SPACE_ID, SD.CAB_UNIQUE_SPACE_ID, ER.ERROR_CODE, "
					+ " MER.ERROR_NAME,  MER.VALIDATION_CLASS,  MER.OWNER_OF_FIXING ,  " 
					+ " ER.STATUS_CD, ER.VALID_STAT, MER.ALERT_FLAG  "  
					+ " FROM EQX_DART.CXI_ERROR_TBL ER   " 
					+ " INNER JOIN EQX_DART.SNAPSHOT_CLX_ASSET_DA SD  ON ER.CLX_DFR_LINE_ID = SD.DFR_LINE_ID " 
					+ " INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER  ON MER.ERROR_CODE = ER.ERROR_CODE  " 
					+ " WHERE MER.ACTIVE = 'Y'   " 
					+ " AND SD.ASSET_NUM IS NOT NULL   " 
					+ " AND ER.ERROR_END_DATE IS NULL  " 
					+ " AND SD.DFR_ID = '#DFRID#'"
					+ " and #LHS# IN (:ids)"   ;
	 
	 public static final String CLX_CAGE_MOBILE_QUERY = CLX_MOBILE_QUERY.replace("#LHS#", "CAGE_UNIQUE_SPACE_ID");
	 
	 public static final String CLX_CAB_MOBILE_QUERY = CLX_MOBILE_QUERY.replace("#LHS#", "CAB_UNIQUE_SPACE_ID");
	 
	 public static final String SV_MOBILE_QUERY = "SELECT 'SV' TBL ,"
			  +"  SV.ROW_ID,"
			  +"  SV.ASSET_NUM,"
			  +"  ER.ERROR_CODE,"
			  +"  MER.ERROR_NAME,"
			  +"  MER.VALIDATION_CLASS,"
			  +"  MER.OWNER_OF_FIXING,"
			  +"  ER.STATUS_CD, ER.VALID_STAT "
			  +"  FROM EQX_DART.CXI_ERROR_TBL ER"
			  +"  INNER JOIN EQX_DART.SRC_CXI_ERROR_MASTER_TBL MER"
			  +"  ON ER.ERROR_CODE = MER.ERROR_CODE"
			  +"  INNER JOIN EQX_DART.SNAPSHOT_SV_ASSET_DA SV"
			  +"  ON SV.DFR_LINE_ID = ER.SV_DFR_LINE_ID WHERE  (SV.ASSET_NUM IS NOT NULL AND SV.DFR_ID='#DFRID#')"
			  + " AND SV.ASSET_NUM in (:ids) ";
	
	 public static final String LOGGED_IN_USER_GROUP_USERS_QUERY =" SELECT UI.USER_ID, R.ROLE_NAME, AG.GROUP_NAME, UR.PRIMARY FROM EQX_DART.ASSIGN_GROUP AG "
 +"  INNER JOIN EQX_DART.USER_GROUP UG ON AG.ROW_ID = UG.GROUP_ID "
 +"  INNER JOIN EQX_DART.USER_INFO UI ON UG.USER_ID = UI.PK_ID "
 +"  INNER JOIN EQX_DART.USER_INFO_ROLE UR ON UR.USER_ID = UI.PK_ID "
 +"  INNER JOIN EQX_DART.ROLE R ON R.PK_ID = UR.ROLE_ID "
 +"  where ag.group_name = ? and ur.primary = 1";  
	 
	 
	 public static final String MY_TEAM_DFR_QUERY = "SELECT  COUNT(*) FROM EQX_DART.DFR_MASTER "
	 		+ "WHERE CREATED_TEAM = ? AND STATUS = 'Physical Audit Initiated'";
	 
	 public static final String MY_TEAM_UNASSIGNED_DFR_QUERY = "SELECT  COUNT(*) FROM EQX_DART.DFR_MASTER WHERE ASSIGNED_TO is null and CREATED_TEAM = ? "
	 		+ " AND STATUS = 'Physical Audit Initiated' ";

 
	 public static final String MY_DFRS_QUERY = "SELECT  COUNT(*) FROM EQX_DART.DFR_MASTER WHERE ASSIGNED_TO = ?  " //AND CREATED_TEAM = ? "
	 		+ " AND STATUS = 'Physical Audit Initiated'";
 
	 public static final String MY_CREATED_DFR_QUERY = "SELECT  COUNT(*) FROM EQX_DART.DFR_MASTER WHERE  CREATED_BY= ? " //AND CREATED_TEAM = ? "
		 		+ " AND STATUS = 'Physical Audit Initiated'";

	 public static final String AUDIT_PROGRESS_QUERY =  "SELECT ((b.cmplete/a.total) * 100 ) as auditProgress , B.CMPLETE , A.TOTAL "
			 +"	FROM "
			 +"	  (SELECT B.DFR_ID,"
			 +"	    B.HEADER_01,"
			 +"	    COUNT(*) AS TOTAL"
			 +"	  FROM EQX_DART.DFR_MASTER A,"
			 +"	    EQX_DART.Asset_new_val B"
			 +"	  WHERE A.dfr_id =?"
			 +"	  AND A.dfr_id   =B.dfr_id"
			 +"	  AND b.header_01='Y'"
			 +"	  GROUP BY b.dfr_id,"
			 +"	    B.HEADER_01"
			 +"	  ) A,"
			 +"	  (SELECT b.dfr_id,"
			 +"	    B.HEADER_01,"
			 +"	    COUNT(*) AS CMPLETE"
			 +"	  FROM EQX_DART.DFR_MASTER A,"
			 +"	    EQX_DART.ASSET_NEW_VAL B"
			 +"	  WHERE A.DFR_ID   =?"
			 +"	  AND A.DFR_ID     =B.DFR_ID"
			 +"	  AND B.HEADER_01  ='Y'"
			 +"	  AND B.HEADER_07 IS NOT NULL"
			 +"	  GROUP BY B.DFR_ID,"
			 +"	    B.HEADER_01"
			 +"	  ) B "
			 +"	WHERE A.DFR_ID= B.DFR_ID";
	 
	 public static final String IS_CABINET_OR_ITS_CHILD_MARKED_FOR_AUDIT_QUERY = "SELECT ROW_ID, "
+"	    X_OPS_PAR_ASSET_NUM , "
+"	    NAME, "
+"	    CAB_UNIQUE_SPACE_ID , HEADER_01 "
+"	  FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA "
+"	  WHERE  "
+"	  ROW_ID              IN "
+"	    (SELECT DISTINCT X_OPS_PAR_ASSET_NUM "
+"	    FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA "
+"	    WHERE header_01 = 'Y' "
+"	    AND DFR_ID      = ? "
+"	    ) "
+"	  UNION "
+"	  SELECT ROW_ID, "
+"	    X_OPS_PAR_ASSET_NUM, "
+"	    NAME , "
+"	    CAB_UNIQUE_SPACE_ID , HEADER_01 "
+"	  FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA "
+"	  WHERE  HEADER_01            = 'Y' "
+"	  AND DFR_ID               = ? ";

	 public static final String MAXIMO_COLUMN = "description";
	 public static final String MAXIMO_QUERY = " select distinct  "+MAXIMO_COLUMN+"  from EQX_DART.Maximo_Child_Asset_V where  "+MAXIMO_COLUMN+" like '#p1#' and "+MAXIMO_COLUMN+" like '#p2#' order by "+MAXIMO_COLUMN+" asc ";

     public static final String ASSET_PREVIEW_QUERY = "SELECT name,"
+"		  CASE "
+"		    WHEN dqm_err_flg IS NULL "
+"		    THEN 'Y' "
+"		    WHEN dqm_err_flg = 'Y' "
+"		    THEN 'Y' "
+"		    WHEN dqm_err_flg = 'N' "
+"		    THEN 'N' "
+"		  END AS isError , "
+"		  CASE "
+"		    WHEN (header_01 !='Y' or header_01 is null) "
+"		    THEN 'N' "
+"		    WHEN header_01 = 'Y' "
+"		    THEN 'Y' "
+"		  END      AS isSelected, "
+"		  COUNT(*) AS assetCount "
+"			FROM eqx_dart.snapshot_siebel_asset_da "
+"			WHERE dfr_id = ? "
+"			GROUP BY name, "
+"		  dqm_err_flg, "
+"	  header_01";

     public static final String IS_MOBILE_DFR_QUERY = "select is_mobile_dfr from eqx_dart.dfr_master where dfr_id = ?";
     
     public static final String UPDATE_SBL_SYNC_FLAG_Y_QUERY = "update eqx_dart.siebel_asset_da set SYNC_STATUS = 'Y' where row_id = ? ";
     
     public static final String UPDATE_CLX_SYNC_FLAG_Y_QUERY = "update eqx_dart.clx_asset_da set SYNC_STATUS = 'Y' where row_id = ? ";
     
     public static final String UPDATE_SV_SYNC_FLAG_Y_QUERY = "update eqx_dart.sv_asset_da set SYNC_STATUS = 'Y' where row_id = ? ";
     
     public static final String UPDATE_ERROR_SYNC_FLAG_Y_QUERY = "update eqx_dart.src_cxi_error_tbl set SYNC_STATUS = 'Y' where ERR_UNIQUE_ID = ? ";


     public static final String QUERY_TO_CHECK_IS_ANY_CHILD_AC_DC_MARKED_SELECTED = "SELECT count(dfr_line_id)  FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA  WHERE X_OPS_PAR_ASSET_NUM  "
     		+ "=  ? and header_01 = 'Y'";

     public static final String LAST_AUDIT_DATE_QUERY = "select header_07 as lastAuditDateStr from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where header_07 is not null and dfr_id = ?";
     
//     public static final String ASSET_NEW_VAL_CHANGE_SUMMARY = "SELECT DFR_ID,NAME,ASSET_NUM FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA WHERE DFR_LINE_ID = ?";
     
     public static final String ASSET_NEW_VAL_CHANGE_SUMMARY = "SELECT A.DFR_ID,A.NAME,NVL(A.ASSET_NUM,B.ASSET_NUM) AS ASSET_NUM FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA A, "
     															+ " EQX_DART.ASSET_NEW_VAL B WHERE A.DFR_LINE_ID = B.DFR_LINE_ID "
     															+ " AND A.DFR_LINE_ID = ? ";
     
     public static final String ASSET_NEW_FROM_NEW_VAL = "select ASSET_NUM from EQX_DART.ASSET_NEW_VAL where dfr_line_id = ?";
     
     public static final String UPDATE_SEIBEL_ASSET_SELECTED_Y = "update eqx_dart.SNAPSHOT_SIEBEL_ASSET_DA set header_01 = 'Y' where dfr_line_id = '#value#' ";

     public static final String UPDATE_SEIBEL_ASSET_SELECTED_N = "update eqx_dart.SNAPSHOT_SIEBEL_ASSET_DA set header_01 = 'N' where dfr_line_id = '#value#' ";

     public static final String EP_CBP_CHECK_QUERY = 
    		 
    		 "Select CAB_UNIQUE_SPACE_ID as cabUsid, CAGE_UNIQUE_SPACE_ID as cageUsid  ,"
    		 + " X_CAB_ID as cabId,"
    		 + " name, system_name , serial_num, #cbpcol# as cbp , 'p' as relation "
    				 +" from EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where dfr_line_id in ( "
    				 +" SELECT DA.X_OPS_PAR_ASSET_NUM FROM "
    				 +" EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA DA INNER JOIN EQX_DART.ASSET_NEW_VAL NV ON DA.DFR_LINE_ID = NV.DFR_LINE_ID "
    				 +" WHERE NV.ATTR_47 = '#ep#' AND NV.#cbpcol# = '#cbpval#' AND NV.DFR_ID = '#dfrid#' AND NV.NAME= '#product#') "
    				 +" UNION "
    				 +" SELECT CASE  when NV2.CAB_UNIQUE_SPACE_ID IS NULL  THEN DAT.CAB_UNIQUE_SPACE_ID  ELSE NV2.CAB_UNIQUE_SPACE_ID end as cabUsid, "
    				 + " CASE  when NV2.CAGE_UNIQUE_SPACE_ID IS NULL  THEN DAT.CAGE_UNIQUE_SPACE_ID  ELSE NV2.CAGE_UNIQUE_SPACE_ID end as cageUsid ,"
    				 + " CASE  when NV2.X_CAB_ID IS NULL  THEN DAT.X_CAB_ID  ELSE NV2.X_CAB_ID end as cabId,"
    				 + " nv2.NAME, CASE  WHEN NV2.SYSTEM_NAME IS NULL  THEN DAT.SYSTEM_NAME  ELSE NV2.SYSTEM_NAME END as system_name,"
    				 + " CASE  WHEN NV2.SERIAL_NUM IS NULL  THEN DAT.SERIAL_NUM  ELSE NV2.SERIAL_NUM END as serial_num,"
    				 + " nv2.#cbpcol# as cbp  , 'c'"
    				 + " FROM EQX_DART.ASSET_NEW_VAL  NV2 INNER JOIN EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA DAT ON nv2.DFR_LINE_ID = DAT.DFR_LINE_ID "
    				 +" WHERE NV2.ATTR_47 = '#ep#' AND NV2.#cbpcol# = '#cbpval#' AND NV2.DFR_ID = '#dfrid#' AND NV2.NAME= '#product#' ";
     
     public static final String DISTINCT_ROLE_NAME = "SELECT DISTINCT ROLE_NAME FROM EQX_DART.ROLE";
     
     public static final String DISTINCT_ASSIGN_GROUP_NAME = "SELECT DISTINCT GROUP_NAME FROM EQX_DART.ASSIGN_GROUP";
     public static final String CHILD_ASSET_QUERY = "select dfr_line_id From EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA where x_ops_par_asset_num = '?'";

     public static final String DFR_ASSETS_QUERY = "select SERIAL_NUM , asset_num, name, dfr_line_id from eqx_dart.snapshot_siebel_asset_da where dfr_id = '?'";

     public static final String RESTRICTION_QUERY_BY_CAGE_DFR_LINE_ID = "SELECT count(row_id) as isRestrictionThere FROM EQX_DART.SNAPSHOT_SIEBEL_ASSET_DA WHERE DFR_LINE_ID = ? "
+" AND ( "
+"  (ATTR_53 != 'No Restriction' AND ATTR_53 != '-' )  " 
+"  OR ( ATTR_52 != 'No Restriction' AND ATTR_52 != '-' ) "
+"  OR ( ATTR_51 != 'No Restriction' and ATTR_51 != '-') "
+"  OR ( ATTR_50 != 'No Restriction' AND ATTR_50 != '-') "
+"  OR ( ATTR_49 != 'No Restriction' AND ATTR_49 != '-') "
+"  OR ( ATTR_48 !='No Restriction' AND ATTR_48 != '-') "
+" )";
} 
 
 
 