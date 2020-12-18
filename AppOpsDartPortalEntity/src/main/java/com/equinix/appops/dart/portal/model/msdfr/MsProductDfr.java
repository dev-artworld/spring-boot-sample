package com.equinix.appops.dart.portal.model.msdfr;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "isSelectedAll",
    "slected_row_ids",
    "da"
})
public class MsProductDfr implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("isSelectedAll")
    private String isSelectedAll;
    @JsonProperty("slected_row_ids")
    private List<String> selectedRowIds;
    @JsonProperty("da")
    private MsDa da;
    
    

	private final static long serialVersionUID = -6991756490935049703L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("is_selected_all")
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
    
    @JsonProperty("da")
   	public MsDa getDa() {
   		return da;
   	}

    @JsonProperty("da")
   	public void setDa(MsDa da) {
   		this.da = da;
   	}
}
