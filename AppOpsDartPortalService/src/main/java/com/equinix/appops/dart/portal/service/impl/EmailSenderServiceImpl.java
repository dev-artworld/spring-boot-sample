package com.equinix.appops.dart.portal.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.dao.EmailSenderDao;
import com.equinix.appops.dart.portal.entity.EmailAudit;
import com.equinix.appops.dart.portal.model.AppOpsServiceException;
import com.equinix.appops.dart.portal.model.EmailAuditModel;
import com.equinix.appops.dart.portal.model.EmailVO;
import com.equinix.appops.dart.portal.model.NotificationContext;
import com.equinix.appops.dart.portal.service.EmailSenderService;
import com.equinix.appops.dart.portal.service.EmailServiceQueueSender;

@Service("emailSenderService")
public class EmailSenderServiceImpl implements EmailSenderService {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(EmailSenderService.class);

	@Autowired
	private EmailTemplateServiceImpl emailTemplateService;

	
	public EmailTemplateServiceImpl getEmailTemplateService() {
		return emailTemplateService;
	}

	public void setEmailTemplateService(
			EmailTemplateServiceImpl emailTemplateService) {
		this.emailTemplateService = emailTemplateService;
	}
	
	@Autowired
	private EmailServiceQueueSender emailServiceQueueSender;
	
	@Autowired
	private EmailSenderDao emailSenderDao;

	
	public void publishEmail(NotificationContext notifycontext) {
		EmailVO emailVO = new EmailVO();
		try {
			
			log.info("Entring EmailSenderServiceImpl-->sendEmail:: ENTER");

			Map<String, String> notifymap = notifycontext.getNotifymap();
			
			if (null != notifycontext.getTemplatekey()) {
				log.debug("EmailSenderServiceImpl-->sendEmail: Template Key not null ,Retrieving template");
				String emailTemplate = emailTemplateService
						.getEmailTemplate(notifycontext.getTemplatekey());

				if (null != emailTemplate && !emailTemplate.isEmpty()) {
					log.debug("EmailSenderServiceImpl-->sendEmail: template not null");
					String emailXml=null;
					emailVO = populateEmailVO(emailTemplate, notifymap);					
					log.debug("EmailSenderServiceImpl-->sendEmail: Email VO populated");
					StringBuffer strbufer = new StringBuffer();
					for (String address : notifycontext.getAudiencelist()) {
						strbufer.append(address);
						strbufer.append(",");
					}

					log.debug("EmailSenderServiceImpl-->sendEmail: replacing audiences");
					String templist = strbufer.toString();
					String finallist = templist.substring(0,
							templist.length() - 1);
					emailVO.setToAddress(finallist);
					
					emailVO.setFromAddress(notifycontext.getFrom());
					if(notifymap.get("SENT_AS_INLINE_IMG") != null && "TRUE".equalsIgnoreCase(notifymap.get("SENT_AS_INLINE_IMG")) ) {
						emailVO.setSentAsInlineImg(true);
					}
					log.debug("EmailSenderServiceImpl-->sendEmail:: sending email");
					emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_PUSHED);
					emailVO.setEmailSendResponse(LogicConstants.EMAIL_MESSAGE_PUSHED);
					// Sent Email to Save for Audit.
					int rowId = sendEmailToSave(emailVO);
					emailVO.setRowId(rowId);
					
					emailServiceQueueSender.publishEmailServiceMessage(emailVO);
					
					
				} else {
					log.debug("email templating is Null...");
					emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_NOT_PUSHED);
					emailVO.setEmailSendResponse("There is an issue in loading Email Template! Please check logs for more detail!");
					int rowId = sendEmailToSave(emailVO);
					emailVO.setRowId(rowId);
					throw new AppOpsServiceException(
							"There is an issue in loading Email Template! Please check logs for more detail!");
				}

			} else {
				log.debug("Template key is received as Null...");
				emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_NOT_PUSHED);
				emailVO.setEmailSendResponse("There is an issue in loading Email Template! Please check logs for more detail!");
				int rowId = sendEmailToSave(emailVO);
				emailVO.setRowId(rowId);
				throw new AppOpsServiceException(
						"There is an issue in loading Email Template! Please check logs for more detail!");

			}
		} catch (Exception ex) {			
			log.error("There is an issue in populating Email Template", ex);
			emailVO.setEmailSendStatus(LogicConstants.EMAIL_MESSAGE_NOT_PUSHED);
			emailVO.setEmailSendResponse("There is an issue in loading Email Template! Please check logs for more detail!");
			int rowId = sendEmailToSave(emailVO);
			emailVO.setRowId(rowId);
			throw new AppOpsServiceException(
					"There is an issue in populating Email Template! Please check logs for more detail!");
		}

	}

	private EmailVO populateEmailVO(String xmlTemplate,
			Map<String, String> notifymap) throws TransformerException,
			ParserConfigurationException, SAXException, IOException {
		log.debug("EmailSenderServiceImpl-->populateEmailVO: ENTERED");
		try {
			String bodyContent = null;
			Set<String> keys = notifymap.keySet();
			EmailVO emailVO = new EmailVO();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream inputstream = new ByteArrayInputStream(
					xmlTemplate.getBytes());

			log.debug("EmailSenderServiceImpl-->populateEmailVO: BEFORE PARSING TEMPLATE");
			Document document = builder.parse(inputstream);
			NodeList nList = document.getElementsByTagName("EmailTemplate");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node node = nList.item(temp);
				NodeList nodelist = node.getChildNodes();
				for (int i = 0; i < nodelist.getLength(); i++) {
					node = nodelist.item(i);
					if ("content".equalsIgnoreCase(node.getNodeName())) {
						log.debug("EmailSenderServiceImpl-->populateEmailVO: starting replacement for CONTENT");
						bodyContent = node.getTextContent();
						if (null != keys) {
							for (Object object : keys) {

								if (bodyContent.indexOf("$#" + (String) object
										+ "#$") != -1) {

									bodyContent = bodyContent.replace("$#"
											+ (String) object + "#$",
											(String) notifymap.get(object));

									log.debug("EmailSenderServiceImpl-->populateEmailVO: replaced#"
											+ (String) object
											+ " with value#"
											+ (String) notifymap.get(object));
								}
							}
							emailVO.setBodyoftheEmail(bodyContent);
						}
						log.debug("EmailSenderServiceImpl-->populateEmailVO: CONTENT replaced");
					}
					if ("subject".equals(node.getNodeName())) {
						log.debug("EmailSenderServiceImpl-->populateEmailVO: starting replacement for SUBJECT");
						bodyContent = node.getTextContent();
						if (null != keys) {
							for (Object object : keys) {
								if (bodyContent.indexOf("$#" + (String) object
										+ "#$") != -1) {
									bodyContent = bodyContent.replace("$#"
											+ (String) object + "#$",
											(String) notifymap.get(object));

									log.debug("EmailSenderServiceImpl-->populateEmailVO: replaced#"
											+ (String) object
											+ " with value#"
											+ (String) notifymap.get(object));
								}
							}
							emailVO.setSubjectOfEmail(bodyContent);

						}
						log.debug("EmailSenderServiceImpl-->populateEmailVO: SUBJECT replaced");
					}
					if ("disclaimer".equalsIgnoreCase((node.getNodeName()))) {
						log.debug("EmailSenderServiceImpl-->populateEmailVO: starting replacement for DISCLAIMER");
						bodyContent = node.getTextContent();
						if (null != keys) {
							for (Object object : keys) {
								if (bodyContent.indexOf("$#" + (String) object
										+ "#$") != -1) {
									bodyContent = bodyContent.replace("$#"
											+ (String) object + "#$",
											(String) notifymap.get(object));

									log.debug("EmailSenderServiceImpl-->populateEmailVO: replaced#"
											+ (String) object
											+ " with value#"
											+ (String) notifymap.get(object));
								}
							}
							emailVO.setDisclaimer(bodyContent);

						}
						log.debug("EmailSenderServiceImpl-->populateEmailVO: DISCLAIMER replaced");
					}

				}

			}
			log.debug("EmailSenderServiceImpl-->populateEmailVO: returning EMAIL VO: END");
			return emailVO;
		} catch (Exception ex) {
			log.debug("There is an issue in mail template parsing!"
					+ ex.getMessage());
			log.error("There is an issue in mail template parsing!", ex);
			throw new AppOpsServiceException(
					"There is an issue in parsing email template! Please check logger for more detail!");
		}

	}
	
	@Override
	public void sendAlert(String recipients, String mailTemplateKey, HashMap<String, String> dataMap) {
		if(dataMap.get("STATUS").equalsIgnoreCase(LogicConstants.SYNCINPROGRESS)) {
			log.info("No Need to send email while DFR status into Sync In Progress"); //BDT-82 Change by Saurabh.
			return;
		}
		NotificationContext nc = new NotificationContext();
		nc.setFrom("dart@equinix.com");		
		List<String> recepientList = new ArrayList<String>();
		if(StringUtils.isBlank(recipients)){
		recepientList.add("Global-GTS-AMP-Admin@equinix.com");
		} else {
			for (String recEmail : recipients.split(",")) {
				recepientList.add(recEmail);
			}
		}
		nc.setAudiencelist(recepientList);
		nc.setNotifymap(dataMap);

		nc.setTemplatekey(mailTemplateKey);			
		publishEmail(nc);
		
	}
	
	private int sendEmailToSave(EmailVO emailVO) {
		
		//System.out.println("In Transforamtion");
		EmailAudit emailAudit = new EmailAudit();
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
		return emailSenderDao.saveAuditEmail(emailAudit);
		
		//System.out.println(emailAudit.toString());
	}

	@Override
	public List<EmailAuditModel> allSentAlert() {
		return convertToModel(emailSenderDao.getAllAuditEmail());
	}
	
	public List<EmailAuditModel> convertToModel(List<EmailAudit> alerts){
		List<EmailAuditModel> models =new ArrayList<>();
		for(EmailAudit alert : alerts){
			EmailAuditModel model = new EmailAuditModel();
			model.setRowId(alert.getRowId());
			model.setFromAddress(alert.getFromAddress());
			model.setReplyToAddress(alert.getReplyToAddress());
			model.setToAddress(alert.getToAddress());
			model.setCcAddress(alert.getCcAddress());
			model.setContentOfEmail(alert.getContentOfEmail()==null?
					null:new String(alert.getContentOfEmail()));
			model.setSubjectOfEmail(alert.getSubjectOfEmail());
			model.setContentTypeOfEmail(alert.getContentTypeOfEmail());
			model.setSentDate(alert.getSentDate());
			model.setBcc(alert.getBcc());
			model.setBodyoftheEmail(alert.getBodyoftheEmail()==null?
					null:new String(alert.getBodyoftheEmail()));
			model.setSentAsInlineImg(alert.getSentAsInlineImg());
			model.setDisclaimer(alert.getDisclaimer());
			models.add(model);
		}
		return models;
	}

	@Override
	public List<EmailAudit> getRecentFailureEmail(Long responseTime, Long retryCount) {		
		return emailSenderDao.getRecentFailureEmail(responseTime,retryCount);
	}

	@Override
	public Integer saveAuditEmail(EmailAudit emailAudit) {
		return emailSenderDao.saveAuditEmail(emailAudit);
		
	}

	@Override
	public EmailAudit getEmailAudit(Integer rowId) {
		return emailSenderDao.getEmailAudit(rowId);
	}
	
	
	
	
	
}
