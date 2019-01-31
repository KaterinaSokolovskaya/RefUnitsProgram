package com.refunits.database.repository;

import com.refunits.database.enumeration.UserRole;
import com.refunits.database.model.RegisteredUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Integer> {

    Optional<RegisteredUser> findById(Integer id);

    List<RegisteredUser> findAll();

    List<RegisteredUser> findAllByIdBetweenAndCompanyContainingAndUserRoleIn(
            Integer beforeUserId,
            Integer afterUserId,
            String company,
            List<UserRole> userRoles);

    RegisteredUser findByLogin(String login);

    RegisteredUser save(RegisteredUser registeredUser);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update refunits_storage.registered_user set role = :userRole where id = :id", nativeQuery = true)
    void updateRole(@Param("userRole") String role, @Param("id") Integer id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update refunits_storage.registered_user set contact_person = :person," +
            "mail = :mail, telephone = :tel where login = :login", nativeQuery = true)
    void updateUser(@Param("login") String login, @Param("person") String person,
                    @Param("mail") String mail, @Param("tel") String telephone);
}