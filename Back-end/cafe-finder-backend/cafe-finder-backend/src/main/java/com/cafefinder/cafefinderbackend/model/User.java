package com.cafefinder.cafefinderbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"userID\"")
    private Integer userID;

    @ManyToOne
    @JoinColumn(name = "\"roleID\"")
    private Role role;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "\"userStatus\"", length = 50)
    private String userStatus;

    @Column(name = "\"createdOn\"")
    private LocalDateTime createdOn;

    @Column(name = "\"updatedOn\"")
    private LocalDateTime updatedOn;

}

