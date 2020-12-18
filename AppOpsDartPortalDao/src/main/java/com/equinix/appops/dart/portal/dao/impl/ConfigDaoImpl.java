package com.equinix.appops.dart.portal.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.equinix.appops.dart.portal.dao.ConfigDao;
import com.equinix.appops.dart.portal.entity.Config;

@Repository
@Transactional
public class ConfigDaoImpl implements ConfigDao {

	Logger logger = LoggerFactory.getLogger(ConfigDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String getValueByKey(String key){
		//System.out.println("Key: "+key);
		Config config=(Config)sessionFactory.getCurrentSession().get(Config.class, key);
		return config.getValue();
	}

	@Override
	public Config getConfigByKey(String key){

		Config config=(Config)sessionFactory.getCurrentSession().get(Config.class, key);
		return config;
	}

	@Override
	public List<String> getAllConfigKeys()
	{
		return sessionFactory.getCurrentSession().createQuery("select key from Config").list();
	}

	@Override
	public Map<String,String> getConfigValuesByKeyList(List<String> configKeyList)
	{
		Map<String,String> configMap = new HashMap<String,String>();
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Config.class)
				.add(Restrictions.in("key", configKeyList));
		List<Config> configList = criteria.list();
		for(Config config : configList){
			configMap.put(config.getKey(), config.getValue());
		}
		return configMap;
	}

	@Override
	public boolean updateConfigKeyValueByKey(Config config) {
		sessionFactory.getCurrentSession().merge(config);
		return true;
	}

	@Override
	public boolean saveConfigKeyValue(Config config) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(config);;
		return true;
	}

	@Override
	public void deleteConfigByKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session
				.createQuery("delete from Config config where config.key =?");
		queryResult.setParameter(0, key);
		queryResult.executeUpdate();


	}

	@Override
	public List<String> getKeysByKeyPatternAndValues(String keyPattern,
			String value) {
		return (List<String>)sessionFactory.getCurrentSession()
				.createQuery("select key from Config where key like ? and value = ?")
		 .setParameter(0, "%"+keyPattern+"%")
		 .setParameter(1, value)
		 .list();
	}

	@Override
	public List<String> getConfigByKeyPattern(String keyPattern) {

		return (List<String>)sessionFactory.getCurrentSession()
				.createQuery("select key from Config where key like ?")
		 .setParameter(0, "%"+keyPattern+"%")
		 .list();
	}

	@Override
	public List<Config> getAllConfigs() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Config.class).list();
	}

}
