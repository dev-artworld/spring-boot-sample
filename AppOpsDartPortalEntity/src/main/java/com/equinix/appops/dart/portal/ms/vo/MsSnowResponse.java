package com.equinix.appops.dart.portal.ms.vo;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsSnowResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("result")
	private MsSnowResult result;
	@JsonProperty("error")
	private MsSnowError error;
	
	@JsonProperty("status")
	private String status;
	
	
	public MsSnowResult getResult() {
		return result;
	}
	public void setResult(MsSnowResult result) {
		this.result = result;
	}
	public MsSnowError getError() {
		return error;
	}
	public void setError(MsSnowError error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public   class MsSnowResult{
		@JsonProperty("output")
		private String output;
		@JsonProperty("Status")
		private String Status;
		@JsonProperty("Success IDs ")
		private ArrayList<String> successIds;	
		@JsonProperty("Failed IDs ")
		private ArrayList<String> failedIds;
		@JsonProperty("Failure Reason")
		private String failedReason;
		public String getOutput() {
			return output;
		}
		public void setOutput(String output) {
			this.output = output;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public ArrayList<String> getSuccessIds() {
			return successIds;
		}
		public void setSuccessIds(ArrayList<String> successIds) {
			this.successIds = successIds;
		}
		public ArrayList<String> getFailedIds() {
			return failedIds;
		}
		public void setFailedIds(ArrayList<String> failedIds) {
			this.failedIds = failedIds;
		}
		public String getFailedReason() {
			return failedReason;
		}
		public void setFailedReason(String failedReason) {
			this.failedReason = failedReason;
		}
		
		
	}
	
	public class MsSnowError{
		@JsonProperty("detail")
		private String detail;
		@JsonProperty("message")
		private String message;
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		

	}

}




