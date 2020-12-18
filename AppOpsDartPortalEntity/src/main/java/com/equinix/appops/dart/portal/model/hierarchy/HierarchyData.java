package com.equinix.appops.dart.portal.model.hierarchy;

import java.io.Serializable;

public class HierarchyData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8783766140241955729L;
	
	public String parentRowId;
	public String childRowId;
	public String parentName;
	public String parentSystemName;
	public String childName;
	public String childSystemName;
	
	public HierarchyData(String parentRowId, String childRowId, String parentName, String parentSystemName,
			String childName, String childSystemName) {
		super();
		this.parentRowId = parentRowId;
		this.childRowId = childRowId;
		this.parentName = parentName;
		this.parentSystemName = parentSystemName;
		this.childName = childName;
		this.childSystemName = childSystemName;
	}
	
	
	
	public String getParentRowId() {
		return parentRowId;
	}



	public void setParentRowId(String parentRowId) {
		this.parentRowId = parentRowId;
	}



	public String getChildRowId() {
		return childRowId;
	}



	public void setChildRowId(String childRowId) {
		this.childRowId = childRowId;
	}



	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentSystemName() {
		return parentSystemName;
	}
	public void setParentSystemName(String parentSystemName) {
		this.parentSystemName = parentSystemName;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getChildSystemName() {
		return childSystemName;
	}
	public void setChildSystemName(String childSystemName) {
		this.childSystemName = childSystemName;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HierarchyData [parentRowId=");
		builder.append(parentRowId);
		builder.append(", childRowId=");
		builder.append(childRowId);
		builder.append(", parentName=");
		builder.append(parentName);
		builder.append(", parentSystemName=");
		builder.append(parentSystemName);
		builder.append(", childName=");
		builder.append(childName);
		builder.append(", childSystemName=");
		builder.append(childSystemName);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
