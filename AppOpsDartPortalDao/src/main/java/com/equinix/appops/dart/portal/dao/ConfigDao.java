package com.equinix.appops.dart.portal.dao;

import java.util.List;
import java.util.Map;

import com.equinix.appops.dart.portal.entity.Config;

public interface ConfigDao {	


	String getValueByKey(String key);

	List<String> getAllConfigKeys();

	Map<String,String> getConfigValuesByKeyList(List<String> configKeyList);

	boolean updateConfigKeyValueByKey(Config config);

	List<String> getKeysByKeyPatternAndValues(String keyPattern, String value);

	boolean saveConfigKeyValue(Config config);

	Config getConfigByKey(String key);

	void deleteConfigByKey(String key);

	List<String> getConfigByKeyPattern(String keyPattern);

	List<Config> getAllConfigs();

}
