package com.cafefinder.cafefinderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "\"Cafe\"")
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"cafeID\"")
    private Integer cafeID;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"menuID\"")
    @JsonIgnoreProperties({"cafes"})
    private Menu menu;

    @Column(name = "\"name\"")
    private String name;

    @Column(name = "\"address\"")
    private String address;

    @Column(name = "\"rating\"")
    private BigDecimal rating;

    @Column(name = "\"description\"")
    private String description;

    @Column(name = "\"openingTime\"")
    private String openingTime;

    @Column(name = "\"image\"")
    private String image;

    @Column(name = "\"cafeStatus\"")
    private String cafeStatus;
}
