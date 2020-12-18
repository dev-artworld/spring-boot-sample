package com.equinix.appops.dart.portal.ms.vo;

import java.io.Serializable;
import java.util.HashMap;

public class MsContractOutboundVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String,String> headers = new HashMap<String,String>();
	private HashMap<String,HashMap<String,String>> attrs = new HashMap<String,HashMap<String,String>>();
	public HashMap<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}
	public HashMap<String, HashMap<String, String>> getAttrs() {
		return attrs;
	}
	public void setAttrs(HashMap<String, HashMap<String, String>> attrs) {
		this.attrs = attrs;
	}
	
}
