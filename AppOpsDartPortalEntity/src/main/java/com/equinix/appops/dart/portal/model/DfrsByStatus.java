
package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"team",
	"region",
    "status",
    "ibx",
    "dfrs",
    "dfrIdSet",
    "assignedTeamSet",
    "statusSet",
    "createdBySet",
    "createdAtSet",
    "countMap"
})
public class DfrsByStatus implements Serializable
{
	
	@JsonProperty("team")
    private String team;
	@JsonProperty("region")
    private String region;
	
	@JsonProperty("ibx")
    private String ibx;
	
	

	@JsonProperty("ibx")
    public String getIbx() {
		return ibx;
	}

	@JsonProperty("ibx")
	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

	@JsonProperty("status")
    private String status;
    @JsonProperty("dfrs")
    private List<Dfr> dfrs = new ArrayList<>();
    private final static long serialVersionUID = -8694280703742596639L;
    
    @JsonProperty("dfrIdSet")
    private Set<String> dfrIdSet;
    
    @JsonProperty("assignedTeamSet")
    private Set<String> assignedTeamSet;
    
    @JsonProperty("statusSet")
    private Set<String> statusSet;
    
    @JsonProperty("createdBySet")
    private Set<String> createdBySet;
    
    @JsonProperty("createdAtSet")
    private Set<String> createdAtSet;
    
    @JsonProperty("countMap")
    private HashMap<String,String> countMap;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("dfrs")
    public List<Dfr> getDfrs() {
        return dfrs;
    }

    @JsonProperty("dfrs")
    public void setDfrs(List<Dfr> dfrs) {
        this.dfrs = dfrs;
    }

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

	public Set<String> getDfrIdSet() {
		return dfrIdSet;
	}

	public void setDfrIdSet(Set<String> dfrIdSet) {
		this.dfrIdSet = dfrIdSet;
	}

	public Set<String> getAssignedTeamSet() {
		return assignedTeamSet;
	}

	public void setAssignedTeamSet(Set<String> assignedTeamSet) {
		this.assignedTeamSet = assignedTeamSet;
	}

	public Set<String> getStatusSet() {
		return statusSet;
	}

	public void setStatusSet(Set<String> statusSet) {
		this.statusSet = statusSet;
	}

	public Set<String> getCreatedBySet() {
		return createdBySet;
	}

	public void setCreatedBySet(Set<String> createdBySet) {
		this.createdBySet = createdBySet;
	}

	public Set<String> getCreatedAtSet() {
		return createdAtSet;
	}

	public void setCreatedAtSet(Set<String> createdAtSet) {
		this.createdAtSet = createdAtSet;
	}
	
	public HashMap<String,String> getCountMap() {
		return countMap;
	}

	public void setCountMap(HashMap<String,String> countMap) {
		this.countMap = countMap;
	}
}
