package com.equinix.appops.dart.portal.entity.mservices;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="SRC_SNOW_CONFIG_ITEM_CHILD",schema="EQX_DART")
@NamedQuery(name="ConfigChildItem.findAll", query="SELECT a FROM ConfigChildItem a")
public class ConfigChildItem {

    @Column(name = "FIELD_VALUE")
    private String fieldValue;

    @EmbeddedId
    private ConfigChildItemId configChildItemId;
    
    @Column(name="DTL__CAPXUSER")
    private String dtlCapxUser ;
    @Column(name="DTL__CAPXTIMESTAMP")
    private String dtlCapxTimestamp;
    @Column(name="DTL__CAPXACTION")
    private String dtlCapxAction;



    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public ConfigChildItemId getConfigChildItemId() {
        return configChildItemId;
    }

    public void setConfigChildItemId(ConfigChildItemId configChildItemId) {
        this.configChildItemId = configChildItemId;
    }

	public String getDtlCapxUser() {
		return dtlCapxUser;
	}

	public void setDtlCapxUser(String dtlCapxUser) {
		this.dtlCapxUser = dtlCapxUser;
	}

	public String getDtlCapxTimestamp() {
		return dtlCapxTimestamp;
	}

	public void setDtlCapxTimestamp(String dtlCapxTimestamp) {
		this.dtlCapxTimestamp = dtlCapxTimestamp;
	}

	public String getDtlCapxAction() {
		return dtlCapxAction;
	}

	public void setDtlCapxAction(String dtlCapxAction) {
		this.dtlCapxAction = dtlCapxAction;
	}
    
}


