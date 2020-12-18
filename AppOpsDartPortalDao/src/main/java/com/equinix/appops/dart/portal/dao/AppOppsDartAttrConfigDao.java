package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;
import com.equinix.appops.dart.portal.model.AttrConfigVO;

public interface AppOppsDartAttrConfigDao {

	List<AttributeConfig> getAttrsWithDpntAttr();

	AttributeConfig getAttrConfigVlaueByKey(String product, String name);
	
	AttributeConfig getAttrConfigVlaueByHeaderPos(String product, String position);

	List<DependentAttrUpdate> getDependentAttrUpdate(String attributeFamily);

	AttributeConfig getAttrConfigVlaueById(String rowId);

	void runSqlQuery(String sql);

	List<AttributeConfig> getAttrConfigVlaueForCaplogix(String productType);
	
	List<AttributeConfig> getAttrConfigVlaueByProduct(String product);

	List<AttributeConfig> getAttrConfigVlaueByProduct(String ...productHeader);

	List<AttributeConfig> getOnlyAttrConfigVlaueSiebel();

	void runBatchUpdate(String[] sqlArray) throws Exception;
	List<AttrConfigVO> getAttrConfigByProductAndAttr();

	String resetAttributeConfigByProductAndAttr();

	String resetDependentAttrUpdateByAttrFamily();

	List<DependentAttrUpdate> getAutoDependentAttrUpdate(String attributeFamily);

	List<String> getAllAttrFamilies();
}
