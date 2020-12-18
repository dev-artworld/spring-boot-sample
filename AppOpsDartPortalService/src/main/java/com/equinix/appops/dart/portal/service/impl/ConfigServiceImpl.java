package com.equinix.appops.dart.portal.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.ConfigDao;
import com.equinix.appops.dart.portal.entity.Config;
import com.equinix.appops.dart.portal.model.AppOpsServiceException;
import com.equinix.appops.dart.portal.service.ConfigService;


@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {

	private static final Logger log = LoggerFactory.getLogger("configServiceLogger");
	
	@Autowired
	private ConfigDao configDao;

	Map<String,String> configMap = new HashMap<String,String>();
	
		
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void loadConfigSingletonContainer(){
		log.debug("Start warming of cohrence and local cache for Config from ConfigSingletonContainer : ");
		try {
			configMap = new HashMap<String,String>();
			List<Config> configs = configDao.getAllConfigs();
			for (Config config : configs) {
				configMap.put(config.getKey(), config.getValue());
			}
		}catch(Exception ex){
			log.error("Error in " + this.getClass().getName() + " init method execution!" + ex.getMessage());
			log.error("Error:"+ex);
			throw new AppOpsServiceException(ex.getMessage());
		}
		log.debug("Done warming of cohrence and local cache for Config from ConfigSingletonContainer!");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getValueByKey(String key)
	{
		log.debug("fetching " + key);
		String value = configMap.get(key);
		if(value == null){
			 log.debug("fetching " + key + " from Database");
			 value = configDao.getValueByKey(key);
			 configMap.put(key,value);

		}
		return value;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> getAllConfigKeys()
	{
		return configDao.getAllConfigKeys();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Map<String,String> getConfigValuesByKeyList(List<String> configKeyList)
	{
		return configDao.getConfigValuesByKeyList(configKeyList);
	}

	@Override
	public boolean updateConfigKeyValueByKey(Config config) {
		return configDao.updateConfigKeyValueByKey(config);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean saveConfigKeyValue(Config config) {
		return configDao.saveConfigKeyValue(config);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteConfigKeyValue(String key) {
		configDao.deleteConfigByKey(key);
	}


	@Override
	public List<String> getKeysByKeyPatternAndValues(String string,
			String string2) {
		return configDao.getKeysByKeyPatternAndValues(string, string2);
	}

	@Override
	public Config getConfigByKey(String key) {
		// TODO Auto-generated method stub
		return configDao.getConfigByKey(key);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> getConfigByKeyPattern(String keyPattern)
	{
		return configDao.getConfigByKeyPattern(keyPattern);
	}



}
