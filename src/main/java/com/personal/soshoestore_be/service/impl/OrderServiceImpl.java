package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.dto.OrderDTO;
import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.mapper.OrderMapper;
import com.personal.soshoestore_be.model.Order;
import com.personal.soshoestore_be.model.User;
import com.personal.soshoestore_be.repository.OrderRepository;
import com.personal.soshoestore_be.repository.UserRepository;
import com.personal.soshoestore_be.service.OrderService;
import lombok.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Getter
    @Builder
    static class OrderValidation {
        private User user;
        private Date shippingDate;
    }


    @Override
    public Order createOrder(OrderDTO orderDTO) {
        OrderValidation orderValidation = valid(orderDTO);

        Order newOrder = orderMapper.toEntity(orderDTO);
        newOrder.setUser(orderValidation.getUser());
        newOrder.setShippingDate(orderValidation.getShippingDate());
        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + orderId));
    }

    @Override
    public Order updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + orderId));

        OrderValidation orderValidation = valid(orderDTO);

        Order newOrder = orderMapper.mapToEntity(existingOrder, orderDTO);
        newOrder.setShippingDate(orderValidation.getShippingDate());
        newOrder.setUser(orderValidation.getUser());
        return orderRepository.save(newOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public Page<Order> getAllOrderByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    private OrderValidation valid(OrderDTO orderDTO){
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));

        Date shippingDate = orderDTO.getShippingDate() == null ? DateUtils.addDays(new Date(), 2) : orderDTO.getShippingDate();
        if(shippingDate.before(new Date())){
            throw new RuntimeException("Shipping date cannot be in the past.");
        }
        return OrderValidation.builder()
                .user(existingUser)
                .shippingDate(shippingDate)
                .build();
    }
}
