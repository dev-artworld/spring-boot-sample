package com.equinix.appops.dart.portal.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttrConfigVOMapper implements RowMapper<AttrConfigVO> {
	
	public AttrConfigVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttrConfigVO attrConfig = new AttrConfigVO();
		attrConfig.setProductName(rs.getString("PRODUCT"));
		attrConfig.setAttrName(rs.getString("ATTR_NAME"));
		return attrConfig;
	}
}
