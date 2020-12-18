package com.equinix.appops.dart.portal.mservice.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.dao.mservice.dao.ContractItemDao;
import com.equinix.appops.dart.portal.entity.mservices.ContractItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;
import com.equinix.appops.dart.portal.mservice.ConfigItemService;
import com.equinix.appops.dart.portal.mservice.ContractItemService;
import com.equinix.appops.dart.portal.util.MSUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.Gson;

@Service
public class ContractItemServiceImpl implements  ContractItemService {
	
	Logger logger = LoggerFactory.getLogger(ContractItemService.class);

    @Autowired
    ContractItemDao contractItemDao;
    @Override
    public ContractItem execute(Map<String, Map<String, Value>> payload) throws Exception {
    	logger.info("Execution of ContractItemServiceImpl class started ");
        Map<String, Object> contractItemMap = new HashMap<String, Object>();
        Map<String, Value> valueMap = payload.get("result");

        valueMap.remove("sys_tags");
        for (String key : valueMap.keySet()) {
            String val = null;
            if(valueMap.get(key) != null) {
                if(valueMap.get(key).getLink() != null) {
                    val = valueMap.get(key).getDisplay_value();
                } else {
                    val = valueMap.get(key).getValue();
                }
            }
            contractItemMap.put(key, val);
        }

        Gson gson = new Gson();
        String json = gson.toJson(contractItemMap);

        ObjectMapper mapper = new ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

        ContractItem ci = mapper.readValue(json, ContractItem.class);
        ci.setDtlCapxUser("TIBCO API");
        ContractItem contractItem = contractItemDao.getContractItem(ci.getSys_id());
        if(null!=contractItem && null!=contractItem.getSys_id())
        	ci.setDtlCapxAction("UPDATE OPERATION");
        else
            ci.setDtlCapxAction("INSERT OPERATION");
        ci.setDtlCapxTimestamp(MSUtils.setDtlCapxTimestamp());
        logger.info("Execution for "+ci.getSys_id()+" in ContractItemServiceImpl class ended ");
       return contractItemDao.saveContractItem(ci);

    }
}
