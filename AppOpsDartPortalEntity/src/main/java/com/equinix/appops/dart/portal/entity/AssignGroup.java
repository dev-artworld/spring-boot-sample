package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the assign_group database table.
 * 
 */
@Entity
@Table(name="ASSIGN_GROUP",schema="EQX_DART")
@NamedQuery(name="AssignGroup.findAll", query="SELECT a FROM AssignGroup a")
public class AssignGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW_ID")
	private double rowId;

	@Column(name="GROUP_NAME")
	private String groupName;
	
	@Column(name="REGION")
	private String region;
	
	@Column(name="SYSTEM")
	private String system;

	@JsonIgnore
	@OneToMany(mappedBy = "primaryKey.group",cascade = CascadeType.ALL)
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();
	
	public AssignGroup() {
	}

	public double getRowId() {
		return this.rowId;
	}

	public void setRowId(double rowId) {
		this.rowId = rowId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> groups) {
		this.userGroups = groups;
	}
	
	public void addUserGroup(UserGroup userGroup) {
		this.userGroups.add(userGroup);
	}
	

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

}