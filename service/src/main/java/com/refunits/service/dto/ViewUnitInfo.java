package com.refunits.service.dto;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewUnitInfo {

    private Integer id;
    private String name;
    private UnitRange range;
    private BoilingPoint boilingPoint;
    private Double refCapacity;
    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;
    private String description;
    private Integer price;
    private Set<ViewOptionInfo> options;
}