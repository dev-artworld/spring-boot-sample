package com.equinix.appops.dart.portal.dao.mservice.dao;

import com.equinix.appops.dart.portal.entity.mservices.ChildContractItem;

public interface ChildContractItemDao {
    ChildContractItem saveContractItem(ChildContractItem childContractItem);
    ChildContractItem getChildContractItem(String sysId, String uContractItem);
}
