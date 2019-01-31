package com.refunits.service.converter;

import com.refunits.database.model.RegisteredUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsConverter implements Converter<RegisteredUser, UserDetails> {

    @Override
    public UserDetails convert(RegisteredUser user) {

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getUserRole().name())
                .build();
    }
}