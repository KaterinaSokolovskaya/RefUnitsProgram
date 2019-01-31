package com.refunits.service.service;

import com.refunits.service.dto.*;
import com.refunits.service.dto.ProductTemp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final UnitService unitService;
    private final OptionService optionService;

    private List<ProductTemp> productTemps = new ArrayList<>();

    public void addProduct(String login, Product product) {
        product.setNumber(1);
        product.setSum((product.getSelectedOptions() != null
                ? sum(product.getSelectedOptions().stream()
                .map(opt -> optionService.getById(opt).orElseThrow(RuntimeException::new).getPrice())
                .collect(Collectors.toList()))
                : 0)
                + unitService.getById(product.getUnitId()).getPrice());

        productTemps.add(ProductTemp.builder()
                .login(login)
                .product(product)
                .build());
    }

    public void addAllProducts(String login, List<ViewProduct> updatedProducts) {
        productTemps.addAll(updatedProducts.stream()
                .map(upProd -> ProductTemp.builder()
                        .login(login)
                        .product(Product.builder()
                                .unitId(upProd.getUnit().getId())
                                .selectedOptions(upProd.getOptions().stream()
                                        .map(ViewOptionInfo::getId)
                                        .collect(Collectors.toList()))
                                .number(upProd.getNumber())
                                .sum(upProd.getSum())
                                .build())
                        .build())
                .collect(Collectors.toList()));
    }

    public List<Product> getProductTemps(String login) {

        return productTemps.stream()
                .filter(it -> it.getLogin().equals(login))
                .map(ProductTemp::getProduct)
                .collect(Collectors.toList());
    }

    public void removeProduct(String login) {
        productTemps.removeAll(productTemps.stream()
                .filter(productTemp -> productTemp.getLogin().equals(login))
                .collect(Collectors.toList()));
    }

    public List<ViewProduct> viewProducts(List<Product> products) {

        return products.stream()
                .map(prod -> ViewProduct.builder()
                        .id(prod.getId())
                        .unit(ViewBasicUnitInfo.builder()
                                .id(prod.getUnitId())
                                .name(unitService.getById(prod.getUnitId()).getName())
                                .price(unitService.getById(prod.getUnitId()).getPrice())
                                .build())
                        .options(prod.getSelectedOptions() != null
                                ? prod.getSelectedOptions().stream()
                                .map(opt -> optionService.getById(opt).orElseThrow(RuntimeException::new))
                                .collect(Collectors.toList())
                                : new ArrayList<>())
                        .sum(prod.getSum())
                        .number(prod.getNumber())
                        .build())
                .collect(Collectors.toList());
    }

    public Integer sum(List<Integer> values) {
        Integer sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }

        return sum;
    }
}