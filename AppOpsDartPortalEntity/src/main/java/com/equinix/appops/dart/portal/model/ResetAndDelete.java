package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.List;

public class ResetAndDelete implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Reset> resetData;
	public List<String> deleteData;
	public List<Reset> getResetData() {
		return resetData;
	}
	public void setResetData(List<Reset> resetData) {
		this.resetData = resetData;
	}
	public List<String> getDeleteData() {
		return deleteData;
	}
	public void setDeleteData(List<String> deleteData) {
		this.deleteData = deleteData;
	}
	
	
	
   
}
