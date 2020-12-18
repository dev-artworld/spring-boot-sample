package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.List;

public class Reset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String dfrLineId;
	List<String> keys;
	public String getDfrLineId() {
		return dfrLineId;
	}
	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	
	
}
