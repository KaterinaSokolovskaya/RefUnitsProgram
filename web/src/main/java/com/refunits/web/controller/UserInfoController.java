package com.refunits.web.controller;

import com.refunits.database.enumeration.UserRole;
import com.refunits.service.dto.SearchUser;
import com.refunits.service.service.RegisteredUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoController {

    private final RegisteredUserService userService;

    @ModelAttribute("allRoles")
    public UserRole[] getAllUserRoles() {

        return UserRole.values();
    }

    @GetMapping("/user-info")
    public String getUserInfoPage(Model model, @RequestParam("id") String paramId) {
        model.addAttribute("userInfo", userService.getById(Integer.valueOf(paramId)));

        return "user-info";
    }

    @PostMapping("/user-info-u")
    public String updateUserRole(Model model, @ModelAttribute("updateSearchUser") SearchUser dto) {
        userService.updateUserRole(dto);
        model.addAttribute("userInfo", userService.getById(dto.getId()));

        return "user-info";
    }
}