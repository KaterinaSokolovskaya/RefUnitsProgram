package com.refunits.web.controller;

import com.refunits.service.dto.UpdateUser;
import com.refunits.service.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserUpdateController {

    private final RegisteredUserService userService;

    @GetMapping("/user-up")
    public String getUserInfoPage(Model model) {
        model.addAttribute("defaultUserInfo", userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "user-up";
    }

    @PostMapping("/user-up")
    public String updateUserRole(@ModelAttribute("updateSearchUser") UpdateUser dto) {
        dto.setLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.updateUserInfo(dto);

        return "redirect:/account";
    }
}