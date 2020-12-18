package com.equinix.appops.dart.portal.entity.mservices;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ChildContractItemId implements Serializable {

    @Column(name = "SYS_ID")
    private String sysId;

    @Column(name = "U_CONTRACT_ITEM")
    private String contractItem;

    public ChildContractItemId(){}
    
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getContractItem() {
		return contractItem;
	}

	public void setContractItem(String contractItem) {
		this.contractItem = contractItem;
	}
    
    
}
