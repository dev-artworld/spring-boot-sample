package com.equinix.appops.dart.portal.model;

public class AppOpsServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AppOpsServiceException(String message,Throwable cause){
		
		super(message,cause);
		this.exceptionMessage = message;
		this.exceptionCause = cause;
	}
	
	public AppOpsServiceException(String message,Throwable cause,String code){
		
		super(message,cause);
		this.exceptionMessage = message;
		this.exceptionCause = cause;
		this.exceptionCode = code;
	}
	
	public AppOpsServiceException(String message){
		
		super(message);
		this.exceptionMessage = message;
	}
	private String exceptionMessage;
	
	private Throwable exceptionCause;
	
	private String exceptionCode;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Throwable getExceptionCause() {
		return exceptionCause;
	}

	public void setExceptionCause(Throwable exceptionCause) {
		this.exceptionCause = exceptionCause;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
