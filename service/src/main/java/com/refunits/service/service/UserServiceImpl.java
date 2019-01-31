package com.refunits.service.service;

import com.refunits.database.repository.RegisteredUserRepository;
import com.refunits.service.converter.UserDetailsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final RegisteredUserRepository userRepository;
    private final UserDetailsConverter detailsConverter;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails userDetails = detailsConverter.convert(userRepository.findByLogin(login));

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return userDetails;
    }
}