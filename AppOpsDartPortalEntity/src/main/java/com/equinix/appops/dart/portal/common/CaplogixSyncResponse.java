package com.equinix.appops.dart.portal.common;

public class CaplogixSyncResponse {
	
	Boolean error;
	String message;
	String requestBodyPayLoad;
	
	public String getRequestBodyPayLoad() {
		return requestBodyPayLoad;
	}
	public void setRequestBodyPayLoad(String requestBodyPayLoad) {
		this.requestBodyPayLoad = requestBodyPayLoad;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
