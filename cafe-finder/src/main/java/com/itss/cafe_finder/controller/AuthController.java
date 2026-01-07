package com.itss.cafe_finder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.repository.UserRepository;
import com.itss.cafe_finder.service.impl.UserService;

import dto.request.UserUpdateRequest;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

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
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/update")
    public String showUpdateForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userUpdateRequest", new UserUpdateRequest());
        model.addAttribute("email", auth.getName());
        return "update";
    }

    @PutMapping("/update")
    public String updateUser(@ModelAttribute("userUpdateRequest") UserUpdateRequest userUpdateRequest) {
        this.userService.updateUser(userUpdateRequest);
        return "redirect:/login";
    }
    
    // Thêm endpoint để xử lý sau khi đăng nhập thành công
    @GetMapping("/login-success")
    public String loginSuccess(RedirectAttributes redirectAttributes) {
        // Thêm flash attribute để hiển thị location popup
        redirectAttributes.addFlashAttribute("showLocationPopup", true);
        return "redirect:/";
    }
}