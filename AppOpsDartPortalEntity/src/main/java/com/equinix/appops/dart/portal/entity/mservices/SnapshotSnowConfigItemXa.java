package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SNAPSHOT_SNOW_CONFIG_ITEM_XA database table.
 * 
 */
@Entity
@Table(name="SNAPSHOT_SNOW_CONFIG_ITEM_XA",schema="EQX_DART")
@NamedQuery(name="SnapshotSnowConfigItemXa.findAll", query="SELECT s FROM SnapshotSnowConfigItemXa s")
public class SnapshotSnowConfigItemXa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DFR_LINE_ID")
	private String dfrLineId;
	
	@Column(name="ROW_ID")
	private String rowId;

	@Column(name="ASSET_ID")
	private String assetId;

	@Column(name="ASSET_NUM")
	private String assetNum;

	@Column(name="ATTR_NAME")
	private String attrName;

	@Column(name="ATTR_VALUE")
	private String attrValue;

	@Column(name="DFR_ID")
	private String dfrId;

	@Column(name="PRODUCT")
	private String product;

	@Column(name="STATUS_CD")
	private String statusCd;

	public SnapshotSnowConfigItemXa() {
	}

	
	public String getRowId() {
		return rowId;
	}


	public void setRowId(String rowId) {
		this.rowId = rowId;
	}


	public String getDfrLineId() {
		return this.dfrLineId;
	}

	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
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

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return this.attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getDfrId() {
		return this.dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

}