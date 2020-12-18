package com.equinix.appops.dart.portal.dao.mservice.dao.impl;


import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.mservice.dao.ConfigItemDao;
import com.equinix.appops.dart.portal.entity.mservices.ConfigChildItem;
import com.equinix.appops.dart.portal.entity.mservices.ConfigItem;

@Repository
@Transactional
public class ConfigItemDaoImpl implements ConfigItemDao {

	@Autowired
	SessionFactory sessionFactory ;

    @Override
    public ConfigItem saveConfigItem(ConfigItem configItem, List<ConfigChildItem> configChildItemList) {
    	Session session = sessionFactory.getCurrentSession();
        	     session.saveOrUpdate(configItem);
        	     if (CollectionUtils.isNotEmpty(configChildItemList)) {
        	    	 for(ConfigChildItem configChildItem: configChildItemList) {
        	    		 session.saveOrUpdate(configChildItem);
        	            }
        			}
        return configItem;
    }
    
    @Override
    public ConfigItem getConfigItem(String sysId){
    	Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ConfigItem.class);
		criteria.add(Restrictions.eq("sysId", sysId));
		return (ConfigItem)criteria.uniqueResult();
    }
    
    @Override
    public ConfigChildItem getConfigChildItem(String fieldName, String sysId){
    	Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ConfigChildItem.class);
		criteria.add(Restrictions.eq("configChildItemId.sysId", sysId));
		criteria.add(Restrictions.eq("configChildItemId.fieldName", fieldName));
		return (ConfigChildItem)criteria.uniqueResult();
    }
}
