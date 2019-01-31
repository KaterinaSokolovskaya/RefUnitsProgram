package com.refunits.web.configuration;

import com.refunits.database.enumeration.UserRole;
import com.refunits.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/create-unit", "/create-option", "/user-info-u")
                .hasAnyAuthority(UserRole.ADMIN.name())
                .antMatchers("/all-orders", "/all-users", "/user-info", "/order-u")
                .hasAnyAuthority(UserRole.ADMIN.name(), UserRole.MANAGER.name())
                .antMatchers("/account", "/current-order", "/create-order", "/unit-p", "/order", "/user-up")
                .authenticated()
                .antMatchers("/catalog", "/unit", "/login", "/registration")
                .permitAll();

        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/account")
                .and()
                .logout();

        http
                .userDetailsService(userDetailsService);
    }
}