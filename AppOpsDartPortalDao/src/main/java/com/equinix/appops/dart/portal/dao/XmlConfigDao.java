package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.XmlConfig;

public interface XmlConfigDao {	
	public void saveUpdateXmlConfig(XmlConfig xmlConfig);

	public XmlConfig getXmlDataByXmlName(String xmlName);

	public List<XmlConfig> getXmlConfigsByXmlGroup(String xmlGroup);
}
