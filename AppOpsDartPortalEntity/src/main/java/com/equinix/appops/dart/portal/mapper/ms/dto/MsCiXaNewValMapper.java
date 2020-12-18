package com.equinix.appops.dart.portal.mapper.ms.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.mservices.MsCiXaNewVal;

public class MsCiXaNewValMapper implements RowMapper<MsCiXaNewVal> {

		@Override
		public MsCiXaNewVal mapRow(ResultSet rs, int arg1) throws SQLException {
			MsCiXaNewVal msCiXaNewVal = new MsCiXaNewVal();
			msCiXaNewVal.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
			msCiXaNewVal.setDfrId(this.processRs(rs, "DFR_ID"));
			msCiXaNewVal.setAssetId(this.processRs(rs, "ASSET_ID"));
			msCiXaNewVal.setAssetNum(this.processRs(rs, "ASSET_NUM"));
			msCiXaNewVal.setAttrName(this.processRs(rs, "ATTR_NAME"));
			msCiXaNewVal.setAttrValue(this.processRs(rs, "ATTR_VALUE"));
			msCiXaNewVal.setProduct(this.processRs(rs, "PRODUCT"));
			msCiXaNewVal.setStatusCd(this.processRs(rs, "STATUS_CD"));
			msCiXaNewVal.setDisplayFlag(this.processRs(rs, "DISPLAY_FLAG"));
			return msCiXaNewVal;
		}
		
		private String processRs(ResultSet rs , String columnName){
			try{
			   return rs.getString(columnName);
			}catch(Exception e){
			   return CacheConstant.BLANK;	
			}
		}
		
	}