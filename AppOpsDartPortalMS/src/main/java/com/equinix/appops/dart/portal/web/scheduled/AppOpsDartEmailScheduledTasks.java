package com.equinix.appops.dart.portal.web.scheduled;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.entity.EmailAudit;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.EmailVO;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.EmailSenderService;
import com.equinix.appops.dart.portal.service.KafkaSenderService;
/**
 * 
 * @author MM
 *
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name="spring.schedule.job.email.retryjob.status", havingValue="ON", matchIfMissing=true)
public class AppOpsDartEmailScheduledTasks{
	
	private static final Logger logger = LoggerFactory.getLogger(AppOpsDartEmailScheduledTasks.class);
	
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
 
    @Autowired
	ConfigService configService;
    
    @Autowired 
    EmailSenderService emailSenderService;
    
    @Autowired 
	KafkaSenderService kafkaSenderService;
    
    @Value("${spring.schedule.job.email.responsetime.delay}")
    private Long responseTime;

    @Value("${spring.schedule.job.email.retry.count}")
    private Long retryCount;
    
    @Scheduled(cron = "${spring.schedule.job.email.retryjob.cron}")
    public void scheduleTaskWithCronExpression() {
    	logger.info("Cron Task :Email Failure: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
     checkFailureEmailAndRetry();
    }

	private void checkFailureEmailAndRetry() {
		try{
			String retryFlag = configService.getValueByKey(LogicConstants.FAILURE_EMAIL_RETRY_INTEGRATION_FLAG);
			if("ON".equalsIgnoreCase(retryFlag)){
				List<EmailAudit> emailAudits = emailSenderService.getRecentFailureEmail(responseTime, retryCount);
				logger.info("Total unsucessfull email(s):"+((emailAudits==null||emailAudits.isEmpty())?0:emailAudits.size()));
				for (EmailAudit emailAudit : emailAudits) {
					try{
						logger.error("Email ID# "+emailAudit.getRowId()+" - "+emailAudit.getRetryCount());
						emailAudit.setRetryCount(emailAudit.getRetryCount()+1);
						EmailVO emailVO = new EmailVO(emailAudit);
						Integer id=sendEmailToSave(emailVO);
						logger.info("Email ID#"+emailAudit.getRowId()+" retry count:"+emailAudit.getRetryCount()+" updated id:"+id);
						if(id !=null){
							kafkaSenderService.pushEmail(emailVO);
						}
					}catch(Exception ex){
						logger.error("unable to send email "+emailAudit.getRowId(),ex);
					}
				}
			} else{
				logger.info("Unable to retry send email to retry flag:"+retryFlag);
			}
		}catch(Exception ex){
			logger.error("unable to get failure email's:",ex);
		}
		
	}

	private int sendEmailToSave(EmailVO emailVO) {

		//System.out.println("In Transforamtion");
		EmailAudit emailAudit = new EmailAudit();
		if(emailVO.getRowId() != null){
			emailAudit = emailSenderService.getEmailAudit(emailVO.getRowId());
			emailAudit.setEmailSendStatus(emailVO.getEmailSendStatus());
			emailAudit.setEmailSendResMessage(emailVO.getEmailSendResponse());
			emailAudit.setRetryCount(emailVO.getRetryCount());
			return emailSenderService.saveAuditEmail(emailAudit);
		} else{
			emailAudit.setFromAddress(emailVO.getFromAddress());
			emailAudit.setReplyToAddress(emailVO.getReplyToAddress());
			emailAudit.setToAddress(emailVO.getToAddress());
			emailAudit.setCcAddress(emailVO.getCcAddress());
			emailAudit.setContentOfEmail(emailVO.getContentOfEmail()==null?
					null:emailVO.getContentOfEmail().getBytes());
			emailAudit.setSubjectOfEmail(emailVO.getSubjectOfEmail());
			emailAudit.setContentTypeOfEmail(emailVO.getContentTypeOfEmail());
			emailAudit.setSentDate(new Timestamp(new Date().getTime()));
			emailAudit.setBcc(emailVO.getBCC());
			emailAudit.setBodyoftheEmail(emailVO.getBodyoftheEmail()==null?
					null:emailVO.getBodyoftheEmail().getBytes());
			emailAudit.setSentAsInlineImg(emailVO.isSentAsInlineImg()==null?"false":emailVO.isSentAsInlineImg().toString());
			emailAudit.setDisclaimer("");
			emailAudit.setEmailSendStatus(emailVO.getEmailSendStatus());
			emailAudit.setEmailSendResMessage(emailVO.getEmailSendResponse());
			emailAudit.setRetryCount(0L);
			return emailSenderService.saveAuditEmail(emailAudit);
		}
	}
}
