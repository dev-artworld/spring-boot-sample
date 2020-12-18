package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SRC_CXI_ERROR_MASTER_TBL database table.
 * 
 */
@Entity
@Table(name="SRC_CXI_ERROR_MASTER_TBL",schema="EQX_DART")
@NamedQuery(name="SrcCxiErrorMasterTbl.findAll", query="SELECT s FROM SrcCxiErrorMasterTbl s")
public class SrcCxiErrorMasterTbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ERROR_CODE")
	private String errorCode;

	private String active;

	@Column(name="ALERT_FLAG")
	private String alertFlag;

	@Column(name="BUSINESS_OWNER")
	private String businessOwner;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="ERROR_NAME")
	private String errorName;

	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@Column(name="OWNED_BY")
	private String ownedBy;

	@Column(name="OWNER_OF_FIXING")
	private String ownerOfFixing;

	private String suspend;

	@Column(name="\"SYSTEM\"")
	private String system;

	@Lob
	@Column(name="TECHNICAL_LOGIC")
	private String technicalLogic;

	@Column(name="THRESHOLD_LIMIT")
	private Long thresholdLimit;

	@Column(name="\"TYPE\"")
	private String type;

	@Column(name="VALIDATION_CLASS")
	private String validationClass;

	@Column(name="VALIDATION_DESCRIPTION")
	private String validationDescription;

	/*
	@OneToMany(mappedBy="cxiErrorMasterTbl")
	private List<SrcCxiErrorTbl> srcCxiErrorTbls;
	
	
	
	
	public List<SrcCxiErrorTbl> getSrcCxiErrorTbls() {
		return srcCxiErrorTbls;
	}

	public void setSrcCxiErrorTbls(List<SrcCxiErrorTbl> srcCxiErrorTbls) {
		this.srcCxiErrorTbls = srcCxiErrorTbls;
	}*/

	public SrcCxiErrorMasterTbl() {
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAlertFlag() {
		return this.alertFlag;
	}

	public void setAlertFlag(String alertFlag) {
		this.alertFlag = alertFlag;
	}

	public String getBusinessOwner() {
		return this.businessOwner;
	}

	public void setBusinessOwner(String businessOwner) {
		this.businessOwner = businessOwner;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getErrorName() {
		return this.errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getOwnedBy() {
		return this.ownedBy;
	}

	public void setOwnedBy(String ownedBy) {
		this.ownedBy = ownedBy;
	}

	public String getOwnerOfFixing() {
		return this.ownerOfFixing;
	}

	public void setOwnerOfFixing(String ownerOfFixing) {
		this.ownerOfFixing = ownerOfFixing;
	}

	public String getSuspend() {
		return this.suspend;
	}

	public void setSuspend(String suspend) {
		this.suspend = suspend;
	}

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getTechnicalLogic() {
		return this.technicalLogic;
	}

	public void setTechnicalLogic(String technicalLogic) {
		this.technicalLogic = technicalLogic;
	}

	public Long getThresholdLimit() {
		return this.thresholdLimit;
	}

	public void setThresholdLimit(Long thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidationClass() {
		return this.validationClass;
	}

	public void setValidationClass(String validationClass) {
		this.validationClass = validationClass;
	}

	public String getValidationDescription() {
		return this.validationDescription;
	}

	public void setValidationDescription(String validationDescription) {
		this.validationDescription = validationDescription;
	}

}