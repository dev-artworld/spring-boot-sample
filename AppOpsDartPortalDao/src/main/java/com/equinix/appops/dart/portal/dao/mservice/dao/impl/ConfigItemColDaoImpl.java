package com.equinix.appops.dart.portal.dao.mservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.constant.MSNativeQueries;
import com.equinix.appops.dart.portal.dao.mservice.dao.ConfigItemColDao;

@Repository
@Transactional
public class ConfigItemColDaoImpl implements ConfigItemColDao {

	
	@Autowired
    JdbcTemplate jdbc;
	
	@Autowired
	SessionFactory sessionFactory ;


	@Override
    public List<String> getParentCols() {
	//	Object args[] = new Object[] { request.getSrcAssets().get(0).getRootAssetId() };
	//	return jdbc.query(MSNativeQueries.PARENT_CONFIG_COL,  new RowMapper<String>());
		
		return jdbc.query(MSNativeQueries.PARENT_CONFIG_COL, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	       /* Session session = sessionFactory.getCurrentSession().getSession();
	        List<String> pcol = new ArrayList<String>();
            SQLQuery list =  session.createSQLQuery(MSNativeQueries.PARENT_CONFIG_COL);
            pcol=list.list();
            return pcol;*/
   }
   @Override
   public List<String> getContractCols() {
	   return jdbc.query(MSNativeQueries.PAREN_CONTRACT_COL, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
   }

   @Override
   public List<String> getChildContractCols() {
	   return jdbc.query(MSNativeQueries.CHILD_CONTRACTT_COL, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
   }


}
