package com.itss.cafe_finder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**", "/register", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cafes/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/location/update").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/?justLoggedIn=true", true)  // ← THAY ĐỔI Ở ĐÂY
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .rememberMe((rememberMe) -> rememberMe
                .key("superSecretKey")
                .tokenValiditySeconds(86400) // 1 day
                .rememberMeParameter("remember-me")
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers("/user/location/update")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}