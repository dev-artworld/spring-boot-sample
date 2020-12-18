package com.equinix.appops.dart.portal.mservice;

import com.equinix.appops.dart.portal.entity.mservices.ContractItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;

import java.util.Map;


public interface ContractItemService {
    ContractItem execute(Map<String, Map<String, Value>> payload) throws Exception;
}
