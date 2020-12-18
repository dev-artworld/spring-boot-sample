package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.OneToMany;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="ROLE",schema="EQX_DART")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PK_ID")
	private long pkId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	private byte deleted;

	private byte priority;

	@Column(name="ROLE_NAME")
	private String roleName;

	/*@JsonIgnore
	//bi-directional many-to-many association to UserInfo
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="roles")
	private List<UserInfo> userInfos;*/

	//bi-directional many-to-many association to DartResource
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(
		name="role_resource"
		,schema="EQX_DART"
		, joinColumns={
			@JoinColumn(name="ROLE_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="RESOURCE_ID")
			}
		)
	private List<DartResource> dartResources;

	@JsonIgnore
	//bi-directional many-to-one association to UserInfoRole
	@OneToMany(mappedBy="role",fetch=FetchType.EAGER)
	private List<UserInfoRole> userInfoRoles;
	
	public Role() {
	}

	public long getPkId() {
		return this.pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public byte getPriority() {
		return this.priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/*public List<UserInfo> getUserInfos() {
		return this.userInfos;
	}

	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}*/

	public List<DartResource> getDartResources() {
		return this.dartResources;
	}

	public void setDartResources(List<DartResource> dartResources) {
		this.dartResources = dartResources;
	}

	public List<UserInfoRole> getUserInfoRoles() {
		return userInfoRoles;
	}

	public void setUserInfoRoles(List<UserInfoRole> userInfoRoles) {
		this.userInfoRoles = userInfoRoles;
	}

	
}