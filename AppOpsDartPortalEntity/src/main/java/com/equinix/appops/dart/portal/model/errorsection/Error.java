
package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "pkid",
    "category",
    "code",
    "asset_count",
    "current_asset_count",
    "trend",
    "error_description",
    "biz_group",
    "alert_flag"
})
public class Error implements Serializable
{

    @JsonProperty("pkid")
    private String pkid;
    @JsonProperty("category")
    private String category;
    @JsonProperty("code")
    private String code;
    @JsonProperty("asset_count")
    private String assetCount;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("biz_group")
    private String bizGroup;
    
    @JsonProperty("current_asset_count")
    private String currentAssetCount;
    @JsonProperty("trend")
    private String trend;
    
    @JsonProperty("alert_flag")
    private String alertFlag;
    
    private final static long serialVersionUID = 3620313653262008177L;

    @JsonProperty("alert_flag") 
    public String getAlertFlag() {
		return alertFlag;
	}
    @JsonProperty("alert_flag")
	public void setAlertFlag(String alertFlag) {
		this.alertFlag = alertFlag;
	}

	@JsonProperty("current_asset_count")
    public String getCurrentAssetCount() {
		return currentAssetCount;
	}

    @JsonProperty("current_asset_count")
	public void setCurrentAssetCount(String currentAssetCount) {
		this.currentAssetCount = currentAssetCount;
	}

	@JsonProperty("trend")
	public String getTrend() {
		return trend;
	}

	@JsonProperty("trend")
	public void setTrend(String trend) {
		this.trend = trend;
	}

	@JsonProperty("pkid")
    public String getPkid() {
        return pkid;
    }

    @JsonProperty("pkid")
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("asset_count")
    public String getAssetCount() {
        return assetCount;
    }

    @JsonProperty("asset_count")
    public void setAssetCount(String assetCount) {
        this.assetCount = assetCount;
    }

    @JsonProperty("error_description")
    public String getErrorDescription() {
        return errorDescription;
    }

    @JsonProperty("error_description")
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @JsonProperty("biz_group")
    public String getBizGroup() {
        return bizGroup;
    }

    @JsonProperty("biz_group")
    public void setBizGroup(String bizGroup) {
        this.bizGroup = bizGroup;
    }

}
