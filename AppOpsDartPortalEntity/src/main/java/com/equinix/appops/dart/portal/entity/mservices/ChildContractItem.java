package com.equinix.appops.dart.portal.entity.mservices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="SRC_SNOW_CONTRACT_ITEM_CHILD",schema="EQX_DART")
@NamedQuery(name="ChildContractItem.findAll", query="SELECT a FROM ChildContractItem a")
public class ChildContractItem {

    //@Column(name="U_CONTRACT_ITEM")
    @Transient
    @JsonProperty("u_contract_item")
    private String uContractItem;
    //@Column(name="SYS_ID")
    @Transient
    @JsonProperty("sys_id")
    private String sysId;
    @EmbeddedId
    private ChildContractItemId childContractItemId;
    @Column(name="U_DISPLAY_NAME")
    @JsonProperty("u_display_name")
    private String displayName;
    @Column(name="SYS_UPDATED_BY")
    @JsonProperty("sys_updated_by")
    private String sysUpdatedBY;
    @Column(name="U_VALUE")
    @JsonProperty("u_value")
    private String uValue;
    @Column(name="SYS_CREATED_ON")
    @JsonProperty("sys_created_on")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date sysCreatedOn;
    @Column(name="SYS_MOD_COUNT")
    @JsonProperty("sys_mod_count")
    private String sysModCount;
    @Column(name="SYS_UPDATED_ON")
    @JsonProperty("sys_updated_on")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date sysUpdatedOn;
    @Column(name="SYS_TAGS")
    @JsonProperty("sys_tags")
    private String sysTags;
    @Column(name="U_ATTRIBUTE")
    @JsonProperty("u_attribute")
    private String attribute;
    @Column(name="SYS_CREATED_BY")
    @JsonProperty("sys_created_by")
    private String sysCreatedBy;
    @Column(name="DTL__CAPXUSER")
    private String dtlCapxUser ;
    @Column(name="DTL__CAPXTIMESTAMP")
    private String dtlCapxTimestamp;
    @Column(name="DTL__CAPXACTION")
    private String dtlCapxAction;
    
		
	public ChildContractItem(){}
	
	public String getuContractItem() {
		return uContractItem;
	}
	public void setuContractItem(String uContractItem) {
		this.uContractItem = uContractItem;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public ChildContractItemId getChildContractItemId() {
		return childContractItemId;
	}
	public void setChildContractItemId(ChildContractItemId childContractItemId) {
		this.childContractItemId = childContractItemId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getSysUpdatedBY() {
		return sysUpdatedBY;
	}
	public void setSysUpdatedBY(String sysUpdatedBY) {
		this.sysUpdatedBY = sysUpdatedBY;
	}
	public String getuValue() {
		return uValue;
	}
	public void setuValue(String uValue) {
		this.uValue = uValue;
	}
	public Date getSysCreatedOn() {
		return sysCreatedOn;
	}
	public void setSysCreatedOn(Date sysCreatedOn) {
		this.sysCreatedOn = sysCreatedOn;
	}
	public String getSysModCount() {
		return sysModCount;
	}
	public void setSysModCount(String sysModCount) {
		this.sysModCount = sysModCount;
	}
	public Date getSysUpdatedOn() {
		return sysUpdatedOn;
	}
	public void setSysUpdatedOn(Date sysUpdatedOn) {
		this.sysUpdatedOn = sysUpdatedOn;
	}
	public String getSysTags() {
		return sysTags;
	}
	public void setSysTags(String sysTags) {
		this.sysTags = sysTags;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getSysCreatedBy() {
		return sysCreatedBy;
	}
	public void setSysCreatedBy(String sysCreatedBy) {
		this.sysCreatedBy = sysCreatedBy;
	}

	public String getDtlCapxUser() {
		return dtlCapxUser;
	}

	public void setDtlCapxUser(String dtlCapxUser) {
		this.dtlCapxUser = dtlCapxUser;
	}

	public String getDtlCapxTimestamp() {
		return dtlCapxTimestamp;
	}

	public void setDtlCapxTimestamp(String dtlCapxTimestamp) {
		this.dtlCapxTimestamp = dtlCapxTimestamp;
	}

	public String getDtlCapxAction() {
		return dtlCapxAction;
	}

	public void setDtlCapxAction(String dtlCapxAction) {
		this.dtlCapxAction = dtlCapxAction;
	}
    
    

 }


