package com.equinix.appops.dart.portal.model;

import com.equinix.appops.dart.portal.entity.DfrMaster;

public class DfrResult {

	boolean isFound;
	
	DfrMaster dfrMaster;

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	public DfrMaster getDfrMaster() {
		return dfrMaster;
	}

	public void setDfrMaster(DfrMaster dfrMaster) {
		this.dfrMaster = dfrMaster;
	}
}
