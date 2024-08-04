package com.personal.soshoestore_be.mapper;

import com.personal.soshoestore_be.dto.CartDetailDTO;
import com.personal.soshoestore_be.dto.OrderDetailDTO;
import com.personal.soshoestore_be.model.OrderDetail;
import com.personal.soshoestore_be.response.OrderDetailResponse;
import com.personal.soshoestore_be.response.SubOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderDetailMapper {

  public abstract OrderDetail toEntity(OrderDetailDTO orderDetailDTO);

  public OrderDetailResponse toResponse(OrderDetail orderDetail) {
    return OrderDetailResponse.builder()
        .id(orderDetail.getId())
        .orderId(orderDetail.getOrder().getId())
        .shoeId(orderDetail.getShoe().getId())
        .price(orderDetail.getPrice())
        .numberOfProduct(orderDetail.getNumberOfProduct())
        .totalMoney(orderDetail.getTotalMoney())
        .colourId(orderDetail.getColour().getId())
        .sizeId(orderDetail.getSize().getId())
        .build();
  };

  public SubOrderResponse toSubOrderResponse(OrderDetail orderDetail){
    return SubOrderResponse.builder()
            .id(orderDetail.getId())
            .shoeName(orderDetail.getShoe().getName())
            .imageUrl(orderDetail.getShoe().getImageUrl())
            .colour(orderDetail.getColour())
            .size(orderDetail.getSize())
            .price(orderDetail.getPrice())
            .numberOfProduct(orderDetail.getNumberOfProduct())
            .totalMoney(orderDetail.getTotalMoney())
            .build();
  }

  public OrderDetail mapToEntity(OrderDetail orderDetail, OrderDetailDTO orderDetailDTO) {
    orderDetail.setPrice(orderDetailDTO.getPrice());
    orderDetail.setNumberOfProduct(orderDetailDTO.getNumberOfProduct());
    orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
    return orderDetail;
  }

  public OrderDetailDTO mapToDTO(CartDetailDTO cartDetailDTO) {
    return OrderDetailDTO.builder()
            .shoeId(cartDetailDTO.getShoeId())
            .sizeId(cartDetailDTO.getSize().getId())
            .numberOfProduct(cartDetailDTO.getQuantity())
            .price(cartDetailDTO.getPrice())
            .totalMoney(cartDetailDTO.getPrice() * cartDetailDTO.getQuantity())
            .build();
  }
}
