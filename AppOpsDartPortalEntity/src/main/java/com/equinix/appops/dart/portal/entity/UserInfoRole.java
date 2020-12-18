package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the USER_INFO_ROLE database table.
 * 
 */
@Entity
@Table(name="USER_INFO_ROLE" , schema="EQX_DART")
@NamedQuery(name="UserInfoRole.findAll", query="SELECT u FROM UserInfoRole u")
public class UserInfoRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PK_ID")
	private long pkId;

	@Column(name="\"PRIMARY\"")
	private Integer primary;

	//bi-directional many-to-one association to Role
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ROLE_ID")
	private Role role;

	//bi-directional many-to-one association to UserInfo
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	private UserInfo userInfo;

	public UserInfoRole() {
	}

	public long getPkId() {
		return this.pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public Integer getPrimary() {
		return this.primary;
	}

	public void setPrimary(Integer primary) {
		this.primary = primary;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}