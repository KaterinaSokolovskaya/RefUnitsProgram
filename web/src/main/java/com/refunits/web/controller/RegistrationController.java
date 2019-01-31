package com.refunits.web.controller;

import com.refunits.service.dto.CreateUser;
import com.refunits.service.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final RegisteredUserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("createUser") CreateUser createUser) {

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(Model model, @Valid CreateUser createUser, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("createUser", createUser);

            return "registration";
        } else {
            userService.saveUser(createUser);

            return "login";
        }
    }
}