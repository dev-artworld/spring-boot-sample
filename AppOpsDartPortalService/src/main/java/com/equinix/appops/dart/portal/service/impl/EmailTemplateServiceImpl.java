package com.equinix.appops.dart.portal.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.service.EmailTemplateService;
import com.equinix.appops.dart.portal.service.XmlConfigService;

@Service("emailTemplteService")
@Scope("singleton")
public class EmailTemplateServiceImpl implements EmailTemplateService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);

	@Autowired
	private XmlConfigService xmlConfigService;

	public XmlConfigService getXmlConfigService() {
		return xmlConfigService;
	}

	public void setXmlConfigService(XmlConfigService xmlConfigService) {
		this.xmlConfigService = xmlConfigService;
	}

	@Override
	public String getEmailTemplate(String xmlName) {		
		return xmlConfigService.getEmailTemplate(xmlName);
	}

}
