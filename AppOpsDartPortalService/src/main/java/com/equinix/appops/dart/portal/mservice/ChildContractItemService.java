package com.equinix.appops.dart.portal.mservice;



import java.util.Map;

import com.equinix.appops.dart.portal.entity.mservices.ChildContractItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;

public interface ChildContractItemService {

    ChildContractItem execute(Map<String, Map<String, Value>> payload) throws Exception;
}
