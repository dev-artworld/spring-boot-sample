package com.equinix.appops.dart.portal.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ChangeSummaryDTOMapper implements RowMapper<ChangeSummaryDTO>{

	@Override
	public ChangeSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChangeSummaryDTO dto = new ChangeSummaryDTO();
		dto.setDfrId(rs.getString("DFR_ID"));
		dto.setProductName(rs.getString("NAME"));
		dto.setAssetNum(rs.getString("ASSET_NUM"));
		return dto;
	}

}
