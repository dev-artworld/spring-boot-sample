package com.equinix.appops.dart.portal.buisness.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.buisness.AppOpsCacheBusiness;
import com.equinix.appops.dart.portal.service.AppOpsCacheService;

@Service
public class AppOpsCacheBusinessImpl implements AppOpsCacheBusiness {

	
	@Autowired
	AppOpsCacheService cacheService;
	
	@Override
	public String refreshAttributeConfigCache() {
		// TODO Auto-generated method stub
		return cacheService.refreshAttributeConfigCache();
	}
	
	@Override
	public String refreshOtherAttributesCache() {
		return cacheService.refreshOtherAttributesCache();
	}

}
