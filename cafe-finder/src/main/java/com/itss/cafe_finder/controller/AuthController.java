package com.itss.cafe_finder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.service.impl.UserService;

import dto.request.UserUpdateRequest;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về file login.html trong templates
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Tạo một đối tượng User rỗng để bind dữ liệu từ form
        model.addAttribute("user", new User());
        return "register"; // Trả về file register.html trong templates
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
            // Nếu có lỗi (ví dụ: Email đã tồn tại), hiển thị lại form đăng ký kèm thông báo lỗi
            model.addAttribute("error", "Đăng ký thất bại. Có thể email đã được sử dụng.");
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