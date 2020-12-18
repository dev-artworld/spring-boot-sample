package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrMasterHomeVO;

public class DfrMasterHomeMapper implements RowMapper<DfrMasterHomeVO> {
	@Override
	public DfrMasterHomeVO mapRow(ResultSet rs, int arg1) throws SQLException {
		DfrMasterHomeVO dfrMasterVO = new DfrMasterHomeVO();
		dfrMasterVO.setDfrId(this.processRs(rs,"DFR_ID"));
		dfrMasterVO.setAssignedTeam(this.processRs(rs,"ASSIGNED_TEAM"));
		dfrMasterVO.setAssignedTo(this.processRs(rs,"ASSIGNED_TO"));
		dfrMasterVO.setAssignedDt(rs.getDate("ASSIGNED_DT"));
		dfrMasterVO.setCreatedBy(this.processRs(rs,"CREATED_BY"));
		dfrMasterVO.setCreatedDt(rs.getDate("CREATED_DT"));
	//	dfrMasterVO.setCreatedDt(this.processRs(rs.getDate("CREATED_DT")));
		dfrMasterVO.setCreatedTeam(this.processRs(rs,"CREATED_TEAM"));
		dfrMasterVO.setPriority(this.processRs(rs,"PRIORITY"));
		dfrMasterVO.setStatus(this.processRs(rs,"STATUS"));
		dfrMasterVO.setIsMsDfr(this.processRs(rs,"IS_MS_DFR"));
		dfrMasterVO.setIbx(this.processRs(rs,"IBX"));
		return dfrMasterVO;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName)	;
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}
}
