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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @ModelAttribute("allOrderStatuses")
    public OrderStatus[] getAllOrderStatuses() {

        return OrderStatus.values();
    }

    @GetMapping("/order")
    public String getOrderPage(Model model, @RequestParam("id") String paramId) {
        model.addAttribute("searchOrder", orderService.getById(Integer.valueOf(paramId)));

        return "order";
    }

    @PostMapping("/order-u")
    public String updateOrderStatus(Model model, @ModelAttribute("updateSearchOrder") SearchOrder dto) {
        orderService.updateOrderStatus(dto);
        model.addAttribute("searchOrder", orderService.getById(dto.getId()));

        return "order";
    }
}