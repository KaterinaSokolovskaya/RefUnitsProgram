package com.refunits.service.service;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.database.repository.UnitRepository;
import com.refunits.service.dto.SearchUnit;
import com.refunits.service.dto.ViewBasicUnitInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CatalogService {

    private final UnitRepository unitRepository;

    public List<ViewBasicUnitInfo> getAll() {

        return unitRepository.findAll().stream()
                .map(it -> new ViewBasicUnitInfo(it.getId(), it.getName(), it.getPrice()))
                .collect(Collectors.toList());
    }

    public Set<ViewBasicUnitInfo> getFiltered(SearchUnit searchUnit) {
        List<BoilingPoint> boilingPoints = new ArrayList<>();
        List<UnitRange> ranges = new ArrayList<>();
        Double minRefCapacity;
        Double maxRefCapacity;

        if (searchUnit.getBoilingPoint() != null) {
            boilingPoints.add(searchUnit.getBoilingPoint());
        } else {
            boilingPoints.addAll(Arrays.asList(BoilingPoint.values()));
        }

        if (searchUnit.getRange() != null) {
            ranges.add(searchUnit.getRange());
        } else {
            ranges.addAll(Arrays.asList(UnitRange.values()));
        }

        if (searchUnit.getMinRefCapacity() != null) {
            minRefCapacity = searchUnit.getMinRefCapacity();
        } else {
            minRefCapacity = Double.MIN_VALUE;
        }

        if (searchUnit.getMaxRefCapacity() != null) {
            maxRefCapacity = searchUnit.getMaxRefCapacity();
        } else {
            maxRefCapacity = Double.MAX_VALUE;
        }

        return unitRepository.findAllByBoilingPointInAndRangeInAndRefCapacityBetween(boilingPoints, ranges, minRefCapacity, maxRefCapacity)
                .stream()
                .map(it -> new ViewBasicUnitInfo(it.getId(), it.getName(), it.getPrice()))
                .collect(Collectors.toSet());
    }
}