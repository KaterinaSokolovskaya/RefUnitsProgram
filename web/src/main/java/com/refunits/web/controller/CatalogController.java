package com.refunits.web.controller;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.service.dto.SearchUnit;
import com.refunits.service.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CatalogController {

    private final CatalogService catalogService;

    @ModelAttribute("allUnitRanges")
    public UnitRange[] getAllUnitRanges() {

        return UnitRange.values();
    }

    @ModelAttribute("allBoilingPoints")
    public BoilingPoint[] getAllBoilingPoints() {

        return BoilingPoint.values();
    }

    @GetMapping("/catalog")
    public String getCatalogPage(Model model) {
        model.addAttribute("units", catalogService.getAll());
        model.addAttribute("searchUnit", new SearchUnit());

        return "catalog";
    }

    @PostMapping("/catalog")
    public String getFilteredCatalogPage(Model model, SearchUnit searchUnit) {
        model.addAttribute("units", catalogService.getFiltered(searchUnit));

        return "catalog";
    }
}