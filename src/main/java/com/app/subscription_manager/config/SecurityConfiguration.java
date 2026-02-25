package com.app.subscription_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private JwtAuthFilter jwtAuthFilter;

    public SecurityConfiguration(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/test").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/subscriptions").authenticated()
                        //.requestMatchers(HttpMethod.POST,"/api/subscriptions/**/cancel").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/subscriptions").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/subscriptions/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/subscriptions/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/subscriptions/user/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/users/change-password/*").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/email/send-email").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
