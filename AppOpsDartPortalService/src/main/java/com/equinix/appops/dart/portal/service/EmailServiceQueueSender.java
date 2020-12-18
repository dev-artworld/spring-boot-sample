package com.equinix.appops.dart.portal.service;

import com.equinix.appops.dart.portal.model.EmailVO;

public interface EmailServiceQueueSender {
	
  public void publishEmailServiceMessage(EmailVO emailVO);   

}
