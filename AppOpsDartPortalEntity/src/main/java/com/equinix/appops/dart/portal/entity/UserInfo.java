package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * The persistent class for the user_info database table.
 * 
 */
@Entity
@Table(name="USER_INFO",schema="EQX_DART")
@NamedQuery(name="UserInfo.findAll", query="SELECT u FROM UserInfo u")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PK_ID")
	private long pkId;

	@Column(name="EMAIL_ID")
	private String emailId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGIN_TIME")
	private Date lastLoginTime;

	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;

	@OneToMany(mappedBy = "primaryKey.user", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();
	
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> groups) {
		this.userGroups = groups;
	}
	
	public void addUserGroup(UserGroup userGroup) {
		this.userGroups.add(userGroup);
	}
	
	/*//bi-directional many-to-many association to Role
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(
		name="user_info_role"
		,schema="EQX_DART"
		, joinColumns={
			@JoinColumn(name="USER_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ROLE_ID")
			}
		)
	private List<Role> roles;*/
	
	@Column(name="RECENT_DFR")
	private String recentDfr;

	@Transient
	private Role primaryRole;
	
	@Transient
	private List<Role> nonPrimaryRoles = new ArrayList<Role>(); 
	
	@JsonIgnore
	//bi-directional many-to-one association to UserInfoRole
	@OneToMany(mappedBy="userInfo", fetch = FetchType.EAGER)
	private List<UserInfoRole> userInfoRoles;

	public UserInfo() {
	}

	public long getPkId() {
		return this.pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserId() {
		return StringUtils.isEmpty(this.userId)?"kpunyakoteeswaran":this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}*/

	public String getRecentDfr() {
		return recentDfr;
	}

	public void setRecentDfr(String recentDfr) {
		this.recentDfr = recentDfr;
	}

	public List<UserInfoRole> getUserInfoRoles() {
		return this.userInfoRoles;
	}

	public void setUserInfoRoles(List<UserInfoRole> userInfoRoles) {
		this.userInfoRoles = userInfoRoles;
	}

	public UserInfoRole addUserInfoRole(UserInfoRole userInfoRole) {
		getUserInfoRoles().add(userInfoRole);
		userInfoRole.setUserInfo(this);

		return userInfoRole;
	}

	public UserInfoRole removeUserInfoRole(UserInfoRole userInfoRole) {
		getUserInfoRoles().remove(userInfoRole);
		userInfoRole.setUserInfo(null);

		return userInfoRole;
	}

	public Role getPrimaryRole() {
		if(CollectionUtils.isNotEmpty(this.userInfoRoles)){
			for(UserInfoRole userInfoRole : this.userInfoRoles){
				if(userInfoRole.getPrimary()==1 && userInfoRole.getRole()!=null){
					return userInfoRole.getRole();
				}
			}
		}
		return primaryRole;
	}

	public List<Role> getNonPrimaryRoles() {
		if(CollectionUtils.isNotEmpty(this.userInfoRoles)){
			for(UserInfoRole userInfoRole : this.userInfoRoles){
				if(userInfoRole.getPrimary()!=1 && userInfoRole.getRole()!=null){
					this.nonPrimaryRoles.add(userInfoRole.getRole());
				}
			}
		}
		return this.nonPrimaryRoles;
	}
	
	/**
	 * First Name and Last Name newly added
	 */
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}