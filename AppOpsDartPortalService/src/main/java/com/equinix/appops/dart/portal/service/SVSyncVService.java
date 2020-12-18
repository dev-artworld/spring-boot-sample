package com.equinix.appops.dart.portal.service;

import java.util.List;

import com.equinix.appops.dart.portal.entity.Recon2Sync;

public interface SVSyncVService {
     
	List<Recon2Sync> getSvSyncV(String dfrId);
	
	
}
