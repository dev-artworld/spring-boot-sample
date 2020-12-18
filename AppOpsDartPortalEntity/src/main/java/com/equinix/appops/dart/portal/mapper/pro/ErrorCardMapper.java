package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.dto.pro.ErrorCardVO;

public class ErrorCardMapper implements RowMapper<ErrorCardVO> {

	@Override
	public ErrorCardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ErrorCardVO errorCardVO = new ErrorCardVO();
		errorCardVO.setTotalErrorCount(rs.getString("TOTAL_ERR_CNT"));
		errorCardVO.setFixedErrorCount(rs.getString("TOTAL_FIXED_CNT"));
		return errorCardVO;
	}

}
