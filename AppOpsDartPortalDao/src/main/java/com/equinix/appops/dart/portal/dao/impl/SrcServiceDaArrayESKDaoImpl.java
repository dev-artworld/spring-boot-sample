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

import com.equinix.appops.dart.portal.dao.SrcServiceDaArrayESKDao;
import com.equinix.appops.dart.portal.entity.SrcServiceDaArrayEsk;

@Repository
public class SrcServiceDaArrayESKDaoImpl implements SrcServiceDaArrayESKDao {
	Logger logger = LoggerFactory.getLogger(SrcServiceDaArrayESKDao.class); 

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SrcServiceDaArrayEsk> getSrcServiceDaArrayESK(Long serviceId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcServiceDaArrayEsk.class);
		criteria.add(Restrictions.eq("serviceId",serviceId));
		return criteria.list();
	}
}
