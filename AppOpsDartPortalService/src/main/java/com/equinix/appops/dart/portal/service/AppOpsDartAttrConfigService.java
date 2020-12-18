package com.equinix.appops.dart.portal.service;

import java.util.List;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;

public interface AppOpsDartAttrConfigService {
     
     
	 AttributeConfig getAttrConfigVlaueByKey(String product,String name);

	 List<AttributeConfig> getAttrsWithDpntAttr();

	List<DependentAttrUpdate> getDependentAttrUpdate(String attributeFamily);

	AttributeConfig getAttrConfigVlaueById(String rowId);
	
	void runSqlQuery(String sql);
	
	void runBatchUpdate(String[] sqlArray) throws Exception;

	List<DependentAttrUpdate> getAutoDependentAttrUpdate(String attributeFamily);

	List<String> getAllAttrFamilies();
}
