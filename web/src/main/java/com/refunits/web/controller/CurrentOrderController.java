package com.refunits.web.controller;

import com.refunits.service.dto.ViewProduct;
import com.refunits.service.service.OrderService;
import com.refunits.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentOrderController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/current-order")
    public String getCurrentOrderPage(Model model) {
        List<ViewProduct> products = productService.viewProducts(productService.getProductTemps(SecurityContextHolder.getContext().getAuthentication().getName()));
        products.forEach(prod -> prod.setSubtotal(prod.getSum() * prod.getNumber()));
        Integer total = productService.sum(products.stream().map(ViewProduct::getSubtotal).collect(Collectors.toList()));

        model.addAttribute("products", products);
        model.addAttribute("total", total);
        model.addAttribute("updateProduct", new ViewProduct());

        return "current-order";
    }

    @PostMapping("/current-order")
    public String setProductNumber(ViewProduct updateProduct) {

        List<ViewProduct> products = productService.viewProducts(productService.getProductTemps(SecurityContextHolder.getContext().getAuthentication().getName()));

        products.stream().filter(prod -> prod.getUnit().getName().equals(updateProduct.getUnit().getName()))
                .filter(prod -> prod.getSum().equals(updateProduct.getSum()))
                .forEach(prod -> prod.setNumber(updateProduct.getNumber()));
        products.forEach(prod -> prod.setSubtotal(prod.getSum() * prod.getNumber()));

        productService.removeProduct(SecurityContextHolder.getContext().getAuthentication().getName());
        productService.addAllProducts(SecurityContextHolder.getContext().getAuthentication().getName(),
                products);

        return "redirect:/current-order";
    }

    @PostMapping("/create-order")
    public String saveCurrentOrder(String comment) {
        orderService.saveOrder(SecurityContextHolder.getContext().getAuthentication().getName(), comment);

        return "redirect:/account";
    }
}