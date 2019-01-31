package com.refunits.web.controller;

import com.refunits.service.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final RegisteredUserService userService;

    @GetMapping("/account")
    public String getAccountPage(Model model) {
        model.addAttribute("userInfo", userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "account";
    }
}