package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface YearSales {
    @JsonProperty("month")
    String getMonthName();
    @JsonProperty("month_sales")
    Double getMonthSales();
}
