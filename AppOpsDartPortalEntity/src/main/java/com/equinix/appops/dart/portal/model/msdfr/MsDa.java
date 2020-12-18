
package com.equinix.appops.dart.portal.model.msdfr;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sbl",
    "snow",
    "snowConfigDa",
    "snowConfigXa"
    
})
public class MsDa implements Serializable
{

    @JsonProperty("sbl")
    private String sbl;
    @JsonProperty("snow")
    private String snow;
    @JsonProperty("snowConfigDa")
    private String snowConfigDa;
    @JsonProperty("snowConfigXa")
    private String snowConfigXa;
    private final static long serialVersionUID = -763804115801320579L;
	public String getSbl() {
		return sbl;
	}
	public void setSbl(String sbl) {
		this.sbl = sbl;
	}
	public String getSnow() {
		return snow;
	}
	public void setSnow(String snow) {
		this.snow = snow;
	}
	public String getSnowConfigDa() {
		return snowConfigDa;
	}
	public void setSnowConfigDa(String snowConfigDa) {
		this.snowConfigDa = snowConfigDa;
	}
	public String getSnowConfigXa() {
		return snowConfigXa;
	}
	public void setSnowConfigXa(String snowConfigXa) {
		this.snowConfigXa = snowConfigXa;
	}

    
}
