package com.itss.cafe_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(value = "justLoggedIn", required = false) String justLoggedIn, 
                      Model model) {
        // Nếu có parameter justLoggedIn, thêm vào model
        if ("true".equals(justLoggedIn)) {
            model.addAttribute("showLocationPopup", true);
        }
        return "index";
    }
}