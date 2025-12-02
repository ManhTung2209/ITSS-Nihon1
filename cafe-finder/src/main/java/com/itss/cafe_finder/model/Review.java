package com.itss.cafe_finder.model;

import com.itss.cafe_finder.model.enums.ReviewStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private Integer star;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReviewStatusType status = ReviewStatusType.pending;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;
}
