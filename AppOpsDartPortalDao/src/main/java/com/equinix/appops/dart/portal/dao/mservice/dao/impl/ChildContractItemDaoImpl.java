package com.equinix.appops.dart.portal.dao.mservice.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.mservice.dao.ChildContractItemDao;
import com.equinix.appops.dart.portal.entity.mservices.ChildContractItem;


@Repository
@Transactional
public class ChildContractItemDaoImpl implements ChildContractItemDao {

	@Autowired
	SessionFactory sessionFactory ;

    @Override
    public ChildContractItem saveContractItem(ChildContractItem childContractItem) {
    	sessionFactory.getCurrentSession().saveOrUpdate(childContractItem);
        return childContractItem;
    }
    
    @Override
    public ChildContractItem getChildContractItem(String sysId, String uContractItem){
    	Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ChildContractItem.class);
		criteria.add(Restrictions.eq("childContractItemId.sysId", sysId));
		criteria.add(Restrictions.eq("childContractItemId.contractItem", uContractItem));
		return (ChildContractItem)criteria.uniqueResult();
    }
}
