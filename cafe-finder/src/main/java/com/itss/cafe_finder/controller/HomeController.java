package com.itss.cafe_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(value = "justLoggedIn", required = false) String justLoggedIn,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        // Nếu URL vẫn là kiểu cũ (có param justLoggedIn), thực hiện redirect để xóa param và kích hoạt popup
        if ("true".equals(justLoggedIn)) {
            redirectAttributes.addFlashAttribute("showLocationPopup", true);
            return "redirect:/";
        }
        return "index";
    }
}