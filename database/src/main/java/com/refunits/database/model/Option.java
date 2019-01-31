package com.refunits.database.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"units", "products"})
@ToString(exclude = {"units", "products"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "option", schema = "refunits_storage")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class Option extends BaseEntity<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToMany
    @JoinTable(name = "unit_option", schema = "refunits_storage",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private Set<Unit> units;

    @ManyToMany
    @JoinTable(name = "product_option", schema = "refunits_storage",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    public Option(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Option(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Option(Integer id) {
        this.setId(id);
    }
}