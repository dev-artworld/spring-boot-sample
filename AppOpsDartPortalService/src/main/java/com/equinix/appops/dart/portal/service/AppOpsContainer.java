package com.equinix.appops.dart.portal.service;

import com.equinix.appops.dart.portal.model.AppOpsContainerAttr;

public interface AppOpsContainer {

	String METHOD_REFRESH_CONTAINER = "refreshContainer";
	String METHOD_REFRESH_ALL_CONTAINER = "refreshAllContainer";
	void refreshContainer(AppOpsContainerAttr attr);
	void refreshAll(AppOpsContainerAttr attr);

}
