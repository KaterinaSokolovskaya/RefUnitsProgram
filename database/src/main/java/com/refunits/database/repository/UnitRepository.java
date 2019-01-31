package com.refunits.database.repository;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import com.refunits.database.model.Unit;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends CrudRepository<Unit, Integer> {

    Optional<Unit> findById(Integer id);

    List<Unit> findAll();

    List<Unit> findAllByBoilingPointInAndRangeInAndRefCapacityBetween(
            List<BoilingPoint> boilingPoints,
            List<UnitRange> ranges,
            Double minRefCapacity,
            Double maxRefCapacity);

    Unit save(Unit unit);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "insert into refunits_storage.unit_option values (:unitId, :optionId)", nativeQuery = true)
    void saveOption(@Param("unitId") Integer unitId,
                    @Param("optionId") Integer optionId);
}