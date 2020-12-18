package com.equinix.appops.dart.portal.model.adapter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author MM
 *
 */
public class Cabinet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("usid")
	private String usid;

	@JsonProperty("ucmid")
	private String ucmid;
	
	@JsonProperty("pof")
	private String pof;
	
	@JsonProperty("drawCap")
	private int drawCap;
	
	@JsonProperty("assetId")
	private String assetId;
	
	@JsonProperty("availabilityStatus")
	private String availabilityStatus;
	
	@JsonProperty("cabinetType")
	private String cabinetType;

	/**
	 * @return the usid
	 */
	public String getUsid() {
		return usid;
	}

	/**
	 * @param usid the usid to set
	 */
	public void setUsid(String usid) {
		this.usid = usid;
	}

	/**
	 * @return the ucmid
	 */
	public String getUcmid() {
		return ucmid;
	}

	/**
	 * @param ucmid the ucmid to set
	 */
	public void setUcmid(String ucmid) {
		this.ucmid = ucmid;
	}

	/**
	 * @return the pof
	 */
	public String getPof() {
		return pof;
	}

	/**
	 * @param pof the pof to set
	 */
	public void setPof(String pof) {
		this.pof = pof;
	}

	/**
	 * @return the drawCap
	 */
	public int getDrawCap() {
		return drawCap;
	}

	/**
	 * @param drawCap the drawCap to set
	 */
	public void setDrawCap(int drawCap) {
		this.drawCap = drawCap;
	}

	/**
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	/**
	 * @return the availabilityStatus
	 */
	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	/**
	 * @param availabilityStatus the availabilityStatus to set
	 */
	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	/**
	 * @return the cabinetType
	 */
	public String getCabinetType() {
		return cabinetType;
	}

	/**
	 * @param cabinetType the cabinetType to set
	 */
	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}

	@Override
	public String toString() {
		return "Cabinet [usid=" + usid + ", ucmid=" + ucmid + ", pof=" + pof + ", drawCap=" + drawCap + ", assetId="
				+ assetId + ", availabilityStatus=" + availabilityStatus + ", cabinetType=" + cabinetType + "]";
	}
	
}
