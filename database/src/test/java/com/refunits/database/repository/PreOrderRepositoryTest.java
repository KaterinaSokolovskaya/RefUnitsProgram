package com.refunits.database.repository;

import com.refunits.database.configuration.TestConfiguration;
import com.refunits.database.enumeration.UserRole;
import com.refunits.database.model.PreOrder;
import com.refunits.database.model.RegisteredUser;
import com.refunits.database.util.DatabaseHelper;
import org.junit.Assert;
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
public class PreOrderRepositoryTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Autowired
    private PreOrderRepository preOrderRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
        databaseHelper.prepareData();
    }

    @Test
    public void checkFindAll() {
        preOrderRepository.findAll();
    }

    @Test
    public void checkFindById() {
        preOrderRepository.findById(1);
    }

    @Test
    public void checkFindAllByRegisteredUser() {
        RegisteredUser user = new RegisteredUser("Adm", "111", LocalDate.now(), "Comp", UserRole.ADMIN);
        registeredUserRepository.save(user);

        preOrderRepository.save(new PreOrder(LocalDate.now(), user));
        preOrderRepository.save(new PreOrder(LocalDate.of(2000, 12, 20), user));

        preOrderRepository.findAllByRegisteredUser(user);
    }

    @Test
    public void checkSave() {
        RegisteredUser admin = new RegisteredUser("Admin1", "0000", LocalDate.now(), "Ref", UserRole.ADMIN);
        registeredUserRepository.save(admin);

        PreOrder preOrder = new PreOrder(LocalDate.now(), admin);
        preOrderRepository.save(preOrder);
        Assert.assertNotNull(preOrder);
    }
}