package com.equinix.appops.dart.portal.model.grid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"header",
	"value"
})
public class AuditAttribute {
	
	@JsonProperty("header")
	private String header;
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("header")
	public String getHeader() {
		return header;
	}
	
	@JsonProperty("header")
	public void setHeader(String header) {
		this.header = header;
	}
	
	@JsonProperty("value")
	public String getValue() {
		return value;
	}
	
	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
