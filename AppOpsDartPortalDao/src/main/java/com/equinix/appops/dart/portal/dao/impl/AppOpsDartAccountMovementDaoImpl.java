package com.equinix.appops.dart.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.dao.AppOpsDartAccountMovementDao;
import com.equinix.appops.dart.portal.entity.AccountMoveRequest;
import com.equinix.appops.dart.portal.entity.PortAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.AssetVaildationDataMapper;
import com.equinix.appops.dart.portal.mapper.accountmove.OpsHierarchyAssetMapper;
import com.equinix.appops.dart.portal.mapper.accountmove.PortAssetMapper;
import com.equinix.appops.dart.portal.mapper.accountmove.RootAssetMapper;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetResponse;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveSearchFilter;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.AssetVaildationData;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;

@Repository
@Transactional
public class AppOpsDartAccountMovementDaoImpl implements AppOpsDartAccountMovementDao {

	Logger logger = LoggerFactory.getLogger(AppOpsDartAccountMovementDaoImpl.class);

	private static final String GET_ROOT_ASSET_ID = "SELECT A.asset_num, A.row_id" + " FROM"
			+ " EQX_DART.SRC_S_Asset A," + " EQX_DART.SRC_S_org_ext O," + " EQX_DART.SRC_CX_EQX_UNQ_SPC U,"
			+ " EQX_DART.SRC_S_prod_int P1" + " WHERE" + " A.owner_accnt_id  = O.par_row_id"
			+ " AND A.X_UNIQUE_SPACE_ID = U.EQX_UNQ_SPC_ID" + " AND A.prod_id = P1.row_id " + " AND O.ou_num = ?"
			+ " AND U.EQX_UNIQUE_SPACE_ID = ?" + " AND P1.name = 'Cage'";

	private static final String GET_OPS_HIERARCHY = 
			"SELECT " + 
			"a1.row_id as ROW_ID, " + 
			"a1.asset_num as ASSET_NUM, " + 
			"a1.serial_num as SERIAL_NUM, " + 
			"a1.x_attr_22 as xATTR22, " + 
			"a1.x_attr_25 as ASSET_UCM_ID, " + 
			"o1.X_CUST_UCID as  UCM_ID, " + 
			"o1.ou_num as ACCOUNT, " + 
			"o1.name as ACCOUNTNAME, " + 
			"o2.name as IBX, " + 
			"a1.x_unique_space_id as X_UNIQUE_SPACE_ID, " + 
			"u.eqx_cage_unq_space_id_val as CAGE_USID_VAL, " + 
			"a1.x_cab_usid as CABINET_USID, " + 
			"u2.eqx_unique_space_id as CABUSIDVAL, " + 
			"a1.x_cab_id as CABINETNUMBER, " + 
			"s.eqx_sys_name as SYSNAME, " + 
			"p.name as POENAME, " + 
			"case when p.name = 'Cabinet' and UPPER(p2.name)  like '%KVA%' " + 
			"THEN (SELECT par.ROW_ID FROM EQX_DART.SRC_S_ASSET PAR,EQX_DART.SRC_S_PROD_INT PROD WHERE PAR.PROD_ID = PROD.ROW_ID AND PROD.NAME = 'AC Power'  " + 
			"and par.root_Asset_id = a1.root_Asset_id) " + 
			"else " + 
			"a1.par_asset_id end  " + 
			"as PARASST_ID, " + 
			"a1.root_asset_id as ROOT_AST_ID, " + 
			"PA.ASSET_NUM as POF_AST_NUM, " + 
			"p2.name as POF_NAME, " + 
			"a1.X_REL_ACCNT_ID as REL_ACCT_ID, " + 
			"a1.x_cable_id as CABLE_ID, " + 
			"a1.owner_accnt_id as OWNER_ACCOUTID, " + 
			"a1.x_pp_id as PP_ID, " + 
			"a1.x_ops_par_asset_num, " + 
			"a2.asset_num as PARENT_ASSET#, " + 
			"  CASE " + 
			"  WHEN p.name = 'Cage'      THEN 1 " + 
			"  WHEN ( p.name = 'Cabinet' " + 
			"      OR p.name = 'Demarcation Point' ) THEN 2 " + 
			"  WHEN ( p.name = 'Patch Panel' " + 
			"      OR p.name = 'AC Circuit' " + 
			"      OR p.name = 'DC Circuit' ) THEN 3 " + 
			"  WHEN p.name = 'Network Cable Connection' THEN 4 " + 
			"  WHEN p.name LIKE '%Port%' THEN 5 " + 
			"  ELSE 100 " + 
			" END AS hieararcy_level " + 
			"FROM " + 
			" eqx_dart.src_s_asset a1, " + 
			" eqx_dart.src_s_asset a2, " + 
			" eqx_dart.src_s_prod_int p, " + 
			" eqx_dart.src_s_prod_int p2, " + 
			" eqx_dart.src_s_org_ext o1, " + 
			" eqx_dart.src_s_org_ext o2, " + 
			" eqx_dart.src_cx_eqx_unq_spc u, " + 
			" eqx_dart.src_cx_eqx_unq_spc u2, " + 
			" eqx_dart.src_cx_uni_sys_name s, " + 
			" eqx_dart.src_s_asset PA " + 
			" WHERE " + 
			" a1.x_ops_par_asset_num = a2.row_id (+) " + 
			" AND a1.owner_accnt_id = o1.par_row_id " + 
			" AND a1.serv_acct_id = o2.par_row_id " + 
			" AND a1.prod_id = p.row_id " + 
			" AND a1.x_unique_space_id = u.eqx_unq_spc_id " + 
			" AND a1.x_attr_25 = s.eqx_ucm_id " + 
			" AND a1.x_unique_space_id = s.eqx_cage_unq_space_id " + 
			" and a1.ROOT_ASSET_ID=pa.row_id  " + 
			" AND Pa.prod_id = p2.row_id " + 
			" AND a1.x_cab_usid = u2.eqx_unq_spc_id(+) " + 
			" AND a1.x_root_asset_num = ? " + 
			" and a1.status_cd='Active' and a1.x_attr_01='Ops' " + 
			" order by hieararcy_level asc";

	String PATCH_PANEL_POST_VALIDATION = "select asset_num from eqx_dart.src_s_Asset where root_Asset_id = ? and row_id <> root_Asset_id";
	
	String CABINET_PATCH_PANEL_BILL_VALIDATION = "select sv.asset_num,O.ou_num from eqx_dart.sV_Asset_da sv,eqx_dart.src_s_org_ext O where sv.X_CUST_UCID = O.X_CUST_UCID and  sv.asset_num in (select a.asset_num from eqx_dart.src_s_asset a where a.status_cd = 'Active' start with a.asset_num in (?) CONNECT BY PRIOR  a.row_id = a.X_OPS_PAR_ASSET_NUM)";

	String CABINET_PATCH_PANEL_OPEN_ORDER = "select distinct b.order_num,d.asset_num from eqx_dart.src_s_order_item a, eqx_dart.src_s_order b,  eqx_dart.src_s_asset d where  b.status_cd in ('Booked','In Progress','Submitted','Partial Submission','Open') and a.order_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+)  and d.asset_num in (select a.asset_num from eqx_dart.src_s_asset a,eqx_dart.src_s_prod_int p where p.row_id = a.prod_id and a.status_cd = 'Active' start with a.asset_num in (?) CONNECT BY PRIOR  a.row_id = a.X_OPS_PAR_ASSET_NUM)";

	String CABINET_PATCH_PANEL_OPEN_QUOTE = "select distinct d.asset_num,b.quote_num from eqx_dart.src_s_quote_item a, eqx_dart.src_s_doc_quote b,eqx_dart.src_s_asset d where b.stat_cd in ('Draft','Approved','Pending Approval','Pending Customer Approval') and a.sd_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+) and d.asset_num in (select a.asset_num from eqx_dart.src_s_asset a,eqx_dart.src_s_prod_int p where p.row_id = a.prod_id and a.status_cd = 'Active' start with a.asset_num in (?) CONNECT BY PRIOR  a.row_id = a.X_OPS_PAR_ASSET_NUM)";

	String CIRCUIT_PORT_BILL_VALIDATION = "select sv.asset_num,O.ou_num from eqx_dart.sV_Asset_da sv,eqx_dart.src_s_org_ext O where sv.X_CUST_UCID = O.X_CUST_UCID and  sv.asset_num in (?) ";

	String CIRCUIT_PORT_PANEL_OPEN_ORDER = "select distinct b.order_num,d.asset_num from eqx_dart.src_s_order_item a, eqx_dart.src_s_order b,  eqx_dart.src_s_asset d where  b.status_cd in ('Booked','In Progress','Submitted','Partial Submission','Open') and a.order_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+)  and d.asset_num in (?)";

	String CIRCUIT_PORT_PANEL_OPEN_QUOTE = "select distinct d.asset_num,b.quote_num from eqx_dart.src_s_quote_item a, eqx_dart.src_s_doc_quote b,eqx_dart.src_s_asset d where b.stat_cd in ('Draft','Approved','Pending Approval','Pending Customer Approval') and a.sd_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+) and d.asset_num in (?)";

	//String NCC_BILL_VALIDATION = "select sv.asset_num,O.ou_num from eqx_dart.sV_Asset_da sv,eqx_dart.src_s_org_ext O where sv.X_CUST_UCID = O.X_CUST_UCID and  sv.asset_num in (select asset_num from eqx_dart.src_s_asset where X_OPS_PAR_ASSET_NUM = ?)";

	String NCC_BILL_VALIDATION = "select sv.asset_num,O.ou_num from eqx_dart.sV_Asset_da sv,eqx_dart.src_s_org_ext O where sv.X_CUST_UCID = O.X_CUST_UCID and  sv.asset_num in (select a.asset_num from eqx_dart.src_s_asset a where a.status_cd = 'Active' start with a.asset_num in (?) CONNECT BY PRIOR  a.row_id = a.X_OPS_PAR_ASSET_NUM)";

	String NCC_CHILD_VALIDATION = "select sv.asset_num,O.ou_num from eqx_dart.sV_Asset_da sv,eqx_dart.src_s_org_ext O where sv.X_CUST_UCID = O.X_CUST_UCID and sv.asset_num in(select asset_num from eqx_dart.src_s_Asset where status_cd = 'Active' and root_Asset_id in (select root_Asset_id from eqx_dart.src_s_Asset where asset_num = ? ))";
	
	//String NCC_PANEL_OPEN_ORDER = "select distinct b.order_num,d.asset_num from eqx_dart.src_s_order_item a, eqx_dart.src_s_order b,  eqx_dart.src_s_asset d where  b.status_cd in ('Booked','In Progress','Submitted','Partial Submission','Open') and a.order_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+)  and d.asset_num in (select asset_num from eqx_dart.src_s_asset where X_OPS_PAR_ASSET_NUM = ?)";

	String NCC_PANEL_OPEN_ORDER = "select distinct b.order_num,d.asset_num from eqx_dart.src_s_order_item a, eqx_dart.src_s_order b,  eqx_dart.src_s_asset d where  b.status_cd in ('Booked','In Progress','Submitted','Partial Submission','Open') and a.order_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+)  and d.asset_num in (select a.asset_num from eqx_dart.src_s_asset a,eqx_dart.src_s_prod_int p where p.row_id = a.prod_id and a.status_cd = 'Active' start with a.asset_num in (?) CONNECT BY PRIOR  a.row_id = a.X_OPS_PAR_ASSET_NUM)";


	String NCC_PANEL_OPEN_QUOTE = "select distinct d.asset_num,b.quote_num from eqx_dart.src_s_quote_item a, eqx_dart.src_s_doc_quote b,eqx_dart.src_s_asset d where b.stat_cd in ('Draft','Approved','Pending Approval','Pending Customer Approval') and a.sd_id = b.row_id and A.ASSET_INTEG_ID = D.INTEGRATION_ID(+) and d.asset_num in (select asset_num from eqx_dart.src_s_asset where X_OPS_PAR_ASSET_NUM = ?)";

	String UPDATE_ASSET_ATTR = "update eqx_dart.src_s_asset set" + " owner_accnt_Id = ?," + " x_attr_25 = ?,"
			+ " x_root_asset_num = ?," + " X_OPS_PAR_ASSET_NUM = ?," + " X_UNIQUE_SPACE_ID = ?," + " X_CAB_USID = ?,"
			+ " X_CAB_ID = ?" + " where" + " asset_num = ? ";

	String UPDATE_CABINET_CIRCUIT_ATTR = "Update eqx_dart.src_s_asset set par_row_id = (select root_Asset_id from eqx_dart.src_s_asset where  asset_num = ? ), root_Asset_id = (select root_Asset_id from eqx_dart.src_s_asset where  asset_num = ? ) where asset_num = ? ";

	String UPDATE_NCC_ASSET = "update eqx_dart.src_s_asset set X_PP_ID =? where asset_num = ? ";

	String UPDATE_CAGE_UNIQUE_SPACE_ID = "update eqx_dart.src_s_asset set X_UNIQUE_SPACE_ID = ? where asset_num = ? ";

	String UPDATE_ATTR_EXCEPT_CABINET_AND_PORT = "update eqx_dart.src_s_asset set X_CAB_USID = ?,X_CAB_ID = ?  where asset_num = ?";

	String MODIFIED_PORT_INFO = "select asset_id,char_val port_id from eqx_dart.src_s_asset_xa where"
			+ " asset_id in( :ids ) and attr_name in " + "('Switch Port Instance ID','A-Side Switch Port Instance ID')";
	String MODIFIED_PORT_INFO2 ="select asset_id, char_val port_id from eqx_dart.src_s_asset_xa where asset_id in( :ids ) and attr_name = 'Z-Side Switch Port Instance ID'";
	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	NamedParameterJdbcTemplate namedJdbc; 
	
	@Autowired
	private SessionFactory sessionFactory;

	// SELECT A.asset_num, A.row_id FROM EQX_DART.SRC_S_Asset A,
	// EQX_DART.SRC_S_org_ext O, EQX_DART.SRC_CX_EQX_UNQ_SPC U,
	// EQX_DART.SRC_S_prod_int P1 WHERE A.owner_accnt_id = O.par_row_id AND
	// A.X_UNIQUE_SPACE_ID = U.EQX_UNQ_SPC_ID AND A.prod_id = P1.row_id AND O.ou_num
	// = '117773' AND U.EQX_UNIQUE_SPACE_ID = 'TY5:03:010010' AND P1.name = 'Cage'
	@Override
	public List<RootAsset> getRootAssestId(String accountId, String cageUSID) {
		Object args[] = new Object[] { accountId, cageUSID };
		List<RootAsset> raList = jdbc.query(GET_ROOT_ASSET_ID, args, new RootAssetMapper());
		return raList;
	}

	// SELECT O2.name IBX, A1.asset_num Asset#, P.name Product, A2.asset_num
	// Parent_Asset#, O1.OU_NUM Account#, O1.name Account,
	// U.EQX_CAGE_UNQ_SPACE_ID_VAL Cage, S.EQX_SYS_NAME, A1.x_cab_id Cabinet,
	// A1.x_pp_id PP_ID, A1.x_cable_id Cable_id FROM EQX_DART.SRC_S_Asset A1,
	// EQX_DART.SRC_S_Asset A2, EQX_DART.SRC_S_prod_int P, EQX_DART.SRC_S_org_ext
	// O1, EQX_DART.SRC_S_org_ext O2, EQX_DART.SRC_CX_EQX_UNQ_SPC U,
	// EQX_DART.SRC_CX_UNI_SYS_NAME S WHERE A1.X_OPS_PAR_ASSET_NUM = A2.row_id AND
	// A1.owner_accnt_id = O1.par_row_Id AND A1.serv_acct_id = O2.par_row_id AND
	// A1.prod_id = P.row_id AND A1.X_UNIQUE_SPACE_ID = U.EQX_UNQ_SPC_ID AND
	// A1.x_Attr_25 = S.EQX_UCM_ID AND A1.X_UNIQUE_SPACE_ID =
	// S.EQX_CAGE_UNQ_SPACE_ID AND A1.x_root_Asset_num = '1-JSRARGW'
	@Override
	public List<Asset> getOpsHierarchyByRootAssesstId(OpsHierarchyAsset opsHierarchyAsset, String rootAssetsId) {
		Object args[] = new Object[] { rootAssetsId };
		return jdbc.query(GET_OPS_HIERARCHY, args, new OpsHierarchyAssetMapper(opsHierarchyAsset));
	}

	@Override
	public AccountMoveAssetResponse validateInBillingTable(AccountMoveAssetRequest request) {
		AccountMoveAssetResponse res = new AccountMoveAssetResponse();
		Asset asset = request.getSrcAssets().get(0);
		Object args[] = new Object[] { asset.getAssetNum() };
		List<AssetVaildationData> list = null;
		if (asset.getProduct().equalsIgnoreCase(CacheConstant.CABINET)
				|| asset.getProduct().equalsIgnoreCase(CacheConstant.PATCH_PANEL)) {
			// sv.asset_num,O.ou_num
			list = jdbc.query(CABINET_PATCH_PANEL_BILL_VALIDATION, args,
					new AssetVaildationDataMapper(asset.getProduct(), "BILLING"));
		} else if (asset.getProduct().contains("Circuit") || asset.getProduct().equalsIgnoreCase("Port")) {
			// distinct b.order_num,d.asset_num
			list = jdbc.query(CIRCUIT_PORT_BILL_VALIDATION, args,
					new AssetVaildationDataMapper(asset.getProduct(), "BILLING"));
		} else if (asset.getProduct().equalsIgnoreCase(CacheConstant.NETWORK_CABLE_CONNECTION)) {
			// distinct d.asset_num,b.quote_num
			list = jdbc.query(NCC_BILL_VALIDATION, args, new AssetVaildationDataMapper(asset.getProduct(), "BILLING"));
		}
		res.setList(list);
		return res;
	}

	@Override
	public AccountMoveAssetResponse validateIsAnyOpenOrderExist(AccountMoveAssetRequest request) {
		AccountMoveAssetResponse res = new AccountMoveAssetResponse();
		Asset asset = request.getSrcAssets().get(0);
		Object args[] = new Object[] { asset.getAssetNum() };
		List<AssetVaildationData> list = null;
		if (asset.getProduct().equalsIgnoreCase(CacheConstant.CABINET)
				|| asset.getProduct().equalsIgnoreCase(CacheConstant.PATCH_PANEL)) {
			// sv.asset_num,O.ou_num
			list = jdbc.query(CABINET_PATCH_PANEL_OPEN_ORDER, args,
					new AssetVaildationDataMapper(asset.getProduct(), "OPENORDER"));
		} else if (asset.getProduct().contains("Circuit") || asset.getProduct().equalsIgnoreCase("Port")) {
			// distinct b.order_num,d.asset_num
			list = jdbc.query(CIRCUIT_PORT_PANEL_OPEN_ORDER, args,
					new AssetVaildationDataMapper(asset.getProduct(), "OPENORDER"));
		} else if (asset.getProduct().equalsIgnoreCase(CacheConstant.NETWORK_CABLE_CONNECTION)) {
			// distinct d.asset_num,b.quote_num
			list = jdbc.query(NCC_PANEL_OPEN_ORDER, args,
					new AssetVaildationDataMapper(asset.getProduct(), "OPENORDER"));
		}
		res.setList(list);
		return res;
	}

	@Override
	public AccountMoveAssetResponse validateIsAnyOpenQuoteExist(AccountMoveAssetRequest request) {
		AccountMoveAssetResponse res = new AccountMoveAssetResponse();
		Asset asset = request.getSrcAssets().get(0);
		Object args[] = new Object[] { asset.getAssetNum() };
		List<AssetVaildationData> list = null;
		if (asset.getProduct().equalsIgnoreCase(CacheConstant.CABINET)
				|| asset.getProduct().equalsIgnoreCase(CacheConstant.PATCH_PANEL)) {
			// sv.asset_num,O.ou_num
			list = jdbc.query(CABINET_PATCH_PANEL_OPEN_QUOTE, args,
					new AssetVaildationDataMapper(asset.getProduct(), "OPENQUOTE"));
		} else if (asset.getProduct().contains("Circuit") || asset.getProduct().equalsIgnoreCase("Port")) {
			// distinct b.order_num,d.asset_num
			list = jdbc.query(CIRCUIT_PORT_PANEL_OPEN_QUOTE, args,
					new AssetVaildationDataMapper(asset.getProduct(), "OPENQUOTE"));
		} else if (asset.getProduct().equalsIgnoreCase(CacheConstant.NETWORK_CABLE_CONNECTION)) {
			// distinct d.asset_num,b.quote_num
			list = jdbc.query(NCC_PANEL_OPEN_QUOTE, args,
					new AssetVaildationDataMapper(asset.getProduct(), "OPENQUOTE"));
		}
		res.setList(list);
		return res;
	}

	@Override
	public AccountMoveAssetResponse updateAssetMoveAttr(AccountMoveAssetRequest request) {
		AccountMoveAssetResponse response = new AccountMoveAssetResponse();
		int rowsUpdate;
		AccountMoveSearchFilter sourceCriteria = request.getSourceCriteria();
		AccountMoveSearchFilter targetCriteria = request.getTargetCriteria();
		Asset sourceAsset = request.getSrcAssets().get(0);
		Asset targetAsset = request.getTargetAsset().get(0);
		StringBuffer resmsg = new StringBuffer(sourceAsset.getProduct()).append(" ");

		Object args[] = new Object[] { targetAsset.getAccount(), targetAsset.getUcmID(),
				targetCriteria.getCageRootAssetId(), targetAsset.getParentAsset(), targetAsset.getxUniqueSpaceId(),
				targetAsset.getCabinetUSId(), targetAsset.getCabinetUSId(), sourceAsset.getAssetNum() };
		// EXECUTE ALL : UPDATE_ASSET_ATTR Param#[ Parent==>
		// owner_accnt_Id,x_attr_25=ucmId,x_root_asset_num, X_UNIQUE_SPACE_ID,
		// X_CAB_USID, X_CAB_ID & AssetNum]

		executeUpdate(UPDATE_ASSET_ATTR, args);

		if (sourceAsset.getProduct().equalsIgnoreCase(CacheConstant.CABINET)
				|| sourceAsset.getProduct().contains("Circuit")) {
			// EXECUTE ONLY CABINET & CIRCUIT : UPDATE_CABINET_CIRCUIT_ATTR Param#[ Target
			// Cage Asset , asset_num]
			args = new Object[] { targetAsset.getAssetNum(), targetAsset.getAssetNum(), sourceAsset.getAssetNum() };
			rowsUpdate = executeUpdate(UPDATE_CABINET_CIRCUIT_ATTR, args);
			resmsg.append("UPDATE_CABINET_CIRCUIT_ATTR").append("=").append(rowsUpdate).append(";");
		} else if (sourceAsset.getProduct().equalsIgnoreCase(CacheConstant.NETWORK_CABLE_CONNECTION)) {
			// EXECUTE ONLY IF NCC Param#[ Parent Asset X_PP_ID, asset_num]
			args = new Object[] { targetAsset.getPpId(), sourceAsset.getAssetNum() };
			rowsUpdate = executeUpdate(UPDATE_NCC_ASSET, args);
			resmsg.append("UPDATE_NCC_ASSET").append("=").append(rowsUpdate).append(";");
		}

		if (sourceAsset.getCageUSID().equalsIgnoreCase(targetAsset.getCageUSID()) == false) {
			// EXECUTE ALL: UPDATE_CAGE_UNIQUE_SPACE_ID Param#[Parent Asset
			// X_UNIQUE_SPACE_ID, asset_num]
			args = new Object[] { targetAsset.getxUniqueSpaceId(), sourceAsset.getAssetNum() };
			// rowsUpdate = executeUpdate(UPDATE_CAGE_UNIQUE_SPACE_ID,args);
			// resmsg.append("UPDATE_CAGE_UNIQUE_SPACE_ID").append("=").append(rowsUpdate).append(";");
			if (sourceAsset.getProduct().equalsIgnoreCase(CacheConstant.PATCH_PANEL)
					|| sourceAsset.getProduct().equalsIgnoreCase(CacheConstant.NETWORK_CABLE_CONNECTION)) {
				// EXECUTE: UPDATE_ATTR_EXCEPT_CABINET_AND_PORT Param#[Parent Asset X_CAB_USID,
				// Parent Asset X_CAB_ID, asset_num]
				args = new Object[] { targetAsset.getCageUSID(), targetAsset.getCabinetUSId(),
						sourceAsset.getAssetNum() };
				rowsUpdate = executeUpdate(UPDATE_ATTR_EXCEPT_CABINET_AND_PORT, args);
				resmsg.append("UPDATE_ATTR_EXCEPT_CABINET_AND_PORT").append("=").append(rowsUpdate).append(";");
			}
		}
		response.setMessage(resmsg.toString());
		return response;
	}

	private int executeUpdate(String query, Object[] args) {
		return jdbc.update(query, args);
	}

	@Override
	public void saveAccountMoveRequest(AccountMoveRequest request) {
		sessionFactory.getCurrentSession().saveOrUpdate(request);
	}

	@Override
	public AccountMoveRequest getAccountMoveRequest(String dfrId) {
		Session session = sessionFactory.getCurrentSession();
		AccountMoveRequest acMoveRequest = (AccountMoveRequest) session.createCriteria(AccountMoveRequest.class)
				.add(Restrictions.eq("dfrId", dfrId).ignoreCase()).uniqueResult();
		return acMoveRequest;
	}

	@Override
	public List<PortAsset> getListPortAssets(String assetId, boolean flag) {
		List<String> ids = Arrays.asList(assetId.split(","));
		SqlParameterSource namedParameters =  new MapSqlParameterSource("ids", ids);		 
		return (List<PortAsset>)namedJdbc.query(flag?MODIFIED_PORT_INFO:MODIFIED_PORT_INFO2, namedParameters, new PortAssetMapper());

	}

	@Override
	public List<String> postValidateForPatchPanel(AccountMoveAssetRequest request) {
		Object args[] = new Object[] { request.getSrcAssets().get(0).getRootAssetId() };
		return jdbc.query(PATCH_PANEL_POST_VALIDATION, args, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	}

	@Override
	public AccountMoveAssetResponse validateNCCMOVE(AccountMoveAssetRequest request) {
		// NCC_CHILD_VALIDATION

		AccountMoveAssetResponse res = new AccountMoveAssetResponse();
		Asset asset = request.getSrcAssets().get(0);
		Object args[] = new Object[] { asset.getAssetNum() };
		List<AssetVaildationData> list = null;
		
		list = jdbc.query(NCC_CHILD_VALIDATION, args,
					new AssetVaildationDataMapper(asset.getProduct(), "BILLING"));
		
		res.setList(list);
		return res;
	
	}
}
