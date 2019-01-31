package com.refunits.database.model;

import com.refunits.database.enumeration.UserRole;
import lombok.AllArgsConstructor;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "preOrders")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registered_user", schema = "refunits_storage")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class RegisteredUser extends BaseEntity<Integer> {

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "mail")
    private String mail;

    @Column(name = "telephone")
    private String telephone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "registeredUser")
    private Set<PreOrder> preOrders;

    public RegisteredUser(String login, String password, LocalDate registrationDate, String company, UserRole role) {
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        this.company = company;
        this.userRole = role;
    }

    public RegisteredUser(String login, String password, LocalDate registrationDate, String company,
                          String contactPerson, String mail, String telephone, UserRole role) {
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        this.company = company;
        this.contactPerson = contactPerson;
        this.mail = mail;
        this.telephone = telephone;
        this.userRole = role;
    }
}