package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"team",
	"region",
	"ibx",
	"fromDate",
	"toDate"
})
public class ReportFilter implements Serializable {
	
	@JsonProperty("region")
	private String region;
	
	@JsonProperty("team")
	private String team;
	
	@JsonProperty("ibx")
	private String ibx;
	
	@JsonProperty("fromDate")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date fromDate;
	
	@JsonProperty("toDate")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date toDate;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getIbx() {
		return ibx;
	}

	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
