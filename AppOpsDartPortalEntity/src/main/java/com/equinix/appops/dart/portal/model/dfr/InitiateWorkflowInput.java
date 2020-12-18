package com.equinix.appops.dart.portal.model.dfr;
import java.io.Serializable;
import java.util.List;

import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dfrid",
    "priority",
    "region",
    "notes",
    "override_flag",
    "siebel_flag",
    "singleview_flag",
    "caplogix_flag",
    "clxUpdate",
    "sotArray",
    "productFilter"
    
})
public class InitiateWorkflowInput implements Serializable
{

    @JsonProperty("dfrid")
    private String dfrid;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("region")
    private String region;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("override_flag")
    private String overrideFlag;
    @JsonProperty("siebel_flag")
    private String siebelFlag;
    @JsonProperty("singleview_flag")
    private String singleviewFlag;
    @JsonProperty("caplogix_flag")
    private String caplogixFlag;
    @JsonProperty("clxUpdate")
    private String clxUpdate;
    @JsonProperty("sotArray")
    private List<String> sotArray = null;
    @JsonProperty("productFilter")
    private ProductFilter productFilter;
    
    private final static long serialVersionUID = -8184463877540369107L;

    @JsonProperty("dfrid")
    public String getDfrid() {
        return dfrid;
    }

    @JsonProperty("dfrid")
    public void setDfrid(String dfrid) {
        this.dfrid = dfrid;
    }

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("override_flag")
    public String getOverrideFlag() {
        return overrideFlag;
    }

    @JsonProperty("override_flag")
    public void setOverrideFlag(String overrideFlag) {
        this.overrideFlag = overrideFlag;
    }

    @JsonProperty("siebel_flag")
    public String getSiebelFlag() {
        return siebelFlag;
    }

    @JsonProperty("siebel_flag")
    public void setSiebelFlag(String siebelFlag) {
        this.siebelFlag = siebelFlag;
    }

    @JsonProperty("singleview_flag")
    public String getSingleviewFlag() {
        return singleviewFlag;
    }

    @JsonProperty("singleview_flag")
    public void setSingleviewFlag(String singleviewFlag) {
        this.singleviewFlag = singleviewFlag;
    }

    @JsonProperty("caplogix_flag")
    public String getCaplogixFlag() {
        return caplogixFlag;
    }

    @JsonProperty("caplogix_flag")
    public void setCaplogixFlag(String caplogixFlag) {
        this.caplogixFlag = caplogixFlag;
    }

    @JsonProperty("clxUpdate")
    public String getClxUpdate() {
        return clxUpdate;
    }

    @JsonProperty("clxUpdate")
    public void setClxUpdate(String clxUpdate) {
        this.clxUpdate = clxUpdate;
    }

    @JsonProperty("sotArray")
    public List<String> getSotArray() {
        return sotArray;
    }

    @JsonProperty("sotArray")
    public void setSotArray(List<String> sotArray) {
        this.sotArray = sotArray;
    }
    
    @JsonProperty("productFilter")
    public ProductFilter getProductFilter() {
		return productFilter;
	}
    @JsonProperty("productFilter")
    public void setProductFilter(ProductFilter productFilter) {
		this.productFilter = productFilter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InitiateWorkflowInput [dfrid=");
		builder.append(dfrid);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", region=");
		builder.append(region);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", overrideFlag=");
		builder.append(overrideFlag);
		builder.append(", siebelFlag=");
		builder.append(siebelFlag);
		builder.append(", singleviewFlag=");
		builder.append(singleviewFlag);
		builder.append(", caplogixFlag=");
		builder.append(caplogixFlag);
		builder.append(", clxUpdate=");
		builder.append(clxUpdate);
		builder.append(", sotArray=");
		builder.append(sotArray);
		builder.append("]");
		return builder.toString();
	}

    
}
