package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SRC_IBX_COUNTRY database table.
 * 
 */
@Entity
@Table(name="SRC_IBX_COUNTRY",schema="EQX_DART")
@NamedQuery(name="SrcIbxCountry.findAll", query="SELECT s FROM SrcIbxCountry s")
public class SrcIbxCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CDC_CREATION_DT")
	private String cdcCreationDt;

	private String country;

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

	@Id
	@Column(name="IBX")
	private String ibx;

	@Column(name="REGION")
	private String region;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_ON")
	private Date updatedOn;

	@Column(name="V_DAY_OF_WEEK")
	private String vDayOfWeek;

	public SrcIbxCountry() {
	}

	public String getCdcCreationDt() {
		return this.cdcCreationDt;
	}

	public void setCdcCreationDt(String cdcCreationDt) {
		this.cdcCreationDt = cdcCreationDt;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getIbx() {
		return this.ibx;
	}

	public void setIbx(String ibx) {
		this.ibx = ibx;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getVDayOfWeek() {
		return this.vDayOfWeek;
	}

	public void setVDayOfWeek(String vDayOfWeek) {
		this.vDayOfWeek = vDayOfWeek;
	}

}