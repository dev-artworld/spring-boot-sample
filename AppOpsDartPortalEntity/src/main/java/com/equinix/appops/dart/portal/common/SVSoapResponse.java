package com.equinix.appops.dart.portal.common;

import com.intecbilling.tresoap._2_0.afs_inbound.ProcessOrderResponse;

public class SVSoapResponse {

	ProcessOrderResponse processOrderResponse;
	Boolean error;
	String message;
	public ProcessOrderResponse getProcessOrderResponse() {
		return processOrderResponse;
	}
	public void setProcessOrderResponse(ProcessOrderResponse processOrderResponse) {
		this.processOrderResponse = processOrderResponse;
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
