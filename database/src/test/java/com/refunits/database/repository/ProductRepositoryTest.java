package com.refunits.database.repository;

import com.refunits.database.configuration.TestConfiguration;
import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.database.model.*;
import com.refunits.database.util.DatabaseHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
        databaseHelper.prepareData();
    }

    @Test
    public void checkFindAll() {
        productRepository.findAll();
    }

    @Test
    public void checkFindById() {
        productRepository.findById(1);
    }

    @Test
    public void checkFindAllByPreOrderId() {
        Integer preOrderId = 1;
        productRepository.findAllByPreOrderId(preOrderId);
    }

    @Test
    public void checkSave() {
        Unit unit = new Unit("test", 1.0, BoilingPoint.N10, UnitRange.AM, 1000);

        if (unitRepository.findById(1).isPresent()) {
            unit = unitRepository.findById(1).get();
        }

        Product product = new Product(1, 1000, unit, unit.getOptions());
        productRepository.save(product);
        Assert.assertNotNull(product);
    }
}