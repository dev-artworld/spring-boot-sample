package com.equinix.appops.dart.portal.mapper.dto.pro;

import java.io.Serializable;

public class ErrorCardVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String totalErrorCount;
	private String fixedErrorCount;
	
	public String getTotalErrorCount() {
		return totalErrorCount;
	}
	public void setTotalErrorCount(String totalErrorCount) {
		this.totalErrorCount = totalErrorCount;
	}
	public String getFixedErrorCount() {
		return fixedErrorCount;
	}
	public void setFixedErrorCount(String fixedErrorCount) {
		this.fixedErrorCount = fixedErrorCount;
	}
}
