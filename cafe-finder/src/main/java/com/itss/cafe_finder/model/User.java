package com.itss.cafe_finder.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private UserRoleType roleType = UserRoleType.customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatusType status = UserStatusType.active;

    private LocalDate dob;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    public User(){
        
    }
}
