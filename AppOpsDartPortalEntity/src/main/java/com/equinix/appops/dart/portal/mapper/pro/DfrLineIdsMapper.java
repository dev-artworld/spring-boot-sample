package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.dto.pro.DfrLineIdsVo;

public class DfrLineIdsMapper implements RowMapper<DfrLineIdsVo>{

	@Override
	public DfrLineIdsVo mapRow(ResultSet rs, int rowNum) throws SQLException {
		DfrLineIdsVo dfrLineIdsVo = new DfrLineIdsVo();
		dfrLineIdsVo.setName(rs.getString("NAME"));
		dfrLineIdsVo.setDfrLineId(rs.getString("ASSET_NUM") + "##"+ rs.getString("DFR_LINE_ID"));
		return dfrLineIdsVo;
	}

}
