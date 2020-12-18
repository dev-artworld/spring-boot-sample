package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.DfrNotes;

public class DfrNotesMapper implements RowMapper<DfrNotes>{

	@Override
	public DfrNotes mapRow(ResultSet rs, int rowNum) throws SQLException {
		DfrNotes dfrNotes = new DfrNotes();
		dfrNotes.setRowId(Integer.parseInt(processRs(rs, "ROW_ID")));
		dfrNotes.setDfrId(processRs(rs, "DFR_ID"));
		dfrNotes.setFirstName(processRs(rs, "FIRST_NAME"));
		dfrNotes.setLastName(processRs(rs, "LAST_NAME"));
		dfrNotes.setNotes(processRs(rs, "NOTES"));
		dfrNotes.setMechanism(processRs(rs, "MECHANISM"));
		dfrNotes.setTeam(processRs(rs, "TEAM"));
		dfrNotes.setUserName(processRs(rs, "USER_NAME"));
		dfrNotes.setCreatedDt(rs.getDate("CREATED_DT"));
		return dfrNotes;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}
