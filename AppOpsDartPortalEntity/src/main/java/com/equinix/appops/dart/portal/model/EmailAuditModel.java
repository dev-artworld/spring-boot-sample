package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.equinix.appops.dart.portal.entity.EmailAudit;


public class EmailAuditModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer rowId;
	
	private String fromAddress;
	
	private String replyToAddress;
	
	private String toAddress;
	
	private String ccAddress;
	
	private String contentOfEmail;
	
	private String subjectOfEmail;
	
	private String contentTypeOfEmail;
	
	private Timestamp sentDate;
	
	private String bcc;
	
	private  String bodyoftheEmail;
	
	private String sentAsInlineImg;
	
	private String disclaimer;
	
	
	
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getBodyoftheEmail() {
		return bodyoftheEmail;
	}
	public void setBodyoftheEmail(String bodyoftheEmail) {
		this.bodyoftheEmail = bodyoftheEmail;
	}
	public String getSentAsInlineImg() {
		return sentAsInlineImg;
	}
	public void setSentAsInlineImg(String sentAsInlineImg) {
		this.sentAsInlineImg = sentAsInlineImg;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getReplyToAddress() {
		return replyToAddress;
	}
	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	public String getContentOfEmail() {
		return contentOfEmail;
	}
	public void setContentOfEmail(String contentOfEmail) {
		this.contentOfEmail = contentOfEmail;
	}
	public String getSubjectOfEmail() {
		return subjectOfEmail;
	}
	public void setSubjectOfEmail(String subjectOfEmail) {
		this.subjectOfEmail = subjectOfEmail;
	}
	public String getContentTypeOfEmail() {
		return contentTypeOfEmail;
	}
	public void setContentTypeOfEmail(String contentTypeOfEmail) {
		this.contentTypeOfEmail = contentTypeOfEmail;
	}
	public Timestamp getSentDate() {
		return sentDate;
	}
	public void setSentDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}
	
	
	
	
}
