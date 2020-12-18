package com.equinix.appops.dart.portal.model.domain;

public enum EnumResourceType {

	URL("URL"),COMPONENT("COMPONENT");
	
	private String resourceType;
	
	EnumResourceType(String resourceType){
		this.resourceType = resourceType;
	}

	public String getResourceType() {
		return resourceType;
	}
}
