package com.refunits.service.dto;

import com.refunits.database.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchUser {

    private Integer id;
    private String company;
    private UserRole role;
}