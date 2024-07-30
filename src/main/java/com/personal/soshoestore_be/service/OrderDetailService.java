package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.dto.OrderDetailDTO;
import com.personal.soshoestore_be.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) ;

    List<OrderDetail> createMultiOrderDetail(List<OrderDetailDTO> orderDetailDTOs) ;

    OrderDetail getOrderDetailById(Long orderDetailId) ;

    OrderDetail updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) ;

    void deleteOrderDetail(Long orderDetailId);

    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
