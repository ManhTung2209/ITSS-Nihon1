package com.itss.cafe_finder.controller;

import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.service.UserService;
import com.itss.cafe_finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Xử lý logic khi người dùng đăng nhập
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model,
                           HttpSession session) {
        try {
            logger.info("Login attempt with email: {}", email);
            
            // Tìm user theo email
            User user = userRepository.findByEmail(email).orElse(null);
            
            if (user == null) {
                logger.warn("User not found with email: {}", email);
                model.addAttribute("error", "Email không tồn tại");
                return "login";
            }
            
            logger.info("User found: {}", user.getId());
            
            // Kiểm tra mật khẩu
            boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
            logger.info("Password match result: {}", passwordMatch);
            
            if (!passwordMatch) {
                logger.warn("Password mismatch for email: {}", email);
                model.addAttribute("error", "Mật khẩu không chính xác");
                return "login";
            }
            
            // Đăng nhập thành công -> Lưu userId vào session
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userName", user.getName());
            
            logger.info("User logged in successfully: {} (ID: {})", email, user.getId());
            logger.info("Session attributes set - userId: {}, userEmail: {}, userName: {}", 
                session.getAttribute("userId"), 
                session.getAttribute("userEmail"), 
                session.getAttribute("userName"));
            
            // Chuyển hướng về trang chủ
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Login error", e);
            model.addAttribute("error", "Đăng nhập thất bại. Vui lòng thử lại.");
            return "login";
        }
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý logic khi người dùng bấm nút Đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Gọi service để lưu user (đã mã hóa mật khẩu bên Service)
            userService.registerUser(user);
            
            // Đăng ký thành công -> Chuyển hướng về trang login với thông báo thành công
            return "redirect:/login?success";
        } catch (Exception e) {
            logger.error("Registration error", e);
            model.addAttribute("error", "Đăng ký thất bại. Có thể email đã được sử dụng.");
            return "register";
        }
    }
    
    // Xử lý logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}