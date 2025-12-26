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

@Entity
@Table(name = "users")
@Data
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", insertable = false, updatable = false)
    private UserRoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", insertable = false, updatable = false)
    private UserStatusType status;

    private Double lat;
    private Double lng;

    private LocalDate dob;

    @Column(name = "updated_on", insertable = false, updatable = false)
    private ZonedDateTime updatedOn;

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }
}