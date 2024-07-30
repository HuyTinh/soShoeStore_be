package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/year")
    public ResponseEntity<?> getYearSales() {
        return ResponseEntity.ok(salesService.getYearSales());
    }

    @GetMapping("/shoe-category")
    public ResponseEntity<?> getShoeCategoryYearSales() {
        return ResponseEntity.ok(salesService.getShoeCategoryYearSales());
    }

    @GetMapping("/shoe-top-month/{month}")
    public ResponseEntity<?> getTopShoeSalesInMonth(@PathVariable("month") int month) {
        return ResponseEntity.ok(salesService.getTopShoeSalesInMonth(month));
    }
}
