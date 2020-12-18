package com.equinix.appops.dart.portal.mapper.dto.pro;

import java.io.Serializable;

public class DfrLineIdsVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String dfrLineId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDfrLineId() {
		return dfrLineId;
	}
	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}

}
