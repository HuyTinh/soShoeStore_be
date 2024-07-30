package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderListResponse {
    List<OrderResponse> orders;
    @JsonProperty("page_number")
    Integer pageNumber;
    @JsonProperty("page_size")
    Integer pageSize;
    @JsonProperty("total_pages")
    Integer totalPages;
    @JsonProperty("total_elements")
    Long totalElements;
}
