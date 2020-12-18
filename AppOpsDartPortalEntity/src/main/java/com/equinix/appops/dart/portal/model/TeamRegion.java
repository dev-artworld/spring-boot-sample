package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "team", "region" })
public class TeamRegion implements Serializable {
	private final static long serialVersionUID = 4292788019201015789L;

	@JsonProperty("team")
	private List<String> team = new ArrayList<>();
	@JsonProperty("region")
	private List<String> region = new ArrayList<>();
	

	@JsonProperty("team")
	public List<String> getTeam() {
		return team;
	}

	@JsonProperty("team")
	public void setTeam(List<String> team) {
		this.team = team;
	}

	@JsonProperty("region")
	public List<String> getRegion() {
		return region;
	}

	@JsonProperty("region")
	public void setRegion(List<String> region) {
		this.region = region;
	}

}