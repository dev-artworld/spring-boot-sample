package com.equinix.appops.dart.portal.dao.mservice.dao;



import java.util.List;

import com.equinix.appops.dart.portal.entity.mservices.ConfigChildItem;
import com.equinix.appops.dart.portal.entity.mservices.ConfigItem;


public interface ConfigItemDao {

    ConfigItem saveConfigItem(ConfigItem configItem, List<ConfigChildItem> configChildItemList);
    ConfigItem getConfigItem(String sysId);
    ConfigChildItem getConfigChildItem(String fieldName,String sysId);

}
