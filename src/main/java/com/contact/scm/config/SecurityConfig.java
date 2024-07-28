/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.contact.scm.services.impl.SecurityCustomUserDetailsService;


@Configuration
public class SecurityConfig {
    private final SecurityCustomUserDetailsService userDetailsService;
    private final OAuthenticationSuccessHandler handler;
    private final AuthFailureHandler authFailureHandler;

    public SecurityConfig(SecurityCustomUserDetailsService userDetailsService, OAuthenticationSuccessHandler handler, AuthFailureHandler authFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.handler = handler;
        this.authFailureHandler = authFailureHandler;
    }


    // Configuration of authentication provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // configuration 
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated()
            .anyRequest().permitAll();
        });

        // form default login
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .successForwardUrl("/user/dashboard")
            .usernameParameter("email")
            .passwordParameter("password");

            formLogin.failureHandler(authFailureHandler);
        });

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login")
            .successHandler(handler);
        });
        
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout")
            .logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
