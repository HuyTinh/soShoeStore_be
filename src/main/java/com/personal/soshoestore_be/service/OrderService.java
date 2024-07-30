package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.dto.OrderDTO;
import com.personal.soshoestore_be.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    Order createOrder(OrderDTO orderDTO);

    Order getOrderById(Long orderId);

    Order updateOrder(Long orderId, OrderDTO orderDTO);

    void deleteOrder(Long orderId);

    Page<Order> getAllOrderByUserId(Long userId, Pageable pageable);
}
