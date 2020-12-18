
package com.equinix.appops.dart.portal.model.search.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filters",
    "dfrid"
})
public class SearchFilters implements Serializable
{

    @JsonProperty("filters")
    private List<Filter> filters = new ArrayList<>();
    
    @JsonProperty("dfrid")
    private String dfrid;
    private final static long serialVersionUID = 8515563020422310741L;

    @JsonProperty("filters")
    public List<Filter> getFilters() {
        return filters;
    }

    @JsonProperty("filters")
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

	@Override
	public String toString() {
		return "SearchFilters [filters=" + filters + "]";
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
