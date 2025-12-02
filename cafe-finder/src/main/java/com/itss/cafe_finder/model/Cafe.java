package com.itss.cafe_finder.model;

import com.itss.cafe_finder.model.enums.CafeStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "cafes")
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal rating;

    private String description;

    private String address;

    @Enumerated(EnumType.STRING)
    private CafeStatusType status = CafeStatusType.opening;

    private String image;

    private String time;

    private BigDecimal distance;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;
}
