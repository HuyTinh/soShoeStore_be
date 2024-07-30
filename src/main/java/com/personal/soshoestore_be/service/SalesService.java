package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.model.ShoeCategoryYearSales;
import com.personal.soshoestore_be.model.TopShoeSalesInMonth;
import com.personal.soshoestore_be.model.YearSales;

import java.util.List;

public interface SalesService {
    List<YearSales> getYearSales();

    List<ShoeCategoryYearSales> getShoeCategoryYearSales();

    List<TopShoeSalesInMonth> getTopShoeSalesInMonth(int month);
}
