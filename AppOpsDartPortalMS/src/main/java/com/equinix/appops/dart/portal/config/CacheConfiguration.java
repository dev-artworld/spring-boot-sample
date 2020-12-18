package com.equinix.appops.dart.portal.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.equinix.appops.dart.portal.constant.CacheConstant;

@Configuration
@EnableCaching
@PropertySource("classpath:application.properties")
public class CacheConfiguration {

	@Autowired
	private Environment env;
	
	@Bean
	  public LettuceConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory();
		redisConnectionFactory.setHostName(env.getProperty("redis.host"));
		redisConnectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
		return redisConnectionFactory;
	  }

	  @Bean
	  public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory cf) {
		  RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(cf);
	    /*redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setHashKeySerializer(new StringRedisSerializer());*/
	    return redisTemplate;
	  }

	  /*@Bean
	  public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
	    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
	    List<String> cacheNames = new ArrayList<>();
	    cacheNames.add("filterList");
	    cacheNames.add("globalsearch");
	    cacheNames.add("getErrorSection");
	    cacheNames.add("getFilterListFromErrorMaster");
	    cacheNames.add("getProductFilters");
	    cacheNames.add("daDartFilterProductWidget");
	    
	    cacheNames.add("getHierarchyView");
	    cacheNames.add("SiebelAssetDaCache");
	    cacheNames.add("dartDafilter");
	    cacheNames.add("getConfigHeaderMap");
	    cacheNames.add("getConfigProductAttrMap");
	    cacheNames.add("getConfigProdcutCommonAttrMap");
	    cacheNames.add("sblListWhenOnlyErrorFilterApplied");
	    cacheNames.add("getAttributeConfigByProductAndAttr");
	    cacheNames.add("getDependentAttrUpdateByAttrFamily");
	    cacheNames.add("getDfrByTeamAndStatus");
	    cacheManager.setCacheNames(cacheNames);
	    cacheManager.setTransactionAware(true);
	    // Number of seconds before expiration. Defaults to unlimited (0)
	    cacheManager.setDefaultExpiration(Long.parseLong(env.getProperty("redis.expiration.duration.seconds")));
	    return cacheManager;
	  }*/
	  @Bean
	  public CacheManager cacheManager(LettuceConnectionFactory cf) {
	   
		Map<String, RedisCacheConfiguration> cacheConfigurations = new ConcurrentHashMap<>(); 
		long ttl = Long.parseLong(env.getProperty("redis.expiration.duration.seconds"));
		long ttl24hours = 24;
		
		cacheConfigurations.put(CacheConstant.FILTER_LIST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.GLOBAL_SEARCH, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.ERROR_SECTION, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.FILTER_LIST_FROM_ERROR_MASTER, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.PRODUCT_FILTERS, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.HIERARCHY_VIEW, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.DART_DA_FILTER, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.SIEBEL_ASSET_DA_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.daDartFilterProductWidget, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		cacheConfigurations.put(CacheConstant.sblListWhenOnlyErrorFilterApplied, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl)));
		
		
		cacheConfigurations.put(CacheConstant.CONFIG_HEADER_MAP, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(ttl24hours)));
		cacheConfigurations.put(CacheConstant.CONFIG_PRODUCT_ATTR_MAP, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(ttl24hours)));
		cacheConfigurations.put(CacheConstant.CONFIG_PRODCUT_COMMON_ATTR_MAP, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(ttl24hours)));
		cacheConfigurations.put(CacheConstant.ATTRIBUTE_CONFIG_BY_PRODUCT_ATTR, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(ttl24hours)));
		cacheConfigurations.put(CacheConstant.DEPENDENT_ATTR_UPDATE_BY_ATTR_FAMILY, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(ttl24hours)));
		
		
	    RedisCacheManager cacheManager = RedisCacheManager.builder(cf).withInitialCacheConfigurations(cacheConfigurations).build();
	    cacheManager.setTransactionAware(true);
	    return cacheManager;
	  }
}
