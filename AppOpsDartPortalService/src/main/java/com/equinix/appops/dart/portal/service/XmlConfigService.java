package com.equinix.appops.dart.portal.service;


import java.util.List;

import com.equinix.appops.dart.portal.entity.XmlConfig;

public interface XmlConfigService {
	void saveUpdateXmlConfig(XmlConfig xmlConfig);
	XmlConfig getXmlDataByXmlName(String xmlName);
	List<XmlConfig> getXmlConfigsByXmlGroup(String xmlGroup);
	String getEmailTemplate(String xmlName);
	void loadAllEmailTemplates();
}
