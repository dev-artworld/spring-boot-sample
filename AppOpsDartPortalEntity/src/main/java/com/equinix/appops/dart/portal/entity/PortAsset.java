package com.equinix.appops.dart.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PortAsset {
	
	@Id
	@Column(name="ASSET_ID")
	private String assetId;
	
	@Column(name="PORT_ID")
	private String portId;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}
	
	

}
