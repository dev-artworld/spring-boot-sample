package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the S_ASSET_XA database table.
 * 
 */
@Entity
@Table(name="S_ASSET_XA",schema="EQX_DART")
@NamedQuery(name="SAssetXa.findAll", query="SELECT s FROM SAssetXa s")
public class SAssetXa implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name="ASSET_ID")
	private String assetId;

	@Column(name="ASSET_NUM")
	private String assetNum;

	@Column(name="ATTR_ID")
	private String attrId;

	@Column(name="ATTR_NAME")
	private String attrName;

	@Column(name="CDC_CREATION_DT")
	private String cdcCreationDt;

	@Column(name="CFG_STATE_CD")
	private String cfgStateCd;

	@Column(name="CHAR_VAL")
	private String charVal;

	@Column(name="CONFLICT_ID")
	private String conflictId;

	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="DATA_TYPE_CD")
	private String dataTypeCd;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_VAL")
	private Date dateVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DB_LAST_UPD")
	private Date dbLastUpd;

	@Column(name="DB_LAST_UPD_SRC")
	private String dbLastUpdSrc;

	@Column(name="DESC_TEXT")
	private String descText;

	@Column(name="DFR_ID")
	private String dfrId;

	@Id
	@Column(name="DFR_LINE_ID")
	private String dfrLineId;

	@Column(name="DISPLAY_NAME")
	private String displayName;

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

	@Column(name="HDM_ID")
	private BigDecimal hdmId;

	@Column(name="HIDDEN_FLG")
	private String hiddenFlg;

	@Column(name="INTEGRATION_ID")
	private String integrationId;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="LAST_UPD_BY")
	private String lastUpdBy;

	@Column(name="MODIFICATION_NUM")
	private BigDecimal modificationNum;

	@Column(name="NUM_VAL")
	private BigDecimal numVal;

	@Column(name="READ_ONLY_FLG")
	private String readOnlyFlg;

	@Column(name="REQUIRED_FLG")
	private String requiredFlg;

	
	@Column(name="ROW_ID")
	private String rowId;

	@Column(name="SEQ_NUM")
	private BigDecimal seqNum;

	@Column(name="UOM_CD")
	private String uomCd;

	@Column(name="V_DAY_OF_WEEK")
	private String vDayOfWeek;

	@Column(name="VALIDATION_SPEC")
	private String validationSpec;

	@Column(name="VLDTN_LOV_TYPE_CD")
	private String vldtnLovTypeCd;

	@Column(name="X_ATTR_TYPE_CD")
	private String xAttrTypeCd;

	@Column(name="X_CMDB_VALUE")
	private String xCmdbValue;

	@Column(name="X_INT_STATUS")
	private String xIntStatus;

	@Column(name="X_PREV_CHAR_VAL")
	private String xPrevCharVal;

	@Temporal(TemporalType.DATE)
	@Column(name="X_PREV_DATE_VAL")
	private Date xPrevDateVal;

	@Column(name="X_PREV_NUM_VAL")
	private BigDecimal xPrevNumVal;

	@Column(name="X_PREV_STATUS")
	private String xPrevStatus;

	@Column(name="X_STATUS")
	private String xStatus;

	public SAssetXa() {
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetNum() {
		return this.assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public String getAttrId() {
		return this.attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getCdcCreationDt() {
		return this.cdcCreationDt;
	}

	public void setCdcCreationDt(String cdcCreationDt) {
		this.cdcCreationDt = cdcCreationDt;
	}

	public String getCfgStateCd() {
		return this.cfgStateCd;
	}

	public void setCfgStateCd(String cfgStateCd) {
		this.cfgStateCd = cfgStateCd;
	}

	public String getCharVal() {
		return this.charVal;
	}

	public void setCharVal(String charVal) {
		this.charVal = charVal;
	}

	public String getConflictId() {
		return this.conflictId;
	}

	public void setConflictId(String conflictId) {
		this.conflictId = conflictId;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDataTypeCd() {
		return this.dataTypeCd;
	}

	public void setDataTypeCd(String dataTypeCd) {
		this.dataTypeCd = dataTypeCd;
	}

	public Date getDateVal() {
		return this.dateVal;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public Date getDbLastUpd() {
		return this.dbLastUpd;
	}

	public void setDbLastUpd(Date dbLastUpd) {
		this.dbLastUpd = dbLastUpd;
	}

	public String getDbLastUpdSrc() {
		return this.dbLastUpdSrc;
	}

	public void setDbLastUpdSrc(String dbLastUpdSrc) {
		this.dbLastUpdSrc = dbLastUpdSrc;
	}

	public String getDescText() {
		return this.descText;
	}

	public void setDescText(String descText) {
		this.descText = descText;
	}

	public String getDfrId() {
		return this.dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getDfrLineId() {
		return this.dfrLineId;
	}

	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public BigDecimal getHdmId() {
		return this.hdmId;
	}

	public void setHdmId(BigDecimal hdmId) {
		this.hdmId = hdmId;
	}

	public String getHiddenFlg() {
		return this.hiddenFlg;
	}

	public void setHiddenFlg(String hiddenFlg) {
		this.hiddenFlg = hiddenFlg;
	}

	public String getIntegrationId() {
		return this.integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public Date getLastUpd() {
		return this.lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public BigDecimal getModificationNum() {
		return this.modificationNum;
	}

	public void setModificationNum(BigDecimal modificationNum) {
		this.modificationNum = modificationNum;
	}

	public BigDecimal getNumVal() {
		return this.numVal;
	}

	public void setNumVal(BigDecimal numVal) {
		this.numVal = numVal;
	}

	public String getReadOnlyFlg() {
		return this.readOnlyFlg;
	}

	public void setReadOnlyFlg(String readOnlyFlg) {
		this.readOnlyFlg = readOnlyFlg;
	}

	public String getRequiredFlg() {
		return this.requiredFlg;
	}

	public void setRequiredFlg(String requiredFlg) {
		this.requiredFlg = requiredFlg;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public BigDecimal getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(BigDecimal seqNum) {
		this.seqNum = seqNum;
	}

	public String getUomCd() {
		return this.uomCd;
	}

	public void setUomCd(String uomCd) {
		this.uomCd = uomCd;
	}

	public String getVDayOfWeek() {
		return this.vDayOfWeek;
	}

	public void setVDayOfWeek(String vDayOfWeek) {
		this.vDayOfWeek = vDayOfWeek;
	}

	public String getValidationSpec() {
		return this.validationSpec;
	}

	public void setValidationSpec(String validationSpec) {
		this.validationSpec = validationSpec;
	}

	public String getVldtnLovTypeCd() {
		return this.vldtnLovTypeCd;
	}

	public void setVldtnLovTypeCd(String vldtnLovTypeCd) {
		this.vldtnLovTypeCd = vldtnLovTypeCd;
	}

	public String getXAttrTypeCd() {
		return this.xAttrTypeCd;
	}

	public void setXAttrTypeCd(String xAttrTypeCd) {
		this.xAttrTypeCd = xAttrTypeCd;
	}

	public String getXCmdbValue() {
		return this.xCmdbValue;
	}

	public void setXCmdbValue(String xCmdbValue) {
		this.xCmdbValue = xCmdbValue;
	}

	public String getXIntStatus() {
		return this.xIntStatus;
	}

	public void setXIntStatus(String xIntStatus) {
		this.xIntStatus = xIntStatus;
	}

	public String getXPrevCharVal() {
		return this.xPrevCharVal;
	}

	public void setXPrevCharVal(String xPrevCharVal) {
		this.xPrevCharVal = xPrevCharVal;
	}

	public Date getXPrevDateVal() {
		return this.xPrevDateVal;
	}

	public void setXPrevDateVal(Date xPrevDateVal) {
		this.xPrevDateVal = xPrevDateVal;
	}

	public BigDecimal getXPrevNumVal() {
		return this.xPrevNumVal;
	}

	public void setXPrevNumVal(BigDecimal xPrevNumVal) {
		this.xPrevNumVal = xPrevNumVal;
	}

	public String getXPrevStatus() {
		return this.xPrevStatus;
	}

	public void setXPrevStatus(String xPrevStatus) {
		this.xPrevStatus = xPrevStatus;
	}

	public String getXStatus() {
		return this.xStatus;
	}

	public void setXStatus(String xStatus) {
		this.xStatus = xStatus;
	}

}