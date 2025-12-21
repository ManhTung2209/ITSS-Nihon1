package com.itss.cafe_finder.controller;

import com.itss.cafe_finder.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.service.impl.UserService;

import dto.request.UserUpdateRequest;

@Controller
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);            
            return "redirect:/login?success";
        } catch (Exception e) {
            // Nếu có lỗi (ví dụ: Email đã tồn tại), hiển thị lại form đăng ký kèm thông báo lỗi
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/update")
    public String showUpdateForm(Model model) {
        model.addAttribute("userUpdateRequest", new UserUpdateRequest());
        return "update"; // Trả về file update.html trong templates
    }

    @PutMapping("/update")
    public String updateUser(@ModelAttribute("userUpdateRequest")UserUpdateRequest userUpdateRequest){
        this.userService.updateUser(userUpdateRequest);
        return "redirect:/login";
    }
}