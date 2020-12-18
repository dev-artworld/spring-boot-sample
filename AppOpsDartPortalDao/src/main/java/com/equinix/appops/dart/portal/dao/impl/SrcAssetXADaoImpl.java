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

import com.equinix.appops.dart.portal.dao.SrcAssetXADao;
import com.equinix.appops.dart.portal.entity.SrcSAssetXa;

@Repository
public class SrcAssetXADaoImpl implements SrcAssetXADao {
	Logger logger = LoggerFactory.getLogger(SrcAssetXADao.class); 
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<SrcSAssetXa> getSrcAssetXA(String assetId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SrcSAssetXa.class);
		criteria.add(Restrictions.eq("assetId",assetId));
		return criteria.list();
	
	}

	

	}
