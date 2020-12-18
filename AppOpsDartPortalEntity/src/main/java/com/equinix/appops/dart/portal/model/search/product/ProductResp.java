
package com.equinix.appops.dart.portal.model.search.product;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "clean",
    "error",
    "total"
})
public class ProductResp implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("clean")
    private Long clean;
    @JsonProperty("error")
    private Long error;
    @JsonProperty("total")
    private Long total;
    private final static long serialVersionUID = 3778296743858509261L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("clean")
    public Long getClean() {
        return clean;
    }

    @JsonProperty("clean")
    public void setClean(Long clean) {
        this.clean = clean;
    }

    @JsonProperty("error")
    public Long getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Long error) {
        this.error = error;
    }

    @JsonProperty("total")
    public Long getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Long total) {
        this.total = total;
    }

}
