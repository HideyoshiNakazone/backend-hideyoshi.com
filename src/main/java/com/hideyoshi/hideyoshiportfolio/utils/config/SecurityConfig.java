package com.hideyoshi.hideyoshiportfolio.utils.config;

import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import com.hideyoshi.hideyoshiportfolio.client.ClientService;
import com.hideyoshi.hideyoshiportfolio.utils.token.TokenAuthenticatorFilter;
import com.hideyoshi.hideyoshiportfolio.utils.token.TokenValidateFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Log4j2
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${com.hideyoshi.adminFullname}")
    private String adminFullname;

    @Value("${com.hideyoshi.adminEmail}")
    private String adminEmail;

    @Value("${com.hideyoshi.adminUsername}")
    private String adminUsername;

    @Value("${com.hideyoshi.adminPassword}")
    private String adminPassword;

    @Autowired
    private ClientService clientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new TokenAuthenticatorFilter(authenticationManager()))
                .addFilter(new TokenValidateFilter(authenticationManager()));

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
            this.clientService.save(adminAccount);
        } catch (Exception e) {
            log.warn("Admin Client Account already exists. Continuing application boot.");
        }

        auth.userDetailsService(clientService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
