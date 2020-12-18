package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.mapper.dto.pro.AssetCount;

public class AssetCountMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		AssetCount assetCount = new AssetCount();
		assetCount.setCreatedBy(this.processRs(rs,"CREATED_BY"));
		assetCount.setSystemName(this.processRs(rs, "SYSTEM_NAME"));
		assetCount.setNoOfAssets(this.processRs(rs, "No_OF_ASSETS"));
		assetCount.setNewAssets(this.processRs(rs, "NEW_ASSETS"));
		assetCount.setUpdates(this.processRs(rs, "UPDATES"));
		assetCount.setTeminators(this.processRs(rs, "TERMINATORS"));
		assetCount.setNewErrors(this.processRs(rs, "NEW_ERRORS"));
		assetCount.setFixedErrors(this.processRs(rs, "FIXED_ERRORS"));
		assetCount.setRemainingErrors(this.processRs(rs, "REMAINING_ERRORS"));
		assetCount.setComment(this.processRs(rs, "NOTES"));
		assetCount.setRegion(this.processRs(rs, "REGION"));
		assetCount.setPriority(this.processRs(rs, "PRIORITY"));
		assetCount.setClxUpdate(this.processRs(rs, "CLX_UPD"));
		assetCount.setOverride_flag(this.processRs(rs, "OVERRIDE_FLG"));
		assetCount.setCaplogixFlag(this.processRs(rs, "CLX_OVR"));
		assetCount.setSiebelFlag(this.processRs(rs, "SBL_OVR"));
		assetCount.setValidState(this.processRs(rs,"VALID_STATUS"));
		return assetCount;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName)	;
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}

