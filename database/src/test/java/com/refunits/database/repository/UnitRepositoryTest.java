package com.refunits.database.repository;

import com.refunits.database.configuration.TestConfiguration;
import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.database.model.Unit;
import com.refunits.database.util.DatabaseHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
public class UnitRepositoryTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Autowired
    private UnitRepository unitRepository;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
        databaseHelper.prepareData();
    }

    @Test
    public void checkFindAll() {
        unitRepository.findAll();
    }

    @Test
    public void checkFindById() {
        unitRepository.findById(1);
    }

    @Test
    public void checkFindAllByBoilingPointInAndRangeInAndRefCapacityBetween() {
        List<BoilingPoint> boilingPoints = new ArrayList<>();
        boilingPoints.add(BoilingPoint.N10);
        boilingPoints.add(BoilingPoint.N30);

        List<UnitRange> ranges = new ArrayList<>();
        ranges.add(UnitRange.AK);
        ranges.add(UnitRange.AM);

        unitRepository.findAllByBoilingPointInAndRangeInAndRefCapacityBetween(boilingPoints, ranges, 5.0, 20.0);
    }

    @Test
    public void checkSave() {
        Unit unit = new Unit("AM.N10-0111-test", 11.1, BoilingPoint.N10, UnitRange.AM, 2500);
        unitRepository.save(unit);
        Assert.assertNotNull(unit);
    }
}