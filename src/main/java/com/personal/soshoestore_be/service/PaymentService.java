package com.personal.soshoestore_be.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;

public interface PaymentService {
    String payByVNPay(HttpServletRequest req, Double amount) throws Exception;
    String payByPayPal(HttpServletRequest req,Double amount) throws Exception;
}
