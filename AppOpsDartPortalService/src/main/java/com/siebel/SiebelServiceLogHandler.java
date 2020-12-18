package com.siebel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
import com.equinix.appops.dart.portal.util.SpringContext;

public class SiebelServiceLogHandler implements SOAPHandler<SOAPMessageContext> {
	Logger logger = LoggerFactory.getLogger(SiebelServiceLogHandler.class);
	
	//@Autowired
	AppOpsInitiateDFRService appOpsInitiateDFRService;
	
	@Override
	public Set<QName> getHeaders() {
		//System.out.println("------header----------");
		return null;
	}

	@Override
	public void close(MessageContext arg0) {
		System.out.println("------close----------");
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		SOAPMessage message= arg0.getMessage();
		try {
			saveAuditResponse(arg0,LogicConstants.SYNCERROR);
		} catch (SOAPException e) {
			logger.error("SOAPException: ",e);
		} catch (IOException e) {
			logger.error("IOException: ",e);
		}
		//System.out.println(message.getSOAPPart().getAttributes());
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext arg0) {
		SOAPMessage message= arg0.getMessage();
		//TODO: see messageId is available. than set that into audit object. get retry count. rest of thing update.
		boolean isOutboundMessage=  (Boolean)arg0.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if(isOutboundMessage){
			// message.writeTo(System.out);
			try {
				saveAuditRequest(arg0);
			}catch (Exception e) {
				logger.error("SOAPException: ",e);
				return false;
			}

		}else{
			try {
				saveAuditResponse(arg0,LogicConstants.SYNCINPROGRESS);
			}catch (Exception e) {
				logger.error("SOAPException: ",e);
				return false;
			}
		}
		return true;	
	}

	private void saveAuditRequest(SOAPMessageContext arg0) throws SOAPException, IOException {
		if(appOpsInitiateDFRService==null){
			appOpsInitiateDFRService = SpringContext.getBean(AppOpsInitiateDFRService.class);
		}
		SOAPMessage message= arg0.getMessage();
		String requestId = UUID.randomUUID().toString();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		message.writeTo(out);
		  DartSoapAudit dartSoapAudit = new DartSoapAudit();
		  dartSoapAudit.setRequestId(requestId);
		  dartSoapAudit.setRequestTime(new Timestamp(new Date().getTime()));
		  dartSoapAudit.setRequest(out.toByteArray());
		  dartSoapAudit.setProduct("SBL");
		  SOAPBody body = message.getSOAPBody();
		  String dfrId = null;
		  try{
			  dfrId = body.getElementsByTagName("ns2:EQXDFRID").item(0).getTextContent();
			  if(null!=body.getElementsByTagName("MessageId").item(0)){
				  requestId=body.getElementsByTagName("MessageId").item(0).getTextContent();
				  DartSoapAudit prevDartSoapAudit= appOpsInitiateDFRService.getDFRAuditByReqId(requestId);				 
				  prevDartSoapAudit.setRequestTime(new Timestamp(new Date().getTime()));
				  prevDartSoapAudit.setRequest(out.toByteArray());
				  arg0.put("REQUEST_OBJ",prevDartSoapAudit);
				  appOpsInitiateDFRService.saveSoapAudit(prevDartSoapAudit);
			  }else {
				  dartSoapAudit.setDfrId(dfrId);
				  arg0.put("REQUEST_OBJ",dartSoapAudit);
				  appOpsInitiateDFRService.saveSoapAudit(dartSoapAudit);
			  }
		  }catch (Exception e) {
			logger.info("DFR ID getting null");
		  }
		  
	}

	private void saveAuditResponse(SOAPMessageContext arg0,String fault)
			throws SOAPException, IOException {
		if(appOpsInitiateDFRService==null){
			appOpsInitiateDFRService = SpringContext.getBean(AppOpsInitiateDFRService.class);
		}
		SOAPMessage message= arg0.getMessage();
		DartSoapAudit dartSoapAudit =  (DartSoapAudit) arg0.get("REQUEST_OBJ");
		if(dartSoapAudit!=null){
			dartSoapAudit.setResponseTime(new Timestamp(new Date().getTime()));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			dartSoapAudit.setResponse(out.toByteArray()); 
			dartSoapAudit.setFault(fault);
			appOpsInitiateDFRService.saveSoapAudit(dartSoapAudit);
		}
		else{
			logger.info("Soap Audit Request Object from message context getting null.");
		}
	}
	
	
	
}