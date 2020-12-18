
package com.equinix.appops.dart.portal.model.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.equinix.appops.dart.portal.entity.AssignGroup;
import com.equinix.appops.dart.portal.entity.Role;
import com.equinix.appops.dart.portal.entity.UserInfo;


@Component
@Qualifier("userDo")
@Scope("session")
public class User implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private String userId;
	private String firstName;
	private String lastName;
	private String fullname;
	private String emailId;
	private List<String> usrGroups;
	private String country;
	private String region;
	private UserInfo userInfo;
	private transient boolean unAuthorized;
	private transient Role role;
	private transient AssignGroup primaryAssignGroup;
	
	public boolean isUnAuthorized() {
		return unAuthorized;
	}
	public void setUnAuthorized(boolean unAuthorized) {
		this.unAuthorized = unAuthorized;
	}
	public String getUserId() {
		return StringUtils.isEmpty(this.userId)?"kpunyakoteeswaran":this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<String> getUsrGroups() {
		return usrGroups;
	}
	public void setUsrGroups(List<String> usrGroups) {
		this.usrGroups = usrGroups;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public UserInfo getUserInfo() {
		if(null==userInfo){
			userInfo=new UserInfo();
		}
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public AssignGroup getPrimaryAssignGroup() {
		return primaryAssignGroup;
	}
	public void setPrimaryAssignGroup(AssignGroup primaryAssignGroup) {
		this.primaryAssignGroup = primaryAssignGroup;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", fullname="
				+ fullname + ", emailId=" + emailId + ", usrGroups=" + usrGroups + ", country=" + country + ", region="
				+ region + ", userInfo=" + userInfo + "]";
	}
		
	
	
}
