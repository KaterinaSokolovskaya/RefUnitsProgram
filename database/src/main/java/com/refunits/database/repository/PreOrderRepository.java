package com.refunits.database.repository;

import com.refunits.database.enumeration.OrderStatus;
import com.refunits.database.model.PreOrder;
import com.refunits.database.model.RegisteredUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PreOrderRepository extends CrudRepository<PreOrder, Integer> {

    Optional<PreOrder> findById(Integer id);

    List<PreOrder> findAll();

    List<PreOrder> findAllByRegisteredUser(RegisteredUser registeredUser);

    List<PreOrder> findAllByIdBetweenAndDateAfterAndDateBeforeAndStatusInAndRegisteredUserCompanyContaining(
            Integer beforeOrderId,
            Integer afterOrderId,
            LocalDate beforeDate,
            LocalDate afterDate,
            List<OrderStatus> statuses,
            String company);

    PreOrder save(PreOrder preOrder);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update refunits_storage.pre_order set status = :status where id = :id", nativeQuery = true)
    void updateStatus(@Param("status") String status, @Param("id") Integer id);
}