package com.equinix.appops.dart.portal.model;


import java.io.Serializable;
import java.util.HashMap;

public class AppOpsContainerAttr implements Serializable{
	
	private String methodName;
	private String methodBeanName;
	private HashMap<String, Object> methodParams;

	public HashMap<String, Object> getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(HashMap<String, Object> methodParams) {
		this.methodParams = methodParams;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodBeanName() {
		return methodBeanName;
	}

	public void setMethodBeanName(String methodBeanName) {
		this.methodBeanName = methodBeanName;
	}
}
