package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.equinix.appops.dart.portal.entity.mservices.MsAttributeConfig;


/**
 * The persistent class for the SRC_S_LST_OF_VAL database table.
 * 
 */
@Entity
@Table(name="SRC_S_LST_OF_VAL", schema="EQX_DART")
@NamedQuery(name="SrcSLstOfVal.findAll", query="SELECT s FROM SrcSLstOfVal s")
public class SrcSLstOfVal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW_ID")
	private String rowId;

	@Column(name="ACTIVE_FLG")
	private String activeFlg;

	@Column(name="BITMAP_ID")
	private String bitmapId;

	@Column(name="BU_ID")
	private String buId;

	@Column(name="CDC_CREATION_DT")
	private String cdcCreationDt;

	private String code;

	@Column(name="CONFLICT_ID")
	private String conflictId;

	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="DB_LAST_UPD")
	private Date dbLastUpd;

	@Column(name="DB_LAST_UPD_SRC")
	private String dbLastUpdSrc;

	@Column(name="DCKING_NUM")
	private BigDecimal dckingNum;

	@Column(name="DESC_TEXT")
	private String descText;

	@Column(name="DFLT_LIC_FLG")
	private String dfltLicFlg;

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

	private String high;

	@Column(name="INTEGRATION_ID")
	private String integrationId;

	@Column(name="LANG_ID")
	private String langId;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD")
	private Date lastUpd;

	@Column(name="LAST_UPD_BY")
	private String lastUpdBy;

	private String low;

	@Column(name="MLTORG_DISALW_FLG")
	private String mltorgDisalwFlg;

	@Column(name="MODIFIABLE_FLG")
	private String modifiableFlg;

	@Column(name="MODIFICATION_NUM")
	private BigDecimal modificationNum;

	@Column(name="MULTI_LINGUAL_FLG")
	private String multiLingualFlg;

	private String name;

	@Column(name="ORDER_BY")
	private BigDecimal orderBy;

	@Column(name="REQD_LIC_FLG")
	private String reqdLicFlg;

	@Column(name="RPLCTN_LVL_CD")
	private String rplctnLvlCd;

	@Column(name="SUB_TYPE")
	private String subType;

	@Column(name="TARGET_HIGH")
	private BigDecimal targetHigh;

	@Column(name="TARGET_LOW")
	private BigDecimal targetLow;

	@Column(name="TRANSLATE_FLG")
	private String translateFlg;

	@Column(name="\"TYPE\"")
	private String type;

	@Column(name="V_DAY_OF_WEEK")
	private String vDayOfWeek;

	private String val;

	@Column(name="WEIGHTING_FACTOR")
	private BigDecimal weightingFactor;

	@Column(name="WS_ID")
	private String wsId;

	@Column(name="WS_INACTIVE_FLG")
	private String wsInactiveFlg;

	@Column(name="WS_MAX_VER")
	private BigDecimal wsMaxVer;

	@Column(name="WS_MIN_VER")
	private BigDecimal wsMinVer;

	@Column(name="WS_SRC_ID")
	private String wsSrcId;

	@Column(name="X_ATTRIB_01")
	private String xAttrib01;

	@Column(name="X_ATTRIB_02")
	private String xAttrib02;

	@Column(name="X_ATTRIB_03")
	private String xAttrib03;

	@Column(name="X_ATTRIB_04")
	private String xAttrib04;

	@Column(name="X_ATTRIB_05")
	private String xAttrib05;

	@Column(name="X_ATTRIB_06")
	private String xAttrib06;

	@Column(name="X_ATTRIB_07")
	private String xAttrib07;

	@Column(name="X_ATTRIB_08")
	private String xAttrib08;

	@Column(name="X_ATTRIB_09")
	private String xAttrib09;

	@Column(name="X_ATTRIB_10")
	private String xAttrib10;

	@Column(name="X_MACD_ACTION_CODE")
	private String xMacdActionCode;

	@Column(name="X_MACD_IMPACT")
	private String xMacdImpact;

	@Column(name="X_SEARCH")
	private String xSearch;

	//bi-directional many-to-one association to AttributeConfig
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="PAR_ROW_ID")
	private AttributeConfig attributeConfig;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="MS_PAR_ROW_ID")
	private MsAttributeConfig msAttributeConfig;

	public SrcSLstOfVal() {
	}

	public MsAttributeConfig getMsAttributeConfig() {
		return msAttributeConfig;
	}

	public void setMsAttributeConfig(MsAttributeConfig msAttributeConfig) {
		this.msAttributeConfig = msAttributeConfig;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getActiveFlg() {
		return this.activeFlg;
	}

	public void setActiveFlg(String activeFlg) {
		this.activeFlg = activeFlg;
	}

	public String getBitmapId() {
		return this.bitmapId;
	}

	public void setBitmapId(String bitmapId) {
		this.bitmapId = bitmapId;
	}

	public String getBuId() {
		return this.buId;
	}

	public void setBuId(String buId) {
		this.buId = buId;
	}

	public String getCdcCreationDt() {
		return this.cdcCreationDt;
	}

	public void setCdcCreationDt(String cdcCreationDt) {
		this.cdcCreationDt = cdcCreationDt;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public BigDecimal getDckingNum() {
		return this.dckingNum;
	}

	public void setDckingNum(BigDecimal dckingNum) {
		this.dckingNum = dckingNum;
	}

	public String getDescText() {
		return this.descText;
	}

	public void setDescText(String descText) {
		this.descText = descText;
	}

	public String getDfltLicFlg() {
		return this.dfltLicFlg;
	}

	public void setDfltLicFlg(String dfltLicFlg) {
		this.dfltLicFlg = dfltLicFlg;
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

	public String getHigh() {
		return this.high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getIntegrationId() {
		return this.integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getLangId() {
		return this.langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
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

	public String getLow() {
		return this.low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getMltorgDisalwFlg() {
		return this.mltorgDisalwFlg;
	}

	public void setMltorgDisalwFlg(String mltorgDisalwFlg) {
		this.mltorgDisalwFlg = mltorgDisalwFlg;
	}

	public String getModifiableFlg() {
		return this.modifiableFlg;
	}

	public void setModifiableFlg(String modifiableFlg) {
		this.modifiableFlg = modifiableFlg;
	}

	public BigDecimal getModificationNum() {
		return this.modificationNum;
	}

	public void setModificationNum(BigDecimal modificationNum) {
		this.modificationNum = modificationNum;
	}

	public String getMultiLingualFlg() {
		return this.multiLingualFlg;
	}

	public void setMultiLingualFlg(String multiLingualFlg) {
		this.multiLingualFlg = multiLingualFlg;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(BigDecimal orderBy) {
		this.orderBy = orderBy;
	}

	public String getReqdLicFlg() {
		return this.reqdLicFlg;
	}

	public void setReqdLicFlg(String reqdLicFlg) {
		this.reqdLicFlg = reqdLicFlg;
	}

	public String getRplctnLvlCd() {
		return this.rplctnLvlCd;
	}

	public void setRplctnLvlCd(String rplctnLvlCd) {
		this.rplctnLvlCd = rplctnLvlCd;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public BigDecimal getTargetHigh() {
		return this.targetHigh;
	}

	public void setTargetHigh(BigDecimal targetHigh) {
		this.targetHigh = targetHigh;
	}

	public BigDecimal getTargetLow() {
		return this.targetLow;
	}

	public void setTargetLow(BigDecimal targetLow) {
		this.targetLow = targetLow;
	}

	public String getTranslateFlg() {
		return this.translateFlg;
	}

	public void setTranslateFlg(String translateFlg) {
		this.translateFlg = translateFlg;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVDayOfWeek() {
		return this.vDayOfWeek;
	}

	public void setVDayOfWeek(String vDayOfWeek) {
		this.vDayOfWeek = vDayOfWeek;
	}

	public String getVal() {
		return this.val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public BigDecimal getWeightingFactor() {
		return this.weightingFactor;
	}

	public void setWeightingFactor(BigDecimal weightingFactor) {
		this.weightingFactor = weightingFactor;
	}

	public String getWsId() {
		return this.wsId;
	}

	public void setWsId(String wsId) {
		this.wsId = wsId;
	}

	public String getWsInactiveFlg() {
		return this.wsInactiveFlg;
	}

	public void setWsInactiveFlg(String wsInactiveFlg) {
		this.wsInactiveFlg = wsInactiveFlg;
	}

	public BigDecimal getWsMaxVer() {
		return this.wsMaxVer;
	}

	public void setWsMaxVer(BigDecimal wsMaxVer) {
		this.wsMaxVer = wsMaxVer;
	}

	public BigDecimal getWsMinVer() {
		return this.wsMinVer;
	}

	public void setWsMinVer(BigDecimal wsMinVer) {
		this.wsMinVer = wsMinVer;
	}

	public String getWsSrcId() {
		return this.wsSrcId;
	}

	public void setWsSrcId(String wsSrcId) {
		this.wsSrcId = wsSrcId;
	}

	public String getXAttrib01() {
		return this.xAttrib01;
	}

	public void setXAttrib01(String xAttrib01) {
		this.xAttrib01 = xAttrib01;
	}

	public String getXAttrib02() {
		return this.xAttrib02;
	}

	public void setXAttrib02(String xAttrib02) {
		this.xAttrib02 = xAttrib02;
	}

	public String getXAttrib03() {
		return this.xAttrib03;
	}

	public void setXAttrib03(String xAttrib03) {
		this.xAttrib03 = xAttrib03;
	}

	public String getXAttrib04() {
		return this.xAttrib04;
	}

	public void setXAttrib04(String xAttrib04) {
		this.xAttrib04 = xAttrib04;
	}

	public String getXAttrib05() {
		return this.xAttrib05;
	}

	public void setXAttrib05(String xAttrib05) {
		this.xAttrib05 = xAttrib05;
	}

	public String getXAttrib06() {
		return this.xAttrib06;
	}

	public void setXAttrib06(String xAttrib06) {
		this.xAttrib06 = xAttrib06;
	}

	public String getXAttrib07() {
		return this.xAttrib07;
	}

	public void setXAttrib07(String xAttrib07) {
		this.xAttrib07 = xAttrib07;
	}

	public String getXAttrib08() {
		return this.xAttrib08;
	}

	public void setXAttrib08(String xAttrib08) {
		this.xAttrib08 = xAttrib08;
	}

	public String getXAttrib09() {
		return this.xAttrib09;
	}

	public void setXAttrib09(String xAttrib09) {
		this.xAttrib09 = xAttrib09;
	}

	public String getXAttrib10() {
		return this.xAttrib10;
	}

	public void setXAttrib10(String xAttrib10) {
		this.xAttrib10 = xAttrib10;
	}

	public String getXMacdActionCode() {
		return this.xMacdActionCode;
	}

	public void setXMacdActionCode(String xMacdActionCode) {
		this.xMacdActionCode = xMacdActionCode;
	}

	public String getXMacdImpact() {
		return this.xMacdImpact;
	}

	public void setXMacdImpact(String xMacdImpact) {
		this.xMacdImpact = xMacdImpact;
	}

	public String getXSearch() {
		return this.xSearch;
	}

	public void setXSearch(String xSearch) {
		this.xSearch = xSearch;
	}

	public AttributeConfig getAttributeConfig() {
		return this.attributeConfig;
	}

	public void setAttributeConfig(AttributeConfig attributeConfig) {
		this.attributeConfig = attributeConfig;
	}

}