package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SRC_SERVICE_DA_ARRAY_RAMP database table.
 * 
 */
@Entity
@Table(name="SRC_SERVICE_DA_ARRAY_RAMP",schema="EQX_DART")
@NamedQuery(name="SrcServiceDaArrayRamp.findAll", query="SELECT s FROM SrcServiceDaArrayRamp s")
public class SrcServiceDaArrayRamp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="row_Id")
	private String rowId; 
	
	@Column(name="CDC_CREATION_DT")
	private String cdcCreationDt;

	@Column(name="DERIVED_ATTRIBUTE_ID")
	private Long derivedAttributeId;
	@Column(name="DESCRIPTION")
	private String description;

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

	@Temporal(TemporalType.DATE)
	@Column(name="EFFECTIVE_END_DATE")
	private Date effectiveEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="EFFECTIVE_START_DATE")
	private Date effectiveStartDate;

	@Column(name="INDEX1_VALUE")
	private String index1Value;

	@Column(name="INDEX10_VALUE")
	private String index10Value;

	@Column(name="INDEX2_VALUE")
	private String index2Value;

	@Column(name="INDEX3_VALUE")
	private String index3Value;

	@Column(name="INDEX4_VALUE")
	private String index4Value;

	@Column(name="INDEX5_VALUE")
	private String index5Value;

	@Column(name="INDEX6_VALUE")
	private String index6Value;

	@Column(name="INDEX7_VALUE")
	private String index7Value;

	@Column(name="INDEX8_VALUE")
	private String index8Value;

	@Column(name="INDEX9_VALUE")
	private String index9Value;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_MODIFIED")
	private Date lastModified;

	@Column(name="RESULT1_VALUE")
	private String result1Value;

	@Column(name="RESULT10_VALUE")
	private String result10Value;

	@Column(name="RESULT11_VALUE")
	private String result11Value;

	@Column(name="RESULT12_VALUE")
	private String result12Value;

	@Column(name="RESULT13_VALUE")
	private String result13Value;

	@Column(name="RESULT14_VALUE")
	private String result14Value;

	@Column(name="RESULT15_VALUE")
	private String result15Value;

	@Column(name="RESULT16_VALUE")
	private String result16Value;

	@Column(name="RESULT17_VALUE")
	private String result17Value;

	@Column(name="RESULT18_VALUE")
	private String result18Value;

	@Column(name="RESULT19_VALUE")
	private String result19Value;

	@Column(name="RESULT2_VALUE")
	private String result2Value;

	@Column(name="RESULT20_VALUE")
	private String result20Value;

	@Column(name="RESULT3_VALUE")
	private String result3Value;

	@Column(name="RESULT4_VALUE")
	private String result4Value;

	@Column(name="RESULT5_VALUE")
	private String result5Value;

	@Column(name="RESULT6_VALUE")
	private String result6Value;

	@Column(name="RESULT7_VALUE")
	private String result7Value;

	@Column(name="RESULT8_VALUE")
	private String result8Value;

	@Column(name="RESULT9_VALUE")
	private String result9Value;
	@Column(name="SEQNR")
	private Long seqnr;
	
	@Column(name="SERVICE_ID")
	private Long serviceId;

	@Column(name="V_DAY_OF_WEEK")
	private String vDayOfWeek;

	public SrcServiceDaArrayRamp() {
	}

	public String getCdcCreationDt() {
		return this.cdcCreationDt;
	}

	public void setCdcCreationDt(String cdcCreationDt) {
		this.cdcCreationDt = cdcCreationDt;
	}

	public Long getDerivedAttributeId() {
		return this.derivedAttributeId;
	}

	public void setDerivedAttributeId(Long derivedAttributeId) {
		this.derivedAttributeId = derivedAttributeId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getEffectiveEndDate() {
		return this.effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return this.effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public String getIndex1Value() {
		return this.index1Value;
	}

	public void setIndex1Value(String index1Value) {
		this.index1Value = index1Value;
	}

	public String getIndex10Value() {
		return this.index10Value;
	}

	public void setIndex10Value(String index10Value) {
		this.index10Value = index10Value;
	}

	public String getIndex2Value() {
		return this.index2Value;
	}

	public void setIndex2Value(String index2Value) {
		this.index2Value = index2Value;
	}

	public String getIndex3Value() {
		return this.index3Value;
	}

	public void setIndex3Value(String index3Value) {
		this.index3Value = index3Value;
	}

	public String getIndex4Value() {
		return this.index4Value;
	}

	public void setIndex4Value(String index4Value) {
		this.index4Value = index4Value;
	}

	public String getIndex5Value() {
		return this.index5Value;
	}

	public void setIndex5Value(String index5Value) {
		this.index5Value = index5Value;
	}

	public String getIndex6Value() {
		return this.index6Value;
	}

	public void setIndex6Value(String index6Value) {
		this.index6Value = index6Value;
	}

	public String getIndex7Value() {
		return this.index7Value;
	}

	public void setIndex7Value(String index7Value) {
		this.index7Value = index7Value;
	}

	public String getIndex8Value() {
		return this.index8Value;
	}

	public void setIndex8Value(String index8Value) {
		this.index8Value = index8Value;
	}

	public String getIndex9Value() {
		return this.index9Value;
	}

	public void setIndex9Value(String index9Value) {
		this.index9Value = index9Value;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getResult1Value() {
		return this.result1Value;
	}

	public void setResult1Value(String result1Value) {
		this.result1Value = result1Value;
	}

	public String getResult10Value() {
		return this.result10Value;
	}

	public void setResult10Value(String result10Value) {
		this.result10Value = result10Value;
	}

	public String getResult11Value() {
		return this.result11Value;
	}

	public void setResult11Value(String result11Value) {
		this.result11Value = result11Value;
	}

	public String getResult12Value() {
		return this.result12Value;
	}

	public void setResult12Value(String result12Value) {
		this.result12Value = result12Value;
	}

	public String getResult13Value() {
		return this.result13Value;
	}

	public void setResult13Value(String result13Value) {
		this.result13Value = result13Value;
	}

	public String getResult14Value() {
		return this.result14Value;
	}

	public void setResult14Value(String result14Value) {
		this.result14Value = result14Value;
	}

	public String getResult15Value() {
		return this.result15Value;
	}

	public void setResult15Value(String result15Value) {
		this.result15Value = result15Value;
	}

	public String getResult16Value() {
		return this.result16Value;
	}

	public void setResult16Value(String result16Value) {
		this.result16Value = result16Value;
	}

	public String getResult17Value() {
		return this.result17Value;
	}

	public void setResult17Value(String result17Value) {
		this.result17Value = result17Value;
	}

	public String getResult18Value() {
		return this.result18Value;
	}

	public void setResult18Value(String result18Value) {
		this.result18Value = result18Value;
	}

	public String getResult19Value() {
		return this.result19Value;
	}

	public void setResult19Value(String result19Value) {
		this.result19Value = result19Value;
	}

	public String getResult2Value() {
		return this.result2Value;
	}

	public void setResult2Value(String result2Value) {
		this.result2Value = result2Value;
	}

	public String getResult20Value() {
		return this.result20Value;
	}

	public void setResult20Value(String result20Value) {
		this.result20Value = result20Value;
	}

	public String getResult3Value() {
		return this.result3Value;
	}

	public void setResult3Value(String result3Value) {
		this.result3Value = result3Value;
	}

	public String getResult4Value() {
		return this.result4Value;
	}

	public void setResult4Value(String result4Value) {
		this.result4Value = result4Value;
	}

	public String getResult5Value() {
		return this.result5Value;
	}

	public void setResult5Value(String result5Value) {
		this.result5Value = result5Value;
	}

	public String getResult6Value() {
		return this.result6Value;
	}

	public void setResult6Value(String result6Value) {
		this.result6Value = result6Value;
	}

	public String getResult7Value() {
		return this.result7Value;
	}

	public void setResult7Value(String result7Value) {
		this.result7Value = result7Value;
	}

	public String getResult8Value() {
		return this.result8Value;
	}

	public void setResult8Value(String result8Value) {
		this.result8Value = result8Value;
	}

	public String getResult9Value() {
		return this.result9Value;
	}

	public void setResult9Value(String result9Value) {
		this.result9Value = result9Value;
	}

	public Long getSeqnr() {
		return this.seqnr;
	}

	public void setSeqnr(Long seqnr) {
		this.seqnr = seqnr;
	}

	public Long getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getVDayOfWeek() {
		return this.vDayOfWeek;
	}

	public void setVDayOfWeek(String vDayOfWeek) {
		this.vDayOfWeek = vDayOfWeek;
	}

}