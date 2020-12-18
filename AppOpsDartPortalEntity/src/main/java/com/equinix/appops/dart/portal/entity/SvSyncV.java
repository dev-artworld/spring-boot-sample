package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SV_SYNC_V database table.
 * 
 */
@Entity
@Table(name="SV_SYNC_V", schema="EQX_DART")
@NamedQuery(name="SvSyncV.findAll", query="SELECT s FROM SvSyncV s")
public class SvSyncV implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="UNIQUE_KEY")
	private String id;
	
	@Column(name="ASSET_ID")
	private String assetId;
	
	@Column(name="REQUESTED_BY")
	private String requestedBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name="SIEBEL_REQ_DATE")
	private Date siebelReqDate;
	
	@Column(name="INCIDENT_NO")
	private String incidentNo;
	
	@Column(name="REGION")
	private String region;

	public SvSyncV() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getSiebelReqDate() {
		return siebelReqDate;
	}

	public void setSiebelReqDate(Date siebelReqDate) {
		this.siebelReqDate = siebelReqDate;
	}

	public String getIncidentNo() {
		return incidentNo;
	}

	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	

}