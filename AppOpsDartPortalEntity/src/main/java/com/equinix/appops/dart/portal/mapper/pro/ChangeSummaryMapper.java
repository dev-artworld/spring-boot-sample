package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.ChangeSummary;

public class ChangeSummaryMapper implements RowMapper<ChangeSummary>{

	@Override
	public ChangeSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChangeSummary changeSummary = new ChangeSummary();
		changeSummary.setAssetNum(processRs(rs,"ASSET_NUM"));
		changeSummary.setAttrName(processRs(rs, "ATTR_NAME"));
		changeSummary.setAttrValue(processRs(rs, "ATTR_VALUE"));
		changeSummary.setOldValue(processRs(rs, "OLD_VALUE"));
		changeSummary.setCreatedDate(rs.getDate("CREATE_DATE"));
		changeSummary.setUserId(processRs(rs, "USER_ID"));
		changeSummary.setDfrId(processRs(rs, "DFR_ID"));
		changeSummary.setDfrLineId(processRs(rs, "DFR_LINE_ID"));
		changeSummary.setProductName(processRs(rs, "PRODUCT_NAME"));
		
		return changeSummary;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}
