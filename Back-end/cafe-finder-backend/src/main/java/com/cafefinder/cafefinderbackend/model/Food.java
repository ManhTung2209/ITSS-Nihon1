package com.cafefinder.cafefinderbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "\"F&B\"")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"fbID\"")
    private Long fbID;

    @Column(nullable = false)
    private Double price;
}

