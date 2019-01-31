package com.refunits.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOption {

    private Integer id;

    @NotBlank(message = "req.field")
    private String name;

    private String description;

    @NotNull(message = "req.field")
    private Integer price;
}