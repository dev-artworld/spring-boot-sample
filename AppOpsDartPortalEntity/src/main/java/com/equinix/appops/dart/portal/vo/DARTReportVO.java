package com.equinix.appops.dart.portal.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
	"dfrId",
	"createdBy",
	"createdDate",
	"createdTeam",
	"incident",
	"assignedTeam",
	"notes",
	"status",
	"assignedDate",
	"region",
	"ibx",
	"country"
})
public class DARTReportVO implements Serializable {
	
	@JsonProperty("dfrId")
	private String dfrId;
	
	@JsonProperty("createdBy")
	private String createdBy;
	
	@JsonProperty("createdDate")
	private Date createdDate;
	
	@JsonProperty("createdTeam")
	private String createdTeam;
	
	@JsonProperty("incident")
	private String incident;
	
	@JsonProperty("assignedTeam")
	private String assignedTeam;
	
	@JsonProperty("notes")
	private String notes;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("assignedDate")
	private Date assignedDate;
	
	@JsonProperty("region")
	private String region;
	
	@JsonProperty("ibx")
	private String ibx;
	
	@JsonProperty("country")
	private String country;

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTeam() {
		return createdTeam;
	}

	public void setCreatedTeam(String createdTeam) {
		this.createdTeam = createdTeam;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	public String getAssignedTeam() {
		return assignedTeam;
	}

	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}	
