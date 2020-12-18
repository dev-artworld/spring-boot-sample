package com.equinix.appops.dart.portal.model.widget;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.equinix.appops.dart.portal.common.DartEntityConstants;

public class ProductWidgetData implements Serializable {
   
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6306923838206320944L;
	private String parentRowId;
	private String childRowId;
	private String parentProduct;
	private String parentSystemName;
	private String childProduct;
	private String childSystemName;
	private String childCabinetName;
	private String childSerialNumber;
	private String childAsidePatchPanel;
	private String parentCabinetName;
	private String parentSerialNumber;
	public ProductWidgetData(String parentRowId, String childRowId, String parentProduct, String parentSystemName,
			String childProduct, String childSystemName, String childCabinetName, String childSerialNumber,
			String childAsidePatchPanel,String parentCabinetName,String parentSerialNumber ) {
		super();
		this.parentRowId = StringUtils.isNotBlank(parentRowId)?parentRowId:"NA";
		this.childRowId = StringUtils.isNotBlank(childRowId)?childRowId:"NA";
		this.parentProduct = StringUtils.isNotBlank(parentProduct)?parentProduct:"NA";
		this.parentSystemName =StringUtils.isNotBlank(parentSystemName)?parentSystemName:"NA";
		this.childProduct = StringUtils.isNotBlank(childProduct)?childProduct:"NA";
		this.childSystemName = StringUtils.isNotBlank(childSystemName)?childSystemName:"NA";
		this.childCabinetName = StringUtils.isNotBlank(childCabinetName)?childCabinetName:"NA";
		this.childSerialNumber = StringUtils.isNotBlank(childSerialNumber)?childSerialNumber:"NA";
		this.childAsidePatchPanel = StringUtils.isNotBlank(childAsidePatchPanel)?childAsidePatchPanel:"NA";
		this.parentCabinetName = StringUtils.isNotBlank(parentCabinetName)?parentCabinetName:"NA";
		this.parentSerialNumber = StringUtils.isNotBlank(parentSerialNumber)?parentSerialNumber:"NA";
	}
	
	
	
	public String getParentSerialNumber() {
		return parentSerialNumber;
	}



	public void setParentSerialNumber(String parentSerialNumber) {
		this.parentSerialNumber = parentSerialNumber;
	}



	public String getParentCabinetName() {
		return parentCabinetName;
	}

	public void setParentCabinetName(String parentCabinetName) {
		this.parentCabinetName = parentCabinetName;
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
	public String getParentProduct() {
		return parentProduct;
	}
	public void setParentProduct(String parentProduct) {
		this.parentProduct = parentProduct;
	}
	public String getParentSystemName() {
		return parentSystemName;
	}
	public void setParentSystemName(String parentSystemName) {
		this.parentSystemName = parentSystemName;
	}
	public String getChildProduct() {
		return childProduct;
	}
	public void setChildProduct(String childProduct) {
		this.childProduct = childProduct;
	}
	public String getChildSystemName() {
		return childSystemName;
	}
	public void setChildSystemName(String childSystemName) {
		this.childSystemName = childSystemName;
	}
	public String getChildCabinetName() {
		return childCabinetName;
	}
	public void setChildCabinetName(String childCabinetName) {
		this.childCabinetName = childCabinetName;
	}
	public String getChildSerialNumber() {
		return childSerialNumber;
	}
	public void setChildSerialNumber(String childSerialNumber) {
		this.childSerialNumber = childSerialNumber;
	}
	public String getChildAsidePatchPanel() {
		return childAsidePatchPanel;
	}
	public void setChildAsidePatchPanel(String childAsidePatchPanel) {
		this.childAsidePatchPanel = childAsidePatchPanel;
	}
}
