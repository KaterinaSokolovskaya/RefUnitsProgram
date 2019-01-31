package com.refunits.database.repository;

import com.refunits.database.configuration.TestConfiguration;
import com.refunits.database.model.Option;
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
public class OptionRepositoryTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Autowired
    private OptionRepository optionRepository;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
        databaseHelper.prepareData();
    }

    @Test
    public void checkFindAll() {
        optionRepository.findAll();
    }

    @Test
    public void checkFindById() {
        optionRepository.findById(1);
    }

    @Test
    public void checkFindByNameAndPrice() {
        optionRepository.findByNameAndPrice("A1", 140);
    }

    @Test
    public void checkFindAllByUnit() {
        optionRepository.findAllByUnit(1);
    }

    @Test
    public void checkFindAllByProduct() {
        optionRepository.findAllByProduct(1);
    }

    @Test
    public void checkSave() {
        Option option = new Option("C2", 450);
        optionRepository.save(option);
        Assert.assertNotNull(option);
    }
}