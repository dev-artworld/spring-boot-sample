package com.equinix.appops.dart.portal.dao.mservice.dao.impl;




import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.mservice.dao.ContractItemDao;
import com.equinix.appops.dart.portal.entity.mservices.ContractItem;

@Repository
@Transactional
public class ContractItemDaoImpl implements  ContractItemDao {


	@Autowired
	SessionFactory sessionFactory ;


    @Override
    public ContractItem saveContractItem(ContractItem contractItem) {
    	sessionFactory.getCurrentSession().saveOrUpdate(contractItem);
        return contractItem;
    }
    
    @Override
    public ContractItem getContractItem(String sysId) {
    	Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ContractItem.class);
		criteria.add(Restrictions.eq("sys_id", sysId));
		return (ContractItem)criteria.uniqueResult();
    }
}
