package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.SrcServiceDaArrayEsk;

public interface SrcServiceDaArrayESKDao {
	List<SrcServiceDaArrayEsk> getSrcServiceDaArrayESK(Long serviceId);
}
