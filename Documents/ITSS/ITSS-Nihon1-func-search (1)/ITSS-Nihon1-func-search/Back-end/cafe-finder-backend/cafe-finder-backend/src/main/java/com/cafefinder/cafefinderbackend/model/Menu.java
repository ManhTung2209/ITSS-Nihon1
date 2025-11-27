package com.cafefinder.cafefinderbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "\"Menu\"")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"menuID\"")
    private Integer menuID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"fbID\"")
    private Food fb;

    @OneToMany(mappedBy = "menu")
    @JsonIgnore
    private List<Cafe> cafes;


}

