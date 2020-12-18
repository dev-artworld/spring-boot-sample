package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ATTRIBUTE_CONFIG database table.
 * 
 */
@Entity
@Table(name="DEPENDENT_ATTR_UPD",schema="EQX_DART")
@NamedQuery(name="DependentAttrUpdate.findAll", query="SELECT a FROM DependentAttrUpdate a")
public class DependentAttrUpdate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW_ID")
	private String rowId;
	
	@Column(name="ATTRIBUTE_FMLY")
	private String attributeFamly;

	@Column(name="SOURCE_ATTR")
	private String sourceAttr;

	@Column(name="TGT_ATTR")
	private String tgtAttr;

	@Column(name="TGT_CONFIG_ID")
	private String TgtConfigId;

	@Column(name="SRC_CONFIG_ID")
	private String srcConfigId;

	@Column(name="POF_NAME")
	private String pofName;

	@Column(name="PROD_NAME")
	private String ProdName;

	@Column(name="EXECUTION_ORDER")
	private String executionOrder;

	@Column(name="COMMENTS")
	private String comments;

	@Column(name="SQL2")
	private String sql2;
	
	@Column(name="SQL1")
	private String sql1;

	@Column(name="COLUMN1")
	private String column1;

	@Column(name="COLUMN2")
	private String column2;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}


	public String getAttributeFamly() {
		return attributeFamly;
	}

	public void setAttributeFamly(String attributeFamly) {
		this.attributeFamly = attributeFamly;
	}

	public String getSourceAttr() {
		return sourceAttr;
	}

	public void setSourceAttr(String sourceAttr) {
		this.sourceAttr = sourceAttr;
	}

	public String getTgtAttr() {
		return tgtAttr;
	}

	public void setTgtAttr(String tgtAttr) {
		this.tgtAttr = tgtAttr;
	}

	public String getTgtConfigId() {
		return TgtConfigId;
	}

	public void setTgtConfigId(String tgtConfigId) {
		TgtConfigId = tgtConfigId;
	}

	public String getSrcConfigId() {
		return srcConfigId;
	}

	public void setSrcConfigId(String srcConfigId) {
		this.srcConfigId = srcConfigId;
	}

	public String getPofName() {
		return pofName;
	}

	public void setPofName(String pofName) {
		this.pofName = pofName;
	}

	public String getProdName() {
		return ProdName;
	}

	public void setProdName(String prodName) {
		ProdName = prodName;
	}

	public String getExecutionOrder() {
		return executionOrder;
	}

	public void setExecutionOrder(String executionOrder) {
		this.executionOrder = executionOrder;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	public String getSql1() {
		return sql1;
	}

	public void setSql1(String sql1) {
		this.sql1 = sql1;
	}

	public String getSql2() {
		return sql2;
	}

	public void setSql2(String sql2) {
		this.sql2 = sql2;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	@Override
	public String toString() {
		return "DependentAttrUpdate [rowId=" + rowId + ", attributeFamly=" + attributeFamly
				+ ", sourceAttr=" + sourceAttr + ", tgtAttr=" + tgtAttr + ", TgtConfigId=" + TgtConfigId
				+ ", srcConfigId=" + srcConfigId + ", pofName=" + pofName + ", ProdName=" + ProdName
				+ ", executionOrder=" + executionOrder + ", comments=" + comments + ", sql=" + sql2 + ", column1="
				+ column1 + ", column2=" + column2 + "]";
	}
	
	
	
}