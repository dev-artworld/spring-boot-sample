
package com.equinix.appops.dart.portal.model.grid;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "SBL",
    "SV",
    "CLX",
    "new_value"
})
public class Values implements Serializable
{

	
    @JsonProperty("SBL")
    private String sBL;
    @JsonProperty("SV")
    private String sV;
    @JsonProperty("CLX")
    private String cLX;
    
    @JsonProperty("new_value")
    private String newval;
    private final static long serialVersionUID = 5459367603635804809L;

    @JsonProperty("SBL")
    public String getSBL() {
        return sBL;
    }

    @JsonProperty("SBL")
    public void setSBL(String sBL) {
        this.sBL = sBL;
    }

    @JsonProperty("SV")
    public String getSV() {
        return sV;
    }

    @JsonProperty("SV")
    public void setSV(String sV) {
        this.sV = sV;
    }

    @JsonProperty("CLX")
    public String getCLX() {
        return cLX;
    }

    @JsonProperty("CLX")
    public void setCLX(String cLX) {
        this.cLX = cLX;
    }

    @JsonProperty("new_value")
	public String getNewval() {
		return newval;
	}

    @JsonProperty("new_value")
	public void setNewval(String newval) {
		this.newval = newval;
	}

    
}
