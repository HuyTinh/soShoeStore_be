package com.personal.soshoestore_be.service;

import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    String payByVNPay(HttpServletRequest req, Double amount) throws Exception;
    String payByPayPal(HttpServletRequest req,Double amount) throws Exception;
}
