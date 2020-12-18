package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the CXI_ERROR_TBL database table.
 * 
 * @author Sandeep Singh
 * 
 */
@Entity
@Table(name="MS_CXI_ERROR_TBL", schema="EQX_DART")
@NamedQuery(name="MsCxiErrorTbl.findAll", query="SELECT s FROM MsCxiErrorTbl s")
public class MsCxiErrorTbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ERR_UNIQUE_ID")
	private String errUniqueId;
	
	/**
	 * DFR_LINE_ID
	 */
	
	@Column(name = "DFR_LINE_ID")
	private String dfrLineId;

	/**
	 * DFR_ID
	 */

	@Column(name = "DFR_ID")
	private String dfrId;
	

	@Temporal(TemporalType.DATE)
	@Column(name="ASSET_CREATION_DATE")
	private Date assetCreationDate;

	@Column(name="ASSIGNED_TO")
	private String assignedTo;

	@Column(name="AUDIT_01")
	private String audit01;

	@Column(name="AUDIT_01_IN")
	private String audit01In;

	@Column(name="AUDIT_01_OUT")
	private String audit01Out;

	@Column(name="AUDIT_02")
	private String audit02;

	@Column(name="AUDIT_02_IN")
	private String audit02In;

	@Column(name="AUDIT_02_OUT")
	private String audit02Out;

	@Column(name="AUDIT_03")
	private String audit03;

	@Column(name="AUDIT_03_IN")
	private String audit03In;

	@Column(name="AUDIT_03_OUT")
	private String audit03Out;

	@Column(name="AUDIT_04")
	private String audit04;

	@Column(name="AUDIT_04_IN")
	private String audit04In;

	@Column(name="AUDIT_04_OUT")
	private String audit04Out;

	@Column(name="AUDIT_05")
	private String audit05;

	@Column(name="AUDIT_05_IN")
	private String audit05In;

	@Column(name="AUDIT_05_OUT")
	private String audit05Out;

	@Column(name="AUDIT_06")
	private String audit06;

	@Column(name="AUDIT_06_IN")
	private String audit06In;

	@Column(name="AUDIT_06_OUT")
	private String audit06Out;

	@Column(name="AUDIT_07")
	private String audit07;

	@Column(name="AUDIT_07_IN")
	private String audit07In;

	@Column(name="AUDIT_07_OUT")
	private String audit07Out;

	@Column(name="AUDIT_08")
	private String audit08;

	@Column(name="AUDIT_08_IN")
	private String audit08In;

	@Column(name="AUDIT_08_OUT")
	private String audit08Out;

	@Column(name="AUDIT_09")
	private String audit09;

	@Column(name="AUDIT_09_IN")
	private String audit09In;

	@Column(name="AUDIT_09_OUT")
	private String audit09Out;

	@Column(name="AUDIT_10")
	private String audit10;

	@Column(name="AUDIT_10_IN")
	private String audit10In;

	@Column(name="AUDIT_10_OUT")
	private String audit10Out;

	@Column(name="BATCH_NO")
	private String batchNo;

	private String comments;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="ERROR_CODE")
	private String errorCode;


	@Temporal(TemporalType.DATE)
	@Column(name="ERROR_CREATION_DATE")
	private Date errorCreationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="ERROR_END_DATE")
	private Date errorEndDate;

	@Column(name="ERROR_ITEM")
	private String errorItem;
	
	@Column(name="VALID_STAT")
	private String validStat;
	
	@Column(name="STATUS_CD")
	private String statusCD;

	@Column(name="ERROR_NAME")
	private String errorName;

	private String ibx;

	private String incident;

	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	/*@Column(name="SV_RID")
	private String svRid;*/

	//bi-directional many-to-one association to SiebelAssetDa
	@ManyToOne(/*fetch = FetchType.LAZY,optional =false*/) // Commented due to hibarnate error in DART Mobile  
	@JoinColumn(name="SBL_DFR_LINE_ID",nullable=true)
	private MsSnapshotSiebelAssetDa msSnapshotSiebelAssetDa;
	
	//bi-directional many-to-one association to SiebelAssetDa
	@ManyToOne(/*fetch = FetchType.LAZY/*,optional =false*/) // Commented due to hibarnate error in DART Mobile
	@JoinColumn(name="SNOW_DFR_LINE_ID",nullable=true)
	private SnapshotSnowAssetDa snapshotSnowAssetDa ;
	
	@ManyToOne(/*fetch = FetchType.LAZY/*,optional =false*/) // Commented due to hibarnate error in DART Mobile
	@JoinColumn(name="CI_DA_DFR_LINE_ID",nullable=true)
	private SnapshotSnowConfigItemDa snapshotSnowConfigItemDa ;
	
	@ManyToOne(/*fetch = FetchType.LAZY/*,optional =false*/) // Commented due to hibarnate error in DART Mobile
	@JoinColumn(name="CI_XA_DFR_LINE_ID",nullable=true)
	private SnapshotSnowConfigItemXa snapshotSnowConfigItemXa ;
	
	//bi-directional many-to-one association to SiebelAssetDa
	/*@ManyToOne(fetch = FetchType.LAZY,optional =false)
	@JoinColumn(name="ERROR_CODE")
	private SrcCxiErrorMasterTbl cxiErrorMasterTbl ;*/
	@Transient
	private String msSnapshotSiebelAssetDaLineId;
	
	@Transient
	private String snapshotSnowAssetDaLineId ;
	
	@Transient
	private String snapshotSnowConfigItemDaLineId ;
	
	@Transient
	private String snapshotSnowConfigItemXaLineId ;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * DFR_LINE_ID
	 * 
	 */

	public String getDfrLineId() {
		return dfrLineId;
	}

	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}

	/**
	 * DFR_ID
	 * 
	 */
	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
	


	public MsCxiErrorTbl() {
	}

	public String getErrUniqueId() {
		return this.errUniqueId;
	}

	public void setErrUniqueId(String errUniqueId) {
		this.errUniqueId = errUniqueId;
	}

	public Date getAssetCreationDate() {
		return this.assetCreationDate;
	}

	public void setAssetCreationDate(Date assetCreationDate) {
		this.assetCreationDate = assetCreationDate;
	}

	public String getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAudit01() {
		return this.audit01;
	}

	public void setAudit01(String audit01) {
		this.audit01 = audit01;
	}

	public String getAudit01In() {
		return this.audit01In;
	}

	public void setAudit01In(String audit01In) {
		this.audit01In = audit01In;
	}

	public String getAudit01Out() {
		return this.audit01Out;
	}

	public void setAudit01Out(String audit01Out) {
		this.audit01Out = audit01Out;
	}

	public String getAudit02() {
		return this.audit02;
	}

	public void setAudit02(String audit02) {
		this.audit02 = audit02;
	}

	public String getAudit02In() {
		return this.audit02In;
	}

	public void setAudit02In(String audit02In) {
		this.audit02In = audit02In;
	}

	public String getAudit02Out() {
		return this.audit02Out;
	}

	public void setAudit02Out(String audit02Out) {
		this.audit02Out = audit02Out;
	}

	public String getAudit03() {
		return this.audit03;
	}

	public void setAudit03(String audit03) {
		this.audit03 = audit03;
	}

	public String getAudit03In() {
		return this.audit03In;
	}

	public void setAudit03In(String audit03In) {
		this.audit03In = audit03In;
	}

	public String getAudit03Out() {
		return this.audit03Out;
	}

	public void setAudit03Out(String audit03Out) {
		this.audit03Out = audit03Out;
	}

	public String getAudit04() {
		return this.audit04;
	}

	public void setAudit04(String audit04) {
		this.audit04 = audit04;
	}

	public String getAudit04In() {
		return this.audit04In;
	}

	public void setAudit04In(String audit04In) {
		this.audit04In = audit04In;
	}

	public String getAudit04Out() {
		return this.audit04Out;
	}

	public void setAudit04Out(String audit04Out) {
		this.audit04Out = audit04Out;
	}

	public String getAudit05() {
		return this.audit05;
	}

	public void setAudit05(String audit05) {
		this.audit05 = audit05;
	}

	public String getAudit05In() {
		return this.audit05In;
	}

	public void setAudit05In(String audit05In) {
		this.audit05In = audit05In;
	}

	public String getAudit05Out() {
		return this.audit05Out;
	}

	public void setAudit05Out(String audit05Out) {
		this.audit05Out = audit05Out;
	}

	public String getAudit06() {
		return this.audit06;
	}

	public void setAudit06(String audit06) {
		this.audit06 = audit06;
	}

	public String getAudit06In() {
		return this.audit06In;
	}

	public void setAudit06In(String audit06In) {
		this.audit06In = audit06In;
	}

	public String getAudit06Out() {
		return this.audit06Out;
	}

	public void setAudit06Out(String audit06Out) {
		this.audit06Out = audit06Out;
	}

	public String getAudit07() {
		return this.audit07;
	}

	public void setAudit07(String audit07) {
		this.audit07 = audit07;
	}

	public String getAudit07In() {
		return this.audit07In;
	}

	public void setAudit07In(String audit07In) {
		this.audit07In = audit07In;
	}

	public String getAudit07Out() {
		return this.audit07Out;
	}

	public void setAudit07Out(String audit07Out) {
		this.audit07Out = audit07Out;
	}

	public String getAudit08() {
		return this.audit08;
	}

	public void setAudit08(String audit08) {
		this.audit08 = audit08;
	}

	public String getAudit08In() {
		return this.audit08In;
	}

	public void setAudit08In(String audit08In) {
		this.audit08In = audit08In;
	}

	public String getAudit08Out() {
		return this.audit08Out;
	}

	public void setAudit08Out(String audit08Out) {
		this.audit08Out = audit08Out;
	}

	public String getAudit09() {
		return this.audit09;
	}

	public void setAudit09(String audit09) {
		this.audit09 = audit09;
	}

	public String getAudit09In() {
		return this.audit09In;
	}

	public void setAudit09In(String audit09In) {
		this.audit09In = audit09In;
	}

	public String getAudit09Out() {
		return this.audit09Out;
	}

	public void setAudit09Out(String audit09Out) {
		this.audit09Out = audit09Out;
	}

	public String getAudit10() {
		return this.audit10;
	}

	public void setAudit10(String audit10) {
		this.audit10 = audit10;
	}

	public String getAudit10In() {
		return this.audit10In;
	}

	public void setAudit10In(String audit10In) {
		this.audit10In = audit10In;
	}

	public String getAudit10Out() {
		return this.audit10Out;
	}

	public void setAudit10Out(String audit10Out) {
		this.audit10Out = audit10Out;
	}

	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getErrorCreationDate() {
		return this.errorCreationDate;
	}

	public void setErrorCreationDate(Date errorCreationDate) {
		this.errorCreationDate = errorCreationDate;
	}

	public Date getErrorEndDate() {
		return this.errorEndDate;
	}

	public void setErrorEndDate(Date errorEndDate) {
		this.errorEndDate = errorEndDate;
	}

	public String getErrorItem() {
		return this.errorItem;
	}

	public void setErrorItem(String errorItem) {
		this.errorItem = errorItem;
	}

	public String getErrorName() {
		return this.errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getIbx() {
		return this.ibx;
	}

	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

	public String getIncident() {
		return this.incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
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

	public String getValidStat() {
		return validStat;
	}

	public void setValidStat(String validStat) {
		this.validStat = validStat;
	}

	public String getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}

	public MsSnapshotSiebelAssetDa getMsSnapshotSiebelAssetDa() {
		return msSnapshotSiebelAssetDa;
	}

	public void setMsSnapshotSiebelAssetDa(MsSnapshotSiebelAssetDa msSnapshotSiebelAssetDa) {
		this.msSnapshotSiebelAssetDa = msSnapshotSiebelAssetDa;
	}

	public SnapshotSnowAssetDa getSnapshotSnowAssetDa() {
		return snapshotSnowAssetDa;
	}

	public void setSnapshotSnowAssetDa(SnapshotSnowAssetDa snapshotSnowAssetDa) {
		this.snapshotSnowAssetDa = snapshotSnowAssetDa;
	}

	public String getMsSnapshotSiebelAssetDaLineId() {
		return msSnapshotSiebelAssetDaLineId;
	}

	public void setMsSnapshotSiebelAssetDaLineId(String msSnapshotSiebelAssetDaLineId) {
		this.msSnapshotSiebelAssetDaLineId = msSnapshotSiebelAssetDaLineId;
	}

	public String getSnapshotSnowAssetDaLineId() {
		return snapshotSnowAssetDaLineId;
	}

	public void setSnapshotSnowAssetDaLineId(String snapshotSnowAssetDaLineId) {
		this.snapshotSnowAssetDaLineId = snapshotSnowAssetDaLineId;
	}

	public SnapshotSnowConfigItemDa getSnapshotSnowConfigItemDa() {
		return snapshotSnowConfigItemDa;
	}

	public void setSnapshotSnowConfigItemDa(SnapshotSnowConfigItemDa snapshotSnowConfigItemDa) {
		this.snapshotSnowConfigItemDa = snapshotSnowConfigItemDa;
	}

	public SnapshotSnowConfigItemXa getSnapshotSnowConfigItemXa() {
		return snapshotSnowConfigItemXa;
	}

	public void setSnapshotSnowConfigItemXa(SnapshotSnowConfigItemXa snapshotSnowConfigItemXa) {
		this.snapshotSnowConfigItemXa = snapshotSnowConfigItemXa;
	}

	public String getSnapshotSnowConfigItemDaLineId() {
		return snapshotSnowConfigItemDaLineId;
	}

	public void setSnapshotSnowConfigItemDaLineId(String snapshotSnowConfigItemDaLineId) {
		this.snapshotSnowConfigItemDaLineId = snapshotSnowConfigItemDaLineId;
	}

	public String getSnapshotSnowConfigItemXaLineId() {
		return snapshotSnowConfigItemXaLineId;
	}

	public void setSnapshotSnowConfigItemXaLineId(String snapshotSnowConfigItemXaLineId) {
		this.snapshotSnowConfigItemXaLineId = snapshotSnowConfigItemXaLineId;
	}
	

	

}