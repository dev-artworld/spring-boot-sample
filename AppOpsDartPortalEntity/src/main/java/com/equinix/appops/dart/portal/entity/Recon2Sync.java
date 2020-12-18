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
 * The persistent class for the RECON2_SYNC database table.
 * 
 */
@Entity
@Table(name="RECON2_SYNC", schema="EQX_DART")
@NamedQuery(name="Recon2Sync.findAll", query="SELECT r FROM Recon2Sync r")
public class Recon2Sync implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ASSET_ID")
	private String assetId;

	@Column(name="ERROR_REASON")
	private String errorReason;

	@Column(name="INCIDENT_NO")
	private String incidentNo;

	private String region;

	@Column(name="REQUESTED_BY")
	private String requestedBy;

	@Id
	@Column(name="SEQ_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdGenerator")
    @SequenceGenerator(name = "seqIdGenerator", sequenceName="SV_INT_SEQ",schema="EQX_DART", allocationSize = 1)
	private Long seqId;

	@Temporal(TemporalType.DATE)
	@Column(name="SIEBEL_REQ_DATE")
	private Date siebelReqDate;

	@Column(name="SV_SYNC")
	private String svSync;

	@Temporal(TemporalType.DATE)
	@Column(name="SV_SYNC_DATE")
	private Date svSyncDate;

	public Recon2Sync() {
	}

	public Recon2Sync(SvSyncV svSyncV) {
		this.assetId=svSyncV.getAssetId();	
		this.requestedBy=svSyncV.getRequestedBy();
		this.siebelReqDate= svSyncV.getSiebelReqDate();
		this.incidentNo=svSyncV.getIncidentNo();
		this.region=svSyncV.getRegion();
		this.seqId=null;
	}
	

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getErrorReason() {
		return this.errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getIncidentNo() {
		return this.incidentNo;
	}

	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRequestedBy() {
		return this.requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Date getSiebelReqDate() {
		return this.siebelReqDate;
	}

	public void setSiebelReqDate(Date siebelReqDate) {
		this.siebelReqDate = siebelReqDate;
	}

	public String getSvSync() {
		return this.svSync;
	}

	public void setSvSync(String svSync) {
		this.svSync = svSync;
	}

	public Date getSvSyncDate() {
		return this.svSyncDate;
	}

	public void setSvSyncDate(Date svSyncDate) {
		this.svSyncDate = svSyncDate;
	}

}