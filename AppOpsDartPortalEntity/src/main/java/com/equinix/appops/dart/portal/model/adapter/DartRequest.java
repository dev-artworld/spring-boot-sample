/**
 * 
 */
package com.equinix.appops.dart.portal.model.adapter;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author MM
 *
 */
public class DartRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("dartRequestId")
	private String dartRequestId;

	@JsonProperty("cageType")
	private String cageType;

	@JsonProperty("requestBy")
	private String requestBy;

	@JsonProperty("approvedBy")
	private String approvedBy;

	@JsonProperty("cages")
	private List<Cage> cages = null;

	/**
	 * @return the dartRequestId
	 */
	public String getDartRequestId() {
		return dartRequestId;
	}

	/**
	 * @param dartRequestId the dartRequestId to set
	 */
	public void setDartRequestId(String dartRequestId) {
		this.dartRequestId = dartRequestId;
	}

	/**
	 * @return the cageType
	 */
	public String getCageType() {
		return cageType;
	}

	/**
	 * @param cageType the cageType to set
	 */
	public void setCageType(String cageType) {
		this.cageType = cageType;
	}

	/**
	 * @return the requestBy
	 */
	public String getRequestBy() {
		return requestBy;
	}

	/**
	 * @param requestBy the requestBy to set
	 */
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the cages
	 */
	public List<Cage> getCages() {
		return cages;
	}

	/**
	 * @param cages the cages to set
	 */
	public void setCages(List<Cage> cages) {
		this.cages = cages;
	}

	@Override
	public String toString() {
		return "DartRequest [dartRequestId=" + dartRequestId + ", cageType=" + cageType + ", requestBy=" + requestBy
				+ ", approvedBy=" + approvedBy + ", cages=" + cages + "]";
	}
}
