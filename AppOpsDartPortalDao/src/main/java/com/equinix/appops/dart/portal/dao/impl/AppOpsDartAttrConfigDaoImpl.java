package com.equinix.appops.dart.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.constant.ProNativeQueries;
import com.equinix.appops.dart.portal.dao.AppOppsDartAttrConfigDao;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;
import com.equinix.appops.dart.portal.model.AttrConfigVO;
import com.equinix.appops.dart.portal.model.AttrConfigVOMapper;

@Repository
@Transactional
public class AppOpsDartAttrConfigDaoImpl implements AppOppsDartAttrConfigDao {
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartAttrConfigDaoImpl.class);
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
    JdbcTemplate jdbc;	

	@Override
//	@Cacheable(value="getAttributeConfigByProductAndAttr",key="#product.concat('-').concat(#name)")
	public AttributeConfig getAttrConfigVlaueByKey(String product, String name) {
		Session session = sessionFactory.getCurrentSession();
		AttributeConfig attributeConfig  =(AttributeConfig) session.createCriteria(AttributeConfig.class)
				.add(Restrictions.eq("product", product).ignoreCase())
				.add(Restrictions.eq("attrName", name).ignoreCase())
				.uniqueResult();
		return attributeConfig;
	}
	
	@Override
	@CacheEvict(value="getAttributeConfigByProductAndAttr",allEntries = true)
	public String resetAttributeConfigByProductAndAttr() {
		return "AttributeConfigByProductAndAttr refreshed...";
	}

	@Override
	public List<AttributeConfig> getAttrsWithDpntAttr() {
		Session session =   sessionFactory.getCurrentSession();
		List<AttributeConfig> attributeConfigs=new ArrayList<>();
		Criteria criteria=session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  
				.add(Restrictions.eq("dependentAttribute", 'Y').ignoreCase())
				.add(Restrictions.eq("editable", 'Y').ignoreCase());
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			AttributeConfig attributeConfig = (AttributeConfig) scrollableResults.get()[0];
			attributeConfigs.add(attributeConfig);
			//session.evict(attributeConfig);
		}
		return attributeConfigs;
	}

	@Override
	//@Cacheable(value="getDependentAttrUpdateByAttrFamily",key="#attributeFamily")
	public List<DependentAttrUpdate> getDependentAttrUpdate(String attributeFamily) {
		Session session =   sessionFactory.getCurrentSession();
		List<DependentAttrUpdate> dependentAttrUpdates=new ArrayList<>();
		Criteria criteria=session.createCriteria(DependentAttrUpdate.class)
				.add(Restrictions.eq("attributeFamly", attributeFamily));
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			DependentAttrUpdate dependentAttrUpdate = (DependentAttrUpdate) scrollableResults.get()[0];
			dependentAttrUpdates.add(dependentAttrUpdate);
			//session.evict(dependentAttrUpdate);
		}
		return dependentAttrUpdates;
	}
	
	@Override
	//@Cacheable(value="getDependentAttrUpdateByAttrFamily",key="#attributeFamily")
	public List<DependentAttrUpdate> getAutoDependentAttrUpdate(String attributeFamily) {
		Session session =   sessionFactory.getCurrentSession();
		List<DependentAttrUpdate> dependentAttrUpdates=new ArrayList<>();
		Criteria criteria=session.createCriteria(DependentAttrUpdate.class)
				.add(Restrictions.eq("attributeFamly", attributeFamily));
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			DependentAttrUpdate dependentAttrUpdate = (DependentAttrUpdate) scrollableResults.get()[0];
			if (dependentAttrUpdate.getExecutionOrder() != null && dependentAttrUpdate.getSql2() != null) {
				dependentAttrUpdates.add(dependentAttrUpdate);
			}
			// session.evict(dependentAttrUpdate);
		}
		return dependentAttrUpdates;
	}
	
	@Override
	@CacheEvict(value="getDependentAttrUpdateByAttrFamily",allEntries = true)
	public String resetDependentAttrUpdateByAttrFamily() {
		return "DependentAttrUpdateByAttrFamily refreshed...";
	}

	@Override
	public AttributeConfig getAttrConfigVlaueById(String rowId) {
		Session session = sessionFactory.getCurrentSession();
		AttributeConfig attributeConfig  =(AttributeConfig) session.createCriteria(AttributeConfig.class)
				.add(Restrictions.eq("rowId", rowId))
				.uniqueResult();
		return attributeConfig;
	}


	@Override
	public void runSqlQuery(String sql) {
		jdbc.execute(sql);		
	}

	@Override
	public AttributeConfig getAttrConfigVlaueByHeaderPos(String product, String headerPosition) {
		Session session = sessionFactory.getCurrentSession();
		AttributeConfig attributeConfig  =(AttributeConfig) session.createCriteria(AttributeConfig.class)
				.add(Restrictions.eq("product", product).ignoreCase())
				.add(Restrictions.eq("headerPosition", headerPosition).ignoreCase())
				.uniqueResult();
		return attributeConfig;
	}

	@Override
	public List<AttributeConfig> getAttrConfigVlaueForCaplogix(String product) {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  
				.add(Restrictions.eq("product", product).ignoreCase())
				.add(Restrictions.isNotNull("clx"))
				.add(Restrictions.isNotNull("clxTag"))
				.list();
	}

	@Override
	public List<AttributeConfig> getAttrConfigVlaueByProduct(String product) {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  
				.add(Restrictions.eq("product", product).ignoreCase())
				.list();
	}

	@Override
	public List<AttributeConfig> getAttrConfigVlaueByProduct(String... products) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  ;
		criteria.add(Restrictions.in("product", products))
		.add(Restrictions.isNotNull("clxTag"));
		return criteria	.list();
	}

	@Override
	public List<AttributeConfig> getOnlyAttrConfigVlaueSiebel() {
		Session session =   sessionFactory.getCurrentSession();
		List<AttributeConfig> attributeConfigs=new ArrayList<>();
		Criteria criteria=session.createCriteria(AttributeConfig.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)  
				
				.add(Restrictions.not(Restrictions.like("product", "header",MatchMode.ANYWHERE).ignoreCase()));
		ScrollableResults scrollableResults = criteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		while (scrollableResults.next()) {
			AttributeConfig attributeConfig = (AttributeConfig) scrollableResults.get()[0];
			attributeConfigs.add(attributeConfig);
			//session.evict(attributeConfig);
		}
		return attributeConfigs;
	}
	
	@Override
	public List<AttrConfigVO> getAttrConfigByProductAndAttr() {
		String sqlStr = "SELECT PRODUCT,ATTR_NAME FROM EQX_DART.ATTRIBUTE_CONFIG WHERE "
				+ "DEPENDENT_ATTRIBUTE='Y' AND EDITABLE='Y' GROUP BY PRODUCT,ATTR_NAME";
		return jdbc.query(sqlStr, new AttrConfigVOMapper());
	}
	
	@Override
	public void runBatchUpdate(String[] sqlArray) throws Exception{
		jdbc.batchUpdate(sqlArray);		
	}
	
	@Override
	public List<String> getAllAttrFamilies() {
		
		return jdbc.query(ProNativeQueries.ATTR_FAMILIES_LIST, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			
				return rs.getString("ATTRIBUTE_FMLY");
			}
		});
	}
}
