
package com.equinix.appops.dart.portal.model.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"dfrlineid",
	"validate",
	"name",
    "attributes",
    "assetNumber",
    "dqmErrorFlag",
    "dqmErrorCodes",
    "fixedValCodes",
    "redAttrNames",
    "greenAttrNames",
    "redRowIdentifier",
    "greenRowIdentifier"
	
})
public class Product implements Serializable
{
	@JsonProperty("dfrlineid")
    private String dfrlineid;
	
	@JsonProperty("validate")
	private String validate;
	
	@JsonProperty("name")
    private String name;
	
    @JsonProperty("attributes")
    private List<Attribute> attributes = new ArrayList<>();
    
    @JsonProperty("assetNumber")
    private String assetNumber;
    
    @JsonProperty("dqmErrorFlag")
    private String dqmErrorFlag;
    
    @JsonProperty("dqmErrorCodes")
    private String dqmErrorCodes;
    
    @JsonProperty("fixedValCodes")
    private String fixedValCodes;
	
    @JsonProperty("redAttrNames")
	private String redAttrNames;
	
    @JsonProperty("greenAttrNames")
	private String greenAttrNames;
	
    @JsonProperty("redRowIdentifier")
	private String redRowIdentifier;
	
    @JsonProperty("greenRowIdentifier")
	private String greenRowIdentifier;

    
    private final static long serialVersionUID = -8062950703343721213L;
    
    @JsonProperty("validate")
    public String getValidate() {
		return validate;
	}
    @JsonProperty("validate")
	public void setValidate(String validate) {
		this.validate = validate;
	}

	@JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("attributes")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    @JsonProperty("dfrlineid")
	public String getDfrlineid() {
		return dfrlineid;
	}
    @JsonProperty("dfrlineid")
	public void setDfrlineid(String dfrlineid) {
		this.dfrlineid = dfrlineid;
	}
    
	public String getAssetNumber() {
		return assetNumber;
	}
	
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	
	public String getDqmErrorFlag() {
		return dqmErrorFlag;
	}
	
	public void setDqmErrorFlag(String dqmErrorFlag) {
		this.dqmErrorFlag = dqmErrorFlag;
	}
	
	public String getDqmErrorCodes() {
		return dqmErrorCodes;
	}
	
	public void setDqmErrorCodes(String dqmErrorCodes) {
		this.dqmErrorCodes = dqmErrorCodes;
	}
	
	public String getFixedValCodes() {
		return fixedValCodes;
	}
	public void setFixedValCodes(String fixedValCodes) {
		this.fixedValCodes = fixedValCodes;
	}
	public String getRedAttrNames() {
		return redAttrNames;
	}
	public void setRedAttrNames(String redAttrNames) {
		this.redAttrNames = redAttrNames;
	}
	public String getGreenAttrNames() {
		return greenAttrNames;
	}
	public void setGreenAttrNames(String greenAttrNames) {
		this.greenAttrNames = greenAttrNames;
	}
	public String getRedRowIdentifier() {
		return redRowIdentifier;
	}
	public void setRedRowIdentifier(String redRowIdentifier) {
		this.redRowIdentifier = redRowIdentifier;
	}
	public String getGreenRowIdentifier() {
		return greenRowIdentifier;
	}
	public void setGreenRowIdentifier(String greenRowIdentifier) {
		this.greenRowIdentifier = greenRowIdentifier;
	}
	
	
}
