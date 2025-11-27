package com.cafefinder.cafefinderbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"roleID\"")
    private Integer roleID;

    @Column(name = "\"roleName\"", length = 50, nullable = false)
    private String roleName;

}
