package com.equinix.appops.dart.portal.mapper.dto.pro;

import java.io.Serializable;

public class CountryAndIbxVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String country;

	private String ibx;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIbx() {
		return ibx;
	}

	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

}
