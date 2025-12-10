package com.itss.cafe_finder.service.admin;

import dto.admin.AdminUserDTO;
import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import com.itss.cafe_finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    public Page<AdminUserDTO> getAllUsers(int page, int size, String keyword, UserRoleType roleType, UserStatusType status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;

        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    keyword, keyword, pageable);
        } else if (roleType != null && status != null) {
            users = userRepository.findByRoleTypeAndStatus(roleType, status, pageable);
        } else if (roleType != null) {
            users = userRepository.findByRoleType(roleType, pageable);
        } else if (status != null) {
            users = userRepository.findByStatus(status, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(this::toDTO);
    }

    public AdminUserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public AdminUserDTO updateUserStatus(Long id, UserStatusType status) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setStatus(status);
            user.setUpdatedOn(java.time.ZonedDateTime.now());
            return toDTO(userRepository.save(user));
        }
        return null;
    }

    public AdminUserDTO updateUserRole(Long id, UserRoleType roleType) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setRoleType(roleType);
            user.setUpdatedOn(java.time.ZonedDateTime.now());
            return toDTO(userRepository.save(user));
        }
        return null;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getActiveUsers() {
        return userRepository.countByStatus(UserStatusType.active);
    }

    public long getBannedUsers() {
        return userRepository.countByStatus(UserStatusType.banned);
    }

    public long getAdminUsers() {
        return userRepository.countByRoleType(UserRoleType.admin);
    }

    private AdminUserDTO toDTO(User user) {
        AdminUserDTO dto = new AdminUserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoleType(user.getRoleType());
        dto.setStatus(user.getStatus());
        dto.setDob(user.getDob());
        dto.setUpdatedOn(user.getUpdatedOn());
        return dto;
    }
}

