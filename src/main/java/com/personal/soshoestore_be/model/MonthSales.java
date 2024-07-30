package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MonthSales {
    String getDate();
    @JsonProperty("date_sales")
    Double getDateSales();
}
