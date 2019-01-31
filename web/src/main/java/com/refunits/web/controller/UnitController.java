package com.refunits.web.controller;

import com.refunits.service.dto.Product;
import com.refunits.service.service.ProductService;
import com.refunits.service.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UnitController {

    private final UnitService unitService;
    private final ProductService productService;

    @GetMapping("/unit")
    public String getUnitPage(Model model, @RequestParam("id") String paramId) {
        model.addAttribute("searchUnit", unitService.getById(Integer.valueOf(paramId)));

        return "unit";
    }

    @PostMapping("/unit-p")
    public String addProductToOrder(@ModelAttribute("product") Product product) {
        productService.addProduct(SecurityContextHolder.getContext().getAuthentication().getName(), product);

        return "redirect:/catalog";
    }
}