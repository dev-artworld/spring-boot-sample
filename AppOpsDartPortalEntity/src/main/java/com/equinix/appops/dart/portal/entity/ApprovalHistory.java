package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the APPROVAL_HIST database table.
 * 
 */
@Entity
@Table(name="APPROVAL_HIST" , schema="EQX_DART")
@NamedQuery(name="ApprovalHistory.findAll", query="SELECT a FROM ApprovalHistory a")
public class ApprovalHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
    @SequenceGenerator(name = "seqGenerator", sequenceName="APPRSEQ",schema="EQX_DART", allocationSize = 1)
	private Long rowId;

	@Temporal(TemporalType.DATE)
	@Column(name="ASSIGNED_DT")
	private Date assignedDt;

	@Column(name="ASSIGN_TEAM")
	private String assignedTeam;
	
	@Column(name="DFR_ID")
	private String dfrId;

	@Column(name="PENDING_FOR")
	private String pendingFor;

	@Column(name="APP_SEQ")
	private int appSequence;
	
	@Column(name="PHY_AUDIT")
	private String physicalAudit;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DT")
	private Date createdDate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_TEAM")
	private String createdTeam;
	
	
	@Column(name="APPROVED_BY")
	private String approvedBy;
	
	@Column(name="NOTES")
	private String notes;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DFR_UPD_DATE")
	private Date dfrUpdateDate;
	
	
	
	public ApprovalHistory() {}


	public Long getRowId() {
		return rowId;
	}


	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}


	public Date getAssignedDt() {
		return assignedDt;
	}

	public void setAssignedDt(Date assignedDt) {
		this.assignedDt = assignedDt;
	}

	public String getAssignedTeam() {
		return assignedTeam;
	}

	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}

	public String getPendingFor() {
		return pendingFor;
	}

	public void setPendingFor(String pendingFor) {
		this.pendingFor = pendingFor;
	}

	public int getAppSequence() {
		return appSequence;
	}

	public void setAppSequence(int appSequence) {
		this.appSequence = appSequence;
	}

	public String getPhysicalAudit() {
		return physicalAudit;
	}

	public void setPhysicalAudit(String physicalAudit) {
		this.physicalAudit = physicalAudit;
	}

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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCreatedTeam() {
		return createdTeam;
	}


	public void setCreatedTeam(String createdTeam) {
		this.createdTeam = createdTeam;
	}
	
	
	public String getApprovedBy() {
		return approvedBy;
	}


	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public Date getDfrUpdateDate() {
		return dfrUpdateDate;
	}


	public void setDfrUpdateDate(Date dfrUpdateDate) {
		this.dfrUpdateDate = dfrUpdateDate;
	}
	
	
	

}