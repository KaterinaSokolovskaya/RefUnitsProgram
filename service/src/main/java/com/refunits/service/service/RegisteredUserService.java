package com.refunits.service.service;

import com.refunits.database.enumeration.UserRole;
import com.refunits.database.model.RegisteredUser;
import com.refunits.database.repository.RegisteredUserRepository;
import com.refunits.service.dto.*;
import com.refunits.service.dto.SearchUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisteredUserService {

    private final RegisteredUserRepository registeredUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(CreateUser createUser) {
        registeredUserRepository.save(new RegisteredUser(createUser.getLogin(), passwordEncoder.encode(createUser.getPassword()),
                LocalDate.now(), createUser.getCompany(), createUser.getContactPerson(), createUser.getMail(),
                createUser.getTelephone(), UserRole.CONSUMER));
    }

    public void updateUserInfo(UpdateUser updateUser) {
        registeredUserRepository.updateUser(updateUser.getLogin(), updateUser.getContactPerson(),
                updateUser.getMail(), updateUser.getTelephone());
    }

    public void updateUserRole(SearchUser searchUser) {
        registeredUserRepository.updateRole(searchUser.getRole().name(), searchUser.getId());
    }

    public ViewUserInfo getById(Integer id) {

        return registeredUserRepository.findById(id)
                .map(it -> new ViewUserInfo(it.getId(), it.getLogin(), it.getRegistrationDate(),
                        it.getContactPerson(), it.getCompany(), it.getMail(), it.getTelephone(), it.getUserRole(),
                        it.getPreOrders().stream()
                                .map(ord -> new ViewBasicPreOrderInfo(ord.getId(),
                                        ord.getRegisteredUser().getCompany(),
                                        ord.getDate(), ord.getStatus()))
                                .collect(Collectors.toSet())))
                .orElseThrow(RuntimeException::new);
    }

    public ViewUserInfo getByLogin(String login) {
        RegisteredUser user = registeredUserRepository.findByLogin(login);

        return ViewUserInfo.builder()
                .id(user.getId())
                .login(user.getLogin())
                .registrationDate(user.getRegistrationDate())
                .contactPerson(user.getContactPerson())
                .company(user.getCompany())
                .mail(user.getMail())
                .telephone(user.getTelephone())
                .role(user.getUserRole())
                .preOrders(user.getPreOrders().stream()
                        .map(ord -> new ViewBasicPreOrderInfo(ord.getId(),
                                ord.getRegisteredUser().getCompany(),
                                ord.getDate(), ord.getStatus()))
                        .collect(Collectors.toSet()))
                .build();
    }

    public List<ViewBasicUserInfo> getAll() {

        return registeredUserRepository.findAll().stream()
                .map(it -> new ViewBasicUserInfo(it.getId(), it.getLogin(), it.getCompany(), it.getUserRole()))
                .collect(Collectors.toList());
    }

    public Set<ViewBasicUserInfo> getFiltered(SearchUser searchUser) {
        Integer beforeUserId;
        Integer afterUserId;
        String company;
        List<UserRole> userRoles = new ArrayList<>();

        if (searchUser.getId() != null) {
            beforeUserId = searchUser.getId();
            afterUserId = searchUser.getId();
        } else {
            beforeUserId = Integer.MIN_VALUE;
            afterUserId = Integer.MAX_VALUE;
        }

        if (searchUser.getCompany() != null) {
            company = searchUser.getCompany();
        } else {
            company = "";
        }

        if (searchUser.getRole() != null) {
            userRoles.add(searchUser.getRole());
        } else {
            userRoles.addAll(Arrays.asList(UserRole.values()));
        }

        return registeredUserRepository.findAllByIdBetweenAndCompanyContainingAndUserRoleIn(
                beforeUserId, afterUserId, company, userRoles)
                .stream()
                .map(it -> new ViewBasicUserInfo(it.getId(), it.getLogin(), it.getCompany(), it.getUserRole()))
                .collect(Collectors.toSet());
    }
}