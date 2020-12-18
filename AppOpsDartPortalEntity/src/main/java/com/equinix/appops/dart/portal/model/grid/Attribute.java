
package com.equinix.appops.dart.portal.model.grid;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	 "name",
	    "display_name",
	    "key",
	    "values",
	    "type",
	    "data_type",
	    "editable",
	    "validate",
	    "isRequired",
	    "sot",
	    "lov",
	    "pricing_flg",
	    "dfrlineid",
	    "runDependent",
	    "group",
	    "isMandatory"
    
})
public class Attribute implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("key")
    private String key;
    @JsonProperty("values")
    private Values values;
    
    @JsonProperty("lov")
    private List<String> lov = null;
    private final static long serialVersionUID = 6081791379586730906L;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("data_type")
    private String dataType;
    
    @JsonProperty("editable")
    private String editable;
    
    @JsonProperty("validate")
    private String validate;
    
    @JsonProperty("sot")
    private String sot;
   
    @JsonProperty("dfrlineid")
    private String dfrlineid;
    
    @JsonProperty("pricing_flg")
    private String pricingFlg;
    
    @JsonProperty("runDependent")
    private String runDependent;
    
    @JsonProperty("isRequired")
    private String isRequired;
    
    @JsonProperty("isMandatory")
    private String isMandatory;
    
    @JsonProperty("group")
    private String group;
    
    @JsonProperty("dfrlineid")
    public String getDfrlineid() {
		return dfrlineid;
	}
    
    @JsonProperty("dfrlineid")
	public void setDfrlineid(String dfrlineid) {
		this.dfrlineid = dfrlineid;
	}
    
    
	@JsonProperty("lov")
    public List<String> getLov() {
		return lov;
	}
    @JsonProperty("lov")
	public void setLov(List<String> lov) {
		this.lov = lov;
	}
	@JsonProperty("sot")
    public String getSot() {
		return sot;
	}
    @JsonProperty("sot")
    public void setSot(String sot) {
		this.sot = sot;
	}

	@JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("values")
    public Values getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(Values values) {
        this.values = values;
    }
    @JsonProperty("key")
	public String getKey() {
		return key;
	}
    @JsonProperty("key")
	public void setKey(String key) {
		this.key = key;
	}
    @JsonProperty("type")
	public String getType() {
		return type;
	}

    @JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}
 
    @JsonProperty("data_type")
	public String getDataType() {
		return dataType;
	}
    @JsonProperty("data_type")
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
    @JsonProperty("editable")
	public String getEditable() {
		return editable;
	}
    @JsonProperty("editable")
	public void setEditable(String editable) {
		this.editable = editable;
	}
    @JsonProperty("pricing_flg")
	public String getPricingFlg() {
		return pricingFlg;
	}
    @JsonProperty("pricing_flg")
	public void setPricingFlg(String pricingFlg) {
		this.pricingFlg = pricingFlg;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
	@JsonProperty("display_name")
	public String getDisplayName() {
		return displayName;
	}
	@JsonProperty("display_name")
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
        @JsonProperty("runDependent")
	public String getRunDependent() {
		return runDependent;
	}
	@JsonProperty("runDependent")
	public void setRunDependent(String runDependent) {
		this.runDependent = runDependent;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
}
