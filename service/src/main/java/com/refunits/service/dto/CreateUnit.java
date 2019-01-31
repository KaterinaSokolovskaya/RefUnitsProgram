package com.refunits.service.dto;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUnit {

    private Integer id;

    @NotBlank(message = "req.field")
    private String name;

    private UnitRange range;

    private BoilingPoint boilingPoint;

    @Pattern(regexp = "\\d+.?\\d{0,2}", message = "ref.capacity.message")
    private String refCapacity;

    private Integer weight;

    private Integer length;

    private Integer width;

    private Integer height;

    private String description;

    @NotNull(message = "req.field")
    private Integer price;

    private List<Integer> optionIds;
}