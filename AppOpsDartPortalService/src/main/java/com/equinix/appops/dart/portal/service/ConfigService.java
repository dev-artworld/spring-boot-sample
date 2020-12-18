package com.equinix.appops.dart.portal.service;


import java.util.List;
import java.util.Map;

import com.equinix.appops.dart.portal.entity.Config;

public interface ConfigService {


	String getValueByKey(String key);

	List<String> getAllConfigKeys();

	Map<String,String> getConfigValuesByKeyList(List<String> configKeyList);

	boolean updateConfigKeyValueByKey(Config config);

	void loadConfigSingletonContainer();

	List<String> getKeysByKeyPatternAndValues(String string, String string2);

	boolean saveConfigKeyValue(Config config);

	Config getConfigByKey(String key);

	void deleteConfigKeyValue(String key);

	List<String> getConfigByKeyPattern(String keyPattern);
	
}
