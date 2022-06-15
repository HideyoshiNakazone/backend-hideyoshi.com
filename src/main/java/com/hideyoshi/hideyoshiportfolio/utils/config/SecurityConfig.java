package com.hideyoshi.hideyoshiportfolio.utils.config;

import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import com.hideyoshi.hideyoshiportfolio.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${com.hideyoshi.adminFullname}")
    private String adminFullname;

    @Value("${com.hideyoshi.adminEmail}")
    private String adminEmail;

    @Value("${com.hideyoshi.adminUsername}")
    private String adminUsername;

    @Value("${com.hideyoshi.adminPassword}")
    private String adminPassword;

    private final ClientService clientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/client/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        ClientDTO adminAccount = new ClientDTO(
                this.adminFullname,
                this.adminEmail,
                this.adminUsername,
                this.adminPassword,
                "ROLE_ADMIN$ROLE_USER"
        );

        try{
            this.clientService.findByUsername(adminAccount.getUsername());
        } catch (Exception e) {
            this.clientService.save(adminAccount);
        }

        auth.userDetailsService(clientService)
                .passwordEncoder(passwordEncoder);
    }

}
