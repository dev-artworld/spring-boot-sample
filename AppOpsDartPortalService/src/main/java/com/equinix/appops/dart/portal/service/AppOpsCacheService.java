package com.equinix.appops.dart.portal.service;

public interface AppOpsCacheService {

	public String refreshAttributeConfigCache();
	
	public String refreshOtherAttributesCache();
	
	String initializeDependentAttrUpd();
}
