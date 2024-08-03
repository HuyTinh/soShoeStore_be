package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.dto.OrderDTO;
import com.personal.soshoestore_be.mapper.OrderDetailMapper;
import com.personal.soshoestore_be.mapper.OrderMapper;
import com.personal.soshoestore_be.service.OrderDetailService;
import com.personal.soshoestore_be.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final OrderMapper orderMapper;

    private final OrderDetailMapper orderDetailMapper;


    @PostMapping
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDto) {
        try {
            return ResponseEntity.ok(orderMapper.toResponse(orderService.createOrder(orderDto)));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@Valid @PathVariable("id") Long id) {
        System.out.println(orderService.getOrderById(id).getFirstName());
        try {
            return ResponseEntity.ok(orderMapper.toResponse(orderService.getOrderById(id)));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable("user_id") Long userId, @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "20") int size) {
        try {
            return ResponseEntity.ok(orderMapper.toListResponse(orderService.getAllOrderByUserId(userId, getPageable(page, size)).map(orderMapper::toResponse).map(orderResponse -> {
                orderResponse.setOrderDetails(orderDetailService.getOrderDetailsByOrderId(orderResponse.getId()).stream().map(orderDetailMapper::toSubOrderResponse).toList());
                return orderResponse;
            })));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody OrderDTO orderDto
    ){
        try {
            return ResponseEntity.ok(orderMapper.toResponse(orderService.updateOrder(id, orderDto)));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
            @Valid @PathVariable("id") Long id
    ){
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }

    private Pageable getPageable(int page, int size){
        return PageRequest.of(page, size);
    }
}


