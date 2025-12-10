package com.itss.cafe_finder.repository;

import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String email, Pageable pageable);

    Optional<User> findByEmail(String email);
    
    Page<User> findByRoleType(UserRoleType roleType, Pageable pageable);
    
    Page<User> findByStatus(UserStatusType status, Pageable pageable);
    
    Page<User> findByRoleTypeAndStatus(
            UserRoleType roleType, UserStatusType status, Pageable pageable);
    
    long countByRoleType(UserRoleType roleType);
    
    long countByStatus(UserStatusType status);
}
