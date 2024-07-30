package com.personal.soshoestore_be.mapper;

import com.personal.soshoestore_be.dto.OrderDTO;
import com.personal.soshoestore_be.model.Order;
import com.personal.soshoestore_be.repository.UserRepository;
import com.personal.soshoestore_be.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper {

    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(com.personal.soshoestore_be.enums.OrderStatus.PENDING)")
    @Mapping(target = "active", expression = "java(true)")
    public abstract Order toEntity(OrderDTO orderDTO);

    @Mapping(target = "userId", expression = "java(order.getUser().getId())")
    public abstract OrderResponse toResponse(Order order);

    public OrderListResponse toListResponse (Page<OrderResponse> orderResponsePage){
        Pageable pageable = orderResponsePage.getPageable();
        return OrderListResponse.builder()
                .orders(orderResponsePage.getContent())
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages(orderResponsePage.getTotalPages())
                .totalElements(orderResponsePage.getTotalElements()).build();
    }



    public Order mapToEntity (Order order, OrderDTO orderDTO){
        order.setFirstName(orderDTO.getFirstName());
        order.setLastName(orderDTO.getLastName());
        order.setEmail(orderDTO.getEmail());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setAddress(orderDTO.getAddress());
        order.setTotalMoney(orderDTO.getTotalMoney());
        order.setShippingMethod(orderDTO.getShippingMethod());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        return order;
    }

}
