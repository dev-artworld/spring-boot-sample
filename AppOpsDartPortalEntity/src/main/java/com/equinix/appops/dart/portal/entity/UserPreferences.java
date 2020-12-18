package com.equinix.appops.dart.portal.entity;

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

@Entity
@Table(name="USER_PREFERENCES" , schema="EQX_DART")
@NamedQuery(name="UserPreferences.findAll", query="SELECT a FROM UserPreferences a")
public class UserPreferences implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="USER_PREFERENCES_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	@SequenceGenerator(name = "seqGenerator", sequenceName="USER_PREF_SEQ",schema="EQX_DART", allocationSize = 1)
	private long userPreferenceId;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="NAME")
	private String productName;
	
	
	@Column(name="ATTR_NAME")
	private String attrName;
	
	@Column(name="SEQUENCE_ORDER")
	private int sequeneOrder;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createdDate;

	public long getUserPreferenceId() {
		return userPreferenceId;
	}

	public void setUserPreferenceId(long userPreferenceId) {
		this.userPreferenceId = userPreferenceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public int getSequeneOrder() {
		return sequeneOrder;
	}

	public void setSequeneOrder(int sequeneOrder) {
		this.sequeneOrder = sequeneOrder;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
