package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*Persistent Class for CHANGE_SUMMARY Table*/

@Entity
@Table(name="MS_CHANGE_SUMMARY" , schema="EQX_DART")
@NamedQuery(name="MsChangeSummary.findAll", query="SELECT a FROM MsChangeSummary a")
public class MsChangeSummary implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CHANGE_SUMMARY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
    @SequenceGenerator(name = "seqGenerator", sequenceName="CHNG_SUMMARY",schema="EQX_DART", allocationSize = 1)
	private long changeSummaryId;
	
	@Column(name="DFR_ID")
	private String dfrId;
	
	@Column(name = "DFR_LINE_ID")
	private String dfrLineId;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createdDate;
	
	@Column(name="ATTR_NAME")
	private String attrName;
	
	@Column(name="ATTR_VALUE")
	private String attrValue;
	
	@Column(name="ASSET_NUM")
	private String assetNum;
	
	@Column(name="PRODUCT_NAME")
	private String productName;


	public long getChangeSummaryId() {
		return changeSummaryId;
	}

	public void setChangeSummaryId(long changeSummaryId) {
		this.changeSummaryId = changeSummaryId;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getDfrLineId() {
		return dfrLineId;
	}

	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
}
