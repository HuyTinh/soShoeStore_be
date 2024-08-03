package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.dto.PaymentDTO;
import com.personal.soshoestore_be.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> payment(HttpServletRequest req, @RequestBody PaymentDTO paymentDTO) throws Exception {
        String paymentURL = "";
        if(paymentDTO.getPaymentMethod().equals("VNPAY")){
            paymentURL = paymentService.payByVNPay(req, paymentDTO.getAmount());
        } else if(paymentDTO.getPaymentMethod().equals("PayPal")) {
            paymentURL = paymentService.payByPayPal(req, paymentDTO.getAmount());
        }
        return ResponseEntity.ok(paymentURL);
    }

}
