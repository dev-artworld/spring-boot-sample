package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the DFR_MASTER database table.
 * 
 */
@Entity
@Table(name = "PRODUCT_CONFIG", schema = "EQX_DART")
@NamedQuery(name = "ProductConfig.findAll", query = "SELECT d FROM ProductConfig d")
public class ProductConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getParentProductName() {
		return parentProductName;
	}

	public void setParentProductName(String parentProductName) {
		this.parentProductName = parentProductName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	@Column(name = "SEQUENCE")
	private Integer sequence;

	@Column(name = "PARENT_PRODUCT_NAME")
	private String parentProductName;

	@Column(name = "PRODUCT_NAME")
	private String name;

}