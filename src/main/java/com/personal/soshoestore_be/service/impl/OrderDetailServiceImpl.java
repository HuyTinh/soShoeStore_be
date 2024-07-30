package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.dto.OrderDetailDTO;
import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.mapper.OrderDetailMapper;
import com.personal.soshoestore_be.model.*;
import com.personal.soshoestore_be.repository.*;
import com.personal.soshoestore_be.service.OrderDetailService;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ShoeRepository shoeRepository;
    private final SizeRepository sizeRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class OrderDetailValidation {
        private Order order;
        private Shoe shoe;
        private Size size;
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO)  {
        OrderDetailValidation orderDetailValidation = valid(orderDetailDTO);

        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        orderDetail.setOrder(orderDetailValidation.getOrder());
        orderDetail.setShoe(orderDetailValidation.getShoe());
        orderDetail.setColour(orderDetailValidation.getShoe().getShoeColour());
        orderDetail.setSize(orderDetailValidation.getSize());
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> createMultiOrderDetail(List<OrderDetailDTO> orderDetailDTOs)  {
        return orderDetailRepository.saveAll(orderDetailDTOs.stream().map((orderDetailDTO) -> {

            OrderDetailValidation orderDetailValidation = valid(orderDetailDTO);

            OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
            orderDetail.setOrder(orderDetailValidation.getOrder());
            orderDetail.setShoe(orderDetailValidation.getShoe());
            orderDetail.setColour(orderDetailValidation.getShoe().getShoeColour());
            orderDetail.setSize(orderDetailValidation.getSize());
            return orderDetail;
        }).toList());
    }

    @Override
    public OrderDetail getOrderDetailById(Long orderDetailId) {
        return orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException(String.format("OrderDetail with (id = %d) is not found", orderDetailId)));
    }

    @Override
    public OrderDetail updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO)  {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException(String.format("OrderDetail with (id = %d) is not found", orderDetailId)));

        OrderDetailValidation orderDetailValidation = valid(orderDetailDTO);

        OrderDetail newOrderDetail = orderDetailMapper.mapToEntity(existingOrderDetail, orderDetailDTO);
        newOrderDetail.setOrder(orderDetailValidation.getOrder());
        newOrderDetail.setShoe(orderDetailValidation.getShoe());
        newOrderDetail.setColour(orderDetailValidation.getShoe().getShoeColour());
        newOrderDetail.setSize(orderDetailValidation.getSize());
        return newOrderDetail;
    }

    @Override
    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    private OrderDetailValidation valid(OrderDetailDTO orderDetailDTO) {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(String.format("Order with (id = %d) is not found", orderDetailDTO.getOrderId())));

        Shoe shoe = shoeRepository.findById(orderDetailDTO.getShoeId())
                .orElseThrow(() -> new DataNotFoundException(String.format("Shoe with (id = %d) is not found", orderDetailDTO.getShoeId())));

        Size size = sizeRepository.findById(orderDetailDTO.getSizeId())
                .orElseThrow(() -> new DataNotFoundException(String.format("Size with (id = %d) is not found", orderDetailDTO.getSizeId())));

        shoe.getSizes().stream().filter(s -> Objects.equals(s.getId() ,orderDetailDTO.getSizeId())).findFirst().orElseThrow(
                () -> new DataNotFoundException(String.format("Shoe with (id = %d) don't have Size with (id = %d) ", shoe.getId(),orderDetailDTO.getSizeId()))
        );

        return OrderDetailValidation.builder().order(order).shoe(shoe).size(size).build();
    }
}
