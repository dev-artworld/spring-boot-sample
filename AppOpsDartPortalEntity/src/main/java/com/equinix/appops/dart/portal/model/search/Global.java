package com.equinix.appops.dart.portal.model.search;

import java.io.Serializable;import com.fasterxml.jackson.annotation.JsonInclude;import com.fasterxml.jackson.annotation.JsonProperty;import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "keyword" })
public class Global implements Serializable {

	@JsonProperty("keyword")
	private String keyword;
	private final static long serialVersionUID = -7472360220516960512L;

	@JsonProperty("keyword")
	public String getKeyword() {
		return keyword;
	}

	@JsonProperty("keyword")
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}