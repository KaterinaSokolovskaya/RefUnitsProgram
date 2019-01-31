package com.refunits.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "preOrder")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product", schema = "refunits_storage")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class Product extends BaseEntity<Integer> {

    @Column(name = "number")
    private Integer number;

    @Column(name = "sum")
    private Integer sum;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToMany
    @JoinTable(name = "product_option", schema = "refunits_storage",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options;

    @ManyToOne
    @JoinColumn(name = "pre_order_id")
    private PreOrder preOrder;

    public Product(Integer number, Integer sum, Unit unit, Set<Option> options) {
        this.number = number;
        this.sum = sum;
        this.unit = unit;
        this.options = options;
    }
}