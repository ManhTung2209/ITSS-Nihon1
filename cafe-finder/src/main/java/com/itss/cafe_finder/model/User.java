package com.itss.cafe_finder.model;

import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "users")
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
    private UserStatusType status = UserStatusType.active;

    private LocalDate dob;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;
}
