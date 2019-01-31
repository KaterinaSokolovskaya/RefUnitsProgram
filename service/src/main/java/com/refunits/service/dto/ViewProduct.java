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
public class ViewProduct {

    private Integer id;
    private ViewBasicUnitInfo unit;
    private List<ViewOptionInfo> options;
    private Integer sum;
    private Integer number;
    private Integer subtotal;
}