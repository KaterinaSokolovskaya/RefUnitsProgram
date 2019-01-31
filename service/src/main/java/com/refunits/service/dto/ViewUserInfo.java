package com.refunits.service.dto;

import com.refunits.database.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewUserInfo {

    private Integer id;
    private String login;
    private LocalDate registrationDate;
    private String contactPerson;
    private String company;
    private String mail;
    private String telephone;
    private UserRole role;
    private Set<ViewBasicPreOrderInfo> preOrders;
}