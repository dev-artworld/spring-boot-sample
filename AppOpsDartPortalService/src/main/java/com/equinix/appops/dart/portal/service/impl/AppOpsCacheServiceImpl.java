package com.equinix.appops.dart.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.dao.AppOppsDartAttrConfigDao;
import com.equinix.appops.dart.portal.dao.AppOpsDartDaDao;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;
import com.equinix.appops.dart.portal.model.AttrConfigVO;
import com.equinix.appops.dart.portal.service.AppOpsCacheService;

@Service
public class AppOpsCacheServiceImpl implements AppOpsCacheService{
	
	Logger logger = LoggerFactory.getLogger(AppOpsCacheServiceImpl.class);

    @Autowired
    AppOpsDartDaDao daDao;
    
    @Autowired
    AppOppsDartAttrConfigDao attrConfigDao;

    @Override
	public String refreshAttributeConfigCache() {
		String result = daDao.resetConfigHeaderMap();
		result =result + "," + daDao.resetConfigProductAttrMap();
		result =result + "," + daDao.resetConfigProdcutCommonAttrMap();
		result = result + "," + attrConfigDao.resetAttributeConfigByProductAndAttr();
		result = result+ "," + attrConfigDao.resetDependentAttrUpdateByAttrFamily();
		result = result + "," + initializeDependentAttrUpd();
		return result;
	}
      
    @Override
    public String refreshOtherAttributesCache() {
    	StringBuilder result = new StringBuilder(daDao.resetDartFilterProductWidget());
    	result.append(",").append(daDao.resetFilterList()).append(",").append(daDao.resetGlobalSearch()).append(",").append(daDao.resetProductFilters()).append(",")
    	.append(daDao.resetErrorSection()).append(",").append(daDao.resetHierarchyView()).append(",").append(daDao.resetDartDaFilter()).append(",")
    	.append(daDao.resetSiebelAssetDaCache()).append(",").append(daDao.resetFilterListFromErrorMaster());
    	return result.toString();
    }
    
    @Override
    public String initializeDependentAttrUpd() {
    	List<AttrConfigVO> attrConfigVOList = new ArrayList<>();
		List<AttributeConfig> attrConfigList = new ArrayList<>();
		List<DependentAttrUpdate> dependentAttrUpdList = new ArrayList<>();
		logger.info("<-----Attribute Config VO Loading Start----->");
		attrConfigVOList = attrConfigDao.getAttrConfigByProductAndAttr();
		logger.info("<---Attribute Config VO Size--->"+attrConfigVOList.size());
		logger.info("<-----Attribute Config VO Loading End----->");
		
		logger.info("<---Attribute Config Loading Start--->");
		attrConfigVOList.stream().forEach((attrConfigVO) -> {
			attrConfigList.add(attrConfigDao.getAttrConfigVlaueByKey(attrConfigVO.getProductName(), attrConfigVO.getAttrName()));
		});
		logger.info("<---Attribute Config Size --->"+attrConfigList.size());
		logger.info("<---Attribute Config Loading End--->");
		
		logger.info("<---Dependent Attribute Loading Start--->");
		attrConfigList.stream().forEach((attrConfig) -> {
			if (attrConfig.getAttributeFamily() != null && !attrConfig.getAttributeFamily().isEmpty() 
					&& attrConfig.getAttributeFamily().length() > 0) {
			dependentAttrUpdList.addAll(attrConfigDao.getDependentAttrUpdate(attrConfig.getAttributeFamily()));
			}
		});
		logger.info("<---Dependent Attribute Size--->"+dependentAttrUpdList.size());
		logger.info("<---Dependent Attribute Loading End--->");
		return "Attribute Config and Dependent Attribute Update Cache initialized...";
    }

}
