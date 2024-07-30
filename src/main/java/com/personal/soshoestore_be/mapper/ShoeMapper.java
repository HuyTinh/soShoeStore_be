package com.personal.soshoestore_be.mapper;

import com.personal.soshoestore_be.model.Shoe;
import com.personal.soshoestore_be.model.ShoeColour;
import com.personal.soshoestore_be.response.ShoeDifferentColourResponse;
import com.personal.soshoestore_be.response.ShoeListResponse;
import com.personal.soshoestore_be.response.ShoeResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ShoeMapper {

    @Mapping(target = "colour", expression = "java(shoe.getShoeColour())")
    public abstract ShoeResponse toResponse(Shoe shoe);

    @Mapping(target = "colour", expression = "java(getColour(shoe.getShoeColour()))")
    public abstract ShoeDifferentColourResponse toShoeDifferentColourResponse(Shoe shoe);

    public ShoeListResponse toListResponse (Page<ShoeResponse> shoePage){
        Pageable pageable = shoePage.getPageable();
        return ShoeListResponse.builder()
                .shoes(shoePage.getContent())
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages(shoePage.getTotalPages())
                .totalElements(shoePage.getTotalElements()).build();
    }

    protected String getColour(ShoeColour shoeColour){
        if(shoeColour == null){
            return null;
        }
        return shoeColour.getVamp().getName() + " / " +
                shoeColour.getQuarter().getName() + " / " +
                shoeColour.getSole().getName();
    }
}
