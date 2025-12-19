package com.itss.cafe_finder.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                          Object handler, ModelAndView modelAndView) throws Exception {
        
        // Nếu không có ModelAndView (VD: redirect, API response), bỏ qua
        if (modelAndView == null) {
            return;
        }
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            String userName = (String) session.getAttribute("userName");
            String userEmail = (String) session.getAttribute("userEmail");
            
            // Add vào model để template sử dụng
            modelAndView.addObject("userId", userId);
            modelAndView.addObject("userName", userName);
            modelAndView.addObject("userEmail", userEmail);
            modelAndView.addObject("isLoggedIn", userId != null);
            
            logger.debug("Session interceptor - userId: {}, isLoggedIn: {}", userId, userId != null);
        } else {
            modelAndView.addObject("isLoggedIn", false);
        }
    }
}
