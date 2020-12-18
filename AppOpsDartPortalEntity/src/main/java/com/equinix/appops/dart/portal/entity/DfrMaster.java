package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DFR_MASTER database table.
 * 
 */
@Entity
@Table(name="DFR_MASTER" , schema="EQX_DART")
@NamedQuery(name="DfrMaster.findAll", query="SELECT d FROM DfrMaster d")
public class DfrMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DFR_ID")
	private String dfrId;

	@Temporal(TemporalType.DATE)
	@Column(name="ASSIGNED_DT")
	private Date assignedDt;

	@Column(name="ASSIGNED_TEAM")
	private String assignedTeam;

	@Column(name="ASSIGNED_TO")
	private String assignedTo;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DT")
	private Date createdDt;

	@Column(name="CREATED_TEAM")
	private String createdTeam;

	private String notes;

	@Column(name="OPEN_ORDER_FLG")
	private String openOrderFlg;

	@Column(name="OPEN_ORDER_NUM")
	private String openOrderNum;

	@Column(name="OVERRIDE_FLG")
	private String overrideFlg;

	@Temporal(TemporalType.DATE)
	@Column(name="PENDING_SINCE")
	private Date pendingSince;
	
	@Column(name="VALID_STATUS")
	private String validStatus;

	@Column(name="PHY_AUDIT")
	private String physicalAudit;
	
	@Column(name="CLX_OVR")
	private String clxOverride;
	
	@Column(name="SBL_OVR")
	private String sblOverride;
	
	@Column(name="SV_OVR")
	private String svOverride;
		
	@Column(name="CLX_INT_STAT")
	private String clxIntStatus;
	
	@Column(name="SBL_INT_STAT")
	private String sblIntStatus;
	
	@Column(name="SV_INT_STAT")
	private String svIntStatus;
	
	@Column(name="INCIDENT")
	private String incident;
	
	@Column(name="SS_FIL")
	private String ssFilter;
	
	@Column(name="ASYNC_FLG")
	private String asyncVal;

	@Column(name="EMAIL_FLAG")
	private String emailFlag;

	
	private String priority;

	private String region;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name="STATUS_CHANGE_DT")
	private Date statusChangeDt;

	@Column(name="IS_MOBILE_DFR")
	private String isMobileDfr;

	@Column(name="IS_ACCOUNT_MOVE_DFR")
	private String isAccountMoveDfr;
	
	@Column(name="IS_NETWORK_DFR")
	private String isNetworkDfr;
	
	@Column(name = "IS_MS_DFR")
	private String isMsDfr;
	
	public DfrMaster() {
	}
	
	


	public String getIsMsDfr() {
		return isMsDfr;
	}



	public void setIsMsDfr(String isMsDfr) {
		this.isMsDfr = isMsDfr;
	}



	public String getSsFilter() {
		return ssFilter;
	}

	public void setSsFilter(String ssFilter) {
		this.ssFilter = ssFilter;
	}

	public String getAsyncVal() {
		return asyncVal;
	}

	public void setAsyncVal(String asyncVal) {
		this.asyncVal = asyncVal;
	}


	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}



	@Transient
	List<SnapshotClxAssetDa> clxSnapshot;
	@Transient
	List<SnapshotSiebelAssetDa> sblSnapshot;
	@Transient
	List<SnapshotSvAssetDa> svSnapshot;
	@Transient
	List<CxiErrorTbl> errorSnapshot;
	@Transient
	List<EqxClCabinet> destinationCabinateList;
	@Transient
	List<EqxClCage> destinationCageList;
	@Transient
	List<SAssetXa> destinationAssetList;
	
	@OneToMany(mappedBy="dfrId",fetch=FetchType.LAZY)
	private List<ApprovalHistory> approvalHistories;
	
	
	
	public List<ApprovalHistory> getApprovalHistories() {
		return approvalHistories;
	}

	public void setApprovalHistories(List<ApprovalHistory> approvalHistories) {
		this.approvalHistories = approvalHistories;
	}

	public List<SnapshotClxAssetDa> getClxSnapshot() {
		return clxSnapshot;
	}

	public void setClxSnapshot(List<SnapshotClxAssetDa> clxSnapshot) {
		this.clxSnapshot = clxSnapshot;
	}

	public List<SnapshotSiebelAssetDa> getSblSnapshot() {
		return sblSnapshot;
	}

	public void setSblSnapshot(List<SnapshotSiebelAssetDa> sblSnapshot) {
		this.sblSnapshot = sblSnapshot;
	}

	public List<SnapshotSvAssetDa> getSvSnapshot() {
		return svSnapshot;
	}

	public void setSvSnapshot(List<SnapshotSvAssetDa> svSnapshot) {
		this.svSnapshot = svSnapshot;
	}

	public List<CxiErrorTbl> getErrorSnapshot() {
		return errorSnapshot;
	}

	public void setErrorSnapshot(List<CxiErrorTbl> errorSnapshot) {
		this.errorSnapshot = errorSnapshot;
	}

	public List<EqxClCabinet> getDestinationCabinateList() {
		return destinationCabinateList;
	}

	public void setDestinationCabinateList(List<EqxClCabinet> destinationCabinateList) {
		this.destinationCabinateList = destinationCabinateList;
	}

	public List<EqxClCage> getDestinationCageList() {
		return destinationCageList;
	}

	public void setDestinationCageList(List<EqxClCage> destinationCageList) {
		this.destinationCageList = destinationCageList;
	}

	public List<SAssetXa> getDestinationAssetList() {
		return destinationAssetList;
	}

	public void setDestinationAssetList(List<SAssetXa> destinationAssetList) {
		this.destinationAssetList = destinationAssetList;
	}

	public String getDfrId() {
		return this.dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public Date getAssignedDt() {
		return this.assignedDt;
	}

	public void setAssignedDt(Date assignedDt) {
		this.assignedDt = assignedDt;
	}

	public String getAssignedTeam() {
		return this.assignedTeam;
	}

	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}

	public String getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getCreatedTeam() {
		return this.createdTeam;
	}

	public void setCreatedTeam(String createdTeam) {
		this.createdTeam = createdTeam;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOpenOrderFlg() {
		return this.openOrderFlg;
	}

	public void setOpenOrderFlg(String openOrderFlg) {
		this.openOrderFlg = openOrderFlg;
	}

	public String getOpenOrderNum() {
		return this.openOrderNum;
	}

	public void setOpenOrderNum(String openOrderNum) {
		this.openOrderNum = openOrderNum;
	}

	public String getOverrideFlg() {
		return this.overrideFlg;
	}

	public void setOverrideFlg(String overrideFlg) {
		this.overrideFlg = overrideFlg;
	}

	public Date getPendingSince() {
		return this.pendingSince;
	}

	public void setPendingSince(Date pendingSince) {
		this.pendingSince = pendingSince;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusChangeDt() {
		return this.statusChangeDt;
	}

	public void setStatusChangeDt(Date statusChangeDt) {
		this.statusChangeDt = statusChangeDt;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getPhysicalAudit() {
		return physicalAudit;
	}

	public void setPhysicalAudit(String physicalAudit) {
		this.physicalAudit = physicalAudit;
	}

	public String getClxOverride() {
		return clxOverride;
	}

	public void setClxOverride(String clxOverride) {
		this.clxOverride = clxOverride;
	}

	public String getSblOverride() {
		return sblOverride;
	}

	public void setSblOverride(String sblOverride) {
		this.sblOverride = sblOverride;
	}

	public String getSvOverride() {
		return svOverride;
	}

	public void setSvOverride(String svOverride) {
		this.svOverride = svOverride;
	}

	public String getClxIntStatus() {
		return StringUtils.isBlank(clxIntStatus)?"-":clxIntStatus;
	}

	public void setClxIntStatus(String clxIntStatus) {
		this.clxIntStatus = clxIntStatus;
	}

	public String getSblIntStatus() {
		return StringUtils.isBlank(sblIntStatus)?"-":sblIntStatus;
	}

	public void setSblIntStatus(String sblIntStatus) {
		this.sblIntStatus = sblIntStatus;
	}

	public String getSvIntStatus() {
		return StringUtils.isBlank(svIntStatus)?"-":svIntStatus;
	}

	public void setSvIntStatus(String svIntStatus) {
		this.svIntStatus = svIntStatus;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	
	

	public String getIsMobileDfr() {
		return isMobileDfr;
	}



	public void setIsMobileDfr(String isMobileDfr) {
		this.isMobileDfr = isMobileDfr;
	}



	public String getIsAccountMoveDfr() {
		return isAccountMoveDfr;
	}



	public void setIsAccountMoveDfr(String isAccountMoveDfr) {
		this.isAccountMoveDfr = isAccountMoveDfr;
	}

	public String getIsNetworkDfr() {
		return isNetworkDfr;
	}

	public void setIsNetworkDfr(String isNetworkDfr) {
		this.isNetworkDfr = isNetworkDfr;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DfrMaster [dfrId=");
		builder.append(dfrId);
		builder.append(", assignedDt=");
		builder.append(assignedDt);
		builder.append(", assignedTeam=");
		builder.append(assignedTeam);
		builder.append(", assignedTo=");
		builder.append(assignedTo);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDt=");
		builder.append(createdDt);
		builder.append(", createdTeam=");
		builder.append(createdTeam);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", openOrderFlg=");
		builder.append(openOrderFlg);
		builder.append(", openOrderNum=");
		builder.append(openOrderNum);
		builder.append(", overrideFlg=");
		builder.append(overrideFlg);
		builder.append(", pendingSince=");
		builder.append(pendingSince);
		builder.append(", validStatus=");
		builder.append(validStatus);
		builder.append(", physicalAudit=");
		builder.append(physicalAudit);
		builder.append(", clxOverride=");
		builder.append(clxOverride);
		builder.append(", sblOverride=");
		builder.append(sblOverride);
		builder.append(", svOverride=");
		builder.append(svOverride);
		builder.append(", clxIntStatus=");
		builder.append(clxIntStatus);
		builder.append(", sblIntStatus=");
		builder.append(sblIntStatus);
		builder.append(", svIntStatus=");
		builder.append(svIntStatus);
		builder.append(", incident=");
		builder.append(incident);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", region=");
		builder.append(region);
		builder.append(", status=");
		builder.append(status);
		builder.append(", statusChangeDt=");
		builder.append(statusChangeDt);
		builder.append(", clxSnapshot=");
		builder.append(clxSnapshot);
		builder.append(", sblSnapshot=");
		builder.append(sblSnapshot);
		builder.append(", svSnapshot=");
		builder.append(svSnapshot);
		builder.append(", errorSnapshot=");
		builder.append(errorSnapshot);
		builder.append(", destinationCabinateList=");
		builder.append(destinationCabinateList);
		builder.append(", destinationCageList=");
		builder.append(destinationCageList);
		builder.append(", destinationAssetList=");
		builder.append(destinationAssetList);
		builder.append(", approvalHistories=");
		builder.append(approvalHistories);
		builder.append(", isMobileDfr=");
		builder.append(isMobileDfr);
		builder.append(", isAccountMoveDfr=");
		builder.append(isAccountMoveDfr);
		builder.append("]");
		return builder.toString();
	}
	
	
}