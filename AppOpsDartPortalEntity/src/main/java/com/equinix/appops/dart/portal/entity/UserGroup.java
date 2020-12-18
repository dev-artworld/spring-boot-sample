package com.equinix.appops.dart.portal.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_GROUP", schema = "EQX_DART")
@AssociationOverrides({ @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "USER_ID")),
		@AssociationOverride(name = "primaryKey.group", joinColumns = @JoinColumn(name = "GROUP_ID")) })
public class UserGroup {
	// composite-id key
	private UserGroupId primaryKey = new UserGroupId();

	@EmbeddedId
	public UserGroupId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(UserGroupId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Column(name = "primary")
	private Integer primaryGroup;

	@JsonIgnore
	@Transient
	public UserInfo getUser() {
		return getPrimaryKey().getUser();
	}

	public void setUser(UserInfo user) {
		getPrimaryKey().setUser(user);
	}

	@Transient
	public AssignGroup getGroup() {
		return getPrimaryKey().getGroup();
	}

	public void setGroup(AssignGroup group) {
		getPrimaryKey().setGroup(group);
	}

	@Column(name = "primary")
	public Integer getPrimaryGroup() {
		return primaryGroup;
	}

	public void setPrimaryGroup(Integer primaryGroup) {
		this.primaryGroup = primaryGroup;
	}

}
