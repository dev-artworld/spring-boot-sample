package com.equinix.appops.dart.portal.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.model.EmailVO;
import com.equinix.appops.dart.portal.service.EmailServiceQueueSender;
import com.equinix.appops.dart.portal.service.KafkaSenderService;

@Service("emailServiceQueueSender")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EmailServiceQueueSenderImpl implements EmailServiceQueueSender{	
	private static final Logger log = LoggerFactory.getLogger(EmailServiceQueueSenderImpl.class);
	
	@Autowired
    private KafkaSenderService senderService;
	
	
	public void publishEmailServiceMessage(EmailVO emailVO){
		log.debug("Sending email XML to queue: '"  + "'\n");
		
		try {
			senderService.pushEmail(emailVO);
            log.info("Email VO xml sent!!!!"+emailVO);
		} catch (Exception e) {
			log.error("unable to push object", e);
		}
	}
	
	
}
