package com.equinix.appops.dart.portal.entity.mservices;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SnowConfigItemId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="SYS_ID")
    private String sysId;

    @Column(name="ATTR_NAME")
    private String attrName;

    public SnowConfigItemId(){}

   	public String getSysId() {
		return sysId;
	}

    public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
}
