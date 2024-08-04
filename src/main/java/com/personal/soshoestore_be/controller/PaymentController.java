package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.dto.PaymentDTO;
import com.personal.soshoestore_be.service.OrderService;
import com.personal.soshoestore_be.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> payment(HttpServletRequest req, @RequestBody PaymentDTO paymentDTO) throws Exception {
        String paymentURL = "";
        log.info("Add Queue Order UserId {} successfully", orderService.addToQueueOrder(paymentDTO.getOrderDTO()).getId());
        if(paymentDTO.getPaymentMethod().equals("VNPay")){
            paymentURL = paymentService.payByVNPay(req, paymentDTO.getAmount());
        } else if(paymentDTO.getPaymentMethod().equals("PayPal")) {
            paymentURL = paymentService.payByPayPal(req, paymentDTO.getAmount());
        }
        return ResponseEntity.ok(paymentURL);
    }

}
