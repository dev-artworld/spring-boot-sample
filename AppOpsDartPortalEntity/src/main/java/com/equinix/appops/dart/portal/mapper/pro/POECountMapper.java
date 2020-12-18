package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.mapper.dto.pro.POECountVO;

public class POECountMapper implements RowMapper<POECountVO>{

	@Override
	public POECountVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		POECountVO poeCountVO = new POECountVO();
		poeCountVO.setName(this.processRs(rs, "NAME"));
		poeCountVO.setCount(this.processRs(rs, "COUNT"));
		return poeCountVO;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName)	;
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}
