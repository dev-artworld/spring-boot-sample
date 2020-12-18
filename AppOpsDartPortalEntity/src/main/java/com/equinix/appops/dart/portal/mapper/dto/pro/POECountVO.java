package com.equinix.appops.dart.portal.mapper.dto.pro;

import java.io.Serializable;

public class POECountVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String count;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "POECountVO [name=" + name + ", count=" + count + "]";
	}

}
