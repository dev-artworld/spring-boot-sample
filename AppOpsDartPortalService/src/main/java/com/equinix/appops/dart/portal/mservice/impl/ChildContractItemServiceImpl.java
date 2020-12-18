package com.equinix.appops.dart.portal.mservice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.dao.mservice.dao.ChildContractItemDao;
import com.equinix.appops.dart.portal.dao.mservice.dao.ConfigItemColDao;
import com.equinix.appops.dart.portal.entity.mservices.ChildContractItem;
import com.equinix.appops.dart.portal.entity.mservices.ChildContractItemId;
import com.equinix.appops.dart.portal.entity.mservices.ContractItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;
import com.equinix.appops.dart.portal.mservice.ChildContractItemService;
import com.equinix.appops.dart.portal.mservice.ContractItemService;
import com.equinix.appops.dart.portal.util.MSUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.Gson;

@Service
public class ChildContractItemServiceImpl implements  ChildContractItemService {
	
	Logger logger = LoggerFactory.getLogger(ChildContractItemService.class);

    @Autowired
    ChildContractItemDao childContractItemDao;

    @Autowired
    ConfigItemColDao configItemColDao;



    @Override
    public ChildContractItem execute(Map<String, Map<String, Value>> payload) throws Exception {
    	logger.info("Execution of ChildContractItemServiceImpl class started ");
        Map<String, Object>  childContractItemMap = new HashMap<String, Object>();
        List<String> contractCols = configItemColDao.getChildContractCols();
        Map<String, Value> valueMap = payload.get("result");

        valueMap.remove("sys_tags");
        for (String key : valueMap.keySet()) {
            if(contractCols.contains(key)) {
                String val = null;
                if(valueMap.get(key) != null) {
                    if(valueMap.get(key).getLink() != null) {
                        val = valueMap.get(key).getDisplay_value();
                    } else {
                        val = valueMap.get(key).getValue();
                    }
                }
                childContractItemMap.put(key, val);
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(childContractItemMap);

        ObjectMapper mapper = new ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

        ChildContractItem ci = mapper.readValue(json, ChildContractItem.class);

        ChildContractItemId ccId = new ChildContractItemId();
        ccId.setContractItem(ci.getuContractItem());
        ccId.setSysId(ci.getSysId());
        ci.setChildContractItemId(ccId);
        ci.setDtlCapxUser("TIBCO API");
        ChildContractItem childContractItem = childContractItemDao.getChildContractItem(ci.getSysId(),ci.getuContractItem());
        if(null!=childContractItem && null!=childContractItem.getChildContractItemId())
        	ci.setDtlCapxAction("UPDATE OPERATION");
        else
            ci.setDtlCapxAction("INSERT OPERATION");
        ci.setDtlCapxTimestamp(MSUtils.setDtlCapxTimestamp());
        logger.info("Execution for "+ci.getSysId()+" in ChildContractItemServiceImpl class ended ");
	    return childContractItemDao.saveContractItem(ci);
    }
}
