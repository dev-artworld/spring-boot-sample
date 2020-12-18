
package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total_error_count",
    "total_distinct_count",
    "total_distinct_asset"
})
public class ErrorValue implements Serializable
{

    @JsonProperty("total_error_count")
    private String totalErrorCount;
    @JsonProperty("total_distinct_count")
    private String totalDistinctCount;
    @JsonProperty("total_distinct_asset")
    private String totalDistinctAsset;
    private final static long serialVersionUID = 2089130839729730367L;

    @JsonProperty("total_error_count")
    public String getTotalErrorCount() {
        return totalErrorCount;
    }

    @JsonProperty("total_error_count")
    public void setTotalErrorCount(String totalErrorCount) {
        this.totalErrorCount = totalErrorCount;
    }

    @JsonProperty("total_distinct_count")
    public String getTotalDistinctCount() {
        return totalDistinctCount;
    }

    @JsonProperty("total_distinct_count")
    public void setTotalDistinctCount(String totalDistinctCount) {
        this.totalDistinctCount = totalDistinctCount;
    }

    @JsonProperty("total_distinct_asset")
    public String getTotalDistinctAsset() {
        return totalDistinctAsset;
    }

    @JsonProperty("total_distinct_asset")
    public void setTotalDistinctAsset(String totalDistinctAsset) {
        this.totalDistinctAsset = totalDistinctAsset;
    }

}
