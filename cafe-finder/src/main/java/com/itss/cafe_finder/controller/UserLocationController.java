package com.itss.cafe_finder.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itss.cafe_finder.service.impl.UserService;

import dto.LocationDTO;

@RestController
public class UserLocationController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserLocationController.class);

    @PostMapping("/user/location")
    @ResponseBody
    public Map<String, Object> updateLocation(@RequestBody LocationDTO locationDTO) {
        userService.updateLocation(locationDTO.getLat(), locationDTO.getLng());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        return response;
    }

    @RequestMapping(value = "/user/location/current", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getCurrentLocation() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Double> location = userService.getCurrentLocation();
            response.put("lat", location.get("lat"));
            response.put("lng", location.get("lng"));
        } catch (RuntimeException e) {
            response.put("lat", null);
            response.put("lng", null);
        }
        
        return response;
    }

    @PostMapping("/user/location/update")
    public ResponseEntity<String> updateUserLocation(@RequestBody LocationDTO locationDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("Unauthorized access attempt to /user/location/update");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated");
        }

        log.info("User {} is updating location to lat: {}, lng: {}", auth.getName(), locationDTO.getLat(), locationDTO.getLng());
        userService.updateLocation(locationDTO.getLat(), locationDTO.getLng());
        return ResponseEntity.ok("Location updated successfully");
    }
}
