
package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "value"
})
public class ErrorCategory implements Serializable
{

    @JsonProperty("key")
    private String key;
    @JsonProperty("value")
    private ErrorValue value;
    private final static long serialVersionUID = 90151251669349200L;

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("value")
    public ErrorValue getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(ErrorValue value) {
        this.value = value;
    }

}
