package com.refunits.service.dto;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchUnit {

    private BoilingPoint boilingPoint;
    private UnitRange range;
    private Double minRefCapacity;
    private Double maxRefCapacity;
    private Integer limit;
    private Integer offset;
}