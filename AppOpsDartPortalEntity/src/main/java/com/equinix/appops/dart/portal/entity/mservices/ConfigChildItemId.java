package com.equinix.appops.dart.portal.entity.mservices;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class ConfigChildItemId implements Serializable {


    @Column(name = "SYS_ID")
    private String sysId;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    public ConfigChildItemId(){}

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
