package com.refunits.service.dto;

import com.refunits.database.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewBasicPreOrderInfo {

    private Integer id;
    private String company;
    private LocalDate date;
    private OrderStatus status;
}