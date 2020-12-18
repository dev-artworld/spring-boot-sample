package com.equinix.appops.dart.portal.model.search.product;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "search_drop"
})
public class SearchDropBox implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("search_drop")
    private List<SearchDrop> search_drop = null;
    
    @JsonProperty("search_drop")
    public List<SearchDrop> getSearchDrop() {
        return search_drop;
    }

    @JsonProperty("search_drop")
    public void setSearchDrop(List<SearchDrop> search_drop) {
        this.search_drop = search_drop;
    }

   
}
