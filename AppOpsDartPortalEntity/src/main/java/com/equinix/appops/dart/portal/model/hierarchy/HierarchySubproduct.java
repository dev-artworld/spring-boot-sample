
package com.equinix.appops.dart.portal.model.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "key",
    "acc_no",
    "ibx",
    "p_row_id",
    "c_row_id",
    "subproducts"
})
public class HierarchySubproduct implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("key")
    private String key;
    @JsonProperty("subproducts")
    private List<HierarchySubproduct> subproducts = new ArrayList<>();
    private final static long serialVersionUID = 5158726140943121431L;
    
    @JsonProperty("acc_no")
    private String accNo;
    @JsonProperty("ibx")
    private String ibx;
    @JsonProperty("p_row_id")
    private String pRowId;
    
    @JsonProperty("c_row_id")
    private String cRowId;
    
    
    
    
    @JsonProperty("acc_no")
    public String getAccNo() {
		return accNo;
	}
    @JsonProperty("acc_no")
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	@JsonProperty("ibx")
	public String getIbx() {
		return ibx;
	}
	@JsonProperty("ibx")
	public void setIbx(String ibx) {
		this.ibx = ibx;
	}
	@JsonProperty("p_row_id")
	public String getPRowId() {
		return pRowId;
	}
	@JsonProperty("p_row_id")
	public void setPRowId(String pRowId) {
		this.pRowId = pRowId;
	}

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("subproducts")
    public List<HierarchySubproduct> getSubproducts() {
        return subproducts;
    }

    @JsonProperty("subproducts")
    public void setSubproducts(List<HierarchySubproduct> subproducts) {
        this.subproducts = subproducts;
    }

    @JsonProperty("c_row_id")
	public String getcRowId() {
		return cRowId;
	}
    @JsonProperty("c_row_id")
	public void setcRowId(String cRowId) {
		this.cRowId = cRowId;
	}
}
