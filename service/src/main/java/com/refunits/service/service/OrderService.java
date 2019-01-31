package com.refunits.service.service;

import com.refunits.database.enumeration.OrderStatus;
import com.refunits.database.model.PreOrder;
import com.refunits.database.repository.PreOrderRepository;
import com.refunits.database.repository.ProductRepository;
import com.refunits.database.repository.RegisteredUserRepository;
import com.refunits.database.repository.UnitRepository;
import com.refunits.service.dto.Product;
import com.refunits.service.dto.SearchOrder;
import com.refunits.service.dto.ViewBasicPreOrderInfo;
import com.refunits.service.dto.ViewBasicUnitInfo;
import com.refunits.service.dto.ViewOptionInfo;
import com.refunits.service.dto.ViewPreOrderInfo;
import com.refunits.service.dto.ViewProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@CacheConfig(cacheNames = "orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final PreOrderRepository preOrderRepository;
    private final ProductService productService;
    private final RegisteredUserRepository userRepository;
    private final UnitRepository unitRepository;
    private final ProductRepository productRepository;

    public void saveOrder(String login, String comment) {
        PreOrder preOrder = preOrderRepository.save(PreOrder.builder()
                .registeredUser(userRepository.findByLogin(login))
                .date(LocalDate.now())
                .status(OrderStatus.SUBMITTED)
                .comment(comment)
                .build());

        List<Product> products = productService.getProductTemps(login);
        products.forEach(product -> product.setId(productRepository.save(com.refunits.database.model.Product.builder()
                .preOrder(preOrder)
                .unit(unitRepository.findById(product.getUnitId()).orElseThrow(RuntimeException::new))
                .number(product.getNumber())
                .sum(product.getSum())
                .build()).getId()));

        products.stream().filter(product -> product.getSelectedOptions() != null)
                .forEach(product -> product.getSelectedOptions()
                        .forEach(opt -> productRepository.saveOption(product.getId(), opt)));

        productService.removeProduct(login);
    }

    public void updateOrderStatus(SearchOrder searchOrder) {
        preOrderRepository.updateStatus(searchOrder.getStatus().name(), searchOrder.getId());
    }

    @Cacheable
    public ViewPreOrderInfo getById(Integer id) {
        ViewPreOrderInfo preOrder = preOrderRepository.findById(id)
                .map(it -> new ViewPreOrderInfo(it.getId(), it.getRegisteredUser().getCompany(),
                        it.getDate(), it.getStatus(), it.getComment(), it.getProducts().stream()
                        .map(prod -> new ViewProduct(prod.getId(), ViewBasicUnitInfo.builder()
                                .id(prod.getUnit().getId())
                                .name(prod.getUnit().getName())
                                .price(prod.getUnit().getPrice())
                                .build(),
                                prod.getOptions().stream()
                                        .map(opt -> new ViewOptionInfo(opt.getId(), opt.getName(),
                                                opt.getPrice(), opt.getDescription()))
                                        .collect(Collectors.toList()),
                                prod.getSum(), prod.getNumber(), (prod.getSum() * prod.getNumber())))
                        .collect(Collectors.toList()), 0))
                .orElseThrow(RuntimeException::new);
        preOrder.setTotal(productService.sum(preOrder.getProducts().stream()
                .map(ViewProduct::getSubtotal)
                .collect(Collectors.toList())));

        return preOrder;
    }

    public List<ViewBasicPreOrderInfo> getAll() {

        return preOrderRepository.findAll().stream()
                .map(it -> new ViewBasicPreOrderInfo(it.getId(), it.getRegisteredUser().getCompany(), it.getDate(), it.getStatus()))
                .collect(Collectors.toList());
    }

    public Set<ViewBasicPreOrderInfo> getFiltered(SearchOrder searchOrder) {
        Integer beforeOrderId;
        Integer afterOrderId;
        String company;
        LocalDate beforeDate;
        LocalDate afterDate;
        List<OrderStatus> orderStatuses = new ArrayList<>();

        if (searchOrder.getId() != null) {
            beforeOrderId = searchOrder.getId();
            afterOrderId = searchOrder.getId();
        } else {
            beforeOrderId = Integer.MIN_VALUE;
            afterOrderId = Integer.MAX_VALUE;
        }

        if (searchOrder.getCompany() != null) {
            company = searchOrder.getCompany();
        } else {
            company = "";
        }

        if (!searchOrder.getDate().isEmpty()) {
            beforeDate = LocalDate.parse(searchOrder.getDate()).minusDays(1L);
            afterDate = LocalDate.parse(searchOrder.getDate()).plusDays(1L);
        } else {
            beforeDate = LocalDate.of(1970, 1, 1);
            afterDate = LocalDate.of(3000, 1, 1);
        }

        if (searchOrder.getStatus() != null) {
            orderStatuses.add(searchOrder.getStatus());
        } else {
            orderStatuses.addAll(Arrays.asList(OrderStatus.values()));
        }

        return preOrderRepository.findAllByIdBetweenAndDateAfterAndDateBeforeAndStatusInAndRegisteredUserCompanyContaining(beforeOrderId, afterOrderId, beforeDate, afterDate, orderStatuses, company)
                .stream()
                .map(it -> new ViewBasicPreOrderInfo(it.getId(), it.getRegisteredUser().getCompany(), it.getDate(), it.getStatus()))
                .collect(Collectors.toSet());
    }
}