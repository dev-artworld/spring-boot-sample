package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.equinix.appops.dart.portal.common.DFRSyncResponse;


/**
 * The persistent class for the DART_SOAP_AUDIT database table.
 * 
 */
@Entity
@Table(name="DART_SOAP_AUDIT", schema="EQX_DART")
@NamedQuery(name="DartSoapAudit.findAll", query="SELECT d FROM DartSoapAudit d")
public class DartSoapAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REQUEST_ID")
	private String requestId;

	@Column(name="PRODUCT")
	private String product;

	@Column(name="REQUEST")
	private byte[] request;

	@Column(name="REQUEST_TIME")
	private Date requestTime;

	@Column(name="RESPONSE")
	private byte[] response;

	@Column(name="RESPONSE_TIME")
	private Date responseTime;
	
	@Column(name="FAULT")
	private String fault;
	
	@Column(name="DFR_ID")
	private String dfrId;

	@Column(name="RETRY_COUNT")
	private Long retryCount;
	
	public DartSoapAudit() {
		this.retryCount=0L;
	}

	public DartSoapAudit(DFRSyncResponse dfrResponse, String productName) {
		try{
			this.requestId=dfrResponse.getResponseId();
			this.product=productName;
			this.request=dfrResponse.getRequest().getBytes();
			this.response=dfrResponse.getResponse().getBytes();
			this.requestTime = dfrResponse.getRequestDate();
			this.responseTime = dfrResponse.getResponseDate();
			this.dfrId = dfrResponse.getDfrId();
			this.fault=dfrResponse.getStatus();
			this.retryCount=0L;
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public byte[] getRequest() {
		return this.request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}

	public Date getRequestTime() {
		return this.requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public byte[] getResponse() {
		return this.response;
	}

	public void setResponse(byte[] response) {
		this.response = response;
	}

	public Date getResponseTime() {
		return this.responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public Long getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Long retryCount) {
		this.retryCount = retryCount;
	}

	
}