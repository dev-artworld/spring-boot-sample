package com.equinix.appops.dart.portal.mapper.accountmove;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.accountmove.dto.RootAsset;

public class AccountAssetMapper implements RowMapper<RootAsset> {

	@Override
	public RootAsset mapRow(ResultSet rs, int rowNum) throws SQLException {
		RootAsset ra = new RootAsset();
		ra.setAssetNum(rs.getString("asset_num"));
		ra.setRootAssetId(rs.getString("row_id"));
		return ra;
	}

}
