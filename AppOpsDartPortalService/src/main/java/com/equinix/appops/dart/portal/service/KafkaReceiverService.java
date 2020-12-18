package com.equinix.appops.dart.portal.service;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.buisness.AppOpsInitiateDfrBusiness;
import com.equinix.appops.dart.portal.common.DFRSyncResponse;
import com.equinix.appops.dart.portal.dao.EmailSenderDao;
import com.equinix.appops.dart.portal.entity.EmailAudit;
import com.equinix.appops.dart.portal.model.AppOpsServiceException;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.EmailAttachmentVO;
import com.equinix.appops.dart.portal.model.EmailVO;
import com.google.gson.Gson;

@Service
public class KafkaReceiverService {

	private final Logger log = LoggerFactory.getLogger(KafkaReceiverService.class);
	
	//@Autowired
	//EQXSpcUpdateSpcQuoteSpcFromSpcEchoSpcSign_Service soapService;

	@Autowired
	Gson gson;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	AppOpsInitiateDfrBusiness appOpsInitiateDFRBusiness;
	
	@Autowired
	SVSyncVService svService;
	
	@Autowired
	private EmailSenderDao emailSenderDao;

	@KafkaListener(topics = "${spring.kafka.topic.clx.sync}",containerFactory = "kafkaListenerContainerFactory")
	private synchronized void consumeCLXKafkaTopic(@Payload DFRKafkaMessageVO dfrMessageVO, Acknowledgment acknowledgment) {
		log.info("Received message from clx topic: {}", dfrMessageVO);
		try {
			synchronized (dfrMessageVO) {
				acknowledgment.acknowledge();
			DFRSyncResponse response = appOpsInitiateDFRBusiness.syncCLXDfrKafkaConsumer(dfrMessageVO);
			}
		}catch(Exception ex){
			log.error("error while reading clx topic:",ex);
		}
	}

	
	@KafkaListener(topics = "${spring.kafka.topic.sbl.sync}",containerFactory = "kafkaListenerContainerFactory")
	private synchronized void consumeSBLKafkaTopic(@Payload DFRKafkaMessageVO dfrMessageVO, Acknowledgment acknowledgment) {
		log.info("Received message from SBL Kafka topic: {}", dfrMessageVO);
		try {
			synchronized (dfrMessageVO) {
				acknowledgment.acknowledge();
			DFRSyncResponse response = appOpsInitiateDFRBusiness.syncSBLDfrKafkaConsumer(dfrMessageVO);
			}
		}catch(Exception ex){
			log.error("error while reading clx topic:",ex);
		}    
		
	}

	
	@KafkaListener(topics = "${spring.kafka.topic.sv.sync}",containerFactory = "kafkaListenerContainerFactory")
	private synchronized void consumeSVQueue(@Payload DFRKafkaMessageVO dfrMessageVO, Acknowledgment acknowledgment) {
		log.info("Received message from sv kafka topic: {}", dfrMessageVO);
		try {
			synchronized (dfrMessageVO) {
				acknowledgment.acknowledge();
			DFRSyncResponse response = appOpsInitiateDFRBusiness.syncSVDfrKafkaConsumer(dfrMessageVO);
			}
		}catch(Exception ex){
			log.error("error while reading clx topic:",ex);
		}
	}

	//@KafkaListener(topics = "${spring.kafka.topic}",errorHandler="kafkaErrorHandler",containerFactory = "kafkaListenerContainerFactory")
	@KafkaListener(topics = "${spring.kafka.topic}",containerFactory = "kafkaListenerContainerFactory")
	private synchronized void consumeKafkaQueue(@Payload String message) {
		log.info("Received message from kafka queue: {}", message);
		getMessageAndSave(message);      
		log.info("Number:"+message);
		//acknowledgment.acknowledge();
		//Note if the "acknowledgment" parameter is left out Kafka will be acknowledged as soon as the method finishes.
	}

	private void getMessageAndSave(String message) {
		/*EQXSpcUpdateSpcQuoteSpcFromSpcEchoSpcSign service = soapService.getEQXSpcUpdateSpcQuoteSpcFromSpcEchoSpcSign();
		QueryEchoInput queryEchoInput = new QueryEchoInput();
		ListOfEqxSendReceiveQuoteToEchoSign value = new ListOfEqxSendReceiveQuoteToEchoSign();
		Quote quote = new Quote();
		quote.setId(message);
		value.getQuote().add(quote );
		queryEchoInput.setListOfEqxSendReceiveQuoteToEchoSign(value );
		QueryEchoOutput response = service.queryEcho(queryEchoInput );
		quote = response.getListOfEqxSendReceiveQuoteToEchoSign().getQuote().get(0);        
		System.out.println(gson.toJson(quote));*/
	}
	
	@KafkaListener(topics = "${spring.kafka.topic.email}",containerFactory = "emailKafkaListenerContainerFactory")
	private void consumeKafkaQueue(@Payload EmailVO emailVO, Acknowledgment acknowledgment) {
		log.info("Received Email from kafka queue: {}", emailVO);

		log.info("Number:"+emailVO);		
		try{
			acknowledgment.acknowledge();
			emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_CONSUMED);
			emailVO.setEmailSendResponse(LogicConstants.EMAIL_MESSAGE_CONSUMED);
			sendEmailToSave(emailVO);
			sendEmail(emailVO);
			emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_DELIVERED);
			emailVO.setEmailSendResponse(LogicConstants.EMAIL_MESSAGE_DELIVERED);
			sendEmailToSave(emailVO);
			//save audit.
		}catch(Exception ex){ex.printStackTrace();
			emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_UNDELIVERED);
			String reason = "";
			if(StringUtils.isNotBlank(ex.getMessage())){
				reason=ex.getMessage();
				if(reason.length() > 1024){
					reason = reason.substring(0, 1024);
				}
			} else {
				reason=LogicConstants.EMAIL_MESSAGE_DELIVERED;
			}
			emailVO.setEmailSendResponse(reason);
			sendEmailToSave(emailVO);
		}
		
		//acknowledgment.acknowledge();
		//Note if the "acknowledgment" parameter is left out Kafka will be acknowledged as soon as the method finishes.
	}

	private void sendEmail(EmailVO emailVO) {

		try {

			log.debug("===EmailServiceImpl===sendEmail  method::START");
			
			String emailSwitchOn = configService.getValueByKey(LogicConstants.SEND_EMAIL_NOTIFICATION);

			log.debug("===EmailServiceImpl===sendEmail sync method:: EMAIL SWITCH - "
					+ emailSwitchOn);

			if (LogicConstants.ON_VALUE.equalsIgnoreCase(emailSwitchOn)) {
				log.debug("===EmailServiceImpl===sendEmail sync method:: EMAIL Switch is ON, starting sending email");
				
				String mailServerHeader = configService.getValueByKey(LogicConstants.MAIL_SERVER_HOST_HEADER);
				String mailServerVal = configService.getValueByKey(LogicConstants.MAIL_SERVER_HOST_VALUE);
				String mailServerPortHeader =configService.getValueByKey(LogicConstants.MAIL_SERVER_PORT_HEADER);
				String mailServerPortVal = configService.getValueByKey(LogicConstants.MAIL_SERVER_PORT_VALUE);
				String mailServerAuthHeader = configService.getValueByKey(LogicConstants.MAIL_SERVER_AUTH_HEADER);
				String mailServerSmtpProtocolHeader = configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_PROTOCOL_HEADER);
				String mailServerSslTrustHeader = configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_SSL_TRUST_HEADER);
				String mailServerSslTrustIdentity = configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_SSL_IDENTITY_HEADER);
				String mailServerSmtpTlsHeader = configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_TLS_HEADER);
				String mailServerAuthValue= configService.getValueByKey(LogicConstants.MAIL_SERVER_AUTH_VALUE);
				String mailServerSmtpTlsValue= configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_TLS_VALUE);
				String mailServerSmtpProtocolValue=configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_PROTOCOL_VLAUE);
				String mailServerDebugHeader= configService.getValueByKey(LogicConstants.MAIL_SERVER_DEBUG_HEADER);
				String mailServerDebugValue= configService.getValueByKey(LogicConstants.MAIL_SERVER_DEBUG_VALUE);
				String mailServerSmtpSslTrustValue=configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_SSL_TRUST_VALUE);
				String mailServerSslTrustIdentityValue= configService.getValueByKey(LogicConstants.MAIL_SERVER_SMTP_SSL_IDENTITY_VALUE);
				String mailServerEmailToken= configService.getValueByKey(LogicConstants.MAIL_SERVER_EMAIL_TOKENS);
				String logoPath= configService.getValueByKey(LogicConstants.LOGO_PATH);
				String whiteLogoPath= configService.getValueByKey(LogicConstants.LOGO_PATH_WHITE);
				final String mailServerUsername =configService.getValueByKey(LogicConstants.MAIL_SERVER_USERNAME) ;
				final String mailServerPassword = configService.getValueByKey(LogicConstants.MAIL_SERVER_PASSWORD) ;
				
				Properties emailProps = System.getProperties();
				emailProps.setProperty(mailServerHeader, mailServerVal);
				emailProps.setProperty(mailServerPortHeader,mailServerPortVal);
				emailProps.setProperty(mailServerAuthHeader, mailServerAuthValue);
				emailProps.setProperty(mailServerSmtpTlsHeader, mailServerSmtpTlsValue);
				emailProps.setProperty(mailServerSmtpProtocolHeader, mailServerSmtpProtocolValue);
				emailProps.setProperty(mailServerDebugHeader, mailServerDebugValue);
				emailProps.setProperty(mailServerSslTrustHeader, mailServerSmtpSslTrustValue);
				emailProps.setProperty(mailServerSslTrustIdentity, mailServerSslTrustIdentityValue);	
				
				Authenticator auth = null;
				if(LogicConstants.MAIL_AUTH_TRUE.equalsIgnoreCase(mailServerAuthValue)){
					auth = new Authenticator() {
						public PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									mailServerUsername, mailServerPassword);
						}
					};
				}
				Session emailSession = Session.getInstance(emailProps,auth);
				
				MimeMessage message = new MimeMessage(emailSession);
				message.setFrom(new InternetAddress(emailVO.getFromAddress()));
				StringTokenizer emailTokens = new StringTokenizer(emailVO.getToAddress(), mailServerEmailToken);
				int maxAddress = emailTokens.countTokens();
				log.debug("===EmailServiceImpl===sendEmail sync method:: NUMBER OF RECIPIENTS::"
						+ maxAddress);

				int i = 0;

				InternetAddress[] recipients = new InternetAddress[maxAddress];

				while (emailTokens.hasMoreTokens()) {
					String newRecipient = emailTokens.nextToken();

					if (newRecipient != null && !"".equals(newRecipient)) {
						recipients[i] = new InternetAddress(newRecipient);
						i++;
					}
				}

				message.addRecipients(javax.mail.Message.RecipientType.TO,recipients);


				message.setSentDate(new Date());
				message.setSubject(emailVO.getSubjectOfEmail());
				MimeMultipart content = new MimeMultipart("related");
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText(emailVO.getBodyoftheEmail()+emailVO.getDisclaimer(),"utf-8","html");
				content.addBodyPart(textPart);
				MimeBodyPart imagePart = new MimeBodyPart();
				
				imagePart.attachFile(logoPath);
				
				imagePart.setContentID("<equinixlogo>");

				imagePart.setDisposition(MimeBodyPart.INLINE);

				content.addBodyPart(imagePart);
				message.setContent(content);
				
				//TODO: add by Saurabh to send attachment.
				EmailAttachmentVO attachment = emailVO.getAttachment();
				if(attachment != null ){
					MimeBodyPart  logFileAttachment= new MimeBodyPart();
					File logFile = new File(attachment.getFileName());
					FileUtils.copyInputStreamToFile(new ByteArrayInputStream(attachment.getFileContent()), logFile);
					logFileAttachment.attachFile(logFile);
					content.addBodyPart(logFileAttachment);
				}
				

				log.debug("===EmailServiceImpl===sendEmail sync method:: BEFORE EMAIL SENDING");
				try{
				Transport.send(message);
				}catch(Exception ex){
					log.error("Retrying to transport the email:",ex);
					Thread.sleep(10*1000);
					Transport.send(message);
				}

				log.debug("===EmailServiceImpl===sendEmail sync method:: COMPLETED EMAIL SENDING");
			} else {

				log.debug("===EmailServiceImpl===sendEmail sync method:: EMAIL SWITCH - "
						+ emailSwitchOn);
				log.debug("===EmailServiceImpl===sendEmail sync method:: EMAIL SWITCH is OFF , HENCE EMAIL NOT SENT");
			}
		
		} catch (AddressException e) {
			log.error("EMAIL Address not correct", e);
			throw new AppOpsServiceException("UNable to send email::", e);
		} catch (MessagingException e) {
			log.error("UNable to send email::", e);
			throw new AppOpsServiceException("UNable to send email::", e);
		} catch (Exception ex) {
			log.error("Some problem during sending email from appops", ex);
			throw new AppOpsServiceException("UNable to send email::", ex);
		}
		
	}

	private void sendEmailToSave(EmailVO emailVO) {
		//System.out.println("In Transforamtion");
		EmailAudit emailAudit = new EmailAudit();
		if(emailVO.getRowId()!=null){
			emailAudit = emailSenderDao.getEmailAudit(emailVO.getRowId());
			emailAudit.setEmailSendStatus(emailVO.getEmailSendStatus());
			emailAudit.setEmailSendResMessage(emailVO.getEmailSendResponse());
			emailSenderDao.saveAuditEmail(emailAudit);
		} else {
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
			emailSenderDao.saveAuditEmail(emailAudit);
		}
	}
}
