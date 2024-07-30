package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.model.ShoeCategoryYearSales;
import com.personal.soshoestore_be.model.TopShoeSalesInMonth;
import com.personal.soshoestore_be.model.YearSales;
import com.personal.soshoestore_be.repository.OrderRepository;
import com.personal.soshoestore_be.repository.ShoeRepository;
import com.personal.soshoestore_be.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final OrderRepository orderRepository;
    private final ShoeRepository shoeRepository;

    @Override
    public List<YearSales> getYearSales() {
        return orderRepository.getYearSales();
    }

    @Override
    public List<ShoeCategoryYearSales> getShoeCategoryYearSales() {
        return shoeRepository.getShoeCategoryYearSales();
    }

    @Override
    public List<TopShoeSalesInMonth> getTopShoeSalesInMonth(int month) {
        return shoeRepository.getTopShoesSalesInMonth(month);
    }
}
