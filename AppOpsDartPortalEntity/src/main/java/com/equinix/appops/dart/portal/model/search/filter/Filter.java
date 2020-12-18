
package com.equinix.appops.dart.portal.model.search.filter;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "lable",
    "values"
})
public class Filter implements Serializable
{

    @JsonProperty("key")
    private String key;
    @JsonProperty("lable")
    private String lable;
    @JsonProperty("values")
    private List<String> values = null;
    private final static long serialVersionUID = -4707725465334478841L;
    
    public Filter(){
    	
    }
    
    public Filter(String key, String lable, List<String> values) {
		super();
		this.key = key;
		this.lable = lable;
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

    @JsonProperty("lable")
    public String getLable() {
        return lable;
    }

    @JsonProperty("lable")
    public void setLable(String lable) {
        this.lable = lable;
    }

    @JsonProperty("values")
    public List<String> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<String> values) {
        this.values = values;
    }

	@Override
	public String toString() {
		return "Filter [key=" + key + ", lable=" + lable + ", values=" + values + "]";
	}
    
}
