package com.refunits.database.repository;

import com.refunits.database.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findById(Integer id);

    List<Product> findAll();

    List<Product> findAllByPreOrderId(Integer preOrderId);

    Product save(Product product);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "insert into refunits_storage.product_option values (:productId, :optionId)", nativeQuery = true)
    void saveOption(@Param("productId") Integer productId,
                    @Param("optionId") Integer optionId);
}