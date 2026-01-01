package com.itss.cafe_finder.service.impl;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import com.itss.cafe_finder.repository.UserRepository;

import dto.request.UserUpdateRequest;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Logic đăng ký user mới
    public void registerUser(User user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email này đã được sử dụng!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa pass
        user.setRoleType(UserRoleType.customer); // Mặc định là customer
        user.setStatus(UserStatusType.active); // Mặc định là active
        userRepository.save(user);
    }

    // Cập nhật thông tin user (name, dob)
    @Transactional
    public User updateUser(UserUpdateRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(req.getName());
        user.setUpdatedOn(ZonedDateTime.now());

        System.out.println("Updating user: " + user);
        return userRepository.save(user);
    }

    // Cập nhật tọa độ người dùng
    @Transactional
    public void updateLocation(Double lat, Double lng) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Authentication failed. Please log in again.");
        }

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            user.setLat(lat);
            user.setLng(lng);
            user.setUpdatedOn(ZonedDateTime.now());

            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user location: " + e.getMessage(), e);
        }
    }

    // Lấy tọa độ hiện tại của người dùng
    @Transactional(readOnly = true)
    public Map<String, Double> getCurrentLocation() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Double> location = new HashMap<>();
        location.put("lat", user.getLat());
        location.put("lng", user.getLng());
        return location;
    }


    // Logic lấy user cho Spring Security kiểm tra đăng nhập
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user, "ROLE_" + user.getRoleType().name().toUpperCase());
    }

    // Custom UserDetails
    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
        private final String name;
        private final Long id;

        public CustomUserDetails(User user, String roleName) {
            super(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(roleName)));
            this.name = user.getName();
            this.id = user.getId();
        }

        public String getFullName() {
            return name;
        }

        public Long getId() {
            return id;
        }
    }
}
