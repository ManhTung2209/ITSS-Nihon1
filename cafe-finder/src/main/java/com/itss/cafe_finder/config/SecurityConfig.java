package com.itss.cafe_finder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                // Cho phép truy cập công khai các trang static, api tìm kiếm, và trang chủ
                .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**", "/api/cafes/**", "/register", "/login").permitAll()
                // Các request khác yêu cầu đăng nhập
                .anyRequest().permitAll()
            )
            .formLogin((form) -> form
                .loginPage("/login") // Đường dẫn đến trang login tùy chỉnh
                .usernameParameter("email") // Dùng email thay vì username
                .defaultSuccessUrl("/") // Đăng nhập thành công thì về trang chủ
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu an toàn
    }
}