package com.refunits.database.repository;

import com.refunits.database.configuration.TestConfiguration;
import com.refunits.database.enumeration.UserRole;
import com.refunits.database.model.RegisteredUser;
import com.refunits.database.util.DatabaseHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
public class RegisteredUserRepositoryTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
        databaseHelper.prepareData();
    }

    @Test
    public void checkFindAll() {
        registeredUserRepository.findAll();
    }

    @Test
    public void checkFindById() {
        registeredUserRepository.findById(1);
    }

    @Test
    public void checkSave() {
        registeredUserRepository.save(new RegisteredUser("test", "pass", LocalDate.now(), "Company", UserRole.MANAGER));
    }
}