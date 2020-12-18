package com.equinix.appops.dart.portal.mapper.ms.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.mservices.MsCiDaNewVal;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowConfigItemDa;

public class MsCiDaNewValMapper implements RowMapper<MsCiDaNewVal> {

	@Override
	public MsCiDaNewVal mapRow(ResultSet rs, int arg1) throws SQLException {
		MsCiDaNewVal msCiDaNewVal = new MsCiDaNewVal();
		msCiDaNewVal.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
		msCiDaNewVal.setDfrId(this.processRs(rs, "DFR_ID"));
		msCiDaNewVal.setHeader1(this.processRs(rs, "ROW_ID"));
		msCiDaNewVal.setHeader2(this.processRs(rs, "ASSET_NUM"));
		msCiDaNewVal.setHeader3(this.processRs(rs, "PRODUCT"));
		msCiDaNewVal.setHeader4(this.processRs(rs, "STATUS_CD"));
		msCiDaNewVal.setHeader5(this.processRs(rs, "ASSET_TAG"));
		msCiDaNewVal.setHeader6(this.processRs(rs, "COMPANY"));
		msCiDaNewVal.setHeader7(this.processRs(rs, "LOCATION"));
		msCiDaNewVal.setHeader8(this.processRs(rs, "SERIAL_NUMBER"));
		msCiDaNewVal.setHeader9(this.processRs(rs, "SYS_CLASS_NAME"));
		msCiDaNewVal.setHeader10(this.processRs(rs, "U_CABINET"));
		msCiDaNewVal.setHeader11(this.processRs(rs, "U_CUSTOMER_UCM_ID"));
		msCiDaNewVal.setHeader12(this.processRs(rs, "U_HOSTNAME"));
		msCiDaNewVal.setHeader13(this.processRs(rs, "U_LEGACY_ID"));
		msCiDaNewVal.setHeader14(this.processRs(rs, "U_LEGACY_INFO"));
		msCiDaNewVal.setHeader15(this.processRs(rs, "U_LEGACY_PRODUCT_DESCRIPTION"));
		msCiDaNewVal.setHeader16(this.processRs(rs, "U_LICENSING_TERM"));
		msCiDaNewVal.setHeader17(this.processRs(rs, "U_MANAGEMENT_TYPE"));
		msCiDaNewVal.setHeader18(this.processRs(rs, "U_STANDARD_PRODUCT"));
		msCiDaNewVal.setHeader19(this.processRs(rs, "U_SYSTEM_NAME"));
		msCiDaNewVal.setHeader20(this.processRs(rs, "U_TERM_BAND"));
		msCiDaNewVal.setHeader21(this.processRs(rs, "SYS_ID"));
		return msCiDaNewVal;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}
	
}