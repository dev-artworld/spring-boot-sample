package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SRC_EQX_CL_CAGE database table.
 * 
 */
@Entity
@Table(name="SRC_EQX_CL_CAGE", schema="EQX_DART")
@NamedQuery(name="SrcEqxClCage.findAll", query="SELECT s FROM SrcEqxClCage s")
public class SrcEqxClCage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CAGE_ID")
	private String cageId;

	@Column(name="ACT_CABINET")
	private Long actCabinet;

	@Column(name="AGGREGATE_CBP_INSTALLED")
	private Long aggregateCbpInstalled;

	@Column(name="AGGREGATE_KVA_INSTALLED")
	private Long aggregateKvaInstalled;

	@Column(name="ASSET_ID")
	private String assetId;

	@Column(name="AUTOCAD_TAG")
	private String autocadTag;

	@Column(name="CABE_THRESHOLD_LEVEL")
	private String cabeThresholdLevel;

	@Column(name="CABE_THRESHOLD_MAX")
	private Long cabeThresholdMax;

	@Column(name="CABE_THRESHOLD_MIN")
	private Long cabeThresholdMin;

	@Column(name="CABE_THRESHOLD_VAL_MAX")
	private Long cabeThresholdValMax;

	@Column(name="CABE_THRESHOLD_VAL_MIN")
	private Long cabeThresholdValMin;

	@Column(name="CAGE_AREA_SQFT")
	private Long cageAreaSqft;

	@Column(name="CAGE_AREA_SQMT")
	private Long cageAreaSqmt;

	@Column(name="CAGE_AT_MAX_DRAW_ID")
	private String cageAtMaxDrawId;

	@Column(name="CAGE_AUDITED")
	private String cageAudited;

	@Column(name="CAGE_DESIGNED_KVA")
	private Long cageDesignedKva;

	@Column(name="CAGE_DESIGNED_PEC")
	private Long cageDesignedPec;

	@Column(name="CAGE_KWH")
	private Long cageKwh;

	@Column(name="CAGE_LEGACY_NUMBER")
	private String cageLegacyNumber;

	@Column(name="CAGE_LOCATION")
	private Long cageLocation;

	@Column(name="CAGE_NUM")
	private String cageNum;

	@Column(name="CAGESALES_SITE_RATINGFROM")
	private Long cagesalesSiteRatingfrom;

	@Column(name="CAGESALES_SITE_RATINGTO")
	private Long cagesalesSiteRatingto;

	@Column(name="CAPACITY_STATUS_ID")
	private Long capacityStatusId;

	@Column(name="CDC_CREATION_DT")
	private String cdcCreationDt;

	@Column(name="CHURN_CAGE_LOCATION")
	private Long churnCageLocation;

	@Column(name="CHURN_CONTRACTED_CABE")
	private Long churnContractedCabe;

	@Column(name="CHURN_CONTRACTED_KVA")
	private Long churnContractedKva;

	@Column(name="CHURN_CUSTOMER")
	private Long churnCustomer;

	@Column(name="CHURN_DIRECT_ACCESS_CC")
	private String churnDirectAccessCc;

	@Column(name="CHURN_EQUINIX_CAGE_TYPE")
	private Long churnEquinixCageType;

	@Column(name="CHURN_EQUINIX_DEPT_TYPE")
	private Long churnEquinixDeptType;

	@Column(name="CHURN_LOCATION")
	private String churnLocation;

	@Column(name="CHURN_POF")
	private Long churnPof;

	@Column(name="CHURN_PREV_CAP_STATUS")
	private Long churnPrevCapStatus;

	@Column(name="CHURN_RESV_ID")
	private Long churnResvId;

	@Column(name="CIRCUIT_BRK_POS_CAP")
	private Long circuitBrkPosCap;

	@Column(name="CIRCUIT_BRK_POS_INSTALLED")
	private Long circuitBrkPosInstalled;

	@Column(name="CIRCUIT_TYPE")
	private String circuitType;

	@Column(name="CONFIGURABLE_CABINET")
	private Long configurableCabinet;

	@Column(name="CONTRACTED_CABE")
	private Long contractedCabe;

	@Column(name="CONTRACTED_KVA")
	private Long contractedKva;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="CUSTOMER_ID")
	private Long customerId;

	@Column(name="DC_CIRCUITS_INSTALLED")
	private String dcCircuitsInstalled;

	@Column(name="DESGN_CABINET")
	private Long desgnCabinet;

	@Column(name="DIRECT_ACCESS_CROSS_CONNECT")
	private String directAccessCrossConnect;

	@Column(name="DRAW_CAP_KVA")
	private Long drawCapKva;

	@Column(name="DRAWCAP_POW_VALIDATIONS")
	private Long drawcapPowValidations;

	@Column(name="DRAWCAP_THRESHOLD_LEVEL")
	private String drawcapThresholdLevel;

	@Column(name="DRAWCAP_THRESHOLD_MAX")
	private Long drawcapThresholdMax;

	@Column(name="DRAWCAP_THRESHOLD_MIN")
	private Long drawcapThresholdMin;

	@Column(name="DRAWCAP_THRESHOLD_VAL_MAX")
	private Long drawcapThresholdValMax;

	@Column(name="DRAWCAP_THRESHOLD_VAL_MIN")
	private Long drawcapThresholdValMin;

	@Column(name="DRAWCAPKW_THRESHOLD_VAL_MAX")
	private Long drawcapkwThresholdValMax;

	@Column(name="DRAWCAPKW_THRESHOLD_VAL_MIN")
	private Long drawcapkwThresholdValMin;

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

	@Column(name="EQUINIX_CAGE_TYPE")
	private Long equinixCageType;

	@Column(name="EQUINIX_DEPT_TYPE")
	private Long equinixDeptType;

	@Column(name="FLOOR_ID")
	private Long floorId;

	@Column(name="IBX_ID")
	private Long ibxId;

	@Column(name="INSTALL_DATE")
	private Timestamp installDate;

	@Column(name="INSTALLED_CABINET")
	private Long installedCabinet;

	@Column(name="IS_CABE_VALID")
	private String isCabeValid;

	@Column(name="IS_CBP_EDITED_VIA_CFR")
	private String isCbpEditedViaCfr;

	@Column(name="IS_CUSTOMER_VALID")
	private String isCustomerValid;

	@Column(name="IS_DRAW_CAP_VALID")
	private String isDrawCapValid;

	@Column(name="IS_EDITABLE")
	private String isEditable;

	@Column(name="KVA_INSTALLED_ATCAGE")
	private Long kvaInstalledAtcage;

	@Column(name="KVA_PER_CAB")
	private Long kvaPerCab;

	@Column(name="KVA_PERCAB_INSTALLED")
	private Long kvaPercabInstalled;

	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name="LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name="LEASED_SPACE_ID")
	private String leasedSpaceId;

	@Column(name="LEASED_SPACE_LINKED")
	private String leasedSpaceLinked;

	@Column(name="LEGACY_SYSTEM_NAME")
	private String legacySystemName;

	@Column(name="LINKED_PHYSICAL_CAGE")
	private Long linkedPhysicalCage;

	private String location;

	@Column(name="LOCKED_STATUS")
	private Long lockedStatus;

	@Column(name="MAX_CABS_PER_ROW")
	private Long maxCabsPerRow;

	@Column(name="MAX_NO_COND_CIRCUITS")
	private Long maxNoCondCircuits;

	@Column(name="MAX_NO_UNCOND_CIRCUITS")
	private Long maxNoUncondCircuits;

	@Column(name="MAX_NUM_ROWS")
	private Long maxNumRows;

	@Column(name="MINIMUM_COMMITMENT")
	private Long minimumCommitment;

	@Column(name="NO_COND_CIRCUITS_INSTALLED")
	private Long noCondCircuitsInstalled;

	@Column(name="NO_UNCOND_CIRCUITS_INSTALLED")
	private Long noUncondCircuitsInstalled;

	private String notes;

	@Column(name="OPPORTUNITY_ID")
	private String opportunityId;

	private String pec;

	@Column(name="PEC_CAP")
	private Long pecCap;

	@Column(name="PEND_AVAIL_DATE_COMMENTS")
	private String pendAvailDateComments;

	@Column(name="PENDING_AVAILABLE_DATE")
	private Timestamp pendingAvailableDate;

	@Column(name="PHASE_ID")
	private Long phaseId;

	private Long pof;

	@Column(name="POF_NAME")
	private String pofName;

	@Column(name="PREV_AVAIL_STATUS")
	private Long prevAvailStatus;

	@Column(name="PREV_INSTALL_CBP")
	private Long prevInstallCbp;

	@Column(name="PREV_INSTALL_KVA")
	private Long prevInstallKva;

	@Column(name="PREVIOUS_RESERVATION")
	private Long previousReservation;

	@Column(name="PRIMARY_DRAW_REC_DATE")
	private Timestamp primaryDrawRecDate;

	@Column(name="PRODUCT_TYPE_ID")
	private Long productTypeId;

	@Column(name="PUE_CAP")
	private Long pueCap;

	@Column(name="REASON_FOR_HOLD")
	private Long reasonForHold;

	@Column(name="RESERVATION_ID")
	private Long reservationId;

	@Column(name="ROOM_ID")
	private Long roomId;

	@Column(name="SHARED_RACK_UNITS")
	private String sharedRackUnits;

	@Column(name="SPACE_TYPE")
	private String spaceType;

	private String status;

	@Column(name="STATUS_CHANGE_DATE")
	private Timestamp statusChangeDate;

	@Column(name="SUB_CUSTOMER")
	private String subCustomer;

	@Column(name="UNIQUE_SPACEID")
	private Long uniqueSpaceid;

	@Column(name="\"USAGE\"")
	private String usage;

	@Column(name="V_DAY_OF_WEEK")
	private String vDayOfWeek;

	@Column(name="VERIZON_CUSTOMER_NUMBER")
	private String verizonCustomerNumber;

	@Column(name="\"VERSION\"")
	private Long version;

	@Column(name="VIRTUAL_CAGE")
	private String virtualCage;

	private Long weight;

	@Column(name="WORKFLOW_STATUS_ID")
	private Long workflowStatusId;

	@Column(name="WORKSPACE_TYPE_ID")
	private Long workspaceTypeId;

	@Column(name="WWCS_FLAG")
	private String wwcsFlag;

	@Column(name="ZONE_ID")
	private Long zoneId;

	public SrcEqxClCage() {
	}

	public String getCageId() {
		return this.cageId;
	}

	public void setCageId(String cageId) {
		this.cageId = cageId;
	}

	public Long getActCabinet() {
		return this.actCabinet;
	}

	public void setActCabinet(Long actCabinet) {
		this.actCabinet = actCabinet;
	}

	public Long getAggregateCbpInstalled() {
		return this.aggregateCbpInstalled;
	}

	public void setAggregateCbpInstalled(Long aggregateCbpInstalled) {
		this.aggregateCbpInstalled = aggregateCbpInstalled;
	}

	public Long getAggregateKvaInstalled() {
		return this.aggregateKvaInstalled;
	}

	public void setAggregateKvaInstalled(Long aggregateKvaInstalled) {
		this.aggregateKvaInstalled = aggregateKvaInstalled;
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

	public String getCabeThresholdLevel() {
		return this.cabeThresholdLevel;
	}

	public void setCabeThresholdLevel(String cabeThresholdLevel) {
		this.cabeThresholdLevel = cabeThresholdLevel;
	}

	public Long getCabeThresholdMax() {
		return this.cabeThresholdMax;
	}

	public void setCabeThresholdMax(Long cabeThresholdMax) {
		this.cabeThresholdMax = cabeThresholdMax;
	}

	public Long getCabeThresholdMin() {
		return this.cabeThresholdMin;
	}

	public void setCabeThresholdMin(Long cabeThresholdMin) {
		this.cabeThresholdMin = cabeThresholdMin;
	}

	public Long getCabeThresholdValMax() {
		return this.cabeThresholdValMax;
	}

	public void setCabeThresholdValMax(Long cabeThresholdValMax) {
		this.cabeThresholdValMax = cabeThresholdValMax;
	}

	public Long getCabeThresholdValMin() {
		return this.cabeThresholdValMin;
	}

	public void setCabeThresholdValMin(Long cabeThresholdValMin) {
		this.cabeThresholdValMin = cabeThresholdValMin;
	}

	public Long getCageAreaSqft() {
		return this.cageAreaSqft;
	}

	public void setCageAreaSqft(Long cageAreaSqft) {
		this.cageAreaSqft = cageAreaSqft;
	}

	public Long getCageAreaSqmt() {
		return this.cageAreaSqmt;
	}

	public void setCageAreaSqmt(Long cageAreaSqmt) {
		this.cageAreaSqmt = cageAreaSqmt;
	}

	public String getCageAtMaxDrawId() {
		return this.cageAtMaxDrawId;
	}

	public void setCageAtMaxDrawId(String cageAtMaxDrawId) {
		this.cageAtMaxDrawId = cageAtMaxDrawId;
	}

	public String getCageAudited() {
		return this.cageAudited;
	}

	public void setCageAudited(String cageAudited) {
		this.cageAudited = cageAudited;
	}

	public Long getCageDesignedKva() {
		return this.cageDesignedKva;
	}

	public void setCageDesignedKva(Long cageDesignedKva) {
		this.cageDesignedKva = cageDesignedKva;
	}

	public Long getCageDesignedPec() {
		return this.cageDesignedPec;
	}

	public void setCageDesignedPec(Long cageDesignedPec) {
		this.cageDesignedPec = cageDesignedPec;
	}

	public Long getCageKwh() {
		return this.cageKwh;
	}

	public void setCageKwh(Long cageKwh) {
		this.cageKwh = cageKwh;
	}

	public String getCageLegacyNumber() {
		return this.cageLegacyNumber;
	}

	public void setCageLegacyNumber(String cageLegacyNumber) {
		this.cageLegacyNumber = cageLegacyNumber;
	}

	public Long getCageLocation() {
		return this.cageLocation;
	}

	public void setCageLocation(Long cageLocation) {
		this.cageLocation = cageLocation;
	}

	public String getCageNum() {
		return this.cageNum;
	}

	public void setCageNum(String cageNum) {
		this.cageNum = cageNum;
	}

	public Long getCagesalesSiteRatingfrom() {
		return this.cagesalesSiteRatingfrom;
	}

	public void setCagesalesSiteRatingfrom(Long cagesalesSiteRatingfrom) {
		this.cagesalesSiteRatingfrom = cagesalesSiteRatingfrom;
	}

	public Long getCagesalesSiteRatingto() {
		return this.cagesalesSiteRatingto;
	}

	public void setCagesalesSiteRatingto(Long cagesalesSiteRatingto) {
		this.cagesalesSiteRatingto = cagesalesSiteRatingto;
	}

	public Long getCapacityStatusId() {
		return this.capacityStatusId;
	}

	public void setCapacityStatusId(Long capacityStatusId) {
		this.capacityStatusId = capacityStatusId;
	}

	public String getCdcCreationDt() {
		return this.cdcCreationDt;
	}

	public void setCdcCreationDt(String cdcCreationDt) {
		this.cdcCreationDt = cdcCreationDt;
	}

	public Long getChurnCageLocation() {
		return this.churnCageLocation;
	}

	public void setChurnCageLocation(Long churnCageLocation) {
		this.churnCageLocation = churnCageLocation;
	}

	public Long getChurnContractedCabe() {
		return this.churnContractedCabe;
	}

	public void setChurnContractedCabe(Long churnContractedCabe) {
		this.churnContractedCabe = churnContractedCabe;
	}

	public Long getChurnContractedKva() {
		return this.churnContractedKva;
	}

	public void setChurnContractedKva(Long churnContractedKva) {
		this.churnContractedKva = churnContractedKva;
	}

	public Long getChurnCustomer() {
		return this.churnCustomer;
	}

	public void setChurnCustomer(Long churnCustomer) {
		this.churnCustomer = churnCustomer;
	}

	public String getChurnDirectAccessCc() {
		return this.churnDirectAccessCc;
	}

	public void setChurnDirectAccessCc(String churnDirectAccessCc) {
		this.churnDirectAccessCc = churnDirectAccessCc;
	}

	public Long getChurnEquinixCageType() {
		return this.churnEquinixCageType;
	}

	public void setChurnEquinixCageType(Long churnEquinixCageType) {
		this.churnEquinixCageType = churnEquinixCageType;
	}

	public Long getChurnEquinixDeptType() {
		return this.churnEquinixDeptType;
	}

	public void setChurnEquinixDeptType(Long churnEquinixDeptType) {
		this.churnEquinixDeptType = churnEquinixDeptType;
	}

	public String getChurnLocation() {
		return this.churnLocation;
	}

	public void setChurnLocation(String churnLocation) {
		this.churnLocation = churnLocation;
	}

	public Long getChurnPof() {
		return this.churnPof;
	}

	public void setChurnPof(Long churnPof) {
		this.churnPof = churnPof;
	}

	public Long getChurnPrevCapStatus() {
		return this.churnPrevCapStatus;
	}

	public void setChurnPrevCapStatus(Long churnPrevCapStatus) {
		this.churnPrevCapStatus = churnPrevCapStatus;
	}

	public Long getChurnResvId() {
		return this.churnResvId;
	}

	public void setChurnResvId(Long churnResvId) {
		this.churnResvId = churnResvId;
	}

	public Long getCircuitBrkPosCap() {
		return this.circuitBrkPosCap;
	}

	public void setCircuitBrkPosCap(Long circuitBrkPosCap) {
		this.circuitBrkPosCap = circuitBrkPosCap;
	}

	public Long getCircuitBrkPosInstalled() {
		return this.circuitBrkPosInstalled;
	}

	public void setCircuitBrkPosInstalled(Long circuitBrkPosInstalled) {
		this.circuitBrkPosInstalled = circuitBrkPosInstalled;
	}

	public String getCircuitType() {
		return this.circuitType;
	}

	public void setCircuitType(String circuitType) {
		this.circuitType = circuitType;
	}

	public Long getConfigurableCabinet() {
		return this.configurableCabinet;
	}

	public void setConfigurableCabinet(Long configurableCabinet) {
		this.configurableCabinet = configurableCabinet;
	}

	public Long getContractedCabe() {
		return this.contractedCabe;
	}

	public void setContractedCabe(Long contractedCabe) {
		this.contractedCabe = contractedCabe;
	}

	public Long getContractedKva() {
		return this.contractedKva;
	}

	public void setContractedKva(Long contractedKva) {
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

	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDcCircuitsInstalled() {
		return this.dcCircuitsInstalled;
	}

	public void setDcCircuitsInstalled(String dcCircuitsInstalled) {
		this.dcCircuitsInstalled = dcCircuitsInstalled;
	}

	public Long getDesgnCabinet() {
		return this.desgnCabinet;
	}

	public void setDesgnCabinet(Long desgnCabinet) {
		this.desgnCabinet = desgnCabinet;
	}

	public String getDirectAccessCrossConnect() {
		return this.directAccessCrossConnect;
	}

	public void setDirectAccessCrossConnect(String directAccessCrossConnect) {
		this.directAccessCrossConnect = directAccessCrossConnect;
	}

	public Long getDrawCapKva() {
		return this.drawCapKva;
	}

	public void setDrawCapKva(Long drawCapKva) {
		this.drawCapKva = drawCapKva;
	}

	public Long getDrawcapPowValidations() {
		return this.drawcapPowValidations;
	}

	public void setDrawcapPowValidations(Long drawcapPowValidations) {
		this.drawcapPowValidations = drawcapPowValidations;
	}

	public String getDrawcapThresholdLevel() {
		return this.drawcapThresholdLevel;
	}

	public void setDrawcapThresholdLevel(String drawcapThresholdLevel) {
		this.drawcapThresholdLevel = drawcapThresholdLevel;
	}

	public Long getDrawcapThresholdMax() {
		return this.drawcapThresholdMax;
	}

	public void setDrawcapThresholdMax(Long drawcapThresholdMax) {
		this.drawcapThresholdMax = drawcapThresholdMax;
	}

	public Long getDrawcapThresholdMin() {
		return this.drawcapThresholdMin;
	}

	public void setDrawcapThresholdMin(Long drawcapThresholdMin) {
		this.drawcapThresholdMin = drawcapThresholdMin;
	}

	public Long getDrawcapThresholdValMax() {
		return this.drawcapThresholdValMax;
	}

	public void setDrawcapThresholdValMax(Long drawcapThresholdValMax) {
		this.drawcapThresholdValMax = drawcapThresholdValMax;
	}

	public Long getDrawcapThresholdValMin() {
		return this.drawcapThresholdValMin;
	}

	public void setDrawcapThresholdValMin(Long drawcapThresholdValMin) {
		this.drawcapThresholdValMin = drawcapThresholdValMin;
	}

	public Long getDrawcapkwThresholdValMax() {
		return this.drawcapkwThresholdValMax;
	}

	public void setDrawcapkwThresholdValMax(Long drawcapkwThresholdValMax) {
		this.drawcapkwThresholdValMax = drawcapkwThresholdValMax;
	}

	public Long getDrawcapkwThresholdValMin() {
		return this.drawcapkwThresholdValMin;
	}

	public void setDrawcapkwThresholdValMin(Long drawcapkwThresholdValMin) {
		this.drawcapkwThresholdValMin = drawcapkwThresholdValMin;
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

	public Long getEquinixCageType() {
		return this.equinixCageType;
	}

	public void setEquinixCageType(Long equinixCageType) {
		this.equinixCageType = equinixCageType;
	}

	public Long getEquinixDeptType() {
		return this.equinixDeptType;
	}

	public void setEquinixDeptType(Long equinixDeptType) {
		this.equinixDeptType = equinixDeptType;
	}

	public Long getFloorId() {
		return this.floorId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public Long getIbxId() {
		return this.ibxId;
	}

	public void setIbxId(Long ibxId) {
		this.ibxId = ibxId;
	}

	public Timestamp getInstallDate() {
		return this.installDate;
	}

	public void setInstallDate(Timestamp installDate) {
		this.installDate = installDate;
	}

	public Long getInstalledCabinet() {
		return this.installedCabinet;
	}

	public void setInstalledCabinet(Long installedCabinet) {
		this.installedCabinet = installedCabinet;
	}

	public String getIsCabeValid() {
		return this.isCabeValid;
	}

	public void setIsCabeValid(String isCabeValid) {
		this.isCabeValid = isCabeValid;
	}

	public String getIsCbpEditedViaCfr() {
		return this.isCbpEditedViaCfr;
	}

	public void setIsCbpEditedViaCfr(String isCbpEditedViaCfr) {
		this.isCbpEditedViaCfr = isCbpEditedViaCfr;
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

	public String getIsEditable() {
		return this.isEditable;
	}

	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}

	public Long getKvaInstalledAtcage() {
		return this.kvaInstalledAtcage;
	}

	public void setKvaInstalledAtcage(Long kvaInstalledAtcage) {
		this.kvaInstalledAtcage = kvaInstalledAtcage;
	}

	public Long getKvaPerCab() {
		return this.kvaPerCab;
	}

	public void setKvaPerCab(Long kvaPerCab) {
		this.kvaPerCab = kvaPerCab;
	}

	public Long getKvaPercabInstalled() {
		return this.kvaPercabInstalled;
	}

	public void setKvaPercabInstalled(Long kvaPercabInstalled) {
		this.kvaPercabInstalled = kvaPercabInstalled;
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

	public String getLegacySystemName() {
		return this.legacySystemName;
	}

	public void setLegacySystemName(String legacySystemName) {
		this.legacySystemName = legacySystemName;
	}

	public Long getLinkedPhysicalCage() {
		return this.linkedPhysicalCage;
	}

	public void setLinkedPhysicalCage(Long linkedPhysicalCage) {
		this.linkedPhysicalCage = linkedPhysicalCage;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getLockedStatus() {
		return this.lockedStatus;
	}

	public void setLockedStatus(Long lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	public Long getMaxCabsPerRow() {
		return this.maxCabsPerRow;
	}

	public void setMaxCabsPerRow(Long maxCabsPerRow) {
		this.maxCabsPerRow = maxCabsPerRow;
	}

	public Long getMaxNoCondCircuits() {
		return this.maxNoCondCircuits;
	}

	public void setMaxNoCondCircuits(Long maxNoCondCircuits) {
		this.maxNoCondCircuits = maxNoCondCircuits;
	}

	public Long getMaxNoUncondCircuits() {
		return this.maxNoUncondCircuits;
	}

	public void setMaxNoUncondCircuits(Long maxNoUncondCircuits) {
		this.maxNoUncondCircuits = maxNoUncondCircuits;
	}

	public Long getMaxNumRows() {
		return this.maxNumRows;
	}

	public void setMaxNumRows(Long maxNumRows) {
		this.maxNumRows = maxNumRows;
	}

	public Long getMinimumCommitment() {
		return this.minimumCommitment;
	}

	public void setMinimumCommitment(Long minimumCommitment) {
		this.minimumCommitment = minimumCommitment;
	}

	public Long getNoCondCircuitsInstalled() {
		return this.noCondCircuitsInstalled;
	}

	public void setNoCondCircuitsInstalled(Long noCondCircuitsInstalled) {
		this.noCondCircuitsInstalled = noCondCircuitsInstalled;
	}

	public Long getNoUncondCircuitsInstalled() {
		return this.noUncondCircuitsInstalled;
	}

	public void setNoUncondCircuitsInstalled(Long noUncondCircuitsInstalled) {
		this.noUncondCircuitsInstalled = noUncondCircuitsInstalled;
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

	public String getPec() {
		return this.pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public Long getPecCap() {
		return this.pecCap;
	}

	public void setPecCap(Long pecCap) {
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

	public Long getPhaseId() {
		return this.phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public Long getPof() {
		return this.pof;
	}

	public void setPof(Long pof) {
		this.pof = pof;
	}

	public String getPofName() {
		return this.pofName;
	}

	public void setPofName(String pofName) {
		this.pofName = pofName;
	}

	public Long getPrevAvailStatus() {
		return this.prevAvailStatus;
	}

	public void setPrevAvailStatus(Long prevAvailStatus) {
		this.prevAvailStatus = prevAvailStatus;
	}

	public Long getPrevInstallCbp() {
		return this.prevInstallCbp;
	}

	public void setPrevInstallCbp(Long prevInstallCbp) {
		this.prevInstallCbp = prevInstallCbp;
	}

	public Long getPrevInstallKva() {
		return this.prevInstallKva;
	}

	public void setPrevInstallKva(Long prevInstallKva) {
		this.prevInstallKva = prevInstallKva;
	}

	public Long getPreviousReservation() {
		return this.previousReservation;
	}

	public void setPreviousReservation(Long previousReservation) {
		this.previousReservation = previousReservation;
	}

	public Timestamp getPrimaryDrawRecDate() {
		return this.primaryDrawRecDate;
	}

	public void setPrimaryDrawRecDate(Timestamp primaryDrawRecDate) {
		this.primaryDrawRecDate = primaryDrawRecDate;
	}

	public Long getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Long getPueCap() {
		return this.pueCap;
	}

	public void setPueCap(Long pueCap) {
		this.pueCap = pueCap;
	}

	public Long getReasonForHold() {
		return this.reasonForHold;
	}

	public void setReasonForHold(Long reasonForHold) {
		this.reasonForHold = reasonForHold;
	}

	public Long getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getSharedRackUnits() {
		return this.sharedRackUnits;
	}

	public void setSharedRackUnits(String sharedRackUnits) {
		this.sharedRackUnits = sharedRackUnits;
	}

	public String getSpaceType() {
		return this.spaceType;
	}

	public void setSpaceType(String spaceType) {
		this.spaceType = spaceType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStatusChangeDate() {
		return this.statusChangeDate;
	}

	public void setStatusChangeDate(Timestamp statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public String getSubCustomer() {
		return this.subCustomer;
	}

	public void setSubCustomer(String subCustomer) {
		this.subCustomer = subCustomer;
	}

	public Long getUniqueSpaceid() {
		return this.uniqueSpaceid;
	}

	public void setUniqueSpaceid(Long uniqueSpaceid) {
		this.uniqueSpaceid = uniqueSpaceid;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
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

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getVirtualCage() {
		return this.virtualCage;
	}

	public void setVirtualCage(String virtualCage) {
		this.virtualCage = virtualCage;
	}

	public Long getWeight() {
		return this.weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getWorkflowStatusId() {
		return this.workflowStatusId;
	}

	public void setWorkflowStatusId(Long workflowStatusId) {
		this.workflowStatusId = workflowStatusId;
	}

	public Long getWorkspaceTypeId() {
		return this.workspaceTypeId;
	}

	public void setWorkspaceTypeId(Long workspaceTypeId) {
		this.workspaceTypeId = workspaceTypeId;
	}

	public String getWwcsFlag() {
		return this.wwcsFlag;
	}

	public void setWwcsFlag(String wwcsFlag) {
		this.wwcsFlag = wwcsFlag;
	}

	public Long getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

}