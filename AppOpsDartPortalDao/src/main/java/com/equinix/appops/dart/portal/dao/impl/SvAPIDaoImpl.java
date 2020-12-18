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

import com.equinix.appops.dart.portal.dao.SvAPIDao;
import com.equinix.appops.dart.portal.entity.Recon2Sync;
import com.equinix.appops.dart.portal.entity.SvApi;
import com.equinix.appops.dart.portal.entity.SvSyncV;

@Repository
public class SvAPIDaoImpl implements SvAPIDao {
	Logger logger = LoggerFactory.getLogger(SvAPIDao.class); 
	
	@Autowired
    private SessionFactory sessionFactory;
	
	/*@Autowired
	@Qualifier("svSessionFactory")
	 private SessionFactory svSessionFactory;*/

	@Override
	public List<SvApi> getPoeItems(String dfrId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SvApi.class);
		criteria.add(Restrictions.eq("dfrId",dfrId));
		criteria.add(Restrictions.isNotNull("rootid"));
		return criteria.list();
	}

	@Override
	public List<SvApi> getPofItems(String rootId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SvApi.class);
		criteria.add(Restrictions.eq("id",rootId));
		return criteria.list();
	}

	/*@Override
	public List<SvSyncV> getSvSyncV(String dfrId) {
		Session session = svSessionFactory.getCurrentSession();	
		Criteria criteria = session.createCriteria(SvSyncV.class);
		return criteria.list();
	}*/

	@Override
	public List<SvSyncV> getSvSyncVDart(String dfrId) {
		Session session = sessionFactory.getCurrentSession();	
		Criteria criteria = session.createCriteria(SvSyncV.class);
		criteria.add(Restrictions.eq("incidentNo",dfrId));
		return criteria.list();
	}

	@Override
	public void saveRecon2Sync(Recon2Sync recon2Sync) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(recon2Sync);
		
	}

	

	}
