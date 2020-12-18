package com.equinix.appops.dart.portal.service;

import com.equinix.appops.dart.portal.entity.XmlConfig;
import com.equinix.appops.dart.portal.model.UserPrefModel;

public interface AppOpsCommonService {
	public XmlConfig getXmlConfigByName(String xmlName);	
	public void sendSampleAlert(String recepients);
	String persistUserPreferences(UserPrefModel userPrefModel);
	UserPrefModel getUserPreferences();
	String checkCascadeFlag(String dfrId);
}
