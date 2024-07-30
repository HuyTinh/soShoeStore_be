package com.personal.soshoestore_be.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public interface TopShoeSalesInMonth {
    @JsonProperty("id")
    Long getShoeId();
    String getName();
    String getImageUrl();
    Double getSales();
    Integer getOrderCount();
}
