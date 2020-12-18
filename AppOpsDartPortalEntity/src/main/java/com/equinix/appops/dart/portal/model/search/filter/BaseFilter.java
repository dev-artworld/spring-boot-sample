package com.equinix.appops.dart.portal.model.search.filter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"key",
	"keyword",
	"dfrId"
})
public class BaseFilter implements Serializable {
	
	@JsonProperty("key")
	private String key;
	
	@JsonProperty("keyword")
	private String keyword;
	
	@JsonProperty("dfrId")
	private String dfrId;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
}
