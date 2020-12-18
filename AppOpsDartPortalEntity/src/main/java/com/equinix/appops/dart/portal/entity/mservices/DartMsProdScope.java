package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DART_MS_PROD_SCOPE" , schema="EQX_DART")
@NamedQuery(name="DartMsProdScope.findAll", query="SELECT a FROM DartMsProdScope a")
public class DartMsProdScope implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROW_ID")
	private String rowId;

	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "DART_ENABLED")
	private String dartEnabled;
	
	@Column(name="NEW_PRODUCT_IND")
	private String newProductInd;
	
	@Column(name="POF_NAME")
	private String pofName;
	
	@Column(name="POF_FLAG")
	private String pofFlag;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SBL_START_DATE")
	private Date sblStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SBL_END_DATE")
	private Date sblEndDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SNOW_START_DATE")
	private Date snowStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SNOW_END_DATE")
	private Date snowEndDate;
	
	@Column(name="UPD_BY")
	private String updBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPD_DT")
	private Date updDt;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDartEnabled() {
		return dartEnabled;
	}

	public void setDartEnabled(String dartEnabled) {
		this.dartEnabled = dartEnabled;
	}

	public String getNewProductInd() {
		return newProductInd;
	}

	public void setNewProductInd(String newProductInd) {
		this.newProductInd = newProductInd;
	}

	public String getPofName() {
		return pofName;
	}

	public void setPofName(String pofName) {
		this.pofName = pofName;
	}

	public String getPogFlag() {
		return pofFlag;
	}

	public void setPogFlag(String pogFlag) {
		this.pofFlag = pogFlag;
	}

	public Date getSblStartDate() {
		return sblStartDate;
	}

	public void setSblStartDate(Date sblStartDate) {
		this.sblStartDate = sblStartDate;
	}

	public Date getSblEndDate() {
		return sblEndDate;
	}

	public void setSblEndDate(Date sblEndDate) {
		this.sblEndDate = sblEndDate;
	}

	public Date getSnowStartDate() {
		return snowStartDate;
	}

	public void setSnowStartDate(Date snowStartDate) {
		this.snowStartDate = snowStartDate;
	}

	public Date getSnowEndDate() {
		return snowEndDate;
	}

	public void setSnowEndDate(Date snowEndDate) {
		this.snowEndDate = snowEndDate;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public Date getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}
	
	

}
