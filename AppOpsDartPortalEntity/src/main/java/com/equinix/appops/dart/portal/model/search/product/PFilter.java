
package com.equinix.appops.dart.portal.model.search.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.equinix.appops.dart.portal.util.DartUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "lable",
    "value",
    "is",
    "isNot"
})
public class PFilter implements Serializable
{

    @JsonProperty("key")
    private String key;
    @JsonProperty("lable")
    private String lable;
    @JsonProperty("value")
    private String value="";
    @JsonProperty("is")
    private List<String> is = new ArrayList<>();
    @JsonProperty("isNot")
    private List<String> isNot = new ArrayList<>();
    
    private List<String> listOfValues = new ArrayList<>();
    
    
    private final static long serialVersionUID = -7284658349233490748L;

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

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
		if (!key.equalsIgnoreCase("header2") && !key.equalsIgnoreCase("header3")) {
			if (StringUtils.isNotEmpty(this.value) && this.value.contains(",")) {
				for (String val : this.value.split(",")) {
					if (this.key.equalsIgnoreCase("header16") && val.contains("~")) {
						this.getListOfValues().add(DartUtil.sanitizeInput(val));
					} else {
						this.getListOfValues().add(val);
					}
				}
			} else if (StringUtils.isNotEmpty(this.value) && !this.value.contains(",")) {
				this.listOfValues = new ArrayList<>();
				String val = this.value;
				if (this.key.equalsIgnoreCase("header16") && val.contains("~")) {
					this.listOfValues.add(DartUtil.sanitizeInput(val));
				} else {
					this.listOfValues.add(this.value);
				}
			} else {
				this.listOfValues = new ArrayList<>();
			}
		} else {
			this.value = value;
		}
	}
    
   
    // this should be invoked when user searches for the comma separated values  
	public void setListOfValues(List<String> listOfValues) {
		this.listOfValues = listOfValues;
    }

	public List<String> getListOfValues() {
		
		return this.listOfValues;
	}

	@JsonProperty("is")
	public List<String> getIs() {
		return is;
	}

	@JsonProperty("is")
	public void setIs(List<String> is) {
		this.is = is;
	}

	@JsonProperty("isNot")
	public List<String> getIsNot() {
		return isNot;
	}

	@JsonProperty("isNot")
	public void setIsNot(List<String> isNot) {
		this.isNot = isNot;
	}

}
