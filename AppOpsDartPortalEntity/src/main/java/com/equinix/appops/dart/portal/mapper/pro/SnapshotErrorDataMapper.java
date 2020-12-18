package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.model.errorsection.SnapshotErrorData;

public class SnapshotErrorDataMapper implements RowMapper<SnapshotErrorData> {

	@Override
	public SnapshotErrorData mapRow(ResultSet rs, int rowNum) throws SQLException {
		SnapshotErrorData snapshotErrorData = new SnapshotErrorData();
		snapshotErrorData.setTbl(this.processRs(rs, "TBL"));
		snapshotErrorData.setRowId(this.processRs(rs,"ROW_ID"));
		snapshotErrorData.setAssetNum(this.processRs(rs,"ASSET_NUM"));
		snapshotErrorData.setErrorCode(this.processRs(rs,"ERROR_CODE"));
		snapshotErrorData.setErrorName(this.processRs(rs,"ERROR_NAME"));
		snapshotErrorData.setValidationClass(this.processRs(rs, "VALIDATION_CLASS"));
		snapshotErrorData.setOwnerOfFixing(this.processRs(rs,"OWNER_OF_FIXING"));
		snapshotErrorData.setStatusCd(this.processRs(rs, "STATUS_CD"));
		snapshotErrorData.setValidStat(this.processRs(rs,"VALID_STAT"));
		snapshotErrorData.setAlertFlag(this.processRs(rs,"ALERT_FLAG"));
		
		return snapshotErrorData;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}
