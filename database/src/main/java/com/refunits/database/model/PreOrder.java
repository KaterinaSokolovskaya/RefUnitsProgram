package com.refunits.database.model;

import com.refunits.database.enumeration.OrderStatus;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "registeredUser")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pre_order", schema = "refunits_storage")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class PreOrder extends BaseEntity<Integer> {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "preOrder")
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "registered_user_id", nullable = false)
    private RegisteredUser registeredUser;

    public PreOrder(LocalDate date, OrderStatus status, Set<Product> products, RegisteredUser registeredUser) {
        this.date = date;
        this.status = status;
        this.products = products;
        this.registeredUser = registeredUser;
    }

    public PreOrder(LocalDate date, RegisteredUser user) {
        this.date = date;
        this.registeredUser = user;
    }
}