package com.refunits.database.model;

import com.refunits.database.enumeration.BoilingPoint;
import com.refunits.database.enumeration.UnitRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@ToString(exclude = {"products"})
@EqualsAndHashCode(callSuper = true, exclude = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unit", schema = "refunits_storage")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class Unit extends BaseEntity<Integer> {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "range")
    private UnitRange range;

    @Enumerated(EnumType.STRING)
    @Column(name = "boiling_point")
    private BoilingPoint boilingPoint;

    @Column(name = "ref_capacity", nullable = false)
    private Double refCapacity;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "length")
    private Integer length;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToMany
    @JoinTable(name = "unit_option", schema = "refunits_storage",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options;

    @OneToMany(mappedBy = "unit")
    private Set<Product> products;

    public Unit(String name, Double refCapacity, BoilingPoint boilingPoint, UnitRange range, Integer price) {
        this.name = name;
        this.refCapacity = refCapacity;
        this.boilingPoint = boilingPoint;
        this.range = range;
        this.price = price;
    }

    public Unit(String name, Double refCapacity, BoilingPoint boilingPoint, UnitRange range, Set<Option> options, Integer price) {
        this.name = name;
        this.refCapacity = refCapacity;
        this.boilingPoint = boilingPoint;
        this.range = range;
        this.options = options;
        this.price = price;
    }

    public Unit(String name, UnitRange range, BoilingPoint boilingPoint, Double refCapacity,
                Integer weight, Integer length, Integer width, Integer height,
                String description, Integer price) {
        this.name = name;
        this.range = range;
        this.boilingPoint = boilingPoint;
        this.refCapacity = refCapacity;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.description = description;
        this.price = price;
    }
}