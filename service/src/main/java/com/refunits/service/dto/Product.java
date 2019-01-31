package com.refunits.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Integer id;
    private Integer unitId;
    private List<Integer> selectedOptions;
    private Integer number;
    private Integer sum;
}