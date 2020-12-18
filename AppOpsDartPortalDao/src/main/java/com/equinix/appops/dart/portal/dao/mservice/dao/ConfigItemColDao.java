package com.equinix.appops.dart.portal.dao.mservice.dao;


import java.util.List;

public interface ConfigItemColDao {

    List<String> getParentCols();
    List<String> getChildContractCols();
    List<String> getContractCols();
}
