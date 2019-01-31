package com.refunits.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewBasicUnitInfo {

    private Integer id;
    private String name;
    private Integer price;
}