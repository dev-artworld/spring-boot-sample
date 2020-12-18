package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.SrcServiceDaArrayRamp;

public interface SrcServiceDaArrayRampDao {
	List<SrcServiceDaArrayRamp> getSrcServiceDaArrayRamp(Long serviceId);
}
