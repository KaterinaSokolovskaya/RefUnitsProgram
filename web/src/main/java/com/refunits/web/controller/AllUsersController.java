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

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllUsersController {

    private final RegisteredUserService userService;

    @ModelAttribute("allRoles")
    public UserRole[] getAllRoles() {

        return UserRole.values();
    }

    @GetMapping("/all-users")
    public String getAllUsersPage(Model model) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("searchUser", new SearchUser());

        return "all-users";
    }

    @PostMapping(value = "/all-users")
    public String getFilteredUsersPage(Model model, SearchUser searchUser) {
        model.addAttribute("users", userService.getFiltered(searchUser));

        return "all-users";
    }
}