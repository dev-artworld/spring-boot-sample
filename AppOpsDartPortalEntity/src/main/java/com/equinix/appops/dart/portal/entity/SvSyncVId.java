package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The persistent class for the SV_SYNC_V database table.
 * 
 */
@Embeddable
public class SvSyncVId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ASSET_ID")
	private String assetId;

	
	@Column(name="INCIDENT_NO")
	private String incidentNo;


	public SvSyncVId(String assetId, String incidentNo) {
		super();
		this.assetId = assetId;
		this.incidentNo = incidentNo;
	}


	public String getAssetId() {
		return assetId;
	}


	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}


	public String getIncidentNo() {
		return incidentNo;
	}


	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SvSyncVId)) return false;
        SvSyncVId that = (SvSyncVId) o;
        return Objects.equals(getAssetId(), that.getAssetId()) &&
                Objects.equals(getIncidentNo(), that.getIncidentNo());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getAssetId(),getIncidentNo());
    }
	
	
}