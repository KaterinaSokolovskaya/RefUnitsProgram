package com.refunits.service.dto;

import com.refunits.database.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchOrder {

    private Integer id;
    private String company;
    private String date;
    private OrderStatus status;
}