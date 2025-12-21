package com.itss.cafe_finder.service.impl;

import java.time.ZonedDateTime;
import java.util.Collections;

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

    @Transactional
    public User updateUser(UserUpdateRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setName(req.getName());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // Mã hóa password
        user.setEmail(req.getEmail());
        user.setUpdatedOn(ZonedDateTime.now());
        
        System.out.println("Updating user: " + user);
        return userRepository.save(user);
    }


    // Logic lấy user cho Spring Security kiểm tra đăng nhập
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoleType().name().toUpperCase()))
        );
    }






}