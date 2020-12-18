package com.equinix.appops.dart.portal.model.search.filter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "region",
    "country",
    "ibx"
})
public class SearchFormFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("region")
	private String region;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("ibx")
	private String ibx;

	@JsonProperty("region")
	public String getRegion() {
		return region;
	}
	@JsonProperty("region")
	public void setRegion(String region) {
		this.region = region;
	}
	@JsonProperty("country")
	public String getCountry() {
		return country;
	}
	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}
	@JsonProperty("ibx")
	public String getIbx() {
		return ibx;
	}
	@JsonProperty("ibx")
	public void setIbx(String ibx) {
		this.ibx = ibx;
	}
	
}
