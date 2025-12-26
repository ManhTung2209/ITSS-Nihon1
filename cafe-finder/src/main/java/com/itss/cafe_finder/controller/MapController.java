package com.itss.cafe_finder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    
    @GetMapping("/map")
    public String showLocationPage() {
        return "location";
    }
}