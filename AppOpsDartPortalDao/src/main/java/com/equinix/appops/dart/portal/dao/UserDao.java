package com.equinix.appops.dart.portal.dao;

import com.equinix.appops.dart.portal.entity.UserInfo;

public interface UserDao {
	UserInfo getUser(String userId);
}
