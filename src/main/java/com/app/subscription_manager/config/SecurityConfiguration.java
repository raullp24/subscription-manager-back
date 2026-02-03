package com.app.subscription_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.app.subscription_manager.service.CustomUsersDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public SecurityConfiguration(){

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(csrf-> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomUsersDetailsService userDetailsService() {
    return new CustomUsersDetailsService();
  }

}
