package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class UserGroupId implements Serializable {	
	@JsonIgnore
	private UserInfo user;
	private AssignGroup group;

	@ManyToOne(cascade = CascadeType.ALL)
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public AssignGroup getGroup() {
		return group;
	}

	public void setGroup(AssignGroup group) {
		this.group = group;
	}

}
