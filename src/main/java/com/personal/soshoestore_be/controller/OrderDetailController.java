package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.dto.OrderDetailDTO;
import com.personal.soshoestore_be.mapper.OrderDetailMapper;
import com.personal.soshoestore_be.service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailMapper orderDetailMapper;

    private final OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDto
    ){
        try {
            return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDto));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/multi")
    public ResponseEntity<?> createMultiOrderDetail(
            @Valid @RequestBody List<@Valid OrderDetailDTO> orderDetailDtos
    ){
            log.info("Create OrderDetail Successfully");
            return ResponseEntity.ok(orderDetailService.createMultiOrderDetail(orderDetailDtos).stream().map(orderDetailMapper::toResponse).toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id
    ) {
        try {
            return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailsByOrderId(orderId).stream().map(orderDetailMapper::toResponse).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDto
    ) {
        try {
            return ResponseEntity.ok(orderDetailMapper.toResponse(orderDetailService.updateOrderDetail(id, orderDetailDto)));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok("deleteOrderDetail with id: " + id);
    }
}
