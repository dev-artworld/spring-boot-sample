package com.equinix.appops.dart.portal.model;

import java.util.List;
import java.util.Map;

public class NotificationContext {

	Map<String,String> notifymap;
	List<String> audiencelist;
	String from;
	String templatekey;
	String subject;
	String disclaimer;
	String ccEmailIds;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public Map<String, String> getNotifymap() {
		return notifymap;
	}
	public void setNotifymap(Map<String, String> notifymap) {
		this.notifymap = notifymap;
	}
	public List<String> getAudiencelist() {
		return audiencelist;
	}
	public void setAudiencelist(List<String> audiencelist) {
		this.audiencelist = audiencelist;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTemplatekey() {
		return templatekey;
	}
	public void setTemplatekey(String templatekey) {
		this.templatekey = templatekey;
	}
	public String getCcEmailIds() {
		return ccEmailIds;
	}
	public void setCcEmailIds(String ccEmailIds) {
		this.ccEmailIds = ccEmailIds;
	}

}
