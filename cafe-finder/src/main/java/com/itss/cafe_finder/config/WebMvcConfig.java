package com.itss.cafe_finder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Autowired
    private SessionInterceptor sessionInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register SessionInterceptor để tự động add session info vào model
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**") // Apply cho tất cả requests
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/api/**"); // Exclude static resources
    }
}
