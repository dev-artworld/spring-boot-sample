package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;


@Entity
@Table(name="SNOW_CONFIG_ITEM_XA",schema="EQX_DART")
@NamedQuery(name="SnowConfigItemXa.findAll", query="SELECT s FROM SnowConfigItemXa s")
public class SnowConfigItemXa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SerializedName(value="rowidval")
	@Column(name="ROW_ID")
	private String rowIdVal;
	
	@SerializedName(value="assetid")
	@Column(name="ASSET_ID")
	private String assetId;
	 
	@SerializedName(value="assetnum")
	@Column(name="ASSET_NUM")
	private String assetNum;
	
	@SerializedName(value="attrname")
	@Column(name="ATTR_NAME")
	private String attrName;
	
	@SerializedName(value="attrvalue")
	@Column(name="ATTR_VALUE")
	private String attrValue;
	
	@SerializedName(value="product")
	@Column(name="PRODUCT")
	private String product;
	
	@SerializedName(value="statuscd")
	@Column(name="STATUS_CD")
	private String statusCd;
	
	@SerializedName(value="sync_date")
	@Column(name="SYNC_DATE")
	private Date syncDate;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public String getRowIdVal() {
		return rowIdVal;
	}

	public void setRowIdVal(String rowIdVal) {
		this.rowIdVal = rowIdVal;
	}
	

}
