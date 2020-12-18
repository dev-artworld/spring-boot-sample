package com.equinix.appops.dart.portal.mapper.ms.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowConfigItemXa;

public class SnapshotSnowConfigXaMapper implements RowMapper<SnapshotSnowConfigItemXa> {

	@Override
	public SnapshotSnowConfigItemXa mapRow(ResultSet rs, int arg1) throws SQLException {
		SnapshotSnowConfigItemXa snapshotSnowConfigItemXa = new SnapshotSnowConfigItemXa();
		snapshotSnowConfigItemXa.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
		snapshotSnowConfigItemXa.setDfrId(this.processRs(rs, "DFR_ID"));
		snapshotSnowConfigItemXa.setRowId(this.processRs(rs, "ROW_ID"));
		snapshotSnowConfigItemXa.setAssetId(this.processRs(rs, "ASSET_ID"));
		snapshotSnowConfigItemXa.setAssetNum(this.processRs(rs, "ASSET_NUM"));
		snapshotSnowConfigItemXa.setAttrName(this.processRs(rs, "ATTR_NAME"));
		snapshotSnowConfigItemXa.setAttrValue(this.processRs(rs, "ATTR_VALUE"));
		snapshotSnowConfigItemXa.setProduct(this.processRs(rs, "PRODUCT"));
		snapshotSnowConfigItemXa.setStatusCd(this.processRs(rs, "STATUS_CD"));
		return snapshotSnowConfigItemXa;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}
	
}