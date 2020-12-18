
package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "team",
    "region",
    "completed",
    "in-progress",
    "open",
    "physical-audit-Initiated"
})
public class DfrStats implements Serializable
{

    @JsonProperty("team")
    private String team;
    @JsonProperty("region")
    private String region;
    @JsonProperty("completed")
    private Completed completed;
    @JsonProperty("in-progress")
    private InProgress inProgress;
    @JsonProperty("open")
    private Open open;
    @JsonProperty("physical-audit-Initiated")
    private PhysicalAuditInitiated physicalAuditInitiated;
    private final static long serialVersionUID = -5492116138475877539L;

    @JsonProperty("team")
    public String getTeam() {
        return team;
    }

    @JsonProperty("team")
    public void setTeam(String team) {
        this.team = team;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("completed")
    public Completed getCompleted() {
        return completed;
    }

    @JsonProperty("completed")
    public void setCompleted(Completed completed) {
        this.completed = completed;
    }

    @JsonProperty("in-progress")
    public InProgress getInProgress() {
        return inProgress;
    }

    @JsonProperty("in-progress")
    public void setInProgress(InProgress inProgress) {
        this.inProgress = inProgress;
    }

    @JsonProperty("open")
    public Open getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(Open open) {
        this.open = open;
    }

    @JsonProperty("physical-audit-Initiated")
    public PhysicalAuditInitiated getPhysicalAuditInitiated() {
        return physicalAuditInitiated;
    }

    @JsonProperty("physical-audit-Initiated")
    public void setPhysicalAuditInitiated(PhysicalAuditInitiated physicalAuditInitiated) {
        this.physicalAuditInitiated = physicalAuditInitiated;
    }

}
