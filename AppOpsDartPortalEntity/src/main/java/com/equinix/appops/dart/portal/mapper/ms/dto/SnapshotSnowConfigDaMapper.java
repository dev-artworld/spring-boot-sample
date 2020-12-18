package com.equinix.appops.dart.portal.mapper.ms.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowConfigItemDa;

public class SnapshotSnowConfigDaMapper implements RowMapper<SnapshotSnowConfigItemDa> {

	@Override
	public SnapshotSnowConfigItemDa mapRow(ResultSet rs, int arg1) throws SQLException {
		SnapshotSnowConfigItemDa snapshotSnowConfigItemDa = new SnapshotSnowConfigItemDa();
		snapshotSnowConfigItemDa.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
		snapshotSnowConfigItemDa.setDfrId(this.processRs(rs, "DFR_ID"));
		snapshotSnowConfigItemDa.setHeader1(this.processRs(rs, "ROW_ID"));
		snapshotSnowConfigItemDa.setHeader2(this.processRs(rs, "ASSET_NUM"));
		snapshotSnowConfigItemDa.setHeader3(this.processRs(rs, "PRODUCT"));
		snapshotSnowConfigItemDa.setHeader4(this.processRs(rs, "STATUS_CD"));
		snapshotSnowConfigItemDa.setHeader5(this.processRs(rs, "ASSET_TAG"));
		snapshotSnowConfigItemDa.setHeader6(this.processRs(rs, "COMPANY"));
		snapshotSnowConfigItemDa.setHeader7(this.processRs(rs, "LOCATION"));
		snapshotSnowConfigItemDa.setHeader8(this.processRs(rs, "SERIAL_NUMBER"));
		snapshotSnowConfigItemDa.setHeader9(this.processRs(rs, "SYS_CLASS_NAME"));
		snapshotSnowConfigItemDa.setHeader10(this.processRs(rs, "U_CABINET"));
		snapshotSnowConfigItemDa.setHeader11(this.processRs(rs, "U_CUSTOMER_UCM_ID"));
		snapshotSnowConfigItemDa.setHeader12(this.processRs(rs, "U_HOSTNAME"));
		snapshotSnowConfigItemDa.setHeader13(this.processRs(rs, "U_LEGACY_ID"));
		snapshotSnowConfigItemDa.setHeader14(this.processRs(rs, "U_LEGACY_INFO"));
		snapshotSnowConfigItemDa.setHeader15(this.processRs(rs, "U_LEGACY_PRODUCT_DESCRIPTION"));
		snapshotSnowConfigItemDa.setHeader16(this.processRs(rs, "U_LICENSING_TERM"));
		snapshotSnowConfigItemDa.setHeader17(this.processRs(rs, "U_MANAGEMENT_TYPE"));
		snapshotSnowConfigItemDa.setHeader18(this.processRs(rs, "U_STANDARD_PRODUCT"));
		snapshotSnowConfigItemDa.setHeader19(this.processRs(rs, "U_SYSTEM_NAME"));
		snapshotSnowConfigItemDa.setHeader20(this.processRs(rs, "U_TERM_BAND"));
		snapshotSnowConfigItemDa.setHeader21(this.processRs(rs, "SYS_ID"));
		return snapshotSnowConfigItemDa;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}
	
}