package com.refunits.web.controller;

import com.refunits.service.dto.CreateOption;
import com.refunits.service.service.OptionService;
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
public class CreateOptionController {

    private final OptionService optionService;

    @GetMapping("/create-option")
    public String getUnitPage(@ModelAttribute("createOption") CreateOption createOption) {

        return "create-option";
    }

    @PostMapping("/create-option")
    public String createNewUnit(Model model, @Valid CreateOption createOption, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("createOption", createOption);

            return "create-option";
        } else {
            optionService.save(createOption);

            return "redirect:/create-unit";
        }
    }
}