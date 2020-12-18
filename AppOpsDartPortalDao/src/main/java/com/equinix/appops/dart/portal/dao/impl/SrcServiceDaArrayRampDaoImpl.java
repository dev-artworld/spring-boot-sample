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

import com.equinix.appops.dart.portal.dao.SrcServiceDaArrayRampDao;
import com.equinix.appops.dart.portal.entity.SrcServiceDaArrayRamp;

@Repository
public class SrcServiceDaArrayRampDaoImpl implements SrcServiceDaArrayRampDao {
	Logger logger = LoggerFactory.getLogger(SrcServiceDaArrayRampDao.class); 

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SrcServiceDaArrayRamp> getSrcServiceDaArrayRamp(Long serviceId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcServiceDaArrayRamp.class);
		criteria.add(Restrictions.eq("serviceId",serviceId));
		return criteria.list();
	}
}
