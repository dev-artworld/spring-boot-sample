package com.equinix.appops.dart.portal.service.impl;


import static com.equinix.appops.common.LogicConstants.EMAIL_TEMPLATE_GROUP;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.XmlConfigDao;
import com.equinix.appops.dart.portal.entity.XmlConfig;
import com.equinix.appops.dart.portal.model.AppOpsServiceException;
import com.equinix.appops.dart.portal.service.XmlConfigService;


@Service("xmlConfigService")
@Transactional
public class XmlConfigServiceImpl implements XmlConfigService {

	private static final Logger log = LoggerFactory.getLogger("appOpsLogger");
	
	private ConcurrentHashMap<String,String> emailTemplateMap = new ConcurrentHashMap<String,String>();
	
	@Autowired
	private XmlConfigDao xmlConfigDao;

	
	@Override
	public void saveUpdateXmlConfig(XmlConfig xmlConfig){
		log.debug("Start saveUpdateXmlConfig method: ");
		xmlConfigDao.saveUpdateXmlConfig(xmlConfig);
		log.debug("End saveUpdateXmlConfig method!");
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public XmlConfig getXmlDataByXmlName(String xmlName){
		return xmlConfigDao.getXmlDataByXmlName(xmlName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<XmlConfig> getXmlConfigsByXmlGroup(String xmlGroup) {
		return xmlConfigDao.getXmlConfigsByXmlGroup(xmlGroup);
	}
	
	@Override
    public String getEmailTemplate(String xmlName){
    	if(emailTemplateMap.get(xmlName) == null){
			XmlConfig template	= getXmlDataByXmlName(xmlName);		
			emailTemplateMap.put(xmlName, template.getXmlData());
		}
    	return emailTemplateMap.get(xmlName);
    }
	
	@Override
	public void loadAllEmailTemplates() {
		List<XmlConfig> xmlConfigs = xmlConfigDao
				.getXmlConfigsByXmlGroup(EMAIL_TEMPLATE_GROUP);
		for (XmlConfig xmlConfig : xmlConfigs) {
			if (xmlConfig != null && xmlConfig.getXmlName() != null
					&& xmlConfig.getXmlData() != null) {
				emailTemplateMap.put(xmlConfig.getXmlName().toUpperCase(),
						xmlConfig.getXmlData());
			} else {
				log.debug("No XML Name or Data found in DB for xmlGroup "
						+ EMAIL_TEMPLATE_GROUP);
				throw new AppOpsServiceException(
						"No XML Name or Data found in DB for xmlGroup "
								+ EMAIL_TEMPLATE_GROUP);
			}
		}
	}
}
