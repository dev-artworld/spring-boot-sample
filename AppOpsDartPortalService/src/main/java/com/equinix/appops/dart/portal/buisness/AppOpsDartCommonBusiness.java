package com.equinix.appops.dart.portal.buisness;

import com.equinix.appops.dart.portal.model.UserPrefModel;

public interface AppOpsDartCommonBusiness {

	String persistUserPreferences(UserPrefModel userPrefModel);

	UserPrefModel getUserPreferences();
	
	String checkCascadeFlag(String dfrId);
}
