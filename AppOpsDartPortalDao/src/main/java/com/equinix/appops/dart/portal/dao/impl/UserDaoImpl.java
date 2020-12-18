package com.equinix.appops.dart.portal.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.dao.UserDao;
import com.equinix.appops.dart.portal.entity.UserInfo;

@Repository
public class UserDaoImpl implements UserDao {
	Logger logger = LoggerFactory.getLogger(UserDao.class); 
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public UserInfo getUser(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserInfo.class);
		criteria.add(Restrictions.eq("userId",userId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (UserInfo) criteria.uniqueResult();
	}

	

	}
