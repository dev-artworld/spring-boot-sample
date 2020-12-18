package com.equinix.appops.dart.portal.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.UserPreferences;
import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.dao.AppOpsDartCommonDAO;

@Repository
@Transactional
public class AppOpsDartCommonDAOImpl implements AppOpsDartCommonDAO {
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartCommonDAOImpl.class);
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void persistUserPreferences(List<UserPreferences> userPrefList, String userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria userPrefCriteria = session.createCriteria(UserPreferences.class);
		userPrefCriteria.add(Restrictions.eq("userId", userId));
		List<UserPreferences> oldPrefList = (List<UserPreferences>) userPrefCriteria.list();
		if (CollectionUtils.isNotEmpty(oldPrefList)) {
			for (UserPreferences userPref : oldPrefList) {
				session.delete(userPref);
			}
			session.flush();
		}
		if (CollectionUtils.isNotEmpty(userPrefList)) {
			for (UserPreferences userPref : userPrefList) {
				session.saveOrUpdate(userPref);
			}
		}
	}
	
	@Override
	public List<UserPreferences> getUserPreferences(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria userPrefCriteria = session.createCriteria(UserPreferences.class);
		userPrefCriteria.add(Restrictions.eq("userId", userId));
		ProjectionList userPrefProjection = Projections.projectionList();
		userPrefProjection.add(Projections.property("productName"));
		userPrefProjection.add(Projections.property("attrName"));
		userPrefCriteria.setProjection(userPrefProjection);
		ScrollableResults scrollableResults = userPrefCriteria.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
		List<UserPreferences> userPrefColl = new ArrayList<>();
		UserPreferences userPrefObj = null;
		while (scrollableResults.next()) {
			userPrefObj = new UserPreferences();
			userPrefObj.setProductName((String)scrollableResults.get()[0]);
			userPrefObj.setAttrName((String)scrollableResults.get()[1]);
			userPrefColl.add(userPrefObj);
		}
		return userPrefColl;
	}
	
	@Override
	public String checkCascadeFlag(String dfrId) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria criteria = session.createCriteria(AssetNewVal.class);
		criteria.add(Restrictions.eq(DartConstants.DFRID, dfrId))	
				.add(Restrictions.eq(DartConstants.HEADER_57, "Y"))
				.add(Restrictions.eq(DartConstants.ATTR_325, "Y"));
		List<AssetNewVal> assetNewValList = (List<AssetNewVal>)criteria.list();		
		String checkCascadeFlag = "N";
		if(CollectionUtils.isNotEmpty(assetNewValList))
			checkCascadeFlag = "Y";
		
		return checkCascadeFlag;
	}
}
