package com.equinix.appops.dart.portal.service;


import com.equinix.appops.dart.portal.entity.UserInfo;

public interface UserService {
	UserInfo getUser(String userId);
}
