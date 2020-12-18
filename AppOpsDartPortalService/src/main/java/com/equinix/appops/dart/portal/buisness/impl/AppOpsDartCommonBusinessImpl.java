package com.equinix.appops.dart.portal.buisness.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.buisness.AppOpsDartCommonBusiness;
import com.equinix.appops.dart.portal.model.UserPrefModel;
import com.equinix.appops.dart.portal.service.AppOpsCommonService;

@Service
public class AppOpsDartCommonBusinessImpl implements AppOpsDartCommonBusiness {
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartCommonBusinessImpl.class);
	
	@Autowired
	AppOpsCommonService commonService;
	
	@Override
	public String persistUserPreferences(UserPrefModel userPrefModel) {
		return commonService.persistUserPreferences(userPrefModel);
	}
	
	@Override
	public UserPrefModel getUserPreferences() {
		return commonService.getUserPreferences();
	}

	@Override
	public String checkCascadeFlag(String dfrId) {
		return commonService.checkCascadeFlag(dfrId);
	}

}
