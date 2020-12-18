package com.equinix.appops.dart.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.AppOppsDartAttrConfigDao;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;
import com.equinix.appops.dart.portal.service.AppOpsDartAttrConfigService;

@Service
@Transactional
public class AppOppsDartAttrConfigSeriveImpl implements AppOpsDartAttrConfigService{

	@Autowired
    AppOppsDartAttrConfigDao appOppsDartAttrConfigDao;

	@Override
	public AttributeConfig getAttrConfigVlaueByKey(String product, String name) {
		return appOppsDartAttrConfigDao.getAttrConfigVlaueByKey(product,name);
	}

	@Override
	public List<AttributeConfig> getAttrsWithDpntAttr() {
		return appOppsDartAttrConfigDao.getAttrsWithDpntAttr();
		
	}

	@Override
	public List<DependentAttrUpdate> getDependentAttrUpdate(String attributeFamily) {
		return appOppsDartAttrConfigDao.getDependentAttrUpdate(attributeFamily);
	}
	
	@Override
	public List<DependentAttrUpdate> getAutoDependentAttrUpdate(String attributeFamily) {
		return appOppsDartAttrConfigDao.getAutoDependentAttrUpdate(attributeFamily);
	}

	@Override
	public AttributeConfig getAttrConfigVlaueById(String rowId) {
		return appOppsDartAttrConfigDao.getAttrConfigVlaueById(rowId);
	}

	@Override
	public void runSqlQuery(String sql) {
		appOppsDartAttrConfigDao.runSqlQuery(sql);		
	}

	@Override
	public void runBatchUpdate(String[] sqlArray) throws Exception{
		appOppsDartAttrConfigDao.runBatchUpdate(sqlArray);		
	}

	@Override
	public List<String> getAllAttrFamilies() {
		// TODO Auto-generated method stub
		return appOppsDartAttrConfigDao.getAllAttrFamilies();
	}
   
}
