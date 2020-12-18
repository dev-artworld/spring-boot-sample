package com.equinix.appops.dart.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.SvAPIDao;
import com.equinix.appops.dart.portal.entity.Recon2Sync;
import com.equinix.appops.dart.portal.entity.SvSyncV;
import com.equinix.appops.dart.portal.entity.SvSyncVId;
import com.equinix.appops.dart.portal.service.SVSyncVService;

@Service
@Transactional
public class SVSyncVServiceImpl implements SVSyncVService{

	@Autowired
	SvAPIDao svAPIDao;
	
	@Override
	public List<Recon2Sync> getSvSyncV(String dfrId) {
		List<SvSyncV> svSyncVRecords = svAPIDao.getSvSyncVDart(dfrId);
		List<Recon2Sync> recon2SyncRecords = new ArrayList<Recon2Sync>(); 
		for (SvSyncV svSyncV : svSyncVRecords) {
			Recon2Sync recon2Sync = new Recon2Sync(svSyncV);
			svAPIDao.saveRecon2Sync(recon2Sync);
			recon2SyncRecords.add(recon2Sync);
		}
		
		/*//TODO temp:
		SvSyncV svSyncV = new SvSyncV();
		svSyncV.setId("1");
		svSyncV.setAssetId("1123");
		svSyncV.setRequestedBy("saurabh");
		svSyncV.setSiebelReqDate(new Date());
		svSyncV.setIncidentNo("341");
		svSyncV.setRegion("AMER");				
		Recon2Sync recon2Sync = new Recon2Sync(svSyncV);		
		svAPIDao.saveRecon2Sync(recon2Sync);
		recon2SyncRecords.add(recon2Sync);*/
		return recon2SyncRecords;
	}
}
