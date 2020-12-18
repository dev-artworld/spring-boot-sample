package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.dto.pro.AccountVo;

public class AccountMapper implements RowMapper<AccountVo> {

	@Override
	public AccountVo mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountVo accountVo = new AccountVo();
		accountVo.setAccountNum(rs.getString("OU_NUM"));
		accountVo.setAccountName(rs.getString("ACCOUNT_NAME"));
		return accountVo;
	}

}
