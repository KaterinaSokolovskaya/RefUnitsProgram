package com.refunits.web.controller;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.service.dto.CreateUnit;
import com.refunits.service.service.OptionService;
import com.refunits.service.service.UnitService;
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
public class CreateUnitController {

    @ModelAttribute("allUnitRanges")
    public UnitRange[] getAllUnitRanges() {

        return UnitRange.values();
    }

    @ModelAttribute("allBoilingPoints")
    public BoilingPoint[] getAllBoilingPoints() {

        return BoilingPoint.values();
    }

    private final UnitService unitService;
    private final OptionService optionService;

    @GetMapping("/create-unit")
    public String getUnitPage(Model model, @ModelAttribute("createUnit") CreateUnit createUnit) {
        model.addAttribute("options", optionService.getAll());

        return "create-unit";
    }

    @PostMapping("/create-unit")
    public String createNewUnit(Model model, @Valid CreateUnit createUnit, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("createUnit", createUnit);

            return "create-unit";
        } else {
            Integer newUnitId = unitService.save(createUnit);

            return "redirect:/unit?id=" + newUnitId;
        }
    }
}