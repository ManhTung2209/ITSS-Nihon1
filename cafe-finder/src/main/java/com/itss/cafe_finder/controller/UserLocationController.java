package com.itss.cafe_finder.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itss.cafe_finder.service.impl.UserService;

import dto.LocationDTO;

@RestController
public class UserLocationController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/location")
    @ResponseBody
    public Map<String, Object> updateLocation(@RequestBody LocationDTO locationDTO) {
        userService.updateLocation(locationDTO.getLat(), locationDTO.getLng(), locationDTO.getAddress());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        return response;
    }
}
