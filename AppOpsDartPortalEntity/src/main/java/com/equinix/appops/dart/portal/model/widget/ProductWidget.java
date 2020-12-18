
package com.equinix.appops.dart.portal.model.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "groups"
})
public class ProductWidget implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("groups")
    private List<ProductWidgetGroup> groups = new ArrayList<>();
    private final static long serialVersionUID = 8302955525956516618L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("groups")
    public List<ProductWidgetGroup> getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(List<ProductWidgetGroup> groups) {
        this.groups = groups;
    }

}
