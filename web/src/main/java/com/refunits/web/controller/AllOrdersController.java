package com.refunits.web.controller;

import com.refunits.database.enumeration.OrderStatus;
import com.refunits.service.dto.SearchOrder;
import com.refunits.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllOrdersController {

    private final OrderService orderService;

    @ModelAttribute("allStatuses")
    public OrderStatus[] getAllStatuses() {

        return OrderStatus.values();
    }

    @GetMapping("/all-orders")
    public String getAllOrdersPage(Model model) {
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("searchOrder", new SearchOrder());

        return "all-orders";
    }

    @PostMapping(value = "/all-orders")
    public String getFilteredOrdersPage(Model model, SearchOrder searchOrder) {
        model.addAttribute("orders", orderService.getFiltered(searchOrder));

        return "all-orders";
    }
}