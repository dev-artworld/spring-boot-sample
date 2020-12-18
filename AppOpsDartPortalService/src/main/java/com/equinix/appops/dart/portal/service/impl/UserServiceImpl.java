package com.equinix.appops.dart.portal.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.UserDao;
import com.equinix.appops.dart.portal.entity.UserInfo;
import com.equinix.appops.dart.portal.service.UserService;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserInfo getUser(String userId) {		
		return userDao.getUser(userId);
	}

	
	
}
