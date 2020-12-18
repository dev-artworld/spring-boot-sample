package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.Recon2Sync;
import com.equinix.appops.dart.portal.entity.SvApi;
import com.equinix.appops.dart.portal.entity.SvSyncV;

public interface SvAPIDao {
	List<SvApi> getPoeItems(String dfrId);
	List<SvApi> getPofItems(String rootId);
	/*List<SvSyncV> getSvSyncV(String dfrId);*/
	List<SvSyncV> getSvSyncVDart(String dfrId);
	void saveRecon2Sync (Recon2Sync recon2Sync);
}
