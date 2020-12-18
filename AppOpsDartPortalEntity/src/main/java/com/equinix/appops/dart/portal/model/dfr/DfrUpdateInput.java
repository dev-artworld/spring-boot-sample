package com.equinix.appops.dart.portal.model.dfr;


import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dfrId",
    "assignedDt",
    "assignedTeam",
    "assignedTo",
    "createdBy",
    "createdDt",
    "createdTeam",
    "notes",
    "openOrderFlg",
    "openOrderNum",
    "overrideFlg",
    "pendingSince",
    "validStatus",
    "priority",
    "region",
    "status",
    "statusChangeDt"
})
public class DfrUpdateInput implements Serializable
{

    @JsonProperty("dfrId")
    private String dfrId;
    @JsonProperty("assignedDt")
    private String assignedDt;
    @JsonProperty("assignedTeam")
    private String assignedTeam;
    @JsonProperty("assignedTo")
    private String assignedTo;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdDt")
    private String createdDt;
    @JsonProperty("createdTeam")
    private String createdTeam;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("openOrderFlg")
    private String openOrderFlg;
    @JsonProperty("openOrderNum")
    private String openOrderNum;
    @JsonProperty("overrideFlg")
    private String overrideFlg;
    @JsonProperty("pendingSince")
    private String pendingSince;
    @JsonProperty("validStatus")
    private String validStatus;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("region")
    private String region;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusChangeDt")
    private String statusChangeDt;
    private final static long serialVersionUID = -5763078733457594653L;

    @JsonProperty("dfrId")
    public String getDfrId() {
        return dfrId;
    }

    @JsonProperty("dfrId")
    public void setDfrId(String dfrId) {
        this.dfrId = dfrId;
    }

    @JsonProperty("assignedDt")
    public String getAssignedDt() {
        return assignedDt;
    }

    @JsonProperty("assignedDt")
    public void setAssignedDt(String assignedDt) {
        this.assignedDt = assignedDt;
    }

    @JsonProperty("assignedTeam")
    public String getAssignedTeam() {
        return assignedTeam;
    }

    @JsonProperty("assignedTeam")
    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    @JsonProperty("assignedTo")
    public String getAssignedTo() {
        return assignedTo;
    }

    @JsonProperty("assignedTo")
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("createdDt")
    public String getCreatedDt() {
        return createdDt;
    }

    @JsonProperty("createdDt")
    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    @JsonProperty("createdTeam")
    public String getCreatedTeam() {
        return createdTeam;
    }

    @JsonProperty("createdTeam")
    public void setCreatedTeam(String createdTeam) {
        this.createdTeam = createdTeam;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("openOrderFlg")
    public String getOpenOrderFlg() {
        return openOrderFlg;
    }

    @JsonProperty("openOrderFlg")
    public void setOpenOrderFlg(String openOrderFlg) {
        this.openOrderFlg = openOrderFlg;
    }

    @JsonProperty("openOrderNum")
    public String getOpenOrderNum() {
        return openOrderNum;
    }

    @JsonProperty("openOrderNum")
    public void setOpenOrderNum(String openOrderNum) {
        this.openOrderNum = openOrderNum;
    }

    @JsonProperty("overrideFlg")
    public String getOverrideFlg() {
        return overrideFlg;
    }

    @JsonProperty("overrideFlg")
    public void setOverrideFlg(String overrideFlg) {
        this.overrideFlg = overrideFlg;
    }

    @JsonProperty("pendingSince")
    public String getPendingSince() {
        return pendingSince;
    }

    @JsonProperty("pendingSince")
    public void setPendingSince(String pendingSince) {
        this.pendingSince = pendingSince;
    }

    @JsonProperty("validStatus")
    public String getValidStatus() {
        return validStatus;
    }

    @JsonProperty("validStatus")
    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("statusChangeDt")
    public String getStatusChangeDt() {
        return statusChangeDt;
    }

    @JsonProperty("statusChangeDt")
    public void setStatusChangeDt(String statusChangeDt) {
        this.statusChangeDt = statusChangeDt;
    }

}
