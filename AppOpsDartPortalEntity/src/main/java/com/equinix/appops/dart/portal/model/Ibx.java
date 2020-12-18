package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "region", "ibx" })
public class Ibx implements Serializable {

	@JsonProperty("region")
	private String region;
	@JsonProperty("ibx")
	private List<String> ibx = new ArrayList<>();
	private final static long serialVersionUID = 8681801639543543535L;

	@JsonProperty("region")
	public String getRegion() {
		return region;
	}

	@JsonProperty("region")
	public void setRegion(String region) {
		this.region = region;
	}

	@JsonProperty("ibx")
	public List<String> getIbx() {
		return ibx;
	}

	@JsonProperty("ibx")
	public void setIbx(List<String> ibx) {
		this.ibx = ibx;
	}

}