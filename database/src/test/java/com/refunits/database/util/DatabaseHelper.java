package com.refunits.database.util;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.OrderStatus;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.database.enumeration.UserRole;
import com.refunits.database.model.Option;
import com.refunits.database.model.PreOrder;
import com.refunits.database.model.Product;
import com.refunits.database.model.RegisteredUser;
import com.refunits.database.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseHelper {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DatabaseHelper(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void cleanDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Product").executeUpdate();
        entityManager.createQuery("delete from Option").executeUpdate();
        entityManager.createQuery("delete from Unit").executeUpdate();
        entityManager.createQuery("delete from PreOrder").executeUpdate();
        entityManager.createQuery("delete from RegisteredUser").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void prepareData() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Option option1 = new Option("A1", 140);
        Option option2 = new Option("A2", 150);
        Option option3 = new Option("B4", 70);

        entityManager.persist(option1);
        entityManager.persist(option2);
        entityManager.persist(option3);

        Set<Option> options1 = new HashSet<>();
        options1.add(option1);
        options1.add(option2);
        options1.add(option3);

        Set<Option> options2 = new HashSet<>();
        options1.add(option1);
        options1.add(option3);

        Unit unit1 = new Unit("AM.N10-0115-2x2EES3-K45", 11.5, BoilingPoint.N10, UnitRange.AM, options1, 2000);
        Unit unit2 = new Unit("AM.N10-0137-2x2DES3-K45", 13.7, BoilingPoint.N10, UnitRange.AM, options2, 2100);
        Unit unit3 = new Unit("AK.N10-0161-1x4CES9-K45", 16.1, BoilingPoint.N10, UnitRange.AK, 2200);
        Unit unit4 = new Unit("AKM.N35-0015-1xZF09K-K45", 1.5, BoilingPoint.N35, UnitRange.AKM, 2300);
        Unit unit5 = new Unit("AKM.N35-0139-1xZF48K-EVI-K45", 13.9, BoilingPoint.N35, UnitRange.AKM, 2400);

        entityManager.persist(unit1);
        entityManager.persist(unit2);
        entityManager.persist(unit3);
        entityManager.persist(unit4);
        entityManager.persist(unit5);

        Product product1 = new Product(1, 1500, unit1, options1);
        Product product2 = new Product(1, 1500, unit3, new HashSet<>());

        entityManager.persist(product1);
        entityManager.persist(product2);

        RegisteredUser manager = new RegisteredUser("Manager", "1111", LocalDate.now(), "Ref", UserRole.MANAGER);
        RegisteredUser admin = new RegisteredUser("Admin", "0000", LocalDate.now(), "Ref", UserRole.ADMIN);
        RegisteredUser consumer = new RegisteredUser("Consumer", "1234", LocalDate.now(), "Hol", UserRole.CONSUMER);

        entityManager.persist(manager);
        entityManager.persist(admin);
        entityManager.persist(consumer);

        Set<Product> products1 = new HashSet<>();
        products1.add(product1);

        Set<Product> products2 = new HashSet<>();
        products2.add(product1);
        products2.add(product2);

        PreOrder preOrder1 = new PreOrder(LocalDate.now(), manager);
        PreOrder preOrder2 = new PreOrder(LocalDate.now(), OrderStatus.ACCEPTED, products1, admin);
        PreOrder preOrder3 = new PreOrder(LocalDate.now(), OrderStatus.ACCEPTED, products2, consumer);

        entityManager.persist(preOrder1);
        entityManager.persist(preOrder2);
        entityManager.persist(preOrder3);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}