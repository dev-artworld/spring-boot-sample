package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the SRC_EQX_CL_CABINET database table.
 * 
 */
@Entity
@Table(name="SRC_EQX_CL_CABINET", schema="EQX_DART")
@NamedQuery(name="SrcEqxClCabinet.findAll", query="SELECT s FROM SrcEqxClCabinet s")
public class SrcEqxClCabinet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUAL_CAB")
	private BigDecimal actualCab;

	@Column(name="ASSET_ID")
	private String assetId;

	@Column(name="AUTOCAD_TAG")
	private String autocadTag;

	@Column(name="CAB_AT_MAX_DRAW_ID")
	private String cabAtMaxDrawId;

	@Column(name="CABINET_AUDITED")
	private String cabinetAudited;

	@Id
	@Column(name="CABINET_ID")
	private String cabinetId;

	@Column(name="CABINET_KWH")
	private BigDecimal cabinetKwh;

	@Column(name="CABINET_LOCATION")
	private BigDecimal cabinetLocation;

	@Column(name="CABINET_NUM")
	private String cabinetNum;

	@Column(name="CABINET_RATING")
	private BigDecimal cabinetRating;

	@Column(name="CABINET_STATUS")
	private BigDecimal cabinetStatus;

	@Column(name="CABINET_STATUS_DESC")
	private String cabinetStatusDesc;

	@Column(name="CABINET_TYPE")
	private BigDecimal cabinetType;

	@Column(name="CABINET_TYPE_DESC")
	private String cabinetTypeDesc;

	@Column(name="CABINET_USABLE_KVA_CAP")
	private BigDecimal cabinetUsableKvaCap;

	@Column(name="CAGE_ID")
	private String cageId;

	@Column(name="CAPACITY_STATUS_ID")
	private BigDecimal capacityStatusId;

	@Column(name="CDC_CREATION_DT")
	private String cdcCreationDt;

	@Column(name="CHURN_CABINET_LOCATION")
	private BigDecimal churnCabinetLocation;

	@Column(name="CHURN_CONTRACTED_CABE")
	private BigDecimal churnContractedCabe;

	@Column(name="CHURN_CONTRACTED_KVA")
	private BigDecimal churnContractedKva;

	@Column(name="CHURN_CUSTOMER")
	private BigDecimal churnCustomer;

	@Column(name="CHURN_DIRECT_ACCESS_CC")
	private String churnDirectAccessCc;

	@Column(name="CHURN_EQUINIX_CABINET_TYPE")
	private BigDecimal churnEquinixCabinetType;

	@Column(name="CHURN_EQUINIX_DEPT_TYPE")
	private BigDecimal churnEquinixDeptType;

	@Column(name="CHURN_POF")
	private BigDecimal churnPof;

	@Column(name="CHURN_POF_INTG_ID")
	private String churnPofIntgId;

	@Column(name="CHURN_PREV_CAP_STATUS")
	private BigDecimal churnPrevCapStatus;

	@Column(name="CHURN_RESV_ID")
	private BigDecimal churnResvId;

	@Column(name="CIRCUIT_BRK_POS_INSTALLED")
	private BigDecimal circuitBrkPosInstalled;

	@Column(name="CONTIGUOUS_NUM")
	private BigDecimal contiguousNum;

	@Column(name="CONTRACTED_CABE")
	private BigDecimal contractedCabe;

	@Column(name="CONTRACTED_KVA")
	private BigDecimal contractedKva;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="CUST_CAB_REF_NUM")
	private String custCabRefNum;

	@Column(name="CUSTOMER_ID")
	private BigDecimal customerId;

	@Column(name="DC_CIRCUITS_INSTALLED")
	private String dcCircuitsInstalled;

	@Column(name="\"DEPTH\"")
	private String depth;

	@Column(name="DESIGNED_CABINET_TYPE_ID")
	private BigDecimal designedCabinetTypeId;

	@Column(name="DIRECT_ACCESS_CROSS_CONNECT")
	private String directAccessCrossConnect;

	@Column(name="DRAW_CAP")
	private BigDecimal drawCap;

	@Column(name="DRAWCAP_POW_VALIDATIONS")
	private BigDecimal drawcapPowValidations;

	@Column(name="DTL__CAPXACTION")
	private String dtlCapxaction;

	@Column(name="DTL__CAPXRESTART1")
	private String dtlCapxrestart1;

	@Column(name="DTL__CAPXRESTART2")
	private String dtlCapxrestart2;

	@Column(name="DTL__CAPXTIMESTAMP")
	private String dtlCapxtimestamp;

	@Column(name="DTL__CAPXUSER")
	private String dtlCapxuser;

	@Column(name="EARMARKED_TIME")
	private Timestamp earmarkedTime;

	@Column(name="ELIGIBLE_FR_MUL_RESERVATION")
	private String eligibleFrMulReservation;

	@Column(name="EQUINIX_CABINET_TYPE")
	private BigDecimal equinixCabinetType;

	@Column(name="EQUINIX_DEPT_TYPE")
	private BigDecimal equinixDeptType;

	private String height;

	@Column(name="INSTALL_DATE")
	private Timestamp installDate;

	@Column(name="INSTALLED_UNIT_IMP_TYPE")
	private String installedUnitImpType;

	@Column(name="INSTALLED_UNIT_MT_TYPE")
	private String installedUnitMtType;

	@Column(name="IS_CUSTOMER_VALID")
	private String isCustomerValid;

	@Column(name="IS_DRAW_CAP_VALID")
	private String isDrawCapValid;

	@Column(name="IS_PA_VIA_TERMINATION")
	private String isPaViaTermination;

	private String isconfigured;

	@Column(name="KVA_INSTALLED")
	private BigDecimal kvaInstalled;

	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name="LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name="LEASED_SPACE_ID")
	private String leasedSpaceId;

	@Column(name="LEASED_SPACE_LINKED")
	private String leasedSpaceLinked;

	@Column(name="LEGACY_CABINET_NUM")
	private String legacyCabinetNum;

	@Column(name="LEGACY_SYSTEM_NAME")
	private String legacySystemName;

	@Column(name="LOCKED_STATUS")
	private BigDecimal lockedStatus;

	@Column(name="MAX_CIRCUIT_BRK_POSITION")
	private BigDecimal maxCircuitBrkPosition;

	@Column(name="MULIT_BILLING_TERMS")
	private String mulitBillingTerms;

	private String notes;

	@Column(name="OPPORTUNITY_ID")
	private String opportunityId;

	@Column(name="PARENT_CABINET_ID")
	private BigDecimal parentCabinetId;

	private String pec;

	@Column(name="PEC_CAP")
	private BigDecimal pecCap;

	@Column(name="PEND_AVAIL_DATE_COMMENTS")
	private String pendAvailDateComments;

	@Column(name="PENDING_AVAILABLE_DATE")
	private Timestamp pendingAvailableDate;

	@Column(name="PENDING_DELETION")
	private String pendingDeletion;

	private BigDecimal pof;

	@Column(name="POF_INTG_ID")
	private String pofIntgId;

	@Column(name="POF_NAME")
	private String pofName;

	@Column(name="PREV_AVAIL_STATUS")
	private BigDecimal prevAvailStatus;

	@Column(name="PREV_INSTALL_CBP")
	private BigDecimal prevInstallCbp;

	@Column(name="PREV_INSTALL_KVA")
	private BigDecimal prevInstallKva;

	@Column(name="PREVIOUS_RESERVATION")
	private BigDecimal previousReservation;

	@Column(name="REASON_FOR_HOLD")
	private BigDecimal reasonForHold;

	@Column(name="RESERVATION_ID")
	private BigDecimal reservationId;

	@Column(name="SHARED_RACK_UNITS")
	private String sharedRackUnits;

	@Column(name="STATUS_CHANGE_DATE")
	private Timestamp statusChangeDate;

	@Column(name="UNIQUE_SPACEID")
	private BigDecimal uniqueSpaceid;

	@Column(name="V_DAY_OF_WEEK")
	private String vDayOfWeek;

	@Column(name="VERIZON_CUSTOMER_NUMBER")
	private String verizonCustomerNumber;

	@Column(name="\"VERSION\"")
	private BigDecimal version;

	@Column(name="VIRTUAL_CABINET")
	private String virtualCabinet;

	@Column(name="VIRTUAL_RACK_UNIT")
	private String virtualRackUnit;

	private BigDecimal weight;

	private String width;

	@Column(name="WORKFLOW_STATUS")
	private BigDecimal workflowStatus;

	@Column(name="WWCS_FLAG")
	private String wwcsFlag;

	@Column(name="ZONE_ID")
	private BigDecimal zoneId;

	public SrcEqxClCabinet() {
	}

	public BigDecimal getActualCab() {
		return this.actualCab;
	}

	public void setActualCab(BigDecimal actualCab) {
		this.actualCab = actualCab;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAutocadTag() {
		return this.autocadTag;
	}

	public void setAutocadTag(String autocadTag) {
		this.autocadTag = autocadTag;
	}

	public String getCabAtMaxDrawId() {
		return this.cabAtMaxDrawId;
	}

	public void setCabAtMaxDrawId(String cabAtMaxDrawId) {
		this.cabAtMaxDrawId = cabAtMaxDrawId;
	}

	public String getCabinetAudited() {
		return this.cabinetAudited;
	}

	public void setCabinetAudited(String cabinetAudited) {
		this.cabinetAudited = cabinetAudited;
	}

	public String getCabinetId() {
		return this.cabinetId;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public BigDecimal getCabinetKwh() {
		return this.cabinetKwh;
	}

	public void setCabinetKwh(BigDecimal cabinetKwh) {
		this.cabinetKwh = cabinetKwh;
	}

	public BigDecimal getCabinetLocation() {
		return this.cabinetLocation;
	}

	public void setCabinetLocation(BigDecimal cabinetLocation) {
		this.cabinetLocation = cabinetLocation;
	}

	public String getCabinetNum() {
		return this.cabinetNum;
	}

	public void setCabinetNum(String cabinetNum) {
		this.cabinetNum = cabinetNum;
	}

	public BigDecimal getCabinetRating() {
		return this.cabinetRating;
	}

	public void setCabinetRating(BigDecimal cabinetRating) {
		this.cabinetRating = cabinetRating;
	}

	public BigDecimal getCabinetStatus() {
		return this.cabinetStatus;
	}

	public void setCabinetStatus(BigDecimal cabinetStatus) {
		this.cabinetStatus = cabinetStatus;
	}

	public String getCabinetStatusDesc() {
		return this.cabinetStatusDesc;
	}

	public void setCabinetStatusDesc(String cabinetStatusDesc) {
		this.cabinetStatusDesc = cabinetStatusDesc;
	}

	public BigDecimal getCabinetType() {
		return this.cabinetType;
	}

	public void setCabinetType(BigDecimal cabinetType) {
		this.cabinetType = cabinetType;
	}

	public String getCabinetTypeDesc() {
		return this.cabinetTypeDesc;
	}

	public void setCabinetTypeDesc(String cabinetTypeDesc) {
		this.cabinetTypeDesc = cabinetTypeDesc;
	}

	public BigDecimal getCabinetUsableKvaCap() {
		return this.cabinetUsableKvaCap;
	}

	public void setCabinetUsableKvaCap(BigDecimal cabinetUsableKvaCap) {
		this.cabinetUsableKvaCap = cabinetUsableKvaCap;
	}

	public String getCageId() {
		return this.cageId;
	}

	public void setCageId(String cageId) {
		this.cageId = cageId;
	}

	public BigDecimal getCapacityStatusId() {
		return this.capacityStatusId;
	}

	public void setCapacityStatusId(BigDecimal capacityStatusId) {
		this.capacityStatusId = capacityStatusId;
	}

	public String getCdcCreationDt() {
		return this.cdcCreationDt;
	}

	public void setCdcCreationDt(String cdcCreationDt) {
		this.cdcCreationDt = cdcCreationDt;
	}

	public BigDecimal getChurnCabinetLocation() {
		return this.churnCabinetLocation;
	}

	public void setChurnCabinetLocation(BigDecimal churnCabinetLocation) {
		this.churnCabinetLocation = churnCabinetLocation;
	}

	public BigDecimal getChurnContractedCabe() {
		return this.churnContractedCabe;
	}

	public void setChurnContractedCabe(BigDecimal churnContractedCabe) {
		this.churnContractedCabe = churnContractedCabe;
	}

	public BigDecimal getChurnContractedKva() {
		return this.churnContractedKva;
	}

	public void setChurnContractedKva(BigDecimal churnContractedKva) {
		this.churnContractedKva = churnContractedKva;
	}

	public BigDecimal getChurnCustomer() {
		return this.churnCustomer;
	}

	public void setChurnCustomer(BigDecimal churnCustomer) {
		this.churnCustomer = churnCustomer;
	}

	public String getChurnDirectAccessCc() {
		return this.churnDirectAccessCc;
	}

	public void setChurnDirectAccessCc(String churnDirectAccessCc) {
		this.churnDirectAccessCc = churnDirectAccessCc;
	}

	public BigDecimal getChurnEquinixCabinetType() {
		return this.churnEquinixCabinetType;
	}

	public void setChurnEquinixCabinetType(BigDecimal churnEquinixCabinetType) {
		this.churnEquinixCabinetType = churnEquinixCabinetType;
	}

	public BigDecimal getChurnEquinixDeptType() {
		return this.churnEquinixDeptType;
	}

	public void setChurnEquinixDeptType(BigDecimal churnEquinixDeptType) {
		this.churnEquinixDeptType = churnEquinixDeptType;
	}

	public BigDecimal getChurnPof() {
		return this.churnPof;
	}

	public void setChurnPof(BigDecimal churnPof) {
		this.churnPof = churnPof;
	}

	public String getChurnPofIntgId() {
		return this.churnPofIntgId;
	}

	public void setChurnPofIntgId(String churnPofIntgId) {
		this.churnPofIntgId = churnPofIntgId;
	}

	public BigDecimal getChurnPrevCapStatus() {
		return this.churnPrevCapStatus;
	}

	public void setChurnPrevCapStatus(BigDecimal churnPrevCapStatus) {
		this.churnPrevCapStatus = churnPrevCapStatus;
	}

	public BigDecimal getChurnResvId() {
		return this.churnResvId;
	}

	public void setChurnResvId(BigDecimal churnResvId) {
		this.churnResvId = churnResvId;
	}

	public BigDecimal getCircuitBrkPosInstalled() {
		return this.circuitBrkPosInstalled;
	}

	public void setCircuitBrkPosInstalled(BigDecimal circuitBrkPosInstalled) {
		this.circuitBrkPosInstalled = circuitBrkPosInstalled;
	}

	public BigDecimal getContiguousNum() {
		return this.contiguousNum;
	}

	public void setContiguousNum(BigDecimal contiguousNum) {
		this.contiguousNum = contiguousNum;
	}

	public BigDecimal getContractedCabe() {
		return this.contractedCabe;
	}

	public void setContractedCabe(BigDecimal contractedCabe) {
		this.contractedCabe = contractedCabe;
	}

	public BigDecimal getContractedKva() {
		return this.contractedKva;
	}

	public void setContractedKva(BigDecimal contractedKva) {
		this.contractedKva = contractedKva;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCustCabRefNum() {
		return this.custCabRefNum;
	}

	public void setCustCabRefNum(String custCabRefNum) {
		this.custCabRefNum = custCabRefNum;
	}

	public BigDecimal getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getDcCircuitsInstalled() {
		return this.dcCircuitsInstalled;
	}

	public void setDcCircuitsInstalled(String dcCircuitsInstalled) {
		this.dcCircuitsInstalled = dcCircuitsInstalled;
	}

	public String getDepth() {
		return this.depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public BigDecimal getDesignedCabinetTypeId() {
		return this.designedCabinetTypeId;
	}

	public void setDesignedCabinetTypeId(BigDecimal designedCabinetTypeId) {
		this.designedCabinetTypeId = designedCabinetTypeId;
	}

	public String getDirectAccessCrossConnect() {
		return this.directAccessCrossConnect;
	}

	public void setDirectAccessCrossConnect(String directAccessCrossConnect) {
		this.directAccessCrossConnect = directAccessCrossConnect;
	}

	public BigDecimal getDrawCap() {
		return this.drawCap;
	}

	public void setDrawCap(BigDecimal drawCap) {
		this.drawCap = drawCap;
	}

	public BigDecimal getDrawcapPowValidations() {
		return this.drawcapPowValidations;
	}

	public void setDrawcapPowValidations(BigDecimal drawcapPowValidations) {
		this.drawcapPowValidations = drawcapPowValidations;
	}

	public String getDtlCapxaction() {
		return this.dtlCapxaction;
	}

	public void setDtlCapxaction(String dtlCapxaction) {
		this.dtlCapxaction = dtlCapxaction;
	}

	public String getDtlCapxrestart1() {
		return this.dtlCapxrestart1;
	}

	public void setDtlCapxrestart1(String dtlCapxrestart1) {
		this.dtlCapxrestart1 = dtlCapxrestart1;
	}

	public String getDtlCapxrestart2() {
		return this.dtlCapxrestart2;
	}

	public void setDtlCapxrestart2(String dtlCapxrestart2) {
		this.dtlCapxrestart2 = dtlCapxrestart2;
	}

	public String getDtlCapxtimestamp() {
		return this.dtlCapxtimestamp;
	}

	public void setDtlCapxtimestamp(String dtlCapxtimestamp) {
		this.dtlCapxtimestamp = dtlCapxtimestamp;
	}

	public String getDtlCapxuser() {
		return this.dtlCapxuser;
	}

	public void setDtlCapxuser(String dtlCapxuser) {
		this.dtlCapxuser = dtlCapxuser;
	}

	public Timestamp getEarmarkedTime() {
		return this.earmarkedTime;
	}

	public void setEarmarkedTime(Timestamp earmarkedTime) {
		this.earmarkedTime = earmarkedTime;
	}

	public String getEligibleFrMulReservation() {
		return this.eligibleFrMulReservation;
	}

	public void setEligibleFrMulReservation(String eligibleFrMulReservation) {
		this.eligibleFrMulReservation = eligibleFrMulReservation;
	}

	public BigDecimal getEquinixCabinetType() {
		return this.equinixCabinetType;
	}

	public void setEquinixCabinetType(BigDecimal equinixCabinetType) {
		this.equinixCabinetType = equinixCabinetType;
	}

	public BigDecimal getEquinixDeptType() {
		return this.equinixDeptType;
	}

	public void setEquinixDeptType(BigDecimal equinixDeptType) {
		this.equinixDeptType = equinixDeptType;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Timestamp getInstallDate() {
		return this.installDate;
	}

	public void setInstallDate(Timestamp installDate) {
		this.installDate = installDate;
	}

	public String getInstalledUnitImpType() {
		return this.installedUnitImpType;
	}

	public void setInstalledUnitImpType(String installedUnitImpType) {
		this.installedUnitImpType = installedUnitImpType;
	}

	public String getInstalledUnitMtType() {
		return this.installedUnitMtType;
	}

	public void setInstalledUnitMtType(String installedUnitMtType) {
		this.installedUnitMtType = installedUnitMtType;
	}

	public String getIsCustomerValid() {
		return this.isCustomerValid;
	}

	public void setIsCustomerValid(String isCustomerValid) {
		this.isCustomerValid = isCustomerValid;
	}

	public String getIsDrawCapValid() {
		return this.isDrawCapValid;
	}

	public void setIsDrawCapValid(String isDrawCapValid) {
		this.isDrawCapValid = isDrawCapValid;
	}

	public String getIsPaViaTermination() {
		return this.isPaViaTermination;
	}

	public void setIsPaViaTermination(String isPaViaTermination) {
		this.isPaViaTermination = isPaViaTermination;
	}

	public String getIsconfigured() {
		return this.isconfigured;
	}

	public void setIsconfigured(String isconfigured) {
		this.isconfigured = isconfigured;
	}

	public BigDecimal getKvaInstalled() {
		return this.kvaInstalled;
	}

	public void setKvaInstalled(BigDecimal kvaInstalled) {
		this.kvaInstalled = kvaInstalled;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLeasedSpaceId() {
		return this.leasedSpaceId;
	}

	public void setLeasedSpaceId(String leasedSpaceId) {
		this.leasedSpaceId = leasedSpaceId;
	}

	public String getLeasedSpaceLinked() {
		return this.leasedSpaceLinked;
	}

	public void setLeasedSpaceLinked(String leasedSpaceLinked) {
		this.leasedSpaceLinked = leasedSpaceLinked;
	}

	public String getLegacyCabinetNum() {
		return this.legacyCabinetNum;
	}

	public void setLegacyCabinetNum(String legacyCabinetNum) {
		this.legacyCabinetNum = legacyCabinetNum;
	}

	public String getLegacySystemName() {
		return this.legacySystemName;
	}

	public void setLegacySystemName(String legacySystemName) {
		this.legacySystemName = legacySystemName;
	}

	public BigDecimal getLockedStatus() {
		return this.lockedStatus;
	}

	public void setLockedStatus(BigDecimal lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	public BigDecimal getMaxCircuitBrkPosition() {
		return this.maxCircuitBrkPosition;
	}

	public void setMaxCircuitBrkPosition(BigDecimal maxCircuitBrkPosition) {
		this.maxCircuitBrkPosition = maxCircuitBrkPosition;
	}

	public String getMulitBillingTerms() {
		return this.mulitBillingTerms;
	}

	public void setMulitBillingTerms(String mulitBillingTerms) {
		this.mulitBillingTerms = mulitBillingTerms;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOpportunityId() {
		return this.opportunityId;
	}

	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	public BigDecimal getParentCabinetId() {
		return this.parentCabinetId;
	}

	public void setParentCabinetId(BigDecimal parentCabinetId) {
		this.parentCabinetId = parentCabinetId;
	}

	public String getPec() {
		return this.pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public BigDecimal getPecCap() {
		return this.pecCap;
	}

	public void setPecCap(BigDecimal pecCap) {
		this.pecCap = pecCap;
	}

	public String getPendAvailDateComments() {
		return this.pendAvailDateComments;
	}

	public void setPendAvailDateComments(String pendAvailDateComments) {
		this.pendAvailDateComments = pendAvailDateComments;
	}

	public Timestamp getPendingAvailableDate() {
		return this.pendingAvailableDate;
	}

	public void setPendingAvailableDate(Timestamp pendingAvailableDate) {
		this.pendingAvailableDate = pendingAvailableDate;
	}

	public String getPendingDeletion() {
		return this.pendingDeletion;
	}

	public void setPendingDeletion(String pendingDeletion) {
		this.pendingDeletion = pendingDeletion;
	}

	public BigDecimal getPof() {
		return this.pof;
	}

	public void setPof(BigDecimal pof) {
		this.pof = pof;
	}

	public String getPofIntgId() {
		return this.pofIntgId;
	}

	public void setPofIntgId(String pofIntgId) {
		this.pofIntgId = pofIntgId;
	}

	public String getPofName() {
		return this.pofName;
	}

	public void setPofName(String pofName) {
		this.pofName = pofName;
	}

	public BigDecimal getPrevAvailStatus() {
		return this.prevAvailStatus;
	}

	public void setPrevAvailStatus(BigDecimal prevAvailStatus) {
		this.prevAvailStatus = prevAvailStatus;
	}

	public BigDecimal getPrevInstallCbp() {
		return this.prevInstallCbp;
	}

	public void setPrevInstallCbp(BigDecimal prevInstallCbp) {
		this.prevInstallCbp = prevInstallCbp;
	}

	public BigDecimal getPrevInstallKva() {
		return this.prevInstallKva;
	}

	public void setPrevInstallKva(BigDecimal prevInstallKva) {
		this.prevInstallKva = prevInstallKva;
	}

	public BigDecimal getPreviousReservation() {
		return this.previousReservation;
	}

	public void setPreviousReservation(BigDecimal previousReservation) {
		this.previousReservation = previousReservation;
	}

	public BigDecimal getReasonForHold() {
		return this.reasonForHold;
	}

	public void setReasonForHold(BigDecimal reasonForHold) {
		this.reasonForHold = reasonForHold;
	}

	public BigDecimal getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(BigDecimal reservationId) {
		this.reservationId = reservationId;
	}

	public String getSharedRackUnits() {
		return this.sharedRackUnits;
	}

	public void setSharedRackUnits(String sharedRackUnits) {
		this.sharedRackUnits = sharedRackUnits;
	}

	public Timestamp getStatusChangeDate() {
		return this.statusChangeDate;
	}

	public void setStatusChangeDate(Timestamp statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public BigDecimal getUniqueSpaceid() {
		return this.uniqueSpaceid;
	}

	public void setUniqueSpaceid(BigDecimal uniqueSpaceid) {
		this.uniqueSpaceid = uniqueSpaceid;
	}

	public String getVDayOfWeek() {
		return this.vDayOfWeek;
	}

	public void setVDayOfWeek(String vDayOfWeek) {
		this.vDayOfWeek = vDayOfWeek;
	}

	public String getVerizonCustomerNumber() {
		return this.verizonCustomerNumber;
	}

	public void setVerizonCustomerNumber(String verizonCustomerNumber) {
		this.verizonCustomerNumber = verizonCustomerNumber;
	}

	public BigDecimal getVersion() {
		return this.version;
	}

	public void setVersion(BigDecimal version) {
		this.version = version;
	}

	public String getVirtualCabinet() {
		return this.virtualCabinet;
	}

	public void setVirtualCabinet(String virtualCabinet) {
		this.virtualCabinet = virtualCabinet;
	}

	public String getVirtualRackUnit() {
		return this.virtualRackUnit;
	}

	public void setVirtualRackUnit(String virtualRackUnit) {
		this.virtualRackUnit = virtualRackUnit;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public BigDecimal getWorkflowStatus() {
		return this.workflowStatus;
	}

	public void setWorkflowStatus(BigDecimal workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getWwcsFlag() {
		return this.wwcsFlag;
	}

	public void setWwcsFlag(String wwcsFlag) {
		this.wwcsFlag = wwcsFlag;
	}

	public BigDecimal getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

}