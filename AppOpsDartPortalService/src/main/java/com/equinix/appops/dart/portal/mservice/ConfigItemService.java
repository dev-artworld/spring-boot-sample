package com.equinix.appops.dart.portal.mservice;


import com.equinix.appops.dart.portal.entity.mservices.ConfigItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;

import java.util.Map;


public interface ConfigItemService {
    ConfigItem execute(Map<String, Map<String, Value>> payload) throws Exception;
}
