package com.equinix.appops.dart.portal.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.equinix.appops.dart.portal.service.AppOpsCacheService;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
	
	Logger logger = LoggerFactory.getLogger(StartupApplicationListener.class);
	
	@Autowired
	AppOpsCacheService cacheService;
			
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		String response = cacheService.initializeDependentAttrUpd();
		logger.info("Dependent Attribute Update >>> "+response);
	}

}
