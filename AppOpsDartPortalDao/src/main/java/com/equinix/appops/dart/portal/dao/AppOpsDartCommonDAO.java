package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.UserPreferences;

public interface AppOpsDartCommonDAO {

	void persistUserPreferences(List<UserPreferences> userPrefList, String userId);

	List<UserPreferences> getUserPreferences(String userId);

	String checkCascadeFlag(String dfrId);
}
