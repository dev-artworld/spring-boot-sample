
package com.equinix.appops.dart.portal.mapper.dto.pro;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
	"dfrId",
	"priority",
	"status",
	"createdBy",
	"createdTeam",
	"createdDt",
	"assignedTo",
	"assignedTeam",
	"assignedDt",
	"isMsDfr",
	"ibx"
})
public class DfrMasterHomeVO  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("dfrId")
	private String dfrId;
	
	@JsonProperty("priority")
	private String priority;

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("createdBy")
	private String createdBy;
	
	@JsonProperty("createdTeam")
	private String createdTeam;	
	
	@JsonProperty("createdDt")
	private Date createdDt;	
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("assignedTeam")
	private String assignedTeam;
	
	@JsonProperty("assignedDt")
	private Date assignedDt;
	
	@JsonProperty("isMsDfr")
	private String isMsDfr;
	
	@JsonProperty("ibx")
	private String ibx;
	
	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTeam() {
		return createdTeam;
	}

	public void setCreatedTeam(String createdTeam) {
		this.createdTeam = createdTeam;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}


	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAssignedTeam() {
		return assignedTeam;
	}

	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}
	
	public Date getAssignedDt() {
		return assignedDt;
	}

	public void setAssignedDt(Date assignedDt) {
		this.assignedDt = assignedDt;
	}

	public String getIbx() {
		return ibx;
	}

	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

	public String getIsMsDfr() {
		return isMsDfr;
	}

	public void setIsMsDfr(String isMsDfr) {
		this.isMsDfr = isMsDfr;
	}
}
