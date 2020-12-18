package com.equinix.appops.dart.portal.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.entity.mservices.ChildContractItem;
import com.equinix.appops.dart.portal.entity.mservices.ConfigItem;
import com.equinix.appops.dart.portal.entity.mservices.ContractItem;
import com.equinix.appops.dart.portal.entity.mservices.Value;
import com.equinix.appops.dart.portal.mservice.ChildContractItemService;
import com.equinix.appops.dart.portal.mservice.ConfigItemService;
import com.equinix.appops.dart.portal.mservice.ContractItemService;


@RestController
@RequestMapping(value="/tibcoContract")
public class MSInboundApisController {
	
	@Autowired
    private ConfigItemService configItemService;

    @Autowired
    private ContractItemService contractItemService;
    
    @Autowired
    private ChildContractItemService childContractItemService;
	
	@PostMapping("/dmsSaveConfigItem")
	public Map<String, Object> saveConfigItem(@RequestBody Map<String, Map<String, Value>> payload)  {

        Map<String, Object> response = new HashMap<>();

        ConfigItem ciResponse = null;
        try{
            ciResponse = configItemService.execute(payload);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(ciResponse != null) {
            response.put("status", "Success");
        } else {
            response.put("status", "Failed");
        }

        return response;
    } 
	
	@PostMapping("/dmsSaveContractItem")
    public Map<String, Object> saveContractItem(@RequestBody Map<String, Map<String, Value>> payload)  {

        Map<String, Object> response = new HashMap<>();

        ContractItem ciResponse = null;
        try{
            ciResponse = contractItemService.execute(payload);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(ciResponse != null) {
            response.put("status", "Success");
        } else {
            response.put("status", "Failed");
        }

        return response;
    }

	@PostMapping("/dmsSaveChildContractItem")
    public Map<String, Object> saveChildContractItem(@RequestBody Map<String, Map<String, Value>> payload)  {

        Map<String, Object> response = new HashMap<>();

        ChildContractItem ciResponse = null;
        try{
            ciResponse = childContractItemService.execute(payload);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(ciResponse != null) {
            response.put("status", "Success");
        } else {
            response.put("status", "Failed");
        }

        return response;
    }
}
