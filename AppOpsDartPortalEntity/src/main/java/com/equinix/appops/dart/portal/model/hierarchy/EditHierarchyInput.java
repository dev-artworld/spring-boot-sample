package com.equinix.appops.dart.portal.model.hierarchy;



import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"dfrid",
"dfrlineid",
"parentdfrlineid",
"product"
})
public class EditHierarchyInput implements Serializable
{

@JsonProperty("dfrid")
private String dfrid;
@JsonProperty("dfrlineid")
private String dfrlineid;
@JsonProperty("parentdfrlineid")
private String parentdfrlineid;
@JsonProperty("product")
private String product;
private final static long serialVersionUID = 3306725506248993065L;

@JsonProperty("dfrid")
public String getDfrid() {
return dfrid;
}

@JsonProperty("dfrid")
public void setDfrid(String dfrid) {
this.dfrid = dfrid;
}

@JsonProperty("dfrlineid")
public String getDfrlineid() {
return dfrlineid;
}

@JsonProperty("dfrlineid")
public void setDfrlineid(String dfrlineid) {
this.dfrlineid = dfrlineid;
}

@JsonProperty("parentdfrlineid")
public String getParentdfrlineid() {
return parentdfrlineid;
}

@JsonProperty("parentdfrlineid")
public void setParentdfrlineid(String parentdfrlineid) {
this.parentdfrlineid = parentdfrlineid;
}

@JsonProperty("product")
public String getProduct() {
return product;
}

@JsonProperty("product")
public void setProduct(String product) {
this.product = product;
}

}