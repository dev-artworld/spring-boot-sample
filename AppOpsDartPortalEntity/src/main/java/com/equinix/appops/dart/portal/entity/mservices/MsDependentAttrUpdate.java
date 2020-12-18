package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MS_DEPENDENT_ATTR_UPD database table.
 * 
 */
@Entity
@Table(name="MS_DEPENDENT_ATTR_UPD",schema="EQX_DART")
@NamedQuery(name="MsDependentAttrUpdate.findAll", query="SELECT a FROM MsDependentAttrUpdate a")
public class MsDependentAttrUpdate implements Serializable {
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

	@Column(name="SQL1")
	private String sql;

	@Column(name="COLUMN1")
	private String column1;

	@Column(name="COLUMN2")
	private String column2;
	
	@Column(name="SQL2")
	private String sql2;

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

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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
	
	

	public String getSql2() {
		return sql2;
	}

	public void setSql2(String sql2) {
		this.sql2 = sql2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DependentAttrUpdate [rowId=");
		builder.append(rowId);
		builder.append(", attributeFamly=");
		builder.append(attributeFamly);
		builder.append(", sourceAttr=");
		builder.append(sourceAttr);
		builder.append(", tgtAttr=");
		builder.append(tgtAttr);
		builder.append(", TgtConfigId=");
		builder.append(TgtConfigId);
		builder.append(", srcConfigId=");
		builder.append(srcConfigId);
		builder.append(", pofName=");
		builder.append(pofName);
		builder.append(", ProdName=");
		builder.append(ProdName);
		builder.append(", executionOrder=");
		builder.append(executionOrder);
		builder.append(", comments=");
		builder.append(comments);
		builder.append(", sql=");
		builder.append(sql);
		builder.append(", column1=");
		builder.append(column1);
		builder.append(", column2=");
		builder.append(column2);
		builder.append(", sql2=");
		builder.append(sql2);
		builder.append("]");
		return builder.toString();
	}

	
	
	
	
}