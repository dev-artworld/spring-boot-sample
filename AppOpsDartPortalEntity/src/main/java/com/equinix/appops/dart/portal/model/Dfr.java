
package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dfr_id",
    "status",
    "assigned_to",
    "assigned_team",
    "assigned_dt",
    "pending_since",
    "notes",
    "created_at",
    "createdBy",
    "ibx",
    "priority",
    "created_team",
    "assigned_team_userlist",
    "timestamp",
    "asgn_timestamp"
})
public class Dfr implements Serializable
{

    @JsonProperty("dfr_id")
    private String dfrId;
    
    @JsonProperty("assigned_to")
    private String assignedTo;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("assigned_team")
    private String assignedTeam;
    
    @JsonProperty("assigned_dt")
    private String assignedDt;
    
    @JsonProperty("pending_since")
    private String pendingSice;
    
    @JsonProperty("created_at")
    private String createdAt;
    

	@JsonProperty("notes")
    private String notes;
	
	@JsonProperty("createdBy")
	private String createdBy;
	
	@JsonProperty("ibx")
	private String ibx;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("created_team")
	private String created_team;
	
	@JsonProperty("assigned_team_userlist")
	private List<String> assigned_team_userlist;
	
	@JsonProperty("timestamp")
	private String timestamp;
	
	@JsonProperty("asgn_timestamp")
	private String asgnTimestamp;
	
    private final static long serialVersionUID = -1173569396080439192L;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/YYYY");


    @JsonProperty("created_at")
    public String getCreatedAt() {
		return createdAt;
	}

    
	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
		if(!createdAt.equalsIgnoreCase("NA")){
		Date date = new Date(createdAt);
		setTimestamp(String.valueOf(date.getTime()));
	}
	}
    
    @JsonProperty("dfr_id")
    public String getDfrId() {
        return dfrId;
    }

    @JsonProperty("dfr_id")
    public void setDfrId(String dfrId) {
        this.dfrId = dfrId;
    }

    @JsonProperty("assigned_to")
    public String getAssignedTo() {
        return assignedTo;
    }

    @JsonProperty("assigned_to")
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @JsonProperty("status")
    public String getStatus() {
		return status;
	}

    @JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("assigned_team")
    public String getAssignedTeam() {
		return assignedTeam;
	}

    @JsonProperty("assigned_team")
	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}


	@JsonProperty("pending_since")
    public String getPendingSice() {
        return pendingSice;
    }

    @JsonProperty("pending_sice")
    public void setPendingSice(String pendingSice) {
        this.pendingSice = pendingSice;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getIbx() {
		return ibx;
	}


	public void setIbx(String ibx) {
		this.ibx = ibx;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getCreated_team() {
		return created_team;
	}


	public void setCreated_team(String created_team) {
		this.created_team = created_team;
	}


	public List<String> getAssigned_team_userlist() {
		return assigned_team_userlist;
	}


	public void setAssigned_team_userlist(List<String> assigned_team_userlist) {
		this.assigned_team_userlist = assigned_team_userlist;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
    
	public String getAsgnTimestamp() {
		return asgnTimestamp;
	}


	public void setAsgnTimestamp(String asgnTimestamp) {
		this.asgnTimestamp = asgnTimestamp;
	}


	public String getAssignedDt() {
		return assignedDt;
	}


	public void setAssignedDt(String assignedDt) {
		this.assignedDt = assignedDt;
		if(!assignedDt.equalsIgnoreCase("NA")){
			Date date = new Date(assignedDt);
			setAsgnTimestamp(String.valueOf(date.getTime()));
		}
	}
}
