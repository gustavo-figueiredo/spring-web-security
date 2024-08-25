package com.example.spring_web_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

        @Autowired
        private SecurityDatabaseService securityService;

        @Autowired
        public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(
                                                authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                                                .requestMatchers("/").permitAll()
                                                                .requestMatchers("/login").permitAll()
                                                                .requestMatchers("/admins").hasAnyRole("ADMIN")
                                                                .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
                                                                .anyRequest().authenticated())
                                .httpBasic(Customizer.withDefaults());
                return http.build();
        }

}