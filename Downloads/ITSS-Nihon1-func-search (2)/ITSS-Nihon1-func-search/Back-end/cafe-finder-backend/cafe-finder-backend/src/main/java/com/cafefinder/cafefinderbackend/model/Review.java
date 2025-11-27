package com.cafefinder.cafefinderbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "\"Review\"")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"reviewID\"")
    private Integer reviewID;

    @ManyToOne
    @JoinColumn(name = "\"cafeID\"")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "\"userID\"")
    private User user;

    @Column(name = "\"reviewStar\"")
    private Integer reviewStar;

    @Column(name = "\"reviewContent\"")
    private String reviewContent;

    @Column(name = "\"reviewStatus\"", length = 50)
    private String reviewStatus;

}

