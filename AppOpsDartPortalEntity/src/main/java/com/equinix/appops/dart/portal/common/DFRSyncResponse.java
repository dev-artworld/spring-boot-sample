package com.equinix.appops.dart.portal.common;

import java.util.Date;

public class DFRSyncResponse {

	String responseId;
	String dfrId;
	boolean error;
	String request;
	String response;
	String status;
	Date requestDate;
	Date responseDate;
	
	public String getDfrId() {
		return dfrId;
	}
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DFRSyncResponse [responseId=");
		builder.append(responseId);
		builder.append(", error=");
		builder.append(error);
		builder.append(", request=");
		builder.append(request);
		builder.append(", response=");
		builder.append(response);
		builder.append(", status=");
		builder.append(status);
		builder.append(", requestDate=");
		builder.append(requestDate);
		builder.append(", responseDate=");
		builder.append(responseDate);
		builder.append("]");
		return builder.toString();
	}

	
}
