package com.equinix.appops.dart.portal.mservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.dao.AppOpsInitiateDFRDao;
import com.equinix.appops.dart.portal.dao.mservice.dao.ConfigItemColDao;
import com.equinix.appops.dart.portal.dao.mservice.dao.ConfigItemDao;
import com.equinix.appops.dart.portal.entity.mservices.ConfigChildItem;
import com.equinix.appops.dart.portal.entity.mservices.ConfigChildItemId;
import com.equinix.appops.dart.portal.entity.mservices.ConfigItem;
import com.equinix.appops.dart.portal.entity.mservices.ContractItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;
import com.equinix.appops.dart.portal.mservice.ConfigItemService;
import com.equinix.appops.dart.portal.util.MSUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.Gson;


@Service
public class ConfigItemServiceImpl implements ConfigItemService {
	
	Logger logger = LoggerFactory.getLogger(ConfigItemService.class); 

  @Autowired
  ConfigItemDao configItemDao;


  @Autowired
  ConfigItemColDao configItemColDao;


    @Override
    public ConfigItem execute(Map<String, Map<String, Value>> payload) throws Exception{
     logger.info("Execution of ConfigItemServiceImpl class started ");
      List<String> parentCols = configItemColDao.getParentCols();
      Map<String, Object> finalParentCI = new HashMap<String, Object>();
      Map<String, Value> valueMap = payload.get("result");
      List<ConfigChildItem> configChildItemList = new ArrayList<>();
      for(String key : valueMap.keySet()) {
        if(parentCols.contains(key)) {
          String val = null;
          if(valueMap.get(key) != null) {
              if(valueMap.get(key).getLink() != null) {
                  val = valueMap.get(key).getDisplay_value();
              } else {
                  val = valueMap.get(key).getValue();
              }
          }
          finalParentCI.put(key, val);
        } else {
          ConfigChildItem configChildItem = new ConfigChildItem();
          ConfigChildItemId configChildItemId = new ConfigChildItemId();
          configChildItemId.setSysId(valueMap.get("sys_id").getDisplay_value());
          configChildItemId.setFieldName(key);
            if(valueMap.get(key).getLink() != null) {
                configChildItem.setFieldValue(valueMap.get(key).getDisplay_value());
            } else {
                configChildItem.setFieldValue(valueMap.get(key).getValue());
            }
            configChildItem.setDtlCapxUser("TIBCO API");
            ConfigChildItem ccId = configItemDao.getConfigChildItem(key, configChildItemId.getSysId());
            if(null!=ccId && null!=ccId.getConfigChildItemId())
            	configChildItem.setDtlCapxAction("UPDATE OPERATION");
            else
            	configChildItem.setDtlCapxAction("INSERT OPERATION");
            configChildItem.setDtlCapxTimestamp(MSUtils.setDtlCapxTimestamp());
          configChildItem.setConfigChildItemId(configChildItemId);
          configChildItemList.add(configChildItem);
        }
      }

      Gson gson = new Gson();
      String json = gson.toJson(finalParentCI);

      ObjectMapper mapper = new ObjectMapper()
              .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
              .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

       ConfigItem ci = mapper.readValue(json, ConfigItem.class);
       ci.setDtlCapxUser("TIBCO API");
       ConfigItem configItem = configItemDao.getConfigItem(ci.getSysId());
       if(null!=configItem && null!=configItem.getSysId())
    	   ci.setDtlCapxAction("UPDATE OPERATION");
       else
           ci.setDtlCapxAction("INSERT OPERATION");
       ci.setDtlCapxTimestamp(MSUtils.setDtlCapxTimestamp());
       logger.info("Execution for "+ci.getSysId()+" in ConfigItemServiceImpl class ended ");
        return configItemDao.saveConfigItem(ci, configChildItemList);
        
    }


}

