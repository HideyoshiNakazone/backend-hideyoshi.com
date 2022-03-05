package com.hideyoshi.hideyoshiportfolio.utils.config;

import com.hideyoshi.hideyoshiportfolio.client.Client;
import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import com.hideyoshi.hideyoshiportfolio.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientService clientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        ClientDTO client = this.clientService.save(
                new ClientDTO(
                        "Vitor Hideyoshi",
                        "vitor.h.n.batista@gmail.com",
                        "YoshiUnfriendly",
                        "passwd",
                        "ADMIN$USER"
                )
        );

        log.info(passwordEncoder.encode("passwd"));
        log.info(client.getPassword());

        auth.inMemoryAuthentication()
                .withUser(client.getUsername())
                .password(client.getPassword())
                .roles(
                        String.valueOf(client.getRoles())
                );
    }

}
