package com.equinix.appops.dart.portal.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.dao.EmailSenderDao;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.EmailAudit;

@Repository
@Transactional
public class EmailSenderDaoImpl implements EmailSenderDao {
	
	Logger logger = LoggerFactory.getLogger(EmailSenderDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer saveAuditEmail(EmailAudit emailAudit) {
		Integer id = emailAudit.getRowId();
		if(emailAudit.getRowId() == null){
			id = getMaxIdEmailAudit();
			emailAudit.setRowId(id);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(emailAudit);
		System.out.println("Email Saved");
		return id;
	}

	@Override
	public List<EmailAudit> getAllAuditEmail() {
		return sessionFactory.getCurrentSession().createQuery("from EmailAudit e").list();
	}
	
	private int getMaxIdEmailAudit(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("SELECT MAX(ROW_ID) from EQX_DART.EMAIL_AUDIT");
		Object maxId = query.list().get(0);
		int newMaxId = 0;
		if(maxId == null){
			newMaxId = 0;
		}else{
			newMaxId = ((BigDecimal) maxId).intValue()+1;
		}
		return newMaxId;
	}

	@Override
	public List<EmailAudit> getRecentFailureEmail(Long responseTime, Long retryCount) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(EmailAudit.class)		
		.add(Restrictions.or(
					Restrictions.eq("emailSendStatus",LogicConstants.EMAIL_MESSAGE_UNDELIVERED.toLowerCase()).ignoreCase(),
					Restrictions.eq("emailSendStatus",LogicConstants.EMAIL_MESSAGE_NOT_PUSHED.toLowerCase()).ignoreCase()))
		.add(Restrictions.lt("retryCount",retryCount))
		//.add(Restrictions.ge("responseTime", new DateTime(new Date()).minusMinutes(responseTime.intValue()).toDate() ))
		.addOrder(Order.desc("rowId"));
		return criteria.list();
	}

	@Override
	public EmailAudit getEmailAudit(Integer rowId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(EmailAudit.class)
				.add(Restrictions.eq("rowId", rowId));
				
		return (EmailAudit)criteria.uniqueResult();
	}

}
