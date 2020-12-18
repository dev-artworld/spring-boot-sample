package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "EQX_DART.EMAIL_AUDIT")
@NamedQuery(name="EmailAudit.findAll", query="SELECT e FROM EmailAudit e")
public class EmailAudit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROW_ID")
	private Integer rowId;
	
	@Column(name = "EMAIL_FROM")
	private String fromAddress;
	
	@Column(name = "EMAIL_REPLY_TO")
	private String replyToAddress;
	
	@Column(name = "EMAIL_TO")
	private String toAddress;
	
	@Column(name = "EMAIL_CC")
	private String ccAddress;
	
	@Lob
	@Column(name = "EMAIL_CONTENT")
	private byte[] contentOfEmail;
	
	@Column(name = "EMAIL_SUBJECT")
	private String subjectOfEmail;
	
	@Column(name = "EMAIL_CONTENT_TYPE")
	private String contentTypeOfEmail;
	
	@Column(name = "EMAIL_SENT_DATE")
	private Timestamp sentDate;
	
	@Column(name = "EMAIL_BCC")
	private String bcc;
	
	@Lob
	@Column(name = "EMAIL_BODY")
	private  byte[] bodyoftheEmail;
	
	@Column(name = "EMAIL_IS_INLINE")
	private String sentAsInlineImg;
	
	@Column(name = "EMAIL_DISCLAIMER")
	private String disclaimer;
	
	@Column(name = "EMAIL_SENT_STATUS")
	private String emailSendStatus;
	
	@Column(name = "EMAIL_SENT_RES_MESSAGE")
	private String emailSendResMessage;
	
	
	@Column(name = "RETRY_COUNT")
	private Long retryCount;
	
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
	public byte[] getBodyoftheEmail() {
		return bodyoftheEmail;
	}
	public void setBodyoftheEmail(byte[] bodyoftheEmail) {
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
	public byte[] getContentOfEmail() {
		return contentOfEmail;
	}
	public void setContentOfEmail(byte[] contentOfEmail) {
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
	public String getEmailSendStatus() {
		return emailSendStatus;
	}
	public void setEmailSendStatus(String emailSendStatus) {
		this.emailSendStatus = emailSendStatus;
	}
	public String getEmailSendResMessage() {
		return emailSendResMessage;
	}
	public void setEmailSendResMessage(String emailSendResMessage) {
		this.emailSendResMessage = emailSendResMessage;
	}
	public Long getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(Long retryCount) {
		this.retryCount = retryCount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailAudit [rowId=");
		builder.append(rowId);
		builder.append(", fromAddress=");
		builder.append(fromAddress);
		builder.append(", replyToAddress=");
		builder.append(replyToAddress);
		builder.append(", toAddress=");
		builder.append(toAddress);
		builder.append(", ccAddress=");
		builder.append(ccAddress);
		builder.append(", contentOfEmail=");
		builder.append(Arrays.toString(contentOfEmail));
		builder.append(", subjectOfEmail=");
		builder.append(subjectOfEmail);
		builder.append(", contentTypeOfEmail=");
		builder.append(contentTypeOfEmail);
		builder.append(", sentDate=");
		builder.append(sentDate);
		builder.append(", bcc=");
		builder.append(bcc);
		builder.append(", bodyoftheEmail=");
		builder.append(Arrays.toString(bodyoftheEmail));
		builder.append(", sentAsInlineImg=");
		builder.append(sentAsInlineImg);
		builder.append(", disclaimer=");
		builder.append(disclaimer);
		builder.append(", emailSendStatus=");
		builder.append(emailSendStatus);
		builder.append(", emailSendResMessage=");
		builder.append(emailSendResMessage);
		builder.append(", retryCount=");
		builder.append(retryCount);
		builder.append("]");
		return builder.toString();
	}
	
	
}
