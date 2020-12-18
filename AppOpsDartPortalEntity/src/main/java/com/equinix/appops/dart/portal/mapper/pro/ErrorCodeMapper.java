package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCodeVO;

public class ErrorCodeMapper implements RowMapper<ErrorCodeVO> {
	@Override
	public ErrorCodeVO mapRow(ResultSet rs, int arg1) throws SQLException {
		ErrorCodeVO errorCodeVO = new ErrorCodeVO();
		errorCodeVO.setErrorCode(rs.getString("ERROR_CODE"));
		errorCodeVO.setErrorName(rs.getString("ERROR_NAME"));
		return errorCodeVO;
	}

}
