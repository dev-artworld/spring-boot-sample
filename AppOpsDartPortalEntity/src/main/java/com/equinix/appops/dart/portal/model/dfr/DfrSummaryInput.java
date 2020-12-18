package com.equinix.appops.dart.portal.model.dfr;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"team",
	"region",
    "ibx",
    "status",
    "type"
})
public class DfrSummaryInput implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -364816698511455691L;

	@JsonProperty("team")
    private String team;
	
	@JsonProperty("region")
    private String region;
	
	@JsonProperty("ibx")
    private String ibx;
	
	@JsonProperty("status")
    private String status;
	
	@JsonProperty("type")
	private String type;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIbx() {
		return ibx;
	}

	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
