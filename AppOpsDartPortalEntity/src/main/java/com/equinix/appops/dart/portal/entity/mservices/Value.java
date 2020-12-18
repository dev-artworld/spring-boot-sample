package com.equinix.appops.dart.portal.entity.mservices;

public class Value {

    private String display_value;
    private String value;
    private String link;
    public Value(){}
    public Value(String display_value, String value, String link) {
        this.display_value = display_value;
        this.value = value;
        this.link = link;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
