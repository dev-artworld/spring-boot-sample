package com.equinix.appops.dart.portal.service;

import java.util.HashMap;
import java.util.List;

import com.equinix.appops.dart.portal.entity.EmailAudit;
import com.equinix.appops.dart.portal.model.EmailAuditModel;
import com.equinix.appops.dart.portal.model.NotificationContext;

public interface EmailSenderService {
	
	void publishEmail (NotificationContext notifycontext);

	void sendAlert(String recipients, String mailTemplateKey, HashMap<String, String> dataMap);
	
	List<EmailAuditModel> allSentAlert();

	List<EmailAudit> getRecentFailureEmail(Long responseTime, Long retryCount);

	Integer saveAuditEmail(EmailAudit emailAudit);

	EmailAudit getEmailAudit(Integer rowId);

}
