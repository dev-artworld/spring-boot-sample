package com.equinix.appops.dart.portal.mapper.accountmove;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.entity.PortAsset;

public class PortAssetMapper implements RowMapper<PortAsset> {

	@Override
	public PortAsset mapRow(ResultSet rs, int rowNum) throws SQLException {
		PortAsset pa = new PortAsset();
		pa.setAssetId(rs.getString("asset_id"));
		pa.setPortId(rs.getString("port_id"));
		return pa;
	}

}
