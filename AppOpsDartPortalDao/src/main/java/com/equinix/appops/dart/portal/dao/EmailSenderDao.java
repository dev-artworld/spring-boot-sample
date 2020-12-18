package com.equinix.appops.dart.portal.dao;

import java.util.List;

import com.equinix.appops.dart.portal.entity.EmailAudit;

public interface EmailSenderDao {
	
	Integer saveAuditEmail(EmailAudit emailAudit);
	
	List<EmailAudit> getAllAuditEmail();

	List<EmailAudit> getRecentFailureEmail(Long responseTime, Long retryCount);

	EmailAudit getEmailAudit(Integer rowId);

}
