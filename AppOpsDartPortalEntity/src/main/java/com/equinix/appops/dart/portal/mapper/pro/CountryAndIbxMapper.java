package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.dto.pro.CountryAndIbxVo;

public class CountryAndIbxMapper implements RowMapper<CountryAndIbxVo> {

	@Override
	public CountryAndIbxVo mapRow(ResultSet rs, int arg1) throws SQLException {
		CountryAndIbxVo countryAndIbxVo = new CountryAndIbxVo();
		countryAndIbxVo.setCountry(rs.getString("COUNTRY"));
		countryAndIbxVo.setIbx(rs.getString("IBX"));
		return countryAndIbxVo;
	}

}
