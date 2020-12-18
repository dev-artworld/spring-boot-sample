package com.equinix.appops.dart.portal.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.dao.XmlConfigDao;
import com.equinix.appops.dart.portal.entity.XmlConfig;

@Repository
public class XmlConfigDaoImpl implements XmlConfigDao {
	Logger logger = LoggerFactory.getLogger(AppOpsInitiateDFRDao.class); 
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public XmlConfig getXmlDataByXmlName(String xmlName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(XmlConfig.class);
		criteria.add(Restrictions.eq("xmlName",xmlName));
		List<XmlConfig> list = criteria.list();
		return (list==null && list.isEmpty())?null:list.get(0);
	}

	@Override
	public void saveUpdateXmlConfig(XmlConfig xmlConfig) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(xmlConfig);
	}

	@Override
	public List<XmlConfig> getXmlConfigsByXmlGroup(String xmlGroup) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(XmlConfig.class);
		criteria.add(Restrictions.eq("xmlGroup",xmlGroup));
		return criteria.list();
	}

	

	}
