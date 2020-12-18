
package com.equinix.appops.dart.portal.model.msdfr;

import java.io.Serializable;
import java.util.List;

import com.equinix.appops.dart.portal.model.dfr.Da;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "da",
    "isSelectedAll",
    "slected_row_ids"
})
public class MsProduct implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("da")
    private MsDa msDa;
    
    @JsonProperty("isSelectedAll")
    private String isSelectedAll;
    @JsonProperty("slected_row_ids")
    private List<String> selectedRowIds;
    
    private final static long serialVersionUID = -6991756490935049703L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("da")
    public MsDa getMsDa() {
		return msDa;
	}
    @JsonProperty("da")
	public void setMsDa(MsDa msDa) {
		this.msDa = msDa;
    }

    @JsonProperty("isSelectedAll")
	public String getIsSelectedAll() {
		return isSelectedAll;
	}

    @JsonProperty("isSelectedAll")
	public void setIsSelectedAll(String isSelectedAll) {
		this.isSelectedAll = isSelectedAll;
	}

    @JsonProperty("slected_row_ids")
	public List<String> getSelectedRowIds() {
		return selectedRowIds;
	}

    @JsonProperty("slected_row_ids")
	public void setSelectedRowIds(List<String> selectedRowIds) {
		this.selectedRowIds = selectedRowIds;
	}
    
    
}
