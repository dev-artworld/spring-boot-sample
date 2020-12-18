package com.equinix.appops.dart.portal.dao.mservice.dao;


import com.equinix.appops.dart.portal.entity.mservices.ContractItem;

public interface ContractItemDao {

    ContractItem saveContractItem(ContractItem contractItem);
    
    ContractItem getContractItem(String sysId);
}
