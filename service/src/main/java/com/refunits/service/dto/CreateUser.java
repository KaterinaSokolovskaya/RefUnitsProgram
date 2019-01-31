package com.refunits.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUser {

    @NotBlank(message = "req.field")
    private String login;

    @NotBlank(message = "req.field")
    private String password;

    private String contactPerson;

    @NotBlank(message = "req.field")
    private String company;

    @Email
    private String mail;

    private String telephone;
}