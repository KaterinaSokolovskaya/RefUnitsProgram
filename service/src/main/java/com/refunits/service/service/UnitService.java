package com.refunits.service.service;

import com.refunits.database.model.Unit;
import com.refunits.database.repository.OptionRepository;
import com.refunits.database.repository.UnitRepository;
import com.refunits.service.dto.CreateUnit;
import com.refunits.service.dto.ViewOptionInfo;
import com.refunits.service.dto.ViewUnitInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@CacheConfig(cacheNames = "units")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UnitService {

    private final UnitRepository unitRepository;
    private final OptionRepository optionRepository;

    public Integer save(CreateUnit createUnit) {
        Integer newUnitId = unitRepository.save(new Unit(createUnit.getName(), createUnit.getRange(),
                createUnit.getBoilingPoint(), Double.valueOf(createUnit.getRefCapacity()),
                createUnit.getWeight(), createUnit.getLength(),
                createUnit.getWidth(), createUnit.getHeight(),
                createUnit.getDescription(), createUnit.getPrice())).getId();

        if (createUnit.getOptionIds() != null) {
            createUnit.getOptionIds().forEach(opt -> unitRepository.saveOption(newUnitId, opt));
        }

        return newUnitId;
    }

    @Cacheable
    public ViewUnitInfo getById(Integer id) {

        return unitRepository.findById(id)
                .map(it -> new ViewUnitInfo(it.getId(), it.getName(), it.getRange(),
                        it.getBoilingPoint(), it.getRefCapacity(), it.getWeight(), it.getLength(),
                        it.getWidth(), it.getHeight(), it.getDescription(), it.getPrice(),
                        optionRepository.findAllByUnit(it.getId()).stream()
                                .map(opt -> new ViewOptionInfo(opt.getId(), opt.getName(),
                                        opt.getPrice(), opt.getDescription()))
                                .collect(Collectors.toSet())))
                .orElseThrow(RuntimeException::new);
    }
}