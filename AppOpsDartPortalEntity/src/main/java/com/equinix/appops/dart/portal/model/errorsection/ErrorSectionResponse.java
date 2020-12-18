
package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errors",
    "category",
    "dfrid"
})
public class ErrorSectionResponse implements Serializable
{

    @JsonProperty("errors")
    private List<Error> errors = new ArrayList<>();
    @JsonProperty("category")
    private List<ErrorCategory> category = new ArrayList<>();
    
    private String dfrid;
    private final static long serialVersionUID = 707034954021965920L;

    @JsonProperty("errors")
    public List<Error> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @JsonProperty("category")
    public List<ErrorCategory> getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(List<ErrorCategory> category) {
        this.category = category;
    }
    @JsonProperty("dfrid")
	public String getDfrid() {
		return dfrid;
	}
    @JsonProperty("dfrid")
	public void setDfrid(String dfrid) {
		this.dfrid = dfrid;
	}

}
